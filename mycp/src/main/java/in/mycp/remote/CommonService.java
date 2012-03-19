package in.mycp.remote;

import in.mycp.utils.Commons;
import in.mycp.web.MycpSession;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy(name = "CommonService")
public class CommonService {

	private static final Logger log = Logger.getLogger(AddressInfoPService.class.getName());

	@RemoteMethod
	public MycpSession getCurrentSession() {
		try {
			HttpSession session = WebContextFactory.get().getSession();
			Object obj = session.getAttribute("CurrentSession");
			if (obj != null) {
				return (MycpSession) obj;
			} else {
				MycpSession mycpsession = Commons.getCurrentSession();
				session.setAttribute("CurrentSession", mycpsession);
				return mycpsession;
			}

		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}// end of getCurrentUser
	
	@RemoteMethod
	public static void setSessionMsg(String msg) {
		Commons.setSessionMsg(msg);
	}
	
	@RemoteMethod
	public static String getSessionMsg() {
		return Commons.getSessionMsg();
	}
	
}
