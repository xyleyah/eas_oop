// In your UI class where you populate the lists
private void refreshLists() {
    // Get pending events
    List<Event> pendingEvents = DatabaseUtil.getEventsByStatus("PENDING");
    // Get approved events
    List<Event> approvedEvents = DatabaseUtil.getEventsByStatus("APPROVED");
    
    // Clear and populate pending activities list
    pendingActivitiesList.removeAll();  // Assuming you have a list component
    for (Event event : pendingEvents) {
        String displayText = String.format("%s - %s (%s)", 
            event.getFaculty(), 
            event.getEventName(),
            event.getEventDate());
        pendingActivitiesList.add(displayText);
    }
    
    // Clear and populate approved activities list
    approvedActivitiesList.removeAll();  // Assuming you have a list component
    for (Event event : approvedEvents) {
        String displayText = String.format("%s - %s (%s)", 
            event.getFaculty(), 
            event.getEventName(),
            event.getEventDate());
        approvedActivitiesList.add(displayText);
    }
}

// Add this to handle approve/decline actions
private void approveEvent(Event event) {
    if (DatabaseUtil.updateEventStatus(event.getId(), "APPROVED")) {
        refreshLists();
        JOptionPane.showMessageDialog(this, "Event approved successfully!");
    }
}

private void declineEvent(Event event) {
    if (DatabaseUtil.updateEventStatus(event.getId(), "DECLINED")) {
        refreshLists();
        JOptionPane.showMessageDialog(this, "Event declined successfully!");
    }
} 