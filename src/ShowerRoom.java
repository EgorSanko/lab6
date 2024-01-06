import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

public class ShowerRoom {
    private int capacity = 10;
    private int menInside = 0;
    private int womenInside = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition menCondition = lock.newCondition();
    private Condition womenCondition = lock.newCondition();

    public void womanWantsToEnter() throws InterruptedException {
        lock.lock();
        try {
            while (menInside > 0 || womenInside >= capacity) {
                womenCondition.await();
            }
            womenInside++;
        } finally {
            lock.unlock();
        }
    }

    public void manWantsToEnter() throws InterruptedException {
        lock.lock();
        try {
            while (womenInside > 0 || menInside >= capacity) {
                menCondition.await();
            }
            menInside++;
        } finally {
            lock.unlock();
        }
    }

    public void womanLeaves() {
        lock.lock();
        try {
            womenInside--;
            if (womenInside == 0) {
                menCondition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public void manLeaves() {
        lock.lock();
        try {
            menInside--;
            if (menInside == 0) {
                womenCondition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
