package ssd;

import java.lang.reflect.ParameterizedType;

public class TestFx<T> {
    T t;

    public void printFx() {
        Class c = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        System.out.println(c);
        System.out.println(((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

    }
}
