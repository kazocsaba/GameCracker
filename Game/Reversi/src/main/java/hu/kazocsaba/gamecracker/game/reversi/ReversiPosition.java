package hu.kazocsaba.gamecracker.game.reversi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import hu.kazocsaba.gamecracker.game.GameStatus;
import hu.kazocsaba.gamecracker.game.Player;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.SwitchableSquareSymmetry;

/**
 * A Reversi position.
 * @author Kazó Csaba
 */
public abstract class ReversiPosition<P extends ReversiPosition<P,M>, M extends ReversiMove<M>> extends Position<P, M, SwitchableSquareSymmetry> {
	private final GameStatus status;
	// the board itself is technically not immutable, but once the position is constructed, it is not altered any more
	final ReversiBoard board;

	ReversiPosition(GameStatus status, ReversiBoard board) {
		this.status = status;
		this.board = board;
	}
	/**
	 * Returns the player currently occupying a cell.
	 * @param x the column index of the cell
	 * @param y the row index of the cell
	 * @return the player who has a tokan on the cell, or {@code null} if the cell is empty
	 */
	public Player getCell(int x, int y) {
		if (x<0 || x>=getSize()) throw new IndexOutOfBoundsException("Invalid x: "+x);
		if (y<0 || y>=getSize()) throw new IndexOutOfBoundsException("Invalid y: "+y);
		return board.getCell(x, y);
	}
	abstract int getSize();
	abstract M getMove(int x, int y);
	abstract P create(ReversiBoard board, GameStatus status);

	@Override
	public GameStatus getStatus() {
		return status;
	}

	@Override
	public List<M> getMoves() {
		if (status.isFinal()) return Collections.emptyList();
		List<M> moves=new ArrayList<>();
		Player currentPlayer=status.getCurrentPlayer();
		for (int x=0; x<getSize(); x++) for (int y=0; y<getSize(); y++)
			if (board.isMoveValid(currentPlayer, x, y, getSize())) moves.add(getMove(x, y));
		return moves;
	}
	
	@Override
	public P move(M move) {
		final int x = move.getX();
		final int y = move.getY();
		final Player player=status.getCurrentPlayer();
		
		if (board.getCell(x, y)!=null) throw new IllegalArgumentException("Invalid move: "+move);
		
		boolean moveValid=false;
		
		ReversiBoard newBoard=new ReversiBoard(board);
		newBoard.setCell(x, y, player);
		
		// check towards +x
		if (x<getSize()-2 && board.getCell(x+1, y)==player.getOther()) {
			for (int ix=x+2; ix<getSize(); ix++) {
				Player cellContents=board.getCell(ix, y);
				if (cellContents==player) {
					moveValid=true;
					for (int j=x+1; j<ix; j++) newBoard.setCell(j, y, player);
					break;
				}
				if (cellContents==null) break;
			}
		}
		// check towards +y
		if (y<getSize()-2 && board.getCell(x, y+1)==player.getOther()) {
			for (int iy=y+2; iy<getSize(); iy++) {
				Player cellContents=board.getCell(x, iy);
				if (cellContents==player) {
					moveValid=true;
					for (int j=y+1; j<iy; j++) newBoard.setCell(x, j, player);
					break;
				}
				if (cellContents==null) break;
			}
		}
		// check towards -x
		if (x>1 && board.getCell(x-1, y)==player.getOther()) {
			for (int ix=x-2; ix>=0; ix--) {
				Player cellContents=board.getCell(ix, y);
				if (cellContents==player) {
					moveValid=true;
					for (int j=x-1; j>ix; j--) newBoard.setCell(j, y, player);
					break;
				}
				if (cellContents==null) break;
			}
		}
		// check towards -y
		if (y>1 && board.getCell(x, y-1)==player.getOther()) {
			for (int iy=y-2; iy>=0; iy--) {
				Player cellContents=board.getCell(x, iy);
				if (cellContents==player) {
					moveValid=true;
					for (int j=y-1; j>iy; j--) newBoard.setCell(x, j, player);
					break;
				}
				if (cellContents==null) break;
			}
		}
		// check towards +x+y
		if (x<getSize()-2 && y<getSize()-2 && board.getCell(x+1, y+1)==player.getOther()) {
			for (int d=2; x+d<getSize() && y+d<getSize(); d++) {
				Player cellContents=board.getCell(x+d, y+d);
				if (cellContents==player) {
					moveValid=true;
					for (int j=1; j<d; j++) newBoard.setCell(x+j, y+j, player);
					break;
				}
				if (cellContents==null) break;
			}
		}
		// check towards +x-y
		if (x<getSize()-2 && y>1 && board.getCell(x+1, y-1)==player.getOther()) {
			for (int d=2; x+d<getSize() && y-d>=0; d++) {
				Player cellContents=board.getCell(x+d, y-d);
				if (cellContents==player) {
					moveValid=true;
					for (int j=1; j<d; j++) newBoard.setCell(x+j, y-j, player);
					break;
				}
				if (cellContents==null) break;
			}
		}
		// check towards -x+y
		if (x>1 && y<getSize()-2 && board.getCell(x-1, y+1)==player.getOther()) {
			for (int d=2; x-d>=0 && y+d<getSize(); d++) {
				Player cellContents=board.getCell(x-d, y+d);
				if (cellContents==player) {
					moveValid=true;
					for (int j=1; j<d; j++) newBoard.setCell(x-j, y+j, player);
					break;
				}
				if (cellContents==null) break;
			}
		}
		// check towards -x-y
		if (x>1 && y>1 && board.getCell(x-1, y-1)==player.getOther()) {
			for (int d=2; x-d>=0 && y-d>=0; d++) {
				Player cellContents=board.getCell(x-d, y-d);
				if (cellContents==player) {
					moveValid=true;
					for (int j=1; j<d; j++) newBoard.setCell(x-j, y-j, player);
					break;
				}
				if (cellContents==null) break;
			}
		}
		if (!moveValid) throw new IllegalArgumentException("Invalid move: "+move);
		
		return create(newBoard, computeStatus(newBoard, player.getOther()));
	}
	private GameStatus computeStatus(ReversiBoard board, Player nextPlayer) {
		boolean currentPlayerCanMove=false;
		int whiteCount=0;
		int blackCount=0;
		Player currentPlayer = nextPlayer.getOther();
		
		for (int x=0; x<getSize(); x++) for (int y=0; y<getSize(); y++) {
			if (board.isMoveValid(nextPlayer, x, y, getSize())) return nextPlayer.getMoveStatus();
			if (!currentPlayerCanMove && board.isMoveValid(currentPlayer, x, y, getSize())) currentPlayerCanMove=true;
			Player cellContents=board.getCell(x, y);
			if (cellContents==Player.WHITE)
				whiteCount++;
			else if (cellContents==Player.BLACK)
				blackCount++;
		}
		if (currentPlayerCanMove)
			return currentPlayer.getMoveStatus();
		if (whiteCount>blackCount)
			return GameStatus.WHITE_WINS;
		else if (whiteCount<blackCount)
			return GameStatus.BLACK_WINS;
		else
			return GameStatus.DRAW;
	}

