import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

public class Round354_B {

    static final long MOD_10e9_PLUS_7 = 1_000_000_007L; // distributive over + - * (NOT /)

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int n = FastReader.readInt();
        int t = FastReader.readInt();

        // finishing times (when will given glass be full?)
        int fts[][] = new int[n][];
        // incoming flow for glasses
        int[][] inFlowInv = new int[n][];

        // first level (one glass, full at time 1)
        int rInv = 1; // inverse rate of one flow from above (now all flows from bottle to single glass)
        fts[0] = new int[] {1};

        // every other level
        rInv = 2; // from the top-level glass there are two streams, each 1/2, so inverse is 2
        for (int i = 1 ; i < n ; i++) {
            fts[i] = new int[i + 1];

            // first / last glass (single stream from the above glass)
            fts[i][0] = fts[i][i] = fts[i - 1][0] + rInv;

            // middle glasses
            for (int j = 1 ; j < i ; j++) {
                int lAbove = j - 1;
                int rAbove = j;
                int delta = Math.abs(fts[i - 1][lAbove] - fts[i - 1][rAbove]);

                fts[i][j] = Math.min(fts[i - 1][lAbove], fts[i - 1][rAbove])
                          + (rInv + delta) / 2;
            }

            rInv += 2; // one new glass on the next level, so two new streams
        }

        int solution = 0;
        for (int[] row : fts) {
            solution += Arrays.stream(row).filter(ft -> ft <= t).count();
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
