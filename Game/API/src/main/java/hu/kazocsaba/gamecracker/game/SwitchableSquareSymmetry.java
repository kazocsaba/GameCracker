package hu.kazocsaba.gamecracker.game;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.EnumMap;
import hu.kazocsaba.gamecracker.InconsistencyError;

/**
 * A transformation of a square board with a possibility of switching the players.
 * <p>
 * In the description of each transformation, a board of size nxn is assumed, and the notation (i, j) means the point
 * or cell at the intersection of row i and column j.
 * <p>
 * These transformations are analogous to {@link SquareSymmetry}, but each of them
 * has a variant that swaps the two players.
 * 
 * @author Kazó Csaba
 */
public enum SwitchableSquareSymmetry implements SquareBoardTransformation<SwitchableSquareSymmetry> {
	/**
	 * The identity transformation: (i, j) -> (i, j).
	 */
	IDENTITY(SquareSymmetry.IDENTITY) {

		@Override
		public boolean isIdentity() {
			return true;
		}

		@Override
		public SwitchableSquareSymmetry getSwitched() {
			return IDENTITY_SWITCH;
		}

		@Override
		public boolean isPlayerSwitching() {
			return false;
		}

	},
	/**
	 * The identity transformation: (i, j) -> (i, j) with player switching.
	 */
	IDENTITY_SWITCH(SquareSymmetry.IDENTITY) {

		@Override
		public SwitchableSquareSymmetry getSwitched() {
			return IDENTITY;
		}

		@Override
		public boolean isPlayerSwitching() {
			return true;
		}
		
	},
	/**
	 * Reflection in the horizontal direction (along the vertical axis): (i, j) -> (n-i, j).
	 */
	HORIZONTAL_REFLECTION(SquareSymmetry.HORIZONTAL_REFLECTION) {

		@Override
		public SwitchableSquareSymmetry getSwitched() {
			return HORIZONTAL_REFLECTION_SWITCH;
		}

		@Override
		public boolean isPlayerSwitching() {
			return false;
		}
		
	},
	/**
	 * Reflection in the horizontal direction (along the vertical axis): (i, j) -> (n-i, j) with player switching.
	 */
	HORIZONTAL_REFLECTION_SWITCH(SquareSymmetry.HORIZONTAL_REFLECTION) {
		
		@Override
		public SwitchableSquareSymmetry getSwitched() {
			return HORIZONTAL_REFLECTION;
		}

		@Override
		public boolean isPlayerSwitching() {
			return true;
		}
		
	},
	/**
	 * Reflection in the vertical direction (along the horizontal axis): (i, j) -> (i, n-j).
	 */
	VERTICAL_REFLECTION(SquareSymmetry.VERTICAL_REFLECTION) {
		
		@Override
		public SwitchableSquareSymmetry getSwitched() {
			return VERTICAL_REFLECTION_SWITCH;
		}

		@Override
		public boolean isPlayerSwitching() {
			return false;
		}
		
	},
	/**
	 * Reflection in the vertical direction (along the horizontal axis): (i, j) -> (i, n-j) with player switching.
	 */
	VERTICAL_REFLECTION_SWITCH(SquareSymmetry.VERTICAL_REFLECTION) {
		
		@Override
		public SwitchableSquareSymmetry getSwitched() {
			return VERTICAL_REFLECTION;
		}

		@Override
		public boolean isPlayerSwitching() {
			return true;
		}
		
	},
	/**
	 * Rotation by 90°: (i, j) -> (j, n-i).
	 */
	ROTATION_90(SquareSymmetry.ROTATION_90) {
		
		@Override
		public SwitchableSquareSymmetry getSwitched() {
			return ROTATION_90_SWITCH;
		}

		@Override
		public boolean isPlayerSwitching() {
			return false;
		}
		
	},
	/**
	 * Rotation by 90°: (i, j) -> (j, n-i) with player switching.
	 */
	ROTATION_90_SWITCH(SquareSymmetry.ROTATION_90) {
		
		@Override
		public SwitchableSquareSymmetry getSwitched() {
			return ROTATION_90;
		}

		@Override
		public boolean isPlayerSwitching() {
			return true;
		}
		
	},
	/**
	 * Rotation by 180°: (i, j) -> (n-i, n-j).
	 */
	ROTATION_180(SquareSymmetry.ROTATION_180) {
		
		@Override
		public SwitchableSquareSymmetry getSwitched() {
			return ROTATION_180_SWITCH;
		}

		@Override
		public boolean isPlayerSwitching() {
			return false;
		}
		
	},
	/**
	 * Rotation by 180°: (i, j) -> (n-i, n-j) with player switching.
	 */
	ROTATION_180_SWITCH(SquareSymmetry.ROTATION_180) {
		
		@Override
		public SwitchableSquareSymmetry getSwitched() {
			return ROTATION_180;
		}

		@Override
		public boolean isPlayerSwitching() {
			return true;
		}
		
	},
	/**
	 * Rotation by 270°: (i, j) -> (n-j, i).
	 */
	ROTATION_270(SquareSymmetry.ROTATION_270) {
		
		@Override
		public SwitchableSquareSymmetry getSwitched() {
			return ROTATION_270_SWITCH;
		}

		@Override
		public boolean isPlayerSwitching() {
			return false;
		}
		
	},
	/**
	 * Rotation by 270°: (i, j) -> (n-j, i) with player switching.
	 */
	ROTATION_270_SWITCH(SquareSymmetry.ROTATION_270) {
		
		@Override
		public SwitchableSquareSymmetry getSwitched() {
			return ROTATION_270;
		}

		@Override
		public boolean isPlayerSwitching() {
			return true;
		}
		
	},
	/**
	 * Reflection along the major diagonal: (i, j) -> (j, i).
	 */
	MAJOR_DIAGONAL_REFLECTION(SquareSymmetry.MAJOR_DIAGONAL_REFLECTION) {
		
		@Override
		public SwitchableSquareSymmetry getSwitched() {
			return MAJOR_DIAGONAL_REFLECTION_SWITCH;
		}

		@Override
		public boolean isPlayerSwitching() {
			return false;
		}
		
	},
	/**
	 * Reflection along the major diagonal: (i, j) -> (j, i) with player switching.
	 */
	MAJOR_DIAGONAL_REFLECTION_SWITCH(SquareSymmetry.MAJOR_DIAGONAL_REFLECTION) {
		
		@Override
		public SwitchableSquareSymmetry getSwitched() {
			return MAJOR_DIAGONAL_REFLECTION;
		}

		@Override
		public boolean isPlayerSwitching() {
			return true;
		}
		
	},
	/**
	 * Reflection along the minor diagonal: (i, j) -> (n-j, n-i).
	 */
	MINOR_DIAGONAL_REFLECTION(SquareSymmetry.MINOR_DIAGONAL_REFLECTION) {
		
		@Override
		public SwitchableSquareSymmetry getSwitched() {
			return MINOR_DIAGONAL_REFLECTION_SWITCH;
		}

		@Override
		public boolean isPlayerSwitching() {
			return false;
		}
		
	},
	/**
	 * Reflection along the minor diagonal: (i, j) -> (n-j, n-i) with player switching.
	 */
	MINOR_DIAGONAL_REFLECTION_SWITCH(SquareSymmetry.MINOR_DIAGONAL_REFLECTION) {
		
		@Override
		public SwitchableSquareSymmetry getSwitched() {
			return MINOR_DIAGONAL_REFLECTION;
		}

		@Override
		public boolean isPlayerSwitching() {
			return true;
		}
		
	},
	;
	/**
	 * Serializer object for {@code SwitchableSquareSymmetry} objects.
	 */
	public static final TransformationSerializer<SwitchableSquareSymmetry> SERIALIZER=new TransformationSerializer<SwitchableSquareSymmetry>() {
		// Avoid copying the array in values() by using our own instance
		private final SwitchableSquareSymmetry[] VALUES=SwitchableSquareSymmetry.values();
		
		@Override
		public int getTransformationSerializedSize() {
			return 1;
		}

		@Override
		public void writeTransformation(SwitchableSquareSymmetry transformation, DataOutput out) throws IOException {
			out.writeByte(transformation.ordinal());
		}

		@Override
		public SwitchableSquareSymmetry readTransformation(DataInput in) throws IOException {
			int ordinal=in.readByte() & 0xFF;
			if (ordinal<0 || ordinal>=VALUES.length) throw new InconsistencyError("Invalid transformation code: "+ordinal);
			return VALUES[ordinal];
		}
	};
	
