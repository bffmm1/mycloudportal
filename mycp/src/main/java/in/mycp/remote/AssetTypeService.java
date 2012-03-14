package in.mycp.remote;


import in.mycp.domain.AssetType;

import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
/**
 * 
 * @author Charudath.Ramegowda
 *
 */

@RemoteProxy(name="AssetTypeService")
public class AssetTypeService  {

	private static final Logger log = Logger.getLogger(AssetTypeService.class
			.getName());

	
	@RemoteMethod
	public void save(AssetType instance) {
		try {
			instance.persist();
			} catch (Exception e) {
			//e.printStackTrace();
				log.error(e.getMessage());
		}
	}// end of save(Infra

	@RemoteMethod
	public AssetType saveOrUpdate(AssetType instance) {
		try {
			return instance.merge();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}// end of saveOrUpdate(Infra

	@RemoteMethod
	public String remove(int id) {
		try {
			AssetType.findAssetType(id).remove();
			return "Removed AssetType "+id;
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return "Cannot Remove AssetType "+id+". look into logs.";
	}// end of method remove(int id

	@RemoteMethod
	public AssetType findById(int id) {
		try {
			return AssetType.findAssetType(id);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List findAll() {
		try {
			return AssetType.findAllAssetTypes();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}// end of method findAll

	
}// end of class AssetType

