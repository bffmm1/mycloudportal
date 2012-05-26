
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page import="java.util.*" %>
<%@ page import="org.springframework.security.authentication.BadCredentialsException" %>
	
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
	<link type="text/css" href="/styles/home.css" rel="Stylesheet" />
	<link type="text/css" href="/styles/vader/jquery-ui-1.8.16.custom.css" rel="Stylesheet" />
	<link type="text/css" href="/styles/popup.css" rel="Stylesheet" />
	
	<script type="text/javascript">
	$(function() {
		$( "input:button", ".demo" ).button();
		$('a.email').nospam();
		$("#thisform").validate({
			 submitHandler: function(form) {
				form.submit();
				return true;
			 }
			});
	});
	</script>
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
			<a href="/cloud-portal">
				<div class="logo">
					My Cloud Portal<sup>beta</sup><br />
					<span>any service any cloud</span>
					<div style="padding-top: 15px;"><marquee>
					<font color="green" size="1.5px">
					
					</font>
					</marquee></div>
				</div>
			</a>
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