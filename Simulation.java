import java.util.Scanner;
import java.util.concurrent.TimeUnit;

enum Seed {
  // Still lifes
  Block,
  Bee_hive,
  Loaf,
  Boat,
  Tub,
  // Oscillators
  Blinker,
  Toad,
  Beacon,

  // Spaceships
  Glider,

  // Random seed
  Random
}

public class Simulation {
  private Board board;
  private static final int MAX_TICK = 1000;

  public void runSim(Seed seed) {
    int tick = 0;
    board = new Board(seed);
    while (tick < MAX_TICK) {
      for (int i = 0; i < board.getHeight(); i++) {
        for (int j = 0; j < board.getWidth(); j++) {
          Tile tile = board.getTile(i, j, Iteration.next);
          int neighbors = board.getNeighbors(i, j);
          if (tile.getState()) {
            if (neighbors < 2) {
              tile.toggleState();
            } else if (neighbors == 2 || neighbors == 3) {
              continue;
            } else if (neighbors > 3) {
              tile.toggleState();
            }
          } else {
            if (neighbors == 3) {
              tile.toggleState();
            }
          }
        }
      }
      board.commitIteration();
      board.displayBoard(tick);
      try {
        TimeUnit.MILLISECONDS.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
        return;
      }
      tick++;
    }
  }

  public Seed selectedMode() {
    System.out.println("Please select a seed");

    System.out.println("Still lifes:");
    System.out.println("Block (1)");
    System.out.println("Bee-Hive (2)");
    System.out.println("Loaf (3)");
    System.out.println("Boat (4)");
    System.out.println("Tub (5)");
    System.out.println("Oscillators:");
    System.out.println("Blinker (6)");
    System.out.println("Toad (7)");
    System.out.println("Beacon (8)");
    System.out.println("Spaceships:");
    System.out.println("Glider (9)");
    System.out.println("Random (10)");

    Scanner scanner = new Scanner(System.in);
    int selected = scanner.nextInt();
    scanner.close();

    switch (selected) {
      case 1:
        return Seed.Block;
      case 2:
        return Seed.Bee_hive;
      case 3:
        return Seed.Loaf;
      case 4:
        return Seed.Boat;
      case 5:
        return Seed.Tub;
      case 6:
        return Seed.Blinker;
      case 7:
        return Seed.Toad;
      case 8:
        return Seed.Beacon;
      case 9:
        return Seed.Glider;
      case 10:
        return Seed.Random;
      default:
        System.out.println("Invalid option, selecting default");
        return Seed.Random;
    }
  }

  public static void main(String[] args) {
    Simulation s = new Simulation();
    s.runSim(s.selectedMode());
  }
}
