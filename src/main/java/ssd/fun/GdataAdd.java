package ssd.fun;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class GdataAdd {
    static CloseableHttpClient httpclient = HttpClients.createDefault();

    public static void add(String phone) throws IOException {


        String url = "http://jiaomigo.gialen.com/gdata/?phone=" + phone + "&eventCode=index&platform=miniapp&ter=vivo%20X20&terv=jiaolan-minapp-v1&param=%7B%22itemId%22%3A%2215926%22%7D&appType=1";
        HttpGet httppost = new HttpGet(url);


        CloseableHttpResponse response = httpclient.execute(httppost);

        HttpEntity resEntity = response.getEntity();
        String body = EntityUtils.toString(resEntity, "UTF-8");
        System.out.println("Response content: " + body);
    }

    public static void main(String[] args) throws IOException {
        add("186601011225");
    }
}
