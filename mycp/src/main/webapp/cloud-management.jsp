
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<%@ page import="java.util.*" %>
<%@ page import="org.springframework.security.authentication.BadCredentialsException" %>

<html xmlns:og="http://ogp.me/ns#">
	<head>
	<title>Cloud Management Solution</title>
	<meta name="author" content="Charudath Doddanakatte" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta name="description" content="cloud management features in my cloud portal."/>
  	<meta name="keywords" content="cloud management"/>
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
				<h1>Cloud Management features in my cloud portal</h1>
				<p>my cloud portal being a comprehensive cloud management solution packs a rich set of features to manage and control your cloud services.
				From a single control panel, you can use the same cloud management features to Connect &amp; Manage Eucalytpus (2 & 3 versions) 
				private cloud and Amazon Web services(AWS) public cloud. This is in the current state, we will be adding new clouds very soon.</p>
				<p>
				On top of this, my cloud portal helps you define your own users and their roles, view role specific dashboards, comprehensive reports, model procurement workflows based on your existing
				business processes and authorization chains. </p>
				<p>
				<ul>
						<li>manage many cloud providers and services from the same window.</li>
						<li>manage and control the life cycle of Compute, Images, Volumes, Keys, Ip Addresses & Snapshots.</li>
						<li>usage and cost reports.</li>
						<li>Create and manage users, assign them to projects, create any number of departments.</li>
						<li>Create and manage business processes as workflows and span them across cloud providers</li>
						<li>Control your enterprise cloud consumption through approvals. </li>
				</ul>
				<p></p>
				<img alt="cloud-management-solution" src="/images/cloud-management-solution.PNG">
				
				
			</div>
		</div>

		<div class="rightMain">
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