package in.mycp.workers;

import java.util.Date;

import org.jasypt.util.text.BasicTextEncryptor;

import com.xerox.amazonws.ec2.Jec2;

import in.mycp.domain.Asset;
import in.mycp.domain.Infra;
import in.mycp.domain.InstanceP;

public class Worker {
  
	public Jec2 getNewJce2(Infra infra) {
		
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword("gothilla");
			String decAccessId = textEncryptor.decrypt(infra.getAccessId());
			String decSecretKey = textEncryptor.decrypt(infra.getSecretKey());
			
		Jec2 ec2 = new Jec2(decAccessId, decSecretKey, false,
				infra.getServer(), infra.getPort());
		ec2.setResourcePrefix(infra.getResourcePrefix());
		ec2.setSignatureVersion(infra.getSignatureVersion());
		return ec2;
	}
  
	public void setAssetEndTime(Asset a){
		//Asset a = instanceLocal.getAsset();
		a.setEndTime(new Date());
		a.setActive(false);
		a.merge();
	}
	
	
	public void setAssetStartTime(Asset a){
		//Asset a = instanceLocal.getAsset();
		a.setStartTime(new Date());
		a.setActive(true);
		a.merge();
	}
	
	
}