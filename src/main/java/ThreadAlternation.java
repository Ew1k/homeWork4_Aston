
public class ThreadAlternation {

    private static int nextNumber = 1;

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            while (true) {
                printNumber(1);
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                printNumber(2);
            }
        });

        thread1.start();
        thread2.start();
    }

    private static synchronized void printNumber(int number) {

        try {
            while (nextNumber != number) {
                ThreadAlternation.class.wait();
            }

            System.out.println(number);

            nextNumber = (number == 1) ? 2 : 1;

            ThreadAlternation.class.notifyAll();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
