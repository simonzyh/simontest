package ssd.gptest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class 数据导出 {
    static Connection gpCon = null;

    public static void main1(String[] ags) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/zyh/2020-05-13.csv"))));
        PrintWriter pw = new PrintWriter("/Users/zyh/2020-05-13-1.csv");
        int size = 0;
        String line = null;
        int excelNo = 1;
        while ((line = br.readLine()) != null) {

            size++;
            if (size >= 1000000) {
                size = 0;
                pw.flush();
                pw.close();
                pw = new PrintWriter("/Users/zyh/2020-05-13-" + (++excelNo) + ".csv");
                System.out.println(excelNo);
            }
            pw.println(line.trim());
            pw.flush();

        }

    }


    // 2、Greenplum
    public static void getGreenplumConnection() throws ClassNotFoundException, SQLException {
        // 加载数据库驱动
        Class.forName("com.pivotal.jdbc.GreenplumDriver");
        //System.out.println("测试加载数据库成功");
        gpCon = DriverManager.getConnection("jdbc:pivotal:greenplum://10.255.1.92:5432;DatabaseName=gp_sydb", "postgres", "postgres123456");
        gpCon.setAutoCommit(true);
    }

    public static void main(String[] args) throws Exception {
        PrintWriter pw = new PrintWriter("/Users/zyh/2020-05-22-1.csv");
        HashSet<String> sss = getxxx();
        getGreenplumConnection();
        Statement statement = gpCon.createStatement();
        String sql = "";
        int page = 0;
        String lastphone = "";
        int size = 0;
        int excelNo = 1;
        while (true) {
            long l1 = System.currentTimeMillis();
            sql = "select cellphone from ods_gialen_data_crm_member   where   and cellphone > '" + lastphone + "' order by cellphone limit 10000";
            List<String> sqls = getResult(statement, sql);
            for (String s : sqls) {
                lastphone = s;

                if (StringUtils.isBlank(s)) {
                    continue;
                }
                if (!s.startsWith("1")) {
                    continue;
                }
                if (s.startsWith("10")) {
                    continue;
                }
                if (s.length() != 11) {
                    continue;
                }
                if (sss.contains(s.trim())) {
                    continue;
                }
                size++;
                pw.println(s.trim());
                if (size >= 1000000) {
                    pw.flush();
                    pw.close();
                    pw = new PrintWriter("/Users/zyh/2020-05-22-" + (++excelNo) + ".csv");
                    size = 0;
                }
            }
            pw.flush();
            System.out.println(page++ + " " + sql + " " + (System.currentTimeMillis() - l1));

            if (CollectionUtils.isEmpty(sqls)) {
                break;
            }
        }

    }

    private static HashSet<String> getxxx() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/zyh/1.txt"))));
        String line = null;
        HashSet<String> list = new HashSet<>();
        while ((line = br.readLine()) != null) {
            list.add(line.trim());
        }

        return list;

    }

    private static String columnDateTypeT(String type) {
        if (type.contains("bigint")) {
            return "int8";
        }
        if (type.contains("int")) {
            return "int4";
        }
        if ("longtext".equalsIgnoreCase(type)) {
            return "text";
        }
        if ("double".equalsIgnoreCase(type)) {
            return "decimal(11,4)";
        }
        if (type.contains("unsigned")) {
            return type.replace("unsigned", "");
        }
        if ("datetime".equalsIgnoreCase(type)) {
            return "timestamp";
        }
        return type;
    }


    protected static List<String> getResult(Statement statement, String sql) throws SQLException {
        ResultSet resultSet = statement.executeQuery(sql);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        List res = new ArrayList();
        int j = 0;
        while (resultSet.next()) {


            String value = resultSet.getObject(1) + "";
            res.add(value);


        }
        return res;
    }

}
