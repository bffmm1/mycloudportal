package in.mycp.workers;

import in.mycp.domain.AddressInfoP;
import in.mycp.domain.AssetType;
import in.mycp.domain.Infra;
import in.mycp.domain.InstanceP;
import in.mycp.domain.ProductCatalog;
import in.mycp.utils.Commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.xerox.amazonws.ec2.InstanceType;
import com.xerox.amazonws.ec2.Jec2;
import com.xerox.amazonws.ec2.LaunchConfiguration;
import com.xerox.amazonws.ec2.ReservationDescription;
import com.xerox.amazonws.ec2.ReservationDescription.Instance;

@Component("computeWorker")
public class ComputeWorker extends Worker {

	protected static Logger logger = Logger.getLogger(ComputeWorker.class);

	@Async
	public void restartCompute(final Infra infra, final int instancePId) {
		try {

			logger.info("restartCompute Euca instance : " + instancePId);
			Jec2 ec2 = getNewJce2(infra);
			List<String> instanceIds = new ArrayList<String>();
			instanceIds.add(InstanceP.findInstanceP(instancePId).getInstanceId());

			ec2.rebootInstances(instanceIds);

			for (Iterator iterator = instanceIds.iterator(); iterator.hasNext();) {
				String instanceId = (String) iterator.next();

				int SLEEP_TIME = 5000;
				int preparationSleepTime = 0;
				ReservationDescription reservationDescription = ec2.describeInstances(Collections.singletonList(instanceId)).get(0);
				Instance instanceEc2 = reservationDescription.getInstances().get(0);
				InstanceP instanceP = InstanceP.findInstancePsByInstanceIdEquals(instanceId).getSingleResult();

				while (!instanceEc2.isRunning() && !instanceEc2.isTerminated()) {
					try {
						instanceP.setState(Commons.REQUEST_STATUS.RESTARTING + "");
						instanceP.merge();
						logger.info("Instance " + instanceEc2.getInstanceId() + " still rebooting; sleeping " + SLEEP_TIME + "ms");
						Thread.sleep(SLEEP_TIME);
						reservationDescription = ec2.describeInstances(Collections.singletonList(instanceEc2.getInstanceId())).get(0);
						instanceEc2 = reservationDescription.getInstances().get(0);
					} catch (Exception e) {
						logger.error(e.getMessage());
						// e.printStackTrace();
					}
				}
				logger.info("out of while loop, instanceEc2.isRunning() " + instanceEc2.isRunning());

				if (instanceEc2.isRunning()) {
					logger.info("EC2 instance is now running");
					if (preparationSleepTime > 0) {
						logger.info("Sleeping " + preparationSleepTime + "ms allowing instance services to start up properly.");
						Thread.sleep(preparationSleepTime);
						logger.info("Instance prepared - proceeding");
					}
					instanceP.setState(Commons.REQUEST_STATUS.running + "");
					instanceP.merge();
				} else {
					instanceP.setState(Commons.REQUEST_STATUS.FAILED + "");
					setAssetEndTime(instanceP.getAsset());
					instanceP.merge();
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());// e.printStackTrace();
		}

	}// end rebootInstances

	@Async
	public void startCompute(final Infra infra, final int instancePId) {
		InstanceP instanceP = null;
		try {
			logger.info("startCompute Euca instance : " + instancePId);
			Jec2 ec2 = getNewJce2(infra);
			List<String> instanceIds = new ArrayList<String>();
			instanceIds.add(InstanceP.findInstanceP(instancePId).getInstanceId());

			ec2.startInstances(instanceIds);

			for (Iterator iterator = instanceIds.iterator(); iterator.hasNext();) {
				String instanceId = (String) iterator.next();

				int SLEEP_TIME = 5000;
				int preparationSleepTime = 0;
				ReservationDescription reservationDescription = ec2.describeInstances(Collections.singletonList(instanceId)).get(0);
				Instance instanceEc2 = reservationDescription.getInstances().get(0);
				instanceP = InstanceP.findInstancePsByInstanceIdEquals(instanceId).getSingleResult();

				while (!instanceEc2.isRunning() && !instanceEc2.isTerminated()) {
					try {
						instanceP.setState(Commons.REQUEST_STATUS.STARTING + "");
						instanceP.merge();
						logger.info("Instance " + instanceEc2.getInstanceId() + " still booting; sleeping " + SLEEP_TIME + "ms");
						Thread.sleep(SLEEP_TIME);
						reservationDescription = ec2.describeInstances(Collections.singletonList(instanceEc2.getInstanceId())).get(0);
						instanceEc2 = reservationDescription.getInstances().get(0);
					} catch (Exception e) {
						logger.error(e.getMessage());
						// e.printStackTrace();
					}
				}
				logger.info("out of while loop, instanceEc2.isRunning() " + instanceEc2.isRunning());

				if (instanceEc2.isRunning()) {
					logger.info("EC2 instance is now running");
					if (preparationSleepTime > 0) {
						logger.info("Sleeping " + preparationSleepTime + "ms allowing instance services to start up properly.");
						Thread.sleep(preparationSleepTime);
						logger.info("Instance prepared - proceeding");
					}
					instanceP.setState(Commons.REQUEST_STATUS.running + "");
					instanceP.merge();
					

				} else {
					instanceP.setState(Commons.REQUEST_STATUS.FAILED + "");
					setAssetEndTime(instanceP.getAsset());
					instanceP.merge();
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());// e.printStackTrace();
			try {
				InstanceP instance1 = InstanceP.findInstanceP(instancePId);
				instance1.setState(Commons.REQUEST_STATUS.FAILED + "");
				setAssetEndTime(instance1.getAsset());
				instance1.merge();
			} catch (Exception e2) {
				logger.error(e);
				e.printStackTrace();
			}

		}

	}// end startInstances

	@Async
	public void stopCompute(final Infra infra, final int instancePId) {
		InstanceP instanceP = null;
		try {

			logger.info("stopCompute Euca instance: " + instancePId);
			Jec2 ec2 = getNewJce2(infra);
			List<String> instanceIds = new ArrayList<String>();
			instanceIds.add(InstanceP.findInstanceP(instancePId).getInstanceId());
			ec2.stopInstances(instanceIds, true);

			for (Iterator iterator = instanceIds.iterator(); iterator.hasNext();) {
				String instanceId = (String) iterator.next();

				int SLEEP_TIME = 5000;
				int preparationSleepTime = 0;
				ReservationDescription reservationDescription = ec2.describeInstances(Collections.singletonList(instanceId)).get(0);
				Instance instanceEc2 = reservationDescription.getInstances().get(0);
				instanceP = InstanceP.findInstancePsByInstanceIdEquals(instanceId).getSingleResult();

				while (instanceEc2.isRunning() || instanceEc2.isShuttingDown()) {
					try {
						instanceP.setState(Commons.REQUEST_STATUS.SHUTTING_DOWN + "");
						instanceP.merge();
						logger.info("Instance " + instanceEc2.getInstanceId() + " still shutting down; sleeping " + SLEEP_TIME + "ms");
						Thread.sleep(SLEEP_TIME);
						reservationDescription = ec2.describeInstances(Collections.singletonList(instanceEc2.getInstanceId())).get(0);
						instanceEc2 = reservationDescription.getInstances().get(0);
					} catch (Exception e) {
						logger.error(e.getMessage());
						// e.printStackTrace();
					}
				}
				logger.info("out of while loop, instanceEc2.isRunning() " + instanceEc2.isRunning());

				if (!instanceEc2.isRunning()) {
					logger.info("EC2 instance is now running");
					if (preparationSleepTime > 0) {
						logger.info("Sleeping " + preparationSleepTime + "ms allowing instance services to start up properly.");
						Thread.sleep(preparationSleepTime);
						logger.info("Instance prepared - proceeding");
					}
					instanceP.setState(Commons.REQUEST_STATUS.STOPPED + "");
					instanceP.merge();
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());// e.printStackTrace();
			try {
				InstanceP instance1 = InstanceP.findInstanceP(instancePId);
				instance1.setState(Commons.REQUEST_STATUS.FAILED + "");
				setAssetEndTime(instance1.getAsset());
				instance1.merge();
			} catch (Exception e2) {
				logger.error(e);
				e.printStackTrace();
			}
		}

	}// end stopInstances

	@Async
	public void terminateCompute(final Infra infra, final int instancePId) {

		try {

			logger.info("terminateCompute Euca instance : " + instancePId);
			Jec2 ec2 = getNewJce2(infra);
			List<String> instanceIds = new ArrayList<String>();
			instanceIds.add(InstanceP.findInstanceP(instancePId).getInstanceId());
			try {
				ec2.terminateInstances(instanceIds);
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}

			for (Iterator iterator = instanceIds.iterator(); iterator.hasNext();) {
				String instanceId = (String) iterator.next();

				int SLEEP_TIME = 5000;
				int preparationSleepTime = 0;
				ReservationDescription reservationDescription = null;
				try {
					reservationDescription = ec2.describeInstances(Collections.singletonList(instanceId)).get(0);
				
					
				} catch (Exception e) {
					if(e.getMessage().contains("Index: 0, Size: 0")){
						throw new Exception("this instance is no more in the backend infra"); 
					}
				}
				Instance instanceEc2 = reservationDescription.getInstances().get(0);
				InstanceP instanceP = InstanceP.findInstancePsByInstanceIdEquals(instanceId).getSingleResult();

				while (!instanceEc2.isTerminated()) {
					try {
						instanceP.setState(Commons.REQUEST_STATUS.TERMINATING + "");
						instanceP.merge();
						logger.info("Instance " + instanceEc2.getInstanceId() + " pending termination; sleeping " + SLEEP_TIME + "ms");
						Thread.sleep(SLEEP_TIME);
						reservationDescription = ec2.describeInstances(Collections.singletonList(instanceEc2.getInstanceId())).get(0);
						instanceEc2 = reservationDescription.getInstances().get(0);
					} catch (Exception e) {
						logger.error(e.getMessage());
						// e.printStackTrace();
					}
				}
				logger.info("out of while loop, instanceEc2.isTerminated() " + instanceEc2.isTerminated());

				if (instanceEc2.isTerminated()) {
					logger.info("EC2 instance is now Terminated");
					if (preparationSleepTime > 0) {
						logger.info("Sleeping " + preparationSleepTime + "ms allowing instance services to start up properly.");
						Thread.sleep(preparationSleepTime);
						logger.info("Instance prepared - proceeding");
					}
					instanceP.setState(Commons.REQUEST_STATUS.TERMINATED + "");
					instanceP.merge();

					setAssetEndTime(instanceP.getAsset());

					try {
						AddressInfoP a = AddressInfoP.findAddressInfoPsByInstanceIdEquals(instanceP.getInstanceId()).getSingleResult();
						setAssetEndTime(a.getAsset());
						a.remove();
					} catch (Exception e) {
						e.printStackTrace();
						e.printStackTrace();
						logger.error(e);
					}

				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage()); //e.printStackTrace();
			
			try {
				InstanceP instance1 = InstanceP.findInstanceP(instancePId);
				instance1.setState(Commons.REQUEST_STATUS.FAILED + "");
				setAssetEndTime(instance1.getAsset());
				instance1.merge();
			} catch (Exception e2) {
				logger.error(e2);
				e2.printStackTrace();
			}
			
		}

	}// end terminateInstances

	@Async
	public void createrCompute(final Infra infra, final InstanceP instance) {

		InstanceP instanceLocal = null;
		try {
			String imageName = instance.getImageId();
			String keypairName = instance.getKeyName();
			String groupName = instance.getGroupName();
			String instanceType = instance.getInstanceType();

			logger.info("Launching Euca instance " + instance.getId() + " for image: " + imageName);

			Jec2 ec2 = getNewJce2(infra);

			LaunchConfiguration launchConfiguration = new LaunchConfiguration(imageName);
			launchConfiguration.setKeyName(keypairName);
			launchConfiguration.setSecurityGroup(Collections.singletonList(groupName));
			launchConfiguration.setInstanceType(InstanceType.getTypeFromString(instanceType));
			launchConfiguration.setMinCount(1);
			launchConfiguration.setMaxCount(1);

			ReservationDescription reservationDescription = ec2.runInstances(launchConfiguration);
			instanceLocal = InstanceP.findInstanceP(instance.getId());

			Instance instanceEc2 = reservationDescription.getInstances().get(0);
			instanceLocal.setInstanceId(instanceEc2.getInstanceId());
			instanceLocal.setDnsName(instanceEc2.getDnsName());
			instanceLocal.setLaunchTime(instanceEc2.getLaunchTime().getTime());
			instanceLocal.setKernelId(instanceEc2.getKernelId());
			instanceLocal.setRamdiskId(instanceEc2.getRamdiskId());
			instanceLocal.setPlatform(instanceEc2.getPlatform());
			instanceLocal.setState(Commons.REQUEST_STATUS.STARTING + "");

			instanceLocal = instanceLocal.merge();

			int INSTANCE_START_SLEEP_TIME = 5000;
			int preparationSleepTime = 0;
			// int sigVer= infra.getSignatureVersion();

			while (!instanceEc2.isRunning() && !instanceEc2.isTerminated()) {
				try {
					logger.info("Instance " + instanceEc2.getInstanceId() + " still starting up; sleeping " + INSTANCE_START_SLEEP_TIME
							+ "ms");
					Thread.sleep(INSTANCE_START_SLEEP_TIME);
					// to avoid Message replay detected. Same signature was used
					// within the last 5 minutes
					// sigVer= sigVer+1;
					// ec2.setSignatureVersion(sigVer);

					reservationDescription = ec2.describeInstances(Collections.singletonList(instanceEc2.getInstanceId())).get(0);
					instanceEc2 = reservationDescription.getInstances().get(0);
				} catch (Exception e) {
					logger.error(e.getMessage());
					// e.printStackTrace();
				}
			}
			logger.info("out of while loop, instanceEc2.isRunning() " + instanceEc2.isRunning());

			if (instanceEc2.isRunning()) {
				logger.info("EC2 instance is now running");
				if (preparationSleepTime > 0) {
					logger.info("Sleeping " + preparationSleepTime + "ms allowing instance services to start up properly.");
					Thread.sleep(preparationSleepTime);
					logger.info("Instance prepared - proceeding");
				}

				instanceLocal.setInstanceId(instanceEc2.getInstanceId());
				instanceLocal.setDnsName(instanceEc2.getDnsName());
				instanceLocal.setLaunchTime(instanceEc2.getLaunchTime().getTime());
				instanceLocal.setKernelId(instanceEc2.getKernelId());
				instanceLocal.setRamdiskId(instanceEc2.getRamdiskId());
				instanceLocal.setPlatform(instanceEc2.getPlatform());
				instanceLocal.setState(Commons.REQUEST_STATUS.running + "");

				instanceLocal = instanceLocal.merge();

					/*AddressInfoP addressInfoP = new AddressInfoP();
					addressInfoP.setInstanceId(instanceLocal.getInstanceId());
					addressInfoP.setName("Ip for " + instanceLocal.getName());
					addressInfoP.setPublicIp(instanceLocal.getDnsName());
					addressInfoP.setStatus(Commons.ipaddress_STATUS.associated + "");
					addressInfoP = addressInfoP.merge();*/
					
					// create an addressInfo object for this compute's IP.
					try {
						AddressInfoP a = new AddressInfoP();
						
						ProductCatalog pc = ProductCatalog.findProductCatalogsByProductTypeAndCompany(Commons.ProductType.IpAddress.getName(),
								instanceLocal.getAsset().getUser().getProject().getDepartment().getCompany()).getSingleResult();

						a.setAsset(Commons.getNewAsset(AssetType.findAssetTypesByNameEquals(Commons.ProductType.IpAddress + "")
								.getSingleResult(), instanceLocal.getAsset().getUser(), pc));
						a.setAssociated(true);
						a.setInstanceId(instanceLocal.getInstanceId());
						a.setName("Ip for " + instanceLocal.getName());
						a.setPublicIp(instanceLocal.getDnsName());
						a.setStatus(Commons.ipaddress_STATUS.associated + "");
						a.setReason("Automatic Ip addres assigned");
						setAssetStartTime(a.getAsset());
						a.merge();
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e);
					}
					
					setAssetStartTime(instanceLocal.getAsset());

				logger.info("Done creating " + instance.getName() + " and assigning ip " + instanceEc2.getDnsName());
			} else {

				instanceLocal.setInstanceId(instanceEc2.getInstanceId());
				instanceLocal.setDnsName(instanceEc2.getDnsName());
				instanceLocal.setLaunchTime(instanceEc2.getLaunchTime().getTime());
				instanceLocal.setKernelId(instanceEc2.getKernelId());
				instanceLocal.setRamdiskId(instanceEc2.getRamdiskId());
				instanceLocal.setPlatform(instanceEc2.getPlatform());
				instanceLocal.setState(Commons.REQUEST_STATUS.FAILED + "");
				setAssetEndTime(instanceLocal.getAsset());
				instanceLocal = instanceLocal.merge();

				setAssetEndTime(instanceLocal.getAsset());

				throw new IllegalStateException("Failed to start a new instance");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error while creating instance");
			try {
				InstanceP instance1 = InstanceP.findInstanceP(instance.getId());
				instance1.setState(Commons.REQUEST_STATUS.FAILED + "");
				setAssetEndTime(instance1.getAsset());
				instance1.merge();

				setAssetEndTime(instance1.getAsset());

			} catch (Exception e2) {
				logger.error(e);
				e.printStackTrace();
			}
			Thread.currentThread().interrupt();
		}

	}// work

}
