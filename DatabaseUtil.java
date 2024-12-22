import java.sql.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/easdata";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456789";

    private static Connection connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Connection Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    public static void createEventsTable() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS events (
                id INT AUTO_INCREMENT PRIMARY KEY,
                faculty VARCHAR(255) NOT NULL,
                event_name VARCHAR(255) NOT NULL,
                event_date DATE NOT NULL,
                event_details TEXT NOT NULL,
                status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;

        String addStatusColumnSQL = """
            ALTER TABLE events 
            ADD COLUMN IF NOT EXISTS status VARCHAR(50) NOT NULL DEFAULT 'PENDING';
            """;

        try (Connection conn = connectToDatabase();
             Statement stmt = conn.createStatement()) {
            // Create the table if it doesn't exist
            stmt.execute(createTableSQL);

            // Ensure the "status" column exists
            stmt.execute(addStatusColumnSQL);

            System.out.println("Events table created or updated successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error creating or updating table: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void insertEvent(String faculty, String eventName, String eventDate, String eventDetails, String eventStatus) {
        try (Connection connection = connectToDatabase()) {
            if (connection != null) {
                // Convert date format from MM-DD-YYYY to YYYY-MM-DD
                String[] dateParts = eventDate.split("-");
                String formattedDate = dateParts[2] + "-" + dateParts[0] + "-" + dateParts[1];
                
                String query = "INSERT INTO events (faculty, event_name, event_date, event_details, status) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, faculty);
                    statement.setString(2, eventName);
                    statement.setString(3, formattedDate);
                    statement.setString(4, eventDetails);
                    statement.setString(5, eventStatus);
                
                    
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Event inserted successfully!", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error inserting event: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void createAttendanceTable() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS attendance (
                id INT AUTO_INCREMENT PRIMARY KEY,
                student_id VARCHAR(50) NOT NULL,
                faculty VARCHAR(100) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                INDEX idx_student_date (student_id, faculty)
            )
            """;
            
        try (Connection conn = connectToDatabase();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Attendance table created successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error creating attendance table: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void insertAttendance(String studentId, String faculty) {
        try (Connection connection = connectToDatabase()) {
            if (connection != null) {
                String query = "INSERT INTO attendance (student_id, faculty) VALUES (?,?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, studentId);
                    statement.setString(2, faculty);
         
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "YEY ATTENDANCE DONE!", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error inserting attendance: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Connection Error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void createDatabase() {
        String URL_WITHOUT_DB = "jdbc:mysql://localhost:3306/";
        try (Connection conn = DriverManager.getConnection(URL_WITHOUT_DB, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS easdata");
            System.out.println("Database created successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error creating database: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static List<Event> getEventsByStatus(String status) {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events WHERE status = ? ORDER BY created_at DESC";
        
        try (Connection connection = connectToDatabase();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Event event = new Event(
                    rs.getInt("id"),
                    rs.getString("faculty"),
                    rs.getString("event_name"),
                    rs.getString("event_date"),
                    rs.getString("event_details"),
                    rs.getString("status")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public static boolean updateEventStatus(int eventId, String newStatus) {
        String query = "UPDATE events SET status = ? WHERE id = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, eventId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void resetEventsTable() {
        try (Connection conn = connectToDatabase()) {
            if (conn != null) {
                
                String dropTable = "DROP TABLE IF EXISTS events";
                
                String createTable = """
                    CREATE TABLE events (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        faculty VARCHAR(255) NOT NULL,
                        event_name VARCHAR(255) NOT NULL,
                        event_date DATE NOT NULL,
                        event_details TEXT NOT NULL,
                        status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                """;
                
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(dropTable);
                    stmt.execute(createTable);
                    System.out.println("Events table reset successfully!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error resetting events table: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String query = "SELECT id, faculty, event_name, event_date, event_details, status, created_at FROM events";
        
        try (Connection conn = connectToDatabase();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Event event = new Event(
                    rs.getInt("id"),
                    rs.getString("faculty"),
                    rs.getString("event_name"),
                    rs.getString("event_date"),
                    rs.getString("event_details"),
                    rs.getString("status")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public static List<Event> getPendingEvents() {
        return getEventsByStatus("Pending");
    }

    public static void resetAndVerifyEventsTable() {
        try (Connection conn = connectToDatabase()) {
            if (conn != null) {
                // Drop the table
                String dropTable = "DROP TABLE IF EXISTS events";
                
                // Create the table with all required columns
                String createTable = """
                    CREATE TABLE events (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        faculty VARCHAR(255) NOT NULL,
                        event_name VARCHAR(255) NOT NULL,
                        event_date DATE NOT NULL,
                        event_details TEXT NOT NULL,
                        status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                """;
                
                // Verify the structure
                String verifyStructure = "DESCRIBE events";
                
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(dropTable);
                    stmt.execute(createTable);
                    
                    // Verify the table structure
                    ResultSet rs = stmt.executeQuery(verifyStructure);
                    while (rs.next()) {
                        System.out.println("Column: " + rs.getString("Field") + 
                                         ", Type: " + rs.getString("Type"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error resetting events table: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Create the database and tables
        DatabaseUtil.createDatabase();
        DatabaseUtil.createAttendanceTable();
        DatabaseUtil.resetAndVerifyEventsTable();
        
        // Test insertion
        DatabaseUtil.insertEvent("FACET", "Test Event", "03-21-2024", "Test Details", "PENDING");
        
        // Verify the data
        List<Event> events = DatabaseUtil.getAllEvents();
        for (Event event : events) {
            System.out.println("Event ID: " + event.getId() + 
                             ", Status: " + event.getStatus());
        }
    }
}
