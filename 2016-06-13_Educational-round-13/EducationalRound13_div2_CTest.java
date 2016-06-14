import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;

public class EducationalRound13_div2_CTest {

    @org.junit.Test
    public void solve() throws Exception {

        String inputs[] = {
                "5 2 3 12 15",
                "20 2 3 3 5",
                "1 2 3 4 5",
                "1 1 1 5 10"
        };
        String expectedOuts[] = {
                "39",
                "51",
                "0",
                "10"
        };


        for (int i = 0 ; i < inputs.length ; i++) {
            StringWriter actualOut = new StringWriter();
            EducationalRound13_div2_C.FastReader in = new EducationalRound13_div2_C.FastReader(new ByteArrayInputStream(inputs[i].getBytes()));
            PrintWriter out = new PrintWriter(actualOut);

            EducationalRound13_div2_C.solve(in, out); // kept static to avoid "push this" overhead on method calls

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