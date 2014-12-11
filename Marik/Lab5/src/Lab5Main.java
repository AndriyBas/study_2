import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;

/**
 * Function
 * x1 + 2 * x2 + 3 * x3 -> MAX
 * <p>
 * Constrains
 * x1 + 2*x3 <= 5
 * -x1 + x3 <= 2
 * x1 + x2 + x3 <= 8
 * <p>
 * Created by andriybas on 12/11/14.
 */
public class Lab5Main {

    static LinearObjectiveFunction f;
    static ArrayList<LinearConstraint> constraints;

    static double[] FUNCTION_COEFFICIENTS = {1, 2, 3};
    static double[][] CONSTRAINTS = {
            {1, 0, 2},
            {-1, 0, 1},
            {1, 1, 1}
    };

    static double[] CONSTRAINT_BOUND = {5, 2, 8};


    public static void init() {
        f = new LinearObjectiveFunction(FUNCTION_COEFFICIENTS, 0);
        constraints = new ArrayList<>();
        constraints.add(new LinearConstraint(CONSTRAINTS[0], Relationship.LEQ, CONSTRAINT_BOUND[0]));
        constraints.add(new LinearConstraint(CONSTRAINTS[1], Relationship.LEQ, CONSTRAINT_BOUND[1]));
        constraints.add(new LinearConstraint(CONSTRAINTS[2], Relationship.LEQ, CONSTRAINT_BOUND[2]));
    }

    public static void main(String[] args) {

        init();

        // Gomory's algorithm or branch-and-cut algorithm to get integer results

        SimplexSolver solver = new SimplexSolver();
        PointValuePair solution = solver.optimize(new MaxIter(100), f, new LinearConstraintSet(constraints),
                GoalType.MAXIMIZE, new NonNegativeConstraint(false));

//        System.out.println();
        double res[] = solution.getPoint();
        for (int i = 0; i < res.length; i++) {
            System.out.println("x[" + (i + 1) + "] = " + res[i]);
        }
        System.out.println("F = " + f.value(res));
        System.out.println();

        bruteForce();
    }

    public static void bruteForce() {

        final int LOWER_BOUND = 0;
        final int UPPER_BOUND = 100;

        double currentRes = Double.MIN_VALUE;
        double currentPoints[] = new double[]{-1.0, -1.0, -1.0};

        for (int i = LOWER_BOUND; i < UPPER_BOUND; i++) {
            for (int j = LOWER_BOUND; j < UPPER_BOUND; j++) {
                for (int z = LOWER_BOUND; z < UPPER_BOUND; z++) {

                    double[] vector = new double[]{i, j, z};
                    if (satisfiesAll(vector)) {
                        double val = f.value(vector);
                        if (val > currentRes) {
                            currentRes = val;
                            currentPoints = vector;
                        }
                    }
                }
            }
        }

        System.out.println("Brute force : ");
        for (int i = 0; i < currentPoints.length; i++) {
            System.out.println("x[" + (i + 1) + "] = " + currentPoints[i]);
        }
        System.out.println("F = " + f.value(currentPoints));
        System.out.println();
    }


    static boolean satisfiesAll(double[] vector) {
        for (int i = 0; i < FUNCTION_COEFFICIENTS.length; i++) {
            if (!satisfies(vector, CONSTRAINTS[i], CONSTRAINT_BOUND[i])) {
                return false;
            }
        }
        return true;
    }

    static boolean satisfies(double[] vector, double[] coefficients, double val) {
        double t = 0.0;
        for (int i = 0; i < vector.length; i++) {
            t += vector[i] * coefficients[i];
        }
        return t <= val;
    }
}
