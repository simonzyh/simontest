package ssd.io.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class TestSelector {
    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 30; i++) {
            Selector selector = Selector.open();
            SocketChannel channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress("127.0.0.1", 9000));
            channel.register(selector, SelectionKey.OP_READ);
        }
        Thread.sleep(300000);
    }
}
