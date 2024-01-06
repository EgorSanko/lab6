class n1 implements Runnable {
    private final int threadNumber;

    public n1(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        System.out.println("Hello from thread " + threadNumber);
    }
}

class TenThreads {
    public static void main(final String[] args) throws InterruptedException {
        System.out.println("Hello from main Thread");
        Thread[] tr = new Thread[10];
        for(int i = 0; i < 10; i++) {
            tr[i] = new Thread(new n1(i));
            tr[i].start();
        }
        for(int i = 0; i < 10; i++) {
            tr[i].join();
        }
    }
}
