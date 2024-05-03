package ru;
public class Swan {
  int numberEggs;
  public static void main(String... z) {
    Swan mother = new Swan();
    mother.numberEggs = 1;
    System.out.println("Mother.numberEggs="+mother.numberEggs);
  }
}