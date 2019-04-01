package ssd.thrift;

import org.apache.thrift.TException;

/**
 * User: simon
 * Date: 2015/9/1
 * Time: 15:15
 * description：
 */
public class HelloWorldImpl implements HelloWorldService.Iface {

    public HelloWorldImpl() {
    }

    @Override
    public String sayHello(String username) throws TException {
        System.out.println("收到请求:" + username);
        return "Hi," + username + " welcome to my blog www.micmiu.com";
    }

}
