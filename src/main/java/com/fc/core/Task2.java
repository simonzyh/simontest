/**
 * @Title: Task1.java
 * @Package: com.fc.core
 * @Description: TODO
 * @author 寮犱笟鍗�
 * @date 2014骞�鏈�9鏃�涓嬪崍12:09:10
 * @version 1.3.1
 */


package com.fc.core;

import com.fc.pojo.Ball;
import com.fc.pojo.BallTask2;
import com.fc.util.FcConstants;
import com.fc.util.FileUtils;

import java.util.*;


/**
 * @author 寮犱笟鍗�
 * @version V1.3.1
 * @Description
 * @date 2014骞�鏈�9鏃�涓嬪崍12:09:10
 */

public class Task2 {
    static int ballNum = 5;

    public static void run() {
        List<Ball> list = FileUtils.getDouble();
        List<BallTask2> data = new ArrayList<BallTask2>();
        for (int i = 1; i <= 33; i++) {
            boolean b = false;
            for (int j = 0; j < ballNum; j++) {
                if (i == FcConstants.ballsortRed1.get(j).getBall()) {
                    b = true;
                    break;
                }
            }
            if (!b) continue;
            int index = 0;
            for (Ball ball : list) {
                index++;
                if (i == ball.getRed1()) {

                    data.add(new BallTask2(index, i));
                    break;
                }
            }
        }
        FcConstants.ballTask1 = sort(data);
        FileUtils.saveTask2(sort(data), "red1");


        data = new ArrayList<BallTask2>();
        for (int i = 1; i <= 33; i++) {
            boolean b = false;
            for (int j = 0; j < ballNum; j++) {
                if (i == FcConstants.ballsortRed2.get(j).getBall()) {
                    b = true;
                    break;
                }
            }
            if (!b) continue;
            int index = 0;
            for (Ball ball : list) {
                index++;
                if (i == ball.getRed2()) {

                    data.add(new BallTask2(index, i));
                    break;
                }
            }
        }
        FcConstants.ballTask2 = sort(data);
        FileUtils.saveTask2(sort(data), "red2");

        data = new ArrayList<BallTask2>();
        for (int i = 1; i <= 33; i++) {
            boolean b = false;
            for (int j = 0; j < ballNum; j++) {
                if (i == FcConstants.ballsortRed3.get(j).getBall()) {
                    b = true;
                    break;
                }
            }
            if (!b) continue;
            int index = 0;
            for (Ball ball : list) {
                index++;
                if (i == ball.getRed3()) {

                    data.add(new BallTask2(index, i));
                    break;
                }
            }
        }
        FcConstants.ballTask3 = sort(data);
        FileUtils.saveTask2(sort(data), "red3");

        data = new ArrayList<BallTask2>();
        for (int i = 1; i <= 33; i++) {
            boolean b = false;
            for (int j = 0; j < ballNum; j++) {
                if (i == FcConstants.ballsortRed4.get(j).getBall()) {
                    b = true;
                    break;
                }
            }
            if (!b) continue;
            int index = 0;
            for (Ball ball : list) {
                index++;
                if (i == ball.getRed4()) {

                    data.add(new BallTask2(index, i));
                    break;
                }
            }
        }
        FcConstants.ballTask4 = sort(data);
        FileUtils.saveTask2(sort(data), "red4");

        data = new ArrayList<BallTask2>();
        for (int i = 1; i <= 33; i++) {
            boolean b = false;
            for (int j = 0; j < ballNum; j++) {
                if (i == FcConstants.ballsortRed5.get(j).getBall()) {
                    b = true;
                    break;
                }
            }
            if (!b) continue;
            int index = 0;
            for (Ball ball : list) {
                index++;
                if (i == ball.getRed5()) {

                    data.add(new BallTask2(index, i));
                    break;
                }
            }
        }
        FcConstants.ballTask5 = sort(data);
        FileUtils.saveTask2(sort(data), "red5");


        data = new ArrayList<BallTask2>();
        for (int i = 1; i <= 33; i++) {
            boolean b = false;
            for (int j = 0; j < ballNum; j++) {
                if (i == FcConstants.ballsortRed6.get(j).getBall()) {
                    b = true;
                    break;
                }
            }
            if (!b) continue;
            int index = 0;
            for (Ball ball : list) {
                index++;
                if (i == ball.getRed6()) {

                    data.add(new BallTask2(index, i));
                    break;
                }
            }
        }
        FcConstants.ballTask6 = sort(data);
        FileUtils.saveTask2(sort(data), "red6");

        data = new ArrayList<BallTask2>();
        for (int i = 1; i <= 16; i++) {
            
        /*	 boolean b=false;
             for(int j=0;j<ballNum;j++){
                 if(i==FcConstants.ballsortBlue.get(j).getBall())
                 {
                     b=true;break;
                 }
             }
             if(!b)continue;*/
            int index = 0;
            for (Ball ball : list) {
                index++;
                if (i == ball.getBlue()) {

                    data.add(new BallTask2(index, i));
                    break;
                }
            }
        }
        FcConstants.ballTaskBlue = sort(data);
        FileUtils.saveTask2(sort(data), "blue");

        //璁＄畻 
        java.util.Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();

        for (int i = 0; i < 5; i++) {

            int ball = 0;
            //1
            ball = FcConstants.ballTask1.get(random.nextInt(ballNum)).getBall();

            set.add(ball);
            System.out.print(ball + "-");
            //2

            ball = FcConstants.ballTask2.get(random.nextInt(ballNum)).getBall();


            set.add(ball);
            System.out.print(ball + "-");
            //2

            ball = FcConstants.ballTask3.get(random.nextInt(ballNum)).getBall();

            set.add(ball);
            System.out.print(ball + "-");
            //2

            ball = FcConstants.ballTask4.get(random.nextInt(ballNum)).getBall();

            System.out.print(ball + "-");
            //2

            ball = FcConstants.ballTask5.get(random.nextInt(ballNum)).getBall();

            set.add(ball);
            System.out.print(ball + "-");
            //2

            ball = FcConstants.ballTask6.get(random.nextInt(ballNum)).getBall();

            set.add(ball);
            System.out.print(ball + "-");
            //2

            ball = FcConstants.ballTaskBlue.get(i).getBall();


            System.out.print(ball);

            System.out.println();


        }
        System.out.println(set);


    }


    private static List<BallTask2> sort(List<BallTask2> list) {

        Collections.sort(list, new Comparator<BallTask2>() {

            public int compare(BallTask2 o1, BallTask2 o2) {
                return o2.getNum() - o1.getNum();
            }
        });

        return list;
    }


}
