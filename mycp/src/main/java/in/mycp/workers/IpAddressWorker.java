package in.mycp.workers;

import in.mycp.domain.AddressInfoP;
import in.mycp.domain.Infra;
import in.mycp.domain.InstanceP;
import in.mycp.utils.Commons;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.xerox.amazonws.ec2.AddressInfo;
import com.xerox.amazonws.ec2.Jec2;
import com.xerox.amazonws.ec2.ReservationDescription;
import com.xerox.amazonws.ec2.ReservationDescription.Instance;

@Component("ipAddressWorker")
public class IpAddressWorker extends Worker {

	protected static Logger logger = Logger.getLogger(IpAddressWorker.class);

	@Async
	public void allocateAddress(final Infra infra, final AddressInfoP addressInfoP) {
		String threadName = Thread.currentThread().getName();

		try {
			logger.debug("threadName "+threadName+" started.");
			Jec2 ec2 = getNewJce2(infra);
			String newIpAddress = null;
			try {
				newIpAddress = ec2.allocateAddress();	
				logger.info("got new Address "+newIpAddress);
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
				if(e.getMessage().indexOf("Permission denied while") > -1){
					throw new Exception("Permission denied while trying to get address");
				}
			}
			
			AddressInfoP addressInfoPLocal = null;
			try {
				addressInfoPLocal = AddressInfoP.findAddressInfoP(addressInfoP.getId());	
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			
			String address_str=null;
			
			int START_SLEEP_TIME = 10000;
			while(address_str == null){
				logger.info("Ipaddress " + newIpAddress +" still getting created; sleeping "+ START_SLEEP_TIME + "ms");
				Thread.sleep(START_SLEEP_TIME);
				try {
					//address_str = ec2.describeAddresses(Collections.singletonList(newIpAddress)).get(0).getPublicIp();
					List<AddressInfo> adrsses =  ec2.describeAddresses(new ArrayList<String>());
					for (Iterator iterator = adrsses.iterator(); iterator
							.hasNext();) {
						AddressInfo addressInfo = (AddressInfo) iterator.next();
						if(newIpAddress.equals(addressInfo.getPublicIp())
								&& addressInfo.getInstanceId().startsWith("available")){
						//euca logic
							address_str = addressInfo.getPublicIp();
							break;
						}
						
					}
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
					address_str=e.getMessage();
				}	
			}
			
			if(address_str.equals(newIpAddress)){
				addressInfoPLocal.setInstanceId("available");
				addressInfoPLocal.setPublicIp(newIpAddress);
				addressInfoPLocal.setStatus(Commons.ipaddress_STATUS.taken+"");
				addressInfoPLocal = addressInfoPLocal.merge();
				
				setAssetStartTime(addressInfoPLocal.getAsset());
				
			}

		} catch (Exception e) {
			logger.error(e.getMessage());//e.printStackTrace();
			setAssetEndTime(addressInfoP.getAsset());
		}

	}//end allocateAddress
	
	@Async
	public void releaseAddress(final Infra infra, final AddressInfoP addressInfoP) {
		String threadName = Thread.currentThread().getName();

		try {
			logger.debug("threadName "+threadName+" started.");
			Jec2 ec2 = getNewJce2(infra);
			String ipToMatch = addressInfoP.getPublicIp();
			try {
				logger.info("releasing address "+addressInfoP.getPublicIp());
				ec2.releaseAddress(addressInfoP.getPublicIp());	
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
				if(e.getMessage().indexOf("Permission denied while trying to release address") > -1){
					throw new Exception("Permission denied while trying to release address");
				}
			}
			
			AddressInfoP addressInfoPLocal = null;
			try {
				addressInfoPLocal = AddressInfoP.findAddressInfoPsByPublicIpEquals(addressInfoP.getPublicIp()).getSingleResult();	
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			AddressInfo addressInfoLocal=new AddressInfo("", "");
			
			
			int START_SLEEP_TIME = 10000;
			while(addressInfoLocal != null){
				
				try {
					List<AddressInfo> adrsses =  ec2.describeAddresses(new ArrayList<String>());
					for (Iterator iterator = adrsses.iterator(); iterator
							.hasNext();) {
						AddressInfo addressInfo = (AddressInfo) iterator.next();
						if(ipToMatch.equals(addressInfo.getPublicIp())
								&& addressInfo.getInstanceId().equals("nobody")){
						//euca logic
							addressInfoLocal = null;
							break;
						}
						
					}
					
					logger.info("Ipaddress " + addressInfoP.getPublicIp() +" still getting released; sleeping "+ START_SLEEP_TIME + "ms");
					Thread.sleep(START_SLEEP_TIME);	
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
					addressInfoLocal=null;
				}	
			}
			
			if(addressInfoLocal ==null){
				//addressInfoPLocal.remove();
				addressInfoPLocal.setInstanceId("nobody");
				addressInfoPLocal.setStatus(Commons.ipaddress_STATUS.free+"");
				addressInfoPLocal.setReason("Released this address on "+new Date());
				addressInfoPLocal.merge();
				
				setAssetEndTime(addressInfoPLocal.getAsset());
			}

		} catch (Exception e) {
			logger.error(e.getMessage());//e.printStackTrace();
		}

	}//end of releaseAddress

	
	@Async
	public void associateAddress(final Infra infra, final AddressInfoP addressInfoP) {
		try {
			Jec2 ec2 = getNewJce2(infra);
			
			try {
				logger.info("associateAddress address "+addressInfoP.getPublicIp()+" to instance "+addressInfoP.getInstanceId());
				ec2.associateAddress(addressInfoP.getInstanceId(), addressInfoP.getPublicIp());	
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
				if(e.getMessage().indexOf("Permission denied while") > -1){
					throw new Exception("Permission denied.");
				}
			}
			String instanceIdOrig = addressInfoP.getInstanceId();
			if(StringUtils.contains(instanceIdOrig, " ")){
				instanceIdOrig = StringUtils.substringBefore(instanceIdOrig, " ");
			}
			
			InstanceP orig_compute = null;
			try {
				orig_compute = InstanceP.findInstancePsByInstanceIdEquals(instanceIdOrig).getSingleResult();	
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			
			AddressInfoP addressInfoP4PublicIp = null;
			try {
				addressInfoP4PublicIp = AddressInfoP.findAddressInfoPsByPublicIpEquals(addressInfoP.getPublicIp()).getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			
			AddressInfoP addressInfoP4InstanceId = null;
			try {
				addressInfoP4InstanceId = AddressInfoP.findAddressInfoPsByInstanceIdLike(instanceIdOrig).getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			
			boolean match=false;
			int START_SLEEP_TIME = 5000;
			outer: while(!match){
				
				try {
			
					List<String> params = new ArrayList<String>();
					List<ReservationDescription> instances = ec2
							.describeInstances(params);
					for (ReservationDescription res : instances) {
						if (res.getInstances() != null) {
							HashSet<InstanceP> instancesP = new HashSet<InstanceP>();
							for (Instance inst : res.getInstances()) {
								System.out.println(inst.getInstanceId()+" "+orig_compute.getInstanceId()+" "+inst.getDnsName()+" "+addressInfoP.getPublicIp());
								if(inst.getInstanceId().equals(orig_compute.getInstanceId()) && 
										inst.getDnsName().equals(addressInfoP.getPublicIp())){
									match = true;
									break outer;
								}
								
							}//for (Instance inst : res.getInstances()) {
						}//if (res.getInstances() != null) {
					}//for (ReservationDescription res : instances) {
					
					logger.info("Ipaddress " + addressInfoP.getPublicIp() +" getting associated; sleeping "+ START_SLEEP_TIME + "ms");
					Thread.sleep(START_SLEEP_TIME);
					
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
					//addressInfoLocal=null;
				}	
			}
			if(match == true){
				try {
					orig_compute.setDnsName(addressInfoP.getPublicIp());
					orig_compute.merge();
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
				}
				
				try {
					addressInfoP4PublicIp.setAssociated(true);
					addressInfoP4PublicIp.setInstanceId(orig_compute.getInstanceId());
					addressInfoP4PublicIp.setStatus(Commons.ipaddress_STATUS.taken+"");
					addressInfoP4PublicIp.merge();
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
				}
				
				try {
					addressInfoP4InstanceId.setAssociated(false);
					addressInfoP4InstanceId.setInstanceId("somebody");
					addressInfoP4InstanceId.setStatus(Commons.ipaddress_STATUS.taken+"");
					addressInfoP4InstanceId.merge();
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());//e.printStackTrace();
		}

	}//end of associateAddress

	
	@Async
	public void disassociateAddress(final Infra infra, final AddressInfoP addressInfoP) {
		String threadName = Thread.currentThread().getName();

		try {
			logger.debug("threadName "+threadName+" started for disassociateAddress");
			Jec2 ec2 = getNewJce2(infra);
			
			try {
				ec2.disassociateAddress(addressInfoP.getPublicIp());
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
				if(e.getMessage().indexOf("Permission denied while") > -1){
					throw new Exception("Permission denied.");
				}
			}
			String instanceIdOrig = addressInfoP.getInstanceId();
			if(StringUtils.contains(instanceIdOrig, " ")){
				instanceIdOrig = StringUtils.substringBefore(instanceIdOrig, " ");
			}
			
			InstanceP orig_compute = null;
			try {
				orig_compute = InstanceP.findInstancePsByInstanceIdEquals(instanceIdOrig).getSingleResult();	
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			
			AddressInfoP addressInfoP4PublicIp = null;
			try {
				addressInfoP4PublicIp = AddressInfoP.findAddressInfoPsByPublicIpEquals(addressInfoP.getPublicIp()).getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			
			String newIp="";
			boolean match=false;
			int START_SLEEP_TIME = 5000;
			outer: while(!match){
				
				try {
			
					List<String> params = new ArrayList<String>();
					List<ReservationDescription> instances = ec2
							.describeInstances(params);
					for (ReservationDescription res : instances) {
						if (res.getInstances() != null) {
							HashSet<InstanceP> instancesP = new HashSet<InstanceP>();
							for (Instance inst : res.getInstances()) {
								logger.info(inst.getInstanceId()+" "+orig_compute.getInstanceId()+" "+inst.getDnsName()+" "+addressInfoP.getPublicIp());
								if(inst.getInstanceId().equals(orig_compute.getInstanceId()) && 
										!inst.getDnsName().equals(addressInfoP.getPublicIp())){
									
									newIp  = inst.getDnsName();
									match = true;
									break outer;
								}
								
							}//for (Instance inst : res.getInstances()) {
						}//if (res.getInstances() != null) {
					}//for (ReservationDescription res : instances) {
					
					logger.info("Ipaddress " + addressInfoP.getPublicIp() +" getting disassociated; sleeping "+ START_SLEEP_TIME + "ms");
					Thread.sleep(START_SLEEP_TIME);
					
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
					//addressInfoLocal=null;
				}	
			}
			
			AddressInfoP addressInfoP4NewPublicIp = null;
			try {
				addressInfoP4NewPublicIp = AddressInfoP.findAddressInfoPsByPublicIpEquals(newIp).getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			
			if(match == true){
				try {
					orig_compute.setDnsName(newIp);
					orig_compute.merge();
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
				}
				
				try {
					addressInfoP4PublicIp.setAssociated(false);
					addressInfoP4PublicIp.setInstanceId("available");
					addressInfoP4PublicIp.setStatus(Commons.ipaddress_STATUS.free+"");
					addressInfoP4PublicIp.merge();
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
				}
				
				try {
					addressInfoP4NewPublicIp.setAssociated(false);
					addressInfoP4NewPublicIp.setInstanceId(orig_compute.getInstanceId());
					addressInfoP4PublicIp.setStatus(Commons.ipaddress_STATUS.taken+"");
					addressInfoP4NewPublicIp.merge();
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
				}
			}
			
			

		} catch (Exception e) {
			logger.error(e.getMessage());//e.printStackTrace();
		}

	}//enf disassociateAddress

}
