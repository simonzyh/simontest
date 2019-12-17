package ssd.test;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientTest {
    static CloseableHttpClient httpclient = HttpClients.createDefault();


    public static void main(String[] args) throws Exception {
        String s="{\"name\":\"\uD83D\uDC8B十月\uD83D\uDC3E\"}";
        Map<String,String> m=new HashMap<>();
        m.put("name","\uD83D\uDC8B十月\uD83D\uDC3E");
        System.out.println(JSON.toJSONString(m));

        //settlement("19092016231393688");
    }


    //474
    public static void post(String orderSn) throws IOException {
        long l = System.currentTimeMillis();
        String url = "http://shop.gialen.com/nadmin/test/getTotal";
        HttpGet httppost = new HttpGet(url);


        CloseableHttpResponse response = httpclient.execute(httppost);

        HttpEntity resEntity = response.getEntity();
        String body = EntityUtils.toString(resEntity, "UTF-8");
        System.out.println("Response content: " + body);
        Map<String, Object> data = JSON.parseObject(body);



        for (Map.Entry<String, Object> entry : data.entrySet()) {
             delete(entry.getKey());

        }

    }
    public static void settlement(String orderSn) throws IOException {
        String url = "http://10.255.1.48:1085/settlement/test/test1?orderSn=" + orderSn;
        HttpGet httppost = new HttpGet(url);


        CloseableHttpResponse response = httpclient.execute(httppost);

        HttpEntity resEntity = response.getEntity();
        String body = EntityUtils.toString(resEntity, "UTF-8");
        System.out.println("Response content: " + body);


    }
    public static void delete(String userId) throws IOException {
        String url = "http://shop.gialen.com/nadmin/test/delTotal?userId=" + userId;
        HttpGet httppost = new HttpGet(url);


        CloseableHttpResponse response = httpclient.execute(httppost);

        HttpEntity resEntity = response.getEntity();
        String body = EntityUtils.toString(resEntity, "UTF-8");
        System.out.println("Response content: " + body);


    }
}
