package in.mycp.remote;

import in.mycp.domain.AvailabilityZoneP;
import in.mycp.domain.VolumeInfoP;

import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;



	
	@RemoteProxy(name = "ZoneService")
	public class ZoneService {

		private static final Logger log = Logger.getLogger(ZoneService.class
				.getName());
		
		
		
		@RemoteMethod
		public List findAll() {
			try {
				return AvailabilityZoneP.findAllAvailabilityZonePs();
			} catch (Exception e) {
				log.error(e.getMessage());//e.printStackTrace();
			}
			return null;
		}// end of method findAll
}
