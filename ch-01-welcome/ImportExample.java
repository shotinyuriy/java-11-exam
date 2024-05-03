import java.util.*; // import tells us where to find Random
public class ImportExample {
  public static void main(String... arr) {
    Random r = new Random();
    System.out.println(r.nextInt(10)); // print a number 0-9
  }
}