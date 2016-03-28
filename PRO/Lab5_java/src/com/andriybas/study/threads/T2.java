package com.andriybas.study.threads;

import com.andriybas.study.Data;

import java.util.Arrays;

public class T2 extends AbstractThread {

    public T2(Data data, int number) {
        super(data, number);
    }

    @Override
    void begin() {
        int[][] ME = new int[data.N][data.N];
        for (int i = 0; i < data.N; i++) {
            Arrays.fill(ME[i], DEFAULT_VALUE);
        }
        data.ME = ME;
        data.signalInput();
        System.out.println("T" + number + " input finished...");
    }

    @Override
    void end() {
        data.signalCalc();
    }
}
