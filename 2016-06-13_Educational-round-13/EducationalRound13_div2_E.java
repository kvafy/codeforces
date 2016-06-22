
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EducationalRound13_div2_E {

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        solve(in, out);
        out.flush();
    }


    // input
    private static int n;
    private static double[][] pDeffeats;
    // [mask][i] ~ assuming that people in "mask" fought and "i" won the last battle (~ is the overall survivor
    //             so far), what is probability of Jedi being the overall winner if all remaining fighters
    //             are considered in the best way possible
    private static Double[][] pJediWins;

    public static void solve(FastReader in, PrintWriter out) {
        n = in.readInt();
        pDeffeats = new double[n][n];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < n ; j++) {
                pDeffeats[i][j] = Double.parseDouble(in.readString());
            }
        }

        pJediWins = new Double[1 << n][n];

        // We need to choose the first contestant (technically two fighters for the first
        // round, but choosing one works as well).
        // Note that the following code will also work for the case when there is no Sith
        // and only the Jedi, in which case his probability of winning is 1.
        double solution = 0;
        for (int firstContestant = 0 ; firstContestant < n ; firstContestant++) {
            solution = Math.max(solution, pJediWins(1 << firstContestant, firstContestant));
        }

        out.println(solution);
    }

    private static double pJediWins(int mask, int winner) {
        if (pJediWins[mask][winner] == null) {
            double prob = 0;
            if (mask == ((1 << n) - 1)) {
                // everybody already fought, so Jedi is the overall winner iff Jedi is the winner of the last (this) round
                prob = (winner == 0) ? 1.0 : 0.0;
            } else {
                // need to choose the best possible next fighter to maximize Jedi's chance of winning
                for (int next = 0 ; next < n ; next++) {
                    if (next != winner && ((1 << next) & mask) == 0) {
                        prob = Math.max(prob,
                                // either the "winner" beats "next"
                                pJediWins(mask | (1 << next), winner) * pDeffeats[winner][next] +
                                // ... or newcomer "next" beats the until-now "winner"
                                pJediWins(mask | (1 << next), next)   * pDeffeats[next][winner]);
                    }
                }
            }
            pJediWins[mask][winner] = prob;
        }
        return pJediWins[mask][winner];
    }

    /**
     * Custom buffered reader. Faster than Scanner and BufferedReader + StringTokenizer.
     */
    static class FastReader {
        private final InputStream stream;
        private int current;
        private int size;
        private byte[] buffer = new byte[1024 * 8];

        public FastReader(InputStream stream) {
            this.stream = stream;
            current = 0;
            size = 0;
        }

        public int readInt() {
            int sign = 1;
            int abs = 0;
            int c = readNonEmpty();
            if (c == '-') {
                sign = -1;
                c = readAny();
            }
            do {
                if (c < '0' || c > '9') {
                    throw new IllegalStateException();
                }
                abs = 10 * abs + (c - '0');
                c = readAny();
            } while (!isEmpty(c));
            return sign * abs;
        }

        public int[] readIntArray(int n) {
            int[] array = new int[n];
            for (int i = 0 ; i < n ; i++) {
                array[i] = readInt();
            }
            return array;
        }

        public char readChar() {
            return (char) readNonEmpty();
        }

        public String readString() {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = readAny();
            } while (isEmpty(c));
            do {
                sb.append((char) c);
                c = readAny();
            } while (!isEmpty(c));
            return sb.toString();
        }

        private int readAny() {
            try {
                if (current >= size) {
                    current = 0;
                    size = stream.read(buffer);
                    if (size < 0) {
                        return -1;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to readAny next byte", e);
            }
            return buffer[current++];
        }

        private int readNonEmpty() {
            int result;
            do {
                result = readAny();
            } while (isEmpty(result));
            return result;
        }

        private static boolean isEmpty(int c) {
            return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == -1;
        }
    }
}
