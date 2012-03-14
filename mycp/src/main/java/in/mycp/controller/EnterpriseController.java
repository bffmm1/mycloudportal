package in.mycp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
