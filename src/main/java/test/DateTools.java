package test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DateTools {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/ljf/zyh/goods.csv")),"gbk"));

       // String sql = "update `wx_goods` (`name`, `spec`, `remark`, `status`, `price`, `create_time`, `update_time`,   `source`, `inventory`,   `create_by`, `bar_code`) values ";
        String line = null;
       // System.out.println(sql);
        while ((line = br.readLine()) != null) {
            String[] data=line.split(";");

       // System.out.println("('"+data[0].trim()+"','"+data[1].trim()+"','"+data[0].trim()+"',0"+","+data[6].replace("¥","").replace("￥","").trim()+",now(),now(),'hnhys',"+data[9].trim()+",1,'"+data[4].trim()+"'),");
       System.out.println("update wx_goods set bar_code='"+data[2].trim()+"' where bar_code='"+data[4].trim()+"';");

        }



    }
}
