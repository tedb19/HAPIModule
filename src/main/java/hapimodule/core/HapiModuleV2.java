
package hapimodule.core;

import ca.uhn.hl7v2.HL7Exception;
import hapimodule.core.constants.IdentifierType;
import hapimodule.core.constants.MaritalStatus;
import hapimodule.core.entities.PatientSource;
import hapimodule.core.entities.Person;
import hapimodule.core.entities.PersonIdentifier;
import hapimodule.core.hapi.ADTProcessor;
import hapimodule.core.hapi.ORUProcessor;
import hapimodule.core.hapi.models.MSHSegment;
import hapimodule.core.hapi.models.OBXSegment;
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
        
        String facilityName = "TEST FACILITY";
        String mfl_code = "123456";
        String applicationName = "MIRTH CDS";
        String cdsName = "REGIONAL SERVER";
        String cdsApplicationName = "MIRTH CDS";
        
        //create and populate a person object
        Person person = new Person();
        person.setFirstName("stanslaus");
        person.setMiddleName("Otieno");
        person.setLastName("Odhiambo");
        person.setSex("Male");
        person.setBirthdate(new Date());
        person.setMaritalStatusType(MaritalStatus.SEPARATED);
        
        //add identifiers
        Set<PersonIdentifier> identifiers = new HashSet<>();
        PersonIdentifier identifier;
        
        //CCC Number
        identifier = new PersonIdentifier();
        identifier.setIdentifier("123456");
        identifier.setIdentifierType(IdentifierType.CCC_NUMBER);
        identifiers.add(identifier);

        //HEI Number
        identifier = new PersonIdentifier();
        identifier.setIdentifier("44444");
        identifier.setIdentifierType(IdentifierType.HEI_NUMBER);
        identifiers.add(identifier);

        //National ID
        identifier = new PersonIdentifier();
        identifier.setIdentifier("888888");
        identifier.setIdentifierType(IdentifierType.NATIONAL_ID);
        identifiers.add(identifier);
        
        person.setPersonIdentifiers(identifiers);
        
        //add patient source
        PatientSource patientSrc = new PatientSource();
        patientSrc.setMFL_Code("123456");
        patientSrc.setPatientSourceName("TB_CLINIC");
        
        person.setPatientSource(patientSrc);
        
        //add a collection of events recorded..
        List<OBXSegment> obxSegments = new ArrayList<>();
        
        //forming a sample OBXSegment object
        OBXSegment obxSegment=new OBXSegment();
        obxSegment.setObservationIdentifier(null);
        obxSegment.setObservationIdentifierText("WHO_STAGE");
        obxSegment.setCodingSystem("AS4/SNOMED");
        obxSegment.setObservationSubId("2");
        obxSegment.setObservationValue("4");
        obxSegment.setUnits("CM");
        obxSegment.setResultStatus("P");
        obxSegment.setDateOfLastNormalValue(new Date());
        obxSegment.setDateTimeOfObservation(new Date());
        
        //forming a second sample OBXSegment object
        OBXSegment secondObxSegment=new OBXSegment();
        secondObxSegment.setObservationIdentifier(null);
        secondObxSegment.setObservationIdentifierText("HIV_DIAGNOSIS");
        secondObxSegment.setCodingSystem("AS4/SNOMED");
        secondObxSegment.setObservationSubId("2");
        secondObxSegment.setObservationValue("1");
        secondObxSegment.setUnits("CM");
        secondObxSegment.setResultStatus("P");
        secondObxSegment.setDateOfLastNormalValue(new Date());
        secondObxSegment.setDateTimeOfObservation(new Date());
        
        obxSegments.add(secondObxSegment);
        obxSegments.add(obxSegment);
        
        MSHSegment msh = new MSHSegment(applicationName, facilityName, mfl_code, cdsName, cdsApplicationName);
        
        ORUProcessor oruProcessor = new ORUProcessor(person, obxSegments, msh); 
        ADTProcessor adtProcessor = new ADTProcessor(person, msh);
        System.out.println(oruProcessor.generateORU());
        System.out.println(adtProcessor.generateADT("A04"));
        System.out.println(adtProcessor.generateADT("A08"));
        
    }

}
