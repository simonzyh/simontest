package ssd.test;

import com.google.common.cache.*;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by yehua.zyh on 2017/5/4.
 */
public class CacheTest {
    static LoadingCache<String, String> cache = CacheBuilder.newBuilder().
            maximumSize(5)
            .refreshAfterWrite(8, TimeUnit.SECONDS)

            //.expireAfterWrite(80, TimeUnit.SECONDS)
            .removalListener(new RemovalListener<String, String>() {
                @Override
                public void onRemoval(RemovalNotification<String, String> rn) {
                    System.out.println(rn.getKey() + "-" + rn.getValue() + "==被移除");
                }

            }).build(new CacheLoader<String, String>() {

                @Override
                public String load(String s) throws Exception {
                    String v = System.currentTimeMillis() + "";
                    Thread.sleep(100);
                    System.out.println("加载" + s + "-" + v);
                    return v;
                }
            });

    private static <R> R get(String ket, Function<String, ? extends R> function) {
        Object o = null;
        return (R) o;
    }

    public static void main(String[] args) throws Exception {
          Cache<String,String> rushProductCache= CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();

          System.out.println(rushProductCache.getIfPresent("qwe"));
          rushProductCache.put("qwe","qqqqqqq");
        System.out.println(rushProductCache.getIfPresent("qwe"));

    }


    public static String getVirtualUserIdFromUserId(String userId) {
        String shortUid = StringUtils.substring(userId, 13, 15);
        System.out.println(shortUid);
        return StringUtils.leftPad(shortUid, 15, "0") + "0";
    }


}
