package hapimodule.core.hapi.models;

/**
 *
 * @author Ted
 */
public class MSHModel {
    private String applicationName;
    private String facilityName;
    private String MFLCode;
    private String CDSName;
    private String CDSApplicationName;

    public MSHModel(String applicationName, String facilityName, String MFLCode, String CDSName,
            String CDSApplicationName) {
        this.applicationName = applicationName;
        this.facilityName = facilityName;
        this.MFLCode = MFLCode;
        this.CDSName = CDSName;
        this.CDSApplicationName = CDSApplicationName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getMFLCode() {
        return MFLCode;
    }

    public void setMFLCode(String MFLCode) {
        this.MFLCode = MFLCode;
    }

    public String getCDSName() {
        return CDSName;
    }

    public void setCDSName(String CDSName) {
        this.CDSName = CDSName;
    }

    public String getCDSApplicationName() {
        return CDSApplicationName;
    }

    public void setCDSApplicationName(String CDSApplicationName) {
        this.CDSApplicationName = CDSApplicationName;
    }   
}
