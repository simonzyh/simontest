package ssd.io.netty.handler;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class HelloClientHandler extends SimpleChannelHandler {
    static Integer count = 0;
    public boolean isConnection = false;
    private ChannelStateEvent e;

    /**
     * 当绑定到服务端的时候触发，打印"Hello world, I'm client."
     *
     * @alia OneCoder
     * @author lihzh
     */
/*	@Override
    public void channelConnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) {
		System.out.println("Hello world, I'm client.");
		String msg = "Hello, I'm client.";
	 
		ChannelBuffer buffer = ChannelBuffers.buffer(msg.length());
		buffer.writeBytes(msg.getBytes());
		e.getChannel().write(buffer);
	 
	 
		 
	}*/
    /*
     * 当绑定到服务端的时候触发，给服务端发消息。
     *
     * @author lihzh
     * @alia OneCoder
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        this.e = e;
        System.out.println("连接成功");
        isConnection = true;
        // 向服务端发送 Object 信息
        //sendObject(e.getChannel());
    }

    /**
     * 当接受到消息的时候触发
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        Object obj = e.getMessage();

        Command command = (Command) obj;
        // 打印看看是不是我们刚才传过来的那个


    }

    /**
     * 发送 Object
     *
     * @param
     * @author lihzh
     * @alia OneCoder
     */
    public void sendObject(String msg) {

        Command command = new Command();
        command.setActionName(msg);

        e.getChannel().write(command);
    }
}