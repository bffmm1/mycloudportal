package in.mycp.remote;

import in.mycp.domain.Asset;
import in.mycp.domain.AssetType;
import in.mycp.domain.Company;
import in.mycp.domain.ImageDescriptionP;
import in.mycp.domain.Infra;
import in.mycp.domain.KeyPairInfoP;
import in.mycp.domain.ProductCatalog;
import in.mycp.domain.SnapshotInfoP;
import in.mycp.domain.User;
import in.mycp.domain.Workflow;
import in.mycp.utils.Commons;
import in.mycp.workers.SnapshotWorker;

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

@RemoteProxy(name = "SnapshotInfoP")
public class SnapshotService {

	private static final Logger log = Logger.getLogger(SnapshotService.class.getName());

	@Autowired
	WorkflowService workflowService;

	@Autowired
	SnapshotWorker snapshotWorker;

	@RemoteMethod
	public void save(SnapshotInfoP instance) {
		try {
			instance.persist();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of save(SnapshotInfoP

	@RemoteMethod
	public SnapshotInfoP saveOrUpdate(SnapshotInfoP instance) {
		try {
			return requestSnapshot(instance);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of saveOrUpdate(SnapshotInfoP

	@RemoteMethod
	public void remove(int id) {
		try {
			deleteSnapshot(id);
			SnapshotInfoP.findSnapshotInfoP(id).remove();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of method remove(int id

	@RemoteMethod
	public SnapshotInfoP findById(int id) {
		try {
			return SnapshotInfoP.findSnapshotInfoP(id);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List<SnapshotInfoP> findAll() {
		try {
			
			User user = Commons.getCurrentUser();
			if(user.getRole().getName().equals(Commons.ROLE.ROLE_USER+"")){
				return SnapshotInfoP.findSnapshotInfoPsByUser(user).getResultList();
			}else if (user.getRole().getName().equals(Commons.ROLE.ROLE_MANAGER + "") || user.getRole().getName().equals(Commons.ROLE.ROLE_ADMIN+"")){
				return SnapshotInfoP.findSnapshotInfoPsByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
			}
			return SnapshotInfoP.findAllSnapshotInfoPs();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll

	@RemoteMethod
	public SnapshotInfoP requestSnapshot(SnapshotInfoP snapshotInfoP) {
		try {

			AssetType assetTypeSnapshot = AssetType.findAssetTypesByNameEquals("" + Commons.ASSET_TYPE.VolumeSnapshot).getSingleResult();
			User currentUser = Commons.getCurrentUser();
			Asset asset = Commons.getNewAsset(assetTypeSnapshot, currentUser,snapshotInfoP.getProduct());
			snapshotInfoP.setAsset(asset);
			snapshotInfoP = snapshotInfoP.merge();
			if (true == assetTypeSnapshot.getWorkflowEnabled()) {
				Commons.createNewWorkflow(workflowService.createProcessInstance(Commons.PROCESS_DEFN.Snapshot_Request
						+ ""), snapshotInfoP.getId(), asset.getAssetType().getName());
				snapshotInfoP.setStatus(Commons.WORKFLOW_STATUS.PENDING_APPROVAL+"");
				snapshotInfoP = snapshotInfoP.merge();
			} else {
				snapshotInfoP.setStatus(Commons.SNAPSHOT_STATUS.pending+"");
				snapshotInfoP = snapshotInfoP.merge();
				workflowApproved(snapshotInfoP);
			}
			log.info("end of requestSnapshot");
			return snapshotInfoP;
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of requestSnapshot(SnapshotInfoP

	public void workflowApproved(SnapshotInfoP instance) {
		try {
			instance.setStatus(Commons.SNAPSHOT_STATUS.pending+"");
			instance = instance.merge();
			snapshotWorker.createSnapshot(instance.getAsset().getProductCatalog().getInfra(), instance);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}

	}

	@RemoteMethod
	public void deleteSnapshot(int id) {
		try {
			SnapshotInfoP snapshotInfoP = SnapshotInfoP.findSnapshotInfoP(id);
			Commons.setAssetEndTime(snapshotInfoP.getAsset());
			snapshotWorker.deleteSnapshot( snapshotInfoP.getAsset().getProductCatalog().getInfra(), snapshotInfoP);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}
	
	@RemoteMethod
	public List<ProductCatalog> findProductType() {

		if(Commons.getCurrentUser().getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN+"")){
			return ProductCatalog.findProductCatalogsByProductTypeEquals(Commons.ProductType.VolumeSnapshot.getName()).getResultList();
		}else{
			return ProductCatalog.findProductCatalogsByProductTypeAndCompany(Commons.ProductType.VolumeSnapshot.getName(),
					Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
		}
		
	}
	

}// end of class SnapshotInfoPController

