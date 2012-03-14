package in.mycp.remote;




import in.mycp.domain.Company;
import in.mycp.domain.Department;
import in.mycp.domain.RegionP;
import in.mycp.utils.Commons;

import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
/**
 * 
 * @author Charudath.Ramegowda
 *
 */

@RemoteProxy(name="RegionService")
public class RegionService  {

	private static final Logger log = Logger.getLogger(RegionService.class
			.getName());

	
	

	@RemoteMethod
	public RegionP saveOrUpdate(RegionP instance) {
		try {
			instance.setCompany(Company.findCompany(instance.getCompany().getId()));
			return instance.merge();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of saveOrUpdate(Infra

	@RemoteMethod
	public String remove(int id) {
		try {
			RegionP.findRegionP(id).remove();
			return "Removed Region "+id;
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return "Cannot Remove Region "+id+". look into logs.";
	}// end of method remove(int id

	@RemoteMethod
	public RegionP findById(int id) {
		try {
			return RegionP.findRegionP(id);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List<RegionP> findAll() {
		try {
			
			if(Commons.getCurrentUser().getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN+"")){
				return RegionP.findAllRegionPs();
			}else{
				return RegionP.findRegionPsByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
			}
			
			
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll

	
}// end of class RegionP

