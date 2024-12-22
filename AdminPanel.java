import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField adminKeyField;

    public AdminPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(new Color(135, 206, 235));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setupPanel();
    }

    private void setupPanel() {
        try {
            
            JButton backButton = new JButton("BACK");
            backButton.setFont(new Font("Arial", Font.BOLD, 20));
            backButton.setForeground(new Color(50, 50, 50));
            backButton.setBackground(Color.YELLOW);
            backButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 150), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
            backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            backButton.addActionListener(e -> {
                mainFrame.switchToPanel("Initial");
                setVisible(false);
            });

            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topPanel.setOpaque(false);
            topPanel.add(backButton);
            add(topPanel, BorderLayout.NORTH);

         
            adminKeyField = new JPasswordField(18);
            adminKeyField.setPreferredSize(new Dimension(50, 30));
            adminKeyField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            adminKeyField.setHorizontalAlignment(JTextField.CENTER);
            adminKeyField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

            JLabel adminKeyLabel = new JLabel("Enter Admin Key");
            adminKeyLabel.setForeground(new Color(44, 62, 80));
            adminKeyLabel.setFont(new Font("Arial", Font.BOLD, 20  ));

            JLabel adminLabel = new JLabel("ADMIN LOG IN");
            adminLabel.setHorizontalAlignment(SwingConstants.CENTER);
            adminLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
            adminLabel.setForeground(new Color(44, 62, 80));

           
            JButton loginButton = new JButton("LOG IN");
            loginButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
            loginButton.setForeground(Color.BLACK);
            loginButton.setBackground(Color.YELLOW);
            loginButton.setPreferredSize(new Dimension(200, 40));
            loginButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(41, 128, 185), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
            loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            loginButton.addActionListener(e -> handleAdminLogin());

            JPanel centerPanel = new JPanel(new GridBagLayout());
            centerPanel.setOpaque(false);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(20, 0, 30, 0);
            centerPanel.add(adminLabel, gbc);

            gbc.gridy++;
            gbc.insets = new Insets(0, 0, 10, 0);
            centerPanel.add(adminKeyLabel, gbc);

            gbc.gridy++;
            gbc.insets = new Insets(0, 0, 20, 0);
            centerPanel.add(adminKeyField, gbc);

            gbc.gridy++;
            centerPanel.add(loginButton, gbc);

            add(centerPanel, BorderLayout.CENTER);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error setting up Admin Panel: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAdminLogin() {
        try {
            String enteredKey = adminKeyField.getText().trim();

            if ("adminKey123".equals(enteredKey)) {
                JOptionPane.showMessageDialog(this, "Admin Login Successful", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                clearAdminKeyField();
                mainFrame.switchToPanel("AdminDashboard");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Admin Key", "Error",
                        JOptionPane.ERROR_MESSAGE);
                clearAdminKeyField();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during Admin login: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearAdminKeyField() {
        adminKeyField.setText("");
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            clearAdminKeyField();
        }
        super.setVisible(visible);
    }
}