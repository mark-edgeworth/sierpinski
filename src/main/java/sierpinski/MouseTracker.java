package sierpinski;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

public class MouseTracker implements MouseWheelListener, MouseMoveListener, MouseListener {

	private Canvas canvas;
	Point dragStartPoint;
	private float zoom = 1f;
	private int originX;
	private int originY;
	private Point originAtDragStart;
	private int baseLength;
	private static final float ZOOM_FACTOR_UP = 1.1f;
	private static final float ZOOM_FACTOR_DOWN = 1 / ZOOM_FACTOR_UP;

	public MouseTracker(Canvas canvas, Rectangle bounds) {
		this.canvas = canvas;
		this.originX = 10;
		this.originY = bounds.height - 10;

		// Set initial size based on size of canvas
		this.baseLength = Math.min(bounds.width, (int) (bounds.height * Triangle.HEIGHT_FACTOR)) - 20;
	}

	@Override
	public void mouseScrolled(MouseEvent e) {
		float zoomFactor;
		if (e.count < 0) {
			zoomFactor = ZOOM_FACTOR_DOWN;
		} else if (e.count > 0) {
			zoomFactor = ZOOM_FACTOR_UP;
		} else {
			return; // No change
		}

		originX = e.x - (int) ((e.x - originX) * zoomFactor);
		originY = e.y - (int) ((e.y - originY) * zoomFactor);

		zoom *= zoomFactor;

		// Constrain the zoom
		if (zoom < 1) {
			zoom = 1;
		}
		canvas.redraw();
	}

	@Override
	public void mouseMove(MouseEvent e) {
		if (dragStartPoint == null) {
			return;
		}

		originX = originAtDragStart.x + (e.x - dragStartPoint.x);
		originY = originAtDragStart.y + (e.y - dragStartPoint.y);

		canvas.redraw();
	}

	@Override
	public void mouseUp(MouseEvent e) {
		dragStartPoint = null;
	}

	@Override
	public void mouseDown(MouseEvent e) {
		dragStartPoint = new Point(e.x, e.y);
		originAtDragStart = new Point(originX, originY);
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
	public int getZoom() {
		return (int) zoom;
	}

	public int getOriginX() {
		return originX;
	}

	public int getOriginY() {
		return originY;
	}

	public int getBaseLength() {
		return (int) (baseLength * zoom);
	}
}
