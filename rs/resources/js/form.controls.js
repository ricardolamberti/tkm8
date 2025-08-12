 
var regCombos = new Array();
var formLovParentControl = "";

function registerDependantCombo(comboId) {
	if (getRegisteredCombo(comboId)!=null) 
		return;

	//alert("Combo Registered: "+comboId);
	regCombos[regCombos.length] = new ZDependantCombo(comboId);
} 

function dependantComboClicked(comboId) {
	//alert("Clicked Combo Registered: "+comboId);
	var combo = getRegisteredCombo(comboId);
	if (combo)
		combo.onClick();
} 

function cleanCombo(comboId) {
	var combo = getRegisteredCombo(comboId);
	if (combo) 
		combo.reset();
}
function getRegisteredCombo(comboId) {
  //alert("getRegisteredCombo length: "+regCombos.length);
  for (i=0;i<regCombos.length;i++) {
    currAss = regCombos[i];
  	//alert("testing "+i+"-> "+currAss.getId());
    if (currAss!=null && currAss.getId()==comboId) {
      //alert("found combo");
	  return currAss;
	}
  }
  //alert("Error, can't find Registered Combo: "+comboId);
  return null;
}

function cleanComboRegistry() {
	regCombos = new Array();
}




ZDependantCombo = function(comboId) {

	//alert("New Combo: "+comboId);
	
	this.comboId = comboId;
	
	var oCombo = document.getElementById(comboId);
	var oComboStyle = oCombo.style;
	var oDivStyle = document.getElementById(comboId+"_div").style;
	var oTextBox = document.getElementById(comboId+"_text");
	var oButton = document.getElementById(comboId+"_button");
	var oProcessing = document.getElementById(comboId+"_processing");
	var oTextBoxStyle = oTextBox.style;
	var oButtonStyle = oButton.style;
	var oProcessingStyle = oProcessing.style;
	var textBoxWidth = parseDimension(oDivStyle.width);
	var textBoxHeight = parseDimension(oDivStyle.height);
	
	oComboStyle.visibility = 'hidden';
	oComboStyle.zindex = 0	;

	oTextBoxStyle.width = textBoxWidth;
	oTextBoxStyle.height = 18;
	//oTextBoxStyle.top = 0;
	oTextBoxStyle.left = 0;
	oTextBoxStyle.position = 'absolute';
	oTextBoxStyle.zindex = 1;
	oTextBox.readOnly = true;

	oButtonStyle.position = 'absolute';
	oButtonStyle.top = 1;
	oButtonStyle.left = textBoxWidth-17;
	oButtonStyle.width = 20
	oButtonStyle.height = 20

	oProcessingStyle.position = 'absolute';
	oProcessingStyle.top = 4;
	oProcessingStyle.left = textBoxWidth-15;
	oProcessingStyle.width = 20
	oProcessingStyle.height = 20
	oProcessingStyle.visibility = 'hidden';
	
	showCombo(this.comboId);
	
	//alert("new combo registered "+this.comboId);
}
ZDependantCombo.prototype.getId = function() {
	return this.comboId;
}
ZDependantCombo.prototype.isEmptyCombo = function() {
	var oCombo = document.getElementById(this.comboId);
	return oCombo.value=="";
}
function showCombo(comboId) {
	var oCombo = document.getElementById(comboId);
	var oComboStyle = oCombo.style;
	var oProcessingStyle = document.getElementById(comboId+"_processing").style;
	var oTextBoxStyle = document.getElementById(comboId+"_text").style;

//	focusObject = comboId;
//	oCombo.focus();
	oComboStyle.visibility = 'visible';
	oProcessingStyle.visibility = 'hidden';
	oTextBoxStyle.visibility = 'hidden';
	oComboStyle.visibility = 'visible';
}

ZDependantCombo.prototype.onClick = function() {
	if (isAjaxWorking())
	  return;
	  
	var oButtonStyle = document.getElementById(this.comboId+"_button").style;
	var oProcessingStyle = document.getElementById(this.comboId+"_processing").style;

	oButtonStyle.visibility = 'hidden';
	oProcessingStyle.visibility = 'visible';

	var ajax = new ZAjaxSubmit("do-comboAction",false);
	ajax.addUrlParameters(getUrlParameter("dg_combo_id",this.comboId));
	ajax.addUrlParameters(formDataRegisterToUrl(document.getElementById("mainform"),""));
	ajax.addUrlParameters(ajax.setAjaxContainer(this.comboId));
	ajax.setAjaxListenerPosScript(this.onAjaxPosScript);
	ajax.setAjaxCallback(this.onAjaxRecieve,genericErrorMessage);
	ajax.fetch();

}

