-- Use the database
USE easdata;

-- Create the events table
CREATE TABLE IF NOT EXISTS events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    faculty VARCHAR(255) NOT NULL,
    event_name VARCHAR(255) NOT NULL,
    event_date DATE NOT NULL,
    event_details TEXT NOT NULL,
    status VARCHAR(255) NOT NULL DEFAULT 'PENDING', -- Default status value
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- View all events
SELECT * FROM events;

-- Create the attendance table
CREATE TABLE IF NOT EXISTS attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(50) NOT NULL,
    faculty VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_student_date (student_id, faculty)
);

-- View all attendance records
SELECT * FROM attendance;

-- Queries for managing and viewing events and attendance

-- View attendance for a specific faculty
SELECT * FROM attendance WHERE faculty = 'FACET';

-- View events for a specific faculty
SELECT * FROM events WHERE faculty = 'FACET';

-- View events on a specific date (replace 'YYYY-MM-DD' with an actual date)
SELECT * FROM events WHERE event_date = 'YYYY-MM-DD';

-- Delete an event for a specific faculty (use with caution)
DELETE FROM events WHERE faculty = 'your_faculty';

-- View attendance for a specific student
SELECT * FROM attendance WHERE student_id = '123456';

-- Update the status of an event by its ID
UPDATE events SET status = 'APPROVED' WHERE id = your_event_id;

-- Count events grouped by their status
SELECT status, COUNT(*) AS Count 
FROM events 
GROUP BY status;

-- Additional queries for testing and data manipulation

-- Insert a sample event (replace with actual values)
INSERT INTO events (faculty, event_name, event_date, event_details, status) 
VALUES ('FACET', 'Sample Event', '2024-12-21', 'Event Details Here', 'PENDING');

-- Insert a sample attendance record (replace with actual values)
INSERT INTO attendance (student_id, faculty) 
VALUES ('123456', 'FACET');

-- View all pending events
SELECT * FROM events WHERE status = 'PENDING';

-- Reset the events table (drop and recreate it)
DROP TABLE IF EXISTS events;

CREATE TABLE events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    faculty VARCHAR(255) NOT NULL,
    event_name VARCHAR(255) NOT NULL,
    event_date DATE NOT NULL,
    event_details TEXT NOT NULL,
    status VARCHAR(255) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Verify the schema of events and attendance tables
DESCRIBE events;
DESCRIBE attendance;

-- Insert a test event
INSERT INTO events (faculty, event_name, event_date, event_details, status) 
VALUES ('FACET', 'Test Event', '2024-03-21', 'Test Details', 'PENDING');

-- Then verify the insertion
SELECT * FROM events;

-- First, backup existing data (optional but recommended)
CREATE TABLE events_backup AS SELECT * FROM events;

-- Drop and recreate the table with all required columns
DROP TABLE events;

CREATE TABLE events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    faculty VARCHAR(255) NOT NULL,
    event_name VARCHAR(255) NOT NULL,
    event_date DATE NOT NULL,
    event_details TEXT NOT NULL,
    status VARCHAR(255) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Verify the table structure
DESCRIBE events;

-- Insert a test record to verify all columns
INSERT INTO events (faculty, event_name, event_date, event_details, status) 
VALUES ('FACET', 'Test Event', '2024-03-21', 'Test Details', 'PENDING');

-- Verify the insertion with all columns
SELECT * FROM events;
