var anchorSubmit;
var anchorCancel;
var anchorsF;
var statusClick;
var timerClick;
var codeClick;
var urlPrefix;

function setUrlPrefix(urlp) {
	urlPrefix = urlp;
}

function funcClick(t, e, code) {
	if (statusClick == 1) {
		clearClick();
		code(t, e);
	}
}

function runClick(t, e, code) {
	// if (e.ctrlKey) return;
	// if (e.shiftKey) return;
	cleanFocusObject();
	if (codeClick != code) {
		clearClick();
	}
	codeClick = code;
	statusClick++;
	if (timerClick == null) timerClick = setTimeout(funcClick, 500, t, e, code);
}

function clearClick() {
	statusClick = 0;
	if (timerClick != null) clearTimeout(timerClick);
	timerClick = null;
}

function cleanSubmitAndCancel() {
	anchorSubmit = null;
	anchorCancel = null;
	clearFunctions();
}

function selectTab(comp, valor) {
	if ($(comp).val() == valor) return false;
	$(comp).val(valor);
	return true;
}

function setJQValue(comp, valor) {
	$(comp).val(valor);
}

function setAnchorSubmit(as) {
	if (anchorSubmit == null) anchorSubmit = new Array();
	anchorSubmit[anchorSubmit.length] = as;
}

function setAnchorCancel(ac) {
	anchorCancel = ac;
}

function setAnchorF(pos, ac) {
	getAnchorsF()[pos - 1] = ac;
}

function getAnchorSubmit(event, source) {
	var i, target = null;
	if (event.currentTarget != null) {
		$(event.currentTarget).parents().each(function () {
			for (i = 0; i < anchorSubmit.length; i++) {
				currAss = anchorSubmit[i];
				if ($("#" + currAss).is(":hidden")) continue;
				if ($(this).find("#" + currAss).length > 0) {
					target = document.getElementById(currAss);
					return false;
				}
			}
		});
		if (target != null) return target;
		//		for (i=0;i<anchorSubmit.length;i++) {
		//			currAss = anchorSubmit[i];
		//			var parent = $(event.currentTarget).parents().has("#"+currAss).first();
		//			if (parent.length>0) {
		//				if ($("#"+currAss).is(":hidden")) 
		//					continue;
		//				return  document.getElementById(currAss);
		//			}
		//		}
	}
	for (i = 0; i < anchorSubmit.length; i++) {
		currAss = anchorSubmit[i];
		if ($("#" + currAss).is(":hidden")) continue;
		return document.getElementById(currAss);
	}
	return null;
}

function infoResultUpload(field, filename, progP) {
	updateField(field, filename);
	if (filename == '') progreso(progP, 'No se pudo subir el archivo');
	else progreso(progP, 'El documento ha subido correctamente');
	alert("Para confirmar el envio, presione 'Aplicar'");
}

function updateField(field, text) {
	document.getElementById(field).value = text;
}

function progreso(field, content) {
	document.getElementById(field).innerHTML = content;
}

function getURL() {
	return location.href.substring(0, location.href.lastIndexOf('\/'));
}
var uri = new Object();
getURL(uri);
clearClick();

function gaOnKey(event, source){
	var submiter = null;
	var target=source==null?event.currentTarget:source;
	if (target.className.indexOf('enteristab')!=-1) {
		if (event.keyCode==13 ) {
			  nextFocusH(target,+1,true) ;
			  var evt = event ? event:window.event;
			  if (evt.stopPropagation)    evt.stopPropagation();
			  if (evt.cancelBubble!=null) evt.cancelBubble = true;
			  return false;
			}
		if (event.keyCode==39) {
		  nextFocusH(target,+1,false);
		  var evt = event ? event:window.event;
		  if (evt.stopPropagation)    evt.stopPropagation();
		  if (evt.cancelBubble!=null) evt.cancelBubble = true;
		  return false;
		}
		if (event.keyCode==37) {
		  nextFocusH(target,-1,false);
		  var evt = event ? event:window.event;
		  if (evt.stopPropagation)    evt.stopPropagation();
		  if (evt.cancelBubble!=null) evt.cancelBubble = true;
		  return false;
		}
		if (event.keyCode==38) {
		  nextFocusV(target,-1,false);
		  var evt = event ? event:window.event;
		  if (evt.stopPropagation)    evt.stopPropagation();
		  if (evt.cancelBubble!=null) evt.cancelBubble = true;
		  return false;
		}
		if (event.keyCode==40) {
		  nextFocusV(target,+1,false);
		  var evt = event ? event:window.event;
		  if (evt.stopPropagation)    evt.stopPropagation();
		  if (evt.cancelBubble!=null) evt.cancelBubble = true;
		  return false;
		}		
	}

	if(event ==null || event.keyCode==13){ 
		if (anchorSubmit) submiter = getAnchorSubmit(event,source);
		if (submiter==null) {
			submiter = document.getElementById("confirm");
			if (submiter==null) return;
		}
		
		submiter.focus();
		submiter.focus();
		if (navigator.appName=="Netscape") {
			var evt = document.createEvent("MouseEvents"); 
			evt.initMouseEvent("click", true, true, window, 0, 0, 0, 0, 0, false,false, false, false, 0, null); 
			if (source!=null) source.onchange();
			submiter.dispatchEvent(evt); 
			return false;
		}
		else {
			if (source!=null) source.onchange();
			submiter.click();
			return false;
		}
	}
	if(event.keyCode==27){ 
		if (anchorCancel) submiter = document.getElementById(anchorCancel);
		if (submiter==null) return;
		submiter.focus();
		submiter.focus();
		if (navigator.appName=="Netscape") {
		var evt = document.createEvent("MouseEvents"); 
			evt.initMouseEvent("click", true, true, window, 0, 0, 0, 0, 0, false,false, false, false, 0, null); 
			submiter.dispatchEvent(evt); 
			return false;
		}
		else {
			submiter.click();
			return false;
		}
	}
	var table=$(target).parents("table:first");
	return gaOnKeyF(event,table.length==0?null:$(table).id);
// return gaOnKeyF(event,getFirstTable());
}

function nextFocusV(target,sentido,enteristab) {
	  var focusable = null;
	  if (enteristab && $(".enteristab").size()>0)
		focusable = $('input,a,select,button,textarea,.select2-selection--single').filter('.enteristab').filter(':visible').not(".select2-hidden-accessible");
	  else
		focusable = $('input,a,select,button,textarea,.select2-selection--single').filter('.focuseable').filter(':visible').not(".select2-hidden-accessible");
	  if ($(target).parent().find(".select2-selection--single").size()>0)
		target=$(target).parent().find(".select2-selection--single");
	  var j=focusable.index(target);
	  var thisfocus = focusable.eq(j);
	  var nextfocus = focusable.eq(j+sentido);
	  j+=sentido;
	  while (!(nextfocus.offset().left>=thisfocus.offset().left && nextfocus.offset().left<=thisfocus.offset().left+thisfocus.width())) {
		  nextfocus = focusable.eq(j).first();
		  j+=sentido;
		  if (sentido>0 && (nextfocus==null || nextfocus==thisfocus)) break;
		  if (sentido<0) nextfocus = focusable.eq(j);
	  }
	  if (nextfocus!=null) {
		  nextfocus.focus();
		  nextfocus.select();
		  if (nextfocus.is("span"))
			  onFocusObject(nextfocus.parent().find(".select2-selection__rendered")[0]);
		  else
			  onFocusObject(nextfocus[0]);
	  }
	  
}

function nextFocusH(target,sentido,enteristab) {
	if ($(target).parent().find(".select2-selection--single").size()>0)
		target=$(target).parent().find(".select2-selection--single");

	var focusable = null;
	
	if (enteristab && $(".enteristab").size()>0)
	  focusable = $('input,a,select,button,textarea,.select2-selection--single').filter('.enteristab').filter(':visible').not(".select2-hidden-accessible");
	else
	  focusable = $('input,a,select,button,textarea,.select2-selection--single').filter('.focuseable').filter(':visible').not(".select2-hidden-accessible");

	  var nextfocus = focusable.eq(focusable.index(target)+sentido);
	  if (nextfocus!=null) {
		  nextfocus.focus();
		  nextfocus.select();
		  if (nextfocus.is("span"))
			  onFocusObject(nextfocus.parent().find(".select2-selection__rendered")[0]);
		  else
			  onFocusObject(nextfocus[0]);
	  }	  
}

