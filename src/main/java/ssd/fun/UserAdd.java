package ssd.fun;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserAdd {
    static Statement statement = null;
    static Long userPhone = 186601011230L;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://10.255.2.18:3306/gialen_customer?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true", "gialen_customer", "gialenGIALEN123!@#");

            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long add(String phone) throws Exception {
        String sql = "INSERT INTO `gialen_customer`.`user`( `username`, `login_id`, `password`, `email`, `phone`, `nickname`, `sex`, `birthday`, `store_id`, `user_head_pic`, `create_time`, `update_time`, `is_delete`, `platform`, `recommend_code`, `id_card`) VALUES ( '', '', '', '', '" + phone + "', '', 0, NULL, 3700, '', now(), now(), 0, 1, '', '')";
        statement.execute(sql);

        //查询userId
        String querySql = "select id from user where phone='" + phone + "'";

        ResultSet resultSet = statement.executeQuery(querySql);
        resultSet.next();
        Long id = resultSet.getLong(1);
        String levelSQl = "INSERT INTO `gialen_customer`.`user_level`(`user_id`, `level_type`, `status`, `create_time`, `update_time`, `data_version`) VALUES ( " + id + ", 1, 1, now(), now(), 0);";
        statement.execute(levelSQl);
        //添加埋点
        GdataAdd.add(phone);
        System.out.println("插入用户"+phone+" "+id);
        return id;
    }

    public static void main(String[] s) throws Exception {
        String phone = (userPhone++) + "";

        Long userId = add(phone);
        AddOrder.addOrder(userId, phone);
    }


}
