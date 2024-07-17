package bitcamp.project3.util;

public class CreateRandom {
  public interface RandomAction {
    int randomDice(int n);
  }

  //  public static class RandomNum implements RandomAction {
  //    @Override
  //    public int randomDice(int n) {
  //      return random.nextInt(n);
  //    }
  //  }
  //
  //
  //  public static class RandomZero implements RandomAction {
  //    @Override
  //    public int randomDice(int n) {
  //      double probability = ((double) n) / 100;
  //      return random.nextDouble() < probability ? 0 : 1;
  //    }
  //  }
}
