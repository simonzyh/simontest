package ssd;




import org.apache.xmlbeans.impl.util.Base64;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Test3 {

    public static void main(String[] args) {

        byte[] byte2 = Base64.decode("EQAAAAAAAACVQNgAAAAAACgAAAAAAAAA".getBytes());

        System.out.println(Arrays.toString(byte2));
        byte[] bsub = new byte[8];
                System.arraycopy(byte2,0,bsub,0,8);
        int index = 8;
        for (int i = 0; i < byte2.length; i++) {

            bsub[--index] = byte2[i];
            if (index == 0) {
                System.out.println(Arrays.toString(bsub) + "===" + bytesToLong(bsub));
                index = 8;
            }

        }
    }


    public static Long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(0, x);
        return buffer.array();
    }
}
