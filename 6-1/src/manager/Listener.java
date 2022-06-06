package manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * ActionListener Class
 * @author Xi WANG
 *
 */
public class Listener implements ActionListener, MouseListener, MouseMotionListener{
	
	Graphics2D graphics2D;
	JFrame jFrame;
	ManagerUI managerUI;

	int x1, y1,x2, y2;
	int thick = 1;

	Object command = "line";
	String rgb = "0 0 0";
	static Color color = Color.black;
	String record;
	ArrayList<String> records = new ArrayList<>();

	public Listener() {

	}


	public Listener(JFrame jFrame) {
		this.jFrame = jFrame;
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Invoked when a mouse button has been pressed on a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		if (!graphics2D.getColor().equals(color)) {
			graphics2D.setColor(color);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		x2 = e.getX();
		y2 = e.getY();
		rgb = getColor(color);

		//drawLine
		if ("line".equals(command)) {
			graphics2D.setStroke(new BasicStroke(thick));
			graphics2D.drawLine(x1, y1, x2, y2);
			record = "line " + this.thick + " " + rgb + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " @";
			records.add(record);

		} else if ("circle".equals(command)) {
			graphics2D.setStroke(new BasicStroke(thick));
			int diameter = Math.min(Math.abs(x1 - x2), Math.abs(y1 - y2));
			graphics2D.drawOval(Math.min(x1, x2), Math.min(y1, y2), diameter, diameter);
			record = "circle " + this.thick + " " + rgb + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " @";
			records.add(record);
		} else if ("triangle".equals(command)) {
			graphics2D.setStroke(new BasicStroke(thick));

			double x3, y3;
			int length1 = Math.abs(x1 - x2);
			int length2 = Math.abs(y1 - y2);
			double sideLength = Math.sqrt(length1 * length1 + length2 * length2);
			double midX, midY;
			midX = (x1 + x2) / 2;
			midY = (y1 + y2) / 2;
			if (Math.abs(x1 - x2) < 0.001) {
				y3 = midY;
				double tempLen = Math.sqrt(3) / 2 * sideLength;
				x3 = midX + tempLen;
			} else if (Math.abs(y1 - y2) < 0.001) {
				x3 = midX;
				double tempLen = Math.sqrt(3) / 2 * sideLength;
				y3 = midY + tempLen;
			} else {
				double k, k1;
				double b, b1;
				k = (y2 - y1) / (x2 - x1);
				b = y1 - k * x1;
				k1 = -1 / k;
				b1 = midY - k1 * midX;
				double db = 2 * k1 * (b1 - midY) - 2 * midX;
				double da = k1 * k1 + 1;
				double dc = midX * midY + (b1 - midY) * (b1 - midY) - (3.0 / 4) * sideLength * sideLength;
				double dx = db * db - 4 * da * dc;
				double dx1 = Math.sqrt(dx);
				double xa, xb, ya, yb;
				xa = ((-db + dx1) / (2 * da));
				xb = ((-db - dx1) / (2 * da));
				ya = xa * k1 + b1;
				yb = xb * k1 + b1;
				if (ya > midY) {
					x3 = xa;
					y3 = ya;
				} else {
					x3 = xb;
					y3 = yb;
				}
			}
			int[] xPoints = {x1, x2, (int) x3};
			int[] yPoints = {y1, y2, (int) y3};
			graphics2D.drawPolygon(xPoints, yPoints, 3);
			record = "triangle " + this.thick + " " + rgb + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " @";
			records.add(record);
		} else if ("rectangle".equals(command)) {
			graphics2D.setStroke(new BasicStroke(thick));
			graphics2D.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
			record = "rectangle " + this.thick + " " + rgb + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " @";
			records.add(record);
		} else if (command.equals("text")) {
			String text = JOptionPane.showInputDialog("Input your text.");
			if (text != null) {
				Font font = new Font(null,Font.PLAIN,thick + 5);
				graphics2D.setFont(font);
				graphics2D.drawString(text, x2, y2);
				record = "text " + this.thick + " " + rgb + " " + x2 + " "+y2 + " " + text + " @";
				records.add(record);
			}
		} else {
			return;
		}

		try {
			ConnectionMain.heartbeart("paint " + record);
		} catch(Exception e1){
			System.out.println("Failed to heartbeat paint record");
		}
	}

	private String getColor(Color color) {
		return color.getRed() + " " + color.getGreen() + " " + color.getBlue();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("")) {
			JButton button = (JButton) e.getSource();
			color = button.getBackground();
		} else if (e.getActionCommand().equals("color")) {
			final JFrame colorController = new JFrame("ColorController");
			colorController.setSize(200,300);
			colorController.setLocationRelativeTo(null);
			colorController.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			Color chosenColor =  JColorChooser.showDialog(colorController, "Choose a color.", null);
			if (chosenColor != null) {
				color = chosenColor;
            }
        }else if (e.getActionCommand().equals("sendChat")){
			ConnectionMain.sendMessage("message "+ managerUI.getTextFieldChat().getText());
		} else {
			this.command = e.getActionCommand();
			if (command.equals("text")) {
				jFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}  else if(command.equals("line") || command.equals("circle") || command.equals("triangle") || command.equals("rectangle")){
				jFrame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			}
        }

	}

	public void setThick(int thick) {
		this.thick = thick;
	}

	public void SetGraphics(Graphics graphics) {
		this.graphics2D = (Graphics2D) graphics;
	}

	public ArrayList<String> getRecords() {
		return records;
	}

	public void updateRecords(String record) {
		records.add(record);
	}

	public void receiveMessage(String message) {
		String[] requestArray = message.split(" ", 2);
		managerUI.getTextAreaChat().append(requestArray[1]+"\n");
	}

	public void clearRecords() {
		records.clear();
	}

	public int getThick() {
		return this.thick;
	}

}
 