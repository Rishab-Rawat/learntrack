package com.airtribe.learntrack.entity;

/**
 * Person is the base class for all people in the system.
 * Student and Trainer extend this class.
 */
public abstract class Person {

    private int id;
    private String firstName;
    private String lastName;
    private String email;

    // Default constructor
    public Person() {}

    // Parameterized constructor
    public Person(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Getters
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }

    /**
     * Returns a display name. Subclasses can override for specialized behavior.
     */
    public String getDisplayName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "ID=" + id + " | " + getDisplayName() + " | Email: " + email;
    }
}
