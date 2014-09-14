package hu.kazocsaba.gamecracker.game.reversi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import hu.kazocsaba.gamecracker.game.GameStatus;
import hu.kazocsaba.gamecracker.game.SwitchableSquareSymmetry;
import hu.kazocsaba.gamecracker.game.testing.AbstractGameTest;

import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 *
 * @author Kazó Csaba
 */
@RunWith(Theories.class)
public abstract class ReversiTest<
    G extends Reversi<P, M>, P extends ReversiPosition<P, M>, M extends ReversiMove<M>>
    extends AbstractGameTest<G, P, M, SwitchableSquareSymmetry> {

  public static class TestGame<M extends ReversiMove<M>> {

    private final M[] moves;
    private final GameStatus result;

    public TestGame(M[] moves, GameStatus result) {
      this.moves = moves;
      this.result = result;
    }

  }

  abstract P[] createPositionArray(int length);

  public ReversiTest(G game) {
    super(game);
  }

  /**
   * Verifies that all the moves can be parsed from their toString representations.
   */
  @Theory
  public void testMoveParsing(TestGame<M> testGame) {
    for (M move : testGame.moves) {
      assertEquals(move, game.parseMove(move.toString()));
    }
  }

  /**
   * Verifies the games, checking that the moves are valid and can be applied, the intermediate
   * positions are not final, and that the final position is correct.
   */
  @Theory
  public void testGame(TestGame<M> testGame) {
    P pos = game.getInitialPosition();
    for (M move : testGame.moves) {
      assertFalse(pos.getStatus().isFinal());
      assertTrue(pos.getMoves().contains(move));
      pos = pos.move(move);
    }
    assertEquals(testGame.result, pos.getStatus());
  }

  /**
   * Plays out the games along with all their transformations. Starting from the transformations of
   * the initial positions, it checks that the transformations of the moves can be applied, the
   * intermediate positions are not final, the final position's result is a transformation of 'White
   * wins'. For the intermediate positions, it also verifies that the corresponding transformations
   * still apply.
   */
  @Theory
  public void testSymmetries(TestGame<M> testGame) {
    P[] pos = createPositionArray(SwitchableSquareSymmetry.values().length);
    for (SwitchableSquareSymmetry symm : SwitchableSquareSymmetry.values()) {
      pos[symm.ordinal()] = game.getInitialPosition().transform(symm);
    }

    for (M move : testGame.moves) {
      for (SwitchableSquareSymmetry symm : SwitchableSquareSymmetry.values()) {
        assertFalse(pos[symm.ordinal()].getStatus().isFinal());
        M transformedMove = move.transform(symm);
        assertTrue(pos[symm.ordinal()].getMoves().contains(transformedMove));
        pos[symm.ordinal()] = pos[symm.ordinal()].move(transformedMove);
      }
      for (SwitchableSquareSymmetry symm : SwitchableSquareSymmetry.values()) {
        assertPositionsTransformed(
            pos[SwitchableSquareSymmetry.IDENTITY.ordinal()], symm, pos[symm.ordinal()]);
      }
    }
    for (SwitchableSquareSymmetry symm : SwitchableSquareSymmetry.values()) {
      assertEquals(symm.isPlayerSwitching() ? testGame.result.getOther() : testGame.result,
          pos[symm.ordinal()].getStatus());
    }
  }

  /**
   * Plays out the games along with all their transformations, verifying that the transformed move
   * is valid, and testing the serialization of each encountered position.
   */
  @Theory
  public void testIO(TestGame<M> testGame) {
    P[] pos = createPositionArray(SwitchableSquareSymmetry.values().length);
    for (SwitchableSquareSymmetry symm : SwitchableSquareSymmetry.values()) {
      pos[symm.ordinal()] = game.getInitialPosition().transform(symm);
      testPositionSerializer(pos[symm.ordinal()]);
    }

    for (M move : testGame.moves) {
      for (SwitchableSquareSymmetry symm : SwitchableSquareSymmetry.values()) {
        M transformedMove = move.transform(symm);
        assertTrue(pos[symm.ordinal()].getMoves().contains(transformedMove));
        pos[symm.ordinal()] = pos[symm.ordinal()].move(transformedMove);
        testPositionSerializer(pos[symm.ordinal()]);
      }
    }
  }
}
