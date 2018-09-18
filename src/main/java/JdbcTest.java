import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.*;

public class JdbcTest {

    public static void main1(String[] args) throws  Exception, SQLException, FileNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://10.8.24.30:20000/analysis_report?useSSL=false&autoReconnect=true&tinyInt1isBit=false&useUnicode=true&characterEncoding=utf8","root","gjHealth@2018_test");
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("/Users/ljf/zyh/oa_expense_2018-09-07.sql")));
         String line=null;
         int i=0;
         StringBuffer sql=new StringBuffer();
        Statement statement=con.createStatement();

         while((line=br.readLine())!=null){
             if(line.startsWith("#")||line.startsWith("/")){
                 continue;
             }
             sql.append(line+" ");
             if(line.endsWith(";")){
                 System.out.println(i++);
                 statement.execute(sql.toString());
                 sql.delete(0,sql.length());
              }


         }
    }



    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@//192.168.0.215:1521/bjhys"; //连接字符串

         String username = "gaoji"; //用户名

        String password = "gaoji_2018"; //密码

        Connection conn = DriverManager.getConnection(url, username, password);
        ResultSet resultSet=conn.createStatement().executeQuery(" select * from  bjhys.pt_orderhz");
        ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
        int columnCount=resultSetMetaData.getColumnCount();
        while (resultSet.next()){
            StringBuffer data=new StringBuffer();
            for(int i=1;i<=columnCount;i++) {
                data.append(resultSetMetaData.getColumnName(i)+" ="+resultSet.getObject(i)+"  ");
            }
        }
    }
}
