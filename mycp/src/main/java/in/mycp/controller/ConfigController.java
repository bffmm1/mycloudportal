package in.mycp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/config")
@Controller
public class ConfigController  {

	@RequestMapping(value="/infra", produces = "text/html")
	public String infra(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("config/infra");
		//ModelAndView modelandView = new ModelAndView("main");
		return "config/infra";
	}

	@RequestMapping(value="/product", produces = "text/html")
	public String product(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("config/product");
		//ModelAndView modelandView = new ModelAndView("main");
		return "config/product";
	}


	@RequestMapping(value="/quotas", produces = "text/html")
	public String quotas(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("config/quotas");
		//ModelAndView modelandView = new ModelAndView("main");
		return "config/quotas";
	}



	@RequestMapping(value="/region", produces = "text/html")
	public String region(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("config/region");
		//ModelAndView modelandView = new ModelAndView("main");
		return "config/region";
	}

	@RequestMapping(value="/assettype", produces = "text/html")
	public String assettype(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("config/assettype");
		//ModelAndView modelandView = new ModelAndView("main");
		return "config/assettype";
	}

	@RequestMapping(value="/metermetric", produces = "text/html")
	public String metermetric(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("config/metermetric");
		//ModelAndView modelandView = new ModelAndView("main");
		return "config/metermetric";
	}

}
