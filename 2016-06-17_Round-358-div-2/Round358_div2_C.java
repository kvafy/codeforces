import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Round358_div2_C {

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        solve(in, out);
    }

    static int n;
    static int[] vertNum;
    static int[] upEdgeNum;
    static List<Integer>[] children;

    public static void solve(FastReader in, PrintWriter out) {
        n = in.readInt();
        vertNum = in.readIntArray(n);
        upEdgeNum = new int[n];

        children = (List<Integer>[]) new List[n];
        for (int i = 0 ; i < n ; i++) {
            children[i] = new ArrayList<>();
        }

        for (int i = 1 ; i < n ; i++) {
            int p = in.readInt() - 1;
            int c = in.readInt();
            children[p].add(i);
            upEdgeNum[i] = c;
        }

        int solution = dfs(0, 0);

        out.println(solution);
        out.flush();
    }

    static int dfs(int dist, int node) {
        int pruned = 0;
        if (dist > vertNum[node]) {
            pruned = treeSize(node);
        } else {
            for (int child : children[node]) {
                pruned += dfs(Math.max(dist + upEdgeNum[child], 0), child);
            }
        }
        return pruned;
    }

    static int treeSize(int root) {
        int pruned = 0;

        ArrayList<Integer> nodes = new ArrayList<>();
        nodes.add(root);
        while (!nodes.isEmpty()) {
            int node = nodes.remove(nodes.size() - 1);
            nodes.addAll(children[node]);
            pruned++;
        }

        return pruned;
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
