package ssd.asm;

/**
 * Created by yehua.zyh on 2016/1/11.
 */
public class DynamicClassLoader extends ClassLoader {

    public DynamicClassLoader() {
        super(Thread.currentThread().getContextClassLoader());
    }

    public Class dynamicLoadClass(byte[] classData)
            throws Exception {


        return defineClass(null, classData, 0, classData.length);

    }


}