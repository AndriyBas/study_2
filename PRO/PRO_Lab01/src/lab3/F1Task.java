package lab3;

public class F1Task implements Runnable {

    private Vector A, B, C;
    private Matrix MA, MZ;
    int d;

    @Override
    public void run() {
        System.out.println("F1 started...");
        A = new Vector(1);
        B = new Vector(1);
        C = new Vector(1);

        MA = new Matrix(1);
        MZ = new Matrix(1);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        d = Data.f1(A, B, C, MA, MZ);
        System.out.println("d = " + d);
        System.out.println("F1 finished.");
    }
}
