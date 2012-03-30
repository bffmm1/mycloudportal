<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<%@ page import="java.util.*" %>
<%@ page import="org.springframework.security.authentication.BadCredentialsException" %>

<html >
	<head>
	<title>Open Source Self Service Portal for the Cloud</title>
	<meta name="author" content="Charudath Doddanakatte" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta id="MetaDescription" name="DESCRIPTION" content="mycloudportal - Open Source Self Service Portal for the Cloud; Control Panel for Eucalyptus Private Cloud" />
	<meta id="MetaKeywords" name="KEYWORDS" content="self service portal, cloud services gateway, cloud control panel, cloud management solution, connect and manage eucalyptus" />
	<meta id="MetaCopyright" name="COPYRIGHT" content="Copyright 2012 my cloud portal" />
	<meta name="ROBOTS" content="INDEX, FOLLOW"/>

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



/***************************/
//@Author: Adrian "yEnS" Mato Gondelle
//@website: www.yensdesign.com
//@email: yensamg@gmail.com
//@license: Feel free to use it, but keep this credits please!					
/***************************/
	var popupStatus_compute = 0;
	function loadPopup_compute(){
		if(popupStatus_compute==0){
			$("#backgroundPopup_compute").css({
				"opacity": "0.7"
			});
			$("#backgroundPopup_compute").fadeIn("slow");
			$("#popupContact_compute").fadeIn("slow");
			popupStatus_compute = 1;
		}
	}

	function disablePopup_compute(){
		if(popupStatus_compute==1){
			$("#backgroundPopup_compute").fadeOut("slow");
			$("#popupContact_compute").fadeOut("slow");
			popupStatus_compute = 0;
		}
	}

	function centerPopup_compute(){
		var windowWidth = document.documentElement.clientWidth;
		var windowHeight = document.documentElement.clientHeight;
		var popupHeight = $("#popupContact_compute").height();
		var popupWidth = $("#popupContact_compute").width();
		$("#popupContact_compute").css({
			"position": "absolute",
			"top": windowHeight/2-popupHeight/2,
			"left": windowWidth/2-popupWidth/2
		});
		
		$("#backgroundPopup_compute").css({
			"height": windowHeight
		});
		
	}
	
	
	$(function() {
		$( "input:submit, button", ".demo" ).button();
		$('a.email').nospam();
	 	$("#login_button").click(function(){
			document.forms["f"].submit();
		});
		 	
		$("#thisform").validate({
			 submitHandler: function(form) {
				//submitForm_signup(form);
				form.submit();
				return true;
			 }
			});
	});
	
			$(function(){
			$("#popupbutton_compute").click(function(){
				viewed_compute = -1;centerPopup_compute();loadPopup_compute();
			});});
			
			$("#popupContactClose_compute").click(function(){
				viewed_compute = -1;disablePopup_compute();
			});$("#backgroundPopup_compute").click(function(){
				viewed_compute = -1;disablePopup_compute();
			});

			$(document).keypress(function(e){
				if(e.keyCode==27 && popupStatus_compute==1){
					disablePopup_compute();
				}
			});
	
			function cancelForm_compute(f){
				  disablePopup_compute();
			}
	
	function submitForm_signup(f){
		var signup = { name:null,email:null, password:null, organization:null,captchaResp:null};
		  dwr.util.getValues(signup);
		  dwr.engine.beginBatch();
		  SignupService.createUser(signup,aftersubmitForm_signup);
		  dwr.engine.endBatch();
	}
	
	function aftersubmitForm_signup(p){
		if(p !=null && p.email!=null){
			$.sticky('User with email '+p.email+' created.Please Sign In.');	
		}else{
			$.sticky('Could not create User.Try later.');
		}
		window.location.href=window.location.href;
		}
	
	</script>
	
			<style type="text/css">
				* { margin:0;
				    padding:0;
				}
				body { background:white; 
				font-family: arial, helvetica, sans-serif; 
				font-size: 15px;
				color: grey;}
				
				div#menu { margin:5px auto; }
				div#copyright {
				    font:0px 'arial, helvetica, sans-serif';
				    color:#222;
				    text-indent:0px;
				    padding:0px 0 0 0;
				}
				div#copyright a { color:#000; }
				div#copyright a:hover { color:#222; }
				
				div.centered{
				    display:block;
				    position:absolute;
				    top:30%;
				    left:35%;
				    width:350px;
				    color: white;
				  }
				
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

			</style>
			<script type="text/javascript">

			  var _gaq = _gaq || [];
			  _gaq.push(['_setAccount', 'UA-30466112-1']);
			  _gaq.push(['_trackPageview']);
			
			  (function() {
			    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
			    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
			    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
			  })();
			
			</script>
	</head>
	
  	<body>
  	
  	<div style="width: 100%;height: 100px; background: black; ">
	  	<div style="width:60%; float: left;text-align:justify; font-family: arial, helvetica, sans-serif; font-size: xx-large;font-weight: 900;">
	  		<div style="width: 400px; text-align: right; padding-top: 10px; 
	  			font-weight:900; font-size: 40px; font: arial, helvetica, sans-serif; font: bold;">
				My Cloud Portal</br><font style="font-size: 12px; padding-right: 145px; color: white;">any service any cloud &nbsp;</font><font style="font-size: 12px;">beta</font>
			</div>
	  	</div>
		  	<div style="width:40%; float: right;font-weight:normal; ">
		  	<form name="f" action="/resources/j_spring_security_check" method="POST">
				<table style="width: 60%;" >
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
				    	<div class="demo" style="float: left; width: 100px;" id="login_button"><button>Sign In</button></div>
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
				</table>
			</form>
	  	</div>
  	</div>
  	<div style="width: 100%;height: 600px; ">
	  	<div style="width: 65%;height: 600px; float: left;">
		  	<div style="width: 30px;height: 20px;"></div>
		  	<div style="width: 60px;height: 20px; float: left;"></div>
		  		<div style="width: 85%;text-align: left; float: left; font-stretch: wider;font-size: 17px;">
			  		As a self service portal the idea of MyCP is to provide a single pane of glass for the user to 
			  		<font style=" "> <font style="font-weight: bold;font-size: 19px;color: black;"> Consume</font> 
			  		and <font style="font-weight: bold;font-size: 19px;color: black;">Govern</font> any service from any cloud.</font>
			  		<p>
			  		In its current state it enables users to consume, monitor and manage services from Eucalyptus private cloud.
			  		</p>
			  		</br>
			  			<table style="width: 80%;">
						
						<tr>
						    <td style="width: 30%;"><img alt="" src="/images/eucalyptus_cloud.png" > </td>
						    <td style="width: 70%;"><font style="font-weight: bold;">Connect & Manage </font><br><div style="font-size: small;"> Create and Manage infrastructure services from IaaS cloud like Compute (Server), Volume (Storage), Snapshot (Backup), KeyPair, Security Group & Ip Address.</div> </td>
						  </tr>
						  <tr style="height: 10px;"></tr>
						  <tr>
						    <td style="width: 30%;"><img alt="" src="/images/accesscontrol_home.png" > </td>
						    <td style="width: 70%;"><font style="font-weight: bold;">User and Access control</font> <br><div style="font-size: small;"> Role based access controls and security at the Manager, Admin and User levels.</div> </td>
						  </tr>
						  <tr style="height: 10px;"></tr>
						  <tr>
						    <td style="width: 30%;"><img alt="" src="/images/reports_home.png" ></td>
						    <td style="width: 70%;"><font style="font-weight: bold;">Usage & Cost reports</font><br><div style="font-size: small;">Generate Project, Department, Account or User based reports of your resources and their costs.</div></td>
						  </tr>
						  <tr style="height: 10px;"></tr>
						  <tr>
						    <td style="width: 30%;"><img alt="" src="/images/dashboard_home.png" ></td>
						    <td style="width: 70%;"><font style="font-weight: bold;">Dashboards</font><br><div style="font-size: small;"> Get a birds eye view of your cloud's health, workflows, costs and usage backed by roles.</div></td>
						  </tr>
						  <tr style="height: 10px;"></tr>
						  <tr>
						    <td style="width: 30%;"><img alt="" src="/images/workflows_home.png" ></td>
						    <td style="width: 70%;"><font style="font-weight: bold;">Workflows</font><br><div style="font-size: small;"> Control your internal cloud consumption by approving or rejecting infrastructure requests by your users.</div></td>
						  </tr>
						  <tr style="height: 10px;"></tr>
						  <tr>
						    <td style="width: 30%;"><img alt="" src="/images/catalog_home.png" ></td>
						    <td style="width: 70%;"><font style="font-weight: bold;">Product Catalog</font><br><div style="font-size: small;"> Set up, package & price your infrastructure  products in your local currency.</div> </td>
						  </tr>
						</table>
		  		</div>
	  	</div>
	  	
	  	<div style="width: 35%;height: 620px;  float: left;">
	  	  
	  	 	<div style="width: 30px;height: 20px; "></div>
	  	 	<div style="width: 98%;height: 70px; text-align: center;  ">
	  	 	  		
		  	 	<a href="http://code.google.com/p/mycloudportal/" target="_blank"> <img src="/images/opensource.png" height="48" width="48" 
		  	 		style="float:left; padding:20px; padding-top:0; padding-left:0;"></a>
		  	 	<div style="padding-top: 15px;">
		  	 	MyCP is <font style="font-weight: bold;">open source</font> and <font style="font-weight: bold;">free</font> to 
		  	 	<a href="http://code.google.com/p/mycloudportal/" target="_blank"> download & use.</a>
		  	 	</div>
	  	 	</div>
	  	 	<div id="popupbutton_compute" 
	  	 		onclick="$('#popupbutton_compute').click();"
	  	 		style="height: 100px; width: 98%; border-radius: 5px 5px 5px 5px;
	  	 		cursor:pointer; background-color: #F5F5F5;">
			  	 	<div style="float: left; padding-top: 40px; padding-right: 40px;"><img alt="play video" src="/images/play-video.png"> Watch the getting started Video</div>
			  	 	<div style="float: left; padding-top: 5px;"><img alt="video preview" src="http://img.youtube.com/vi/G4zwfKRqEYY/1.jpg"></div>
	  	 	</div>
	  	 	
	  	 	
	  	 	
	  	 	
	  	 	<div style="height: 30px; "></div>
	  	 	Take a test drive by signing up below.
	  	 	<div style="height: 10px; "></div>
	  	 	<div style="width: 98%;text-align: left; border-radius: 5px 5px 5px 5px; font-weight:lighter; ;float: left; font-stretch: wider;font-size: 17px;
	  	 	background-color: #F5F5F5;">
 					<form class="cmxform" id="thisform" method="post" name="thisform" action="/login/signup" >
						<p id="contactArea_signup" class="contactArea" >
						<input type="hidden" id="id" name="id">
						<table style="width: 80%;">
						 <tr style="height: 20px;"></tr>
						  <tr>
						  
						    <td style="width: 30%;">Name : </td>
						    <td style="width: 70%;"><input type="text" name="name" id="name" size="30" maxlength="30" class="required"></td>
						  </tr>
						  <tr style="height: 5px;"></tr>
						  <tr>
						    <td style="width: 30%;">Email : </td>
						    <td style="width: 70%;"><input type="text" name="email" id="email" size="30" maxlength="40" class="email required"></td>
						  </tr>
						  <tr style="height: 5px;"></tr>
						  <tr>
						    <td style="width: 30%;">Password : </td>
						    <td style="width: 70%;"><input type="password" name="password" id="password" size="30" maxlength="40" class="required"  minlength="6"></td>
						  </tr>
						  <tr style="height: 5px;"></tr>
						  <tr>
						    <td style="width: 30%;">Organization : </td>
						    <td style="width: 70%;">
						    
						    <input type="text" name="organization" id="organization" maxlength="40" size="30" class="required">
						    </td>
						  </tr> 
						  <tr>
						    <td style="width: 30%;">&nbsp; </td>
						    <td style="width: 70%;">&nbsp;</td>
						  </tr> 
						  
						    <tr>
						    <td style="width: 30%;"><img  style="border:1px solid grey;" src="/jcaptcha.jsp" /></td>
						    <td style="width: 70%;">
						    Enter text in the box 
						    <input type="text" id="captchaResp" name="captchaResp" value="" maxlength="5" size="30" class="required"/>
						    </td>
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
						    <td style="width: 50%;"></td>
						    <td style="width: 50%;">
						    <br>
								<div class="demo" id="popupbutton_signup_create">
									<input type="submit" value="Sign Up">&nbsp;&nbsp;&nbsp;&nbsp;
								</div>
							</td>
						  </tr>
						  <tr style="height: 5px;"></tr>
						</table>
						</p>
					</form>
			</div>				
	  	</div>
	  	
  	</div>
  	<div class="mycpfooter">
  	  	<div >
  	  	<font style="font-size: 12px; font-weight: normal;">My Cloud Portal � 2012</font><br><p>
  	  	<font><a href="#" rel="moc*liamg++latropduolcym" class="email">Contact</a></font>
  	  	
  	  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://code.google.com/p/mycloudportal/downloads/list" target="_blank">Download</a>
  	  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://code.google.com/p/mycloudportal/" target="_blank">Project Home</a>
  	  	
  	  	</div> 
  	</div>
  	
  	
  	<div id="popupContactParent_compute" >
		<div id="popupContact_compute" class="popupContact_login" >
							<div style="height: 10px;">
							<a  onclick="cancelForm_compute();return false;" class="popupContactClose" style="cursor: pointer; text-decoration:none;">X</a>
							</div>
								<div style="height: 520px;">
									<object style="height: 510px; width: 854px">
									<param name="movie" value="http://www.youtube.com/v/G4zwfKRqEYY?version=3&feature=player_detailpage">
									<param name="allowFullScreen" value="false"><param name="allowScriptAccess" value="always">
									<embed src="http://www.youtube.com/v/G4zwfKRqEYY?version=3&feature=player_detailpage" 
										type="application/x-shockwave-flash" allowfullscreen="false" 
										allowScriptAccess="always" width="854" height="510">
									</object>  	 	
						  	 	</div>
						</div>
		<div id="backgroundPopup_compute" class="backgroundPopup" ></div>
	</div>
   			
	</body>
</html>
