package dailyquestion.easy.q678;

import java.util.ArrayList;
import java.util.List;

class Solution2 {
    public boolean checkValidString(String s) {
        if (s.startsWith(")") || s.endsWith("(")) {
            return false;
        }
        char[] chars = s.toCharArray();
        List<Integer> starIdx = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '*') {
                starIdx.add(i);
            }
        }
        boolean isValid;
        long maxCombination = (long) Math.pow(3, starIdx.size());
        long comb = 0;
        int balance;
        int minBalance = Integer.MAX_VALUE;
        int maxBalance = Integer.MIN_VALUE;
        do {
            StringBuilder tmpComb = new StringBuilder(Long.toString(comb, 3));
            while (tmpComb.length() < starIdx.size()) {
                tmpComb.insert(0, '0');
            }
            for (int j = 0; j < tmpComb.length(); j++) {
                char tmp = tmpComb.charAt(j);
                char c = tmp == '0' ? '(' : tmp == '1' ? ' ' : ')';
                chars[starIdx.get(j)] = c;
            }
            balance = 0;
            for (char c : chars) {
                if (c == '(') {
                    balance++;
                } else if (c == ')') {
                    balance--;
                    if (balance < 0) {
                        break;
                    }
                }
            }
            System.out.printf("maxComb=%s tmpStr=%s balance=%d comb=%d\n", maxCombination, new String(chars), balance, comb);
            minBalance = Math.min(minBalance, balance);
            maxBalance = Math.max(maxBalance, balance);
            isValid = balance == 0;
        } while (!isValid && ++comb < maxCombination);
        System.out.printf("maxComb=%s tmpStr=%s balance=%d comb=%d minBalance=%d maxBalance=%d\n", maxCombination, new String(chars), balance, comb,
                minBalance, maxBalance);
        return isValid;
    }
}
