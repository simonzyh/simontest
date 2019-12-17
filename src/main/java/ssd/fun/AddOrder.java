package ssd.fun;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class AddOrder {

    static Statement statement = null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://10.255.2.20:3306/gialen_order?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true", "gialen_order", "gialenGIALEN123!@#");

            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addOrder(Long userId, String phone) throws Exception {

        String orderSn = OrderSnGen.gen() + "0";
        String insertOrderSql = "INSERT INTO `gialen_order`.`orders`" +
                "( `order_sn`, `user_id`, `order_type`, `order_status`, `pay_money`, `pay_money_snapshot`, `freight`, `fav_money`, `wallet_money_snapshot`, `wallet_money`, `money`, `pay_type`, `pay_sn`, `pay_status`, `pay_time`, `flow_flag`, `send_time`, `received_time`, `del_flag`, `platform`, `is_parent`, `parent_order_sn`, `data_version`, `is_hidden`, `update_time`, `create_time`, `is_new_order`) VALUES " +
                "('" + orderSn + "', " + userId + ", 1, 3, 154.00, 154.00, 0.00,                                     25.00,        0.00,                    0.00,          154.00, 1,            '',        1,            now(),     1,           NULL,        NULL,            0,          2,         1,             NULL,            1,               0,           now(),         now(),           1);";
        statement.execute(insertOrderSql);

        String orderGoodsSql = "INSERT INTO `gialen_order`.`order_goods`(" +
                " `order_sn`, `brand_id`, `product_id`, `sku_id`, `sub_sku_id`, `barcode`, `base_price`, `buy_price`, `real_buy_price`, `fav_detail`, `total_fav`, `buy_num`, `leavings_num`, `del_flag`, `update_time`, `create_time`) VALUES " +
                " ('" + orderSn + "', 2104, 1000560, 1000633,     '20501|20545', 'TC100046', 179.00, 179.00, 154.00, '[{\\\"fav\\\":25.00,\\\"favRemark\\\":\\\"优惠活动\\\",\\\"favType\\\":\\\"2\\\",\\\"totalFav\\\":25.00}]', 25.00, 1, 1, 0, now(), now());";
        statement.execute(orderGoodsSql);
        String insertDesSql = "INSERT INTO `gialen_order`.`order_describe`(" +
                "`order_sn`, `warehouse_id`, `consignee`, `tel`, `address_id`, `province`, `city`, `district`, `street`, `invoice_type`, `invoice_title`, `invoice_detail`, `standby`, `after_sale_num`, `remark`, `update_time`, `create_time`) VALUES (" +
                " '" + orderSn + "', 1, '" + phone + "', '" + phone + "', 1026810, '福建', '漳州市', '龙海市', '漳州台', 1, '', '', '{}', 0, '', now(), now());";
        statement.execute(insertDesSql);
        System.out.println("插入订单" + userId + " " + phone + " " + orderSn);


    }


}
