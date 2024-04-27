package ru.neoflex.tasks;

import java.util.*;
import java.util.concurrent.*;

public class Task4DistributedCalculator {
    public static void main(String[] args) throws InterruptedException {
        CalculatorPublisher calculatorPublisher = new CalculatorPublisher();
        final int stepsCount = calculatorPublisher.generateSteps();
        final int publishedActionsCount = calculatorPublisher.publishActions();
        CalculatorSubscriber calculatorSubscriber = new CalculatorSubscriber();
        final CountDownLatch messagesToConsume = new CountDownLatch(publishedActionsCount);
        calculatorSubscriber.start(messagesToConsume);
        messagesToConsume.await();
        calculatorSubscriber.printEveryStep(stepsCount);
    }

    static class ActionOnValue {
        final String valueId;
        final int step;
        final char operator;
        final double value;

        public ActionOnValue(String valueId, int step, char operator, double value) {
            this.valueId = valueId;
            this.step = step;
            this.operator = operator;
            this.value = value;
        }

        public double applyTo(Double current) {
            switch (operator) {
                case '=':
                    return value;
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

    static final BlockingQueue<ActionOnValue> MESSAGE_BROKER = new ArrayBlockingQueue<>(5);

    static class CalculatorPublisher implements AutoCloseable {
        static final ExecutorService prdThreadPool = Executors.newFixedThreadPool(10);
        final List<ActionOnValue> actions = new ArrayList<>();

        public int generateSteps() {
            final char[] operators = new char[]{'=', '+', '*', '/', '-'};
            final String[] valNames = {"A", "B", "C"};
            int step = 0;
            for (char operator : operators) {
                step++;
                double value = 2.0;
                for (String valName : valNames) {
                    actions.add(new ActionOnValue(valName, step, operator, value++));
                }
            }
            return step;
        }

        public int publishActions() {
            actions.forEach(actionOnValue -> prdThreadPool.execute(() -> {
                while (!MESSAGE_BROKER.offer(actionOnValue)) ;
            }));
            return actions.size();
        }

        @Override
        public void close() throws Exception {
            prdThreadPool.shutdownNow();
        }
    }

    static class CalculatorSubscriber implements AutoCloseable {  // TODO: fix Subscriber Logic
        static final ScheduledExecutorService cnsThreadPool = Executors.newScheduledThreadPool(10);
        static final Map<String, Double> subscriberVariables = new HashMap<>();
        private CountDownLatch messagesToConsume;

        void start(CountDownLatch messagesToConsume) {
            this.messagesToConsume = messagesToConsume;
            cnsThreadPool.scheduleAtFixedRate(() -> consumeActionOnValue(MESSAGE_BROKER.poll()), 0, 5, TimeUnit.MILLISECONDS);
            cnsThreadPool.scheduleAtFixedRate(() -> consumeActionOnValue(MESSAGE_BROKER.poll()), 1, 5, TimeUnit.MILLISECONDS);
            cnsThreadPool.scheduleAtFixedRate(() -> consumeActionOnValue(MESSAGE_BROKER.poll()), 2, 5, TimeUnit.MILLISECONDS);
        }

        void consumeActionOnValue(ActionOnValue actionOnValue) {
            if (actionOnValue == null) return;
            messagesToConsume.countDown();
            System.out.println("received: " + actionOnValue);
            subscriberVariables.compute(actionOnValue.valueId, (valueId, current) -> actionOnValue.applyTo(current));
        }

        void printEveryStep(int stepCount) {
            for (int step = 1; step <= stepCount; step++) {
                printStateForStep(step);
            }
        }

        void printStateForStep(int step) {
            System.out.printf("%n=== subscriberSnapShot. state for step=%d ===%n", step);
            subscriberVariables.forEach((valueId, value) -> System.out.printf("\t%s = %.3f%n", valueId, value));
        }

        @Override
        public void close() throws Exception {
            cnsThreadPool.shutdownNow();
        }
    }
}
