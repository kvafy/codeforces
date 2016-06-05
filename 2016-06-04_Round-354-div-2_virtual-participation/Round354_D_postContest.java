import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Round354_D_postContest {

    static final long MOD_10e9_PLUS_7 = 1_000_000_007L; // distributive over + - * (NOT /)

    public static void main(String[] args) throws IOException {
//        String chrs = "+-|^>vLRUD*";
//        Random random = new Random();
//        int n = 800;
//        int m = 800;
//        char[][] l = new char[n][m];
//        for (int r = 0 ; r < n ; r++) {
//            for (int c = 0 ; c < n ; c++) {
//                l[r][c] = chrs.charAt(random.nextInt(chrs.length()));
//            }
//        }
//
//        int tr = 10;
//        int tc = 10;
//        int mr = n - 11;
//        int mc = m - 11;


        FastReader.init(System.in);

        int n = FastReader.readInt();
        int m = FastReader.readInt();

        char[][] l = new char[n][m];
        for (int r = 0 ; r < n ; r++) {
            String row = FastReader.readString();
            for (int c = 0 ; c < m ; c++) {
                l[r][c] = row.charAt(c);
            }
        }

        int tr = FastReader.readInt() - 1;
        int tc = FastReader.readInt() - 1;
        int mr = FastReader.readInt() - 1;
        int mc = FastReader.readInt() - 1;

        LinkedList<State> open = new LinkedList<>();
        HashSet<State> discovered = new HashSet<>();
        HashMap<State, State> prevMap = new HashMap<>();
        State init = new State(0, tr, tc);
        State goal = null;

        if (tr == mr && tc == mc) {
            goal = init;
        } else {
            open.addLast(init);
            discovered.add(init);

            while (!open.isEmpty() && goal == null) {
                State cur = open.removeFirst();
                for (State next : cur.nextStates(l)) {
                    if (!discovered.contains(next)) {
                        prevMap.put(next, cur);
                        discovered.add(next);
                        open.addLast(next);
                        if (next.tr == mr && next.tc == mc) {
                            goal = next;
                            break;
                        }
                    }
                }
            }
        }

        int solution;
        if (goal != null) {
            solution = 0;
            State cur = goal;
            while (true) {
                State prev = prevMap.get(cur);
                if (prev == null) {
                    break;
                }
                solution++;
                cur = prev;
            }
        } else {
            solution = -1;
        }

        System.out.println(solution);
    }

    static class State {
        final int rot;
        final int tr;
        final int tc;

        private static final ArrayList<State> nextStates = new ArrayList<>(5);

        public State(int rot, int tr, int tc) {
            this.rot = rot;
            this.tr = tr;
            this.tc = tc;
        }

        public List<State> nextStates(char[][] l) {
            int rMax = l.length;
            int cMax = l[0].length;

            nextStates.clear();

            // rotate
            nextStates.add(new State((rot + 1) % 4, tr, tc));
            // go up
            if (tr - 1 >= 0 && southOpen(l, rot, tr - 1, tc) && northOpen(l, rot, tr, tc)) {
                nextStates.add(new State(rot, tr - 1, tc));
            }
            // go down
            if (tr + 1 < rMax && southOpen(l, rot, tr, tc) && northOpen(l, rot, tr + 1, tc)) {
                nextStates.add(new State(rot, tr + 1, tc));
            }
            // go left
            if (tc - 1 >= 0 && eastOpen(l, rot, tr, tc - 1) && westOpen(l, rot, tr, tc)) {
                nextStates.add(new State(rot, tr, tc - 1));
            }
            // go right
            if (tc + 1 < cMax && eastOpen(l, rot, tr, tc) && westOpen(l, rot, tr, tc + 1)) {
                nextStates.add(new State(rot, tr, tc + 1));
            }

            return nextStates;
        }

        private boolean northOpen(char[][] l, int rot, int r, int c) {
            switch(l[r][c]) {
                case '+':
//                «+» means this block has 4 doors (one door to each neighbouring block);
                    return true;
                case '-':
//                «-» means this block has 2 doors — to the left and to the right neighbours;
                    return rot == 1 || rot == 3;
                case '|':
//                «|» means this block has 2 doors — to the top and to the bottom neighbours;
                    return rot == 0 || rot == 2;
                case '^':
//                «^» means this block has 1 door — to the top neighbour;
                    return rot == 0;
                case '>':
//                «>» means this block has 1 door — to the right neighbour;
                    return rot == 3;
                case '<':
//                «<» means this block has 1 door — to the left neighbour;
                    return rot == 1;
                case 'v':
//                «v» means this block has 1 door — to the bottom neighbour;
                    return rot == 2;
                case 'L':
//                «L» means this block has 3 doors — to all neighbours except left one;
                    return rot != 1;
                case 'R':
//                «R» means this block has 3 doors — to all neighbours except right one;
                    return rot != 3;
                case 'U':
//                «U» means this block has 3 doors — to all neighbours except top one;
                    return rot != 0;
                case 'D':
//                «D» means this block has 3 doors — to all neighbours except bottom one;
                    return rot != 2;
                case '*':
//                «*» means this block is a wall and has no doors.
                    return false;
                default:
                    throw new IllegalArgumentException();
            }
        }

        private boolean eastOpen(char[][] l, int rot, int r, int c) {
            return northOpen(l, (rot + 3) % 4, r, c);
        }

        private boolean southOpen(char[][] l, int rot, int r, int c) {
            return northOpen(l, (rot + 2) % 4, r, c);
        }

        private boolean westOpen(char[][] l, int rot, int r, int c) {
            return northOpen(l, (rot + 1) % 4, r, c);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            State state = (State) o;

            if (rot != state.rot) return false;
            if (tr != state.tr) return false;
            return tc == state.tc;

        }

        @Override
        public int hashCode() {
            int result = rot;
            result = 31 * result + tr;
            result = 31 * result + tc;
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

        static double readDouble() throws IOException {
            return Double.parseDouble(readString());
        }
    }
}
