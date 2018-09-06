import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.*;

public class JdbcTest {

    public static void main(String[] args) throws  Exception, SQLException, FileNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://10.8.24.30:20000/analysis_report?useSSL=false&autoReconnect=true&tinyInt1isBit=false&useUnicode=true&characterEncoding=utf8","root","gjHealth@2018_test");
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("/Users/ljf/zyh/analysis_report_2018-09-06.sql")));
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
                 System.out.println(sql.toString());
                 statement.executeQuery(sql.toString());
                 sql.delete(0,sql.length());
              }


         }
    }
}
