import java.io.*;
import java.util.LinkedList;

public class Round361_div2_B {

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        solve(in, out);
        out.flush();
    }

    public static void solve(FastReader in, PrintWriter out) {
        int n = in.readInt();
        int[] a = new int[n];
        for (int i = 0 ; i < n ; i++) {
            int ai = in.readInt() - 1;
            a[i] = ai;
        }

        int[] cost = new int[n];
        for (int i = 0 ; i < n ; i++) {
            cost[i] = -1;
        }

        LinkedList<Integer> toExpand = new LinkedList<>();

        // start BFS
        cost[0] = 0;
        toExpand.addLast(0);

        while (!toExpand.isEmpty()) {
            int i = toExpand.removeFirst();

            if (i - 1 >= 0 && cost[i - 1] == -1) {
                cost[i - 1] = cost[i] + 1;
                toExpand.add(i - 1);
            }

            if (i + 1 < n && cost[i + 1] == -1) {
                cost[i + 1] = cost[i] + 1;
                toExpand.add(i + 1);
            }

            if (a[i] != i && cost[a[i]] == -1) {
                cost[a[i]] = cost[i] + 1;
                toExpand.add(a[i]);
            }
        }

        for (int i = 0 ; i < n ; i++) {
            out.print(cost[i] + " ");
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
