package test;

/**
 * Created by yehua.zyh on 2016/3/28.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class HttpClientTool {

    //由于集团生产机器默认不允许直接访问外网的，所以这里不能使用chaoshi.alicdn.com, 只能用oss自己域名
    public static final String DEFAULT_CDN_URL = "http://tmallsupermarket.cn-shanghai.oss.aliyun-inc.com/";
    public static final String DEFAULT_REQUEST_REFER = "chaoshi.m.tmall.com";
    private static final Log log = LogFactory.getLog(HttpClientTool.class);
    private static final int CONNECT_TIMEOUT = 2000;

    private static final int READ_TIME_OUT = 3000;


 /*   *//**
     *
     * @param jsonParameter
     * @return
     *//*
    public static String getFileName(AldRequest aldRequest, String logica){
        String fileName = StringUtil.EMPTY_STRING;

        if (logica == null || aldRequest == null || aldRequest.getAppId() == null || CollectionUtil.isEmpty(aldRequest.getParaMap())){
            return null;
        }

        //根据logic和appid的参数名来进行md5计算
        String appId = aldRequest.getAppId();
        String sourceStr = appId;
        //log.warn("sourceStr=" + sourceStr);
        String md5Str = encoderByMd5(sourceStr);
        //log.warn("md5Str=" + md5Str);
        if (StringUtil.isEmpty(md5Str)){
            //log.warn("that encript md5 error with logic=" + logica + ",appIds=" + appId);
            return null;
        }
        fileName = md5Str + "_" + logica;
        return fileName;
    }*/

    /**
     * md5加密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String encoderByMd5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
            byte[] byteArray = messageDigest.digest();
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
            return md5StrBuff.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(encoderByMd5("2016031611,2016031612,2016031613,2016031614"));
        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println((Object) map.get("test"));
    }
}
