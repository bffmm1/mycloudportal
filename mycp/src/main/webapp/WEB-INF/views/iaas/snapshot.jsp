<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<script type='text/javascript' src='/dwr/interface/SnapshotInfoP.js'></script>
<script type='text/javascript' src='/dwr/interface/VolumeInfoP.js'></script>
<script type="text/javascript">
/***************************/
//@Author: Adrian "yEnS" Mato Gondelle
//@website: www.yensdesign.com
//@email: yensamg@gmail.com
//@license: Feel free to use it, but keep this credits please!					
/***************************/
	var popupStatus_backup = 0;
	function loadPopup_backup(){
		if(popupStatus_backup==0){
			$("#backgroundPopup_backup").css({
				"opacity": "0.7"
			});
			$("#backgroundPopup_backup").fadeIn("slow");
			$("#popupContact_backup").fadeIn("slow");
			popupStatus_backup = 1;
		}
	}

	function disablePopup_backup(){
		if(popupStatus_backup==1){
			$("#backgroundPopup_backup").fadeOut("slow");
			$("#popupContact_backup").fadeOut("slow");
			popupStatus_backup = 0;
		}
	}

	function centerPopup_backup(){
		var windowWidth = document.documentElement.clientWidth;
		var windowHeight = document.documentElement.clientHeight;
		var popupHeight = $("#popupContact_backup").height();
		var popupWidth = $("#popupContact_backup").width();
		$("#popupContact_backup").css({
			"position": "absolute",
			"top": windowHeight/2-popupHeight/2,
			"left": windowWidth/2-popupWidth/2
		});
		$("#backgroundPopup_backup").css({
			"height": windowHeight
		});
		
	}

	function findAll_backup(p){
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
	            { "sTitle": "Snapshot Id" },
	            { "sTitle": "Volume Id" },
	            { "sTitle": "Start Time" },
	            { "sTitle": "Status" },
	            { "sTitle": "Progress" },
	            
	            { "sTitle": "Actions" }
	           
	        ]
	    } );
		var i=0;
		for (i=0;i<p.length;i++)
		{
			var actions = '<img class="clickimg" title="Remove" alt="Remove" src=../images/remove.png onclick=delete_backup('+p[i].id+')>&nbsp; &nbsp; '+
            '<img class="clickimg" title="Delete" alt="Delete" src=../images/deny.png onclick=remove_backup('+p[i].id+')>';
			if('PENDING_APPROVAL' == p[i].status ){
            	p[i].status='<img title="pending approval" alt="pending approval" src=/images/pending.png>&nbsp;';
            	actions='<img class="clickimg" title="Delete" alt="Delete" src=../images/deny.png onclick=remove_backup('+p[i].id+')>';
            }else if('pending' == p[i].status ){
            	p[i].status='<img title="starting" alt="starting" src=/images/preloader.gif>&nbsp;';
            	actions='';
            }else if('pending' == p[i].status ){
            	p[i].status='<img title="starting" alt="starting" src=/images/preloader.gif>&nbsp;';
            	actions='';
            }else if('inactive' == p[i].status ){
            	p[i].status='<img title="inactive" alt="inactive" src=/images/unknown.png>&nbsp;';
            	actions='<img class="clickimg" title="Delete" alt="Delete" src=../images/deny.png onclick=remove_backup('+p[i].id+')>';
            }
			
			oTable.fnAddData( [i+1,p[i].snapshotId, p[i].volumeId, 
			                   dateFormat(p[i].startTime,"mmm dd yyyy HH:MM:ss"), p[i].status,p[i].progress, 
			                   actions ] );
		}
		
		
	
	}

	var viewed_backup = -1;	
