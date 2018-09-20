package netty4;


import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.httpclient.util.DateUtil;
import test.DateTools;

import java.net.InetAddress;
import java.util.Date;

public class HelloServerHandler extends SimpleChannelInboundHandler<JSONObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JSONObject msg) throws Exception {
        // 收到消息直接打印输出
        System.out.println(DateUtil.formatDate(new Date(), "HH:mm:ss SSS") + "HelloServerHandler.channelRead0 " + ctx.channel().remoteAddress() + " Say : " + msg+" "+ctx.name());

        // 返回客户端消息 - 我已经接收到了你的消息
        JSONObject res=new JSONObject();
        res.put("msg",msg);
        ctx.writeAndFlush(res);


       // ctx.channel().close();
       // ctx.close();
    }

    /*
     *
     * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
     *
     * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
     * */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(DateUtil.formatDate(new Date(), "HH:mm:ss SSS") + "channelActive RamoteAddress : " + ctx.channel().remoteAddress()+" "+ctx.channel().id());

        ctx.channel().writeAndFlush("Welcome to HelloServerHandler" + InetAddress.getLocalHost().getHostName() + " service!\n");

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(DateUtil.formatDate(new Date(), "HH:mm:ss SSS") + "channelInactive RamoteAddress : " + ctx.channel().remoteAddress()+" "+ctx.channel().id());

        super.channelInactive(ctx);
    }
}