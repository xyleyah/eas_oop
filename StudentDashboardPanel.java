import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

import java.sql.SQLException;

public class StudentDashboardPanel extends JPanel {
    private JTextField studentIDField;
    private JTextArea attendanceDisplay;
    private JPanel attendancePanel;
    private JComboBox<String> statusCombo;
    private MainFrame mainFrame;

    public StudentDashboardPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(new Color(173, 216, 230));
        setLayout(new GridBagLayout());
        setupPanel();
    }

    private void setupPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel studentDashboardLabel = new JLabel("STUDENT ATTENDANCE", SwingConstants.CENTER);
        studentDashboardLabel.setFont(new Font("Arial", Font.BOLD, 36));
        studentDashboardLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(studentDashboardLabel, gbc);

        JLabel studentIDLabel = new JLabel("Enter Student ID Number:");
        studentIDLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        studentIDLabel.setForeground(Color.BLACK);
        gbc.gridy = 1;
        add(studentIDLabel, gbc);

        studentIDField = new JTextField(10);
        studentIDField.setPreferredSize(new Dimension(250, 35));
        studentIDField.setFont(new Font("Arial", Font.BOLD, 18));
        studentIDField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleAttendanceSubmission();
                }
            }
        });
        gbc.gridy = 2;
        add(studentIDField, gbc);

        statusCombo = new JComboBox<>(new String[]{"FACET", "FALS", "FCJE", "FNAHS", "FBM", "FTED", "FOSHUCOM"});
        statusCombo.setPreferredSize(new Dimension(250, 35));
        statusCombo.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 3;
        add(statusCombo, gbc);

        JButton loginButton = new JButton("ENTER");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(Color.YELLOW);
        loginButton.setOpaque(true);
        loginButton.setPreferredSize(new Dimension(250, 40));
        loginButton.addActionListener(e -> handleAttendanceSubmission());
        gbc.gridy = 4;
        add(loginButton, gbc);

        attendancePanel = new JPanel();
        attendancePanel.setLayout(new BorderLayout());
        attendancePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        attendancePanel.setBackground(new Color(173, 216, 230));
        attendancePanel.setVisible(false);

            attendanceDisplay = new JTextArea(15, 40);
            attendanceDisplay.setEditable(false);
            attendanceDisplay.setFont(new Font("Monospaced", Font.BOLD, 18));
            attendanceDisplay.setBackground(Color.WHITE);

            JScrollPane scrollPane = new JScrollPane(attendanceDisplay);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            attendancePanel.add(scrollPane, BorderLayout.CENTER);

        gbc.gridy = 5;
        add(attendancePanel, gbc);

        JButton backButton = new JButton("BACK TO MAIN MENU");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(255, 99, 71));
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setPreferredSize(new Dimension(250, 40));
        backButton.addActionListener(e -> mainFrame.switchToPanel("StudentAdminDashboard"));
        gbc.gridy = 6;
        add(backButton, gbc);

    }

    private void handleAttendanceSubmission() {
        String studentId = studentIDField.getText().trim();
        String faculty = statusCombo.getSelectedItem().toString();

        if (validateInputs(studentId, faculty)) {
            DatabaseUtil.insertAttendance(studentId, faculty);
            clearFields();
        }
    }

    private boolean validateInputs(String studentId, String faculty) {
        if (!studentId.matches("^\\d{4}-\\d{4}$")) {
            JOptionPane.showMessageDialog(this,
                "Invalid Student ID! Please enter only letters and numbers.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!faculty.matches("^[a-zA-Z0-9 ]+$")) {
            JOptionPane.showMessageDialog(this,
                "Invalid Faculty Name! Please enter only letters and numbers.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    return true;
}

    private void clearFields() {
        studentIDField.setText("");
        statusCombo.setSelectedIndex(0);
    }
}