import java.awt.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.*;

public class Captcha {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;
    private static final int SHAPE_SIZE = 50;
    private static final int FONT_SIZE = 30;
    private static final int CHAR_WIDTH = 20;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Captcha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JLabel captchaLabel = new JLabel();
        captchaLabel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        captchaLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        captchaLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));

        JPanel captchaPanel = new JPanel();
        captchaPanel.add(captchaLabel);
        frame.add(captchaPanel, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> captchaLabel.setIcon(new ImageIcon(generateCaptcha())));
        frame.add(refreshButton, BorderLayout.SOUTH);

        captchaLabel.setIcon(new ImageIcon(generateCaptcha()));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static BufferedImage generateCaptcha() {
        BufferedImage captcha = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = captcha.createGraphics();

        // Draw background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw random shape
        Shape shape = generateRandomShape();
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2f));
        g2d.draw(shape);

        // Draw random text
        StringBuilder captchaText = new StringBuilder();
        g2d.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        FontMetrics fontMetrics = g2d.getFontMetrics();
        for (int i = 0; i < 4; i++) {
            char c = CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length()));
            captchaText.append(c);
            g2d.setColor(new Color(RANDOM.nextInt(256), RANDOM.nextInt(256), RANDOM.nextInt(256)));
            g2d.drawString(String.valueOf(c), i * CHAR_WIDTH + (SHAPE_SIZE - CHAR_WIDTH * 4) / 2, SHAPE_SIZE / 2 + fontMetrics.getAscent() / 2);
        }

        g2d.dispose();
        return captcha;
    }

    private static Shape generateRandomShape() {
        int[] xPoints = {0, RANDOM.nextInt(SHAPE_SIZE / 2) + SHAPE_SIZE / 2, SHAPE_SIZE};
        int[] yPoints = {RANDOM.nextInt(SHAPE_SIZE / 2), SHAPE_SIZE, RANDOM.nextInt(SHAPE_SIZE / 2)};
        return new Polygon(xPoints, yPoints, 3);
    }
}
