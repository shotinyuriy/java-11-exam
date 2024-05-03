public class ZooMain {
    public static void main(String... zzz) {
        new InnerZoo().new Zebra().feedZebra(3);

        new StaticInnerZoo.Zebra(new StaticInnerZoo()).feedZebra(4);

        new LocalZoo().feed(5);
    }
}
