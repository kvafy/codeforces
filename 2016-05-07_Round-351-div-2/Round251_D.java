import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Round251_D {

    public static void main(String[] args) throws IOException {
        int[] nk = IOUtils.readIntArray(2);
        int[] abcd = IOUtils.readIntArray(4);

        int n = nk[0];
        int k = nk[1];

        int a = abcd[0];
        int b = abcd[1];
        int c = abcd[2];
        int d = abcd[3];

        if (n <= 4 || !(k >= n + 1)) {
            System.out.println(-1);
        } else {
            List<Integer> otherCities = IntStream.range(1, n + 1)
                    .filter(x -> x != a && x != b && x != c && x != d)
                    .mapToObj(Integer::valueOf)
                    .collect(Collectors.toList());
            otherCities = new LinkedList<>(otherCities);

            // build up the cities ordering
            // (a) - (c) - (x) - (d) - (rest ... of ... nodes) - (b)
            int[] order = new int[n];
            order[0] = a;
            order[1] = c;
            order[2] = otherCities.remove(0);
            order[3] = d;
            for (int i = 4 ; i < n-1 ; i++) {
                order[i] = otherCities.remove(0);
            }
            order[n-1] = b;

            String sep;

            // a-b
            // (a-upto-b)
            sep = "";
            for (int i = 0 ; i < n ; i++) {
                System.out.printf("%s%d", sep, order[i]);
                sep = " ";
            }
            System.out.println();

            // c-d
            // (c) - (a) - (x) - (b) - (b-downto-d)
            sep = "";
            System.out.printf("%s%d", sep, order[1]);
            sep = " ";
            System.out.printf("%s%d", sep, order[0]);
            System.out.printf("%s%d", sep, order[2]);
            for (int i = n-1 ; i >= 3 ; i--) {
                System.out.printf("%s%d", sep, order[i]);
                sep = " ";
            }

            System.out.println();
        }
    }


    static class SafeUtils {

        public static int get(int[] array, int idx, int defVal) {
            if (0 <= idx && idx < array.length) {
                return array[idx];
            } else {
                return defVal;
            }
        }

        public static int get(int[][] matrix, int r, int c, int defVal) {
            if (0 <= r && r < matrix.length && 0 <= c && c < matrix[r].length) {
                return matrix[r][c];
            } else {
                return defVal;
            }
        }

    }



    static class IOUtils {
        private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        public static String readLine() throws IOException {
            return reader.readLine();
        }

        public static int readInt() throws IOException {
            int c;
            while ((c = reader.read()) != -1 && !Character.isDigit(c)) {}

            int result = c - '0';
            while ((c = reader.read()) != -1 && Character.isDigit(c)) {
                result = result * 10 + c - '0';
            }

            return result;
        }

        public static int[] readIntArray(int length) throws IOException {
            int[] result = new int[length];
            for (int i = 0 ; i < length ; i++) {
                result[i] = readInt();
            }
            return result;
        }

        public static int[][] readIntMatrix(int rows, int cols) throws IOException {
            int[][] matrix = new int[rows][];
            for (int i = 0 ; i < rows ; i++) {
                matrix[i] = readIntArray(cols);
            }
            return matrix;
        }

        public static long readLong() throws IOException {
            int c;
            while ((c = reader.read()) != -1 && !Character.isDigit(c)) {}

            long result = c - '0';
            while ((c = reader.read()) != -1 && Character.isDigit(c)) {
                result = result * 10 + c - '0';
            }

            return result;
        }

        public static long[] readLongArray(int length) throws IOException {
            long[] result = new long[length];
            for (int i = 0 ; i < length ; i++) {
                result[i] = readLong();
            }
            return result;
        }

        public static long[][] readLongMatrix(int rows, int cols) throws IOException {
            long[][] matrix = new long[rows][];
            for (int i = 0 ; i < rows ; i++)
                matrix[i] = readLongArray(cols);
            return matrix;
        }
    }

}
