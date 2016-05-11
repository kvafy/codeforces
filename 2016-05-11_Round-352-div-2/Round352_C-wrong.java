import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;
import java.util.function.BiFunction;

public class Round352_C {

    public static void main(String[] args) throws IOException {
        int[] params = IOUtils.readIntArray(6);

        Point aPos = new Point(params[0], params[1]);
        Point bPos = new Point(params[2], params[3]);
        Point trash = new Point(params[4], params[5]);

        int bottleCount = IOUtils.readInt();

        Set<Point> bottles = new HashSet<>(bottleCount);
        for (int[] bottle : IOUtils.readIntMatrix(bottleCount, 2)) {
            bottles.add(new Point(bottle[0], bottle[1]));
        }


        // solution

        double solution = 0;
        if (bottles.size() == 0) {
            solution = 0;
        }
        else if (bottles.size() == 1) {
            Point bottle = new ArrayList<>(bottles).get(0);
            solution = bottle.dist(trash) + Math.min(bottle.dist(aPos), bottle.dist(bPos));
        }
        else {
            Point bestBottleForA = findBestInitialBottle(aPos, bottles, trash);
            Point bestBottleForB = findBestInitialBottle(bPos, bottles, trash);

            if (bestBottleForA.equals(bestBottleForB)) {
                Point commonBottle = bestBottleForA;

                bottles.remove(commonBottle);
                Point secondBestBottleForA = findBestInitialBottle(aPos, bottles, trash);
                Point secondBestBottleForB = findBestInitialBottle(bPos, bottles, trash);
                bottles.add(commonBottle);

                if (commonBottle.dist(aPos) + secondBestBottleForB.dist(bPos) + secondBestBottleForB.dist(trash)
                    <
                    commonBottle.dist(bPos) + secondBestBottleForA.dist(aPos) + secondBestBottleForA.dist(trash)) {
                    bestBottleForB = secondBestBottleForB;
                }
                else {
                    bestBottleForA = secondBestBottleForA;
                }
            }
//            else {
//                if (bestBottleForA.dist(aPos) + bestBottleForA.dist(trash)
//                        >
//                    bestBottleForB.dist(bPos) + bestBottleForB.dist(trash)) {
//                    Point tmpPos = aPos;
//                    Point tmpBottle = bestBottleForA;
//                    aPos = bPos;
//                    bestBottleForA = bestBottleForB;
//                    bPos = tmpPos;
//                    bestBottleForB = tmpBottle;
//                }
//            }

            // A picks up the best bottle for him and trashes it
            solution += bestBottleForA.dist(aPos);
            solution += bestBottleForA.dist(trash);
            bottles.remove(bestBottleForA);

            if (!bottles.isEmpty()) {
                bestBottleForB = findBestInitialBottle(bPos, bottles, trash);
                if (bestBottleForB.dist(bPos) < bestBottleForB.dist(trash)) {
                    // the bottle closest to B is better picked by B from his initial position than by A from trash position
                    solution += bestBottleForB.dist(bPos);
                    solution += bestBottleForB.dist(trash);
                    bottles.remove(bestBottleForB);
                }
            }


            // all other bottles will be picked up from the trash position
            for (Point bottle : bottles) {
                solution += 2 * bottle.dist(trash);
            }
        }

        System.out.println(solution);
    }

    private static Point findBestInitialBottle(Point start, Collection<Point> bottles, Point trash) {
        Point bestBottle = null;
        Double bestSavedDistance = null;

        for (Point bottle : bottles) {
            double save = trash.dist(bottle) - start.dist(bottle);
            if (bestSavedDistance == null || save > bestSavedDistance) {
                bestBottle = bottle;
                bestSavedDistance = save;
            }
        }

        return bestBottle;
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
