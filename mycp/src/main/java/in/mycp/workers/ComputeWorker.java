package in.mycp.workers;

import in.mycp.domain.AddressInfoP;
import in.mycp.domain.Infra;
import in.mycp.domain.InstanceP;
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
	public void restartCompute(final Infra infra,
			final int instancePId) {
		try {
			
			logger.info("restartCompute Euca instance : "
					+ instancePId);
			Jec2 ec2 = getNewJce2(infra);
			List<String> instanceIds = new ArrayList<String>();
			instanceIds.add(InstanceP.findInstanceP(instancePId).getInstanceId());
			
			ec2.rebootInstances(instanceIds);

			for (Iterator iterator = instanceIds.iterator(); iterator.hasNext();) {
				String instanceId = (String) iterator.next();

				int SLEEP_TIME = 5000;
				int preparationSleepTime = 0;
				ReservationDescription reservationDescription = ec2
						.describeInstances(
								Collections.singletonList(instanceId)).get(0);
				Instance instanceEc2 = reservationDescription.getInstances()
						.get(0);
				InstanceP instanceP = InstanceP
						.findInstancePsByInstanceIdEquals(instanceId)
						.getSingleResult();

				while (!instanceEc2.isRunning() && !instanceEc2.isTerminated()) {
					try {
						instanceP.setState(Commons.REQUEST_STATUS.RESTARTING
								+ "");
						instanceP.merge();
						logger.info("Instance " + instanceEc2.getInstanceId()
								+ " still rebooting; sleeping " + SLEEP_TIME
								+ "ms");
						Thread.sleep(SLEEP_TIME);
						reservationDescription = ec2.describeInstances(
								Collections.singletonList(instanceEc2
										.getInstanceId())).get(0);
						instanceEc2 = reservationDescription.getInstances()
								.get(0);
					} catch (Exception e) {
						logger.error(e.getMessage());
						//e.printStackTrace();
					}
				}
				logger.info("out of while loop, instanceEc2.isRunning() "
						+ instanceEc2.isRunning());

				if (instanceEc2.isRunning()) {
					logger.info("EC2 instance is now running");
					if (preparationSleepTime > 0) {
						logger.info("Sleeping "
								+ preparationSleepTime
								+ "ms allowing instance services to start up properly.");
						Thread.sleep(preparationSleepTime);
						logger.info("Instance prepared - proceeding");
					}
					instanceP.setState(Commons.REQUEST_STATUS.running + "");
					instanceP.merge();
				}else{
					instanceP.setState(Commons.REQUEST_STATUS.FAILED + "");
					instanceP.merge();
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());//e.printStackTrace();
		}

	}// end rebootInstances

	@Async
	public void startCompute(final Infra infra, final int instancePId) {
		String threadName = Thread.currentThread().getName();
		InstanceP instanceP = null;
		try {
			logger.info("startCompute Euca instance : "
					+ instancePId);
			Jec2 ec2 = getNewJce2(infra);
			List<String> instanceIds = new ArrayList<String>();
			instanceIds.add(InstanceP.findInstanceP(instancePId).getInstanceId());
			
			ec2.startInstances(instanceIds);

			for (Iterator iterator = instanceIds.iterator(); iterator.hasNext();) {
				String instanceId = (String) iterator.next();

				int SLEEP_TIME = 5000;
				int preparationSleepTime = 0;
				ReservationDescription reservationDescription = ec2
						.describeInstances(
								Collections.singletonList(instanceId)).get(0);
				Instance instanceEc2 = reservationDescription.getInstances()
						.get(0);
				instanceP = InstanceP
						.findInstancePsByInstanceIdEquals(instanceId)
						.getSingleResult();

				while (!instanceEc2.isRunning() && !instanceEc2.isTerminated()) {
					try {
						instanceP.setState(Commons.REQUEST_STATUS.STARTING+ "");
						instanceP.merge();
						logger.info("Instance " + instanceEc2.getInstanceId()
								+ " still booting; sleeping " + SLEEP_TIME
								+ "ms");
						Thread.sleep(SLEEP_TIME);
						reservationDescription = ec2.describeInstances(
								Collections.singletonList(instanceEc2
										.getInstanceId())).get(0);
						instanceEc2 = reservationDescription.getInstances()
								.get(0);
					} catch (Exception e) {
						logger.error(e.getMessage());
						//e.printStackTrace();
					}
				}
				logger.info("out of while loop, instanceEc2.isRunning() "
						+ instanceEc2.isRunning());

				if (instanceEc2.isRunning()) {
					logger.info("EC2 instance is now running");
					if (preparationSleepTime > 0) {
						logger.info("Sleeping "
								+ preparationSleepTime
								+ "ms allowing instance services to start up properly.");
						Thread.sleep(preparationSleepTime);
						logger.info("Instance prepared - proceeding");
					}
					instanceP.setState(Commons.REQUEST_STATUS.running + "");
					instanceP.merge();
				}else{
					instanceP.setState(Commons.REQUEST_STATUS.FAILED + "");
					instanceP.merge();
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());//e.printStackTrace();
			try {
				InstanceP instance1 = InstanceP.findInstanceP(instancePId);
				instance1.setState(Commons.REQUEST_STATUS.FAILED + "");
				instance1.merge();	
			} catch (Exception e2) {
				logger.error(e);e.printStackTrace();
			}
			
		}

	}// end startInstances

	@Async
	public void stopCompute(final Infra infra, final int instancePId) {
		String threadName = Thread.currentThread().getName();
		InstanceP instanceP=null;
		try {
			
			logger.info("stopCompute Euca instance: "
					+ instancePId);
			Jec2 ec2 = getNewJce2(infra);
			List<String> instanceIds = new ArrayList<String>();
			instanceIds.add(InstanceP.findInstanceP(instancePId).getInstanceId());
			ec2.stopInstances(instanceIds,true);

			for (Iterator iterator = instanceIds.iterator(); iterator.hasNext();) {
				String instanceId = (String) iterator.next();

				int SLEEP_TIME = 5000;
				int preparationSleepTime = 0;
				ReservationDescription reservationDescription = ec2.describeInstances(Collections.singletonList(instanceId)).get(0);
				Instance instanceEc2 = reservationDescription.getInstances().get(0);
				 instanceP = InstanceP.findInstancePsByInstanceIdEquals(instanceId).getSingleResult();

				while (instanceEc2.isRunning() || instanceEc2.isShuttingDown()) {
					try {
						instanceP.setState(Commons.REQUEST_STATUS.SHUTTING_DOWN+ "");
						instanceP.merge();
						logger.info("Instance " + instanceEc2.getInstanceId()
								+ " still shutting down; sleeping " + SLEEP_TIME
								+ "ms");
						Thread.sleep(SLEEP_TIME);
						reservationDescription = ec2.describeInstances(
								Collections.singletonList(instanceEc2
										.getInstanceId())).get(0);
						instanceEc2 = reservationDescription.getInstances()
								.get(0);
					} catch (Exception e) {
						logger.error(e.getMessage());
						//e.printStackTrace();
					}
				}
				logger.info("out of while loop, instanceEc2.isRunning() "
						+ instanceEc2.isRunning());

				if (!instanceEc2.isRunning()) {
					logger.info("EC2 instance is now running");
					if (preparationSleepTime > 0) {
						logger.info("Sleeping "
								+ preparationSleepTime
								+ "ms allowing instance services to start up properly.");
						Thread.sleep(preparationSleepTime);
						logger.info("Instance prepared - proceeding");
					}
					instanceP.setState(Commons.REQUEST_STATUS.STOPPED + "");
					instanceP.merge();
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());//e.printStackTrace();
			try {
				InstanceP instance1 = InstanceP.findInstanceP(instancePId);
				instance1.setState(Commons.REQUEST_STATUS.FAILED + "");
				instance1.merge();	
			} catch (Exception e2) {
				logger.error(e);e.printStackTrace();
			}
		}

	}// end stopInstances

	@Async
	public void terminateCompute(final Infra infra,
			final int instancePId) {

		try {
			
			logger.info("terminateCompute Euca instance : "
					+ instancePId);
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
				ReservationDescription reservationDescription = ec2.describeInstances(Collections.singletonList(instanceId)).get(0);
				Instance instanceEc2 = reservationDescription.getInstances().get(0);
				InstanceP instanceP = InstanceP.findInstancePsByInstanceIdEquals(instanceId).getSingleResult();

				while (!instanceEc2.isTerminated()) {
					try {
						instanceP.setState(Commons.REQUEST_STATUS.TERMINATING+ "");
						instanceP.merge();
						logger.info("Instance " + instanceEc2.getInstanceId()
								+ " pending termination; sleeping " + SLEEP_TIME
								+ "ms");
						Thread.sleep(SLEEP_TIME);
						reservationDescription = ec2.describeInstances(
								Collections.singletonList(instanceEc2
										.getInstanceId())).get(0);
						instanceEc2 = reservationDescription.getInstances()
								.get(0);
					} catch (Exception e) {
						logger.error(e.getMessage());
						//e.printStackTrace();
					}
				}
				logger.info("out of while loop, instanceEc2.isTerminated() "
						+ instanceEc2.isTerminated());

				if (instanceEc2.isTerminated()) {
					logger.info("EC2 instance is now Terminated");
					if (preparationSleepTime > 0) {
						logger.info("Sleeping "
								+ preparationSleepTime
								+ "ms allowing instance services to start up properly.");
						Thread.sleep(preparationSleepTime);
						logger.info("Instance prepared - proceeding");
					}
					instanceP.setState(Commons.REQUEST_STATUS.TERMINATED + "");
					instanceP.merge();
					
					setAssetEndTime(instanceP.getAsset());
					
					try {
						AddressInfoP a =  AddressInfoP.findAddressInfoPsByInstanceIdEquals(instanceP.getInstanceId()).getSingleResult();
						a.remove();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());//e.printStackTrace();
		}

	}// end terminateInstances

	@Async
	public void createrCompute(final Infra infra, final InstanceP instance) {
		String threadName = Thread.currentThread().getName();
		InstanceP instanceLocal=null;
		try {
			logger.debug(threadName + " Started.");
			// "web"; //"charu"; //"emi-836C178A";
			String imageName = instance.getImageId();
			String keypairName = instance.getKeyName();
			String groupName = instance.getGroupName();
			String instanceType = instance.getInstanceType();

			logger.info("Launching Euca instance for image: " + imageName);

			Jec2 ec2 = getNewJce2(infra);

			LaunchConfiguration launchConfiguration = new LaunchConfiguration(
					imageName);
			launchConfiguration.setKeyName(keypairName);
			launchConfiguration.setSecurityGroup(Collections
					.singletonList(groupName));
			launchConfiguration.setInstanceType(InstanceType
					.getTypeFromString(instanceType));
			launchConfiguration.setMinCount(1);
			launchConfiguration.setMaxCount(1);

			ReservationDescription reservationDescription = ec2
					.runInstances(launchConfiguration);
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
					logger.info("Instance " + instanceEc2.getInstanceId()
							+ " still starting up; sleeping "
							+ INSTANCE_START_SLEEP_TIME + "ms");
					Thread.sleep(INSTANCE_START_SLEEP_TIME);
					// to avoid Message replay detected. Same signature was used
					// within the last 5 minutes
					// sigVer= sigVer+1;
					// ec2.setSignatureVersion(sigVer);

					reservationDescription = ec2.describeInstances(
							Collections.singletonList(instanceEc2
									.getInstanceId())).get(0);
					instanceEc2 = reservationDescription.getInstances().get(0);
				} catch (Exception e) {
					logger.error(e.getMessage());
					//e.printStackTrace();
				}
			}
			logger.info("out of while loop, instanceEc2.isRunning() "
					+ instanceEc2.isRunning());

			if (instanceEc2.isRunning()) {
				logger.info("EC2 instance is now running");
				if (preparationSleepTime > 0) {
					logger.info("Sleeping "
							+ preparationSleepTime
							+ "ms allowing instance services to start up properly.");
					Thread.sleep(preparationSleepTime);
					logger.info("Instance prepared - proceeding");
				}

				instanceLocal.setInstanceId(instanceEc2.getInstanceId());
				instanceLocal.setDnsName(instanceEc2.getDnsName());
				instanceLocal.setLaunchTime(instanceEc2.getLaunchTime()
						.getTime());
				instanceLocal.setKernelId(instanceEc2.getKernelId());
				instanceLocal.setRamdiskId(instanceEc2.getRamdiskId());
				instanceLocal.setPlatform(instanceEc2.getPlatform());
				instanceLocal.setState(Commons.REQUEST_STATUS.running + "");

				instanceLocal = instanceLocal.merge();
				
				AddressInfoP addressInfoP = new AddressInfoP();
				addressInfoP.setInstanceId(instanceLocal.getInstanceId());
				addressInfoP.setName("Ip for "+instanceLocal.getName());
				addressInfoP.setPublicIp(instanceLocal.getDnsName());
				addressInfoP.setStatus(Commons.ipaddress_STATUS.taken+"");
				
				addressInfoP = addressInfoP.merge();
				
				setAssetStartTime(instanceLocal.getAsset());
				
				logger.info("Done creating " + instance.getName()
						+ " and assigning ip " + instanceEc2.getDnsName());
			} else {

				instanceLocal.setInstanceId(instanceEc2.getInstanceId());
				instanceLocal.setDnsName(instanceEc2.getDnsName());
				instanceLocal.setLaunchTime(instanceEc2.getLaunchTime()
						.getTime());
				instanceLocal.setKernelId(instanceEc2.getKernelId());
				instanceLocal.setRamdiskId(instanceEc2.getRamdiskId());
				instanceLocal.setPlatform(instanceEc2.getPlatform());
				instanceLocal.setState(Commons.REQUEST_STATUS.FAILED + "");

				instanceLocal = instanceLocal.merge();

				setAssetEndTime(instanceLocal.getAsset());
				
				throw new IllegalStateException(
						"Failed to start a new instance");
			}

			/*
			 * Jec2 ec2 = new Jec2("WKy3rMzOWPouVOxK1p3Ar1C2uRBwa2FBXnCw",
			 * "TTHGvbf5foJQXhb5UpN3u1Kojetw2310GuGQ", false, //
			 * "115.249.231.107", 8773); "192.168.253.84", 8773);
			 * 
			 * ec2.setResourcePrefix("/services/Eucalyptus");
			 * ec2.setSignatureVersion(1);
			 * 
			 * 
			 * ec2.runInstances(imageId, minCount, maxCount, groupSet, userData,
			 * keyName, publicAddr, type, availabilityZone, kernelId, ramdiskId,
			 * blockDeviceMappings) LaunchConfiguration lc = new
			 * LaunchConfiguration(instanceP.getImageId());
			 */// lc.setInstanceType(InstanceType.LARGE);
			/*
			 * instanceP.getInstanceType() instanceP.getInstanceType()
			 */
			/*
			 * ReservationDescription rd = ec2.runInstances(lc);
			 * //instanceP.setr
			 * instanceP.setState(""+Constants.REQUEST_STATUS.RUNNING);
			 * saveOrUpdate(instanceP);
			 *//*
				 * ec2.startInstances(arg0) ec2.stopInstances(arg0, arg1)
				 * ec2.terminateInstances(arg0)
				 * 
				 * ec2.runInstances(lc) ec2.rebootInstances(arg0)
				 */
			/*
			 * instance.setState("" + Constants.WORKFLOW_STATUS.APPROVED);
			 * InstancePHomeVar.saveOrUpdate(instance);
			 */

			logger.debug(threadName + " Done.");
		} catch (Exception e) {
			logger.debug(threadName + " Interrupted.");
			try {
				InstanceP instance1 = InstanceP.findInstanceP(instance.getId());
				instance1.setState(Commons.REQUEST_STATUS.FAILED + "");
				instance1.merge();	
				
				setAssetEndTime(instance1.getAsset());
				
			} catch (Exception e2) {
				logger.error(e);e.printStackTrace();
			}
			Thread.currentThread().interrupt();
		}

	}// work

	/*
	 * protected void destroyInstance(Object ignored) throws Exception { if
	 * (this.instance != null) { log.info("Shutting down instance"); Jec2 jec2 =
	 * new Jec2(awsKey, awsSecretKey);
	 * jec2.terminateInstances(Collections.singletonList
	 * (this.instance.getInstanceId())); } }
	 * 
	 * update image_description_p set imageid = 'emi-81B91418' where
	 * imageLocation='portal/enstratus-dispatcher.img.manifest.xml';
	 * 
	 * update image_description_p set imageid = 'eki-A6651373' where
	 * imageLocation='debian6-64bit/vmlinuz-2.6.27.21-0.1-xen.manifest.xml';
	 * 
	 * update image_description_p set imageid = 'emi-836C178A' where
	 * imageLocation
	 * ='centos5-lamp-64bit.img/centos5-lamp-64bit.img.manifest.xml';
	 * 
	 * update image_description_p set imageid = 'emi-560F12B8' where
	 * imageLocation='windows-2008/windows.2008-iis.img.manifest.xml';
	 * 
	 * update image_description_p set imageid = 'emi-85941840' where
	 * imageLocation='enstratus-dispatcher/enstratus-dispatcher.manifest.xml';
	 * 
	 * update image_description_p set imageid = 'eri-900F130C' where
	 * imageLocation='centos5-64bit/initrd-2.6.27.21-0.1-xen.manifest.xml';
	 * 
	 * update image_description_p set imageid = 'eri-EDC7148F ' where
	 * imageLocation='ununtu-lts-64bit/initrd-2.6.27.21-0.1-xen.manifest.xml';
	 * 
	 * update image_description_p set imageid = 'emi-5C811289' where
	 * imageLocation='debian6-64bit/debian.6-0.x86-64.img.manifest.xml';
	 * 
	 * update image_description_p set imageid = 'emi-F9E1151E' where
	 * imageLocation='centos5-lamp-stack/centos.5-7.x86-64.img.manifest.xml';
	 * 
	 * update image_description_p set imageid = 'eki-AFEE1391' where
	 * imageLocation='centos5-64bit/vmlinuz-2.6.27.21-0.1-xen.manifest.xml';
	 * 
	 * update image_description_p set imageid = 'emi-EAEA14AB' where
	 * imageLocation='ununtu-lts-64bit/ubuntu.10-04.x86-64.img.manifest.xml';
	 * 
	 * update image_description_p set imageid = 'eki-0F291516' where
	 * imageLocation='ununtu-lts-64bit/vmlinuz-2.6.27.21-0.1-xen.manifest.xml';
	 * 
	 * update image_description_p set imageid = 'emi-3DF412D6' where
	 * imageLocation='portal/enstratus-console.img.manifest.xml';
	 * 
	 * update image_description_p set imageid = 'eri-869312EA' where
	 * imageLocation='debian6-64bit/initrd-2.6.27.21-0.1-xen.manifest.xml';
	 * 
	 * update image_description_p set imageid = 'emi-D61014C7' where
	 * imageLocation='centos5-64bit/centos.5-7.base.64bit.img.manifest.xml';
	 * 
	 * update image_description_p set imageid = 'emi-054E1143' where
	 * imageLocation='windows-2003/windows.2003.img.manifest.xml';
	 * 
	 * update image_description_p set imageid = 'emi-E9CF15E9' where
	 * imageLocation='enstratus-console/enstratus-console.manifest.xml';
	 */

}
