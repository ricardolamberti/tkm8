var ajaxSemaphore = false;
var disableado = null;
var startTime=0;
var downloadTime=0;
var stadistics="";
var reqCount=0;
var lastOpBlock = false;

function getSemaphore(block) {
	if (lastOpBlock) {
		waitFor(function () { return !ajaxSemaphore } , function() {})
//		while (ajaxSemaphore) {}
		if (block) 	ajaxSemaphore = true;
		lastOpBlock=block;
		return;
	}
	if (block) 	ajaxSemaphore = true;
	lastOpBlock=block;
//	if (ajaxXmlHttp) 
//		ajaxXmlHttp.abort();
	endWorkingPane();
	//alert('getSemaphore');
}


function controlReclick(obj) {
   disablear();
   if (obj.id && obj.id=="") return;
   disableado = obj.id;
   if( obj!=null) {
	//	alert("-disablear :"+obj.id);
	   console.log("-disablear :"+obj.id);
	   if (obj.tagName && obj.tagName=='A') {
		   obj.style.pointerEvents="none";
		   obj.style.cursor="default";
	   } else
		   obj.disabled=true;
   }
}

function disablear() {
	lastOpBlock = false;
	if (disableado=="")
		disableado=null;
	if( disableado!=null ) {
	   var obj = document.getElementById(disableado)
	   if (obj) {
		   if (obj.tagName && obj.tagName=='A') {
			   obj.style.pointerEvents="auto";
			   obj.style.cursor="pointer";
		   } else
			   obj.disabled=false;
		   console.log("-desdisableando :"+obj.id);
   //    	alert("-desdisableando :"+obj.id);
       }
//	   disableado = null;
	}
}
function releaseSemaphore() {
	ajaxSemaphore = false;
	disablear();
//	document.onkeydown = killBackSpace;
//	document.onkeypress = killBackSpace;

	//alert('releaseSemaphore');
}
function isAjaxWorking() {
	return ajaxSemaphore;
}


ZAjaxSubmit = function(zUrl,cancel) {
//	alert("ajax zUrl: "+zUrl);
	getSemaphore(!cancel);	
	this.UrlParameters = new Array();
	this.Url = zUrl;
	this.ajaxListenerPosScript = ajaxPosScript;
	this.ajaxListenerPosAjax = ajaxPosAjax;
	this.ajaxListener = null;
	this.ajaxErrorListener = null;
	this.ajaxContainer = '';
}

ZAjaxSubmit.prototype.setAjaxContainer = function(container) {
	this.ajaxContainer = container;
	var vector = new Array();
	pushArray("ajaxContainer",container,vector);
	return vector;
}

ZAjaxSubmit.prototype.addStadistics = function() {
	this.addUrlParameters(getUrlParameter("dg_stadistics",stadistics));
	startTime= new Date().getTime();
	downloadTime= 0;
}



ZAjaxSubmit.prototype.setAjaxCallback = function(callBack,callBackError) {
	this.ajaxListener = callBack;
	this.ajaxErrorListener = callBackError;
}

ZAjaxSubmit.prototype.setAjaxListenerPosScript = function(listener) {
	this.ajaxListenerPosScript = listener;
}

ZAjaxSubmit.prototype.setAjaxListenerPosAjax = function(listener) {
	this.ajaxListenerPosAjax = listener;
}

ZAjaxSubmit.prototype.fetch = function() {
	fetch(true);
}

ZAjaxSubmit.prototype.getFinalUrl = function() {
	return this.Url + ((this.Url.indexOf("?")<0)?"?":"") +"ajaxContainer="+this.ajaxContainer+"&";
}

ZAjaxSubmit.prototype.getFinalUrlWithParameters = function() {
	return this.getFinalUrl() + ((this.getFinalUrl().indexOf("?")<0)?"?":"") +jQuery.param(this.getFinalParams());
}


ZAjaxSubmit.prototype.isHistoriable = function() {
	return (this.getFinalUrl().indexOf('do-Back')==-1 &&  getAjaxContainer(this.getFinalUrl())=='view_area_and_title');
}

function initializeChangesByUser(queue) {
    $("body").on('input','input,select,textarea,.select2-selection--single',function(event){
  	  queue[queue.length]=event;
   });
}
function preserveChangesByUser(queue,values) {
	 $("body").off('input','input,select,textarea,.select2-selection--single');
    	queue.forEach(function (item, index) { 
    		var obj = {};
    		obj[ item.target.id] =  $('#'+item.target.id).val();
    		values.push(obj);
  	 }
   );
}
function restoreChangesByUser(queue,values) {
  	values.forEach(function (item, index) { 
  		for (var i in item) {
	     		$('#'+i).val(item[i]);
  		}
	 }
  );

}
var idRequest=0;
ZAjaxSubmit.prototype.fetch = function(asinc) {
	var queue = new Array();
	detenerChecker();
	this.addStadistics();
	var that = this;
	console.log("navegate: "+this.getFinalUrl());
	console.log("  params: "+this.getFinalParams()["dg_action"]);
	var codeRequest=++idRequest;
	this.addUrlParameters(getUrlParameter("vrs",codeRequest));
	if (this.isHistoriable())
		history.pushState(this.getFinalUrl(), null, window.location.href );

	 var objAjax = $.ajax( {
         "type":     "post",
         "url":      this.getFinalUrl(),
         "data":     this.getFinalParams(),
         "dataType": "html",
         "cache":    false,
         "success":  function ( xhr, ajaxOptions, objAjax) {
    	    var values = new Array();
    	    preserveChangesByUser(queue,values);
  	     	ajaxRecieved(xhr, ajaxOptions, objAjax, this.url,that)
  	     	restoreChangesByUser(queue,values);
  	    	queue=null;
         },
     	"error":  function ( xhr, ajaxOptions, thrownError) {
     		ajaxError(xhr, ajaxOptions, thrownError, this.url);
  	    	queue=null;
    	 },
       "beforeSend": function (xhr) {
    	   initializeChangesByUser(queue);
      	   if (downloadTime==0)
      			downloadTime= new Date().getTime();
		 } 
     } );

}

ZAjaxSubmit.prototype.deleteUrlParameters = function(param) {
	if (param==null) return;
	delete this.UrlParameters[param];
}

function collect() {
	  var ret = {};
	  var len = arguments.length;
	  for (var i=0; i<len; i++) {
	    for (p in arguments[i]) {
	      if (arguments[i].hasOwnProperty(p)) {
	        ret[p] = arguments[i][p];
	      }
	    }
	  }
	  return ret;
	}

ZAjaxSubmit.prototype.addUrlParameters = function(param) {
	if (param==null) return;
	
	this.UrlParameters = collect(this.UrlParameters,param);
}
ZAjaxSubmit.prototype.getFinalParams = function() {
	return this.UrlParameters;
}

function get_url_param(url, name)
{  
	name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");  
	var regexS = "[\\?&]"+name+"=([^&#]*)";  
	var regex = new RegExp( regexS );  
	var results = regex.exec( url );  //window.location.href
	if( results == null )    return "";  
	else return results[1];
}

function getAjaxContainer(zUrl) {
	var param = get_url_param(zUrl,"ajaxContainer");
	return param;
}

function waitPendingNow() {
	lastContainer='view_area_and_title';
	efectiveShowWorkingPane("(ICONO)");	
}

var pendingSend=0;
function waitPendingUpdates() {
	var stop=false;
	if (pendingSend>0) {
		stop=true;
		lastContainer='view_area_and_title';
		efectiveShowWorkingPane("(NOMESSAGE)");
	}
	waitFor(function () { return pendingSend==0 } , function() {})
	if (stop)
		endWorkingPane();
	
}

