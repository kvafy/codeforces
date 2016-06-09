import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;

public class Round356_D_postContest_v2 {

    private static HashMap<Long, Pair> cache = new HashMap<>();

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long m = in.readLong();

        recurse(m);

        Pair solution = cache.get(m);

        out.println(solution.blocks);
        out.println(solution.volume);
        out.flush();
    }

    private static Pair recurse(long remaining) {
        Pair result;

        if (remaining <= 0) {
            result = new Pair(0, 0);
            cache.put(remaining, result);
        } else {
            result = cache.get(remaining);
            if (result == null) {
                long cbrt = (long) Math.cbrt(remaining);
                long block = cbrt * cbrt * cbrt;
                // either use this block
                Pair xp = recurse(remaining - block);
                xp = new Pair(xp.blocks + 1, xp.volume + block);
                // ... or stop using this block and try smaller blocks
                Pair yp = recurse(block - 1);

                if (xp.blocks > yp.blocks || xp.blocks == yp.blocks && xp.volume > yp.volume) {
                    result = xp;
                } else {
                    result = yp;
                }
                cache.put(remaining, result);
            }
        }
        return result;
    }

    static class Pair {
        final long blocks;
        final long volume;
        public Pair(long blocks, long volume) {
            this.blocks = blocks;
            this.volume = volume;
        }
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

        public long readLong() {
            long sign = 1;
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
