package hapimodule.core.hapi;

import hapimodule.core.hapi.models.OBXSegment;
import ca.uhn.hl7v2.HL7Exception;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import hapimodule.core.entities.Person;
import hapimodule.core.hapi.wrappers.ORUWrapper;
import hapimodule.core.hapi.models.MSHSegment;

public class ORUProcessor {

    final static Logger logger = Logger.getLogger(ORUProcessor.class.getName());
    List<OBXSegment> fillers;
    private final Person person;
    private final MSHSegment mshSegment;

    public ORUProcessor(Person person, List<OBXSegment> fillers, MSHSegment mshSegment) {
        this.person = person;
        this.fillers=fillers;
        this.mshSegment = mshSegment;

    }

    public String generateORU(){
        String hl7 = "";
        try {
            ORUWrapper message = new ORUWrapper();
            message.initQuickstart("ORU","R01", "P");
            message.setMessageHeader(mshSegment);
            message.setPatientDemographics(person);
            message.setOrderObservation(mshSegment, fillers);
            hl7 = message.encode();
            
        } catch (HL7Exception|IOException ex) {
            Logger.getLogger(ORUProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hl7;
    }
}
