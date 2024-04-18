package dailyquestion.easy.q678;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Solution4 implements Solution {
    public boolean checkValidString(String s) {
        if (s.startsWith(")") || s.endsWith("(")) {
            return false;
        }
        Stack<Integer> stackO = new Stack<>();
        Stack<Integer> starStackO = new Stack<>();
        Stack<Integer> starStackC = new Stack<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '(') {
                stackO.push(i);
            } else if (c == ')') {
                int prev = -1;
                if (!stackO.isEmpty()) {
                    prev = stackO.pop();
                } else if (!starStackO.isEmpty()) {
                    prev = starStackO.pop();
                }
                if (prev < 0 || prev > i) {
                    return false;
                }
            } else if (c == '*') {
                starStackO.push(i);
                if (!stackO.isEmpty()) {
                    starStackC.push(i);
                }
            }
        }
        while (!stackO.isEmpty() && !starStackC.isEmpty()) {
            int o = stackO.pop();
            int c = starStackC.pop();
            if (c <= o) {
                return false;
            }
        }
        return stackO.isEmpty();
    }
}
