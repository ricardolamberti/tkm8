 
/*********************************

      ENTRY POINT FUNCTIONS

*********************************/
isNS=(window.navigator.appName=="Netscape");
isFF=(window.navigator.userAgent.indexOf("Firefox")!=-1);
isMS=(window.navigator.userAgent.toLowerCase().indexOf('msie')!=-1);

vHPos='center';
vVPos='center'; 
previousTitle='';
previousTitleError='';
 

var regScrolls = new Array();
zScroll = function(obj,iScrollTop,iScrollLeft) {
	this.objName = obj;
	var pos = iScrollTop.indexOf(obj+"=");
	var linea;
	var pos2;
	var pc;
	this.iScrollTop = 0;
	this.iScrollLeft = 0;
	if (pos!=-1) {
		linea = iScrollTop.substring(pos);
		pos2 = linea.indexOf(";");
		pc = linea.substring((obj+"=").length,pos2);
	 	this.iScrollTop = parseInt(pc);
	}
	pos = iScrollLeft.indexOf(obj+"=");
	if (pos!=-1) {
		linea = iScrollTop.substring(pos);
	 	pos2 = linea.indexOf(";");
		pc = linea.substring((obj+"=").length,pos2);
		this.iScrollLeft = parseInt(pc);
	}
}

function registerScroll(obj,iScrollTop,iScrollLeft) {
	if (regScrolls==null) cleanRegisterScroll();

  regScrolls[regScrolls.length] =  new zScroll(obj,iScrollTop,iScrollLeft);
}
function cleanRegisterScroll() {
  regScrolls = new Array();
}
function getInfoContext(){
	var lista = "";
	lista+=getScrollTop();
	lista+=getTableInfo();
	lista+=getModalInfo();
	lista+="focus="+getFocusObject()+";";
	return lista;
	
}
function getTableInfo(){
	var lista = "";
	for (i=0;i<regTables.length;i++) {
		    currAss = regTables[i];
		    if (currAss[1].isDataTable()) continue;
		    var table = $('#'+currAss[0]);
		    lista+="th_"+currAss[0]+"(";
		    $(table).find('th').each(
		    	    function(index) {
	                    if ($(this).closest('table').hasClass("table-grid")) {
		    	    		lista+="p_"+$(this).attr("data-pos")+"="+($(this).css('display')=='none'?'I':$(this).width())+",";
	                    } else if ($(this).closest('thead').hasClass("tableFloatingHeaderOriginal"))
		    	    		lista+="p_"+$(this).attr("data-pos")+"="+($(this).css('display')=='none'?'I':$(this).width())+",";
		    		});
		    lista+=");";
	 }
 return lista;
	  
}
function getScrollTop(){
 var iCount = regScrolls.length;
  var iCounter = 0;
  var lista = "";
  for (iCounter=0; iCounter < iCount; iCounter++) {
    var regScroll = regScrolls[iCounter];
    var o = regScroll.objName=='body'?document.body:getElement(regScroll.objName);
    if (o) {
    	lista+= regScroll.objName+"="+ o.scrollTop+";"; 
    }
  }
  return lista;
  
}
function getScrollLeft(){
 var iCount = regScrolls.length;
  var iCounter = 0;
  var lista ="";
  for (iCounter=0; iCounter < iCount; iCounter++) {
    var regScroll = regScrolls[iCounter];
    var o = regScroll.objName=='body'?document.body:getElement(regScroll.objName);
    if (o) {
    	lista+= regScroll.objName+"="+ o.scrollLeft+";"; 
    }
  }
  return lista;
}


function makeScroll(){
 var iCount = regScrolls.length;
  var iCounter = 0;
  for (iCounter=0; iCounter < iCount; iCounter++) {
    var regScroll = regScrolls[iCounter];
    var o = regScroll.objName=='body'?document.body:getElement(regScroll.objName);
    if (o) {
    	if (regScroll.iScrollTop!=-1)
    		o.scrollTop = regScroll.iScrollTop; 
    	if (regScroll.iScrollLeft!=-1)
    		o.scrollLeft = regScroll.iScrollLeft; 
    }
  }
}
 
/*
 function showWorkingPane(container) {
	if (container==null) return;
	$("#"+container).addClass("processing");
	
	previousTitle = container;
}

function endWorkingPane() {
	if (previousTitle=='') return;
	$("#"+previousTitle).removeClass("processing");
}
 */

/*
Layout the view in the suitable position when the window resizes
*/
function locateView() {
//  if (isNS) return;
  var oCurrView = getRef('view');
  if (!oCurrView) return;


  if (vHPos!='left') {
    var iXDiff = getWidth('view_area')-getWidth('view');
    if (iXDiff >= 0) {
	  if (vHPos=='right') {
	    oCurrView.style.left = iXDiff;
	  } else {
	    oCurrView.style.left = iXDiff / 2;
	  }
    }
  }
  /*
  if (vVPos!='top') {
    var iYDiff = getHeight('view_area')-getHeight('view');
    if (iYDiff >= 0) {
      if (vVPos=='bottom') {
        oCurrView.style.top = iYDiff;
      } else {
        oCurrView.style.top = (iYDiff / 2);
  	  }
    }
  }*/
}

