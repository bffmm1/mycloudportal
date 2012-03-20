package in.mycp.remote;

import in.mycp.domain.Company;
import in.mycp.domain.Department;
import in.mycp.domain.Infra;
import in.mycp.domain.ProductCatalog;
import in.mycp.domain.Project;
import in.mycp.domain.Role;
import in.mycp.domain.User;
import in.mycp.utils.Commons;
import in.mycp.web.SignupDTO;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

/**
 * 
 * @author Charudath.Ramegowda
 * 
 */

@RemoteProxy(name = "SignupService")
public class SignupService {

	private static final Logger log = Logger.getLogger(SignupService.class.getName());

	@Autowired
	ShaPasswordEncoder passwordEncoder;
	@Autowired
	DefaultManageableImageCaptchaService imageCaptchaService;
	
	@RemoteMethod
	public User createUser(SignupDTO instance) {
		try {
			String userCaptchaResponse = instance.getCaptchaResp();
			boolean captchaPassed =  SimpleImageCaptchaServlet.validateResponse(WebContextFactory.get().getHttpServletRequest(), userCaptchaResponse);

			if(captchaPassed){
				log.info("captcha passed");
			}else{
				log.error("captcha failed ");
				Commons.setSessionAttribute("MYCP_SIGNUP_MSG", "<font style=\"color: red;\"> Cannot create User.Captcha failed.</font>");
				return null;
			}
			
			if (instance != null && StringUtils.isBlank(instance.getPassword())) {
				throw new Exception("Password cannot be empty");
			}
			if (instance != null && StringUtils.isBlank(instance.getEmail())) {
				throw new Exception("Email cannot be empty");
			}	
			boolean exists = false;
			try {
				User u = User.findUsersByEmailEquals(instance.getEmail()).getSingleResult();	
				if(u!=null && u.getId()>0){
					exists = true;
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			if( exists){
				throw new Exception("User with this Email already exists");
			}
			
			//create User
			User user = new User();
			user.setFirstName(instance.getName());
			user.setEmail(instance.getEmail());
			user.setActive(true);
			user.setRole(Role.findRolesByNameEquals(Commons.ROLE.ROLE_MANAGER+"").getSingleResult());
			user.setRegistereddate(new Date());
			user.setPassword(passwordEncoder.encodePassword(instance.getPassword(), instance.getEmail()));
			
			//create Company/Account
			Company c = new Company();
			c.setName(instance.getOrganization());
			c.setCurrency("INR");
			c = c.merge();
			
			//create Department
			Department d = new Department();
			d.setCompany(c);
			d.setName("Dept @ "+c.getName());
			d = d.merge();
			
			//create Project
			Project p = new Project();
			p.setDepartment(d);
			p.setName("Proj @ "+d.getName());
			p = p.merge();
			
			user.setProject(p);
			user = user.merge();
			
			//create infra ,
			
			Infra infra = new Infra();
			infra.setName(c.getName()+" Euca Setup");
			infra.setAccessId("change it");
			infra.setSecretKey("change it");
			infra.setServer("change it");
			infra.setCompany(c);
			infra.setDetails("");
			infra.setPort(8773);
			infra.setResourcePrefix("/services/Eucalyptus");
			infra.setSignatureVersion(1);
			infra.setZone("");
			infra = infra.merge();
			
			//create products
			
			createAllProducts(infra);
			
			Commons.setSessionAttribute("MYCP_SIGNUP_MSG", "<font style=\"color: green;\"> User "+user.getEmail()+" created.Please Sign In now.</font>");
			
			return user;
		} catch (Exception e) {
			
			//e.printStackTrace();
			Commons.setSessionAttribute("MYCP_SIGNUP_MSG", "<font style=\"color: red;\"> Cannot create User.Please try later.</font>");
			log.error(e.getMessage());
		}
		return null;
	}// end of save(SignupDTO

	
	public void createAllProducts(Infra i){
		
		ProductCatalog pc = new ProductCatalog();
		pc.setInfra(i);
		pc.setCurrency(i.getCompany().getCurrency());
		//pc.setDetails(details);
		pc.setName(Commons.ProductType.ComputeInstance+" @ "+i.getName());
		pc.setPrice(10);
		pc.setProductType(Commons.ProductType.ComputeInstance.getName());
		pc.merge();
		
		pc = new ProductCatalog();
		pc.setId(0);
		pc.setInfra(i);
		pc.setCurrency(i.getCompany().getCurrency());
		//pc.setDetails(details);
		pc.setName(Commons.ProductType.IpAddress+" @ "+i.getName());
		pc.setPrice(10);
		pc.setProductType(Commons.ProductType.IpAddress.getName());
		pc.merge();
		
		pc = new ProductCatalog();
		pc.setId(0);
		pc.setInfra(i);
		pc.setCurrency(i.getCompany().getCurrency());
		//pc.setDetails(details);
		pc.setName(Commons.ProductType.KeyPair+" @ "+i.getName());
		pc.setPrice(10);
		pc.setProductType(Commons.ProductType.KeyPair.getName());
		pc.merge();
		
		pc = new ProductCatalog();
		pc.setId(0);
		pc.setInfra(i);
		pc.setCurrency(i.getCompany().getCurrency());
		//pc.setDetails(details);
		pc.setName(Commons.ProductType.SecurityGroup+" @ "+i.getName());
		pc.setPrice(10);
		pc.setProductType(Commons.ProductType.SecurityGroup.getName());
		pc.merge();
		
		pc = new ProductCatalog();
		pc.setId(0);
		pc.setInfra(i);
		pc.setCurrency(i.getCompany().getCurrency());
		//pc.setDetails(details);
		pc.setName(Commons.ProductType.Volume+" @ "+i.getName());
		pc.setPrice(10);
		pc.setProductType(Commons.ProductType.Volume.getName());
		pc.merge();
		
		pc = new ProductCatalog();
		pc.setId(0);
		pc.setInfra(i);
		pc.setCurrency(i.getCompany().getCurrency());
		//pc.setDetails(details);
		pc.setName(Commons.ProductType.VolumeSnapshot+" @ "+i.getName());
		pc.setPrice(10);
		pc.setProductType(Commons.ProductType.VolumeSnapshot.getName());
		pc.merge();
	}
	
	@RemoteMethod
	public void cleanupUser(int userId){
		
		User u = User.findUser(userId);
		int projId = u.getProject().getId();
		u.remove();
		Project p = Project.findProject(projId);
		int did = p.getDepartment().getId();
		p.remove();
		Department d = Department.findDepartment(did);
		int compId = d.getCompany().getId();
		d.remove();
		Company c = Company.findCompany(compId);
		
		List<ProductCatalog> proList = ProductCatalog.findProductCatalogsByCompany(c).getResultList();
		for (Iterator iterator = proList.iterator(); iterator.hasNext();) {
			ProductCatalog productCatalog = (ProductCatalog) iterator.next();
			productCatalog.remove();
		}
		
		List<Infra> infras =  Infra.findInfrasByCompany(c).getResultList();
		for (Iterator iterator = infras.iterator(); iterator.hasNext();) {
			Infra infra = (Infra) iterator.next();
			infra.remove();
		}
		
		c.remove();
	}

}// end of class SignupService

