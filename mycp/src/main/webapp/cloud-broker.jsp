
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<%@ page import="java.util.*" %>
<%@ page import="org.springframework.security.authentication.BadCredentialsException" %>

<html xmlns:og="http://ogp.me/ns#">
	<head>
	<title>Cloud Broker : Open Source</title>
	<meta name="author" content="Charudath Doddanakatte" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta name="description" content="Cloud Broker and its relevance in consuming cloud services.my cloud portal is an enterprise cloud broker. It is open source and free to use."/>
  	<meta name="keywords" content="cloud broker"/>
	<meta id="MetaCopyright" name="COPYRIGHT" content="Copyright 2012 my cloud portal" />
	<meta name="ROBOTS" content="INDEX, FOLLOW"/>
	<link rel="image_src" href="/images/mycpSmall.PNG" type="image/x-icon" />
	<meta property="og:title" content="My Cloud Portal" />
	<meta property="og:type" content="website" />       
	<meta property="og:image" content="http://mycloudportal.in/images/mycpSmall.PNG" />      
	<meta property="og:url" content="http://mycloudportal.in" />

<jsp:include page="WEB-INF/layouts/layoutheader.jsp"></jsp:include>

  	<div id="wrapper">
		<div class="leftMain">
			<div class="intro">
				<h1>Cloud Broker and its relevance in consuming cloud services</h1>
				<p>
				As an enterprise starts using different cloud services such as email, desktop and servers from varied cloud service providers,
				the manageability and transparency issues crop up.</p>
				<p>
				How do you manage all these providers & pay for their services? <br></br>
				How do you monitor the consumption of these resources inside your organization?<br></br>
				How do you make sure that the right resources are being used by the right users?<br></br>
				Don't you need a independent tool to enable a consistent view across different cloud providers?<br></br>
				</p>
				<p>
				cloud broker answers all these questions and helps you in consolidating your enterprise cloud services from a single 
				control panel.</p>
				<p>my cloud portal is a cloud broker which resides inside your enterprise network and provides you with a control panel 
				to consume, manage and monitor all your cloud resources and service providers.</p>
				<p>
				In its current state, <a href="/"> </a>my cloud portal's cloud brokerage feature connects to Eucalyptus private cloud and AWS (Amazon public cloud) public cloud. 
				We will be adding more cloud connectors soon.
				Since it is open sourced , you can extend it on your own too.
				</p>
				<p>
				<h3>Cloud Brokerage features in my cloud portal</h3>
				<br>
				<ul>
						<li>Connect and manage AWS and Eucalyptus.</li>
						<li>life cycle management of Servers, IP Address, Storage, Keys, Images & Security groups.</li>
						<li>monitor and manage all cloud providers from a single control panel.</li>
						<li>Create & Manage your enterprise users, projects & departments</li>
						<li>view consolidated cost and usage reports</li>
						<li>create your provisioning workflows with authorizations and span them across any cloud service.</li>
						<li>recreate cloud products and price them in your local currency.</li>
				</ul>
				</p>
				
				<img alt="cloud-broker" src="/images/cloud-broker.PNG">
				
				
			</div>
		</div>

		<div class="rightMain">
			
			<div class="features">
				<div class="icon"><a href="/cloud-management.jsp"><img alt="" src="/images/accesscontrol_home.png"></a></div>
				<h5>User and Access control</h5>
				<p>Use the cloud governance features to define role based access controls and security at the Manager, Admin and User levels using the cloud control panel.</p>
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
				<div class="icon"><a href="/self-service-portal.jsp"><img alt="" src="/images/reports_home.png"></a></div>
				<h5>Usage &amp; Cost reports</h5>
				<p>Use the cloud management features to generate Project, Department, Account or User based reports of your resources and their costs.</p>
			</div>
		</div>
		
		<div class="clr"></div>

	</div>

<jsp:include page="WEB-INF/layouts/layoutfooter.jsp"></jsp:include>