package client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Painter extends JPanel {
    private static final long serialVersionUID = 1L;
    private List<String> paintRecords = new ArrayList<>();

    /**
     * 	history list.
     */
    public void setPaintRecords(List<String> paintRecords) {
        this.paintRecords = paintRecords;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        painter((Graphics2D) graphics, this.paintRecords);
    }

    private void painter(Graphics2D graphics2D, List<String> paintRecords) {
        try{
            String[] records = paintRecords.toArray(new String[paintRecords.size()]);
            for (String s : records) {
                String[] record = s.split(" ");
                int x1, y1, x2, y2, thick, red, green, blue;
                if (record[1].equals("@")) {
                    continue;
                }


                if ("line".equals(record[0])) {
                    thick = Integer.parseInt(record[1]);
                    graphics2D.setStroke(new BasicStroke(thick));
                    red = Integer.parseInt(record[2]);
                    green = Integer.parseInt(record[3]);
                    blue = Integer.parseInt(record[4]);
                    graphics2D.setColor(new Color(red, green, blue));
                    x1 = Integer.parseInt(record[5]);
                    y1 = Integer.parseInt(record[6]);
                    x2 = Integer.parseInt(record[7]);
                    y2 = Integer.parseInt(record[8]);
                    graphics2D.drawLine(x1, y1, x2, y2);
                } else if ("circle".equals(record[0])) {
                    thick = Integer.parseInt(record[1]);
                    graphics2D.setStroke(new BasicStroke(thick));
                    red = Integer.parseInt(record[2]);
                    green = Integer.parseInt(record[3]);
                    blue = Integer.parseInt(record[4]);
                    graphics2D.setColor(new Color(red, green, blue));
                    x1 = Integer.parseInt(record[5]);
                    y1 = Integer.parseInt(record[6]);
                    x2 = Integer.parseInt(record[7]);
                    y2 = Integer.parseInt(record[8]);
                    int diameter = Math.min(Math.abs(x1 - x2), Math.abs(y1 - y2));
                    graphics2D.drawOval(Math.min(x1, x2), Math.min(y1, y2), diameter, diameter);
                } else if ("triangle".equals(record[0])) {
                    thick = Integer.parseInt(record[1]);
                    graphics2D.setStroke(new BasicStroke(thick));
                    red = Integer.parseInt(record[2]);
                    green = Integer.parseInt(record[3]);
                    blue = Integer.parseInt(record[4]);
                    graphics2D.setColor(new Color(red, green, blue));
                    x1 = Integer.parseInt(record[5]);
                    y1 = Integer.parseInt(record[6]);
                    x2 = Integer.parseInt(record[7]);
                    y2 = Integer.parseInt(record[8]);
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
                } else if ("rectangle".equals(record[0])) {
                    thick = Integer.parseInt(record[1]);
                    graphics2D.setStroke(new BasicStroke(thick));
                    red = Integer.parseInt(record[2]);
                    green = Integer.parseInt(record[3]);
                    blue = Integer.parseInt(record[4]);
                    graphics2D.setColor(new Color(red, green, blue));
                    x1 = Integer.parseInt(record[5]);
                    y1 = Integer.parseInt(record[6]);
                    x2 = Integer.parseInt(record[7]);
                    y2 = Integer.parseInt(record[8]);
                    graphics2D.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
                } else if (record[0].equals("color")) {
                    continue;
                } else if (record[0].equals("text"))  {
                    thick = Integer.parseInt(record[1]);
                    graphics2D.setStroke(new BasicStroke(thick));
                    red = Integer.parseInt(record[2]);
                    green = Integer.parseInt(record[3]);
                    blue = Integer.parseInt(record[4]);
                    graphics2D.setColor(new Color(red, green, blue));
                    x1 = Integer.parseInt(record[5]);
                    y1 = Integer.parseInt(record[6]);
                    String text = record[7];
                    Font font = new Font(null, Font.PLAIN, thick + 5);
                    graphics2D.setFont(font);
                    graphics2D.drawString(text, x1, y1);
                } else {
                    continue;
                }
            }
        } catch (Exception e){
            System.out.println("Painter error!");
        }

    }


}
