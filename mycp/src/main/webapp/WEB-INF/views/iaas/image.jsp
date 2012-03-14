<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
  <script type='text/javascript' src='/dwr/interface/ImageDescriptionP.js'></script>
<script type='text/javascript' src='/dwr/interface/InstanceP.js'></script>
  
<script type="text/javascript">
/***************************/
//@Author: Adrian "yEnS" Mato Gondelle
//@website: www.yensdesign.com
//@email: yensamg@gmail.com
//@license: Feel free to use it, but keep this credits please!					
/***************************/
//SETTING UP OUR POPUP
	//0 means disabled; 1 means enabled;
	var popupStatus_image = 0;

	//loading popup with jQuery magic!
	function loadPopup_image(){
		//loads popup only if it is disabled
		if(popupStatus_image==0){
			$("#backgroundPopup_image").css({
				"opacity": "0.7"
			});
			$("#backgroundPopup_image").fadeIn("slow");
			$("#popupContact_image").fadeIn("slow");
			popupStatus_image = 1;
		}
	}

	//disabling popup with jQuery magic!
	function disablePopup_image(){
		//disables popup only if it is enabled
		if(popupStatus_image==1){
			$("#backgroundPopup_image").fadeOut("slow");
			$("#popupContact_image").fadeOut("slow");
			popupStatus_image = 0;
		}
	}

	//centering popup
	function centerPopup_image(){
		//request data for centering
		var windowWidth = document.documentElement.clientWidth;
		var windowHeight = document.documentElement.clientHeight;
		var popupHeight = $("#popupContact_image").height();
		var popupWidth = $("#popupContact_image").width();
		//centering
		$("#popupContact_image").css({
			"position": "absolute",
			"top": windowHeight/2-popupHeight/2,
			"left": windowWidth/2-popupWidth/2
		});
		//only need force for IE6
		
		$("#backgroundPopup_image").css({
			"height": windowHeight
		});
		
	}

	function findAll_image(p){
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
	            { "sTitle": "Image Name" },
	            { "sTitle": "Image Id" },
	            { "sTitle": "Owner" },
	            { "sTitle": "State" },
	            { "sTitle": "Public" },
	            { "sTitle": "Architecture" },
	            { "sTitle": "Platform" },
	            { "sTitle": "Root Device" },
	            { "sTitle": "Image Type" },
	            { "sTitle": "Hypervisor" },
	            { "sTitle": "Virtualization" }
	            //{ "sTitle": "Actions" }
	           
	        ]
	    } );
		
		//oTable.fnClearTable();
		
		var i=0;
		for (i=0;i<p.length;i++)
		{
			
			oTable.fnAddData( [i+1,p[i].name, p[i].imageId, 
			                   p[i].imageOwnerId, p[i].imageState, p[i].isPublic,
			                   p[i].architecture,p[i].platform,p[i].rootDeviceName,p[i].imageType,p[i].hypervisor,p[i].virtualizationType,
			                   //'<img alt="Edit" src=../images/edit.png onclick=edit_image('+p[i].id+')>&nbsp; &nbsp; &nbsp; '+
			                   //'<img class="clickimg" title="Remove"  alt="Remove" src=../images/deny.png onclick=remove_image('+p[i].id+')>' 
			                   ] );
		}
		
		
	
	}

	var viewed_image = -1;	
$(function(){
		$("#popupbutton_image").click(function(){
			viewed_image = -1;
			centerPopup_image();
			loadPopup_image();
		});
	
			$("#popupbutton_imagelist").click(function(){
					dwr.engine.beginBatch();
					ImageDescriptionP.findAll(findAll_image);
				  dwr.engine.endBatch();
			} );
		});
		
		$("#popupContactClose_image").click(function(){
			viewed_image = -1;
			disablePopup_image();
		});
		$("#backgroundPopup_image").click(function(){
			viewed_image = -1;
			disablePopup_image();
		});
		$(document).keypress(function(e){
			if(e.keyCode==27 && popupStatus_image==1){
				disablePopup_image();
			}
		});
		
		$(document).ready(function() {
			$("#popupbutton_imagelist").click();
			InstanceP.findAll(function(p){
				dwr.util.removeAllOptions('instance');
				dwr.util.addOptions('instance', p, 'instanceId', function(p) {
					return p.name+' '+p.instanceId+' '+p.platform+' @ '+p.dnsName;
				});
			});
			
			$("#thisform").validate({
				 submitHandler: function(form) {
					 submitForm_image(form);
					 return false;
				 }
				});
		});
		
