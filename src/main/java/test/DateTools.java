package test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class DateTools {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/ljf/zyh/goods.csv")), "gbk"));
        String barcode = "110900055," +
                "702000018," +
                "602200235," +
                "601300063," +
                "702000391," +
                "109520004," +
                "119120001," +
                "111320040," +
                "702000012," +
                "602300152," +
                "611120025," +
                "109110842," +
                "111320146," +
                "611100085," +
                "111320187," +
                "111620028," +
                "114410108," +
                "108120082," +
                "303220085," +
                "108210053," +
                "302510356," +
                "302420010," +
                "108210070," +
                "303210047," +
                "108110082," +
                "302440001," +
                "601200022," +
                "601200081," +
                "902201100," +
                "301630016," +
                "301620022," +
                "301660035," +
                "301630015," +
                "301650016," +
                "309100005," +
                "120200053," +
                "301610070,";
        String line = null;
        // System.out.println(sql);
        while ((line = br.readLine()) != null) {
            String[] data = line.split(";");
            System.out.println(line+" "+data.length);
            String barCode=data[3];
            if(!barcode.contains(barCode)){
                continue;
            }
            String spid=data[2];
            String unit=data[7];
            // System.out.println("('"+data[0].trim()+"','"+data[1].trim()+"','"+data[0].trim()+"',0"+","+data[6].replace("¥","").replace("￥","").trim()+",now(),now(),'hnhys',"+data[9].trim()+",1,'"+data[4].trim()+"'),");
            System.out.println("update wx_goods set sp_id='" + spid + "',unit='"+unit+"' where bar_code='" + barCode + "';");

        }


    }
}
