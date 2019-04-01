package ssd.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestSocketServer {

    /**
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9000);

        while (true) {
            Socket socket = server.accept();

            new SocketThread(socket).start();
        }

    }

    static class SocketThread extends Thread {
        private Socket socket;

        public SocketThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                InputStream input = socket.getInputStream();
                byte[] in = new byte[1024];
                input.read(in);
                System.out.println("socker server" + new String(in));

                OutputStream out = socket.getOutputStream();
                out.write("hello".getBytes());
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

