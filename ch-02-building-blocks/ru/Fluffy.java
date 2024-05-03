package ru;
import java.util.function.Supplier;
public class Fluffy {
  static { System.out.println("Before static constant"); }
  private static final String CONSTANT = new Supplier<String>() {
      public String get(){ System.out.println("Supplier"); return "constant"; }
    }.get();
  static { System.out.println("After static constant"); }
  { System.out.println("Before setting a field"); }
  private String name = "Fluffy";
  { System.out.println("After setting a field"); }
  public Fluffy() {
    System.out.println("Creating a Fluffy");
  }
  public static void main(String... z) {
    System.out.println("Start main");
    Fluffy f = new Fluffy();
    System.out.println("End main");
  }
}