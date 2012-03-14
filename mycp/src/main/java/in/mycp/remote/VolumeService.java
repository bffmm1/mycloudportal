package in.mycp.remote;

// Generated Dec 29, 2011 2:51:05 PM by Charudath

import in.mycp.domain.Asset;
import in.mycp.domain.AssetType;
import in.mycp.domain.AttachmentInfoP;
import in.mycp.domain.Company;
import in.mycp.domain.ImageDescriptionP;
import in.mycp.domain.Infra;
import in.mycp.domain.InstanceP;
import in.mycp.domain.ProductCatalog;
import in.mycp.domain.User;
import in.mycp.domain.VolumeInfoP;
import in.mycp.domain.Workflow;
import in.mycp.utils.Commons;
import in.mycp.workers.VolumeWorker;

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

@RemoteProxy(name = "VolumeInfoP")
public class VolumeService {

	private static final Logger log = Logger.getLogger(VolumeService.class.getName());

	@Autowired
	VolumeWorker volumeWorker;

	@Autowired
	WorkflowService workflowService;

	@RemoteMethod
	public VolumeInfoP requestVolume(VolumeInfoP volume) {
		try {

			AssetType assetTypeVolume = AssetType.findAssetTypesByNameEquals("Volume").getSingleResult();
			User currentUser = Commons.getCurrentUser();
			Asset asset = Commons.getNewAsset(assetTypeVolume, currentUser,volume.getProduct());
			volume.setAsset(asset);
			volume = volume.merge();
			if(true == assetTypeVolume.getWorkflowEnabled()){
				Workflow workflow = Commons.createNewWorkflow(workflowService.createProcessInstance(Commons.PROCESS_DEFN.Volume_Request + ""),
						volume.getId(), asset.getAssetType().getName());
			}else{
				workflowApproved(volume);
			}

			log.info("end of requestVolume");
			return volume;
		} catch (Exception e) {
			log.error(e.getMessage());e.printStackTrace();
		}
		return null;
	}// end of requestVolume(VolumeInfoP

	public void workflowApproved(VolumeInfoP volume) {
		AssetType assetTypeVolume = AssetType.findAssetTypesByNameEquals("Volume").getSingleResult();
		User currentUser = Commons.getCurrentUser();
		volume = VolumeInfoP.findVolumeInfoP(volume.getId());
		Asset asset = Commons.getNewAsset(assetTypeVolume, currentUser,ProductCatalog.findProductCatalogsByNameEquals("Volume @ Eucalyptus").getSingleResult());
		volume.setAsset(asset);
		volume = volume.merge();
		
		createVolume(volume);
	}

	@RemoteMethod
	public void deleteVolume(int id) {
		try {
			VolumeInfoP volumeInfoP = VolumeInfoP.findVolumeInfoP(id);
			Commons.setAssetEndTime(volumeInfoP.getAsset());
			volumeWorker.deleteVolume(volumeInfoP.getAsset().getProductCatalog().getInfra(), volumeInfoP);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}

	}// end of deleteVolume(VolumeInfoP

	@RemoteMethod
	public void attachVolume(VolumeInfoP volume) {
		try {
			//volume = VolumeInfoP.findVolumeInfoP(volume.getId());
			log.info("calling Worker for " + volume);
			// TODO - whats a better way to find the infra object
			volumeWorker.attachVolume(VolumeInfoP.findVolumeInfoP(volume.getId()).getAsset().getProductCatalog().getInfra(), volume);
			log.info("scheduled Worker for " + volume);
		} catch (Exception e) {
			log.error(e.getMessage());e.printStackTrace();
		}
	}// end of deleteVolume(VolumeInfoP

	@RemoteMethod
	public void detachVolume(int id) {
		try {
			VolumeInfoP volume = VolumeInfoP.findVolumeInfoP(id);
			log.info("calling detachVolume Worker for " + volume.getSize() + ", " + volume.getSnapshotId() + ", " + volume.getZone());
			volumeWorker.detachVolume(volume.getAsset().getProductCatalog().getInfra(), volume);
			log.info("scheduled detachVolume Worker for " + volume.getSize() + ", " + volume.getSnapshotId() + ", " + volume.getZone());
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of deleteVolume(VolumeInfoP

	@RemoteMethod
	public void createVolume(VolumeInfoP volume) {
		try {
			log.info("calling Worker for " + volume.getSize() + ", " + volume.getSnapshotId() + ", " + volume.getZone());
			volumeWorker.createVolume(volume.getAsset().getProductCatalog().getInfra(), volume);
			log.info("scheduled Worker for " + volume.getSize() + ", " + volume.getSnapshotId() + ", " + volume.getZone());
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of createVolume(VolumeInfoP

	

	@RemoteMethod
	public VolumeInfoP saveOrUpdate(VolumeInfoP instance) {
		try {
			return requestVolume(instance);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of saveOrUpdate(VolumeInfoP

	@RemoteMethod
	public void remove(int id) {
		try {
			deleteVolume(id);
			VolumeInfoP.findVolumeInfoP(id).remove();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of method remove(int id

	@RemoteMethod
	public VolumeInfoP findById(int id) {
		try {
			VolumeInfoP instance = VolumeInfoP.findVolumeInfoP(id);
			instance.setProduct(""+instance.getAsset().getProductCatalog().getId());
			return instance;
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List<VolumeInfoP> findAll() {
		try {
			
			User user = Commons.getCurrentUser();
			if(user.getRole().getName().equals(Commons.ROLE.ROLE_USER+"")){
				return VolumeInfoP.findVolumeInfoPsByUser(user).getResultList();
			}else if (user.getRole().getName().equals(Commons.ROLE.ROLE_MANAGER + "") || user.getRole().getName().equals(Commons.ROLE.ROLE_ADMIN+"")){
				return VolumeInfoP.findVolumeInfoPsByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
			}
			return VolumeInfoP.findAllVolumeInfoPs();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll

	@RemoteMethod
	public List findAllWithAttachInfo() {
		try {
			List<VolumeInfoP> volumes = findAll();
			for (Iterator iterator = volumes.iterator(); iterator.hasNext();) {
				VolumeInfoP volumeInfoP = (VolumeInfoP) iterator.next();
				if(volumeInfoP.getVolumeId() == null || volumeInfoP.getVolumeId().equals("")){
					continue;
				}
				List<AttachmentInfoP> attaches = AttachmentInfoP.findAttachmentInfoPsByVolumeIdEquals(volumeInfoP.getVolumeId())
						.getResultList();
				if (attaches != null && attaches.size() > 0) {
					for (Iterator iterator2 = attaches.iterator(); iterator2.hasNext();) {
						AttachmentInfoP attachmentInfoP = (AttachmentInfoP) iterator2.next();
						volumeInfoP.setDetails(attachmentInfoP.getStatus() + " @ " + attachmentInfoP.getAttachTime() + " to "
								+ attachmentInfoP.getInstanceId());
					}
				}
			}
			return volumes;

		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll
	
	@RemoteMethod
	public List<ProductCatalog> findProductType() {
		if(Commons.getCurrentUser().getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN+"")){
			return ProductCatalog.findProductCatalogsByProductTypeEquals(Commons.ProductType.Volume.getName()).getResultList();
		}else{
			
			return ProductCatalog.findProductCatalogsByProductTypeAndCompany(Commons.ProductType.Volume.getName(),
					Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
		}
		
		
	}
	
}// end of class VolumeInfoPController

