import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ReaderWriterProblem {

    private static final int NUM_WRITERS = 5;
    private static final int NUM_READERS = 10;

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static int activeReaders = 0;
    private static boolean writerActive = false;

    static class Reader implements Runnable {
        public void run() {
            lock.lock();
            try {
                while (writerActive) {
                    condition.await();
                }
                activeReaders++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

            // Reading...
            System.out.println(Thread.currentThread().getName() + " is reading.");

            lock.lock();
            try {
                activeReaders--;
                if (activeReaders == 0) {
                    condition.signalAll();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    static class Writer implements Runnable {
        public void run() {
            lock.lock();
            try {
                while (activeReaders > 0 || writerActive) {
                    condition.await();
                }
                writerActive = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

            // Writing...
            System.out.println(Thread.currentThread().getName() + " is writing.");

            lock.lock();
            try {
                writerActive = false;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        // Create and start readers
        for (int i = 0; i < NUM_READERS; i++) {
            new Thread(new Reader(), "Reader " + i).start();
        }

        // Create and start writers
        for (int i = 0; i < NUM_WRITERS; i++) {
            new Thread(new Writer(), "Writer " + i).start();
        }
    }
}
