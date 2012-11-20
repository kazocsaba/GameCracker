package hu.kazocsaba.gamecracker.game.reversi;

/**
 *
 * @author Kazó Csaba
 */
public class Reversi4Test extends ReversiTest<Reversi4, Reversi4Position, Reversi4Move> {
	@Override
	Reversi4Move[] getBasicWinGame() {
		return new Reversi4Move[]{
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
	};
	@Override
	Reversi4Move[] getDrawGame() {
		return new Reversi4Move[]{
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
	};

	@Override
	Reversi4Position[] createPositionArray(int length) {
		return new Reversi4Position[length];
	}
	
	public Reversi4Test() {
		super(new Reversi4());
	}
	
}
