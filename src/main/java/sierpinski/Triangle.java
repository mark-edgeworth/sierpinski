package sierpinski;

public class Triangle {
	private static final float HEIGHT_FACTOR = 0.866f; // SQRT(3)/2

	private int originX;
	private int originY;
	private int sideLength;

	/**
	 * Constructor
	 *
	 * @param x
	 *            origin X-coordinate
	 * @param y
	 *            origin Y-coordinate
	 * @param sideLength
	 */
	public Triangle(int x, int y, int sideLength) {
		this.originX = x;
		this.originY = y;
		this.sideLength = sideLength;
	}

	/**
	 * Creates a set of points representing a triangle at a specific position
	 * and side length
	 *
	 * @param x
	 *            the x offset of the bottom-left vertex
	 * @param y
	 *            the y offset of the bottom-left vertex
	 * @param sideLength
	 *            The length of a side
	 * @return The set of six points (three sets of (x, y) coordinate pairs)
	 */
	int[] makeTriangle() {
		int[] points = new int[6];
		// Left base
		points[0] = originX;
		points[1] = originY;

		// Right base
		points[2] = originX + (sideLength - 1);
		points[3] = originY;

		// Apex
		points[4] = getXMidpoint();
		points[5] = originY - getHeight();

		// Closure is not required for filled polygons in SWT

		return points;
	}

	public int getXMidpoint() {
		return originX + sideLength / 2;
	}

	public int getHeight() {
		return (int) (HEIGHT_FACTOR * sideLength);
	}

	public int getYMidpoint() {
		return originY - (getHeight() / 2);
	}
}
