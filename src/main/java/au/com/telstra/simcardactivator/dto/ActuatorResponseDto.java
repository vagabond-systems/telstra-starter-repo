package au.com.telstra.simcardactivator.dto;

public class ActuatorResponseDto {
	public boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ActuatorResponseDto(boolean success) {
		super();
		this.success = success;
	}

	public ActuatorResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
