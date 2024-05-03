package ru;
public class Egg {
  public Egg() {
    number = getNumber();
    number = 5;
  }
  public int getNumber() {
    System.out.println("getNumber()="+number); 
    return number;
  }
  public static void main(String[] argv) {
    Egg egg = new Egg();
    System.out.println(egg.number);
  }
  private int number = 3;
  { number = 4; } }