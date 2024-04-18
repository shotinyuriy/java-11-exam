package dailyquestion.easy.q2849;

class Solution1 {
    public boolean isReachableAtTime(int sx, int sy, int fx, int fy, int t) {
        int xstep = Integer.compare(fx, sx);
        int ystep = Integer.compare(fy, sy);
        int count = 0;
        while (sx != fx || sy != fy) {
            if (sx != fx) sx += xstep;
            if (sy != fy) sy += ystep;
            count++;
        }
        return (count == 0 && (t == 0 || t > 1))
                || (count > 0 && count <= t);
    }
}
