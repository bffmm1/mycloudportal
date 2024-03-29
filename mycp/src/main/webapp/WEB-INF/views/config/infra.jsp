<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<script type='text/javascript' src='/dwr/interface/InfraService.js'></script>
<script type='text/javascript' src='/dwr/interface/CompanyService.js'></script>

<script type="text/javascript">
/***************************/
//@Author: Adrian "yEnS" Mato Gondelle
//@website: www.yensdesign.com
//@email: yensamg@gmail.com
//@license: Feel free to use it, but keep this credits please!					
/***************************/
	var popupStatus_infra = 0;

	function loadPopup_infra(){
		if(popupStatus_infra==0){
			$("#backgroundPopup_infra").css({
				"opacity": "0.7"
			});
			$("#backgroundPopup_infra").fadeIn("slow");
			$("#popupContact_infra").fadeIn("slow");
			popupStatus_infra = 1;
		}
	}

	function disablePopup_infra(){
		if(popupStatus_infra==1){
			$("#backgroundPopup_infra").fadeOut("slow");
			$("#popupContact_infra").fadeOut("slow");
			popupStatus_infra = 0;
		}
	}

	function centerPopup_infra(){
		var windowWidth = document.documentElement.clientWidth;
		var windowHeight = document.documentElement.clientHeight;
		var popupHeight = $("#popupContact_infra").height();
		var popupWidth = $("#popupContact_infra").width();
		$("#popupContact_infra").css({
			"position": "absolute",
			"top": windowHeight/2-popupHeight/2,
			"left": windowWidth/2-popupWidth/2
		});
		
		$("#backgroundPopup_infra").css({
			"height": windowHeight
		});
		
	}
	
	var popupStatus_infra_aws = 0;

	function loadPopup_infra_aws(){
		if(popupStatus_infra_aws==0){
			$("#backgroundPopup_infra_aws").css({
				"opacity": "0.7"
			});
			$("#backgroundPopup_infra_aws").fadeIn("slow");
			$("#popupContact_infra_aws").fadeIn("slow");
			popupStatus_infra_aws = 1;
		}
	}

	function disablePopup_infra_aws(){
		if(popupStatus_infra_aws==1){
			$("#backgroundPopup_infra_aws").fadeOut("slow");
			$("#popupContact_infra_aws").fadeOut("slow");
			popupStatus_infra_aws = 0;
		}
	}

	function centerPopup_infra_aws(){
		var windowWidth = document.documentElement.clientWidth;
		var windowHeight = document.documentElement.clientHeight;
		var popupHeight = $("#popupContact_infra_aws").height();
		var popupWidth = $("#popupContact_infra_aws").width();
		$("#popupContact_infra_aws").css({
			"position": "absolute",
			"top": windowHeight/2-popupHeight/2,
			"left": windowWidth/2-popupWidth/2
		});
		
		$("#backgroundPopup_infra_aws").css({
			"height": windowHeight
		});
		
	}
	
	var isMycpAdmin = false;
	var isAdmin = false;
	function findAll_infra(p){
		/* alert(p.length);
		alert(p[0].imageId); */
	//alert(dwr.util.toDescriptiveString(p,3));
		
		//var tableData = eval( dwr.util.toDescriptiveString(p,3) );
		//var continents = arrayAsJSONText.parseJSON();
		//alert(tableData);
		//alert(tableData[0].id+tableData[0].name);

		 oTable = $('#compute-table').dataTable( {
	    	"sPaginationType": "full_numbers",
	    	"bDestroy": true,
	    	"bAutoWidth": false,
	    	"bDeferRender": true,
	    	"bJQueryUI": false,
	    	"bLengthChange": false,
	    	"iDisplayLength": 17,
	        "aaData": [
	        ],
	        "aoColumns": [
	      	            { "sTitle": "#" },
	      	            { "sTitle": "Name" },
	      	            { "sTitle": "Access Key" },
	      	            { "sTitle": "Secure?" },
	      	            { "sTitle": "Server IP/DNS" },
	      	            { "sTitle": "Server Port" },
	      	            { "sTitle": "Sync" },
	      	            { "sTitle": "Actions" }
	      	           
	      	        ]
	      	    } );
		
		//oTable.fnClearTable();
		
		var i=0;
		for (i=0;i<p.length;i++)
		{
			var options = '';
			
			if(isMycpAdmin || isAdmin){
				options = '<img alt="Sync" class="clickimg" src=/images/sync.png onclick=sync_infra('+p[i].id+') title="Synchronize" />&nbsp;  '+
	            '<img alt="Edit" class="clickimg" src=/images/edit.png onclick=edit_infra('+p[i].id+') title="Edit" />&nbsp;  '+
                '<img alt="Remove" class="clickimg" src=/images/deny.png onclick=remove_infra('+p[i].id+') title="Remove" />';
			}
			if(p[i].syncInProgress == 1){
				p[i].syncInProgress='<img  title="Starting" alt="Starting" src=/images/preloader.gif>&nbsp;';
			}else{
				if(p[i].syncDate !=null &&  p[i].syncstatus!=null && p[i].syncstatus == 2){
					p[i].syncInProgress='Synced on '+dateFormat(p[i].syncDate,"mmm dd HH:MM");	
				}else if(p[i].syncDate !=null && p[i].syncstatus!=null && p[i].syncstatus == 1){
					p[i].syncInProgress='Synced Failed on '+dateFormat(p[i].syncDate,"mmm dd HH:MM");	
					
				}else {
					p[i].syncInProgress='No Sync';
				}
				
			}
			//oTable.fnAddData( [i+1, p[i].accessId, p[i].secretKey,p[i].isSecure,p[i].server,p[i].port,p[i].details,
			oTable.fnAddData( [i+1, p[i].name, p[i].accessId,p[i].isSecure,p[i].server,p[i].port,p[i].syncInProgress,
			                   options ] );
		}
		
		  
	
	}
	
	
	var viewed_infra = -1;	
