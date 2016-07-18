package hapimodule.core.entities;

public class Location {

	private String villageName;
    private String locationName;
    private String subLocationName;
    private String landMark;
    private String countyName;
    private String districtName;
    private String provinceName;
    private String division;
    
    public Location(String villageName, String locationName, String subLocationName, String landMark, String countyName,
            String districtName, String provinceName, String division){
	    this.countyName = countyName;
	    this.districtName = districtName;
	    this.division = division;
	    this.locationName = locationName;
	    this.provinceName = provinceName;
	    this.subLocationName = subLocationName;
	    this.villageName = villageName;
	    this.landMark = landMark;
    }

    public Location(){}
    
	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getSubLocationName() {
		return subLocationName;
	}

	public void setSubLocationName(String subLocationName) {
		this.subLocationName = subLocationName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
    
    
}
