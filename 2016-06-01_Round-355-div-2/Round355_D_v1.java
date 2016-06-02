import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Round355_D_v1 {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        // chest type -> list of rooms with given chest type (~ graph adjacency list)
        Map<Integer, List<Pair>> pNodes = new HashMap<>();

        int n = FastReader.readInt();
        int m = FastReader.readInt();
        int p = FastReader.readInt();
//        int[][] aij = new int[n + 1][]; // [0][x] and [x][0] won't be used
        for (int i = 1 ; i <= n ; i++) {
//            aij[i] = new int[m + 1];
            for (int j = 1 ; j <= m ; j++) {
                int type = FastReader.readInt();
//                aij[i][j] = type;
                List<Pair> typeNodes = pNodes.get(type);
                if (typeNodes == null) {
                    typeNodes = new ArrayList<>();
                    pNodes.put(type, typeNodes);
                }
                typeNodes.add(new Pair(i, j));
            }
        }


        int[][] dist = new int[n + 1][m + 1]; // [0][x] and [x][0] won't be used

        // starting position
        dist[1][1] = 0;
        pNodes.put(0, Arrays.asList(new Pair(1,1)));

        // every other position
        for (int pi = 1 ; pi <= p ; pi++) {
            List<Pair> prevNodes = pNodes.get(pi - 1);
            for (Pair curNode : pNodes.get(pi)) {
                dist[curNode.r][curNode.c] = bestDist(dist, prevNodes, curNode);
            }
        }

        int solution = pNodes.get(p).stream()
                .mapToInt(node -> dist[node.r][node.c])
                .min().orElse(0);

        System.out.println(solution);
    }

    private static int bestDist(int[][] distSoFar, List<Pair> froms, Pair to) {
        int bestDist = Integer.MAX_VALUE;
        for (Pair from : froms) {
            int dist = distSoFar[from.r][from.c]
                    + Math.abs(from.r - to.r)
                    + Math.abs(from.c - to.c);
            bestDist = Math.min(bestDist, dist);
        }
        return bestDist;
    }

    static class Pair {
        final int r;
        final int c;
        public Pair(int r, int c) {
            this.r = r;
            this.c = c;
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