function doSubmit() {
	if (anchorSubmit) submiter = document.getElementById(anchorSubmit);
	if (submiter == null) {
		submiter = document.getElementById("confirm");
		if (submiter == null) return true;
	}
	if (navigator.appName == "Netscape") {
		var evt = document.createEvent("MouseEvents");
		evt.initMouseEvent("click", true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
		submiter.dispatchEvent(evt);
		return false;
	} else {
		submiter.click();
		return false;
	}
	return true;
}

function isCTRL(e) {
	if (e == null) return false;
	var ctrl = false;
	if (navigator.userAgent.indexOf('Mac OS X') != -1) ctrl = e.metaKey;
	else ctrl = e.ctrlKey;
	return ctrl;
}

function setSelectExpandTab(nameinput, nametab) {
	$('#' + nameinput).val(nametab);
}

function selectExpandTab(name) {
	var s = $('#' + name).val();
	if (s == '') return;
	$('#' + s).collapse('show');
}

function selectJSTab(name) {
	var s = $('#' + name).val();
	$('#' + name + '_ul a[href="#' + s + '"]').tab('show');
}

function gaOnKeyF(event, zTable) {
	var submiter = null;
	if (event.keyCode == 13) {
		return false;
	}
	if (event.keyCode >= 111 && event.keyCode <= 124) {
		if (getAnchorsF()[parseInt("" + event.keyCode) - 112]) submiter = document.getElementById(getAnchorsF()[parseInt("" + event.keyCode) - 112]);
		if (submiter == null) return;
		if (navigator.appName == "Netscape") {
			var evt = document.createEvent("MouseEvents");
			evt.initMouseEvent("click", true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
			submiter.dispatchEvent(evt);
			return false;
		} else {
			submiter.click();
			return false;
		}
	}
	if (zTable) {
		var tableStr = getZTableForTableID(zTable);
		if (tableStr) {
			var table = document.getElementById(tableStr.getHtmlTableId());
			if (window.event) // IE
				keynum = event.keyCode;
			else if (event.which) // Netscape/Firefox/Opera
				keynum = event.which;
			if (keynum == 38) {
				// table.focus();
				// table.focus();
				// tableStr.select();
				tableStr.rsDown(event);
				return false;
			} else if (keynum == 40) {
				// table.focus();
				// table.focus();
				// tableStr.select();
				tableStr.rsUp(event);
				return false;
			} else if (keynum == 39) {
				tableStr.rsScrollRight(event);
				return false;
			} else if (keynum == 37) {
				tableStr.rsScrollLeft(event);
				return false;
			}
		}
	}
	return true;
}

function onKeyToClick(event, fullname) {
	if (event.keyCode == 13) {
		if (document.ie4) event.keyCode = -1;
		getElement(fullname).onclick();
		return false;
	}
}

function adaptativeScroll(tbody, theader, alternativescroll, iScrollTop, iScrollLeft) {
	registerScroll(alternativescroll, iScrollTop, iScrollLeft);
}

function insertHelp(obj) {
	alert(obj.id);
}

function getLeftObj(elementid) {
	var iReturnValue = 0;
	while (elementid != null) {
		try {
			if (elementid.offsetLeft && elementid.scrollLeft) iReturnValue += elementid.offsetLeft - elementid.scrollLeft;
			else if (elementid.offsetLeft) iReturnValue += elementid.offsetLeft;
			elementid = elementid.offsetParent;
		} catch (err) {
			break;
		}
	}
	return iReturnValue;
}

function getTopObj(elementid) {
	var iReturnValue = 0;
	while (elementid != null) {
		try {
			if (elementid.offsetTop && elementid.scrollTop) iReturnValue += elementid.offsetTop - elementid.scrollTop;
			else if (elementid.offsetTop) iReturnValue += elementid.offsetTop;
			elementid = elementid.offsetParent;
		} catch (err) {
			break;
		}
	}
	return iReturnValue;
}

function getLeft(id) {
	var iReturnValue = 0;
	if (id == "absolute") return 0;
	elementid = getElement(id);
	return getLeftObj(elementid);
}

function getTop(id) {
	var iReturnValue = 0;
	if (id == "absolute") return 0;
	elementid = getElement(id);
	return getTopObj(elementid);
}
document.onmousemove = mouseMove;
document.onmouseup = mouseUp;
var dragObject = null;
var mouseOffset = null;
var oldmousePos = null;
var remoteObject = false;

function forceScrollTop(objname, scroll) {
	var obj = getElement(objname);
	if (obj) obj.scrollTop = scroll;
}

function mouseCoords(ev) {
	if (ev.pageX || ev.pageY) {
		return {
			x: ev.pageX,
			y: ev.pageY
		};
	}
	return {
		x: ev.clientX + document.body.scrollLeft - document.body.clientLeft,
		y: ev.clientY + document.body.scrollTop - document.body.clientTop
	};
}

function getMouseOffset(target, ev) {
	ev = ev || window.event;
	var docPos = getPosition(target);
	var mousePos = mouseCoords(ev);
	if (remoteObject) {
		oldmousePos = mousePos;
		return {
			x: -(docPos.x - mousePos.x),
			y: -(docPos.y - mousePos.y)
		};
	} else return {
		x: mousePos.x - docPos.x,
		y: mousePos.y - docPos.y
	};
}

function getPosition(e) {
	return {
		x: getLeftObj(e),
		y: getTopObj(e)
	};
}

function mouseMove(ev) {
	if (dragObject) {
		ev = ev || window.event;
		var mousePos = mouseCoords(ev);
		dragObject.style.position = 'absolute';
		var px = mousePos.x - mouseOffset.x;
		var py = mousePos.y - mouseOffset.y;
		dragObject.style.top = py;
		dragObject.style.left = px;
		if (remoteObject) {
			px = -(px - oldmousePos.x);
			py = -(py - oldmousePos.y)
			mouseOffset = {
				x: px,
				y: py
			};
		}
		return false;
	}
}

function mouseUpRemote() {
	parent.dragObject = null;
}

function mouseUp() {
	dragObject = null;
	remoteObject = false;
	mouseOffset = null;
}
// remote que esta dentro de un iframe
function startDrag(element, ev, remote) {
	if (dragObject == null) {
		remoteObject = remote;
		mouseOffset = getMouseOffset(element, ev);
		dragObject = element;
	}
	return false;
}

function makeDraggable(item) {
	if (!item) return;
	item.onmousedown = 'javascript:startDrag(this,event,false);';
}
// ////////////////////////////////////////
var helpersLinkers = null;

function openCalculator(event, imagen, input) {
	var calc = getElement("calculator_container");
	var img = getElement(imagen);
	var inp = getElement(input);
	if (calc) {
		calc.parentNode.removeChild(calc);
	}
	var elem = createElement("div", document.body);
	elem.id = "calculator_container";
	elem.innerHTML = "<div id='calculator' class='calculator'><iframe src='html/calculator.html' width='236' height='173' alscrolling='no' frameborder='0'  allowTransparency='true' STYLE='background-color: transparent;overflow: hidden'><p>Su navegador no acepta iframes.</p></iframe>    </div>";
	calc = getElement("calculator");
	calc.style.display = 'none';
	if (img) {
		calc.name = "calculator_" + input;
		calc.style.left = getLeft(imagen) + 10;
		calc.style.top = getTop(imagen) + 10;
		calc.style.zIndex = 10000;
		calc.style.display = 'block';
		calc.style.position = 'absolute';
	}
}

function openColor(event, imagen, input) {
	var calc = getElement("color_container");
	var img = getElement(imagen);
	var inp = getElement(input);
	if (calc) {
		calc.parentNode.removeChild(calc);
	}
	var elem = createElement("div", document.body);
	elem.id = "color_container";
	elem.innerHTML = "<div id='color' class='calculator'><iframe src='html/color.html' width='235' height='230' alscrolling='no' frameborder='0'  allowTransparency='true' STYLE='background-color: transparent;overflow: visible'><p>Su navegador no acepta iframes.</p></iframe>    </div>";
	calc = getElement("color");
	calc.style.display = 'none';
	if (img) {
		calc.name = "color_" + input;
		calc.style.left = getLeft(imagen) + 10;
		calc.style.top = getTop(imagen) + 10;
		calc.style.zIndex = 10000;
		calc.style.position = 'absolute';
		calc.style.display = 'block';
	}
}

function cleanLinkWithHelp() {
	if (helpersLinkers != null) {
		for (i = 0; i < helpersLinkers.length; i++) {
			removeHelpBox(helpersLinkers[i][1], helpersLinkers[i][4], helpersLinkers[i][0], helpersLinkers[i][2], helpersLinkers[i][3]);
		}
	}
	helpersLinkers = null;
}

function linkWithHelp(obj, objHelp, pos, dx, dy, dz, publ) {
	if (helpersLinkers == null) helpersLinkers = new Array();
	link = new Array(7);
	link[0] = obj;
	link[1] = objHelp;
	link[2] = dx;
	link[3] = dy;
	link[4] = pos;
	link[5] = dz;
	link[6] = publ;
	helpersLinkers[helpersLinkers.length] = link;
}

function checkHelpShow(obj) {
	if (helpersLinkers == null) return;
	if (obj == "absolute") {
		var oh = getElement(helpersLinkers[i][1]);
		oh.style.display = "block";
		return;
	}
	for (i = 0; i < helpersLinkers.length; i++) {
		if (helpersLinkers[i] && helpersLinkers[i][0] == obj.id) {
			var oh = getElement(helpersLinkers[i][1]);
			if (oh) oh.style.display = (obj.style.visibility == 'visible' ? "block" : "none");
		}
	}
}
var focusObject = null;
var changeFocus = 1;
var focusObjectSelected;

function cleanFocusObject() {
	focusObject = null;
}

function onFocusObjectById(objId) {
	if (typeof objId == "undefined") return;
	if (objId == null) return;
	if (changeFocus == 1) {
		focusObject = objId;
		focusObjectSelected = getSelection(focusObject);
	}
	renderHelp();
}

function getSelection(elemName) { // only allow input[type=text]/textarea
	if (elemName == null) return null;
	var elem = document.getElementById(elemName);
	if (elem == null) return null;
	if (elem.tagName === "TEXTAREA" || (elem.tagName === "INPUT" && elem.type === "text")) {
		return elem.value.substring(elem.selectionStart, elem.selectionEnd);
		// or return the return value of Tim Down's selection code here
	}
	return null;
}

function onFocusObject(obj) {
	if (!obj || obj.id==null)
		console.log("set focus null");
	if (changeFocus == 1) {
		focusObject = obj ? obj.id : null;
	}
	if (obj!=null && (obj.tagName.toLowerCase() === 'input' || obj.tagName.toLowerCase() === 'textarea'))
		obj.select();
	renderHelp();
}

function getFocusObject() {
	return focusObject;
}

function forceFocusObject() {
	if (getFocusObject()) {
		var pub = document.getElementById(getFocusObject());
		if (pub) {
			pub.focus();
			pub.focus(); // por bug del explorer
			if (focusObjectSelected != null && focusObjectSelected != '') pub.select();
		}
	}
}

function renderHelp() {
	if (helpersLinkers == null) return;
	for (i = 0; i < helpersLinkers.length; i++) {
		renderHelpBox(helpersLinkers[i][1], helpersLinkers[i][4], helpersLinkers[i][0], helpersLinkers[i][2], helpersLinkers[i][3], helpersLinkers[i][5], helpersLinkers[i][6], document.getElementById(focusObject));
	}
}

function getElement(zElementId) {
	if (document.ie4) return document.all[zElementId];
	if (document.ns4) return document.layers[zElementId];
	if (document.ns6) return document.getElementById(zElementId);
	return document.getElementById(zElementId);
}

function subscribeHelpBox(helpBox, position, obj, dx, dy, dz, publ) {
	linkWithHelp(obj, helpBox, position, dx, dy, dz, publ);
	renderHelpBox(helpBox, position, obj, dx, dy, dz, publ, document.getElementById(focusObject));
}

function renderHelpBox(helpBox, position, obj, dx, dy, dz, publ, focusObject) {
	if (dx > 10000) dx = -(dx - 10000);
	if (dy > 10000) dy = -(dy - 10000);
	// alert("obj ("+obj+") x: "+ (getLeft(obj))+" y: "+getTop(obj));
	var o = null;
	if (position != "absolute" && position != "fixed") o = getElement(obj);
	var oh = getElement(helpBox);
	if (position == "absolute" && oh) {
		px = dx;
		py = dy;
		if (px < -100 || py < -50) { // se ve menos de la mitad
			hide(oh);
		} else oh.style.display = "block";
	} else if (position == "static") {
		if (o && oh) {
			var parent = oh.parentNode;
			if (parent) parent.removeChild(oh);
			o.appendChild(oh);
		} else {
			hide(oh);
		}
	} else if ((position == "relative" || position == "onfocus") && o && oh) {
		var px;
		var py;
		if (publ == 1) {
			px = getLeft(obj) + getWidth(obj) + dx;
			py = getTop(obj) + dy;
		} else {
			px = (getLeft(obj) + dx);
			py = (getTop(obj) - (getHeight(helpBox))) + dy;
		}
		if (px < -100 || py < -50) { // se ve menos de la mitad
			hide(oh);
		} else {
			oh.style.left = px < 0 ? 0 : px;
			oh.style.top = py < 0 ? 0 : py;
			if (position == "onfocus") {
				if (focusObject) {
					oh.style.display = (o.id == focusObject.id) ? "block" : "none";
				} else oh.style.display = "none";
			} else oh.style.display = (o.style.visibility == '' || o.style.visibility == 'visible') ? "block" : "none";
		}
		// alert("obj ("+obj+") x: "+ px+" y: "+py +" v:"+oh.style.display);
	} else if (position == "fixed" && oh) {
		oh.style.left = dx;
		oh.style.top = dy;
		oh.style.display = "block";
	} else {
		hide(oh);
	}
	if (dz == -1) {
		if (oh.style.top.indexOf("px") == -1) {
			if (oh.style.top < 80) oh.style.zIndex = 100;
			else oh.style.zIndex = publ == 1 ? 0 : 100;
		} else {
			var intTop = parseInt(oh.style.top.substr(0, oh.style.top.indexOf("px")))
			if (intTop < 80) oh.style.zIndex = 100;
			else oh.style.zIndex = publ == 1 ? 0 : 100;
		}
	} else {
		oh.style.zIndex = dz;
	}
	oh.style.position = 'absolute';
}

function removeHelpBox(helpBox, position, obj, dx, dy) {
	var oh = getElement(helpBox);
	if (oh) {
		var parent = oh.parentNode;
		if (parent) {
			parent.removeChild(oh);
		}
	}
}

function hide(obj) {
	if (!obj) return;
	obj.style.display = "none";
}

function show(obj) {
	obj.style.display = "block";
}

function createElement(type, parent) {
	var el = null;
	if (document.createElementNS) {
		el = document.createElementNS("http://www.w3.org/1999/xhtml", type);
	} else {
		el = document.createElement(type);
	}
	if (typeof parent != "undefined") {
		parent.appendChild(el);
	}
	return el;
}
/*
 * encodes the string passed in val argument if it contains characters which are
 * 'unsafe' to be sent as part of an URL
 *
 * var unsafeString = "\"<>%\\^[]`\+\$\,="; function encode(val) { var len =
 * val.length; var i = 0; var newStr = ""; var original = val;
 *
 * for (i=0;i<len;i++) if (val.substring(i,i+1).charCodeAt(0) < 255) if
 * (isUnsafe(val.substring(i,i+1)) == false) newStr = newStr +
 * val.substring(i,i+1); else newStr = newStr + escape(val.substring(i,i+1));
 * else // error newStr = original; i=len; return newStr; }
 */
function isUnsafe(compareChar) {
	return !(unsafeString.indexOf(compareChar) == -1 && compareChar.charCodeAt(0) > 32 && compareChar.charCodeAt(0) < 123)
}

function analizeTable(infotablr) {
	var first_pos=0;
	while (true) {
		var pos = infotablr.indexOf(";th_",first_pos);
		if (pos==-1) break;
		var porparA = infotablr.indexOf("(",pos);
		if (porparA==-1) break;
		var porparC = infotablr.indexOf(")",porparA);
		if (porparC==-1) break;
		var nameTable = infotablr.substring(pos+4,porparA);
		var info = infotablr.substring(porparA+1,porparC);
		first_pos=porparC+1;
		var pos=0;
		var regtable = getTableRegistered(nameTable);
		if (regtable==null) continue;
		var table = regtable.htmlTableId;
		var apos=0;
		while (true) {
			var posTh=info.indexOf("p_",apos);
			if (posTh==-1) break;
			var posThE=info.indexOf("=",posTh);
			if (posThE==-1) break;
			var realPos = info.substring(posTh+2,posThE);
			apos=posThE;
			var th = $('#'+table).find('th').eq(pos);
			if (th==null || $(th).length==0) break;
			if (!($(th).closest('thead').hasClass("tableFloatingHeaderOriginal")) &&
			    !($(th).closest('table').hasClass("table-grid")) 
						  ) break;
			if (($(th).hasClass("column-html")) ) break;
			var thok =  $('#'+table).find('th[data-pos=\"'+(realPos)+'\"]')[0];
			if (thok==null  ||$(thok).length==0) break;
			var posA=pos;// parseInt(($(th).attr("data-pos")))-1;
			var posB=$(thok).parent().children().index(thok);
			if (posA!=posB) {
				switchColumns($('#'+table),posA,posB);
			}	
			
			var marca ="p_"+($(thok).attr("data-pos"))+"=";
			if (marca==-1) return;
			var posancho = info.indexOf(marca)+marca.length;
			if (posancho==-1) return;
			var posfinancho = info.indexOf(",",posancho);
			if (posfinancho==-1) return;
			var ancho = info.substring(posancho,posfinancho);
			if (ancho=='I')
				hideColumns($('#'+table), $(thok)[0].id )	
			else {
				adjustWidthColumn(table,thok,ancho);
// $(thok).width(ancho);
			}
			pos++;
		}
		
	}
	
}
function analizeModal(infotablr) {
	var first_pos=0;
	while (true) {
		var pos = infotablr.indexOf(";mm_",first_pos);
		if (pos==-1) break;
		var porparA = infotablr.indexOf("(",pos);
		if (porparA==-1) break;
		var porparC = infotablr.indexOf(")",porparA);
		if (porparC==-1) break;
		first_pos=porparC+1;
		var nameModal = infotablr.substring(pos+4,porparA);
		var info = infotablr.substring(porparA+1,porparC);
		var poscoma = info.indexOf(",");
		if (poscoma==-1) return;
		var left = info.substring(0,poscoma);
		var top = info.substring(poscoma+1);
		var obj = $('#'+nameModal).first();
		if (!obj || (top==0 && left==0)) return;
		obj.offset({
	        'left': left,
	        'top': top
	    });
	}
	
}
function firstFocus(lastFocus) {
	if (lastFocus!=null && lastFocus!='') {
		var pos = lastFocus.indexOf("focus=");
		var posPC = lastFocus.indexOf(";",pos);
		if (pos!=-1) {
			var objname = posPC==-1? lastFocus.substring(pos+6): lastFocus.substring(pos+6,posPC);
			var obj = document.getElementById(objname);
			if ($(obj).is("span"))
				  obj = $(obj).parent()[0];

			if (obj) {
				obj.focus(); 
				obj.focus();
			}
			return;
		}
	}

	 setTimeout(function(){
		 lastModal = getLastModal();
		 var nextfocus = $("#"+lastModal).find('input,select,textarea,.select2-selection--single')
	     .not('input[type=hidden],input[type=button],input[type=submit],input[type=reset],input[type=image],button')
	     .filter(':visible:enabled:first');
		  if (nextfocus.is("span"))
			  nextfocus=(nextfocus.parent().find(".select2-selection__rendered")[0]);

		  nextfocus.focus();
		}, 500);
	// var one=null;
	// var two=null;
	// var tree=null;
	// allForms = document.forms;
	// if( allForms.length > 0 ) {
	// found = false;
	// for( j=0; allForms.length > j; j++ ) {
	// var TForm = allForms[j];
	// var oChild;
	// for( i=0; TForm.length > i; i++ ) {
	// oChild = TForm.elements[i];
	// if (oChild.autofocus) {
	// oChild.focus();
	// oChild.focus(); // negrada para que funcione en explrorer
	// found = true;
	// return;
	// }
	// if (found) continue;
	// sType = oChild.type;
	// if (two==null && sType=='text' && oChild.style.vis71ibility!='hidden')
	// two=oChild;
	// if((sType=='text') || (sType=='textarea') || (sType=='password') ||
	// (sType=='select') || (sType=='select-one') || (sType=='checkbox') ||
	// (sType=='radiobutton')) {
	// // alert("testing for focus: "+oChild.name);
	// if (one==null) one=oChild;
	// if (!oChild.readOnly && oChild.style.visibility!='hidden') {
	// // alert("focusing");
	// oChild.focus();
	// oChild.focus(); // negrada para que funcione en explrorer
	// // oChild.select();
	// found = true;
	// }
	// }
	// }
	// }
	// }
	// 
	// if (found==false && two!=null) {
	// two.focus();
	// two.focus();
	// if (!two.readOnly) two.select();
	// }
	// else if (found==false && one!=null) {
	// one.focus();
	// one.focus();
	// if (!one.readOnly) one.select();
	// }
}
var parentTabId = null;

function getParentTabId() {
	return parentTabId;
}

function setParentTabId(parentTab) {
	parentTabId = parentTab;
}

function create_UUID(force) {
	// if (window.location.href.indexOf("&")!=-1) {
	// var sess =get_url_param('session',window.location.href); // name
	// if (sess!=null && sess!=''&& sess!='undefined') return sess;
	// }
	if (!force) {
		var pos = getParentTabId();
		if (getParentTabId() != null) {
			return getParentTabId();
		}
	}
	var dt = new Date().getTime();
	var uuid = 'xxxxxxxx-xxxx-9xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
		var r = (dt + Math.random() * 16) % 16 | 0;
		dt = Math.floor(dt / 16);
		return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
	});
	return uuid;
}

