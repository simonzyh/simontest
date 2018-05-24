import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Created by yehua.zyh on 2018/4/19.
 */
public class FileTest {

    public static void main(String[] args)throws   Exception{
        String orgCode="V_F51_PFBK";
         System.out.println(orgCode.substring(orgCode.lastIndexOf("_")+1));
         Map<String,Object> mapData=new HashMap<>();
        mapData.put("due_date","2018-05-14 16:20:46.708797");
        String date= (String)mapData.get("due_date");
        System.out.println(date.substring(0,date.lastIndexOf(".")));
        System.out.println( DateUtils.parseDate(date.substring(0,date.lastIndexOf(".")), "yyyy-MM-dd HH:mm:ss"));
    }

}

