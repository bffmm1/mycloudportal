package in.mycp.remote;


import in.mycp.domain.MeterMetric;

import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
/**
 * 
 * @author Charudath.Ramegowda
 *
 */

@RemoteProxy(name="MeterMetricService")
public class MeterMetricService  {

	private static final Logger log = Logger.getLogger(MeterMetricService.class
			.getName());

	
	@RemoteMethod
	public void save(MeterMetric instance) {
		try {
			instance.persist();
			} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of save(Infra

	@RemoteMethod
	public MeterMetric saveOrUpdate(MeterMetric instance) {
		try {
			return instance.merge();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of saveOrUpdate(Infra

	@RemoteMethod
	public void remove(int id) {
		try {
			MeterMetric.findMeterMetric(id).remove();
			
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of method remove(int id

	@RemoteMethod
	public MeterMetric findById(int id) {
		try {
			return MeterMetric.findMeterMetric(id);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List findAll() {
		try {
			return MeterMetric.findAllMeterMetrics();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll

	
}// end of class MeterMetric

