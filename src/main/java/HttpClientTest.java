import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class HttpClientTest {
    static CloseableHttpClient httpclient = HttpClients.createDefault();
    static RequestConfig DEFAULT_CONFIG = RequestConfig.custom()
            .setConnectTimeout(5000)//一、连接超时：connectionTimeout-->指的是连接一个url的连接等待时间
            .setSocketTimeout(5000)// 二、读取数据超时：SocketTimeout-->指的是连接上一个url，获取response的返回等待时间
            .setConnectionRequestTimeout(5000)
            .build();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            test();
        }
    }

    private static void test() throws InterruptedException {
          List<Long> times = new ArrayList<Long>();

        for (int i = 0; i < 10; i++) {

            new Thread() {


                public void run() {
                    for (int j = 0; j < 100; j++) {
                        post(times);
                    }

                }
            }.start();
        }

        Thread.sleep(1000 * 10 * 2);
        long total = 0;
        for (Long l : times) {
            total += l;
        }
        System.out.println(times.size() + "   " + total / times.size());
    }

    //474
    private static void post(List<Long> times) {
        long l = System.currentTimeMillis();
        String url = "http://localhost:1081/ma-api/ma/collage/detail/1";
        HttpPost httppost = new HttpPost(url);

        try {
             CloseableHttpResponse response =httpclient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                  //  System.out.println("Response content: " + EntityUtils.toString(resEntity, "UTF-8"));
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        times.add(System.currentTimeMillis() - l);

    }


    static PoolingHttpClientConnectionManager cm = null;

    static {


        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .build();
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);
    }

    public static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();

        /*CloseableHttpClient httpClient = HttpClients.createDefault();//如果不采用连接池就是这种方式获取连接*/
        return httpClient;
    }
}
