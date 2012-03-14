package in.mycp.remote;

import in.mycp.domain.AddressInfoP;
import in.mycp.domain.Company;
import in.mycp.domain.ImageDescriptionP;
import in.mycp.domain.InstanceP;
import in.mycp.domain.SnapshotInfoP;
import in.mycp.domain.User;
import in.mycp.domain.VolumeInfoP;
import in.mycp.domain.Workflow;
import in.mycp.service.WorkflowImpl4Jbpm;
import in.mycp.utils.Commons;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.jbpm.api.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Charudath.Ramegowda
 * 
 */

@RemoteProxy(name = "WorkflowService")
public class WorkflowService {

	private static final Logger log = Logger.getLogger(WorkflowService.class.getName());

	@Autowired
	WorkflowImpl4Jbpm workflowImpl4Jbpm;
	@Autowired
	InstancePService instancePService;
	@Autowired
	AddressInfoPService addressInfoPService;
	@Autowired
	IpPermissionService ipPermissionService;
	@Autowired
	ImageService imageService;
	@Autowired
	KeyPairService keyPairService;
	@Autowired
	SecurityGroupService securityGroupService;
	@Autowired
	VolumeService volumeService;
	@Autowired
	SnapshotService snapshotService;

	@RemoteMethod
	public void save(Workflow instance) {
		try {
			instance.persist();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of save(VolumeInfoP

	@RemoteMethod
	public Workflow saveOrUpdate(Workflow instance) {
		try {
			return instance.merge();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of saveOrUpdate(VolumeInfoP

	@RemoteMethod
	public void remove(int id) {
		try {
			Workflow.findWorkflow(id).remove();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of method remove(int id

	@RemoteMethod
	public Workflow findById(int id) {
		try {

			return Workflow.findWorkflow(id);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List<Workflow> findAll() {
		try {

			User user = Commons.getCurrentUser();
			List<Workflow> wfs = null;
			if (user.getRole().getName().equals(Commons.ROLE.ROLE_MANAGER + "")
					|| user.getRole().getName().equals(Commons.ROLE.ROLE_ADMIN + "")) {
				wfs = Workflow.findWorkflowsByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
			} else if (user.getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN + "")) {
				wfs = Workflow.findAllWorkflows();
			} else {
				wfs = Workflow.findWorkflowsByUser(user).getResultList();
			}

			List<Workflow> wfs2return = new ArrayList<Workflow>();
			for (Iterator iterator = wfs.iterator(); iterator.hasNext();) {
				Workflow workflow = (Workflow) iterator.next();
				ProcessInstance pi = workflowImpl4Jbpm.findProcessInstance(workflow.getProcessId());
				if (pi == null || pi.getId() == null) {
					continue;
				} else {

					workflow.setProcessName(pi.getId());
					String activityName = "";
					Set<String> activityNames = pi.findActiveActivityNames();
					for (Iterator iterator1 = activityNames.iterator(); iterator1.hasNext();) {
						String string = (String) iterator1.next();
						activityName = string;
						// System.out.println("before move activityNames = " +
						// string);
						break;
					}
					activityName = StringUtils.replace(activityName, "null", " ");
					System.out.println("activityName = " + activityName + " pi.getId() =  " + pi.getId());

					// if currently looged in user is manager , do not show any
					// workflows in Admin status
					//TODO - uncomment this when needed
					/*if (Commons.getCurrentUser().getRole().getName().equals(Commons.ROLE.ROLE_MANAGER + "")) {
						if (activityName != null && activityName.contains("Admin")) {
							continue;
						}
					} else if (Commons.getCurrentUser().getRole().getName().equals(Commons.ROLE.ROLE_ADMIN + "")) {
						if (activityName != null && activityName.contains("Manager")) {
							continue;
						}
					}*/

					workflow.setProcessStatus(activityName);

					workflow.setStartTime(workflowImpl4Jbpm.findStartTime(pi.getId()));
					try {
						workflow.setProcessName(workflow.getProcessName().substring(0,workflow.getProcessName().indexOf(".")));	
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					wfs2return.add(workflow);
				}

				if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.ComputeImage)) {
					try {
						ImageDescriptionP image = ImageDescriptionP.findImageDescriptionP(workflow.getAssetId());

						workflow.setAssetDetails(workflow.getAssetType() + " " + image.getName() + " " + image.getImageId());
					} catch (Exception e) {
						log.error(e.getMessage());//e.printStackTrace();
					}
				} else if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.ComputeInstance)) {
					try {
						InstanceP instance = InstanceP.findInstanceP(workflow.getAssetId());
						workflow.setAssetDetails(workflow.getAssetType() + " " + instance.getName() + " " + instance.getDnsName() + " "
								+ instance.getInstanceId());
					} catch (Exception e) {
						//log.error(e.getMessage());//e.printStackTrace();
					}
				} else if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.IpAddress)) {
					try {
						AddressInfoP address = AddressInfoP.findAddressInfoP(workflow.getAssetId());
						workflow.setAssetDetails(workflow.getAssetType() + " " + address.getName() + " " + address.getPublicIp());
					} catch (Exception e) {
						log.error(e.getMessage());//e.printStackTrace();
					}
				} else if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.IpPermission)) {
					// nothig here
				} else if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.KeyPair)) {
					// nothing here
				} else if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.SecurityGroup)) {
					// nothing here
				} else if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.Volume)) {
					try {
						VolumeInfoP volume = VolumeInfoP.findVolumeInfoP(workflow.getAssetId());
						workflow.setAssetDetails(workflow.getAssetType() + " " + volume.getName() + " " + volume.getVolumeId() + " "
								+ volume.getSize() + "(GB)");
					} catch (Exception e) {
						log.error(e.getMessage());//e.printStackTrace();
					}
				} else if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.VolumeSnapshot)) {
					try {
						SnapshotInfoP snapshot = SnapshotInfoP.findSnapshotInfoP(workflow.getAssetId());
						workflow.setAssetDetails(workflow.getAssetType() + " " + snapshot.getSnapshotId() + " " + snapshot.getOwnerId());
					} catch (Exception e) {
						log.error(e.getMessage());//e.printStackTrace();
					}
				} else if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.addressInfo)) {
					// TODO
				} else {
					log.error("Which asset does this workflow belong?");
					// throw new
					// Exception("Which asset does this workflow belong?");
				}

			}
			return wfs2return;
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll

	public ProcessInstance findProcessInstance(String processInstanceId) {
		
		try {
			return workflowImpl4Jbpm.findProcessInstance(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}

	public void endProcessInstance(String processInstanceId) {
		
		try {
			workflowImpl4Jbpm.endProcessInstance(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}

	public ProcessInstance createProcessInstance(String processDefnKey) {
		System.out.println("In createProcessInstance...");
		try {
			ProcessInstance pi = workflowImpl4Jbpm.createProcessInstance(processDefnKey);
			return pi;
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}

	@RemoteMethod
	public ProcessInstance moveProcessInstance(String processInstanceId, String transition) {
		log.info("In moveProcessInstance..." + processInstanceId + " " + transition);
		try {
			ProcessInstance pi = workflowImpl4Jbpm.moveProcessInstance(processInstanceId, transition);
			// find out if the process instance is ended .
			// if so , find out for which asset and continue processing it.
			if (pi.isEnded()) {
				Workflow workflow = Workflow.findWorkflowsByProcessIdEquals(processInstanceId).getSingleResult();
				if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.ComputeImage)) {
					log.info("Moving workflow of type "+Commons.ASSET_TYPE.ComputeImage);
					ImageDescriptionP imageDescriptionP = ImageDescriptionP.findImageDescriptionP(workflow.getAssetId());
					imageService.workflowApproved(imageDescriptionP);
				} else if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.ComputeInstance)) {
					InstanceP instance = InstanceP.findInstanceP(workflow.getAssetId());
					log.info("Moving workflow of type "+Commons.ASSET_TYPE.ComputeInstance);
					Set instances = new HashSet<InstanceP>();
					instances.add(instance);
					instancePService.workflowApproved(new HashSet<InstanceP>(instances));

				}/*
				 * else if(workflow.getAssetType().equals(""+Commons.ASSET_TYPE.
				 * ComputeReservation)){ ReservationDescriptionP reservation =
				 * ReservationDescriptionP
				 * .findReservationDescriptionP(workflow.getAssetId());
				 * List<InstanceP> instances =
				 * InstanceP.findInstancePsByReservationDescription
				 * (reservation).getResultList();
				 * instancePService.workflowApproved(new
				 * HashSet<InstanceP>(instances)); }
				 */else if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.IpAddress)) {
					// nothing here
					 log.info("Moving workflow of type "+Commons.ASSET_TYPE.IpAddress);
					 
					 AddressInfoP address = AddressInfoP.findAddressInfoP(workflow.getAssetId());
						
						addressInfoPService.workflowApproved(address);
						
				} else if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.IpPermission)) {
					// nothig here
					log.info("Moving workflow of type "+Commons.ASSET_TYPE.IpPermission);
				} else if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.KeyPair)) {
					// nothing here
					log.info("Moving workflow of type "+Commons.ASSET_TYPE.KeyPair);
				} else if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.SecurityGroup)) {
					
					log.info("Moving workflow of type "+Commons.ASSET_TYPE.SecurityGroup);
				} else if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.Volume)) {
					VolumeInfoP volume = VolumeInfoP.findVolumeInfoP(workflow.getAssetId());
					log.info("Moving workflow of type "+Commons.ASSET_TYPE.Volume);
					volumeService.workflowApproved(volume);
				} else if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.VolumeSnapshot)) {
					SnapshotInfoP snapshot = SnapshotInfoP.findSnapshotInfoP(workflow.getAssetId());
					log.info("Moving workflow of type "+Commons.ASSET_TYPE.VolumeSnapshot);
					snapshotService.workflowApproved(snapshot);
				} else if (workflow.getAssetType().equals("" + Commons.ASSET_TYPE.addressInfo)) {
					AddressInfoP address = AddressInfoP.findAddressInfoP(workflow.getAssetId());
					log.info("Moving workflow of type "+Commons.ASSET_TYPE.addressInfo);
					addressInfoPService.workflowApproved(address);
				} else {
					log.error("Which asset does this workflow belong?");
					throw new Exception("Which asset does this workflow belong?");
				}
			}

			return pi;
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}

	@RemoteMethod
	public void cleanupAllProcessDefinitions() {
		System.out.println("In cleanupAllProcessDefinitions...");
		try {
			workflowImpl4Jbpm.cleanupAllProcessDefinitions();

		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}

	@RemoteMethod
	public void testProcessDefinitions() {
		workflowImpl4Jbpm.testProcessDefinitions();
	}

	@RemoteMethod
	public void setupProcessDefinitions() {
		System.out.println("In setupProcessDefinitions...");
		try {

			workflowImpl4Jbpm.setupProcessDefinitions();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
			log.info("IOException occurred: ", e);
			throw new RuntimeException("An error occured while trying to deploy a process definition", e);
		}
	}

}// end of class WorkflowService

