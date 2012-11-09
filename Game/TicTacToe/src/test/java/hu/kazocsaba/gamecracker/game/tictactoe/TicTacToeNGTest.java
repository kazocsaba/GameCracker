package hu.kazocsaba.gamecracker.game.tictactoe;

import java.util.Arrays;
import java.util.HashSet;
import hu.kazocsaba.gamecracker.game.GameStatus;
import hu.kazocsaba.gamecracker.game.SquareSymmetry;
import hu.kazocsaba.gamecracker.game.testing.AbstractGameTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 *
 * @author Kazó Csaba
 */
public class TicTacToeNGTest extends AbstractGameTest<TicTacToe, TicTacToePosition, TicTacToeMove, SquareSymmetry> {

	public TicTacToeNGTest() {
		super(new TicTacToe());
	}
	
	@Test
	public void basicGameTest() {
		TicTacToePosition position=game.getInitialPosition();
		position=position.move(TicTacToeMove.get(1, 1));
		position=position.move(TicTacToeMove.get(0, 1));
		position=position.move(TicTacToeMove.get(1, 2));
		position=position.move(TicTacToeMove.get(2, 2));
		position=position.move(TicTacToeMove.get(1, 0));
		assertEquals(position.getStatus(), GameStatus.WHITE_WINS);
	}
	
	@Test
	public void testTransformations() {
		TicTacToePosition position=game.getInitialPosition();
		
		TicTacToePosition position1=position.move(TicTacToeMove.get(0, 2));
		TicTacToePosition position2=position.move(TicTacToeMove.get(0, 0));
		
		SquareSymmetry trans = position1.getTransformationTo(position2);
		
		assertTrue(trans==SquareSymmetry.VERTICAL_REFLECTION || trans==SquareSymmetry.ROTATION_270);
		assertPositionsTransformed(position1, SquareSymmetry.VERTICAL_REFLECTION, position2);
		assertPositionsTransformed(position1, SquareSymmetry.ROTATION_270, position2);
		
		position1=position1.move(TicTacToeMove.get(0, 1));
		position2=position2.move(TicTacToeMove.get(0, 1));
		
		assertEquals(position1.getTransformationTo(position2), SquareSymmetry.VERTICAL_REFLECTION);
		assertPositionsTransformed(position1, SquareSymmetry.VERTICAL_REFLECTION, position2);
		assertNotEquals(position1.transform(SquareSymmetry.ROTATION_270), position2);
		
		// check moves
		HashSet<TicTacToeMove> moves1=new HashSet<>(position1.getMoves());
		HashSet<TicTacToeMove> moves2=new HashSet<>(position2.getMoves());
		for (TicTacToeMove m: moves1)
			assertTrue(moves2.contains(m.transform(SquareSymmetry.VERTICAL_REFLECTION)));
		
		position1=position1.move(TicTacToeMove.get(0, 0));
		position2=position2.move(TicTacToeMove.get(0, 2));
		
		assertPositionsEqual(position1, position2);
	}
	
	@Test
	public void gameTest() {
		TicTacToePosition position=game.getInitialPosition();
		
		position=position.move(TicTacToeMove.get(1, 0));
		position=position.move(TicTacToeMove.get(2, 1));
		position=position.move(TicTacToeMove.get(0, 0));
		position=position.move(TicTacToeMove.get(2, 0));
		position=position.move(TicTacToeMove.get(2, 2));
		position=position.move(TicTacToeMove.get(1, 2));
		
		assertEquals(new HashSet<>(position.getMoves()), new HashSet<>(Arrays.asList(TicTacToeMove.get(0, 1), TicTacToeMove.get(0, 2), TicTacToeMove.get(1, 1))));
		assertEquals(position.move(TicTacToeMove.get(0, 1)).getStatus(), GameStatus.BLACK_MOVES);
		assertEquals(position.move(TicTacToeMove.get(0, 2)).getStatus(), GameStatus.BLACK_MOVES);
		assertEquals(position.move(TicTacToeMove.get(1, 1)).getStatus(), GameStatus.WHITE_WINS);
	}
	
