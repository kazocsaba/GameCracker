package hu.kazocsaba.gamecracker.graph.memory;

import hu.kazocsaba.gamecracker.game.Match;
import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.SquareSymmetry;
import hu.kazocsaba.gamecracker.game.SwitchableSquareSymmetry;
import hu.kazocsaba.gamecracker.game.Transformation;
import hu.kazocsaba.gamecracker.game.reversi.Reversi4;
import hu.kazocsaba.gamecracker.game.reversi.Reversi4Move;
import hu.kazocsaba.gamecracker.game.reversi.Reversi4Position;
import hu.kazocsaba.gamecracker.game.tictactoe.TicTacToe;
import hu.kazocsaba.gamecracker.game.tictactoe.TicTacToeMove;
import hu.kazocsaba.gamecracker.game.tictactoe.TicTacToePosition;
import hu.kazocsaba.gamecracker.graph.GraphMatch;
import hu.kazocsaba.gamecracker.graph.GraphResult;
import java.util.Iterator;
import java.util.List;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Kazó Csaba
 */
public class MemoryGraphNGTest {
	@Test(timeOut=5000)
	public void testTicTacToe() {
		MemoryGraph<TicTacToePosition, TicTacToeMove, SquareSymmetry> graph=new MemoryGraph<>(new TicTacToe());
		solve(graph.createMatch());
		
		assertEquals(graph.createMatch().getResult(), GraphResult.DRAW);
	}
	@Test(timeOut=5000)
	public void testReversi() {
		MemoryGraph<Reversi4Position, Reversi4Move, SwitchableSquareSymmetry> graph=new MemoryGraph<>(new Reversi4());
		solve(graph.createMatch());
		
		assertEquals(graph.createMatch().getResult(), GraphResult.BLACK_WINS);
	}
	
	private static void solve(GraphMatch<?,?,?> match) {
		testMatch(match);
		if (match.getMoveCount()==0) {
			assertTrue(match.getPosition().getStatus().isFinal());
			assertEquals(match.getResult().asGameStatus(), match.getPosition().getStatus());
		} else {
			for (int i=0; i<match.getMoveCount() && !match.getResult().isKnown(); i++) {
				match.move(i);
				solve(match);
				assertTrue(match.getResult().isKnown());
				match.back();
			}
		}
	}
	private static <
			P extends Position<P, M, T>,
			M extends Move<M, T>,
			T extends Transformation<T>> void testMatch(GraphMatch<P,M,T> match) {
		{
			List<M> positionReportedMoves = match.getPosition().getMoves();
			assertEquals(match.getMoveCount(), positionReportedMoves.size());
			for (int i=0; i<match.getMoveCount(); i++)
				assertEquals(match.getMove(i), positionReportedMoves.get(i));
		}
		
		int matchLength=match.getLength();
		int pointCount=0;
		
		Match.Point<P,M,T> lastPoint=null;
		for (Iterator<Match.Point<P,M,T>> it = match.iterator(); it.hasNext();) {
			Match.Point<P,M,T> point = it.next();
			pointCount++;
			
			if (lastPoint!=null) {
				assertTrue(lastPoint.getPosition().getMoves().contains(lastPoint.getMove()));
				assertEquals(point.getPosition(), lastPoint.getPosition().move(lastPoint.getMove()));
			}
			
			lastPoint=point;
			// if there is a move, there should be a next position
			assertEquals(it.hasNext(), point.getMove()!=null);
		}
		assertEquals(matchLength, pointCount-1);
	}
}
