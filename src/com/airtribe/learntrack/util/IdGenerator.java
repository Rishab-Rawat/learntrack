package com.airtribe.learntrack.util;

/**
 * IdGenerator provides auto-incrementing IDs for each entity type.
 * Uses static fields and methods — a class-level concern, not instance-level.
 */
public class IdGenerator {

    // Static counters — shared across all instances (there won't be instances anyway)
    private static int studentIdCounter = 0;
    private static int courseIdCounter = 0;
    private static int enrollmentIdCounter = 0;
    private static int trainerIdCounter = 0;

    // Private constructor: this class should never be instantiated
    private IdGenerator() {}

    public static int getNextStudentId() {
        return ++studentIdCounter;
    }

    public static int getNextCourseId() {
        return ++courseIdCounter;
    }

    public static int getNextEnrollmentId() {
        return ++enrollmentIdCounter;
    }

    public static int getNextTrainerId() {
        return ++trainerIdCounter;
    }
}
