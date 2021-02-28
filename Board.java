enum Iteration {
  current,
  next
}

public class Board {
  private Tile[][] board;
  private Tile[][] nextIteration;

  private static final int width = 50;
  private static final int height = 50;

  public Board(Seed seed) {
    board = new Tile[width][height];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        board[i][j] = new Tile(false);
      }
    }
    switch (seed) {
      case Block:
        board[height / 2][width / 2].setState(true);
        board[height / 2][width / 2 + 1].setState(true);
        board[height / 2 - 1][width / 2].setState(true);
        board[height / 2 - 1][width / 2 + 1].setState(true);
        break;
      case Bee_hive:
        break;
      case Loaf:
        break;
      case Boat:
        break;
      case Tub:
        break;
      case Blinker:
        board[height / 2][width / 2].setState(true);
        board[height / 2][width / 2 + 1].setState(true);
        board[height / 2][width / 2 - 1].setState(true);
        break;
      case Toad:
        break;
      case Beacon:
        break;
      case Glider:
        board[width / 2][height / 2].setState(true);
        board[height / 2][width / 2 + 1].setState(true);
        board[height / 2][width / 2 - 1].setState(true);
        board[height / 2 - 1][width / 2 + 1].setState(true);
        board[height / 2 - 2][width / 2].setState(true);
        break;
      case Random:
        double maxDist = getDistance(24.5, 24.5, 50, 50);
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            double rand = Math.random() * maxDist;
            double probability = maxDist / (getDistance(24.5, 24.5, j, i) + 0.75);
            if (rand < probability) {
              board[i][j].setState(true);
            }
          }
        }
        break;
    }

    nextIteration = new Tile[width][height];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        nextIteration[i][j] = new Tile(board[i][j]);
      }
    }
  }

  private double getDistance(double fromx, double fromy, double tox, double toy) {
    return Math.sqrt(Math.pow(fromx - tox, 2) + Math.pow(fromy - toy, 2));
  }

  public int getHeight() {
    return board.length;
  }

  public int getWidth() {
    return board[0].length;
  }

  public int getNeighbors(int row, int col) {
    int count = 0;
    if (isLivingNeighbor(row - 1, col)) count++;
    if (isLivingNeighbor(row + 1, col)) count++;
    if (isLivingNeighbor(row, col - 1)) count++;
    if (isLivingNeighbor(row, col + 1)) count++;
    if (isLivingNeighbor(row - 1, col - 1)) count++;
    if (isLivingNeighbor(row - 1, col + 1)) count++;
    if (isLivingNeighbor(row + 1, col - 1)) count++;
    if (isLivingNeighbor(row + 1, col + 1)) count++;
    return count;
  }

  public boolean isLivingNeighbor(int row, int col) {
    return getTile(row, col, Iteration.current) != null
        && getTile(row, col, Iteration.current).getState();
  }

  public Tile getTile(int row, int col, Iteration iteration) {
    if (row < 0 || row >= width) return null;
    if (col < 0 || col >= height) return null;
    if (iteration == Iteration.current) {
      // make it immutable
      return new Tile(board[row][col]);
    } else {
      return nextIteration[row][col];
    }
  }

  public void displayBoard(int tick) {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    for (int i = 0; i < getWidth(); i++) {
      System.out.print("-");
    }
    System.out.println();
    for (int i = 0; i < getHeight(); i++) {
      System.out.print("|");
      for (int j = 0; j < getWidth(); j++) {
        if (board[i][j].getState()) {
          System.out.print("X");
        } else {
          System.out.print(" ");
        }
      }
      System.out.println("|");
    }
    for (int i = 0; i < getWidth(); i++) {
      System.out.print("-");
    }
    System.out.println();
    System.out.println("Tick: " + tick);
  }

  public void commitIteration() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        board[i][j] = new Tile(nextIteration[i][j]);
      }
    }
  }
}
