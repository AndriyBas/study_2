import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class SolutionRoadTrip {

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

        class Station {

            Station(int pricePerFuel, int distance) {
                this.pricePerFuel = pricePerFuel;
                this.distance = distance;
            }

            Station(int pricePerFuel, int distance, int index) {
                this.pricePerFuel = pricePerFuel;
                this.distance = distance;
                this.index = index;
            }

            int pricePerFuel;
            int distance;

            int index;
            boolean visited = false;

            int takeHere = 0;
        }

        @SuppressWarnings("deprecation")
        public void solve() throws IOException {

            final int N = in.nextInt();
            final int F = in.nextInt();
            final int T = in.nextInt();
            final int L = in.nextInt();

            Station[] sByPrice = new Station[N];
            Station[] sByDistance = new Station[N];

            for (int i = 0; i < N; i++) {
                int d = in.nextInt();
                int p = in.nextInt();
                sByPrice[i] = new Station(p, d);
            }

            Arrays.sort(sByDistance, new Comparator<Station>() {
                @Override
                public int compare(Station o1, Station o2) {
                    return Integer.compare(o1.distance, o2.distance);
                }
            });

            for (int i = 0; i < N; i++) {
                Station sD = sByDistance[i];
                sD.index = i;
                sByPrice[i] = new Station(sD.pricePerFuel, sD.distance, i);
            }


            Arrays.sort(sByPrice, new Comparator<Station>() {
                @Override
                public int compare(Station o1, Station o2) {
                    return Integer.compare(o1.pricePerFuel, o2.pricePerFuel);
                }
            });


            // initial state
            sByDistance[0].takeHere = T - sByDistance[0].distance;

            for (int i = 0; i < N; i++) {

                Station s = sByPrice[i];
                s.visited = true;

                if (s.distance >= L)
                    continue;

                if (s.takeHere == 0) {
                    int toEnd = L - s.distance;
                    if (toEnd > F) {
                        s.takeHere = F;

                        // TODO : if cannot reach next station
                        if (s.index < N - 1) {
                            Station sNext = sByDistance[s.index + 1];
                            sNext.takeHere += (F - (sNext.distance - s.distance));
                        }

                    } else {
                        s.takeHere = toEnd;
                    }

                    if (s.index > 0) {
                        Station sPrev = sByDistance[s.index - 1];
                        sPrev.takeHere -= (s.distance - sPrev.distance);
                    }
                } else if (s.takeHere < 0) {

                    s.takeHere = -s.takeHere;

                    // TODO : if cannot reach previous station
                    if (s.index > 0) {
                        Station sPrev = sByDistance[s.index - 1];
                        sPrev.takeHere -= (s.distance - sPrev.distance);
                    }

                } else { // s.takeHere > 0

                    int toEnd = L - s.distance;
                    int here = F - s.takeHere;

                    if (toEnd > F) {
                        s.takeHere = here;

                        // TODO : if cannot reach next station
                        if (s.index < N - 1) {
                            Station sNext = sByDistance[s.index + 1];
                            sNext.takeHere += (F - (sNext.distance - s.distance));
                        }

                    } else {
                        s.takeHere = toEnd - s.takeHere;
                    }
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

        new SolutionRoadTrip().solveProblem();
    }

}



