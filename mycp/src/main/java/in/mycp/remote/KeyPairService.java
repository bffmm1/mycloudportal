package in.mycp.remote;

import in.mycp.domain.Asset;
import in.mycp.domain.AssetType;
import in.mycp.domain.Company;
import in.mycp.domain.GroupDescriptionP;
import in.mycp.domain.Infra;
import in.mycp.domain.InstanceP;
import in.mycp.domain.KeyPairInfoP;
import in.mycp.domain.ProductCatalog;
import in.mycp.domain.User;
import in.mycp.domain.VolumeInfoP;
import in.mycp.utils.Commons;
import in.mycp.workers.KeyPairWorker;

import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * 
 * @author Charudath.Ramegowda
 * 
 */

@RemoteProxy(name = "KeyPairInfoP")
public class KeyPairService {

	private static final Logger log = Logger.getLogger(KeyPairService.class.getName());

	@Autowired
	KeyPairWorker keyPairWorker;

	@RemoteMethod
	public void save(KeyPairInfoP instance) {
		try {
			instance.persist();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of save(KeyPairInfoP

	@Autowired
	WorkflowService workflowService;
	
	@RemoteMethod
	public KeyPairInfoP saveOrUpdate(KeyPairInfoP instance) {
		try {
			AssetType assetTypeKeyPair = AssetType.findAssetTypesByNameEquals("KeyPair").getSingleResult();
			if (instance.getId() != null && instance.getId() > 0) {
			} else {
				User currentUser = Commons.getCurrentUser();
				Asset asset = Commons.getNewAsset(assetTypeKeyPair, currentUser,instance.getProduct());
				instance.setAsset(asset);
				instance = instance.merge();
				if (true == assetTypeKeyPair.getWorkflowEnabled()) {
					Commons.createNewWorkflow(workflowService.createProcessInstance(Commons.PROCESS_DEFN.Keys_Request
							+ ""), instance.getId(), asset.getAssetType().getName());
					instance.setStatus(Commons.WORKFLOW_STATUS.PENDING_APPROVAL+"");
					instance = instance.merge();
				} else {
					instance.setStatus(Commons.keypair_STATUS.starting+"");
					instance = instance.merge();
					workflowApproved(instance);
				}
			}
			return instance;
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of saveOrUpdate(KeyPairInfoP

	@RemoteMethod
	public void workflowApproved(KeyPairInfoP instance) {
		try {
			instance.setStatus(Commons.keypair_STATUS.starting+"");
			instance = instance.merge();
			createKeyPair(instance.getKeyName());
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}
	
	@RemoteMethod
	public void remove(int id) {
		try {
			deleteKeyPair(id);
			KeyPairInfoP.findKeyPairInfoP(id).remove();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}// end of method remove(int id

	@RemoteMethod
	public KeyPairInfoP findById(int id) {
		try {

			KeyPairInfoP instance = KeyPairInfoP.findKeyPairInfoP(id);
			instance.setProduct(""+instance.getAsset().getProductCatalog().getId());
			return instance;
			//return KeyPairInfoP.findKeyPairInfoP(id);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List findAll() {
		try {
			
			User user = Commons.getCurrentUser();
			if(user.getRole().getName().equals(Commons.ROLE.ROLE_USER+"")){
				return KeyPairInfoP.findKeyPairInfoPsByUser(user).getResultList();
			}else if (user.getRole().getName().equals(Commons.ROLE.ROLE_MANAGER + "") || user.getRole().getName().equals(Commons.ROLE.ROLE_ADMIN+"")){
				return KeyPairInfoP.findKeyPairInfoPsByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
			}
			
			return KeyPairInfoP.findAllKeyPairInfoPs();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
			
		}
		return null;
	}// end of method findAll

	@RemoteMethod
	public void deleteKeyPair(int id) {
		try {
			KeyPairInfoP key = KeyPairInfoP.findKeyPairInfoP(id);
			Commons.setAssetEndTime(key.getAsset());
			KeyPairInfoP keyPairInfoP = KeyPairInfoP.findKeyPairInfoPsByKeyNameEquals(key.getKeyName()).getSingleResult();
			keyPairWorker.deleteKeyPair(keyPairInfoP.getAsset().getProductCatalog().getInfra(), keyPairInfoP);

		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}

	@RemoteMethod
	public void createKeyPair(String name) {
		try {
			KeyPairInfoP keyPairInfoP = KeyPairInfoP.findKeyPairInfoPsByKeyNameEquals(name).getSingleResult();
			keyPairWorker.createKeyPair(keyPairInfoP.getAsset().getProductCatalog().getInfra(), keyPairInfoP);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
	}
	
	@RemoteMethod
	public List<ProductCatalog> findProductType() {
		if(Commons.getCurrentUser().getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN+"")){
			return ProductCatalog.findProductCatalogsByProductTypeEquals(Commons.ProductType.KeyPair.getName()).getResultList();
		}else{
			return ProductCatalog.findProductCatalogsByProductTypeAndCompany(Commons.ProductType.KeyPair.getName(),
					Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
		}
	}

}// end of class KeyPairInfoPController

