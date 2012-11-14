package hu.kazocsaba.gamecracker.game.reversi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import hu.kazocsaba.gamecracker.InconsistencyError;
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
	
	// Package-private functions for serialization

	static int getMaxSerializedSize() {
		return 8*2;
	}

	void write(DataOutput out) throws IOException {
		out.writeLong(boardData1);
		out.writeLong(boardData2);
	}
	
	static ReversiBoard read(int boardSize, DataInput in) throws IOException {
		ReversiBoard board=new ReversiBoard();
		board.boardData1=in.readLong();
		board.boardData2=in.readLong();
		long validMask;
		long centerMask;
		switch (boardSize) {
			case 4:
				validMask=  0b00001111_00001111_00001111_00001111L;
				centerMask= 0b00000000_00000110_00000110_00000000L;
				break;
			case 6:
				validMask=  0b00111111_00111111_00111111_00111111_00111111_00111111L;
				centerMask= 0b00000000_00000000_00001100_00001100_00000000_00000000L;
				break;
			case 8:
				validMask=  0b11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111L;
				centerMask= 0b00000000_00000000_00000000_00011000_00011000_00000000_00000000_00000000L;
				break;
			default:
				throw new AssertionError();
		}
		// check that there are no pieces outside the board
		if ((board.boardData1 & ~validMask)!=0) throw new InconsistencyError("Invalid board (piece out of bounds)");
		// check that there are pieces in the center
		if ((board.boardData1 & centerMask)!=centerMask) throw new InconsistencyError("Invalid board (empty cell in center)");
		return board;
	}
}
