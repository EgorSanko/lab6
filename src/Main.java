public class Main {
    public static void main(String[] args) {
        ShowerRoom showerRoom = new ShowerRoom();
        for (int i = 0; i < 20; i++) {
            new Thread(new Man(showerRoom)).start();
            new Thread(new Woman(showerRoom)).start();
        }
    }
}
