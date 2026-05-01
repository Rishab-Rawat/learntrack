package com.airtribe.learntrack.entity;


public class Enrollment {

    private int id;
    private int studentId;
    private int courseId;
    private String enrollmentDate;
    private EnrollmentStatus status;

    // Default constructor
    public Enrollment() {
        this.status = EnrollmentStatus.ACTIVE;
    }

    // Parameterized constructor
    public Enrollment(int id, int studentId, int courseId, String enrollmentDate) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
        this.status = EnrollmentStatus.ACTIVE;
    }

    // Getters
    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public int getCourseId() { return courseId; }
    public String getEnrollmentDate() { return enrollmentDate; }
    public EnrollmentStatus getStatus() { return status; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public void setEnrollmentDate(String enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("Enrollment ID: %-4d | Student ID: %-4d | Course ID: %-4d | Date: %-12s | Status: %s",
                id, studentId, courseId, enrollmentDate, status);
    }
}
