package in.mycp.web;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class SignupDTO {
	
	public String email;
	public String name;
	public String password;
	public String organization;
	public String captchaResp;
	
	
	
	public String getCaptchaResp() {
		return captchaResp;
	}
	public void setCaptchaResp(String captchaResp) {
		this.captchaResp = captchaResp;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	

}
