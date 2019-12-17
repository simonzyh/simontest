package ssd.test;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTest {


    public static void main(String[] args) throws Exception {

        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://10.255.2.18:3306/gialen_settlement?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true", "gialen_settlement", "gialenGIALEN123!@#");

        Statement statement = connection.createStatement();
        String sql = null;
        Map<String, Long> old = getOld();

        for (Map.Entry<String, Long> entry : old.entrySet()) {
            String barcode = entry.getKey();
            Long skuid = entry.getValue();

            sql = "update  commission_settlement_detail  set sku_id=" + skuid + " where bar_code='" + barcode + "'";
            System.out.println(sql);
            statement.execute(sql);
        }


    }


    public static List<String> getData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/zyh/Documents/sku.txt"))));

        String line = "";
        List<String> data = new ArrayList<>();
        while ((line = br.readLine()) != null) {

            data.add(line);
        }

        return data;
    }

    public static Map<String, Long> getOld() throws IOException {

        Map<String, Long> res = new HashMap<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/zyh/Documents/oldsku.txt"))));

        String line = "";
        while ((line = br.readLine()) != null) {

            String[] arr = line.split("\\s+");
            if (arr.length != 2) continue;
            res.put(arr[1].trim(), Long.parseLong(arr[0].trim()));

        }
        return res;

    }


}
