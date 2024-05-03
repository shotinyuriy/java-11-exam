public class StaticInnerZoo {
    private int foodInZoo = 10;

    {
        System.out.println("foodInZoo=" + foodInZoo);
    }

    protected static class Zebra {
        private StaticInnerZoo zoo;

        public Zebra(StaticInnerZoo zoo) {
            this.zoo = zoo;
        }

        public void feedZebra(int amountOfFood) {
            zoo.foodInZoo -= amountOfFood;
            System.out.println("foodInZoo=" + zoo.foodInZoo);
        }
    }
}
