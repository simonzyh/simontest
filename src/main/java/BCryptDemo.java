import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BCryptDemo {
    public static void main(String[] args) throws IOException {
        Pattern pattern = Pattern.compile("(\\{.*?\\})");
        String url = "{123}/{qwe}";
        Matcher matcher = pattern.matcher(url);
        while (matcher.find()) {
            String pathName = matcher.group();
            String value = 123 + "";
            url = url.replace(pathName, value);
        }
        System.out.println(url);

        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("logid", "哈哈哈"));
        formparams.add(new BasicNameValuePair("caller", "mapi"));
        HttpEntity reqEntity = new UrlEncodedFormEntity(formparams, Charset.forName("UTF-8"));
        //添加请求日志

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        reqEntity.writeTo(baos);
        if (url.indexOf("?") > 1) {
            url = url + "&" + baos.toString();
        } else {
            url = url + "?" + baos.toString();
        }

        System.out.println(url);

    }
}