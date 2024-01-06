import java.util.Random;
import java.util.Stack;

public class ProducerConsumerNoSync {
    static Stack<Integer> stack = new Stack<>();

    static class Producer extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                int item = new Random().nextInt(100);
                stack.push(item);
                System.out.println("Producer produced: " + item);
                // Имитация задержки
                try {
                    Thread.sleep(new Random().nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer extends Thread {
        @Override
        public void run() {
            while (true) {
                if (!stack.isEmpty()) {
                    int item = stack.pop();
                    System.out.println("Consumer consumed: " + item);
                }
                // Имитация задержки
                try {
                    Thread.sleep(new Random().nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Producer[] producers = new Producer[10];
        Consumer[] consumers = new Consumer[10];

        for (int i = 0; i < 10; i++) {
            producers[i] = new Producer();
            consumers[i] = new Consumer();
            producers[i].start();
            consumers[i].start();
        }
    }
}
