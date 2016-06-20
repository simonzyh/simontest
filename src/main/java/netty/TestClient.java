package netty;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class TestClient {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            // Thread.sleep(5000L);
            new Thread() {
                public void run() {
                    try {
                        //创建客户端channel的辅助类,发起connection请求
                        ClientBootstrap bootstrap = new ClientBootstrap(
                                new NioClientSocketChannelFactory(
                                        Executors.newCachedThreadPool(),
                                        Executors.newCachedThreadPool()));
                        final TimeClientHandler3 handler = new TimeClientHandler3();
                        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
                            public ChannelPipeline getPipeline() {
                                ChannelPipeline pipeline = Channels.pipeline();
                                pipeline.addLast("decoder", new TimeDecoder());
                                pipeline.addLast("encoder", new TimeEncoder());
                                pipeline.addLast("handler", handler);
                                return pipeline;
                            }
                        });
                        //创建无连接传输channel的辅助类(UDP),包括client和server
                        ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", 9999));
                        // future.getChannel().getCloseFuture().awaitUninterruptibly();
                        // bootstrap.releaseExternalResources();

                        //sleep5s 不然tcp连接还没就绪
                        Thread.sleep(5000L);

                        for (int j = 0; j < 10; j++) {
                            Thread.sleep(300L);
                            Persons person = new Persons(j + "testclient", 1, 1.1);
                            future.getChannel().write(person);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }.start();
        }


    }

}
