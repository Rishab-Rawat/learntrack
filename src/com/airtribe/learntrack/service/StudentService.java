package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentService manages all student-related operations.
 * In-memory storage uses ArrayList — chosen over arrays for dynamic sizing.
 */
public class StudentService {

    // ArrayList preferred over array: dynamic resizing, easy add/remove, no manual index management
    private ArrayList<Student> students = new ArrayList<>();

    // ── Add ────────────────────────────────────────────────────────────────

    /**
     * Adds a student with email (full overload).
     */
    public Student addStudent(String firstName, String lastName, String email, String batch)
            throws InvalidInputException {
        InputValidator.requireNonEmpty(firstName, "First name");
        InputValidator.requireNonEmpty(lastName, "Last name");
        InputValidator.requireNonEmpty(batch, "Batch");
        if (!email.isEmpty()) {
            InputValidator.requireValidEmail(email);
        }
        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, email, batch);
        students.add(student);
        return student;
    }

    /**
     * Adds a student without email (constructor overloading demo).
     */
    public Student addStudent(String firstName, String lastName, String batch)
            throws InvalidInputException {
        InputValidator.requireNonEmpty(firstName, "First name");
        InputValidator.requireNonEmpty(lastName, "Last name");
        InputValidator.requireNonEmpty(batch, "Batch");
        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, batch);
        students.add(student);
        return student;
    }

    // ── Read ───────────────────────────────────────────────────────────────

    public List<Student> getAllStudents() {
        return students;
    }

    public List<Student> getActiveStudents() {
        List<Student> active = new ArrayList<>();
        for (Student s : students) {
            if (s.isActive()) {
                active.add(s);
            }
        }
        return active;
    }

    public Student findStudentById(int id) throws EntityNotFoundException {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        throw new EntityNotFoundException("Student", id);
    }

    public List<Student> searchByName(String keyword) {
        List<Student> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Student s : students) {
            if (s.getFirstName().toLowerCase().contains(lowerKeyword)
                    || s.getLastName().toLowerCase().contains(lowerKeyword)) {
                results.add(s);
            }
        }
        return results;
    }

    // ── Update ─────────────────────────────────────────────────────────────

    public void updateStudentEmail(int id, String newEmail)
            throws EntityNotFoundException, InvalidInputException {
        InputValidator.requireValidEmail(newEmail);
        Student s = findStudentById(id);
        s.setEmail(newEmail);
    }

    public void updateStudentBatch(int id, String newBatch)
            throws EntityNotFoundException, InvalidInputException {
        InputValidator.requireNonEmpty(newBatch, "Batch");
        Student s = findStudentById(id);
        s.setBatch(newBatch);
    }

    // ── Deactivate ─────────────────────────────────────────────────────────

    /**
     * Soft-delete: sets active=false instead of removing from the list.
     * This preserves enrollment history.
     */
    public void deactivateStudent(int id) throws EntityNotFoundException {
        Student s = findStudentById(id);
        s.setActive(false);
    }

    public void reactivateStudent(int id) throws EntityNotFoundException {
        Student s = findStudentById(id);
        s.setActive(true);
    }

    public int getTotalCount() {
        return students.size();
    }
}
