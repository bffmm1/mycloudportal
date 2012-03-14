package in.mycp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/iaas")
@Controller
public class IaaSController  {


	@RequestMapping(produces = "text/html")
	public String main(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("iaas/main");
		//ModelAndView modelandView = new ModelAndView("main");
		return "iaas/main";
	}
	
	@RequestMapping(value="/compute", produces = "text/html")
	public String compute(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("iaas/compute");
		//ModelAndView modelandView = new ModelAndView("main");
		return "iaas/compute";
	}
	
	@RequestMapping(value="/snapshot", produces = "text/html")
	public String snapshot(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("iaas/snapshot");
		return "iaas/snapshot";
	}
	
	@RequestMapping(value="/desktop", produces = "text/html")
	public String desktop(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("iaas/desktop");
		return "iaas/desktop";
	}
	
	@RequestMapping(value="/image", produces = "text/html")
	public String image(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("iaas/image");
		return "iaas/image";
	}
	
	@RequestMapping(value="/ipaddress", produces = "text/html")
	public String ipaddress(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("iaas/ipaddress");
		return "iaas/ipaddress";
	}
	
	@RequestMapping(value="/keys", produces = "text/html")
	public String keys(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("iaas/keys");
		return "iaas/keys";
	}
	
	@RequestMapping(value="/network", produces = "text/html")
	public String network(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("iaas/network");
		return "iaas/network";
	}
	
	@RequestMapping(value="/secgroup", produces = "text/html")
	public String secgroup(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("iaas/secgroup");
		return "iaas/secgroup";
	}
	
	@RequestMapping(value="/volume", produces = "text/html")
	public String volume(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("iaas/volume");
		return "iaas/volume";
	}
	
}
