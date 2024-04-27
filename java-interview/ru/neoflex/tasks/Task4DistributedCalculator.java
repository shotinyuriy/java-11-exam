package ru.neoflex.tasks;

import java.util.*;
import java.util.concurrent.*;

public class Task4DistributedCalculator {
    public static void main(String[] args) throws InterruptedException {
        try (CalcPub calcPub = new CalcPub(); CalcSub calcSub = new CalcSub();) {
            final int stepsCount = calcPub.generateSteps();
            final int pubMsgCount = calcPub.publishActions();
            final CountDownLatch msgToConsumeCount = new CountDownLatch(pubMsgCount);
            calcSub.start(msgToConsumeCount);
            if (!msgToConsumeCount.await(5, TimeUnit.SECONDS)) {
                System.err.println("Timeout Exceeded");
            }
            calcSub.printEveryStep(stepsCount);
        }
    }

    static class CalcAction {
        final String valueId;
        final int step;
        final char operator;
        final double value;

        public CalcAction(String valueId, int step, char operator, double value) {
            this.valueId = valueId;
            this.step = step;
            this.operator = operator;
            this.value = value;
        }

        public Double applyTo(Double current) {
            if (operator == '=') return value;
            if (current == null) return current;
            switch (operator) {
                case '+':
                    return current + value;
                case '-':
                    return current - value;
                case '*':
                    return current * value;
                case '/':
                    return current / value;
                default:
                    throw new UnsupportedOperationException("Unsupported " + operator);
            }
        }

        @Override
        public String toString() {
            return String.format("%s step %d: %s %.2f", valueId, step, operator, value);
        }
    }

    static final BlockingQueue<CalcAction> MESSAGE_BROKER = new ArrayBlockingQueue<>(5);

    static class CalcPub implements AutoCloseable { // Publisher is OK
        static final ExecutorService prdThreadPool = Executors.newFixedThreadPool(10);
        final List<CalcAction> actions = new ArrayList<>();

        public int generateSteps() {
            // ( ( ( ( =x +x ) *x) /x) -x) = x | if x = 2 then 2 -> 4 -> 8 -> 4 -> 2
            final char[] operators = new char[]{'=', '+', '*', '/', '-'};
            final String[] varNames = {"A", "B", "C"};
            int step = 0;
            for (char operator : operators) {
                step++;
                double value = 2.0;
                for (String valName : varNames) {
                    actions.add(new CalcAction(valName, step, operator, value));
                }
            }
            return step;
        }

        public int publishActions() {
            actions.forEach(calcAction -> prdThreadPool.execute(() -> {
                while (!MESSAGE_BROKER.offer(calcAction)) ;
            }));
            return actions.size();
        }

        @Override
        public void close() {
            prdThreadPool.shutdownNow();
        }
    }

    static class CalcSub implements AutoCloseable {  // TODO: fix Subscriber Logic
        static final ScheduledExecutorService cnsThreadPool = Executors.newScheduledThreadPool(5);
        static final Map<String, Double> subscriberVariables = new HashMap<>();
        private CountDownLatch messagesToConsume;

        void start(CountDownLatch messagesToConsume) {
            this.messagesToConsume = messagesToConsume;
            cnsThreadPool.scheduleAtFixedRate(() -> consumeActionOnValue(MESSAGE_BROKER.poll()), 0, 10, TimeUnit.MILLISECONDS);
            cnsThreadPool.scheduleAtFixedRate(() -> consumeActionOnValue(MESSAGE_BROKER.poll()), 1, 10, TimeUnit.MILLISECONDS);
            cnsThreadPool.scheduleAtFixedRate(() -> consumeActionOnValue(MESSAGE_BROKER.poll()), 2, 10, TimeUnit.MILLISECONDS);
        }

        void consumeActionOnValue(CalcAction calcAction) {
            try {
                if (calcAction == null) return;
                System.out.println("received: " + calcAction);
                messagesToConsume.countDown();
                subscriberVariables.compute(calcAction.valueId, (valueId, current) -> calcAction.applyTo(current));
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        void printEveryStep(int stepCount) {
            for (int step = 1; step <= stepCount; step++) {
                printStateForStep(step, stepCount);
            }
        }

        void printStateForStep(int step, int stepCount) {
            System.out.printf("%n=== subscriberSnapShot. state for step=%d/%d ===%n", step, stepCount);
            subscriberVariables.forEach((valueId, value) -> System.out.printf("\t%s = %.3f%n", valueId, value));
        }

        @Override
        public void close() {
            cnsThreadPool.shutdownNow();
        }
    }
}
