package lab3;

public class F3Task implements Runnable {

    private Vector B;
    private Matrix MA, MB, MD;

    @Override
    public void run() {
        System.out.println("F3 started...");
        B = new Vector(1);
        MA = new Matrix(1);
        MB = new Matrix(1);
        MD = new Matrix(1);

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        B = Data.f3(B, MA, MB, MD);
        System.out.println("B = " + B.toString());
        System.out.println("F3 finished.");
    }
}
