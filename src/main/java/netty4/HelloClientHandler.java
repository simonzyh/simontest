package netty4;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty4.model.GjMessage;
import netty4.model.GjMessageHead;
import org.apache.commons.httpclient.util.DateUtil;

import java.util.Date;

import static netty4.HelloClient.conVertTextToMD5;

public class HelloClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println(DateUtil.formatDate(new Date(), "HH:mm:ss SSS") + "channelRead0 Server say : " + msg);
        if (!msg.contains("{")) {
            return;
        }
        GjMessage request = JSON.parseObject(msg,GjMessage.class);
        GjMessageHead headj = request.getHead();
        if (headj.getMessageType()!=0) {
            return;
        }
        System.out.println("收到请求" + msg);
        //head
        GjMessage response = new GjMessage();
        response.setBody(JSON.toJSONString("12345"));
        GjMessageHead head = new GjMessageHead();
        head.setMessageId(headj.getMessageId());
        head.setAppKey("hnhys");
        head.setMessageType(1);
         head.setSign(conVertTextToMD5("hnhys" + response.getBody()));
        response.setHead(head);
        // ctx.writeAndFlush(JSON.toJSONString(response) + "\n");

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(DateUtil.formatDate(new Date(), "HH:mm:ss SSS") + "channelActive Client active ");

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(DateUtil.formatDate(new Date(), "HH:mm:ss SSS") + "channelInactive Client close ");

        super.channelInactive(ctx);
    }
}