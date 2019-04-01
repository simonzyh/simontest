package ssd.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class AsyncServer implements Runnable {
    //private ByteBuffer r_buff = ByteBuffer.allocate(1024);
    //private ByteBuffer w_buff = ByteBuffer.allocate(1024);
    private static int port = 10001;

    public AsyncServer() {
        new Thread(this).start();
    }

    public static void main(String args[]) {

        new AsyncServer();
    }

    public void run() {
        try {
            // 生成一个侦听端
            ServerSocketChannel ssc = ServerSocketChannel.open();
            // 将侦听端设为异步方式
            ssc.configureBlocking(false);
            // 生成一个信号监视器
            Selector s = Selector.open();
            // 侦听端绑定到一个端口
            ssc.socket().bind(new InetSocketAddress(port));
            // 设置侦听端所选的异步信号OP_ACCEPT
            ssc.register(s, SelectionKey.OP_ACCEPT);

            System.out.println("echo server has been set up ......");

            while (true) {
                int n = s.select();
                if (n == 0) {// 没有指定的I/O事件发生
                    continue;
                }
                Iterator it = s.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    if (key.isAcceptable()) {// 侦听端信号触发
                        //System.out.println("连接到达:"+System.currentTimeMillis());
                        ServerSocketChannel server = (ServerSocketChannel) key
                                .channel();
                        // 接受一个新的连接
                        SocketChannel sc = server.accept();
                        sc.configureBlocking(false);
                        // 设置该socket的异步信号OP_READ:当socket可读时，
                        // 触发函数DealwithData();
                        sc.register(s, SelectionKey.OP_READ);
                    }
                    if (key.isReadable()) {// 某socket可读信号

                        DealwithData(key);
                    }
                    it.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DealwithData(SelectionKey key) throws IOException {
        ByteBuffer r_buff = ByteBuffer.allocate(1024);
        int count;
        // 由key获取指定socketchannel的引用
        SocketChannel sc = (SocketChannel) key.channel();
        r_buff.clear();
        // 读取数据到r_buff
        while ((count = sc.read(r_buff)) > 0)
            ;
        System.out.println("数据到达:" + System.currentTimeMillis() + " " + new String(r_buff.array()));
        // 确保r_buff可读
        r_buff.flip();


        // 将数据返回给客户端
        EchoToClient(sc, r_buff);

        r_buff.clear();
        try {
            Thread.sleep(1000 * 10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void EchoToClient(SocketChannel sc, ByteBuffer w_buff) throws IOException {
        while (w_buff.hasRemaining())
            sc.write(w_buff);
    }
}