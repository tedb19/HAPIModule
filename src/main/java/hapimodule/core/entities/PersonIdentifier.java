package hapimodule.core.entities;

import hapimodule.core.constants.IdentifierType;

public class PersonIdentifier  implements java.io.Serializable {
    /*
    * Represents a patient's unique identifier(s)
    */
     private IdentifierType identifierType;
     private String identifier;

    public PersonIdentifier() {
    }

    public PersonIdentifier(IdentifierType identifierType) {
        this.identifierType = identifierType;
    }
    public PersonIdentifier(IdentifierType identifierType, String identifier) {
       this.identifierType = identifierType;
       this.identifier = identifier;
    }
   
    public IdentifierType getIdentifierType() {
        return this.identifierType;
    }
    
    public void setIdentifierType(IdentifierType identifierType) {
        this.identifierType = identifierType;
    }

    public String getIdentifier() {
        return this.identifier;
    }
    
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}


