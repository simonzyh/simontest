import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JdbcTest {


    public static void main(String[] args) throws Exception {
        update();
    }

    public static void insert() throws Exception, SQLException, InterruptedException, NoSuchMethodException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/ljf/Downloads/商品佣金录入（新增的商品佣金）-20190306.csv"))));
        String line = null;
        Map<String, String[]> stringMap = getGoodsPrice();
        Set<String> s = new HashSet<>();
        while ((line = br.readLine()) != null) {
            String[] arr = line.split(";");
            String countryCn = arr[0].trim();
            Integer countryId = 0;
            if ("泰国".equals(countryCn)) {
                countryId = 3;
            } else if ("沙特阿拉伯".equals(countryCn)) {
                countryId = 2;
            }

            String goodsCode = arr[1].trim();
            if (!s.add(countryId + "," + goodsCode)) {
                //System.out.println(countryId+","+goodsCode+" 重复");
                continue;
            }
            // System.out.println(countryId+","+goodsCode);
            String ratio = (new BigDecimal(arr[2].replace("%", "")).divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP).doubleValue()) + "";
            String[] pricedata = stringMap.get(countryId + "," + goodsCode);
            String title = pricedata[2];
            String price = pricedata[3];
            //  System.out.println(countryId+" "+goodsCode+" "+ratio+" "+title+" "+price);
            String sql = "  INSERT INTO `orko_distribution`.`distribution_goods_ratio`( `goods_code`, `create_at`, `update_at`, `is_delete`, `goods_name`, `goods_price`, `goods_pic`, `status`, `commission_ratio`, `country_id`, `update_user`) VALUES (";
            sql += "'" + goodsCode + "' , now(), now(),0 ,";
            sql += "'" + title + "'," + price + ", NULL, 0, " + ratio + ", " + countryId + ", NULL) ;";
            System.out.println(sql);
        }

    }


    private static Map<String, String[]> getGoodsPrice() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/ljf/Downloads/goodsprice.txt"))));
        String line = null;
        Map<String, String[]> res = new HashMap<>();
        while ((line = br.readLine()) != null) {
            String[] data = line.split("\t");
            res.put(data[0].trim() + "," + data[1].trim(), data);
        }

        return res;
    }


    private static void update() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/ljf/Downloads/商品佣金录入（更新的商品佣金）-20190305.csv"))));
        String line = null;
        Map<String, String[]> stringMap = getGoodsPrice();
        Set<String> s = new HashSet<>();
        while ((line = br.readLine()) != null) {
            String[] arr = line.split(";");
            String countryCn = arr[0].trim();
            Integer countryId = 0;
            if ("泰国".equals(countryCn)) {
                countryId = 3;
            } else if ("沙特阿拉伯".equals(countryCn)) {
                countryId = 2;
            }

            String goodsCode = arr[1].trim();
            String ratio = (new BigDecimal(arr[2].replace("%", "")).divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP).doubleValue()) + "";

            String sql = "update distribution_goods_ratio set update_at=now(),commission_ratio=" + ratio + " where goods_code='" + goodsCode + "' and country_id=" + countryId + " ;";
            System.out.println(sql);
        }
    }
}
