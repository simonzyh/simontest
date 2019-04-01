package ssd.javassisttest;

import lombok.Data;
import org.springframework.beans.BeanUtils;

public class Test {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        c1 cc = new c1();
        cc.setI1(1);
        cc.setI2(2);
        cc.setS1("123");
        cc.setS2("123");
        cc.setS3("123");
        cc.setS4("123");
        cc.setS5("123");
        cc.setS6("123");
        cc.setS7("123");
        cc.setS8("123");
        cc.setS9("123");
        cc.setS10("123");
        cc.setS11("123");
        c2 cc21 = new c2();
        Copier.copy(cc, cc21);
        c2 cc22 = new c2();
        BeanUtils.copyProperties(cc, cc22);

        long l1 = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            c2 cc2 = new c2();
            Copier.copy(cc, cc2);
        }
        System.out.println(System.currentTimeMillis() - l1);
        long l2 = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            c2 cc2 = new c2();
            BeanUtils.copyProperties(cc, cc2);
        }
        System.out.println(System.currentTimeMillis() - l2);

    }

}

@Data
class c1 {
    public String t;
    private String s1;
    private String s2;
    private String s3;
    private String s4;
    private String s5;
    private String s6;
    private String s7;
    private String s8;
    private String s9;
    private String s10;
    private String s11;
    private Integer i1;
    private int i2;


}

@Data
class c2 {
    public String t;
    private String s1;
    private String s2;
    private String s3;
    private String s4;
    private String s5;
    private String s6;
    private String s7;
    private String s8;
    private String s9;
    private String s10;
    private String s11;
    private Integer i1;
    private int i2;


}