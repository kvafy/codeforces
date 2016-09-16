import java.io.*;

public class Round370_div2_B {

    static final long MOD = 1_000_000_007L; // distributive over + - * (NOT /)

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        solve(in, out);
        out.flush();
    }


    public static void solve(FastReader in, PrintWriter out) {
        String s = in.readString();

        int dx = 0;
        int dy = 0;
        for (int i = 0 ; i < s.length() ; i++) {
            switch (s.charAt(i)) {
                case 'L':
                    dx--; break;
                case 'R':
                    dx++; break;
                case 'D':
                    dy--; break;
                case 'U':
                    dy++; break;
            }
        }

        dx = Math.abs(dx);
        dy = Math.abs(dy);

        if (dx % 2 == 0 && dy % 2 == 0) {
            out.println(dx / 2 + dy / 2);
        } else if (dx % 2 == 1 && dy % 2 == 1) {
            out.println(dx / 2 + dy / 2 + 1);
        } else {
            out.println(-1);
        }
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
