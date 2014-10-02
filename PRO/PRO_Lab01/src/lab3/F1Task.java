package lab3;

/**
 * Created by andriybas on 10/3/14.
 */
public class F1Task extends Thread {
    private Vector A, B, C, D;
    private Matrix MA, MZ;
    public void run() {
        System.out.println("F1 starting...");
        A = new Vector(1);
        B = new Vector(1);
        C = new Vector(1);
        D = new Vector(1);

        MA = new Matrix(1);
        MZ = new Matrix(1);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Data.f1(A, B, C, D, MA, MZ));
        System.out.println("F1 finished.");
    }
}
