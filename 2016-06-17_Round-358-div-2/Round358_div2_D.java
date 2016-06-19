import java.io.*;
import java.util.stream.IntStream;

public class Round358_div2_D {

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        solve(in, out);
    }

    static int n, m, k;
    static String s, t;
    static Integer[][][][] dyn;

    public static void solve(FastReader in, PrintWriter out) {
        n = in.readInt();
        m = in.readInt();
        k = in.readInt();
        s = in.readString();
        t = in.readString();

        dyn = new Integer[n + 1][m + 1][k + 1][2];
        int solution = dfs(n, m, k, false);

        out.println(solution);
        out.flush();
    }

    static int dfs(int i, int j, int c, boolean wasMatch) {
        int m = wasMatch ? 1 : 0;
        if (i == 0 || j == 0) {
            return (c == 0) ? 0 : Integer.MIN_VALUE;
        }
        if (c < 0) {
            return Integer.MIN_VALUE;
        }

        if (dyn[i][j][c][m] == null) {
            int movest = -1;
            if (s.charAt(i - 1) == t.charAt(j - 1)) {
                if (wasMatch) {
                    movest = 1 + dfs(i - 1, j - 1, c, true);
                }
                movest = Math.max(movest, 1 + dfs(i - 1, j - 1, c - 1, true));
            } else {
                movest = dfs(i - 1, j - 1, c, false);
            }
            int moves = dfs(i - 1, j, c, false);
            int movet = dfs(i, j - 1, c, false);

            dyn[i][j][c][m] = IntStream.of(movest, moves, movet).max().getAsInt();
        }
        return dyn[i][j][c][m];
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
