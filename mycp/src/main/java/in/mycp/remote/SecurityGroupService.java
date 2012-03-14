package in.mycp.remote;

import in.mycp.domain.Asset;
import in.mycp.domain.AssetType;
import in.mycp.domain.Company;
import in.mycp.domain.GroupDescriptionP;
import in.mycp.domain.IpPermissionP;
import in.mycp.domain.ProductCatalog;
import in.mycp.domain.User;
import in.mycp.utils.Commons;
import in.mycp.workers.SecurityGroupWorker;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Charudath.Ramegowda
 * 
 */

@RemoteProxy(name = "GroupDescriptionP")
public class SecurityGroupService {

	private static final Logger log = Logger.getLogger(SecurityGroupService.class.getName());

	@Autowired
	SecurityGroupWorker securityGroupWorker;

	@Autowired
	WorkflowService workflowService;

	

	@RemoteMethod
	public GroupDescriptionP saveOrUpdate(GroupDescriptionP instance) {
		try {
			String companyName = Commons.getCurrentSession().getCompany();
			if(instance!=null && instance.getName()!=null && instance.getName().indexOf("_"+companyName) <0){
				instance.setName(instance.getName()+"_"+companyName);
			}
			AssetType assetTypeSecurityGroup = AssetType.findAssetTypesByNameEquals(Commons.ASSET_TYPE.SecurityGroup + "")
					.getSingleResult();
			if (instance.getId() != null && instance.getId() > 0) {
				instance = GroupDescriptionP.findGroupDescriptionP(instance.getId());
			} else {
				User currentUser = Commons.getCurrentUser();
				Asset asset = Commons.getNewAsset(assetTypeSecurityGroup, currentUser,instance.getProduct());
				instance.setAsset(asset);
				instance.setStatus(Commons.secgroup_STATUS.inactive+"");
				instance = instance.merge();
				workflowApproved(instance);
			}
			
			

			/*if (true == assetTypeSecurityGroup.getWorkflowEnabled()) {
				Commons.createNewWorkflow(
						workflowService.createProcessInstance(Commons.PROCESS_DEFN.SecGroup_Request + ""), instance.getId(),
						assetTypeSecurityGroup.getName());
			} else {
				workflowApproved(instance);
			}*/
			

			return instance;
		} catch (Exception e) {
			log.error(e.getMessage());e.printStackTrace();
		}
		return null;
	}// end of saveOrUpdate(GroupDescriptionP

	@RemoteMethod
	public void workflowApproved(GroupDescriptionP instance) {
		try {
			securityGroupWorker.createSecurityGroup(instance.getAsset().getProductCatalog().getInfra(), instance);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of createCompute(InstanceP

	@RemoteMethod
	public void remove(int id) {
		try {
			List<IpPermissionP> IpPermissionPs = IpPermissionP.findIpPermissionPsByGroupDescription(
					GroupDescriptionP.findGroupDescriptionP(id)).getResultList();
			for (Iterator iterator = IpPermissionPs.iterator(); iterator.hasNext();) {
				IpPermissionP ipPermissionP = (IpPermissionP) iterator.next();
				ipPermissionP.remove();
			}
			try {
				securityGroupWorker.deleteSecurityGroup(GroupDescriptionP.findGroupDescriptionP(id).getAsset().getProductCatalog().getInfra(), GroupDescriptionP.findGroupDescriptionP(id));	
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			
			GroupDescriptionP g = GroupDescriptionP.findGroupDescriptionP(id);
			Commons.setAssetEndTime(g.getAsset());
			g.remove();
			/*g.setStatus(Commons.secgroup_STATUS.inactive+"");
			g.merge();*/
		} catch (Exception e) {
			log.error(e.getMessage());e.printStackTrace();
		}
	}// end of method remove(int id

	@RemoteMethod
	public GroupDescriptionP findById(int id) {
		try {
			GroupDescriptionP instance = GroupDescriptionP.findGroupDescriptionP(id);
			instance.setProduct(""+instance.getAsset().getProductCatalog().getId());
			return instance;
			//return GroupDescriptionP.findGroupDescriptionP(id);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List findAll() {
		try {
			List<GroupDescriptionP> gds = null;
			
			User user = Commons.getCurrentUser();
			if(user.getRole().getName().equals(Commons.ROLE.ROLE_USER+"")){
				gds = GroupDescriptionP.findActiveGroupDescriptionPsByUser(user).getResultList();
			}else if (user.getRole().getName().equals(Commons.ROLE.ROLE_MANAGER + "") || user.getRole().getName().equals(Commons.ROLE.ROLE_ADMIN+"")){
				
				gds = GroupDescriptionP.findActiveGroupDescriptionPsByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
			}else {
				gds = GroupDescriptionP.findAllActiveGroupDescriptionPs();
			}
			
			for (Iterator iterator = gds.iterator(); iterator.hasNext();) {
				GroupDescriptionP groupDescriptionP = (GroupDescriptionP) iterator.next();

				List<IpPermissionP> ips = IpPermissionP.findIpPermissionPsByGroupDescription(groupDescriptionP).getResultList();

				Set<IpPermissionP> hset = new HashSet<IpPermissionP>();

				for (Iterator iterator2 = ips.iterator(); iterator2.hasNext();) {
					IpPermissionP ipPermissionP = (IpPermissionP) iterator2.next();
					hset.add(ipPermissionP);
				}

				groupDescriptionP.setIpPermissionPs(hset);

			}

			return gds;
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll
	
	@RemoteMethod
	public List findAll4Edit() {
		try {
			List<GroupDescriptionP> gds = null;
			
			User user = Commons.getCurrentUser();
			if(user.getRole().getName().equals(Commons.ROLE.ROLE_USER+"")){
				gds = GroupDescriptionP.findAllGroupDescriptionPsByUser(user).getResultList();
			}else if (user.getRole().getName().equals(Commons.ROLE.ROLE_MANAGER + "") || user.getRole().getName().equals(Commons.ROLE.ROLE_ADMIN+"")){
				gds = GroupDescriptionP.findAllGroupDescriptionPsByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
			}else {
				gds = GroupDescriptionP.findAllGroupDescriptionPs();
			}
			
			for (Iterator iterator = gds.iterator(); iterator.hasNext();) {
				GroupDescriptionP groupDescriptionP = (GroupDescriptionP) iterator.next();

				List<IpPermissionP> ips = IpPermissionP.findIpPermissionPsByGroupDescription(groupDescriptionP).getResultList();

				Set<IpPermissionP> hset = new HashSet<IpPermissionP>();

				for (Iterator iterator2 = ips.iterator(); iterator2.hasNext();) {
					IpPermissionP ipPermissionP = (IpPermissionP) iterator2.next();
					hset.add(ipPermissionP);
				}

				groupDescriptionP.setIpPermissionPs(hset);

			}

			return gds;
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll
	
	@RemoteMethod
	public List<ProductCatalog> findProductType() {
		if(Commons.getCurrentUser().getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN+"")){
			return ProductCatalog.findProductCatalogsByProductTypeEquals(Commons.ProductType.SecurityGroup.getName()).getResultList();
		}else{
			return ProductCatalog.findProductCatalogsByProductTypeAndCompany(Commons.ProductType.SecurityGroup.getName(),
					Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
		}
	}
	
}// end of class SecurityGroupService

