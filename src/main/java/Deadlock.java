
import java.util.concurrent.CountDownLatch;

public class Deadlock {

    private static final Object RESOURCE_A = new Object();
    private static final Object RESOURCE_B = new Object();

    private static final CountDownLatch latch = new CountDownLatch(2);

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            synchronized (RESOURCE_A) {
                System.out.println("Поток 1 захватил ресурс A");

                latch.countDown();
                awaitLatch();

                System.out.println("Поток 1 ожидает ресурс B");

                synchronized (RESOURCE_B) {
                    System.out.println("Поток 1 захватил ресурс B");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (RESOURCE_B) {
                System.out.println("Поток 2 захватил ресурс B");

                latch.countDown();
                awaitLatch();

                System.out.println("Поток 2 ожидает ресурс A");

                synchronized (RESOURCE_A) {
                    System.out.println("Поток 2 захватил ресурс A");
                }
            }
        });

        thread1.start();
        thread2.start();
    }

    private static void awaitLatch() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
