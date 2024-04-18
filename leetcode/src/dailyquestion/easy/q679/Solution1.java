package dailyquestion.easy.q679;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// (a ? b) ? c ? d
// (a ? b ? c) ? d
// (a ? b ? c ? d)
// a ? (b ? c ? d)
// a ? b ? (c ? d)
// (a ? b) ? (c ? d)
// a ? (b ? c) ? d
public class Solution1 implements Solution {
    @Override
    public boolean judgePoint24(int[] cards) {

        java.math.BigDecimal x = java.math.BigDecimal.valueOf(24).setScale(14, java.math.RoundingMode.HALF_UP);
        List<int[]> cardsPerm = new ArrayList<>();
        cardsPerm.add(cards); // abcd
        int[] cards2 = Arrays.copyOf(cards, cards.length);
        swap(cards2, 1, 2); // acbd
        cardsPerm.add(cards2);
        int[] cards3 = Arrays.copyOf(cards, cards.length);
        swap(cards3, 1, 3); // adcb
        cardsPerm.add(cards3);

        for (int[] curCards : cardsPerm) {
            System.out.printf("judgePoint24 given=%s\n", Arrays.toString(curCards), 24);
            for (int i = 0; i < curCards.length; i++) {
                int[] c = new int[3];
                for (int j = 0, k = 0; j < curCards.length; j++) {
                    if (j == i) continue;
                    c[k++] = curCards[j];
                }
                java.math.BigDecimal bdi = java.math.BigDecimal.valueOf(curCards[i]);
                if (judge3PointX(c, x.subtract(bdi))
                        || judge3PointX(c, bdi.subtract(x).setScale(14, java.math.RoundingMode.HALF_UP))
                        || judge3PointX(c, x.add(bdi).setScale(14, java.math.RoundingMode.HALF_UP))
                        || judge3PointX(c, x.multiply(bdi).setScale(14, java.math.RoundingMode.HALF_UP))
                        || judge3PointX(c, x.divide(bdi, java.math.RoundingMode.HALF_UP).setScale(14, java.math.RoundingMode.HALF_UP))
                        || judge3PointX(c, bdi.divide(x, java.math.RoundingMode.HALF_UP).setScale(14, java.math.RoundingMode.HALF_UP))
                ) {
                    System.out.printf("judgePoint24 -> 3 true: given=%s x=%s expect=%s\n", Arrays.toString(curCards), x, 24);
                    return true;
                }
            }
            {
                int[] c = new int[]{curCards[2], curCards[3]};
                BigDecimal bd0 = BigDecimal.valueOf(curCards[0]).setScale(14, java.math.RoundingMode.HALF_UP);
                BigDecimal bd1 = BigDecimal.valueOf(curCards[1]).setScale(14, java.math.RoundingMode.HALF_UP);
                BigDecimal[] xs = new BigDecimal[]{
                        bd0.add(bd1),
                        bd0.subtract(bd1),
                        bd1.subtract(bd0),
                        bd0.multiply(bd0),
                        bd0.divide(bd1, java.math.RoundingMode.HALF_UP),
                        bd1.divide(bd0, java.math.RoundingMode.HALF_UP),
                };
                for (int i = 0; i < xs.length; i++) {
                    java.math.BigDecimal y = xs[i];
                    if (y.compareTo(java.math.BigDecimal.ZERO) == 0) {
                        continue;
                    }
                    if (judge2PointX(c, y.subtract(x).setScale(14, java.math.RoundingMode.HALF_UP))
                            || judge2PointX(c, x.add(y).setScale(14, java.math.RoundingMode.HALF_UP))
                            || judge2PointX(c, x.subtract(y).setScale(14, java.math.RoundingMode.HALF_UP))
                            || judge2PointX(c, x.divide(y, java.math.RoundingMode.HALF_UP).setScale(14, java.math.RoundingMode.HALF_UP))
                            || judge2PointX(c, y.divide(x, java.math.RoundingMode.HALF_UP).setScale(14, java.math.RoundingMode.HALF_UP))
                            || judge2PointX(c, x.multiply(y))
                    ) {
                        System.out.printf("judge2PointX -> 2 true: given=%s y=%s expect=%s\n", Arrays.toString(curCards), "" + y, "" + 24);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void swap(int[] arr, int idx1, int idx2) {
        int tmp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = tmp;
    }

    private boolean judge3PointX(int[] cards, java.math.BigDecimal x) {
        if (x.compareTo(java.math.BigDecimal.ZERO) == 0) {
            return false;
        }
        System.out.printf("\tjudge3PointX given=%s expect=%s\n", Arrays.toString(cards), x);
        for (int i = 0; i < cards.length; i++) {
            int[] c = new int[2];
            for (int j = 0, k = 0; j < cards.length; j++) {
                if (j == i) continue;
                c[k++] = cards[j];
            }
            java.math.BigDecimal bdi = java.math.BigDecimal.valueOf(cards[i]).setScale(14, java.math.RoundingMode.HALF_UP);
            if (
                    judge2PointX(c, x.subtract(bdi).setScale(14, java.math.RoundingMode.HALF_UP))
                            || judge2PointX(c, bdi.subtract(x).setScale(14, java.math.RoundingMode.HALF_UP))
                            || judge2PointX(c, x.add(bdi).setScale(14, java.math.RoundingMode.HALF_UP))
                            || judge2PointX(c, x.multiply(bdi).setScale(14, java.math.RoundingMode.HALF_UP))
                            || judge2PointX(c, x.divide(bdi, java.math.RoundingMode.HALF_UP).setScale(14, java.math.RoundingMode.HALF_UP))
                            || judge2PointX(c, bdi.divide(x, java.math.RoundingMode.HALF_UP).setScale(14, java.math.RoundingMode.HALF_UP))
            ) {
                System.out.printf("\tjudge3PointX true: given=%s expect=%s bdi=%s\n", Arrays.toString(cards), x, bdi);
                return true;
            }
        }
        return false;
    }

    private boolean judge2PointX(int[] c, java.math.BigDecimal x) {
        if (x.compareTo(java.math.BigDecimal.ZERO) == 0) {
            return false;
        }
        System.out.printf("\t\tjudge2PointX given=%s expect=%s\n", Arrays.toString(c), x);
        if (java.math.BigDecimal.valueOf(c[0] + c[1]).setScale(14, java.math.RoundingMode.HALF_UP).compareTo(x) == 0
                || java.math.BigDecimal.valueOf(c[0] - c[1]).setScale(14, java.math.RoundingMode.HALF_UP).compareTo(x) == 0
                || java.math.BigDecimal.valueOf(c[1] - c[0]).setScale(14, java.math.RoundingMode.HALF_UP).compareTo(x) == 0
                || java.math.BigDecimal.valueOf(c[0]).setScale(14, java.math.RoundingMode.HALF_UP).multiply(java.math.BigDecimal.valueOf(c[1])).compareTo(x) == 0
                || java.math.BigDecimal.valueOf(c[0]).setScale(14, java.math.RoundingMode.HALF_UP).divide(java.math.BigDecimal.valueOf(c[1]), java.math.RoundingMode.HALF_UP).compareTo(x) == 0
                || java.math.BigDecimal.valueOf(c[1]).setScale(14, java.math.RoundingMode.HALF_UP).divide(java.math.BigDecimal.valueOf(c[0]), java.math.RoundingMode.HALF_UP).compareTo(x) == 0) {
            System.out.printf("\t\tjudge2PointX true: given=%s expect=%s\n", Arrays.toString(c), x);
            return true;
        }
        return false;
    }
}