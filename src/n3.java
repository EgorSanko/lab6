class n3 extends Thread {
    private final long startTime;
    public static volatile boolean keepRunning = true; // Разделяемая переменная для управления потоками

    public n3() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while (keepRunning && !isInterrupted()) {
            try {
                long timeElapsed = (System.currentTimeMillis() - startTime) / 1000;
                System.out.println(getName() + " thread has been starting already " + timeElapsed + " seconds");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(getName() + " has interrupted.");
                return; // Завершаем выполнение потока при прерывании
            }
        }
    }
}

class Thirds {
    public static void main(String[] args) throws InterruptedException {
        n3[] stop = new n3[10];
        for (int i = 0; i < stop.length; i++) {
            stop[i] = new n3();
            stop[i].start();
        }

        Thread.sleep(5000); // Главный поток спит 5 секунд
        n3.keepRunning = false; // Переключаем разделяемую переменную, чтобы остановить все потоки

        for (n3 thread : stop) {
            thread.join(); // Ожидаем завершения всех потоков
        }

        System.out.println("All done.");
    }
}
