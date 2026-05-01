package com.airtribe.learntrack.entity;


public class Student extends Person {

    private String batch;
    private boolean active;

    // Default constructor
    public Student() {
        super();
        this.active = true;
    }

    // Constructor overload 1: without email
    public Student(int id, String firstName, String lastName, String batch) {
        super(id, firstName, lastName, "");
        this.batch = batch;
        this.active = true;
    }

    // Constructor overload 2: with email
    public Student(int id, String firstName, String lastName, String email, String batch) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = true;
    }

    // Getters
    public String getBatch() { return batch; }
    public boolean isActive() { return active; }

    // Setters
    public void setBatch(String batch) { this.batch = batch; }
    public void setActive(boolean active) { this.active = active; }

    /**
     * Overrides Person.getDisplayName() to include batch info.
     */
    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " [Batch: " + batch + "]";
    }

    @Override
    public String toString() {
        String status = active ? "ACTIVE" : "INACTIVE";
        String email = getEmail().isEmpty() ? "N/A" : getEmail();
        return String.format("ID: %-4d | %-25s | Email: %-30s | Batch: %-10s | Status: %s",
                getId(), super.getDisplayName(), email, batch, status);
    }
}
