package ssd.logan;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UvTest {


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/zyh/Downloads/log/H5.gialen.com2.log"))));
        List<String[]> dataList = new ArrayList<>();
        String line = null;
        Set<String> set1 = new HashSet();
        Set<String> set2 = new HashSet();
        int i1 = 0;
        int i2 = 0;
        while ((line = br.readLine()) != null) {
            if (StringUtils.isBlank(line)) {
                continue;
            }
            if (!line.contains("15/Apr/2019")) {
                continue;
            }

            String[] data = line.split("\\s+");
            String path = data[6].split("\\?")[0];

            if (line.contains("APP_")) {
                String phone = line.substring(line.indexOf("APP") + 4, line.indexOf("APP") + 15);
                set1.add(phone);
                i1++;

                if (!line.contains("/m/share/productDetail") && !line.contains("/m/product")) {
                    continue;
                }

                if (line.contains("12160")) {
                    set2.add(phone);
                    System.out.println(data[6]);
                    i2++;
                }
            }
            String cookie = data[10];

        }
        System.out.println(set1.size() + " " + set2.size() + " " + i1 + " " + i2);

    }


    private String getAppUser(String cookie) {
        if (StringUtils.isBlank(cookie)) {
            return null;
        }
        if (cookie.startsWith("APP")) {
            return cookie;
        }
        return null;
    }
}
