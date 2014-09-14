package hu.kazocsaba.gamecracker.game.reversi;

import hu.kazocsaba.gamecracker.InconsistencyError;
import hu.kazocsaba.gamecracker.game.CategoryFunction;
import hu.kazocsaba.gamecracker.game.GameStatus;
import hu.kazocsaba.gamecracker.game.PositionSerializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A position of 6x6 Reversi.
 *
 * @author Kazó Csaba
 */
public class Reversi6Position extends ReversiPosition<Reversi6Position, Reversi6Move> {

  static final PositionSerializer<Reversi6Position> SERIALIZER =
      new PositionSerializer<Reversi6Position>() {

    @Override
    public int getPositionMaxSerializedSize() {
      return 1 + ReversiBoard.getMaxSerializedSize();
    }

    @Override
    public void writePosition(Reversi6Position position, DataOutput out) throws IOException {
      out.writeByte((byte) position.getStatus().ordinal());
      position.board.write(out);
    }

    @Override
    public Reversi6Position readPosition(DataInput in) throws IOException {
      int statusOrdinal = in.readByte() & 0xFF;
      if (statusOrdinal < 0 || statusOrdinal >= GameStatus.getCount()) {
        throw new InconsistencyError("Invalid GameStatus ordinal: " + statusOrdinal);
      }
      return new Reversi6Position(GameStatus.getByOrdinal(statusOrdinal), ReversiBoard.read(6, in));
    }
  };
  static final CategoryFunction<Reversi6Position> CATEGORY_FUNCTION =
      new CategoryFunction<Reversi6Position>() {

    @Override
    public long category(Reversi6Position position) {
      return position.board.getTokenCount();
    }
  };

  Reversi6Position(GameStatus status, ReversiBoard board) {
    super(status, board);
  }

  @Override
  int getSize() {
    return 6;
  }

  @Override
  Reversi6Move getMove(int x, int y) {
    return Reversi6Move.get(x, y);
  }

  @Override
  Reversi6Position create(ReversiBoard board, GameStatus status) {
    return new Reversi6Position(status, board);
  }

}
