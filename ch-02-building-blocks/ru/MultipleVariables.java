package ru;
public class MultipleVariables {
  private static String s1, s2;
  private static String s3 = "yes", s4 = "no";
  public static void main(String... z) {
    System.out.println("s3="+s3+" s4="+s4);
    int i1, i2, i3 = 0;
    System.out.println("i3="+i3);
  }
}