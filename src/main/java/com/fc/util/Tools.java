/**
 * @Title: Tools.java
 * @Package: com.fc.util
 * @Description: TODO
 * @author 寮犱笟鍗�
 * @date 2014骞�鏈�2鏃�涓嬪崍4:49:19
 */


package com.fc.util;

import com.fc.pojo.Ball;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.*;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author 寮犱笟鍗�
 * @Description
 * @date 2014骞�鏈�2鏃�涓嬪崍4:49:19
 */

public class Tools {

    public static String doGet(String url) {
        HttpClient client = new HttpClient();
        GetMethod get = null;

        try {

            client.getHttpConnectionManager().getParams().setConnectionTimeout(30 * 1000);
            client.getHttpConnectionManager().getParams().setSoTimeout(30 * 1000);

            get = new GetMethod(url);

            int status = client.executeMethod(get);
            String resultStr = get.getResponseBodyAsString();
            System.out.println("璋冪敤鎺ュ彛:url=" + url + "  status=" + status);
            return resultStr;


        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            get.releaseConnection();

        }
        return null;
    }


    public static List<Ball> parse(String url) throws Exception, IOException {
        Pattern p = Pattern.compile("<em>.+?</em>");
        Matcher m = null;
        List<Ball> res = new ArrayList<Ball>();
        try {
            Parser myParser = new Parser(new URL(url).openConnection());

            myParser.setEncoding("UTF-8");
            NodeList nodeList = null;

            NodeFilter tableFilter = new NodeClassFilter(TableTag.class);
            NodeFilter spanFilter = new NodeClassFilter(Span.class);
            Parser spanParse = new Parser();
            NodeList spanNodeList = null;


            NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);
            Parser linkParse = new Parser();
            NodeList linkNodeList = null;
            spanParse.setEncoding("UTF-8");
            nodeList = myParser.parse(tableFilter);

            TableTag roottag = (TableTag) nodeList.elementAt(0);

            TableRow[] rows = roottag.getRows();
            for (int j = 1; j < rows.length; j++) {
                TableRow tr = (TableRow) rows[j];
                TableColumn[] td = tr.getColumns();

                //瑙ｆ瀽date
                linkParse.setInputHTML(td[0].getStringText());

                linkNodeList = linkParse.parse(linkFilter);

                m = p.matcher(td[2].getStringText());
                String blue = "";
                List<Integer> ballList = new ArrayList<Integer>();
                int i = 0;
                while (m.find()) {

                    blue = m.group().replaceAll("em", "")
                            .replaceAll("<", "").replaceAll(">", "").replaceAll("/", "");
                    if (i == 6) {
                        break;
                    }
                    i++;

                    ballList.add(Integer.parseInt(blue));
                }


                java.util.Collections.sort(ballList);

                res.add(new Ball(linkNodeList.elementAt(0).getFirstChild().getText(),
                        ballList.get(0),
                        ballList.get(1),
                        ballList.get(2),
                        ballList.get(3),
                        ballList.get(4),
                        ballList.get(5),
                        Integer.parseInt(blue)));


            }
//                    System.out.println(nodeList.elementAt(i)+ " "+ i);


        } catch (ParserException e) {
            e.printStackTrace();
        }
        return res;
    }


    public static void main(String[] args) throws IOException, Exception {
        parse("http://baidu.lecai.com/lottery/draw/list/50?d=2004-01-01");

        Calendar xRange = Calendar.getInstance();
        xRange.add(Calendar.DATE, -(0));
        System.out.println(xRange);

    }
}
