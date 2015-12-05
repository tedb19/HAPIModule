package hapimodule.core.constants;

/**
 *
 * @author Teddy Odhiambo
 */
public enum IdentifierType {
    
    CCC_NUMBER("CCC_NUMBER"),
    ANC_NUMBER("ANC_NUMBER"),
    PMTCT_NUMBER("PMTCT_NUMBER"),
    TB_NUMBER("TB_NUMBER"),
    NATIONAL_ID("NATIONAL_ID"),
    GBVRC_NUMBER("GBVRC_NUMBER"),
    PID_NUMBER("PID_NUMBER"),
    HEI_NUMBER("HEI_NUMBER");
    
    private final String value;
    
    public String getValue(){
        return value;
    }
    private IdentifierType(String value){
        this.value=value;
    }
}
