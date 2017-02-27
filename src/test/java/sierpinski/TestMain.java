package sierpinski;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestMain {

	@Test
	public void test_makeTriangle() {
		Triangle t = new Triangle(0, 0, 10);
		assertEquals(6, t.makeTriangle().length);
	}
}