function waitFor(condition, callback) {
    if(!condition()) {
        window.setTimeout(waitFor.bind(null, condition, callback), 100); /* this checks the flag every 100 milliseconds*/
    } else {
       callback();
    }
}
function updateServerField(object, event, datatable) {
	var now = new Date(); 
	var field = $(object).data('colname');
	var datetime = now.getFullYear()+'_'+(now.getMonth()+1)+'_'+now.getDate(); 
	datetime += ' '+now.getHours()+':'+now.getMinutes()+':'+now.getSeconds(); 
	
	var realRow = $(object).closest("tr");
	var realCell = $(object).closest("td");

	var providerParent = realRow.data('provider_parent');
	var provider = realRow.data('provider'); 
	var value=null;
	var row = datatable.row( realRow );
    var oldValue = row.data();
    
	if ($(object).prop("type")=='checkbox')
		value = $(object)[0].checked?"S":"N"; 
	else
		value = $(object).val(); 
	setModalChanges(true);
	pendingSend++;
 	
	var objAjax = $.ajax( {
         "type":     "post",
         "url":      "do-updateserverfield",
         "data":     { "dg_dictionary":  sessionStorage.getItem('dictionary'),"vrs": ++idRequest, "obj_provider_parent": providerParent, "obj_provider": provider, "field":field, "value": value, "lastupdate": datetime , "subsession":getURLParameters("subsession")},
         "dataType": "json",
         "cache":    false,
         "tryCount" : 0,
         "retryLimit" : 10,
         "success":  function ( json) {
        	 if (json.results==1)
        		 updateServerFieldResponse(object, datatable,json,field);
        	 else {
        		 if (json.message!='')
        			 genericErrorMessage("zzzzerrzzzz"+json.message);
                 reverseServerFieldResponse(object, datatable,field);
        	 }
         },
     	"error":  function (xhr, textStatus, errorThrown ) {
            if (textStatus == 'timeout') {
                this.tryCount++;
                if (this.tryCount <= this.retryLimit) {
                    console.log ('try again (try:'+tryCount+' of '+retryLimit+')');
                    $.ajax(this);
                    return;
                }            
           		genericErrorMessage("zzzzerrzzzzLost Connection");
            } else if (xhr.status == 500) {
           		genericErrorMessage("zzzzerrzzzzLost Connection");
            } else {
           		genericErrorMessage("zzzzerrzzzzLost Connection");
            }  
            reverseServerFieldResponse(object, datatable,field);

         }
     
     } );
}
function reverseServerFieldResponse(object, datatable,field) {
	var realRow = $(object).closest("tr");
	var realCell = $(object).closest("td");
    var cell = datatable.cell( realCell );
    var row = datatable.row( realRow );
    var data = row.data();
	var value=null;
	if ($(object).prop("type")=='checkbox')
		$(object).attr("checked",data[field]=='S'); 
	else
		$(object).val(data[field]); 
   	pendingSend--;
    
    row.invalidate().draw('page');
}
function updateServerFieldResponse(object, datatable,json,field) {
	var realRow = $(object).closest("tr");
	var realCell = $(object).closest("td");
    var cell = datatable.cell( realCell );
    var row = datatable.row( realRow );
    var data = row.data();
	var value=null;
	if ($(object).prop("type")=='checkbox')
		value = $(object)[0].checked?"S":"N"; 
	else
		value = $(object).val(); 
    data[field]=value;
   	pendingSend--;
   	if (pendingSend==0)
   		row.invalidate().draw('page');
   	else
   		row.invalidate();
}

function sendWait(waitrequest, cancel) {
	adjustSessionID();
	var now = new Date(); 
	var datetime = now.getFullYear()+'_'+(now.getMonth()+1)+'_'+now.getDate(); 
	datetime += ' '+now.getHours()+':'+now.getMinutes()+':'+now.getSeconds(); 
	 var objAjax = $.ajax( {
         "type":     "post",
         "url":      "do-WaitingAction",
         "data":     { "wait_request": waitrequest, "lastupdate": datetime,"cancel_wait":cancel , "subsession":getURLParameters("subsession")},
         "dataType": "html",
         "cache":    false,
         "success":  function ( msg, ajaxOptions, objAjax) {
        	 waitMessage(msg)
         },
     	"error":  function ( xhr, ajaxOptions, thrownError) {
     		endWorkingPaneContainer(wp);
           },
     
     } );
}


function sendEcho(action) {
	adjustSessionID();
	   var ajax = new ZAjaxSubmit("do-saveHelp",false);
		ajax.setAjaxCallback(nullFetch,genericErrorMessage);
	var now = new Date(); 
	var datetime = now.getFullYear()+'_'+(now.getMonth()+1)+'_'+now.getDate(); 
	datetime += ' '+now.getHours()+':'+now.getMinutes()+':'+now.getSeconds(); 
	 var objAjax = $.ajax( {
         "type":     "post",
         "url":      "do-echoAction",
         "data":     {   "subsession":getURLParameters("subsession")},
         "dataType": "html",
         "cache":    false,
         "success":  function ( msg, ajaxOptions, objAjax) {
        	 if (msg.indexOf('ok;')!=-1) {
        		 action();
        	 } else {
        		 isSpecialResponse(msg,ajax);
       		 
        	 }
         },
     	"error":  function ( xhr, ajaxOptions, thrownError) {
     		genericErrorMessage("zzzzerrzzzzLost Connection");
           },
     
     } );
}



var sendWaitTimer=null;
function waitMessage(response) {
	if (sendWaitTimer==null) return;
	showWorkingPaneIcon(response);
	
	ejecuteAjaxScripts(response);
	
	if (lastContainer==null) return;
	if (sendWaitTimer!=null)
		clearTimeout(sendWaitTimer);
	sendWaitTimer= setTimeout(sendWait,1000,last_idrequest,false);
}
var last_idrequest=null;
var wp='';
var lastContainer=null;
function showWorkingPane(container) {
	showWorkingPaneIn(container,false);
}

