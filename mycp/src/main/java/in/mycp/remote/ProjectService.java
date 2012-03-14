package in.mycp.remote;

import in.mycp.domain.Company;
import in.mycp.domain.Department;
import in.mycp.domain.Project;
import in.mycp.domain.Quota;
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

@RemoteProxy(name = "ProjectService")
public class ProjectService {

	private static final Logger log = Logger.getLogger(ProjectService.class
			.getName());

	@RemoteMethod
	public void save(Project instance) {
		try {
			instance.persist();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of save(Project

	@RemoteMethod
	public Project saveOrUpdate(Project instance) {
		try {
			
			//instance.setCompany(Company.findCompany(instance.getCompany().getId()));
			instance.setDepartment(Department.findDepartment(instance.getDepartment().getId()));
			//instance.setQuota(Quota.findQuota(instance.getQuota().getId()));
			
			
			return instance.merge();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of saveOrUpdate(Project

	@RemoteMethod
	public String remove(int id) {
		try {
			Project.findProject(id).remove();
			return "Removed Project "+id;
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
			return "Error while removing Project "+id;
		}
	}// end of method remove(int id

	@RemoteMethod
	public Project findById(int id) {
		try {
			return Project.findProject(id);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List<Project> findAll() {
		try {
			if(Commons.getCurrentUser().getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN+"")){
				return Project.findAllProjects();
			}else{
				return Project.findProjectsByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
			}
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll

}// end of class Project

