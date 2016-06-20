package asm;

/**
 * Created by yehua.zyh on 2016/1/11.
 */
public class Account implements AccountI {
    static {
        System.out.println("Account load");
    }

    public void operation() {
        System.out.println("operation...");
        //TODO real operation
    }
}