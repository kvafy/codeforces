import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;

public class Round357_div2_ATest {

    @org.junit.Test
    public void solve() throws Exception {

        String inputs[] = {
                "3\n" +
                        "Burunduk1 2526 2537\n" +
                        "BudAlNik 2084 2214\n" +
                        "subscriber 2833 2749",
                "3\n" +
                        "Applejack 2400 2400\n" +
                        "Fluttershy 2390 2431\n" +
                        "Pinkie_Pie -2500 -2450"
        };
        String expectedOuts[] = {
                "YES",
                "NO"
        };


        for (int i = 0 ; i < inputs.length ; i++) {
            StringWriter actualOut = new StringWriter();
            Round357_div2_A.FastReader in = new Round357_div2_A.FastReader(new ByteArrayInputStream(inputs[i].getBytes()));
            PrintWriter out = new PrintWriter(actualOut);

            Round357_div2_A.solve(in, out); // kept static to avoid "push this" overhead on method calls

            assertString(expectedOuts[i], actualOut);
        }
    }

    private void assertString(String expected, StringWriter actual) {
        assertEquals(expected.trim(), actual.toString().trim());
    }

    private void assertDouble(String expected, StringWriter actual, double eps) {
        double exp = Double.parseDouble(expected.trim());
        double act = Double.parseDouble(actual.toString().trim());
        assertEquals(exp, act, eps);
    }
}