function prepareLogin() {
	adjustSessionID(true);
}

function adjustSessionID(force) {
  var cookie = document.cookie;
  var strgCookie = sessionStorage.getItem("TABID");
  if (force || (strgCookie == null || strgCookie == "undefined" || strgCookie == '')) {
    var id = create_UUID(force);
    sessionStorage.setItem("TABID", id);
    document.cookie = 'TABID=' + id +";path=/";
    return id;
  } else {
    document.cookie = 'TABID=' + strgCookie+";path=/";
    return strgCookie;
  }
}

var lastOp = "";
function goTo(zSource, zUrl, zIsSubmit, zActionOwnerProvider, zObjectOwnerId, zAjaxContainer, zResolveString, action, asinc, cancelable, event, cfg, uploadData, dataasoc, specialselector, backOnPrint, refreshOnPrint, contextobj,zIsBackAfterSubmit, runextraaction) {
	adjustSessionID();
	clearClick()
	waitPendingUpdates();
	var formToSubmit;
	if (uploadData) {
		formToSubmit = document.mainform;
	} else {
		formToSubmit = document.navform;
	}
	if (zSource != null) controlReclick(zSource);
	if (lastOp == zUrl && isAjaxWorking()) return;
	lastOp = zUrl;
	clearClick();
	
	// alert("url:"+zUrl+" zIsSubmit:"+zIsSubmit+"
	// zActionOwnerProvider:"+zActionOwnerProvider+"
	// zObjectOwnerId:"+zObjectOwnerId+" zAjaxContainer:"+ zAjaxContainer+"
	// zResolveString:"+ zResolveString+" action:"+ action);
	return goToViaForm(zUrl, formToSubmit, zActionOwnerProvider, zObjectOwnerId, zAjaxContainer, zResolveString, action, asinc, cancelable, event, zIsSubmit, zIsBackAfterSubmit, cfg, uploadData, dataasoc, specialselector, backOnPrint, refreshOnPrint, runextraaction, contextobj);

}

function goToMenuAction(zIdAction, new_window) {
	if (new_window == "true") goToViaForm('do-ViewAreaAndTitleAction', document.navform, '', '', 'NEW_WINDOW', null, zIdAction, true, true);
	else goToViaForm('do-ViewAreaAndTitleAction', document.navform, '', '', 'view_area_and_title', null, zIdAction, true, true);
}

function goToMenuActionResponsive(zIdAction, new_window) {
	if (new_window == "true") goToViaForm('do-menuPanelsAction', document.navform, '', '', 'NEW_WINDOW', null, zIdAction, true, true);
	else goToViaForm('do-menuPanelsAction', document.navform, '', '', 'view_area_and_title', null, zIdAction, true, true);
}

function goToSaveHelp(fieldName, x, y, zWin, zAction, status, focus) {
	var texto = prompt('Ayuda (' + fieldName + ')');
	var rx = x;
	var ry = y;
	if (texto == '' || texto == null) return false;
	var st = status;
	var ztype = "4";
	if (st && st != '') {
		st = prompt('Status: para ignorar completar con * ', st);
	}
	if (focus == 1) ztype = "1";
	// ztype = prompt('2=Static 3=Absolut 4=Relative 5=Fixed');
	// calcular X Y correctos
	if (focus == 2 && fieldName != null) {
		rx = 50;
		ry = 10;
	} else if (ztype != "3" && fieldName != null) {
		rx = x - getLeft(fieldName);
		ry = y - getTop(fieldName);
	}
	var ajax = new ZAjaxSubmit("do-saveHelp", false);
	ajax.addUrlParameters(getUrlParameter("dg_texto", texto));
	ajax.addUrlParameters(getUrlParameter("dg_x", rx));
	ajax.addUrlParameters(getUrlParameter("dg_y", ry));
	ajax.addUrlParameters(getUrlParameter("dg_fieldname", fieldName));
	ajax.addUrlParameters(getUrlParameter("dg_typepos", ztype));
	ajax.addUrlParameters(getUrlParameter("dg_win", zWin));
	ajax.addUrlParameters(getUrlParameter("dg_action", zAction));
	ajax.addUrlParameters(getUrlParameter("dg_status", st));
	ajax.addUrlParameters(ajax.setAjaxContainer(""));
	ajax.setAjaxCallback(nullFetch, genericErrorMessage);
	ajax.fetch(nullFetch, genericErrorMessage);
	return false;
}

function nullFetch() {}

