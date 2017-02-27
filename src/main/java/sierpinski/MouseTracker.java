package sierpinski;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;

public class MouseTracker implements MouseWheelListener, MouseMoveListener, MouseListener {

	private Canvas canvas;
	Point dragStartPoint;
	private float zoom = 1f;
	private int originX;
	private int originY;
	private Point originAtDragStart;

	public MouseTracker(Canvas canvas, int originX, int originY) {
		this.canvas = canvas;
		this.originX = originX;
		this.originY = originY;
	}

	@Override
	public void mouseScrolled(MouseEvent e) {
		if (e.count < 0) {
			zoom *= 0.9f;
		} else if (e.count > 0) {
			zoom *= 1.1f;
		}

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

		System.out.println("Moved: " + e);

		canvas.redraw();
	}

	@Override
	public void mouseUp(MouseEvent e) {
		System.out.println("Mouse-up: " + e);
		dragStartPoint = null;
	}

	@Override
	public void mouseDown(MouseEvent e) {
		System.out.println("Mouse-down: " + e);
		dragStartPoint = new Point(e.x, e.y);
		originAtDragStart = new Point(originX, originY);
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		System.out.println("Mouse-2-click");
	}

	public int getZoom() {
		return (int) zoom;
	}

	public int getOriginX() {
		return originX;
	}

	public int getOriginY() {
		return originY;
	}
}
