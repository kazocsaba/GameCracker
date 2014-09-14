package hu.kazocsaba.gamecracker.game.tictactoe;

import hu.kazocsaba.gamecracker.InconsistencyError;
import hu.kazocsaba.gamecracker.game.CategoryFunction;
import hu.kazocsaba.gamecracker.game.GameStatus;
import hu.kazocsaba.gamecracker.game.Player;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.PositionSerializer;
import hu.kazocsaba.gamecracker.game.SquareSymmetry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

/**
 * A position in Tic Tac Toe.
 *
 * @author Kazó Csaba
 */
public class TicTacToePosition extends Position<TicTacToePosition, TicTacToeMove, SquareSymmetry> {

  private final Player[][] board;
  private final GameStatus status;

  static final CategoryFunction<TicTacToePosition> MOVE_COUNT_CATEGORY =
      new CategoryFunction<TicTacToePosition>() {

    @Override
    public long category(TicTacToePosition position) {
      int count = 0;
      for (int x = 0; x < 3; x++) {
        for (int y = 0; y < 3; y++) {
          if (position.board[x][y] != null) {
            count++;
          }
        }
      }
      return count;
    }
  };
  static final PositionSerializer<TicTacToePosition> SERIALIZER =
      new PositionSerializer<TicTacToePosition>() {
    private int cellCode(Player cell) {
      if (cell == null) {
        return 0;
      }
      if (cell == Player.WHITE) {
        return 1;
      }
      return 2;
    }

    private Player cellDecode(int code) {
      switch (code) {
        case 0: return null;
        case 1: return Player.WHITE;
        case 2: return Player.BLACK;
        default: throw new InconsistencyError("Invalid cell code: " + code);
      }
    }

    @Override
    public int getPositionMaxSerializedSize() {
      return 3;
    }

    @Override
    public void writePosition(TicTacToePosition position, DataOutput out) throws IOException {
      for (int row = 0; row < 3; row++) {
        int code = cellCode(position.board[0][row]);
        code = (code << 2) | cellCode(position.board[1][row]);
        code = (code << 2) | cellCode(position.board[2][row]);
        out.writeByte(code);
      }
    }

    @Override
    public TicTacToePosition readPosition(DataInput in) throws IOException {
      Player[][] board = new Player[3][3];
      int whiteCount = 0;
      int blackCount = 0;
      for (int row = 0; row < 3; row++) {
        int code = in.readByte();
        board[2][row] = cellDecode(code & 3);
        code = code >> 2;
        board[1][row] = cellDecode(code & 3);
        code = code >> 2;
        board[0][row] = cellDecode(code);
        for (int col = 0; col < 3; col++) {
          if (board[col][row] == Player.WHITE) {
            whiteCount++;
          } else if (board[col][row] == Player.BLACK) {
            blackCount++;
          }
        }
      }
      if (whiteCount < blackCount || whiteCount > blackCount + 1) {
        throw new InconsistencyError(
            String.format("Invalid position; white has %d, black has %d", whiteCount, blackCount));
      }
      GameStatus status =
          whiteCount == blackCount ? GameStatus.WHITE_MOVES : GameStatus.BLACK_MOVES;
      // check for win
      if (board[1][1] != null) {
        if (board[0][0] == board[1][1] && board[2][2] == board[1][1]) {
          status = board[1][1].getWinStatus();
        } else if (board[0][1] == board[1][1] && board[2][1] == board[1][1]) {
          status = board[1][1].getWinStatus();
        } else if (board[0][2] == board[1][1] && board[2][0] == board[1][1]) {
          status = board[1][1].getWinStatus();
        } else if (board[1][0] == board[1][1] && board[1][2] == board[1][1]) {
          status = board[1][1].getWinStatus();
        }
      }
      if (board[0][0] != null) {
        if (board[1][0] == board[0][0] && board[2][0] == board[0][0]) {
          if (status == board[0][0].getOther().getWinStatus()) {
            throw new InconsistencyError("Invalid positions: 3-in-a-row for both players");
          }
          status = board[0][0].getWinStatus();
        } else if (board[0][1] == board[0][0] && board[0][2] == board[0][0]) {
          if (status == board[0][0].getOther().getWinStatus()) {
            throw new InconsistencyError("Invalid positions: 3-in-a-row for both players");
          }
          status = board[0][0].getWinStatus();
        }
      }
      if (board[2][2] != null) {
        if (board[1][2] == board[2][2] && board[0][2] == board[2][2]) {
          if (status == board[2][2].getOther().getWinStatus()) {
            throw new InconsistencyError("Invalid positions: 3-in-a-row for both players");
          }
          status = board[2][2].getWinStatus();
        } else if (board[2][1] == board[2][2] && board[2][0] == board[2][2]) {
          if (status == board[2][2].getOther().getWinStatus()) {
            throw new InconsistencyError("Invalid positions: 3-in-a-row for both players");
          }
          status = board[2][2].getWinStatus();
        }
      }
      // check for draw
      if (!status.isFinal() && whiteCount + blackCount == 9) {
        status = GameStatus.DRAW;
      }

      return new TicTacToePosition(board, status);
    }
  };

  TicTacToePosition() {
    board = new Player[3][3];
    status = GameStatus.WHITE_MOVES;
  }

  private TicTacToePosition(Player[][] board, GameStatus status) {
    this.board = board;
    this.status = status;
  }

  @Override
  public GameStatus getStatus() {
    return status;
  }

