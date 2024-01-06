import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

    private static final int NUM_PHILOSOPHERS = 7;

    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];
        Lock[] forks = new ReentrantLock[NUM_PHILOSOPHERS];

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new ReentrantLock();
        }

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            Lock leftFork = forks[i];
            Lock rightFork = forks[(i + 1) % NUM_PHILOSOPHERS];

            philosophers[i] = new Philosopher(leftFork, rightFork);

            Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1));
            t.start();
        }
    }

    public static class Philosopher implements Runnable {
        private final Lock leftFork;
        private final Lock rightFork;

        public Philosopher(Lock leftFork, Lock rightFork) {
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        public void run() {
            try {
                while (true) {
                    // Thinking
                    System.out.println(Thread.currentThread().getName() + " is thinking.");
                    Thread.sleep(((int) (Math.random() * 100)));

                    // Hungry
                    System.out.println(Thread.currentThread().getName() + " is hungry.");

                    leftFork.lock();
                    try {
                        rightFork.lock();
                        try {
                            // Eating
                            System.out.println(Thread.currentThread().getName() + " is eating.");
                            Thread.sleep(((int) (Math.random() * 100)));
                        } finally {
                            rightFork.unlock();
                        }
                    } finally {
                        leftFork.unlock();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
