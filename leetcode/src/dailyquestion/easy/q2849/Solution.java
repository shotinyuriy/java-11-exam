package dailyquestion.easy.q2849;

class Solution {
    public boolean isReachableAtTime(int sx, int sy, int fx, int fy, int t) {
        int xl = Math.abs(fx - sx);
        int yl = Math.abs(fy - sy);
        int count = Math.max(xl, yl);
        return count == 0 ? t != 1 : count <= t;
    }
}