function goToRefreshForm(zUrl, fieldName, provider, ownerId, zAjaxContainer, actionId, dataasoc,context) {
	if (fieldName!=null)
		document.mainform.dg_source_control_id.value=fieldName;
	nextFocusH($('#'+fieldName)[0],+1,true);
	goToViaForm(zUrl, getFormInContext(zUrl), provider, ownerId ,zAjaxContainer , null, actionId, false, false,null, true, true, null, true, dataasoc, null, false, false, null, context);
}
function getFormInContext(context) {
	return  context.indexOf('do-WinListRefreshAction')!=-1? document.navform: document.mainform;
}

function setupOneCalendarEditors(oCurrCalSetup) {
	var textbox = document.getElementById(oCurrCalSetup[0]).style;
	var trigger = document.getElementById(oCurrCalSetup[0] + "_trigger").style;
	var textboxHeight = oCurrCalSetup[2] - 0;
	var margin = (textboxHeight - parseDimension(trigger.height)) / 2
	trigger.top = parseDimension(textbox.top) + margin;
	Calendar.setup({
		inputField: oCurrCalSetup[0],
		ifFormat: oCurrCalSetup[1],
		daFormat: oCurrCalSetup[1],
		button: oCurrCalSetup[0] + "_trigger",
		singleClick: false
	});
};

function setupCalendarEditors() {
	if (document.calendarsToSetup) {
		var oCurrCalSetup;
		var iCounter;
		for (iCounter = 0; iCounter < document.calendarsToSetup.length; iCounter++) {
			setupOneCalendarEditors(document.calendarsToSetup[iCounter]);
		}
	}
}

function setupZTables() {
	if (document.zTablesToSetup) {
		var oCurrTable;
		var iCounter;
		for (iCounter = 0; iCounter < document.zTablesToSetup.length; iCounter++) {
			oCurrTable = document.zTablesToSetup[iCounter];
			if (oCurrTable.registerTable()) registerObjectProvider(oCurrTable.getObjectProvider(), oCurrTable);
			oCurrTable.setup();
		}
		document.zTablesToSetup = null;
	}
}

function backListener() {
	window.addEventListener('popstate', function (e) {
		// if (e.state=='console')
		goBack(this, e);
	});
	$(window).bind("beforeunload", function (event) {
		adjustSessionID();
		if (checkCloseBrowser()) {
			var message = 'Are you sure?'; // este mensaje no lo ve nadie, los
			// viejos browser lo mostraban los
			// nuevos muestran un mensaje del
			// browser
			event.returnValue = message;
			return message;
		}
	});
	document.onkeydown = captureF5;
	document.onkeypress = captureF5;
	document.onkeyup = captureF5;
		if (isMS()) {
	
	// explorer no diferencia entre refresh y close
			
		} else {
			$(window).bind("unload", function (event) {
				adjustSessionID();
				if (isLogin())
					goClose();
			});
			
		}
}

function captureF5(e) {
	e = e || window.event;
	if (e.code == 'F5') {
		adjustSessionID();
		e.preventDefault(); 
		if (!e.shiftKey) {
			showWorkingPaneIn('view_area_and_title',true);
			goRefresh(); 
		} else {
			const currentUrl = new URL(window.location.href);
		    const originalParams = localStorage.getItem('originalParams') || '';
		    window.location.href = `./html/redirect.html${originalParams}`;
			
		}

	}
}



function goBack(form, event) {
	wait(2000);
	goTo(form, 'do-BackToQueryAction', false, '', null, 'view_area_and_title', '', '', true, true, event)
}

function goBackAction() {
	wait(2000);
	goTo(document.mainform, 'do-BackAction', false, '', null, 'view_area_and_title', '', '', true, true, null)
}

function goRefresh() {
	wait(2000);
	goTo(this, 'do-RefreshAction', false, '', null, 'view_area_and_title', '', '', true, true);
}

function goClose() {
	goTo(this, 'closed?persistence=Y&', false, '', null, 'view_area_and_title', '', '', true, true);
}

function wait(ms) {
	var start = new Date().getTime();
	var end = start;
	while (end < start + ms) {
		end = new Date().getTime();
	}
}
// /////////////////////////////////////////////////////////////////////
//
// INTERNAL UTILITIES
//
// /////////////////////////////////////////////////////////////////////
/*
 * returns the string reprsenting a Pss Web action data for the current tree
 * selection
 */
function getTreeSelectionString() {
	if (document.treeDataById != null) {
		var sSel = GetCookie("highlightedTreeviewLink");
		var sRes = document.treeDataById[parseInt(sSel)];
		return sRes;
	} else {
		return "";
	}
}

function getURLParameters(paramName) {
	var sURL = window.document.URL.toString();
	if (sURL.indexOf("?") > 0) {
		var arrParams = sURL.split("?");
		var arrURLParams = arrParams[1].split("&");
		var arrParamNames = new Array(arrURLParams.length);
		var arrParamValues = new Array(arrURLParams.length);
		var i = 0;
		for (i = 0; i < arrURLParams.length; i++) {
			var sParam = arrURLParams[i].split("=");
			arrParamNames[i] = sParam[0];
			if (sParam[1] != "") arrParamValues[i] = unescape(sParam[1]);
			else arrParamValues[i] = "";
		}
		for (i = 0; i < arrURLParams.length; i++) {
			if (arrParamNames[i] == paramName) {
				// alert("Param:"+arrParamValues[i]);
				return arrParamValues[i];
			}
		}
		return "";
	}
}
/*
 * goes to the given URL, submitting the given form
 */
function goToViaForm(zUrl, zFormToSubmit, zActionOwnerProvider, zObjectOwnerId, zAjaxContainer, zTheObjectResolveString, zIdAction, asinc, cancelable, event, issubmit,isbackaftersubmit, cfg, uploadData, dataasoc, specialselector, backOnPrint, refreshOnPrint, runextraaction, contextobj) {
	if (uploadData && issubmit) {
		if (zIdAction != null && zUrl.indexOf('do-PartialRefreshForm') == -1 && // refresh
			// parcial(buscar otra
			// manera)
			!validationForm(zActionOwnerProvider)) {
			disablear();
			return;
		}
	}
	if (isAjaxWorking()) {
		disablear();
		return;
	}
	if (zAjaxContainer != '') {
		document.getElementById("navform").dg_dictionary.value=   sessionStorage.getItem('dictionary');
		document.getElementById("mainform").dg_dictionary.value=  sessionStorage.getItem('dictionary');
	}
	if (runextraaction) runextraaction();
	// setChangeInputs(false);
	// alert("url:"+zUrl);
	// alert("form:"+zFormToSubmit.name);
	// alert("ajax: "+zAjaxContainer);
	addCurrentPageSizeParameters(zFormToSubmit, zAjaxContainer);
	if (!addActionOwnerParameters(zUrl, zFormToSubmit, zActionOwnerProvider, zTheObjectResolveString, zObjectOwnerId, zIdAction, event, specialselector, contextobj, zAjaxContainer)) {
		disablear();
		return;
	}
	zUrl = addFormLovParameters(zUrl);
	if (zAjaxContainer != 'NEW_WINDOW' && zAjaxContainer != 'NEW_WINDOW_REFRESH') addLoginParameters(zUrl, zFormToSubmit);
	addTreeParameters(zFormToSubmit);
	// addRegisterToURLFromList(""); aparentemente no hace nada
	zFormToSubmit.method = 'POST';
	if (zAjaxContainer == '') {
		zFormToSubmit.action = zUrl;

		if (zUrl == 'closed_subsession') {
			zFormToSubmit.submit();
			//setTimeout(function () {
				window.open('', '_parent', '');
				window.close();
			//}, 500);
		} else zFormToSubmit.submit();
		return;
	}
	if (isAjaxDelete(zUrl)) {
		// alert("is WinListDelete:
		// "+document.getElementById(document.navform.dg_object_owner.value).innerHTML);
		var ajax = new ZAjaxSubmit(zUrl, true);
		ajax.addUrlParameters(getUrlParameter("issubmit", issubmit));
		ajax.addUrlParameters(getMetaFormParameters(zFormToSubmit));
		ajax.addUrlParameters(getHiddenFormParameters(zFormToSubmit));
		if (document.navform.dg_multiple_owner_list.value) {
			zAjaxContainer = document.navform.dg_multiple_owner_list.value;
		} else {
			zAjaxContainer = document.navform.dg_object_owner.value;
		}
		if (cfg) ajax.addUrlParameters(cfg);
		ajax.addUrlParameters(ajax.setAjaxContainer(zAjaxContainer));
		ajax.setAjaxCallback(refreshListener, genericErrorMessage);
		if (cfg) ajax.addUrlParameters(cfg);
		ajax.fetch();
		disablear();
		return
	}
	unRegisterTable(zActionOwnerProvider);
	var ajax = new ZAjaxSubmit(zUrl, cancelable);
	ajax.addUrlParameters(getUrlParameter("issubmit", issubmit));
	ajax.addUrlParameters(getUrlParameter("isbackaftersubmit", isbackaftersubmit));
	ajax.addUrlParameters(getUrlParameter("data_asoc", dataasoc));
	if (cfg) ajax.addUrlParameters(cfg);
	if (isAjaxSubmit(zUrl)) {
		ajax.addUrlParameters(getContainerDimensionParameter(zAjaxContainer));
		ajax.addUrlParameters(getMetaFormParameters(zFormToSubmit));
		ajax.addUrlParameters(getHiddenFormParameters(zFormToSubmit));
		ajax.addUrlParameters(getWinListFilters());
	}
	if (zFormToSubmit == document.navform) {
		ajax.addUrlParameters(getMetaFormParameters(zFormToSubmit));
		ajax.addUrlParameters(getHiddenFormParameters(zFormToSubmit));
		ajax.addUrlParameters(getWinListFilters());
		if (zUrl.indexOf('do-WinLovAction') == 0) ajax.addUrlParameters(formDataRegisterToUrl(document.getElementById("mainform"), ""));
		if (uploadData) ajax.addUrlParameters(addRegisterToUrl("", 500));
	}
	if (zFormToSubmit == document.mainform) {
		ajax.addUrlParameters(getMetaFormParameters(zFormToSubmit));
		ajax.addUrlParameters(getFormControlsParameters(zFormToSubmit));
	}
	if (zAjaxContainer.indexOf('PRINT_WINDOW') != -1 || zAjaxContainer.indexOf('NEW_WINDOW') != -1) {
		if (zUrl == 'do-login' || zUrl == 'do-login-ini') {
			var newConf = zFormToSubmit.dg_client_conf.value + ',sw=' + 800 + ',sh=' + 600;
			zFormToSubmit.dg_client_conf.value = newConf;
			zFormToSubmit.subsession.value = getURLParameters("subsession");
		}
		zFormToSubmit.dg_client_conf.value = 'pw=' + 800 + ',ph=' + 600 + ", mode=NEW_WINDOW";
		ajax.addUrlParameters(getMetaFormParameters(zFormToSubmit));
		ajax.addUrlParameters(getHiddenFormParameters(zFormToSubmit));
		ajax.addUrlParameters(getWinListFilters());
		openInNewWindowPre(zFormToSubmit, ajax, (zAjaxContainer.indexOf('NEW_WINDOW_REFRESH') != -1) ? windowsParentLoaded : ((zAjaxContainer.indexOf('NEW_WINDOW_CLOSE') != -1) ? windowsLoadedClose : ((zAjaxContainer.indexOf('PRINT_WINDOW_REFRESH') != -1) ? windowsLoaded : ((zAjaxContainer.indexOf('PRINT_WINDOW_CLOSE') != -1) ? windowsLoadedClose : null))), zAjaxContainer.indexOf('PRINT_WINDOW_REFRESH') != -1 || zAjaxContainer.indexOf('PRINT_WINDOW_CLOSE') != -1 || zAjaxContainer.indexOf('NEW_WINDOW_CLOSE') != -1, zAjaxContainer.indexOf('PRINT_WINDOW') != -1, zAjaxContainer.indexOf('MAX') != -1);
		disablear();
		if ((zAjaxContainer.indexOf('NEW_WINDOW_REFRESH') == -1) && (zAjaxContainer.indexOf('PRINT_WINDOW_CLOSE') == -1) && (zAjaxContainer.indexOf('NEW_WINDOW_CLOSE') == -1)) {
			releaseSemaphore();
		}
		return;
	}
	if (zAjaxContainer.indexOf('NEW_SESSION') != -1) {
		var d = new Date();
		var n = d.getMilliseconds();
//		if (cfg) ajax.addUrlParameters(cfg);
//		ajax.setAjaxContainer(zAjaxContainer);
//		ajax.addUrlParameters(getMetaFormParameters(zFormToSubmit));
//		ajax.addUrlParameters(getHiddenFormParameters(zFormToSubmit));
//		ajax.addUrlParameters(getWinListFilters());
//		ajax.deleteUrlParameters("subsession");
//		ajax.addUrlParameters(getUrlParameter("subsession", n));
//		sendEcho(function () {
//			openInNewWindowAcc(zFormToSubmit, ajax.getFinalUrlWithParameters(), zAjaxContainer.indexOf('MAX') != -1, setParentTab());
	//	});
		const currentUrl = new URL(window.location.href);
	    const originalParams = localStorage.getItem('originalParams') || '';
	    openInNewWindowAcc(null, `./html/redirect.html${originalParams}`, zAjaxContainer.indexOf('MAX') != -1, setParentTab());
	    releaseSemaphore();
		disablear();
		return;
	}
	if (zAjaxContainer == 'ONLY_URL') {
		var d = new Date();
		var n = d.getMilliseconds();
		if (cfg) ajax.addUrlParameters(cfg);
		ajax.setAjaxContainer(zAjaxContainer);
		ajax.addUrlParameters(getMetaFormParameters(zFormToSubmit));
		ajax.addUrlParameters(getHiddenFormParameters(zFormToSubmit));
		ajax.addUrlParameters(getWinListFilters());
		ajax.deleteUrlParameters("dg_dictionary");
		return ajax.getFinalUrlWithParameters();
	}
	if (cfg) ajax.addUrlParameters(cfg);
	ajax.addUrlParameters(getUrlParameter("back_on_print", backOnPrint));
	ajax.addUrlParameters(getUrlParameter("refresh_on_print", refreshOnPrint));
	ajax.addUrlParameters(getContainerDimensionParameter(zAjaxContainer));
	ajax.addUrlParameters(ajax.setAjaxContainer(zAjaxContainer));
	showWorkingPane(isModalContainer(zAjaxContainer) ? getLastModal() : zAjaxContainer);
	ajax.setAjaxCallback(refreshListener, refreshErrorListener);
	ajax.fetch(asinc);
	selectedRowForce = null;
}
var check = -1;
var timer = 10000;
var marcaRefresh = null;

