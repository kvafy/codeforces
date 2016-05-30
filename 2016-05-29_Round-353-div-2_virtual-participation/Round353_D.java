import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;

public class Round353_D {

    public static void main(String[] args) throws IOException {
        int n = IOUtils.readInt();
        int[] as = IOUtils.readIntArray(n);

        // map: left interval bound => interval owner
        TreeMap<Integer, Integer> cover = new TreeMap<>();
        cover.put(Integer.MIN_VALUE, as[0]); // starting by -inf, the interval covered by as[0]
        cover.put(as[0], as[0]);             // starting by as[0], the interval is covered by as[0]

        for (int i = 1 ; i < n ; i++) {
            // find out into which interval the current as[i] belongs (who would be the father in BST)
            Map.Entry<Integer, Integer> intervalStartAndOwner = cover.lowerEntry(as[i]);
            int intervalStart = intervalStartAndOwner.getKey();
            int owner = intervalStartAndOwner.getValue();
            cover.put(intervalStart, as[i]);
            cover.put(as[i], as[i]);
            System.out.printf("%d ", owner);
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

        public static void print(int[] array, String sep) {
            String s = "";
            for (int item : array) {
                System.out.print(s);
                s = sep;
                System.out.print(item);
            }
            System.out.println();
        }
    }

}
