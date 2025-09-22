package au.com.telstra.simcardactivator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sim_card_activation_records")
public class SimCardActivationRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "iccid", nullable = false, length = 50)
    private String iccid;
    
    @Column(name = "customer_email", nullable = false, length = 255)
    private String customerEmail;
    
    @Column(name = "active", nullable = false)
    private boolean active;
    
    @Column(name = "activation_timestamp", nullable = false)
    private LocalDateTime activationTimestamp;
    
    @Column(name = "actuator_response", length = 1000)
    private String actuatorResponse;
    
    // Default constructor
    public SimCardActivationRecord() {
        this.activationTimestamp = LocalDateTime.now();
    }
    
    // Constructor with parameters
    public SimCardActivationRecord(String iccid, String customerEmail, boolean active) {
        this();
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.active = active;
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getIccid() {
        return iccid;
    }
    
    public void setIccid(String iccid) {
        this.iccid = iccid;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }
    
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public LocalDateTime getActivationTimestamp() {
        return activationTimestamp;
    }
    
    public void setActivationTimestamp(LocalDateTime activationTimestamp) {
        this.activationTimestamp = activationTimestamp;
    }
    
    public String getActuatorResponse() {
        return actuatorResponse;
    }
    
    public void setActuatorResponse(String actuatorResponse) {
        this.actuatorResponse = actuatorResponse;
    }
    
    @Override
    public String toString() {
        return "SimCardActivationRecord{" +
                "id=" + id +
                ", iccid='" + iccid + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", active=" + active +
                ", activationTimestamp=" + activationTimestamp +
                ", actuatorResponse='" + actuatorResponse + '\'' +
                '}';
    }
}
