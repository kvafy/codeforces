import java.io.*;

public class Round364_div2_D {

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        solve(in, out);
        out.flush();
    }

    public static void solve(FastReader in, PrintWriter out) {
        int n     = in.readInt(); // number of kids
        double l  = in.readInt(); // distance to travel
        double v1 = in.readInt(); // on foot velocity
        double v2 = in.readInt(); // bus velocity
        int k     = in.readInt(); // capacity of bus

        double solution;
        if (v1 >= v2) {
            // bus is useless, everyone goes on foot
            solution = l / v1;
        } else {
            // Idea is to ultimately advance all kids by the same amount using bus,
            // so that the they all arrive at the destination at exactly the same time.
            //
            // Kids have to be divided into batches of ceil(n/k). There will be total of T
            // batches.
            // The bus takes kids at the start and drives them for "tf" time. Then drops
            // them off and they will walk to destination. Now bus goes back
            // to the rest of the walkers, which takes "tb" time, equal to
            // (v2 - v1) / (v2 + v1) * tf.
            // Now the bus again takes kids and drives them forward for "tf" time, returns
            // in "tb" time and so on.

            int T = (n + (k - 1)) / k; // how many times bus brings kids forward
            double tf = (l / v2) / (T - (T - 1) * (v2 - v1) / (v2 + v1));
            double tb = (v2 - v1) / (v2 + v1) * tf;
            solution = T * tf + (T - 1) * tb;
        }

        out.println(solution);
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
