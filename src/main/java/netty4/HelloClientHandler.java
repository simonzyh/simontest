package netty4;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.time.DateUtils;
import test.DateTools;

import java.util.Date;

public class HelloClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println(DateUtil.formatDate(new Date(),"HH:mm:ss SSS")+"channelRead0 Server say : " + msg);
        DateTools.sleep(100L);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(DateUtil.formatDate(new Date(),"HH:mm:ss SSS")+"channelActive Client active ");
        DateTools.sleep(100L);

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(DateUtil.formatDate(new Date(),"HH:mm:ss SSS")+"channelInactive Client close ");
        DateTools.sleep(100L);

        super.channelInactive(ctx);
    }
}