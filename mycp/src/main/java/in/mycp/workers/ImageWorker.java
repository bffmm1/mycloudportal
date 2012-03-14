package in.mycp.workers;

import in.mycp.domain.ImageDescriptionP;
import in.mycp.domain.Infra;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.xerox.amazonws.ec2.ImageDescription;
import com.xerox.amazonws.ec2.Jec2;

@Component("imageWorker")
public class ImageWorker extends Worker {
	protected static Logger logger = Logger.getLogger(ImageWorker.class);

	@Async
	public void createImage(final Infra infra, final ImageDescriptionP image) {
		String threadName = Thread.currentThread().getName();

		try {
			logger.debug("threadName "+threadName+" started.");
			
			Jec2 ec2 = getNewJce2(infra);
			String imageId = "";
			try {
				imageId = ec2.createImage(image.getInstanceIdForImgCreation(), image.getName(), image.getDescription(), false);	
			} catch (Exception e) {
				logger.error(e.getMessage());//e.printStackTrace();
			}
			
			ImageDescription imageFromEuca = ec2.describeImages(Collections.singletonList(imageId)).get(0);
			
			String imageState = imageFromEuca.getImageState();
			
			int START_SLEEP_TIME = 10000;
			while(!"available".equals(imageState)){
				imageFromEuca = ec2.describeImages(Collections.singletonList(imageId)).get(0);
				imageState = imageFromEuca.getImageState();
				logger.info("Image  " + imageFromEuca.getImageId() +" still getting created; sleeping "+ START_SLEEP_TIME + "ms");
				Thread.sleep(START_SLEEP_TIME);
				
			}
			if("available".equals(imageState)){
				
				image.setImageId(imageFromEuca.getImageId());
				image.setImageLocation(imageFromEuca.getImageLocation());
				image.setImageOwnerId(imageFromEuca.getImageOwnerId());
				image.setImageState(imageFromEuca.getImageState());
				image.setIsPublic(imageFromEuca.isPublic());
				List<String> prodCodes = imageFromEuca.getProductCodes();
				String prodCodes_str = "";
				for (Iterator iterator = prodCodes.iterator(); iterator
						.hasNext();) {
					String prodCode = (String) iterator.next();
					prodCodes_str =prodCodes_str+ prodCode+",";
				}
				prodCodes_str = StringUtils.removeEnd(prodCodes_str, ",");
				image.setProductCodes(prodCodes_str);
				image.setArchitecture(imageFromEuca.getArchitecture());
				image.setImageType(imageFromEuca.getImageType());
				image.setKernelId(imageFromEuca.getKernelId());
				image.setRamdiskId(imageFromEuca.getRamdiskId());
				image.setPlatform(imageFromEuca.getPlatform());
				image.setReason(imageFromEuca.getReason());
				image.setImageOwnerAlias(imageFromEuca.getImageOwnerAlias());
				
				image.setName(imageFromEuca.getName());
				image.setDescription(imageFromEuca.getDescription());
				image.setRootDeviceType(imageFromEuca.getRootDeviceType());
				image.setRootDeviceName(imageFromEuca.getRootDeviceName());
				image.setVirtualizationType(imageFromEuca.getVirtualizationType());
				
				image.merge();
			}
			

		} catch (Exception e) {
			logger.error(e.getMessage());//e.printStackTrace();
		}
	}
}