function iniciarChecker(timer, zMarca) {
	marcaRefresh = zMarca;
	if (check == -1) check = setInterval('checker()', timer);
}

function reIniciarChecker() {
	if (check == -1) check = setInterval('checker()', timer);
}

function detenerChecker() {
	if (check != -1) clearInterval(check);
	check = -1;
}

function checker() {
	var now = new Date();
	var datetime = now.getFullYear() + '_' + (now.getMonth() + 1) + '_' + now.getDate();
	datetime += ' ' + now.getHours() + ':' + now.getMinutes() + ':' + now.getSeconds();
	detenerChecker();
	var objAjax = $.ajax({
		"type": "post",
		"url": "do-checkRefresh",
		"data": {
			"marca": marcaRefresh,
			"lastupdate": datetime
		},
		"dataType": "html",
		"cache": false,
		"success": function (msg, ajaxOptions, objAjax) {
			checkMessage(msg)
		},
		"error": function (xhr, ajaxOptions, thrownError) {},
	});
	// ajaxXmlHttp2=GetXmlHttpObject();
	// if (ajaxXmlHttp2==null){
	// return;
	// }
	// ajaxXmlHttp2.onreadystatechange = checkMessage;
	// ajaxXmlHttp2.open("POST","do-checkRefresh",true);
	// ajaxXmlHttp2.setRequestHeader("Content-type",
	// "application/x-www-form-urlencoded");
	// ajaxXmlHttp2.send("marca="+marcaRefresh+"&lastupdate="+datetime);
}

function checkMessage(response) {
	if (response.indexOf("REFRESH") != -1) {
		goTo(this, 'do-RefreshAction', false, '', null, 'view_area_and_title', '', '', true, true);
	}
	reIniciarChecker();
}

function setParentTab() {
	setParentTabId(parent.adjustSessionID());
}

function windowsParentLoaded() {
	parent.windowsLoaded();
}

function windowsLoaded() {
	parent.releaseSemaphore();
	parent.disablear();
	parent.goTo(this, 'do-RefreshAction', false, '', null, 'view_area_and_title', '', '', true, true);
}


function windowsParentLoadedClose() {
	parent.windowsParentLoadedClose();
}

function windowsParentLoadedClose() {
	parent.releaseSemaphore();
	parent.disablear();
	parent.goBackAction();
}

function windowsLoadedClose() {
	releaseSemaphore();
	disablear();
	goBackAction();
}

function addActionOwnerParameters(zUrl, zFormToSubmit, zActionOwnerProvider, zTheObjectResolveString, zObjectOwnerId, zIdAction, event, specialselector, contextobj,zAjaxContainer) {
	// alert("provider: "+zActionOwnerProvider);
	// alert("ownerId: "+zObjectOwnerId);
	// alert("action: "+zIdAction);
	// alert("resolve: "+zTheObjectResolveString);
	// set the action owner object if needed
	var sTheObjectResolveString = null;
	var zObjectOwnerDest = null;
	var sMultipleOwnerList = "";
	var sMultipleOwnerListDest = "";
	var bHasMultipleOwner = false;
	var zCellSelect = "";
	var zRowSelect = "";
	var zObjectSelectId = "";
	var zClearSelection = "0";
	var sScroller = getInfoContext(); /* getElement(zActionOwnerProvider)?getElement(zActionOwnerProvider).scrollTop:0; */
	var embedded = false;
	var objectDrop = !event || !event.dataTransfer ? null : event.dataTransfer.getData("object");
	if (objectDrop) {
		// var oProvider = getObjectProvider(zActionOwnerProvider);
		zObjectSelectId = zObjectOwnerId; // listener
		zObjectOwnerId = objectDrop; // drop
	} else if (zTheObjectResolveString != null && zTheObjectResolveString.length > 0) {
		sTheObjectResolveString = zTheObjectResolveString;
	} else {
		sTheObjectResolveString = '';
		if (zActionOwnerProvider != null && zActionOwnerProvider.length > 0) {
			var oProvider = getObjectProvider(zActionOwnerProvider);
			// alert("getObjectProvider="+oProvider);
			if (oProvider != null) {
				if (!(oProvider instanceof ZForm) && specialselector != null && specialselector != '') {
					zObjectOwnerId = oProvider.getCurrentActionOwnerFromSelect();
					zObjectOwnerDest = oProvider.getMultipleCurrentActionOwnerDest();
					sMultipleOwnerList = oProvider.getSelectionSpecial(specialselector);
					sMultipleOwnerListDest = zObjectOwnerDest==null?null:oProvider.getMultipleActionOwnerList();
					bHasMultipleOwner = oProvider.hasMultipleSelectSpecial(specialselector);
					zClearSelection = "0";
				} else if (!oProvider.getClearSelection() && oProvider.hasMoreSelections()) {
					zObjectOwnerId = oProvider.getCurrentActionOwnerFromSelect();
					sMultipleOwnerList = oProvider.getSelection();
					bHasMultipleOwner = oProvider.hasMultipleSelect();
					zClearSelection = oProvider.getClearSelection() ? "1" : "0";
				} else {
					zObjectOwnerId = (zObjectOwnerId != null) ? zObjectOwnerId : oProvider.getCurrentActionOwner();
					zObjectOwnerDest = oProvider.getMultipleCurrentActionOwnerDest();
					sMultipleOwnerList = oProvider.getMultipleActionOwnerList();
					zObjectSelectId = oProvider.getCurrentActionOwnerFromSelect();
					bHasMultipleOwner = oProvider.hasMultipleSelect();
					zClearSelection = oProvider.getClearSelection() ? "1" : "0";
				}
				embedded = (oProvider instanceof ZForm && oProvider.isEmbedded());
				zRowSelect = oProvider.getSelectedRow();
				zCellSelect = oProvider.getSelectedCell();
				if (isWinDependant(zUrl) && zObjectOwnerId == null) {
					alert(global_selectrow);
					disablear();
					return false;
				}
				// alert("zObjectOwnerId: "+zObjectOwnerId);
				// alert("sMultipleOwnerList: "+sMultipleOwnerList);
			}
		} else {
			if (zUrl.indexOf('do-SwapListRefreshAction') != -1 || zUrl.indexOf('do-WinListRefreshAction') != -1 || zUrl.indexOf('do-WinListExpandAction') != -1) {
				var oProvider = getObjectProvider(zActionOwnerProvider);//RJL; codigo muerto? zActionOwner o es null o igual a "" por lo que siempre oProvider devolvera null
				if (oProvider != null) {
					zClearSelection = oProvider.getClearSelection() ? "1" : "0";
					zObjectSelectId = oProvider.getCurrentActionOwnerFromSelect();
					zRowSelect = oProvider.getSelectedRow();
					zCellSelect = oProvider.getSelectedCell();
					sMultipleOwnerList = oProvider.getSelection();
				}
			}
		}
	}
	zFormToSubmit.dg_is_modal.value = inModal() ? "Y" : "N";
	zFormToSubmit.dg_act_owner.value = sTheObjectResolveString;
	zFormToSubmit.dg_action.value = zIdAction;
	zFormToSubmit.dg_object_owner.value = zObjectOwnerId;
	zFormToSubmit.dg_object_owner_dest.value = zObjectOwnerDest;
	zFormToSubmit.dg_object_owner_context.value = contextobj;
	zFormToSubmit.dg_object_select.value = zObjectSelectId;
	zFormToSubmit.dg_clear_select.value = zClearSelection;
	zFormToSubmit.dg_table_provider.value = zActionOwnerProvider;
	zFormToSubmit.dg_cell_select.value = zCellSelect;
	zFormToSubmit.dg_row_select.value = zRowSelect;
	zFormToSubmit.dg_multiple_owner_list.value = sMultipleOwnerList;
	zFormToSubmit.dg_multiple_owner_list_dest.value = sMultipleOwnerListDest;
	zFormToSubmit.dg_is_multiple_owner.value = bHasMultipleOwner;
	zFormToSubmit.dg_scroller.value = sScroller;
	zFormToSubmit.dg_back_modal.value = isBackModal();
	zFormToSubmit.dg_extra_form_data.value = "embedded=" + embedded;
	zFormToSubmit.dg_stadistics.value = stadistics;
	zFormToSubmit.dg_url.value = getURL();
	zFormToSubmit.dg_ajaxcontainer.value = zAjaxContainer;
	
	adjustSessionID();
	return true;
}

