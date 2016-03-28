package com.andriybas.study;
/**
 * PPCS Lab 5
 * Andriy Bas
 * IO-22
 * MA = max(Z) * MO + min(Z) * (ME * MT)
 */

import com.andriybas.study.threads.*;

public class Main {

    private final static int N = 8;

    public static void main(String[] args) {
        final Data data = new Data(N);
        final int threadCount = Data.P;
        AbstractThread[] threads = new AbstractThread[threadCount];
        threads[0] = new T1(data, 0);
        threads[1] = new T2(data, 1);
        threads[2] = new T3(data, 2);
        threads[3] = new T4(data, 3);
        threads[4] = new T5(data, 4);
        threads[5] = new T6(data, 5);
        threads[6] = new T7(data, 6);
        threads[7] = new T8(data, 7);
        for (int i = 0; i < threadCount; i++) {
            threads[i].start();
        }
    }
}
