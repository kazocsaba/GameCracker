package hu.kazocsaba.gamecracker.game;

/**
 * A transformation of a square board. This transformation set does not support player switching.
 * <p>
 * In the description of each transformation, a board of size nxn is assumed, and the notation (i, j) means the point
 * or cell at the intersection of row i and column j.
 * 
 * @author Kazó Csaba
 */
public enum SquareTransformation implements Transformation {

	/**
	 * The identity transformation: (i, j) -> (i, j).
	 */
	IDENTITY {
		@Override
		public boolean isIdentity() {
			return true;
		}

		@Override
		public Transformation compose(Transformation trans) {
			return trans;
		}

		@Override
		public Transformation inverse() {
			return this;
		}

		@Override
		public int transformX(int x, int y, int size) {
			return x;
		}

		@Override
		public int transformY(int x, int y, int size) {
			return y;
		}
		
	},
	/**
	 * Reflection in the horizontal direction (along the vertical axis): (i, j) -> (n-i, j).
	 */
	HORIZONTAL_REFLECTION {

		@Override
		public Transformation inverse() {
			return this;
		}

		@Override
		public Transformation compose(Transformation trans) {
			switch ((SquareTransformation)trans) {
				case IDENTITY: return this;
				case HORIZONTAL_REFLECTION: return IDENTITY;
				case VERTICAL_REFLECTION: return ROTATION_180;
				case ROTATION_180: return VERTICAL_REFLECTION;
				case ROTATION_90: return MAJOR_DIAGONAL_REFLECTION;
				case ROTATION_270: return MINOR_DIAGONAL_REFLECTION;
				case MAJOR_DIAGONAL_REFLECTION: return ROTATION_90;
				case MINOR_DIAGONAL_REFLECTION: return ROTATION_270;
				default: throw new AssertionError(trans);
			}
		}

		@Override
		public int transformX(int x, int y, int size) {
			return size-1-x;
		}

		@Override
		public int transformY(int x, int y, int size) {
			return y;
		}
		
	},
	/**
	 * Reflection in the vertical direction (along the horizontal axis): (i, j) -> (i, n-j).
	 */
	VERTICAL_REFLECTION {

		@Override
		public Transformation inverse() {
			return this;
		}

		@Override
		public Transformation compose(Transformation trans) {
			switch ((SquareTransformation)trans) {
				case IDENTITY: return this;
				case HORIZONTAL_REFLECTION: return ROTATION_180;
				case VERTICAL_REFLECTION: return IDENTITY;
				case ROTATION_180: return HORIZONTAL_REFLECTION;
				case ROTATION_90: return MINOR_DIAGONAL_REFLECTION;
				case ROTATION_270: return MAJOR_DIAGONAL_REFLECTION;
				case MAJOR_DIAGONAL_REFLECTION: return ROTATION_270;
				case MINOR_DIAGONAL_REFLECTION: return ROTATION_90;
				default: throw new AssertionError(trans);
			}
		}

		@Override
		public int transformX(int x, int y, int size) {
			return x;
		}

		@Override
		public int transformY(int x, int y, int size) {
			return size-1-y;
		}
		
	},
	/**
	 * Rotation by 90°: (i, j) -> (j, n-i).
	 */
	ROTATION_90 {

		@Override
		public Transformation inverse() {
			return ROTATION_270;
		}

		@Override
		public Transformation compose(Transformation trans) {
			switch ((SquareTransformation)trans) {
				case IDENTITY: return this;
				case HORIZONTAL_REFLECTION: return MINOR_DIAGONAL_REFLECTION;
				case VERTICAL_REFLECTION: return MAJOR_DIAGONAL_REFLECTION;
				case ROTATION_180: return ROTATION_270;
				case ROTATION_90: return ROTATION_180;
				case ROTATION_270: return IDENTITY;
				case MAJOR_DIAGONAL_REFLECTION: return HORIZONTAL_REFLECTION;
				case MINOR_DIAGONAL_REFLECTION: return VERTICAL_REFLECTION;
				default: throw new AssertionError(trans);
			}
		}

		@Override
		public int transformX(int x, int y, int size) {
			return y;
		}

		@Override
		public int transformY(int x, int y, int size) {
			return size-1-x;
		}
		
	},
	/**
	 * Rotation by 180°: (i, j) -> (n-i, n-j).
	 */
	ROTATION_180 {

		@Override
		public Transformation inverse() {
			return this;
		}

		@Override
		public Transformation compose(Transformation trans) {
			switch ((SquareTransformation)trans) {
				case IDENTITY: return this;
				case HORIZONTAL_REFLECTION: return VERTICAL_REFLECTION;
				case VERTICAL_REFLECTION: return HORIZONTAL_REFLECTION;
				case ROTATION_180: return IDENTITY;
				case ROTATION_90: return ROTATION_270;
				case ROTATION_270: return ROTATION_90;
				case MAJOR_DIAGONAL_REFLECTION: return MINOR_DIAGONAL_REFLECTION;
				case MINOR_DIAGONAL_REFLECTION: return MAJOR_DIAGONAL_REFLECTION;
				default: throw new AssertionError(trans);
			}
		}

		@Override
		public int transformX(int x, int y, int size) {
			return size-1-x;
		}

		@Override
		public int transformY(int x, int y, int size) {
			return size-1-y;
		}
		
	},
	/**
	 * Rotation by 90°: (i, j) -> (n-j, i).
	 */
	ROTATION_270 {

		@Override
		public Transformation inverse() {
			return ROTATION_90;
		}

		@Override
		public Transformation compose(Transformation trans) {
			switch ((SquareTransformation)trans) {
				case IDENTITY: return this;
				case HORIZONTAL_REFLECTION: return MAJOR_DIAGONAL_REFLECTION;
				case VERTICAL_REFLECTION: return MINOR_DIAGONAL_REFLECTION;
				case ROTATION_180: return ROTATION_90;
				case ROTATION_90: return IDENTITY;
				case ROTATION_270: return ROTATION_180;
				case MAJOR_DIAGONAL_REFLECTION: return VERTICAL_REFLECTION;
				case MINOR_DIAGONAL_REFLECTION: return HORIZONTAL_REFLECTION;
				default: throw new AssertionError(trans);
			}
		}

		@Override
		public int transformX(int x, int y, int size) {
			return size-1-y;
		}

		@Override
		public int transformY(int x, int y, int size) {
			return x;
		}
		
	},
	/**
	 * Reflection along the major diagonal: (i, j) -> (j, i).
	 */
	MAJOR_DIAGONAL_REFLECTION {

		@Override
		public Transformation inverse() {
			return this;
		}

		@Override
		public Transformation compose(Transformation trans) {
			switch ((SquareTransformation)trans) {
				case IDENTITY: return this;
				case HORIZONTAL_REFLECTION: return ROTATION_270;
				case VERTICAL_REFLECTION: return ROTATION_90;
				case ROTATION_180: return MINOR_DIAGONAL_REFLECTION;
				case ROTATION_90: return VERTICAL_REFLECTION;
				case ROTATION_270: return HORIZONTAL_REFLECTION;
				case MAJOR_DIAGONAL_REFLECTION: return IDENTITY;
				case MINOR_DIAGONAL_REFLECTION: return ROTATION_180;
				default: throw new AssertionError(trans);
			}
		}

		@Override
		public int transformX(int x, int y, int size) {
			return y;
		}

		@Override
		public int transformY(int x, int y, int size) {
			return x;
		}
		
	},
	/**
	 * Reflection along the minor diagonal: (i, j) -> (n-j, n-i).
	 */
	MINOR_DIAGONAL_REFLECTION {

		@Override
		public Transformation inverse() {
			return this;
		}

		@Override
		public Transformation compose(Transformation trans) {
			switch ((SquareTransformation)trans) {
				case IDENTITY: return this;
				case HORIZONTAL_REFLECTION: return ROTATION_90;
				case VERTICAL_REFLECTION: return ROTATION_270;
				case ROTATION_180: return MAJOR_DIAGONAL_REFLECTION;
				case ROTATION_90: return HORIZONTAL_REFLECTION;
				case ROTATION_270: return VERTICAL_REFLECTION;
				case MAJOR_DIAGONAL_REFLECTION: return ROTATION_180;
				case MINOR_DIAGONAL_REFLECTION: return IDENTITY;
				default: throw new AssertionError(trans);
			}
		}

		@Override
		public int transformX(int x, int y, int size) {
			return size-1-y;
		}

		@Override
		public int transformY(int x, int y, int size) {
			return size-1-x;
		}
		
	}
	;

	@Override
	public boolean isPlayerSwitching() {
		return false;
	}

	@Override
	public boolean isIdentity() {
		// overridden only in IDENTITY
		return false;
	}
	
	/**
	 * Transforms a point and returns the x coordinate of the result. A {@code (size x size)} board
	 * is assumed where the coordinates are in the range {@code [0, size-1]}.
	 * 
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 * @param size the size of the board
	 * @return the x coordinate of the result of the transformation
	 */
	public abstract int transformX(int x, int y, int size);
	
	/**
	 * Transforms a point and returns the y coordinate of the result. A {@code (size x size)} board
	 * is assumed where the coordinates are in the range {@code [0, size-1]}.
	 * 
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 * @param size the size of the board
	 * @return the x coordinate of the result of the transformation
	 */
	public abstract int transformY(int x, int y, int size);
}
