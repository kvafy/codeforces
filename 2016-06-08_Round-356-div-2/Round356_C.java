import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Round356_C {

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        List<Integer> potentialDivisors = IntStream.range(2, 101)
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toList());

        int foundDivisorsCnt = 0;
        for (int i = 0 ; i < 20 && foundDivisorsCnt < 2; i++) {
            if (potentialDivisors.isEmpty()) {
                break;
            }
            int divisor = potentialDivisors.remove(0);

            if (divisor > 50 && isPrime(divisor)) {
                continue;
            }

            out.println(divisor);
            out.flush();
            String answer = in.readString();
            if ("yes".equals(answer)) {
                foundDivisorsCnt++;
                potentialDivisors = potentialDivisors.stream()
                        .filter(x -> x % divisor == 0)
                        .collect(Collectors.toList());
            } else {
                potentialDivisors = potentialDivisors.stream()
                        .filter(x -> x % divisor != 0)
                        .collect(Collectors.toList());
            }
        }

        out.println(foundDivisorsCnt > 1 ? "composite" : "prime");
        out.flush();
    }

    private static boolean isPrime(int n) {
        for (int div = 2 ; div < n && div * div <= n ; div++) {
            if (n % div == 0) {
                return false;
            }
        }
        return true;
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
