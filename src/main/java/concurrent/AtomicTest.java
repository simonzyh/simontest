package concurrent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yehua.zyh on 2015/11/11.
 */
public class AtomicTest {
    public static void main(String[] args) {
        AtomicInteger ai = new AtomicInteger();
        System.out.println(ai);
        ai.getAndIncrement();
        System.out.println(ai);

        AtomicBoolean ab = new AtomicBoolean();

    }
}
