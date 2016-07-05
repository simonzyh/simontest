package javassisttest;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * 对象属性复制
 *
 * @author jiachun.fjc
 */
public class Copier {

    private static final Logger LOG = LoggerFactory.getLogger(Copier.class);


    // copy类的缓存，容量1024
    private static Cache<Key, Copy> copierCache = CacheBuilder.newBuilder().maximumSize(1024).build();

    /**
     * 对象属性拷贝
     *
     * @param from             源对象
     * @param to               目标对象
     * @param ignoreProperties 忽略属性值
     * @param <F>              源对象类型
     * @param <T>              目标对象类型
     * @return 目标对象，同第二个参数to
     */
    public static <F, T> T copy(final F from, final T to, final String... ignoreProperties) {
        Key key = getKey(from, to, ignoreProperties);
        try {
            Copy copier = copierCache.get(key, new Callable<Copy>() {


                public Copy call() throws Exception {
                    Generator gen = new Generator();
                    gen.setSource(from.getClass());
                    gen.setTarget(to.getClass());
                    gen.setIgnoreProperties(ignoreProperties);
                    return gen.generate().newInstance();
                }
            });
            copier.copy(from, to);
            return to;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    private static Key getKey(Object from, Object to, String[] ignoreProperties) {
        Class<?> fromClass = from.getClass();
        Class<?> toClass = to.getClass();
        return new Key(fromClass, toClass, ignoreProperties);
    }


    public static void main(String[] args) {
        c1 t1 = new c1();
        t1.setAddress("address");
        t1.setName("name");
        c2 t2 = new c2();
        Copier.copy(t1, t2);
        System.out.println(JSON.toJSONString(t2));
    }


}

class c1 {
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

class c2 {
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}