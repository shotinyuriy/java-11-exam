package ru.neoflex.task1;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Task1OrdersTopCustomers {
    static class CommerceOrder {
        public long customerId;
        public LocalDate orderDate;
        public double orderTotal;

        public CommerceOrder() {
        }

        public CommerceOrder(long customerId, LocalDate orderDate, double orderTotal) {
            this.customerId = customerId;
            this.orderDate = orderDate;
            this.orderTotal = orderTotal;
        }
        @Override
        public String toString() {
            return "CommerceOrder{customerId=" + customerId + ", orderDate=" + orderDate +
                    ", orderTotal=" + orderTotal + "}\n";
        }
    }

    public static void main(String[] args) {
        final int customerCount = 7;
        List<CommerceOrder> commerceOrders = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            var order = new CommerceOrder();
            order.customerId = i % customerCount + 1;
            var month = Month.of((i / 9) % 12 + 1);
            order.orderDate = LocalDate.of(2020 + i / 99, month, i % month.minLength() + 1);
            order.orderTotal = 1.0 + i % 10 + i % 10 / 10.0;
            commerceOrders.add(order);
        }
        System.out.printf("commerceOrders=" + commerceOrders);
    }

    public static void printTopNCustomers(int N, List<CommerceOrder> commerceOrders) {

    }
}
