package sierpinski;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
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

	/**
	 * Creates a set of points representing a triangle at a specific position
	 * and side length
	 *
	 * @param origin
	 *            The position of the lower left vertex
	 * @param side
	 *            The length of a side
	 * @return The set of six points (three sets of (x, y) coordinate pairs)
	 */
	private int[] makeTriangle(Point origin, int side) {
		return new int[6];
	}
}