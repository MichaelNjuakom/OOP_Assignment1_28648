package com.auca.bookloan;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import com.auca.librarybook.LibraryBook;
import java.time.LocalDate;
import com.auca.student.Student;

public class BookLoan implements Identifiable, Registrable {
    private String loanId;
    private Student student;
    private LibraryBook book;
    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    public BookLoan(String loanId, Student student, LibraryBook book, LocalDate borrowedDate, LocalDate dueDate, LocalDate returnedDate) { this.loanId=loanId; this.student=student; this.book=book; this.borrowedDate=borrowedDate; this.dueDate=dueDate; this.returnedDate=returnedDate; }
    public String getId() { return loanId; }
    public String getDisplayName() { return String.valueOf(loanId); }
    public boolean isActive() { return true; }
    public void validate() throws ValidationException {
        if (loanId == null || loanId.isBlank()) throw new ValidationException("loanId is required.");
        if (student == null) throw new ValidationException("student is required.");
        if (book == null) throw new ValidationException("book is required.");
        if (borrowedDate == null) throw new ValidationException("borrowedDate is required.");
        if (dueDate == null) throw new ValidationException("dueDate is required.");
        if (dueDate.isBefore(borrowedDate) || (returnedDate != null && returnedDate.isBefore(borrowedDate))) throw new ValidationException("Invalid loan dates.");
    }
    public void printDetails() { System.out.println(this); }
    public String getRegistrationSummary() { return "BookLoan: " + getDisplayName() + " [" + getId() + "]"; }
    public String getLoanId() { return loanId; }
    public void setLoanId(String value) { this.loanId=value; }
    public Student getStudent() { return student; }
    public void setStudent(Student value) { this.student=value; }
    public LibraryBook getBook() { return book; }
    public void setBook(LibraryBook value) { this.book=value; }
    public LocalDate getBorrowedDate() { return borrowedDate; }
    public void setBorrowedDate(LocalDate value) { this.borrowedDate=value; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate value) { this.dueDate=value; }
    public LocalDate getReturnedDate() { return returnedDate; }
    public void setReturnedDate(LocalDate value) { this.returnedDate=value; }
    public String toString() { return "BookLoan{" + "loanId=" + loanId + ", " + "student=" + student + ", " + "book=" + book + ", " + "borrowedDate=" + borrowedDate + ", " + "dueDate=" + dueDate + ", " + "returnedDate=" + returnedDate + "}"; }
}
