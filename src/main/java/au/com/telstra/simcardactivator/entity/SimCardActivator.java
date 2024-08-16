package au.com.telstra.simcardactivator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenerationTime;

@Entity
public class SimCardActivator {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	@Column(nullable = false,unique = true)
	public String iccid;
	@Column(nullable = false,unique = true)
	public String customerEmail;
	@Column(nullable = false)
	public boolean active;
	public SimCardActivator() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SimCardActivator(String iccid, String customerEmail, boolean active) {
		super();
		this.iccid = iccid;
		this.customerEmail = customerEmail;
		this.active = active;
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
	
	
	
}
