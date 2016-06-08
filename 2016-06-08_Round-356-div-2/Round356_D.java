import java.io.*;

public class Round356_D {

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long m = in.readLong();

        long blocks = 0;
        long volume = 0;
        long available = m;

        for (long blockSide = 1 ; blockSide * blockSide * blockSide <= available ; blockSide++) {
            long blockSize = blockSide * blockSide * blockSide;
            long availableForBlock = Math.min(available, blockSize * blockSize * blockSize - 1 - volume);
            long cnt = availableForBlock / blockSize;

            blocks += cnt;
            volume += cnt * blockSize;
            available -= cnt * blockSize;
        }

        out.println(blocks);
        out.println(volume);
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
