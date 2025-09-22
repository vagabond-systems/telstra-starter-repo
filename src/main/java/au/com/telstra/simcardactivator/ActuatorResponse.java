package au.com.telstra.simcardactivator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActuatorResponse {
    
    @JsonProperty("success")
    private boolean success;
    
    // Default constructor
    public ActuatorResponse() {}
    
    // Constructor with parameters
    public ActuatorResponse(boolean success) {
        this.success = success;
    }
    
    // Getters and setters
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    @Override
    public String toString() {
        return "ActuatorResponse{" +
                "success=" + success +
                '}';
    }
}
