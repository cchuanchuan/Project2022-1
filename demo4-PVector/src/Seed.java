import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import processing.core.PVector;

public class Seed {

	private PVector pos;
	
	public Seed() {
		pos = new PVector((float) (BugPanel.GARDEN_X + Math.random() * BugPanel.GARDEN_W),
				(float) (BugPanel.GARDEN_Y + Math.random() * BugPanel.GARDEN_H));

	}


	public PVector getPos() {
		return pos;
	}

	public void drawSeed(Graphics2D g2) {

		AffineTransform at = g2.getTransform();
		g2.translate(pos.x, pos.y);
		g2.setColor(Color.yellow);
		g2.fillOval(-5, -5, 10, 10);
		g2.setTransform(at);

	}

}
