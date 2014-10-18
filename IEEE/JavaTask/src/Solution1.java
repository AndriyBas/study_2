import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution1 {

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

            System.out.println(String.format("%-3d", 2));

            int n = in.nextInt();
            int[][] a = new int[n + 1][n + 1];
            int[][] sum = new int[n + 1][n + 1];
            int[][] res = new int[n + 1][n + 1];

            for (int i = 0; i <= n; i++) {
                Arrays.fill(a[i], 0);
                Arrays.fill(sum[i], 0);
                Arrays.fill(res[i], 0);
            }

            for (int i = 1; i <= n; i++)
                for (int j = 1; j <= n; j++)
                    a[i][j] = in.nextInt();

            for (int i = 1; i <= n; i++)
                for (int j = 0; j <= n; j++)
                    sum[i][j] = sum[i - 1][j] + a[i][j];

            for (int i = 1; i <= n; i++)
                res[i][1] = a[i][1];

            for (int j = 2; j <= n; j++) {
                for (int i = 1; i <= n; i++) {
                    int minCurrent = Integer.MAX_VALUE;
                    for (int z = 1; z <= n; z++) {
                        int d = Math.abs(sum[z][j] - sum[i][j]) + a[Math.min(i, z)][j];
                        int current = res[z][j - 1] + d;
                        if (minCurrent > current)
                            minCurrent = current;
                    }
                    res[i][j] = minCurrent;
                }
            }

            int ans = res[1][n];
            for (int i = 2; i <= n; i++)
                if (res[i][n] < ans)
                    ans = res[i][n];

            System.out.println(ans);
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

        new Solution1().solveProblem();
    }

}



