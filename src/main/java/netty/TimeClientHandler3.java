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
     }

    @Override
    public synchronized void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
            throws Exception {
        this.ctx = ctx;
    }


}
  