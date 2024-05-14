package dailyquestion.easy.q680;

public class Solution1 implements Solution {
    @Override
    public boolean validPalindrome(String s) {
        char[] chars = s.toCharArray();
        int deletions = 0;
        boolean mismatch = false;
        for (int l = 0, r = chars.length - 1;
             !mismatch && l < r; l++, r--) {
            if (chars[l] != chars[r]) {
                System.out.printf("1. l=%d r=%d chars[l]=%c chars[r]=%c deletions=%d\n", l, r, chars[l], chars[r], deletions);
                if (chars[l + 1] == chars[r]) {
                    l++;
                    deletions++;
                    System.out.printf("2. l=%d r=%d chars[l]=%c chars[r]=%c deletions=%d\n", l, r, chars[l], chars[r], deletions);
                } else if (chars[l] == chars[r - 1]) {
                    r--;
                    deletions++;
                    System.out.printf("3. l=%d r=%d chars[l]=%c chars[r]=%c deletions=%d\n", l, r, chars[l], chars[r], deletions);
                } else {
                    mismatch = true;
                }
            }
        }
        return !mismatch && (deletions) <= 1;
    }
}