function getWidth(id){
	value = 0;
	someObject = document.getElementById(id);
	if (!someObject) return value;
	if (someObject.style) {
		if (someObject.style.width){value =  parseInt(someObject.style.width);}
		if (someObject.style.pixelWidth){value =  parseInt(someObject.style.pixelWidth);}
	}
	if (someObject.offsetWidth){value = someObject.offsetWidth;}
    if (document.defaultView && document.defaultView.getComputedStyle) {
        var obj = document.defaultView.getComputedStyle(someObject,'');
		if (obj) value = parseInt(obj.getPropertyValue('width'));
    }
    return value;
}
function getHeight(id){
	value = 0;
	someObject = document.getElementById(id);
	if (!someObject) return value;
	if (someObject.style) {
	   	if (someObject.style.height){value =  parseInt(someObject.style.height);}
	  	if (someObject.style.pixelHeight){value =  parseInt(someObject.style.pixelHeight);}
	}
	else if (someObject.offsetHeight){value = someObject.offsetHeight;}
	else if (document.defaultView && document.defaultView.getComputedStyle) {
		var obj = document.defaultView.getComputedStyle(someObject,'');
		if (obj) value = parseInt(obj.getPropertyValue('height'));
	}
    
   return value;
} 
function getRealTop(someObject){
	value = 0;
//	someObject = document.getElementById(id);
	if (someObject==null) return 0;
	if (!someObject) return value;
	if (someObject.style) {
	   	if (someObject.style.top){value = someObject.style.top;}
	  	if (someObject.style.pixelTop){value = someObject.style.pixelTop;}
	}
	if (someObject.offsetTop){value = someObject.offsetTop;}
	if (document.defaultView && document.defaultView.getComputedStyle) {
		var obj = document.defaultView.getComputedStyle(someObject,'');
		if (obj) value = parseInt(obj.getPropertyValue('top'));
	}
    
   return value;
}  

function getRealLeft(someObject){
	value = 0;
//	someObject = document.getElementById(id);
	if (someObject==null) return 0;
	if (!someObject) return value;
	if (someObject.style) {
	   	if (someObject.style.left){value = someObject.style.left;}
	  	if (someObject.style.pixelLeft){value = someObject.style.pixelLeft;}
	}
	if (someObject.offsetLeft){value = someObject.offsetLeft;}
	if (document.defaultView && document.defaultView.getComputedStyle) {
		var obj = document.defaultView.getComputedStyle(someObject,'');
		if (obj) value = parseInt(obj.getPropertyValue('left'));
	}
    
   return value;
} 

/*
Set listener for NS4 to reload the page when the navigator resizes
*/
function reloadOnResize(init) {
  if (init==true) 
     with (navigator) {
        if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    		document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=reloadOnResize; 
    	}
        else if (navigator.userAgent.indexOf("Firefox")!=-1) {
    		document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=reloadOnResize; 
    	}
    }
  	else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) 
  		location.reload();
}
/*
Cross-browser function to setup the page layout
*/
function setupBrowser() {
	hideBrowserScrollBars();
}
function setupLayout(pageStereotype, zAppBarHeight, zViewTitleHeight, zInfoBarHeight, zTreeAreaWidth, zViewHPos, zViewVPos, zNavbarwidth,zViewAreaTop) {
	appBarHeight = zAppBarHeight;
	NavbarWidth = zNavbarwidth;
	viewTitleHeight = zViewTitleHeight;
    viewAreaOffset = zViewAreaTop;
	infoBarHeight = zInfoBarHeight;
	treeAreaWidth = zTreeAreaWidth;
	
	setupStyles();
	vHPos=zViewHPos;
	vVPos=zViewVPos;
//	if (!isNS) 
//	  document.body.onresize=locateView;
	locateView();
}

/*********************************

      UTILITY FUNCTIONS

*********************************/

function set_class(objName,type,value){
	var nodes = document.getElementById(objName).childNodes;
	for(var i=0; i<nodes.length; i++) {
	    if (nodes[i].nodeName.toLowerCase() == type) {
	         nodes[i].className = value;
	     }
	}
}
function setStyle(objName,style,value){
  var obj=getRef(objName); 
  if (obj) obj.style[style]= value;
}

function getRef(obj){
  return document.getElementById(obj);
}

function hideBrowserScrollBars() {
  document.body.style.overflow = 'hidden';
}

function setupStyles() {
	 setStyle('tree_area', 'overflow', 'auto');
	 setStyle('view_area', 'overflow', 'auto');
	 setupStylesResponsive();
}
function setupStylesResponsive() {
	 makeScroll();

}