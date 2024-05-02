package ru.neoflex.tasks;

import java.util.*;
import java.util.concurrent.*;

public class Task4DistributedCalculator {
    public static void main(String[] args) throws InterruptedException {
        try (CalcPub calcPub = new CalcPub(); CalcSub calcSub = new CalcSub()) {
            // Example: ( ( ( ( =x +x ) *x) /x) -x) = x | if x = 2 then =2 +2 -> 4 *2 -> 8 /2 -> 4 -2 -> 2
            final int stepsCount = calcPub.generateSteps(new String[]{"A", "B", "C"}, 2.0, new char[]{'=', '+', '*', '/', '-'});
            final int pubMsgCount = calcPub.publishActions();
            final CountDownLatch msgToConsumeCount = new CountDownLatch(pubMsgCount);
            calcSub.start(msgToConsumeCount);
            if (!msgToConsumeCount.await(5, TimeUnit.SECONDS)) {
                System.err.println("Timeout Exceeded");
            }
            calcSub.printEveryStep(stepsCount);
        }
    }

    static final BlockingQueue<CalcAction> MESSAGE_BROKER = new ArrayBlockingQueue<>(5);

    static class CalcSub implements AutoCloseable {  // TODO: fix Calculator Subscriber Logic
        static final ScheduledExecutorService subThreadPool = Executors.newScheduledThreadPool(5);
        static final Map<String, Double> subscriberVariables = new HashMap<>();
        private CountDownLatch messagesToConsume;

        void start(CountDownLatch messagesToConsume) { // TODO: fix if necessary
            this.messagesToConsume = messagesToConsume;
            subThreadPool.scheduleAtFixedRate(() -> consumeActionOnValue(MESSAGE_BROKER.poll()), 0, 10, TimeUnit.MILLISECONDS);
            subThreadPool.scheduleAtFixedRate(() -> consumeActionOnValue(MESSAGE_BROKER.poll()), 1, 10, TimeUnit.MILLISECONDS);
            subThreadPool.scheduleAtFixedRate(() -> consumeActionOnValue(MESSAGE_BROKER.poll()), 2, 10, TimeUnit.MILLISECONDS);
        }

        void consumeActionOnValue(CalcAction calcAction) { // TODO: fix if necessary
            try {
                if (calcAction == null) return;
                System.out.println("received: " + calcAction);
                messagesToConsume.countDown();
                subscriberVariables.compute(calcAction.varName, (varName, current) -> calcAction.applyTo(current));
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        void printEveryStep(int stepCount) { // TODO: fix if necessary
            for (int step = 1; step <= stepCount; step++) {
                printStateForStep(step, stepCount);
            }
        }

        void printStateForStep(int step, int stepCount) { // TODO: fix if necessary
            System.out.printf("%n=== subscriber variables for step=%d/%d ===%n", step, stepCount);
            subscriberVariables.forEach((valueId, value) -> System.out.printf("\t%s = %.3f%n", valueId, value));
        }

        @Override
        public void close() {
            subThreadPool.shutdownNow();
        }
    }

    /** Read Only Class */
    static class CalcAction {
        final String varName;
        final int step;
        final char operator;
        final double value;

        public CalcAction(String varName, int step, char operator, double value) {
            this.varName = varName;
            this.step = step;
            this.operator = operator;
            this.value = value;
        }

        public Double applyTo(Double current) {
            if (operator == '=') return value;
            if (current == null) return null;
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
            return String.format("%s step %d: %s %.2f", varName, step, operator, value);
        }
    }
    /** Read Only Class */
    static class CalcPub implements AutoCloseable { // Publisher is OK
        static final ExecutorService pubThreadPool = Executors.newFixedThreadPool(10);
        final List<CalcAction> actions = new ArrayList<>();

        public int generateSteps(final String[] varNames, final double value, final char[] operators) {
            int step = 0;
            for (char operator : operators) {
                step++;
                for (String valName : varNames) {
                    actions.add(new CalcAction(valName, step, operator, value));
                }
            }
            return step;
        }

        public int publishActions() {
            actions.forEach(calcAction -> pubThreadPool.execute(() -> {
                while (!MESSAGE_BROKER.offer(calcAction)) ;
            }));
            return actions.size();
        }

        @Override
        public void close() {
            pubThreadPool.shutdownNow();
        }
    }
}
