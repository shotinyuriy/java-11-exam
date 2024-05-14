package dailyquestion.easy.q0;

import dailyquestion.easy.AbstractTest;
import dailyquestion.easy.q679.Solution2;

import java.util.Arrays;

public class Test extends AbstractTest {

    private static class TestData {
        int arg1;
        boolean expectedValue;

        public TestData(int arg1, boolean expectedValue) {
            this.arg1 = arg1;
            this.expectedValue = expectedValue;
        }

        public int getArg1() {
            return arg1;
        }

        public boolean isExpectedValue() {
            return expectedValue;
        }
    }

    public static void main(String[] args) {
        TestData[] testDatas = new TestData[]{
                new TestData(1, true)
        };
        Solution solution = new Solution() {

            @Override
            public boolean solutionMethod(int arg1) {
                return false;
            }
        };
        for (TestData testData : testDatas) {
            boolean result = solution.solutionMethod(testData.getArg1());
            System.out.printf("arg1=%s expected=%s actual=%s\n",
                    testData.getArg1(), testData.isExpectedValue(), result);
        }
    }
}
