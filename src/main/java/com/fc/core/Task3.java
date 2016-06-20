package com.fc.core;

import com.fc.pojo.Ball;
import com.fc.util.FcConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Task3 {
    public static void run() {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> map1 = new HashMap<Integer, Integer>();
        System.out.println("red1");
        for (int j = 0; j < FcConstants.balllist.size(); j++) {
            Ball ball = FcConstants.balllist.get(j);

            for (int i = j + 1; i < FcConstants.balllist.size(); i++) {
                if (ball.getRed2() == FcConstants.balllist.get(i).getRed2()) {
                    int nul = 0;
                    int index = i - j;
                    if (map.containsKey(index))
                        nul = map.get(index);

                    map.put(index, nul + 1);
                    break;
                }

            }

            for (int i = j + 1; i < FcConstants.balllist.size(); i++) {
                if (check(FcConstants.balllist.get(i), ball.getRed2())) {
                    int nul = 0;
                    int index = i - j;
                    if (map1.containsKey(index))
                        nul = map1.get(index);

                    map1.put(index, nul + 1);
                    break;

                }
            }
        }
        printMap(map);
        printMap(map1);


    }

    public static void printMap(HashMap<Integer, Integer> map) {
        List<Integer> keyList = new ArrayList<Integer>();
        for (Integer key : map.keySet())
            keyList.add(key);

        java.util.Collections.sort(keyList);

        for (Integer key : keyList) {
            System.out.println(key + " " + map.get(key));
        }
    }

    public static boolean check(Ball ball, int ball1) {

        if (ball.getBlue() == ball1) return true;
        if (ball.getRed1() == ball1) return true;
        if (ball.getRed2() == ball1) return true;
        if (ball.getRed3() == ball1) return true;
        if (ball.getRed4() == ball1) return true;
        if (ball.getRed5() == ball1) return true;
        if (ball.getRed6() == ball1) return true;
        return false;
    }
}
