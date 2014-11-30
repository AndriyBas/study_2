package com.company.transfer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by andriybas on 11/30/14.
 */
public class Receiver implements Runnable {


    @Override
    public void run() {

        FileOutputStream fos = null;

        ServerSocket serverSock = null;
        Socket sock = null;

        try {
            serverSock = new ServerSocket(Const.SOCKET_PORT);
            while (true) {
                try {
                    sock = serverSock.accept();

                    InputStream is = sock.getInputStream();
                    fos = new FileOutputStream(Const.FILE_TO_RECEIVE);

                    byte[] buffer = new byte[4096];
                    int n;
                    while ((n = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, n);
                    }

                    fos.flush();

                    System.out.println("File " + Const.FILE_TO_RECEIVE
                            + " downloaded");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) fos.close();
//                    if (bos != null) bos.close();
                    if (sock != null) sock.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) fos.close();
                if (sock != null) sock.close();

                if (serverSock != null) serverSock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
