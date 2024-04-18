package dailyquestion.easy.q678;

import dailyquestion.easy.AbstractTest;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

class Test extends AbstractTest {
    public static void main(String[] args) {
        Solution solution = new Solution4();
        Map<String, Boolean> strings = new LinkedHashMap<>();
        Object[] objects = new Object[]{
                "(((((*(()((((*((**(((()()*)()()()*((((**)())*)*)))))))(())(()))())((*()()(((()((()*(())*(()**)()(())", false,
                "(((((*(((((*((**(((*)*((((**))*)*)))))))))((*(((((**(**)", false,
                "(((()))())))*))())()(**(((())(()(*()((((())))*())(())*(*(()(*)))()*())**((()(()))())(*(*))*))())", true,
                "((((()(()()()*()(((((*)()*(**(())))))(())()())(((())())())))))))(((((())*)))()))(()((*()*(*)))(*)()", true,
                "((*(((((**(**)", false,
                "*(*(*(*)))))((*(((((**(**)", false,
                "*(*(*(*)))((*(((**(**)", false,
                "(((((*(((((*((**(((*)*((((**))*)*)))))))))((*(((((**(**)", false,
                "", true,
                "(*)", true,
                "()", true,
                "(*", true,
                "*)", true,
                "*(*)", true,
                "(*)*", true,
                "*(*)*", true,
                "*()", true,
                "()*", true,
                "())(", false,
                "*))*", false,
                "*((*", false,
                "*))*)", false,
                "*())*", true,
                "(()**", true,
                "()()", true,
                "(())", true,
                "(**(", false,
                ")**)", false,
                "*((*", false,
                "**((*", false,
                "**((**", true,
                "(((*)**(*))", true, //
                "()*)(((((**)**(()*)", true,
                "()*)((((((**)**(()*)", true
        };
        for (int i = 0; i < objects.length - 1; i += 2) {
            if (objects[i] instanceof String && objects[i + 1] instanceof Boolean) {
                strings.put((String) objects[i], (Boolean) objects[i + 1]);
            }
        }
        Instant instant1 = Instant.now();
        for (Map.Entry<String, Boolean> tc : strings.entrySet()) {
            boolean valid = solution.checkValidString(tc.getKey());
            if (valid != tc.getValue()) {
                System.out.printf("s=%s valid=%s expected=%s\n", tc.getKey(), valid, tc.getValue());
            }
        }
        Instant instant2 = Instant.now();

        printTime(instant1, instant2, strings.size());
    }
}
