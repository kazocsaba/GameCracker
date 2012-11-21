package hu.kazocsaba.gamecracker.game.reversi;

import java.util.List;
import hu.kazocsaba.gamecracker.game.GameStatus;
import hu.kazocsaba.gamecracker.game.SwitchableSquareSymmetry;
import hu.kazocsaba.gamecracker.game.testing.AbstractGameTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 *
 * @author Kazó Csaba
 */
public abstract class ReversiTest<G extends Reversi<P, M>, P extends ReversiPosition<P, M>, M extends ReversiMove<M>> extends AbstractGameTest<G, P, M, SwitchableSquareSymmetry> {
	class TestGame {
		private final M[] moves;
		private final GameStatus result;

		public TestGame(M[] moves, GameStatus result) {
			this.moves = moves;
			this.result = result;
		}
		
	}
	abstract P[] createPositionArray(int length);
	abstract List<TestGame> getTestGames();

	public ReversiTest(G game) {
		super(game);
	}
	
	@DataProvider(name="game", parallel=true)
	Object[][] gameProvider() {
		List<TestGame> games=getTestGames();
		Object[][] result=new Object[games.size()][2];
		for (int i=0; i<games.size(); i++) {
			result[i][0]=games.get(i).moves;
			result[i][1]=games.get(i).result;
		}
		return result;
	}
	/*
	 * Verifies that all the moves can be parsed from their toString representations.
	 */
	@Test(dataProvider="game")
	public void testMoveParsing(M[] moves, GameStatus expectedResult) {
		for (M move: moves)
			assertEquals(game.parseMove(move.toString()), move);
	}
	
	/*
	 * Verifies the games, checking that the moves are valid and can be applied, the intermediate positions
	 * are not final, and that the final position is correct.
	 */
	@Test(dataProvider="game")
	public void testGame(M[] moves, GameStatus expectedResult) {
		P pos=game.getInitialPosition();
		for (M move: moves) {
			assertFalse(pos.getStatus().isFinal());
			assertTrue(pos.getMoves().contains(move));
			pos=pos.move(move);
		}
		assertEquals(pos.getStatus(), expectedResult);
	}
	
	/*
	 * Plays out the games along with all their transformations. Starting from the transformations of the
	 * initial positions, it checks that the transformations of the moves can be applied, the intermediate positions
	 * are not final, the final position's result is a transformation of 'White wins'. For the intermediate positions,
	 * it also verifies that the corresponding transformations still apply.
	 */
	@Test(dataProvider="game")
	public void testSymmetries(M[] moves, GameStatus expectedResult) {
		P[] pos=createPositionArray(SwitchableSquareSymmetry.values().length);
		for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values())
			pos[symm.ordinal()]=game.getInitialPosition().transform(symm);
		
		for (M move: moves) {
			for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values()) {
				assertFalse(pos[symm.ordinal()].getStatus().isFinal());
				M transformedMove=move.transform(symm);
				assertTrue(pos[symm.ordinal()].getMoves().contains(transformedMove));
				pos[symm.ordinal()]=pos[symm.ordinal()].move(transformedMove);
			}
			for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values()) {
				assertPositionsTransformed(pos[SwitchableSquareSymmetry.IDENTITY.ordinal()], symm, pos[symm.ordinal()]);
			}
		}
		for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values())
			assertEquals(pos[symm.ordinal()].getStatus(), symm.isPlayerSwitching() ? expectedResult.getOther() : expectedResult);
	}
	/*
	 * Plays out the games along with all their transformations, verifying that the transformed move is valid,
	 * and testing the serialization of each encountered position.
	 */
	@Test(dataProvider="game")
	public void testIO(M[] moves, GameStatus expectedResult) {
		P[] pos=createPositionArray(SwitchableSquareSymmetry.values().length);
		for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values()) {
			pos[symm.ordinal()]=game.getInitialPosition().transform(symm);
			testPositionSerializer(pos[symm.ordinal()]);
		}
		
		for (M move: moves) {
			for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values()) {
				M transformedMove=move.transform(symm);
				assertTrue(pos[symm.ordinal()].getMoves().contains(transformedMove));
				pos[symm.ordinal()]=pos[symm.ordinal()].move(transformedMove);
				testPositionSerializer(pos[symm.ordinal()]);
			}
		}
	}
}
