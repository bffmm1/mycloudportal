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

package in.mycp.controller;

import in.mycp.domain.User;
import in.mycp.utils.Commons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContextFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Charudath Doddanakatte
 * @author cgowdas@gmail.com
 *
 */

@RequestMapping("/login")
@Controller
public class LoginController {
	protected static Logger logger = Logger.getLogger(LoginController.class);


	@RequestMapping(produces = "text/html")
	public String main(HttpServletRequest req, HttpServletResponse resp) {
		logger.info("login");
		//ModelAndView modelandView = new ModelAndView("main");
		HttpSession s = req.getSession(true);
		return "mycplogin";
	}
	
	
	@RequestMapping(value="/dash", produces = "text/html")
	public String dashboard(HttpServletRequest req, HttpServletResponse resp) {
		logger.info("login/dash");
		try {
			User user = Commons.getCurrentUser();
			if(user.getRole().getName().equals(Commons.ROLE.ROLE_USER+"")){
				return "login/userdash";
			}else if(user.getRole().getName().equals(Commons.ROLE.ROLE_MANAGER+"")){
				return "login/managerdash";
			}else if(user.getRole().getName().equals(Commons.ROLE.ROLE_ADMIN+"")){
				return "login/admindash";
			}else if(user.getRole().getName().equals(Commons.ROLE.ROLE_SUPERADMIN+"")){
				return "login/superadmindash";
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.info(e.getMessage());
		}
		
		//cant figure out the user role .
		return "mycplogin";
	}
	
	
	
}