function fillJSonRequest(url, action, owner, provider) {
	var cfg = {
		ajaxContainer: 'view_area_and_title',
		dg_dictionary: sessionStorage.getItem('dictionary'),
		subsession: document.navform.subsession.value,
		src_uri: document.navform.src_uri.value,
		src_sbmt: document.navform.src_sbmt.value,
		dg_action: action,
		dg_act_owner: '',
		dg_object_owner: owner,
		dg_object_select: '',
		dg_table_provider: provider,
		dg_clear_select: '0',
		dg_multiple_owner_list: '',
		dg_is_multiple_owner_list: false,
		dg_back_modal: false,
		dg_cell_select: '',
		dg_extra_form_data: 'embedded=false',
	};
	adjustSessionID();
	return collect(cfg, addRegisterToUrl("", 0), getWinListFilters());
}

function addTreeParameters(zFormToSubmit) {
	// always set current tree selection argument and send client conf
	zFormToSubmit.dg_tree_selection.value = getTreeSelectionString();
}

function addLoginParameters(zUrl, zFormToSubmit) {
	if (zUrl == 'do-login' || zUrl == 'do-login-ini') {
		var newConf = zFormToSubmit.dg_client_conf.value + ',sw=' + screen.width + ',sh=' + screen.height;
		zFormToSubmit.dg_client_conf.value = newConf;
		/*
		 * if (getURLParameters("subsession")==null ||
		 * getURLParameters("subsession") == "undefined") { var d = new Date();
		 * var n = d.getMilliseconds(); zFormToSubmit.subsession.value = n; }
		 * else
		 */
		adjustSessionID(true);
		zFormToSubmit.subsession.value = getURLParameters("subsession");
	}
}

function checkCloseBrowser() {
	if (!isLogin()) return false;
	return lostChanges();
}

function isLogin() {
	if (window.location.href.indexOf('do-') != -1) return true;
	if (window.location.href.indexOf('login') != -1) return false;
	if (window.location.href.indexOf('closed') != -1) return false;
	if (window.location.href.indexOf('do') != -1) return false;
	return true;
}

function detectmob() {
	if (navigator.userAgent.match(/Android/i) || navigator.userAgent.match(/webOS/i) || navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/iPad/i) || navigator.userAgent.match(/iPod/i) || navigator.userAgent.match(/BlackBerry/i) || navigator.userAgent.match(/Windows Phone/i)) {
		return true;
	} else {
		return false;
	}
}

function addCurrentPageSizeParameters(zFormToSubmit, zAjaxContainer) {
	if (zAjaxContainer == 'NEW_WINDOW') return;
	if (zAjaxContainer == 'NEW_WINDOW_REFRESH') return;
	var iInnerW = 0;
	var iInnerH = 0;
	if (detectmob()) {
		iInnerW = 1400;
		iInnerH = 1000;
	} else if (document.getElementById(zAjaxContainer) != null) {
		iInnerW = document.getElementById(zAjaxContainer).clientWidth;
		iInnerH = document.getElementById(zAjaxContainer).clientHeight;
	} else if (self.innerWidth) {
		iInnerW = self.innerWidth;
		iInnerH = self.innerHeight;
	} else if (document.documentElement && document.documentElement.clientWidth) {
		iInnerW = document.documentElement.clientWidth;
		iInnerH = document.documentElement.clientHeight;
	} else if (document.body) {
		iInnerW = document.body.clientWidth;
		iInnerH = document.body.clientHeight;
	}
	zFormToSubmit.dg_client_conf.value = 'pw=' + iInnerW + ',ph=' + iInnerH;
}

function refreshErrorListener(ajaxResponse, objajax) {
	// alert("refreshErrorListener: "+ajaxResponse);
	commitUnRegisterTable();
	genericErrorMessage(ajaxResponse);
	endWorkingPaneInErrorContainer(objajax.ajaxContainer);
	// if (document.getElementById('dg_action') &&
	// document.getElementById("notebook")) {
	// storeAjaxNotebook(document.navform.dg_table_provider.value);
	// //alert(document.dg_action.value);
	// }
	// if (document.getElementById(objajax.ajaxContainer)!=null)
	// ejecuteAjaxScripts(document.getElementById(objajax.ajaxContainer).innerHTML);
}

function refreshListener(ajaxResponse, objajax) {
	// alert("container: " + ajaxContainer);
	// alert("response :"+ajaxResponse.substring(1, 2000));
	// alert("response :"+ajaxResponse.substring(2001, 4000));
	// alert("response :"+ajaxResponse.substring(4001, 6000));
	// commitUnRegisterTable();
	if (document.getElementById(objajax.ajaxContainer) != null) document.getElementById(objajax.ajaxContainer).innerHTML = ajaxResponse;
	else alert("Se perdió el mensaje "+objajax.ajaxContainer);
	endWorkingPaneContainer(objajax.ajaxContainer);
	if (document.getElementById('dg_action') && document.getElementById("notebook")) {
		storeAjaxNotebook(document.navform.dg_action.value);
	}
	// ejecuteAjaxScripts(ajaxResponse);
}
// function useNotebookCache(notebookStoreId) {
// //alert("searching in notebook cache "+notebookStoreId);
// if (isAjaxContainerRegistered(notebookStoreId)) {
// //alert("found and using ajax cache");
// var storeHTML = getAjaxContainerStoreEntry(notebookStoreId)
// document.getElementById("notebook").innerHTML=storeHTML ;
// setupCalendarEditors();
// zTable = getObjectProvider(notebookStoreId)
// if (zTable instanceof ZTable) zTable.unselectAllRows(false);
// return true;
// }
// //alert("not using ajax cache");
// return false;
// }
function getObjectProvider(zActionOwnerProviderId) {
	if (document.pssObjectProvidersArr && document.pssObjectProvidersArr != null) {
		return document.pssObjectProvidersArr[zActionOwnerProviderId];
	}
	return null;
}

function registerObjectProvider(zActionOwnerProviderId, zProvider) {
	// alert("registerObjectProvider "+zActionOwnerProviderId);
	if (!document.pssObjectProvidersArr || document.pssObjectProvidersArr == null) {
		document.pssObjectProvidersArr = new Array();
	}
	document.pssObjectProvidersArr[zActionOwnerProviderId] = zProvider;
}

function parseDimension(value) {
	return value.substring(0, value.length - 2) - 0;
}

function insertAfter(oldNode, newNode) {
	if (oldNode.nextSibling) {
		oldNode.parentNode.insertBefore(newNode, oldNode.nextSibling);
	} else {
		oldNode.parentNode.appendChild(newNode);
	}
}

function showHtmlView() {
	var oView = document.getElementById("view");
	alert(oView.innerHTML);
	disablear();
}

function initializeViewArea() {
	cleanFormRegister();
	document.pssObjectProvidersArr = null;
	unRegisterAllTables();
	clearAjaxContainer();
	cleanComboRegistry();
	cleanFormLovRegistry();
	cleanResponsiveFormLovRegistry();
	cleanLinkWithHelp();
	cleanCalculateScriptRegistry();
	cleanStartedScriptRegistry();
	unsubscribeControlConnect();
	document.calendarsToSetup = null;
	cleanRegisterScroll();
	cleanRegisterRTE();
	cleanSubmitAndCancel();
	clearRow_subdetail();
	clearFunctions();
	unRegisterRender3d();
	removeAffixObject();
	cleanMemoryArray();
}
var regRTE = new Array();

function clearFunctions() {
	anchorsF = new Array();
	for (i = 0; i < 12; i++) {
		anchorsF[i] = null;
	}
}

function getAnchorsF() {
	if (!anchorsF) clearFunctions();
	return anchorsF;
}

function cleanRegisterRTE(name) {
	regRTE = new Array();
}

function getRegisteredRTE(name) {
	for (i = 0; i < regRTE.length; i++) {
		currAss = regRTE[i];
		if (currAss != null && currAss.getid() == name) {
			return currAss;
		}
	}
	return null;
}

function addrte(zone, name, content, ronly, formulario, isweb, margenIzq, margenimgsup, margenimgleft, margenimgsize, fondo, ancho, mapa, source) {
	// if (getRegisteredRTE(name)!=null) {
	// return;
	// }
		var pos = regRTE.length;
		regRTE[pos] = new WYSIWYG_Editor(zone, name, unescape(unescape(content)), ronly, formulario, isweb, mapa, source, margenIzq, margenimgsup, margenimgleft, margenimgsize, fondo, ancho);
		regRTE[pos].display();
	
}

function myConfirm(message) {
	disablear();
	return confirm(message);
}

function openInNewWindowMax(zFormToSubmit, zUrl, max) {
	openInNewWindow(zFormToSubmit, zUrl, null, false, false, max);
}

function openInNewWindowAcc(zFormToSubmit, zUrl, max, postAccion) {
	openInNewWindow(zFormToSubmit, zUrl, postAccion, false, false, max);
}

function openInNewWindowPre(zFormToSubmit, ajax, postAccion, runOnParent, print, full) {
	if (Object.keys(ajax.getFinalParams()).length > 60) {
		openInNewWindowPost(zFormToSubmit, ajax.getFinalUrl(), ajax.getFinalParams(), postAccion, runOnParent, print, full);
	} else {
		openInNewWindow(zFormToSubmit, ajax.getFinalUrlWithParameters(), postAccion, runOnParent, print, full);
	}
}

function openInNewWindowPost(zFormToSubmit, zUrl, obj, postAccion, runOnParent, print, full) {
	height = screen.height - 200;
	width = screen.width - 100;
	var size = ',width=' + width + ',height=' + height;
	// var posicion = ",left="+(screen.width - width)/ 2+",top="+(screen.height -
	// height) / 2;
	var posicion = ",left=50,top=50";
	if (full) {
		posicion = ",left=" + screen.availLeft + ",top=" + screen.availTop;
		size = ',width=' + screen.availWidth + ',height=' + (screen.availHeight - 40) + ",fullscreen=yes"; //fullscreen solo IE
	}
	var winName = 'OpenWin';
	var winURL = zUrl;
	var windowoption = ',scrollbars=yes,resizable=yes,menubar=no,location=no,toolbar=no,status=no,directories=no,titlebar=no,' + size + posicion;
	var params = obj;
	var form = document.createElement("form");
	form.setAttribute("method", "post");
	form.setAttribute("action", winURL);
	form.setAttribute("target", winName);
	for (var i in params) {
		if (params.hasOwnProperty(i)) {
			var input = document.createElement('input');
			input.type = 'hidden';
			input.name = i;
			input.value = params[i];
			form.appendChild(input);
		}
	}
	document.body.appendChild(form);
	disablear();
	var win = window.open('', winName, windowoption);
	form.target = winName;
	form.submit();
	document.body.removeChild(form);
	setTimeout(function () {
		if (print) {
			win.print();
		}
		if (postAccion != null) {
			if (runOnParent) {
				$(win).ready(postAccion);
			} else {
				win.addEventListener('load', postAccion, false);
				if (isMS) win.attachEvent('onload', postAccion, false);
			}
		}
	}, 1000);
}

function openInNewWindow(zFormToSubmit, zUrl, postAccion, runOnParent, print, full) {
	height = screen.height - 200;
	width = screen.width - 100;
	var size = ',width=' + width + ',height=' + height;
	// var posicion = ",left="+(screen.width - width)/ 2+",top="+(screen.height -
	// height) / 2;
	var posicion = ",left=50,top=50";
	if (full) {
		posicion = ",left=" + screen.availLeft + ",top=" + screen.availTop;
		size = ',width=' + screen.availWidth + ',height=' + (screen.availHeight - 40) + ",fullscreen=yes";
	}
	var param = ',scrollbars=yes,resizable=yes,menubar=no,location=no,toolbar=no,status=no,directories=no,titlebar=no,' + size + posicion;
	// alert(zUrl);
	disablear();
	var win = window.open(zUrl, '_blank', param, true);
	if (print) {
		win.print();
	}
	if (postAccion != null) {
		if (runOnParent) {
			$(win).ready(postAccion);
		} else {
			win.addEventListener('load', postAccion, false);
			if (isMS) win.attachEvent('onload', postAccion, false);
		}
	}
}

