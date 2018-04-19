package fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * User: simon
 * Date: 2015/7/13
 * Time: 19:39
 * description：
 */
public class FastJsonTest {


    public static void main(String[] args) throws IOException {
        ABC abc=new ABC();
        abc.setA(40000000D);
        String s=JSON.toJSONString(abc);
        System.out.println(s);
        ABC a=JSON.parseObject(s,ABC.class);
        System.out.println(a.getA());


        File f=new File("/Users/yehua.zyh/1.png");
        InputStream inputStream=new FileInputStream(f);
        byte[] bytes=new byte[inputStream.available()];
        inputStream.read(bytes);
        System.out.println( bytes.length+" "+ Arrays.toString(bytes));

        File f1=new File("/Users/yehua.zyh/2.png");
        OutputStream outputStream=new FileOutputStream(f1);
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
        //f1.createNewFile();

        System.out.println("V_F51_SZBK".substring("V_F51_SZBK".lastIndexOf("_")+1));

    }

   static class ABC {
       @JSONField()
        private Double a;

        public Double getA() {
            return a;
        }

        public void setA(Double a) {
            this.a = a;
        }
    }
}
