package ru;
public class NumberBase {
  public static void main(String... y) {
    byte b1 = 017;
    byte bmin = -0200;
    byte bmax = 0177;

    short s1 = 0xff;
    short smin = (short)(-0x8000);
    short smax = (short)0x7fff;

    int i1 = 0b10011101;
    float f1 = 1_2.3_4f;
    double d1 = 5__6.7__8;

    Object[] numbers = new Object[] {
      b1, bmin, bmax, s1, smin, smax,
      i1, f1, d1
    };
    for(Object number : numbers) {
      System.out.println(number);
    }
  }
}