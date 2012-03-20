package in.mycp.remote;

import in.mycp.domain.AssetType;
import in.mycp.domain.GroupDescriptionP;
import in.mycp.domain.IpPermissionP;
import in.mycp.utils.Commons;
import in.mycp.workers.SecurityGroupWorker;

import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Charudath.Ramegowda
 * 
 */

@RemoteProxy(name = "IpPermissionP")
public class IpPermissionService {

	private static final Logger log = Logger.getLogger(IpPermissionService.class.getName());

	@Autowired
	SecurityGroupWorker securityGroupWorker;
	
	@Autowired
	WorkflowService workflowService;
	

	public void save(IpPermissionP instance) {
		try {
			instance.persist();
		} catch (Exception e) {
			log.error(e);e.printStackTrace();
		}
	}// end of save(IpPermissionP

	@RemoteMethod
	public IpPermissionP saveOrUpdate(IpPermissionP instance) {
		try {
			instance = instance.merge();
			AssetType assetTypeSecurityGroup = AssetType.findAssetTypesByNameEquals(Commons.ASSET_TYPE.SecurityGroup + "")
					.getSingleResult();
			if(!assetTypeSecurityGroup.getWorkflowEnabled() || (instance.getGroupDescription() !=null 
					&& instance.getGroupDescription().getStatus().equals(Commons.secgroup_STATUS.active+""))){
				workflowApproved(instance);
			}
			
			return instance;
		} catch (Exception e) {
			log.error(e);e.printStackTrace();
		}
		return null;
	}// end of saveOrUpdate(IpPermissionP

	@RemoteMethod
	public void workflowApproved(IpPermissionP instance) {
		try {
			securityGroupWorker.authorizeSecurityGroupIngress(instance.getGroupDescription().getAsset().getProductCatalog().getInfra(),
					instance);
		} catch (Exception e) {
			log.error(e);e.printStackTrace();
		}
	}// end of createCompute(InstanceP
	
	@RemoteMethod
	public void remove(int id) {
		try {

			securityGroupWorker.revokeSecurityGroupIngress(IpPermissionP.findIpPermissionP(id).getGroupDescription().getAsset()
					.getProductCatalog().getInfra(), IpPermissionP.findIpPermissionP(id));
			IpPermissionP.findIpPermissionP(id).remove();
		} catch (Exception e) {
			log.error(e);e.printStackTrace();
		}
	}// end of method remove(int id

	@RemoteMethod
	public IpPermissionP findById(int id) {
		try {

			return IpPermissionP.findIpPermissionP(id);
		} catch (Exception e) {
			log.error(e);//e.printStackTrace();
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List<IpPermissionP> findAll() {
		try {
			return IpPermissionP.findAllIpPermissionPs();
		} catch (Exception e) {
			log.error(e);//e.printStackTrace();
		}
		return null;
	}// end of method findAll

	@RemoteMethod
	public List<IpPermissionP> findBySecurityGroup(GroupDescriptionP groupDescriptionP) {
		try {
			return IpPermissionP.findIpPermissionPsByGroupDescription(groupDescriptionP).getResultList();
		} catch (Exception e) {
			log.error(e);e.printStackTrace();
		}
		return null;
	}// end of method findById(int id
}// end of class IpPermissionPController