function registerDate(fullname, js_date_format, height) {
	if (document.calendarsToSetup == null) {
		document.calendarsToSetup = new Array();
	}
	var pos = document.calendarsToSetup.length;
	document.calendarsToSetup[pos] = [fullname, js_date_format, height];
	setupOneCalendarEditors(document.calendarsToSetup[pos]);
}

function addDays(sdate, dias) {
	var date = new Date(sdate.substring(6, 10), "" + (parseInt(sdate.substring(3, 5)) - 1), sdate.substring(0, 2), "0", "0", "0");
	var mm = date.getTime() + (parseInt(dias) * 24 * 60 * 60 * 1000);
	date.setTime(mm);
	d = "" + date.getDate();
	if (d.length == 1) d = "0" + d;
	m = "" + (date.getMonth() + 1);
	if (m.length == 1) m = "0" + m;
	s = d + "-" + m + "-" + date.getFullYear();
	return s;
}

function adjustDate(sdate, dias, mes, year) {
	var date = new Date(sdate.substring(6, 10), sdate.substring(3, 5), sdate.substring(0, 2), "0", "0", "0");
	var mm = date.getTime() + (parseInt(dias) * 24 * 60 * 60 * 1000);
	date.setTime(mm);
	d = "" + date.getDate();
	if (d.length == 1) d = "0" + d;
	m = (mes != 0) ? "" + mes : "" + (date.getMonth() + 1);
	if (m.length == 1) m = "0" + m;
	s = d + "-" + m + "-" + (date.getFullYear() + year);
	return s;
}

function changecss(name, attr, value) {
	if (value == 'remove') {
		$(name).css(attr, '');
	} else {
		$(name).css(attr, value);
	}
}
var encodings = {
	// Windows code page 1252 Western European
	//
	cp1252: '\x00\x01\x02\x03\x04\x05\x06\x07\x08\t\n\x0b\x0c\r\x0e\x0f\x10\x11\x12\x13\x14\x15\x16\x17\x18\x19\x1a\x1b\x1c\x1d\x1e\x1f !"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\x7f\u20ac\ufffd\u201a\u0192\u201e\u2026\u2020\u2021\u02c6\u2030\u0160\u2039\u0152\ufffd\u017d\ufffd\ufffd\u2018\u2019\u201c\u201d\u2022\u2013\u2014\u02dc\u2122\u0161\u203a\u0153\ufffd\u017e\u0178\xa0\xa1\xa2\xa3\xa4\xa5\xa6\xa7\xa8\xa9\xaa\xab\xac\xad\xae\xaf\xb0\xb1\xb2\xb3\xb4\xb5\xb6\xb7\xb8\xb9\xba\xbb\xbc\xbd\xbe\xbf\xc0\xc1\xc2\xc3\xc4\xc5\xc6\xc7\xc8\xc9\xca\xcb\xcc\xcd\xce\xcf\xd0\xd1\xd2\xd3\xd4\xd5\xd6\xd7\xd8\xd9\xda\xdb\xdc\xdd\xde\xdf\xe0\xe1\xe2\xe3\xe4\xe5\xe6\xe7\xe8\xe9\xea\xeb\xec\xed\xee\xef\xf0\xf1\xf2\xf3\xf4\xf5\xf6\xf7\xf8\xf9\xfa\xfb\xfc\xfd\xfe\xff',
	// Windows code page 1251 Cyrillic
	//
	cp1251: '\x00\x01\x02\x03\x04\x05\x06\x07\x08\t\n\x0b\x0c\r\x0e\x0f\x10\x11\x12\x13\x14\x15\x16\x17\x18\x19\x1a\x1b\x1c\x1d\x1e\x1f !"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\x7f\u0402\u0403\u201a\u0453\u201e\u2026\u2020\u2021\u20ac\u2030\u0409\u2039\u040a\u040c\u040b\u040f\u0452\u2018\u2019\u201c\u201d\u2022\u2013\u2014\ufffd\u2122\u0459\u203a\u045a\u045c\u045b\u045f\xa0\u040e\u045e\u0408\xa4\u0490\xa6\xa7\u0401\xa9\u0404\xab\xac\xad\xae\u0407\xb0\xb1\u0406\u0456\u0491\xb5\xb6\xb7\u0451\u2116\u0454\xbb\u0458\u0405\u0455\u0457\u0410\u0411\u0412\u0413\u0414\u0415\u0416\u0417\u0418\u0419\u041a\u041b\u041c\u041d\u041e\u041f\u0420\u0421\u0422\u0423\u0424\u0425\u0426\u0427\u0428\u0429\u042a\u042b\u042c\u042d\u042e\u042f\u0430\u0431\u0432\u0433\u0434\u0435\u0436\u0437\u0438\u0439\u043a\u043b\u043c\u043d\u043e\u043f\u0440\u0441\u0442\u0443\u0444\u0445\u0446\u0447\u0448\u0449\u044a\u044b\u044c\u044d\u044e\u044f'
};

function toUTF(bytes, encoding) {
	var enc = encodings[encoding];
	var n = bytes.length;
	var chars = new Array(n);
	for (var i = 0; i < n; i++) chars[i] = enc.charAt(bytes.charCodeAt(i));
	return chars.join('');
}

