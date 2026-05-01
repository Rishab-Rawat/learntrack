package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class CourseService {

    private ArrayList<Course> courses = new ArrayList<>();

    // ── Add ────────────────────────────────────────────────────────────────

    public Course addCourse(String courseName, String description, int durationInWeeks)
            throws InvalidInputException {
        InputValidator.requireNonEmpty(courseName, "Course name");
        InputValidator.requireNonEmpty(description, "Description");
        InputValidator.requirePositive(durationInWeeks, "Duration");

        int id = IdGenerator.getNextCourseId();
        Course course = new Course(id, courseName, description, durationInWeeks);
        courses.add(course);
        return course;
    }

    // ── Read ───────────────────────────────────────────────────────────────

    public List<Course> getAllCourses() {
        return courses;
    }

    public List<Course> getActiveCourses() {
        List<Course> active = new ArrayList<>();
        for (Course c : courses) {
            if (c.isActive()) active.add(c);
        }
        return active;
    }

    public Course findCourseById(int id) throws EntityNotFoundException {
        for (Course c : courses) {
            if (c.getId() == id) return c;
        }
        throw new EntityNotFoundException("Course", id);
    }

    public List<Course> searchByName(String keyword) {
        List<Course> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Course c : courses) {
            if (c.getCourseName().toLowerCase().contains(lowerKeyword)) {
                results.add(c);
            }
        }
        return results;
    }

    // ── Toggle active ──────────────────────────────────────────────────────

    public void activateCourse(int id) throws EntityNotFoundException {
        findCourseById(id).setActive(true);
    }

    public void deactivateCourse(int id) throws EntityNotFoundException {
        findCourseById(id).setActive(false);
    }

    // ── Update ─────────────────────────────────────────────────────────────

    public void updateCourseDuration(int id, int newDuration)
            throws EntityNotFoundException, InvalidInputException {
        InputValidator.requirePositive(newDuration, "Duration");
        findCourseById(id).setDurationInWeeks(newDuration);
    }

    public int getTotalCount() {
        return courses.size();
    }
}
