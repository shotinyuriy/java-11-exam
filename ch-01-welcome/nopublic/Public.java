package nopublic;
public class Public {
  public static void main(String... inputs) {
    NoPublic noPublic = new NoPublicChild();
    System.out.println("noPublic=" + noPublic);
  }
}