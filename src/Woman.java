import java.util.Random;


public class Woman implements Runnable {
    private final ShowerRoom showerRoom;
    private final Random random = new Random();

    public Woman(ShowerRoom showerRoom) {
        this.showerRoom = showerRoom;
    }

    @Override
    public void run() {
        try {
            while (true) {
                showerRoom.womanWantsToEnter();
                System.out.println("Woman entered the shower.");
                Thread.sleep(random.nextInt(1000)); // Время пребывания в душе
                showerRoom.womanLeaves();
                System.out.println("Woman left the shower.");
                Thread.sleep(random.nextInt(3000)); // Время до следующей попытки входа
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
