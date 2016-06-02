import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Round355_B {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int n = FastReader.readInt();
        int h = FastReader.readInt();
        int k = FastReader.readInt();
        int[] as  = FastReader.readIntArray(n);

        int solution = 0;

        int curH = 0;
        int next = 0;
        while(next < n || curH > 0) {
            while (next < n && curH + as[next] <= h) {
                curH += as[next++];
            }

            if (curH > k) {
                int cycles = curH / k;
                solution += cycles;
                curH = curH - cycles * k;
            } else {
                solution++;
                curH = 0;
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

        static long readLong() throws IOException {
            return Long.parseLong(readString());
        }

        static double readDouble() throws IOException {
            return Double.parseDouble(readString());
        }
    }
}
