package javassisttest;

/**
 * Created by yehua.zyh on 2016/6/17.
 */

/**
 * 把source转换为target需要的包装器类型或者原始类型
 */

public interface Copy {
    void copy(Object source, Object target);

    void merge(Object source, Object target);
}