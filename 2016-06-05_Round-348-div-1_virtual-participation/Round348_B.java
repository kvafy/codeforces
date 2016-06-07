import java.io.*;
import java.util.*;

public class Round348_B {

    public static void main(String[] args) throws IOException {
        FastReader_v2 in = new FastReader_v2(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.readInt();
        int q = in.readInt();

        long offsetSum = 0;
        long firstFwSteps = 0;
        long firstBkSteps = 0;

        boolean firstOnOddPos = true;
        for (int i = 0 ; i < q ; i++) {
            int cmd = in.readInt();

            if (cmd == 1) {
                int offsetDelta = (in.readInt() + n) % n;
                offsetSum = (offsetSum + offsetDelta) % n;
                if ((offsetDelta & 0x1) == 1) {
                    firstOnOddPos = !firstOnOddPos;
                }
            } else {
                if (firstOnOddPos) {
                    firstFwSteps++;
                } else {
                    firstBkSteps++;
                }
                firstOnOddPos = !firstOnOddPos;
            }
        }


        // print solution

        for (int g = 0 ; g < n ; g++) {
            int bq;
            if ((g & 0x1) == 0 && firstOnOddPos  ||  (g & 0x1) == 1 && !firstOnOddPos) {
                bq = (int) ((g - (offsetSum + firstFwSteps - firstBkSteps)) % n);
            } else {
                bq = (int) ((g - (offsetSum + firstBkSteps - firstFwSteps)) % n);
            }

            if (bq < 0) {
                bq += n;
            }

            bq++; // +1 ~ transform from 0-based positions to 1-based positions

            // the girl no. 'g' will be with boy no 'b'
            out.print(bq);
            out.print(' ');
        }

        out.flush();

//        int[] finalBoyOfGirl = new int[n];
//        for (int i = 0 ; i < n ; i++) {
//            int iBoyPos;
//            if ((i & 0x1) == 0) {
//                iBoyPos = (int) ((i + offsetSum + firstFwSteps - firstBkSteps) % n);
//            } else {
//                iBoyPos = (int) ((i + offsetSum + firstBkSteps - firstFwSteps) % n);
//            }
//
//            if (iBoyPos < 0) {
//                iBoyPos += n;
//            }
//
//            finalBoyOfGirl[iBoyPos] = i + 1; // +1 ~ transform from 0-based positions to 1-based positions
//        }
//
//        for (int boyOfGirl : finalBoyOfGirl) {
//            System.out.printf("%d ", boyOfGirl);
//        }
    }

    // inspired by https://www.cpe.ku.ac.th/~jim/java-io.html
    /*static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        static String readString() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                String line = reader.readLine();
//                if (line == null) {
//                    throw new IllegalStateException("No more input");
//                }
                tokenizer = new StringTokenizer(line);
            }
            return tokenizer.nextToken();
        }

        static int readInt() throws IOException {
            return Integer.parseInt(readString());
        }
    }*/

    static class FastReader_v2 {
        private final InputStream stream;
        private int current;
        private int size;
        private byte[] buffer = new byte[1024 * 8];

        public FastReader_v2(InputStream stream) {
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
                c = read();
            }
            do {
                if (c < '0' || c > '9') {
                    throw new IllegalStateException();
                }
                abs = 10 * abs + (c - '0');
                c = read();
            } while (!isEmpty(c));
            return sign * abs;
        }

        public char readChar() {
            return (char) readNonEmpty();
        }

        public String readString() {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = read();
            } while (isEmpty(c));
            do {
                sb.append((char) c);
                c = read();
            } while (!isEmpty(c));
            return sb.toString();
        }

        private int read() {
            try {
                if (current >= size) {
                    current = 0;
                    size = stream.read(buffer);
                    if (size < 0) {
                        return -1;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to read next byte", e);
            }
            return buffer[current++];
        }

        private int readNonEmpty() {
            int result;
            do {
                result = read();
            } while (isEmpty(result));
            return result;
        }

        private static boolean isEmpty(int c) {
            return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == -1;
        }
    }
}
