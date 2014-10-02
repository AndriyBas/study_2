package lab3;

/**
 * Created by andriybas on 10/3/14.
 */
public class Data {
    public static void setN(int N) {
        Vector.setN(N);
        Matrix.setN(N);
    }

    public static int f1(Vector A, Vector B, Vector C, Vector D, Matrix MA, Matrix MZ) {
        return (A.add(B)).mul(C.add(D.mul(MA.mul(MZ))));
    }

    public static Matrix f2(Matrix MA, Matrix MB, Matrix MC) {
        return (MB.mul(MC).mul(MA.min()));
    }

    public static Vector f3(Vector C, Vector D, Vector P, Matrix MA, Matrix MB, Matrix ME, Matrix MX) {
        return MA.mul(D).add(MB.mul(C)).add(MX.mul(ME).mul(P));
    }

    public static void output(Vector v) {
        if(Vector.getN() <= 8)
            System.out.println(v);
        else
            System.out.println("Vector " + Vector.getN());
    }

    public static void output(Matrix m) {
        if(Matrix.getN() <= 8)
            System.out.println(m);
        else
            System.out.println("Matrix " + Matrix.getN() + "x" + Matrix.getN());
    }

}
