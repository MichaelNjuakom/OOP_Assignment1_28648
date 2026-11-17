package com.auca.main;

import com.auca.abstracts.Person;
import com.auca.administrator.Administrator;
import com.auca.assessment.Assessment;
import com.auca.bookloan.BookLoan;
import com.auca.classroom.Classroom;
import com.auca.course.Course;
import com.auca.department.Department;
import com.auca.enrollment.Enrollment;
import com.auca.exceptions.*;
import com.auca.feepayment.FeePayment;
import com.auca.interfaces.*;
import com.auca.librarybook.LibraryBook;
import com.auca.student.Student;
import com.auca.teacher.Teacher;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final List<Department> departments = new ArrayList<>();
    private static final List<Student> students = new ArrayList<>();
    private static final List<Teacher> teachers = new ArrayList<>();
    private static final List<Administrator> administrators = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();
    private static final List<Classroom> classrooms = new ArrayList<>();
    private static final List<Enrollment> enrollments = new ArrayList<>();
    private static final List<Assessment> assessments = new ArrayList<>();
    private static final List<FeePayment> payments = new ArrayList<>();
    private static final List<LibraryBook> books = new ArrayList<>();
    private static final List<BookLoan> loans = new ArrayList<>();

    public static void main(String[] args) {
        int choice;
        do {
            menu();
            choice = readInt("Select an option: ", 0, 19);
            try {
                switch (choice) {
                    case 1 -> registerDepartment();
                    case 2 -> registerStudent();
                    case 3 -> registerTeacher();
                    case 4 -> registerAdministrator();
                    case 5 -> registerCourse();
                    case 6 -> registerClassroom();
                    case 7 -> enrollStudent();
                    case 8 -> recordAssessment();
                    case 9 -> recordPayment();
                    case 10 -> registerBook();
                    case 11 -> borrowBook();
                    case 12 -> returnBook();
                    case 13 -> viewAll();
                    case 14 -> searchById();
                    case 15 -> updateRecord();
                    case 16 -> academicSummary();
                    case 17 -> paymentHistory();
                    case 18 -> availableBooks();
                    case 19 -> demonstratePolymorphism();
                    case 0 -> System.out.println("Goodbye. AUCA_ID closed.");
                }
            } catch (ValidationException | DuplicateRecordException | RecordNotFoundException
                    | PrerequisiteMissingException | BookUnavailableException ex) {
                System.out.println("Operation not completed: " + ex.getMessage());
            } catch (RuntimeException ex) {
                System.out.println("Input or operation error: " + ex.getMessage());
            }
            if (choice != 0)
                pause();
        } while (choice != 0);
    }

    private static void menu() {
        System.out.println("\n============== AUCA_ID SCHOOL MANAGEMENT ==============");
        System.out.println(" 1. Register Department       11. Borrow Library Book");
        System.out.println(" 2. Register Student          12. Return Library Book");
        System.out.println(" 3. Register Teacher          13. View All Records");
        System.out.println(" 4. Register Administrator    14. Search Record by ID");
        System.out.println(" 5. Register Course           15. Update Existing Record");
        System.out.println(" 6. Register Classroom        16. Student Academic Summary");
        System.out.println(" 7. Enroll Student            17. Student Payment History");
        System.out.println(" 8. Record Assessment         18. View Available Books");
        System.out.println(" 9. Record Fee Payment        19. Demonstrate Polymorphism");
        System.out.println("10. Register Library Book      0. Exit");
    }

    private static String read(String label) {
        System.out.print(label);
        return sc.nextLine().trim();
    }

    private static String required(String label) {
        while (true) {
            String s = read(label);
            if (!s.isBlank())
                return s;
            System.out.println("This field is required.");
        }
    }

    private static int readInt(String label, int min, int max) {
        while (true) {
            try {
                int n = Integer.parseInt(read(label));
                if (n >= min && n <= max)
                    return n;
                System.out.println("Enter a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String label, double min, double max) {
        while (true) {
            try {
                double n = Double.parseDouble(read(label));
                if (n >= min && n <= max)
                    return n;
                System.out.println("Enter a value from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    private static BigDecimal readMoney(String label) {
        while (true) {
            try {
                BigDecimal n = new BigDecimal(read(label));
                if (n.compareTo(BigDecimal.ZERO) > 0)
                    return n;
                System.out.println("Amount must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid amount, e.g. 25000.00");
            }
        }
    }

    private static LocalDate readDate(String label) {
        while (true) {
            try {
                return LocalDate.parse(required(label));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Use YYYY-MM-DD, e.g. 2004-05-16.");
            }
        }
    }

    private static boolean yesNo(String label) {
        return readInt(label + " 1 Yes / 2 No: ", 1, 2) == 1;
    }

    private static void pause() {
        read("Press ENTER to return to the main menu...");
    }

    private static void unique(String id, List<? extends Identifiable> list) throws DuplicateRecordException {
        for (Identifiable x : list)
            if (x.getId().equalsIgnoreCase(id))
                throw new DuplicateRecordException("ID already exists: " + id);
    }

    private static Department chooseDepartment() throws PrerequisiteMissingException {
        if (departments.isEmpty())
            throw new PrerequisiteMissingException(
                    "No Department exists. Register a Department first (menu option 1).");
        return choose("Select Department", departments);
    }

    private static Student chooseStudent() throws PrerequisiteMissingException {
        if (students.isEmpty())
            throw new PrerequisiteMissingException("No Student exists. Register a Student first (menu option 2).");
        return choose("Select Student", students);
    }

    private static Teacher chooseTeacher() throws PrerequisiteMissingException {
        if (teachers.isEmpty())
            throw new PrerequisiteMissingException("No Teacher exists. Register a Teacher first (menu option 3).");
        return choose("Select Teacher", teachers);
    }

    private static Course chooseCourse() throws PrerequisiteMissingException {
        if (courses.isEmpty())
            throw new PrerequisiteMissingException("No Course exists. Register a Course first (menu option 5).");
        return choose("Select Course", courses);
    }

    private static <T extends Identifiable & Registrable> T choose(String title, List<T> list) {
        System.out.println("\n--- " + title + " ---");
        for (int i = 0; i < list.size(); i++)
            System.out.println((i + 1) + ". " + list.get(i).getRegistrationSummary());
        System.out.println("0. Cancel");
        int n = readInt("Select: ", 0, list.size());
        if (n == 0)
            return null;
        return list.get(n - 1);
    }

    private static void confirm(String summary) throws ValidationException {
        System.out.println("\nReview entered data:\n" + summary);
        int c = readInt("1 Save  0 Cancel: ", 0, 1);
        if (c == 0)
            throw new ValidationException("Registration cancelled; no record was saved.");
    }

    private static void registerDepartment() throws ValidationException, DuplicateRecordException {
        System.out.println("\n--- REGISTER DEPARTMENT ---");
        String id = required("Department ID: ");
        unique(id, departments);
        String name = required("Department name: ");
        String code = required("Department code: ");
        String office = required("Office location: ");
        String email = required("Contact email: ");
        Department d = new Department(id, name, code, office, email, null);
        d.validate();
        confirm(d.toString());
        departments.add(d);
        System.out.println("Department saved. You may assign a head teacher later.");
    }

    private static void registerStudent()
            throws ValidationException, DuplicateRecordException, PrerequisiteMissingException {
        System.out.println("\n--- REGISTER STUDENT (PERSON + ACADEMIC MEMBER + STUDENT) ---");
        String pid = required("01 Person ID: ");
        unique(pid, allPeople());
        String fn = required("02 First name: ");
        String ln = required("03 Last name: ");
        String email = required("04 Personal email: ");
        String phone = required("05 Phone (10-15 digits): ");
        LocalDate dob = readDate("06 Date of birth (YYYY-MM-DD): ");
        String member = required("07 Member number: ");
        Department dep = chooseDepartment();
        if (dep == null)
            return;
        LocalDate join = readDate("09 Join date: ");
        String status = readInt("10 Academic status 1 ACTIVE / 2 INACTIVE: ", 1, 2) == 1 ? "ACTIVE" : "INACTIVE";
        String inst = required("11 Institutional email: ");
        String advisor = required("12 Advisor name: ");
        String sn = required("13 Student number: ");
        unique(sn, students);
        String program = required("14 Program name: ");
        LocalDate admission = readDate("15 Admission date: ");
        int year = readInt("16 Study year (1-6): ", 1, 6);
        String guardian = required("17 Guardian phone: ");
        boolean scholarship = yesNo("18 Scholarship?");
        Student s = new Student(pid, fn, ln, email, phone, dob, member, dep, join, status, inst, advisor, sn, program,
                admission, year, guardian, scholarship);
        s.validate();
        confirm(s.toString());
        students.add(s);
        System.out.println("Student registered successfully: " + sn);
    }

    private static void registerTeacher()
            throws ValidationException, DuplicateRecordException, PrerequisiteMissingException {
        System.out.println("\n--- REGISTER TEACHER ---");
        String pid = required("Person ID: ");
        unique(pid, allPeople());
        String fn = required("First name: "), ln = required("Last name: "), email = required("Email: "),
                phone = required("Phone: ");
        LocalDate dob = readDate("Date of birth: ");
        String mem = required("Member number: ");
        Department dep = chooseDepartment();
        if (dep == null)
            return;
        LocalDate join = readDate("Join date: ");
        String status = readInt("Academic status 1 ACTIVE / 2 INACTIVE: ", 1, 2) == 1 ? "ACTIVE" : "INACTIVE";
        String inst = required("Institutional email: "), advisor = required("Advisor name: ");
        String emp = required("Employee number: ");
        unique(emp, teachers);
        String spec = required("Specialization: ");
        LocalDate hire = readDate("Hire date: ");
        BigDecimal salary = readMoney("Salary: ");
        String office = required("Office number: "), type = required("Employment type (FULL_TIME/PART_TIME): ");
        Teacher t = new Teacher(pid, fn, ln, email, phone, dob, mem, dep, join, status, inst, advisor, emp, spec, hire,
                salary, office, type);
        t.validate();
        confirm(t.toString());
        teachers.add(t);
        System.out.println("Teacher registered: " + emp);
    }

    private static void registerAdministrator() throws ValidationException, DuplicateRecordException {
        System.out.println("\n--- REGISTER ADMINISTRATOR ---");
        String pid = required("Person ID: ");
        unique(pid, allPeople());
        String fn = required("First name: "), ln = required("Last name: "), email = required("Email: "),
                phone = required("Phone: ");
        LocalDate dob = readDate("Date of birth: ");
        String staff = required("Staff number: ");
        unique(staff, administrators);
        String title = required("Job title: "), office = required("Assigned office: ");
        LocalDate date = readDate("Employment date: ");
        int level = readInt("Access level (1-5): ", 1, 5);
        boolean active = yesNo("Active?");
        Administrator a = new Administrator(pid, fn, ln, email, phone, dob, staff, title, office, date, level, active);
        a.validate();
        confirm(a.toString());
        administrators.add(a);
        System.out.println("Administrator saved.");
    }

    private static void registerCourse()
            throws ValidationException, DuplicateRecordException, PrerequisiteMissingException {
        System.out.println("\n--- REGISTER COURSE ---");
        String id = required("Course ID: ");
        unique(id, courses);
        String code = required("Course code: ");
        String title = required("Course title: ");
        int credits = readInt("Credit hours (1-6): ", 1, 6);
        Department dep = chooseDepartment();
        if (dep == null)
            return;
        Teacher teacher = chooseTeacher();
        if (teacher == null)
            return;
        Course c = new Course(id, code, title, credits, dep, teacher);
        c.validate();
        confirm(c.toString());
        courses.add(c);
        System.out.println("Course saved.");
    }

    private static void registerClassroom() throws ValidationException, DuplicateRecordException {
        System.out.println("\n--- REGISTER CLASSROOM ---");
        String id = required("Classroom ID: ");
        unique(id, classrooms);
        String building = required("Building name: "), room = required("Room number: ");
        int capacity = readInt("Capacity: ", 1, 10000), floor = readInt("Floor number: ", 0, 200);
        boolean available = yesNo("Available?");
        Classroom c = new Classroom(id, building, room, capacity, floor, available);
        c.validate();
        confirm(c.toString());
        classrooms.add(c);
        System.out.println("Classroom saved.");
    }

    private static void enrollStudent()
            throws ValidationException, DuplicateRecordException, PrerequisiteMissingException {
        System.out.println("\n--- ENROLL STUDENT ---");
        Student s = chooseStudent();
        if (s == null)
            return;
        Course c = chooseCourse();
        if (c == null)
            return;
        String sem = required("Semester (e.g. SEMESTER_1): ");
        for (Enrollment e : enrollments)
            if (e.getStudent() == s && e.getCourse() == c && e.getSemester().equalsIgnoreCase(sem)
                    && e.getEnrollmentStatus().equalsIgnoreCase("ACTIVE"))
                throw new DuplicateRecordException(
                        "Student already has an active enrollment in this course and semester.");
        String id = required("Enrollment ID: ");
        unique(id, enrollments);
        LocalDate date = readDate("Enrollment date: ");
        Enrollment e = new Enrollment(id, s, c, date, sem, "ACTIVE");
        e.validate();
        confirm(e.toString());
        enrollments.add(e);
        System.out.println("Enrollment saved.");
    }

    private static void recordAssessment()
            throws ValidationException, DuplicateRecordException, PrerequisiteMissingException {
        if (enrollments.isEmpty())
            throw new PrerequisiteMissingException("No enrollment exists. Enroll a student first.");
        Enrollment e = choose("Select active enrollment", enrollments);
        if (e == null)
            return;
        if (!e.getEnrollmentStatus().equalsIgnoreCase("ACTIVE"))
            throw new ValidationException("Only active enrollments can be assessed.");
        String id = required("Assessment ID: ");
        unique(id, assessments);
        String type = required("Assessment type (QUIZ/TEST/ASSIGNMENT/EXAM): ");
        double max = readDouble("Maximum score: ", 0.01, 100000);
        double score = readDouble("Obtained score: ", 0, max);
        LocalDate date = readDate("Assessment date: ");
        Assessment a = new Assessment(id, e, type, max, score, date);
        a.validate();
        confirm(a.toString());
        assessments.add(a);
        System.out.println("Assessment recorded.");
    }

    private static void recordPayment()
            throws ValidationException, DuplicateRecordException, PrerequisiteMissingException {
        Student s = chooseStudent();
        if (s == null)
            return;
        String id = required("Payment ID: ");
        unique(id, payments);
        BigDecimal amount = readMoney("Amount: ");
        LocalDate date = readDate("Payment date: ");
        String method = required("Payment method (CASH/BANK/MOBILE_MONEY/CARD): ");
        String receipt = required("Receipt number: ");
        for (FeePayment p : payments)
            if (p.getReceiptNumber().equalsIgnoreCase(receipt))
                throw new DuplicateRecordException("Receipt number already exists.");
        FeePayment p = new FeePayment(id, s, amount, date, method, receipt);
        p.validate();
        confirm(p.toString());
        payments.add(p);
        System.out.println("Payment recorded.");
    }

    private static void registerBook() throws ValidationException, DuplicateRecordException {
        System.out.println("\n--- REGISTER LIBRARY BOOK ---");
        String id = required("Book ID: ");
        unique(id, books);
        String isbn = required("ISBN: "), title = required("Title: "), author = required("Author: ");
        int total = readInt("Total copies: ", 0, 100000);
        int available = readInt("Available copies: ", 0, total);
        LibraryBook b = new LibraryBook(id, isbn, title, author, total, available);
        b.validate();
        confirm(b.toString());
        books.add(b);
        System.out.println("Book saved.");
    }

    private static void borrowBook() throws ValidationException, DuplicateRecordException, PrerequisiteMissingException,
            BookUnavailableException {
        Student s = chooseStudent();
        if (s == null)
            return;
        if (books.isEmpty())
            throw new PrerequisiteMissingException("No library book exists. Register a book first.");
        LibraryBook b = choose("Select book", books);
        if (b == null)
            return;
        if (b.getAvailableCopies() < 1)
            throw new BookUnavailableException("No copies of this book are currently available.");
        String id = required("Loan ID: ");
        unique(id, loans);
        LocalDate borrowed = readDate("Borrowed date: ");
        LocalDate due = readDate("Due date: ");
        BookLoan loan = new BookLoan(id, s, b, borrowed, due, null);
        loan.validate();
        confirm(loan.toString());
        b.setAvailableCopies(b.getAvailableCopies() - 1);
        loans.add(loan);
        System.out.println("Book borrowed. Available copies: " + b.getAvailableCopies());
    }

    private static void returnBook() throws ValidationException, RecordNotFoundException {
        List<BookLoan> open = new ArrayList<>();
        for (BookLoan l : loans)
            if (l.getReturnedDate() == null)
                open.add(l);
        if (open.isEmpty())
            throw new RecordNotFoundException("There are no active book loans.");
        BookLoan l = choose("Select loan to return", open);
        if (l == null)
            return;
        LocalDate returned = readDate("Return date: ");
        if (returned.isBefore(l.getBorrowedDate()))
            throw new ValidationException("Return date cannot be before borrowed date.");
        if (!read("Confirm return? Type YES: ").equalsIgnoreCase("YES"))
            return;
        l.setReturnedDate(returned);
        l.getBook().setAvailableCopies(l.getBook().getAvailableCopies() + 1);
        System.out.println("Book returned. Available copies: " + l.getBook().getAvailableCopies());
    }

    private static List<Person> allPeople() {
        List<Person> p = new ArrayList<>();
        p.addAll(students);
        p.addAll(teachers);
        p.addAll(administrators);
        return p;
    }

    private static List<Identifiable> allRecords() {
        List<Identifiable> r = new ArrayList<>();
        r.addAll(departments);
        r.addAll(students);
        r.addAll(teachers);
        r.addAll(administrators);
        r.addAll(courses);
        r.addAll(classrooms);
        r.addAll(enrollments);
        r.addAll(assessments);
        r.addAll(payments);
        r.addAll(books);
        r.addAll(loans);
        return r;
    }

    private static void viewAll() {
        System.out.println("\n--- ALL RECORDS ---");
        for (Identifiable r : allRecords())
            System.out.println(
                    r instanceof Registrable ? ((Registrable) r).getRegistrationSummary() : r.getDisplayName());
        if (allRecords().isEmpty())
            System.out.println("No records have been registered.");
    }

    private static void searchById() throws RecordNotFoundException {
        String id = required("Enter record ID: ");
        for (Identifiable r : allRecords())
            if (r.getId().equalsIgnoreCase(id)) {
                if (r instanceof Registrable)
                    ((Registrable) r).printDetails();
                return;
            }
        throw new RecordNotFoundException("No record found with ID " + id);
    }

    private static void updateRecord() throws RecordNotFoundException {
        String id = required("Enter record ID to update: ");
        for (Student s : students)
            if (s.getId().equalsIgnoreCase(id)) {
                System.out.println(s);
                s.setProgramName(required("New program name: "));
                System.out.println("Student updated.");
                return;
            }
        for (Classroom c : classrooms)
            if (c.getId().equalsIgnoreCase(id)) {
                System.out.println(c);
                c.setAvailable(yesNo("Set available?"));
                System.out.println("Classroom updated.");
                return;
            }
        throw new RecordNotFoundException("No editable Student or Classroom found with ID " + id
                + ". This starter implementation exposes safe updates for these record types.");
    }

    private static void academicSummary() throws PrerequisiteMissingException {
        Student s = chooseStudent();
        if (s == null)
            return;
        System.out.println("\nACADEMIC SUMMARY: " + s.getDisplayName());
        System.out.println("Program: " + s.getProgramName() + " | Year: " + s.getStudyYear() + " | Department: "
                + s.getDepartment().getDepartmentName());
        for (Enrollment e : enrollments)
            if (e.getStudent() == s) {
                System.out.println("Course: " + e.getCourse().getCourseTitle() + " / " + e.getSemester());
                for (Assessment a : assessments)
                    if (a.getEnrollment() == e)
                        System.out.println(
                                "  " + a.getAssessmentType() + ": " + a.getObtainedScore() + "/" + a.getMaximumScore());
            }
    }

    private static void paymentHistory() throws PrerequisiteMissingException {
        Student s = chooseStudent();
        if (s == null)
            return;
        System.out.println("\nPAYMENT HISTORY: " + s.getDisplayName());
        boolean found = false;
        for (FeePayment p : payments)
            if (p.getStudent() == s) {
                System.out.println(p);
                found = true;
            }
        if (!found)
            System.out.println("No payments found.");
    }

    private static void availableBooks() {
        System.out.println("\nAVAILABLE LIBRARY BOOKS");
        boolean found = false;
        for (LibraryBook b : books)
            if (b.getAvailableCopies() > 0) {
                System.out.println(b);
                found = true;
            }
        if (!found)
            System.out.println("No books currently available.");
    }

    private static void demonstratePolymorphism() {
        System.out.println("\n--- SUPERCLASS POLYMORPHISM: List<Person> ---");
        List<Person> people = allPeople();
        for (Person p : people)
            System.out
                    .println(p.getRoleDescription() + " -> " + p.getDisplayName() + " | " + p.getRegistrationSummary());
        System.out.println("\n--- INTERFACE POLYMORPHISM: List<Identifiable> ---");
        for (Identifiable item : allRecords())
            System.out.println(item.getId() + " | " + item.getDisplayName() + " | active=" + item.isActive());
        if (people.isEmpty() && allRecords().isEmpty())
            System.out.println("Register records first to see polymorphism in action.");
    }
}
