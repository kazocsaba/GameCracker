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
public class Reversi4Test extends AbstractGameTest<Reversi4, Reversi4Position, Reversi4Move, SwitchableSquareSymmetry> {
	private static Reversi4Move[] basicWinGame={
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
	};
	private static Reversi4Move[] drawGame={
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
	};
	
	public Reversi4Test() {
		super(new Reversi4());
	}
	
	@Test
	public void testBasicGame() {
		Reversi4Position pos=game.getInitialPosition();
		for (Reversi4Move move: basicWinGame) {
			assertFalse(pos.getStatus().isFinal());
			assertTrue(pos.getMoves().contains(move));
			pos=pos.move(move);
		}
		assertEquals(pos.getStatus(), GameStatus.WHITE_WINS);
	}
	@Test
	public void testDraw() {
		Reversi4Position pos=game.getInitialPosition();
		for (Reversi4Move move: drawGame) {
			assertFalse(pos.getStatus().isFinal());
			assertTrue(pos.getMoves().contains(move));
			pos=pos.move(move);
		}
		assertEquals(pos.getStatus(), GameStatus.DRAW);
	}
	@Test
	public void testSymmetries() {
		Reversi4Position[] pos=new Reversi4Position[SwitchableSquareSymmetry.values().length];
		for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values())
			pos[symm.ordinal()]=game.getInitialPosition().transform(symm);
		
		for (Reversi4Move move: basicWinGame) {
			for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values()) {
				assertFalse(pos[symm.ordinal()].getStatus().isFinal());
				Reversi4Move transformedMove=move.transform(symm);
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
		Reversi4Position[] pos=new Reversi4Position[SwitchableSquareSymmetry.values().length];
		for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values()) {
			pos[symm.ordinal()]=game.getInitialPosition().transform(symm);
			testPositionSerializer(pos[symm.ordinal()]);
		}
		
		for (Reversi4Move move: basicWinGame) {
			for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values()) {
				Reversi4Move transformedMove=move.transform(symm);
				assertTrue(pos[symm.ordinal()].getMoves().contains(transformedMove));
				pos[symm.ordinal()]=pos[symm.ordinal()].move(transformedMove);
				testPositionSerializer(pos[symm.ordinal()]);
			}
		}
	}
	@Test
	public void testSymmetriesDraw() {
		Reversi4Position[] pos=new Reversi4Position[SwitchableSquareSymmetry.values().length];
		for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values())
			pos[symm.ordinal()]=game.getInitialPosition().transform(symm);
		
		for (Reversi4Move move: drawGame) {
			for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values()) {
				assertFalse(pos[symm.ordinal()].getStatus().isFinal());
				Reversi4Move transformedMove=move.transform(symm);
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
		Reversi4Position[] pos=new Reversi4Position[SwitchableSquareSymmetry.values().length];
		for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values()) {
			pos[symm.ordinal()]=game.getInitialPosition().transform(symm);
			testPositionSerializer(pos[symm.ordinal()]);
		}
		
		for (Reversi4Move move: drawGame) {
			for (SwitchableSquareSymmetry symm: SwitchableSquareSymmetry.values()) {
				Reversi4Move transformedMove=move.transform(symm);
				assertTrue(pos[symm.ordinal()].getMoves().contains(transformedMove));
				pos[symm.ordinal()]=pos[symm.ordinal()].move(transformedMove);
				testPositionSerializer(pos[symm.ordinal()]);
			}
		}
	}
}
