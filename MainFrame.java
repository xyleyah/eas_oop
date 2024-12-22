import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel cardPanel;
    private InitialPanel initialPanel;
    private AdminPanel adminPanel;
    private StudentAdminPanel studentAdminPanel;
    private AdminDashboardPanel adminDashboardPanel;
    private StudentAdminDashboardPanel studentAdminDashboardPanel;
    private CreateEventDashboardPanel createEventDashboardPanel;
    private StudentDashboardPanel studentDashboardPanel;
    private JTextField adminKeyField, studentAdminKeyField;
    private String currentCard = "Initial";
    private DefaultListModel<String> pendingListModel = new DefaultListModel<>();
    private DefaultListModel<String> approvedListModel = new DefaultListModel<>();
    private boolean isAdminLoggedIn = false;
    private boolean isStudentAdminLoggedIn = false;
    private JButton backButton;
    DefaultListModel<String> pendingEventsModel = new DefaultListModel<>();
    private JList<String> pendingEventsList;
    private AdminDashboardPanel adminDashboard;

    public MainFrame() {
        setTitle("USC Event Attendance System");
        setSize(950, 850);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.YELLOW);

        setLocationRelativeTo(null);

        cardPanel = new JPanel();
        cardPanel.setLayout(new CardLayout());
        
  
        initialPanel = new InitialPanel(this);
        adminPanel = new AdminPanel(this);
        studentAdminPanel = new StudentAdminPanel(this);
        adminDashboardPanel = new AdminDashboardPanel(this);
        studentAdminDashboardPanel = new StudentAdminDashboardPanel(this);
        createEventDashboardPanel = new CreateEventDashboardPanel(this);
        studentDashboardPanel = new StudentDashboardPanel(this);
       
        cardPanel.add(initialPanel, "Initial");
        cardPanel.add(adminPanel, "AdminLogin");
        cardPanel.add(studentAdminPanel, "StudentAdminLogin");
        cardPanel.add(adminDashboardPanel, "AdminDashboard");
        cardPanel.add(studentAdminDashboardPanel, "StudentAdminDashboard");
        cardPanel.add(createEventDashboardPanel, "CreateEventDashboard");
        cardPanel.add(studentDashboardPanel, "StudentDashboard");

        add(cardPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void switchToPanel(String panelName) {
        CardLayout layout = (CardLayout) cardPanel.getLayout();
        layout.show(cardPanel, panelName);
        currentCard = panelName;
    }

    // Getters and Setters
    public JPanel getCardPanel() {
        return cardPanel;
    }

    public void setCardPanel(JPanel cardPanel) {
        this.cardPanel = cardPanel;
    }

    public JPanel getInitialPanel() {
        return initialPanel;
    }

    public void setInitialPanel(InitialPanel initialPanel) {
        this.initialPanel = initialPanel;
    }

    public JPanel getAdminPanel() {
        return adminPanel;
    }

    public void setAdminPanel(AdminPanel adminPanel) {
        this.adminPanel = adminPanel;
    }

    public JPanel getStudentAdminPanel() {
        return studentAdminPanel;
    }

    public void setStudentAdminPanel(StudentAdminPanel studentAdminPanel) {
        this.studentAdminPanel = studentAdminPanel;
    }

    public JPanel getAdminDashboardPanel() {
        return adminDashboardPanel;
    }

    public void setAdminDashboardPanel(AdminDashboardPanel adminDashboardPanel) {
        this.adminDashboardPanel = adminDashboardPanel;
    }

    public JPanel getStudentAdminDashboardPanel() {
        return studentAdminDashboardPanel;
    }

    public void setStudentAdminDashboardPanel(StudentAdminDashboardPanel studentAdminDashboardPanel) {
        this.studentAdminDashboardPanel = studentAdminDashboardPanel;
    }

    public JTextField getAdminKeyField() {
        return adminKeyField;
    }

    public void setAdminKeyField(JTextField adminKeyField) {
        this.adminKeyField = adminKeyField;
    }

    public JTextField getStudentAdminKeyField() {
        return studentAdminKeyField;
    }

    public void setStudentAdminKeyField(JTextField studentAdminKeyField) {
        this.studentAdminKeyField = studentAdminKeyField;
    }

    public String getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(String currentCard) {
        this.currentCard = currentCard;
    }

    public DefaultListModel<String> getPendingListModel() {
        return pendingListModel;
    }

    public void setPendingListModel(DefaultListModel<String> pendingListModel) {
        this.pendingListModel = pendingListModel;
    }

    public DefaultListModel<String> getApprovedListModel() {
        return approvedListModel;
    }

    public void setApprovedListModel(DefaultListModel<String> approvedListModel) {
        this.approvedListModel = approvedListModel;
    }

    public boolean isAdminLoggedIn() {
        return isAdminLoggedIn;
    }

    public void setAdminLoggedIn(boolean isAdminLoggedIn) {
        this.isAdminLoggedIn = isAdminLoggedIn;
    }

    public boolean isStudentAdminLoggedIn() {
        return isStudentAdminLoggedIn;
    }

    public void setStudentAdminLoggedIn(boolean isStudentAdminLoggedIn) {
        this.isStudentAdminLoggedIn = isStudentAdminLoggedIn;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void setBackButton(JButton backButton) {
        this.backButton = backButton;
    }

    public DefaultListModel<String> getPendingEventsModel() {
        return pendingEventsModel;
    }

    public void setPendingEventsModel(DefaultListModel<String> pendingEventsModel) {
        this.pendingEventsModel = pendingEventsModel;
    }

    public JList<String> getPendingEventsList() {
        return pendingEventsList;
    }

    public void setPendingEventsList(JList<String> pendingEventsList) {
        this.pendingEventsList = pendingEventsList;
    }

    public CreateEventDashboardPanel getCreateEventDashboardPanel() {
        return createEventDashboardPanel;
    }

    public void setCreateEventDashboardPanel(CreateEventDashboardPanel createEventDashboardPanel) {
        this.createEventDashboardPanel = createEventDashboardPanel;
    }

    public StudentDashboardPanel getStudentDashboardPanel() {
        return studentDashboardPanel;
    }

    public void setStudentDashboardPanel(StudentDashboardPanel studentDashboardPanel) {
        this.studentDashboardPanel = studentDashboardPanel;
    }

    public void refreshAdminDashboard() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refreshAdminDashboard'");
    }

    public AdminDashboardPanel getAdminDashboard() {
        return adminDashboard;
    }

    private void initializePanels() {
        adminDashboard = new AdminDashboardPanel(this);
        // ... other panel initializations ...
    }
}