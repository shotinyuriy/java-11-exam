package dailyquestion.easy.q1544;

import dailyquestion.easy.AbstractTest;

import java.time.Instant;

class Test extends AbstractTest {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String[] strings = {
                "MeSikSIIfsaoWOGojdgjop",
                "JjJsuueoOOSeododiIIE",
                "JJuUUuUIOiIKik",
                "leEeetcode",
                "abBAcC",
                "",
                "LoKijOPOs",
                "LlokKijOPOsS",
                "gigGnhgaAI",
                "s",
                "qFxXfQo"
        };
        Instant instant1 = Instant.now();
        for (String s : strings) {
            String result = solution.makeGood(s);
            System.out.printf("s=%s result=%s\n", s, result);
        }
        Instant instant2 = Instant.now();
        printTime(instant1, instant2, strings.length);
    }
}