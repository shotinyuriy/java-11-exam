package dailyquestion.easy.q679;

import dailyquestion.easy.AbstractTest;

import java.util.Arrays;

public class Test extends AbstractTest {

    private static class TestData {
        int[] cards;
        boolean expectedValue;

        public TestData(int[] cards, boolean expectedValue) {
            this.cards = cards;
            this.expectedValue = expectedValue;
        }

        public int[] getCards() {
            return cards;
        }

        public boolean isExpectedValue() {
            return expectedValue;
        }
    }

    public static void main(String[] args) {
        TestData[] testDatas = new TestData[]{
                new TestData(new int[]{1, 1, 1, 1}, false),
                new TestData(new int[]{2, 2, 2, 2}, false),
                new TestData(new int[]{3, 3, 3, 3}, true),
                new TestData(new int[]{4, 4, 4, 4}, true),
                new TestData(new int[]{5, 5, 5, 5}, true),
                new TestData(new int[]{6, 6, 6, 6}, true),
                new TestData(new int[]{7, 7, 7, 7}, false),
                new TestData(new int[]{8, 8, 8, 8}, false),
                new TestData(new int[]{9, 9, 9, 9}, false),

                new TestData(new int[]{1, 2, 3, 4}, true),
                new TestData(new int[]{2, 3, 4, 5}, true),
                new TestData(new int[]{3, 4, 5, 6}, true),
                new TestData(new int[]{4, 5, 6, 7}, true),
                new TestData(new int[]{5, 6, 7, 8}, true),
                new TestData(new int[]{6, 7, 8, 9}, true),

                new TestData(new int[]{2, 2, 3, 4}, true),
                new TestData(new int[]{3, 3, 4, 5}, true),
                new TestData(new int[]{4, 4, 5, 6}, true),
                new TestData(new int[]{5, 5, 6, 7}, true),
                new TestData(new int[]{6, 6, 7, 8}, false),
                new TestData(new int[]{7, 7, 8, 9}, false),
                new TestData(new int[]{4, 1, 8, 7}, true),
                new TestData(new int[]{3, 3, 7, 7}, true), // ((3 / 7) + 3) * 7 = 24/7 * 7
                new TestData(new int[]{1, 5, 9, 1}, false),
                new TestData(new int[]{8, 1, 6, 6}, true),
                new TestData(new int[]{1, 3, 4, 6}, true), // (1/3 + 4) * 6
                new TestData(new int[]{1, 9, 1, 2}, true),
                new TestData(new int[]{1, 2, 1, 2}, false),
                new TestData(new int[]{3, 3, 8, 8}, true),
        };
        Solution solution = new Solution2();
        for (TestData testData : testDatas) {
            boolean result = solution.judgePoint24(testData.getCards());
            System.out.printf("cards=%s expected=%s actual=%s\n",
                    Arrays.toString(testData.getCards()), testData.isExpectedValue(), result);
        }
    }
}
