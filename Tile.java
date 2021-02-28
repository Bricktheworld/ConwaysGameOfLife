public class Tile {
  private boolean state;

  public Tile(boolean state) {
    this.state = state;
  }

  public Tile(Tile other) {
    this.state = other.state;
  }

  public boolean getState() {
    return this.state;
  }

  public void toggleState() {
    this.state = !this.state;
  }

  public void setState(boolean state) {
    this.state = state;
  }
}