	@Override
	public P transform(SwitchableSquareSymmetry t) {
		ReversiBoard newBoard=new ReversiBoard();
		
		for (int x=0; x<getSize(); x++) for (int y=0; y<getSize(); y++) {
			Player cellContents=board.getCell(x, y);
			if (cellContents!=null) {
				newBoard.setCell(t.transformX(x, y, getSize()), t.transformY(x, y, getSize()), t.isPlayerSwitching() ? cellContents.getOther() : cellContents);
			}
		}
		
		return create(newBoard, t.isPlayerSwitching() ? status.getOther() : status);
	}

	@Override
	public SwitchableSquareSymmetry getTransformationTo(P target) {
		if (status.isFinal() != target.getStatus().isFinal()) return null;
		if (board.getTokenCount()!=target.board.getTokenCount()) return null;
		
		if (status==target.getStatus() && board.equals(target.board)) return SwitchableSquareSymmetry.IDENTITY;
		
		EnumSet<SwitchableSquareSymmetry> possibilities=EnumSet.allOf(SwitchableSquareSymmetry.class);
		possibilities.remove(SwitchableSquareSymmetry.IDENTITY);
		
		for (Iterator<SwitchableSquareSymmetry> it=possibilities.iterator(); it.hasNext();) {
			if (!checkTransformation(target, it.next())) it.remove();
		}
		if (possibilities.isEmpty()) return null;
		return possibilities.iterator().next();
	}
	
	private boolean checkTransformation(P target, SwitchableSquareSymmetry trans) {
		if (trans.isPlayerSwitching()) {
			if (status.getOther()!=target.getStatus()) return false;
			for (int x=0; x<getSize(); x++) for (int y=0; y<getSize(); y++) {
				Player myCell=board.getCell(x, y);
				Player herCell=target.board.getCell(trans.transformX(x, y, getSize()), trans.transformY(x, y, getSize()));
				if (myCell==null) {
					if (herCell!=null) return false;
				} else {
					if (herCell!=myCell.getOther()) return false;
				}
			}
		} else {
			if (status!=target.getStatus()) return false;
			for (int x=0; x<getSize(); x++) for (int y=0; y<getSize(); y++) {
				Player myCell=board.getCell(x, y);
				Player herCell=target.board.getCell(trans.transformX(x, y, getSize()), trans.transformY(x, y, getSize()));
				if (herCell!=myCell) return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + status.hashCode();
		hash = 89 * hash + board.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final ReversiPosition<?,?> other = (ReversiPosition<?,?>)obj;
		return status == other.status && board.equals(other.board);
	}

}
