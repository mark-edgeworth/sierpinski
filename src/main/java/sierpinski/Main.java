package sierpinski;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This class demonstrates a Canvas
 */
public class Main {

	/**
	 * Runs the application
	 */
	public void run() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Canvas Example");
		createContents(shell);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	/**
	 * Creates the main window's contents
	 *
	 * @param shell
	 *            the main window
	 */
	private void createContents(Shell shell) {
		shell.setLayout(new FillLayout());

		// Create a canvas
		Canvas canvas = new Canvas(shell, SWT.NONE);

		// Create a button on the canvas
		Button button = new Button(canvas, SWT.PUSH);
		button.setBounds(10, 10, 300, 40);
		button.setText("You can place widgets on a canvas");

		// Create a paint handler for the canvas
		canvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				// Do some drawing
				Rectangle rect = ((Canvas) e.widget).getBounds();
				e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_RED));
				e.gc.drawFocus(5, 5, rect.width - 10, rect.height - 10);
				e.gc.drawText("You can draw text directly on a canvas", 60, 60);
				List<Triangle> tList = makeTriangles(100, 600, 500, 6);
				e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_BLACK));
				for (Triangle t : tList) {
					e.gc.fillPolygon(t.makeTriangle());
				}
			}
		});
	}

	/**
	 * The application entry point
	 *
	 * @param args
	 *            the command line arguments (currently none)
	 */
	public static void main(String[] args) {
		new Main().run();
	}

	List<Triangle> makeTriangles(int x, int y, int sideLength, int depth) {
		List<Triangle> tList = new ArrayList<>();
		Triangle t = new Triangle(x, y, sideLength);
		if (depth <= 1) {
			tList.add(t);
		} else {
			int midX = t.getXMidpoint();
			int midY = t.getYMidpoint();
			int topX = (x + midX) / 2;
			int halfSide = sideLength / 2;
			tList.addAll(makeTriangles(x, y, halfSide, depth - 1));
			tList.addAll(makeTriangles(midX, y, halfSide, depth - 1));
			tList.addAll(makeTriangles(topX, midY, halfSide, depth - 1));
		}

		return tList;
	}
}
