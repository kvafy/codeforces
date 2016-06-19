import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class Round358_div2_D_v2Test {

    @org.junit.Test
    public void solve() throws Exception {

        String inputs[] = {
                "15 9 4\nababaaabbaaaabb\nbbaababbb",
                "2 7 1\nbb\nbbaabaa",
                "13 4 3\nabbaababaaaab\naaab",
                "2 3 2\nab\naab",
                "13 9 1\noaflomxegekyv\nbgwwqizfo",
                "5 9 1\nbabcb\nabbcbaacb",
                "15 10 1\nabbccbaaaabaabb\nbbaabaacca\n"
        };
        String expectedOuts[] = {
                "8",
                "2",
                "4",
                "2",
                "1",
                "3",
                "5"
        };


        for (int i = 0 ; i < inputs.length ; i++) {
            StringWriter actualOut = new StringWriter();
            Round358_div2_D_v2.FastReader in = new Round358_div2_D_v2.FastReader(new ByteArrayInputStream(inputs[i].getBytes()));
            PrintWriter out = new PrintWriter(actualOut);

            Round358_div2_D_v2.solve(in, out); // kept static to avoid "push this" overhead on method calls

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