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
//SETTING UP OUR POPUP
	//0 means disabled; 1 means enabled;
	var popupStatus_infra = 0;

	//loading popup with jQuery magic!
	function loadPopup_infra(){
		//loads popup only if it is disabled
		if(popupStatus_infra==0){
			$("#backgroundPopup_infra").css({
				"opacity": "0.7"
			});
			$("#backgroundPopup_infra").fadeIn("slow");
			$("#popupContact_infra").fadeIn("slow");
			popupStatus_infra = 1;
		}
	}

	//disabling popup with jQuery magic!
	function disablePopup_infra(){
		//disables popup only if it is enabled
		if(popupStatus_infra==1){
			$("#backgroundPopup_infra").fadeOut("slow");
			$("#popupContact_infra").fadeOut("slow");
			popupStatus_infra = 0;
		}
	}

	//centering popup
	function centerPopup_infra(){
		//request data for centering
		var windowWidth = document.documentElement.clientWidth;
		var windowHeight = document.documentElement.clientHeight;
		var popupHeight = $("#popupContact_infra").height();
		var popupWidth = $("#popupContact_infra").width();
		//centering
		$("#popupContact_infra").css({
			"position": "absolute",
			"top": windowHeight/2-popupHeight/2,
			"left": windowWidth/2-popupWidth/2
		});
		//only need force for IE6
		
		$("#backgroundPopup_infra").css({
			"height": windowHeight
		});
		
	}
	var isMycpAdmin = false;
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
	      	            { "sTitle": "Description" },
	      	            { "sTitle": "Actions" }
	      	           
	      	        ]
	      	    } );
		
		//oTable.fnClearTable();
		
		var i=0;
		for (i=0;i<p.length;i++)
		{
			var options = '<img class="clickimg" alt="Sync" src=/images/sync.png onclick=sync_infra('+p[i].id+') title="Sync Backend" />&nbsp;  '+
            '<img alt="Edit" class="clickimg" src=../images/edit.png onclick=edit_infra('+p[i].id+') title="Edit"  />&nbsp;  ';
			if(isMycpAdmin){
				options = '<img alt="Sync" class="clickimg" src=/images/sync.png onclick=sync_infra('+p[i].id+') title="Sync Backend" />&nbsp;  '+
	            '<img alt="Edit" class="clickimg" src=/images/edit.png onclick=edit_infra('+p[i].id+') title="Edit" />&nbsp;  '+
                '<img alt="Remove" class="clickimg" src=/images/deny.png onclick=remove_infra('+p[i].id+') title="Remove" />';
			}
			//oTable.fnAddData( [i+1, p[i].accessId, p[i].secretKey,p[i].isSecure,p[i].server,p[i].port,p[i].details,
			oTable.fnAddData( [i+1, p[i].name, p[i].accessId,p[i].isSecure,p[i].server,p[i].port,p[i].details,
			                   options ] );
		}
		
		  
	
	}
	
	
	var viewed_infra = -1;	
$(function(){
	
	
	//LOADING POPUP
		//Click the button event!
		$("#popupbutton_infra").click(function(){
			viewed_infra = -1;
			//centering with css
			centerPopup_infra();
			//load popup
			loadPopup_infra();
		});
	
		$("#popupbutton_infralist").click(function(){
				dwr.engine.beginBatch();
				InfraService.findAll(findAll_infra);
			  dwr.engine.endBatch();
		} );
		
		});
		
		
		
					
		//CLOSING POPUP
		//Click the x event!
		$("#popupContactClose_infra").click(function(){
			viewed_infra = -1;
			disablePopup_infra();
		});
		//Click out event!
		$("#backgroundPopup_infra").click(function(){
			viewed_infra = -1;
			disablePopup_infra();
		});
		//Press Escape event!
		$(document).keypress(function(e){
			if(e.keyCode==27 && popupStatus_infra==1){
				disablePopup_infra();
			}
		});
		
		
		
		$(document).ready(function() {
			
			$("#popupbutton_infralist").click();
			
			$("#thisform").validate({
				 submitHandler: function(form) {
					 submitForm_infra(form);
					 return false;
				 }
			});
			
			CommonService.getCurrentSession(function(p){
					if(p.role == 'ROLE_USER'){
						dwr.util.setValue('only4mycpadmin', '');	
					}else{
						isMycpAdmin = true; 
					}
				});
			
				});
			
			
			
			CompanyService.findAll(function(p){
				dwr.util.removeAllOptions('company');
				dwr.util.addOptions('company', p, 'id', 'name');
				//dwr.util.setValue(id, sel);
			});
			
			
			  
		
		
function submitForm_infra(f){
	var infra = {  id:viewed_infra,name:null,accessId:null, secretKey:null, isSecure:null, server:null,resourcePrefix:null,signatureVersion:null, port:null, details:null, company:{},zone:null };
	  dwr.util.getValues(infra);
	  infra.company.id= dwr.util.getValue("company");
	  if(viewed_infra == -1){
		  infra.id  = null; 
	  }
	  dwr.engine.beginBatch();
	  InfraService.saveOrUpdate(infra,afterSave_infra);
	 
	  dwr.engine.endBatch();
	  disablePopup_infra();
	  viewed_infra=-1;
}
function cancelForm_infra(f){

	var infra = {  id:null,name:null,accessId:null, secretKey:null, isSecure:null, server:null,resourcePrefix:null,signatureVersion:null, port:null, details:null,zone:null };
	  dwr.util.setValues(infra);
	  viewed_infra = -1;
	  disablePopup_infra();
}

function afterEdit_infra(p){
	var infra = eval(p);
	viewed_infra=p.id;
	centerPopup_infra();
	loadPopup_infra();
	dwr.util.setValues(infra);
	dwr.util.setValue('company',p.company.id);
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
									<span id="only4mycpadmin"><div class="demo" id="popupbutton_infra"><button>New Cloud</button></div></span>
								</td>
								<td width="10%">
									<div class="demo" id="popupbutton_infralist"><button>List Cloud</button></div>
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
								    <td style="width: 50%;"><input type="text" name="signatureVersion" id="signatureVersion" size="30" ></td>
								  </tr>
								  
								  
								   <tr>
								    <td style="width: 50%;">Port : </td>
								    <td style="width: 50%;"><input type="text" name="port" id="port" size="30" class="required number"></td>
								  </tr>
								  <tr>
								    <td style="width: 50%;">Zone : </td>
								    <td style="width: 50%;"><input type="text" name="zone" id="zone" size="30" class="required"></td>
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