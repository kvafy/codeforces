import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Round355_D_v2 {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        // chest type -> list of rooms with given chest type
        // ~ graph adjacency list
        Map<Integer, List<Room>> pRoomsMap = new HashMap<>();

        int n = FastReader.readInt();
        int m = FastReader.readInt();
        int p = FastReader.readInt();
        int[][] aij = new int[n + 1][]; // [0][x] and [x][0] won't be used
        for (int i = 1 ; i <= n ; i++) {
            aij[i] = new int[m + 1];
            for (int j = 1 ; j <= m ; j++) {
                aij[i][j] = FastReader.readInt();
                List<Room> pRooms = pRoomsMap.get(aij[i][j]);
                if (pRooms == null) {
                    pRooms = new ArrayList<>();
                    pRoomsMap.put(aij[i][j], pRooms);
                }
                pRooms.add(new Room(i, j));
            }
        }

        final int NOT_REACHED_YET = Integer.MAX_VALUE;
        int[][] dist = new int[n + 1][m + 1]; // [0][x] and [x][0] won't be used
        fill(dist, NOT_REACHED_YET);

        PriorityQueue<RoomWithDist> queue = new PriorityQueue<>();
        Set<Room> closed = new HashSet<>();

        queue.add(new RoomWithDist(new Room(1,1), 0));

        int solution = 0;
        while (!queue.isEmpty()) {
            RoomWithDist cur = queue.remove();


            int chestType;
            if (cur.dist == 0) {
                chestType = 0;
            } else {
                chestType = aij[cur.pair.r][cur.pair.c];
                closed.add(cur.pair);
            }

            if (chestType == p) {
                solution = cur.dist;
                break;
            }

            for (Room next : pRoomsMap.get(chestType + 1)) {
                if (!closed.contains(next)) {
                    int curToNext = cur.dist + distance(cur.pair, next);

                    if (dist[next.r][next.c] == NOT_REACHED_YET) {
                        dist[next.r][next.c] = curToNext;
                        queue.add(new RoomWithDist(next, curToNext));
                    } else if (curToNext < dist[next.r][next.c]) {
                        // better way found
                        //TODO slow update
                        RoomWithDist oldPair = new RoomWithDist(next, dist[next.r][next.c]);
                        RoomWithDist newPair = new RoomWithDist(next, curToNext);
                        dist[next.r][next.c] = curToNext;
                        queue.remove(oldPair);
                        queue.add(newPair);
                    }
                }
            }
        }

        System.out.println(solution);
    }

    private static void fill(int[][] matrix, int val) {
        for(int[] row : matrix) {
            Arrays.fill(row, val);
        }
    }

    private static int distance(Room from, Room to) {
        return Math.abs(from.r - to.r) + Math.abs(from.c - to.c);
    }

    static class Room {
        final int r;
        final int c;

        public Room(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Room pair = (Room) o;

            if (r != pair.r) return false;
            return c == pair.c;

        }

        @Override
        public int hashCode() {
            int result = r;
            result = 31 * result + c;
            return result;
        }
    }

    static class RoomWithDist implements Comparable {
        final Room pair;
        int dist;

        public RoomWithDist(Room pair, int dist) {
            this.pair = pair;
            this.dist = dist;
        }

        @Override
        public int compareTo(Object that) {
            return Integer.compare(this.dist, ((RoomWithDist) that).dist);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RoomWithDist that = (RoomWithDist) o;

            if (dist != that.dist) return false;
            return pair.equals(that.pair);

        }

        @Override
        public int hashCode() {
            int result = pair.hashCode();
            result = 31 * result + dist;
            return result;
        }
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
