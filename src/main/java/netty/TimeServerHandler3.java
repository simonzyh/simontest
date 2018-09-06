package netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import test.DateTools;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 用POJO代替ChannelBuffer
 */

public class TimeServerHandler3 extends SimpleChannelHandler {

    //server 收到的消息暂存队列
    public static final BlockingQueue<Persons> queue = new ArrayBlockingQueue<Persons>(10000);

    private ChannelHandlerContext ctx;

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
            throws Exception {
        System.out.println("服务端接收到连接");
        this.ctx = ctx;

    }

    /**
     * 收到数据
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        Persons person = (Persons) e.getMessage();
        queue.add(person);

    }

    //发送消息到客户端
    public void sendMessage(Persons person) {
        person.setName("server 回显 " + person.getName());
        ctx.getChannel().write(person);

    }
}   
