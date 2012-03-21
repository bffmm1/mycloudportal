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
import in.mycp.utils.Commons;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * 
 * @author Charudath Doddanakatte
 * @author cgowdas@gmail.com
 *
 */

@RemoteProxy(name = "CompanyService")
public class CompanyService {

	private static final Logger log = Logger.getLogger(CompanyService.class.getName());

	@RemoteMethod
	public void save(Company instance) {
		try {
			instance.persist();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		}
	}// end of save(Company

	@RemoteMethod
	public Company saveOrUpdate(Company instance) {
		try {
			return instance.merge();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}// end of saveOrUpdate(Company

	@RemoteMethod
	public String remove(int id) {
		try {
			Company.findCompany(id).remove();
			return "Removed Company " + id;
		} catch (Exception e) {
			// System.out.println(" = "+e.getMessage());
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return "Error removing Company " + id + ". Look into logs.";
	}// end of method remove(int id

	@RemoteMethod
	public Company findById(int id) {
		try {
			return Company.findCompany(id);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List<Company> findAll() {
		try {
			if (Commons.getCurrentUser().getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN + "")) {
				return Company.findAllCompanys();
				
			} else {
				List<Company> comapnies = new ArrayList<Company>();
				comapnies.add(Company.findCompany(Commons.getCurrentSession().getCompanyId()));
				return comapnies;
			}

		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}// end of method findAll
	
	@RemoteMethod
	public List<String> findAllDistinctCurrency() {
		try {
			return Company.findAllDistinctCurrency();

		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}// end of method findAll
	
	

}// end of class Company

