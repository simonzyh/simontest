package fastjson;

import com.alibaba.fastjson.JSON;
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
          System.out.println(URLEncoder.encode("pageId=1&supplierToken=50a1b56f5fa94126a7648ebc1d440271","UTF-8"));
        Integer ii = null;
        System.out.println(2 == ii);
        Map<String, Long> map = new HashMap<String, Long>();
        Long l11 = 1010012200785506277L;

        map.put("long", l11);
        String s1 = JSON.toJSONString(map);
        System.out.println(s1 + " " + JSON.parseObject(s1, Map.class));
        Group group = new Group();
        group.setId(0);
        group.setName("admin");


        for (int i = 0; i < 1; i++) {
            User guestUser = new User();
            guestUser.setId(i);
            guestUser.setName("guest" + i);
            group.getUsers().add(guestUser);
        }

        ObjectMapper mapper = new ObjectMapper();
        Long l1 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            JSON.toJSONString(group);
        }
        Long l2 = System.currentTimeMillis();


        for (int i = 0; i < 1000; i++) {
            mapper.writeValueAsString(group);
        }
        System.out.println((l2 - l1) + "    " + (System.currentTimeMillis() - l2));
    }
}
