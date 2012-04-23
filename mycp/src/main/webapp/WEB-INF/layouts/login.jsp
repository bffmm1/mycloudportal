<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<%@ page import="java.util.*" %>
<%@ page import="org.springframework.security.authentication.BadCredentialsException" %>

<html xmlns:og="http://ogp.me/ns#">
	<head>
	<title>Open Source Self Service Portal for the Cloud</title>
	<meta name="author" content="Charudath Doddanakatte" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta name="description" content="My Cloud Portal is an open Source Self Service Portal for the Cloud. It can be used as a Cloud Broker, Cloud Control Panel, Cloud Services Gateway or a Cloud management solution."/>
  	<meta name="keywords" content="enterprise open source self service portal, cloud services gateway, control panel, cloud broker, cloud management solution, on-premise, cloud governance, eucalyptus, cloud management software, cloud computing management"/>
	<meta id="MetaCopyright" name="COPYRIGHT" content="Copyright 2012 my cloud portal" />
	<meta name="ROBOTS" content="INDEX, FOLLOW"/>
	<link rel="image_src" href="/images/mycpSmall.PNG" type="image/x-icon" />
	<meta property="og:title" content="My Cloud Portal" />
	<meta property="og:type" content="website" />       
	<meta property="og:image" content="http://www.mycloudportal.in/images/mycpSmall.PNG" />      
	<meta property="og:url" content="http://www.mycloudportal.in" />
	
	<script type="text/javascript" src="/dwr/engine.js"></script>
	<script type="text/javascript" src="/dwr/util.js"></script>
	<script type="text/javascript" src="/js/jquery-1.6.2.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui-1.8.16.custom.min.js"></script>	
	<script type="text/javascript" src="/js/jqueryplugins/jquery.validate.js"></script>
	<script type="text/javascript" src="/js/jqueryplugins/sticky/sticky.full.js"></script>
	<script type="text/javascript" src="/js/myccep.js"></script>
	<script type="text/javascript" src="/js/jqueryplugins/nospam.js"></script>
	<script type="text/javascript" src="/dwr/interface/SignupService.js"></script>
	<script type='text/javascript' src='/dwr/interface/RealmService.js'></script>
	
	
	
	<link type="text/css" href="/js/jqueryplugins/sticky/sticky.full.css" rel="Stylesheet" />
	<link type="text/css" href="/styles/vader/jquery-ui-1.8.16.custom.css" rel="Stylesheet" />
	<link type="text/css" href="/styles/popup.css" rel="Stylesheet" />
	<script type="text/javascript">



	$(function() {
		$( "input:button", ".demo" ).button();
		$('a.email').nospam();
	 	

		$("#thisform").validate({
			 submitHandler: function(form) {
				 
				//submitForm_signup(form);
				form.submit();
				return true;
			 }
			});
	});



	</script>

	<style type="text/css">
	* { margin:0;
		padding:0;
	}
	.clr{clear:both; height:0; font-size:0; line-height:0}
	body{background:white; font-family: verdana, helvetica, sans-serif; font-size: 15px; color: grey}
	.band{background:#000; padding:10px 0}
	#wrapper, #header, #footer{width:960px; margin:0 auto; padding:0 10px; clear:both}
	#header{height:80px; padding-top:10px}
	.logo{width: 380px; float:left; font-weight:bold; font-size: 24px; font: arial, helvetica, sans-serif; font: bold; padding-left:10px; padding-top:10px; color:#f60}
	.logo span{font-size: 12px; padding-right: 145px; color:#999;}
	.logo sup{font-size:10px; color:#999}
	.login{width:440px; float:right; font-weight:normal; font-size:11px;}
	.login input{margin-top:5px;}
	.login li{list-style:none; float:left}
	.login #login_button{color:#888}
	.leftMain{width:660px; float:left; padding:0 10px 20px;}
	.intro{font-size: 14px; color:grey; margin:15px 10px 10px 0px;}
	.intro p{padding:10px 0;}
	.intro span{color: #000}
	.features{margin:20px 0 25px; font-size:12px; clear:both; line-height:18px}
	.features .icon{float:left; width:90px; padding-top:5px}
	.features h5{font-size:14px; color:#f60; margin:5px}
	.rightMain{width:270px; float:left; padding:0 10px 0 0}
	.rgtnForm{border-radius: 5px 5px 5px 5px; font-weight:lighter; font-stretch: wider; font-size: 12px; background-color: #f0f0f0; line-height:20px; padding:0 30px 15px}
	.rgtnForm .contactArea{display:none}
	.rgtnForm p{padding:10px 0 0; color:#555; font-weight:bold}
	.rgtnForm td{padding:10px 0 0}
	.demoVideo{border-radius: 5px 5px 5px 5px; cursor:pointer; background-color: #f0f0f0; margin-bottom:20px; padding:15px;}
	.demoVideo p{font-size:12px; text-align:center; color:#555; font-weight:bold; line-height:18px; margin:10px 0 0}
	.demoVideo img{border:1px solid #888; margin:0 15%}
	#footer .nav a{text-decoration:none; color:#fff}
	#footer .nav a:hover{color:#f60}
	#footer .copyright{text-align:right; font-size:11px; color:#888}
	div#menu{margin:5px auto}
	div#copyright{font:0px 'arial, helvetica, sans-serif'; color:#222; text-indent:0px; padding:0px 0 0 0}
	div#copyright a { color:#000; }
	div#copyright a:hover { color:#222; }
	div.centered{display:block; position:absolute; top:30%; left:35%; width:350px; color: white}
	.mycpfooter {
		clear: both;
		text-align: center;
		margin-top: 3.5em;
		margin-bottom: 1em;
		height: 53px;
		background-repeat: no-repeat;
		background-position: left center;
		background-color: black;
		font-weight: bold;
		padding-top: 10px;
	}

	.popupContact_login{
		display:none;
		position:fixed;
		_position:absolute; /* hack for internet explorer 6*/
		height:520px;
		width:854px;
		background-color: black;
		border:2px solid #cecece;
		z-index:2;
		padding:12px;
		font-size:18px;
		color: grey;
	}
	
	.login-button {
	display: inline-block;
	position: relative;
	padding: .4em 1em;
	padding-top: 0.4em;
	padding-right: 1em;
	padding-bottom: 0.4em;
	padding-left: 1em;
	margin-right: .1em;
	text-decoration: none !important;
	cursor: pointer;
	text-align: center;
	zoom: 1;
	overflow: visible;
	border: 1px solid #CCC;
	background: #ADADAD url(/images/ui-bg_highlight-soft_35_adadad_1x100.png) 50% 50% repeat-x;
	font-weight: normal;
	color: #333;
	moz-border-radius-topright: 5px;
	-webkit-border-top-right-radius: 5px;
	-khtml-border-top-right-radius: 5px;
	border-top-right-radius: 5px;
	-moz-border-radius-bottomleft: 5px;
	-webkit-border-bottom-left-radius: 5px;
	-khtml-border-bottom-left-radius: 5px;
	border-bottom-left-radius: 5px;
	-moz-border-radius-topleft: 5px;
	-webkit-border-top-left-radius: 5px;
	-khtml-border-top-left-radius: 5px;
	border-top-left-radius: 5px;
	font-family: Helvetica, Arial, sans-serif;
	font-size: 0.8em;
	}

	</style>

	<script type="text/javascript">

	  var _gaq = _gaq || [];
	  _gaq.push(['_setAccount', 'UA-30763069-1']);
	  _gaq.push(['_trackPageview']);
	
	  (function() {
	    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	  })();
	
	</script>

</head>

<body>
<div id="fb-root"></div>
			<script>(function(d, s, id) {
			  var js, fjs = d.getElementsByTagName(s)[0];
			  if (d.getElementById(id)) return;
			  js = d.createElement(s); js.id = id;
			  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
			  fjs.parentNode.insertBefore(js, fjs);
			}(document, 'script', 'facebook-jssdk'));</script>

	<div class="band">
		<div id="header">
			<div class="logo">
				My Cloud Portal<sup>beta</sup><br />
				<span>any service any cloud</span>
			</div>
			<div class="login">
			
			<form name="f" action="/resources/j_spring_security_check" method="POST">
				<table  >
				  <tr >
				    <td style="width: 30%;">Email  </td>
				    <td style="width: 30%;">Password  </td>
					<td style="width: 30%; "></td>				    
				  </tr>
				  
				  <tr>
				    <td style="width: 30%;">
				    	<input id="j_username" type='text' name='j_username' maxlength="40" style="width:200px" />
				    </td>
				    <td style="width: 30%;">
				    	<input id="j_password" type='password' name='j_password'  maxlength="40" style="width:200px" />
				    </td>
				    <td style="width: 30%;">
				    	<input class="login-button" type="submit" value="Sign In">
				    </td>
				  </tr>
				  <tr >
				  
				  <td colspan="3" style="color: red;">
					  <%try{
					  String s = ((BadCredentialsException)session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION")).getMessage();
					  out.print(s);
					  }catch(Exception e){}
					  %>
				  </td>
				  </tr>
				  <tr>
				  	<td colspan="3" style="height: 6px;"></td>
				  </tr>
				  <tr>
				  <td colspan="3" style="color: red;">
						  <table >
					  	<tr>
						  	<td width="10%">
						  		<a href="https://twitter.com/share" class="twitter-share-button" data-url="http://mycloudportal.in" data-count="none" data-hashtags="mycloudportal">Tweet</a>
								<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
							</td>
							<td width="2%"> &nbsp;</td>
						  	<td width="10%">
						  		<su:badge layout="3"></su:badge>
								 <script type="text/javascript"> 
								 (function() { 
								     var li = document.createElement('script'); li.type = 'text/javascript'; li.async = true; 
								     li.src = window.location.protocol + '//platform.stumbleupon.com/1/widgets.js'; 
								     var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(li, s); 
								 })(); 
								 </script>
						  	</td>
						  	<td width="2%"> &nbsp;</td>
						  	<td width="10%">
							  	<script src="//platform.linkedin.com/in.js" type="text/javascript"></script>
								<script type="IN/Share" data-url="mycloudportal.in"></script>
						  	</td>
						  	<td width="2%"> &nbsp;</td>
						  	<td width="10%">
								<g:plusone size="medium" annotation="none"></g:plusone>
								<script type="text/javascript">
								  (function() {
								    var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
								    po.src = 'https://apis.google.com/js/plusone.js';
								    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
								  })();
								</script>
						  	</td>
						  	
						  	<td width="2%"> &nbsp;</td>
						  	 <td width="10%">
						  	 	<div class="fb-like" data-href="http://mycloudportal.in" 
						  	 	data-send="true" data-layout="button_count" data-width="150" 
						  	 	data-show-faces="false" data-colorscheme="dark" data-font="arial"></div>
						  	 </td>
						  	 
					  	</tr>
					  	</table>
				  </td>
				  </tr>
				  
				</table>
			</form>
			</div>
			
		</div>
	</div>

  	<div id="wrapper">
		<div class="leftMain">
			<div class="intro">
				
				<p>As an enterprise self service portal the idea of my cloud portal is to provide a single pane of glass for the user to
				<span>Consume</span> and <span>Govern</span> any service from any cloud. This cloud  management solution can be used as a cloud broker, cloud gateway or a cloud control panel.</p>
				<p>In its current state it enables users to consume, monitor and manage services from Eucalyptus private cloud.</p>
			</div>
			<div>
				<a target="_blank" href="http://www.youtube.com/v/BOqpyErM4PI?version=3&amp;feature=player_detailpage&amp;loop=1&amp;autoplay=1&amp;modestbranding=1&amp;rel=0&amp;theme=light">
						<img alt="Overview" src="/images/mycpSmall.PNG" style="float:none; padding-left: 200px;">
					</a>
			</div>
			
			
			
			<div class="features">
				<div class="icon"><img alt="" src="/images/eucalyptus_cloud.png"></div>
				<h5>Connect &amp; Manage</h5>
				<p>Acts as a cloud services gateway or a cloud broker to help you create and manage infrastructure services like Compute, Volume, Snapshot, KeyPair, Security Group &amp; Ip Address from IaaS cloud.</p>
			</div>
			<div class="features">
				<div class="icon"><img alt="" src="/images/accesscontrol_home.png"></div>
				<h5>User and Access control</h5>
				<p>Use the cloud governance features to define role based access controls and security at the Manager, Admin and User levels using the cloud control panel.</p>
			</div>
			<div class="features">
				<div class="icon"><img alt="" src="/images/reports_home.png"></div>
				<h5>Usage &amp; Cost reports</h5>
				<p>Use the cloud management features to generate Project, Department, Account or User based reports of your resources and their costs.</p>
			</div>
			<div class="features">
				<div class="icon"><img alt="" src="/images/dashboard_home.png"></div>
				<h5>Dashboards</h5>
				<p>As a cloud management solution, you can get a birds eye view of your cloud's health, workflows, costs and usage and that too, based on predefined roles.</p>
			</div>
			<div class="features">
				<div class="icon"><img alt="" src="/images/workflows_home.png"></div>
				<h5>Workflows</h5>
				<p>Use the cloud governance features to setup workflows mirroring your business processes and control your internal cloud consumption by approving or rejecting infrastructure requests by your users.</p>
			</div>
			<div class="features">
				<div class="icon"><img alt="" src="/images/catalog_home.png"></div>
				<h5>Product Catalog</h5>
				<p>Set up, package &amp; price your infrastructure  products in your local currency.</p>
			</div>
		</div>

		<div class="rightMain">
			<div style="padding: 15px 0;">
				<a href="http://code.google.com/p/mycloudportal/" target="_blank"><img src="/images/opensource.png" style="float:left; margin-right:10px" height="48" width="48"></a>
				MyCP is <font style="font-weight: bold;">open source</font> and <font style="font-weight: bold;">free</font> to
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
				<form novalidate="novalidate" class="cmxform" id="thisform" method="post" name="thisform" action="/login/signup">
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
		</div>
		
		<div class="clr"></div>

	</div>

	<div class="band">
		<div id="footer">
			<p class="nav"><a href="mailto:mycloudportal@gmail.com" rel="moc*liamg++latropduolcym" class="email">Contact</a> | <a href="http://code.google.com/p/mycloudportal/downloads/list" target="_blank">Download</a> | <a href="http://code.google.com/p/mycloudportal/" target="_blank">Project Home</a></p>
			<p class="copyright">My Cloud Portal © 2012</p>
		</div>
	</div>
</body>
</html>