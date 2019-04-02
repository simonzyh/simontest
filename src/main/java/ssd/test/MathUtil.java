package ssd.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 数学运算
 *
 * @author zz
 * @email charles.zz.life@gmail.com
 * @date 2013-7-30
 */
public class MathUtil {
    private static MathContext mathContext = new MathContext(12, RoundingMode.HALF_DOWN);

    /**
     * Number to a BigDecimal
     *
     * @param n
     * @return The number as BigDecimal
     */
    public static BigDecimal toBigDecimal(Number n) {

        if (n instanceof BigDecimal) {
            return (BigDecimal) n;
        }

        if (n instanceof BigInteger) {
            return new BigDecimal((BigInteger) n);
        }

        return new BigDecimal(n.doubleValue());

    }

    /**
     * 空值转为0
     *
     * @param n
     * @return
     */
    public static BigDecimal nullToZero(Number n) {
        if (n == null) return BigDecimal.ZERO;
        return toBigDecimal(n);
    }

    /**
     * 除运算
     *
     * @param n1
     * @param n2
     * @return
     */
    public static BigDecimal divide(Number n1, Number n2) {
        return toBigDecimal(n1).divide(toBigDecimal(n2), mathContext);
    }

    /**
     * 乘运算
     *
     * @param n1
     * @param n2
     * @return
     */
    public static BigDecimal multiply(Number n1, Number n2) {
        return toBigDecimal(n1).multiply(toBigDecimal(n2), mathContext);
    }

    /**
     * 减运算
     *
     * @param n1
     * @param n2
     * @return
     */
    public static BigDecimal subtract(Number n1, Number n2) {
        return toBigDecimal(n1).subtract(toBigDecimal(n2), mathContext);
    }

    /**
     * 加运算
     *
     * @param n1
     * @param n2
     * @return
     */
    public static BigDecimal add(Number n1, Number n2) {
        return toBigDecimal(n1).add(toBigDecimal(n2), mathContext);
    }

    public static void main(String[] args) {
        List<Long> list = new ArrayList<Long>();
        list.add(123L);
        long l = 123;
        boolean b = true;
        boolean b1 = false;
        b1 &= b;
        System.out.println(b1);
        System.out.println(MathUtil.multiply(8, MathUtil.subtract(1, 0.8)).setScale(0, BigDecimal.ROUND_UP));
    }
}
