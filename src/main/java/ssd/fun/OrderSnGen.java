package ssd.fun;

import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderSnGen {

    //时间格式化
    static SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
    private static AtomicInteger SEQ = new AtomicInteger(0);
    //机器id
    private static String workerId;

    static {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
            workerId = StringUtils.leftPad(Math.abs(address.getHostAddress().hashCode() % 100) + "", 2, "0");

        } catch (UnknownHostException e) {
        }
    }

    //生成序列号
    // 11 时间  +机器ID+ 序列号
    public synchronized static String gen() {
        if (SEQ.incrementAndGet() > 999) {
            SEQ = new AtomicInteger(0);
        }
        String time = formatter.format(new Date());
        String sequen = time + workerId + SEQ.get();
        return "11" + StringUtils.rightPad(sequen, 17, "0");

    }

    public static void main(String[] args) {
        System.out.println(gen());
    }

}
