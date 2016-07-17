import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Round361_div2_A {

    static final Character[][] BOARD = {
            {'1',  '2', '3'},
            {'4',  '5', '6'},
            {'7',  '8', '9'},
            {null, '0', null},
    };

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        solve(in, out);
        out.flush();
    }


    public static void solve(FastReader in, PrintWriter out) {
        final int n = in.readInt();
        final String s = in.readString();

        List<Character> possibilities = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

        for (int[] move : getMoves(s)) {
            ArrayList<Character> filteredPosibilities = new ArrayList<>(possibilities.size());

            for (char position : possibilities) {
                Optional<Character> newPosition = doMove(position, move);
                if (newPosition.isPresent()) {
                    filteredPosibilities.add(newPosition.get());
                }
            }

            possibilities = filteredPosibilities;
        }

        out.println(possibilities.size() == 1 ? "YES" : "NO");
    }

    private static Optional<Character> doMove(char position, int[] move) {
        int[] rcNow = getCoords(position);
        int rNew = rcNow[0] + move[0];
        int cNew = rcNow[1] + move[1];
        if (0 <= rNew && rNew < 4 && 0 <= cNew && cNew < 3 && BOARD[rNew][cNew] != null) {
            return Optional.of(BOARD[rNew][cNew]);
        } else {
            return Optional.empty();
        }
    }

    private static Iterable<int[]> getMoves(String str) {
        ArrayList<int[]> moves = new ArrayList<>(str.length() - 1);
        for (int i = 1 ; i < str.length() ; i++) {
            int[] rc1 = getCoords(str.charAt(i - 1));
            int[] rc2 = getCoords(str.charAt(i));
            int deltaR = rc2[0] - rc1[0];
            int deltaC = rc2[1] - rc1[1];
            moves.add(new int[] { deltaR, deltaC });
        }
        return moves;
    }

    private static int[] getCoords(char chr) {
        for (int r = 0 ; r < 4 ; r++) {
            for (int c = 0 ; c < 3 ; c++) {
                if (BOARD[r][c] != null && BOARD[r][c] == chr) {
                    return new int[] {r, c};
                }
            }
        }
        throw new IllegalArgumentException("Unrecognized symbol " + chr);
    }

    /**
     * Custom buffered reader. Faster than Scanner and BufferedReader + StringTokenizer.
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

