package netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import test.DateTools;

import java.util.Date;

public class TimeClientHandler3 extends SimpleChannelHandler {

    private ChannelHandlerContext ctx;


    @Override
    public synchronized void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        Persons person = (Persons) e.getMessage();
        System.out.println(DateTools.format(new Date(), "yyyy-MM-dd HH:mm:ss:SSS") + "客户端端接收到服务器返回的数据 " + person.getName());
    }

    @Override
    public synchronized void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
            throws Exception {
        this.ctx = ctx;
    }


}
  