package in.mycp.service;

import in.mycp.domain.Infra;
import in.mycp.domain.InstanceP;
import in.mycp.workers.Worker;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.roo.addon.layers.service.RooService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
 
/**
 * Scheduler for handling jobs
 */
@Service
public class SchedulerService {
 protected static Logger logger = Logger.getLogger(SchedulerService.class);
 
 public void runSchedule(Worker worker){
	 //worker.work();
	 logger.info(worker.getClass().getName()+" scheduled.");
 }
 
}