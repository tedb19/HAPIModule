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
     private String villageName;
     private String locationName;
     private String subLocationName;
     private String landMark;
     private String countyName;
     private String districtName;
     private String provinceName;
     private String division;
     private String patientSerial;
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
            String middleName, String lastName, String sex, Date birthdate, Date deathdate,
            String villageName, String locationName, String subLocationName, String landMark, String countyName,
            String districtName, String provinceName, String division, String patientSerial, Set<PersonIdentifier> personIdentifiers) {
       this.maritalStatusType = maritalStatusType;
       this.patientSource = patientSource;
       this.firstName = firstName;
       this.middleName = middleName;
       this.lastName = lastName;
       this.sex = sex;
       this.birthdate = birthdate;
       this.deathdate = deathdate;
       this.countyName = countyName;
       this.districtName = districtName;
       this.division = division;
       this.locationName = locationName;
       this.provinceName = provinceName;
       this.subLocationName = subLocationName;
       this.villageName = villageName;
       this.landMark = landMark;
       this.patientSerial = patientSerial;
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

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getSubLocationName() {
		return subLocationName;
	}

	public void setSubLocationName(String subLocationName) {
		this.subLocationName = subLocationName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getPatientSerial() {
		return patientSerial;
	}

	public void setPatientSerial(String patientSerial) {
		this.patientSerial = patientSerial;
	}

}


