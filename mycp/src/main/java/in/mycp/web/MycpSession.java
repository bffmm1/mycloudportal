package in.mycp.web;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class MycpSession {

	public String firstName="";
	public String lastName="";
	public String email="";
	public Date loggedInDate;
	public String company="";
	public String role="";
	public int companyId=0;
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getLoggedInDate() {
		return loggedInDate;
	}
	public void setLoggedInDate(Date loggedInDate) {
		this.loggedInDate = loggedInDate;
	}
	
	
}
