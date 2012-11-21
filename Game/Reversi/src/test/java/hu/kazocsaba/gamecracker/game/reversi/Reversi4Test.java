package hu.kazocsaba.gamecracker.game.reversi;

import java.util.ArrayList;
import java.util.List;
import hu.kazocsaba.gamecracker.game.GameStatus;

/**
 *
 * @author Kazó Csaba
 */
public class Reversi4Test extends ReversiTest<Reversi4, Reversi4Position, Reversi4Move> {
	@Override
	List<TestGame> getTestGames() {
		List<TestGame> games = new ArrayList<>();
		games.add(new TestGame(
				new Reversi4Move[]{
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
				},
				GameStatus.WHITE_WINS));
		games.add(new TestGame(
				new Reversi4Move[]{
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
				},
				GameStatus.DRAW));
		return games;
	}

	@Override
	Reversi4Position[] createPositionArray(int length) {
		return new Reversi4Position[length];
	}

	public Reversi4Test() {
		super(new Reversi4());
	}
}
