import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.stream.IntStream;

public class Round358_div2_D_v2 {

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        solve(in, out);
        out.flush();
    }

    public static void solve(FastReader in, PrintWriter out) {
        int n, m, k;
        String s, t;
        int[][][][] dyn;

        n = in.readInt();
        m = in.readInt();
        k = in.readInt();
        s = in.readString();
        t = in.readString();

        // dyn[p][c][i][j] ~ what is the best substring overlap I can get with
        //                   strings s(1..i) and t(1..j), where c is the number
        //                   of components (standalone substrings) and
        //                   p = 1 if s(i) == t(j), otherwise p = 0
        dyn = new int[2][k+1][n+1][m+1]; // this order of dimensions is needed to avoid Time Limit Exceeded
        for (int c = 1 ; c <= k ; c++) {
            for (int i = 1 ; i <= n ; i++) {
                for (int j = 1 ; j <= m ; j++) {
                    if (s.charAt(i-1) == t.charAt(j-1)) {
                        dyn[1][c][i][j] = max(dyn[1][c][i][j], 1 + dyn[1][c][i-1][j-1], 1 + dyn[0][c-1][i-1][j-1]);

                    } else {
                        dyn[0][c][i][j] = max(dyn[0][c][i][j], dyn[0][c][i-1][j-1], dyn[1][c][i-1][j-1]);
                    }

                    dyn[0][c][i][j] = max(dyn[0][c][i][j],
                            dyn[0][c][i-1][j], dyn[0][c][i][j-1],
                            dyn[1][c][i-1][j], dyn[1][c][i][j-1]);
                }
            }
        }

        int solution = 0;
        for (int c = 1 ; c <= k ; c++) {
            solution = max(solution, dyn[0][c][n][m], dyn[1][c][n][m]);
        }
        out.println(solution);
    }

    static int max(int a, int b, int c) {
        if (a > b) {
            return a > c ? a : c;
        } else {
            return b > c ? b : c;
        }
    }

    static int max(int a, int b, int c, int d, int e) {
        if (e > a) {
            a = e;
        }
        if (d > b) {
            b = d;
        }

        if (a > b) {
            return a > c ? a : c;
        } else {
            return b > c ? b : c;
        }
    }

    /**
     * Custom buffered reader. Faster than Scanner and BufferedReader + StringTokenizer.
     */
    static class FastReader {
        private final InputStream stream;
        private int current;
        private int size;
        private byte[] buffer = new byte[1024 * 4];

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
