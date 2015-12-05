package hapimodule.core.hapi.wrappers;

import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v24.message.ADT_A01;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.model.v24.segment.PV1;
import hapimodule.core.entities.PatientSource;
import hapimodule.core.entities.Person;
import hapimodule.core.entities.PersonIdentifier;
import hapimodule.core.hapi.models.MSHModel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Teddy Odhiambo
 */
public class ADTWrapper extends ADT_A01{
    
    /*
    * Populates the MSH segment of the ADT
    */
    public MSH setMessageHeader(MSHModel mshModel){
        MSH msh = getMSH();
        
        try {
            msh.getSendingApplication().getNamespaceID().setValue(mshModel.getApplicationName());
            msh.getSendingFacility().getNamespaceID().setValue(mshModel.getFacilityName());
            msh.getSendingFacility().getUniversalID().setValue(mshModel.getMFLCode());
            msh.getReceivingApplication().getNamespaceID().setValue(mshModel.getCDSName());
            msh.getReceivingFacility().getNamespaceID().setValue(mshModel.getCDSApplicationName());
            
        } catch (DataTypeException ex) {
            Logger.getLogger(ORUWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msh;
    }
    
    /*
    * Populates the PID an the PV1 segments of the ADT
    */
    public PID setPatientDemographics(Person person, PatientSource patientSource){ 
        PID pid = getPID();
        try {
            pid.getPatientName(0).getFamilyName().getSurname().setValue(person.getLastName());
            pid.getPatientName(0).getGivenName().setValue(person.getFirstName());
            pid.getPatientName(0).getSecondAndFurtherGivenNamesOrInitialsThereof().setValue(person.getMiddleName());
            pid.getPatientIdentifierList(0).getID().setValue(((PersonIdentifier) person.getPersonIdentifiers().iterator().next()).getIdentifier());
            pid.getAdministrativeSex().setValue(person.getSex());
            pid.getDateTimeOfBirth().getTimeOfAnEvent().setValue(person.getBirthdate());
            pid.getMaritalStatus().getText().setValue(person.getMaritalStatusType().getValue());
            
            if(patientSource != null){
                PV1 pv1 = getPV1();
                pv1.getPriorPatientLocation().getFacility().getUniversalID().setValue(patientSource.getMFL_Code());
                pv1.getPriorPatientLocation().getPersonLocationType().setValue(patientSource.getPatientSourceName());
            }
        } catch (DataTypeException ex) {
            Logger.getLogger(ADTWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pid;
    }
}
