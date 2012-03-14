package in.mycp.remote;



import in.mycp.domain.IpAddressP;

import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * 
 * @author Charudath.Ramegowda
 *
 */

@RemoteProxy(name = "IpAddressP")
public class IpAddressService {

    private static final Logger log = Logger.getLogger(IpAddressService.class.getName());

	
	
    	@RemoteMethod
		public void save(IpAddressP instance){
			try{
				instance.persist();
			}catch (Exception e) {
				log.error(e.getMessage());//e.printStackTrace();
			}
		}//end of save(IpAddressP
		
    	@RemoteMethod
		public IpAddressP saveOrUpdate(IpAddressP instance){
			try{
				if(instance.getId()>0){
					IpAddressP instance_local = IpAddressP.findIpAddressP(instance.getId());
					
				}
				return instance.merge();
			}catch (Exception e) {
				log.error(e.getMessage());//e.printStackTrace();
			}
			return null;
		}//end of saveOrUpdate(IpAddressP
		
    	@RemoteMethod
		public void remove(int id){
			try{
				IpAddressP.findIpAddressP(id).remove();
			}catch (Exception e) {
				log.error(e.getMessage());//e.printStackTrace();
			}
		}//end of method remove(int id
		
    	@RemoteMethod
		public IpAddressP findById(int id){
			try{
				return IpAddressP.findIpAddressP(id);
			}catch (Exception e) {
				log.error(e.getMessage());//e.printStackTrace();
			}
			return null;
		}//end of method findById(int id

    	@RemoteMethod
		public List findAll(){
			try{
				return IpAddressP.findAllIpAddressPs();
				}catch (Exception e) {
				log.error(e.getMessage());//e.printStackTrace();
			}
			return null;
		}//end of method findAll
   }//end of class IpAddressPController
   
   

