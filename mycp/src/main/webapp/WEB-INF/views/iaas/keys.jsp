<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
  <script type='text/javascript' src='/dwr/interface/KeyPairInfoP.js'></script>
  
<script type="text/javascript">
/***************************/
//@Author: Adrian "yEnS" Mato Gondelle
//@website: www.yensdesign.com
//@email: yensamg@gmail.com
//@license: Feel free to use it, but keep this credits please!					
/***************************/
	var popupStatus_keys = 0;

	function loadPopup_keys(){
		if(popupStatus_keys==0){
			$("#backgroundPopup_keys").css({
				"opacity": "0.7"
			});
			$("#backgroundPopup_keys").fadeIn("slow");
			$("#popupContact_keys").fadeIn("slow");
			popupStatus_keys = 1;
		}
	}

	function disablePopup_keys(){
		if(popupStatus_keys==1){
			$("#backgroundPopup_keys").fadeOut("slow");
			$("#popupContact_keys").fadeOut("slow");
			popupStatus_keys = 0;
		}
	}

	function centerPopup_keys(){
		var windowWidth = document.documentElement.clientWidth;
		var windowHeight = document.documentElement.clientHeight;
		var popupHeight = $("#popupContact_keys").height();
		var popupWidth = $("#popupContact_keys").width();
		$("#popupContact_keys").css({
			"position": "absolute",
			"top": windowHeight/2-popupHeight/2,
			"left": windowWidth/2-popupWidth/2
		});
		
		$("#backgroundPopup_keys").css({
			"height": windowHeight
		});
		
	}

	function findAll_keys(p){
		
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
	            { "sTitle": "Fingerprint" },
	            { "sTitle": "Key" },
	            { "sTitle": "Actions" }
	           
	        ]
	    } );
		
		var i=0;
		for (i=0;i<p.length;i++)
		{
			var d = '<div style=\"display:none;\" id= keyMat'+p[i].id+'>'+p[i].keyMaterial+'</div>';
			oTable.fnAddData( [i+1,p[i].keyName, p[i].keyFingerprint,'<a href=\"#\" onClick=\"+showKeyMaterial('+p[i].id+')\">Show/Hide</a>'+d,
			                   '<img class="clickimg" title="Remove" alt="Remove" src=../images/remove.png onclick=delete_keys('+p[i].id+')>&nbsp; &nbsp; '+
			                   //'<img alt="Edit" src=../images/edit.png onclick=edit_keys('+p[i].id+')>&nbsp; &nbsp; &nbsp; '+
			                   '<img class="clickimg" title="Delete" alt="Delete" src=../images/deny.png onclick=remove_keys('+p[i].id+')>' ] );
		}
		
		
	
	}

	function showKeyMaterial(id){
		if (document.getElementById('keyMat'+id).style.display == 'none') {
			document.getElementById('keyMat'+id).style.display = 'block';
		}else{
			document.getElementById('keyMat'+id).style.display = 'none';
		}
		//KeyPairInfoP.findById(id,showKey);
	}
	
	
	
	var viewed_keys = -1;	
	$(function(){
			$("#popupbutton_keys").click(function(){
				viewed_keys = -1;
				centerPopup_keys();
				loadPopup_keys();
			});
		
				$("#popupbutton_keyslist").click(function(){
						dwr.engine.beginBatch();
						KeyPairInfoP.findAll(findAll_keys);
					  dwr.engine.endBatch();
				} );
			});
		
		$("#popupContactClose_keys").click(function(){
			viewed_keys = -1;
			disablePopup_keys();
		});
		$("#backgroundPopup_keys").click(function(){
			viewed_keys = -1;
			disablePopup_keys();
		});
		$(document).keypress(function(e){
			if(e.keyCode==27 && popupStatus_keys==1){
				disablePopup_keys();
			}
		});
		
		$(document).ready(function() {
			$("#popupbutton_keyslist").click();

			KeyPairInfoP.findProductType(function(p){
				//alert(dwr.util.toDescriptiveString(p,3));
  				dwr.util.removeAllOptions('product');
  				dwr.util.addOptions('product', p,'id','name');
  				//dwr.util.setValue(id, sel);
  			});
			
			$("#thisform").validate({
				 submitHandler: function(form) {
					 submitForm_keys(form);
					 return false;
				 }
				});

			
		});
		
	function submitForm_keys(f){
		var keyPairInfop = {  id:viewed_keys,keyName:null,product:null };
		  dwr.util.getValues(keyPairInfop);
		  if(viewed_keys == -1){
			  keyPairInfop.id  = null; 
		  }
		  dwr.engine.beginBatch();
		  KeyPairInfoP.saveOrUpdate(keyPairInfop,afterSave_keys);
		  dwr.engine.endBatch();
		  disablePopup_keys();
		  viewed_keys=-1;
	}
	function cancelForm_keys(f){
	
		var keyPairInfop = {  id:null,keyName:null ,product:null};
		  dwr.util.setValues(keyPairInfop);
		  viewed_keys = -1;
		  disablePopup_keys();
	}
	
	function afterEdit_keys(p){
		var keyPairInfop = eval(p);
		viewed_keys=p.id;
		centerPopup_keys();
		loadPopup_keys();
		dwr.util.setValues(keyPairInfop);
		dwr.util.setValue('product',p.product.id);
	}
	
	function edit_keys(id){
		KeyPairInfoP.findById(id,afterEdit_keys);
	}
	
	function remove_keys(id){
		if(!disp_confirm('Key')){
			return false;
		}
		dwr.engine.beginBatch();
		KeyPairInfoP.remove(id,afterSave_keys);
		dwr.engine.endBatch();
	}
	function afterSave_keys(){
		viewed_keys = -1;
		$("#popupbutton_keyslist").click();}
	
	function delete_keys(id){
		dwr.engine.beginBatch();
		KeyPairInfoP.deleteKeyPair(id,afterSave_keys);
		dwr.engine.endBatch();
		}
	
	</script>
<p class="dataTableHeader">Key Resource</p>
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
									<div class="demo" id="popupbutton_keys"><button>Request Key</button></div>
								</td>
								<td width="10%">
									<div class="demo" id="popupbutton_keyslist"><button>List Keys</button></div>
								</td>
							</tr>
						</table>
						
				</div>
				</div>
	<div id="popupContactParent_keys" >
		<div id="popupContact_keys" class="popupContact" >
							<a  onclick="cancelForm_keys();return false;" class="popupContactClose" style="cursor: pointer; text-decoration:none;">X</a>
							<h1>Keys</h1>
							<form class="cmxform" id="thisform" method="post" name="thisform">
								<p id="contactArea_keys" class="contactArea" >
								<input type="hidden" id="id" name="id">
								<table style="width: 100%;">
								  <tr>
								    <td style="width: 50%;">Name : </td>
								    <td style="width: 50%;"><input type="text" name="keyName" id="keyName" size="30" class="required"></td>
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
										<div class="demo" id="popupbutton_keys_create">
											<input class="submit" type="submit" value="Save"/>&nbsp;&nbsp;&nbsp;&nbsp;
											<button onclick="cancelForm_keys(this.form);return false;">Cancel</button>
										</div>
									</td>
								  </tr>
								</table>
								</p>
							</form>
						</div>
		<div id="backgroundPopup_keys" class="backgroundPopup" ></div>
	</div>				