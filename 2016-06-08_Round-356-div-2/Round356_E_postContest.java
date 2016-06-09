import java.io.*;
import java.util.*;

public class Round356_E_postContest {

    static final int WALL = 0;
    static final int EMPTY_NOT_DISCOVERED = -1;

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        // read input

        int n = in.readInt();
        int k = in.readInt();

        int[][] grid = new int[n][n];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < n ; j++) {
                char c = in.readChar();
                grid[i][j] = (c == '.') ? EMPTY_NOT_DISCOVERED : WALL;
            }
        }


        // find components
        int components = 0;
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < n ; j++) {
                if (grid[i][j] == EMPTY_NOT_DISCOVERED) {
                    discover(grid, i, j, ++components);
                }
            }
        }
        // each grid[i][j] cell now holds either 0 (WALL) or number of the component (1..x)

        // compute component sizes
        int[] componentSize = new int[components + 1];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < n ; j++) {
                if (grid[i][j] != WALL) {
                    componentSize[grid[i][j]]++;
                }
            }
        }

        // initialize window
        int[] overlaps = new int[components + 1];
        int[] touches = new int[components + 1];
        Set<Integer> nonZeroOverlaps = new HashSet<>(components);
        Set<Integer> nonZeroTouches = new HashSet<>(components);
        for (int i = 0 ; i < k ; i++) {
            for (int j = 0 ; j < k ; j++) {
                if (grid[i][j] != WALL) {
                    add(overlaps, nonZeroOverlaps, grid, i ,j);
                    add(touches, nonZeroTouches, grid, i, j);
                }
            }
        }
        if (k < n) {
            for (int off = 0; off < k; off++) {
                add(touches, nonZeroTouches, grid, k, off);
                add(touches, nonZeroTouches, grid, off, k);
            }
        }

        // ...and move the window across board
        int xTop = 0;
        int yTop = 0;
        int dx = +1;
        int solution = 0;
        while (true) {
            int cur = k*k
                    + nonZeroTouches.stream().mapToInt(c -> componentSize[c]).sum()
                    - nonZeroOverlaps.stream().mapToInt(c -> overlaps[c]).sum();
            solution = Math.max(solution, cur);

            boolean moveRight = dx == +1 && (xTop + k + 1 <= n);
            boolean moveLeft  = dx == -1 && (xTop - 1 >= 0);
            boolean moveDown  = !moveRight && !moveLeft;
            if (moveRight) {
                for (int off = 0; off < k; off++) {
                    remove(touches, nonZeroTouches, grid, xTop - 1, yTop + off);
                    remove(overlaps, nonZeroOverlaps, grid, xTop, yTop + off);
                }
                remove(touches, nonZeroTouches, grid, xTop, yTop - 1);
                remove(touches, nonZeroTouches, grid, xTop, yTop + k);

                xTop++;

                for (int off = 0; off < k; off++) {
                    add(touches, nonZeroTouches, grid, xTop + k, yTop + off);
                    add(overlaps, nonZeroOverlaps, grid, xTop + k - 1, yTop + off);
                }
                add(touches, nonZeroTouches, grid, xTop + k - 1, yTop - 1);
                add(touches, nonZeroTouches, grid, xTop + k - 1, yTop + k);
            } else if (moveLeft) {
                for (int off = 0 ; off < k ; off++) {
                    remove(touches, nonZeroTouches, grid, xTop + k, yTop + off);
                    remove(overlaps, nonZeroOverlaps, grid, xTop + k - 1, yTop + off);
                }
                remove(touches, nonZeroTouches, grid, xTop + k - 1, yTop - 1);
                remove(touches, nonZeroTouches, grid, xTop + k - 1, yTop + k);

                xTop--;

                for (int off = 0 ; off < k ; off++) {
                    add(touches, nonZeroTouches, grid, xTop - 1, yTop + off);
                    add(overlaps, nonZeroOverlaps, grid, xTop, yTop + off);
                }
                add(touches, nonZeroTouches, grid, xTop, yTop - 1);
                add(touches, nonZeroTouches, grid, xTop, yTop + k);
            } else {
                if (yTop + k + 1 > n) {
                    break;
                }

                for (int off = 0 ; off < k ; off++) {
                    remove(touches, nonZeroTouches, grid, xTop + off, yTop - 1);
                    remove(overlaps, nonZeroOverlaps, grid, xTop + off, yTop);
                }
                remove(touches, nonZeroTouches, grid, xTop - 1, yTop);
                remove(touches, nonZeroTouches, grid, xTop + k, yTop);

                dx *= -1;
                yTop++;

                for (int off = 0 ; off < k ; off++) {
                    add(touches, nonZeroTouches, grid, xTop + off, yTop + k);
                    add(overlaps, nonZeroOverlaps, grid, xTop + off, yTop + k - 1);
                }
                add(touches, nonZeroTouches, grid, xTop - 1, yTop + k - 1);
                add(touches, nonZeroTouches, grid, xTop + k, yTop + k - 1);
            }
        }

        out.println(solution);
        out.flush();
    }

    private static void discover(int[][] grid, int i, int j, int componentNo) {
        int n = grid.length;
        LinkedList<int[]> open = new LinkedList<>();

        grid[i][j] = componentNo;
        open.add(new int[] {i,j});

        while (!open.isEmpty()) {
            int[] xy = open.removeFirst();
            int x = xy[0];
            int y = xy[1];

            for (int[] xxyy : new int[][] {{x-1, y}, {x+1,y}, {x,y+1}, {x,y-1}}) {
                int xx = xxyy[0];
                int yy = xxyy[1];
                if (0 <= xx && xx < n && 0 <= yy && yy < n) {
                    if (grid[xx][yy] == EMPTY_NOT_DISCOVERED) {
                        grid[xx][yy] = componentNo;
                        open.add(new int[] {xx, yy});
                    }
                }
            }
        }
    }

    private static void add(int[] cntPerComponent, Set<Integer> nonZeroCntComponents, int[][] grid, int i, int j) {
        if (isValid(grid, i, j) && grid[i][j] != WALL) {
            if (cntPerComponent[grid[i][j]]++ == 0) {
                nonZeroCntComponents.add(grid[i][j]);
            }
        }
    }

    private static void remove(int[] cntPerComponent, Set<Integer> nonZeroCntComponents, int[][] grid, int i, int j) {
        if (isValid(grid, i, j) && grid[i][j] != WALL) {
            if (--cntPerComponent[grid[i][j]] == 0) {
                nonZeroCntComponents.remove(grid[i][j]);
            }
        }
    }

    private static boolean isValid(int[][] grid, int i, int j) {
        int n = grid.length;
        return (0 <= i && i < n && 0 <= j && j < n);
    }

    /**
     * Custom buffered reader. Faster than Scanner and
     * BufferedReader + StringTokenizer.
     */
    static class FastReader {
        private final InputStream stream;
        private int current;
        private int size;
        private byte[] buffer = new byte[1024 * 8];

        public FastReader(InputStream stream) {
            this.stream = stream;
            current = 0;
            size = 0;
        }

        public int readInt() {
            int sign = 1;
            int abs = 0;
            int c = readNonEmpty();
            if (c == '-') {
                sign = -1;
                c = readAny();
            }
            do {
                if (c < '0' || c > '9') {
                    throw new IllegalStateException();
                }
                abs = 10 * abs + (c - '0');
                c = readAny();
            } while (!isEmpty(c));
            return sign * abs;
        }

        public int[] readIntArray(int n) {
            int[] array = new int[n];
            for (int i = 0 ; i < n ; i++) {
                array[i] = readInt();
            }
            return array;
        }

        public char readChar() {
            return (char) readNonEmpty();
        }

        public String readString() {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = readAny();
            } while (isEmpty(c));
            do {
                sb.append((char) c);
                c = readAny();
            } while (!isEmpty(c));
            return sb.toString();
        }

        private int readAny() {
            try {
                if (current >= size) {
                    current = 0;
                    size = stream.read(buffer);
                    if (size < 0) {
                        return -1;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to readAny next byte", e);
            }
            return buffer[current++];
        }

        private int readNonEmpty() {
            int result;
            do {
                result = readAny();
            } while (isEmpty(result));
            return result;
        }

        private static boolean isEmpty(int c) {
            return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == -1;
        }
    }
}
