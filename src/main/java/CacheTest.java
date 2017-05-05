import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * Created by yehua.zyh on 2017/5/4.
 */
public class CacheTest {
   static LoadingCache<String, String> cache = CacheBuilder.newBuilder().
       maximumSize(5)
       .refreshAfterWrite(8,TimeUnit.SECONDS)

       //.expireAfterWrite(80, TimeUnit.SECONDS)
        .removalListener(new RemovalListener<String, String>() {
            @Override
            public void onRemoval(RemovalNotification<String, String> rn) {
                System.out.println(rn.getKey() +"-"+rn.getValue()+ "==被移除");
            }

        }).build(new CacheLoader<String,String>() {

           @Override
           public String load(String s) throws Exception {
               String v=System.currentTimeMillis()+"";
               Thread.sleep(100);
               System.out.println("加载"+s+"-"+v);
               return v;
           }
       });
    public static  void main(String[] args) throws  Exception {

            System.out.println("1" +"-"+ cache.get("1"));

        Thread.sleep(10000L);
        for(int i=0;i<10;i++) {
            new Thread(){

                @Override
                public void run() {
                    try {
                      System.out.println("1"+"-" + cache.get("1"));
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        Thread.sleep(10000L);

        System.out.println("1"+"-" + cache.get("1"));
    }


}
