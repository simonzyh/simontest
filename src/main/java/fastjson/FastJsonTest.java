package fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URLEncoder;
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