	@Test
	public void winTest() {
		// win on edge with move in middle
		TicTacToePosition position=game.getInitialPosition();
		
		position=position.move(TicTacToeMove.get(0, 0));
		position=position.move(TicTacToeMove.get(1, 1));
		position=position.move(TicTacToeMove.get(2, 0));
		position=position.move(TicTacToeMove.get(2, 2));
		for (SquareSymmetry trans: SquareSymmetry.values()) {
			TicTacToePosition transformedPosition=position.transform(trans);
			assertEquals(transformedPosition.move(TicTacToeMove.get(1, 0).transform(trans)).getStatus(), GameStatus.WHITE_WINS);
		}
		
		// win in middle with move in middle
		position=game.getInitialPosition();
		
		position=position.move(TicTacToeMove.get(0, 1));
		position=position.move(TicTacToeMove.get(1, 0));
		position=position.move(TicTacToeMove.get(2, 1));
		position=position.move(TicTacToeMove.get(2, 2));
		for (SquareSymmetry trans: SquareSymmetry.values()) {
			TicTacToePosition transformedPosition=position.transform(trans);
			assertEquals(transformedPosition.move(TicTacToeMove.get(1, 1).transform(trans)).getStatus(), GameStatus.WHITE_WINS);
		}
		
		// win in diagonal with move in middle
		position=game.getInitialPosition();
		
		position=position.move(TicTacToeMove.get(0, 0));
		position=position.move(TicTacToeMove.get(1, 0));
		position=position.move(TicTacToeMove.get(2, 2));
		position=position.move(TicTacToeMove.get(2, 0));
		for (SquareSymmetry trans: SquareSymmetry.values()) {
			TicTacToePosition transformedPosition=position.transform(trans);
			assertEquals(transformedPosition.move(TicTacToeMove.get(1, 1).transform(trans)).getStatus(), GameStatus.WHITE_WINS);
		}
		
		// win in middle with move on edge
		position=game.getInitialPosition();
		
		position=position.move(TicTacToeMove.get(1, 1));
		position=position.move(TicTacToeMove.get(1, 0));
		position=position.move(TicTacToeMove.get(2, 1));
		position=position.move(TicTacToeMove.get(2, 2));
		for (SquareSymmetry trans: SquareSymmetry.values()) {
			TicTacToePosition transformedPosition=position.transform(trans);
			assertEquals(transformedPosition.move(TicTacToeMove.get(0, 1).transform(trans)).getStatus(), GameStatus.WHITE_WINS);
		}
		
		// win in diagonal with move in corner
		position=game.getInitialPosition();
		
		position=position.move(TicTacToeMove.get(0, 0));
		position=position.move(TicTacToeMove.get(1, 0));
		position=position.move(TicTacToeMove.get(1, 1));
		position=position.move(TicTacToeMove.get(2, 0));
		for (SquareSymmetry trans: SquareSymmetry.values()) {
			TicTacToePosition transformedPosition=position.transform(trans);
			assertEquals(transformedPosition.move(TicTacToeMove.get(2, 2).transform(trans)).getStatus(), GameStatus.WHITE_WINS);
		}
	}
	
	@Test
	public void positionIOTest() {
		// win on edge with move in middle
		TicTacToePosition position=game.getInitialPosition();
		
		position=position.move(TicTacToeMove.get(0, 0));
		position=position.move(TicTacToeMove.get(1, 1));
		position=position.move(TicTacToeMove.get(2, 0));
		position=position.move(TicTacToeMove.get(2, 2));
		for (SquareSymmetry trans: SquareSymmetry.values()) {
			TicTacToePosition transformedPosition=position.transform(trans);
			testPositionSerializer(transformedPosition);
			
			transformedPosition=transformedPosition.move(TicTacToeMove.get(1, 0).transform(trans));
			testPositionSerializer(transformedPosition);
		}
		
		// win in middle with move in middle
		position=game.getInitialPosition();
		
		position=position.move(TicTacToeMove.get(0, 1));
		position=position.move(TicTacToeMove.get(1, 0));
		position=position.move(TicTacToeMove.get(2, 1));
		position=position.move(TicTacToeMove.get(2, 2));
		for (SquareSymmetry trans: SquareSymmetry.values()) {
			TicTacToePosition transformedPosition=position.transform(trans);
			testPositionSerializer(transformedPosition);
			
			transformedPosition=transformedPosition.move(TicTacToeMove.get(1, 1).transform(trans));
			testPositionSerializer(transformedPosition);
		}
		
		// win in diagonal with move in middle
		position=game.getInitialPosition();
		
		position=position.move(TicTacToeMove.get(0, 0));
		position=position.move(TicTacToeMove.get(1, 0));
		position=position.move(TicTacToeMove.get(2, 2));
		position=position.move(TicTacToeMove.get(2, 0));
		for (SquareSymmetry trans: SquareSymmetry.values()) {
			TicTacToePosition transformedPosition=position.transform(trans);
			testPositionSerializer(transformedPosition);
			
			transformedPosition=transformedPosition.move(TicTacToeMove.get(1, 1).transform(trans));
			testPositionSerializer(transformedPosition);
		}
		
		// win in middle with move on edge
		position=game.getInitialPosition();
		
		position=position.move(TicTacToeMove.get(1, 1));
		position=position.move(TicTacToeMove.get(1, 0));
		position=position.move(TicTacToeMove.get(2, 1));
		position=position.move(TicTacToeMove.get(2, 2));
		for (SquareSymmetry trans: SquareSymmetry.values()) {
			TicTacToePosition transformedPosition=position.transform(trans);
			testPositionSerializer(transformedPosition);
			
			transformedPosition=transformedPosition.move(TicTacToeMove.get(0, 1).transform(trans));
			testPositionSerializer(transformedPosition);
		}
		
		// win in diagonal with move in corner
		position=game.getInitialPosition();
		
		position=position.move(TicTacToeMove.get(0, 0));
		position=position.move(TicTacToeMove.get(1, 0));
		position=position.move(TicTacToeMove.get(1, 1));
		position=position.move(TicTacToeMove.get(2, 0));
		for (SquareSymmetry trans: SquareSymmetry.values()) {
			TicTacToePosition transformedPosition=position.transform(trans);
			testPositionSerializer(transformedPosition);
			
			transformedPosition=transformedPosition.move(TicTacToeMove.get(2, 2).transform(trans));
			testPositionSerializer(transformedPosition);
		}
	}
}
