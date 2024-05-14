package dailyquestion.easy.q680;

import dailyquestion.easy.AbstractTest;

public class Test extends AbstractTest {

    private static class TestData {
        String arg1;
        boolean expectedValue;

        public TestData(String arg1, boolean expectedValue) {
            this.arg1 = arg1;
            this.expectedValue = expectedValue;
        }

        public String getArg1() {
            return arg1;
        }

        public boolean isExpectedValue() {
            return expectedValue;
        }
    }

    public static void main(String[] args) {
        TestData[] testDatas = new TestData[]{
//                new TestData("a", true),
//                new TestData("ab", true),
//                new TestData("abc", false),
//                new TestData("abca", true),
//                new TestData("abcda", false),
//                new TestData("abbda", true),
//                new TestData("abb da", false),
//                new TestData("arozaupalanalapuazora", true),
//                new TestData("arozaupaltanalapuazora", true),
//                new TestData("arozaupal analapuazora", true),
//                new TestData("arozaupala na lapuazora", false),
//                new TestData("arozaupala naglapuazora", false),
                new TestData("aguokepatgbnvfqmgmlcupuufxoohdfpgjdmysgvhmvffcnqxjjxqncffvmhvgsymdjgpfdhooxfuupuculmgmqfvnbgtapekouga", true),
        };
        Solution solution = new Solution1();
        for (TestData testData : testDatas) {
            boolean result = solution.validPalindrome(testData.getArg1());
            System.out.printf("arg1=%s expected=%s actual=%s\n",
                    testData.getArg1(), testData.isExpectedValue(), result);
        }
    }
}
