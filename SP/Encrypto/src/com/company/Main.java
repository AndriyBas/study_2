package com.company;

import com.company.transfer.Receiver;
import com.company.transfer.Sender;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        // write your code here

        new Thread(new Receiver()).start();


        new Sender().send(new File("wow.txt"));
    }
}
