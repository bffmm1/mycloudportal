//My Cloud Portal - Self Service Portal for the cloud.
//This file is part of My Cloud Portal.
//
//My Cloud Portal is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, version 3 of the License.
//
//My Cloud Portal is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with My Cloud Portal.  If not, see <http://www.gnu.org/licenses/>.

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
 * @author Charudath Doddanakatte
 * @author cgowdas@gmail.com
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
			log.error(e);//e.printStackTrace();
		}
	}// end of save(KeyPairInfoP

	@Autowired
	WorkflowService workflowService;
	
	@RemoteMethod
	public KeyPairInfoP saveOrUpdate(KeyPairInfoP instance) {
		try {
			
			//check unique name per infra
			try{if(KeyPairInfoP.findKeyPairInfoPsByKeyNameEqualsAndCompanyEquals(instance.getKeyName(),
					Commons.getCurrentUser().getProject().getDepartment().getCompany()).getSingleResult().getId() > 0){
				throw new Exception("Key with this name already exists for this account, Choose another name.");
			}}catch(Exception e){
				e.printStackTrace();
				if(e.getMessage().contains("returns more than one elements")
						|| e.getMessage().contains("Key with this name already exists for this account")){
					throw new Exception("Key with this name already exists for this account, Choose another name.");
				}
			}
			
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
			Commons.setSessionMsg("Key Saved");
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			if(e.getMessage().contains("Key with this name already exists")){
				Commons.setSessionMsg("Key with this name already exists for this account, Choose another name.");
			}
			
		}
		return null;
	}// end of saveOrUpdate(KeyPairInfoP

	@RemoteMethod
	public void workflowApproved(KeyPairInfoP instance) {
		try {
			instance.setStatus(Commons.keypair_STATUS.starting+"");
			instance = instance.merge();
			createKeyPair(instance.getKeyName());
			Commons.setSessionMsg("Scheduled Key creation");
		} catch (Exception e) {
			log.error(e);//e.printStackTrace();
			Commons.setSessionMsg("Error while scheduling Key creation");
		}
	}
	
	@RemoteMethod
	public void remove(int id) {
		try {
			deleteKeyPair(id);
			KeyPairInfoP.findKeyPairInfoP(id).remove();
			Commons.setSessionMsg("Scheduled Key removal");
		} catch (Exception e) {
			log.error(e);//e.printStackTrace();
			Commons.setSessionMsg("Error during Scheduled Key removal");
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
			log.error(e);//e.printStackTrace();
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List<KeyPairInfoP> findAll4List() {
		try {
			
			User user = Commons.getCurrentUser();
			if(user.getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN+"")){
				return KeyPairInfoP.findAllKeyPairInfoPs();
			}else {
				return KeyPairInfoP.findKeyPairInfoPsByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId()),0, 100,"").getResultList();
			}
		} catch (Exception e) {
			log.error(e);//e.printStackTrace();
		}
		return null;
	}// end of method findAll4List
	
	@RemoteMethod
	public List<KeyPairInfoP> findAll(int start, int max,String search) {
		try {
			
			User user = Commons.getCurrentUser();
			if(user.getRole().getName().equals(Commons.ROLE.ROLE_USER+"")){
				return KeyPairInfoP.findKeyPairInfoPsByUser(user, start,  max, search).getResultList();
			}else if (user.getRole().getName().equals(Commons.ROLE.ROLE_MANAGER + "") || user.getRole().getName().equals(Commons.ROLE.ROLE_ADMIN+"")){
				return KeyPairInfoP.findKeyPairInfoPsByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId()), start,  max, search).getResultList();
			}
			
			return KeyPairInfoP.findAllKeyPairInfoPs(start,  max, search);
		} catch (Exception e) {
			log.error(e);//e.printStackTrace();
			
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
			log.error(e);//e.printStackTrace();
		}
	}

	@RemoteMethod
	public void createKeyPair(String name) {
		try {
			KeyPairInfoP keyPairInfoP = KeyPairInfoP.findKeyPairInfoPsByKeyNameEquals(name).getSingleResult();
			keyPairWorker.createKeyPair(keyPairInfoP.getAsset().getProductCatalog().getInfra(), keyPairInfoP);
		} catch (Exception e) {
			log.error(e);//e.printStackTrace();
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

