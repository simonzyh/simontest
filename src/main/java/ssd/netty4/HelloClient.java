package ssd.netty4;


import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import ssd.netty4.model.GjMessage;
import ssd.netty4.model.GjMessageHead;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
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
        System.out.println(new BigDecimal("12.09").toString());
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HelloClientInitializer());

            // 连接服务端
            Channel ch1 = b.connect(host, port).sync().channel();

            // 控制台输入
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {
                String line = in.readLine();
                if (line == null) {
                    continue;
                }
                if ("exit".equals(line)) {
                    ch1.close();
                    //ch2.close();

                    break;
                }
                /*
                 * 向服务端发送在控制台输入的文本 并用"\r\n"结尾
                 * 之所以用\r\n结尾 是因为我们在handler中添加了 DelimiterBasedFrameDecoder 帧解码。
                 * 这个解码器是一个根据\n符号位分隔符的解码器。所以每条消息的最后必须加上\n否则无法识别和解码
                 * */
                GjMessage request = new GjMessage();
                //body 是请求参数的json字符串
                //例如  请求接口是 object test(Object o1,Object o2) 则body是 {'o1':'123','o2':'456'}

                //request.setBody("{\"id\":\"1\"}");
                //head
                GjMessageHead head = new GjMessageHead();
                head.setMessageId(line);
                head.setMessageType(0);
                head.setAppKey("hnhys");
                head.setRequestMethod("com.gaojihealth.weixinmp.service.ErpProxyService.register");
                head.setSign(conVertTextToMD5("hnhys" + request.getBody()));
                request.setHead(head);
                //发送ping 消息
                if ("ping".equals(line)) {
                    ch1.writeAndFlush("ping" + "\n");
                } else {
                    //发送注册
                    ch1.writeAndFlush(JSON.toJSONString(request) + "\n");
                }

            }
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