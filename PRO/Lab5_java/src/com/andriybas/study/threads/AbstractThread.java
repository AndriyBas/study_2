package com.andriybas.study.threads;

import com.andriybas.study.Data;
import com.andriybas.study.util.Util;

public abstract class AbstractThread extends Thread {

    public static final int DEFAULT_VALUE = 1;

    final int start;
    final int end;
    final int number;
    final Data data;

    int minZ;
    int maxZ;

    public AbstractThread(Data data, int number) {
        this.data = data;
        this.number = number + 1;
        start = number * data.H;
        end = (number + 1) * data.H;
    }

    @Override
    public void run() {
        System.out.println("T" + number + " started...");
        // Input data if exist input in this thread
        begin();
        // Wait input
        data.waitInput();
        // local min/max
        minZ = Util.findMin(data.Z, start, end);
        maxZ = Util.findMax(data.Z, start, end);
        // global min/max
        data.findGlobalMinMax(minZ, maxZ);
        System.out.println("T" + number + " found min/max...");
        // Send signal about finding min/max
        data.signalFindMinMax();
        // Wait finding min/max in other threads
        data.waitFindMinMax();
        // Copy common resources
        minZ = data.copyMinZ();
        maxZ = data.copyMaxZ();
        int[][] MTi = data.copyMT();
        // Calculating
        Util.calculate(data.N, start, end, data.MA, maxZ, data.MO, minZ, data.ME, MTi);
        // Send or wait signal if need in this thread
        end();
        System.out.println("T" + number + " finished...");
    }

    abstract void begin();
    abstract void end();

}
