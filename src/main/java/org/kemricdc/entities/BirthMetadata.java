package org.kemricdc.entities;
// Generated Nov 28, 2014 11:52:32 AM by Hibernate Tools 3.6.0



/**
 * BirthMetadata generated by hbm2java
 */
public class BirthMetadata  implements java.io.Serializable {


     private int birthMetadataId;
     private Personevents personevents;
     private String deliveryMode;
     private String placeOfDelivery;
     private String childArv;
     private String pmtctDrug;

    public BirthMetadata() {
    }

	
    public BirthMetadata(int birthMetadataId, Personevents personevents) {
        this.birthMetadataId = birthMetadataId;
        this.personevents = personevents;
    }
    public BirthMetadata(int birthMetadataId, Personevents personevents, String deliveryMode, String placeOfDelivery, String childArv, String pmtctDrug) {
       this.birthMetadataId = birthMetadataId;
       this.personevents = personevents;
       this.deliveryMode = deliveryMode;
       this.placeOfDelivery = placeOfDelivery;
       this.childArv = childArv;
       this.pmtctDrug = pmtctDrug;
    }
   
    public int getBirthMetadataId() {
        return this.birthMetadataId;
    }
    
    public void setBirthMetadataId(int birthMetadataId) {
        this.birthMetadataId = birthMetadataId;
    }
    public Personevents getPersonevents() {
        return this.personevents;
    }
    
    public void setPersonevents(Personevents personevents) {
        this.personevents = personevents;
    }
    public String getDeliveryMode() {
        return this.deliveryMode;
    }
    
    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
    }
    public String getPlaceOfDelivery() {
        return this.placeOfDelivery;
    }
    
    public void setPlaceOfDelivery(String placeOfDelivery) {
        this.placeOfDelivery = placeOfDelivery;
    }
    public String getChildArv() {
        return this.childArv;
    }
    
    public void setChildArv(String childArv) {
        this.childArv = childArv;
    }
    public String getPmtctDrug() {
        return this.pmtctDrug;
    }
    
    public void setPmtctDrug(String pmtctDrug) {
        this.pmtctDrug = pmtctDrug;
    }




}


