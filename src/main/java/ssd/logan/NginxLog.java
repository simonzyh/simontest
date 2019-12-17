package ssd.logan;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NginxLog {
    static List<String> black = Lists.newArrayList("/gdata", "/cmsstatic",
            "/m", "/product", "/static", "/admin", "/intf/shop/exclusive",
            "/callBack/aliPay", "/intf/wxMiniProgram/product",
            "/intf/search/getHotSearch", "/intf/sysPara/advertActivity",
            "/intf/user/virtualCoin", "/intf/wxMiniProgram/user",
            "/intf/hotArea", "/intf/shop/getShopBaseInfo", "/intf/hotArea",
            "/intf/user/order/deleteOrder", "/intf/user/getShopBaseInfo",
            "/intf/product/getProductIntroduce", "/intf/sysPara/versionCheck",
            "/intf/product/category/rushPurchaseTime", "/intf/userlogin",
            "/intf/present/getPresentInfo", "/intf/sysPara/getAdvertiseList",
            "/intf/present/getPresentInfo", "/intf/user/updatePushToken",
            "/intf/user/getAreaInfo", "/intf/dataPoint/eventTrack/addEventRecord",
            "/intf/shop/getShopUserInfo", "/intf/user/addressInfo/getReceiveAddress",
            "/intf/baseData/getUserLeverConfigList", "/intf/product/getProductArtsyList"

    );

    public static void main(String[] file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/zyh/Downloads/419.txt"))));
        Map<String, NginxL> map = new HashMap<>();
        String line = null;
        while ((line = br.readLine()) != null) {
            try {
                if (!line.contains("Go-http-client")) {
                    continue;
                }
                String[] arr = line.split("\\s+");
                String url = arr[3];
                String code = arr[5];
                String time = arr[7];
                Boolean skip = false;
                for (String b : black) {
                    if (url.startsWith(b)) {
                        skip = true;
                        break;
                    }
                }
                if (skip) {
                    continue;
                }
                if (!map.containsKey(url)) {
                    map.put(url, new NginxL());
                }
                NginxL nginxL = map.get(url);
                nginxL.allNum++;
                if ("200".equals(code)) {
                    nginxL.succNum++;
                    int timei = (int) (Double.parseDouble(time) * 1000);
                    nginxL.succTime = nginxL.succTime + timei;
                } else {
                    nginxL.errorNum++;
                }
                map.put(url, nginxL);
            } catch (Exception e) {
                e.getMessage();
                System.out.println(line);
                throw e;
            }


        }
        for (Map.Entry<String, NginxL> entry : map.entrySet()) {
            if (entry.getValue().allNum < 500) {
                continue;
            }
            if ((entry.getValue().succTime / entry.getValue().succNum) < 200) {
                continue;
            }
            System.out.println((entry.getValue().succTime / entry.getValue().succNum) + " " + entry.getKey() + " " + JSON.toJSONString(entry.getValue()));
        }
    }
}

@Data
class NginxL {
    int allNum = 0;
    int succNum = 0;
    int errorNum = 0;
    int succTime = 0;
}