package au.com.telstra.simcardactivator.Model;

import org.springframework.stereotype.Component;

import javax.persistence.*;

//@Component
@Entity
public class Iccid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String iccid;

    private String customerEmail;

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Iccid() {
    }

    //    public Iccid(long id, String iccid, String customerEmail, boolean active) {
//        this.id = id;
//        this.iccid = iccid;
//        this.customerEmail = customerEmail;
//        this.active = active;
//    }

    public Iccid(String iccid, String customerEmail, boolean active) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.active = active;
    }
}
