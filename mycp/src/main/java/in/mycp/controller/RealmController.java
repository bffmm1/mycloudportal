package in.mycp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/realm")
@Controller
public class RealmController  {

	@RequestMapping(value="/user", produces = "text/html")
	public String user(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("realm/user");
		//ModelAndView modelandView = new ModelAndView("main");
		return "realm/user";
	}
	
	

}
