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
