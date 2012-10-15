package hu.kazocsaba.gamecracker.game;

import java.awt.Point;
import java.util.EnumMap;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Kazó Csaba
 */
public class SquareTransformationTest {
	/*
	 *    -----------------
	 *    |   |   |   |   |
	 * 3  |   | 3 | 5 |   |
	 *    |   |   |   |   |
	 *    -----------------
	 *    |   |   |   |   |
	 * 2  | 4 |   |   | 8 |
	 *    |   |   |   |   |
	 *    -----------------
	 *    |   |   |   |   |
	 * 1  | 7 |   |   | 6 |
	 *    |   |   |   |   |
	 *    -----------------
	 *    |   |   |   |   |
	 * 0  |   | 1 | 2 |   |
	 *    |   |   |   |   |
	 *    -----------------
	 * 
	 *      0   1   2   3
	 * 
	 * 1: IDENTITY
	 * 2: HORIZONTAL_REFLECTION
	 * 3: VERTICAL_REFLECTION
	 * 4: ROTATION_90
	 * 5: ROTATION_180
	 * 6: ROTATION_270
	 * 7: MAJOR_DIAGONAL_REFLECTION
	 * 8: MINOR_DIAGONAL_REFLECTION
	 */
	private final Point square=new Point(1, 0);
	private final int size=4;
	private final EnumMap<SquareTransformation, Point> images=new EnumMap<>(SquareTransformation.class);
	
	@BeforeClass
	public void setupMap() {
		images.put(SquareTransformation.IDENTITY, square);
		images.put(SquareTransformation.HORIZONTAL_REFLECTION, new Point(2, 0));
		images.put(SquareTransformation.VERTICAL_REFLECTION, new Point(1, 3));
		images.put(SquareTransformation.ROTATION_90, new Point(0, 2));
		images.put(SquareTransformation.ROTATION_180, new Point(2, 3));
		images.put(SquareTransformation.ROTATION_270, new Point(3, 1));
		images.put(SquareTransformation.MAJOR_DIAGONAL_REFLECTION, new Point(0, 1));
		images.put(SquareTransformation.MINOR_DIAGONAL_REFLECTION, new Point(3, 2));
	}
	private Point transform(Point p, SquareTransformation t) {
		return new Point(t.transformX(p.x, p.y, size), t.transformY(p.x, p.y, size));
	}
	@Test
	public void testTransformations() {
		for (SquareTransformation t: SquareTransformation.values())
			assertEquals(transform(square, t), images.get(t), "Incorrect transformation for "+t);
	}
	@Test
	public void testProperties() {
		for (SquareTransformation t: SquareTransformation.values()) {
			assertFalse(t.isPlayerSwitching());
			assertEquals(t.isIdentity(), square.equals(transform(square, t)));
		}
	}
	
	@Test
	public void testComposition() {
		for (SquareTransformation t1: SquareTransformation.values()) {
			for (SquareTransformation t2: SquareTransformation.values()) {
				SquareTransformation comp=(SquareTransformation) t1.compose(t2);
				assertEquals(transform(square, comp), transform(transform(square, t1), t2), "Incorrect "+t1+".compose("+t2+")");
			}
		}
	}
}
