package hu.kazocsaba.gamecracker.game.testing;

import java.util.Iterator;
import java.util.Objects;
import java.util.ServiceLoader;
import hu.kazocsaba.gamecracker.game.Game;
import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * A base class for game tests.
 * @author Kazó Csaba
 */
public abstract class AbstractGameTest<G extends Game<P,M,T>, P extends Position<P,M,T>, M extends Move<M,T>, T extends Transformation<T>> {
	protected final G game;

	/**
	 * Creates a new instance testing the specified game.
	 * @param game the game
	 */
	public AbstractGameTest(G game) {
		this.game = Objects.requireNonNull(game);
	}
	
	@Test
	public void testServiceLocator() {
		ServiceLoader<Game> loader=ServiceLoader.load(Game.class);
		boolean found=false;
		for (Iterator<Game> it = loader.iterator(); it.hasNext();) {
			Game g = it.next();
			if (g.getClass()==game.getClass()) {
				found=true;
				break;
			}
		}
		assertTrue(found, "Game "+game.getClass()+" not loadable via ServiceLoader");
	}
	/**
	 * Asserts that two positions are equal. This function performs the following checks:
	 * <ul>
	 * <li>equality of the positions themselves</li>
	 * <li>their hash codes</li>
	 * <li>their categories</li>
	 * <li>each report an identity transformation to the other</li>
	 * </ul>
	 */
	protected void assertPositionsEqual(P p1, P p2) {
		assertEquals(p1, p2);
		assertEquals(p2, p1);
		T transformation=p1.getTransformationTo(p2);
		assertNotNull(transformation);
		assertTrue(transformation.isIdentity());
		transformation=p2.getTransformationTo(p1);
		assertNotNull(transformation);
		assertTrue(transformation.isIdentity());
		assertEquals(p1.hashCode(), p2.hashCode());
		assertEquals(game.getCategoryFunction().category(p1), game.getCategoryFunction().category(p2));
	}
	/**
	 * Asserts that two positions are equal through a transformation. This function performs the following checks:
	 * <ul>
	 * <li>the categories of the positions are the same</li>
	 * <li>applying the transformation to the first results in a position equal to the second
	 * (using {@link #assertPositionsEqual(Position, Position)})</li>
	 * <li>applying the inverse of the transformation to the second results in a position equal to the first
	 * (using {@link #assertPositionsEqual(Position, Position)})</li>
	 * </ul>
	 */
	protected void assertPositionsTransformed(P p1, T t, P p2) {
		assertEquals(game.getCategoryFunction().category(p1), game.getCategoryFunction().category(p2));
		assertPositionsEqual(p1.transform(t), p2);
		assertPositionsEqual(p2.transform(t.inverse()), p1);
	}
}
