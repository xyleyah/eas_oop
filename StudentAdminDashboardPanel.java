import javax.swing.*;
import java.awt.*;

public class StudentAdminDashboardPanel extends JPanel {
    private MainFrame mainFrame;

    public StudentAdminDashboardPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(new Color(173, 216, 230));
        setLayout(new BorderLayout());
        setupPanel();
    }

    private void setupPanel() {
        try {
        
            JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            backButtonPanel.setBackground(new Color(173, 216, 230));
            JButton backButton = new JButton("LOG OUT");
            backButton.setFont(new Font("ARIAL", Font.BOLD, 16));
            backButton.setBackground(Color.YELLOW);
            backButton.setOpaque(true);
            backButton.addActionListener(e -> mainFrame.switchToPanel("Initial"));
            backButtonPanel.add(backButton);
            add(backButtonPanel, BorderLayout.NORTH);


            JPanel centerPanel = new JPanel(new GridBagLayout());
            centerPanel.setBackground(new Color(173, 216, 230));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(10, 0, 10, 0);

           
            JLabel studentAdminDashboardLabel = new JLabel("SELECT TRANSACTION", SwingConstants.CENTER);
            studentAdminDashboardLabel.setFont(new Font("ARIAL", Font.BOLD, 34));
            studentAdminDashboardLabel.setForeground(Color.BLACK);
            gbc.gridx = 0;
            gbc.gridy = 0;
            centerPanel.add(studentAdminDashboardLabel, gbc);

          
            JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            optionPanel.setBackground(new Color(173, 216, 230));

        
            JButton studentDashboardButton = new JButton("STUDENT ATTENDANCE");
            studentDashboardButton.setFont(new Font("Arial", Font.BOLD, 16));
            studentDashboardButton.setBackground(Color.YELLOW);
            studentDashboardButton.setOpaque(true);
            studentDashboardButton.setPreferredSize(new Dimension(300,50));
            studentDashboardButton.addActionListener(e -> mainFrame.switchToPanel("StudentDashboard"));

            JButton createEventButton = new JButton("CREATE EVENT ");
            createEventButton.setFont(new Font("Arial", Font.BOLD, 16));
            createEventButton.setBackground(Color.YELLOW);
            createEventButton.setOpaque(true);
            createEventButton.setPreferredSize(new Dimension(300, 50));
            createEventButton.addActionListener(e -> mainFrame.switchToPanel("CreateEventDashboard"));

            optionPanel.add(studentDashboardButton);
            optionPanel.add(createEventButton);

            gbc.gridy = 1;
            centerPanel.add(optionPanel, gbc);
            
            add(centerPanel, BorderLayout.CENTER);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error in setting up Student Admin Dashboard: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}