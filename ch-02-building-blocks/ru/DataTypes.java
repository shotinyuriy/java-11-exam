package ru;
public class DataTypes {
  public static void main(String what[]) {
    boolean b1 = true;
    boolean b2 = false;
    
    byte bmin = -128;
    byte bmax = 127;
    
    short smin = -32768;
    short smax = 32767;
    short stoomax = (short)32768;

    int imin = -2_147_483_648;
    int imax = 2_147_483_647;

    long lmin = -9_223_372_036_854_775_808L;
    long lmax = 9_223_372_036_854_775_807L;

    float fmin = Float.MIN_VALUE;
    float fmax = Float.MAX_VALUE;

    double dmin = Double.MIN_VALUE;
    double dmax = Double.MAX_VALUE;

    char cmin = Character.MIN_VALUE;
    char cmax = Character.MAX_VALUE;

    Object[] dtypes = new Object[] {
      b1, b2, bmin, bmax, smin, smax, stoomax,
      imin, imax, lmin, lmax,
      fmin, fmax, dmin, dmax,
      cmin, cmax
    };

    for(Object dtype : dtypes) {
      System.out.println(dtype);
    }
    System.out.println("Char");
    System.out.println((int)cmin);
    System.out.println((int)cmax);
  }
}