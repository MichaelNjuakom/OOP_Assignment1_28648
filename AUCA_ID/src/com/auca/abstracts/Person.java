package com.auca.abstracts;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public abstract class Person implements Identifiable, Registrable {
    private String personId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;

    public Person(String personId, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth) {
        this.personId=personId; this.firstName=firstName; this.lastName=lastName; this.email=email; this.phoneNumber=phoneNumber; this.dateOfBirth=dateOfBirth;
    }
    public abstract String getRoleDescription();
    public String getId(){ return personId; }
    public String getDisplayName(){ return firstName + " " + lastName; }
    public boolean isActive(){ return true; }
    public void validate() throws ValidationException {
        if (blank(personId) || blank(firstName) || blank(lastName) || blank(email) || blank(phoneNumber) || dateOfBirth==null) throw new ValidationException("All person fields are required.");
        if(firstName.trim().length()<2 || firstName.trim().length()>60 || lastName.trim().length()<2 || lastName.trim().length()>60) throw new ValidationException("Names must contain 2-60 characters.");
        if(!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$") || email.length()>120) throw new ValidationException("Enter a valid email address.");
        String digits=phoneNumber.replaceAll("^\\+", "").replaceAll("\\D", "");
        if(!phoneNumber.matches("^\\+?[0-9]{10,15}$")) throw new ValidationException("Phone must contain 10-15 digits, optionally starting with +.");
        if(!dateOfBirth.isBefore(LocalDate.now())) throw new ValidationException("Date of birth must be in the past.");
    }
    protected boolean blank(String s){ return s==null || s.trim().isEmpty(); }
    public void printDetails(){ System.out.println(this); }
    public String getRegistrationSummary(){ return getRoleDescription()+": "+getDisplayName()+" ["+personId+"]"; }
    public String getPersonId(){return personId;} public void setPersonId(String v){personId=v;}
    public String getFirstName(){return firstName;} public void setFirstName(String v){firstName=v;}
    public String getLastName(){return lastName;} public void setLastName(String v){lastName=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPhoneNumber(){return phoneNumber;} public void setPhoneNumber(String v){phoneNumber=v;}
    public LocalDate getDateOfBirth(){return dateOfBirth;} public void setDateOfBirth(LocalDate v){dateOfBirth=v;}
    public String toString(){return "Person ID: "+personId+", Name: "+getDisplayName()+", Email: "+email+", Phone: "+phoneNumber+", DOB: "+dateOfBirth;}
}
