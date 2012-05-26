
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<%@ page import="java.util.*" %>
<%@ page import="org.springframework.security.authentication.BadCredentialsException" %>

<html xmlns:og="http://ogp.me/ns#">
	<head>
	<title>Self Service Portal Cloud</title>
	<meta name="author" content="Charudath Doddanakatte" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta name="description" content="Self service portal for the cloud. my cloud portal is a open source SSP."/>
  	<meta name="keywords" content="self service portal cloud"/>
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
				<h1>Self Service Portal for the Cloud</h1>
				<p>my cloud portal is a web based self service portal any cloud service.moreover, it is open sourced so that you can extend it as you wish.
				<p>
				one of the most important characteristics of any cloud is to be enabled through a self service portal.my cloud portal, because of 
				its open source and extendible platform approach 
				allows its users to extend the self service features to suit their organization's requirements.</p>
				<p>out of the box, the self service features in my cloud portal are </p>
				
				<ul>
						<li>request & approve for cloud resources.</li>
						<li>manage the lifecycle of these cloud services (Start, Stop).</li>
						<li>keep an eye on the usage and take action.</li>
						<li>Create & Manage Users and their Roles</li>
						<li>Add, Modify and Synchronize new clouds such as Eucalyptus and AWS.</li>
						<li>Create & Manage your organization structure through projects, departments and users.</li>
				</ul>

				
				<p><p>
				<img alt="self-service-portal" src="/images/self-service-portal.PNG">
				
				
			</div>
		</div>

		<div class="rightMain">
			
			<div class="features">
				<div class="icon"><a href="/self-service-portal.jsp"><img alt="" src="/images/reports_home.png"></a></div>
				<h5>Usage &amp; Cost reports</h5>
				<p>Use the cloud management features to generate Project, Department, Account or User based reports of your resources and their costs.</p>
			</div>
			
			<div class="features">
				<div class="icon"><a href="/cloud-broker.jsp"><img alt="" src="/images/eucalyptus_cloud.png"></a></div>
				<h5> Connect &amp; Manage</h5>
				<p>Acts as a cloud services gateway or a cloud broker to help you create and manage infrastructure services like Compute, Volume, Snapshot, KeyPair, Security Group &amp; Ip Address from IaaS cloud.</p>
			</div>
			
			<div class="features">
				<div class="icon"><a href="/self-service-portal.jsp"><img alt="" src="/images/catalog_home.png"></a></div>
				<h5>Product Catalog</h5>
				<p>Set up, package &amp; price your infrastructure  products in your local currency.</p>
			</div>
		</div>
		
		<div class="clr"></div>

	</div>

<jsp:include page="WEB-INF/layouts/layoutfooter.jsp"></jsp:include>