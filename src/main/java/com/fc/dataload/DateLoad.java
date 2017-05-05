/**
 * @Title: DateLoad.java
 * @Package: com.fc.dataload
 * @Description: TODO
 * @author 张业华
 * @date 2014年8月12日 下午4:45:23
 */


package com.fc.dataload;

import com.fc.pojo.Ball;
import com.fc.util.FcConstants;
import com.fc.util.FileUtils;
import com.fc.util.Tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * @author 张业华
 * @Description
 * @date 2014年8月12日 下午4:45:23
 */

public class DateLoad {


    /**
     * @param
     * @throws Exception
     * @throws IOException
     * @Description
     * @author 张业华
     */

    public static void load() throws IOException, Exception {
        List<Ball> list = new ArrayList<Ball>();

        list.addAll(Tools.parse("http://baidu.lecai.com/lottery/draw/list/50?type=range&start=2003011&end=2019012"));


        Collections.sort(list, new Comparator<Ball>() {

            public int compare(Ball o1, Ball o2) {
                return o2.getDataFix().compareTo(o1.getDataFix());
            }
        });
        FcConstants.balllist = list;
        FileUtils.saveDouble(list);
    }

}
