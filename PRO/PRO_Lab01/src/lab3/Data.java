package lab3;

public class Data {

    public static void setN(int N) {
        Vector.setN(N);
        Matrix.setN(N);
    }

    public static int f1(Vector A, Vector B, Vector C, Matrix MA, Matrix MZ) {
        return B.mul(C) + A.mul(B) + C.mul(B.mul(MA.mul(MZ)));
    }

    public static int f2(Matrix MA, Matrix MB, Matrix MC) {
        return MA.add(MB.mul(MC)).max();
    }

    public static Vector f3(Vector B, Matrix MA, Matrix MB, Matrix MD) {
        return B.mul(MD).sort().mul(MA.mul(MB));
    }
}