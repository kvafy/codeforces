import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Round355_D_postContest {

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
                    pRooms = new ArrayList<>(n * m - p); // pre-allocate
                    pRoomsMap.put(aij[i][j], pRooms);
                }
                pRooms.add(new Room(i, j));
            }
        }

        final int NOT_REACHED_YET = Integer.MAX_VALUE;
        int[][] dist = new int[n + 1][m + 1]; // [0][x] and [x][0] won't be used
        fill(dist, NOT_REACHED_YET);

        MinHeap<Room> queue = new MinHeap<>();

        queue.add(new Room(1,1), 0);

        int solution = 0;
        boolean initial = true;
        while (queue.size() > 0) {
            Room cur = queue.getHeadItem();
            int curDist = queue.getHeadCost();
            queue.removeHead();

            int chestType;
            if (initial) {
                // special handling if the target position is also the initial position
                chestType = 0;
                initial = false;
            } else {
                chestType = aij[cur.r][cur.c];
            }

            if (chestType == p) {
                solution = curDist;
                break;
            }

            for (Room next : pRoomsMap.get(chestType + 1)) {
                int curToNext = curDist + distance(cur, next);

                if (dist[next.r][next.c] == NOT_REACHED_YET) {
                    dist[next.r][next.c] = curToNext;
                    queue.add(next, curToNext);
                } else if (curToNext < dist[next.r][next.c]) {
                    // better way found
                    dist[next.r][next.c] = curToNext;
                    queue.update(next, curToNext);
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
    }
}


class MinHeap<T> {
    private final ArrayList<ValueWrapper<T>> heap = new ArrayList<>();
    private final Map<T, ValueWrapper<T>> extMap = new HashMap<>();

    public void add(T value, int cost) {
        ValueWrapper<T> valueExt = new ValueWrapper<>(value, heap.size(), cost);
        heap.add(valueExt);
        extMap.put(value, valueExt);

        heapifyUp(valueExt.idx);
    }

    public T getHeadItem() {
        return heap.get(0).value;
    }

    public int getHeadCost() {
        return heap.get(0).cost;
    }

    public void removeHead() {
        T head = heap.get(0).value;

        ValueWrapper<T> lastExt = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, lastExt);
            lastExt.idx = 0;
            heapifyDown(0);
        }

        extMap.remove(head);
    }

    public void update(T value, int newCost) {
        ValueWrapper<T> valueExt = extMap.get(value);

        int delta = newCost - valueExt.cost;
        valueExt.cost = newCost;
        if (delta > 0) {
            heapifyDown(valueExt.idx);
        } else if (delta < 0) {
            heapifyUp(valueExt.idx);
        }
    }

    public int size() {
        return heap.size();
    }

    private void heapifyUp(int idx) {
        while (true) {
            if (idx == 0) {
                break;
            }

            int parentIdx = (idx - 1) / 2;

            ValueWrapper<T> curExt = heap.get(idx);
            ValueWrapper<T> parentExt = heap.get(parentIdx);
            if (parentExt.cost <= curExt.cost) {
                break;
            }

            swap(parentIdx, idx);

            idx = parentIdx;
        }
    }

    private void heapifyDown(int idx) {
        while (true) {
            int leftIdx = idx * 2 + 1;
            if (leftIdx >= heap.size()) {
                break;
            }
            int rightIdx = (leftIdx + 1 < heap.size()) ? leftIdx + 1 : leftIdx;

            ValueWrapper<T> curExt = heap.get(idx);
            ValueWrapper<T> leftExt = heap.get(leftIdx);
            ValueWrapper<T> rightExt = heap.get(rightIdx);
            ValueWrapper<T> minChildExt = (leftExt.cost <= rightExt.cost) ? leftExt : rightExt;

            if (curExt.cost <= minChildExt.cost) {
                break;
            }

            int swapIdx = minChildExt.idx;
            swap(idx, minChildExt.idx);
            idx = swapIdx;
        }
    }

    private void swap(int i, int j) {
        if (i != j) {
            ValueWrapper<T> iExt = heap.get(i);
            ValueWrapper<T> jExt = heap.get(j);

            iExt.idx = j;
            jExt.idx = i;
            heap.set(i, jExt);
            heap.set(j, iExt);
        }
    }


    private static class ValueWrapper<T> {
        final T value;
        int idx;
        int cost;
        public ValueWrapper(T value, int idx, int cost) {
            this.value = value;
            this.idx = idx;
            this.cost = cost;
        }
    }
}