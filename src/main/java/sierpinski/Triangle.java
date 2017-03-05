package sierpinski;

import org.eclipse.swt.graphics.Rectangle;

/**
 * Class representing a single triangular shape which may or may not be drawn.
 *
 * @author mse
 *
 */
public class Triangle {
	public static final float HEIGHT_FACTOR = 0.866f; // SQRT(3)/2

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
	 *            the length of any side of the equilateral triangle
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

	/**
	 *
	 * @return the X-coordinate of the midpoint of the triangle base
	 */
	public int getXMidpoint() {
		return originX + sideLength / 2;
	}

	/**
	 *
	 * @return the height of the triangle, in pixels
	 */
	public int getHeight() {
		return (int) (HEIGHT_FACTOR * sideLength);
	}

	/**
	 *
	 * @return the Y-coordinate of the midpoint between base and apex
	 */
	public int getYMidpoint() {
		return originY - (getHeight() / 2);
	}

	/**
	 * Determines whether this triangle has any visible part within the supplied
	 * bounds. This is an optimisation step, allowing culling of invisible
	 * elements to improve performance.
	 *
	 * @param viewport
	 *            The bounds of the visible area
	 * @return true if any part is visible, false if not.
	 */
	public boolean isVisible(Rectangle viewport) {
		// Triangle all to the left of viewport left edge
		if (originX + (sideLength - 1) < viewport.x) {
			return false;
		}

		// Triangle all above viewport top edge
		if (originY < viewport.y) {
			return false;
		}

		// Triangle all to the right of viewport right edge
		if (originX > viewport.x + viewport.width) {
			return false;
		}

		// Triangle all below of viewport bottom edge
		if (originY - getHeight() > viewport.y + viewport.height) {
			return false;
		}

		return true;
	}
}
