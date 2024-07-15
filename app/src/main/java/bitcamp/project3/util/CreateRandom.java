package bitcamp.project3.util;

import java.util.Random;

public class CreateRandom {
    private static final Random random = new Random();

    public interface RandomAction {
        int randomDice(int n);

        default int randomDice() {
            return random.nextInt(1, 8); // 1~7
        }
    }

    public static class RandomNum implements RandomAction {
        private final Random random = new Random();

        @Override
        public int randomDice(int n) {
            return random.nextInt(n);
        }

    }

    public static class RandomZero implements RandomAction {
        private final Random random = new Random();

        @Override
        public int randomDice(int n) {
            double probability = ((double) n) / 100;
            return random.nextDouble() < probability ? 0 : 1;
        }
    }

}
