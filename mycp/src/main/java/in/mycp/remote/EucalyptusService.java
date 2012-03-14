package in.mycp.remote;

import in.mycp.domain.AddressInfoP;
import in.mycp.domain.Asset;
import in.mycp.domain.AssetType;
import in.mycp.domain.AttachmentInfoP;
import in.mycp.domain.AvailabilityZoneP;
import in.mycp.domain.Company;
import in.mycp.domain.GroupDescriptionP;
import in.mycp.domain.ImageDescriptionP;
import in.mycp.domain.Infra;
import in.mycp.domain.InstanceP;
import in.mycp.domain.IpPermissionP;
import in.mycp.domain.KeyPairInfoP;
import in.mycp.domain.ProductCatalog;
import in.mycp.domain.RegionP;
import in.mycp.domain.SnapshotInfoP;
import in.mycp.domain.User;
import in.mycp.domain.VolumeInfoP;
import in.mycp.utils.Commons;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.jasypt.util.text.BasicTextEncryptor;

import com.xerox.amazonws.ec2.AddressInfo;
import com.xerox.amazonws.ec2.AttachmentInfo;
import com.xerox.amazonws.ec2.AvailabilityZone;
import com.xerox.amazonws.ec2.GroupDescription;
import com.xerox.amazonws.ec2.GroupDescription.IpPermission;
import com.xerox.amazonws.ec2.ImageDescription;
import com.xerox.amazonws.ec2.Jec2;
import com.xerox.amazonws.ec2.KeyPairInfo;
import com.xerox.amazonws.ec2.RegionInfo;
import com.xerox.amazonws.ec2.ReservationDescription;
import com.xerox.amazonws.ec2.ReservationDescription.Instance;
import com.xerox.amazonws.ec2.SnapshotInfo;
import com.xerox.amazonws.ec2.VolumeInfo;

/**
 * Remote service to be used by DWR for Euca setup,cleanup and sync
 * 
 * @author Charudath Doddanakatte
 * 
 */

@RemoteProxy(name = "eucalyptusService")
public class EucalyptusService {

	private static Log logger = LogFactory.getLog(EucalyptusService.class);

