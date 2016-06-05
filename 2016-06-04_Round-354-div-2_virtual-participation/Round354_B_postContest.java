import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Round354_B_postContest {

    static final long MOD_10e9_PLUS_7 = 1_000_000_007L; // distributive over + - * (NOT /)

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int n = FastReader.readInt();
        int t = FastReader.readInt();

        // finishing times (when will given glass be full?)
        int fts[][] = new int[n][];
        // out-coming flow for glasses
        int[][] outFlowInv = new int[n][];

        // first level (one glass, full at time 1)
        fts[0] = new int[] {1};
        // top-level glass is receiving flow 1.0 and two flows are comming out, each (1/2), so inverse is 2
        outFlowInv[0] = new int[] {2};

        // every other level
        for (int i = 1 ; i < n ; i++) {
            fts[i] = new int[i + 1];
            outFlowInv[i] = new int[i + 1];

            // first / last glass (single stream from the above glass)
            fts[i][0] = fts[i][i] = fts[i - 1][0] + outFlowInv[i - 1][0];
            outFlowInv[i][0] = outFlowInv[i][i] = 2 * outFlowInv[i - 1][0];

            // middle glasses
            for (int j = 1 ; j < i ; j++) {
                int left = j - 1;
                int right = j;
                int ftLeft = fts[i - 1][left];
                int ftRight = fts[i - 1][right];
                int flowInvLeft = outFlowInv[i - 1][left];
                int flowInvRight = outFlowInv[i - 1][right];

                // compute finishing time
                if (ftLeft == ftRight) {
                    fts[i][j] = ftLeft
                              + (flowInvLeft * flowInvRight) / (flowInvLeft + flowInvRight);
                } else {
                    int flowInvLonger;
                    int flowInvShorter;
                    if (ftLeft < ftRight) {
                        flowInvLonger = flowInvLeft;
                        flowInvShorter = flowInvRight;
                    } else {
                        flowInvLonger = flowInvRight;
                        flowInvShorter = flowInvLeft;
                    }

                    int delta = Math.abs(ftLeft - ftRight);
                    fts[i][j] = Math.min(ftLeft, ftRight)
                              + delta
                              + flowInvShorter * (flowInvLonger - delta) / (flowInvShorter + flowInvLonger)
                              //+ (flowInvShorter * (flowInvLonger - delta) + (flowInvShorter + flowInvLonger - 1)) / (flowInvShorter + flowInvLonger)
                    ;
                }

                // compute flow
                outFlowInv[i][j] = (2 * flowInvLeft * flowInvRight) / (flowInvLeft + flowInvRight);
            }
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
