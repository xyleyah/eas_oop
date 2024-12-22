public class Event {
    private int id;
    private String faculty;
    private String eventName;
    private String eventDate;
    private String eventDetails;
    private String status;

    public Event(int id, String faculty, String eventName, String eventDate, String eventDetails, String status) {
        this.id = id;
        this.faculty = faculty;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventDetails = eventDetails;
        this.status = status;
    }

    public int getId() { return id; }
    public String getFaculty() { return faculty; }
    public String getEventName() { return eventName; }
    public String getEventDate() { return eventDate; }
    public String getEventDetails() { return eventDetails; }
    public String getStatus() { return status; }
}