ZDependantCombo.prototype.onAjaxPosScript = function() {
	
	var oCombo = document.getElementById(this.comboId);
	var oComboStyle = oCombo.style;
	var oTextBoxStyle = document.getElementById(comboId+"_text").style;

	oComboStyle.width = oTextBoxStyle.width;
	oComboStyle.position = 'absolute';
	oComboStyle.top = oTextBoxStyle.top;
	oComboStyle.left = oTextBoxStyle.left;
	oComboselectedIndex=-1;
//	if (oCombo && !(navigator.userAgent.indexOf("Firefox")!=-1)) {
//		oCombo.click();
//	}
}
ZDependantCombo.prototype.onAjaxRecieve = function(ajaxResponse) {
	this.comboId = ajaxContainer;
	showCombo(ajaxContainer);
	var oComboDiv = document.getElementById(this.comboId+"_div_main");
	oComboDiv.innerHTML = ajaxResponse;
}
ZDependantCombo.prototype.reset = function() {
		var oCombo = document.getElementById(this.comboId);
		var oComboStyle = oCombo.style;
		var oButtonStyle = document.getElementById(this.comboId+"_button").style;
		var oProcessingStyle = document.getElementById(this.comboId+"_processing").style;
		var oTextBoxStyle = document.getElementById(this.comboId+"_text").style;
	
		oCombo.selectedIndex=-1;
		oComboStyle.visibility = 'hidden';
		oProcessingStyle.visibility = 'hidden';
		oButtonStyle.visibility = 'visible';
		oTextBoxStyle.visibility = 'visible'
}

//function getRow(control){
//	var parent = control.parentNode;
//	if (!parent) return "";
//	if (parent.tagName != "TR") 
//			return getRow(parent);
//	return 'row_'+parent.rowIndex;
//}

///////////////////////////////////////////////////////////////////////////////
function winLovOpen(button,controlId, objectOwner, objectAction,tableProvider, refreshForm,  multiple, modal) {
	var listFilter = "";
	if (isAjaxWorking())
		  return;
	var oSource = document.getElementById(controlId);
	var url;
    var cfg ={
    		"dg_object_owner":objectOwner,
    		"dg_object_action":objectAction,
    		"dg_table_provider":tableProvider,
    		"dg_formLov_id":controlId,
    		"dg_formLov_filter":listFilter,
    		"dg_multiple":multiple
    } ;
	url = (modal?'modal':'')+'do-WinLovAction';
	var container = getModalQueueLast( )==-1?'modal_view_area_'+tableProvider:'modal_view_area_'+tableProvider+'__l'+getModalQueueLast( );
	goTo(button,url, false, '', null, container, '', '',  true, false, null, cfg, true)

//	url = 'do-WinLovAction';
//	
//	goTo(oSource,url, false, '', null, 'view_area_and_title', '', '',  true, true, null, cfg, true)


}


function formLovOpen(controlId, objectOwner, objectAction, tableProvider, refreshForm, mode, multiple,w,h) {
	var listFilter = "";
	//listFilter = prompt("Ingrese un filtro","");
	
	var oCombo = document.getElementById(controlId);
	oCombo.value="";
    height = h==0?405:h;
    width =  w==0?905:w;
    var size = ',width=' + width + ',height=' + height ;
    var posicion = ",left="+(screen.width - width)/ 2+",top="+(screen.height - height) / 2;
	var param = ',scrollbars=yes,resizable=yes,menubar=no,location=no,toolbar=no,status=no,directories=no,titlebar=no,'+size+posicion;
	var url;

	document.getElementById("mainform").dg_action.value="";
	if(mode) {
		url = 'do-FormLovAction?';
		url += getUrlParameterStr("dg_object_owner",objectOwner);
		url += getUrlParameterStr("dg_object_action",objectAction);
		url += getUrlParameterStr("dg_table_provider",tableProvider);
		url += getUrlParameterStr("dg_formLov_id",controlId);
		url += getUrlParameterStr("dg_formLov_filter",listFilter);
		url += getUrlParameterStr("dg_container_height",height);
		url += getUrlParameterStr("dg_container_width",width);
		url += getUrlParameterStr("dg_multiple",multiple);
		var filter = controlId.lastIndexOf("-")==-1?"":controlId.substring(0,controlId.lastIndexOf("-"));
		url += formDataRegisterToUrlFormFormLov(document.getElementById("mainform"),filter);//formDataToUrl(document.getElementById("mainform"), 1);
	    var popUp=window.open(url,'_blank',param);
    	popUp.opener=self;
    	popUp.refreshFormOnSelect=refreshForm;
	}
	else {
		url = 'do-FormLovIndoorAction?';
		url += getUrlParameterStr("dg_object_owner",objectOwner);
		url += getUrlParameterStr("dg_object_action",objectAction);
		url += getUrlParameterStr("dg_table_provider",tableProvider);
		url += getUrlParameterStr("dg_formLov_id",controlId);
		url += getUrlParameterStr("dg_formLov_filter",listFilter);
		url += getUrlParameterStr("dg_container_height",height);
		url += getUrlParameterStr("dg_container_width",width);
		url += getUrlParameterStr("dg_multiple",multiple);
		var filter = controlId.lastIndexOf("-")==-1?"":controlId.substring(0,controlId.lastIndexOf("-"));

		url += formDataRegisterToUrlFormFormLov(document.getElementById("mainform"),filter);//formDataToUrl(document.getElementById("mainform"), 1);
	//alert("form Lov url: "+url);
    	var winW=1024;
    	var winH=768;
    	if (parseInt(navigator.appVersion)>3) {
			 if (navigator.appName=="Netscape") {
			  winW = window.innerWidth;
			  winH = window.innerHeight;
			 }
			 if (navigator.appName.indexOf("Microsoft")!=-1) {
			  winW = document.body.offsetWidth;
			  winH = document.body.offsetHeight;
			 }
		}	
	   	//x=(winW - width)/ 2;
	   	x=20;
	   	y=(winH - height) / 2;
//	   	if (x<170) x=170;
		refreshFormOnSelect=refreshForm;
		tableProviderOnSelect=tableProvider;
	    openVirtualWindows(url,x,y,width,height);
	 }
}


