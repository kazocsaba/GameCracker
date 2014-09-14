package hu.kazocsaba.gamecracker.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.Point;
import java.util.EnumMap;
import java.util.EnumSet;

/**
 *
 * @author Kazó Csaba
 */
public class SwitchableSquareSymmetryTest {
	//
  //    -----------------
  //    |   |   |   |   |
  // 3  |   | 3 | 5 |   |
  //    |   |   |   |   |
  //    -----------------
  //    |   |   |   |   |
  // 2  | 4 |   |   | 8 |
  //    |   |   |   |   |
  //    -----------------
  //    |   |   |   |   |
  // 1  | 7 |   |   | 6 |
  //    |   |   |   |   |
  //    -----------------
  //    |   |   |   |   |
  // 0  |   | 1 | 2 |   |
  //    |   |   |   |   |
  //    -----------------
  // 
  //      0   1   2   3
  // 
  // 1: IDENTITY
  // 2: HORIZONTAL_REFLECTION
  // 3: VERTICAL_REFLECTION
  // 4: ROTATION_90
  // 5: ROTATION_180
  // 6: ROTATION_270
  // 7: MAJOR_DIAGONAL_REFLECTION
  // 8: MINOR_DIAGONAL_REFLECTION
  //

  private static final Point square = new Point(1, 0);
  private static final int size = 4;
  private static final EnumMap<SwitchableSquareSymmetry, Point> images =
      new EnumMap<>(SwitchableSquareSymmetry.class);
  private static final EnumSet<SwitchableSquareSymmetry> switchers = EnumSet.of(
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
  public static void setupMap() {
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
    for (SwitchableSquareSymmetry t : SwitchableSquareSymmetry.values()) {
      assertEquals("Incorrect transformation for " + t, images.get(t), transform(square, t));
    }
  }

  @Test
  public void testProperties() {
    for (SwitchableSquareSymmetry t : SwitchableSquareSymmetry.values()) {
      assertEquals(switchers.contains(t), t.isPlayerSwitching());
      assertEquals(t == SwitchableSquareSymmetry.IDENTITY, t.isIdentity());
    }
  }

  @Test
  public void testComposition() {
    for (SwitchableSquareSymmetry t1 : SwitchableSquareSymmetry.values()) {
      for (SwitchableSquareSymmetry t2 : SwitchableSquareSymmetry.values()) {
        SwitchableSquareSymmetry comp = t1.compose(t2);
        assertNotNull(comp);
        assertEquals(
            "Incorrect " + t1 + ".compose(" + t2 + ")",
            transform(transform(square, t1), t2),
            transform(square, comp));
        assertEquals(t1.isPlayerSwitching() != t2.isPlayerSwitching(), comp.isPlayerSwitching());
      }
    }
  }
}
