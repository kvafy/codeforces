import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Round364_div2_C {

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        solve(in, out);
        out.flush();
    }


    public static void solve(FastReader in, PrintWriter out) {
        int n = in.readInt();
        String s = in.readString();

        int totalCnt = (int) s.chars().distinct().count();

        Map<Character, Integer> curCnts = new HashMap<>();

        int solution = s.length();
        int i = 0;
        for (int j = 0 ; j < s.length() ; j++) {
            // add s_j
            char sj = s.charAt(j);
            curCnts.put(sj, curCnts.getOrDefault(sj, 0) + 1);

            // contract s_i
            while (curCnts.getOrDefault(s.charAt(i), 0) > 1) {
                char si = s.charAt(i);
                int siCnt = curCnts.get(si);
                if (siCnt > 1) {
                    curCnts.put(si, siCnt - 1);
                } else {
                    curCnts.remove(si);
                }
                i++;
            }

            if (curCnts.size() == totalCnt) {
                solution = Math.min(solution, j - i + 1);
            }
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
