package hu.kazocsaba.gamecracker.game;

import java.awt.Point;
import java.util.EnumMap;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 *
 * @author Kazó Csaba
 */
public class SquareSymmetryTest {
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
	private final EnumMap<SquareSymmetry, Point> images=new EnumMap<>(SquareSymmetry.class);
	
	@BeforeClass
	public void setupMap() {
		images.put(SquareSymmetry.IDENTITY, square);
		images.put(SquareSymmetry.HORIZONTAL_REFLECTION, new Point(2, 0));
		images.put(SquareSymmetry.VERTICAL_REFLECTION, new Point(1, 3));
		images.put(SquareSymmetry.ROTATION_90, new Point(0, 2));
		images.put(SquareSymmetry.ROTATION_180, new Point(2, 3));
		images.put(SquareSymmetry.ROTATION_270, new Point(3, 1));
		images.put(SquareSymmetry.MAJOR_DIAGONAL_REFLECTION, new Point(0, 1));
		images.put(SquareSymmetry.MINOR_DIAGONAL_REFLECTION, new Point(3, 2));
	}
	private Point transform(Point p, SquareSymmetry t) {
		return new Point(t.transformX(p.x, p.y, size), t.transformY(p.x, p.y, size));
	}
	@Test
	public void testTransformations() {
		for (SquareSymmetry t: SquareSymmetry.values())
			assertEquals(transform(square, t), images.get(t), "Incorrect transformation for "+t);
	}
	@Test
	public void testProperties() {
		for (SquareSymmetry t: SquareSymmetry.values()) {
			assertFalse(t.isPlayerSwitching());
			assertEquals(t.isIdentity(), t==SquareSymmetry.IDENTITY);
		}
	}
	
	@Test
	public void testComposition() {
		for (SquareSymmetry t1: SquareSymmetry.values()) {
			for (SquareSymmetry t2: SquareSymmetry.values()) {
				SquareSymmetry comp=t1.compose(t2);
				assertEquals(transform(square, comp), transform(transform(square, t1), t2), "Incorrect "+t1+".compose("+t2+")");
			}
		}
	}
}
