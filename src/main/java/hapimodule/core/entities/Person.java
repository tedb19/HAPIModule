package hapimodule.core.entities;

import hapimodule.core.constants.MaritalStatus;
import java.util.Date;
import java.util.Set;

public class Person implements java.io.Serializable {
    /*
    * Maps onto a person entity from the EMR
    */
     private MaritalStatus maritalStatusType;
     private PatientSource patientSource = null;
     private String firstName;
     private String middleName;
     private String lastName;
     private String sex;
     private Date birthdate;
     private Date deathdate;
     private Set<PersonIdentifier> personIdentifiers;
     private Date dateCreated;
     private Date dateModified;

    public Set<PersonIdentifier> getPersonIdentifiers() {
        return personIdentifiers;
    }

    public void setPersonIdentifiers(Set<PersonIdentifier> personIdentifiers) {
        this.personIdentifiers = personIdentifiers;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Person() {
    }

	
    public Person(MaritalStatus maritalStatusType, PatientSource patientSource) {
        this.maritalStatusType = maritalStatusType;
        this.patientSource = patientSource;
    }
    public Person(MaritalStatus maritalStatusType, PatientSource patientSource, String firstName,
            String middleName, String lastName, String sex, Date birthdate, Date deathdate, Set personIdentifiers) {
       this.maritalStatusType = maritalStatusType;
       this.patientSource = patientSource;
       this.firstName = firstName;
       this.middleName = middleName;
       this.lastName = lastName;
       this.sex = sex;
       this.birthdate = birthdate;
       this.deathdate = deathdate;
       this.personIdentifiers = personIdentifiers;
    }
   
    public MaritalStatus getMaritalStatusType() {
        return this.maritalStatusType;
    }
    
    public void setMaritalStatusType(MaritalStatus maritalStatusType) {
        this.maritalStatusType = maritalStatusType;
    }
    public PatientSource getPatientSource() {
        return this.patientSource;
    }
    
    public void setPatientSource(PatientSource patientSource) {
        this.patientSource = patientSource;
    }

    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getMiddleName() {
        return this.middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getSex() {
        return this.sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    public Date getBirthdate() {
        return this.birthdate;
    }
    
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    public Date getDeathdate() {
        return this.deathdate;
    }
    
    public void setDeathdate(Date deathdate) {
        this.deathdate = deathdate;
    }

}


