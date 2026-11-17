AUCA_ID - SCHOOL MANAGEMENT SYSTEM (JAVA CONSOLE PROJECT)

REQUIREMENTS
- JDK 17 or newer (uses switch expressions / arrow switch labels).
- No external libraries are required.

PROJECT STRUCTURE
src/com/auca/interfaces       Identifiable, Registrable
src/com/auca/abstracts        Person, AcademicMember
src/com/auca/student         Student
src/com/auca/teacher         Teacher
src/com/auca/administrator   Administrator
src/com/auca/department      Department
src/com/auca/course          Course
src/com/auca/classroom       Classroom
src/com/auca/enrollment      Enrollment
src/com/auca/assessment      Assessment
src/com/auca/feepayment      FeePayment
src/com/auca/librarybook     LibraryBook
src/com/auca/bookloan        BookLoan
src/com/auca/exceptions      Five custom exceptions
src/com/auca/main            Main

COMPILE (Windows CMD)
1. Open CMD in the AUCA_ID folder.
2. Run:
   if not exist out mkdir out
   dir /s /b src\*.java > sources.txt
   javac -encoding UTF-8 -d out @sources.txt
3. Run:
   java -cp out com.auca.main.Main

COMPILE (VS CODE)
Open the AUCA_ID folder (the folder containing src) in VS Code. Ensure a JDK is installed, then run Main.java.

IMPORTANT IMPLEMENTATION NOTES
- The project is an in-memory console application; records disappear when the program exits, as the assignment permits.
- One shared Scanner is used.
- The code includes all prescribed domain types and six declared attributes per type, the prescribed package structure, inheritance, interfaces, custom exceptions, object references, collections and a working menu flow.
- This is a runnable teaching baseline, not yet a 100%-complete implementation of every advanced smart-form requirement in the handout. In particular, nested prerequisite registration/resume, the full four-step review/edit experience for every entity, and complete editing for every record type still need to be extended before claiming full compliance. The update menu currently supports Student program and Classroom availability edits.
- Department head teacher is intentionally optional at creation, and BookLoan.returnedDate is intentionally null until return.
- Before submission, test each menu operation and be prepared to explain every class and method. Do not submit code you cannot explain.
