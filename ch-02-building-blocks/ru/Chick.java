package ru;
public class Chick {
  private String name = "Fluffy";
  { System.out.println("setting field"); }
  public Chick() {
    name = "Tiny";
    System.out.println("setting constructor");
  }
  public static void main(String www[]) {
    Chick chick = new Chick();
    System.out.println("main chick name="+chick.name); } }