package dailyquestion.easy.q679;

import java.util.*;

// (a ? b) ? c ? d
// (a ? b ? c) ? d
// (a ? b ? c ? d)
// a ? (b ? c ? d)
// a ? b ? (c ? d)
// (a ? b) ? (c ? d)
// a ? (b ? c) ? d
public class Solution2 implements Solution {
    private static final int scale = 20;
    private static final java.math.RoundingMode rMode = java.math.RoundingMode.HALF_UP;

    private static class BinOper {
        protected int value = 0;
        protected BinOper left = null;
        protected BinOper right = null;
        protected char action = '_';

        public BinOper(int value) {
            this.value = value;
        }

        public BinOper(BinOper left, char action, BinOper right) {
            this.left = left;
            this.action = action;
            this.right = right;
        }

        public int priority() {
            switch (action) {
                case '+':
                case '-':
                case '_':
                    return 0;
                case '/':
                    return 1;
                default:
                    return 2;
            }
        }

        public java.math.BigDecimal result() {
            try {
                switch (action) {
                    case '+':
                        return left.result().add(right.result());
                    case '-':
                        return left.result().subtract(right.result());
                    case '*':
                        return left.result().multiply(right.result());
                    case '/':
                        return left.result().divide(right.result(), rMode);
                }
                return java.math.BigDecimal.valueOf(value).setScale(scale, rMode);
            } catch (ArithmeticException e) {
                return java.math.BigDecimal.ZERO;
            }
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BinOper binOper = (BinOper) o;
            return value == binOper.value && action == binOper.action && Objects.equals(left, binOper.left) && Objects.equals(right, binOper.right);
        }

        public int hashCode() {
            return Objects.hash(value, left, right, action);
        }

        public String toString() {
            return toString(false);
        }

        public String toString(boolean needP) {
            if (action != '_') {
                boolean lNeedP = left.priority() < Math.max(right.priority(), this.priority()) || this.action == '-' && left.action == '-' || this.action == '/' && left.action == '/';
                boolean rNeedP = Math.max(left.priority(), this.priority()) > right.priority() || this.action == '-' && right.action == '-' || this.action == '/' && right.action == '/';
                if (needP) {
                    return '(' + left.toString(lNeedP) + action + right.toString(rNeedP) + ')';
                } else {
                    return left.toString(lNeedP) + action + right.toString(rNeedP);
                }
            }
            return String.valueOf(value);
        }
    }

    public boolean judgePoint24(int[] cards) {
        double x = 24.0;
        // System.out.printf("x=%s\n", x.doubleValue());
        List<int[]> cardsPerm = new ArrayList<>();
        cardsPerm.add(cards); // 0123
        copySwapAndAdd(cardsPerm, cards, 0, 1); // 1023
        copySwapAndAdd(cardsPerm, cards, 0, 2); // 2103
        copySwapAndAdd(cardsPerm, cards, 0, 3); // 3120
        copySwapAndAdd(cardsPerm, cards, 1, 2); // 0213
        copySwapAndAdd(cardsPerm, cards, 1, 3); // 0321
        copySwapAndAdd(cardsPerm, cards, 2, 3); // 0132

        Set<BinOper> opers = new HashSet<>();
        for (int[] curCards : cardsPerm) {
            opers.addAll(allOperations(curCards, 1));
            opers.addAll(allOperations(curCards, 2));
            opers.addAll(allOperations(curCards, 3));
        }
        for (BinOper oper : opers) {
            java.math.BigDecimal result = oper.result();

            if (Double.compare(oper.result().doubleValue(), x) == 0) {
                System.out.printf("%s=%s\n", oper, result.doubleValue());
                return true;
            }
            if (Double.compare(result.doubleValue(), 23.0) > 0 && Double.compare(result.doubleValue(), 25.0) < 0) {
                System.out.printf("%s=%s\n", oper, result.doubleValue());
            }
        }
        return false;
    }

    private void copySwapAndAdd(List<int[]> list, int[] arr, int... idx) {
        int[] cards = Arrays.copyOf(arr, arr.length);
        if (idx.length >= 2) {
            swap(cards, idx[0], idx[1]);
        }
        if (idx.length >= 4) {
            swap(cards, idx[2], idx[3]);
        }
        if (idx.length >= 6) {
            swap(cards, idx[4], idx[5]);
        }
        if (list.stream().noneMatch(old -> Arrays.equals(old, cards))) {
            list.add(cards);
        }
    }

    private void swap(int[] arr, int idx1, int idx2) {
        int tmp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = tmp;
    }

    private Set<BinOper> operation(int[] left, char action, int[] right) {
        Set<BinOper> lOpers = new HashSet<>();
        Set<BinOper> rOpers = new HashSet<>();
        for (int i = 1; i <= left.length; i++) {
            lOpers.addAll(allOperations(left, i));
        }
        for (int i = 1; i <= right.length; i++) {
            rOpers.addAll(allOperations(right, i));
        }
        Set<BinOper> opers = new HashSet<>();
        for (BinOper lOper : lOpers) {
            for (BinOper rOper : rOpers) {
                opers.add(new BinOper(lOper, action, rOper));
            }
        }
        return opers;
    }

    private Set<BinOper> allOperations(int[] arr, int first) {
        Set<BinOper> opers = new HashSet<>();
        if (arr.length == 1) {
            opers.add(new BinOper(arr[0]));
        } else if (arr.length > first) {
            int[] nl = Arrays.copyOfRange(arr, 0, first);
            int[] nr = Arrays.copyOfRange(arr, first, arr.length);
            opers.addAll(operation(nl, '+', nr));
            opers.addAll(operation(nl, '-', nr));
            if (!nl.equals(nr)) {
                opers.addAll(operation(nr, '-', nl));
            }
            opers.addAll(operation(nl, '*', nr));
            opers.addAll(operation(nl, '/', nr));
            if (!nl.equals(nr)) {
                opers.addAll(operation(nr, '/', nl));
            }
        }
        return opers;
    }
}