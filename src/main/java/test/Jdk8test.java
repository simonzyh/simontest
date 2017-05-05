/**
 * @Title: 都是.java
 * @Package: test
 * @Description: TODO
 * @author 张业�?
 * @date 2014�?8�?15�? 下午4:01:06
 * @version 1.3.1
 */


package test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;


interface Converter<F, T> {
    T convert(F from);
}

/**
 * @author 张业�?
 * @version V1.3.1
 * @Description
 * @date 2014�?8�?15�? 下午4:01:06
 */

public class Jdk8test {
    public static Integer startsWith(String s) {
        return Integer.parseInt(s.substring(0, 1));
    }

    public static void predicateTest() {
        Predicate<String> predicate = (s) -> s.length() > 0;
        predicate.test("foo");              // true
        predicate.negate().test("foo");     // false
        Predicate<Boolean> nonNull = (s) -> Objects.nonNull(s);
        Predicate<Boolean> isNull = Objects::isNull;
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
        System.out.println(predicate.test("123") + " " + predicate.negate().test("123"));


    }

    public static void functionTest() {
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        System.out.println(backToString.apply("123"));     // "123"
    }

    public static void optionalTest() {
        Optional<String> optional = Optional.of("bam");
        optional.isPresent();           // true
        optional.get();                 // "bam"
        optional.orElse("fallback");    // "bam"
        optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"
    }

    public static void streamTest() {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
        stringCollection
                .stream()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);
        stringCollection.stream().map((s) -> s + "1");
       Map map=Maps.uniqueIndex(stringCollection, input -> {
           return input.substring(1);
       });

         System.out.println(map);
    }

    public static void parallelStreamTest() {
        try {
            int max = 2;
            List<String> values = new ArrayList<>(max);
            for (int i = 0; i < max; i++) {
                UUID uuid = UUID.randomUUID();
                values.add(uuid.toString());
            }
         //   System.out.println(JSON.toJSONString(values.spliterator()));

            long t0 = System.nanoTime();
            long count = values.stream().sorted().count();
            System.out.println(count);
            long t1 = System.nanoTime();
            long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
            System.out.println(String.format("sequential sort took: %d ms", millis));

            long t2 = System.nanoTime();
            long count1 = values.parallelStream().sorted().count();
            System.out.println(count1);
            long t3 = System.nanoTime();
            long millis1 = TimeUnit.NANOSECONDS.toMillis(t3 - t2);
            System.out.println(String.format("parallel sort took: %d ms", millis1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws  Exception {
        //streamTest();
        // predicateTest();
        //functionTest();
        //streamTest();
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File("d:\\bmd.txt"))));
        List<String> old=new ArrayList<>();
        String readline=null;
        while((readline=br.readLine())!=null){
            old.add(readline);
        }

        Set<String> set=new HashSet<>();
        BufferedReader br1=new BufferedReader(new InputStreamReader(new FileInputStream(new File("d:\\itemList.txt"))));
         while((readline=br1.readLine())!=null){
             if(!old.contains(readline)) {
                 set.add(readline);
             }
        }

        PrintWriter pw=new PrintWriter("d:\\newitem.txt");
        for(String str:set){
            pw.println(str);
        }
        pw.flush();
    }

    public void lambdaTest() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

        Collections.sort(names, (String a, String b) -> {
            return b.compareTo(a);
        });

        Collections.sort(names, (String a, String b) -> b.compareTo(a));

        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        Converter<String, Integer> converter1 = Integer::valueOf;
        Converter<String, Integer> converter2 = Jdk8test::startsWith;
    }
}
