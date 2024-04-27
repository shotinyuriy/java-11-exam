package ru.neoflex.task1;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;

public class Task4DistributedCalculator {
    static class ActionOnValue {
        final String valueId;
        final int step;
        final Function<Double, Double> action;

        public ActionOnValue(String valueId, int step, Function<Double, Double> action) {
            this.valueId = valueId;
            this.step = step;
            this.action = action;
        }

        static ActionOnValue act(String valueId, int step, Function<Double, Double> action) {
            return new ActionOnValue(valueId, step, action);
        }
    }

    static Function<Double, Double> calc(char operator, double value) {
        switch (operator) {
            case '=':
                return current -> value;
            case '+':
                return current -> current + value;
            case '-':
                return current -> current - value;
            case '*':
                return current -> current * value;
            case '/':
                return current -> current / value;
            default:
                throw new UnsupportedOperationException("Unsupported " + operator);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CalculatorPublisher calculatorPublisher = new CalculatorPublisher();
        int steps = calculatorPublisher.generateSteps();
        int publishedActions = calculatorPublisher.publishActions();
        CalculatorSubscriber calculatorSubscriber = new CalculatorSubscriber(publishedActions);
        calculatorSubscriber.start();
        calculatorSubscriber.messagesToConsume.await();
        for (int step = 1; step <= steps; step++) {
            calculatorSubscriber.printStateOnDay(step);
        }
    }

    static final BlockingQueue<ActionOnValue> MESSAGE_BROKER = new ArrayBlockingQueue<>(5);

    static class CalculatorPublisher implements AutoCloseable {
        static final ExecutorService prdThreadPool = Executors.newFixedThreadPool(10);
        final List<ActionOnValue> actions = new ArrayList<>();

        public int generateSteps() {
            final char[] operators = new char[]{'=', '+', '*', '/', '-'}; // = ( ( (x + x) * x) / x) - x
            final String[] valNames = {"A", "B", "C"};
            int step = 0;
            for (char operator : operators) {
                step++;
                double value = 2.0;
                for (String valName : valNames) {
                    actions.add(new ActionOnValue(valName, step, calc(operator, value++)));
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

    static class CalculatorSubscriber implements AutoCloseable {
        static final ScheduledExecutorService cnsThreadPool = Executors.newScheduledThreadPool(10);
        static final Map<String, Double> subscriberSnapShot = new HashMap<>();
        public final CountDownLatch messagesToConsume;

        public CalculatorSubscriber(int messagesExpected) {
            messagesToConsume = new CountDownLatch(messagesExpected);
        }

        void start() {
            cnsThreadPool.scheduleAtFixedRate(() -> consumeActionOnValue(MESSAGE_BROKER.poll()), 0, 5, TimeUnit.MILLISECONDS);
            cnsThreadPool.scheduleAtFixedRate(() -> consumeActionOnValue(MESSAGE_BROKER.poll()), 1, 5, TimeUnit.MILLISECONDS);
            cnsThreadPool.scheduleAtFixedRate(() -> consumeActionOnValue(MESSAGE_BROKER.poll()), 2, 5, TimeUnit.MILLISECONDS);
        }

        void consumeActionOnValue(ActionOnValue actionOnValue) {
            if (actionOnValue == null) return;
            messagesToConsume.countDown();
            subscriberSnapShot.compute(actionOnValue.valueId, (valueId, current) -> actionOnValue.action.apply(current));
        }

        void printStateOnDay(int step) {
            System.out.printf("%n=== subscriberSnapShot. state for step=%d ===%n", step);
            subscriberSnapShot.forEach((valueId, value) -> System.out.printf("\t%s = %.3f%n", valueId, value));
        }

        @Override
        public void close() throws Exception {
            cnsThreadPool.shutdownNow();
        }
    }
}
