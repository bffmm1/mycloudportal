<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<%@ page import="java.util.*" %>
<%@ page import="org.springframework.security.authentication.BadCredentialsException" %>

<html >
	<head>
	<title>Open Source Self Service Portal for the Cloud</title>
	<script type="text/javascript" src="/dwr/engine.js"></script>
	<script type="text/javascript" src="/dwr/util.js"></script>
	<script type="text/javascript" src="/js/jquery-1.6.2.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui-1.8.16.custom.min.js"></script>	
	<script type="text/javascript" src="/js/jqueryplugins/jquery.validate.js"></script>
	<script type="text/javascript" src="/js/jqueryplugins/sticky/sticky.full.js"></script>
	<script type="text/javascript" src="/js/jqueryplugins/nospam.js"></script>
	<script type="text/javascript" src="/js/myccep.js"></script>
	<script type="text/javascript" src="/dwr/interface/SignupService.js"></script>
	<script type='text/javascript' src='/dwr/interface/RealmService.js'></script>
	
	<link type="text/css" href="/js/jqueryplugins/sticky/sticky.full.css" rel="Stylesheet" />
	<link type="text/css" href="/styles/vader/jquery-ui-1.8.16.custom.css" rel="Stylesheet" />
	<link type="text/css" href="/styles/popup.css" rel="Stylesheet" />
	
	
	
<script type="text/javascript">
	$(function() {
		$( "input:submit, button", ".demo" ).button();
		$('a.email').nospam();
	 	$("#login_button").click(function(){
			document.forms["f"].submit();
		});
		
	 	$( '#email').blur( function() {
				RealmService.emailExists(this.value, function(p) {
					if(p){
						$( '#email' ).select();
						$( '#email' ).val('Id exists.Choose another.');
  				}else{
  				}
				})});
	 	
		$("#thisform").validate({
			
			 submitHandler: function(form) {
				 submitForm_signup(form);
				return false;
			 }
			});
		
	});
	
	
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

			</style>
	</head>
	
  	<body>
  	
  	<div style="width: 100%;height: 100px; background: black; ">
	  	<div style="width:60%; float: left;text-align:justify; font-family: arial, helvetica, sans-serif; font-size: xx-large;font-weight: 900;">
	  		<div style="width: 400px; text-align: right; padding-top: 10px; 
	  			font-weight:900; font-size: 40px; font: arial, helvetica, sans-serif; font: bold;">
				My Cloud Portal</br><font style="font-size: 12px; padding-right: 150px; color: white;">any service any cloud</font><font style="font-size: 12px;">beta</font>
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
				    	<input id="j_username" type='text' name='j_username' maxlength="25" style="width:200px" />
				    </td>
				    <td style="width: 30%;">
				    	<input id="j_password" type='password' name='j_password'  maxlength="25" style="width:200px" />
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
			  		<font style="font-weight: bold;font-size: 19px; "> <font style="color: green;"> Consume</font> 
			  		and <font style="color: green;">Govern</font> any service from any cloud.</font>
			  		<p>
			  		In its current state it enables users to consume, monitor and manage services from Eucalyptus private cloud.
			  		</p>
			  		</br>
			  		
			  		
						<table style="width: 80%;">
						
						<tr>
						    <td style="width: 30%;"><img alt="" src="/images/eucalyptus_cloud.png" > </td>
						    <td style="width: 70%;"><font style="font-weight: bold;">Connect & Manage </font><br><div style="font-size: small;"> Create and Manage infrastructure services from Eucalyptus like Compute, Volume, Snapshot, KeyPair, Security Group & Ip Address.</div> </td>
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
	  	  
	  	 	<div style="width: 30px;height: 40px; "></div>
	  	 	<div style="width: 98%;height: 100px; ">
	  	 	  		
	  	 	<img src="/images/opensource.png" height="48" width="48" style="float:left; padding:20px; padding-top:0; padding-left:0;">
	  	 	MyCP is <font style="font-weight: bold;">open source</font> and <font style="font-weight: bold;">free</font> to <a href="http://code.google.com/p/mycloudportal/"> download & use.</a>
	  	 	
	  	 	<br></br> Take a test drive by signing up below.
	  	 	</div>
	  	 	<div style="width: 98%;text-align: left; border-radius: 5px 5px 5px 5px; font-weight:lighter; ;float: left; font-stretch: wider;font-size: 17px;
	  	 	background-color: #F5F5F5;">
 					
					<form class="cmxform" id="thisform" method="post" name="thisform">
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
						    <td style="width: 70%;"><input type="text" name="email" id="email" size="30" maxlength="25" class="email required"></td>
						  </tr>
						  <tr style="height: 5px;"></tr>
						  <tr>
						    <td style="width: 30%;">Password : </td>
						    <td style="width: 70%;"><input type="password" name="password" id="password" size="30" maxlength="25" class="required"  minlength="6"></td>
						  </tr>
						  <tr style="height: 5px;"></tr>
						  <tr>
						    <td style="width: 30%;">Organization : </td>
						    <td style="width: 70%;">
						    <input type="text" name="organization" id="organization" maxlength="25" size="30" class="required">
						    </td>
						  </tr> 
						  <tr>
						    <td style="width: 30%;">&nbsp; </td>
						    <td style="width: 70%;">&nbsp;</td>
						  </tr> 
						  
						    <tr>
						    <td style="width: 30%;"><img  style="border:1px solid grey;" src="/jcaptcha.jpg" /></td>
						    <td style="width: 70%;">
						    Enter text in the box 
						    <input type="text" id="captchaResp" name="captchaResp" value="" maxlength="25" size="30" class="required"/>
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
									<input class="submit" type="submit" value="Sign Up"/>&nbsp;&nbsp;&nbsp;&nbsp;
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
  	  	<font>My Cloud Portal © 2012</font><br><p>
  	  	<font><a href="#" rel="moc*liamg++latropduolcym" class="email">Contact</a></font>
  	  	
  	  	</div> 
  	</div>
   			
	</body>
</html>
