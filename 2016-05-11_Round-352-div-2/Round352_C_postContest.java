import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Round352_C_postContest {

    public static void main(String[] args) throws IOException {
        int[] params = IOUtils.readIntArray(6);

        Point a0 = new Point(params[0], params[1]);
        Point b0 = new Point(params[2], params[3]);
        Point trash = new Point(params[4], params[5]);

        int n = IOUtils.readInt();

        double solution;
        if (n == 0) {
            solution = 0;
        }
        else {
            List<Point> bottles = new ArrayList<>(n);
            for (int[] bottleXY : IOUtils.readIntMatrix(n, 2)) {
                bottles.add(new Point(bottleXY[0], bottleXY[1]));
            }

            final int T = 0,
                      TA = 1,
                      TB = 2,
                      TAB = 3;

            // dp[i][<sources>] ~ what is the best conceivable distance to collect
            //                  bottles 1..i (inclusive) having <sources> as starting places available
            double[][] dp = new double[n + 1][4];

            // solution
            dp[0][T] = Double.POSITIVE_INFINITY;
            dp[0][TA] = Double.POSITIVE_INFINITY;
            dp[0][TB] = Double.POSITIVE_INFINITY;
            dp[0][TAB] = 0;

            for (int i = 1; i <= n; i++) {
                Point bottleI = bottles.get(i-1);

                double bottleToTrash = bottleI.dist(trash);
                double a0ToBottle = a0.dist(bottleI);
                double b0ToBottle = b0.dist(bottleI);

                dp[i][T] = min(
                        dp[i-1][T]  + 2 * bottleToTrash,
                        dp[i-1][TA] + a0ToBottle + bottleToTrash,  // use initial position of A for this bottle
                        dp[i-1][TB] + b0ToBottle + bottleToTrash); // use initial position of B for this bottle

                dp[i][TA] = min(
                        dp[i-1][TA]  + 2 * bottleToTrash,
                        dp[i-1][TAB] + b0ToBottle + bottleToTrash
                );

                dp[i][TB] = min(
                        dp[i-1][TB]  + 2 * bottleToTrash,
                        dp[i-1][TAB] + a0ToBottle + bottleToTrash
                );

                dp[i][TAB] = dp[i-1][TAB] + 2 * bottleToTrash;
            }

            // don't include TAB (at least one of A or B has to move from its initial position)
            solution = min(dp[n][T], dp[n][TA], dp[n][TB]);
        }

        System.out.println(solution);
    }

    private static double min(double... values) {
        double result = values[0];
        for (double v : values) {
            result = Math.min(result, v);
        }
        return result;
    }


    static class Point {
        final int x;
        final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public double dist(Point that) {
            return Math.sqrt(Math.pow(this.x - that.x, 2) + Math.pow(this.y - that.y, 2));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (x != point.x) return false;
            return y == point.y;

        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public String toString() {
            return "[" + x + "," + y + "]";
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