$(function(){

	$("#popupbutton_infra").click(function(){
			viewed_infra = -1;
			centerPopup_infra();
			loadPopup_infra();
		});
	
		$("#popupbutton_infralist").click(function(){
				dwr.engine.beginBatch();
				InfraService.findAll(findAll_infra);
			  dwr.engine.endBatch();
		} );
		});
		
		$("#popupContactClose_infra").click(function(){
			viewed_infra = -1;
			disablePopup_infra();
		});

		$("#backgroundPopup_infra").click(function(){
			viewed_infra = -1;
			disablePopup_infra();
		});
		
		$("#popupContactClose_infra_aws").click(function(){
			viewed_infra = -1;
			disablePopup_infra_aws();
		});

		$("#backgroundPopup_infra_aws").click(function(){
			viewed_infra = -1;
			disablePopup_infra_aws();
		});

		$(document).keypress(function(e){
			if(e.keyCode==27 && (popupStatus_infra==1 ||
					popupStatus_infra_aws==1)){
				disablePopup_infra();
				disablePopup_infra_aws();
			}
		});
		
		
		
		$(document).ready(function() {
			$("#thisform").validate({
				 submitHandler: function(form) {
					 submitForm_infra(form);
					 return false;
				 }
			});
			
			$("#thisform_aws").validate({
				 submitHandler: function(form) {
					 submitForm_infra_aws(form);
					 return false;
				 }
			});
			
			CommonService.getCurrentSession(function(p){
					if(p.role == 'ROLE_SUPERADMIN'){
						isMycpAdmin = true; 	
					}else if(p.role == 'ROLE_USER'){
						dwr.util.setValue('only4mycpadmin', '');
					}else{
						isAdmin = true;
					}
				});
					$("#popupbutton_infralist").click();
				});
			
			
			
			CompanyService.findAll(function(p){
				dwr.util.removeAllOptions('company');
				dwr.util.addOptions('company', p, 'id', 'name');
				//dwr.util.setValue(id, sel);
			});
			
			CompanyService.findAll(function(p){
				dwr.util.removeAllOptions('company_aws');
				dwr.util.addOptions('company_aws', p, 'id', 'name');
				//dwr.util.setValue(id, sel);
			});
			
			
			function loadCloudType(sel)  {
				var value = sel.options[sel.selectedIndex].value;
				//alert(sel.selectedIndex);
				viewed_infra = -1;
				if('AWS' == value){
					disablePopup_infra();
					
					centerPopup_infra_aws();
					loadPopup_infra_aws();
					var selObj = document.getElementById('cloudtype_aws');
					selObj.selectedIndex = 1;
					
				}else if('Euca' == value){
					disablePopup_infra_aws();
					
					centerPopup_infra();
					loadPopup_infra();
					var selObj = document.getElementById('cloudtype');
					selObj.selectedIndex = 0;
				}
				
			}
		
		
	function submitForm_infra(f){
		var infra = {  id:viewed_infra,name:null,accessId:null, secretKey:null, isSecure:null, server:null,resourcePrefix:null,signatureVersion:null, port:null, details:null, company:{} };
		  dwr.util.getValues(infra);
		  infra.company.id= dwr.util.getValue("company");
		  
		  if(viewed_infra == -1){
			  infra.id  = null; 
		  }
		 
		  
		  InfraService.saveOrUpdate(infra,afterSave_infra);

		  dwr.util.setValue('cloudtype_aws','');
		  dwr.util.setValue('cloudtype','');
		  disablePopup_infra();
		  viewed_infra=-1;
		  
	}

	function submitForm_infra_aws(f){
		var infra = {  id:viewed_infra,name:null,accessId:null, secretKey:null, server:null,port:null, details:null, company:{} };
		  
		  infra.company.id= dwr.util.getValue("company_aws");
		  infra.name=dwr.util.getValue("name_aws");
		  infra.accessId=dwr.util.getValue("accessId_aws");
		  infra.secretKey=dwr.util.getValue("secretKey_aws");
		  infra.server=dwr.util.getValue("server_aws");
		  infra.details=dwr.util.getValue("details_aws");
		  infra.port='80';
		  if(viewed_infra == -1){
			  infra.id  = null; 
		  }
		  
		  InfraService.saveOrUpdate(infra,afterSave_infra);
		 
		  dwr.util.setValue('cloudtype_aws','');
		  dwr.util.setValue('cloudtype','');
		  disablePopup_infra_aws();
		  viewed_infra=-1;
		  
	}

