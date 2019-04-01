package io.netty.handler;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class HelloServerHandler extends SimpleChannelHandler {
    /**
     * 当有客户端绑定到服务端的时候触发，打印"Hello world, I'm server."
     *
     * @alia OneCoder
     * @author lihzh
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        System.out.println("服务端接收到连接");

    }
    /*@Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
	ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
	System.out.println(buffer.toString(Charset.defaultCharset()));
	System.out.println("=============");
	} */

    /**
     * 当接受到消息的时候触发
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        Object obj = e.getMessage();

        Command command = (Command) obj;
        // 打印看看是不是我们刚才传过来的那个
        System.out.println("服务端接收到数据 " + command.getActionName() + " " + this);
        Thread.sleep(1000 * 10L);

        e.getChannel().write(command);
    }
}