package io.netty;

import io.netty.handler.HelloClientHandler;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;


public class HelloNettyClient {
    public static void main(String args[]) throws InterruptedException {
        // Client 服务启动器
        final ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        final HelloClientHandler handler = new HelloClientHandler();
        // 设置一个处理服务端消息和各种消息事件的类(Handler)
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new ObjectEncoder(),
                        handler);
            }
        });

        bootstrap.connect(new InetSocketAddress("127.0.0.1", 9000));
        while (!handler.isConnection) {
            System.out.println(handler.isConnection);
        }
        ;

        for (int i = 0; i < 2; i++) {

            handler.sendObject("客户端消息发送:" + i);
            Thread.sleep(5L);
        }


    }

}