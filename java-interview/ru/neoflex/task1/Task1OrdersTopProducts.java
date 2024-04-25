package ru.neoflex.task1;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Task1OrdersTopProducts {
    static class CommerceItem {
        public String productName;
        public int count;

        public CommerceItem(String productName, int count) {
            this.productName = productName;
            this.count = count;
        }

        @Override
        public String toString() {
            return "CommerceItem{" + "productName='" + productName + '\'' + ", count=" + count + '}';
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
            return "CommerceOrder{" + "orderDate=" + orderDate + ", commerceItems=" + commerceItems + '}';
        }
    }

    public static void printTop2ProductsOfMonth(List<CommerceOrder> commerceOrders, Month month) {
        System.out.println("=== Top 2 Product of " + month.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " ===");
        // TODO: print ${productName}=${sum(count)}
        System.out.println(commerceOrders);
    }

    public static void main(String[] args) {
        List<CommerceOrder> commerceOrders = new ArrayList<>();
        commerceOrders.add(new CommerceOrder(LocalDate.parse("2024-01-09"), List.of(
                new CommerceItem("pears", 10), new CommerceItem("apples", 3)))
        );
        commerceOrders.add(new CommerceOrder(LocalDate.parse("2024-03-12"), List.of(
                new CommerceItem("apples", 3), new CommerceItem("babanas", 1)))
        );
        commerceOrders.add(new CommerceOrder(LocalDate.parse("2024-03-14"), List.of(
                new CommerceItem("babanas", 1)))
        );
        commerceOrders.add(new CommerceOrder(LocalDate.parse("2024-02-16"), List.of(
                new CommerceItem("pears", 10), new CommerceItem("babanas", 5)))
        );
        commerceOrders.add(new CommerceOrder(LocalDate.parse("2024-03-09"), List.of(
                new CommerceItem("apples", 1)))
        );
        commerceOrders.add(new CommerceOrder(LocalDate.parse("2024-03-15"), List.of(
                new CommerceItem("pears", 3), new CommerceItem("babanas", 7)))
        );
        System.out.println("commerceOrders count=" + commerceOrders.size());
        printTop2ProductsOfMonth(commerceOrders, Month.MARCH);
    }
}
