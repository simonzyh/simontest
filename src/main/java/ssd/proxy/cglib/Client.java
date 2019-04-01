package ssd.proxy.cglib;

public class Client {

    public static void main(String[] args) {
        int i = 0;
        BookServiceBean service = BookServiceFactory.getProxyInstance(new MyCglibProxy("boss"));
        System.out.println(service.getClass().getClassLoader());
        System.out.println(Thread.currentThread().getContextClassLoader());
        service.create();
        BookServiceBean service2 = BookServiceFactory.getProxyInstance(new MyCglibProxy("john"));
        service2.create();


    }

}