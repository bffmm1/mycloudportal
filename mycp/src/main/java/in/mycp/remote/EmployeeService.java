package in.mycp.remote;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * 
 * @author Charudath.Ramegowda
 * 
 */

@RemoteProxy(name = "EmployeeService")
public class EmployeeService {

	private static final Logger log = Logger.getLogger(EmployeeService.class
			.getName());
/*
	@RemoteMethod
	public void save(Employee instance) {
		try {
			instance.persist();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// end of save(Employee

	@RemoteMethod
	public Employee saveOrUpdate(Employee instance) {
		try {
			
			instance.setManager(Manager.findManager(instance.getManager().getId()));
			instance.setQuota(Quota.findQuota(instance.getQuota().getId()));
			return instance.merge();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}// end of saveOrUpdate(Employee

	@RemoteMethod
	public void remove(int id) {
		try {
			Employee.findEmployee(id).remove();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// end of method remove(int id

	@RemoteMethod
	public Employee findById(int id) {
		try {
			return Employee.findEmployee(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List findAll() {
		try {
			return Employee.findAllEmployees();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}// end of method findAll
*/
}// end of class Employee