function showWorkingPaneIn(container,now) {
	if (wp!='') return;
	$("body").css("cursor", "progress");
	wp=container;
	lastContainer=container;
	last_idrequest=idRequest;
	if (now) {
		showWorkingPaneIcon("(NOMESSAGE)(ICONO)");	
	} else{
		if (sendWaitTimer!=null)
			clearTimeout(sendWaitTimer);
		sendWaitTimer= setTimeout(sendWait,500,last_idrequest,false);
	}
}
function showWorkingPaneIcon(response) {
	if (lastContainer==null) return;
	efectiveShowWorkingPane(response);

}
function efectiveShowWorkingPane(messageConfig) {
    var obj = lastContainer;
    
	var reenter = $("#"+obj).hasClass("processing");
 	var nomessage = messageConfig.indexOf("(NOMESSAGE)")!=-1;
	var cancel = messageConfig.indexOf("(CANCEL)")!=-1;
	var progress =  messageConfig.indexOf("(PROGRESS)")!=-1;
	var hasIcono =  messageConfig.indexOf("(ICONO)")!=-1;
	var message =  messageConfig.substring(messageConfig.indexOf("{")+1,messageConfig.indexOf("}",messageConfig.indexOf("{")+1));
	var req =  messageConfig.substring(messageConfig.indexOf("[REQ:")+5,messageConfig.indexOf("]",messageConfig.indexOf("[REQ:")+5));

	var porcentaje=-1;
	var total=-1;
	var icono = "fa fa-spinner fa-spin fa-3x fa-fw";
	if (nomessage) {
		if (reenter) return;
		if (!(progress||cancel)) {
			 var x = ($("#"+obj).offset()==null)?0:$("#"+obj).offset().left + ($("#"+obj).width()/2)-20;
			 var y = ($("#"+obj).offset()==null)?0:$("#"+obj).offset().top + (($("#"+obj).height()==0?300:$("#"+obj).height())/2)-20;
			 workingPaneHTML =  '<div id="waiting_pane" style="position:absolute;top:'+y+'px;left:'+x+'px;" > ';
			 workingPaneHTML += ' <i class="'+icono+'"/>';
			 workingPaneHTML +='  <span class="sr-only">'+msgProcessing+'</span>';
			 workingPaneHTML +='</div>	';  	
			 if (!reenter)
				 $("#"+obj).addClass("processing");
			 $('#waitpane').html(workingPaneHTML);
			 return;
		}
	}
	if (progress) {
		porcentaje =  parseFloat(messageConfig.substring(messageConfig.indexOf("[P:")+3,messageConfig.indexOf("|",messageConfig.indexOf("[P:")+3)));
		total =  parseFloat(messageConfig.substring(messageConfig.indexOf("|T:")+3,messageConfig.indexOf("]",messageConfig.indexOf("|T:")+3)));

	}
	if (hasIcono && messageConfig.indexOf("[I:")!=-1) {
		icono =  messageConfig.substring(messageConfig.indexOf("[I:")+3,messageConfig.indexOf("]",messageConfig.indexOf("[I:")+3));
	}

    
   
	var workingPaneHTML = '';
    
    if (progress||cancel) {
    	var top  = window.pageYOffset || document.documentElement.scrollTop;
		var wx = 600;
	    var hy = 200;
		var mx = (window.innerWidth / 2) - (wx/2);
	    var my = top+hy;
	   	 workingPaneHTML =  '<div class="panel waiting-panel" id="waiting_pane" data-backdrop="static" data-keyboard="false" style="position:absolute;top:'+my+'px;left:'+mx+'px;z-index:100000;border-color: #4c4545;width:'+wx+'px;height:'+hy+'px" > ';
		 workingPaneHTML +='  <div class="panel-header waiting-panel-header" style="padding-left:15px;"><h1>'+msgProcessing+'</h1></div> <div class="panel-body">';
		 if (hasIcono || !progress) {
			 workingPaneHTML += '<i class="'+icono+'"/>';
		 }
		 if (progress) {
			workingPaneHTML += ' <div class="progress waiting-progress">';
			workingPaneHTML +='   <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="'+porcentaje+'" aria-valuemin="0" aria-valuemax="'+total+'" style="width: '+((porcentaje*100)/total)+'%"></div>';
		 	workingPaneHTML +='  </div>	';
		 }
		 workingPaneHTML +='     <span>'+message+'</span> <div>';
		 if (cancel) {
			 workingPaneHTML +='  <button class="btn btn-danger pull-right waiting-btn" onclick="sendWait('+req+',true);">Cancel</button> ';
			 
		 }
		 workingPaneHTML +=' </div>	';
    	
    } else {
	 var x = ($("#"+obj).offset()==null)?0:$("#"+obj).offset().left + ($("#"+obj).width()/2)-20;
     var y = ($("#"+obj).offset()==null)?0:$("#"+obj).offset().top + ($("#"+obj).height()/2)-20;
   	 workingPaneHTML =  '<div id="waiting_pane" style="position:absolute;top:'+y+'px;left:'+x+'px;" > ';
	 workingPaneHTML += ' <i class="'+icono+'"/>';
	 workingPaneHTML +='  <span class="sr-only">'+msgProcessing+'</span>';
	 workingPaneHTML +='</div>	';  	
    }
	


 
	 if (!reenter)
		 $("#"+obj).addClass("processing");
	 $('#waitpane').html(workingPaneHTML);
}
function endWorkingPane() {
	$("body").css("cursor", "default");
	endWorkingPaneContainer(wp);
}

function endWorkingPaneInError() {
	endWorkingPaneInErrorContainer(wp);
}
function endWorkingPaneContainer(container) {
	lastContainer=null;
	wp='';	
	if (sendWaitTimer!=null)
		clearTimeout(sendWaitTimer);
	sendWaitTimer=null;

	 $('#waitpane').html('');
	if (container=='') return;
	$("#"+container).removeClass("processing");
}

function endWorkingPaneInErrorContainer(container) {
	endWorkingPaneContainer(container);
}








function ajaxError(xhr, ajaxOptions, thrownError, url) {
	endWorkingPaneInErrorContainer(getAjaxContainer(url));
	genericErrorMessage("zzzzerrzzzzLost connection [Error number:"+ xhr.status+"]");
} 

function isOutSequence(container,ajaxobj) {
	if (container!='view_area_and_title') return;
	return (ajaxobj.getFinalParams()["vrs"])<idRequest;
}


function isClearChangeInputs(url,contents) {
	if (url.indexOf("do-BackToQueryAction")!=-1 || url.indexOf("do-BackAction")!=-1) return false;
	return contents.indexOf("clearChangeInputs")!=-1;
}
function isOnlyClose(url,contents) {
	if (url.indexOf("do-BackToQueryAction")!=-1 || url.indexOf("do-BackAction")!=-1) return true;
	return contents.indexOf("modalmustback_onlyClose")!=-1;
}
function isCloseAndOpen(url,contents) {
	return contents.indexOf("modalmustback_onlyClose")!=-1;
}
function ajaxRecieved(msg, ajaxOptions, ajaxXmlHttp, url, ajaxobj) {
	var ss = 0;
	var es = 0;
    var returnTime= new Date().getTime();
	var ajaxContainer = getAjaxContainer(url);
	var arrayParents = new Array();  
	if (isOutSequence(ajaxContainer,ajaxobj)) 
		return;
	if (isClearChangeInputs(url,ajaxXmlHttp.responseText))
		setChangeInputs(false);

	waitingDialog.hide();
	endWorkingPane();
	closeModalOnly("force_modal");
	if (isNoScriptSpecialResponse(ajaxXmlHttp.responseText,ajaxobj)) {
		releaseSemaphore();
	} else{
		if (isDownloadFile(ajaxXmlHttp.getResponseHeader('x-response-url'))) { //es un archivo
			if (ajaxobj.UrlParameters['back_on_print']=='true') {
				closeModals(ajaxContainer);
			}
			adjustSessionID();
//	    	if (ajaxXmlHttp.getResponseHeader('x-response-url').indexOf(".pdf")!=-1 || ajaxXmlHttp.getResponseHeader('x-response-url').indexOf(".mpdf")!=-1)
	    		downloadInNewTab(ajaxXmlHttp.responseText,ajaxXmlHttp.getResponseHeader('x-response-url'),ajaxXmlHttp.responseType);
//	    	else
//	    		download(ajaxXmlHttp.responseText,getNameFile(ajaxXmlHttp.getResponseHeader('x-response-url')),ajaxXmlHttp.responseType);
			releaseSemaphore();
			if (ajaxobj.UrlParameters['back_on_print']=='true') {
				setChangeInputs(false);//mejorar
				goTo(this,'do-BackToQueryAction', false, '', null, 'view_area_and_title', '', '', true, true, event, null, false, '','','true'); 
			}
			else if (ajaxobj.UrlParameters['refresh_on_print']=='true') 
				goRefresh();
			return;
		} else if (isModalBack(url,ajaxXmlHttp.responseText,ajaxobj) ) {
			ajaxContainer = getLastModalBack(url,ajaxXmlHttp.responseText);
	   
			releaseSemaphore();
			if (isIgnoreResponse(ajaxXmlHttp.responseText,ajaxobj)) {
//				endWorkingPane();// ignore
				closeModals(getLastModal());
			} else { 
				if (!isSpecialResponse(ajaxXmlHttp.responseText,ajaxobj)) {
					var ajaxContainerName = closeModals(ajaxContainer);
//					endWorkingPane();
					createModal(arrayParents,ajaxContainerName,ajaxobj,ajaxXmlHttp.responseText,nuevo,true,calculeModalWidth(null));
				}
			}
	    } else if (isModal(ajaxContainer,url,ajaxXmlHttp.responseText)) {
			releaseSemaphore();
			if (isIgnoreResponse(ajaxXmlHttp.responseText,ajaxobj)) {
//				endWorkingPane();// ignore
				closeModals( getLastModal());
			} else { 
				if (!isSpecialResponse(ajaxXmlHttp.responseText,ajaxobj)) {
					var nuevo = closeModalsInv(ajaxContainer);
//					endWorkingPane();
					createModal(arrayParents,ajaxContainer,ajaxobj,ajaxXmlHttp.responseText,nuevo,true,calculeModalWidth(null));
				}
			}
	    } else if (isForceModal(ajaxXmlHttp.responseText)) {
			releaseSemaphore();
			ajaxContainer= "force_modal";
			if (isIgnoreResponse(ajaxXmlHttp.responseText,ajaxobj)) {
//				endWorkingPane();// ignore
				closeModals( getLastModal());
			} else { 
				if (!isSpecialResponse(ajaxXmlHttp.responseText,ajaxobj)) {
					var nuevo = closeModalsInv(ajaxContainer);
//					endWorkingPane();
					createModal(arrayParents,ajaxContainer,ajaxobj,ajaxXmlHttp.responseText,nuevo,false,calculeModalWidth(50));
				}
			}
	    } else {
	    	if (isIgnoreResponse(ajaxXmlHttp.responseText,ajaxobj)) {
		    	closeModals(ajaxContainer);
//				endWorkingPane();// ignore
			} else { 
				if (!isSpecialResponse(ajaxXmlHttp.responseText,ajaxobj)) {
					var newajaxContainer=closeModals(ajaxContainer);
					if (ajaxContainer.indexOf("modal_")==0) {
						ajaxContainer=newajaxContainer;
						ajaxobj.ajaxContainer=ajaxContainer;
					}
				    preWorkReceiveAjax(arrayParents,ajaxXmlHttp.responseText,ajaxContainer);
				    ajaxobj.ajaxListener(ajaxXmlHttp.responseText,ajaxobj);
		  	    	ss= new Date().getTime();
					ejecuteAjaxScripts(ajaxXmlHttp.responseText);
		 	    	es= new Date().getTime();
					posWorkReceiveAjax(arrayParents);
				}
				ajaxobj.ajaxListenerPosScript();
			}
	    }
		releaseSemaphore();
		ajaxobj.ajaxListenerPosAjax();
		ejecutePostAjaxScripts(ajaxXmlHttp.responseText);

	}
	var processTime= new Date().getTime();

	stadistics="IDREQ="+(ajaxobj.getFinalParams()["vrs"])+"|AJAX="+(returnTime-startTime)+"|BUILD="+(processTime-returnTime)+"|JS="+(es-ss)+"|DT="+(returnTime-downloadTime)+"|PT="+(downloadTime-startTime)+"|SIZE="+ajaxXmlHttp.responseText.length;
//	    alert("Return ajax: "+(returnTime-startTime)+"ms |Process ajax: "+(processTime-returnTime)+"ms| scripts: "+(es-ss)+"ms");
//    	firstFocus();
//	history.replaceState('console', null, 'console');
	changeFocus=1;
//	if (getFocusObject()) {
//		var pub = document.getElementById(getFocusObject());
//		if (pub) {
//			pub.focus();
//			pub.focus(); // por bug del explorer
//		}
//	} 

	
}

