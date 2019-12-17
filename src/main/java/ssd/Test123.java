package ssd;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Test123 {
    // 2.私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY ="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCQt1fXSWWc3MhWuzvnmINR5V1EzPiITU6xLHQ7R2HWwwLEXO5ZoUjS/OCBORtLtg6xe2YEhjVM8krb8Lv1Bu2u+NspYQ42JLdPyP4Y/xpIpHclIoGso1JtSGpRNeshfkenF2EUbWv0yHAmbSQcaiEdlUJ19HxsLeOXZNaMprLwXtQ7U/RKdkbsi908Tqlqw66uE1bC/VO160+lPWv3msGfzte51ABETL1dhSlIfMkn/bNXSr9zP3I/UAcasUW4cuOWUFGN2hqNfuiw7j5m9dbzI2EuGKyDB4eRrh8a3zZM9HFeFT+TCIdDD2K7DoVPZLjWVLNYI6oL0q+Yd0CK42o5AgMBAAECggEALKIvmHnNujyqGX3z8vEqB3c7df3V5YwaajCwxJu2hFtvHgsfhEtnP5O41oI7dfV3wKuzgk+2Xr4X8o/kGKWDZ0Tfq3qAvJhEZXozTJhZjfATw0NKRWm2AXilOdjsZU8iYFmomnlJI50yYdelnJHrFsmzTtgSC/d3S3lRZt3/MH1G6ErqAr120tlqK0ZFlLtg04M+gyvJYn/my0isDSLFt8dLJ3hkeZFPx3m/dwvVip4t8CDLwODk8mGDNuRbHxyi5ESYgma7O8CXjkXzkbvBNrZDJPROnXn6RXJqBgDmfL7tuQGEtKTI54uBYFk6b0WGnvG3xW6YSdVILkNZNvp4kQKBgQDfDADbxsN7TQF6yfDRBXuW1BIHPSMV8ljNfve/SVIHSoOqyP/4p36YnJMiG5m0C1oHq04WIdsXu8lqcqKZgUFdRpBezHifLugAkHKxi9jxztO+v96jDG3B6lMCGZJoGt4U7M2AS0BG/LFpuaZNPfwtNopklB+PwTS7qHKbrlKBvQKBgQCmGL61GQBf8L8LvsuDrboCQknuneZXkmPYrNvDu9aHQq0Ac3//VH15nNfZmKAaFWLBPJNGxlidfv+h21GIHTUsEuNC7PVaDdoArHA6xvvgtz7n26XI2T3jzdLoHDoeQZ9HGkSSZ+UOqVO9gbJztuDtv19uLFv6EOUUeuJqswDMLQKBgEjO8UTMqCkKpQ4A93QztvkwNsZYJajXWDeUV6kAdR9eKobIYA46FrAYhErg+ZZ1EilouO+uk4c2zHPMAdncAItDKoNXeZR0ylTM0jO0s9eTLTmaBkHxH67qH7iSwmDrTE1GU3qqlcvWVs9tQ6T5M+VKZXiZhYf2iJEtVmQNTm99AoGARPk7oJqcRDsGKst1Wd43wx5h8zwQ3kbFPcIylKqZIJw4mVpdSdt9K5ZNMGgn/AzrcJFGG3rca7LEVyNrTRnL7dx+EVmzXU0pPWReSpo9GIkgD0pwIuLLx6C+N3MZU10u1mj7Fs0cGqWIR8bnIxRLfY8eAvNa0pJXftS011g0OZUCgYBV82IsoH6w4UPPfK1Hc/0l3JlCFvqUYtQY/hFcmy76KlNBm7HJj90fSHU+NS6uDaLny199y49I0HNKCsqsnayFLg+SMINSgRf7Fb+uIqPHBKbgGhR3473mfJZGhQEp3U/j70jgt1D83XHOybZFJ1gEYGuMdIond2toDem8tnGQkQ==";

    // 3.支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgmZsuWO0gC+YcLBA5mDDodC/REQghWM25Fp9JiTTBnSLOdyoQUM69szu38bAAlEevjHKxnIW3pFd7gGrdRuox4JzfaTI/XUPlirGki+DzLFH8hBGF4PRNtxE1Odbn1MMcYJQXuTE2eXKYsHEsJ3CPGfMDXJoJ6X8XNen81nsJ+SFT/wnpz/pyLE1hihtjzlCCoIIZBsew1EfWlZH1UFan2T36qfRDwaxZtG1DarOUjU8YA10igd5fgl2JIoWG1qCYe6tmPWFGDf16goFebulOrpE9tQUsoejC5ahwmW//h7hC0YdbhXSvJvVAmTo0NtSxwhyhCqnoW8rSf/vrOahVwIDAQAB";
    // 7.编码
    public static String CHARSET = "utf-8";
    public static String SIGNTYPE = "RSA2";

    public static void main(String[] srga) throws AlipayApiException {

        JSONObject object = parsePayNotifyParams("gmt_create=2019-09-27+20%3A26%3A00&charset=utf-8&seller_email=qijijing%40gialen.com&subject=+LEADERS%2F%E4%B8%BD%E5%BE%97%E5%A7%BF%E7%BE%8E%E8%92%82%E4%BC%98%E6%B0%A8%E5%9F%BA%E9%85%B8%E4%BF%9D%E6%B9%BF%E9%9D%A2%E8%86%9C+%E5%9B%9B%E7%9B%92%EF%BC%88%E5%85%B140%E7%89%87&sign=Wz0CGWCEu5sSa7YJpIUu8gSuoTo5PM%2Bn4nz8l608gEkPaAdEHZ5Oxk1e9HEaTz2tgq1naE3Jl6DF1unFdVVflb6iltXtY5bioZo9iDNVLMs%2BeRrVJ3JYynfEzMJLzL2gUgA9y8lNvDZrQ7RL3V2PwrqTBjRCOmek46awPbgy0cXXNy3TWmY%2F9Xwk6Nzd88kMhgC2hke%2BBCO%2BGCzADQ%2FVR2ishn%2FcHXfHXm4agPgRZq%2B9glbRwg45wO440bDE7yFh4BfGcJf0yPZ30ybc%2FYYDc8mIEaNqEUcQ8GioFkQv%2FURUHlTZ%2BJTiceYVXEcvJYh7XIIQaq3%2FDoivF98F62zm0g%3D%3D&buyer_id=2088502759737711&invoice_amount=168.00&notify_id=2019092700222202601037710506887743&fund_bill_list=%5B%7B%22amount%22%3A%22168.00%22%2C%22fundChannel%22%3A%22PCREDIT%22%7D%5D&notify_type=trade_status_sync&trade_status=TRADE_SUCCESS&receipt_amount=168.00&app_id=2018071060512903&buyer_pay_amount=168.00&sign_type=RSA2&seller_id=2088031961907583&gmt_payment=2019-09-27+20%3A26%3A00&notify_time=2019-09-27+20%3A29%3A13&version=1.0&out_trade_no=GP20190927202553355190&total_amount=168.00&trade_no=2019092722001437710546363945&auth_app_id=2018071060512903&buyer_logon_id=735***%40qq.com&point_amount=0.00");
        Map<String,String> map=conversionParams(object);
      Boolean  signVerified = AlipaySignature.rsaCheckV1(map,  ALIPAY_PUBLIC_KEY,  CHARSET,  SIGNTYPE);
      System.out.println(signVerified);
    }

    public static JSONObject parsePayNotifyParams(String params) {
        if (StringUtils.isBlank(params)) {
            return null;
        }
        List<String> paramList = Lists.newArrayList(params.split("&"));
        JSONObject jsonObject = new JSONObject();
        for (String param : paramList) {
            String[] paramArr = param.split("=");
            String key = paramArr[0];
            String value = paramArr[1];
            jsonObject.put(key, value);
        }
        return jsonObject;
    }

    private static Map<String, String> conversionParams(JSONObject paramsJson) {
        Map<String, String> params = Maps.newHashMap();
        for (Iterator<String> iter = paramsJson.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String values = (String) paramsJson.get(name);
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            try {
                System.out.println(values + " " + URLDecoder.decode(values, "utf-8"));

                values = URLDecoder.decode(values, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(name, StringUtils.trimToEmpty(values));
        }
        return params;
    }
}
