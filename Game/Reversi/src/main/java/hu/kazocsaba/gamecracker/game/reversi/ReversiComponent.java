package hu.kazocsaba.gamecracker.game.reversi;

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
import hu.kazocsaba.gamecracker.game.SwitchableSquareSymmetry;

/**
 *
 * @author Kazó Csaba
 */
abstract class ReversiComponent<P extends ReversiPosition<P,M>, M extends ReversiMove<M>> extends GameComponent<P,M,SwitchableSquareSymmetry> {
	private final int boardSize;
	private P position;
	private MoveReceiver<M, SwitchableSquareSymmetry> moveReceiver;
	
	private JComponent comp;
	private MouseListener moveListener=new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
			int size=Math.min(comp.getWidth(), comp.getHeight());
			int x=e.getX()-(comp.getWidth()-size)/2;
			int y=e.getY()-(comp.getHeight()-size)/2;
			x=x/(size/boardSize);
			y=boardSize-1-y/(size/boardSize);
			if (moveReceiver!=null && x>=0 && y>=0 && x<boardSize && y<boardSize)
				moveReceiver.onMove(getMove(x, y));
		}
		
	};

	ReversiComponent(P pos, int size) {
		this.position = pos;
		this.boardSize = size;
		comp=new JComponent() {
			{
				setDoubleBuffered(true);
				setPreferredSize(new Dimension(30*boardSize, 30*boardSize));
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
					g.setColor(Color.BLACK);

					for (int x=0; x<boardSize; x++) for (int y=0; y<boardSize; y++) {
						int left=x*(size-1)/boardSize;
						int top=(boardSize-1-y)*(size-1)/boardSize;
						int right=(x+1)*(size-1)/boardSize;
						int bottom=((boardSize-1-y)+1)*(size-1)/boardSize;

						int cellSize=right-left;

						g.drawRect(left, top, right-left, bottom-top);

						Player cellContents=position.getCell(x, y);
						if (cellContents==Player.WHITE) {
							g.drawOval(left + cellSize/7, top+cellSize/7, 5*cellSize/7+1, 5*cellSize/7+1);
						} else if (cellContents==Player.BLACK) {
							g.fillOval(left + cellSize/7, top+cellSize/7, 5*cellSize/7+1, 5*cellSize/7+1);
						}
					}
				} finally {
					g.dispose();
				}
			}

		};
	}
	
	abstract M getMove(int x, int y);
	
	@Override
	public void setPosition(P position) {
		this.position=Objects.requireNonNull(position);
		comp.repaint();
	}

	@Override
	public P getPosition() {
		return position;
	}

	@Override
	public Component getComponent() {
		return comp;
	}

	@Override
	public void setMoveReceiver(MoveReceiver<M, SwitchableSquareSymmetry> receiver) {
		if (this.moveReceiver==receiver) return;
		
		if (receiver==null)
			comp.removeMouseListener(moveListener);
		else if (this.moveReceiver==null)
			comp.addMouseListener(moveListener);
		
		this.moveReceiver=receiver;
	}

}
