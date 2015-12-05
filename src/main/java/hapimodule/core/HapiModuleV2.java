
package hapimodule.core;

import ca.uhn.hl7v2.HL7Exception;
import hapimodule.core.constants.IdentifierType;
import hapimodule.core.constants.MaritalStatus;
import hapimodule.core.entities.PatientSource;
import hapimodule.core.entities.Person;
import hapimodule.core.entities.PersonIdentifier;
import hapimodule.core.hapi.ADTProcessor;
import hapimodule.core.hapi.ORUProcessor;
import hapimodule.core.hapi.models.MSHModel;
import hapimodule.core.hapi.models.OBXModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Teddy Odhiambo
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
        Person person = new Person();
        person.setFirstName("stanslaus");
        person.setMiddleName("Otieno");
        person.setLastName("Odhiambo");
        person.setSex("Male");
        person.setBirthdate(new Date());
        person.setMaritalStatusType(MaritalStatus.SEPARATED);
        
        
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
        person.setPersonIdentifiers(identifiers);
        
        //set the birth date
        person.setBirthdate(new Date());

        PatientSource patientSrc = new PatientSource();
        patientSrc.setMFL_Code("123456");
        patientSrc.setPatientSourceName("TB_CLINIC");
        //Leave this error for the time..reminds me of what is to be done.
        //No use of null values

//        Ensure person fields populated before passing to the constructor
        List<OBXModel> fillers = new ArrayList<>();
        
        //forming a sample OBXModel object
        OBXModel filler=new OBXModel();
        filler.setObservationIdentifier(null);
        filler.setObservationIdentifierText("WHO_STAGE");
        filler.setCodingSystem("AS4/SNOMED");
        filler.setObservationSubId("2");
        filler.setObservationValue("4");
        filler.setUnits("CM");
        filler.setResultStatus("P");
        filler.setDateOfLastNormalValue(new Date());
        filler.setDateTimeOfObservation(new Date());
        
        //forming a sample OBXModel object
        OBXModel filler1=new OBXModel();
        filler1.setObservationIdentifier(null);
        filler1.setObservationIdentifierText("HIV_DIAGNOSIS");
        filler1.setCodingSystem("AS4/SNOMED");
        filler1.setObservationSubId("2");
        filler1.setObservationValue("1");
        filler1.setUnits("CM");
        filler1.setResultStatus("P");
        filler1.setDateOfLastNormalValue(new Date());
        filler1.setDateTimeOfObservation(new Date());
        
        fillers.add(filler1);
        fillers.add(filler);
        
        MSHModel msh = new MSHModel(applicationName, facilityName, mfl_code, cdsName, cdsApplicationName);
        
        ORUProcessor oruProcessor = new ORUProcessor(person, patientSrc, fillers, msh); 
        ADTProcessor adtProcessor = new ADTProcessor(person, patientSrc, msh);
        System.out.println("here\n");
        System.out.println(oruProcessor.generateORU());
        System.out.println(adtProcessor.generateADT("A04"));
        System.out.println(adtProcessor.generateADT("A08"));
        
    }

}
