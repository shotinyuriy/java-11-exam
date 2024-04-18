package dailyquestion.easy.q679;

import java.math.BigDecimal;
import java.util.*;

// (a ? b) ? c ? d
// (a ? b ? c) ? d
// (a ? b ? c ? d)
// a ? (b ? c ? d)
// a ? b ? (c ? d)
// (a ? b) ? (c ? d)
// a ? (b ? c) ? d
public class Solution2 implements Solution {
    private static class BinOper {
        protected int value;
        protected BinOper next = null;
        protected char action = '_';
        public static BinOper valueOf(int value) {
            return new BinOper(value);
        }
        public BinOper(int value) {
            this.value = value;
        }

        public BinOper next(BinOper next, char action) {
            this.next = next;
            this.action = action;
            return this.next;
        }
    }

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

        }
        return false;
    }

    private void swap(int[] arr, int idx1, int idx2) {
        int tmp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = tmp;
    }

    private int swap(int[] arr, int idx1, int idx2) {
        int tmp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = tmp;
    }

    private Set<BinOper> operation(int[] left, char action, int[] right) {
        Set<BinOper> lOpers = allOperations(left);
        Set<BinOper> rOpers = allOperations(right);
        Set<BinOper> opers = new HashSet<>();
        for(BinOper lOper : lOpers) {
            for (BinOper rOper : rOpers) {
                opers.add(lOper.next(rOper, action));
            }
        }
        return opers;
    }

    private Set<BinOper> allOperations(int[] arr) {
        Set<BinOper> opers = new HashSet<>();
        if (arr.length == 1) {
            opers.add(BinOper.valueOf(arr[0]));
        } else if (arr.length > 1) {
            int[] nl = Arrays.copyOfRange(arr, 0, 1);
            int[] nr = Arrays.copyOfRange(arr, 1, arr.length);
            opers.addAll(operation(nl, '+', nr));
            opers.addAll(operation(nl, '-', nr));
            opers.addAll(operation(nl, '*', nr));
            opers.addAll(operation(nl, '/', nr));
        }
        return opers;
    }
}