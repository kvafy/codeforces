import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Round356_E_postContest_v2 {

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
        int insideTotal = 0;
        int[] outside = Arrays.copyOf(componentSize, componentSize.length);
        for (int i = 0 ; i < k ; i++) {
            for (int j = 0 ; j < k ; j++) {
                if (grid[i][j] != WALL) {
                    insideTotal++;
                    outside[grid[i][j]]--;
                }
            }
        }

        // ...and move the window across board
        int xTop = 0;
        int yTop = 0;
        int dx = +1;
        int solution = 0;
        int[] flags = new int[components + 1];
        int flag = 0;
        while (true) {

            flag++;
            int cur = k*k;
            for (int off = 0 ; off < k ; off++) {
                if (xTop - 1 >= 0) {
                    int comp = grid[xTop - 1][yTop + off];
                    if (comp != WALL && flags[comp] != flag) {
                        cur += outside[comp];
                        flags[comp] = flag;
                    }
                }
                if (xTop + k < n) {
                    int comp = grid[xTop + k][yTop + off];
                    if (comp != WALL && flags[comp] != flag) {
                        cur += outside[comp];
                        flags[comp] = flag;
                    }
                }
                if (yTop - 1 >= 0) {
                    int comp = grid[xTop + off][yTop - 1];
                    if (comp != WALL && flags[comp] != flag) {
                        cur += outside[comp];
                        flags[comp] = flag;
                    }
                }
                if (yTop + k < n) {
                    int comp = grid[xTop + off][yTop + k];
                    if (comp != WALL && flags[comp] != flag) {
                        cur += outside[comp];
                        flags[comp] = flag;
                    }
                }
            }
            solution = Math.max(solution, cur);


            boolean moveRight = dx == +1 && (xTop + k + 1 <= n);
            boolean moveLeft  = dx == -1 && (xTop - 1 >= 0);
            boolean moveDown  = !moveRight && !moveLeft;
            if (moveRight) {
                for (int off = 0; off < k; off++) {
                    if (grid[xTop][yTop + off] != WALL) {
                        outside[grid[xTop][yTop + off]]++;
                    }
                    if (grid[xTop + k][yTop + off] != WALL) {
                        outside[grid[xTop + k][yTop + off]]--;
                    }
                }
                xTop++;
            } else if (moveLeft) {
                for (int off = 0 ; off < k ; off++) {
                    if (grid[xTop + k - 1][yTop + off] != WALL) {
                        outside[grid[xTop + k - 1][yTop + off]]++;
                    }
                    if (grid[xTop - 1][yTop + off] != WALL) {
                        outside[grid[xTop - 1][yTop + off]]--;
                    }
                }
                xTop--;
            } else {
                if (yTop + k == n) {
                    break;
                }

                for (int off = 0 ; off < k ; off++) {
                    if (grid[xTop + off][yTop] != WALL) {
                        outside[grid[xTop + off][yTop]]++;
                    }
                    if (grid[xTop + off][yTop + k] != WALL) {
                        outside[grid[xTop + off][yTop + k]]--;
                    }
                }
                dx *= -1;
                yTop++;
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
