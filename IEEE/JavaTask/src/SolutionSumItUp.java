import java.io.*;
import java.util.StringTokenizer;

public class SolutionSumItUp {

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

            int n = in.nextInt();
            final int mod = 1000000007;
            long res = 0;
            for (int i = 0; i < n; i++) {
                res += in.nextInt();
                res %= mod;
            }

            long mul = 1;
            int q = in.nextInt();
            for (int i = 0; i < q; i++) {
                mul *= 2;
                mul %= mod;
            }

            res = res * mul % mod;
            System.out.println(res);

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

        new SolutionSumItUp().solveProblem();
    }

}



