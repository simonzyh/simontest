package ssd;

import java.io.*;

public class Test20200223 {

    public static void main(String[] args) throws  Exception {
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/zyh/2020-02-23.txt"))));
       String line=null;
       String s1="2020-03-12 09:00:00";
       String s2="2020-03-15 10:00:00";
       while((line=br.readLine())!=null){
           String skuid=line.split("\\s+")[0].trim();
           String skuName=line.split("\\s+")[1].trim();
           System.out.println("insert into limit_sale_range(sku_id,sku_name,limit_type,limit_num,begin_time,end_time,status) values "
           +"("+skuid+",'"+skuName+"',"+2+","+5+",'"+s1+"','"+s2+"',"+1+");"

           );
       }

    }
}
