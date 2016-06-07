import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

public class Round348_A {

    static final long MOD_10e9_PLUS_7 = 1_000_000_007L; // distributive over + - * (NOT /)

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int n = FastReader.readInt();
        int m = FastReader.readInt();
        int q = FastReader.readInt();

        ArrayList<int[]> operations = new ArrayList<>(q); // TODO plain array will do

        for (int i = 0 ; i < q ; i++) {
            int type = FastReader.readInt();
            switch(type) {
                case 1:
                    int row = FastReader.readInt() - 1;
                    operations.add(new int[] {1, row});
                    break;
                case 2:
                    int col = FastReader.readInt() - 1;
                    operations.add(new int[] {2, col});
                    break;
                case 3:
                    int r = FastReader.readInt() - 1;
                    int c = FastReader.readInt() - 1;
                    int x = FastReader.readInt();
                    operations.add(new int[] {3, r, c, x});
                    break;
            }
        }

        // simulate backwards
        int[][] xs = new int[n][m];

        for (int i = operations.size() - 1 ; i >= 0 ; i--) {
            int[] op = operations.get(i);
            switch(op[0]) {
                case 1:
                    rotateInvRow(xs, op[1]);
                    break;
                case 2:
                    rotateInvCol(xs, op[1]);
                    break;
                case 3:
                    xs[op[1]][op[2]] = op[3];
                    break;
            }
        }

        // result
        for (int r = 0 ; r < n ; r++) {
            for (int c = 0 ; c < m ; c++) {
                System.out.printf("%d ", xs[r][c]);
            }
            System.out.println();
        }
    }

    private static void rotateInvRow(int[][] xs, int r) {
        int cMax = xs[r].length - 1;
        int tmp = xs[r][cMax];
        System.arraycopy(xs[r], 0, xs[r], 1, cMax);
        xs[r][0] = tmp;
    }

    private static void rotateInvCol(int[][] xs, int c) {
        int rMax = xs.length - 1;
        int tmp = xs[rMax][c];
        for (int i = rMax ; i >= 1 ; i--) {
            xs[i][c] = xs[i-1][c];
        }
        xs[0][c] = tmp;
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
