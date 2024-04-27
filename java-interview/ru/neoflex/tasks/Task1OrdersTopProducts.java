package ru.neoflex.tasks;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Task1OrdersTopProducts {

    public static void printTop2ProductsOfMonth(List<CommerceOrder> commerceOrders, Month month) {
        System.out.println("=== Top 2 Product of " + month.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " ===");
        // TODO: print ${productName}=${sum(count)}
        System.out.println(commerceOrders);
    }

    public static void main(String[] args) {
        List<CommerceOrder> commerceOrders = new ArrayList<>();
        commerceOrders.add(new CommerceOrder(LocalDate.parse("2024-01-09"), Arrays.asList(
                new CommerceItem("pears", 2), new CommerceItem("apples", 3)))
        );
        commerceOrders.add(new CommerceOrder(LocalDate.parse("2024-03-12"), Arrays.asList(
                new CommerceItem("apples", 3), new CommerceItem("babanas", 1)))
        );
        commerceOrders.add(new CommerceOrder(LocalDate.parse("2024-03-14"), Arrays.asList(
                new CommerceItem("babanas", 1)))
        );
        commerceOrders.add(new CommerceOrder(LocalDate.parse("2024-02-16"), Arrays.asList(
                new CommerceItem("pears", 2), new CommerceItem("babanas", 5)))
        );
        commerceOrders.add(new CommerceOrder(LocalDate.parse("2024-03-09"), Arrays.asList(
                new CommerceItem("apples", 1)))
        );
        commerceOrders.add(new CommerceOrder(LocalDate.parse("2024-03-15"), Arrays.asList(
                new CommerceItem("pears", 3), new CommerceItem("babanas", 7)))
        );
//        commerceOrders.forEach(order -> {
//            System.out.println("orderDate " + order.orderDate);
//            order.commerceItems.forEach(item -> System.out.println("\t" + item));
//            System.out.println();
//        });
        printTop2ProductsOfMonth(commerceOrders, Month.MARCH);
    }

    static class CommerceItem {
        public String productName;
        public int count;

        public CommerceItem(String productName, int count) {
            this.productName = productName;
            this.count = count;
        }

        @Override
        public String toString() {
            return "item{" + "productName='" + productName + '\'' + ", count=" + count + '}';
        }
    }

    static class CommerceOrder {
        public LocalDate orderDate;
        public List<CommerceItem> commerceItems;

        public CommerceOrder(LocalDate orderDate, List<CommerceItem> commerceItems) {
            this.orderDate = orderDate;
            this.commerceItems = commerceItems;
        }

        @Override
        public String toString() {
            return "order{" + "orderDate=" + orderDate + ", commerceItems=" + commerceItems + '}';
        }
    }
}