function openVirtualWindows(url,x,y,w,h) {
	var virt = getElement("virtual_windows");
	var create = !virt;
/*	if (virt) { // podria servir para que no recargue si reclickea pero no me andaba y lo saque
		//alert (" src: "+virt.childNodes[0].src.substring(28)+ " url: "+url);
		if (virt.childNodes[0].src.indexOf(url)==-1) { // pseudo cache 
			virt.parentNode.removeChild(virt);
			create = true;
		}
	}*/
	if (virt) {
			virt.parentNode.removeChild(virt);
			create = true;
		}	
	if (create) {
		var elem = createElement("div",document.body);
		elem.innerHTML ="<div id='virtual_windows' style='position:absolute;z-index:1000;left:"+x+";top:"+y+";width:"+(w+100)+";height:"+(h+100)+";'><iframe src='"+url+"' width='"+w+"' height='"+h+"' scrolling='no' frameborder='0' allowTransparency='true' STYLE='background-color: transparent'><p>Su navegador no acepta iframes.</p></iframe>    </div>";
		virt = getElement("virtual_windows"); 
	}
	virt.style.display = 'none';
	virt.style.left = x ;
	virt.style.top = y;
	virt.style.display = 'block';
	
}

function addFormLovParameters(zUrl) {
	if (zUrl.indexOf('do-FormLovRefreshAction')>=0 || zUrl.indexOf('do-PartialRefreshForm')>=0) {
		if (zUrl.indexOf("?")<0)
			zUrl += "?";
		zUrl += getUrlParameterStr("dg_formLov_id",formLovParentControl);
	}
	return zUrl;
}

function onClickFormLovClear(controlId) {
	formLovClear(controlId);	
}

function formLovClear(controlId) {
	formLovUpdateValues(controlId, "", "");
	cleanControlDependencies(controlId);
	var FormLov = getRegisteredFormLov(controlId);
	if (FormLov) 
		FormLov.reset();
	$('#'+controlId).trigger("cleared");
	
}


function formLovRegister(controlId) {
	//alert(document.getElementById(controlId+"_div").innerHTML);
	var oTextBox = document.getElementById(controlId+"_text").style;
	var oDiv = document.getElementById(controlId+"_div");
	var oDivStyle = oDiv.style;
	var textBoxWidth = parseDimension(oTextBox.width);
	var oOpen = document.getElementById(controlId+"_open").style;
	var oClear = document.getElementById(controlId+"_clear").style;

	if (oDiv.parentNode.parentNode.id=="filter_pane") {
		oTextBox.width = 84;
	} else {
		oDivStyle.width = textBoxWidth + 40;
	}
	oOpen.height = oTextBox.height;
	oClear.height = oTextBox.height;
}

function formLovSelect(zActionOwnerProvider) {
	if (window && window.opener) {
		formLovSelectOuter(zActionOwnerProvider);
	}
	else {
		formLovSelectInner(zActionOwnerProvider);
	}
}


function formLovSelectOuter(zActionOwnerProvider) {
    var oProvider = getObjectProvider(zActionOwnerProvider);
  	key = oProvider.getCurrentSelectedKey();
  	description = oProvider.getCurrentSelectedDescription();
  	if (key==null) {
  	  return;
  	}
    window.opener.formLovUpdateValues(formLovParentControl, key, description);
    window.opener.cleanControlDependencies(formLovParentControl);
    if (window.refreshFormOnSelect) {
    
      window.opener.document.getElementById("mainform").dg_source_control_id.value=formLovParentControl;
      window.opener.goToRefreshForm('do-PartialRefreshForm',formLovParentControl, oProvider.objectProvider, null, getLastModal(), null);	
      executeOnChange(formLovParentControl);

    }
	window.close();
}

function formLovSelectInner(zActionOwnerProvider) {
    var oProvider = getObjectProvider(zActionOwnerProvider);
  	key = oProvider.getCurrentSelectedKey();
  	description = oProvider.getCurrentSelectedDescription();
 	if (key==null) {
  	  return;
  	}
    parent.formLovUpdateValues(formLovParentControl, key, description);
    parent.cleanControlDependencies(formLovParentControl);
    if (parent.refreshFormOnSelect) {
      parent.goToRefreshForm('do-PartialRefreshForm',formLovParentControl, parent.tableProviderOnSelect, null, getLastModal(), null);	
      parent.executeOnChange(formLovParentControl);
    }
 	parent.document.getElementById("virtual_windows").style.display="none";
 
}

function sleep(dur) {t=setTimeout("flow()",dur);} //starts flow control again after dur

function flow() {
	
};

function formLovSetParentControl(controlId) {
	formLovParentControl = controlId;
}

