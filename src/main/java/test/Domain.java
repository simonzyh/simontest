package test;

import java.math.BigDecimal;
import java.util.zip.CRC32;


public class Domain {

    protected static String[] domainLists = {"http://a.vpimg2.com", "http://a.vpimg3.com", "http://a.vpimg4.com"};


    /**
     * return the host domain by key
     *
     * @param string type
     * @param string key
     * @return string
     * @link: http://www.php.net/manual/zh/language.operators.arithmetic.php
     */
    public static String getDomain(String brand_id, String img_pre) {
        try {

            String server = null;
            CRC32 crc32 = new CRC32();
            crc32.update(brand_id.getBytes("UTF8"));

            long keyHash = Long.parseLong(String.format("%1$d", crc32.getValue()));

            if (img_pre != null && img_pre.length() > 0) {

                long weight = keyHash % 100;

                double weight_all = 0;

                if (weight_all == 0) weight_all = (double) domainLists.length;

                for (int i = 1; i < domainLists.length + 1; i++) {

                    BigDecimal b = new BigDecimal(((double) i / weight_all) * 100);
                    double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    if (weight <= f1) {
                        server = domainLists[i - 1];
                        break;
                    }
                }
            }
            if (server == null) server = "http://a.vpimg4.com";

            return server;
        } catch (Exception ex) {
            //TODO
            return "";
        }
    }

}
