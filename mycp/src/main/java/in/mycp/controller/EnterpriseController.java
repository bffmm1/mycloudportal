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

@RequestMapping("/enterprise")
@Controller
public class EnterpriseController  {

	@RequestMapping(value="/company", produces = "text/html")
	public String company(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("enterprise/company");
		//ModelAndView modelandView = new ModelAndView("main");
		return "enterprise/company";
	}
	
	@RequestMapping(value="/project", produces = "text/html")
	public String project(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("enterprise/project");
		//ModelAndView modelandView = new ModelAndView("main");
		return "enterprise/project";
	}
	
	@RequestMapping(value="/department", produces = "text/html")
	public String department(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("enterprise/department");
		//ModelAndView modelandView = new ModelAndView("main");
		return "enterprise/department";
	}

	@RequestMapping(value="/manager", produces = "text/html")
	public String manager(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("enterprise/manager");
		//ModelAndView modelandView = new ModelAndView("main");
		return "enterprise/manager";
	}
	
	@RequestMapping(value="/employee", produces = "text/html")
	public String employee(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("enterprise/employee");
		//ModelAndView modelandView = new ModelAndView("main");
		return "enterprise/employee";
	}

}
