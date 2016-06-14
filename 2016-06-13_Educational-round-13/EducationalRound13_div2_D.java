import java.io.*;

public class EducationalRound13_div2_D {

    static final long MOD = 1_000_000_007L; // distributive over + - * (NOT /)

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        solve(in, out);
    }


    public static void solve(FastReader in, PrintWriter out) {
        long a = in.readLong();
        long b = in.readLong();
        long n = in.readLong();
        long x = in.readLong();

        long solution;
        if (n == 0) {
            solution = x % MOD;
        } else {
            // a^n * x  +  b * (a^(n-1) + ... + a + 1)
            long s1 = pow(a, n) * (x % MOD);
            long s2 = (b % MOD) * powsum(a, n - 1);
            solution = (s1 % MOD + s2 % MOD) % MOD;
        }

        out.println(solution);
        out.flush();
    }

    private static long powsum(long a, long p) {
        if (p == 0) {
            return 1;
        } else if (p == 1) {
            return (1 + a) % MOD;
        } else {
            long halfSum = powsum(a, p / 2);
            long aPowPHalf = pow(a, p / 2);

            long result = (halfSum + ((aPowPHalf * halfSum) % MOD) - aPowPHalf) % MOD;
            result = (result + MOD) % MOD;
            if (p % 2 != 0) {
                long extra = (((aPowPHalf * aPowPHalf) % MOD) * a) % MOD;
                result = (result + extra) % MOD;
            }
            return result % MOD;
        }
    }

    private static long pow(long x, long n) {
        x = x % MOD;
        if (n == 0) {
            return 1;
        } else if (n == 1) {
            return x;
        } else {
            long mult = (n % 2 != 0) ? (x % MOD) : 1;
            return (mult * pow((x * x) % MOD, n / 2)) % MOD;
        }
    }
/*
    private static long[] gcdExtended(long a, long b) {
        if (a == 0) {
            return new long[] {b, 0, 1};
        }

        long[] result = gcdExtended(b%a, a);
        long gcd = result[0];
        long x1 = result[1];
        long y1 = result[2];

        return new long[] {gcd, y1 - (b/a) * x1, x1};
    }

    private static long modInverse(long a, long m) {
        long[] gcdExt = gcdExtended(a, m);
        long g = gcdExt[0];
        long x = gcdExt[1];
        long y = gcdExt[2];
        if (g != 1) {
            throw new IllegalArgumentException("Inverse does not exist");
        } else {
            return (x % m + m) % m;
        }
    }
*/

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

        public long readLong() {
            int sign = 1;
            long abs = 0;
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
