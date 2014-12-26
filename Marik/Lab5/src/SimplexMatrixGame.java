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
public class SimplexMatrixGame {

    static LinearObjectiveFunction f;
    static ArrayList<LinearConstraint> constraints;

    static double[] FUNCTION_COEFFICIENTS = {1, 1, 1, 1};
    static double[][] CONSTRAINTS = {
            {2, 3, 4, 7},
            {6, 5, 1, 4},
            {7, 2, 8, 1}
    };

    static double[] CONSTRAINT_BOUND = {1, 1, 1};


    //    B
    static LinearObjectiveFunction f_B;
    static ArrayList<LinearConstraint> constraints_B;


    static double[] FUNCTION_COEFFICIENTS_B = {1, 1, 1};
    static double[][] CONSTRAINTS_B = {
            {2, 6, 7},
            {3, 5, 2},
            {4, 1, 8},
            {7, 4, 1}
    };

    static double[] CONSTRAINT_BOUND_B = {1, 1, 1, 1};


    public static void init_A() {

        assert CONSTRAINTS.length == CONSTRAINT_BOUND.length;
        assert CONSTRAINTS.length > 0;
        assert CONSTRAINTS[0].length == FUNCTION_COEFFICIENTS.length;


        f = new LinearObjectiveFunction(FUNCTION_COEFFICIENTS, 0);
        constraints = new ArrayList<>();
        for (int i = 0; i < CONSTRAINTS.length; i++) {
            constraints.add(new LinearConstraint(CONSTRAINTS[i], Relationship.LEQ, CONSTRAINT_BOUND[i]));
        }
//        constraints.add(new LinearConstraint(CONSTRAINTS[0], Relationship.LEQ, CONSTRAINT_BOUND[0]));
//        constraints.add(new LinearConstraint(CONSTRAINTS[1], Relationship.LEQ, CONSTRAINT_BOUND[1]));
//        constraints.add(new LinearConstraint(CONSTRAINTS[2], Relationship.LEQ, CONSTRAINT_BOUND[2]));
    }

    public static void init_B() {

        assert CONSTRAINTS_B.length == CONSTRAINT_BOUND_B.length;
        assert CONSTRAINTS_B.length > 0;
        assert CONSTRAINTS_B[0].length == FUNCTION_COEFFICIENTS_B.length;


        f_B = new LinearObjectiveFunction(FUNCTION_COEFFICIENTS_B, 0);
        constraints_B = new ArrayList<>();
        for (int i = 0; i < CONSTRAINTS_B.length; i++) {
            constraints_B.add(new LinearConstraint(CONSTRAINTS_B[i], Relationship.GEQ, CONSTRAINT_BOUND_B[i]));
        }
    }

    public static void main(String[] args) {

        init_A();
        init_B();


        solve_A();
        System.out.println("");
        solve_B();
    }

    public static void solve_A() {
        // Gomory's algorithm or branch-and-cut algorithm to get integer results

        SimplexSolver solver = new SimplexSolver();
        PointValuePair solution = solver.optimize(new MaxIter(100), f, new LinearConstraintSet(constraints),
                GoalType.MAXIMIZE, new NonNegativeConstraint(true));

//        System.out.println();
        double res[] = solution.getPoint();
        for (int i = 0; i < res.length; i++) {
            System.out.println("x[" + (i + 1) + "] = " + res[i]);
        }
//        double sum = 0.0;
//        for(int i = 0; i < res.length; i++) {
//            sum += res[i];
//        }
//        System.out.println("sum = " + sum);

        double v = f.value(res);
        System.out.println("F_A = " + v);
        System.out.println("Win = " + (1 / v));
        for (int i = 0; i < res.length; i++) {
            System.out.printf("p[%d] = %.4f\n", (i + 1), res[i] / v);
        }
        System.out.println();
    }

    public static void solve_B() {

        SimplexSolver solver = new SimplexSolver();
        PointValuePair solution = solver.optimize(new MaxIter(100), f_B, new LinearConstraintSet(constraints_B),
                GoalType.MINIMIZE, new NonNegativeConstraint(true));

//        System.out.println();
        double res[] = solution.getPoint();
        for (int i = 0; i < res.length; i++) {
            System.out.println("y[" + (i + 1) + "] = " + res[i]);
        }
//        double sum = 0.0;
//        for(int i = 0; i < res.length; i++) {
//            sum += res[i];
//        }
//        System.out.println("sum = " + sum);

        double v = f_B.value(res);
        System.out.println("F_B = " + v);
        System.out.println("Loss = " + (1 / v));
        for (int i = 0; i < res.length; i++) {
            System.out.printf("p[%d] = %.4f\n", (i + 1), res[i] / v);
        }
        System.out.println();
        System.out.println();
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
