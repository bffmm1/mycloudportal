package in.mycp.remote;

import in.mycp.domain.Manager;

import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * 
 * @author Charudath.Ramegowda
 * 
 */

@RemoteProxy(name = "ManagerService")
public class ManagerService {

	private static final Logger log = Logger.getLogger(ManagerService.class
			.getName());

	@RemoteMethod
	public void save(Manager instance) {
		try {
			instance.persist();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of save(Manager

	@RemoteMethod
	public Manager saveOrUpdate(Manager instance) {
		try {
			return instance.merge();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of saveOrUpdate(Manager

	@RemoteMethod
	public void remove(int id) {
		try {
			Manager.findManager(id).remove();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of method remove(int id

	@RemoteMethod
	public Manager findById(int id) {
		try {
			return Manager.findManager(id);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List findAll() {
		try {
			return Manager.findAllManagers();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll

}// end of class Manager

