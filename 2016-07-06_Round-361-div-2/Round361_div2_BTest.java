import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;

import static org.junit.Assert.*;

public class Round361_div2_BTest {

    private static final double EPS = 1e-6;
    private static final BiFunction<String, String, Boolean> STR_COMPARATOR =
            (x, y) -> x.equals(y);
    private static final BiFunction<String, String, Boolean> FLOAT_COMPARATOR =
            (xStr, yStr) -> Math.abs(Double.parseDouble(xStr) - Double.parseDouble(yStr)) < EPS;

    @org.junit.Test
    public void solve() throws Exception {

        // test specification {input, expected output}
        String[][] tests = {
                {"3\n2 2 3",
                 "0 1 2"},
                {"5\n1 2 3 4 5",
                 "0 1 2 3 4"},
                {"7\n4 4 4 4 7 7 7",
                 "0 1 2 1 2 3 3"},
                {"1\n1",
                 "0"},
                {"98\n17 17 57 57 57 57 57 57 57 57 57 57 57 57 57 57 57 57 57 57 57 57 57 57 57 57 57 57 57 57 57 57 87 87 87 87 87 87 87 87 87 87 87 87 87 87 87 87 87 87 87 87 87 90 90 90 90 90 90 90 90 90 90 90 92 92 92 92 92 92 92 92 92 92 92 92 92 92 92 92 92 92 92 92 92 92 92 92 92 92 95 95 95 95 95 97 98 98",
                 "0 1 2 3 4 5 6 7 8 8 7 6 5 4 3 2 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 4 4 5 6 5 6 7 8"}
        };


        int fails = 0;
        for (int i = 0 ; i < tests.length ; i++) {
            StringWriter actualOut = new StringWriter();
            Round361_div2_B.FastReader in = new Round361_div2_B.FastReader(new ByteArrayInputStream(tests[i][0].getBytes()));
            PrintWriter out = new PrintWriter(actualOut);

            Round361_div2_B.solve(in, out); // kept static to avoid "push this" overhead on method calls

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