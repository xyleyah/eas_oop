import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InitialPanel extends JPanel {
    private MainFrame mainFrame;
    private BufferedImage backgroundImage;

    public InitialPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        setupPanel();
    }

    private void setupPanel() {
        try {
            backgroundImage = ImageIO.read(new File("C:/Users/LEA MAE B. GUDES/OneDrive/Desktop/OOP_FINALPROJ/maxresdefault.jpg"));
        } catch (IOException e) {
            System.err.println("Failed to load background image: " + e.getMessage());
        }

        JLabel initialLabel = new JLabel("SELECT LOGIN OPTION");
        initialLabel.setHorizontalAlignment(SwingConstants.CENTER);
        initialLabel.setFont(new Font("Monospace", Font.BOLD, 38));
        initialLabel.setForeground(Color.WHITE);
        initialLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

  
        JButton buttonAdmin = createRoundedButton("Log in as Admin");
        JButton buttonStudentAdmin = createRoundedButton("Log in as Student Admin");

        buttonAdmin.addActionListener(e -> mainFrame.switchToPanel("AdminLogin"));
        buttonStudentAdmin.addActionListener(e -> mainFrame.switchToPanel("StudentAdminLogin"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        add(initialLabel, gbc);
        gbc.gridy = 1;
        add(buttonAdmin, gbc);
        gbc.gridy = 2;
        add(buttonStudentAdmin, gbc);
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isArmed()) {
                    g.setColor(new Color(220, 220, 220));
                } else {
                    g.setColor(Color.YELLOW);
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }

            @Override
            public void paintBorder(Graphics g) {
                g.setColor(new Color(200, 200, 200));
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };

        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(240, 50));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        GradientPaint gradient = new GradientPaint(0, 0, new Color(85, 85, 255, 100), 
            0, getHeight(), new Color(135, 206, 250, 100));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}