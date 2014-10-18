import java.io.*;
import java.util.StringTokenizer;

public class SolutionLotery {

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

            int s = in.nextInt();
            int e = in.nextInt();
            int p = in.nextInt();
            int n = in.nextInt();
            String[] ns = new String[n];
            for (int i = 0; i < n; i++)
                ns[i] = in.nextString();

            for (int i = s; i <= e; i++) {
                for (int j = 0; j < n; j++) {
                    if (String.valueOf(i).contains(ns[j])) {
                        p--;
                        if (p == 0) {
                            System.out.println(i);
                            System.exit(0);
                        }
                        break;
                    }
                }
            }
            System.out.println("DOES NOT EXIST");
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

        new SolutionLotery().solveProblem();
    }

}



