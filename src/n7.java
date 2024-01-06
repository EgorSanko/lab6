import java.util.Stack;


public class n7 {
    static Stack<Integer> shelf = new Stack<>();
    static final Object lock = new Object(); // Объект для блокировки

    static class Prod extends Thread {
        @Override
        public void run() {
            int n = 0;
            while (n < 1000) {
                synchronized (lock) {
                    while (shelf.size() == 100) {
                        try {
                            lock.wait(); // Ожидаем пока потребитель освободит место
                        } catch (InterruptedException e) {
                            // Обработка прерывания
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    shelf.push(n); // Производим продукт
                    lock.notifyAll(); // Оповещаем остальные потоки
                }
                n++;
            }
        }
    }

    static class Cons extends Thread {
        @Override
        public void run() {
            int n = 0;
            while (n < 1000) {
                synchronized (lock) {
                    while (shelf.isEmpty()) {
                        try {
                            lock.wait(); // Ожидаем пока производитель добавит продукт
                        } catch (InterruptedException e) {
                            // Обработка прерывания
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    shelf.pop(); // Потребляем продукт
                    lock.notifyAll(); // Оповещаем остальные потоки
                }
                n++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] producers = new Thread[10];
        Thread[] consumers = new Thread[10];

        for (int i = 0; i < producers.length; i++) {
            producers[i] = new Prod();
            consumers[i] = new Cons();
            producers[i].start();
            consumers[i].start();
        }

        for (int i = 0; i < producers.length; i++) {
            producers[i].join();
            consumers[i].join();
        }
    }
}
