package dailyquestion.easy.q1759;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution1 extends Solution {
    public int countHomogenous(String s) {
        Map<Character, List<Integer>> allChars = new HashMap<Character, List<Integer>>();
        Map<Character, Integer> maxChars = new HashMap<Character, Integer>();
        int sameCount = 1;
        if (s.length() <= 1) return s.length();

        int i = 0;
        char[] chars = s.toCharArray();
        char prevCh = chars[i++];
        for (; i < chars.length; i++) {
            if (chars[i-1] == chars[i]) {
                sameCount++;
            } else {
                allChars.putIfAbsent(prevCh, new ArrayList<>());
                allChars.get(prevCh).add(sameCount);
                maxChars.putIfAbsent(prevCh, sameCount);
                final int currentCount = sameCount;
                maxChars.compute(prevCh, (existC, count) -> Math.max(count, currentCount));
                sameCount = 1;
            }
        }
        {
            allChars.putIfAbsent(prevCh, new ArrayList<>());
            allChars.get(prevCh).add(sameCount);
            maxChars.putIfAbsent(prevCh, sameCount);
            final int currentCount = sameCount;
            maxChars.compute(prevCh, (existC, count) -> Math.max(count, currentCount));
        }
        // System.out.printf("-----\n allChars=%s\n", allChars);
        // System.out.printf(" maxChars=%s\n", maxChars);

        long homoCount = 0;
        for (var chAndMax : maxChars.entrySet()) {
            for (i = 0; i < chAndMax.getValue(); i++) {
                int appear = 0;
                for (int count : allChars.get(chAndMax.getKey())) {
                    if (count >= i) {
                        appear += (count - i);
                    }
                }
                // System.out.printf("%s appears %d times\n", String.valueOf(chAndMax.getKey()).repeat(i+1), appear);
                homoCount += appear;
            }
        }
//        System.out.printf(" homoCount=%d\n", homoCount);
        return (int) (homoCount % (1000000000 + 7));
    }
}
