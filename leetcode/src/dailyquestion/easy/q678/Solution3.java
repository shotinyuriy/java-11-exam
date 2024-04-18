package dailyquestion.easy.q678;

import java.util.ArrayList;
import java.util.List;

class Solution3 implements Solution {
    public boolean checkValidString(String s) {
        if (s.startsWith(")") || s.endsWith("(")) {
            return false;
        }
        while (s.contains("()")) {
            s = s.replace("()", "");
        }
        char[] originalChars = s.toCharArray();
        int[] chars = new int[s.length()];
        List<Integer> starIdx = new ArrayList<>();
        for (int i = 0; i < originalChars.length; i++) {
            if (originalChars[i] == '*') {
                starIdx.add(i);
                chars[i] = 0;
            } else if (originalChars[i] == '(') {
                chars[i] = 1;
            } else if (originalChars[i] == ')') {
                chars[i] = -1;
            }
        }
        boolean isValid;
        long maxCombination = (long) Math.pow(3, starIdx.size());
        long comb = 0;
        int balance;
        do {
            long tmpComb = comb;
            for (int j = 0; j < starIdx.size(); j++) {
                int tmp = (int) (tmpComb % 3);
                int c = tmp == 0 ? 1 : tmp == 1 ? 0 : -1;
                chars[starIdx.get(starIdx.size() - 1 - j)] = c;
                tmpComb = tmpComb / 3;
            }
            balance = 0;
            for (int i : chars) {
                balance += i;
                if (balance < 0) {
                    break;
                }
            }
            isValid = balance == 0;
        } while (!isValid && ++comb < maxCombination);
        return isValid;
    }
}
