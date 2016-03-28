package com.andriybas.study.threads;

import com.andriybas.study.Data;

public class T6 extends AbstractThread {

    public T6(Data data, int number) {
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
