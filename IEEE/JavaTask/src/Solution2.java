import java.io.*;
import java.util.StringTokenizer;

public class Solution2 {

    class Input {
        BufferedReader bf;
        StringTokenizer st;

        Input(InputStream stream) throws IOException {
            bf = new BufferedReader(new InputStreamReader(stream));
            st = new StringTokenizer("");
        }

        int nextInt() throws IOException {
            if (!st.hasMoreTokens())
                st = new StringTokenizer(bf.readLine());

            return Integer.parseInt(st.nextToken());
        }

        long nextLong() throws IOException {
            if (!st.hasMoreTokens())
                st = new StringTokenizer(bf.readLine());
            return Long.parseLong(st.nextToken());
        }

        double nextDouble() throws IOException {
            if (!st.hasMoreTokens())
                st = new StringTokenizer(bf.readLine());
            return Double.parseDouble(st.nextToken());
        }

        void readLine() throws IOException {
            st = new StringTokenizer(bf.readLine());
        }

        String nextString() throws IOException {
            if (!st.hasMoreTokens())
                st = new StringTokenizer(bf.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException {
            return bf.readLine();
        }

        void close() throws IOException {
            bf.close();
        }
    }

    Input in;

    public class Problem {

//        class Point implements Comparable<Point> {
//            int x, y, p;
//
//            Point(int a, int b, int k) {
//                x = a;
//                y = b;
//                p = k;
//            }
//
//            @Override
//            public int compareTo(Point o) {
//                // TODO Auto-generated method stub
//                return Integer.compare(this.x, o.x);
//            }
//        }

        @SuppressWarnings("deprecation")
        public void solve() throws IOException {

            long rule = in.nextLong();
            int maxIter = in.nextInt();
            int n = in.nextInt();
            long init = in.nextLong();

            long prev = init;
            for (int k = 0; k < maxIter; k++) {

                prettyPrint(prev, k + 1, n);

                long next = 0L;

                for (int i = 0; i < n; i++) {
                    long curr = prev & (1 << i);
                    long curr3 = curr;
                    curr3 |= (prev & (1 << (i - 1)));
                    curr3 |= (prev & (1 << (i + 1)));

                    if (i > 0) curr3 >>= (i - 1);

                    // flip last 3 bits
//                    long r = curr3 & 2;
//                    r |= ((curr3 & 1) << 2);
//                    r |= ((curr3 & 4) >> 2);
//                    curr3 = 7 & (~curr3);

//                    curr3--;
                    long t1 = (1 << curr3);
                    long ff = (rule & t1);

                    if (ff > 0) {
                        next |= 1 << i;
                    }
                }

                if (next == prev)
                    break;

                prev = next;
            }
        }

        void prettyPrint(long x, int iter, int n) {
            System.out.printf("%-3d -", iter);
            for (int i = n - 1; i >= 0; i--) {
                System.out.print(((1 << i) & x) > 0 ? '*' : ' ');
            }
            System.out.println('-');
        }
    }


    public void solveProblem() throws IOException {

        boolean oj = true; //System.getProperty("ONLINE_JUDGE") != null;
        in = new Input(oj ? System.in : new FileInputStream("D:/Codes/Java/input.txt"));

        // in = new Input(System.in);
        // in = new Input(new FileInputStream("D:/Codes/Java/input.txt"));
        new Problem().solve();
        in.close();
    }

    public static void main(String[] args) throws IOException {

        new Solution2().solveProblem();
    }

}



