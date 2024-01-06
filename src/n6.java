import java.util.Stack;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class n6 {
    static Stack<Integer> shelf = new Stack<>();
    static ReentrantLock lock = new ReentrantLock();
    static Condition notFull = lock.newCondition();
    static Condition notEmpty = lock.newCondition();

    static class Prod extends Thread {
        @Override
        public void run() {
            int n = 0;
            try {
                while (n < 1000) {
                    lock.lock();
                    try {
                        while (shelf.size() == 100) {
                            notFull.await();
                        }
                        int product = new Random().nextInt(100);
                        shelf.push(product);
                        notEmpty.signal();
                    } finally {
                        lock.unlock();
                    }
                    n++;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Cons extends Thread {
        @Override
        public void run() {
            int n = 0;
            try {
                while (n < 1000) {
                    lock.lock();
                    try {
                        while (shelf.isEmpty()) {
                            notEmpty.await();
                        }
                        shelf.pop();
                        notFull.signal();
                    } finally {
                        lock.unlock();
                    }
                    n++;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Thread[] producers = new Thread[10];
        Thread[] consumers = new Thread[10];

        for (int i = 0; i < 10; i++) {
            producers[i] = new Prod();
            consumers[i] = new Cons();
            producers[i].start();
            consumers[i].start();
        }

        for (int i = 0; i < 10; i++) {
            try {
                producers[i].join();
                consumers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
