public class InnerZoo {
    private int foodInZoo;

    {
        foodInZoo = 10;
        System.out.println("foodInZoo=" + foodInZoo);
    }

     class Zebra {
        public void feedZebra(int amountOfFood) {
            foodInZoo -= amountOfFood;
            System.out.println("foodInZoo=" + foodInZoo);
        }
    }
}
