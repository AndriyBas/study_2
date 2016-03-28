package com.andriybas.study.threads;

import com.andriybas.study.Data;

public class T5 extends AbstractThread {

    public T5(Data data, int number) {
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
