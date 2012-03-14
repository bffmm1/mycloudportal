package in.mycp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
