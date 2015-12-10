package hapimodule.core.entities;

import java.util.HashSet;
import java.util.Set;

public class PatientSource  implements java.io.Serializable {
    /*
    * Represents a patient referal source, i.e. the MFL Code
    * of the facility, and the internal clinic which made the
    * referral.
    */
     private String patientSourceName;
     private String MFL_Code;

    public String getMFL_Code() {
        return MFL_Code;
    }

    public void setMFL_Code(String MFL_Code) {
        this.MFL_Code = MFL_Code;
    }
     private Set persons = new HashSet(0);

    public PatientSource() {
    }

    public PatientSource(String patientSourceName, Set persons, String MFL_Code) {
       this.patientSourceName = patientSourceName;
       this.persons = persons;
       this.MFL_Code = MFL_Code;
    }
    
    public PatientSource(String patientSourceName, Set persons) {
       this.patientSourceName = patientSourceName;
       this.persons = persons;
    }

    public String getPatientSourceName() {
        return this.patientSourceName;
    }
    
    public void setPatientSourceName(String patientSourceName) {
        this.patientSourceName = patientSourceName;
    }
    public Set getPersons() {
        return this.persons;
    }
    
    public void setPersons(Set persons) {
        this.persons = persons;
    }




}


