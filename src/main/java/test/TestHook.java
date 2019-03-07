package test;

public class TestHook {


    public static void main(String[] args) throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("exec hook start");
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("exec hook start");

        }
        ));
        byte[] b = new byte[1000 * 1024 * 1024];
        System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024);
        Thread.sleep(1000 * 10);
        System.out.println("main exit");
    }
}