function formLovUpdateValues(controlId, id, description) {
//	alert("controlid="+controlId+", id="+id+", description="+description);
	document.getElementById(controlId+"_text").value = description;
	document.getElementById(controlId).value = id;
	
}

////////////////////////////////////////////////////////////////

function responsiveFormLovKeyPress(controlId, objectOwner,event) {
	adjustSessionID();
	var FormLov = getRegisteredResponsiveFormLov(controlId);
	if (FormLov)
		FormLov.keyPress(event);
}
function responsiveFillDataListJSON(controlId, search) {
	adjustSessionID();
	var FormLov = getRegisteredResponsiveFormLov(controlId);
	if (FormLov)
		return FormLov.fillDataListJSON(search);
}


function responsiveFormLovChange(controlId, objectOwner) {
	// disparar ajax...
	adjustSessionID();
	var FormLov = getRegisteredResponsiveFormLov(controlId);
	if (FormLov)
		FormLov.onChange();
}

function responsiveFormLovLostFocus(controlId, objectOwner) {
	// disparar ajax...
	adjustSessionID();
	var FormLov = getRegisteredResponsiveFormLov(controlId);
	if (FormLov)
		FormLov.onLostFocus();
}

function responsiveFormLovInput(controlId, objectOwner) {
	// disparar ajax...
	adjustSessionID();
	var FormLov = getRegisteredResponsiveFormLov(controlId);
	if (FormLov)
		FormLov.onInput();
}

function responsiveFormLovClickSelect(controlId, id, description) {
	adjustSessionID();
	var FormLov = getRegisteredResponsiveFormLov(controlId);
	if (FormLov) {
		FormLov.selectItem(id,description);
	}
}



////////////////////////////////////////////////////////////////

function formLovFocus(controlId, objectOwner) {
	adjustSessionID();
	var FormLov = getRegisteredFormLov(controlId);
	if (FormLov)
		FormLov.focus();
}

function formLovKeyPress(controlId, objectOwner,event) {
	adjustSessionID();
	var FormLov = getRegisteredFormLov(controlId);
	if (FormLov)
		FormLov.keyPress(event);
}

function formLovLostFocus(controlId, objectOwner) {
	adjustSessionID();
	var FormLov = getRegisteredFormLov(controlId);
	if (FormLov)
		FormLov.lostFocus();
}

function formLovChange(controlId, objectOwner) {
	// disparar ajax...
	adjustSessionID();
	var FormLov = getRegisteredFormLov(controlId);
	if (FormLov)
		FormLov.onChange();
}

function formLovKey(fullname,self,event) {
 	if(event.keyCode==13 || event.keyCode==32){ 
		formLovClickSelect(fullname,self);
		return false;
	}
	if(event.keyCode==27){ 
		formLovCancelCombo(fullname);
		return false;
	}
	return true;
}
	
function formLovClickSelect(fullname,self) {
	if (self.selectedIndex==-1)
		formLovCancelCombo(fullname);
	else 
		formLovSelectCombo(fullname,self.options.item(self.selectedIndex).value,self.options.item(self.selectedIndex).text);
}

function formLovSelectCombo(controlId, id, description) {
	var FormLov = getRegisteredFormLov(controlId);
	if (FormLov) {
		FormLov.selectItem(id,description);
	}
}

function formLovCancelCombo(controlId) {
	var FormLov = getRegisteredFormLov(controlId);
	if (FormLov) {
		FormLov.cancel();
	}
}

var regFormLovs = new Array();
var responsiveFormLovs = new Array();
var formLovParentControl = "";

function registerDependantFormLov(FormLovId,refreshForm, objectOwner, objectAction, multiple, provider) {
	if (getRegisteredFormLov(FormLovId)!=null) {
		cleanFormLov(FormLovId);
		return;
	}
     
	//alert("FormLov Registered: "+FormLovId);
	regFormLovs[regFormLovs.length] = new ZDependantFormLov(FormLovId,refreshForm, objectOwner, objectAction, multiple, provider);
}


function registerResponsiveFormLov(FormLovId,showkey,refreshForm, objectOwner, objectAction, multiple, provider, detectchange) {
	if (getRegisteredResponsiveFormLov(FormLovId)!=null) {
		cleanResponsiveFormLov(FormLovId);
		return;
	}
     
	responsiveFormLovs[responsiveFormLovs.length] = new ZResponsiveFormLov(FormLovId,showkey,refreshForm, objectOwner, objectAction, multiple, provider, detectchange);
}
function cleanResponsiveFormLov(FormLovId) {
	getRegisteredResponsiveFormLov(FormLovId).reset(true);
}
function cleanFormLov(FormLovId) {
	getRegisteredFormLov(FormLovId).reset(true);
}
function refreshResponsiveFormLov(FormLovId) {
	getRegisteredResponsiveFormLov(FormLovId).refreshForm();
}


function getRegisteredFormLov(FormLovId) {
//  alert("getRegisteredFormLov length: "+regFormLovs.length);
  for (i=0;i<regFormLovs.length;i++) {
    currAss = regFormLovs[i];
  	//alert("testing "+i+"-> "+currAss.getId());
    if (currAss!=null && currAss.getId()==FormLovId) {
      //alert("found FormLov");
	  return currAss;
	}
  }
  //alert("Error, can't find Registered FormLov: "+FormLovId);
  return null;
}

