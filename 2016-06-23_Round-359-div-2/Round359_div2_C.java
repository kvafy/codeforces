import java.io.*;

public class Round359_div2_C {

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        solve(in, out);
        out.flush();
    }

    private static int n;
    private static int m;
    private static int hDigits;
    private static int mDigits;
    private static int[] watch;
    private static int solution;

    public static void solve(FastReader in, PrintWriter out) {
        n = in.readInt();
        m = in.readInt();

        hDigits = Math.max(1, (int) Math.ceil(Math.log(n) / Math.log(7.0)));
        mDigits = Math.max(1, (int) Math.ceil(Math.log(m) / Math.log(7.0)));

        solution = 0;
        if (hDigits + mDigits <= 7) {
            watch = new int[hDigits + mDigits];
            permute(0, 0b1111111);
        }
        out.println(solution);
    }

    private static void permute(int idx, int mask) {
        if (idx == watch.length) {
            if (toNumBase7(watch, 0, hDigits - 1) < n && toNumBase7(watch, hDigits, watch.length - 1) < m) {
                solution++;
            }
        } else {
            for (int d = 0 ; d <= 6 ; d++) {
                if (((1 << d) & mask) != 0) {
                    mask = mask ^ (1 << d);
                    watch[idx] = d;
                    permute(idx + 1, mask);
                    mask = mask ^ (1 << d);
                }
            }
        }
    }

    private static int toNumBase7(int[] digits, int begin, int end) {
        int result = 0;
        while (end >= begin) {
            result = result * 7 + digits[end];
            end--;
        }
        return result;
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
