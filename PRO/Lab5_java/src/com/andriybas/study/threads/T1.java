package com.andriybas.study.threads;

import com.andriybas.study.Data;

import java.util.Arrays;

public class T1 extends AbstractThread {

    public T1(Data data, int number) {
        super(data, number);
    }

    @Override
    void begin() {
        int[][] MT = new int[data.N][data.N];
        for (int i = 0; i < data.N; i++) {
            Arrays.fill(MT[i], DEFAULT_VALUE);
        }
        data.setMT(MT);
        data.signalInput();
        System.out.println("T" + number + " input finished...");
    }

    @Override
    void end() {
        data.signalCalc();
    }
}