function getRegisteredResponsiveFormLov(FormLovId) {
//  alert("getRegisteredFormLov length: "+regFormLovs.length);
  for (i=0;i<responsiveFormLovs.length;i++) {
    currAss = responsiveFormLovs[i];
  	//alert("testing "+i+"-> "+currAss.getId());
    if (currAss!=null && currAss.getId()==FormLovId) {
      //alert("found FormLov");
	  return currAss;
	}
  }
  //alert("Error, can't find Registered FormLov: "+FormLovId);
  return null;
}


function cleanResponsiveFormLovRegistry() {
	responsiveFormLovs = new Array();
}
function cleanFormLovRegistry() {
	regFormLovs = new Array();
}


ZDependantFormLov = function(FormLovId,refreshForm, objectOwner, objectAction,multiple, provider) {
	this.objectOwner =objectOwner;
	this.objectAction=objectAction;
	this.multiple=multiple;
	this.FormLovId = FormLovId;
	this.refreshFormOnSelect=refreshForm;
	this.provider = provider;
	this.reset(false);
}

// primitivas para visibilizar o no componentes
ZDependantFormLov.prototype.removeCombo = function() {
	var oCombo = document.getElementById(this.FormLovId+"_combo");
	var oComboDiv = document.getElementById(this.FormLovId+"_combo_div");
 	if (navigator.appName.indexOf("Microsoft")!=-1)
 		oCombo.releaseCapture();
	oComboDiv.style.display = 'none';
}

ZDependantFormLov.prototype.showCombo = function() {
		var oCombo = document.getElementById(this.FormLovId+"_combo");
		var oComboDiv = document.getElementById(this.FormLovId+"_combo_div");
		var oTextBox = document.getElementById(this.FormLovId+"_text");
		if (navigator.appName.indexOf("Microsoft")!=-1)
			oCombo.setCapture();
		oComboDiv.style.display = 'block';
		oCombo.style.width=oTextBox.style.width;
		oCombo.focus();
		oCombo.focus();

}

ZDependantFormLov.prototype.showSearchButtons = function() {
	var oButtonStyle = document.getElementById(this.FormLovId+"_combo_button").style;
	oButtonStyle.visibility = 'visible';
}

ZDependantFormLov.prototype.showProcButtons = function() {
	var oProcessingStyle = document.getElementById(this.FormLovId+"_combo_processing").style;
	oProcessingStyle.visibility = 'visible';
}

ZDependantFormLov.prototype.removeSearchButtons = function() {
	var oButtonStyle = document.getElementById(this.FormLovId+"_combo_button").style;
	oButtonStyle.visibility = 'hidden';
}

ZDependantFormLov.prototype.removeProcButtons = function() {
	var oProcessingStyle = document.getElementById(this.FormLovId+"_combo_processing").style;
	oProcessingStyle.visibility = 'hidden';
}
ZDependantFormLov.prototype.reset = function() {
	this.reset(true);
}
ZDependantFormLov.prototype.reset = function(refresh) {
	formLovParentControl=this.FormLovId;
		var oTextBox = document.getElementById(this.FormLovId+"_text");
		var oProcessingStyle = document.getElementById(this.FormLovId+"_combo_processing").style;
		var oButtonStyle = document.getElementById(this.FormLovId+"_combo_button").style;
		var oId = document.getElementById(this.FormLovId);
		var oTextBoxStyle = oTextBox.style;
		var textBoxWidth = parseDimension(oTextBoxStyle.width);
		formLovParentControl=this.FormLovId;
		this.userInput = "";
		this.userInputVisible=0;
		this.ShowingButtons = 4;
		this.oldValue = oTextBox.value;
		this.oldId = oId.value;
		this.oldSize = textBoxWidth;

		oButtonStyle.position = 'absolute';
		oButtonStyle.top=0;
		oButtonStyle.left=this.oldSize-20;
		oButtonStyle.width=18;
		oButtonStyle.height=18;

		oProcessingStyle.position = 'absolute';
		oProcessingStyle.top=0;
		oProcessingStyle.left=this.oldSize-20;
		oProcessingStyle.width=18;
		oProcessingStyle.height=18;

		this.removeButtons();
		this.removeCombo();
		this.userInputVisible=0;

	
	if (this.refreshFormOnSelect && refresh)
		  window.goToRefreshForm('do-PartialRefreshForm',formLovParentControl, this.provider, null, getLastModal(), null);

}


/// los tres estados en que pueden estar los botones
ZDependantFormLov.prototype.searchButtons = function() {
	if (this.ShowingButtons==2) return;
	this.ShowingButtons = 2;
	this.showSearchButtons();
	this.removeProcButtons();
	var oTextBoxStyle = document.getElementById(this.FormLovId+"_text").style;
	oTextBoxStyle.width = this.oldSize-20;
}

ZDependantFormLov.prototype.processingButtons = function() {
	if (this.ShowingButtons==1) return;
	this.ShowingButtons = 1;
	this.removeSearchButtons();
	this.showProcButtons();
	var oTextBoxStyle = document.getElementById(this.FormLovId+"_text").style;
	oTextBoxStyle.width = this.oldSize-20;
}
	
