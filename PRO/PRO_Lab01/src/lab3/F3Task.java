package lab3;

/**
 * Created by andriybas on 10/3/14.
 */
public class F3Task  implements Runnable {
    private Vector C, D, P;
    private Matrix MA, MB, ME, MX;
    @Override
    public void run() {
        System.out.println("F3 starting...");
        C = new Vector(1);
        D = new Vector(1);
        P = new Vector(1);
        MA = new Matrix(1);
        MB = new Matrix(1);
        ME = new Matrix(1);
        MX = new Matrix(1);

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Data.output(Data.f3(C, D, P, MA, MB, ME, MX));
        System.out.println("F3 finished.");
    }
}
