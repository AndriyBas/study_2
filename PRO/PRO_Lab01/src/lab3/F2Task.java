package lab3;

public class F2Task implements Runnable {

    int v;
    private Matrix MA, MB, MC;

    @Override
    public void run() {
        System.out.println("F2 started...");
        MA = new Matrix(1);
        MB = new Matrix(1);
        MC = new Matrix(1);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        v = Data.f2(MA, MB, MC);
        System.out.println("v = " + v);
        System.out.println("F2 finished.");
    }
}
