package ssd.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;


public class BookServiceFactory {
    public static BookServiceBean getProxyInstance(MyCglibProxy myProxy) {
        Enhancer en = new Enhancer();
        //进行代理
        en.setSuperclass(BookServiceBean.class);
        en.setCallback(myProxy);
        //生成代理实例
        Object obj = en.create();
        System.out.println(obj.getClass().getClassLoader());
        return (BookServiceBean) obj;
    }
}