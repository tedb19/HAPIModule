package hapimodule.core.hapi;

import ca.uhn.hl7v2.HL7Exception;
import hapimodule.core.entities.PatientSource;
import hapimodule.core.entities.Person;
import hapimodule.core.hapi.models.MSHSegment;
import hapimodule.core.hapi.wrappers.ADTWrapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Teddy Odhiambo
 */
public class ADTProcessor {
    final static Logger logger = Logger.getLogger(ADTProcessor.class.getName());
    private final Person person;
    private final MSHSegment mshSegment;
    
    public ADTProcessor(Person person, MSHSegment mshSegment){
        this.person = person;
        this.mshSegment = mshSegment;
    }
    
    public String generateADT(String triggerEvent){
        String hl7 = "";
        try {
            ADTWrapper message = new ADTWrapper();
            message.initQuickstart("ADT", triggerEvent, "P");
            message.setMessageHeader(mshSegment);
            message.setPatientDemographics(person);
            hl7 = message.encode();
            
        } catch (HL7Exception|IOException ex) {
            Logger.getLogger(ORUProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hl7;
    }
}
