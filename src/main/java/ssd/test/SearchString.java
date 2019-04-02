package ssd.test;

/**
 * Created by yehua.zyh on 2016/12/27.
 */
public class SearchString {

    public static void main(String[] args) {
        System.out.println(String.format("%-20s", 1234).replaceAll(" ", "0").substring(0, 5));

        b b1 = new b();
        b1.ma();
    }

    static class a {

        void ma() {
            m1();
            m2();
        }


        void m1() {
            System.out.println("a.m1");
        }

        void m2() {
            System.out.println("a.m2");
        }
    }

    static class b extends a {
        void m1() {
            System.out.println("b.m1");
        }
    }
}
