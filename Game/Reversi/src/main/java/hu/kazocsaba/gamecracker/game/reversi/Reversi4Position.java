package hu.kazocsaba.gamecracker.game.reversi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import hu.kazocsaba.gamecracker.InconsistencyError;
import hu.kazocsaba.gamecracker.game.CategoryFunction;
import hu.kazocsaba.gamecracker.game.GameStatus;
import hu.kazocsaba.gamecracker.game.PositionSerializer;

/**
 *
 * @author Kazó Csaba
 */
public class Reversi4Position extends ReversiPosition<Reversi4Position, Reversi4Move> {
	static final PositionSerializer<Reversi4Position> SERIALIZER=new PositionSerializer<Reversi4Position>() {

		@Override
		public int getPositionMaxSerializedSize() {
			return 1+ReversiBoard.getMaxSerializedSize();
		}

		@Override
		public void writePosition(Reversi4Position position, DataOutput out) throws IOException {
			out.writeByte((byte)position.getStatus().ordinal());
			position.board.write(out);
		}

		@Override
		public Reversi4Position readPosition(DataInput in) throws IOException {
			int statusOrdinal=in.readByte() & 0xFF;
			if (statusOrdinal<0 || statusOrdinal>=GameStatus.VALUES.size()) throw new InconsistencyError("Invalid GameStatus ordinal: "+statusOrdinal);
			return new Reversi4Position(GameStatus.getByOrdinal(statusOrdinal), ReversiBoard.read(4, in));
		}
	};
	static final CategoryFunction<Reversi4Position> CATEGORY_FUNCTION=new CategoryFunction<Reversi4Position>() {

		@Override
		public long category(Reversi4Position position) {
			return position.board.getTokenCount();
		}
	};
	Reversi4Position(GameStatus status, ReversiBoard board) {
		super(status, board);
	}

	@Override
	int getSize() {
		return 4;
	}

	@Override
	Reversi4Move getMove(int x, int y) {
		return Reversi4Move.get(x, y);
	}

	@Override
	Reversi4Position create(ReversiBoard board, GameStatus status) {
		return new Reversi4Position(status, board);
	}

}
