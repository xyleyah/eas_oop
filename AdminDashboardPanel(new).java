import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminDashboardPanel extends JPanel {
    private MainFrame mainFrame;
    private DefaultListModel<String> pendingListModel;
    private DefaultListModel<String> approvedListModel;
    private DefaultListModel<String> declinedListModel;
    private JList<String> pendingList;
    private JList<String> approvedList;
    private JList<String> declinedList;

    public AdminDashboardPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.pendingListModel = new DefaultListModel<>();
        this.approvedListModel = new DefaultListModel<>();
        this.declinedListModel = new DefaultListModel<>();
        setLayout(new BorderLayout());
        setupPanel();
        refreshLists();
    }

    public void refreshLists() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                loadPendingEvents();
                loadApprovedEvents();
                loadDeclinedEvents();
                return null;
            }

            @Override
            protected void done() {
                setCursor(Cursor.getDefaultCursor());
            }
        };
        worker.execute();
    }

    private void loadPendingEvents() {
        try {
            SwingUtilities.invokeLater(() -> pendingListModel.clear());
            List<Event> events = DatabaseUtil.getPendingEvents();
            for (Event event : events) {
                String eventDetails = String.format("<html>ID: %d<br>Event: %s<br>Faculty: %s<br>Date: %s</html>",
                        event.getId(),
                        event.getEventName(),
                        event.getFaculty(),
                        event.getEventDate()
                );
                SwingUtilities.invokeLater(() -> pendingListModel.addElement(eventDetails));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error loading pending events", e);
        }
    }

    private void loadApprovedEvents() {
        try {
            SwingUtilities.invokeLater(() -> approvedListModel.clear());
            List<Event> events = DatabaseUtil.getEventsByStatus("APPROVED");
            for (Event event : events) {
                String eventDetails = String.format("<html>ID: %d<br>Event: %s<br>Faculty: %s<br>Date: %s<br>Details: %s</html>",
                        event.getId(),
                        event.getEventName(),
                        event.getFaculty(),
                        event.getEventDate(),
                        event.getEventDetails()
                );
                SwingUtilities.invokeLater(() -> approvedListModel.addElement(eventDetails));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error loading approved events", e);
        }
    }

    private void loadDeclinedEvents() {
        try {
            SwingUtilities.invokeLater(() -> declinedListModel.clear());
            List<Event> events = DatabaseUtil.getEventsByStatus("DECLINED");
            for (Event event : events) {
                String eventDetails = String.format("<html>ID: %d<br>Event: %s<br>Faculty: %s<br>Date: %s<br>Details: %s</html>",
                        event.getId(),
                        event.getEventName(),
                        event.getFaculty(),
                        event.getEventDate(),
                        event.getEventDetails()
                );
                SwingUtilities.invokeLater(() -> declinedListModel.addElement(eventDetails));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error loading declined events", e);
        }
    }

    private void moveItem(DefaultListModel<String> sourceModel, DefaultListModel<String> destinationModel, JList<String> sourceList, String newStatus) {
        int selectedIndex = sourceList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "No activity selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    String item = sourceModel.get(selectedIndex);
                    int eventId = extractEventId(item);
                    DatabaseUtil.updateEventStatus(eventId, newStatus);
                    SwingUtilities.invokeLater(() -> refreshLists());
                } catch (Exception e) {
                    e.printStackTrace();
                    showError("Error while moving item", e);
                }
                return null;
            }
        };
        worker.execute();
    }

    private int extractEventId(String item) throws NumberFormatException {
        int startIndex = item.indexOf("ID: ") + 4;
        int endIndex = item.indexOf("<br>", startIndex);
        return Integer.parseInt(item.substring(startIndex, endIndex));
    }

    private void setupPanel() {
        JButton backButton = new JButton("LOG OUT");
        backButton.setFont(new Font("Monospace", Font.BOLD, 14));
        backButton.addActionListener(e -> mainFrame.switchToPanel("Initial"));
        backButton.setBackground(Color.YELLOW);
        backButton.setForeground(Color.BLACK);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel adminDashboardLabel = new JLabel(" ACTIVITY MANAGEMENT");
        adminDashboardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        adminDashboardLabel.setFont(new Font("Arial", Font.BOLD, 28));
        adminDashboardLabel.setOpaque(true);
        adminDashboardLabel.setBackground(new Color(135, 206, 235));
        adminDashboardLabel.setForeground(Color.BLACK);
        adminDashboardLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        topPanel.add(adminDashboardLabel, BorderLayout.CENTER);

        JPanel activityPanel = new JPanel(new GridLayout(1, 2));
        JPanel pendingPanel = createPendingPanel();
        JPanel resultPanel = createResultPanel();

        activityPanel.add(pendingPanel);
        activityPanel.add(resultPanel);

        add(topPanel, BorderLayout.NORTH);
        add(activityPanel, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh Lists");
        refreshButton.addActionListener(e -> refreshLists());
        add(refreshButton, BorderLayout.SOUTH);
    }

    private JPanel createPendingPanel() {
        JPanel pendingPanel = new JPanel(new BorderLayout());
        JLabel pendingLabel = new JLabel("Pending Activities");
        pendingLabel.setFont(new Font("Arial", Font.BOLD, 20));

        pendingList = new JList<>(pendingListModel);
        pendingList.setFont(new Font("Arial", Font.PLAIN, 14));
        pendingList.setFixedCellHeight(100);
        pendingList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return label;
            }
        });

        JScrollPane pendingScroll = new JScrollPane(pendingList);
        pendingScroll.setPreferredSize(new Dimension(200, 300));

        JButton approveButton = new JButton("Approve");
        approveButton.setFont(new Font("Arial", Font.BOLD, 16));
        approveButton.setBackground(Color.YELLOW);
        approveButton.addActionListener(e -> moveItem(pendingListModel, approvedListModel, pendingList, "APPROVED"));

        JButton declineButton = new JButton("Decline");
        declineButton.setFont(new Font("Arial", Font.BOLD, 16));
        declineButton.setBackground(Color.YELLOW);
        declineButton.addActionListener(e -> moveItem(pendingListModel, declinedListModel, pendingList, "DECLINED"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(approveButton);
        buttonPanel.add(declineButton);

        pendingPanel.add(pendingLabel, BorderLayout.NORTH);
        pendingPanel.add(pendingScroll, BorderLayout.CENTER);
        pendingPanel.add(buttonPanel, BorderLayout.SOUTH);

        return pendingPanel;
    }

    private JPanel createResultPanel() {
        JPanel resultPanel = new JPanel(new GridLayout(2, 1));

        JPanel approvedPanel = new JPanel(new BorderLayout());
        JLabel approvedLabel = new JLabel("Approved Activities");
        approvedLabel.setFont(new Font("Arial", Font.BOLD, 20));

        approvedList = new JList<>(approvedListModel);
        approvedList.setFont(new Font("Arial", Font.PLAIN, 16));
        approvedList.setFixedCellHeight(100);

        JScrollPane approvedScroll = new JScrollPane(approvedList);
        approvedScroll.setPreferredSize(new Dimension(200, 150));

        approvedPanel.add(approvedLabel, BorderLayout.NORTH);
        approvedPanel.add(approvedScroll, BorderLayout.CENTER);

        JPanel declinedPanel = new JPanel(new BorderLayout());
        JLabel declinedLabel = new JLabel("Declined Activities");
        declinedLabel.setFont(new Font("Arial", Font.BOLD, 20));

        declinedList = new JList<>(declinedListModel);
        declinedList.setFont(new Font("Arial", Font.PLAIN, 16));
        declinedList.setFixedCellHeight(100);

        JScrollPane declinedScroll = new JScrollPane(declinedList);
        declinedScroll.setPreferredSize(new Dimension(200, 150));

        declinedPanel.add(declinedLabel, BorderLayout.NORTH);
        declinedPanel.add(declinedScroll, BorderLayout.CENTER);

        resultPanel.add(approvedPanel);
        resultPanel.add(declinedPanel);

        return resultPanel;
    }

    private void showError(String message, Exception e) {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(this,
                        message + ": " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE)
        );
    }
}
