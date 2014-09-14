package hu.kazocsaba.gamecracker.graph;

import static org.junit.Assert.assertEquals;

import hu.kazocsaba.gamecracker.game.Player;

import org.junit.Test;

import java.util.Arrays;

/**
 *
 * @author Kazó Csaba
 */
public class GraphResultComputerTest {

  @Test
  public void testResultComputing() {
    GraphResult[] results = new GraphResult[6];
    for (GraphResult r1 : GraphResult.values()) {
      results[0] = r1;
      for (GraphResult r2 : GraphResult.values()) {
        results[1] = r2;
        for (GraphResult r3 : GraphResult.values()) {
          results[2] = r3;
          for (GraphResult r4 : GraphResult.values()) {
            results[3] = r4;
            for (GraphResult r5 : GraphResult.values()) {
              results[4] = r5;
              for (GraphResult r6 : GraphResult.values()) {
                results[5] = r6;
                assertEquals(
                    "Wrong results for White's move to " + Arrays.toString(results),
                    computeReference(Player.WHITE, results),
                    compute(Player.WHITE, results));
                assertEquals(
                    "Wrong results for Black's move to " + Arrays.toString(results),
                    computeReference(Player.BLACK, results),
                    compute(Player.BLACK, results));
              }
            }
          }
        }
      }
    }
  }

  private static GraphResult compute(Player player, GraphResult[] results) {
    GraphResultComputer resultComputer = GraphResultComputer.start(player);
    for (GraphResult result : results) {
      resultComputer = resultComputer.withMove(result);
    }
    return resultComputer.get();
  }

  private static GraphResult computeReference(Player player, GraphResult[] results) {
    boolean existsWontWin = false;
    boolean existsUnknown = false;
    boolean existsWontLose = false;
    boolean existsDraw = false;

    for (GraphResult result : results) {
      if (result.willWin(player)) {
        return result;
      }
      switch (result) {
        case WHITE_WINS:
        case BLACK_WINS:
          break;
        case DRAW:
          existsDraw = true;
          break;
        case UNKNOWN:
          existsUnknown = true;
          break;
        case WHITE_WONT_WIN:
        case BLACK_WONT_WIN:
          if (result == GraphResult.getWontWin(player)) {
            existsWontWin = true;
          } else {
            existsWontLose = true;
          }
          break;
        default:
          throw new AssertionError(result);
      }
    }
    if (existsWontLose || (existsDraw && existsUnknown)) {
      return GraphResult.getWontWin(player.getOther());
    }
    if (existsUnknown) {
      return GraphResult.UNKNOWN;
    }
    if (existsDraw) {
      return GraphResult.DRAW;
    }
    if (existsWontWin) {
      return GraphResult.getWontWin(player);
    }
    return GraphResult.getWin(player.getOther());
  }
}
