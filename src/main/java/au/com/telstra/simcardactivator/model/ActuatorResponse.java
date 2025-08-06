package au.com.telstra.simcardactivator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActuatorResponse {
    
    @JsonProperty("success")
    private boolean success;

    public ActuatorResponse() {}

    public ActuatorResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}