package test;

public class TestThread {
    static Object obj = new Object();

    public static void main(String[] args) {
        t1.start();
        t2.start();

    }

    static Thread t1 = new Thread() {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (obj) {
                    obj.notify();

                    System.out.println("t1:" + i);
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(i==9){
                        obj.notifyAll();
                    }
                }

            }
        }
    };
    static Thread t2 = new Thread() {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (obj) {
                    obj.notify();

                    System.out.println("t2:" + i);
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

}