	/**
	 * setup the following in the order listed 1.user - admin@mycp.com/password
	 * 2.Region - Bangalore 3.Infra - Euca details 4.example product catalog
	 * produced out of the above infra 5.asset type
	 * 
	 */
	@RemoteMethod
	public void setUpDataForEuca() {
		//TODO
		Infra infra = null;
		try {
			logger.info("start setUpDataForEuca ");

			User user = null;
			try {
				user = User.findUsersByEmailEquals("admin@mycp.com").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (user == null) {
				user = new User();
				user.setActive(new Boolean(true));
				user.setEmail("admin@mycp.com");
				user.setPassword("password");
				user.setRegistereddate(new Date());
				user = user.merge();
			}
			logger.info("user setup");
			Company company=null;
			try {
				company = Company.findCompanysByNameEquals("MyCP IDC").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			
			if (company ==null){
				company = new Company();
				company.setAddress("Up St");
				company.setCity("Bangalore");
				company.setCountry("India");
				company.setEmail("me@mycpidc.com");
				company.setName("MyCP IDC");
				company.setPhone("474747");
				company = company.merge();
			}
			
			RegionP regionP = null;
			try {
				regionP = RegionP.findRegionPsByNameEquals("Bangalore").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (regionP == null) {
				regionP = new RegionP();
				regionP.setName("Bangalore");
				//regionP.setCity("Bangalore");
				//regionP.setCountry("India");
				//regionP.setAddress("007, Gothilla Layout");
				regionP.setUrl("url");
				regionP.setCompany(company);
				regionP = regionP.merge();
			}
			logger.info("region setp");

			try {
				infra = Infra.findInfrasByNameEquals("Eucalyptus Bang").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (infra == null) {
				infra = new Infra();
				infra.setServer("192.168.253.84");
				infra.setPort(8773);
				infra.setAccessId("WKy3rMzOWPouVOxK1p3Ar1C2uRBwa2FBXnCw");
				infra.setSecretKey("TTHGvbf5foJQXhb5UpN3u1Kojetw2310GuGQ");
				infra.setIsSecure(false);
				infra.setResourcePrefix("/services/Eucalyptus");
				infra.setSignatureVersion(1);

				//infra.setRegion(regionP);
				infra.setCompany(company);
				infra.setName("Eucalyptus Bang");
				infra.setDetails("Eucalyptus + enstratus setup in Bangalore");
				infra.setImportDate(new Date());
				infra.setSyncInProgress(true);
				infra = infra.merge();
			}
			logger.info("infra setp");

			ProductCatalog productCatalog = null;
			
			
			Set<ProductCatalog> productCatalogs = new HashSet<ProductCatalog>();

			try {
				productCatalog = ProductCatalog.findProductCatalogsByNameEquals("Eucalyptus Bang IpAddress").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (productCatalog == null) {
				productCatalog = new ProductCatalog();
				productCatalog.setCurrency("INR");
				productCatalog.setDetails("Eucalyptus Bang IpAddress");
				productCatalog.setName("Eucalyptus Bang IpAddress");
				productCatalog.setPrice(12);
				productCatalog.setInfra(infra);
				productCatalog = productCatalog.merge();
				productCatalogs.add(productCatalog);
			}
			logger.info("product setp " + productCatalog.getName());
			// reset the local variable
			productCatalog = null;
			try {
				productCatalog = ProductCatalog.findProductCatalogsByNameEquals("Eucalyptus Bang SecurityGroup").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (productCatalog == null) {
				productCatalog = new ProductCatalog();
				productCatalog.setCurrency("INR");
				productCatalog.setDetails("Eucalyptus Bang SecurityGroup");
				productCatalog.setName("Eucalyptus Bang SecurityGroup");
				productCatalog.setPrice(12);
				productCatalog.setInfra(infra);
				productCatalog = productCatalog.merge();
				productCatalogs.add(productCatalog);
			}
			logger.info("product setp " + productCatalog.getName());

			// reset the local variable
			productCatalog = null;
			try {
				productCatalog = ProductCatalog.findProductCatalogsByNameEquals("Eucalyptus Bang IpPermission").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (productCatalog == null) {
				productCatalog = new ProductCatalog();
				productCatalog.setCurrency("INR");
				productCatalog.setDetails("Eucalyptus Bang IpPermission");
				productCatalog.setName("Eucalyptus Bang IpPermission");
				productCatalog.setPrice(12);
				productCatalog.setInfra(infra);
				productCatalog = productCatalog.merge();
				productCatalogs.add(productCatalog);
			}
			logger.info("product setp " + productCatalog.getName());

			// reset the local variable
			productCatalog = null;
			try {
				productCatalog = ProductCatalog.findProductCatalogsByNameEquals("Eucalyptus Bang Volume").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}

			if (productCatalog == null) {
				productCatalog = new ProductCatalog();
				productCatalog.setCurrency("INR");
				productCatalog.setDetails("Eucalyptus Bang Volume");
				productCatalog.setName("Eucalyptus Bang Volume");
				productCatalog.setPrice(12);
				productCatalog.setInfra(infra);
				productCatalog = productCatalog.merge();
				productCatalogs.add(productCatalog);
			}
			logger.info("product setp " + productCatalog.getName());

			// reset the local variable
			productCatalog = null;
			try {
				productCatalog = ProductCatalog.findProductCatalogsByNameEquals("Eucalyptus Bang VolumeSnapshot").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (productCatalog == null) {
				productCatalog = new ProductCatalog();
				productCatalog.setCurrency("INR");
				productCatalog.setDetails("Eucalyptus Bang VolumeSnapshot");
				productCatalog.setName("Eucalyptus Bang VolumeSnapshot");
				productCatalog.setPrice(12);
				productCatalog.setInfra(infra);
				productCatalog = productCatalog.merge();
				productCatalogs.add(productCatalog);
			}
			logger.info("product setp " + productCatalog.getName());

			// reset the local variable
			productCatalog = null;
			try {
				productCatalog = ProductCatalog.findProductCatalogsByNameEquals("Eucalyptus Bang ComputeImage").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (productCatalog == null) {
				productCatalog = new ProductCatalog();
				productCatalog.setCurrency("INR");
				productCatalog.setDetails("Eucalyptus Bang ComputeImage");
				productCatalog.setName("Eucalyptus Bang ComputeImage");
				productCatalog.setPrice(12);
				productCatalog.setInfra(infra);
				productCatalog = productCatalog.merge();
				productCatalogs.add(productCatalog);
			}
			logger.info("product setp " + productCatalog.getName());

			// reset the local variable
			productCatalog = null;
			try {
				productCatalog = ProductCatalog.findProductCatalogsByNameEquals("Eucalyptus Bang ComputeReservation").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (productCatalog == null) {
				productCatalog = new ProductCatalog();
				productCatalog.setCurrency("INR");
				productCatalog.setDetails("Eucalyptus Bang ComputeReservation");
				productCatalog.setName("Eucalyptus Bang ComputeReservation");
				productCatalog.setPrice(12);
				productCatalog.setInfra(infra);
				productCatalog = productCatalog.merge();
				productCatalogs.add(productCatalog);
			}
			logger.info("product setp " + productCatalog.getName());

			// reset the local variable
			productCatalog = null;
			try {
				productCatalog = ProductCatalog.findProductCatalogsByNameEquals("Eucalyptus Bang ComputeInstance").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (productCatalog == null) {
				productCatalog = new ProductCatalog();
				productCatalog.setCurrency("INR");
				productCatalog.setDetails("Eucalyptus Bang ComputeInstance");
				productCatalog.setName("Eucalyptus Bang ComputeInstance");
				productCatalog.setPrice(12);
				productCatalog.setInfra(infra);
				productCatalog = productCatalog.merge();
				productCatalogs.add(productCatalog);
			}
			logger.info("product setp " + productCatalog.getName());

			// reset the local variable
			productCatalog = null;
			try {
				productCatalog = ProductCatalog.findProductCatalogsByNameEquals("Eucalyptus Bang KeyPair").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (productCatalog == null) {
				productCatalog = new ProductCatalog();
				productCatalog.setCurrency("INR");
				productCatalog.setDetails("Eucalyptus Bang KeyPair");
				productCatalog.setName("Eucalyptus Bang KeyPair");
				productCatalog.setPrice(12);
				productCatalog.setInfra(infra);
				productCatalog = productCatalog.merge();
				productCatalogs.add(productCatalog);
			}
			logger.info("product setp " + productCatalog.getName());

			infra.setProductCatalogs(productCatalogs);
			infra = infra.merge();

			AssetType assetType = null;

			try {
				assetType = AssetType.findAssetTypesByNameEquals("IpAddress").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (assetType == null) {
				assetType = new AssetType();
				assetType.setName("IpAddress");
				assetType.setDescription("IpAddress");
				assetType.merge();
			}

			logger.info("assetType setp " + assetType.getName());

			assetType = null;
			try {
				assetType = AssetType.findAssetTypesByNameEquals("SecurityGroup").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (assetType == null) {
				assetType = new AssetType();
				assetType.setName("SecurityGroup");
				assetType.setDescription("SecurityGroup");
				assetType.merge();
			}
			logger.info("assetType setp " + assetType.getName());

			assetType = null;
			try {
				assetType = AssetType.findAssetTypesByNameEquals("IpPermission").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (assetType == null) {
				assetType = new AssetType();
				assetType.setName("IpPermission");
				assetType.setDescription("IpPermission");
				assetType.merge();
			}
			logger.info("assetType setp " + assetType.getName());

			assetType = null;
			try {
				assetType = AssetType.findAssetTypesByNameEquals("Volume").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (assetType == null) {
				assetType = new AssetType();
				assetType.setName("Volume");
				assetType.setDescription("Volume");
				assetType.merge();
			}
			logger.info("assetType setp " + assetType.getName());

			assetType = null;
			try {
				assetType = AssetType.findAssetTypesByNameEquals("VolumeSnapshot").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (assetType == null) {
				assetType = new AssetType();
				assetType.setName("VolumeSnapshot");
				assetType.setDescription("VolumeSnapshot");
				assetType.merge();
			}
			logger.info("assetType setp " + assetType.getName());

			/*
			 * insert into asset_type(name,description) values('IpAddress','');
			 * insert into asset_type(name,description)
			 * values('SecurityGroup',''); insert into
			 * asset_type(name,description) values('IpPermission',''); insert
			 * into asset_type(name,description) values('Volume',''); insert
			 * into asset_type(name,description) values('VolumeSnapshot','');
			 * insert into asset_type(name,description)
			 * values('ComputeImage',''); insert into
			 * asset_type(name,description) values('ComputeReservation','');
			 * insert into asset_type(name,description)
			 * values('ComputeInstance',''); insert into
			 * asset_type(name,description) values('KeyPair','');
			 */
			assetType = null;
			try {
				assetType = AssetType.findAssetTypesByNameEquals("ComputeImage").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (assetType == null) {
				assetType = new AssetType();
				assetType.setName("ComputeImage");
				assetType.setDescription("ComputeImage");
				assetType.merge();
			}
			logger.info("assetType setp " + assetType.getName());

			assetType = null;
			try {
				assetType = AssetType.findAssetTypesByNameEquals("ComputeReservation").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (assetType == null) {
				assetType = new AssetType();
				assetType.setName("ComputeReservation");
				assetType.setDescription("ComputeReservation");
				assetType.merge();
			}
			logger.info("assetType setp " + assetType.getName());

			assetType = null;
			try {
				assetType = AssetType.findAssetTypesByNameEquals("ComputeInstance").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (assetType == null) {
				assetType = new AssetType();
				assetType.setName("ComputeInstance");
				assetType.setDescription("ComputeInstance");
				assetType.merge();
			}
			logger.info("assetType setp " + assetType.getName());

			assetType = null;
			try {
				assetType = AssetType.findAssetTypesByNameEquals("KeyPair").getSingleResult();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			if (assetType == null) {
				assetType = new AssetType();
				assetType.setName("KeyPair");
				assetType.setDescription("KeyPair");
				assetType.merge();
			}
			logger.info("assetType setp " + assetType.getName());
			logger.info("end setUpDataForEuca");
		} catch (Exception e) {
			logger.info(" error while setUpDataForEuca ");
			logger.error(e.getMessage());//e.printStackTrace();
		} finally {
			logger.info(" setting back sync setSyncInProgress(false)");
			infra.setSyncInProgress(false);
			infra = infra.merge();
		}
	}

	@RemoteMethod
	public void cleanUpDataFromEuca() {

		logger.info("Start cleanUpDataFromEuca");

		List<AddressInfoP> addressInfoPs = AddressInfoP.findAllAddressInfoPs();

		for (Iterator iterator = addressInfoPs.iterator(); iterator.hasNext();) {
			AddressInfoP addressInfoP = (AddressInfoP) iterator.next();
			addressInfoP.remove();
		}
		logger.info("AddressInfoP removed ");

		List<IpPermissionP> ipPermissionPs = IpPermissionP.findAllIpPermissionPs();
		for (Iterator iterator = ipPermissionPs.iterator(); iterator.hasNext();) {
			IpPermissionP ipPermissionP = (IpPermissionP) iterator.next();
			ipPermissionP.remove();
		}
		logger.info("ipPermissionPs removed ");

		List<GroupDescriptionP> groupDescriptionPs = GroupDescriptionP.findAllGroupDescriptionPs();
		for (Iterator iterator = groupDescriptionPs.iterator(); iterator.hasNext();) {
			GroupDescriptionP groupDescriptionP = (GroupDescriptionP) iterator.next();
			groupDescriptionP.remove();

		}
		logger.info("groupDescriptionPs removed ");

		List<SnapshotInfoP> snapshotInfoPs = SnapshotInfoP.findAllSnapshotInfoPs();
		for (Iterator iterator = snapshotInfoPs.iterator(); iterator.hasNext();) {
			SnapshotInfoP snapshotInfoP = (SnapshotInfoP) iterator.next();
			snapshotInfoP.remove();
		}
		logger.info("snapshotInfoPs removed ");

		List<VolumeInfoP> volumeInfoPs = VolumeInfoP.findAllVolumeInfoPs();
		for (Iterator iterator = volumeInfoPs.iterator(); iterator.hasNext();) {
			VolumeInfoP volumeInfoP = (VolumeInfoP) iterator.next();
			volumeInfoP.remove();
		}
		logger.info("VolumeInfoP removed ");

		List<InstanceP> InstancePs = InstanceP.findAllInstancePs();
		for (Iterator iterator = InstancePs.iterator(); iterator.hasNext();) {
			InstanceP instanceP = (InstanceP) iterator.next();
			instanceP.remove();
		}
		logger.info("InstancePs removed ");

		/*List<ReservationDescriptionP> ReservationDescriptionPs = ReservationDescriptionP.findAllReservationDescriptionPs();
		for (Iterator iterator = ReservationDescriptionPs.iterator(); iterator.hasNext();) {
			ReservationDescriptionP reservationDescriptionP = (ReservationDescriptionP) iterator.next();
			reservationDescriptionP.remove();
		}
		logger.info("reservationDescriptionP removed ");
*/
		List<ImageDescriptionP> imageDescriptionPs = ImageDescriptionP.findAllImageDescriptionPs();
		for (Iterator iterator = imageDescriptionPs.iterator(); iterator.hasNext();) {
			ImageDescriptionP imageDescriptionP = (ImageDescriptionP) iterator.next();
			imageDescriptionP.remove();
		}
		logger.info("imageDescriptionPs removed ");

		List<KeyPairInfoP> KeyPairInfoPs = KeyPairInfoP.findAllKeyPairInfoPs();
		for (Iterator iterator = KeyPairInfoPs.iterator(); iterator.hasNext();) {
			KeyPairInfoP keyPairInfoP = (KeyPairInfoP) iterator.next();
			keyPairInfoP.remove();
		}
		logger.info("KeyPairInfoPs removed ");

		List<Asset> assets = Asset.findAllAssets();
		for (Iterator iterator = assets.iterator(); iterator.hasNext();) {
			Asset asset = (Asset) iterator.next();
			asset.remove();
		}
		logger.info("Asset removed ");

		List<User> users = User.findAllUsers();
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			user.remove();
		}
		logger.info("Users removed ");

		/*
		 * AddressInfo Asset GroupDescriptionP IpPermission VolumeInfoP
		 * SnapshotInfoP ImageDescriptionP ReservationDescriptionP
		 * 
		 * InstanceP KeyPairInfoP
		 */
		logger.info("End cleanUpDataFromEuca");

	}

	@RemoteMethod
	public void syncDataFromEuca() {

		try {
			User currentUser = null;
			try {
				currentUser = Commons.getCurrentUser();
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
				throw new Exception("run the setup data ");
			}

			AssetType assetType = new AssetType();
			AssetType assetTypeIpAddress = AssetType.findAssetTypesByNameEquals("IpAddress").getSingleResult();
			AssetType assetTypeSecurityGroup = AssetType.findAssetTypesByNameEquals("SecurityGroup").getSingleResult();
			AssetType assetTypeIpPermission = AssetType.findAssetTypesByNameEquals("IpPermission").getSingleResult();
			AssetType assetTypeVolume = AssetType.findAssetTypesByNameEquals("Volume").getSingleResult();
			AssetType assetTypeVolumeSnapshot = AssetType.findAssetTypesByNameEquals("VolumeSnapshot").getSingleResult();
			AssetType assetTypeComputeImage = AssetType.findAssetTypesByNameEquals("ComputeImage").getSingleResult();
			AssetType assetTypeComputeReservation = AssetType.findAssetTypesByNameEquals("ComputeReservation").getSingleResult();
			AssetType assetTypeComputeInstance = AssetType.findAssetTypesByNameEquals("ComputeInstance").getSingleResult();
			AssetType assetTypeKeyPair = AssetType.findAssetTypesByNameEquals("KeyPair").getSingleResult();

			Date start = new Date();
			logger.info("Connect Start:" + new Date());
			Jec2 ec2 = null;
			try {
				ec2 = new Jec2("WKy3rMzOWPouVOxK1p3Ar1C2uRBwa2FBXnCw", "TTHGvbf5foJQXhb5UpN3u1Kojetw2310GuGQ", false,
				// "115.249.231.107", 8773);
						"192.168.253.84", 8773);

				ec2.setResourcePrefix("/services/Eucalyptus");
				ec2.setSignatureVersion(1);
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			List<String> params = new ArrayList<String>();
			List<AddressInfo> addressInfos = ec2.describeAddresses(params);

			logger.info("Available addresses @ " + (new Date().getTime() - start.getTime()) / 1000 + " S");
			for (Iterator iterator = addressInfos.iterator(); iterator.hasNext();) {
				AddressInfo addressInfo = (AddressInfo) iterator.next();
				logger.info(addressInfo.getInstanceId() + "-----" + addressInfo.getPublicIp());
				if (addressInfo.getInstanceId() == null || addressInfo.getInstanceId().startsWith("nobody")) {
					// do not import free IPs
					continue;
				}
				AddressInfoP addressInfoP = null;
				try {
					addressInfoP = AddressInfoP.findAddressInfoPsByPublicIpEquals(addressInfo.getPublicIp()).getSingleResult();

				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
				}
				if (addressInfoP == null) {
					addressInfoP = new AddressInfoP();
					Asset asset = Commons.getNewAsset(assetTypeIpAddress, currentUser,
							ProductCatalog.findProductCatalogsByNameEquals("Ip Address @ Eucalyptus").getSingleResult());
					addressInfoP.setAsset(asset);
					addressInfoP = addressInfoP.merge();

				}

				addressInfoP.setInstanceId(addressInfo.getInstanceId());
				addressInfoP.setPublicIp(addressInfo.getPublicIp());
				addressInfoP = addressInfoP.merge();
			}

			params = new ArrayList<String>();
			List<RegionInfo> regions = ec2.describeRegions(params);
			for (Iterator iterator = regions.iterator(); iterator.hasNext();) {
				RegionInfo regionInfo = (RegionInfo) iterator.next();
				logger.info("regionInfo = " + regionInfo);
				RegionP regionP = null;
				try {
					regionP = RegionP.findRegionPsByNameEquals(regionInfo.getName()).getSingleResult();
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
				}

				if (regionP != null) {

				} else {
					regionP = new RegionP();
				}

				regionP.setName(regionInfo.getName());
				regionP.setUrl(regionInfo.getUrl());

				regionP = regionP.merge();

			}

			params = new ArrayList<String>();

			List<AvailabilityZone> zones = ec2.describeAvailabilityZones(params);
			for (Iterator iterator = zones.iterator(); iterator.hasNext();) {
				AvailabilityZone availabilityZone = (AvailabilityZone) iterator.next();
				logger.info("availabilityZone = " + availabilityZone);

				AvailabilityZoneP availabilityZoneP = null;
				try {
					availabilityZoneP = AvailabilityZoneP.findAvailabilityZonePsByNameEquals(availabilityZone.getName()).getSingleResult();
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
				}

				if (availabilityZoneP != null) {

				} else {
					availabilityZoneP = new AvailabilityZoneP();
				}

				availabilityZoneP.setName(availabilityZone.getName());
				availabilityZoneP.setRegionName(availabilityZone.getRegionName());
				availabilityZoneP.setState(availabilityZone.getState());
				availabilityZoneP = availabilityZoneP.merge();
			}

			params = new ArrayList<String>();
			List<GroupDescription> groupDescs = ec2.describeSecurityGroups(params);
			logger.info("Available Security groups @" + (new Date().getTime() - start.getTime()) / 1000 + " S");
			for (Iterator iterator = groupDescs.iterator(); iterator.hasNext();) {
				GroupDescription groupDescription = (GroupDescription) iterator.next();
				logger.info(groupDescription);
				GroupDescriptionP descriptionP = null;
				try {
					descriptionP = GroupDescriptionP.findGroupDescriptionPsByNameEquals(groupDescription.getName()).getSingleResult();
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
				}

				if (descriptionP != null) {
					descriptionP.setDescripton(groupDescription.getDescription());
					descriptionP.setOwner(groupDescription.getOwner());
				} else {
					descriptionP = new GroupDescriptionP();
					descriptionP.setName(groupDescription.getName());
					descriptionP.setDescripton(groupDescription.getDescription());
					descriptionP.setOwner(groupDescription.getOwner());
					Asset asset = Commons.getNewAsset(assetTypeSecurityGroup, currentUser,
							ProductCatalog.findProductCatalogsByNameEquals("Security Group @ Eucalyptus").getSingleResult());

					descriptionP.setAsset(asset);
				}
				descriptionP = descriptionP.merge();

				List<IpPermission> ipPermissions = groupDescription.getPermissions();

				Set<IpPermissionP> ipPermissionPs = new HashSet<IpPermissionP>();

				for (Iterator iterator2 = ipPermissions.iterator(); iterator2.hasNext();) {
					IpPermission ipPermission = (IpPermission) iterator2.next();

					logger.info(ipPermission.getFromPort() + ipPermission.getProtocol() + ipPermission.getToPort()
							+ ipPermission.getIpRanges());
					IpPermissionP ipPermissionP = null;
					try {
						// .findIpPermissionPsByProtocolEqualsAndToPortEqualsAndFromPortEquals(
						ipPermissionP = IpPermissionP.findIpPermissionPsByGroupDescriptionAndProtocolEqualsAndFromPortEquals(descriptionP,
								ipPermission.getProtocol(), ipPermission.getFromPort()).getSingleResult();
						// ipPermission.getProtocol(), ipPermission.getToPort(),
						// ipPermission.getFromPort()).getSingleResult();
					} catch (Exception e) {
						logger.error(e.getMessage());//e.printStackTrace();
					}

					if (ipPermissionP != null) {
						// do not create a new object
					} else {
						ipPermissionP = new IpPermissionP();
					}
					List<String> cidrIps = ipPermission.getIpRanges();
					String cidrIps_str = "";
					for (Iterator iterator3 = cidrIps.iterator(); iterator3.hasNext();) {
						String string = (String) iterator3.next();
						cidrIps_str = cidrIps_str + string + ",";
					}
					cidrIps_str = StringUtils.removeEnd(cidrIps_str, ",");
					List<String[]> uidGroupPairs = ipPermission.getUidGroupPairs();
					String uidGroupPairs_str = "";
					for (Iterator iterator3 = uidGroupPairs.iterator(); iterator3.hasNext();) {
						String[] strArray = (String[]) iterator3.next();
						String strArray_str = "";
						for (int i = 0; i < strArray.length; i++) {
							strArray_str = strArray_str + strArray[i] + ",";
						}
						strArray_str = StringUtils.removeEnd(strArray_str, ",");
						uidGroupPairs_str = uidGroupPairs_str + strArray_str + ",";
					}
					uidGroupPairs_str = StringUtils.removeEnd(uidGroupPairs_str, ",");

					ipPermissionP.setCidrIps(cidrIps_str);
					ipPermissionP.setUidGroupPairs(uidGroupPairs_str);

					ipPermissionP.setFromPort(ipPermission.getFromPort());
					ipPermissionP.setGroupDescription(descriptionP);
					ipPermissionP.setProtocol(ipPermission.getProtocol());
					ipPermissionP.setToPort(ipPermission.getToPort());

					descriptionP = descriptionP.merge();
					ipPermissionP.setGroupDescription(descriptionP);
					ipPermissionP = ipPermissionP.merge();
					if (descriptionP.getIpPermissionPs() != null) {
						descriptionP.getIpPermissionPs().add(ipPermissionP);
					} else {
						Set<IpPermissionP> ipPermissionPsNew = new HashSet<IpPermissionP>();
						ipPermissionPsNew.add(ipPermissionP);
						descriptionP.setIpPermissionPs(ipPermissionPsNew);
					}

					descriptionP = descriptionP.merge();
				}
				/*
				 * GroupDescriptionP descriptionP = new GroupDescriptionP(null,
				 * groupDescription.getName(),
				 * groupDescription.getDescription(),
				 * groupDescription.getOwner(), null);
				 */

			}// end of for groupDescs.iterator()

			params = new ArrayList<String>();
			List<VolumeInfo> volumes = ec2.describeVolumes(params);
			logger.info("Available Volumes");
			for (Iterator iterator = volumes.iterator(); iterator.hasNext();) {
				VolumeInfo volumeInfo = (VolumeInfo) iterator.next();
				logger.info(volumeInfo.getSize() + volumeInfo.getVolumeId() + volumeInfo.getCreateTime().getTime());
				VolumeInfoP volumeInfoP = null;
				try {
					volumeInfoP = VolumeInfoP.findVolumeInfoPsByVolumeIdEquals(volumeInfo.getVolumeId()).getSingleResult();
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
				}
				if (volumeInfoP != null) {

				} else {
					volumeInfoP = new VolumeInfoP();
					Asset asset = Commons.getNewAsset(assetTypeVolume, currentUser,
							ProductCatalog.findProductCatalogsByNameEquals("Volume @ Eucalyptus").getSingleResult());

					volumeInfoP.setAsset(asset);

				}
				volumeInfoP.setSize(Integer.parseInt(volumeInfo.getSize()));
				volumeInfoP.setVolumeId(volumeInfo.getVolumeId());
				volumeInfoP.setCreateTime(volumeInfo.getCreateTime().getTime());
				volumeInfoP.setZone(volumeInfo.getZone());
				volumeInfoP.setStatus(volumeInfo.getStatus());
				volumeInfoP.setSnapshotId(volumeInfo.getSnapshotId());
				volumeInfoP = volumeInfoP.merge();

				List<AttachmentInfoP> existingAttachments = AttachmentInfoP.findAttachmentInfoPsByVolumeIdEquals(volumeInfoP.getVolumeId())
						.getResultList();

				if (existingAttachments != null) {
					for (Iterator iterator2 = existingAttachments.iterator(); iterator2.hasNext();) {
						AttachmentInfoP attachmentInfoP = (AttachmentInfoP) iterator2.next();
						attachmentInfoP.remove();

					}
				}

				List<AttachmentInfo> attachments = volumeInfo.getAttachmentInfo();
				Set<AttachmentInfoP> attachments4Store = new HashSet<AttachmentInfoP>();
				if (attachments != null && attachments.size() > 0) {
					for (Iterator iterator2 = attachments.iterator(); iterator2.hasNext();) {
						AttachmentInfo attachmentInfo = (AttachmentInfo) iterator2.next();

						AttachmentInfoP attachmentInfoP = new AttachmentInfoP();
						attachmentInfoP.setAttachTime(attachmentInfo.getAttachTime().getTime());
						attachmentInfoP.setDevice(attachmentInfo.getDevice());
						attachmentInfoP.setInstanceId(attachmentInfo.getInstanceId());
						attachmentInfoP.setVolumeId(attachmentInfo.getVolumeId());
						attachmentInfoP.setStatus(attachmentInfo.getStatus());
						attachmentInfoP.setVolumeInfo(volumeInfoP);
						attachmentInfoP = attachmentInfoP.merge();
						attachments4Store.add(attachmentInfoP);
					}
				}

				volumeInfoP.setAttachmentInfoPs(attachments4Store);
				volumeInfoP = volumeInfoP.merge();

			}// end of for volumes.iterator()

			params = new ArrayList<String>();
			List<SnapshotInfo> snapshots = ec2.describeSnapshots(params);
			logger.info("Available Snapshots");
			for (Iterator iterator = snapshots.iterator(); iterator.hasNext();) {
				SnapshotInfo snapshotInfo = (SnapshotInfo) iterator.next();
				logger.info(snapshotInfo.getDescription() + snapshotInfo.getProgress() + snapshotInfo.getStatus()
						+ snapshotInfo.getVolumeId() + snapshotInfo.getStartTime().getTime());
				SnapshotInfoP snapshotInfoP = null;
				try {
					snapshotInfoP = SnapshotInfoP.findSnapshotInfoPsBySnapshotIdEquals(snapshotInfo.getSnapshotId()).getSingleResult();
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
				}

				if (snapshotInfoP != null) {

				} else {
					snapshotInfoP = new SnapshotInfoP();
					Asset asset = Commons.getNewAsset(assetTypeVolumeSnapshot, currentUser,
							ProductCatalog.findProductCatalogsByNameEquals("Snapshot @ Eucalyptus").getSingleResult());

					snapshotInfoP.setAsset(asset);
					snapshotInfoP = snapshotInfoP.merge();

				}

				snapshotInfoP.setDescription(snapshotInfo.getDescription());
				snapshotInfoP.setProgress(snapshotInfo.getProgress());
				snapshotInfoP.setVolumeId(snapshotInfo.getVolumeId());
				snapshotInfoP.setStartTime(snapshotInfo.getStartTime().getTime());
				snapshotInfoP.setSnapshotId(snapshotInfo.getSnapshotId());
				snapshotInfoP.setStatus(snapshotInfo.getStatus());
				snapshotInfoP.setOwnerId(snapshotInfo.getOwnerId());
				snapshotInfoP.setVolumeSize(snapshotInfo.getVolumeSize());
				snapshotInfoP.setOwnerAlias(snapshotInfo.getOwnerAlias());

				snapshotInfoP = snapshotInfoP.merge();

			}// end of for snapshots.iterator()

			List<ImageDescription> images = ec2.describeImages(params);
			logger.info("Available Images");
			for (ImageDescription img : images) {
				if (img.getImageState().equals("available")) {
					logger.info(img.getImageId() + "\t" + img.getImageLocation() + "\t" + img.getImageOwnerId());
					ImageDescriptionP imageDescriptionP = null;
					try {
						imageDescriptionP = ImageDescriptionP.findImageDescriptionPsByImageIdEquals(img.getImageId()).getSingleResult();
					} catch (Exception e) {
						logger.error(e.getMessage());//e.printStackTrace();
					}
					if (imageDescriptionP != null) {

					} else {
						imageDescriptionP = new ImageDescriptionP();
						Asset asset = Commons.getNewAsset(assetTypeComputeImage, currentUser,
								ProductCatalog.findProductCatalogsByNameEquals("Image @ Eucalyptus").getSingleResult());

						imageDescriptionP.setAsset(asset);
						imageDescriptionP = imageDescriptionP.merge();
					}

					imageDescriptionP.setImageId(img.getImageId());

					imageDescriptionP.setImageLocation(img.getImageLocation());
					imageDescriptionP.setImageOwnerId(img.getImageOwnerId());
					imageDescriptionP.setImageState(img.getImageState());
					imageDescriptionP.setIsPublic(img.isPublic());
					List<String> prodCodes = img.getProductCodes();
					String prodCodes_str = "";
					for (Iterator iterator = prodCodes.iterator(); iterator.hasNext();) {
						String prodCode = (String) iterator.next();
						prodCodes_str = prodCodes_str + prodCode + ",";
					}
					prodCodes_str = StringUtils.removeEnd(prodCodes_str, ",");
					imageDescriptionP.setProductCodes(prodCodes_str);
					imageDescriptionP.setArchitecture(img.getArchitecture());
					imageDescriptionP.setImageType(img.getImageType());
					imageDescriptionP.setKernelId(img.getKernelId());
					imageDescriptionP.setRamdiskId(img.getRamdiskId());
					imageDescriptionP.setPlatform(img.getPlatform());
					imageDescriptionP.setReason(img.getReason());
					imageDescriptionP.setImageOwnerAlias(img.getImageOwnerAlias());

					imageDescriptionP.setName(img.getName());
					imageDescriptionP.setDescription(img.getDescription());
					imageDescriptionP.setRootDeviceType(img.getRootDeviceType());
					imageDescriptionP.setRootDeviceName(img.getRootDeviceName());
					imageDescriptionP.setVirtualizationType(img.getVirtualizationType());

					imageDescriptionP = imageDescriptionP.merge();
				}
			}// end of for ImageDescription img : images

			params = new ArrayList<String>();
			List<ReservationDescription> instances = ec2.describeInstances(params);
			logger.info("Instances");
			String instanceId = "";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
			Date now = new Date();
			for (ReservationDescription res : instances) {
				logger.info(res.getOwner() + "\t" + res.getReservationId());
				/*ReservationDescriptionP reservationDescriptionP = null;*/

				/*try {
					reservationDescriptionP = ReservationDescriptionP.findReservationDescriptionPsByResIdEquals(res.getReservationId())
							.getSingleResult();
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
				}*/

				/*if (reservationDescriptionP != null) {

				} else {
					reservationDescriptionP = new ReservationDescriptionP();
					Asset asset = Commons.getNewAsset(assetTypeComputeReservation, currentUser);

					reservationDescriptionP.setAsset(asset);
					reservationDescriptionP = reservationDescriptionP.merge();

				}

				reservationDescriptionP.setResId(res.getReservationId());
				reservationDescriptionP.setRequesterId(res.getRequesterId());
				reservationDescriptionP.setRequestId(res.getRequestId());
				reservationDescriptionP.setOwner(res.getOwner());

				reservationDescriptionP = reservationDescriptionP.merge();
*/
				if (res.getInstances() != null) {
					HashSet<InstanceP> instancesP = new HashSet<InstanceP>();
					for (Instance inst : res.getInstances()) {
						Date then = inst.getLaunchTime().getTime();
						long timediff = now.getTime() - then.getTime();
						long hours = timediff / (1000 * 60 * 60);
						// inst.get
						// c.getTimeInMillis();
						logger.info("\t" + inst.getImageId() + "\t" + inst.getDnsName() + "\t" + inst.getState() + "\t" + inst.getKeyName()
								+ "\t" + formatter.format(then) + "\t(H)" + hours

								+ "\t" + inst.getInstanceType().getTypeId() + inst.getPlatform());

						InstanceP instanceP = null;
						try {
							instanceP = InstanceP.findInstancePsByInstanceIdEquals(inst.getInstanceId()).getSingleResult();
						} catch (Exception e) {
							logger.error(e.getMessage());//e.printStackTrace();
						}

						if (instanceP != null) {

						} else {
							instanceP = new InstanceP();
							Asset asset = Commons.getNewAsset(assetTypeComputeInstance, currentUser,
									ProductCatalog.findProductCatalogsByNameEquals("Compute @ Eucalyptus").getSingleResult());
							instanceP.setAsset(asset);
						}

						instanceP.setInstanceId(inst.getInstanceId());
						instanceP.setImageId(inst.getImageId());
						instanceP.setDnsName(inst.getDnsName());
						instanceP.setState(inst.getState());
						instanceP.setKeyName(inst.getKeyName());
						instanceP.setInstanceType(inst.getInstanceType().getTypeId());
						instanceP.setPlatform(inst.getPlatform());
						instanceP.setPrivateDnsName(inst.getPrivateDnsName());
						instanceP.setReason(inst.getReason());
						instanceP.setLaunchIndex(inst.getLaunchIndex());

						List<String> prodCodes = inst.getProductCodes();
						String prodCodes_str = "";
						for (Iterator iterator = prodCodes.iterator(); iterator.hasNext();) {
							String prodCode = (String) iterator.next();
							prodCodes_str = prodCodes_str + prodCode + ",";
						}
						prodCodes_str = StringUtils.removeEnd(prodCodes_str, ",");

						instanceP.setProductCodes(prodCodes_str);
						instanceP.setLaunchTime(inst.getLaunchTime().getTime());
						instanceP.setAvailabilityZone(inst.getAvailabilityZone());
						instanceP.setKernelId(inst.getKernelId());
						instanceP.setRamdiskId(inst.getRamdiskId());
						instanceP.setStateCode(inst.getStateCode());
						// instanceP.setMonitoring(inst.get)
						instanceP.setSubnetId(inst.getSubnetId());
						instanceP.setVpcId(inst.getVpcId());
						instanceP.setPrivateIpAddress(inst.getPrivateIpAddress());
						instanceP.setIpAddress(inst.getIpAddress());
						instanceP.setArchitecture(inst.getArchitecture());
						instanceP.setRootDeviceType(inst.getRootDeviceType());
						instanceP.setRootDeviceName(inst.getRootDeviceName());
						instanceP.setInstanceLifecycle(inst.getInstanceLifecycle());
						instanceP.setSpotInstanceRequestId(inst.getSpotInstanceRequestId());
						instanceP.setVirtualizationType(inst.getVirtualizationType());
						// instanceP.setClientToken(inst.get)

						//instanceP.setReservationDescription(reservationDescriptionP);

						instanceP = instanceP.merge();

						instancesP.add(instanceP);

					}
					/*
					 * reservationDescriptionP.setInstancePs(instancesP);
					 * reservationDescriptionP =
					 * reservationDescriptionP.merge();
					 */
				}
			}// end of ReservationDescription res : instances

			List<KeyPairInfo> info = ec2.describeKeyPairs(new String[] {});
			logger.info("keypair list");
			for (KeyPairInfo keypairinfo : info) {
				logger.info("keypair : " + keypairinfo.getKeyName() + ", " + keypairinfo.getKeyFingerprint());
				KeyPairInfoP keyPairInfoP = null;
				try {
					keyPairInfoP = KeyPairInfoP.findKeyPairInfoPsByKeyNameEquals(keypairinfo.getKeyName()).getSingleResult();
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
				}

				if (keyPairInfoP != null) {

				} else {
					keyPairInfoP = new KeyPairInfoP();
					Asset asset = Commons.getNewAsset(assetTypeKeyPair, currentUser,
							ProductCatalog.findProductCatalogsByNameEquals("KeyPair @ Eucalyptus").getSingleResult());

					keyPairInfoP.setAsset(asset);
					keyPairInfoP = keyPairInfoP.merge();
				}
				keyPairInfoP.setKeyName(keypairinfo.getKeyName());
				keyPairInfoP.setKeyFingerprint(keypairinfo.getKeyFingerprint());
				keyPairInfoP.setKeyMaterial(keypairinfo.getKeyMaterial());
				keyPairInfoP = keyPairInfoP.merge();

			}// end of for KeyPairInfo i : info)

		} catch (Exception e) {
			logger.error(e.getMessage());//e.printStackTrace();
		}

	}// end of sync
	
	

}// end of class
