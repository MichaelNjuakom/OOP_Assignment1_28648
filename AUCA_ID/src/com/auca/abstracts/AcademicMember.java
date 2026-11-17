package com.auca.abstracts;

import com.auca.department.Department;
import java.time.LocalDate;

public abstract class AcademicMember extends Person {
    private String memberNumber;
    private Department department;
    private LocalDate joinDate;
    private String academicStatus;
    private String institutionalEmail;
    private String advisorName;

    public AcademicMember(String personId,String firstName,String lastName,String email,String phoneNumber,LocalDate dateOfBirth,
        String memberNumber,Department department,LocalDate joinDate,String academicStatus,String institutionalEmail,String advisorName) {
        super(personId,firstName,lastName,email,phoneNumber,dateOfBirth);
        this.memberNumber=memberNumber; this.department=department; this.joinDate=joinDate; this.academicStatus=academicStatus; this.institutionalEmail=institutionalEmail; this.advisorName=advisorName;
    }
    public abstract String getAcademicCategory();
    public String getMemberNumber(){
        return memberNumber;
    } 
    public void setMemberNumber(String v){
        memberNumber=v;
    }
    public Department getDepartment(){
        return department;
    } 
    public void setDepartment(Department v){
        department=v;
    }
    public LocalDate getJoinDate(){
        return joinDate;
    } public void setJoinDate(LocalDate v){joinDate=v;}
    public String getAcademicStatus(){
        return academicStatus;
    } 
    public void setAcademicStatus(String v){
        academicStatus=v;
    }
    public String getInstitutionalEmail(){return institutionalEmail;} public void setInstitutionalEmail(String v){institutionalEmail=v;}
    public String getAdvisorName(){return advisorName;} public void setAdvisorName(String v){advisorName=v;}
    public boolean isActive(){return "ACTIVE".equalsIgnoreCase(academicStatus);}
    public void validate() throws com.auca.exceptions.ValidationException {
        super.validate();
        if(memberNumber==null||memberNumber.isBlank()||department==null||joinDate==null||academicStatus==null||institutionalEmail==null||advisorName==null||advisorName.isBlank()) throw new com.auca.exceptions.ValidationException("All academic member fields are required.");
        if(!academicStatus.equalsIgnoreCase("ACTIVE")&&!academicStatus.equalsIgnoreCase("INACTIVE")) throw new com.auca.exceptions.ValidationException("Status must be ACTIVE or INACTIVE.");
        if(!institutionalEmail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) throw new com.auca.exceptions.ValidationException("Invalid institutional email.");
    }
    public String toString(){return super.toString()+", Member No: "+memberNumber+", Department: "+department.getDepartmentName()+", Join: "+joinDate+", Status: "+academicStatus+", Institutional email: "+institutionalEmail+", Advisor: "+advisorName;}
}