ZDependantFormLov.prototype.removeButtons = function() {
	if (this.ShowingButtons==0) return;
	this.ShowingButtons = 0;
	this.removeSearchButtons();
	this.removeProcButtons();
	var oTextBoxStyle = document.getElementById(this.FormLovId+"_text").style;
	oTextBoxStyle.width = this.oldSize;
}

/// manejo del foco
ZDependantFormLov.prototype.cancel = function() {

	this.removeCombo();
	if (this.userInputVisible==1) return; 
	this.removeButtons();
	var oTextBox = document.getElementById(this.FormLovId+"_text");
	// la idea es que si no hizo nada vuelve todo como estaba
	this.userInput = oTextBox.value; 
	oTextBox.value = this.oldValue;
}

/// manejo del foco
ZDependantFormLov.prototype.lostFocus = function() {
	if (this.userInputVisible==1) return; 
	this.removeButtons();
	var oTextBox = document.getElementById(this.FormLovId+"_text");
	// la idea es que si no hizo nada vuelve todo como estaba
	this.userInput = oTextBox.value; 
	oTextBox.value = this.oldValue;
}

ZDependantFormLov.prototype.focus = function() {
	var oTextBox = document.getElementById(this.FormLovId+"_text");
	var oIdBox = document.getElementById(this.FormLovId);
	// la idea es que al clickear el editbox muestre el id y espere uno ingresado por el usuario
	if (this.userInputVisible==1) return; 
//	oTextBox.value = oIdBox.value;
	oTextBox.select();
}

// evento de presion de una tecla
ZDependantFormLov.prototype.keyPress = function(event) {
	if(event.keyCode==13 && this.userInputVisible==1) {
		this.onClick();
		return;
	}
	if (this.userInputVisible==0) {
		this.focus();
	}
	this.searchButtons();
	this.userInputVisible=1;
}


ZDependantFormLov.prototype.getId = function() {
	return this.FormLovId;
}
function ltrim(s) {
   return s.replace(/^\s+/, "");
}

function rtrim(s) {
   return s.replace(/\s+$/, "");
}

function trim(s) {
   return rtrim(ltrim(s));
}

ZDependantFormLov.prototype.onChange = function() {
	this.onClick();
}

ZDependantFormLov.prototype.onClick = function() {
//	if (isAjaxWorking())
//	  return;

	if (this.userInputVisible==0) 
		return;
	this.userInputVisible=0;
	  
	var oID = document.getElementById(this.FormLovId);
	var oButtonStyle = document.getElementById(this.FormLovId+"_combo_button").style;
	var oProcessingStyle = document.getElementById(this.FormLovId+"_combo_processing").style;
	var oTextBox = document.getElementById(this.FormLovId+"_text");

	if (trim(oTextBox.value)=="") return;
	if (oID.value == oTextBox.value) {
		oID.value=this.oldId;
		document.getElementById(this.FormLovId+"_text").value = this.oldValue;
		this.removeCombo();
		this.removeButtons();
		return;
	}

	// send message
//	showWorkingPane('view_title');
	this.processingButtons();
//	oID.value = oTextBox.value;

	var ajax = new ZAjaxSubmit("do-comboFormLovAction",false);
	ajax.addUrlParameters(getUrlParameter("dg_comboFormLov_id",this.FormLovId));
	ajax.addUrlParameters(getUrlParameter("dg_object_owner",this.objectOwner));
	ajax.addUrlParameters(getUrlParameter("dg_object_action",this.objectAction));
	ajax.addUrlParameters(getUrlParameter("dg_table_provider",this.provider));
	ajax.addUrlParameters(getUrlParameter("dg_source_control_id",this.FormLovId));
	var filter = this.FormLovId.lastIndexOf("-")==-1?"":this.FormLovId.substring(0,this.FormLovId.lastIndexOf("-"));
	ajax.addUrlParameters(formDataRegisterToUrl(document.getElementById("mainform"),filter));
	
//	ajax.addUrlParameters(formDataRegisterToUrl(document.getElementById("mainform"),""));//formDataToUrl(document.getElementById("mainform"), 1));
	ajax.addUrlParameters(ajax.setAjaxContainer(this.FormLovId));
	ajax.setAjaxListenerPosScript(this.onAjaxPosScript);
	ajax.setAjaxListenerPosAjax(this.onAjaxPosAjax);
	ajax.setAjaxCallback(this.onAjaxRecieve,genericErrorMessage);
	ajax.fetch(this.onAjaxRecieve,genericErrorMessage);

}

ZDependantFormLov.prototype.onAjaxPosScript = function() {
}

ZDependantFormLov.prototype.onAjaxPosAjax = function() {
	endWorkingPane();


	var FormLov = getRegisteredFormLov(this.FormLovId);
	if (FormLov)
		FormLov.onProcess();

}


