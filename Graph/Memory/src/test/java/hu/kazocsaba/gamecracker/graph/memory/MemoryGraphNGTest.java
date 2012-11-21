package hu.kazocsaba.gamecracker.graph.memory;

import hu.kazocsaba.gamecracker.game.reversi.Reversi4;
import hu.kazocsaba.gamecracker.game.tictactoe.TicTacToe;
import hu.kazocsaba.gamecracker.graph.Graph;
import hu.kazocsaba.gamecracker.graph.GraphResult;
import hu.kazocsaba.gamecracker.graph.Node;
import hu.kazocsaba.gamecracker.graph.NormalNode;
import hu.kazocsaba.gamecracker.graph.TransformationNode;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 *
 * @author Kazó Csaba
 */
public class MemoryGraphNGTest {
	@Test(timeOut=5000)
	public void testTicTacToe() {
		MemoryGraph graph=new MemoryGraph(new TicTacToe());
		solve(graph, graph.getRoot());
		
		assertEquals(graph.getRoot().getResult(), GraphResult.DRAW);
	}
	@Test(timeOut=5000)
	public void testReversi() {
		MemoryGraph graph=new MemoryGraph(new Reversi4());
		solve(graph, graph.getRoot());
		
		assertEquals(graph.getRoot().getResult(), GraphResult.BLACK_WINS);
	}
	
	private static void solve(Graph graph, NormalNode node) {
		while (!node.getResult().isKnown()) {
			for (int i=0; i<node.getChildCount(); i++) {
				Node child=node.getChild(i);
				if (child==null) child=graph.expand(node, i);
				if (!child.getResult().isKnown()) {
					if (child instanceof NormalNode)
						solve(graph, (NormalNode)child);
					else
						solve(graph, ((TransformationNode)child).getLinkedNode());
					break;
				}
			}
		}
	}
}
