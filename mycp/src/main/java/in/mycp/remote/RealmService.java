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

import in.mycp.domain.Company;
import in.mycp.domain.Manager;
import in.mycp.domain.Project;
import in.mycp.domain.Quota;
import in.mycp.domain.Role;
import in.mycp.domain.User;
import in.mycp.utils.Commons;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * 
 * @author Charudath Doddanakatte
 * @author cgowdas@gmail.com
 *
 */


@RemoteProxy(name = "RealmService")
public class RealmService {

	private static final Logger log = Logger.getLogger(RealmService.class.getName());

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");

	

	@Autowired
	ShaPasswordEncoder passwordEncoder;

	
	@RemoteMethod
	public boolean emailExists(String email) {
		try {
			User user = User.findUsersByEmailEquals(email).getSingleResult();
			
			if(user !=null){
				return true;
			}else{
				return false;
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
			return false;
		}
	}
	
	
	@RemoteMethod
	public User saveOrUpdate(User instance) {
		try {
			instance.setProject(Project.findProject(instance.getProject().getId()));
			//instance.setManager(Manager.findManager(instance.getManager().getId()));
			//instance.setQuota(Quota.findQuota(instance.getQuota().getId()));
			// ShaPasswordEncoder passEncoder = new ShaPasswordEncoder(256);
			if (instance != null && StringUtils.isBlank(instance.getPassword())) {
				throw new Exception("Password cannot be empty");
			}
			System.out.println("instance.getRole().getId() = " + instance.getRole().getId());
			if (instance.getId() == null || instance.getId() < 1) {
				instance.setRegistereddate(new Date());
				instance.setPassword(passwordEncoder.encodePassword(instance.getPassword(), instance.getEmail()));
			} else {
				User localUser = User.findUser(instance.getId());
				instance.setRegistereddate(localUser.getRegistereddate());
				if (!localUser.getPassword().equals(instance.getPassword())) {
					instance.setPassword(passwordEncoder.encodePassword(instance.getPassword(), instance.getEmail()));
				}
			}

			return instance.merge();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of saveOrUpdate(User

	@RemoteMethod
	public void remove(int id) {
		try {
			User.findUser(id).remove();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of method remove(int id

	@RemoteMethod
	public User findById(int id) {
		try {
			return User.findUser(id);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List<User> findAll() {
		try {
			
			if(Commons.getCurrentUser().getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN+"")){
				return User.findAllUsers();
			}else{
				return User.findUsersByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
			}
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll

	@RemoteMethod
	public List<Role> findAllRoles() {
		try {
			// System.out.println(" = "+Role.findAllRoles().size());
			return Role.findAllRoles();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll

}// end of class UserController

