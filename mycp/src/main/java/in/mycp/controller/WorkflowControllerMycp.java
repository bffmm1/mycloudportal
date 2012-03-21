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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Charudath Doddanakatte
 * @author cgowdas@gmail.com
 *
 */

@RequestMapping("/workflow")
@Controller
public class WorkflowControllerMycp  {

	@RequestMapping(value="/processDefinition", produces = "text/html")
	public String processDefinition(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("workflow/processDefinition");
		//ModelAndView modelandView = new ModelAndView("main");
		return "workflow/processDefinition";
	}
	
	@RequestMapping(value="/processInstance", produces = "text/html")
	public String processInstance(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("workflow/processInstance");
		//ModelAndView modelandView = new ModelAndView("main");
		return "workflow/processInstance";
	}
	
	

}
