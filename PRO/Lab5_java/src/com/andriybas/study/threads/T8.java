package com.andriybas.study.threads;

import com.andriybas.study.Data;

import java.util.Arrays;

public class T8 extends AbstractThread {

    public T8(Data data, int number) {
        super(data, number);
    }

    @Override
    void begin() {
        data.MA = new int[data.N][data.N];
        data.Z = new int[data.N];
        Arrays.fill(data.Z, DEFAULT_VALUE);
        data.MO = new int[data.N][data.N];
        for (int i = 0; i < data.N; i++) {
            Arrays.fill(data.MO[i], DEFAULT_VALUE);
            Arrays.fill(data.MA[i], 0);
        }
        data.signalInput();
        System.out.println("T" + number + " input finished...");
    }

    @Override
    void end() {
        data.waitCalc();
        if (data.N <= 8) {
            for (int i = 0; i < data.N; i++) {
                for (int j = 0; j < data.N; j++) {
                    System.out.print(data.MA[i][j] + " ");
                }
                System.out.println();
            }
        }
    }
}
