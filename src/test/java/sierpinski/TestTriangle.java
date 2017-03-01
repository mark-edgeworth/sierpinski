package sierpinski;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.graphics.Rectangle;
import org.junit.Test;

public class TestTriangle {

	/**
	 * Tests that correct number of points are returned
	 */
	@Test
	public void test_makeTriangle() {
		Triangle t = new Triangle(0, 0, 10);
		assertEquals(6, t.makeTriangle().length);
	}

	@Test
	public void test_getMidpointX() {
		assertEquals(20, new Triangle(0, 0, 40).getXMidpoint());
		assertEquals(25, new Triangle(0, 0, 50).getXMidpoint());

		// Rounds down
		assertEquals(0, new Triangle(0, 0, 1).getXMidpoint());

		// Handles zero
		assertEquals(0, new Triangle(0, 0, 0).getXMidpoint());

		// Sensitive to X offset
		assertEquals(35, new Triangle(15, 0, 40).getXMidpoint());

		// Insensitive to Y offset
		assertEquals(20, new Triangle(0, 1000, 40).getXMidpoint());
	}

	@Test
	public void test_getMidpointY() {
		assertEquals(457, new Triangle(0, 500, 100).getYMidpoint());
		assertEquals(414, new Triangle(0, 500, 200).getYMidpoint());

		// Rounds down
		assertEquals(500, new Triangle(0, 500, 1).getYMidpoint());

		// Handles zero
		assertEquals(500, new Triangle(0, 500, 0).getYMidpoint());

		// Insensitive to X offset
		assertEquals(457, new Triangle(1000, 500, 100).getYMidpoint());

		// Sensitive to Y offset
		assertEquals(257, new Triangle(0, 300, 100).getYMidpoint());
	}

	@Test
	public void test_isVisible() {
		// L=(20, 25), R=(30, 25), Apex=(25, 17)
		Triangle t = new Triangle(20, 25, 10);

		assertEquals(8, t.getHeight());

		// Triangle fully in view
		Rectangle viewport = new Rectangle(0, 0, 100, 100);
		assertTrue(t.isVisible(viewport));

		// Off screen to the left
		viewport = new Rectangle(30, 0, 100, 100);
		assertFalse(t.isVisible(viewport));

		// Off screen to the right
		viewport = new Rectangle(0, 0, 19, 100);
		assertFalse(t.isVisible(viewport));

		// Off screen above
		viewport = new Rectangle(0, 36, 100, 100);
		assertFalse(t.isVisible(viewport));

		// Off screen below
		viewport = new Rectangle(0, 0, 100, 16);
		assertFalse(t.isVisible(viewport));
	}

	/**
	 * Tests for partial visibility (part of triangle is in viewport, part not.
	 */
	@Test
	public void test_isVisible2() {
		// L=(20, 25), R=(30, 25), Apex=(25, 12)
		Triangle t = new Triangle(20, 25, 10);

		// Mostly off screen to the left
		Rectangle viewport = new Rectangle(29, 0, 100, 100);
		assertTrue(t.isVisible(viewport));

		// Mostly off screen to the right
		viewport = new Rectangle(0, 0, 20, 100);
		assertTrue(t.isVisible(viewport));

		// Mostly off screen above
		viewport = new Rectangle(0, 25, 100, 100);
		assertTrue(t.isVisible(viewport));

		// Mostly off screen below
		viewport = new Rectangle(0, 0, 100, 17);
		assertTrue(t.isVisible(viewport));
	}
}
