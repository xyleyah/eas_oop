import javax.swing.*;
import java.awt.*;

public class StudentAdminPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField studentAdminKeyField;

    public StudentAdminPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(new Color(135, 206, 250));
        setupPanel();
    }

    private void setupPanel() {
        try {
            JButton backButton = new JButton("BACK");
            backButton.setFont(new Font("ARIAL", Font.BOLD, 16));
            backButton.setBackground(Color.YELLOW);
            backButton.setOpaque(true);
            backButton.addActionListener(e -> mainFrame.switchToPanel("Initial"));

            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topPanel.setOpaque(false);
            topPanel.add(backButton);

            JLabel studentAdminLabel = new JLabel("STUDENT ADMIN LOG IN");
            studentAdminLabel.setHorizontalAlignment(SwingConstants.CENTER);
            studentAdminLabel.setFont(new Font("ARIAL", Font.BOLD, 35));
            studentAdminLabel.setOpaque(false);
            studentAdminLabel.setForeground(Color.BLACK);

            
            JPanel titlePanel = new JPanel(new BorderLayout());
            titlePanel.setBackground(new Color(135, 206, 250));
            titlePanel.add(studentAdminLabel, BorderLayout.CENTER);
            titlePanel.setBorder(BorderFactory.createEmptyBorder(200, 0, 0, 0)); // Reduced top padding, added bottom padding

          
            JLabel facultyLabel = new JLabel("Select Faculty:");
            facultyLabel.setForeground(Color.BLACK);
            facultyLabel.setFont(new Font("ARIAL", Font.PLAIN, 25));
            facultyLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            String[] faculties = {"FACET", "FALS", "FCJE", "FNAHS", "FBM", "FTED", "FOSHUCOM"};
            JComboBox<String> facultyDropdown = new JComboBox<>(faculties);
            facultyDropdown.setFont(new Font("ARIAL", Font.PLAIN, 20));

            JLabel keyLabel = new JLabel("Enter Admin Key:");
            keyLabel.setForeground(Color.BLACK);
            keyLabel.setFont(new Font("ARIAL", Font.PLAIN, 25));
            studentAdminKeyField = new JTextField(15);
            studentAdminKeyField.setFont(new Font("Arial", Font.PLAIN, 20));
            studentAdminKeyField.setBorder(BorderFactory.createCompoundBorder(
                studentAdminKeyField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            studentAdminKeyField.setToolTipText("Enter your faculty admin key");

           
            JLabel passwordLabel = new JLabel("Enter Password:");
            passwordLabel.setForeground(Color.BLACK);
            passwordLabel.setFont(new Font("Arial", Font.PLAIN, 25));
            JPasswordField passwordField = new JPasswordField(15);
            passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
            passwordField.setBorder(BorderFactory.createCompoundBorder(
                passwordField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            passwordField.setToolTipText("Enter your password");

            
         
            JButton loginButton = new JButton("LOG IN");
            loginButton.addActionListener(e -> handleStudentAdminLogin(facultyDropdown, passwordField));
            loginButton.setFont(new Font("Arial", Font.BOLD, 22));
            loginButton.setBackground(Color.YELLOW);
            loginButton.setOpaque(true);

         
            JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
            inputPanel.setBackground(new Color(135, 206, 250));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10)); // Add padding around input panel
            inputPanel.add(facultyLabel);
            inputPanel.add(facultyDropdown);
            inputPanel.add(keyLabel);
            inputPanel.add(studentAdminKeyField);
            inputPanel.add(passwordLabel);
            inputPanel.add(passwordField);

        
            JPanel centeredInputPanel = new JPanel(new GridBagLayout());
            centeredInputPanel.setBackground(new Color(135, 206, 250));
            
          
            centeredInputPanel.add(inputPanel);

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.setBackground(new Color(135, 206, 250));
            buttonPanel.add(loginButton);

            
            JPanel backButtonWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
            backButtonWrapper.setBackground(new Color(135, 206, 250));
            backButtonWrapper.add(backButton);

          
            JPanel topPanelContainer = new JPanel();
            topPanelContainer.setLayout(new BoxLayout(topPanelContainer, BoxLayout.Y_AXIS));
            topPanelContainer.setBackground(new Color(135, 206, 250));

           
            topPanelContainer.add(backButtonWrapper);
            topPanelContainer.add(titlePanel);

            add(topPanelContainer, BorderLayout.NORTH);
            add(centeredInputPanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error setting up Student Admin Panel: " + e.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleStudentAdminLogin(JComboBox<String> facultyDropdown, JPasswordField passwordField) {
        try {
            String faculty = (String) facultyDropdown.getSelectedItem();
            String adminKey = studentAdminKeyField.getText().trim();
            String password = new String(passwordField.getPassword());

            if ((faculty.equals("FACET") && adminKey.equals("Facet001") && password.equals("facetadmin001")) ||
                (faculty.equals("FALS") && adminKey.equals("Fals002") && password.equals("falsadmin002")) ||
                (faculty.equals("FCJE") && adminKey.equals("Fcje003") && password.equals("fcjeadmin003")) ||
                (faculty.equals("FNAHS") && adminKey.equals("Fnahs004") && password.equals("fnahsadmin004")) ||
                (faculty.equals("FBM") && adminKey.equals("Fbm005") && password.equals("fbmadmin005")) ||
                (faculty.equals("FTED") && adminKey.equals("Fted006") && password.equals("ftedadmin006")) ||
                (faculty.equals("FOSHUCOM") && adminKey.equals("Foshucom007") && password.equals("foshucomadmin007"))) {

                
                studentAdminKeyField.setText("");
                passwordField.setText("");
                facultyDropdown.setSelectedIndex(0);

                JOptionPane.showMessageDialog(this, "Student Admin Login Successful", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                mainFrame.switchToPanel("StudentAdminDashboard");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Admin Key or Password", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during Student Admin login: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}