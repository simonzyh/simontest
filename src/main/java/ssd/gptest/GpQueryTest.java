package ssd.gptest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GpQueryTest {
    static Connection con21 = null;
    static Connection gpCon = null;

    // 2、Greenplum
    public static void getGreenplumConnection() throws ClassNotFoundException, SQLException {
        // 加载数据库驱动
        Class.forName("com.pivotal.jdbc.GreenplumDriver");
        //System.out.println("测试加载数据库成功");
        gpCon = DriverManager.getConnection("jdbc:pivotal:greenplum://10.255.1.92:5432;DatabaseName=gp_sydb", "postgres", "postgres123456");
        Class.forName("com.mysql.jdbc.Driver");
        con21 = DriverManager.getConnection("jdbc:mysql://10.255.2.21:3306/gialen_order?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true", "report", "reportREPORT1!");
        gpCon.setAutoCommit(true);
        con21.setAutoCommit(true);
    }

    public static void main(String[] args) throws Exception {
        getGreenplumConnection();
        Statement statement = gpCon.createStatement();
        List<String> sqls = queryTableSchema();
        for (String s : sqls) {
           // statement.execute(s);
            System.out.println(s);
        }

        System.out.println(GpQueryTest.class.getName().substring(GpQueryTest.class.getName().lastIndexOf(".")));
    }

    private static List<String> queryTableSchema() throws Exception {
        List<String> res = new ArrayList<>();
        Statement statement = con21.createStatement();
        Statement statement1 = con21.createStatement();
        ResultSet resultSet = statement.executeQuery("select TABLE_SCHEMA,TABLE_NAME from information_schema.TABLES where TABLE_SCHEMA    in ('gialen_settlement' )");

        while (resultSet.next()) {
            String TABLE_SCHEMA = resultSet.getString(1);
            String TABLE_NAME = resultSet.getString(2);
            String table_name = "ods_" + TABLE_SCHEMA + "_" + TABLE_NAME;

            String createTableSql = "CREATE TABLE " + table_name + "(";
            ResultSet resultSet1 = statement1.executeQuery("select COLUMN_NAME,COLUMN_TYPE from information_schema.COLUMNS where TABLE_NAME='" + TABLE_NAME + "' and TABLE_SCHEMA='" + TABLE_SCHEMA + "'");

            String pk = "";
            while (resultSet1.next()) {
                String COLUMN_NAME = resultSet1.getString(1);
                String COLUMN_TYPE = resultSet1.getString(2);
                COLUMN_TYPE = columnDateTypeT(COLUMN_TYPE);
                if (StringUtils.isBlank(pk)) {
                    pk = COLUMN_NAME;
                }
                createTableSql += COLUMN_NAME + "  " + COLUMN_TYPE;
                if (COLUMN_TYPE.contains("varchar")) {
                    createTableSql += "COLLATE \"pg_catalog\".\"default\"";
                }

                createTableSql += ",";
            }
           //createTableSql += " dt varchar(10) );";
            createTableSql += "CONSTRAINT \"" + table_name + "key\" PRIMARY KEY (\"" + pk + "\")) distributed by (" + pk + ") ;";
            res.add(createTableSql);
        }
        return res;
    }

    private static void syncAllTable() throws Exception {
        Statement statement = con21.createStatement();
        ResultSet resultSet = statement.executeQuery("select TABLE_SCHEMA,TABLE_NAME from information_schema.TABLES where TABLE_SCHEMA not  in ('information_schema','mysql','performance_schema','sys','xxl-job')");

        while (resultSet.next()) {
            String TABLE_SCHEMA = resultSet.getString(1);
            String TABLE_NAME = resultSet.getString(2);
            try {
                System.out.println("===" + TABLE_SCHEMA + "." + TABLE_NAME + " start");
                syncData(TABLE_SCHEMA, TABLE_NAME);
                System.out.println("===" + TABLE_SCHEMA + "." + TABLE_NAME + " end");

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("同步数据异常:" + e.getMessage() + " " + TABLE_NAME);
            }
        }
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


    private static void syncData(String database, String tablename) throws Exception {
        long maxId = 0;
        Statement statement = con21.createStatement();
        Statement statementGP = gpCon.createStatement();
        statementGP.execute("TRUNCATE table ods_" + database + "_" + tablename + "_day");
        String insertSql = "insert into ods_" + database + "_" + tablename + " values ";
        while (true) {
            String sql = "select * from " + database + "." + tablename + " where id<" + maxId + " order by id limit 5000";
            List<List<String>> data = getResult(statement, sql);
            if (CollectionUtils.isEmpty(data)) {
                break;
            }
            System.out.println("同步进度:" + database + "." + tablename + " " + maxId + " " + data.size());

            for (List<String> d : data) {
                Long id = Long.parseLong(d.get(0));
                if (id > maxId) {
                    maxId = id;
                }
                insertSql += "('";

                insertSql += StringUtils.join(d, "','").replaceAll("'null'", "null");
                insertSql += "','20191122'),";
            }


        }
        statementGP.execute(insertSql.substring(0, insertSql.length() - 1));

    }

    protected static List<List<String>> getResult(Statement statement, String sql) throws SQLException {
        ResultSet resultSet = statement.executeQuery(sql);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        List res = new ArrayList();
        int j = 0;
        while (resultSet.next()) {

            List<String> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String value = resultSet.getObject(i) + "";
                if ("TINYINT".equalsIgnoreCase(resultSetMetaData.getColumnTypeName(i))) {
                    if ("true".equalsIgnoreCase(value)) {
                        value = "1";
                    }
                    if ("false".equalsIgnoreCase(value)) {
                        value = "0";
                    }
                }

                row.add(value);

            }
            res.add(row);


        }
        return res;
    }

}
