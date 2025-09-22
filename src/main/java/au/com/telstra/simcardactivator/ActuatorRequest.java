package au.com.telstra.simcardactivator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActuatorRequest {
    
    @JsonProperty("iccid")
    private String iccid;
    
    // Default constructor
    public ActuatorRequest() {}
    
    // Constructor with parameters
    public ActuatorRequest(String iccid) {
        this.iccid = iccid;
    }
    
    // Getters and setters
    public String getIccid() {
        return iccid;
    }
    
    public void setIccid(String iccid) {
        this.iccid = iccid;
    }
    
    @Override
    public String toString() {
        return "ActuatorRequest{" +
                "iccid='" + iccid + '\'' +
                '}';
    }
}
