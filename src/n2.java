class n2 extends Thread {
    private final long startTime;
    private final int threadNumber;

    public n2(int threadNumber) {
        this.threadNumber = threadNumber;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                long timeElapsed = (System.currentTimeMillis() - startTime) / 1000;
                if (timeElapsed >= 10) {
                    System.out.println("Thread " + threadNumber + " is stopping after " + timeElapsed + " seconds");
                    break; // Выходим из цикла после 10 секунд работы
                }
                System.out.println("Thread " + threadNumber + " has been starting since " + timeElapsed + " seconds");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadNumber + " interrupted at " + ((System.currentTimeMillis() - startTime) / 1000) + " seconds");
        }
    }
}

class SecondTask {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new n2(i).start();
        }
    }
}
