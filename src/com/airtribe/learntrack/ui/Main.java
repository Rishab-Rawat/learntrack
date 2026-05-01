package com.airtribe.learntrack.ui;

import com.airtribe.learntrack.entity.*;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;

import java.util.List;
import java.util.Scanner;

/**
 * Main is the console entry point for LearnTrack.
 * Responsibility: display menus, read input, delegate to service classes.
 * No business logic lives here.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService =
            new EnrollmentService(studentService, courseService);

    public static void main(String[] args) {
        printBanner();
        seedSampleData();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> studentMenu();
                case 2 -> courseMenu();
                case 3 -> enrollmentMenu();
                case 4 -> printDashboard();
                case 0 -> {
                    System.out.println("\n  Goodbye! Happy learning.\n");
                    running = false;
                }
                default -> printError("Invalid option. Please choose 0-4.");
            }
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  STUDENT MENU
    // ═══════════════════════════════════════════════════════════

    private static void studentMenu() {
        boolean back = false;
        while (!back) {
            printHeader("Student Management");
            System.out.println("  1. Add new student");
            System.out.println("  2. View all students");
            System.out.println("  3. Search student by ID");
            System.out.println("  4. Search students by name");
            System.out.println("  5. Update student email");
            System.out.println("  6. Update student batch");
            System.out.println("  7. Deactivate student");
            System.out.println("  8. Reactivate student");
            System.out.println("  0. Back to main menu");
            printSeparator();

            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> listAllStudents();
                case 3 -> searchStudentById();
                case 4 -> searchStudentByName();
                case 5 -> updateStudentEmail();
                case 6 -> updateStudentBatch();
                case 7 -> deactivateStudent();
                case 8 -> reactivateStudent();
                case 0 -> back = true;
                default -> printError("Invalid option.");
            }
        }
    }

    private static void addStudent() {
        printHeader("Add New Student");
        try {
            String firstName = readString("First name: ");
            String lastName  = readString("Last name : ");
            String email     = readString("Email (leave blank to skip): ");
            String batch     = readString("Batch     : ");

            Student student;
            if (email.isBlank()) {
                student = studentService.addStudent(firstName, lastName, batch);
            } else {
                student = studentService.addStudent(firstName, lastName, email, batch);
            }
            printSuccess("Student added: " + student.getDisplayName() + " (ID: " + student.getId() + ")");
        } catch (InvalidInputException e) {
            printError(e.getMessage());
        }
    }

    private static void listAllStudents() {
        printHeader("All Students");
        List<Student> list = studentService.getAllStudents();
        if (list.isEmpty()) {
            System.out.println("  No students found.");
        } else {
            for (Student s : list) {
                System.out.println("  " + s);
            }
        }
        printSeparator();
    }

    private static void searchStudentById() {
        printHeader("Search Student by ID");
        int id = readInt("Enter student ID: ");
        try {
            Student s = studentService.findStudentById(id);
            System.out.println("  Found: " + s);
            // Also show enrollments
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsForStudent(id);
            if (!enrollments.isEmpty()) {
                System.out.println("  Enrollments:");
                for (Enrollment e : enrollments) {
                    System.out.println("    " + e);
                }
            }
        } catch (EntityNotFoundException e) {
            printError(e.getMessage());
        }
        printSeparator();
    }

    private static void searchStudentByName() {
        printHeader("Search Students by Name");
        String keyword = readString("Enter name keyword: ");
        List<Student> results = studentService.searchByName(keyword);
        if (results.isEmpty()) {
            System.out.println("  No students found matching '" + keyword + "'.");
        } else {
            for (Student s : results) {
                System.out.println("  " + s);
            }
        }
        printSeparator();
    }

    private static void updateStudentEmail() {
        printHeader("Update Student Email");
        int id = readInt("Enter student ID: ");
        String newEmail = readString("New email: ");
        try {
            studentService.updateStudentEmail(id, newEmail);
            printSuccess("Email updated.");
        } catch (EntityNotFoundException | InvalidInputException e) {
            printError(e.getMessage());
        }
    }

    private static void updateStudentBatch() {
        printHeader("Update Student Batch");
        int id = readInt("Enter student ID: ");
        String newBatch = readString("New batch: ");
        try {
            studentService.updateStudentBatch(id, newBatch);
            printSuccess("Batch updated.");
        } catch (EntityNotFoundException | InvalidInputException e) {
            printError(e.getMessage());
        }
    }

    private static void deactivateStudent() {
        printHeader("Deactivate Student");
        int id = readInt("Enter student ID: ");
        try {
            studentService.deactivateStudent(id);
            printSuccess("Student ID " + id + " has been deactivated.");
        } catch (EntityNotFoundException e) {
            printError(e.getMessage());
        }
    }

    private static void reactivateStudent() {
        printHeader("Reactivate Student");
        int id = readInt("Enter student ID: ");
        try {
            studentService.reactivateStudent(id);
            printSuccess("Student ID " + id + " has been reactivated.");
        } catch (EntityNotFoundException e) {
            printError(e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  COURSE MENU
    // ═══════════════════════════════════════════════════════════

    private static void courseMenu() {
        boolean back = false;
        while (!back) {
            printHeader("Course Management");
            System.out.println("  1. Add new course");
            System.out.println("  2. View all courses");
            System.out.println("  3. Search course by ID");
            System.out.println("  4. Search courses by name");
            System.out.println("  5. Activate course");
            System.out.println("  6. Deactivate course");
            System.out.println("  7. Update course duration");
            System.out.println("  0. Back to main menu");
            printSeparator();

            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> addCourse();
                case 2 -> listAllCourses();
                case 3 -> searchCourseById();
                case 4 -> searchCourseByName();
                case 5 -> activateCourse();
                case 6 -> deactivateCourse();
                case 7 -> updateCourseDuration();
                case 0 -> back = true;
                default -> printError("Invalid option.");
            }
        }
    }

    private static void addCourse() {
        printHeader("Add New Course");
        try {
            String name = readString("Course name  : ");
            String desc  = readString("Description  : ");
            int duration = readInt("Duration (weeks): ");
            Course course = courseService.addCourse(name, desc, duration);
            printSuccess("Course added: " + course.getCourseName() + " (ID: " + course.getId() + ")");
        } catch (InvalidInputException e) {
            printError(e.getMessage());
        }
    }

    private static void listAllCourses() {
        printHeader("All Courses");
        List<Course> list = courseService.getAllCourses();
        if (list.isEmpty()) {
            System.out.println("  No courses found.");
        } else {
            for (Course c : list) {
                System.out.println("  " + c);
            }
        }
        printSeparator();
    }

    private static void searchCourseById() {
        printHeader("Search Course by ID");
        int id = readInt("Enter course ID: ");
        try {
            Course c = courseService.findCourseById(id);
            System.out.println("  Found: " + c);
            List<Enrollment> enrolled = enrollmentService.getEnrollmentsForCourse(id);
            System.out.println("  Enrollments in this course: " + enrolled.size());
        } catch (EntityNotFoundException e) {
            printError(e.getMessage());
        }
        printSeparator();
    }

    private static void searchCourseByName() {
        printHeader("Search Courses by Name");
        String keyword = readString("Enter name keyword: ");
        List<Course> results = courseService.searchByName(keyword);
        if (results.isEmpty()) {
            System.out.println("  No courses found matching '" + keyword + "'.");
        } else {
            for (Course c : results) {
                System.out.println("  " + c);
            }
        }
        printSeparator();
    }

    private static void activateCourse() {
        int id = readInt("Enter course ID to activate: ");
        try {
            courseService.activateCourse(id);
            printSuccess("Course ID " + id + " activated.");
        } catch (EntityNotFoundException e) {
            printError(e.getMessage());
        }
    }

    private static void deactivateCourse() {
        int id = readInt("Enter course ID to deactivate: ");
        try {
            courseService.deactivateCourse(id);
            printSuccess("Course ID " + id + " deactivated.");
        } catch (EntityNotFoundException e) {
            printError(e.getMessage());
        }
    }

    private static void updateCourseDuration() {
        int id = readInt("Enter course ID: ");
        int newDuration = readInt("New duration (weeks): ");
        try {
            courseService.updateCourseDuration(id, newDuration);
            printSuccess("Duration updated.");
        } catch (EntityNotFoundException | InvalidInputException e) {
            printError(e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  ENROLLMENT MENU
    // ═══════════════════════════════════════════════════════════

    private static void enrollmentMenu() {
        boolean back = false;
        while (!back) {
            printHeader("Enrollment Management");
            System.out.println("  1. Enroll student in course");
            System.out.println("  2. View all enrollments");
            System.out.println("  3. View enrollments for a student");
            System.out.println("  4. View enrollments for a course");
            System.out.println("  5. Mark enrollment as completed");
            System.out.println("  6. Cancel enrollment");
            System.out.println("  0. Back to main menu");
            printSeparator();

            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> enrollStudent();
                case 2 -> listAllEnrollments();
                case 3 -> viewEnrollmentsForStudent();
                case 4 -> viewEnrollmentsForCourse();
                case 5 -> markCompleted();
                case 6 -> cancelEnrollment();
                case 0 -> back = true;
                default -> printError("Invalid option.");
            }
        }
    }

    private static void enrollStudent() {
        printHeader("Enroll Student");
        int studentId = readInt("Student ID: ");
        int courseId  = readInt("Course ID : ");
        try {
            Enrollment e = enrollmentService.enrollStudent(studentId, courseId);
            printSuccess("Enrolled! " + e);
        } catch (EntityNotFoundException | InvalidInputException e) {
            printError(e.getMessage());
        }
    }

    private static void listAllEnrollments() {
        printHeader("All Enrollments");
        List<Enrollment> list = enrollmentService.getAllEnrollments();
        if (list.isEmpty()) {
            System.out.println("  No enrollments found.");
        } else {
            for (Enrollment e : list) {
                System.out.println("  " + e);
            }
        }
        printSeparator();
    }

    private static void viewEnrollmentsForStudent() {
        int studentId = readInt("Student ID: ");
        try {
            studentService.findStudentById(studentId); // validates existence
            List<Enrollment> list = enrollmentService.getEnrollmentsForStudent(studentId);
            printHeader("Enrollments for Student ID " + studentId);
            if (list.isEmpty()) {
                System.out.println("  No enrollments found.");
            } else {
                for (Enrollment e : list) {
                    try {
                        Course c = courseService.findCourseById(e.getCourseId());
                        System.out.println("  " + e + " | Course: " + c.getCourseName());
                    } catch (EntityNotFoundException ex) {
                        System.out.println("  " + e);
                    }
                }
            }
            printSeparator();
        } catch (EntityNotFoundException e) {
            printError(e.getMessage());
        }
    }

    private static void viewEnrollmentsForCourse() {
        int courseId = readInt("Course ID: ");
        try {
            courseService.findCourseById(courseId); // validates existence
            List<Enrollment> list = enrollmentService.getEnrollmentsForCourse(courseId);
            printHeader("Enrollments for Course ID " + courseId);
            if (list.isEmpty()) {
                System.out.println("  No enrollments found.");
            } else {
                for (Enrollment e : list) {
                    try {
                        Student s = studentService.findStudentById(e.getStudentId());
                        System.out.println("  " + e + " | Student: " + s.getDisplayName());
                    } catch (EntityNotFoundException ex) {
                        System.out.println("  " + e);
                    }
                }
            }
            printSeparator();
        } catch (EntityNotFoundException e) {
            printError(e.getMessage());
        }
    }

    private static void markCompleted() {
        int enrollmentId = readInt("Enrollment ID: ");
        try {
            enrollmentService.markCompleted(enrollmentId);
            printSuccess("Enrollment ID " + enrollmentId + " marked as COMPLETED.");
        } catch (EntityNotFoundException e) {
            printError(e.getMessage());
        }
    }

    private static void cancelEnrollment() {
        int enrollmentId = readInt("Enrollment ID: ");
        try {
            enrollmentService.markCancelled(enrollmentId);
            printSuccess("Enrollment ID " + enrollmentId + " has been CANCELLED.");
        } catch (EntityNotFoundException e) {
            printError(e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  DASHBOARD
    // ═══════════════════════════════════════════════════════════

    private static void printDashboard() {
        printHeader("System Dashboard");
        System.out.println("  Total Students    : " + studentService.getTotalCount());
        System.out.println("  Active Students   : " + studentService.getActiveStudents().size());
        System.out.println("  Total Courses     : " + courseService.getTotalCount());
        System.out.println("  Active Courses    : " + courseService.getActiveCourses().size());
        System.out.println("  Total Enrollments : " + enrollmentService.getTotalCount());
        printSeparator();
    }

    // ═══════════════════════════════════════════════════════════
    //  SEED DATA (pre-loads sample records for testing)
    // ═══════════════════════════════════════════════════════════

    private static void seedSampleData() {
        try {
            studentService.addStudent("Aarav", "Sharma", "aarav@example.com", "Batch-2024-A");
            studentService.addStudent("Priya", "Patel", "priya@example.com", "Batch-2024-A");
            studentService.addStudent("Rohan", "Mehta", "Batch-2024-B");   // no email overload
            studentService.addStudent("Sneha", "Rao",   "sneha@example.com", "Batch-2024-B");

            courseService.addCourse("Core Java Fundamentals", "Variables, OOP, Collections", 8);
            courseService.addCourse("Spring Boot Basics",     "REST APIs with Spring",       10);
            courseService.addCourse("SQL & Databases",        "Relational DB design",         6);

            enrollmentService.enrollStudent(1, 1);
            enrollmentService.enrollStudent(1, 3);
            enrollmentService.enrollStudent(2, 1);
            enrollmentService.enrollStudent(3, 2);

        } catch (InvalidInputException | EntityNotFoundException e) {
            System.err.println("Seed data error: " + e.getMessage());
        }
        System.out.println("  Sample data loaded. (4 students, 3 courses, 4 enrollments)");
        printSeparator();
    }

    // ═══════════════════════════════════════════════════════════
    //  UI HELPERS
    // ═══════════════════════════════════════════════════════════

    private static void printBanner() {
        System.out.println();
        System.out.println("  ╔═══════════════════════════════════════╗");
        System.out.println("  ║         LearnTrack v1.0               ║");
        System.out.println("  ║   Student & Course Management System  ║");
        System.out.println("  ╚═══════════════════════════════════════╝");
        System.out.println();
    }

    private static void printMainMenu() {
        printHeader("Main Menu");
        System.out.println("  1. Student Management");
        System.out.println("  2. Course Management");
        System.out.println("  3. Enrollment Management");
        System.out.println("  4. Dashboard");
        System.out.println("  0. Exit");
        printSeparator();
    }

    private static void printHeader(String title) {
        System.out.println();
        System.out.println("  ── " + title + " ──");
    }

    private static void printSeparator() {
        System.out.println("  ────────────────────────────────────────");
    }

    private static void printSuccess(String msg) {
        System.out.println("  [OK] " + msg);
    }

    private static void printError(String msg) {
        System.out.println("  [ERROR] " + msg);
    }

    /**
     * Reads an integer safely — wraps NumberFormatException in a try-catch
     * so the program never crashes on bad input.
     */
    private static int readInt(String prompt) {
        while (true) {
            System.out.print("  " + prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                printError("'" + line + "' is not a valid number. Please try again.");
            }
        }
    }

    private static String readString(String prompt) {
        System.out.print("  " + prompt);
        return scanner.nextLine().trim();
    }
}
