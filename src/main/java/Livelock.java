
import java.util.concurrent.CyclicBarrier;

public class Livelock {

    private static final boolean[] intent = new boolean[2];

    private static final CyclicBarrier barrier = new CyclicBarrier(2);

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> work(0, 1));
        Thread thread2 = new Thread(() -> work(1, 0));

        thread1.start();
        thread2.start();
    }

    private static void work(int current, int other) {

        while (true) {
            try {

                intent[current] = true;

                barrier.await();


                if (intent[other]) {
                    System.out.println(
                            "Поток " + (current + 1) +
                                    " уступает другому потоку"
                    );

                    intent[current] = false;
                }

                barrier.await();

            } catch (Exception e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
