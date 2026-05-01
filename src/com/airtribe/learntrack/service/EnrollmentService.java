package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.EnrollmentStatus;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * EnrollmentService manages enrollments between students and courses.
 */
public class EnrollmentService {

    private ArrayList<Enrollment> enrollments = new ArrayList<>();

    // Dependencies — passed in so services stay decoupled
    private StudentService studentService;
    private CourseService courseService;

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    // ── Enroll ─────────────────────────────────────────────────────────────

    public Enrollment enrollStudent(int studentId, int courseId)
            throws EntityNotFoundException, InvalidInputException {

        Student student = studentService.findStudentById(studentId);
        if (!student.isActive()) {
            throw new InvalidInputException("Student ID " + studentId + " is inactive and cannot be enrolled.");
        }

        Course course = courseService.findCourseById(courseId);
        if (!course.isActive()) {
            throw new InvalidInputException("Course ID " + courseId + " is inactive and not open for enrollment.");
        }

        // Check for duplicate active enrollment
        for (Enrollment e : enrollments) {
            if (e.getStudentId() == studentId
                    && e.getCourseId() == courseId
                    && e.getStatus() == EnrollmentStatus.ACTIVE) {
                throw new InvalidInputException("Student is already actively enrolled in this course.");
            }
        }

        int id = IdGenerator.getNextEnrollmentId();
        String today = LocalDate.now().toString();
        Enrollment enrollment = new Enrollment(id, studentId, courseId, today);
        enrollments.add(enrollment);
        return enrollment;
    }

    // ── Read ───────────────────────────────────────────────────────────────

    public List<Enrollment> getAllEnrollments() {
        return enrollments;
    }

    public List<Enrollment> getEnrollmentsForStudent(int studentId) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getStudentId() == studentId) result.add(e);
        }
        return result;
    }

    public List<Enrollment> getEnrollmentsForCourse(int courseId) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getCourseId() == courseId) result.add(e);
        }
        return result;
    }

    public Enrollment findEnrollmentById(int id) throws EntityNotFoundException {
        for (Enrollment e : enrollments) {
            if (e.getId() == id) return e;
        }
        throw new EntityNotFoundException("Enrollment", id);
    }

    // ── Update status ──────────────────────────────────────────────────────

    public void markCompleted(int enrollmentId) throws EntityNotFoundException {
        findEnrollmentById(enrollmentId).setStatus(EnrollmentStatus.COMPLETED);
    }

    public void markCancelled(int enrollmentId) throws EntityNotFoundException {
        findEnrollmentById(enrollmentId).setStatus(EnrollmentStatus.CANCELLED);
    }

    public int getTotalCount() {
        return enrollments.size();
    }
}
