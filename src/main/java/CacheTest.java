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
        List l = get("", new Function<String, List<String>>() {
            /**
             * Applies this function to the given argument.
             *
             * @param s the function argument
             * @return the function result
             */
            @Override
            public List<String> apply(String s) {
                return null;
            }
        });

        System.out.println("1" + "-" + cache.get("1"));

        for (int i = 0; i < 3; i++) {
            new Thread() {

                @Override
                public void run() {
                    try {
                        while (true) {
                            int j = 0;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();


        }

        System.out.println(Integer.parseInt("000"));
        String userid = "20881212121212199";
        int mUserId = Integer.parseInt(userid.substring(userid.length() - 2));
        System.out.println(getVirtualUserIdFromUserId("2088302211026005"));
    }


    public static String getVirtualUserIdFromUserId(String userId) {
        String shortUid = StringUtils.substring(userId, 13, 15);
        System.out.println(shortUid);
        return StringUtils.leftPad(shortUid, 15, "0") + "0";
    }


}
