package ssd.test;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

public class OracleTest {
    /**
     * jdbc访问数据库连接串地址：
     * <p>
     * HDCARDN_JLJR_ZS=
     * (DESCRIPTION =
     * (LOAD_BALANCE = off)
     * (FAILOVER = on)
     * (ADDRESS_LIST =
     * (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.171.25)(PORT = 1521))
     * (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.171.26)(PORT = 1521))
     * )
     * (CONNECT_DATA =
     * (SERVER = DEDICATED)
     * (SERVICE_NAME = HDCARDPT)
     * )
     *
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {
        InetAddress address = null;

            address = InetAddress.getLocalHost();
            System.out.println(address.getHostAddress().replaceAll("\\.",""));
            System.out.println("".hashCode());
        System.out.println("10.255.1.48".hashCode());
        System.out.println("10.255.1.49".hashCode());
        System.out.println("10255148".hashCode());
        System.out.println(StringUtils.leftPad(Math.abs("10.255.1.48".hashCode() % 100) + "", 2, "0"));
        System.out.println(StringUtils.leftPad(Math.abs("10.255.1.49".hashCode() % 100) + "", 2, "0"));
        System.out.println(StringUtils.leftPad(Math.abs("10.255.1.50".hashCode() % 100) + "", 2, "0"));



        }
}