ZDependantFormLov.prototype.onProcess = function() {
	var oFormLovCombo = document.getElementById(this.FormLovId+"_combo");
	//alert("combo: "+this.FormLovId+"_combo ("+oFormLovCombo.options.length+")");
	var oID = document.getElementById(this.FormLovId);
	var oTextBox = document.getElementById(this.FormLovId+"_text")

	if (oFormLovCombo==null || oFormLovCombo.options.length==0) {
		alert("No hay coincidencias");
		oID.value=this.oldId;
		document.getElementById(this.FormLovId+"_text").value = this.oldValue;
		this.removeCombo();
		this.userInputVisible=0;
	}
	else if (oFormLovCombo.options.length==1) {
		// hay uno, lo elijo sin mas preambulos
//		oTextBox.value = oFormLovCombo.options.item(0).text;
//		oID.value = oFormLovCombo.options.item(0).value;
		this.selectItem(oFormLovCombo.options.item(0).value,oFormLovCombo.options.item(0).text);
		
		this.removeCombo();
//		if (this.refreshFormOnSelect)
//		  window.goToRefreshForm('do-PartialRefreshForm',formLovParentControl, this.provider, null, 'view_area_and_title', null);
	}
	else {
		// hay muchos, restauro ls default
		oID.value=this.oldId;
		
		this.userInputVisible=0;
		// abro el combo
		this.showCombo();
	}
	this.removeButtons();
	
}

ZDependantFormLov.prototype.onAjaxRecieve = function(ajaxResponse) {
	//alert("Response:"+ajaxResponse);
	this.FormLovId = ajaxContainer;
		
	var oFormLovDiv = document.getElementById(this.FormLovId+"_combo_div");
	oFormLovDiv.innerHTML = ajaxResponse;


}

ZDependantFormLov.prototype.selectItem = function(id,description) {
		var oID = document.getElementById(this.FormLovId);
		formLovParentControl=this.FormLovId;
		formLovUpdateValues(this.FormLovId, id, description);
		this.oldValue = description;
		this.userInput = "";
		this.oldId=oID.value;
		this.userInputVisible=0;
	    cleanControlDependencies(this.FormLovId);
    	if (this.refreshFormOnSelect) {
	      	document.getElementById("mainform").dg_source_control_id.value=this.FormLovId;
      		goToRefreshForm('do-PartialRefreshForm',formLovParentControl, this.provider, null, getLastModal(), null);	
            executeOnChange(formLovParentControl);
    	}
		this.removeButtons();
		this.removeCombo();
}


//////////////////////////////////
ZResponsiveFormLov = function(FormLovId,showkey,refreshForm, objectOwner, objectAction,multiple, provider,zdetectchange) {
	this.objectOwner =objectOwner;
	this.objectAction=objectAction;
	this.multiple=multiple;
	this.showkey=showkey;
	this.FormLovId = FormLovId;
	this.refreshFormOnSelect=refreshForm;
	this.provider = provider;
	this.lastSearch = "";
	this.expectedSearch = "";
	this.sizeSearch = 0;
	this.maxSearch = 50;
	this.detectchange =zdetectchange;
	this.reset(false);
}

ZResponsiveFormLov.prototype.reset = function() {
	this.reset(true);
}

ZResponsiveFormLov.prototype.reset = function(refresh) {
	if (!refresh) return;
	this.refreshForm();
}

ZResponsiveFormLov.prototype.refreshForm = function() {
	if (this.refreshFormOnSelect) {
     	document.getElementById("mainform").dg_source_control_id.value=this.FormLovId;
     	if (this.FormLovId.indexOf('filter_pane')!=-1)
     		goToRefreshForm('do-WinListRefreshAction',this.FormLovId, this.provider, this.objectOwner, 'win_list_complete_'+this.provider, this.objectAction);	
     	else
     		goToRefreshForm('do-PartialRefreshForm',this.FormLovId, this.provider, null, getLastModal(), null);	
	} else
		nextFocusH($("#"+this.FormLovId)[0],+1,true);

    executeOnChange(this.FormLovId);
    if (this.detectchange)
    	setChangeInputs(true);
}
ZResponsiveFormLov.prototype.getId = function() {
	return this.FormLovId;
}
ZResponsiveFormLov.prototype.keyPress = function(event) {
	var oTextBox = document.getElementById(this.FormLovId+"_text");
	this.fillDataList(oTextBox.value);
}

ZResponsiveFormLov.prototype.onChange = function() {
}