function submitForm_image(f){
	var imageDescriptionp = {  id:viewed_image,name:null, description:null };
	  dwr.util.getValues(imageDescriptionp);
	  imageDescriptionp.instanceIdForImgCreation=dwr.util.getValue("instance");
	  alert(imageDescriptionp.instanceIdForImgCreation);
	  if(viewed_image == -1){
		  imageDescriptionp.id  = null; 
	  }
	  dwr.engine.beginBatch();
	  ImageDescriptionP.saveOrUpdate(imageDescriptionp,afterSave_image);
	  dwr.engine.endBatch();
	  disablePopup_image();
	  viewed_image=-1;
}
function cancelForm_image(f){

	var imageDescriptionp = {  id:null,name:null, description:null };
	  dwr.util.setValues(imageDescriptionp);
	  viewed_image = -1;
	  disablePopup_image();
}

function afterEdit_image(p){
	var imageDescriptionp = eval(p);
	viewed_image=p.id;
	centerPopup_image();
	loadPopup_image();
	dwr.util.setValues(imageDescriptionp);
}

function edit_image(id){
	ImageDescriptionP.findById(id,afterEdit_image);
}

function remove_image(id){
	if(!disp_confirm('Image')){
		return false;
	}
	dwr.engine.beginBatch();
	ImageDescriptionP.remove(id,afterSave_image);
	dwr.engine.endBatch();
}
function afterSave_image(){
	viewed_image = -1;
	$("#popupbutton_imagelist").click();}

</script>
<p class="dataTableHeader">Image Resource</p>
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
								<font color="green">IaaS -- Image</font>
								</td>
								<td width="10%">
									<!-- <div class="demo" id="popupbutton_image"><button>Request Image</button></div> -->
								</td>
								<td width="10%">
									<div class="demo" id="popupbutton_imagelist"><button>List Images</button></div>
								</td>
							</tr>
						</table>
						
						
						
				</div>
				</div>
				
	<div id="popupContactParent_image" >
		<div id="popupContact_image" class="popupContact" >
							<a  onclick="cancelForm_image();return false;" class="popupContactClose" style="cursor: pointer; text-decoration:none;">X</a>
							<h1>Image</h1>
							<form class="cmxform" id="thisform" method="post" name="thisform">
								<p id="contactArea_image" class="contactArea" >
								<input type="hidden" id="id" name="id">
								<table style="width: 100%;">
								  <tr>
								    <td style="width: 50%;">Name : </td>
								    <td style="width: 50%;"><input type="text" name="name" id="name" size="30" class="required"></td>
								  </tr>
								  
								  <tr>
								    <td style="width: 50%;">Description : </td>
								    <td style="width: 50%;"><input type="text" name="description" id="description" size="30"></td>
								  </tr>
								  <tr>
								    <td style="width: 50%;">Instance : </td>
								    <td style="width: 50%;"><select id="instance" name="instance" style="width: 205px;" class="required">
							    	</select></td>
								  </tr>
								  
								  
								  <tr>
								    <td style="width: 50%;"></td>
								    <td style="width: 50%;">
								    <br><br>
										<div class="demo" id="popupbutton_image_create">
											<input class="submit" type="submit" value="Save"/>&nbsp;&nbsp;&nbsp;&nbsp;
											<button onclick="cancelForm_image(this.form);return false;">Cancel</button>
										</div>
									</td>
								  </tr>
								</table>
								</p>
							</form>
						</div>
		<div id="backgroundPopup_image" class="backgroundPopup" ></div>
	</div>				