package au.com.telstra.simcardactivator.dto;

public class ActuatorDto {
	public String iccid;

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public ActuatorDto(String iccid) {
		super();
		this.iccid = iccid;
	}

	public ActuatorDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
