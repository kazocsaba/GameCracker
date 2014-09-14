package hu.kazocsaba.gamecracker.game.tictactoe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import hu.kazocsaba.gamecracker.game.GameStatus;
import hu.kazocsaba.gamecracker.game.SquareSymmetry;
import hu.kazocsaba.gamecracker.game.testing.AbstractGameTest;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author Kazó Csaba
 */
public class TicTacToeTest
    extends AbstractGameTest<TicTacToe, TicTacToePosition, TicTacToeMove, SquareSymmetry> {

  public TicTacToeTest() {
    super(new TicTacToe());
  }

  @Test
  public void basicGameTest() {
    TicTacToePosition position = game.getInitialPosition();
    position = position.move(TicTacToeMove.get(1, 1));
    position = position.move(TicTacToeMove.get(0, 1));
    position = position.move(TicTacToeMove.get(1, 2));
    position = position.move(TicTacToeMove.get(2, 2));
    position = position.move(TicTacToeMove.get(1, 0));
    assertEquals(GameStatus.WHITE_WINS, position.getStatus());
  }

  @Test
  public void testMoveParsing() {
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        assertEquals(TicTacToeMove.get(x, y), game.parseMove(TicTacToeMove.get(x, y).toString()));
      }
    }
  }

  @Test
  public void testTransformations() {
    TicTacToePosition position = game.getInitialPosition();

    TicTacToePosition position1 = position.move(TicTacToeMove.get(0, 2));
    TicTacToePosition position2 = position.move(TicTacToeMove.get(0, 0));

    SquareSymmetry trans = position1.getTransformationTo(position2);

    assertTrue(trans == SquareSymmetry.VERTICAL_REFLECTION || trans == SquareSymmetry.ROTATION_270);
    assertPositionsTransformed(position1, SquareSymmetry.VERTICAL_REFLECTION, position2);
    assertPositionsTransformed(position1, SquareSymmetry.ROTATION_270, position2);

    position1 = position1.move(TicTacToeMove.get(0, 1));
    position2 = position2.move(TicTacToeMove.get(0, 1));

    assertEquals(SquareSymmetry.VERTICAL_REFLECTION, position1.getTransformationTo(position2));
    assertPositionsTransformed(position1, SquareSymmetry.VERTICAL_REFLECTION, position2);
    assertNotEquals(position1.transform(SquareSymmetry.ROTATION_270), position2);

    // check moves
    HashSet<TicTacToeMove> moves1 = new HashSet<>(position1.getMoves());
    HashSet<TicTacToeMove> moves2 = new HashSet<>(position2.getMoves());
    for (TicTacToeMove m : moves1) {
      assertTrue(moves2.contains(m.transform(SquareSymmetry.VERTICAL_REFLECTION)));
    }

    position1 = position1.move(TicTacToeMove.get(0, 0));
    position2 = position2.move(TicTacToeMove.get(0, 2));

    assertPositionsEqual(position1, position2);
  }

  @Test
  public void gameTest() {
    TicTacToePosition position = game.getInitialPosition();

    position = position.move(TicTacToeMove.get(1, 0));
    position = position.move(TicTacToeMove.get(2, 1));
    position = position.move(TicTacToeMove.get(0, 0));
    position = position.move(TicTacToeMove.get(2, 0));
    position = position.move(TicTacToeMove.get(2, 2));
    position = position.move(TicTacToeMove.get(1, 2));

    assertEquals(new HashSet<>(Arrays.asList(TicTacToeMove.get(0, 1),
        TicTacToeMove.get(0, 2), TicTacToeMove.get(1, 1))),
        new HashSet<>(position.getMoves()));
    assertEquals(GameStatus.BLACK_MOVES, position.move(TicTacToeMove.get(0, 1)).getStatus());
    assertEquals(GameStatus.BLACK_MOVES, position.move(TicTacToeMove.get(0, 2)).getStatus());
    assertEquals(GameStatus.WHITE_WINS, position.move(TicTacToeMove.get(1, 1)).getStatus());
  }

  @Test
  public void winTest() {
    // win on edge with move in middle
    TicTacToePosition position = game.getInitialPosition();

    position = position.move(TicTacToeMove.get(0, 0));
    position = position.move(TicTacToeMove.get(1, 1));
    position = position.move(TicTacToeMove.get(2, 0));
    position = position.move(TicTacToeMove.get(2, 2));
    for (SquareSymmetry trans : SquareSymmetry.values()) {
      TicTacToePosition transformedPosition = position.transform(trans);
      assertEquals(GameStatus.WHITE_WINS,
          transformedPosition.move(TicTacToeMove.get(1, 0).transform(trans)).getStatus());
    }

    // win in middle with move in middle
    position = game.getInitialPosition();

    position = position.move(TicTacToeMove.get(0, 1));
    position = position.move(TicTacToeMove.get(1, 0));
    position = position.move(TicTacToeMove.get(2, 1));
    position = position.move(TicTacToeMove.get(2, 2));
    for (SquareSymmetry trans : SquareSymmetry.values()) {
      TicTacToePosition transformedPosition = position.transform(trans);
      assertEquals(GameStatus.WHITE_WINS,
          transformedPosition.move(TicTacToeMove.get(1, 1).transform(trans)).getStatus());
    }

    // win in diagonal with move in middle
    position = game.getInitialPosition();

    position = position.move(TicTacToeMove.get(0, 0));
    position = position.move(TicTacToeMove.get(1, 0));
    position = position.move(TicTacToeMove.get(2, 2));
    position = position.move(TicTacToeMove.get(2, 0));
    for (SquareSymmetry trans : SquareSymmetry.values()) {
      TicTacToePosition transformedPosition = position.transform(trans);
      assertEquals(GameStatus.WHITE_WINS,
          transformedPosition.move(TicTacToeMove.get(1, 1).transform(trans)).getStatus());
    }

    // win in middle with move on edge
    position = game.getInitialPosition();

    position = position.move(TicTacToeMove.get(1, 1));
    position = position.move(TicTacToeMove.get(1, 0));
    position = position.move(TicTacToeMove.get(2, 1));
    position = position.move(TicTacToeMove.get(2, 2));
    for (SquareSymmetry trans : SquareSymmetry.values()) {
      TicTacToePosition transformedPosition = position.transform(trans);
      assertEquals(GameStatus.WHITE_WINS,
          transformedPosition.move(TicTacToeMove.get(0, 1).transform(trans)).getStatus());
    }

    // win in diagonal with move in corner
    position = game.getInitialPosition();

    position = position.move(TicTacToeMove.get(0, 0));
    position = position.move(TicTacToeMove.get(1, 0));
    position = position.move(TicTacToeMove.get(1, 1));
    position = position.move(TicTacToeMove.get(2, 0));
    for (SquareSymmetry trans : SquareSymmetry.values()) {
      TicTacToePosition transformedPosition = position.transform(trans);
      assertEquals(GameStatus.WHITE_WINS,
          transformedPosition.move(TicTacToeMove.get(2, 2).transform(trans)).getStatus());
    }
  }

  @Test
  public void positionIOTest() {
    // win on edge with move in middle
    TicTacToePosition position = game.getInitialPosition();

    position = position.move(TicTacToeMove.get(0, 0));
    position = position.move(TicTacToeMove.get(1, 1));
    position = position.move(TicTacToeMove.get(2, 0));
    position = position.move(TicTacToeMove.get(2, 2));
    for (SquareSymmetry trans : SquareSymmetry.values()) {
      TicTacToePosition transformedPosition = position.transform(trans);
      testPositionSerializer(transformedPosition);

      transformedPosition = transformedPosition.move(TicTacToeMove.get(1, 0).transform(trans));
      testPositionSerializer(transformedPosition);
    }

    // win in middle with move in middle
    position = game.getInitialPosition();

    position = position.move(TicTacToeMove.get(0, 1));
    position = position.move(TicTacToeMove.get(1, 0));
    position = position.move(TicTacToeMove.get(2, 1));
    position = position.move(TicTacToeMove.get(2, 2));
    for (SquareSymmetry trans : SquareSymmetry.values()) {
      TicTacToePosition transformedPosition = position.transform(trans);
      testPositionSerializer(transformedPosition);

      transformedPosition = transformedPosition.move(TicTacToeMove.get(1, 1).transform(trans));
      testPositionSerializer(transformedPosition);
    }

    // win in diagonal with move in middle
    position = game.getInitialPosition();

    position = position.move(TicTacToeMove.get(0, 0));
    position = position.move(TicTacToeMove.get(1, 0));
    position = position.move(TicTacToeMove.get(2, 2));
    position = position.move(TicTacToeMove.get(2, 0));
    for (SquareSymmetry trans : SquareSymmetry.values()) {
      TicTacToePosition transformedPosition = position.transform(trans);
      testPositionSerializer(transformedPosition);

      transformedPosition = transformedPosition.move(TicTacToeMove.get(1, 1).transform(trans));
      testPositionSerializer(transformedPosition);
    }

    // win in middle with move on edge
    position = game.getInitialPosition();

    position = position.move(TicTacToeMove.get(1, 1));
    position = position.move(TicTacToeMove.get(1, 0));
    position = position.move(TicTacToeMove.get(2, 1));
    position = position.move(TicTacToeMove.get(2, 2));
    for (SquareSymmetry trans : SquareSymmetry.values()) {
      TicTacToePosition transformedPosition = position.transform(trans);
      testPositionSerializer(transformedPosition);

      transformedPosition = transformedPosition.move(TicTacToeMove.get(0, 1).transform(trans));
      testPositionSerializer(transformedPosition);
    }

    // win in diagonal with move in corner
    position = game.getInitialPosition();

    position = position.move(TicTacToeMove.get(0, 0));
    position = position.move(TicTacToeMove.get(1, 0));
    position = position.move(TicTacToeMove.get(1, 1));
    position = position.move(TicTacToeMove.get(2, 0));
    for (SquareSymmetry trans : SquareSymmetry.values()) {
      TicTacToePosition transformedPosition = position.transform(trans);
      testPositionSerializer(transformedPosition);

      transformedPosition = transformedPosition.move(TicTacToeMove.get(2, 2).transform(trans));
      testPositionSerializer(transformedPosition);
    }
  }
}
