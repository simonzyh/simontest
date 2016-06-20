package com.fc;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public abstract class Test {
    static HashMap<String, Integer> em = new HashMap<String, Integer>();
    static HashMap<String, Integer> emNew = new HashMap<String, Integer>();


    public static void main(String[] args) throws Exception {
        parsepUSH();
    }

    public static void printMap(HashMap<String, Integer> map) {
        List<String> keyList = new ArrayList<String>();
        for (String key : map.keySet())
            keyList.add(key);

        java.util.Collections.sort(keyList);

        for (String key : keyList) {
            System.out.println(key + " " + map.get(key));
        }
    }

    public static void parseCart() throws Exception {
        BufferedReader brEm = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:\\em.txt")), "gbk"));
        BufferedReader brEmNew = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:\\em-new.txt")), "utf-8"));
        BufferedReader brEmNewSms = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:\\em-new-sms.txt")), "utf-8"));
        String redline = null;
        while ((redline = brEm.readLine()) != null) {
            parseEm(redline, em);
        }
        while ((redline = brEmNew.readLine()) != null) {
            parseEm(redline, emNew);
        }
        List<String> keyList = new ArrayList<String>();
        for (String key : em.keySet())
            keyList.add(key);

        java.util.Collections.sort(keyList);

        for (String key : keyList) {
            System.out.println(key + " " + em.get(key) + " " + emNew.get(key));
        }
    }


    public static void parseEm(String line, HashMap<String, Integer> map) {
        try {
            String dateStr = line.substring(line.indexOf("加入时间=") + 5, line.indexOf("数量=")).trim().substring(0, 13);
            String numStr = line.substring(line.indexOf("数量=") + 3, line.indexOf("花费时间=")).trim();
            int oldNum = 0;
            if (map.containsKey(dateStr)) {
                oldNum = map.get(dateStr);
            }
            map.put(dateStr, oldNum + Integer.parseInt(numStr));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(line);
        }

    }

    public static void parseEmsms() throws Exception, FileNotFoundException {

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        BufferedReader brEmNewSms = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:\\em-new-sms.txt")), "utf-8"));
        String redline = null;
        while ((redline = brEmNewSms.readLine()) != null) {
            try {

                String dateStr = redline.substring(0, 13).trim();
                String numStr = redline.substring(redline.indexOf("本批次处理短信数据量") + 10, redline.indexOf("发送比例")).trim();
                int oldNum = 0;
                if (map.containsKey(dateStr)) {
                    oldNum = map.get(dateStr);
                }
                map.put(dateStr, oldNum + Integer.parseInt(numStr));
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        printMap(map);
    }

    public static void parseEmemail() throws Exception, FileNotFoundException {

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        BufferedReader brEmNewSms = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:\\em-new-email1.txt")), "utf-8"));
        String redline = null;
        while ((redline = brEmNewSms.readLine()) != null) {
            try {

                String dateStr = redline.substring(0, 13).trim();
                String numStr = redline.substring(redline.indexOf("本批次处理邮件数据量") + 10, redline.indexOf("发送比例")).trim();
                int oldNum = 0;
                if (map.containsKey(dateStr)) {
                    oldNum = map.get(dateStr);
                }
                map.put(dateStr, oldNum + Integer.parseInt(numStr));
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        printMap(map);
    }


    public static void parsepUSH() throws Exception, FileNotFoundException {

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        BufferedReader brEmNewSms = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:\\em-push1.txt")), "utf-8"));
        String redline = null;
        while ((redline = brEmNewSms.readLine()) != null) {
            try {

                String dateStr = redline.substring(0, 13).trim();
                String numStr = redline.substring(redline.indexOf("发送PUSH") + 6, redline.indexOf("条")).trim();
                int oldNum = 0;
                if (map.containsKey(dateStr)) {
                    oldNum = map.get(dateStr);
                }
                map.put(dateStr, oldNum + Integer.parseInt(numStr));
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        printMap(map);
    }
}