function cancelForm_infra(f){

	var infra = {  id:null,name:null,accessId:null, secretKey:null, isSecure:null, server:null,resourcePrefix:null,signatureVersion:null, port:null, details:null };
	  dwr.util.setValues(infra);
	  dwr.util.setValue('cloudtype_aws','');
	  dwr.util.setValue('cloudtype','');
	  viewed_infra = -1;
	  disablePopup_infra();
}

function cancelForm_infra_aws(f){

	var infra = {  id:null,name:null,accessId:null, secretKey:null, isSecure:null, server:null, details:null };
	  dwr.util.setValues(infra);
	  dwr.util.setValue('cloudtype_aws','');
	  dwr.util.setValue('cloudtype','');
	  viewed_infra_aws = -1;
	  disablePopup_infra_aws();
}

function afterEdit_infra(p){
	var infra = eval(p);
	if(infra.server=='ec2.amazonaws.com'){
		viewed_infra_aws=p.id;
		viewed_infra=p.id;
		centerPopup_infra_aws();
		loadPopup_infra_aws();
		dwr.util.setValues(infra);
			dwr.util.setValue("company_aws",p.company.id);
		  	dwr.util.setValue("name_aws",p.name);
		  	dwr.util.setValue("accessId_aws",p.accessId);
		  	dwr.util.setValue("secretKey_aws",p.secretKey);
		  	dwr.util.setValue("server_aws",p.server);
		  	dwr.util.setValue("details_aws",p.details);
		  
		
		var selObj = document.getElementById('cloudtype_aws');
		selObj.selectedIndex = 1;
	}else{
		viewed_infra=p.id;
		centerPopup_infra();
		loadPopup_infra();
		dwr.util.setValues(infra);
		dwr.util.setValue('company',p.company.id);
		var selObj = document.getElementById('cloudtype');
		selObj.selectedIndex = 0;
	}
	
	
}

