package lab3;

/**
 * Created by andriybas on 10/3/14.
 */
public class F2Task implements Runnable {
    private Matrix MA, MB, MC;
    @Override
    public void run() {
        System.out.println("F2 starting...");
        MA = new Matrix(1);
        MB = new Matrix(1);
        MC = new Matrix(1);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Data.output(Data.f2(MA, MB, MC));
        System.out.println("F2 finished.");
    }
}
