import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;

public class Round358_div2_ATest {

    @org.junit.Test
    public void solve() throws Exception {

        String inputs[] = {
                "6 12",
                "11 14",
                "1 5",
                "3 8",
                "5 7",
                "21 21"
        };
        String expectedOuts[] = {
                "14",
                "31",
                "1",
                "5",
                "7",
                "88"
        };


        for (int i = 0 ; i < inputs.length ; i++) {
            StringWriter actualOut = new StringWriter();
            Round358_div2_A.FastReader in = new Round358_div2_A.FastReader(new ByteArrayInputStream(inputs[i].getBytes()));
            PrintWriter out = new PrintWriter(actualOut);

            Round358_div2_A.solve(in, out); // kept static to avoid "push this" overhead on method calls

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