function edit_infra(id){
	InfraService.findById(id,afterEdit_infra);
}

function remove_infra(id){
	if(!disp_confirm('Cloud')){
		return false;
	}
	dwr.engine.beginBatch();
	InfraService.remove(id,afterRemove_infra);
	dwr.engine.endBatch();
}

function afterRemove_infra(p){
	viewed_infra = -1;
	$("#popupbutton_infralist").click();
	$.sticky(p);
	}
	
function afterSave_infra(){
	viewed_infra = -1;
	$("#popupbutton_infralist").click();
	}

function import_infra(id){
	//alert(id);
}

function sync_infra(id){
	InfraService.syncDataFromEuca(id+'');
	$.sticky('Sync scheduled');
	//alert(id);
}

</script>
<p class="dataTableHeader">Cloud Configuration</p>
<div id="datatable-iaas-parent" class="infragrid2">
					<div id="datatable-iaas" >
						<table cellpadding="0" cellspacing="0" border="0" class="display" id="compute-table">
							<thead><tr></tr></thead>
							<tfoot><tr><th rowspan="1" colspan="5"></th></tr>
							</tfoot><tbody></tbody>
						</table>
						<div style="height: 50px;"></div>
						
						<table align="right" border="0" width="100%">
							<tr>
								<td width="80%">
								
								</td>
								<td width="10%">
									<span id="only4mycpadmin"><div class="demo" id="popupbutton_infra"><button>Configure New Cloud</button></div></span>
								</td>
								<td width="10%">
									<div class="demo" id="popupbutton_infralist"><button>List All Clouds</button></div>
								</td>
							</tr>
						</table>
						
						
				</div>
				</div>
				
	<div id="popupContactParent_infra" >
		<div id="popupContact_infra" class="popupContact" >
							<a  onclick="cancelForm_infra();return false;" class="popupContactClose" style="cursor: pointer; text-decoration:none;">X</a>
							<h1>Cloud</h1>
							<form class="cmxform" id="thisform" method="post" name="thisform">
								<p id="contactArea_infra" class="contactArea" >
								<input type="hidden" id="id" name="id">
								<table style="width: 100%;">
								<tr>
								    <td style="width: 50%;">Cloud Type: </td>
								    <td style="width: 50%;"> 
									    <select id="cloudtype" name="cloudtype" style="width: 205px;" class="required" onchange="loadCloudType(this)" >
									    	<option value="Euca" selected="selected">Eucalyptus</option>
									    	<option value="AWS" >Amazon Web Services</option>
									    </select>
								    </td>
								  </tr>
								  
								<tr>
								    <td style="width: 50%;">Name : </td>
								    <td style="width: 50%;"><input type="text" name="name" id="name" size="30" class="required"></td>
								  </tr>
								  <tr>
								    <td style="width: 50%;">Access Key : </td>
								    <td style="width: 50%;"><input type="text" name="accessId" id="accessId" size="30" class="required"></td>
								  </tr>
								  
								  <tr>
								    <td style="width: 50%;">Secret Key : </td>
								    <td style="width: 50%;"><input type="text" name="secretKey" id="secretKey" size="30" class="required"></td>
								  </tr>
								  <tr>
								    <td style="width: 50%;">Secure? : </td>
								    <td style="width: 50%;"><input type="checkbox" name="isSecure" id="isSecure"></td>
								  </tr>
								  
								  <tr>
								    <td style="width: 50%;">Server : </td>
								    <td style="width: 50%;"><input type="text" name="server" id="server" size="30" class="required"></td>
								  </tr>
								  
								  
								  <tr>
								    <td style="width: 50%;">Resource Prefix : </td>
								    <td style="width: 50%;"><input type="text" name="resourcePrefix" id="resourcePrefix" size="30" ></td>
								  </tr>
								  
								  <tr>
								    <td style="width: 50%;">Signature Version : </td>
								    <td style="width: 50%;"><input type="text" name="signatureVersion" id="signatureVersion" size="30" class="number" ></td>
								  </tr>
								  
								  
								   <tr>
								    <td style="width: 50%;">Port : </td>
								    <td style="width: 50%;"><input type="text" name="port" id="port" size="30" class="required number"></td>
								  </tr>
								  
								  <tr>
								    <td style="width: 50%;">Details : </td>
								    <td style="width: 50%;"><input type="text" name="details" id="details" size="30"></td>
								  </tr>
								   <tr>
								    <td style="width: 50%;">Account : </td>
								    <td style="width: 50%;">
								    <select id="company" name="company" style="width: 205px;" class="required">
							    	</select>
								    <!-- <input type="text" name="company" id="company" size="30"> -->
								    </td>
								  </tr> 
								  <tr>
								    <td style="width: 50%;"></td>
								    <td style="width: 50%;">
								    <br><br>
										<div class="demo" id="popupbutton_infra_create">
											<input class="submit" type="submit" value="Save"/>&nbsp;&nbsp;&nbsp;&nbsp;
											<button onclick="cancelForm_infra(this.form);return false;">Cancel</button>
										</div>
									</td>
								  </tr>
								</table>
								</p>
							</form>
						</div>
		<div id="backgroundPopup_infra" class="backgroundPopup" ></div>
	</div>	
	
	<div id="popupContactParent_infra_aws" >
		<div id="popupContact_infra_aws" class="popupContact" >
							<a  onclick="cancelForm_infra_aws();return false;" class="popupContactClose" style="cursor: pointer; text-decoration:none;">X</a>
							<h1>Cloud</h1>
							<form class="cmxform" id="thisform_aws" method="post" name="thisform_aws">
								<p id="contactArea_infra_aws" class="contactArea" >
								<input type="hidden" id="id" name="id">
								<table style="width: 100%;">
								  <tr>
								    <td style="width: 50%;">Cloud Type: </td>
								    <td style="width: 50%;"> 
									    <select id="cloudtype_aws" name="cloudtype_aws" style="width: 205px;" class="required" onchange="loadCloudType(this)" >
									    	<option value="Euca" >Eucalyptus</option>
									    	<option value="AWS" selected="selected">Amazon Web Services</option>
									    	
									    </select>
								    </td>
								  </tr>
								  <tr>
								    <td style="width: 50%;">Name : </td>
								    <td style="width: 50%;"><input type="text" name="name_aws" id="name_aws" size="30" class="required"></td>
								  </tr>
								  
								  <tr>
								    <td style="width: 50%;">Access Key : </td>
								    <td style="width: 50%;"><input type="text" name="accessId_aws" id="accessId_aws" size="30" class="required"></td>
								  </tr>
								  
								  <tr>
								    <td style="width: 50%;">Secret Key : </td>
								    <td style="width: 50%;"><input type="text" name="secretKey_aws" id="secretKey_aws" size="30" class="required"></td>
								  </tr>
								  <tr>
								    <td style="width: 50%;">Server : </td>
								    <td style="width: 50%;"><input type="text" name="server_aws" id="server_aws" size="30" class="required" readonly="readonly" value="ec2.amazonaws.com"></td>
								  </tr>
								  <tr>
								    <td style="width: 50%;">Details : </td>
								    <td style="width: 50%;"><input type="text" name="details_aws" id="details_aws" size="30"></td>
								  </tr>
								   <tr>
								    <td style="width: 50%;">Account : </td>
								    <td style="width: 50%;">
								    <select id="company_aws" name="company_aws" style="width: 205px;" class="required"></select>
								    </td>
								  </tr> 
								  <tr>
								    <td style="width: 50%;"></td>
								    <td style="width: 50%;">
								    <br><br>
										<div class="demo" id="popupbutton_infra_create_aws">
											<input class="submit" type="submit" value="Save"/>&nbsp;&nbsp;&nbsp;&nbsp;
											<button onclick="cancelForm_infra_aws(this.form);return false;">Cancel</button>
										</div>
									</td>
								  </tr>
								</table>
								</p>
							</form>
						</div>
		<div id="backgroundPopup_infra_aws" class="backgroundPopup" ></div>
	</div>			