ZResponsiveFormLov.prototype.onInput = function() {
	
}
ZResponsiveFormLov.prototype.cleanDataList = function(search) {
	var oFormLovData= document.getElementById(this.FormLovId+"_datalist");
	oFormLovData.innerHtml="";

}
var mutex=0;
ZResponsiveFormLov.prototype.fillDataList = function(search) {
	if (search==null||trim(search)=="") {
		this.cleanDataList();
		return; // descarto
	}
	if (this.multiple) {
		var pos = search.lastIndexOf(";") 
		if (pos!=-1) {
			search=search.substring(pos+1);
		}
		if (search=="") {
			this.expectedSearch='';
			return;
		}
	}
	if (this.expectedSearch==search) return; // esta bien cargado
	if (this.expectedSearch!=null && this.expectedSearch!='' &&search.indexOf(this.expectedSearch)==0 && this.maxSearch>this.sizeSearch) return; // esta bien cargado
		
	if (mutex==1) return;
	mutex=1;
	this.expectedSearch = search;
	focusObject = null;
	var ajax = new ZAjaxSubmit("do-comboResponsiveFormLovAction",true);
	ajax.addUrlParameters(getUrlParameter("dg_comboFormLov_id",this.FormLovId));
	ajax.addUrlParameters(getUrlParameter("dg_object_owner",this.objectOwner));
	ajax.addUrlParameters(getUrlParameter("dg_object_action",this.objectAction));
	ajax.addUrlParameters(getUrlParameter("dg_table_provider",this.provider));
	ajax.addUrlParameters(getUrlParameter("dg_source_control_id",this.FormLovId));
	var filter = this.FormLovId.lastIndexOf("-")==-1?"":this.FormLovId.substring(0,this.FormLovId.lastIndexOf("-"));
	ajax.addUrlParameters(formDataRegisterToUrl(document.getElementById("mainform"),filter));
	
	ajax.addUrlParameters(ajax.setAjaxContainer(this.FormLovId));
	ajax.setAjaxListenerPosScript(this.onAjaxPosScript);
	ajax.setAjaxListenerPosAjax(this.onAjaxPosAjax);
	ajax.setAjaxCallback(this.onAjaxRecieve,genericErrorMessage);
	ajax.fetch(this.onAjaxRecieve,genericErrorMessage);
	mutex=0;
}

ZResponsiveFormLov.prototype.fillDataListJSON = function(search) {
	var cfg = {ajaxContainer: this.FormLovId,
		dg_dictionary:  sessionStorage.getItem('dictionary'),
		subsession: document.navform.subsession.value,
		src_uri: document.navform.src_uri.value,
		src_sbmt: document.navform.src_sbmt.value,
		dg_comboFormLov_id: this.FormLovId,
		dg_object_owner: this.objectOwner,
		dg_object_action: this.objectAction,
		dg_table_provider: this.provider, 
		dg_source_control_id:this.FormLovId, 
		dg_partial_info:'S',
		search: search,
        type: 'public'
		};
		pushArray(this.FormLovId+"_text",search,cfg);

		var filter = this.FormLovId.lastIndexOf("-")==-1?"":this.FormLovId.substring(0,this.FormLovId.lastIndexOf("-"));
		cfg=collect(cfg,addRegisterToURLFromList(""));
		cfg=collect(cfg,addRegisterToUrl(filter,1000));
		
//		cfg=collect(cfg,formDataRegisterToUrlFormFormLov(document.getElementById("mainform"),filter));
		
	return cfg;
}

ZResponsiveFormLov.prototype.onAjaxPosScript = function() {
}

ZResponsiveFormLov.prototype.onAjaxPosAjax = function() {
	var FormLov = getRegisteredResponsiveFormLov(this.FormLovId);
	if (FormLov)
		FormLov.onProcess();

}


ZResponsiveFormLov.prototype.onProcess = function() {
	var oFormLovDataList = document.getElementById(this.FormLovId+"_datalist");

	this.lastSearch=oFormLovDataList.dataset.search;
	this.sizeSearch=oFormLovDataList.options.length;


}

ZResponsiveFormLov.prototype.onLostFocus = function() {
	this.selectItem(null,null,1);
}

ZResponsiveFormLov.prototype.onAjaxRecieve = function(ajaxResponse) {
	//alert("Response:"+ajaxResponse);
	this.FormLovId = ajaxContainer;

	var FormLov = getRegisteredResponsiveFormLov(this.FormLovId);
	if (FormLov) {
		if (ajaxResponse.indexOf("data-search=\""+FormLov.expectedSearch+"\"")==-1) return; // descarto por antigua

		var oFormLovDiv = document.getElementById(this.FormLovId+"_container");
		oFormLovDiv.innerHTML = ajaxResponse;
	}

	


}

ZResponsiveFormLov.prototype.selectItem = function(id,description,onchange) {
	var shownVal=description;
	var value2send=id;
	var input = document.getElementById(this.FormLovId+"_text");
	var lista = document.getElementById(this.FormLovId+"_datalist");
	if (lista.options.length==0) return;
	var options = lista.options;
	if (description==null) {
		shownVal = input.value;
		value2send=null;
	    for(var i = 0; i < options.length; i++) {
	        var option = options[i];

	        if(option.value === shownVal) {
	            value2send=option.dataset.value;
	            break;
	        }
	    }
		if (value2send==null) {
		    for(var i = 0; i < options.length; i++) {
		        var option = options[i];
		        if(option.value.toLowerCase() == shownVal.toLowerCase()) {
		            value2send=option.dataset.value;
		            break;
		        }
		    }			
		}
	}
	
	if (onchange) {
		var oFormLovDataList = document.getElementById(this.FormLovId+"_datalist");
		if (oFormLovDataList.options.length==1 && oFormLovDataList.options.item(0).text.indexOf(shownVal)) {
			this.selectItem(oFormLovDataList.options.item(0).value,oFormLovDataList.options.item(0).text,0);
		}
		else if (value2send==null) {
			value2send=='';
			shownVal='';
		}
	}
		
	formLovParentControl=this.FormLovId;
	document.getElementById(this.FormLovId+"_text").value = shownVal;
	document.getElementById(this.FormLovId).value = value2send;

    cleanControlDependencies(this.FormLovId);
    this.refreshForm();

}



