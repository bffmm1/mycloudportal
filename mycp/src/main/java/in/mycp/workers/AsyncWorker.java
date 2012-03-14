package in.mycp.workers;

import in.mycp.domain.Infra;
import in.mycp.domain.InstanceP;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
/*
 * use this as a template for creating further workers
 * like ComputeCreateWorker, StorageCreateWorker etc
 */
@Component("asyncWorker")
public class AsyncWorker  {

	protected static Logger logger = Logger.getLogger(AsyncWorker.class);
	
	@Async
	public void work() {
		String threadName = Thread.currentThread().getName();
		try {
	         logger.info(threadName+" Started.");
	         System.out.println(threadName+" Started.");
	         
	         while(1==1){
	         Thread.sleep(10);
	         logger.info(threadName+" doing-----.");
	         System.out.println(threadName+" doing-----.");
	         
	         }
	         
	         
	        }catch (InterruptedException e) {
	        	logger.info(threadName+" Interrupted.");
	        	System.out.println(threadName+" Interrupted.");
	            Thread.currentThread().interrupt();
	        }
		
	}//work
	
}
