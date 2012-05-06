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


import in.mycp.domain.Company;
import in.mycp.domain.Infra;
import in.mycp.domain.ProductCatalog;
import in.mycp.domain.RegionP;
import in.mycp.utils.Commons;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

import com.xerox.amazonws.ec2.Jec2;
import com.xerox.amazonws.ec2.RegionInfo;
/**
 * 
 * @author Charudath Doddanakatte
 * @author cgowdas@gmail.com
 *
 */

@RemoteProxy(name="InfraService")
public class InfraService  {

	private static final Logger log = Logger.getLogger(InfraService.class
			.getName());

	@Autowired
	EucalyptusService eucalyptusService;
	
	
	
	@RemoteMethod
	public Infra syncDataFromEuca(String instanceId) {
		Infra instance = Infra.findInfra(new Integer(instanceId));
		try {
			
			if(instance.getSyncInProgress()!=null && instance.getSyncInProgress()){
				log.error("Sync is in progress, Cannot start another one now. Wait till the current one gets over.");
				return null;
			}
			instance.setSyncDate(new Date());
			instance.setSyncInProgress(true);
			instance.setSyncstatus(Commons.sync_status.running.ordinal());
			instance.merge();
			
			eucalyptusService.syncDataFromEuca(instance);
			instance.setSyncInProgress(false);
			instance.setSyncstatus(Commons.sync_status.success.ordinal());
			instance.merge();
			
			
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Sync failed.Error follows.");
			log.error(e);
			try {
				instance.setSyncDate(new Date());
				instance.setSyncInProgress(false);
				instance.setSyncstatus(Commons.sync_status.failed.ordinal());
				instance.merge();
				
			} catch (Exception e2) {
				log.error(e2);
			}
			
		}
		return null;
	}// end of saveOrUpdate(Infra
	

	@RemoteMethod
	public Infra saveOrUpdate(Infra instance) {
		try {
			
			//instance.setRegion(RegionP.findRegionP(instance.getRegion().getId()));
			instance.setCompany(Company.findCompany(instance.getCompany().getId()));
			
			BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
			textEncryptor.setPassword("gothilla");
			String encAccessId = textEncryptor.encrypt(instance.getAccessId());
			String encSecretKey = textEncryptor.encrypt(instance.getSecretKey());
			
			if(instance.getId() == null || instance.getId()<1){
				instance.setAccessId(encAccessId);
				instance.setSecretKey(encSecretKey);
			}else{
				//avoid double encryption
				Infra local = Infra.findInfra(instance.getId());
				if(!local.getSecretKey().equals(instance.getSecretKey())){
					instance.setSecretKey(encSecretKey);
				}
				
				if(!local.getAccessId().equals(instance.getAccessId())){
					instance.setAccessId(encAccessId);
				}
				
			}
			instance = instance.merge();
			return instance;
		} catch (Exception e) {
			log.error(e.getMessage());e.printStackTrace();
		}
		return null;
	}// end of saveOrUpdate(Infra

