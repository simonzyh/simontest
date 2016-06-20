package aop.service;

/**
 * 接口A的实现类
 */
public class AServiceImpl implements AService {

    public void barA() {
        System.out.println("执行 AServiceImpl.barA()");
    }

    public String fooA(String _msg) {
        return "执行 AServiceImpl.fooA(msg:" + _msg + ")";
    }
}