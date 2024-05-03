import java.util.List;
import java.util.stream.Stream;

public class GenericsTest {
    public static void main(String[] args) {

    }

    public static void listProducer(List<? extends Class3> list) {
        if (!list.isEmpty()) {
            Class3 class3 = list.get(0);
            Class2 class2 = list.get(0);
            Class1 class1 = list.get(0);
        }
        Container2 class3 = new Class3().set1(1).set2(3.0);
        Stream.of(new Class3(), new Class3());
    }

    public static void listConsumer(List<? super Class3> list) {
        list.add(new Class3());
        list.add(new Class4());
    }

    public static interface Container {
        <T extends Container> T set1(int one);
    }

    public static interface Container2 extends Container {
        <T extends Container2> T set2(double two);
    }

    public static class Class1 {

    }

    public static class Class2 extends Class1 {

    }

    public static class Class3 extends Class2 implements Container2 {

        @Override
        public Class3 set1(int one) {
            return this;
        }

        @Override
        public Class3 set2(double two) {
            return this;
        }
    }

    public static class Class4 extends Class3 {

    }
}
