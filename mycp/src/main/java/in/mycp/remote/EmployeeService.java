//My Cloud Portal - Self Service Portal for the cloud.
//This file is part of My Cloud Portal.
//
//My Cloud Portal is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, version 3 of the License.
//
//My Cloud Portal is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with My Cloud Portal.  If not, see <http://www.gnu.org/licenses/>.

package in.mycp.remote;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * 
 * @author Charudath Doddanakatte
 * @author cgowdas@gmail.com
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