  @Override
  public TicTacToePosition move(TicTacToeMove move) {
    if (board[move.getX()][move.getY()] != null) {
      throw new IllegalArgumentException("Illegal move: " + move);
    }
    Player[][] newBoard = new Player[3][3];
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        newBoard[x][y] = board[x][y];
      }
    }
    Player movingPlayer = status.getCurrentPlayer();
    newBoard[move.getX()][move.getY()] = movingPlayer;
    GameStatus newStatus = status.getOther();

    // check if the moving player won
    if (move.getX() == 0) {
      if (newBoard[1][move.getY()] == movingPlayer && newBoard[2][move.getY()] == movingPlayer) {
        // row
        newStatus = movingPlayer.getWinStatus();
      } else if (newBoard[0][0] == movingPlayer && newBoard[0][1] == movingPlayer
          && newBoard[0][2] == movingPlayer) {
        // column
        newStatus = movingPlayer.getWinStatus();
      } else if (move.getY() != 1 && newBoard[1][1] == movingPlayer
          && newBoard[2][2 - move.getY()] == movingPlayer) {
        // diagonal
        newStatus = movingPlayer.getWinStatus();
      }
    } else if (move.getX() == 1) {
      if (newBoard[0][move.getY()] == movingPlayer && newBoard[2][move.getY()] == movingPlayer) {
        // row
        newStatus = movingPlayer.getWinStatus();
      } else if (newBoard[1][0] == movingPlayer && newBoard[1][1] == movingPlayer
          && newBoard[1][2] == movingPlayer) {
        // column
        newStatus = movingPlayer.getWinStatus();
      } else if (move.getY() == 1 && (
          (newBoard[0][0] == movingPlayer && newBoard[2][2] == movingPlayer)
          || (newBoard[0][2] == movingPlayer && newBoard[2][0] == movingPlayer))) {
        // diagonal
        newStatus = movingPlayer.getWinStatus();
      }
    } else {
      if (newBoard[0][move.getY()] == movingPlayer && newBoard[1][move.getY()] == movingPlayer) {
        // row
        newStatus = movingPlayer.getWinStatus();
      } else if (newBoard[2][0] == movingPlayer && newBoard[2][1] == movingPlayer
          && newBoard[2][2] == movingPlayer) {
        // column
        newStatus = movingPlayer.getWinStatus();
      } else if (move.getY() != 1 && newBoard[1][1] == movingPlayer
          && newBoard[0][2 - move.getY()] == movingPlayer) {
        // diagonal
        newStatus = movingPlayer.getWinStatus();
      }
    }
    if (!newStatus.isFinal()) {
      // check for draw
      boolean hasEmpty = false;
      for (int x = 0; x < 3; x++) {
        for (int y = 0; y < 3; y++) {
          if (newBoard[x][y] == null) {
            hasEmpty = true;
            break;
          }
        }
      }
      if (!hasEmpty) {
        newStatus = GameStatus.DRAW;
      }
    }
    return new TicTacToePosition(newBoard, newStatus);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof TicTacToePosition)) {
      return false;
    }

    TicTacToePosition other = (TicTacToePosition) obj;
    if (status != other.status) {
      return false;
    }
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        if (board[x][y] != other.board[x][y]) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + Arrays.deepHashCode(this.board);
    hash = 37 * hash + status.hashCode();
    return hash;
  }

  @Override
  public List<TicTacToeMove> getMoves() {
    if (status.isFinal()) {
      return Collections.emptyList();
    }

    List<TicTacToeMove> moves = new ArrayList<>(9);
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        if (board[x][y] == null) {
          moves.add(TicTacToeMove.get(x, y));
        }
      }
    }
    return moves;
  }

  @Override
  public TicTacToePosition transform(SquareSymmetry t) {
    Player[][] newBoard = new Player[3][3];
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        newBoard[t.transformX(x, y, 3)][t.transformY(x, y, 3)] = board[x][y];
      }
    }
    return new TicTacToePosition(newBoard, status);
  }

  @Override
  public SquareSymmetry getTransformationTo(TicTacToePosition target) {
    if (status != target.status) {
      return null;
    }
    EnumSet<SquareSymmetry> possibleTransformations = EnumSet.allOf(SquareSymmetry.class);

    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        Player myCell = board[x][y];
        for (Iterator<SquareSymmetry> transformIterator = possibleTransformations.iterator();
            transformIterator.hasNext();) {
          SquareSymmetry trans = transformIterator.next();
          Player targetCell = target.board[trans.transformX(x, y, 3)][trans.transformY(x, y, 3)];
          if (myCell != targetCell) {
            transformIterator.remove();
          }
        }
        if (possibleTransformations.isEmpty()) {
          return null;
        }
      }
    }
    if (possibleTransformations.contains(SquareSymmetry.IDENTITY)) {
      return SquareSymmetry.IDENTITY;
    }
    return possibleTransformations.iterator().next();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(13);
    sb.append('[');
    for (int y = 2; y >= 0; y--) {
      for (int x = 0; x < 3; x++) {
        sb.append(board[x][y] == null ? '.' : board[x][y] == Player.WHITE ? 'O' : 'X');
      }
      if (y > 0) {
        sb.append('|');
      }
    }
    sb.append(']');
    return sb.toString();
  }

  /**
   * Returns the player occupying a cell.
   *
   * @param x the column of the cell
   * @param y the row of the cell
   * @return the player occupying the cell, or {@code null} if the cell is empty
   */
  public Player getCell(int x, int y) {
    return board[x][y];
  }
}
