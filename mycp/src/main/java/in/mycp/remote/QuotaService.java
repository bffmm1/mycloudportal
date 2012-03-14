package in.mycp.remote;



import in.mycp.domain.MeterMetric;
import in.mycp.domain.Quota;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
/**
 * 
 * @author Charudath.Ramegowda
 *
 */

@RemoteProxy(name="QuotaService")
public class QuotaService  {

	private static final Logger log = Logger.getLogger(QuotaService.class
			.getName());

	
	@RemoteMethod
	public void save(Quota instance) {
		try {
			instance.persist();
			} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of save(Infra

	@RemoteMethod
	public Quota saveOrUpdate(Quota instance) {
		try {
			//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			
			//formatter.parse(instance.getStartdate());
			
			//formatter.getCalendar().getTime();
			
			MeterMetric mm = MeterMetric.findMeterMetric(instance.getMeterMetric().getId());
			instance.setMeterMetric(mm);
			
			/*Quota q = new Quota();
			q.setEnddate(instance.getEnddate());
			q.setStartdate(instance.getStartdate());
			q.setLimit(instance.getLimit());
			q.setName(instance.getName());
			q.setMeterMetric(mm);
			System.out.println("saveOrUpdate");
			
			Quota q1 = new Quota();
			q1.setName("ggggggg");
			q1.setEnddate(new Date());*/
			return instance.merge();   
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of saveOrUpdate(Infra

	@RemoteMethod
	public void remove(int id) {
		try {
			Quota.findQuota(id).remove();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of method remove(int id

	@RemoteMethod
	public Quota findById(int id) {
		try {
			return Quota.findQuota(id);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List findAll() {
		try {
			return Quota.findAllQuotas();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll

	
}// end of class Quota

