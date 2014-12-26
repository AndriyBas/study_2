import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by andriybas on 12/26/14.
 */
public class ResourceDistribution {


    final Function<Integer, Integer> f1 = x -> x * 10;

    final Function<Integer, Integer> f2 = x -> {
        if (x <= 3) return 12;
        else return 45;
    };

    final Function<Integer, Integer> f3 = x -> {
        if (x <= 5) return 2 * x * x;
        else return 52 + x;
    };

    final int resources = 7;

    public void solve() {

        List<Function<Integer, Integer>> functions = new ArrayList<>();
        functions.add(f1);
        functions.add(f2);
        functions.add(f3);

        int a[][] = new int[resources + 1][functions.size()];
        for (int i = 0; i < a.length; i++) {
            Arrays.fill(a[i], 0);
        }

        for (int i = 0; i <= resources; i++) {
            a[i][0] = functions.get(0).apply(i);
        }

        // for all functions (except first)
        for (int j = 1; j < functions.size(); j++) {

            // for all resources
            for (int i = 0; i <= resources; i++) {

                // for all possible number of resources for given function
                for (int k = 0; k <= i; k++) {
                    int t = functions.get(j).apply(k) + a[i - k][j - 1];
                    if (t > a[i][j]) {
                        a[i][j] = t;
                    }
                }

            }
        }

        for (int i = 0; i < a.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }


    }

    public static void main(String[] args) {
        new ResourceDistribution().solve();
    }

}
