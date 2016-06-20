/**
 * @Title: InsertTestData.java
 * @Package: test
 * @Description: TODO
 * @author 张业华
 * @date 2014-3-27 下午02:47:36
 */


package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author 张业华
 * @Description
 * @date 2014-3-27 下午02:47:36
 */

public class InsertTestData {
    static int i = 0;

    /**
     * @param args
     * @throws Exception
     * @Description
     * @author 张业华
     */

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://192.168.42.90:8066/dbtest", "test", "test");


        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery("select  t.name from student t	where id=1 ");
        rs.next();
        System.out.println(rs.getString(1));


    }


}
