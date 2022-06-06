import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestAffineTransform extends JPanel implements ActionListener {
	private double angle = Math.PI/4;
	Thread t = null;
    Image buffImage;
    Timer timer = null;
    
	public TestAffineTransform() {
		setBackground(new Color(150, 150, 150));
		timer = new Timer(10, this);
		timer.start();
	}

	public void paint(Graphics g) {
		drawPattern(g);
	}
	
	public void drawPattern(Graphics g){
		Dimension size = getSize();
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(new Color(150, 150, 150));
		g2.fillRect(0, 0, size.width, size.height);
		
//		AffineTransform transform1 = g2.getTransform();
		g2.translate(size.width / 2, size.height / 2);
		g2.setColor(Color.WHITE);
		g2.fillOval(-25, -25, 50, 50);
//		g2.setTransform(transform1);
	
		

//		AffineTransform transform = g2.getTransform();
		g2.translate(-75, -75);
		g2.rotate(-angle);
		g2.fillRect(-15, -25, 30, 50);
//		g2.setTransform(transform);
		
		g2.translate(75, -75);
		g2.rotate(angle);
		g2.fillRect(-15, -25, 30, 50);

		
//	g2.setTransform(transform1);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
//		angle += 0.04;
		repaint();		
	}
	
	   public static void main(String[] args) {
	        JFrame frame = new JFrame("Affine Test");
	        frame.setSize(400, 400);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        TestAffineTransform tat = new TestAffineTransform();
	        frame.add(tat);
	        frame.setLocationRelativeTo(null);	//set the window to the center of screen
	        frame.setVisible(true);
	    }

}
