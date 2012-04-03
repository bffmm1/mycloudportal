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
import in.mycp.domain.ImageDescriptionP;
import in.mycp.domain.Infra;
import in.mycp.domain.InstanceP;
import in.mycp.domain.KeyPairInfoP;
import in.mycp.domain.SnapshotInfoP;
import in.mycp.domain.User;
import in.mycp.domain.VolumeInfoP;
import in.mycp.domain.Workflow;
import in.mycp.utils.Commons;
import in.mycp.workers.ImageWorker;

import java.util.Iterator;
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


@RemoteProxy(name="ImageDescriptionP")
public class ImageService {

    private static final Logger log = Logger.getLogger(ImageService.class.getName());
    @Autowired
	WorkflowService workflowService;
    @Autowired
    ImageWorker imageWorker;
	
    	@RemoteMethod
		public void save(ImageDescriptionP instance){
			try{
				instance.persist();

			}catch (Exception e) {
				log.error(e);//e.printStackTrace();
			}
		}//end of save(ImageDescriptionP
    	
    	@RemoteMethod
		public ImageDescriptionP saveOrUpdate(ImageDescriptionP instance){
			try{
				return requestImage(instance);
			}catch (Exception e) {
				log.error(e);//e.printStackTrace();
			}
			return null;
		}//end of saveOrUpdate(ImageDescriptionP
    	
    	
    	@RemoteMethod
		public void remove(int id){
			try{
				ImageDescriptionP.findImageDescriptionP(id).remove();
			}catch (Exception e) {
				log.error(e);//e.printStackTrace();
			}
		}//end of method remove(int id
		
    	@RemoteMethod
		public ImageDescriptionP findById(int id){
			try{
				return ImageDescriptionP.findImageDescriptionP(id);
			}catch (Exception e) {
				log.error(e);//e.printStackTrace();
			}
			return null;
		}//end of method findById(int id

    	@RemoteMethod
		public List<ImageDescriptionP> findAll4List(){
			try{
				User user = Commons.getCurrentUser();
				if(user.getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN+"")){
					return ImageDescriptionP.findAllImageDescriptionPs();
				}else {
					return ImageDescriptionP.findImageDescriptionPsByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
				}				
				
				}catch (Exception e) {
				log.error(e);//e.printStackTrace();
			}
			return null;
		}//end of method findAll4List
    	
    	@RemoteMethod
		public List<ImageDescriptionP> findAll(){
			try{
				User user = Commons.getCurrentUser();
				if(user.getRole().getName().equals(Commons.ROLE.ROLE_USER+"")){
					return ImageDescriptionP.findImageDescriptionPsByUser(user).getResultList();
				}else if (user.getRole().getName().equals(Commons.ROLE.ROLE_MANAGER + "") || user.getRole().getName().equals(Commons.ROLE.ROLE_ADMIN+"")){
					return ImageDescriptionP.findImageDescriptionPsByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
				}				
				return ImageDescriptionP.findAllImageDescriptionPs();
				}catch (Exception e) {
				log.error(e);//e.printStackTrace();
			}
			return null;
		}//end of method findAll
    	
    	@RemoteMethod
    	public ImageDescriptionP requestImage(ImageDescriptionP instance){
    		try {
    			String instanceIdForImgCreation = instance.getInstanceIdForImgCreation();
    			AssetType assetTypeImageDescription = AssetType.findAssetTypesByNameEquals("" + Commons.ASSET_TYPE.ComputeImage).getSingleResult();
    			User currentUser = Commons.getCurrentUser();
    			Asset asset = Commons.getNewAsset(assetTypeImageDescription, currentUser,"");
    			instance.setAsset(asset);
    			instance = instance.merge();
    			if(true == assetTypeImageDescription.getWorkflowEnabled()){
    				Workflow workflow = Commons.createNewWorkflow(
        					workflowService.createProcessInstance(Commons.PROCESS_DEFN.Image_Request + ""), instance.getId(), asset
        							.getAssetType().getName());
    			}else{
    				instance.setInstanceIdForImgCreation(instanceIdForImgCreation);
    				workflowApproved(instance);
    			}
    			log.info("end of requestImage");
    			return instance;
    		} catch (Exception e) {
    			log.error(e);//e.printStackTrace();
    		}
    		return null;
    	}// end of requestSnapshot(SnapshotInfoP
    	
    	public void workflowApproved(ImageDescriptionP instance){
    		try {
    			imageWorker.createImage(instance.getAsset().getProductCatalog().getInfra(), instance);
    		} catch (Exception e) {
    			log.error(e);//e.printStackTrace();
    		}
    	}
    	
    	
   }//end of class ImageDescriptionPController
   
   

