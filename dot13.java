import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.*;

public class dot13 extends JFrame implements MouseListener, MouseMotionListener {

    private JPanel panel;
    private Point[] dots = new Point[10];
    private int dotSize = 10;
    private Point lineStart, lineEnd;
    private Point mousePos;
    private BasicStroke stroke = new BasicStroke(5);
    private int linesDrawn = 0;
    private ArrayList<Line2D> lines = new ArrayList<Line2D>();
    private ArrayList<Point[]> connectedDots = new ArrayList<Point[]>();
    private JButton submitButton;

    public dot13() {
        panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(stroke);
                for (int i = 0; i < dots.length; i++) {
                    Color dotColor = Color.BLACK;
                    for (Point[] connectedDotPair : connectedDots) {
                        if (connectedDotPair[0].equals(dots[i]) || connectedDotPair[1].equals(dots[i])) {
                            dotColor = Color.GREEN;
                            break;
                        }
                    }
                    g.setColor(dotColor);
                    g.fillOval(dots[i].x - dotSize / 2, dots[i].y - dotSize / 2, dotSize, dotSize);
                }
                g.setColor(Color.RED); // set the line color here
                for (Line2D line : lines) {
                    g2.draw(line);
                }
                if (lineStart != null && lineEnd != null) {
                    g.drawLine(lineStart.x, lineStart.y, lineEnd.x, lineEnd.y);
                }
                if (mousePos != null && lineStart != null && lineEnd == null) {
                    g.setColor(Color.BLUE);
                    g.drawLine(lineStart.x, lineStart.y, mousePos.x, mousePos.y);
                }
            }
        };

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (linesDrawn == 10 && checkAllDotsConnected()) {
                    JOptionPane.showMessageDialog(dot13.this, "Successful");
                } else {
                    JOptionPane.showMessageDialog(dot13.this, "Try Again");
                    connectedDots.clear();
                    lines.clear();
                    linesDrawn = 0;
                    repaint();
                }
            }
            
        });

        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        add(panel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);

        // Create 10 points to make a circle
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = 100;
        for (int i = 0; i < 10; i++) {
            double angle = Math.toRadians(i * 36);
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            dots[i]= new Point(x, y);
        }
        }public static void main(String[] args) {
            new dot13();
        }
        
        private boolean checkAllDotsConnected() {
            // Check if all dots are connected to at least one other dot
            for (Point dot : dots) {
                boolean isConnected = false;
                for (Point[] connectedDotPair : connectedDots) {
                    if (connectedDotPair[0].equals(dot) || connectedDotPair[1].equals(dot)) {
                        isConnected = true;
                        break;
                    }
                }
                if (!isConnected) {
                    return false;
                }
            }
            return true;
        }
        
        public void mouseClicked(MouseEvent e) {}
        
        public void mousePressed(MouseEvent e) {
            for (int i = 0; i < dots.length; i++) {
                if (Math.abs(e.getX() - dots[i].x) < dotSize / 2 && Math.abs(e.getY() - dots[i].y) < dotSize / 2) {
                    if (lineStart == null && lineEnd == null) {
                        mousePos = new Point(e.getX(), e.getY());
                        lineStart = dots[i];
                    } else if (lineStart != null && lineEnd == null && lineStart != dots[i]) {
                        lineEnd = dots[i];
                        lines.add(new Line2D.Double(lineStart, lineEnd));
                        connectedDots.add(new Point[] { lineStart, lineEnd });
                        linesDrawn++;
                        lineStart = null;
                        lineEnd = null;
                    }
                    break;
                }
            }
            repaint();
        }
        public void mouseReleased(MouseEvent e) {}
        
        public void mouseEntered(MouseEvent e) {}
        
        public void mouseExited(MouseEvent e) {}
        
        public void mouseDragged(MouseEvent e) {
            if (lineStart != null && lineEnd == null) {
                mousePos = new Point(e.getX(), e.getY());
                repaint();
            }
        }
        
        public void mouseMoved(MouseEvent e) {
            if (lineStart != null && lineEnd == null) {
                mousePos = new Point(e.getX(), e.getY());
                for (int i = 0; i < dots.length; i++) {
                    if (dots[i] != lineStart && Math.abs(mousePos.x - dots[i].x) < dotSize / 2 && Math.abs(mousePos.y - dots[i].y) < dotSize / 2) {
                        lineEnd = dots[i];
                        lines.add(new Line2D.Double(lineStart, lineEnd));
                        connectedDots.add(new Point[] { lineStart, lineEnd });
                        linesDrawn++;
                        lineStart = null;
                        lineEnd = null;
                        break;
                    }
                }
                repaint();
            }
        }
    }