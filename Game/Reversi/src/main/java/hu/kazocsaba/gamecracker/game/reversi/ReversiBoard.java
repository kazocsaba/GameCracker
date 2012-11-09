package hu.kazocsaba.gamecracker.game.reversi;

import hu.kazocsaba.gamecracker.game.Player;

/**
 * A board state in Reversi.
 * @author Kazó Csaba
 */
class ReversiBoard {
	/*
	 * 2 bits correspond to each cell.
	 *   00: empty
	 *   10: white
	 *   11: black
	 * boardData1 holds the first bit, boardData2 holds the second bit. They encode a 8x8 board, although only a
	 * 4x4 or 6x6 portion might be in use.
	 */
	private long boardData1, boardData2;

	public ReversiBoard() {
	}

	public ReversiBoard(ReversiBoard board) {
		this.boardData1 = board.boardData1;
		this.boardData2 = board.boardData2;
	}
	
	int getTokenCount() {
		return Long.bitCount(boardData1);
	}
	
	public Player getCell(int x, int y) {
		long mask=1L << (x+8*y);
		if ((boardData1 & mask)==0) return null;
		if ((boardData2 & mask)==0) return Player.WHITE;
		return Player.BLACK;
	}
	public void setCell(int x, int y, Player player) {
		long mask=1L << (x+8*y);
		if (player==null) {
			boardData1 &= ~mask;
			boardData2 &= ~mask;
		} else if (player == Player.WHITE) {
			boardData1 |= mask;
			boardData2 &= ~mask;
		} else {
			boardData1 |= mask;
			boardData2 |= mask;
		}
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (int)(this.boardData1 ^ (this.boardData1 >>> 32));
		hash = 31 * hash + (int)(this.boardData2 ^ (this.boardData2 >>> 32));
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ReversiBoard)) return false;
		final ReversiBoard other = (ReversiBoard)obj;
		return boardData1 == other.boardData1 && boardData2 == other.boardData2;
	}
	
	boolean isMoveValid(Player player, int x, int y, int size) {
		if (getCell(x, y)!=null) return false;
		
		// check towards +x
		if (x<size-2 && getCell(x+1, y)==player.getOther()) {
			for (int ix=x+2; ix<size; ix++) {
				Player cellContents=getCell(ix, y);
				if (cellContents==player) return true;
				if (cellContents==null) break;
			}
		}
		// check towards +y
		if (y<size-2 && getCell(x, y+1)==player.getOther()) {
			for (int iy=y+2; iy<size; iy++) {
				Player cellContents=getCell(x, iy);
				if (cellContents==player) return true;
				if (cellContents==null) break;
			}
		}
		// check towards -x
		if (x>1 && getCell(x-1, y)==player.getOther()) {
			for (int ix=x-2; ix>=0; ix--) {
				Player cellContents=getCell(ix, y);
				if (cellContents==player) return true;
				if (cellContents==null) break;
			}
		}
		// check towards -y
		if (y>1 && getCell(x, y-1)==player.getOther()) {
			for (int iy=y-2; iy>=0; iy--) {
				Player cellContents=getCell(x, iy);
				if (cellContents==player) return true;
				if (cellContents==null) break;
			}
		}
		// check towards +x+y
		if (x<size-2 && y<size-2 && getCell(x+1, y+1)==player.getOther()) {
			for (int d=2; x+d<size && y+d<size; d++) {
				Player cellContents=getCell(x+d, y+d);
				if (cellContents==player) return true;
				if (cellContents==null) break;
			}
		}
		// check towards +x-y
		if (x<size-2 && y>1 && getCell(x+1, y-1)==player.getOther()) {
			for (int d=2; x+d<size && y-d>=0; d++) {
				Player cellContents=getCell(x+d, y-d);
				if (cellContents==player) return true;
				if (cellContents==null) break;
			}
		}
		// check towards -x+y
		if (x>1 && y<size-2 && getCell(x-1, y+1)==player.getOther()) {
			for (int d=2; x-d>=0 && y+d<size; d++) {
				Player cellContents=getCell(x-d, y+d);
				if (cellContents==player) return true;
				if (cellContents==null) break;
			}
		}
		// check towards -x-y
		if (x>1 && y>1 && getCell(x-1, y-1)==player.getOther()) {
			for (int d=2; x-d>=0 && y-d>=0; d++) {
				Player cellContents=getCell(x-d, y-d);
				if (cellContents==player) return true;
				if (cellContents==null) break;
			}
		}
		return false;
	}
	
}
