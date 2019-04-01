package ssd.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: simon
 * Date: 2015/7/13
 * Time: 19:39
 * description：
 */
public class FastJsonTest {


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/ljf/zyh/district.json"))));
        String line = br.readLine();
        JSONObject object = JSON.parseObject(line);

        List<String> all = new ArrayList<>();

        JSONArray districts = object.getJSONArray("districts");
        handel(null, object, all);
        PrintWriter pw = new PrintWriter("/Users/ljf/zyh/workspace/gaoji-weixin-ma/src/main/resources/districts-init.sql");
        for (String s : all) {
            pw.println(s);
        }
        System.out.println(all.size());
        pw.flush();

    }

    private static void handel(JSONObject parent, JSONObject obj, List<String> all) {
        JSONArray childs = obj.getJSONArray("districts");
        String sql = "insert into wx_districts(city_code,ad_code,level,center,name,parent_code) values (";
        String citycode = obj.getString("citycode");
        if (null == citycode || citycode.contains("[")) {
            sql += " null ,";
        } else {
            sql += "'" + obj.getString("citycode") + "',";
        }
        sql += "'" + obj.getString("adcode") + "',";
        sql += "'" + obj.getString("level") + "',";
        sql += "'" + obj.getString("center") + "',";
        sql += "'" + obj.getString("name") + "',";
        if (null == parent) {
            sql += " null )";

        } else {
            sql += "'" + parent.getString("adcode") + "') ; ";

        }

        all.add(sql);
        if (childs == null || childs.size() == 0) {
            return;
        }
        int i = 0;
        for (Object object : childs) {
            JSONObject child = (JSONObject) object;

            if ("district".equals(obj.getString("level"))) {

                child.put("adcode", child.getString("adcode") + (i++));

            }
            handel(obj, child, all);
        }

    }


}
