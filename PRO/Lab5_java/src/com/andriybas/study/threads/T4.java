package com.andriybas.study.threads;

import com.andriybas.study.Data;

import java.util.Arrays;

public class T4 extends AbstractThread {

    public T4(Data data, int number) {
        super(data, number);
    }

    @Override
    void begin() {
    }

    @Override
    void end() {
        data.signalCalc();
    }
}
