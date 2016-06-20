package io;

import java.io.OutputStream;
import java.net.Socket;

public class TestSocket {
    static boolean isContinue = true;

    public static void main(String[] args) {
        int i = 0;
        while (isContinue) {
            System.out.println(i++);
            if (i > 500) break;
            new Thread() {
                public void run() {
                    try {
                        Socket socket = new Socket("127.0.0.1", 9001);
                        OutputStream out = socket.getOutputStream();

                        while (true) {
                            out.write("hello".getBytes());
                            out.flush();
                            Thread.sleep(100);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        isContinue = false;
                    }


                }

            }.start();


        }
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
