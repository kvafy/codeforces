import java.io.*;
import java.util.*;

public class Round357_div2_C {

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        solve(in, out);
    }


    public static void solve(FastReader in, PrintWriter out) {
        int n = in.readInt();

        List<String> solution = new ArrayList<>(n);

        MultiTreeSet heap = new MultiTreeSet();

        for (int i = 0 ; i < n ; i++) {
            int x;
            switch(in.readString()) {
                case "insert":
                    x = in.readInt();
                    heap.add(x);
                    solution.add(String.format("insert %d", x));
                    break;
                case "getMin":
                    x = in.readInt();
                    while (true) {
                        if (heap.isEmpty()) {
                            heap.add(x);
                            solution.add(String.format("insert %d", x));
                        } else {
                            if (heap.first() < x) {
                                heap.pollFirst();
                                solution.add(String.format("removeMin"));
                            } else if (heap.first() == x) {
                                solution.add(String.format("getMin %d", x));
                                break;
                            } else {
                                heap.add(x);
                                solution.add(String.format("insert %d", x));
                            }
                        }
                    }
                    break;
                case "removeMin":
                    if (heap.isEmpty()) {
                        solution.add(String.format("insert %d", 0));
                    } else {
                        heap.pollFirst();
                    }
                    solution.add(String.format("removeMin"));
                    break;
            }
        }

        out.println(solution.size());
        for (String line : solution) {
            out.println(line);
        }
        out.flush();
    }


    static class MultiTreeSet {
        private TreeMap<Integer, Integer> cntMap = new TreeMap<>();


        public void add(int x) {
            int cnt = cntMap.getOrDefault(x, 0);
            cntMap.put(x, cnt + 1);
        }

        public boolean isEmpty() {
            return cntMap.isEmpty();
        }

        public int first() {
            return cntMap.firstKey();
        }

        public void pollFirst() {
            Map.Entry<Integer, Integer> kv = cntMap.firstEntry();
            if (kv.getValue() > 1) {
                cntMap.put(kv.getKey(), kv.getValue() - 1);
            } else {
                cntMap.pollFirstEntry();
            }
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
