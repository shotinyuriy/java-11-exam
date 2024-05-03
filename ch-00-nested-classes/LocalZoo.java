public class LocalZoo {
    private int foodInZoo = 10;

    {
        System.out.println("foodInZoo=" + foodInZoo);
    }

    public void feed(int amoutOfFood) {
        abstract class Animal {
            public abstract void feed(int amountOfFood);
        }
        final class Zebra extends Animal {
            public void feed(int amountOfFood) {
                foodInZoo -= amountOfFood;
                System.out.println("feed Zebra foodInZoo=" + foodInZoo);
            }
        }

        new Zebra().feed(amoutOfFood);

        new Animal() {

            @Override
            public void feed(int amountOfFood) {
                foodInZoo -= amountOfFood;
                System.out.println("feed Anonimous Animal 1 foodInZoo=" + foodInZoo);
            }
        }.feed(1);
    }
}
