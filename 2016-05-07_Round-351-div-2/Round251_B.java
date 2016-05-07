import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;

public class Round251_B {

    public static void main(String[] args) throws IOException {
        int[] nm = IOUtils.readIntArray(2);
        int n = nm[0];
        int m = nm[1];

        int solution;
        if (m == 0) {
            // no constraints
            solution = n - 1;
        } else {
            int maxDiv2 = Integer.MIN_VALUE;
            int minDiv1 = Integer.MAX_VALUE;
            for (int i = 0 ; i < m ; i++) {
                int u = IOUtils.readInt();
                int v = IOUtils.readInt();
                if (v < u) {
                    maxDiv2 = Math.max(maxDiv2, v);
                    minDiv1 = Math.min(minDiv1, u);
                } else {
                    maxDiv2 = Math.max(maxDiv2, u);
                    minDiv1 = Math.min(minDiv1, v);
                }
            }
            solution = Math.max(0, minDiv1 - maxDiv2);
        }

        System.out.println(solution);
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
