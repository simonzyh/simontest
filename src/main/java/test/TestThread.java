package test;

public class TestThread {
    static Object obj = new Object();

    public static void main(String[] args) {
        //t1.start();
        //t2.start();
        t3.start();
        t4.start();

    }

    static Thread t1 = new Thread(() -> {
        for (int i = 0; i < 10; i++) {
            synchronized (obj) {
                obj.notify();

                System.out.println("t1:" + i);
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i == 9) {
                    obj.notifyAll();
                }
            }

        }
    });
    static Thread t2 = new Thread(() -> {
        for (int i = 0; i < 10; i++) {
            synchronized (obj) {
                obj.notify();

                System.out.println("t2:" + i);
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i == 9) {
                    obj.notifyAll();
                }
            }
        }
    });
    static Thread t3 = new Thread(() -> {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getId() + " " + i);

            Thread.currentThread().yield();

        }
    });

    static Thread t4 = new Thread(() -> {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getId() + " " + i);

            Thread.currentThread().yield();

        }
    });




}
