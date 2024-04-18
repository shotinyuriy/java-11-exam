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
    private static final java.math.RoundingMode rMode = java.math.RoundingMode.HALF_EVEN;

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

        public java.math.BigDecimal result() {
            java.math.BigDecimal bdl = java.math.BigDecimal.valueOf(value).setScale(14, rMode);
            switch (action) {
                case '+':
                    return bdl.add(this.next.result()).setScale(14, rMode);
                case '-':
                    return bdl.subtract(this.next.result()).setScale(14, rMode);
                case '*':
                    return bdl.multiply(this.next.result()).setScale(14, rMode);
                case '/':
                    return bdl.divide(this.next.result()).setScale(14, rMode);
            }
            return bdl;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BinOper oper = (BinOper) o;
            return value == oper.value && action == oper.action && Objects.equals(next, oper.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, next, action);
        }
    }

    @Override
    public boolean judgePoint24(int[] cards) {
        java.math.BigDecimal x = java.math.BigDecimal.valueOf(24).setScale(14, rMode);
        List<int[]> cardsPerm = new ArrayList<>();
        cardsPerm.add(cards); // abcd
        int[] cards2 = Arrays.copyOf(cards, cards.length);
        swap(cards2, 1, 2); // acbd
        cardsPerm.add(cards2);
        int[] cards3 = Arrays.copyOf(cards, cards.length);
        swap(cards3, 1, 3); // adcb
        cardsPerm.add(cards3);
        Set<BinOper> opers = new HashSet<>();
        for (int[] curCards : cardsPerm) {
            opers.addAll(allOperations(curCards, 1));
            opers.addAll(allOperations(curCards, 2));
            opers.addAll(allOperations(curCards, 3));
        }
        for (BinOper oper : opers) {
            BigDecimal result = oper.result();
            System.out.printf("result=%s\n", result);
            if (oper.result().equals(x)) return true;
        }
        return false;
    }

    private void swap(int[] arr, int idx1, int idx2) {
        int tmp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = tmp;
    }

    private Set<BinOper> operation(int[] left, char action, int[] right) {
        Set<BinOper> lOpers = allOperations(left, 1);
        Set<BinOper> rOpers = allOperations(right, 1);
        Set<BinOper> opers = new HashSet<>();
        for (BinOper lOper : lOpers) {
            for (BinOper rOper : rOpers) {
                opers.add(lOper.next(rOper, action));
            }
        }
        return opers;
    }

    private Set<BinOper> allOperations(int[] arr, int first) {
        Set<BinOper> opers = new HashSet<>();
        if (arr.length == 1) {
            opers.add(BinOper.valueOf(arr[0]));
        } else if (arr.length > first) {
            int[] nl = Arrays.copyOfRange(arr, 0, first);
            int[] nr = Arrays.copyOfRange(arr, first, arr.length);
            opers.addAll(operation(nl, '+', nr));
            opers.addAll(operation(nl, '-', nr));
            opers.addAll(operation(nl, '*', nr));
            opers.addAll(operation(nl, '/', nr));
        }
        return opers;
    }
}