var modalQueue = new Array();
var modalChangesArray = new Array();
var basechanges = false;
var modalQueueLast =-1;
function getModalQueueLast( ) {
	return modalQueueLast;
}
function lostChanges() {
	var j;
	if ( basechanges) return true;
	for (j=0;j<=modalQueueLast;j++) {
		if (modalChangesArray[j]) return true;
	}
	return false;
}

function setModalChanges( change ) {
	if (modalQueueLast==-1) {
		basechanges=change;
	} else {
		modalChangesArray[modalQueueLast] = change;
	}
	adaptChanges() ; 
}
function getModalChanges() {
	if (modalQueueLast==-1) {
		return false;
	}
	return modalChangesArray[modalQueueLast];
}
function adaptChanges(  ) {
	var zone = getLastModal();
	if (getModalChanges()) {
		$("#"+zone).find(".modalchanges_hide").removeClass('hidden');
		$("#"+zone).find(".modalchanges_switch").html('<i class="fa fa-reply" style="padding-right:5px;"/>Cancel');
		
	} else {
		$("#"+zone).find(".modalchanges_hide").addClass('hidden');
		$("#"+zone).find(".modalchanges_switch").html('<i class="fa fa-reply" style="padding-right:5px;"/>Close');
	}
}
function getModalChanges() {
	if (modalQueueLast==-1) {
		return basechanges;
	}
	return modalChangesArray[modalQueueLast];
}
function registerModal( name ) {
	modalQueueLast++;
	modalQueue[modalQueueLast] = name;
	if (modalChangesArray.length<=modalQueueLast) {
		modalChangesArray[modalQueueLast] = false;
	}
	return modalQueue[modalQueueLast];
}
function closeModals( zone  ) {
	var j;
	if (zone.indexOf("win_list_complete")==0) return zone;
	var s = "view_area_and_title";
	for (j=modalQueueLast;j>=0;j--) {
	//	if ($('#'+zone).parents('#'+modalQueue[j]).length == 0)
		if (zone!=modalQueue[j]) {
			closeModal(modalQueue[j]);
			modalChangesArray[j] = false;
		} else {
			s=modalQueue[j];
			closeModal(s);
			break;
		}
	}
	modalQueueLast=j-1;
	if (modalQueueLast<-1)
		modalQueueLast=-1;
	return s;
}
function closeModalOnly( zone  ) {
	if (modalQueueLast<=-1) return;
	if (zone==modalQueue[modalQueueLast]) {
		closeModal(modalQueue[modalQueueLast]);
		modalChangesArray[modalQueueLast] = false;
		modalQueueLast=modalQueueLast-1;
		if (modalQueueLast<-1)
			modalQueueLast=-1;
	} 
	
}
function calculeModalClass(contents) {
//	if (contents.indexOf('class="nav nav-tabs')!=-1)
		return 'modal-dialog';
//	return 'modal-dialog-centered';
}
function calculeModalWidth(force) {
	if (force) return ($(window).width()*force)/100+"px";
	if (modalQueueLast==-1) return ($(window).width()*80)/100+"px";
	if (modalQueueLast==0) return ($(window).width()*75)/100+"px";
	if (modalQueueLast==1) return ($(window).width()*70)/100+"px";
	if (modalQueueLast==2) return ($(window).width()*65)/100+"px";
	if (modalQueueLast==3) return ($(window).width()*60)/100+"px";
	return  ($(window).width()*55)/100+"px";
}
function getLastModal() {
	var j;
	for (j=modalQueueLast;j>=0;j--) {
		return modalQueue[j];
	}
	return "view_area_and_title";
}
function setFocusModal() {
	var j;
	for (j=modalQueueLast;j>=0;j--) {
		  $('body').addClass('modal-open');
		return;
	}
	return;
}
function getLastModalBack(url,contents) {
	var j,i;
	if (isOnlyClose(url,contents)) {
		for (i=0,j=modalQueueLast;j>=0;j--,i++) {
			if (i==0) continue;
			return modalQueue[j];
		}
	} else {
		for (j=modalQueueLast;j>=0;j--) {
			return modalQueue[j];
		}
	}
	return "view_area_and_title";
}

function inModal() {
	return modalQueueLast!=-1;
}

function closeModalsInv( zone ) {
	var j;
	var existe=false;
//	if (zone.indexOf('_anonimus_')!=-1) {
//		var closeds=2;
//		for (j=modalQueueLast;j>=0;j--) {
//			closeModal(modalQueue[j]);
//			closeds--;
//			modalQueueLast--;
//			if (closeds==0)
//				break;
//		}
//		return false;
//	}
	if (modalQueueLast==-1) return;
	for (j=0;j<=modalQueueLast;j++) {
		if (zone==modalQueue[j]) {
			existe=true;
			break;
		}
	}


	if (existe) {
		var k=j-1;
		for (;j<=modalQueueLast;j++) {
			closeModal(modalQueue[j]);
		}
		modalQueueLast=k;
	}
	
	return !existe;
}


var modalChanges=false;
function closeModal(modalName) {
	if (modalName==null) return;
	$('#'+modalName).modal('hide');
	$(".modal-backdrop:first").remove();
	$('#'+modalName).remove();
	modalChanges=false;
}

