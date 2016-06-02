import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;

public class Round355_C {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        String s = FastReader.readString();

        boolean allZeros = true;
        BigInteger solution = BigInteger.ONE;
        for (int i = 0 ; i < s.length() ; i++) {
            int n = toBase64(s.charAt(i));
            solution = solution.multiply(BigInteger.valueOf(possibilities(n)));
            allZeros = allZeros && n == 0;
        }

        if (allZeros) {
            solution = BigInteger.ONE;
        }

        System.out.println(solution.mod(BigInteger.valueOf(1_000_000_007L)).toString());
    }

    private static int toBase64(char c) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        } else if ('A' <= c && c <= 'Z') {
            return c - 'A' + 10;
        } else if ('a' <= c && c <= 'z') {
            return c - 'a' + 36;
        } else if (c == '-') {
            return 62;
        } else if (c == '_') {
            return 63;
        } else {
            throw new IllegalArgumentException("Unknown character '" + c + "'.");
        }
    }

    private static int possibilities(int word6) {
        int result = 1;

        for (int i = 0 ; i < 6 ; i++) {
            if ((((word6 >>> i) & 1)) == 0) {
                result *= 3;
            }
        }

        return result;
    }

    // inspired by https://www.cpe.ku.ac.th/~jim/java-io.html
    static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        static String readString() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                String line = reader.readLine();
                if (line == null) {
                    throw new IllegalStateException("No more input");
                }
                tokenizer = new StringTokenizer(line);
            }
            return tokenizer.nextToken();
        }

        static int readInt() throws IOException {
            return Integer.parseInt(readString());
        }

        static int[] readIntArray(int length) throws IOException {
            int[] result = new int[length];
            for (int i = 0 ; i < length ; i++) {
                result[i] = readInt();
            }
            return result;
        }

        static int[][] readIntMatrix(int rows, int cols) throws IOException {
            int[][] matrix = new int[rows][];
            for (int i = 0 ; i < rows ; i++) {
                matrix[i] = readIntArray(cols);
            }
            return matrix;
        }

        static long readLong() throws IOException {
            return Long.parseLong(readString());
        }

        static double readDouble() throws IOException {
            return Double.parseDouble(readString());
        }
    }
}
