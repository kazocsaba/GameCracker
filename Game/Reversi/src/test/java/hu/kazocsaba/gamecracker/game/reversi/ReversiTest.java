package hu.kazocsaba.gamecracker.game.reversi;

import hu.kazocsaba.gamecracker.game.GameStatus;
import hu.kazocsaba.gamecracker.game.SwitchableSquareSymmetry;
import hu.kazocsaba.gamecracker.game.testing.AbstractGameTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 *
 * @author Kazó Csaba
 */
public abstract class ReversiTest<G extends Reversi<P, M>, P extends ReversiPosition<P, M>, M extends ReversiMove<M>> extends AbstractGameTest<G, P, M, SwitchableSquareSymmetry> {
	abstract M[] getBasicWinGame();
	abstract M[] getDrawGame();
	abstract P[] createPositionArray(int length);

	public ReversiTest(G game) {
		super(game);
	}
	
	@Test
	public void testBasicGame() {
		P pos=game.getInitialPosition();
		for (M move: getBasicWinGame()) {
			assertFalse(pos.getStatus().isFinal());
			assertTrue(pos.getMoves().contains(move));
			pos=pos.move(move);
		}
		assertEquals(pos.getStatus(), GameStatus.WHITE_WINS);
	}
	@Test
	public void testDraw() {
		P pos=game.getInitialPosition();
		for (M move: getDrawGame()) {
			assertFalse(pos.getStatus().isFinal());
			assertTrue(pos.getMoves().contains(move));
			pos=pos.move(move);
		}
		assertEquals(pos.getStatus(), GameStatus.DRAW);
	}
	@Test
	public void testSymmetries() {
		P[] pos=createPositionArray(SwitchableSquareSymmetry.values().length);
		for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values())
			pos[symm.ordinal()]=game.getInitialPosition().transform(symm);
		
		for (M move: getBasicWinGame()) {
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
			assertEquals(pos[symm.ordinal()].getStatus(), symm.isPlayerSwitching() ? GameStatus.BLACK_WINS : GameStatus.WHITE_WINS);
	}
	@Test
	public void testIO() {
		P[] pos=createPositionArray(SwitchableSquareSymmetry.values().length);
		for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values()) {
			pos[symm.ordinal()]=game.getInitialPosition().transform(symm);
			testPositionSerializer(pos[symm.ordinal()]);
		}
		
		for (M move: getBasicWinGame()) {
			for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values()) {
				M transformedMove=move.transform(symm);
				assertTrue(pos[symm.ordinal()].getMoves().contains(transformedMove));
				pos[symm.ordinal()]=pos[symm.ordinal()].move(transformedMove);
				testPositionSerializer(pos[symm.ordinal()]);
			}
		}
	}
	@Test
	public void testSymmetriesDraw() {
		P[] pos=createPositionArray(SwitchableSquareSymmetry.values().length);
		for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values())
			pos[symm.ordinal()]=game.getInitialPosition().transform(symm);
		
		for (M move: getDrawGame()) {
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
			assertEquals(pos[symm.ordinal()].getStatus(), GameStatus.DRAW);
	}
	@Test
	public void testIODraw() {
		P[] pos=createPositionArray(SwitchableSquareSymmetry.values().length);
		for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values()) {
			pos[symm.ordinal()]=game.getInitialPosition().transform(symm);
			testPositionSerializer(pos[symm.ordinal()]);
		}
		
		for (M move: getDrawGame()) {
			for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values()) {
				M transformedMove=move.transform(symm);
				assertTrue(pos[symm.ordinal()].getMoves().contains(transformedMove));
				pos[symm.ordinal()]=pos[symm.ordinal()].move(transformedMove);
				testPositionSerializer(pos[symm.ordinal()]);
			}
		}
	}
}
