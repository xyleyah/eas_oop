import javax.swing.*;
import java.awt.*;
import com.your.package.EventManager;

public class EventCreationForm extends JPanel {
    private AdminDashboardPanel adminDashboard;
    private JTextField facultyField;
    private JTextField eventNameField;
    private JTextField eventDateField;
    private JTextArea eventDetailsField;
    private EventManager eventManager;
    
    public EventCreationForm(AdminDashboardPanel adminDashboard) {
        this.adminDashboard = adminDashboard;
        this.eventManager = new EventManager();
    }
    
    private void handleEventSubmission() {
        String faculty = facultyField.getText().trim();
        String eventName = eventNameField.getText().trim();
        String eventDate = eventDateField.getText().trim();
        String eventDetails = eventDetailsField.getText().trim();
        
        DatabaseUtil.insertEvent(faculty, eventName, eventDate, eventDetails, "PENDING");
        eventManager.notifyEventCreated();
        JOptionPane.showMessageDialog(this, "Event created successfully!");
    }
} 