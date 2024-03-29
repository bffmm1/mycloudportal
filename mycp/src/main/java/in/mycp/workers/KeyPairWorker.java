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

package in.mycp.workers;

import java.util.List;

import in.mycp.domain.Asset;
import in.mycp.domain.Infra;
import in.mycp.domain.KeyPairInfoP;
import in.mycp.domain.SnapshotInfoP;
import in.mycp.domain.VolumeInfoP;
import in.mycp.utils.Commons;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.xerox.amazonws.ec2.Jec2;
import com.xerox.amazonws.ec2.KeyPairInfo;

/**
 * 
 * @author Charudath Doddanakatte
 * @author cgowdas@gmail.com
 *
 */

@Component("keyPairWorker")
public class KeyPairWorker extends Worker {

	protected static Logger logger = Logger.getLogger(KeyPairWorker.class);

	@Async
	public void createKeyPair(final Infra infra, final KeyPairInfoP keypair) {
		String threadName = Thread.currentThread().getName();

		try {
			logger.debug("threadName "+threadName+" started.");
			Jec2 ec2 = getNewJce2(infra);
			KeyPairInfo kpi = null;
			try {
				kpi = ec2.createKeyPair(keypair.getKeyName());	
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			logger.info("created kpi.getKeyFingerprint() = " + kpi.getKeyFingerprint());
			
			boolean found =false;
			int START_SLEEP_TIME = 10000;
			long timeout = START_SLEEP_TIME *100;
			long runDuration=0;
			outer: while(!found ){
				runDuration = runDuration+START_SLEEP_TIME;
				if(runDuration > timeout){
					logger.info("Tried enough.Am bored, quitting.");
					break outer;
				}
				List<KeyPairInfo> info = ec2.describeKeyPairs(new String[] {});
				for (KeyPairInfo keypairinfo : info) {
					logger.info("keypair : " + keypairinfo.getKeyName() + ", "
							+ keypairinfo.getKeyFingerprint());
					if(keypairinfo.getKeyName().equals(keypair.getKeyName())){
						found = true;
						break outer;
					}
				}
				logger.info("Keypair  " + keypair.getKeyName()+" still getting created; sleeping "+ START_SLEEP_TIME + "ms");
				Thread.sleep(START_SLEEP_TIME);
			}

			if(found){
				KeyPairInfoP keyPairInfoP = KeyPairInfoP.findKeyPairInfoP(keypair.getId());
				keyPairInfoP.setKeyName(kpi.getKeyName());
				keyPairInfoP.setKeyFingerprint(kpi.getKeyFingerprint());
				keyPairInfoP.setKeyMaterial(kpi.getKeyMaterial());
				keyPairInfoP.setStatus(Commons.keypair_STATUS.active+"");
				keyPairInfoP = keyPairInfoP.merge();
				logger.info("Keypair createKeyPair - created");
				setAssetStartTime(keyPairInfoP.getAsset());
			}
		} catch (Exception e) {
			logger.error(e);//e.printStackTrace();
			KeyPairInfoP keyPairInfoP = KeyPairInfoP.findKeyPairInfoP(keypair.getId());
			keyPairInfoP.setStatus(Commons.keypair_STATUS.failed+"");
			keyPairInfoP = keyPairInfoP.merge();
		}
	}
	
	
	@Async
	public void deleteKeyPair(final Infra infra, final KeyPairInfoP keypair) {
		try {
			
			Jec2 ec2 = getNewJce2(infra);
			
			try {
				ec2.deleteKeyPair(keypair.getKeyName());	
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			boolean found =true;
			int START_SLEEP_TIME = 10000;
			long timeout = START_SLEEP_TIME *100;
			long runDuration=0;
			outer: while(found ){
				runDuration = runDuration+START_SLEEP_TIME;
				if(runDuration > timeout){
					logger.info("Tried enough.Am bored, quitting.");
					break outer;
				}
				List<KeyPairInfo> info = ec2.describeKeyPairs(new String[] {});
				found = false;
				for (KeyPairInfo keypairinfo : info) {
					logger.info("keypair : " + keypairinfo.getKeyName() + ", "
							+ keypairinfo.getKeyFingerprint());
					if(keypairinfo.getKeyName().equals(keypair.getKeyName())){
						found = true;
					}
				}
				//check if the keypair is deleted
				if(!found){
					break outer;
				}
				logger.info("Keypair  " + keypair.getKeyName()+" still getting deleted; sleeping "+ START_SLEEP_TIME + "ms");
				Thread.sleep(START_SLEEP_TIME);
			}
			
			if(!found){
				try {
					KeyPairInfoP keyPairInfoP = (KeyPairInfoP.findKeyPairInfoPsByKeyNameEquals(keypair.getKeyName()).getSingleResult());
					keyPairInfoP.setStatus(Commons.keypair_STATUS.inactive+"");
					keyPairInfoP = keyPairInfoP.merge();
					
					logger.info("Keypair - "+keypair.getKeyName()+" Removed");
					
					setAssetEndTime(keyPairInfoP.getAsset());
					
				} catch (Exception e) {
					logger.error(e.getMessage());//e.printStackTrace();
					throw new Exception("KeyPair in Infra deleted but not in mycp DB");
				}
				
			}

			

		} catch (Exception e) {
			logger.error(e);//e.printStackTrace();
		}
	}
	
}