import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;

public class Round357_div2_BTest {

    @org.junit.Test
    public void solve() throws Exception {

        String inputs[] = {
                "1359257",
                "17851817"
        };
        String expectedOuts[] = {
                "YES",
                "NO"
        };


        for (int i = 0 ; i < inputs.length ; i++) {
            StringWriter actualOut = new StringWriter();
            Round357_div2_B.FastReader in = new Round357_div2_B.FastReader(new ByteArrayInputStream(inputs[i].getBytes()));
            PrintWriter out = new PrintWriter(actualOut);

            Round357_div2_B.solve(in, out); // kept static to avoid "push this" overhead on method calls

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