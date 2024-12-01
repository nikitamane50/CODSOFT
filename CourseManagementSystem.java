package CodSoftTasks;

import java.util.ArrayList;
import java.util.Scanner;

class Course {
    String code;
    String title;
    String description;
    int capacity;
    int enrolled;
    String schedule;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolled = 0;
        this.schedule = schedule;
    }

    boolean hasAvailableSlot() {
        return enrolled < capacity;
    }

    void enrollStudent() {
        if (hasAvailableSlot()) {
            enrolled++;
        }
    }

    void removeStudent() {
        if (enrolled > 0) {
            enrolled--;
        }
    }

    @Override
    public String toString() {
        return "Course Code: " + code + "\nTitle: " + title + "\nDescription: " + description +
               "\nCapacity: " + enrolled + "/" + capacity + "\nSchedule: " + schedule + "\n";
    }
}

class Student {
    String id;
    String name;
    ArrayList<Course> registeredCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    void registerCourse(Course course) {
        if (course.hasAvailableSlot()) {
            registeredCourses.add(course);
            course.enrollStudent();
        } else {
            System.out.println("Course is full. Registration failed.");
        }
    }

    void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.removeStudent();
        } else {
            System.out.println("You are not registered for this course.");
        }
    }

    void displayRegisteredCourses() {
        if (registeredCourses.isEmpty()) {
            System.out.println("No courses registered.");
        } else {
            System.out.println("Registered Courses:");
            for (Course course : registeredCourses) {
                System.out.println(course.title + " (" + course.code + ")");
            }
        }
    }
}

public class CourseManagementSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();

        // Adding sample courses
        courses.add(new Course("CS101", "Intro to Programming", "Learn programming basics.", 30, "MWF 10-11AM"));
        courses.add(new Course("MTH201", "Calculus I", "Study differential and integral calculus.", 40, "TTh 9-10:30AM"));
        courses.add(new Course("PHY301", "Physics I", "Introduction to mechanics and thermodynamics.", 25, "MWF 1-2PM"));

        // Main menu
        while (true) {
            System.out.println("\nCourse Management System");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register a Student");
            System.out.println("3. Register for a Course");
            System.out.println("4. Drop a Course");
            System.out.println("5. View Student's Registered Courses");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nAvailable Courses:");
                    for (Course course : courses) {
                        System.out.println(course);
                    }
                    break;

                case 2:
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Enter Student Name: ");
                    String studentName = scanner.nextLine();
                    students.add(new Student(studentId, studentName));
                    System.out.println("Student registered successfully.");
                    break;

                case 3:
                    System.out.print("Enter Student ID: ");
                    studentId = scanner.nextLine();
                    Student student = findStudentById(students, studentId);

                    if (student != null) {
                        System.out.println("\nAvailable Courses:");
                        for (int i = 0; i < courses.size(); i++) {
                            System.out.println((i + 1) + ". " + courses.get(i).title + " (" + courses.get(i).code + ")");
                        }
                        System.out.print("Enter the number of the course to register: ");
                        int courseChoice = scanner.nextInt() - 1;

                        if (courseChoice >= 0 && courseChoice < courses.size()) {
                            student.registerCourse(courses.get(courseChoice));
                            System.out.println("Course registered successfully.");
                        } else {
                            System.out.println("Invalid course selection.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Student ID: ");
                    studentId = scanner.nextLine();
                    student = findStudentById(students, studentId);

                    if (student != null) {
                        System.out.println("\nRegistered Courses:");
                        for (int i = 0; i < student.registeredCourses.size(); i++) {
                            System.out.println((i + 1) + ". " + student.registeredCourses.get(i).title + " (" + student.registeredCourses.get(i).code + ")");
                        }
                        System.out.print("Enter the number of the course to drop: ");
                        int courseChoice = scanner.nextInt() - 1;

                        if (courseChoice >= 0 && courseChoice < student.registeredCourses.size()) {
                            student.dropCourse(student.registeredCourses.get(courseChoice));
                            System.out.println("Course dropped successfully.");
                        } else {
                            System.out.println("Invalid course selection.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 5:
                    System.out.print("Enter Student ID: ");
                    studentId = scanner.nextLine();
                    student = findStudentById(students, studentId);

                    if (student != null) {
                        student.displayRegisteredCourses();
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 6:
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static Student findStudentById(ArrayList<Student> students, String id) {
        for (Student student : students) {
            if (student.id.equals(id)) {
                return student;
            }
        }
        return null;
    }
}

