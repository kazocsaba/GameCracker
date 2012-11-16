package hu.kazocsaba.gamecracker.game.tictactoe;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;
import javax.swing.JComponent;
import hu.kazocsaba.gamecracker.game.GameComponent;
import hu.kazocsaba.gamecracker.game.Player;
import hu.kazocsaba.gamecracker.game.SquareSymmetry;

/**
 * A Tic Tac Toe component.
 * 
 * @author Kazó Csaba
 */
class TicTacToeComponent extends GameComponent<TicTacToePosition, TicTacToeMove, SquareSymmetry> {
	private TicTacToePosition position;
	private MoveReceiver<TicTacToeMove, SquareSymmetry> moveReceiver;
	
	private JComponent comp=new JComponent() {
		{
			setDoubleBuffered(true);
			setPreferredSize(new Dimension(90, 90));
		}

		// no children
		@Override
		protected void paintChildren(Graphics g) {
		}

		@Override
		protected void paintComponent(Graphics g) {
			g=g.create();
			try {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				
				int size=Math.min(getWidth(), getHeight());
				g.translate((getWidth()-size)/2, (getHeight()-size)/2);
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, size, size);
				
				for (int x=0; x<3; x++) for (int y=0; y<3; y++) {
					int left=x*(size-1)/3;
					int top=(2-y)*(size-1)/3;
					int right=(x+1)*(size-1)/3;
					int bottom=((2-y)+1)*(size-1)/3;
					
					int cellSize=right-left;
					
					g.setColor(Color.BLACK);
					g.drawRect(left, top, right-left, bottom-top);
					
					Player cellContents=position.getCell(x, y);
					if (cellContents==Player.WHITE) {
						g.drawOval(left + cellSize/7, top+cellSize/7, 5*cellSize/7+1, 5*cellSize/7+1);
					} else if (cellContents==Player.BLACK) {
						g.drawLine(left + cellSize/7, top + cellSize/7, right - cellSize/7, bottom - cellSize/7);
						g.drawLine(left + cellSize/7, bottom - cellSize/7, right - cellSize/7, top + cellSize/7);
					}
				}
			} finally {
				g.dispose();
			}
		}
		
	};
	private MouseListener moveListener=new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
			int size=Math.min(comp.getWidth(), comp.getHeight());
			int x=e.getX()-(comp.getWidth()-size)/2;
			int y=e.getY()-(comp.getHeight()-size)/2;
			x=x/(size/3);
			y=2-y/(size/3);
			if (moveReceiver!=null && x>=0 && y>=0 && x<3 && y<3)
				moveReceiver.onMove(TicTacToeMove.get(x, y));
		}
		
	};

	TicTacToeComponent(TicTacToePosition position) {
		this.position = position;
	}
	
	
	@Override
	public void setPosition(TicTacToePosition position) {
		this.position=Objects.requireNonNull(position);
		comp.repaint();
	}

	@Override
	public TicTacToePosition getPosition() {
		return position;
	}

	@Override
	public Component getComponent() {
		return comp;
	}

	@Override
	public void setMoveReceiver(MoveReceiver<TicTacToeMove, SquareSymmetry> receiver) {
		if (this.moveReceiver==receiver) return;
		
		if (receiver==null)
			comp.removeMouseListener(moveListener);
		else if (this.moveReceiver==null)
			comp.addMouseListener(moveListener);
		
		this.moveReceiver=receiver;
	}

}
