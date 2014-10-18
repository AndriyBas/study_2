import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Solution {

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


        class Station implements Comparable<Station> {

            int price;
            int distance;

            Station(int price, int distance) {
                this.price = price;
                this.distance = distance;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Station station = (Station) o;

                if (distance != station.distance) return false;
                if (price != station.price) return false;

                return true;
            }

            @Override
            public int hashCode() {
                int result = price;
                result = 31 * result + distance;
                return result;
            }

            @Override
            public int compareTo(Station o) {
                return Integer.compare(this.price, o.price);
            }
        }

        @SuppressWarnings("deprecation")
        public void solve() throws IOException {

            final int M = in.nextInt();
            for (int i = 0; i < M; i++) {
                runCase();
            }

        }

        void runCase() throws IOException {

            final int N = in.nextInt();
            final int F = in.nextInt();
            final int T = in.nextInt();
            final int L = in.nextInt();

//            Station[] sByPrice = new Station[N];
            Station[] sByDistance = new Station[N];

            for (int i = 0; i < N; i++) {
                int d = in.nextInt();
                int p = in.nextInt();
                sByDistance[i] = new Station(p, d);
            }

            Arrays.sort(sByDistance, new Comparator<Station>() {
                @Override
                public int compare(Station o1, Station o2) {
                    return Integer.compare(o1.distance, o2.distance);
                }
            });

            int t = T - sByDistance[0].distance;

            if (T < sByDistance[0].distance) {
                System.out.println("-1");
                return;
            }

            TreeMap<Integer, Station> reachable = new TreeMap<Integer, Station>();
            reachable.put(sByDistance[0].price, sByDistance[0]);


            for (int i = 0; i < N; i++) {
                Station s = sByDistance[i];
                int j = i + 1;

                while (j < N && sByDistance[j].distance - s.distance <= F) {
                    Station sNext = sByDistance[j];
                    if (reachable.get(sNext.price) == null /*|| reachable.get(sNext.price).price > sNext.price */) {
                        reachable.put(sNext.price, sNext);
                    }

                    j++;
                }


                if (reachable.get(s.price) != null && reachable.get(s.price).distance == s.distance) {
                }

            }


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

        new Solution().solveProblem();
    }

}



