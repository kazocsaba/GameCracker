package hu.kazocsaba.gamecracker.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.Point;
import java.util.EnumMap;

/**
 *
 * @author Kazó Csaba
 */
public class SquareSymmetryTest {
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
  private static final EnumMap<SquareSymmetry, Point> images = new EnumMap<>(SquareSymmetry.class);

  @BeforeClass
  public static void setupMap() {
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
    for (SquareSymmetry t : SquareSymmetry.values()) {
      assertEquals("Incorrect transformation for " + t, images.get(t), transform(square, t));
    }
  }

  @Test
  public void testProperties() {
    for (SquareSymmetry t : SquareSymmetry.values()) {
      assertFalse(t.isPlayerSwitching());
      assertEquals(t == SquareSymmetry.IDENTITY, t.isIdentity());
    }
  }

  @Test
  public void testComposition() {
    for (SquareSymmetry t1 : SquareSymmetry.values()) {
      for (SquareSymmetry t2 : SquareSymmetry.values()) {
        SquareSymmetry comp = t1.compose(t2);
        assertEquals(
            "Incorrect " + t1 + ".compose(" + t2 + ")",
            transform(transform(square, t1), t2),
            transform(square, comp));
      }
    }
  }
}
