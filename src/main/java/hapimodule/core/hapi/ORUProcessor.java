package hapimodule.core.hapi;

import hapimodule.core.hapi.models.OBXModel;
import ca.uhn.hl7v2.HL7Exception;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import hapimodule.core.entities.PatientSource;
import hapimodule.core.entities.Person;
import hapimodule.core.hapi.wrappers.ORUWrapper;
import hapimodule.core.hapi.models.MSHModel;

public class ORUProcessor {

    final static Logger logger = Logger.getLogger(ORUProcessor.class.getName());
    List<OBXModel> fillers;
    private final Person person;
    private final PatientSource patientSource;
    private final MSHModel mshModel;

    public ORUProcessor(Person person,PatientSource patientSource, List<OBXModel> fillers, MSHModel mshModel) {
        this.person = person;
        this.patientSource = patientSource;
        this.fillers=fillers;
        this.mshModel = mshModel;

    }

    public String generateORU(){
        String hl7 = "";
        try {
            ORUWrapper message = new ORUWrapper();
            message.initQuickstart("ORU","R01", "P");
            message.setMessageHeader(mshModel);
            message.setPatientDemographics(person, patientSource);
            message.setOrderObservation(mshModel, fillers);
            hl7 = message.encode();
            
        } catch (HL7Exception|IOException ex) {
            Logger.getLogger(ORUProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hl7;
    }
}
