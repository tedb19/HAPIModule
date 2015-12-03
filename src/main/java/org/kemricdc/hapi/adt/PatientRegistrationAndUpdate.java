/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kemricdc.hapi.adt;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.v24.message.*;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.model.v24.segment.PV1;
import ca.uhn.hl7v2.parser.Parser;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kemricdc.entities.Address;
import org.kemricdc.entities.Location;
import org.kemricdc.entities.PatientSource;
import org.kemricdc.entities.Person;
import org.kemricdc.entities.PersonIdentifier;
import org.kemricdc.hapi.PersonFactory;

/**
 *
 * @author Stanslaus Odhiambo
 */
public class PatientRegistrationAndUpdate {

    final static Logger logger = Logger.getLogger(PatientRegistrationAndUpdate.class.getName());
    
    PersonFactory factory;
    Person person;
    HapiContext context = new DefaultHapiContext();
    Properties properties = new Properties();
    String facilityName;
    String mfl_code;
    String applicationName;
    PatientSource patientSource;
    
    public PatientRegistrationAndUpdate(Person person, Set<Address> addresses, Location location,
            PatientSource patientSource, String facilityName, String mfl_code, String applicationName) {
        this.facilityName = facilityName;
        this.mfl_code = mfl_code;
        this.applicationName = applicationName;
        this.patientSource = patientSource;

        factory = new PersonFactory(person, addresses, location, patientSource);
        this.person = factory.buildPerson();

    }

    public String patientRegistrationOrUpdate(String triggerEvent) {
        String hl7 = "";
        try {
            hl7 = generateHL7String(triggerEvent);
        } catch (HL7Exception | IOException ex) {
            Logger.getLogger(PatientRegistrationAndUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hl7;
    }

    private String generateHL7String(String triggerEvent) throws HL7Exception, IOException {

        ADT_A01 adt_a01 = new ADT_A01();
        adt_a01.initQuickstart("ADT", triggerEvent, "T");

        // Populate the MSH Segment
        MSH mshSegment = adt_a01.getMSH();
        mshSegment.getSendingApplication().getNamespaceID().setValue(applicationName);
        mshSegment.getSendingFacility().getNamespaceID().setValue(facilityName);
        mshSegment.getSendingFacility().getUniversalID().setValue(mfl_code);
        mshSegment.getSequenceNumber().setValue("1");
        mshSegment.getMessageType().getMessageStructure().setValue("ADT_"+triggerEvent);

        // Populate the PID Segment
        PID pid = adt_a01.getPID();
        pid.getPatientName(0).getFamilyName().getSurname().setValue(person.getLastName());
        pid.getPatientName(0).getGivenName().setValue(person.getFirstName());
        pid.getPatientName(0).getSecondAndFurtherGivenNamesOrInitialsThereof().setValue(person.getMiddleName());
        
        int count = 0;
        Set<PersonIdentifier> identifiers = person.getPersonIdentifiers();

        for (PersonIdentifier personIdentifier : identifiers) {
            pid.getPatientIdentifierList(count).getID().setValue(personIdentifier.getIdentifier());
            pid.getPatientIdentifierList(count).getIdentifierTypeCode().setValue(personIdentifier.getIdentifierType().getValue());
            count++;
        }
        pid.getAdministrativeSex().setValue(person.getSex());
        pid.getDateTimeOfBirth().getTimeOfAnEvent().setValue(person.getBirthdate());
        pid.getMaritalStatus().getCe1_Identifier().setValue(person.getMaritalStatusType().getValue());
        
        //populate the PV1 segment
        PV1 pv1 = adt_a01.getPV1();
        pv1.getPriorPatientLocation().getFacility().getUniversalID().setValue(patientSource.getMFL_Code());
        pv1.getPriorPatientLocation().getPersonLocationType().setValue(patientSource.getPatientSourceName());
        
        Parser parser = context.getXMLParser();
        String encodedMessage = parser.encode(adt_a01);
        logger.log(Level.FINE, 
                        "Printing XML Encoded Message:\n{0}",encodedMessage);
        

        // Next, let's use the ER7 parser to encode as ER7
        parser = context.getPipeParser();
        encodedMessage = parser.encode(adt_a01);
        logger.log(Level.FINE, 
                        "Printing ER7 Encoded Message:\n{0}",encodedMessage);
        
        return encodedMessage;
    }
}
