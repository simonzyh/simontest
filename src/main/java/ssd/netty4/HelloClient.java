package ssd.netty4;


import com.alibaba.fastjson.JSON;
import com.gialen.rpc.common.model.GLMessageHeader;
import com.gialen.rpc.common.model.GrpcMessage;
import com.google.common.collect.Lists;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HelloClient {

    public static String host = "127.0.0.1";
    public static int port = 7878;

    /**
     * @param args
     * @throws InterruptedException
     * @throws IOException
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            GrpcMessage request = new GrpcMessage();

            GLMessageHeader head = new GLMessageHeader();
            head.setMesageId(123L);
            head.setAppName("gateway");
            head.setServiceName("com.gialen.gateway.service.TestService");
            head.setServiceMethod("get");
            head.setParamTypes("java.lang.Integer,java.lang.String,java.lang.String");
            request.setBody(Lists.newArrayList(12, "hahaha", "第三个参数"));
            request.setHeader(head);


            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HelloClientInitializer());

            // 连接服务端
            Channel ch1 = b.connect(host, port).sync().channel();
            System.out.println(ch1.isOpen() + " " + ch1.isActive());
            ch1.writeAndFlush(JSON.toJSONString(request) + "\n");

            Channel ch2 = b.connect(host, 7879).sync().channel();
            Channel ch3 = b.connect(host, port).sync().channel();
            Channel ch4 = b.connect(host, port).sync().channel();
            Channel ch5 = b.connect(host, port).sync().channel();
            Channel ch6 = b.connect(host, port).sync().channel();

            Bootstrap b1 = new Bootstrap();
            b1.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HelloClientInitializer());
            Channel ch11 = b1.connect(host, port).sync().channel();

            Channel ch21 = b1.connect(host, port).sync().channel();
            Channel ch31 = b1.connect(host, port).sync().channel();
            Channel ch41 = b1.connect(host, port).sync().channel();
            Channel ch51 = b1.connect(host, port).sync().channel();
            Channel ch61 = b1.connect(host, port).sync().channel();


            /*
             * 向服务端发送在控制台输入的文本 并用"\r\n"结尾
             * 之所以用\r\n结尾 是因为我们在handler中添加了 DelimiterBasedFrameDecoder 帧解码。
             * 这个解码器是一个根据\n符号位分隔符的解码器。所以每条消息的最后必须加上\n否则无法识别和解码
             * */
            //body 是请求参数的json字符串
            //例如  请求接口是 object test(Object o1,Object o2) 则body是 {'o1':'123','o2':'456'}

            //request.setBody("{\"id\":\"1\"}");
            //head

            //发送注册
            ch3.writeAndFlush(JSON.toJSONString(request) + "\n");
            ch4.writeAndFlush(JSON.toJSONString(request) + "\n");
            ch5.writeAndFlush(JSON.toJSONString(request) + "\n");
            ch6.writeAndFlush(JSON.toJSONString(request) + "\n");
            ch11.writeAndFlush(JSON.toJSONString(request) + "\n");
            ch21.writeAndFlush(JSON.toJSONString(request) + "\n");
            ch31.writeAndFlush(JSON.toJSONString(request) + "\n");
            ch41.writeAndFlush(JSON.toJSONString(request) + "\n");
            ch51.writeAndFlush(JSON.toJSONString(request) + "\n");
            ch61.writeAndFlush(JSON.toJSONString(request) + "\n");


        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }

    // 计算字符串的MD5
    public static String conVertTextToMD5(String plainText) {
        try {
            System.out.println(plainText);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // 32位加密
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }


}