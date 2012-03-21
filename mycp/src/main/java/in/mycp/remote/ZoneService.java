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

import in.mycp.domain.AvailabilityZoneP;
import in.mycp.domain.Company;
import in.mycp.domain.Infra;
import in.mycp.domain.VolumeInfoP;
import in.mycp.utils.Commons;

import java.util.ArrayList;
import java.util.Iterator;
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
	
	@RemoteProxy(name = "ZoneService")
	public class ZoneService {

		private static final Logger log = Logger.getLogger(ZoneService.class
				.getName());
		
		
		
		@RemoteMethod
		public List<AvailabilityZoneP> findAll() {
			try {
				List<AvailabilityZoneP> az= new ArrayList<AvailabilityZoneP>();
				List<Infra> is = null;
				if(Commons.getCurrentUser().getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN+"")){
					is =  Infra.findAllInfras();
				}else{
					is =  Infra.findInfrasByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
				}
				StringBuffer simplecheck =new StringBuffer();
				for (Iterator iterator = is.iterator(); iterator.hasNext();) {
					Infra infra = (Infra) iterator.next();
					
					if(simplecheck.indexOf(infra.getZone())<0){
						AvailabilityZoneP a = new AvailabilityZoneP();
						a.setName(infra.getZone());
						az.add(a);
						simplecheck.append(infra.getZone());
					}
				}
				
			//	return AvailabilityZoneP.findAllAvailabilityZonePs();
				return az;
			} catch (Exception e) {
				log.error(e.getMessage());//e.printStackTrace();
			}
			return null;
		}// end of method findAll
}
