package com.airtribe.learntrack.entity;


public class Trainer extends Person {

    private String specialization;

    // Default constructor
    public Trainer() {
        super();
    }

    // Parameterized constructor — calls super()
    public Trainer(int id, String firstName, String lastName, String email, String specialization) {
        super(id, firstName, lastName, email);
        this.specialization = specialization;
    }

    // Getter / Setter
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }


    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " (Trainer: " + specialization + ")";
    }

    @Override
    public String toString() {
        return String.format("ID: %-4d | %-25s | Specialization: %s | Email: %s",
                getId(), super.getDisplayName(), specialization, getEmail());
    }
}
