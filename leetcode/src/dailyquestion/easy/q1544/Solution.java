package dailyquestion.easy.q1544;

class Solution {
    public String makeGood(String s) {
        char[] chars = s.toCharArray();
        int newLen = chars.length;
        boolean hasBad;
        do {
            hasBad = false;
            for (int i = 0; i < newLen - 1; i++) {
                hasBad = (Character.isLowerCase(chars[i])
                        && chars[i] == Character.toLowerCase(chars[i + 1])
                        && Character.isUpperCase(chars[i + 1]))
                        || (Character.isUpperCase(chars[i])
                        && chars[i] == Character.toUpperCase(chars[i + 1])
                        && Character.isLowerCase(chars[i + 1]));
                if (hasBad) {
                    newLen = newLen - 2;
                    for (int j = i; j < newLen; j++) {
                        chars[j] = chars[j + 2];
                    }
                    break;
                }
            }
        } while (hasBad);
        return new String(chars, 0, newLen);
    }
}
