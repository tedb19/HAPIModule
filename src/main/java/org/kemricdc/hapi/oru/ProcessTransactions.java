/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kemricdc.hapi.oru;

import java.io.IOException;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.v24.datatype.CE;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_PATIENT;
import ca.uhn.hl7v2.model.v24.message.ORU_R01;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.OBR;
import ca.uhn.hl7v2.model.v24.segment.OBX;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.model.v25.datatype.NM;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kemricdc.entities.Person;
import org.kemricdc.entities.PersonIdentifier;


public class ProcessTransactions {

    final static Logger logger = Logger.getLogger(ProcessTransactions.class.getName());
    List<OruFiller> fillers;
    private final Person person;

    public ProcessTransactions(Person person,List<OruFiller> fillers) {
        this.person = person;
        this.fillers=fillers;

    }

    
    public String generateORU(String application_name, String facility_name, String mfl_code, 
            String cds_name, String cdsapplication_name) throws HL7Exception, IOException{
        
        // First, a message object is constructed
        ORU_R01 message = new ORU_R01();

        /*
         * The initQuickstart method populates all of the mandatory fields in the
         * MSH segment of the message, including the message type, the timestamp,
         * and the control ID.
         */
        message.initQuickstart("ORU","R01", "P");
        
        MSH msh = message.getMSH();
        msh.getSendingApplication().getNamespaceID().setValue(application_name);
        msh.getSendingFacility().getNamespaceID().setValue(facility_name);
        msh.getSendingFacility().getUniversalID().setValue(mfl_code);

        //populate the receiving application details
        msh.getReceivingApplication().getNamespaceID().setValue(cds_name);
        msh.getReceivingFacility().getNamespaceID().setValue(cdsapplication_name);
        msh.getSequenceNumber().setValue("123");
        
        ORU_R01_PATIENT oruPatient = message.getPATIENT_RESULT().getPATIENT();
        PID pid = oruPatient.getPID();
        pid.getDateTimeOfBirth().getTimeOfAnEvent().setValue(person.getBirthdate());
        pid.getPatientName(0).getFamilyName().getSurname().setValue(person.getLastName());
        pid.getPatientName(0).getSecondAndFurtherGivenNamesOrInitialsThereof().setValue(person.getMiddleName());
       //pid.getPatientName(0).get().get().setValue(person.getMiddleName());
        
        pid.getPatientName(0).getGivenName().setValue(person.getFirstName());
        pid.getAdministrativeSex().setValue(person.getSex());
        pid.getMaritalStatus().getCe1_Identifier().setValue(person.getMaritalStatusType().getValue());
       
        
        //Changed to the below to handle scenario where the patient has more than one identifier
        Set<PersonIdentifier> identifiers = person.getPersonIdentifiers();
        int count = 0;
        for (PersonIdentifier personIdentifier : identifiers) {
            pid.getPatientIdentifierList(count).getID().setValue(personIdentifier.getIdentifier());
            pid.getPatientIdentifierList(count).getIdentifierTypeCode().setValue(personIdentifier.getIdentifierType().getValue());
            count++;
        }
        
        ORU_R01_ORDER_OBSERVATION orderObservation = message.getPATIENT_RESULT().getORDER_OBSERVATION();

        // Populate the OBR
        OBR obr = orderObservation.getOBR();
        obr.getSetIDOBR().setValue(mfl_code);
        obr.getFillerOrderNumber().getEntityIdentifier().setValue(mfl_code);
        obr.getFillerOrderNumber().getNamespaceID().setValue(facility_name);

        obr.getUniversalServiceIdentifier().getIdentifier().setValue(mfl_code);
        OBX obx;
        Varies value;

        for (int i = 0; i < fillers.size(); i++) {
            OruFiller oruFiller = fillers.get(i);

            // Populate the OBXs
            obx = orderObservation.getOBSERVATION(i).getOBX();
            obx.getSetIDOBX().setValue(mfl_code);
            
            
            //We are working with a fixed value of ST  - pretty much works for us
            obx.getValueType().setValue("ST");
            
            //Form the Observation Identifier
            obx.getObservationIdentifier().getIdentifier().setValue(oruFiller.getObservationIdentifier());
            obx.getObservationIdentifier().getText().setValue(oruFiller.getObservationIdentifierText());
            obx.getObservationIdentifier().getNameOfCodingSystem().setValue(oruFiller.getCodingSystem());
            
            
            //Form the Observation Sub-ID if necessary
            obx.getObservationSubId().setValue(oruFiller.getObservationSubId());
            
            //Form the Observation Value
            NM nm=new NM(message);
            nm.setValue(oruFiller.getObservationValue());
            value = obx.getObservationValue(0);
            value.setData(nm);
            
            //Form the Units
            obx.getUnits().getText().setValue(oruFiller.getUnits());
            
            //Form References Range
            obx.getReferencesRange().setValue(oruFiller.getReferencesRange());
            
            //Form the Abnormal Flags
            obx.getAbnormalFlags().setValue(oruFiller.getAbnormalFlags());
            
            //Form the Probability
            obx.getProbability(0).setValue(oruFiller.getProbability());
            
            //Form Nature of Abnormal Test
            obx.getNatureOfAbnormalTest().setValue(oruFiller.getNatureOfAbnormalTest());
            
            //Form Result Status
            obx.getObservationResultStatus().setValue(oruFiller.getResultStatus());
            
            //Form the Date of Last Normal Values
            obx.getDateLastObservationNormalValue().getTimeOfAnEvent().setValue(oruFiller.getDateOfLastNormalValue());
            
            //set the User Defined Access Checks if necessary
            obx.getUserDefinedAccessChecks().setValue(oruFiller.getUserDefinedAccessChecks());
            
            //set Date/Time of the Observation
            obx.getDateTimeOfTheObservation().getTimeOfAnEvent().setValue(oruFiller.getDateTimeOfObservation());
            
            
            //set the Producer's ID
            obx.getProducerSID().getText().setValue(oruFiller.getProducerId());
            
            //form the Responsible Observer
            obx.getResponsibleObserver().getIDNumber().setValue(oruFiller.getResponsibleObserverId());
            obx.getResponsibleObserver().getGivenName().setValue(oruFiller.getResponsibleObserverGivenName());
            
            //Form the Observation Method
            obx.getObservationMethod(0).getText().setValue(oruFiller.getObservationMethod());

        }

        // Print the message (remember, the MSH segment was not fully or correctly populated)
        String finalString = message.encode();
        logger.log(Level.FINE, finalString);
        
        return finalString;

    }
    
    

}