$(function(){
		$("#popupbutton_backup").click(function(){
			viewed_backup = -1;
			centerPopup_backup();
			loadPopup_backup();
		});
	
		$("#popupbutton_backuplist").click(function(){
			
				dwr.engine.beginBatch();
				SnapshotInfoP.findAll(findAll_backup);
			  dwr.engine.endBatch();
			  
		
		} );
		
		});

	$("#popupContactClose_backup").click(function(){
			viewed_backup = -1;
			disablePopup_backup();
		});
		$("#backgroundPopup_backup").click(function(){
			viewed_backup = -1;
			disablePopup_backup();
		});
		$(document).keypress(function(e){
			if(e.keyCode==27 && popupStatus_backup==1){
				disablePopup_backup();
			}
		});
		
		$(document).ready(function() {
			$("#popupbutton_backuplist").click();
			
			VolumeInfoP.findAll(function(p){
				dwr.util.removeAllOptions('instance');
				dwr.util.addOptions('volumeId', p, 'volumeId', function(p) {
					if(p.volumeId !=null){
						return p.volumeId+' '+p.size+' '+p.name;
					}else {
						return null;
					}
				});
			});
			
			SnapshotInfoP.findProductType(function(p){
				//alert(dwr.util.toDescriptiveString(p,3));
  				dwr.util.removeAllOptions('product');
  				dwr.util.addOptions('product', p,'id','name');
  				//dwr.util.setValue(id, sel);
  			});
			
			$("#thisform").validate({
				 submitHandler: function(form) {
					 submitForm_backup(form);
					 return false;
				 }
				});
		});
		
	function submitForm_backup(f){
		var snapshotInfop = {  id:viewed_backup,description:null, volumeId:null,product:null };
		  dwr.util.getValues(snapshotInfop);
		  if(viewed_backup == -1){
			  snapshotInfop.id  = null; 
		  }
		  dwr.engine.beginBatch();
		  SnapshotInfoP.saveOrUpdate(snapshotInfop,afterSave_backup);
		  dwr.engine.endBatch();
		  disablePopup_backup();
		  viewed_backup=-1;
		  $.sticky('<b>Snapshot scheduled to be created.</b><p>');
	}
	function cancelForm_backup(f){
	
		var snapshotInfop = {  id:null,description:null, volumeId:null,product:null  };
		  dwr.util.setValues(snapshotInfop);
		  viewed_backup = -1;
		  disablePopup_backup();
	}
	
	function afterEdit_backup(p){
		var snapshotInfop = eval(p);
		viewed_backup=p.id;
		centerPopup_backup();
		loadPopup_backup();
		dwr.util.setValues(snapshotInfop);
	}
	
	function edit_backup(id){
		SnapshotInfoP.findById(id,afterEdit_backup);
	}
	
	function remove_backup(id){
		if(!disp_confirm('Snapshot')){
			return false;
		}
		dwr.engine.beginBatch();
		SnapshotInfoP.remove(id,afterSave_backup);
		dwr.engine.endBatch();
	}
	function afterSave_backup(){
		viewed_backup = -1;
		$("#popupbutton_backuplist").click();
	}
	
	function delete_backup(id){
		dwr.engine.beginBatch();
		SnapshotInfoP.deleteSnapshot(id,afterSave_backup);
		dwr.engine.endBatch();
	}
	</script>
<p class="dataTableHeader">Snapshot Resource</p>
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
									<div class="demo" id="popupbutton_backup"><button>Request Snapshot</button></div>
								</td>
								<td width="10%">
									<div class="demo" id="popupbutton_backuplist"><button>List Snapshots</button></div>
								</td>
							</tr>
						</table>
						
						
				</div>
				</div>
				
	<div id="popupContactParent_backup" >
		<div id="popupContact_backup" class="popupContact" >
							<a  onclick="cancelForm_backup();return false;" class="popupContactClose" style="cursor: pointer; text-decoration:none;">X</a>
							<h1>Snapshot</h1>
							<form class="cmxform" id="thisform" method="post" name="thisform">
								<p id="contactArea_backup" class="contactArea" >
								<input type="hidden" id="id" name="id">
								<table style="width: 100%;">
								  <tr>
								    <td style="width: 50%;">Description : </td>
								    <td style="width: 50%;"><input type="text" name="description" id="description" size="30" class="required"> </td>
								  </tr>
								  
								  <tr>
								    <td style="width: 50%;">Volume : </td>
								    <td style="width: 50%;"><select id="volumeId" name="volumeId" style="width: 205px;" class="required"></td>
								  </tr>
								   <tr>
								    <td style="width: 50%;">Product : </td>
								    <td style="width: 50%;">
								    <select id="product" name="product" style="width: 205px;" class="required">
							    	</select>
							    	</td>
								  </tr>
								  <tr>
								    <td style="width: 50%;"></td>
								    <td style="width: 50%;">
								    <br><br>
										<div class="demo" id="popupbutton_backup_create">
											<input class="submit" type="submit" value="Save"/>&nbsp;&nbsp;&nbsp;
											<button onclick="cancelForm_backup(this.form);return false;">Cancel</button>
										</div>
									</td>
								  </tr>
								</table>
								</p>
							</form>
						</div>
		<div id="backgroundPopup_backup" class="backgroundPopup" ></div>
	</div>				