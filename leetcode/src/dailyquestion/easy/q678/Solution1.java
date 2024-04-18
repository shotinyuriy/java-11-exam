package dailyquestion.easy.q678;

class Solution1 {
    public boolean checkValidString(String s) {
        if (s.startsWith(")") || s.endsWith("(")) {
            return false;
        }
        char[] chars = s.toCharArray();
        int balance = 0;
        int firstStar = 0;
        int lastStar = 0;
        int middleStars = 0;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
                if (balance < 0) {
                    System.out.printf("balance=%d first*=%d last*=%d middle*=%d\n",
                            balance, firstStar, lastStar, middleStars);
                    if (firstStar > 0) {
                        firstStar--;
                        balance++;
                    } else if (middleStars > 0) {
                        middleStars--;
                        balance++;
                    } else {
                        return false;
                    }
                }
            } else if (c == '*') {
                if (i == 0) {
                    firstStar = 1;
                } else if (i == chars.length - 1) {
                    lastStar = 1;
                } else {
                    middleStars++;
                }
            }
        }
        System.out.printf("balance=%d first*=%d last*=%d middle*=%d\n",
                balance, firstStar, lastStar, middleStars);
        return balance == 0 ||
                balance + firstStar == 0 ||
                balance - lastStar == 0 ||
                balance + firstStar - lastStar == 0 ||
                Math.abs(balance + firstStar - lastStar) <= middleStars;
    }
}
