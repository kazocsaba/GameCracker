package hu.kazocsaba.gamecracker.graph.memory;

import hu.kazocsaba.gamecracker.game.reversi.Reversi4;
import hu.kazocsaba.gamecracker.game.tictactoe.TicTacToe;
import hu.kazocsaba.gamecracker.graph.GraphMatch;
import hu.kazocsaba.gamecracker.graph.GraphResult;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Kazó Csaba
 */
public class MemoryGraphNGTest {
	@Test(timeOut=5000)
	public void testTicTacToe() {
		MemoryGraph<?,?,?> graph=new MemoryGraph<>(new TicTacToe());
		solve(graph.createMatch());
		
		assertEquals(graph.createMatch().getResult(), GraphResult.DRAW);
	}
	@Test(timeOut=5000)
	public void testReversi() {
		MemoryGraph<?,?,?> graph=new MemoryGraph<>(new Reversi4());
		solve(graph.createMatch());
		
		assertEquals(graph.createMatch().getResult(), GraphResult.BLACK_WINS);
	}
	
	private static void solve(GraphMatch<?,?,?> match) {
		for (int i=0; i<match.getMoveCount() && !match.getResult().isKnown(); i++) {
			match.move(i);
			solve(match);
			match.back();
		}
	}
}
