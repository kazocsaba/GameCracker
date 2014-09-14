package hu.kazocsaba.gamecracker.game.reversi;

import hu.kazocsaba.gamecracker.game.GameStatus;

import org.junit.experimental.theories.DataPoint;

/**
 *
 * @author Kazó Csaba
 */
public class Reversi4Test extends ReversiTest<Reversi4, Reversi4Position, Reversi4Move> {

  @DataPoint
  public static TestGame<Reversi4Move> GAME1 = new TestGame<>(
    new Reversi4Move[]{
      Reversi4Move.get(2, 0),
      Reversi4Move.get(1, 0),
      Reversi4Move.get(0, 3),
      Reversi4Move.get(3, 0),
      Reversi4Move.get(0, 0),
      Reversi4Move.get(0, 2),
      Reversi4Move.get(0, 1),
      Reversi4Move.get(3, 2),
      Reversi4Move.get(3, 1),
      Reversi4Move.get(2, 3),
      Reversi4Move.get(1, 3),
      Reversi4Move.get(3, 3)
    },
    GameStatus.WHITE_WINS);

  @DataPoint
  public static TestGame<Reversi4Move> GAME2 = new TestGame<>(
    new Reversi4Move[]{
      Reversi4Move.get(2, 0),
      Reversi4Move.get(1, 0),
      Reversi4Move.get(0, 0),
      Reversi4Move.get(3, 0),
      Reversi4Move.get(3, 1),
      Reversi4Move.get(3, 2),
      Reversi4Move.get(2, 3),
      Reversi4Move.get(0, 1),
      Reversi4Move.get(0, 2),
      Reversi4Move.get(0, 3),
      Reversi4Move.get(1, 3),
      Reversi4Move.get(3, 3)
    },
    GameStatus.DRAW);

  @Override
  Reversi4Position[] createPositionArray(int length) {
    return new Reversi4Position[length];
  }

  public Reversi4Test() {
    super(new Reversi4());
  }
}
