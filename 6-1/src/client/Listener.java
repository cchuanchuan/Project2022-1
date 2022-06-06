package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Listener implements ActionListener, MouseListener, MouseMotionListener {
     private Graphics2D graphics2D;
     private int x1, y1, x2, y2;
     private Object command = "line";
//     JFrame jFrame  = new JFrame("111");
    private JFrame jFrame;
    private String record;
    private ArrayList<String> records = new ArrayList<>();
    private int thick = 1;

    static Color color = Color.black;
    private String rgb = "0 0 0";

    private String paint;

    public Listener(JFrame frmManager) {
        this.jFrame = frmManager;
    }
    public Listener() { }



    private String getColor(Color color) {
        return color.getRed() + " " + color.getGreen() + " " + color.getBlue();
    }


    public List<String> getHistory() {
        return records;
    }


    public int getThick() {
        return thick;
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

    public void clearRecords() {
        records.clear();
    }

    public void setThick(int thick) {
        this.thick = thick;
    }
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
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
            color =  JColorChooser.showDialog(colorController, "Choose a color.", null);
        } else {
            this.command = e.getActionCommand();
            if (command.equals("text")) {
                jFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            } else if(command.equals("line") || command.equals("circle") || command.equals("triangle") || command.equals("rectangle")){
                jFrame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            }
        }
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
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

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        rgb = getColor(color);
        graphics2D.setStroke(new BasicStroke(thick));
        //drawLine
        if (this.command.equals("line")) {
            graphics2D.drawLine(x1, y1, x2, y2);
            record = "line " + this.thick + " " + rgb + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " @";
            records.add(record);

        } else if ("circle".equals(command)) {
            int diameter = Math.min(Math.abs(x1 - x2), Math.abs(y1 - y2));
            graphics2D.drawOval(Math.min(x1, x2), Math.min(y1, y2), diameter, diameter);
            record = "circle " + this.thick + " " + rgb + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " @";
            records.add(record);
        } else if ("triangle".equals(command)) {
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
            LoginUI.connection.dataOutputStream.writeUTF("paint " + record);
            LoginUI.connection.dataOutputStream.flush();
        } catch(IOException e1){
            System.out.println("Failed to send paint record");
        }
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  {@code MOUSE_DRAGGED} events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * {@code MOUSE_DRAGGED} events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
