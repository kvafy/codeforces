import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

// Note: Time restriction is not met

public class Round356_div1_D {
    private static int n;
    private static int m;
    private static int[][] dist;
    private static Map<Integer, List<Integer>>[] citiesByDist;

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in /*new FileInputStream("../data-graph-400.txt")*/);
        PrintWriter out = new PrintWriter(System.out);

        n = in.readInt();
        m = in.readInt();

        dist = new int[n][n];
        for (int i = 0 ; i < n ; i++) {
            Arrays.fill(dist[i], n); // all cities should be connected => n-1 is the maximum distance possible
            dist[i][i] = 0;
        }

        for (int i = 0 ; i < m ; i++) {
            int u = in.readInt() - 1;
            int v = in.readInt() - 1;
            dist[u][v] = dist[v][u] = 1;
        }

        // Floyd-Warshall shortest path
        for (int k = 0 ; k < n ; k++) {
            for (int i = 0 ; i < n ; i++) {
                for (int j = 0 ; j < n ; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        citiesByDist = (Map<Integer, List<Integer>>[]) new HashMap[n];
        for (int i = 0 ; i < n ; i++) {
            citiesByDist[i] = new HashMap<>();
            for (int j = 0 ; j < n ; j++) {
                List<Integer> dCities = citiesByDist[i].get(dist[i][j]);
                if (dCities == null) {
                    dCities = new ArrayList<>();
                    citiesByDist[i].put(dist[i][j], dCities);
                }
                dCities.add(j);
            }
        }


        // solve

        // initially Limak has equal probability 1/n of being in each city
        double[] pLimak = new double[n];
        for (int i = 0 ; i < n ; i++) {
            pLimak[i] = 1.0 / n;
        }

        double solution = pCatch(pLimak, 1);

        out.println(solution);
        out.flush();
    }

    private static double pCatch(double[] pLimak, int movesLeft) {
        double maxProb = 0.0;

        // find out in which city it makes most sense to use the BCD (maximize probability of catching Limak)
        for (int bcdCity = 0 ; bcdCity < n ; bcdCity++) {
            double prob = 0.0;
            // consider each group of cities with same distance from bcdCity
            for (Collection<Integer> cityGroup : citiesByDist[bcdCity].values()) {
                double pGroup = cityGroup.stream()
                        .mapToDouble(c -> pLimak[c])
                        .sum();

                if (pGroup == 0) {
                    continue;
                }

                // I can either try to catch Limak now in the most probable city
                double pCatchNow = cityGroup.stream()
                        .mapToDouble(c -> pLimak[c] / pGroup)
                        .max().orElse(0);
                // ... or let him make a move and then try to catch him
                double pCatchNext = 0;
                if (movesLeft > 0) {
                    pCatchNext = pCatch(pLimakNext(pLimak, cityGroup), movesLeft - 1);
                }

                prob += pGroup * Math.max(pCatchNow, pCatchNext);
            }
            maxProb = Math.max(maxProb, prob);
        }

        return maxProb;
    }

    private static double[] pLimakNext(double[] pLimak, Collection<Integer> currentCities) {
        // Given probability distribution of Limak over all cities, compute the probability
        // distribution of Limak being in one of the cities adjacent to current cities.
        double[] pLimakNext = new double[n];
        double normalizer = 0;

        for (int curCity : currentCities) {
            List<Integer> adj = citiesByDist[curCity].get(1);
            double delta = pLimak[curCity] * (1.0 / adj.size());
            for (int nextCity : adj) {
                pLimakNext[nextCity] += delta;
                normalizer += delta;
            }
        }

        for (int i = 0 ; i < n ; i++) {
            pLimakNext[i] /= normalizer;
        }

        return pLimakNext;
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
