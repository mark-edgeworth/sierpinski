package sierpinski;

import static org.junit.Assert.assertEquals;

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
}
