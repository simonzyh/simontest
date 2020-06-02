package ssd.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;

public class 店主回访 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users//zyh/店主在4.10-4.27有多少人回来访问商城.csv"))));
        Class.forName("com.pivotal.jdbc.GreenplumDriver");
        Connection con21 = DriverManager.getConnection("jdbc:pivotal:greenplum://10.255.1.92:5432;DatabaseName=gp_sydb", "postgres", "postgres123456");
        Statement statement = con21.createStatement();
        String readLine = null;
        int i = 0;
        while ((readLine = br.readLine()) != null) {
            if (i++ == 0) continue;
            String[] arr = readLine.split(",");
            String id = arr[0];
            String phone = arr[1];
            String vistTime = queryVistDate(statement, id);
            System.out.println(id + "," + phone + "," + (vistTime == null ? "未到访" : "有到访"));
        }
    }

    private static String queryVistDate(Statement statement, String userId) throws SQLException {
        String sql = "select create_date from ods_gialen_data_data_point where create_date >'2020-04-10' and create_date<='2020-04-28' and user_id=" + userId + " limit 1";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            return resultSet.getObject(1) + "";
        }
        return null;
    }
}
