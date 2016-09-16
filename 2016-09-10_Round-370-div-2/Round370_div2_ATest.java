import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;

import static org.junit.Assert.*;

public class Round370_div2_ATest {

    private static final double EPS = 1e-6;
    private static final BiFunction<String, String, Boolean> STR_COMPARATOR =
            (x, y) -> x.equals(y);
    private static final BiFunction<String, String, Boolean> FLOAT_COMPARATOR =
            (xStr, yStr) -> Math.abs(Double.parseDouble(xStr) - Double.parseDouble(yStr)) < EPS;

    @org.junit.Test
    public void solve() throws Exception {

        // test specification {input, expected output}
        String[][] tests = {
                {"3", "0"},
        };


        int fails = 0;
        for (int i = 0 ; i < tests.length ; i++) {
            StringWriter actualOut = new StringWriter();
            Round370_div2_A.FastReader in = new Round370_div2_A.FastReader(new ByteArrayInputStream(tests[i][0].getBytes()));
            PrintWriter out = new PrintWriter(actualOut);

            Round370_div2_A.solve(in, out); // kept static to avoid "push this" overhead on method calls

            String result = evaluateAssertion(i + 1, tests[i][1].trim(), actualOut.toString().trim(), STR_COMPARATOR);
            fails += result.startsWith("[ ]") ? 1 : 0;
            System.out.println(result);
        }

        if (fails > 0) {
            fail(String.format("%d out of %d tests failed", fails, tests.length));
        }
    }

    private static String evaluateAssertion(int testNo,
                                            String expected, String actual,
                                            BiFunction<String,String,Boolean> isOk) {
        String tick = "x";
        String extra = "";
        if (!isOk.apply(expected, actual)) {
            tick = " ";
            extra = String.format("[%s] != [%s] (expected != actual)", expected, actual);
        }
        return String.format("[%s] Test %2d: %s", tick, testNo, extra);
    }
}