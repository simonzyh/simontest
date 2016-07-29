package concurrent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fasttext.sec.xss.XssXppScanner;
import com.taobao.security.util.SecurityUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yehua.zyh on 2015/11/11.
 */
public class AtomicTest {
    public static void main(String[] args) {
        AtomicInteger ai = new AtomicInteger();
        System.out.println(ai);
        ai.getAndIncrement();
        System.out.println(ai);

        AtomicBoolean ab = new AtomicBoolean();
String s="https://content.tmall.com/wow/pegasus/subject/1/725677994/4038384?id=4038384&&channel=chaoshi&wh_channel=chaoshi4&gccpm=5855752.815.2.subject-chaoshi-2004.709074";

        System.out.println(s.replaceFirst("\\?","?site=HD&"));
        Map<String,Object> joMap=new HashMap<String,Object>();
        joMap.put("type","3\"</script><script>alert(1)</script>d=\"");
        System.out.println(JSON.toJSONString(joMap, SerializerFeature.BrowserSecure));
        System.out.println(SecurityUtil.escapeHtml("'3\"</script><script>alert(1)</script>d=\""));

    }
}
