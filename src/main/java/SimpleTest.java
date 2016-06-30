import au.com.bytecode.opencsv.CSVReader;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import test.DateTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yehua.zyh on 2016/6/20.
 */
public class SimpleTest {
    public static void test1(String[] args) throws Exception {
        CSVReader reader=new CSVReader(new InputStreamReader(new FileInputStream(new File("d:\\1.csv"))));
        List<String> data=new ArrayList<String>();
        for(String[] arr:reader.readAll()){
            if(NumberUtils.isNumber(arr[0].trim())){
                data.add(arr[0].trim()+"-1");
            }else{
                System.out.println("error+"+arr.toString());
            }
            if(data.size()>=20){
                System.out.println();
                System.out.println("http://mkt.cbbs.tmall.com/supplierpage/page/addSupplierMonthPageLimit.json?data="+ StringUtils.join(data.iterator(), ","));
                data.clear();
            }
        }
        System.out.println("http://mkt.cbbs.tmall.com/supplierpage/page/addSupplierMonthPageLimit.json?data="+StringUtils.join(data.iterator(),","));

    }
    public static void test2(String[] args) throws Exception {

        CSVReader reader=new CSVReader(new InputStreamReader(new FileInputStream(new File("d:\\test1.csv"))));
        List<String> data=new ArrayList<String>();
        for(String[] arr:reader.readAll()){
            System.out.println(arr);
            data.add(arr[1]+":"+arr[0]);
        }
        System.out.println(StringUtils.join(data.iterator(),";"));
    }

    public static void test3(String[] args){
        String p4pSellerWithSmSupplierStr="100036666:100036666;2208526039:100037395;1790973264:100036934;299244686:100035168;628189716:100034450;880734502:100037803;2250259915:100035078;605623409:100034000;1652554937:100034338;700459267:100040969;100034793:1660765956;100034131:685092642;100037915:2436467110;100037387:2279845441;100036871:739764527;100038063:368609005;100034452:379424083;100037633:797591834;100034089:921052700;100034121:735011836;100037360:684978371;100033869:1611505160;100038050:2119428048;100034445:2023036960;100034239:699634751;100034343:1015804794;100033972:337639595;100036958:732501769;100035487:109484039;100036889:1772698439;100033778:745949152;100034338:1773212562;100034009:333792045;100034417:2185427664;100036907:2074627616;100034038:2328901391;100037697:2108418347;100038135:2894571536;100034440:105152347;100038502:1664976033;100038133:747872819;100034014:7180;100037529:12741736;100040344:783465912;100039780:2657760996;100040732:846338659;100035367:2078085190;100034197:2424338511;100034909:2369922932;100035146:844537169;100037729:1613774608;100035485:1032555792;100037142:1040084460;100034546:1061555146;100034275:395597533;100034413:888092555;100037550:2453986920;100036711:401641740;100035830:1129431561;100034573:681185851;100034903:1777552687;100036045:452082797;100037671:2261075216;100033979:818934540;100037090:1657487457;100034295:291107303;100034038:2378610884";
        String[] paris = p4pSellerWithSmSupplierStr.split(";");
        for (String pair : paris) {
            String[] kv = pair.split(":");
            System.out.println(Long.parseLong(kv[0].trim())+" "+ Long.parseLong(kv[1].trim()));
        }
    }
  public static void main(String[] args){
      Calendar cal=Calendar.getInstance();
      cal.setTime(DateTools.parse("2016-01-01","yyyy-MM-dd"));
      System.out.println(cal.get(Calendar.MONTH));
  }
    static class testA{
        String name;
    }

}
