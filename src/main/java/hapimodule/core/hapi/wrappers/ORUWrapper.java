package hapimodule.core.hapi.wrappers;

import hapimodule.core.hapi.models.MSHSegment;
import hapimodule.core.hapi.models.OBXSegment;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_PATIENT;
import ca.uhn.hl7v2.model.v24.message.ORU_R01;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.OBR;
import ca.uhn.hl7v2.model.v24.segment.OBX;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.model.v24.segment.PV1;
import ca.uhn.hl7v2.model.v25.datatype.NM;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import hapimodule.core.entities.PatientSource;
import hapimodule.core.entities.Person;
import hapimodule.core.entities.PersonIdentifier;

/**
 *
 * @author Ted
 */
public class ORUWrapper extends ORU_R01{
    
    /*
     * Populates the MSH segment of the ORU
     */
    public MSH setMessageHeader(MSHSegment mshSegment){
        MSH msh = getMSH();
        
        try {
            msh.getSendingApplication().getNamespaceID().setValue(mshSegment.getApplicationName());
            msh.getSendingFacility().getNamespaceID().setValue(mshSegment.getFacilityName());
            msh.getSendingFacility().getUniversalID().setValue(mshSegment.getMFLCode());
            msh.getReceivingApplication().getNamespaceID().setValue(mshSegment.getCDSName());
            msh.getReceivingFacility().getNamespaceID().setValue(mshSegment.getCDSApplicationName());
            
        } catch (DataTypeException ex) {
            Logger.getLogger(ORUWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msh;
    }
    
    /*
     * Populates both the PID and PV1 segments
     */
    public ORU_R01_PATIENT setPatientDemographics(Person person){
        ORU_R01_PATIENT oruPatient = getPATIENT_RESULT().getPATIENT();
        try {
            PID pid = oruPatient.getPID();
            pid.getDateTimeOfBirth().getTimeOfAnEvent().setValue(person.getBirthdate());
            pid.getPatientName(0).getFamilyName().getSurname().setValue(person.getLastName());
            pid.getPatientName(0).getSecondAndFurtherGivenNamesOrInitialsThereof().setValue(person.getMiddleName());
            pid.getPatientName(0).getGivenName().setValue(person.getFirstName());
            pid.getAdministrativeSex().setValue(person.getSex());
            pid.getMaritalStatus().getCe1_Identifier().setValue(person.getMaritalStatusType().getValue());
            //set the person identifiers
            Set<PersonIdentifier> identifiers = person.getPersonIdentifiers();
            int count = 0;
            for (PersonIdentifier personIdentifier : identifiers) {
                pid.getPatientIdentifierList(count).getID().setValue(personIdentifier.getIdentifier());
                pid.getPatientIdentifierList(count).getIdentifierTypeCode().setValue(personIdentifier.getIdentifierType().getValue());
                count++;
            }
            
            //set the patient source details
            if(person.getPatientSource() != null){
                PV1 pv1 = oruPatient.getVISIT().getPV1();
                pv1.getPriorPatientLocation().getFacility().getUniversalID().setValue(person.getPatientSource().getMFL_Code());
                pv1.getPriorPatientLocation().getPersonLocationType().setValue(person.getPatientSource().getPatientSourceName());
            }
            
        } catch (DataTypeException ex) {
            Logger.getLogger(ORUWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oruPatient;
    }
    
    /*
     * Populates both the OBR segment, and the collection of OBX segments
     */
    public ORU_R01_ORDER_OBSERVATION setOrderObservation(MSHSegment mshSegment, List<OBXSegment> obxSegments){
        ORU_R01_ORDER_OBSERVATION orderObservation = getPATIENT_RESULT().getORDER_OBSERVATION();
        try {
            // Populate the OBR
            OBR obr = orderObservation.getOBR();
            obr.getSetIDOBR().setValue(mshSegment.getMFLCode());
            obr.getFillerOrderNumber().getEntityIdentifier().setValue(mshSegment.getMFLCode());
            obr.getFillerOrderNumber().getNamespaceID().setValue(mshSegment.getFacilityName());
            obr.getUniversalServiceIdentifier().getIdentifier().setValue(mshSegment.getMFLCode());
            
            OBX obx;
            Varies value;
            int index = 0;
            for (OBXSegment obxSegment:obxSegments) {
                obxSegment = obxSegments.get(index);
                obx = orderObservation.getOBSERVATION(index).getOBX();
                obx.getSetIDOBX().setValue(mshSegment.getMFLCode());
                obx.getValueType().setValue("ST");
                obx.getObservationIdentifier().getIdentifier().setValue(obxSegment.getObservationIdentifier());
                obx.getObservationIdentifier().getText().setValue(obxSegment.getObservationIdentifierText());
                obx.getObservationIdentifier().getNameOfCodingSystem().setValue(obxSegment.getCodingSystem());
                obx.getObservationSubId().setValue(obxSegment.getObservationSubId());
                NM nm=new NM(new ORU_R01());
                nm.setValue(obxSegment.getObservationValue());
                value = obx.getObservationValue(0);
                value.setData(nm);
                obx.getUnits().getText().setValue(obxSegment.getUnits());
                obx.getReferencesRange().setValue(obxSegment.getReferencesRange());
                obx.getAbnormalFlags().setValue(obxSegment.getAbnormalFlags());
                obx.getProbability(0).setValue(obxSegment.getProbability());
                obx.getNatureOfAbnormalTest().setValue(obxSegment.getNatureOfAbnormalTest());
                obx.getObservationResultStatus().setValue(obxSegment.getResultStatus());
                obx.getDateLastObservationNormalValue().getTimeOfAnEvent().setValue(obxSegment.getDateOfLastNormalValue());
                obx.getUserDefinedAccessChecks().setValue(obxSegment.getUserDefinedAccessChecks());
                obx.getDateTimeOfTheObservation().getTimeOfAnEvent().setValue(obxSegment.getDateTimeOfObservation());
                obx.getProducerSID().getText().setValue(obxSegment.getProducerId());
                obx.getResponsibleObserver().getIDNumber().setValue(obxSegment.getResponsibleObserverId());
                obx.getResponsibleObserver().getGivenName().setValue(obxSegment.getResponsibleObserverGivenName());
                obx.getObservationMethod(0).getText().setValue(obxSegment.getObservationMethod());
                index++;
            }
        } catch (DataTypeException ex) {
            Logger.getLogger(ORUWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderObservation;
    }
}