	private static final EnumMap<SquareSymmetry, SwitchableSquareSymmetry> MAP_TO_SWITCHABLE;
	static {
		MAP_TO_SWITCHABLE=new EnumMap<>(SquareSymmetry.class);
		/*
		 * This map could be filled up automatically based on the names, but for safety, just
		 * do it manually, so that a rename far in the future won't bite me in the heinie.
		 */
		MAP_TO_SWITCHABLE.put(SquareSymmetry.IDENTITY, IDENTITY);
		MAP_TO_SWITCHABLE.put(SquareSymmetry.HORIZONTAL_REFLECTION, HORIZONTAL_REFLECTION);
		MAP_TO_SWITCHABLE.put(SquareSymmetry.VERTICAL_REFLECTION, VERTICAL_REFLECTION);
		MAP_TO_SWITCHABLE.put(SquareSymmetry.ROTATION_90, ROTATION_90);
		MAP_TO_SWITCHABLE.put(SquareSymmetry.ROTATION_180, ROTATION_180);
		MAP_TO_SWITCHABLE.put(SquareSymmetry.ROTATION_270, ROTATION_270);
		MAP_TO_SWITCHABLE.put(SquareSymmetry.MAJOR_DIAGONAL_REFLECTION, MAJOR_DIAGONAL_REFLECTION);
		MAP_TO_SWITCHABLE.put(SquareSymmetry.MINOR_DIAGONAL_REFLECTION, MINOR_DIAGONAL_REFLECTION);
	}
	
