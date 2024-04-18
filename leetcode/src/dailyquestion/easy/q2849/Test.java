package dailyquestion.easy.q2849;

import dailyquestion.easy.AbstractTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class Test extends AbstractTest {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        Solution1 solution = new Solution1();
        // 3 F
        // 2
        // 1 S
        // 0 1 2 3
        Instant instant1 = Instant.now();
        List<int[]> arguments = new ArrayList<>();
        arguments.add(new int[]{5, 1, 1, 4, 4});
        arguments.add(new int[]{1, 1, 1, 3, 2});
        arguments.add(new int[]{1, 2, 3, 1, 2});
        arguments.add(new int[]{729021164, 202572150, 474133610, 4551105, 208849287});
        arguments.add(new int[]{1, 1, 1, 1, 3});
        arguments.add(new int[]{1, 1, 2, 2, 1});
        arguments.add(new int[]{1, 2, 1, 2, 1});
        arguments.add(new int[]{1, 1, 1, 2, 0});
        arguments.add(new int[]{2, 4, 7, 7, 6});
        arguments.add(new int[]{3, 1, 7, 3, 3});
        arguments.add(new int[]{0, 0, 1, 1, 1});
        arguments.add(new int[]{0, 0, 1, 1, 2});
        arguments.add(new int[]{0, 0, -1, -1, 1});
        arguments.add(new int[]{0, 0, -1, -1, -1});
        arguments.add(new int[]{0, 0, 0, 0, 0});
        arguments.add(new int[]{1, -1, -1, 1, 2});
        for (int i = 0; i < 1000; i += 9) {
            for (int j = 0; j < 1000; j += 9) {
                arguments.add(new int[]{i, i, j, j, i + j});
            }
        }
        for (int[] a : arguments) {
            int sx = a[0];
            int sy = a[1];
            int fx = a[2];
            int fy = a[3];
            int t = a[4];
            boolean isReachable = solution.isReachableAtTime(sx, sy, fx, fy, t);
//            System.out.printf("s=(%d,%d) f=(%d,%d) t=%d isReachable=%s\n", sx, sy, fx, fy, t, isReachable);
        }
        Instant instant2 = Instant.now();
        printTime(instant1, instant2, arguments.size());
    }
}
