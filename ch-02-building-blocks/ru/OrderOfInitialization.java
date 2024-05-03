package ru;
public class OrderOfInitialization {
  private String field1 = "field 1";
  { System.out.println("setting in instance initializer field 1"); }
  private String field2;
  public OrderOfInitialization() {
    field2 = "field 2";
    System.out.println("setting in constructor field 2");
  }
  public static void main(String z[]) {
    OrderOfInitialization ooi = new OrderOfInitialization();
    System.out.println("ooi.field1=" + ooi.field1);
    System.out.println("ooi.field2=" + ooi.field2);
  }
}