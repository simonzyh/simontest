package ssd.logan;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.*;

public class H5Test1 {
    static Map<String,Integer> code = new HashMap<>();

    public static void main(String[] args) throws Exception {

        List<String[]> datalist = getDate("/Users/zyh/Downloads/log/04-06-h5.log");
        // datalist.addAll(getDate("/Users/zyh/Downloads/log/H5.gialen.com.log"));
        Map<String, Integer> item = new HashMap();

        Map<urlparse, Integer> an = new HashMap();

        Map<urlparse, PrintWriter> pws = new HashMap();

        for (String[] data : datalist) {


            String path = data[6].split("\\?")[0];
            if (!data[8].equals("200") && !data[8].equals("302")) {
                if (!data[8].equals("404"))
                    System.out.println(JSON.toJSONString(data));
                continue;
            }
            urlparse urlname = parseUrl(path);
            if (null == urlname) {

                continue;
            }
            if (urlname.equals(urlparse.详情页面)) {
                //  System.out.println(path);
                int i=0;
                if(item.containsKey(path)){
                    i=item.get(path);
                }
                i++;
                item.put(path,i);

            }

            int num = 0;
            if (an.containsKey(urlname)) {
                num = an.get(urlname);
            }
            if (!pws.containsKey(urlname)) {
                PrintWriter pw = new PrintWriter("/Users/zyh/Downloads/log/detail/" + urlname.name() + ".log");
                pws.put(urlname, pw);
            }
            pws.get(urlname).println(StringUtils.join(data," "));

            an.put(urlname, num + 1);

        }
        for (Map.Entry<urlparse, PrintWriter> entry : pws.entrySet()) {
            entry.getValue().flush();
        }

        List<Map.Entry<urlparse, Integer>> dataList = new ArrayList();
        for (Map.Entry<urlparse, Integer> entry : an.entrySet()) {
            dataList.add(entry);
        }

        dataList.sort(Comparator.comparingInt(Map.Entry::getValue)


        );
        for (Map.Entry<urlparse, Integer> entry : dataList) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        int pv=an.get(urlparse.榜单)+an.get(urlparse.热区页面)
                +an.get(urlparse.首页)+an.get(urlparse.限时精选)
                +an.get(urlparse.首页类目)+
                an.get(urlparse.详情页面)
                +an.get(urlparse.二级类目页面)
                +an.get(urlparse.精选)-2*an.get(urlparse.首页);
        System.out.println("浏览量"+pv);

        System.out.println("加够"+(an.get(urlparse.添加购物车_点击)
                +an.get(urlparse.立即购买_点击)));
        System.out.println(JSON.toJSONString(code));

        //====
        List<Map.Entry<String, Integer>> itemList = new ArrayList();
        for (Map.Entry<String, Integer> entry : item.entrySet()) {
            itemList.add(entry);
        }

        itemList.sort(Comparator.comparingInt(Map.Entry::getValue)


        );
        for (Map.Entry<String, Integer> entry : itemList) {
           // System.out.println(entry.getKey() + " " + entry.getValue());
        }


    }


    static List<String[]> getDate(String file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
        List<String[]> dataList = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null) {
            try{
                if(StringUtils.isBlank(line)){
                    continue;
                }
                //if(!line.contains("04/Apr/2019")){
                //    continue;
               // }
            String[] data = line.split("\\s+");
            String path = data[6].split("\\?")[0];
            int num=0;
            if(code.containsKey(data[8])){
                num=code.get(data[8]);
            }
            num++;
            code.put(data[8],num);
            if (path.startsWith("/cmsstatic") || path.startsWith("/static")) {
                continue;
            }
            urlparse urlname = parseUrl(path);
            if (null == urlname) {
                System.out.println(path);
                continue;
            }
            dataList.add(data);
        }catch (Exception e){
                e.getMessage();
                System.out.println(line);
                throw e;
            }}
        return dataList;
    }

    static urlparse parseUrl(String url) {
        for (urlparse urlparse : urlparse.values()) {
            if (urlparse.matchType == 0) {
                if (url.startsWith(urlparse.urlpattern) && !exs(url, urlparse)) {
                    return urlparse;
                }
            } else {
                if (url.equals(urlparse.urlpattern) && !exs(url, urlparse)) {
                    return urlparse;
                }
            }

        }


        return null;
    }

    static boolean exs(String url, urlparse p) {
        if (p.exs == null) {
            return false;
        }
        for (String s : p.exs) {
            if (url.contains(s)) {
                {
                    return true;
                }
            }
        }

        return false;
    }

}

enum urlparse {
    订单提交_点击("点击", "/m/checkout/complete", null),
    订单页面点击确认支付按钮_点击("点击", "/m/account/payment/submitPayment_ajaxpage", null),

    首页类目("页面", "/intf/product/category/getProductOfCategoryList", null),
    二级类目页面("页面", "/m/subCategory", null),
    限时精选("页面", "/intf/product/category/rushPurchaseProduct", null),
    榜单("页面", "/m/share/topic", null),
    精选("页面", "/m/share/shopHome", null),
    详情页面("页面", "/m/product", Lists.newArrayList("skuInfo", "/m/product/piece")),
    热区页面("页面", "/m/hotArea", null),
    确认订单页面("页面", "/m/checkout", 1, null),
    我的购物车("页面", "/m/cart", 1, null),
    我的("页面", "/m/account", 1, null),
    登录("页面", "/m/login", 1, null),
    首页("页面", "/m", 1, null),
    错误("页面", "/404.html", null),
    支付页面("页面", "/m/account/payment", Lists.newArrayList("/m/account/payment/success", "/m/account/payment/failure")),

    添加购物车_点击("点击", "/m/cart/add", null),
    搜索_点击("点击", "/intf/search/getProductByKeywords", null),

    购物车点击结算生成订单_点击("点击", "/m/cart/tocheckout", null),

    立即购买_点击("点击", "/m/buynow/add", null);


    urlparse(String type, String urlpattern, List<String> exs) {
        this.urlpattern = urlpattern;
        this.type = type;
        this.exs = exs;
    }

    urlparse(String type, String urlpattern, int matchType, List<String> exs) {
        this.urlpattern = urlpattern;
        this.type = type;
        this.matchType = matchType;
        this.exs = exs;
    }

    String urlpattern;
    String type = "页面";
    int matchType = 0;
    List<String> exs;


}
