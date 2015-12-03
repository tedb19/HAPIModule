
package org.kemricdc;

import ca.uhn.hl7v2.HL7Exception;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kemricdc.constants.IdentifierType;
import org.kemricdc.constants.MaritalStatus;
import org.kemricdc.entities.PatientSource;
import org.kemricdc.entities.Person;
import org.kemricdc.entities.PersonIdentifier;
import org.kemricdc.hapi.adt.PatientRegistrationAndUpdate;
import org.kemricdc.hapi.oru.OruFiller;
import org.kemricdc.hapi.oru.ProcessTransactions;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class HapiModuleV2 {

    /**
     * @param args the command line arguments
     * @throws ca.uhn.hl7v2.HL7Exception
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws HL7Exception, IOException {
        
        String host = "localhost";
        String facilityName = "TEST FACILITY";
        String mfl_code = "123456";
        String applicationName = "MIRTH CDS";
        String cdsName = "REGIONAL SERVER";
        String cdsApplicationName = "MIRTH CDS";
        
        System.out.println("Host: "+host);
        Person p = new Person();
        p.setFirstName("stanslaus");
        p.setLastName("Odhiambo");
        p.setSex("male");
        p.setMaritalStatusType(MaritalStatus.SEPARATED);
        
        
        //Set 3 identifiers
        Set<PersonIdentifier> identifiers = new HashSet<>();
        PersonIdentifier pi = new PersonIdentifier();
        pi.setIdentifier("123456");
        pi.setIdentifierType(IdentifierType.CCC_NUMBER);
        identifiers.add(pi);

        pi = new PersonIdentifier();
        pi.setIdentifier("44444");
        pi.setIdentifierType(IdentifierType.HEI_NUMBER);
        identifiers.add(pi);

        pi = new PersonIdentifier();
        pi.setIdentifier("888888");
        pi.setIdentifierType(IdentifierType.NATIONAL_ID);
        identifiers.add(pi);
        
        //Add identifiers to the set
        p.setPersonIdentifiers(identifiers);
        
        //set the birth date
        p.setBirthdate(new Date());

        PatientSource patientSrc = new PatientSource();
        patientSrc.setMFL_Code("123456");
        patientSrc.setPatientSourceName("TB_CLINIC");
        //Leave this error for the time..reminds me of what is to be done.
        //No use of null values

        PatientRegistrationAndUpdate patientRegistration = new PatientRegistrationAndUpdate(p, null, null, patientSrc,facilityName, mfl_code, applicationName);
        patientRegistration.patientRegistrationOrUpdate("A04");

        
        
        System.err.println("\n\nThe transaction phase\n\n");
//        Ensure person fields populated before passing to the constructor
        List<OruFiller> fillers = new ArrayList<>();
        
        //forming a sample OruFiller object
        OruFiller filler=new OruFiller();
        filler.setObservationIdentifier(null);
        filler.setObservationIdentifierText("WHO_STAGE");
        filler.setCodingSystem("AS4/SNOMED");
        filler.setObservationSubId("2");
        filler.setObservationValue("4");
        filler.setUnits("CM");
        filler.setResultStatus("P");
        filler.setDateOfLastNormalValue(new Date());
        filler.setDateTimeOfObservation(new Date());
        
        fillers.add(filler);
        
        ProcessTransactions bXSegment = new ProcessTransactions(p,fillers);        
        String bXString = bXSegment.generateORU(applicationName,facilityName,mfl_code,cdsName, cdsApplicationName);
        
    }

}
