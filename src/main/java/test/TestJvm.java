package test;

import java.util.ArrayList;
import java.util.List;

/**
 * User: simon
 * Date: 2015/7/29
 * Time: 19:14
 * description：
 */
public class TestJvm {

    public static void main(String[] args) {
        List<TestA> cache = new ArrayList<TestA>();
        System.out.println(java.lang.Runtime.getRuntime().maxMemory()/1024
                + " " + java.lang.Runtime.getRuntime().totalMemory()/1024);

        int i = 0;
        while (true) {

            try {
                Thread.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cache.add(new TestA("ABCDFGQWQWQWQWQWQWQWABCDFGQWQWQWQWQWQWQWABCDFGQWQWQWQWQWQWQWABCDFGQWQWQWQWQWQWQWABCDFGQWQWQWQWQWQWQWABCDFGQWQWQWQWQWQWQWABCDFGQWQWQWQWQWQWQWABCDFGQWQWQWQWQWQWQWABCDFGQWQWQWQWQWQWQWABCDFGQWQWQWQWQWQWQWABCDFGQWQWQWQWQWQWQWABCDFGQWQWQWQWQWQWQW" + (i++)));
            if(i%5000==0) {
                System.out.println(i+" "+cache.size());
            }
        }

    }

    static class TestA {
        private String name;

        TestA(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

