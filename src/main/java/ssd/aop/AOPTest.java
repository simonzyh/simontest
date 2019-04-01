package ssd.aop;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import ssd.aop.service.AService;
import ssd.aop.service.BServiceImpl;

public class AOPTest {


    public static void main(String[] srga) {

        ApplicationContext ctx = new FileSystemXmlApplicationContext("F:\\couponWorkspace\\test\\src\\main\\java\\aop\\applicationContext.xml");
        AService aService = (AService) ctx.getBean("aService");
        BServiceImpl bService = (BServiceImpl) ctx.getBean("bService");
        System.out.println(aService);
        aService.fooA("JUnit test fooA");
    }


    /**
     * 测试正常调用
     *//*
    public void testCall()
	{
		System.out.println("SpringTest JUnit test");
		aService.fooA("JUnit test fooA");
		aService.barA();
		bService.fooB();
		bService.barB("JUnit test barB",0);
	}
	
	*//**
     * 测试After-Throwing
     *//*
    public void testThrow()
	{
		try {
			bService.barB("JUnit call barB",1);
		} catch (IllegalArgumentException e) {
			
		}
	}*/


}