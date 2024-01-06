import java.util.Random;

public class Man implements Runnable {
    private final ShowerRoom showerRoom;
    private final Random random = new Random();

    public Man(ShowerRoom showerRoom) {
        this.showerRoom = showerRoom;
    }

    @Override
    public void run() {
        try {
            while (true) {
                showerRoom.manWantsToEnter();
                System.out.println("Man entered the shower.");
                Thread.sleep(random.nextInt(1000)); // Время пребывания в душе
                showerRoom.manLeaves();
                System.out.println("Man left the shower.");
                Thread.sleep(random.nextInt(3000)); // Время до следующей попытки входа
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

