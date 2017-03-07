package sierpinski;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

public class MouseTracker implements MouseWheelListener, MouseMoveListener, MouseListener {

	// The side length of the smallest drawn triangle.
	public static final double MIN_SIDE_LENGTH = 20;

	private Canvas canvas;
	Point dragStartPoint;
	private float zoom = 1f;
	private Point origin;
	private Point originAtDragStart;
	private int baseLength;
	private static final float ZOOM_FACTOR_UP = 1.1f;
	private static final float ZOOM_FACTOR_DOWN = 1 / ZOOM_FACTOR_UP;
	private static final int MARGIN = 20; // Bottom and left margin in pixels

	/*
	 * This is the practical limit for zoom-in using the chosen algorithm.
	 * Beyond this point I ran into issues with integer overflow, preceded by
	 * jitter probably caused by the limitation on precision on the zoom
	 * multiplication.
	 */
	private static int MAX_DEPTH = 25;

	private int depth;

	public MouseTracker(Canvas canvas, Rectangle bounds) {
		this.canvas = canvas;
		this.origin = new Point(10, bounds.height - 10);

		// Set initial size based on size of canvas
		this.baseLength = Math.min(bounds.width, (int) (bounds.height / Triangle.HEIGHT_FACTOR)) - MARGIN;
	}

	@Override
	public void mouseScrolled(MouseEvent e) {
		float zoomFactor = 1f;
		int deltaDepth = 0;

		// Calculate the side length of a small triangle at the current zoom
		// level.
		double triangleSideLength = getFullFigureSideLength() / Math.pow(2, depth);

		if (e.count < 0) {
			/*
			 * Zoom out: if the triangle size is big enough then zoom down,
			 * otherwise reduce the depth instead (making the small triangle
			 * size larger by a factor of 2).
			 */
			if (triangleSideLength >= MIN_SIDE_LENGTH) {
				zoomFactor = ZOOM_FACTOR_DOWN;
			} else {
				deltaDepth = -1;
			}

		} else if (e.count > 0 && depth < MAX_DEPTH) {
			/*
			 * Zoom in: make the picture bigger until the small triangle is
			 * large enough to split.
			 */
			if (triangleSideLength < MIN_SIDE_LENGTH) {
				zoomFactor = ZOOM_FACTOR_UP;
			} else {
				deltaDepth = 1;
			}

		} else {
			return; // No change
		}

		zoom *= zoomFactor;

		// Constrain the zoom
		if (zoom < 1) {
			zoom = 1;
			zoomFactor = 1;
			deltaDepth--;
		}

		depth += deltaDepth;

		if (depth < 0) {
			depth = 0;
		}

		origin.x = e.x - (int) ((e.x - origin.x) * zoomFactor);
		origin.y = e.y - (int) ((e.y - origin.y) * zoomFactor);

		canvas.redraw();
	}

	/**
	 * Gets the figure depth. Each step down increases the depth by one,
	 * starting at zero for the single triangle starting point.
	 *
	 * @return positive integer indicating number of splits.
	 */
	public int getDepth() {
		return depth;
	}

	@Override
	public void mouseMove(MouseEvent e) {
		if (dragStartPoint == null) {
			return;
		}

		origin.x = originAtDragStart.x + (e.x - dragStartPoint.x);
		origin.y = originAtDragStart.y + (e.y - dragStartPoint.y);

		canvas.redraw();
	}

	@Override
	public void mouseUp(MouseEvent e) {
		dragStartPoint = null;
	}

	@Override
	public void mouseDown(MouseEvent e) {
		dragStartPoint = new Point(e.x, e.y);
		originAtDragStart = new Point(origin.x, origin.y);
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		System.out.println("Mouse-2-click");
	}

	/**
	 * The zoom level is a value representing how deep into the structure the
	 * user has selected to view.
	 *
	 * @return the current zoom level.
	 */
	public float getZoom() {
		return zoom;
	}

	/**
	 *
	 * @return the coordinates of the origin of the top-level figure
	 */
	public Point getOrigin() {
		return origin;
	}

	/**
	 * @return Gets the size of the top-level figure at the current zoom level.
	 */
	public int getFullFigureSideLength() {
		return (int) (baseLength * zoom);
	}

	/**
	 * @return Gets the visible rectangle
	 */
	public Rectangle getCurrentViewport() {
		return canvas.getBounds();
	}
}
