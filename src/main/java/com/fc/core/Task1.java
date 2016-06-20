/**
 * @Title: Task1.java
 * @Package: com.fc.core
 * @Description: TODO
 * @author 张业华
 * @date 2014年8月19日 下午12:09:10
 * @version 1.3.1
 */


package com.fc.core;

import com.fc.pojo.Ball;
import com.fc.pojo.BallSort;
import com.fc.util.FcConstants;
import com.fc.util.FileUtils;

import java.util.*;
import java.util.Map.Entry;


/**
 * @author 张业华
 * @version V1.3.1
 * @Description
 * @date 2014年8月19日 下午12:09:10
 */

public class Task1 {
    public static void run() {
        List<Ball> list = FileUtils.getDouble();
        //计算bule1
        HashMap<Integer, BallSort> map = new HashMap<Integer, BallSort>();

        for (Ball ball : list) {

            if (!map.containsKey(ball.getRed1())) {
                BallSort sort = new BallSort();
                sort.setBall(ball.getRed1());
                sort.setBalltype("red1");
                sort.setTotal(list.size());
                map.put(ball.getRed1(), sort);
            }

            map.get(ball.getRed1()).setNum();
        }
        FcConstants.ballsortRed1 = sort(map);
        FileUtils.saveSort(sort(map), "red1");


        map = new HashMap<Integer, BallSort>();

        for (Ball ball : list) {

            if (!map.containsKey(ball.getRed2())) {
                BallSort sort = new BallSort();
                sort.setBall(ball.getRed2());
                sort.setBalltype("red2");
                sort.setTotal(list.size());
                map.put(ball.getRed2(), sort);
            }

            map.get(ball.getRed2()).setNum();
        }
        FcConstants.ballsortRed2 = sort(map);
        FileUtils.saveSort(sort(map), "red2");


        map = new HashMap<Integer, BallSort>();

        for (Ball ball : list) {

            if (!map.containsKey(ball.getRed3())) {
                BallSort sort = new BallSort();
                sort.setBall(ball.getRed3());
                sort.setBalltype("red3");
                sort.setTotal(list.size());
                map.put(ball.getRed3(), sort);
            }

            map.get(ball.getRed3()).setNum();
        }
        FcConstants.ballsortRed3 = sort(map);
        FileUtils.saveSort(sort(map), "red3");


        map = new HashMap<Integer, BallSort>();

        for (Ball ball : list) {

            if (!map.containsKey(ball.getRed4())) {
                BallSort sort = new BallSort();
                sort.setBall(ball.getRed4());
                sort.setBalltype("red4");
                sort.setTotal(list.size());
                map.put(ball.getRed4(), sort);
            }

            map.get(ball.getRed4()).setNum();
        }
        FcConstants.ballsortRed4 = sort(map);
        FileUtils.saveSort(sort(map), "red4");

        map = new HashMap<Integer, BallSort>();

        for (Ball ball : list) {

            if (!map.containsKey(ball.getRed5())) {
                BallSort sort = new BallSort();
                sort.setBall(ball.getRed5());
                sort.setBalltype("red5");
                sort.setTotal(list.size());
                map.put(ball.getRed5(), sort);
            }

            map.get(ball.getRed5()).setNum();
        }
        FcConstants.ballsortRed5 = sort(map);
        FileUtils.saveSort(sort(map), "red5");

        map = new HashMap<Integer, BallSort>();

        for (Ball ball : list) {

            if (!map.containsKey(ball.getRed6())) {
                BallSort sort = new BallSort();
                sort.setBall(ball.getRed6());
                sort.setBalltype("red6");
                sort.setTotal(list.size());
                map.put(ball.getRed6(), sort);
            }

            map.get(ball.getRed6()).setNum();
        }
        FcConstants.ballsortRed6 = sort(map);
        FileUtils.saveSort(sort(map), "red6");


        map = new HashMap<Integer, BallSort>();

        for (Ball ball : list) {

            if (!map.containsKey(ball.getBlue())) {
                BallSort sort = new BallSort();
                sort.setBall(ball.getBlue());
                sort.setBalltype("blue");
                sort.setTotal(list.size());
                map.put(ball.getBlue(), sort);
            }

            map.get(ball.getBlue()).setNum();
        }
        FcConstants.ballsortBlue = sort(map);
        FileUtils.saveSort(sort(map), "blue");
    }


    private static List<BallSort> sort(HashMap<Integer, BallSort> map) {
        List<BallSort> list = new ArrayList<BallSort>();
        for (Entry<Integer, BallSort> entry : map.entrySet())
            list.add(entry.getValue());

        Collections.sort(list, new Comparator<BallSort>() {

            public int compare(BallSort o1, BallSort o2) {
                return o2.getNum() - o1.getNum();
            }
        });

        return list;
    }


}
