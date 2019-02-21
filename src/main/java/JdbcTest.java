import com.alibaba.fastjson.JSON;
import netty4.HelloClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JdbcTest {



    public static void main(String[] args) throws  Exception, SQLException, InterruptedException, NoSuchMethodException {
         BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/ljf/Downloads/商品佣金录入-20190220.csv"))));
         String line=null;
        Map<String,String[]>  stringMap=getGoodsPrice() ;
        Set<String> s=new HashSet<>();

        while ((line=br.readLine())!=null){
             String[] arr=line.split(";");
             String countryCn=arr[0];
             Integer countryId=0;
             if("泰国".equals(countryCn)){
                 countryId=3;
             }else if("沙特阿拉伯".equals(countryCn)){
                 countryId=2;
             }

             String goodsCode=arr[1];
            if(!s.add(countryId+","+goodsCode)){
                //System.out.println(countryId+","+goodsCode+" 重复");
               continue;
            }

            String ratio=( new BigDecimal(arr[2].replace("%","")).divide(new BigDecimal(100),4,BigDecimal.ROUND_HALF_UP).doubleValue())+"";
          String[] pricedata=stringMap.get(countryId+","+goodsCode);
            String title=pricedata[2];
            String price=pricedata[3];
           //  System.out.println(countryId+" "+goodsCode+" "+ratio+" "+title+" "+price);
          String sql="  INSERT INTO `orko_distribution`.`distribution_goods_ratio`( `goods_code`, `create_at`, `update_at`, `is_delete`, `goods_name`, `goods_price`, `goods_pic`, `status`, `commission_ratio`, `country_id`, `update_user`) VALUES (";
             sql+="'"+goodsCode+"' , now(), now(),0 ,";
              sql+= "'"+    title+"',"+ price+", NULL, 1, "+ratio+", "+countryId+", NULL) ;" ;
              System.out.println(sql);
         }

    }


    private static Map<String,String[]> getGoodsPrice() throws Exception{
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/ljf/Downloads/goodsprice.txt"))));
        String line=null;
        Map<String,String[]> res=new HashMap<>();
         while ((line=br.readLine())!=null){
             String[] data=line.split("\t");
            // System.out.println(JSON.toJSONString(data));
             res.put(data[0]+","+data[1],data);
        }

        return res;
    }
}