function createModal(arrayParents, ajaxContainer, ajaxobj, contents, nuevo, withCancel, ancho) {
	var modalName=registerModal(ajaxContainer);
	$("#"+modalName).remove();
	modalChanges=false;
	var shtml='<div style="z-index:5000;" class="modal'+(nuevo?' fade':'')+'" id="'+modalName+'" data-focus-on="input:first"><div class="'+calculeModalClass(contents)+'" style="width:'+ancho+'"><div class="modal-content">';

	if (!withCancel || contents.indexOf('id="actionbar')!=-1) { // tiene action bar pero sin cancel
		shtml+='<div class="modal-body">'+contents; 
		shtml+='</div></div></div></div>';
	} else { // no tiene action bar
		shtml+='<div class="modal-body">'+contents+'</div><div class="modal-footer">';
		shtml+='<button type="button" class="btn btn-danger" data-dismiss="modal" >'+msgAbmButtonCancel+'</button>';
		shtml+='</div></div></div></div>';
	} 
 
	$("body").append(shtml);
    $('#'+modalName).on('show.bs.modal', function() {
 	    preWorkReceiveAjax(arrayParents,contents);
	    ss= new Date().getTime();
		ejecuteAjaxScripts(contents);
    	es= new Date().getTime();
		posWorkReceiveAjax(arrayParents);
		ajaxobj.ajaxListenerPosScript();
		executeStartedScripts();
		//setupStylesResponsive();
//		$(function() {
//			if ($(this).find(".modal-dialog-centered").length > 0) {
//				if ($(this).find(".modal-dialog-centered").height()+150>=$(window).height()) {
//					$(this).find(".modal-dialog-centered").addClass('modal-dialog')
//					$(this).find(".modal-dialog-centered").removeClass('modal-dialog-centered')
//				}
//			}
//		});
    });
	var that=$("#"+modalName);
	$("#"+modalName).on('hidden.bs.modal', function () {
		if (modalChanges) {
			openModalConfirm(msgLostData, function(){
				startNoCanChange();
				that.remove();
				endWorkingPane();
				endNoCanChange();
//				setChangeInputs(true);
			},function() {
				that.modal();
			});
		} else {
			startNoCanChange();
			that.remove();
			endWorkingPane();
			endNoCanChange();
//			setChangeInputs(true);
		}
		//goRefresh();
    });

	$("#"+modalName).modal({
	     backdrop: 'static',
	     keyboard: false
	 });
	
	$("#"+modalName).draggable({
        handle: ".panel-heading"
    });
	

}
function getModalInfo(){
	var modal;
	var lista = "";
	
	for (j=modalQueueLast;j>=0;j--) {
		modal=modalQueue[j];
	    lista+="mm_"+modal+"(";
	    lista+=$('#'+modal).position().left;
	    lista+=",";
	    lista+=$('#'+modal).position().top;
	    lista+=")";
	
	}
	return lista;
}

function preWorkReceiveAjax(arrayParents,text,ajaxcontainer) {
	// remuevo viejas zonas conocidas
	if (ajaxcontainer && ajaxcontainer=="view_area_and_title")
		cleanSubmitAndCancel();


	if (text.indexOf("publicity_zone")>=0) {
		var pub = document.getElementById("publicity_zone");
		if (pub) {
		 	arrayParents[0] =pub.parentNode; 
			arrayParents[0].removeChild(pub);
		}
	}
	else 
		arrayParents[0]=null;
		
	if (text.indexOf("help_zone")>=0) {
		var help = document.getElementById("help_zone");
		if (help) {
			arrayParents[1] =help.parentNode; 
			arrayParents[1].removeChild(help);
		}
	}
	else 
		arrayParents[1]=null;
    ////
	
	var urg = document.getElementById("urgent_message");
	if (urg) {
		 arrayParents[2] =urg.parentNode; 
		 arrayParents[2].removeChild(urg);
	}
	else 
		arrayParents[2]=null;
}

function posWorkReceiveAjax(arrayParents) {
	// muevo a las nuevas zonas conocidas
	var ajaxpub = document.getElementById("publicity_zone");
	if (ajaxpub && arrayParents[0]) {
		ajaxpub.parentNode.removeChild(ajaxpub);
		arrayParents[0].appendChild(ajaxpub);
	}
	
	var ajaxHelp = document.getElementById("help_zone");
	if (ajaxHelp && arrayParents[1]) {
		ajaxHelp.parentNode.removeChild(ajaxHelp);
		arrayParents[1].appendChild(ajaxHelp);
	}
	///
	var ajaxUrgent = document.getElementById("urgent_message");
	if (ajaxUrgent && arrayParents[2]) {
		ajaxUrgent.parentNode.removeChild(ajaxUrgent);
		arrayParents[2].appendChild(ajaxUrgent);
	}

}

//function ajaxPosScript() {
//}
function ajaxPosAjax() {
}

function ajaxPosScript() {
	//alert("ajaxPosScript");
	setupZTables();
}



function isAjaxSubmit(zUrl) {
	//alert("testing is ajax submit");
	if (zUrl.indexOf("do-WinListRefreshAction")==0) {
		//alert("is Submit WinListRefresh");
		return true;
	}
	if (zUrl.indexOf("do-SwapListRefreshAction")==0) {
		//alert("is Submit WinListRefresh");
		return true;
	}
	if (zUrl.indexOf("do-WinListDeleteAction")==0) {
		//alert("is Submit WinListDelete");
		return true;
	}
	if (zUrl.indexOf("do-WinListReloadAction")==0) {
		//alert("is Submit WinListReload");
		return true;
	} 
	if (zUrl.indexOf("do-comboAction")==0) {
		//alert("is Submit ComboAction");
		return true;
	}
	if (zUrl.indexOf("do-comboFormLovAction")==0) {
		//alert("is Submit ComboFormLovAction");
		return true;
	}
	if (zUrl.indexOf("do-FormLovRefreshAction")==0) {
		//alert("is Submit WinListRefresh");
		return true;
	}
	if (zUrl.indexOf("do-security")==0) {
		//alert("is Submit WinListRefresh");
		return true;
	}
	
	//alert("not is ajax submit");
	return false;
	
}
 

function isAjaxTabFormAction(zUrl) {
	if (zUrl.indexOf("do-tabFormAction")==0) {
		return true;
	}
	return false;
}

function isAjaxTabAction(zUrl) {
	if (zUrl.indexOf("do-tabAction")==0) {
		return true;
	}
	return false;
}

function isAjaxReload(zUrl) {
	if (zUrl.indexOf("do-WinListReloadAction")==0) {
		return true;
	}
	return false;
}
function isAjaxLayout(zUrl) {
	if (zUrl.indexOf("do-Layout")==0) {
		return true;
	}
	return false;
}


function isAjaxCheckRefresh(zUrl) {
	if (zUrl.indexOf("do-checkRefresh")==0) {
		return true;
	}
	return false;
}
function isAjaxDelete(zUrl) {
	if (zUrl.indexOf("do-WinListDeleteAction")==0) {
		//return true;
	}
	return false;
}
function isAjaxSaveHelp(zUrl) {
	if (zUrl.indexOf("do-saveHelp")==0) {
		return true;
	}
	return false;
}

function isWinDependant(zUrl) {
	if (zUrl.indexOf("do-WinListDeleteAction")==0) {
		//alert("is Submit WinListRefresh");
		return true;
	}
	if (zUrl.indexOf("do-ViewAreaAction")==0) {
		//alert("is Submit WinListRefresh");
		return true;
	}
	if (zUrl.indexOf("do-ViewAreaAndTitleAction")==0) {
		//alert("is Submit WinListRefresh");
		return true;
	}
	if (zUrl.indexOf("do-allPanelsAction")==0) {
		//alert("is Submit WinListRefresh");
		return true;
	}
	
	if (zUrl.indexOf("do-WinListReloadAction")==0) {
		//alert("is Submit WinListRefresh");
		return true;
	}
	//alert("not is ajax submit");
	return false;
	
}
function getMetaFormParameters(objForm) {
	return addRegisterMetaToURLFromList();
//	return formDataToUrl(objForm, 0);
}

