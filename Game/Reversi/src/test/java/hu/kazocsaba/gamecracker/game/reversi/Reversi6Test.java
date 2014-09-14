package hu.kazocsaba.gamecracker.game.reversi;

import hu.kazocsaba.gamecracker.game.GameStatus;

import org.junit.experimental.theories.DataPoint;

/**
 *
 * @author Kazó Csaba
 */
public class Reversi6Test extends ReversiTest<Reversi6, Reversi6Position, Reversi6Move> {

  @DataPoint
  public static TestGame<Reversi6Move> GAME1 = new TestGame<>(
    new Reversi6Move[]{
      Reversi6Move.get(4, 2), Reversi6Move.get(2, 1),
      Reversi6Move.get(1, 1), Reversi6Move.get(4, 3),
      Reversi6Move.get(4, 4), Reversi6Move.get(1, 2),
      Reversi6Move.get(1, 3), Reversi6Move.get(0, 1),
      Reversi6Move.get(0, 0), Reversi6Move.get(3, 4),
      Reversi6Move.get(2, 4), Reversi6Move.get(1, 0),
      Reversi6Move.get(0, 2), Reversi6Move.get(0, 3),
      Reversi6Move.get(0, 4), Reversi6Move.get(1, 4),
      Reversi6Move.get(2, 0), Reversi6Move.get(3, 5),
      Reversi6Move.get(4, 5), Reversi6Move.get(5, 5),
      Reversi6Move.get(0, 5), Reversi6Move.get(1, 5),
      Reversi6Move.get(2, 5), Reversi6Move.get(5, 1),
      Reversi6Move.get(3, 1), Reversi6Move.get(4, 0),
      Reversi6Move.get(5, 4), Reversi6Move.get(5, 3),
      Reversi6Move.get(5, 2), Reversi6Move.get(4, 1),
      Reversi6Move.get(5, 0),
      Reversi6Move.get(3, 0)
    },
    GameStatus.WHITE_WINS);

  @DataPoint
  public static TestGame<Reversi6Move> GAME2 = new TestGame<>(
    new Reversi6Move[]{
      Reversi6Move.get(2, 4), Reversi6Move.get(1, 4),
      Reversi6Move.get(0, 4), Reversi6Move.get(0, 5),
      Reversi6Move.get(3, 1), Reversi6Move.get(2, 5),
      Reversi6Move.get(1, 5), Reversi6Move.get(3, 4),
      Reversi6Move.get(3, 5), Reversi6Move.get(4, 5),
      Reversi6Move.get(4, 4), Reversi6Move.get(2, 1),
      Reversi6Move.get(2, 0), Reversi6Move.get(3, 0),
      Reversi6Move.get(4, 1), Reversi6Move.get(1, 0),
      Reversi6Move.get(4, 3), Reversi6Move.get(5, 4),
      Reversi6Move.get(5, 3), Reversi6Move.get(4, 2),
      Reversi6Move.get(1, 1), Reversi6Move.get(0, 3),
      Reversi6Move.get(5, 5), Reversi6Move.get(1, 3),
      Reversi6Move.get(5, 2), Reversi6Move.get(4, 0),
      Reversi6Move.get(5, 0), Reversi6Move.get(5, 1),
      Reversi6Move.get(0, 0), Reversi6Move.get(0, 1),
      Reversi6Move.get(0, 2),
      Reversi6Move.get(1, 2)
    },
    GameStatus.DRAW);

  @Override
  Reversi6Position[] createPositionArray(int length) {
    return new Reversi6Position[length];
  }

  public Reversi6Test() {
    super(new Reversi6());
  }
}
