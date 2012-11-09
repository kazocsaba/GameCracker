package hu.kazocsaba.gamecracker.game;

import java.awt.Point;
import java.util.EnumMap;
import java.util.EnumSet;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 *
 * @author Kazó Csaba
 */
public class SwitchableSquareSymmetryTest {
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
	private final EnumMap<SwitchableSquareSymmetry, Point> images=new EnumMap<>(SwitchableSquareSymmetry.class);
	private final EnumSet<SwitchableSquareSymmetry> switchers=EnumSet.of(
			SwitchableSquareSymmetry.IDENTITY_SWITCH,
			SwitchableSquareSymmetry.HORIZONTAL_REFLECTION_SWITCH,
			SwitchableSquareSymmetry.VERTICAL_REFLECTION_SWITCH,
			SwitchableSquareSymmetry.ROTATION_90_SWITCH,
			SwitchableSquareSymmetry.ROTATION_180_SWITCH,
			SwitchableSquareSymmetry.ROTATION_270_SWITCH,
			SwitchableSquareSymmetry.MAJOR_DIAGONAL_REFLECTION_SWITCH,
			SwitchableSquareSymmetry.MINOR_DIAGONAL_REFLECTION_SWITCH
	);
	
	@BeforeClass
	public void setupMap() {
		images.put(SwitchableSquareSymmetry.IDENTITY, square);
		images.put(SwitchableSquareSymmetry.HORIZONTAL_REFLECTION, new Point(2, 0));
		images.put(SwitchableSquareSymmetry.VERTICAL_REFLECTION, new Point(1, 3));
		images.put(SwitchableSquareSymmetry.ROTATION_90, new Point(0, 2));
		images.put(SwitchableSquareSymmetry.ROTATION_180, new Point(2, 3));
		images.put(SwitchableSquareSymmetry.ROTATION_270, new Point(3, 1));
		images.put(SwitchableSquareSymmetry.MAJOR_DIAGONAL_REFLECTION, new Point(0, 1));
		images.put(SwitchableSquareSymmetry.MINOR_DIAGONAL_REFLECTION, new Point(3, 2));
		images.put(SwitchableSquareSymmetry.IDENTITY_SWITCH, square);
		images.put(SwitchableSquareSymmetry.HORIZONTAL_REFLECTION_SWITCH, new Point(2, 0));
		images.put(SwitchableSquareSymmetry.VERTICAL_REFLECTION_SWITCH, new Point(1, 3));
		images.put(SwitchableSquareSymmetry.ROTATION_90_SWITCH, new Point(0, 2));
		images.put(SwitchableSquareSymmetry.ROTATION_180_SWITCH, new Point(2, 3));
		images.put(SwitchableSquareSymmetry.ROTATION_270_SWITCH, new Point(3, 1));
		images.put(SwitchableSquareSymmetry.MAJOR_DIAGONAL_REFLECTION_SWITCH, new Point(0, 1));
		images.put(SwitchableSquareSymmetry.MINOR_DIAGONAL_REFLECTION_SWITCH, new Point(3, 2));
	}
	private Point transform(Point p, SwitchableSquareSymmetry t) {
		return new Point(t.transformX(p.x, p.y, size), t.transformY(p.x, p.y, size));
	}
	@Test
	public void testTransformations() {
		for (SwitchableSquareSymmetry t: SwitchableSquareSymmetry.values())
			assertEquals(transform(square, t), images.get(t), "Incorrect transformation for "+t);
	}
	@Test
	public void testProperties() {
		for (SwitchableSquareSymmetry t: SwitchableSquareSymmetry.values()) {
			assertEquals(t.isPlayerSwitching(), switchers.contains(t));
			assertEquals(t.isIdentity(), t==SwitchableSquareSymmetry.IDENTITY);
		}
	}
	
	@Test
	public void testComposition() {
		for (SwitchableSquareSymmetry t1: SwitchableSquareSymmetry.values()) {
			for (SwitchableSquareSymmetry t2: SwitchableSquareSymmetry.values()) {
				SwitchableSquareSymmetry comp=t1.compose(t2);
				assertNotNull(comp);
				assertEquals(transform(square, comp), transform(transform(square, t1), t2), "Incorrect "+t1+".compose("+t2+")");
				assertEquals(comp.isPlayerSwitching(), t1.isPlayerSwitching()!=t2.isPlayerSwitching());
			}
		}
	}
}