function getHiddenFormParameters(objForm) {
	return formDataRegisterToUrlSimple(objForm,"");
//	return formDataToUrl(objForm, 0);
}

function getFormControlsParameters(objForm) {
	return formDataRegisterToUrl(objForm,"");//formDataToUrl(objForm, 1);
}

function getWinListFilters() {
	return formDataRegisterToUrl(document.getElementById("filter_pane"),"filter_pane");
}

function getContainerDimensionParameter(zAjaxContainer) {
	var vector = new Array();
	if (zAjaxContainer=="") {
		return vector;
	}
	
    pushArray("dg_container_height",getHeight(zAjaxContainer),vector);
    pushArray("dg_container_width",getWidth(zAjaxContainer),vector);

	return vector;
}

function clearAjaxContainer() {
  ajaxContainerStore = new Array();
}

function isAjaxContainerRegistered(ajaxContainerId) {
  //alert("isAjaxContainerRegistered : "+ajaxContainerId);
  	
  for (i=0;i<ajaxContainerStore.length;i++) {
    currEntry = ajaxContainerStore[i];
    if (currEntry[0]==ajaxContainerId) {
    	//alert("found");
	  return true;
	}
  }
    	//alert("not found");
  return false;
}
function getAjaxContainerStoreEntry(ajaxContainerId) {
  return ajaxContainerStore[getAjaxContainerStoreEntryPosition(ajaxContainerId)][1];
}

function getAjaxContainerStoreEntryPosition(ajaxContainerId) {
  for (i=0;i<ajaxContainerStore.length;i++) {
    currEntry = ajaxContainerStore[i];
    if (currEntry[0]==ajaxContainerId) {
	  //alert("get store position found for '"+ajaxContainerId+"' at "+i);
	  return i;
	}
  }
  //alert("get store entry: not found !!!! "+ajaxContainerId);
  return "NOT FOUND !!!";
}
function storeAjaxNotebook(notebookToStoreId) {
	
	//alert("store notebook, StoreId="+notebookToStoreId);
	
	if (document.getElementById("notebook")==null) {
	  //alert("not storing");
	  return
	}
	
	var storePosition = ajaxContainerStore.length;

	//alert("store position:"+storePosition );
    
	if (isAjaxContainerRegistered(notebookToStoreId)) {
		storePosition = getAjaxContainerStoreEntryPosition(notebookToStoreId)
		//alert("replacing store position:"+storePosition );
	}
		
	ajaxContainerStore[storePosition] = new Array(notebookToStoreId, document.getElementById("notebook").innerHTML)
	//alert("store end");
}


function myescape(value) {
	return replaceSpecialPlusHTML(escape(value));
}


function addAnRegisterToUrl(campo) {
		var vector = new Array();
		var obj = getElement(campo);
		if (!obj) return vector;
        if (obj.tagName == "INPUT") {
	        if (obj.type == "radio") {
	           if (obj.checked) {
	        	   pushArray(obj.name,obj.value,vector);
	           }
	        } 
	        else if (obj.type == "checkbox") {
	              pushArray(obj.name, obj.checked?"S":"N",vector);
			}else {
	            pushArray(obj.name,obj.value,vector);
	        }
		} else if (obj.tagName == "DIV") {
			vector = collect(vector,formDataRegisterToUrlRadioButton(obj,campo));
		} else if (obj.tagName == "IFRAME") {
            pushArray(obj.name,escape(obj.contentWindow.document.body.innerHTML),vector);
		} else if (obj.tagName == "SELECT") {
			 var comboValue = getSelectValues(obj)
             pushArray(obj.name,comboValue,vector);
        }
        else if (obj.tagName == "TEXTAREA") {
        	aux = myescape(obj.value);
        	aux = aux.replace( /%u\d{4}/g, ""  );
            pushArray(obj.name,aux,vector);
        }   
        return vector;
}

function getSelectValues(select) {
	  var result = "";
	  var options = select && select.options;
	  var opt;
	  var name = select.name;
	  var full = name==null?false:name.indexOf("[]")!=-1;

	  for (var i=0, iLen=options.length; i<iLen; i++) {
	    opt = options[i];

	    if (full||opt.selected) {
	      result+= (result==""?"":",")+opt.value;
	    }
	  }
	  return result;
	}
