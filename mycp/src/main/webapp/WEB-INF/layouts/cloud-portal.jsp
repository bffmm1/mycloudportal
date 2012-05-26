
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<%@ page import="java.util.*" %>
<%@ page import="org.springframework.security.authentication.BadCredentialsException" %>

<html xmlns:og="http://ogp.me/ns#">
	<head>
	<title>Cloud Portal open source</title>
	<meta name="author" content="Charudath Doddanakatte" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta name="description" content="my cloud portal is a generic self service portal for the cloud"/>
  	<meta name="keywords" content="cloud portal"/>
  	<meta id="MetaCopyright" name="COPYRIGHT" content="Copyright 2012 my cloud portal" />
	<meta name="ROBOTS" content="INDEX, FOLLOW"/>
	<link rel="image_src" href="/images/mycpSmall.PNG" type="image/x-icon" />
	<meta property="og:title" content="My Cloud Portal" />
	<meta property="og:type" content="website" />       
	<meta property="og:image" content="http://mycloudportal.in/images/mycpSmall.PNG" />      
	<meta property="og:url" content="http://mycloudportal.in" />
	
<jsp:include page="layoutheader.jsp"></jsp:include>

  	<div id="wrapper">
		<div class="leftMain">
			<div class="intro">
				
				<p>As an enterprise cloud management solution the idea of my cloud portal is to provide a single pane of glass for the user to
				<span>Consume</span> and <span>Govern</span> any service from any cloud. This cloud  management solution can be used as a cloud broker, cloud gateway or a cloud control panel.</p>
				<p>In its current state it enables users to consume, monitor and manage services from <b>Eucalyptus private cloud & AWS public cloud
				 (Amazon Web Services).
				</b></p>
			</div>
			<div>
				<a target="_blank" href="http://www.youtube.com/v/BOqpyErM4PI?version=3&amp;feature=player_detailpage&amp;loop=1&amp;autoplay=1&amp;modestbranding=1&amp;rel=0&amp;theme=light">
						<img alt="cloud-portal" src="/images/mycpSmall.PNG" style="float:none; padding-left: 200px;">
					</a>
			</div>
			
			
			<h1 style="color: #FF6600;font-size: 20px;margin: 5px;">My Cloud Portal features</h1>
			<div class="features">
				<div class="icon"><a href="/cloud-broker.jsp"><img alt="" src="/images/eucalyptus_cloud.png"></a></div>
				<h5> Connect &amp; Manage</h5>
				<p>Acts as a cloud services gateway or a cloud broker to help you create and manage infrastructure services like Compute, Volume, Snapshot, KeyPair, Security Group &amp; Ip Address from IaaS cloud.</p>
			</div>
			<div class="features">
				<div class="icon"><a href="/cloud-management.jsp"><img alt="" src="/images/accesscontrol_home.png"></a></div>
				<h5>User and Access control</h5>
				<p>Use the cloud governance features to define role based access controls and security at the Manager, Admin and User levels using the cloud control panel.</p>
			</div>
			<div class="features">
				<div class="icon"><a href="/self-service-portal.jsp"><img alt="" src="/images/reports_home.png"></a></div>
				<h5>Usage &amp; Cost reports</h5>
				<p>Use the cloud management features to generate Project, Department, Account or User based reports of your resources and their costs.</p>
			</div>
			<div class="features">
				<div class="icon"><a href="/cloud-management.jsp"><img alt="" src="/images/dashboard_home.png"></a></div>
				<h5>Dashboards</h5>
				<p>As a cloud management solution, you can get a birds eye view of your cloud's health, workflows, costs and usage and that too, based on predefined roles.</p>
			</div>
			<div class="features">
				<div class="icon"><a href="/cloud-management.jsp"><img alt="" src="/images/workflows_home.png"></a></div>
				<h5>Workflows</h5>
				<p>Use the cloud governance features to setup workflows mirroring your business processes and control your internal cloud consumption by approving or rejecting infrastructure requests by your users.</p>
			</div>
			<div class="features">
				<div class="icon"><a href="/self-service-portal.jsp"><img alt="" src="/images/catalog_home.png"></a></div>
				<h5>Product Catalog</h5>
				<p>Set up, package &amp; price your infrastructure  products in your local currency.</p>
			</div>
		</div>

		<div class="rightMain">
			<div style="padding: 15px 0;">
				<a href="http://code.google.com/p/mycloudportal/" target="_blank"><img src="/images/opensource.png" style="float:left; margin-right:10px" height="48" width="48"></a>
				My Cloud Portal is <font style="font-weight: bold;">open source</font> and <font style="font-weight: bold;">free</font> to
				<a href="http://code.google.com/p/mycloudportal/" target="_blank"> download &amp; use.</a>
			</div>

			<div class="demoVideo">
				<a target="_blank" href="http://www.youtube.com/v/G4zwfKRqEYY?version=3&amp;feature=player_detailpage&amp;loop=1&amp;autoplay=1&amp;modestbranding=1&amp;rel=0&amp;theme=light">
					<img alt="video preview" src="/images/videos1.PNG">
				</a>
				<p>Watch the getting started Video</p>
			</div>

			<div class="rgtnForm">
				<p>Take a test drive by signing up</p>
				<form novalidate="novalidate" class="cmxform" id="thisform" method="post" name="thisform" action="/cloud-portal/signup">
					<p id="contactArea_signup" class="contactArea">
						<input id="id" name="id" type="hidden">
					</p>
					<table>
						  <tr>
							<td>Name : <br />
							<input name="name" id="name" size="30" maxlength="30" class="required" type="text"></td>
						  </tr>
						  <tr>
							<td>Email : <br />
							<input name="email" id="email" size="30" maxlength="40" class="email required" type="text"></td>
						  </tr>
						  <tr>
							<td>Password : <br />
							<input name="password" id="password" size="30" maxlength="40" class="required" minlength="6" type="password"></td>
						  </tr>
						  <tr>
							<td>Organization : <br />
							<input name="organization" id="organization" maxlength="40" size="30" class="required" type="text"></td>
						  </tr>
						  <tr>
							<td>Enter text in the box <br />
							<input id="captchaResp" name="captchaResp" maxlength="5" size="30" class="required" type="text">
							</td>
						  </tr>
						  <tr>
							<td><img  style="border:1px solid grey;" src="/jcaptcha.jsp" /></td>
						  </tr>
							<tr>
							    <td style="width: 100%; " colspan="2">
								    <%try{
										  String s = ((String)session.getAttribute("MYCP_SIGNUP_MSG"));
										  if(s!=null && !s.equals("") && !s.equals("null")){out.print(s);}
									  }catch(Exception e){}
									  %>
								  </td>
						  	</tr> 
						  <tr>
							<td style="width: 50%;">
								<div class="demo" id="popupbutton_signup_create">
									<input aria-disabled="false" role="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Sign Up" type="submit">&nbsp;&nbsp;&nbsp;&nbsp;
								</div>
							</td>
						  </tr>
					</table>
				</form>
			</div>
			<div class="newsfeatures">
			<br>
			<h4>26 May, 2012: mycloudportal 1.1 beta deployed!</h4> 
			<br>
			
			<p>
			&nbsp;&nbsp;&nbsp; 1. Added AWS connectivity.
			<br>
			&nbsp;&nbsp;&nbsp; 2. New gmail like menu added.
			<br>
			&nbsp;&nbsp;&nbsp; 3. Updated cloud sync function.
			<br>
			</div>
		</div>
		
		<div class="clr"></div>

	</div>

	<jsp:include page="layoutfooter.jsp"></jsp:include>