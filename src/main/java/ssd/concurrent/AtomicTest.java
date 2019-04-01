package ssd.concurrent;

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
        String s = "https://content.tmall.com/wow/pegasus/subject/1/725677994/4038384?id=4038384&&channel=chaoshi&wh_channel=chaoshi4&gccpm=5855752.815.2.subject-chaoshi-2004.709074";


    }
}
