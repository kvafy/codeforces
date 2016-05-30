import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;

public class Round353_A {

    public static void main(String[] args) throws IOException {
        int a = IOUtils.readInt();
        int b = IOUtils.readInt();
        int c = IOUtils.readInt();


        boolean yes;
        if (c == 0) {
            yes = (a == b);
        } else {
            yes = ((b - a) % c == 0) && ((b - a) / c) >= 0;
        }

        System.out.println(yes ? "YES" : "NO");
    }

    static class IOUtils {
        private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        public static String readLine() throws IOException {
            return reader.readLine();
        }

        public static int readInt() throws IOException {
            int c;
            while ((c = reader.read()) != -1 && !Character.isDigit(c) && c != '-' && c != '+') {}

            int sign = (c == '-') ? -1 : 1;
            int result = Character.isDigit(c) ? (c - '0') : 0;
            while ((c = reader.read()) != -1 && Character.isDigit(c)) {
                result = result * 10 + c - '0';
            }

            return sign * result;
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

