public class DebugUtils {

    public static void printMatrix(Object[][] matrix, int colWidth, char fmt) {
        String format = "%" + colWidth + fmt;

        for (Object[] row : matrix) {
            for (Object item : row) {
                System.out.printf(format, item.toString());
            }
            System.out.println();
        }
    }

    public static void printMatrix(int[][] matrix, int colWidth) {
        String format = "%" + colWidth + "d";

        for (int[] row : matrix) {
            for (int item : row) {
                System.out.printf(format, item);
            }
            System.out.println();
        }
    }

    public static void printMatrix(long[][] matrix, int colWidth) {
        String format = "%" + colWidth + "d";

        for (long[] row : matrix) {
            for (long item : row) {
                System.out.printf(format, item);
            }
            System.out.println();
        }
    }
}