	@RemoteMethod
	public String remove(int id) {
		try {
			Infra.findInfra(id).remove();
			return "Removed Infra "+id;
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return "Cannot Remove Infra "+id+". look into logs.";
	}// end of method remove(int id

	@RemoteMethod
	public Infra findById(int id) {
		try {
			return Infra.findInfra(id);
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findById(int id

	@RemoteMethod
	public List<Infra> findAll() {
		try {
			
			if(Commons.getCurrentUser().getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN+"")){
				return Infra.findAllInfras();
			}else{
				return Infra.findInfrasByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
			}
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll
	
	@RemoteMethod
	public List<Infra> findAll4Dashboard() {
		try {
			List<Infra> infras = null;
			if(Commons.getCurrentUser().getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN+"")){
				infras = Infra.findAllInfras();
			}else{
				infras = Infra.findInfrasByCompany(Company.findCompany(Commons.getCurrentSession().getCompanyId())).getResultList();
			}
			for (Iterator iterator = infras.iterator(); iterator.hasNext();) {
				Infra infra = (Infra) iterator.next();
				infra.setStatus("loading");
			}
				
			return infras;
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
		}
		return null;
	}// end of method findAll

	
	
	public String getInfraStatus(Infra infra) {
		String status = Commons.EUCA_STATUS.unknown+"";
		try {
			//first , just try to reach and ping the server, then try connecting
			try {
				InetAddress byIpAsName = InetAddress.getByName(infra.getServer());
				SocketAddress sockaddr = new InetSocketAddress(byIpAsName, infra.getPort());
				Socket theSock = new Socket();
				theSock.connect(sockaddr, 2000);
			} catch (Exception e) {
				//log.error(e.getMessage());//e.printStackTrace();
				log.info(e.getMessage());
				status = Commons.EUCA_STATUS.unreachable+"";
				throw new Exception("Cant even open socket to server "+infra.getServer()+".wont try to connect!");
			}
			
			BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
			textEncryptor.setPassword("gothilla");
				String decAccessId = textEncryptor.decrypt(infra.getAccessId());
				String decSecretKey = textEncryptor.decrypt(infra.getSecretKey());
				
			Jec2 ec2 = new Jec2(decAccessId, decSecretKey, false,
					infra.getServer(), infra.getPort());
			ec2.setResourcePrefix(infra.getResourcePrefix());
			ec2.setSignatureVersion(infra.getSignatureVersion());
			ec2.setMaxRetries(1);
			List params = new ArrayList<String>();
			List<RegionInfo> regions = ec2.describeRegions(params);
			for (Iterator iterator = regions.iterator(); iterator.hasNext();) {
				RegionInfo regionInfo = (RegionInfo) iterator.next();
				if(regionInfo !=null){
					status = Commons.EUCA_STATUS.running+"";
				}else if(regionInfo ==null){
					status = Commons.EUCA_STATUS.unknown+"";
				}
				break;
			}
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
			status = Commons.EUCA_STATUS.unknown+"";
		}
		//System.out.println(" = ssssssssssssssssssssssssssssssss ");
		return status;
	}
	
	@RemoteMethod
	public String getInfraStatusDWR(int infraId) {
		Infra infra = Infra.findInfra(infraId);
		String status = Commons.EUCA_STATUS.unknown+"";
		try {
			//first , just try to reach and ping the server, then try connecting
			try {
				InetAddress byIpAsName = InetAddress.getByName(infra.getServer());
				SocketAddress sockaddr = new InetSocketAddress(byIpAsName, infra.getPort());
				Socket theSock = new Socket();
				theSock.connect(sockaddr, 4000);
			} catch (Exception e) {
				//log.error(e.getMessage());//e.printStackTrace();
				log.error(e);
				status = Commons.EUCA_STATUS.unreachable+"";
				log.info("Cant even open socket to server "+infra.getServer()+".wont try to connect!");
				throw new Exception("Cant even open socket to server "+infra.getServer()+".wont try to connect!");
			}
			
			BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
			textEncryptor.setPassword("gothilla");
				String decAccessId = textEncryptor.decrypt(infra.getAccessId());
				String decSecretKey = textEncryptor.decrypt(infra.getSecretKey());
				
			Jec2 ec2 = new Jec2(decAccessId, decSecretKey, false,
					infra.getServer(), infra.getPort());
			ec2.setResourcePrefix(infra.getResourcePrefix());
			ec2.setSignatureVersion(infra.getSignatureVersion());
			ec2.setMaxRetries(1);
			List params = new ArrayList<String>();

			List<RegionInfo> regions = ec2.describeRegions(params);
			for (Iterator iterator = regions.iterator(); iterator.hasNext();) {
				RegionInfo regionInfo = (RegionInfo) iterator.next();
				if(regionInfo !=null){
					status = Commons.EUCA_STATUS.running+"";
				}else if(regionInfo ==null){
					status = Commons.EUCA_STATUS.error+"";
				}
				break;
			}
		} catch (Exception e) {
			log.error(e.getMessage());//e.printStackTrace();
			log.error(e);
			if(e.getMessage().indexOf("Client error") > -1){
				status = Commons.EUCA_STATUS.running+"";
			}else if(e.getMessage().indexOf("Cant even open socket") > -1){
				status = Commons.EUCA_STATUS.unreachable+"";
			}else{
				status = Commons.EUCA_STATUS.unknown+"";
			}
		}
		//System.out.println(" = ssssssssssssssssssssssssssssssss ");
		return infra.getServer()+"="+status;
	}
	
}// end of class InfraController

