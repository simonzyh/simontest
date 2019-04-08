package ssd.test;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class DateTools {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/zyh/Downloads/log/埋点日志数据.csv"))));
        String line = null;
        Map<String, Integer> item = new HashMap();

        while ((line = br.readLine()) != null) {

            String[] dataArr=line.split(",");
           //    if(!dataArr[2].equals("h5")){
           //     continue;
           //  }
            int i=0;
            String key=dataArr[5]+dataArr[6];
            if(item.containsKey(key)){
                i=item.get(key);
             }
            i++;

            item.put(key,i);
        }
        List<Map.Entry<String, Integer>> dataList = new ArrayList();
        for (Map.Entry<String, Integer> entry : item.entrySet()) {
            dataList.add(entry);
        }

        dataList.sort(Comparator.comparingInt(Map.Entry::getValue)


        );
        for (Map.Entry<String, Integer> entry : dataList) {
            System.out.println(entry.getKey() + "     " + entry.getValue());
        }

    }
}
