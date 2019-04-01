package ssd.classloader;

import java.net.URL;

/**
 * Created by yehua.zyh on 2016/1/14.
 */
public class ClassLoaderTest {


    public static void main(String[] args) throws Exception {
        MyClassLoader myClassLoader = new MyClassLoader();
        MyClassLoader myClassLoader1 = new MyClassLoader();
        Thread.currentThread().setContextClassLoader(myClassLoader);

        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urls) {
            System.out.println(url.toExternalForm());
        }
        System.out.println(System.getProperty("java.ext.dirs"));
        ClassLoader extensionClassloader = ClassLoader.getSystemClassLoader().getParent();
        System.out.println("the parent of extension classloader : " + extensionClassloader.getParent());

        System.out.println(ClassLoaderTest.class.getClassLoader());
        System.out.println(ClassLoaderTest.class.getClassLoader().getParent());
        System.out.println(Thread.currentThread().getContextClassLoader());
        ClassLoadTestBean b1 = (ClassLoadTestBean) myClassLoader.dynamicLoadClass(ClassLoadTestBean.class.getName()).newInstance();
        ClassLoadTestBean b2 = (ClassLoadTestBean) myClassLoader1.dynamicLoadClass(ClassLoadTestBean.class.getName()).newInstance();
        System.out.println(b1);
        System.out.println(b2);
        //myClassLoader.loadClass(ClassLoaderTest.class.getName());

    }
}
