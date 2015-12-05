/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hapimodule.core.hapi.wrappers;

import hapimodule.core.hapi.models.MSHModel;
import hapimodule.core.hapi.models.OBXModel;
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
     * Populates both the PID and PV1 segments
     */
    public ORU_R01_PATIENT setPatientDemographics(Person person, PatientSource patientSource){
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
            if(patientSource != null){
                PV1 pv1 = oruPatient.getVISIT().getPV1();
                pv1.getPriorPatientLocation().getFacility().getUniversalID().setValue(patientSource.getMFL_Code());
                pv1.getPriorPatientLocation().getPersonLocationType().setValue(patientSource.getPatientSourceName());
            }
            
        } catch (DataTypeException ex) {
            Logger.getLogger(ORUWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oruPatient;
    }
    
    /*
     * Populates both the OBR segment, and the collection of OBX segments
     */
    public ORU_R01_ORDER_OBSERVATION setOrderObservation(MSHModel mshModel, List<OBXModel> fillers){
        ORU_R01_ORDER_OBSERVATION orderObservation = getPATIENT_RESULT().getORDER_OBSERVATION();
        try {
            // Populate the OBR
            OBR obr = orderObservation.getOBR();
            obr.getSetIDOBR().setValue(mshModel.getMFLCode());
            obr.getFillerOrderNumber().getEntityIdentifier().setValue(mshModel.getMFLCode());
            obr.getFillerOrderNumber().getNamespaceID().setValue(mshModel.getFacilityName());
            obr.getUniversalServiceIdentifier().getIdentifier().setValue(mshModel.getMFLCode());
            
            OBX obx;
            Varies value;
            int index = 0;
            for (OBXModel obxModel:fillers) {
                obxModel = fillers.get(index);
                obx = orderObservation.getOBSERVATION(index).getOBX();
                obx.getSetIDOBX().setValue(mshModel.getMFLCode());
                obx.getValueType().setValue("ST");
                obx.getObservationIdentifier().getIdentifier().setValue(obxModel.getObservationIdentifier());
                obx.getObservationIdentifier().getText().setValue(obxModel.getObservationIdentifierText());
                obx.getObservationIdentifier().getNameOfCodingSystem().setValue(obxModel.getCodingSystem());
                obx.getObservationSubId().setValue(obxModel.getObservationSubId());
                NM nm=new NM(new ORU_R01());
                nm.setValue(obxModel.getObservationValue());
                value = obx.getObservationValue(0);
                value.setData(nm);
                obx.getUnits().getText().setValue(obxModel.getUnits());
                obx.getReferencesRange().setValue(obxModel.getReferencesRange());
                obx.getAbnormalFlags().setValue(obxModel.getAbnormalFlags());
                obx.getProbability(0).setValue(obxModel.getProbability());
                obx.getNatureOfAbnormalTest().setValue(obxModel.getNatureOfAbnormalTest());
                obx.getObservationResultStatus().setValue(obxModel.getResultStatus());
                obx.getDateLastObservationNormalValue().getTimeOfAnEvent().setValue(obxModel.getDateOfLastNormalValue());
                obx.getUserDefinedAccessChecks().setValue(obxModel.getUserDefinedAccessChecks());
                obx.getDateTimeOfTheObservation().getTimeOfAnEvent().setValue(obxModel.getDateTimeOfObservation());
                obx.getProducerSID().getText().setValue(obxModel.getProducerId());
                obx.getResponsibleObserver().getIDNumber().setValue(obxModel.getResponsibleObserverId());
                obx.getResponsibleObserver().getGivenName().setValue(obxModel.getResponsibleObserverGivenName());
                obx.getObservationMethod(0).getText().setValue(obxModel.getObservationMethod());
                index++;
            }
        } catch (DataTypeException ex) {
            Logger.getLogger(ORUWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderObservation;
    }
}
