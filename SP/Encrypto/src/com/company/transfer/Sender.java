package com.company.transfer;

import java.io.*;
import java.net.Socket;

/**
 * Created by andriybas on 11/30/14.
 */
public class Sender {


    public void send(final File file) {


        Socket sock = null;
        OutputStream os = null;
        FileInputStream fis = null;

        try {
            sock = new Socket(Const.LOCALHOST, Const.SOCKET_PORT);

            os = sock.getOutputStream();
            fis = new FileInputStream(file);


            byte[] buffer = new byte[4096];
            int n;
            while ((n = fis.read(buffer)) > 0) {
                os.write(buffer, 0, n);
            }

            os.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                if (os != null) os.close();
                if (fis != null) fis.close();

                if (sock != null) {
                    sock.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
