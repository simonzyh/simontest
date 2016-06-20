/**
 * @Title: BallSort.java
 * @Package: com.fc.pojo
 * @Description: TODO
 * @author 张业华
 * @date 2014年8月20日 下午3:22:32
 * @version 1.3.1
 */


package com.fc.pojo;

import java.text.NumberFormat;


/**
 * @author 张业华
 * @version V1.3.1
 * @Description
 * @date 2014年8月20日 下午3:22:32
 */

public class BallSort {
    private static java.text.NumberFormat fprmat;

    static {

        fprmat = NumberFormat.getInstance();
        fprmat.setMaximumFractionDigits(4);
    }

    private int total = 0;
    private String balltype;
    private Integer ball = 0;
    private String per;
    private int num = 0;

    public String getBalltype() {
        return balltype;
    }

    public void setBalltype(String balltype) {
        this.balltype = balltype;
    }

    public Integer getBall() {
        return ball;
    }

    public void setBall(Integer ball) {
        this.ball = ball;
    }


    public String getPer() {
        return fprmat.format((num / (total * 1.0)));
    }


    public int getNum() {
        return num;
    }


    public void setNum() {
        this.num++;
    }


    public int getTotal() {
        return total;
    }


    public void setTotal(int total) {
        this.total = total;
    }


}
