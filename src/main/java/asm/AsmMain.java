package asm;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * Created by yehua.zyh on 2016/1/11.
 */
public class AsmMain {

    public static byte[] codeHandl(Class c) {
        try {

            ClassReader cr = new ClassReader(c.getName());
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
            cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
            byte[] data = cw.toByteArray();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public static void main(String[] args) throws Exception {

        Account ac1 = new Account();
        ac1.operation();

        AccountI ac = (AccountI) new DynamicClassLoader().dynamicLoadClass(codeHandl(Account.class)).newInstance();

        ac.operation();
    }
}
