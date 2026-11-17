package com.auca.course;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import com.auca.department.Department;
import com.auca.teacher.Teacher;

public class Course implements Identifiable, Registrable {
    private String courseId;
    private String courseCode;
    private String courseTitle;
    private int creditHours;
    private Department department;
    private Teacher assignedTeacher;

    public Course(String courseId, String courseCode, String courseTitle, int creditHours, Department department, Teacher assignedTeacher) { this.courseId=courseId; this.courseCode=courseCode; this.courseTitle=courseTitle; this.creditHours=creditHours; this.department=department; this.assignedTeacher=assignedTeacher; }
    public String getId() { return courseId; }
    public String getDisplayName() { return String.valueOf(courseTitle); }
    public boolean isActive() { return true; }
    public void validate() throws ValidationException {
        if (courseId == null || courseId.isBlank()) throw new ValidationException("courseId is required.");
        if (courseCode == null || courseCode.isBlank()) throw new ValidationException("courseCode is required.");
        if (courseTitle == null || courseTitle.isBlank()) throw new ValidationException("courseTitle is required.");
        if (creditHours < 0) throw new ValidationException("creditHours cannot be negative.");
        if (department == null) throw new ValidationException("department is required.");
        if (assignedTeacher == null) throw new ValidationException("assignedTeacher is required.");
        if (creditHours < 1 || creditHours > 6 || department == null || assignedTeacher == null) throw new ValidationException("Course requires 1-6 credit hours, a department and a teacher.");
    }
    public void printDetails() { System.out.println(this); }
    public String getRegistrationSummary() { return "Course: " + getDisplayName() + " [" + getId() + "]"; }
    public String getCourseId() { return courseId; }
    public void setCourseId(String value) { this.courseId=value; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String value) { this.courseCode=value; }
    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String value) { this.courseTitle=value; }
    public int getCreditHours() { return creditHours; }
    public void setCreditHours(int value) { this.creditHours=value; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department value) { this.department=value; }
    public Teacher getAssignedTeacher() { return assignedTeacher; }
    public void setAssignedTeacher(Teacher value) { this.assignedTeacher=value; }
    public String toString() { return "Course{" + "courseId=" + courseId + ", " + "courseCode=" + courseCode + ", " + "courseTitle=" + courseTitle + ", " + "creditHours=" + creditHours + ", " + "department=" + department + ", " + "assignedTeacher=" + assignedTeacher + "}"; }
}
