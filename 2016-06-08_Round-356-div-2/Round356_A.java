import java.io.*;
import java.util.Arrays;

public class Round356_A {

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int[] xs = new int[] { in.readInt(), in.readInt(), in.readInt(), in.readInt(), in.readInt() };

        int sum = Arrays.stream(xs).sum();

        int maxDecrease = 0;
        for (int val : xs) {
            int cnt = 0;
            for (int val2 : xs) {
                if (val == val2) {
                    cnt++;
                }
            }

            if (cnt > 1) {
                maxDecrease = Math.max(
                        maxDecrease,
                        Math.min(cnt, 3) * val);
            }
        }


        int solution = sum - maxDecrease;


        out.println(solution);

        out.flush();
    }

    /**
     * Custom buffered reader. Faster than Scanner and
     * BufferedReader + StringTokenizer.
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
