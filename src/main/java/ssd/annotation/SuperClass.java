package ssd.annotation;

import java.lang.annotation.Annotation;

/**
 * Created by yehua.zyh on 2016/4/13.
 */
public class SuperClass {

    /**
     * 判断是否是MTOP请求
     * 根据class 注解 和 url传过来的 api进行对比
     *
     * @param rundata
     * @return
     */
    public boolean isMtop() {
        try {

            Annotation annotation = this.getClass().getAnnotation(MtopApiAnnotation.class);
            for (Annotation a : this.getClass().getAnnotations()) {
                System.out.println(a);

            }
            System.out.println(this + " " + annotation + " " + this.getClass().getAnnotations().length);
            if (null == annotation) {
                return false;
            }
            MtopApiAnnotation mtopApiAnnotation = (MtopApiAnnotation) annotation;
            System.out.println(mtopApiAnnotation.api());

            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
