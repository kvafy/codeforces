import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Round354_C {

    static final long MOD_10e9_PLUS_7 = 1_000_000_007L; // distributive over + - * (NOT /)

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int n = FastReader.readInt();
        int k = FastReader.readInt();
        String s = FastReader.readString();

        int solution = 0;

        int as = 0;
        int bs = 0;
        int i = 0;
        int j = 0;
        while (j < n) {
            // examine s[i,j]
            char ci = s.charAt(i);
            char cj = s.charAt(j);

            if (Math.min(as + (cj == 'a' ? 1 : 0),
                         bs + (cj == 'b' ? 1 : 0))
                    <= k) {
                solution = Math.max(solution, j - i + 1);
                as += (cj == 'a') ? 1 : 0;
                bs += (cj == 'b') ? 1 : 0;
                j++;
            } else {
                as -= (ci == 'a') ? 1 : 0;
                bs -= (ci == 'b') ? 1 : 0;
                i++;
            }
        }

        System.out.println(solution);
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

        static double readDouble() throws IOException {
            return Double.parseDouble(readString());
        }
    }
}
