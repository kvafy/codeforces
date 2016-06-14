import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;

public class EducationalRound13_div2_DTest {

    @org.junit.Test
    public void solve() throws Exception {

        String inputs[] = {
                "3 4 1 1",
                "3 4 2 1",
                "3 4 3 1",
                "3 10 723 6"
        };
        String expectedOuts[] = {
                "7",
                "25",
                "79",
                "443623217"
        };


        for (int i = 0 ; i < inputs.length ; i++) {
            StringWriter actualOut = new StringWriter();
            EducationalRound13_div2_D.FastReader in = new EducationalRound13_div2_D.FastReader(new ByteArrayInputStream(inputs[i].getBytes()));
            PrintWriter out = new PrintWriter(actualOut);

            EducationalRound13_div2_D.solve(in, out); // kept static to avoid "push this" overhead on method calls

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