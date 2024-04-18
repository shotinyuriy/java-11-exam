package dailyquestion.easy.q1759;

class Solution {
    public int countHomogenous(String s) {
        if (s.length() <= 1) return s.length();
        long homoCount = 0;
        long sameCount = 1;
        char[] chars = s.toCharArray();
        for (int i = 1; i < chars.length; i++) {
            if (chars[i - 1] == chars[i]) {
                sameCount++;
            } else {
                homoCount += (1 + sameCount) * sameCount / 2;
                sameCount = 1;
            }
        }
        homoCount += (1 + sameCount) * sameCount / 2;
        return (int) (homoCount % (1000000000 + 7));
    }
}