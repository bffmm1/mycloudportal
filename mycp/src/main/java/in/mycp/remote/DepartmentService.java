package in.mycp.remote;

import in.mycp.domain.Company;
import in.mycp.domain.Department;
import in.mycp.domain.InstanceP;
import in.mycp.domain.Manager;
import in.mycp.domain.Quota;
import in.mycp.domain.User;
import in.mycp.utils.Commons;
import in.mycp.web.MycpSession;

import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Charudath.Ramegowda
 * 
 */

@RemoteProxy(name = "DepartmentService")
public class DepartmentService {

	private static final Logger log = Logger.getLogger(DepartmentService.class
			.getName());

	

	@RemoteMethod
	public Department saveOrUpdate(Department instance) {
		try {
			
			instance.setCompany(Company.findCompany(instance.getCompany().getId()));
			//instance.setManager(Manager.findManager(instance.getManager().getId()));
			//instance.setQuota(Quota.findQuota(instance.getQuota().getId()));
			
			return instance.merge();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}// end of saveOrUpdate(Department

	@RemoteMethod
	public String remove(int id) {
		try {
			Department.findDepartment(id).remove();
			return "Department removed "+id;
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return "Error removing Department "+id+". Look into logs.";
	}// end of method remove(int id

	@RemoteMethod
	public Department findById(int id) {
		try {
			return Department.findDepartment(id);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List<Department> findAll() {
		try {
			if(Commons.getCurrentUser().getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN+"")){
				return Department.findAllDepartments();
			}else{
				return Department.findDepartmentsByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
			}
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}// end of method findAll

}// end of class Department