	private final SquareSymmetry baseBoardTransform;

	private SwitchableSquareSymmetry(SquareSymmetry baseBoardTransform) {
		this.baseBoardTransform = baseBoardTransform;
	}
	
	@Override
	public boolean isIdentity() {
		// overridden only in IDENTITY
		return false;
	}

	@Override
	public int transformX(int x, int y, int size) {
		return baseBoardTransform.transformX(x, y, size);
	}

	@Override
	public int transformY(int x, int y, int size) {
		return baseBoardTransform.transformY(x, y, size);
	}
	
	/**
	 * Returns the same transformation but with different player switching. If this transformation
	 * is switching, then the returned one will be not, and vice versa.
	 * 
	 * @return the switching pair of this transformation if it is non-switching, and the non-switching
	 * pair of this transformation if it is switching
	 */
	public abstract SwitchableSquareSymmetry getSwitched();

	@Override
	public SwitchableSquareSymmetry compose(SwitchableSquareSymmetry trans) {
		SwitchableSquareSymmetry result=MAP_TO_SWITCHABLE.get(baseBoardTransform.compose(trans.baseBoardTransform));
		if (isPlayerSwitching() != trans.isPlayerSwitching()) result=result.getSwitched();
		return result;
	}

	@Override
	public SwitchableSquareSymmetry inverse() {
		SwitchableSquareSymmetry result=MAP_TO_SWITCHABLE.get(baseBoardTransform.inverse());
		if (isPlayerSwitching()) result=result.getSwitched();
		return result;
	}
	
}
