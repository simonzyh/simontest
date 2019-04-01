package ssd.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;


/**
 * 切面
 */
public class TestAspect {

    public void doAfter(JoinPoint jp) {

        System.out.println("执行 doafter: "
                + jp.getTarget().getClass().getName() + "."
                + jp.getSignature().getName() + " " + jp.getArgs());
    }

    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("执行 doAround 开始");
        long time = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        time = System.currentTimeMillis() - time;
        System.out.println("执行 doAround 结束process time: " + time + " ms" + " retVal=" + retVal);
        return retVal;
    }

    public void doBefore(JoinPoint jp) {
        System.out.println("执行 doBefore log Begining method: "
                + jp.getTarget().getClass().getName() + "."
                + jp.getSignature().getName());
    }

    public void doThrowing(JoinPoint jp, Throwable ex) {
        System.out.println("method " + jp.getTarget().getClass().getName()
                + "." + jp.getSignature().getName() + " throw exception");
        System.out.println(ex.getMessage());
    }

    private void sendEx(String ex) {
        //TODO 发送短信或邮件提醒
    }
}