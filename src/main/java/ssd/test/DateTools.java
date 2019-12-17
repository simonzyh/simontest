package ssd.test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class DateTools {

    public static void main(String[] args) throws Exception {
        List<String> data = getData("/Users/zyh/Documents/dataPoint.log");
        System.out.println(data.size());
        data.addAll(getData("/Users/zyh/Documents/dataPoint.2019-04-24.log"));
        System.out.println(data.size());
        data.addAll(getData("/Users/zyh/Documents/dataPoint.2019-04-23.log"));

        data.addAll(getData("/Users/zyh/Documents/dataPoint.2019-04-22.log"));
        data.addAll(getData("/Users/zyh/Documents/dataPoint.2019-04-21.log"));
        data.addAll(getData("/Users/zyh/Documents/dataPoint.2019-04-20.log"));
        data.addAll(getData("/Users/zyh/Documents/dataPoint.2019-04-19.log"));

        Set<String> phoneSet = new HashSet<>();
        Set<String> userSet = new HashSet<>();
        Map<String, gdata> map = new HashMap<>();

        for (String ling : data) {
            try {

                String[] pathList = ling.split("\\|");
                String phone = pathList[7];
                String type = pathList[3];
                String userId = pathList[2];
                String time = pathList[0];
                if (time.compareTo("2019-04-24 10:00:00.000") < 0) {
                    continue;
                }
                if (time.compareTo("2019-04-25 10:00:00.000") > 0) {
                    continue;
                }
                phoneSet.add(phone);
                userSet.add(userId);
                if (!map.containsKey(type)) {
                    map.put(type, new gdata());
                }
                gdata gd = map.get(type);
                gd.setPhone(phone);
                gd.setUserId(userId);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(ling);
            }

        }

        System.out.println(userSet.size() + " " + phoneSet.size());
        for (Map.Entry<String, gdata> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " 总数"
                    + entry.getValue().userIdList.size() + " 独立用户"
                    + entry.getValue().userIdSet.size() + " " + " 独立手机"
                    + entry.getValue().phoneSet.size());
        }

    }

    static class gdata {
        List<String> userIdList = new ArrayList<>();
        List<String> phoneList = new ArrayList<>();

        Set<String> userIdSet = new HashSet<>();
        Set<String> phoneSet = new HashSet<>();

        public void setPhone(String phone) {
            phoneList.add(phone);
            phoneSet.add(phone);
        }

        public void setUserId(String userId) {
            userIdList.add(userId);
            userIdSet.add(userId);
        }


    }


    private static List<String> getData(String path) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
        String line = null;
        List<String> phone = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            phone.add(line);
        }
        return phone;
    }


}
