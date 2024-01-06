public class PiCalculator {

    private static final double PRECISION = 1e-9;
    private static final int NUM_THREADS = 5;
    private static double pi = 0.0;
    private static final Object lock = new Object();

    private static class PiThread extends Thread {
        private final int startIndex;

        public PiThread(int startIndex) {
            this.startIndex = startIndex;
        }

        @Override
        public void run() {
            double localSum = 0.0;
            double denominator = this.startIndex * 2 + 1;
            for (int i = this.startIndex; 1/denominator >= PRECISION; i += NUM_THREADS) {
                if (i % 2 == 0) {
                    localSum += 1 / denominator;
                } else {
                    localSum -= 1 / denominator;
                }
                denominator += NUM_THREADS * 2;
            }
            synchronized (lock) {
                pi += localSum;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PiThread[] threads = new PiThread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new PiThread(i);
            threads[i].start();
        }

        for (PiThread thread : threads) {
            thread.join();
        }

        pi *= 4; // умножаем на 4, чтобы получить конечное значение Пи

        System.out.println("Calculated pi: " + pi);
    }
}
