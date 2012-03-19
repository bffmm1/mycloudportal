<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="in.mycp.utils.Commons" %>
<%@ page import="in.mycp.domain.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<title>Open Source Self Service Portal for the Cloud</title>
    <link type="text/css" href="/styles/menu.css" rel="stylesheet" />
    <script type="text/javascript" src="/js/jquery-1.6.2.min.js"></script>
    <script type="text/javascript" src="/js/menu.js"></script>
    <link type="text/css" href="/styles/vader/jquery-ui-1.8.16.custom.css" rel="Stylesheet" />	
<link type="text/css" href="/styles/global.css" rel="Stylesheet" />
<link type="text/css" href="/styles/myccep.css" rel="Stylesheet" />
<link type="text/css" href="/styles/popup.css" rel="Stylesheet" />

<script type="text/javascript" src="/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="/js/jqueryplugins/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/js/jqueryplugins/jquery.validate.js"></script>

<script type="text/javascript" src="/js/jqueryplugins/sticky/sticky.full.js"></script>
<link type="text/css" href="/js/jqueryplugins/sticky/sticky.full.css" rel="Stylesheet" />

<script type="text/javascript" src="/js/myccep.js"></script>

	<script type="text/javascript" src="/dwr/engine.js"></script>
		<script type="text/javascript" src="/dwr/util.js"></script>
		<script type="text/javascript" src="/dwr/interface/eucalyptusService.js"></script>
		<script type="text/javascript" src="/dwr/interface/CommonService.js"></script>
		<script type="text/javascript">
		
		$(document).ready(function() {
			CommonService.getCurrentSession(function(p){
				dwr.util.setValue('mysession', ' '+p.company+' '+p.email+' '+dateFormat(p.loggedInDate));
			});
		});
			</script>
			
</head>
<body>

	<style type="text/css">
		
	</style>

<!--  	<div class="mycpheaderHeader">
	  	<div class="mycpheaderName">
	  		<div style=" text-align: right; 
	  			font-weight:900; font-size: 25px; font: arial, helvetica, sans-serif; font: bold;">
				My Cloud Portal</br><font style="font-size: 11px;">beta</font>
			</div>
			
	  	</div>
	  	
		  	<div style="width:85%; float: right;font-weight:normal; ">
		  	
	  	</div>
	  	
  	</div> -->

<div id="menu">

    <ul class="menu">
    <li>
    	<a href="/login/dash" class="parent"><span>Home</span></a>
    </li>
    
    <%
    String roles = Commons.getCurrentUserRolesNonDWR();
    
    if(roles.contains(Commons.ROLE.ROLE_ADMIN+"") || 
    		roles.contains(Commons.ROLE.ROLE_MANAGER+"") ||
    			roles.contains(Commons.ROLE.ROLE_SUPERADMIN+""))
    {
    %>
    <li>
    
    <a href="#" class="parent"><span>Setup</span></a>
            <div><ul>
					    	
                <li><a href="/enterprise/company">Account</a></li>
                <li><a href="/enterprise/department">Department</a></li>
                <li><a href="/enterprise/project">Project</a></li>
                <li><a href="/realm/user">User</a></li>
                <!-- <li><a href="/enterprise/manager">Manager</a></li> -->
                <!-- <li><a href="/enterprise/employee">Employee</a></li> -->

            </ul></div>
   
        </li>
        <li><a href="#" class="parent"><span>Configuration</span></a>
            <div><ul>
            	<!-- <li><a href="/config/region">Region</a></li> -->
                <li><a href="/config/infra">Cloud</a></li>
                <li><a href="/config/assettype">Product Type</a></li>
                <li><a href="/config/product">Product</a></li>
                <!-- <li><a href="/config/quotas">Quotas</a></li> -->
                <!-- <li><a href="/config/metermetric">Meter Metric</a></li> -->
				<!--<li><a href="#"><font color="red">Payment</font></a></li> -->
            </ul></div>
        </li>
      <%} %>
        <li><a href="#" class="parent"><span>Resource</span></a>
            <div><ul>
                
                <li><a href="/iaas/compute">Compute</a></li>
                <li><a href="/iaas/volume">Volume</a></li>
                <li><a href="/iaas/ipaddress">IP Address</a></li>
                <li><a href="/iaas/secgroup">Security Groups</a></li>
                <li><a href="/iaas/keys">Key Pairs</a></li>

                <li><a href="/iaas/image">Image</a></li>
                <li><a href="/iaas/snapshot">Snapshots</a></li>
                
<!--                 <li><a href="#"><font color="red">Storage</font></a></li>
                <li><a href="#"><font color="red">Desktop</font></a></li>
              	<li><a href="#"><font color="red">Network</font></a></li>
 -->
            </ul></div>
        </li>
        <!--  <li><a href="#" class="parent"><span>Realm</span></a>
            <div><ul> -->
           		<!-- <li><a href="/realm/user">User</a></li> -->
<!--                 <li><a href="#"><font color="red">Role</font></a></li>
                <li><a href="#"><font color="red">Permission</font></a></li>
 -->                
           <!--  </ul></div>
        </li> -->
        <%
	    if(roles.contains("ROLE_ADMIN") || 
	    		roles.contains("ROLE_MANAGER") ||
	    			roles.contains("ROLE_SUPERADMIN"))
	    {
	    %>
	         <li><a href="#" class="parent"><span>Control</span></a>
	            <div><ul>
					<li><a href="/workflow/processInstance">Workflows</a></li>
	                <!-- 
		                <li><a href="/workflow/processDefinition">Process</a></li>
		                <li><a href="#"><font color="red">Task</font></a></li>
		                <li><a href="#"><font color="red">Transition</font></a></li>
	                 -->
	            </ul></div>
	        </li>
       	<%} %>
        <!--  <li><a href="#" class="parent"><span>Bills & Meter</span></a>
            <div><ul>
            
					    	
                <li><a href="/billings">Billing</a></li>
                <li><a href="/meters">Metering</a></li>
               
            </ul></div>
        </li> -->
         <li><a href="#" class="parent"><span>Usage Reports</span></a>
          <div><ul>
        <%
	    if(roles.contains("ROLE_SUPERADMIN"))
	    {
	    %>
                <li><a href="/reports/usageAll">All</a></li>
        <%} %>
        
        <%
	    if(roles.contains("ROLE_ADMIN") || 
	    		roles.contains("ROLE_MANAGER") ||
	    			roles.contains("ROLE_SUPERADMIN"))
	    {
	    %>
                <li><a href="/reports/usageDept">Departments</a></li>
                <li><a href="/reports/usageProj">Projects</a></li>
       <%} %>
                <li><a href="/reports/usageUser">Users</a></li>
            </ul></div> 
        </li>
        <li><a class="parent" href="/resources/j_spring_security_logout"><span>Logout</span></a>
        </li>
    </ul>
<div style=" color: grey;line-height: 30px;padding: 1px 20px;    
   		text-align: right; cursor: pointer;display: block;  font-weight: bold; "> 
   			
   		<span id="mysession"></span>
   </div>    
  
</div>


<div style="visibility: hidden;" id="copyright">Copyright &copy; 2012 <a href="http://apycom.com/">Apycom jQuery Menus</a></div>


