package in.mycp.remote;

import in.mycp.domain.Company;
import in.mycp.domain.Infra;
import in.mycp.domain.ProductCatalog;
import in.mycp.utils.Commons;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * 
 * @author Charudath.Ramegowda
 * 
 */

@RemoteProxy(name = "ProductService")
public class ProductService {

	private static final Logger log = Logger.getLogger(ProductService.class.getName());

	@RemoteMethod
	public ProductCatalog saveOrUpdate(ProductCatalog instance) {
		try {
			// MeterMetric mm =
			// MeterMetric.findMeterMetric(instance.getMeterMetric().getId());
			// instance.setMeterMetric(mm);
			instance.setInfra(Infra.findInfra(instance.getInfra().getId()));

			return instance.merge();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of saveOrUpdate(

	@RemoteMethod
	public String remove(int id) {
		try {
			ProductCatalog.findProductCatalog(id).remove();
			return "Removed Product "+id;
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
			return "Error while removing Product "+id+". Check logs.";
		}
	}// end of method remove(int id

	@RemoteMethod
	public ProductCatalog findById(int id) {
		try {
			return ProductCatalog.findProductCatalog(id);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List<ProductCatalog> findAll() {
		try {
			
			if(Commons.getCurrentUser().getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN+"")){
				return ProductCatalog.findAllProductCatalogs();
			}else{
				return ProductCatalog.findProductCatalogsByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
			}
			
			
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll

	@RemoteMethod
	public List<String> findAllProductTypesAsString() {
		try {
			List<String> productTypes = new ArrayList<String>();
			for (Commons.ProductType d : Commons.ProductType.values()) {
				// System.out.println(" = "+d.getName()+"  "+d);
				productTypes.add(d.getName());
			}
			return productTypes;
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll

	public List<ProductCatalog> findByType(String type) {
		try {
			return ProductCatalog.findProductCatalogsByProductTypeEquals(type).getResultList();
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}

}// end of class ProductCatalog

