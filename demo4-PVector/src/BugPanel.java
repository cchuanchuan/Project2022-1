
/* A Ladybug Panel that draws a ladybug moving in PVector
 * A demo program created for IAT-265 Summer 18
 * Author: Eric Yang
 * Date of creation: May 17 2018
 * All rights reserved
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import processing.core.PVector;

public class BugPanel extends JPanel implements ActionListener {
	private Ladybug bug;
	private Timer timer;
	private int seedGenTimer = 0; // custom timer used to generate a seed after 5 seconds
	public final static int GARDEN_X = 50;
	public final static int GARDEN_Y = 50;
	public final static int GARDEN_W = 500;
	public final static int GARDEN_H = 300;

	private Seed seed = null;

	public BugPanel() {
		setPreferredSize(new Dimension(600, 450));
		timer = new Timer(33, this);
		timer.start();
		double scale = 1.0;
		PVector loc = new PVector(GARDEN_X + 50, GARDEN_Y + GARDEN_H / 2);
		bug = new Ladybug(loc, 45, 40, scale);
		// seed = new Seed();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Call JPanel's method to clear the background
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(new Color(0, 200, 0));
		g2.fillRect(GARDEN_X, GARDEN_Y, GARDEN_W, GARDEN_H);

		bug.drawBug(g2);

		// draw seed
		if (seed != null)
			seed.drawSeed(g2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		bug.move();
		System.out.println(seedGenTimer);
		if (seedGenTimer < 300) // increase only when it's less than 10 seconds
			seedGenTimer++;
		else {
			seedGenTimer = 0;
			seed = new Seed(); // produce a seed when it's 10 seconds since program launches
		}

		if (seed != null && bug.approach(seed.getPos())) { // Only when seed is NOT NULL, it makes sense to approach it
			seed = null; // If bug catches seed eat it by setting it to null so that it will be garbage
							// collected by system
		}

		repaint();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Ladybug in Garden");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BugPanel bugPane = new BugPanel();
		frame.add(bugPane);
		frame.pack();
		frame.setVisible(true);
	}
}