function download(strData, strFileName, strMimeType) {
	var D = document,
		A = arguments,
		a = D.createElement("a"),
		d = A[0],
		n = A[1],
		t = A[2] || "text/plain";
	// build download link:
	a.href = "data:" + t + "," + escape(strData);
	if (window.MSBlobBuilder) {
		var bb = new MSBlobBuilder();
		bb.append(strData);
		return navigator.msSaveBlob(bb, strFileName);
	} /* end if(window.MSBlobBuilder) */
	if ('download' in a) {
		a.setAttribute("download", n);
		a.innerHTML = "downloading...";
		D.body.appendChild(a);
		setTimeout(function () {
			var e = D.createEvent("MouseEvents");
			e.initMouseEvent("click", true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
			a.dispatchEvent(e);
			D.body.removeChild(a);
		}, 66);
		return true;
	} /* end if('download' in a) */ ; // end if a[download]?
	// do iframe dataURL download:
	var f = D.createElement("iframe");
	D.body.appendChild(f);
	f.src = "data:" + (A[2] ? A[2] : "application/octet-stream") + (window.btoa ? ";base64" : "") + "," + (window.btoa ? window.btoa : escape)(strData);
	setTimeout(function () {
		D.body.removeChild(f);
	}, 555);
	return true;
} /* end download() */
function downloadInNewTab(strData, strFileName, strMimeType) {
	height = screen.height - 200;
	width = screen.width - 100;
	var size = ',width=' + width + ',height=' + height;
	var posicion = ",left=50,top=50";
	var param = ',scrollbars=yes,resizable=yes,menubar=no,location=no,toolbar=no,status=no,directories=no,titlebar=no,' + size + posicion;
	var win = window.open(strFileName, "_blank", param, true);
	//  	win.print();
	return false;
}

function takePicture(object) {
	// html2canvas(this.document.getElementById('workarea')).then(canvas => {
	// var img = canvas.toDataURL("image/jpeg");
	// object.src=img;
	// });
	html2canvas(this.document.getElementById('workarea')).then(function (canvas) {
		var img = canvas.toDataURL("image/jpeg");
		object.src = img;
	});
}

function screenGrabber(object) {
	html2canvas(document.body, {
		logging: true,
		useCORS: true,
		onrendered: function (canvas) {
			img = canvas.toDataURL("image/jpeg");
			download(img, "snapshot.jpg", "image/jpeg");
		}
	});
}


function round(numero, dec) {
	var original = parseFloat(numero);
	var result = Math.round(original * Math.pow(10, dec)) / Math.pow(10, dec);
	return result;
}

function formatDouble(numero, dec) {
	return numero.toFixed(dec);
}
// sirve para calculo de iva de comision en aereos
function findCoefIva(baseImp, iva, tasa) {
	return findCoefIva(baseImp, iva, tasa, false);
}

function findCoefIva(baseImp, iva, tasa, absoluto) {
	if (baseImp == 0) return 0;
	var coef = round(iva / tasa, 2) / baseImp;
	if (coef > 0.999) return 1;
	return absoluto ? 0 : coef;
}


function firstDate(type,period,date) {
	var month=12;
	var y = date.getFullYear();
	var m = date.getMonth();
	var firstDay = new Date(y, m, 1);
	
	if (type=='FY') {
		m=0;
		firstDay = new Date(y, m, 1);
		month=12;
	}
	if (type=='Q') {
		m=m<2?0:m<5?3:m<8?6:9;
		firstDay = new Date(y, m, 1);
		month=3;
	}
	if (type=='ME') {
		firstDay = new Date(y, m, 1);
		month=1;
	}
	month=month*period;
	firstDay.setMonth (firstDay.getMonth () + month);

	y = firstDay.getFullYear();
	m = firstDay.getMonth();
	firstDay = new Date(y, m, 1);

	return firstDay.getDate()+"/"+(firstDay.getMonth()+1)+"/"+firstDay.getFullYear();
}

function parseDate(str1) {
	var d = parseInt(str1.substring(0, 2));
	var m = parseInt(str1.substring(3, 5));
	var y = parseInt(str1.substring(6, 10));
	var dia = new Date(y, m - 1, d);
	return dia;
}
var Base64 = {
	_keyStr: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
	encode: function (e) {
		var t = "";
		var n, r, i, s, o, u, a;
		var f = 0;
		e = Base64._utf8_encode(e);
		while (f < e.length) {
			n = e.charCodeAt(f++);
			r = e.charCodeAt(f++);
			i = e.charCodeAt(f++);
			s = n >> 2;
			o = (n & 3) << 4 | r >> 4;
			u = (r & 15) << 2 | i >> 6;
			a = i & 63;
			if (isNaN(r)) {
				u = a = 64
			} else if (isNaN(i)) {
				a = 64
			}
			t = t + this._keyStr.charAt(s) + this._keyStr.charAt(o) + this._keyStr.charAt(u) + this._keyStr.charAt(a)
		}
		return t
	},
	decode: function (e) {
		var t = "";
		var n, r, i;
		var s, o, u, a;
		var f = 0;
		e = e.replace(/[^A-Za-z0-9+/=]/g, "");
		while (f < e.length) {
			s = this._keyStr.indexOf(e.charAt(f++));
			o = this._keyStr.indexOf(e.charAt(f++));
			u = this._keyStr.indexOf(e.charAt(f++));
			a = this._keyStr.indexOf(e.charAt(f++));
			n = s << 2 | o >> 4;
			r = (o & 15) << 4 | u >> 2;
			i = (u & 3) << 6 | a;
			t = t + String.fromCharCode(n);
			if (u != 64) {
				t = t + String.fromCharCode(r)
			}
			if (a != 64) {
				t = t + String.fromCharCode(i)
			}
		}
		t = Base64._utf8_decode(t);
		return t
	},
	_utf8_encode: function (e) {
		e = e.replace(/rn/g, "n");
		var t = "";
		for (var n = 0; n < e.length; n++) {
			var r = e.charCodeAt(n);
			if (r < 128) {
				t += String.fromCharCode(r)
			} else if (r > 127 && r < 2048) {
				t += String.fromCharCode(r >> 6 | 192);
				t += String.fromCharCode(r & 63 | 128)
			} else {
				t += String.fromCharCode(r >> 12 | 224);
				t += String.fromCharCode(r >> 6 & 63 | 128);
				t += String.fromCharCode(r & 63 | 128)
			}
		}
		return t
	},
	_utf8_decode: function (e) {
		var t = "";
		var n = 0;
		var r = c1 = c2 = 0;
		while (n < e.length) {
			r = e.charCodeAt(n);
			if (r < 128) {
				t += String.fromCharCode(r);
				n++
			} else if (r > 191 && r < 224) {
				c2 = e.charCodeAt(n + 1);
				t += String.fromCharCode((r & 31) << 6 | c2 & 63);
				n += 2
			} else {
				c2 = e.charCodeAt(n + 1);
				c3 = e.charCodeAt(n + 2);
				t += String.fromCharCode((r & 15) << 12 | (c2 & 63) << 6 | c3 & 63);
				n += 3
			}
		}
		return t
	}
}

function openModal(name, title, contents, reload, closeButton, actionclose) {
	if (jQuery("#" + name).length) return;
	$('#' + name).modal().hide();
	$("#" + name).remove();
	var shtml = '<div class="modal fade" style="'+(name=='err'?"z-index:20000":"")+'" id="' + name + '"><div class="modal-dialog"><div class="modal-content" data-focus-on="input:first"><div class="modal-header ' + (name == 'err' ? 'bg-danger' : 'bg-success') + '"><h3 class="modal-title">' + title + '</h3>';
	if (!closeButton) shtml += '<button type="button" class="close" data-dismiss="modal">x</button>';
	shtml += '</div><div class="modal-body">' + contents + '</div><div class="modal-footer">';
	if (closeButton) shtml += '<button type="button" class="btn ' + (name == 'err' ? 'bg-danger' : 'bg-success') + '" data-dismiss="modal">' + msgClose + '</button>';
	shtml += '</div></div></div></div>';
	$("#modal-zone").append(shtml);
	$("#" + name).modal({
		backdrop: 'static',
		keyboard: false
	});
	$("#" + name).on('hidden.bs.modal', function () {
		$("#" + name).remove();
		setFocusModal();
		ejecuteAjaxScripts(contents);
		if (actionclose) actionclose();
	});
}

function openModalConfirm(contents, fnYes, fnNo) {
	if (contents==null || contents=="") {
	    fnYes();
	    return;
	  }
	var name = "confirm";
	$("#" + name).remove();
	var shtml = '<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="' + name + '" style="z-index: 10000;" data-focus-on="input:first"><div class="modal-dialog modal-sm"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button><h4 class="modal-title" id="myModalLabel">' + contents + '</h4></div>';;
	shtml += '<div class="modal-footer"><button type="button" class="btn btn-default" id="modal-btn-si">' + msgConfirmationYes + '</button><button type="button" class="btn btn-primary" id="modal-btn-no">' + msgConfirmationNo + '</button></div></div></div></div>';
	$("#modal-zone-confirm").append(shtml);
	$("#modal-btn-si").on("click", function () {
		$("#" + name).modal('hide');
		if (fnYes) fnYes();
	});
	$("#modal-btn-no").on("click", function () {
		$("#" + name).modal('hide');
		if (fnNo) fnNo();
	});
	$("#" + name).modal('show');
}

function consumeEvent(event) {
	var evt = event ? event : window.event;
	if (evt.stopPropagation) evt.stopPropagation();
	if (evt.cancelBubble != null) evt.cancelBubble = true;
	if (evt.returnValue) evt.returnValue = false;
	evt.preventDefault();
}
// call gotorefreshform para optimizar size
var sizeMemoryArray = 0;
var memoryArray;

function saveMemoryArray(a) {
	if (memoryArray == null) memoryArray = new Array();
	var i = sizeMemoryArray;
	sizeMemoryArray++;
	memoryArray[i] = a;
	return i;
}

function cleanMemoryArray() {
	memoryArray = null;
	sizeMemoryArray = 0;
}

function gr(i) {
	var a = memoryArray[i];
	goToRefreshForm('do-PartialRefreshForm', a.fullname, a.provider, a.owner, getLastModal(), a.action);
}

function uf(i, text) {
	var a = memoryArray[i];
	var field = a.fullname;
	updateField(field, text);
}

function runAction(that, confirmation, question, target, action) {
	if (confirmation) openModalConfirm(question, action);
	else {
		//if ((target == 'do-BackAction') && hasChangeInputs()) {
		//	openModalConfirm(question, function () {
			//	action(that);
		//	});
	//	} else {
			action(that);
	//	}
	}
}

function aa(k, idTree, parent, descr, acc, icon, iconopen) {
	k.add(idTree, parent, descr, acc, null, null, icon, iconopen);
}

function aa(k, idTree, parent, descr, acc, icon) {
	k.add(idTree, parent, descr, acc, null, null, icon, icon);
}

function bt(k, m, f, v, title, icon) {
	k.add('root_', '-1', title, null, null, null, icon);
	for (var j = 0; j < m.length; j++) {
		if (v == m[j].a) {
			document.getElementById(m[j].f).value = m[j].a;
			k.s1(m[j].i);
		}
		aa(k, m[j].i, m[j].p, m[j].d, m[j].x, m[j].c, (m[j].o == null) ? m[j].c : m[j].o);
	}
	k.preOpenTo(k.selectedNode, true);
	document.getElementById(f).innerHTML = k.toString();
}

function zoomToFitHorizontal(father, child) {
	$('#' + child).css({
		'zoom': '1%'
	});
	var width = document.getElementById(child).offsetWidth;
	var windowWidth = document.getElementById(father).offsetWidth;
	var r = 1;
	r = (windowWidth / width) * 100;
	$('#' + child).css({
		'zoom': '' + r + '%'
	});
}

function zoomToFit(father, child) {
	$('#' + child).css({
		'zoom': '100%'
	});
	var width = document.getElementById(child).offsetWidth;
	var height = document.getElementById(child).offsetHeight;
	var windowWidth = document.getElementById(father).offsetWidth;
	var windowHeight = document.getElementById(father).offsetHeight;
	var r = 1;
	r = Math.min(windowWidth / width, windowHeight / height) * 100;
	$('#' + child).css({
		'zoom': '' + r + '%'
	});
}

function daysBetween(d1, d2) {
	return (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
}

function allowDropColumn(ev) {
	ev.preventDefault();
}

function disableMenu() {
	$(".nav *").bind('click', false);
	$(".nav li:not(.disabled)").addClass('disabled_auto disabled');
}

function enableMenu() {
	$(".nav li.disabled_auto").removeClass('disabled');
	$(".nav *").unbind('click', false);
}

function dragColumn(ev) {
	ev.dataTransfer.setData("text", $(ev.target).closest('th')[0].id);
	ev.dataTransfer.setDragImage($(ev.target).closest('th')[0], 0, 0)
	$("#waitpane").html('<div class="circle" style="position:absolute;z-index:5000;padding:12px;top:' + (event.y - 100) + 'px;left:' + (event.x) + 'px;height:50px;width:50px;" ondrop="dropDelete(event)" ondragover="allowDropColumn(event)" ><i class="fa fa-trash-alt fa-2x" /></div>');
}

function dragLeave(ev) {
	$("#waitpane").html('');
}

function dropContentColumn(ev, table, ui) {
	var tbl = $('#' + table);
	var rows = jQuery('tr', tbl);
	var cols = $(tbl).find('th');
	var from = $(cols).index($(ui.element));
	var cols;
	rows.each(function () {
		cols = jQuery(this).children('td');
		cols.eq(from).addClass('resizing');
	});
}

function removeDropContentColumn(ev, table, ui) {
	var tbl = $('#' + table);
	var rows = jQuery('tr', tbl);
	var cols = $(tbl).find('th');
	var from = $(cols).index($(ui.element));
	var cols;
	rows.each(function () {
		cols = jQuery(this).children('td');
		cols.eq(from).removeClass('resizing');
		// cols = jQuery(this).children('td');
		// cols.eq(from).find('input').css('width','100%');
	});
}

function resizeDropContentColumn(ev, table, ui) {
	adjustWidthColumn(table, ui.element, ui.size.width); // $(ui.element).width())
	// var tbl =$('#'+table);
	// var rows = jQuery('tr', tbl);
	// var cols = $(tbl).find('th');
	// var from = $(cols).index($(ui.element));
	// var cols;
	// rows.each(function() {
	// cols = jQuery(this).children('td');
	// cols.eq(from).find('input').css('width',ui.size.width+'px');
	// });
}

function adjustWidthColumn(table, thok, ancho) {
	$(thok).width(ancho);
	var tbl = $('#' + table);
	var rows = jQuery('tr', tbl);
	var cols = $(tbl).find('th');
	var from = $(cols).index($(thok));
	var cols;
	rows.each(function () {
		cols = jQuery(this).children('td');
		cols.eq(from).find('input').css('width', ancho + 'px');
	});
}

function dropDelete(ev) {
	$("#waitpane").html('');
	ev.preventDefault();
	var data = ev.dataTransfer.getData("text");
	var tbl = jQuery($('#' + data).closest('table'));
	hideColumns(tbl, data)
}

function dropColumn(ev) {
	ev.preventDefault();
	var data = ev.dataTransfer.getData("text");
	var tbl = jQuery($(ev.target).closest('table'));
	moveColumn(tbl, data, $(ev.target).closest('th')[0].id);
	$("#waitpane").html('');
}
function moveColumn(table, ifrom, ito) {
	var rows = jQuery('tr', table);
	var cols = $(table).find('th');
	var from = $(cols).index($('#' + ifrom));
	var to = $(cols).index($('#' + ito));
	if (from == to) return;
	if (from == to - 1) {
		switchColumns(table, from, to);
		return;
	}
	var cols;
	rows.each(function () {
		cols = jQuery(this).children('th, td');
		cols.eq(from).detach().insertBefore(cols.eq(to));
	});
}
function hideColumns(table, ifrom) {
	var rows = jQuery('tr', table);
	var cols = $(table).find('th');
	var from = $(cols).index($('#' + ifrom));
	var cols;
	rows.each(function () {
		cols = jQuery(this).children('th, td');
		cols.eq(from).css('display', 'none');
	});
};
function switchColumns(table, from, to) {
	var rows = jQuery('tr', table);
	var cols = $(table).find('th');
	if (from == to) return;
	var cols;
	rows.each(function () {
		cols = jQuery(this).children('th, td');
		cols.eq(from).detach().insertBefore(cols.eq(to));
	});
	rows.each(function () {
		cols = jQuery(this).children('th, td');
		cols.eq(to).detach().insertBefore(cols.eq(from));
	});
};

function ra(that, confirmation, question, target, run) {
	runAction(that, confirmation, question, target, function () {
		if ((target == 'do-BackAction')) setModalChanges(false);
		run(that);
	}, function () {});
}

function prf(provider, owner, id, action) {
	var row = getObjectProvider(provider).getSelectionRow();
	goToRefreshForm('do-PartialRefreshForm', '', owner, id, getLastModal(), action);
	rs(row, null);
}

function dc1(that, e, run, k, d) {
	clearClick();
	rs_forced_with_key(that, k, d, e);
	run(that);
	that.disabled = false;
	rs_forced(null, e);
}

function dc2(that, e, run) {
	clearClick();
	rs_forced_with_key_cell(that, '', '', e);
	run(that);
	that.disabled = false;
}

function dc3(that, e, run) {
	if (e.keyCode == 13) {
		run(that);
		that.disabled = false;
		return false;
	}
}

function dc4(e, b) {
	var out = gaOnKey(e);
	if (b && out) out = legalNumericChar(e, null, -1);
	return out;
}

function dc5(that, e, run) {
	rs(that, e);
	runClick(that, e, function (that) {
		run(that);
	});
}

function dc6(that, e, run, k, d) {
	rsKey(that, k, d, e);
	runClick(that, e, function (that) {
		run(that);
	});
}

function ev(event) {
	return (typeof (event) == 'undefined') ? null : event;
}

function cev(event) {
	if (!(typeof (event) == 'undefined')) consumeEvent(event);
}

