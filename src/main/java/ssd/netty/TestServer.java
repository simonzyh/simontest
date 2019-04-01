package ssd.netty;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class TestServer {

    public static void main(String[] args) throws InterruptedException {

        final TimeServerHandler3 handler3 = new TimeServerHandler3();


        ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool(), 2);
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new TimeDecoder());
                pipeline.addLast("encoder", new TimeEncoder());
                pipeline.addLast("handler", handler3);
                return pipeline;
                //return Channels.pipeline(new TimeEncoder(), new TimeServerHandler3());
            }
        });
        synchronized (bootstrap) {
            bootstrap.setOption("child.tcpNoDelay", true);
            bootstrap.setOption("child.keepAlive", true);
            //bootstrap.wait();
        }

        final Channel channel = bootstrap.bind(new InetSocketAddress("localhost", 9999));
        //起一个新线程处理客户端发来的数据 这里只模拟一个客户端的情况。
        new Thread() {
            public void run() {
                try {

                    while (true) {
                        Persons persons = TimeServerHandler3.queue.take();
                        System.out.println("收到客户端请求+" + persons.getName());

                        channel.write(persons);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

}
