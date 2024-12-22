import javax.swing.*;
import java.awt.*;
import javax.swing.text.JTextComponent;

public class CreateEventDashboardPanel extends JPanel {
    private MainFrame mainFrame;

    public CreateEventDashboardPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(new Color(135, 206, 235));
        setLayout(new GridBagLayout());
        setupPanel();
    }

    private void setupPanel() {
        try {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(2, 2, 2, 2);

          
            JButton backButton = new JButton("BACK");
            backButton.setFont(new Font("Arial", Font.BOLD, 16));
            backButton.setBackground(Color.YELLOW);
            backButton.setForeground(Color.BLACK);
            backButton.setOpaque(true);
            backButton.addActionListener(e -> mainFrame.switchToPanel("StudentAdminDashboard"));

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.weightx = 0.0;
            gbc.weighty = 0.0;
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            gbc.fill = GridBagConstraints.NONE;
            add(backButton, gbc);

            JLabel createEventLabel = new JLabel("CREATE EVENT ");
            createEventLabel.setFont(new Font("Arial", Font.BOLD, 35));
            createEventLabel.setForeground(Color.BLACK);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1.0;
            gbc.weighty = 0.0;
            gbc.anchor = GridBagConstraints.PAGE_START;
            add(createEventLabel, gbc);

            gbc.gridy = 2;
            gbc.weighty = 1.0;
            setupFormFields(gbc);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error in setting up Create Event Dashboard: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupFormFields(GridBagConstraints gbc) {
        
        JLabel facultyLabel = new JLabel("Faculty:");
        facultyLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JTextField facultyField = new JTextField();
        facultyField.setFont(new Font("Arial", Font.PLAIN, 25));
        facultyField.setPreferredSize(new Dimension(600, 30));
    
        JLabel eventNameLabel = new JLabel("Event Name:");
        eventNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JTextField eventNameField = new JTextField();
        eventNameField.setFont(new Font("Arial", Font.PLAIN, 25));
        eventNameField.setPreferredSize(new Dimension(600, 30));
    
        JLabel eventDateLabel = new JLabel("Event Date (MM-DD-YYYY):");
        eventDateLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JTextField eventDateField = new JTextField();
        eventDateField.setFont(new Font("Arial", Font.PLAIN, 25));
        eventDateField.setPreferredSize(new Dimension(600, 30));
    
        JLabel eventDetailsLabel = new JLabel("Event Details:");
        eventDetailsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JTextArea eventDetailsField = new JTextArea(5, 50); // 5 rows and 50 columns
        eventDetailsField.setFont(new Font("Arial", Font.PLAIN, 25));
        eventDetailsField.setLineWrap(true); // Enable text wrapping
        eventDetailsField.setWrapStyleWord(true); // Wrap at word boundaries
        eventDetailsField.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Add a border
         JScrollPane eventDetailsScrollPane = new JScrollPane(eventDetailsField); // Add a scroll pane
        eventDetailsScrollPane.setPreferredSize(new Dimension(600, 100)); // Set preferred size for scrolling
        

        JButton submitButton = new JButton("SUBMIT");
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.setBackground(Color.YELLOW);
        submitButton.setForeground(Color.BLACK);
        submitButton.setOpaque(true);
    
        
        gbc.gridwidth = 1;
    
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(facultyLabel, gbc);
    
        gbc.gridx = 1;
        add(facultyField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(eventNameLabel, gbc);
    
        gbc.gridx = 1;
        add(eventNameField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(eventDateLabel, gbc);
    
        gbc.gridx = 1;
        add(eventDateField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(eventDetailsLabel, gbc);
        
        gbc.gridx = 1;
        add(eventDetailsScrollPane, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);
    
       
        JButton waitingButton = new JButton("Waiting to be Approved");
        waitingButton.setFont(new Font("Arial", Font.ITALIC, 16));
        waitingButton.setBackground(new Color(200, 200, 200)); 
        waitingButton.setEnabled(false); 
        waitingButton.setVisible(false); 
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(waitingButton, gbc);

        
        submitButton.addActionListener(e -> {
            String faculty = facultyField.getText().trim();
            String eventName = eventNameField.getText().trim();
            String eventDate = eventDateField.getText().trim();
            String eventDetails = eventDetailsField.getText().trim();
        
            if (validateInputs(faculty, eventName, eventDate, eventDetails)) {
                DatabaseUtil.insertEvent(faculty, eventName, eventDate, eventDetails, "PENDING");
        
                JOptionPane.showMessageDialog(this,
                    "EVENT CREATED!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        
                clearFields(facultyField, eventNameField, eventDateField, eventDetailsField);
        
                // Notify MainFrame to refresh the Admin Dashboard
                if (mainFrame != null) {
                    mainFrame.refreshAdminDashboard();      
                }
        
                revalidate();
                repaint();
            }
        });
    }        
    

    private boolean validateInputs(String faculty, String eventName, String eventDate, String eventDetails) {
        if (!faculty.matches("^[a-zA-Z0-9 ]+$")) {
            JOptionPane.showMessageDialog(this,
                "Invalid Faculty Name! Please enter only letters and numbers.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (eventName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Event Name cannot be empty!",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!eventDate.matches("^\\d{2}-\\d{2}-\\d{4}$")) {
            JOptionPane.showMessageDialog(this,
                "Invalid Event Date! Please enter the date in MM-DD-YYYY format.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (eventDetails.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Event Details cannot be empty!",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }

    private void clearFields(JTextComponent... components) {
        for (JTextComponent component : components) {
            component.setText("");
        }
    }
}