function addAnRegisterToUrlFromList(campo,values) {
		var vector = new Array();
		var fillCampo = false;
		var getstr = "";
	    var valuesIndex=0;
	    var s = values.length;
		for(valuesIndex=0;valuesIndex < s;valuesIndex++) {
			var obj = getElement(values[valuesIndex]);
			if (!obj) continue;
			fillCampo = values[valuesIndex]!=campo && obj.tagName != "IFRAME";
			if (valuesIndex!=0)
		    	getstr+=",";
			if (obj.tagName == "INPUT") {
		        if (obj.type == "radio") {
		           if (obj.checked) {
		              getstr +=  ""+obj.value;
		           }
		        } else if (obj.type == "checkbox") {
			           if (obj.checked) {
				              getstr += "S";
			           } else {
				          getstr += "N";
			           }
				} else if ($(obj).data( "checkselect" ) ===true) {
					getstr += $(obj).closest("tr").hasClass("selected")?1:0;
				} else {
		        	getstr += ""+obj.value;
		        }
			} else if (obj.tagName == "DIV") {
				getstr += formDataRegisterToUrlRadioButtonStr(obj,campo);
		
			}else if (obj.tagName == "IFRAME") {
				getstr += ""+encodeURIComponent((obj.contentWindow.document.body.innerHTML)).replace(/'/g,"%27").replace(/"/g,"%22");
			}else if (obj.tagName == "SELECT") {
	            getstr += "" + getSelectValues(obj);
	        }
	        else if (obj.tagName == "TEXTAREA") {
	    		getstr += ""+myescape(obj.value);
	        }   
	    }
	    if (fillCampo) {
			var objOrig = getElement(campo);
	    	if (objOrig)
	    		objOrig.value = getstr;
	    }
	    pushArray(campo,getstr,vector);

	    return vector;
}


function formDataRegisterToUrl(objForm,filter) {
    var vector1 = formDataRegisterToUrlSimple(objForm);
    var vector2 = addRegisterToUrl(filter,0);
  	return collect(vector1,vector2);
}


function formDataRegisterToUrlFormFormLov(objForm,filter) {
	var vector3 = new Array();
	var vector1 = formDataRegisterToUrlSimple(objForm);
	var vector2 = addRegisterToUrl(filter,500);
	if (filter!=null && filter!="") 
		vector3 = addRegisterToUrl("win_form",500);
	var vector4 =  addRegisterToURLFromList(filter,500);

  	return collect(vector1,vector2,vector3,vector4);
}

function addRegisterToUrl(filter, limit) {
	var val;
    var vector = new Array();
	var valIndex = 0;

//	for(valIndex=0;valIndex < valCount;valIndex++) {
//	    val = valArr[valIndex];
//	    if (val[12]) continue;
//	    if (filter=="" || val[0].indexOf(filter)!=-1)
//			vector=collect(vector,addAnRegisterToUrl(val[0]));
//	}

	var vector2 = addRegisterToURLFromList(filter, limit);
    var vector3 = addRegisterTableToUrl();

  	return collect(vector,vector2,vector3);
}

function addRegisterTableToUrl() {
	var val;
    var vector = new Array();
	var valIndex = 0;

	for(valIndex=0;valIndex < valCountTable;valIndex++) {
	    val = valArrTable[valIndex];
	  	pushArray(val[0],getContentTable(val[0]),vector);
	}

  	return vector;
}
function addRegisterToURLFromList() {
	return addRegisterToURLFromList("");
}
function addRegisterToURLFromList(filter) {
	var val;
    var vector = new Array();
	var valIndex = 0;

	for(valIndex=0;valIndex < valCountNC;valIndex++) {
	    val = valArrNC[valIndex];
	    var name = val[0];
	    var values = val[1];
	    if (filter == "" || name.indexOf(filter)!=-1) {
	    	vector=collect(vector, addAnRegisterToUrlFromList(name,values));
		}
	}
	
  	return vector;
}
function addRegisterMetaToURLFromList() {
	var val;
    var vector = new Array();
	var valIndex = 0;

	for(valIndex=0;valIndex < valCountMeta;valIndex++) {
	    val = valArrMeta[valIndex];
	    var name = "meta_"+val[0];
	    var values = val[1];
	   	vector=collect(vector, addAnRegisterToUrlFromList(name,values));
	}
	
  	return vector;
}
function pushArray(name,value,vector) {
	if (!vector) return;
    vector[name] = value;
 }


function formDataRegisterToUrlSimple(objForm) {
	var val;
    var vector = new Array();
	var valIndex = 0;
	if (!objForm) return vector;

    for (i=0; i<objForm.childNodes.length; i++) {
    	var obj =objForm.childNodes[i]; 
         if (obj.tagName == "INPUT") {
	        if (obj.type == "radio") {
	           if (obj.checked) {
	              pushArray(obj.name,obj.value,vector);
	           }
            } else if (obj.type == "checkbox") {
		        pushArray(obj.name, obj.checked?"S":"N",vector);
            } else {
                pushArray(obj.name,obj.value,vector);
	        }
	     } else if (obj.tagName == "SELECT") {
			 var comboValue = getSelectValues(obj)
             pushArray(obj.name,comboValue,vector);
		 } else if (obj.tagName == "TEXTAREA") {
	        pushArray(obj.name,obj.value,vector);
	     } 
         
	}
	
	
  	return vector;
}


function formDataRegisterToUrlRadioButton(objForm,campo) {
	var val;
    var vector = new Array();
	var valIndex = 0;
	if (!objForm) return vector;

    for (i=0; i<objForm.childNodes.length; i++) {
    	var obj =objForm.childNodes[i];
         if (obj.tagName == "INPUT") {
	        if (obj.type == "radio") {
	           if (obj.checked) {
	              pushArray(campo,obj.value,vector);
	           }
            }
        }
         else if (obj.tagName == "LABEL") {
        	    for (j=0; j<obj.childNodes.length; j++) {
        	    	var objI =obj.childNodes[j];
        	         if (objI.tagName == "INPUT") {
        		        if (objI.type == "radio") {
        		           if (objI.checked) {
        		              pushArray(campo,objI.value,vector);
        		           }
        	            }
        	        }
        	    }
         }

	}
	
  	return vector;
}

function formDataRegisterToUrlRadioButtonStr(objForm,campo) {
	var val;
    var getstr = "";
	var valIndex = 0;
	if (!objForm) return getstr;

    for (i=0; i<objForm.childNodes.length; i++) {
    	var obj =objForm.childNodes[i];
         if (obj.tagName == "INPUT") {
	        if (obj.type == "radio") {
	           if (obj.checked) {
	              getstr +=  obj.value;
	           }
            }
        }
         else if (obj.tagName == "LABEL") {
     	    for (j=0; j<obj.childNodes.length; j++) {
     	    	var objI =obj.childNodes[j];
     	         if (objI.tagName == "INPUT") {
     		        if (objI.type == "radio") {
     		           if (objI.checked) {
     		              getstr +=  objI.value;
     		           }
     	            }
     	        }
     	    }
      }
	}
	
  	return getstr;
}

function getUrlParameter(param, value) {
	var vector = new Array();
	pushArray(param,value,vector);
	return vector;
}
function getUrlParameterStr(param, value) {
	return param + "=" + myescape(value) + "&";
}
formDataToUrlResult = function(value) {
	this.currentId = value;
	this.addUrl = "";
}
formDataToUrlResult.prototype.getCurrentId = function() {
	return this.currentId;
}
formDataToUrlResult.prototype.getURIresult = function() {
	return this.addUrl;
}
formDataToUrlResult.prototype.setURIresult = function(value) {
	this.addUrl = value;
}


function ejecuteAjaxScripts(response) {
//	alert("ejecuteAjaxScripts: start");
	var upperResponse = response.toUpperCase();
	var pos= upperResponse.indexOf("<SCRIPT");
	while (pos>=0) {
		var startPos = pos+1;
		pos = response.indexOf(">",pos+1)+1;
		endPos = upperResponse.indexOf("</SCRIPT>",pos);
		var zScript='';
		if (endPos!=-1) {
			 zScript = response.substring(pos,endPos);
		} else {
			endPos=pos+8;
		}
		//alert("script:"+zScript )
			console.log("script:"+zScript);
		if (zScript!='' && zScript.indexOf("maps.google.com")<0 ) {;
			var extra = response.substring(startPos,pos);
//			var ss1= new Date().getTime();
			zScript = replaceSpecialCharacters(zScript);
	    	var ss2= new Date().getTime();
//			alert("script:"+zScript )
			if (extra.toUpperCase().indexOf("POSTSCRIPT")>=0) {
				;//ignoring
			}
			else {
				if (window.ActiveXObject)			{
					window.execScript(zScript);
				}
				else /* (window.XMLHttpRequest) */		{
					window.eval(zScript);
				}
			}
	    	var ss3= new Date().getTime();
	    	var demora= ss3-ss2;
			if (demora>50)  	    	
				console.log("slow script:"+zScript+" "+(demora)+"ms");
		}
	
		pos = upperResponse.indexOf("<SCRIPT",endPos+9);
	}
	//alert("ejecuteAjaxScripts: end");
}
function ejecutePostAjaxScripts(response) {
//	alert("ejecuteAjaxScripts: start");
	var upperResponse = response.toUpperCase();
	var pos= upperResponse.indexOf("<SCRIPT TYPE=\"POSTSCRIPT\"");
	while (pos>=0) {
		var startPos = pos+1;
		pos = response.indexOf(">",pos+1)+1;
		endPos = upperResponse.indexOf("</SCRIPT>",pos);
		var zScript = response.substring(pos,endPos);
		console.log("pos script:"+zScript);
		if (zScript!='' && zScript.indexOf("maps.google.com")<0 ) {;
			var extra = response.substring(startPos,pos);
			zScript = replaceSpecialCharacters(zScript);
			
			if (window.ActiveXObject)			{
				window.execScript(zScript);
			}
			else /* (window.XMLHttpRequest) */		{
				window.eval(zScript);
			}
		
		}
		pos = upperResponse.indexOf("<SCRIPT TYPE=\"POSTSCRIPT\"",endPos+9);
	}
}
function replaceSpecialCharacters(zValue) {
	//zValue = replaceSpecialCharactersHTMLencoded(zValue);
	zValue = replaceSpecialCharactersHTML(zValue);
	return zValue;	
}
function replaceSpecialCharactersHTMLencoded(zValue) {
	var pos=0;
	while (true) {
		pos = zValue.indexOf("&#",pos);
		if (pos<0)
			break;
	    var charCode = zValue.substring(pos+2,pos+2+3)-0;
	    zValue = zValue.substring(0,pos)+String.fromCharCode(charCode)+zValue.substring(pos+6);
	}
	return zValue;
}
function replaceSpecialPlusHTML(zValue) {
	var pos=0;
	while (true) {
		pos = zValue.indexOf("+",pos);
		if (pos<0)
			break;
	    zValue = zValue.substring(0,pos)+"%2B"+zValue.substring(pos+1);
	}
	while (true) {
		pos = zValue.indexOf("%u2013",pos);
		if (pos<0)
			break;
	    zValue = zValue.substring(0,pos)+"-"+zValue.substring(pos+6);
	}
	while (true) {
		pos = zValue.indexOf("%u201C",pos);
		if (pos<0)
			break;
	    zValue = zValue.substring(0,pos)+"%22"+zValue.substring(pos+6);
	}
	while (true) {
		pos = zValue.indexOf("%u201D",pos);
		if (pos<0)
			break;
	    zValue = zValue.substring(0,pos)+"%22"+zValue.substring(pos+6);
	}
	while (true) {
		pos = zValue.indexOf("%u201E",pos);
		if (pos<0)
			break;
	    zValue = zValue.substring(0,pos)+"%22"+zValue.substring(pos+6);
	}
	return zValue;
}
function replaceSpecialCharactersHTML(zValue) {
	zValue = replaceSpecialCharactersHTMLacute(zValue,'a','\u00E1');
	zValue = replaceSpecialCharactersHTMLacute(zValue,'e','\u00E9');
	zValue = replaceSpecialCharactersHTMLacute(zValue,'i','\u00ED');
	zValue = replaceSpecialCharactersHTMLacute(zValue,'o','\u00F3');
	zValue = replaceSpecialCharactersHTMLacute(zValue,'u','\u00FA');
	zValue = replaceSpecialCharactersHTMLacute(zValue,'A','\u00C1');
	zValue = replaceSpecialCharactersHTMLacute(zValue,'E','\u00C9');
	zValue = replaceSpecialCharactersHTMLacute(zValue,'I','\u00CD');
	zValue = replaceSpecialCharactersHTMLacute(zValue,'O','\u00D3');
	zValue = replaceSpecialCharactersHTMLacute(zValue,'U','\u00DA');
	zValue = replaceSpecialCharactersHTMLtilde(zValue,'n','\u00F1');
	zValue = replaceSpecialCharactersHTMLtilde(zValue,'N','\u00D1');
	return zValue;
}
function replaceSpecialCharactersHTMLacute(zValue, zSearch, zReplace) {
	return replaceSpecialCharactersHTMLaux(zValue, zSearch, zReplace, 'acute');
}
function replaceSpecialCharactersHTMLtilde(zValue, zSearch, zReplace) {
	return replaceSpecialCharactersHTMLaux(zValue, zSearch, zReplace, 'tilde');
}
function replaceSpecialCharactersHTMLaux(zValue, zSearch, zReplace, zType) {
	var pos=0;
	while (true) {
		pos = zValue.indexOf("&"+zSearch+zType+";",pos);
		if (pos<0)
			break;
	    zValue = zValue.substring(0,pos)+zReplace+zValue.substring(pos+8);
	}
	return zValue;
}
function isDownloadFile(url) {
  if (!url) return false;
  if (url.indexOf("file/")!=-1) return true;
  if (url.indexOf("business_resource/")!=-1) return true;
  return false;
}


function isBackModal() {
	return modalQueueLast>0;
}
function isModal(ajaxContainer,url,contents) {
	if (url.indexOf("do-BackToQueryAction")!=-1 || url.indexOf("do-BackAction")!=-1 || contents.indexOf("modalmustback")!=-1) {
		if ( getLastModalBack(url,contents)=="view_area_and_title")
			return false;
	}
    return  isModalContainer(ajaxContainer);
}
function isForceModal(contents) {
	var modal = contents.indexOf(' data-modal="true" ')!=-1;
	return modal;
}
function isModalBack(url,contents,ajaxobj) {
	if (url.indexOf("do-BackToQueryAction")==-1 && url.indexOf("do-BackAction")==-1 && contents.indexOf("modalmustback")==-1) return false;
//	if (modalQueueLast==-1) return false;
	
   return getLastModalBack(url,contents)!="view_area_and_title";
}
function isModalContainer(ajaxobj) {
	return  ajaxobj.indexOf("modal_")!=-1;
}
function getNameFile(url) {
	var name=url;
	return name.substring(name.lastIndexOf("/")+1);
}

function isIgnoreResponse(zText,ajaxobj) {
	var flag = "zzzzerrzzzz";
	if (zText.indexOf(flag)<0) 
		return false;
	if (zText.indexOf("NOSHOW")!=-1) {
		return true;
	}
return false;		
}

function isSpecialResponse(zText,ajaxobj) {
	if (isErrorMessageResponse(zText,ajaxobj))
		return true;
	
	if (isInfoMessageResponse(zText,ajaxobj))
		return true;

	if (isSessionTimeOutResponse(zText,ajaxobj))
		return true;
		
	if (isWinlistDeleteResponse(zText,ajaxobj))
		return true;

	return false;		
}
function isNoScriptSpecialResponse(zText,ajaxobj) {
	if (isErrorMessageResponse(zText,ajaxobj))
		return true;
	
	if (isInfoMessageResponse(zText,ajaxobj))
		return true;


	return false;		
}
function isErrorMessageResponse(zText,ajaxobj) {
	var flag = "zzzzerrzzzz";
	if (zText.indexOf(flag)<0) {
		return false;
	}
	if (zText.indexOf("bad request")>0) {
		return true;
	}
	ajaxobj.ajaxErrorListener(zText,ajaxobj);
	return true;
}

function isInfoMessageResponse(zText,ajaxobj) {
	var flag = "zzzzinfzzzz";
	if (zText.indexOf(flag)<0) {
		return false;
	}
	if (zText.indexOf("bad request")>0) {
		return true;
	}
	ajaxobj.ajaxErrorListener(zText,ajaxobj);
	return true;
}

function genericErrorMessage(zText) {
	var flag = "zzzzerrzzzz";
	var type = 'err';
	var title = msgError;
	if (zText.indexOf(flag)<0) {
		flag = "zzzzinfzzzz";
		if (zText.indexOf(flag)<0) 
			return;
		else {
			type='inf';
			title = msgInfo;
		}
	} 
		
	
	var message = zText.substring(zText.indexOf(flag)+flag.length);
	//message = replaceSpecialCharacters(message);
	
	if (message!="Transaccion Abortada por el usuario") {
		openModal(type,title,message,true,true);
	}
	return;
}
function isSessionTimeOutResponse(zText,ajaxobj) {
	//alert(zText);
	var flag = "zzzzSessionTimeOutzzzz";
	var flag2 = "nnnn";
	if (zText.indexOf(flag)<0) 
		return false;
	var message = zText.substring(zText.indexOf(flag)+flag.length);
	var loginPage = zText.substring(zText.indexOf(flag2)+flag2.length,zText.indexOf(flag));
	message = replaceSpecialCharacters(message);
	openModal("err",msgError,message,false,true, function() {
		if (window.parent && !isFF) { //en firefox siempre tien padre y cierra aun en la ventan principal
			window.open('','_parent','');
			window.close();		
		}	
		window.location.href='loginPage';
	});
	releaseSemaphore();
	
	return true;
}

function isWinlistDeleteResponse(zText,ajaxobj) {
	//alert(zText);
	var flag = "zzzzWinListDeletezzzz";
	var deleteRow;
	var line = ajaxobj.ajaxContainer;
	var owner;
	var pos;
	var deleteRow;
	var unselect = 0;
	if (zText.indexOf(flag)<0) {
		return false;
	}
//	alert("Delete Row: "+document.getElementById(ajaxContainer).innerHTML);
	
	//alert("Delete Line: "+line);
	while (1) {
		pos = line.indexOf(";");
		if (pos == -1) {
			owner = line;
			line = "";
		}
		else {
			owner = line.substring(0,pos);
			line = line.substring(pos+1);
		} 
	//	alert("Delete Row: "+owner+"; "+line);
		deleteRow = document.getElementById(owner);
		if (deleteRow && deleteRow.cells) {
			if (unselect==0) {
				unselectAll_fromRow(deleteRow);
				unselect = 1;
			}
			while (deleteRow.cells.length>0)
				deleteRow.deleteCell(0);
		} else { /* si no hay rows, es porque requiero un refresh de todo*/
			clearAjaxContainer();
			break;
		}
		if (line=="") break;
		 
	}

	

	if (document.getElementById('dg_table_provider'))
		storeAjaxNotebook(document.navform.dg_table_provider.value);

	return true;
}
