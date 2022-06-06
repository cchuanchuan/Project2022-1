
/* A Ladybug class that draws and moves in PVector
 * A demo program created for IAT-265 Summer 18
 * Author: Eric Yang
 * Date of creation: May 17 2018
 * All rights reserved
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import processing.core.PVector;

public class Ladybug {
	// Properties of Ladybug object
	private PVector loc;
	private PVector vel;
	private int bodyW;
	private int bodyH;
	private double scale;
	private int dir = 1;
	private double angle;

	// Constructor
	public Ladybug(PVector loc, int w, int h, double sc) {
		this.loc = loc;
		bodyW = w;
		bodyH = h;
		scale = sc;
		vel = new PVector(1, 1);
	}

	public void drawBug(Graphics2D g2) {

		AffineTransform at = g2.getTransform();
//		g2.translate(loc.x, loc.y);
		g2.scale(scale, scale);

		float angle = vel.heading();
		g2.rotate(angle);

		// draw body as an oval in orange
		g2.setColor(new Color(160, 0, 0));

		// make the new (0, 0) be the center of the bug's body
		g2.drawOval(-bodyW / 2, -bodyH / 2, bodyW, bodyH);
		g2.fillOval(-bodyW / 2, -bodyH / 2, bodyW, bodyH);

		// draw body dots and line
		g2.setColor(Color.BLACK);
		g2.fillOval(10, -12, 7, 7);
		g2.fillOval(-10, -12, 7, 7);

		g2.fillOval(10, 7, 7, 7);
		g2.fillOval(-10, 7, 7, 7);
		g2.drawLine(-bodyW / 2, 0, bodyW / 2, 0);

		// draw antenna as two arcs
		g2.drawArc(11, -20, bodyW / 2, bodyH / 2, -90, 90);
		g2.drawArc(11, 0, bodyW / 2, bodyH / 2, 0, 90);

		// draw head as a half pie
		g2.fillArc(bodyW / 2 - 6, -6, 12, 12, -90, 180);
		g2.setTransform(at);

	}

	boolean approach(PVector targ) {
		boolean reach = false;

		// calculate the path to target point
		PVector path = PVector.sub(targ, loc);

		// returns the direction as angle
		float angle = path.heading();

		// make a vel that points toward the target and then move
		vel = PVector.fromAngle(angle);
		vel.mult(2);

		// check if bug reaches target
		if (path.mag()- bodyW / 2 <= 10 ) {
			reach = true;
		}

		return reach;
	}

	public void move() {
		loc.add(vel);
		
		detectWall();
		
		//make it walk randomly
	    angle += 0.04 * dir; 
	    if ( Math.random()*32 < 1) {
	      dir *= -1;
	    }
	    vel.set((float) (1* Math.cos(angle)), (float) (1* Math.sin(angle))); 

		
	}

//
	private void detectWall() {

		// Collision against right edge of garden
		if (loc.x + (bodyW / 2 + bodyW / 4) > (BugPanel.GARDEN_X + BugPanel.GARDEN_W)) {
			loc.x = BugPanel.GARDEN_X + BugPanel.GARDEN_W - (bodyW / 2 + bodyW / 4);
			vel.x = -vel.x;
		}

		// Collision against left edge of garden
		if (loc.x - (bodyW / 2 + bodyW / 4) < BugPanel.GARDEN_X) {
			loc.x = BugPanel.GARDEN_X + (bodyW / 2 + bodyW / 4);
			vel.x = -vel.x;
		}

		// Collision against top or bottom edge of garden
		if (loc.y + (bodyW / 2 + bodyW / 4) > (BugPanel.GARDEN_Y + BugPanel.GARDEN_H)) {
			loc.y = BugPanel.GARDEN_Y + BugPanel.GARDEN_H - (bodyW / 2 + bodyW / 4);
			vel.y = -vel.y;
		}
		if (loc.y - (bodyW / 2 + bodyW / 4) < BugPanel.GARDEN_Y) {
			loc.y = BugPanel.GARDEN_Y + (bodyW / 2 + bodyW / 4);
			vel.y = -vel.y;
		}
	}

}
