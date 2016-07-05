package annotation;

import java.lang.annotation.Annotation;

/**
 * Created by yehua.zyh on 2016/4/13.
 */
public class Test {

    public static void main(String[] args) {
        SubClass sub = new SubClass();
        sub.isMtop();
        Annotation annotation = SubClass.class.getAnnotation(MtopApiAnnotation.class);
        System.out.println(annotation);


    }
}
