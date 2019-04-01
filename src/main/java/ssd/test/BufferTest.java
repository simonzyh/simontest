package test;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class BufferTest {

    /**
     * @param args
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        byte[] b = "my test".getBytes("utf-8");//获取字节数组
        ByteBuffer bytebuffer = ByteBuffer.allocate(15);//定义一个buffer，并为之分配容量。
        bytebuffer.put(b);//把字节放进buffer

        /*没有设置mark（标记）， */
        System.out.println("capacity:" + bytebuffer.capacity() + "    limit:" + bytebuffer.limit() + "   position:" + bytebuffer.position() + "    mark:" + "需要自己设置");

        //反转此缓冲区，从结果来看，我们知道，capacity不变，limit=position  ；position=0；
        //此时的buffer感觉就像队列一样，先进先出。
        bytebuffer.flip();

        System.out.println("capacity:" + bytebuffer.capacity() + "    limit:" + bytebuffer.limit() + "   position:" + bytebuffer.position() + "    mark:" + "需要自己设置");
        byte[] byteArr = new byte[3];
        bytebuffer.get(byteArr);
        System.out.println(new String(byteArr, "utf-8"));
        System.out.println("capacity:" + bytebuffer.capacity() + "    limit:" + bytebuffer.limit() + "   position:" + bytebuffer.position() + "    mark:" + "需要自己设置");
        bytebuffer.put((byte) 'A');
        //重绕此缓冲区，其实就是相当于刷新一下，通知buffer做好读或写的准备，并没有什么改变。
        bytebuffer.rewind();

        System.out.println("capacity:" + bytebuffer.capacity() + "    limit:" + bytebuffer.limit() + "   position:" + bytebuffer.position() + "    mark:" + "需要自己设置");
        byte[] byteArr1 = new byte[5];

        bytebuffer.get(byteArr1);
        System.out.println(new String(byteArr1, "utf-8"));
    }

}