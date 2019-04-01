package ssd.javassisttest;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;

/**
 * 对象属性copy 所有可复制属性全部copy
 * or merge 只要不为null的才复制
 */
public class Copier {


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
    public static <F, T> T copy(final F from, final T to, final String ignoreProperties) {
        Copy copier = getCopy(from, to);
        copier.copy(from, to, ignoreProperties);
        return to;
    }

    public static <F, T> T copy(final F from, final T to) {

        return copy(from, to, null);
    }

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
    public static <F, T> T merge(final F from, final T to, final String ignoreProperties) {

        Copy copier = getCopy(from, to);
        copier.merge(from, to, ignoreProperties);
        return to;

    }

    public static <F, T> T merge(final F from, final T to) {

        return merge(from, to, null);

    }

    private static <F, T> Copy getCopy(F from, T to) {
        try {
            Key key = getKey(from, to);
            Copy copier = copierCache.get(key, () -> {
                Generator gen = new Generator();
                gen.setSource(from.getClass());
                gen.setTarget(to.getClass());
                return gen.generate().newInstance();
            });
            return copier;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static Key getKey(Object from, Object to) {
        Class<?> fromClass = from.getClass();
        Class<?> toClass = to.getClass();
        return new Key(fromClass, toClass);
    }


}

