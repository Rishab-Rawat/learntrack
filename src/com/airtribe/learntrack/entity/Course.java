package com.airtribe.learntrack.entity;


public class Course {

    private int id;
    private String courseName;
    private String description;
    private int durationInWeeks;
    private boolean active;

    // Default constructor
    public Course() {
        this.active = true;
    }

    // Parameterized constructor
    public Course(int id, String courseName, String description, int durationInWeeks) {
        this.id = id;
        this.courseName = courseName;
        this.description = description;
        this.durationInWeeks = durationInWeeks;
        this.active = true;
    }

    // Getters
    public int getId() { return id; }
    public String getCourseName() { return courseName; }
    public String getDescription() { return description; }
    public int getDurationInWeeks() { return durationInWeeks; }
    public boolean isActive() { return active; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setDescription(String description) { this.description = description; }
    public void setDurationInWeeks(int durationInWeeks) { this.durationInWeeks = durationInWeeks; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        String status = active ? "ACTIVE" : "INACTIVE";
        return String.format("ID: %-4d | %-30s | Duration: %-3d weeks | Status: %-8s | %s",
                id, courseName, durationInWeeks, status, description);
    }
}
