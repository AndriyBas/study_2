package com.andriybas.study;

public class Data {

    public final int N;
    public final int H;
    public static final int P = 8;

    private int minZ = Integer.MAX_VALUE;
    private int maxZ = Integer.MIN_VALUE;

    private int[][] MT;

    public int[] Z;
    public int[][] MA;
    public int[][] ME;
    public int[][] MO;

    private int f1;
    private int f2;
    private int f3;

    public Data(int n) {
        this.N = n;
        this.H = N / P;
    }

    public synchronized void waitInput() {
        try {
            while (f1 != 3) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void signalInput() {
        if (++f1 == 3) {
            notifyAll();
        }
    }

    public synchronized void waitFindMinMax() {
        try {
            while (f2 != P) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void signalFindMinMax() {
        if (++f2 == P) {
            notifyAll();
        }
    }

    public synchronized void waitCalc() {
        try {
            while (f3 != P - 1) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void signalCalc() {
        if (++f3 == P - 1)
            notifyAll();
    }

    public synchronized void findGlobalMinMax(int localMin, int localMax) {
        if(minZ > localMin)
            minZ = localMin;
        if(maxZ < localMax)
            maxZ = localMax;
    }

    public synchronized void setMT(int[][] MT) {
        this.MT = MT;
    }

    public synchronized int[][] copyMT() {
        int[][] mt = new int[N][N];
        System.arraycopy(this.MT, 0, mt, 0, N);
        return mt;
    }

    public synchronized int copyMinZ() {
        return minZ;
    }

    public synchronized int copyMaxZ() {
        return maxZ;
    }
}
