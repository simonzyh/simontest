package ssd.netty4;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

public class TestStringEncoder extends MessageToMessageEncoder<JSONObject> {
    /**
     * Encode from one message to an other. This method will be called for each written message that can be handled
     * by this encoder.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link MessageToMessageEncoder} belongs to
     * @param msg the message to encode to an other one
     * @param out the {@link List} into which the encoded msg should be added
     *            needs to do some kind of aggregation
     * @throws Exception is thrown if an error occurs
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, JSONObject msg, List<Object> out) throws Exception {
        String jsonstr = msg.toJSONString() + "\n";

        out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(jsonstr), Charset.defaultCharset()));

    }
}
