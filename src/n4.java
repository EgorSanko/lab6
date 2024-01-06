class n4 extends Thread {
    private final long startTime;

    public n4() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                long timeElapsed = (System.currentTimeMillis() - startTime) / 1000;
                System.out.println(getName() + " thread has been starting already " + timeElapsed + " seconds");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " has been interrupted after " + ((System.currentTimeMillis() - startTime) / 1000) + " seconds.");
            // Поток может выполнить необходимую работу по завершению здесь, если это требуется.
        }
    }
}

class FourthTask {
    public static void main(String[] args) throws InterruptedException {
        n4[] threads = new n4[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new n4();
            threads[i].start();
        }

        // Главный поток ждет 5 секунд
        Thread.sleep(5000);

        // Прерываем все потоки
        for (n4 thread : threads) {
            thread.interrupt();
        }

        // Ожидаем завершения всех потоков
        for (n4 thread : threads) {
            thread.join();
        }

        System.out.println("All n4 threads have been interrupted and are now closed.");
    }
}
