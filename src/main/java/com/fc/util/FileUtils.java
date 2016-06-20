/**
 * @Title: FileUtils.java
 * @Package: com.fc.util
 * @Description: TODO
 * @author 张业华
 * @date 2014年8月19日 下午12:10:15
 * @version 1.3.1
 */


package com.fc.util;

import com.alibaba.fastjson.JSON;
import com.fc.pojo.Ball;
import com.fc.pojo.BallSort;
import com.fc.pojo.BallTask2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 张业华
 * @version V1.3.1
 * @Description
 * @date 2014年8月19日 下午12:10:15
 */

public class FileUtils {
    public static void saveDouble(List<Ball> data) {
        try {
            List<String> strdata = new ArrayList<String>(data.size());
            for (Object line : data) {
                System.out.println(JSON.toJSONString(line));
                strdata.add(JSON.toJSONString(line));
            }
            witeToFile(strdata, FcConstants.FILENAME_DOUBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void saveSort(List<BallSort> data, String type) {
        try {
            List<String> strdata = new ArrayList<String>(data.size());
            for (Object line : data) {
                strdata.add(JSON.toJSONString(line));
            }
            witeToFile(strdata, FcConstants.FILENAME_SORT + "-" + type);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void saveTask2(List<BallTask2> data, String type) {
        try {
            List<String> strdata = new ArrayList<String>(data.size());
            for (Object line : data) {
                strdata.add(JSON.toJSONString(line));
            }
            witeToFile(strdata, FcConstants.TASK2 + "-" + type);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<Ball> getDouble() {
        List<Ball> res = new ArrayList<Ball>();
        for (String str : getData(FcConstants.FILENAME_DOUBLE)) {

            res.add((Ball) JSON.parseObject(str, Ball.class));
        }

        return res;

    }

    private static void witeToFile(List<String> data, String filename) {
        try {
            String path = System.getProperty("user.dir") + File.separator + "file" + File.separator;
            PrintWriter pw = new PrintWriter(path + filename + ".txt");
            for (Object line : data) {
                pw.println(line);

            }
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static List<String> getData(String filename) {
        List<String> res = new ArrayList<String>();
        try {
            String path = System.getProperty("user.dir") + File.separator + "file" + File.separator;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path + filename + ".txt"))));
            String strLine = null;
            while ((strLine = br.readLine()) != null)
                res.add(strLine);

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;


    }
}
