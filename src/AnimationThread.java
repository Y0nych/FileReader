import java.util.List;
import java.util.SplittableRandom;

public class AnimationThread extends Thread implements Listener {
    private boolean keep = true;

    private static final String hui = "Соси хуй";
    private int ctr = 1;

    @Override
    public void run() {
        System.out.println();
        System.out.print(hui);

        while (keep) {
            if (ctr % 4 != 0) {
                System.out.print(".");
            } else {
                System.out.print("\b\b\b");
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {}
            ctr++;
        }
    }

    @Override
    public void onStop() {
        keep = false;
        for (int i = 0; i < hui.length() + ctr; i++) {
            System.out.print("\b");
        }
    }
}
