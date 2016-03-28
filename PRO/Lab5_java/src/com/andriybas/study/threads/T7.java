package com.andriybas.study.threads;

import com.andriybas.study.Data;

public class T7 extends AbstractThread {

    public T7(Data data, int number) {
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
