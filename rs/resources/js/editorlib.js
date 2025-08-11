var key = "SECTOR_STARTMAIN";
var wys=null;
var objectName = "";
var newmapa = "";

function pasteImage(objetName,wysiwyg) {

	document.getElementById(wysiwyg).contentWindow.document.addEventListener("paste", function(e) {
	    for (var i = 0; i < e.clipboardData.items.length; i++) {
	        if (e.clipboardData.items[i].kind == "file" && e.clipboardData.items[i].type == "image/png") {
	            // get the blob
	            var imageFile = e.clipboardData.items[i].getAsFile();
	
	            // read the blob as a data URL
	            var fileReader = new FileReader();
	            fileReader.onloadend = function(e) {
	                // create an image
	                var image = document.getElementById(wysiwyg).contentWindow.document.createElement("IMG");
	                image.src = this.result;
	
	                // insert the image
	                
	                var range = document.getElementById(wysiwyg).contentWindow.getSelection().getRangeAt(0);
	                range.insertNode(image);
	                range.collapse(false);
	
	                // set the selection to after the image
	                var selection = document.getElementById(wysiwyg).contentWindow.getSelection();
	                selection.removeAllRanges();
	                selection.addRange(range);
	            };
	
	            // TODO: Error Handling!
	            // fileReader.onerror = ...
	
	            fileReader.readAsDataURL(imageFile);
	
	            // prevent the default paste action
	            e.preventDefault();
	
	            // only paste 1 image at a time
	            break;
	        }
	    }
	});




        // Tells the browser that we *can* drop on this target
        document.getElementById(wysiwyg).contentWindow.document.addEventListener( 'dragover', function (e) {
            e = e || window.event; // get window.event if e argument missing
									// (in IE)
            if (e.preventDefault) {
                e.preventDefault();
            }
 
            return false;
        });
        // Tells the browser that we *can* drop on this target
        document.getElementById(wysiwyg).contentWindow.document.addEventListener('dragleave', function (e) {
            e = e || window.event; // get window.event if e argument missing
									// (in IE)
            if (e.preventDefault) {
                e.preventDefault();
            }
             return false;
        });

    

        document.getElementById(wysiwyg).contentWindow.document.addEventListener( 'drop', function (e) {
            e = e || window.event; // get window.event if e argument missing
									// (in IE)
            if (e.preventDefault) {
                e.preventDefault();
            } // stops the browser from redirecting off to the image.

            var dt = e.dataTransfer;
            var files = dt.files;
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                if (file.type.indexOf("image")!=-1) {
                    var reader = new FileReader();
                    reader.readAsDataURL(file);
                    reader.onloadend = function(e, file) {
    	                // create an image
    	                var image = document.getElementById(wysiwyg).contentWindow.document.createElement("IMG");
    	                image.src = this.result;
    	                var range;
    	                // insert the image
    	                if ( document.getElementById(wysiwyg).contentWindow.getSelection().rangeCount <= 0) {
    		                document.getElementById(wysiwyg).contentWindow.document.onFocus();
    	                	
    	                } 
    		            range = document.getElementById(wysiwyg).contentWindow.getSelection().getRangeAt(0);
    	                range.insertNode(image);
		                range.collapse(false);
		
		                // set the selection to after the image
		                var selection = document.getElementById(wysiwyg).contentWindow.getSelection();
		                selection.removeAllRanges();
		                selection.addRange(range);
    	                	
    	                
    	            };

                	
                }	
                else if (file.type.indexOf("text")!=-1){
                    var reader = new FileReader();
                    reader.readAsDataURL(file);
                    reader.onloadend = function(e, file) {
    	                var adiv = document.getElementById(wysiwyg).contentWindow.document.createElement("DIV");
    	                var pos = this.result.indexOf("base64,");
    	                var texto = Base64.decode(this.result.substring(pos+7));
    	                var htmlText = '<DIV>'+texto+'</DIV>';
    	                
    	            	wys= window.parent.getRegisteredRTE(objetName);
    	            	wys.doTextFormat('insertHTML',htmlText,null);
    	            };

                	
                }	
            }
            return false;
        });

} 

var lastEvent;

function clicker(event,objetName,wysiwyg){
	// mapa: [ [ key, type, [[ texto, newKey],[ texto, newKey],[ texto,
	// newKey]]] ,[[ texto, html],[ texto, html],[ texto, html]]], [ key, ....
	lastEvent=event;
	key = "SECTOR_STARTMAIN";
	wys= window.parent.getRegisteredRTE(objetName);
    if(wys.isMSIE()){
    	var posCursor=document.getElementById(wysiwyg).contentWindow.document.selection.createRange();
		if (!posCursor || !posCursor.pasteHTML) return true;
		var tentKey = findFirstKey(posCursor.parentElement());
		if (tentKey!=null) key = tentKey;
	} else {
		var contentWin=document.getElementById(wysiwyg).contentWindow;
	    var sel = contentWin.getSelection();           // get current selection
	    var range = sel.getRangeAt(0);          // get the first range of the
												// selection (there's almost
												// always only one range)
		var tentKey = findFirstKey(range.commonAncestorContainer);
		if (tentKey!=null) key = tentKey;
	}

	if (key=='SECTOR_STARTMAIN') {
		var opciones = wys.getMapa('SECTOR_MAIN');
		if (opciones && opciones.length>0)
			rclickeditor(objetName,opciones[2],opciones[1],0)
	}
	else {
	    objectName=objetName;
		var ajax = new ZAjaxSubmit("do-LayoutAction",false);
		ajax.addUrlParameters(getUrlParameter("key",key));
		ajax.addUrlParameters(getUrlParameter("source",wys.getSource()));
		ajax.setAjaxCallback(layoutListener,genericErrorMessage);
		ajax.fetch();
	}

// var opciones = wys.getMapa(key);
// if (opciones && opciones.length>0)
// rclickeditor(objetName,opciones[2],opciones[1])

	return false;	
}

function layoutListener(ajaxResponse) {
	// alert("container: " + ajaxContainer);
	// alert("response :"+ajaxResponse.substring(1, 2000));
// alert("response :"+ajaxResponse.substring(2001, 4000));
// alert("response :"+ajaxResponse.substring(4001, 6000));
	ejecuteAjaxScripts(ajaxResponse);
	wys.setMapaSec(newmapa);
	var opciones = wys.getMapaSec(key);
	if (opciones && opciones.length>0)
	   rclickeditor(objectName,opciones[2],opciones[1],1) 

}
var findFirstKey = function(el) {
    if (!el) return null;
    if (!el.parentNode) return null;
    for(var i = 0; i < el.parentNode.childNodes.length; i++) {
        var node = el.parentNode.childNodes[i];
        if(node.nodeType === 8) {
        	if (node.data.indexOf("DATA:")!=0) continue;
        	return node.data.substring(5);
        }
        if(node.id && node.id.indexOf('zone_') == 0) {
        	return node.id.substring(5);
        }
    }
    return findFirstKey(el.parentNode);
};

function getSelectionContainerElement(doc) {
    var range, sel, container;
    if (doc.selection && doc.selection.createRange) {
        // IE case
        range = doc.selection.createRange();
        return range.parentElement();
    } else if (window.getSelection) {
        sel = window.getSelection();
        if (sel.getRangeAt) {
            if (sel.rangeCount > 0) {
                range = sel.getRangeAt(0);
            }
        } else {
            range = doc.createRange();
            range.setStart(sel.anchorNode, sel.anchorOffset);
            range.setEnd(sel.focusNode, sel.focusOffset);

            if (range.collapsed !== sel.isCollapsed) {
                range.setStart(sel.focusNode, sel.focusOffset);
                range.setEnd(sel.anchorNode, sel.anchorOffset);
            }
        }

        if (range) {
           container = range.commonAncestorContainer;

           return container.nodeType === 3 ? container.parentNode : container;
        }   
    }
}
/*
function rclick(row,e,listactions,skin){
    var zTable = getZTableFor(row);
	if (zTable==null) return;
	$(".popup_menu").remove();
	unselectAll_fromRow(row);
	rs_forced(row,e);
    var bodyOffsets = document.body.getBoundingClientRect();
    tempX = e.pageX - bodyOffsets.left;
    tempY = e.pageY;
//	var shtml= '<div id="popup_menu" class="popup_menu" style=" position:absolute;top: '+tempY+'px; left: '+tempX+'px;z-index:10000 "><ul class="dropdown-menu" id="popup_menu_ul" role="menu"  ><li>';
//	shtml +=$("#"+listactions).html();
//	shtml   += '</li></div>'; 
//
//	$("#"+listactions+" a").each(function() {
//		shtml = shtml.replace('onclick="goTo','onclick="killMenu();goTo');
//	});
	var shtml= '<div id="popup_menu" class="popup_menu" style=" position:absolute;top: '+tempY+'px; left: '+tempX+'px;z-index:10000 ">';
	shtml+= ' <ul class="dropdown-menu" id="popup_menu_ul" role="menu"  >';

	$("#"+listactions+">a").each(function() {
		
		shtml   += '<li class="dropdown-item">'+$(this).prop("outerHTML").replace('consumeEvent(event);','consumeEvent(event);killMenu();')+'</li>';

	});
	$("#"+listactions+" li:not(.hidden) a").each(function() {
		
		shtml   += '<li class="dropdown-item">'+$(this).prop("outerHTML").replace('consumeEvent(event);','consumeEvent(event);killMenu();')+'</li>';

	});
	shtml   += '</ul></div>';

	$("body").append(shtml);
	$('#popup_menu_ul').dropdown('toggle')

	$(document).off("click",hidePopupMenu);
	$(document).on("click",hidePopupMenu);

   return false;
}
*/
var last = 0;
function rclickeditor(objeto,actions,parent, base){
	var ahora = new Date();
    var bodyOffsets = document.body.getBoundingClientRect();
    var tempX = lastEvent.pageX - bodyOffsets.left;
    var tempY = lastEvent.pageY;

    var shtml= '<div id="popup_menu" class="popup_menu" style=" position:absolute;top: '+tempY+'px; left: '+tempX+'px;z-index:10000 ">';
	shtml+= ' <ul class="dropdown-menu" id="popup_menu_ul" role="menu" style="height:300px;overflow: auto;" >';

	var actionsToDisplay = new Array();
	var act=0;
	var j=0; 
	for (act=0; act<actions.length; act++) {
		if (actions[act][1]=='') continue;
		actionsToDisplay[j] = new Array(3);
		actionsToDisplay[j][0] = actions[act][1];   // title
		actionsToDisplay[j][1] = 'pss_icon/arrow_left.png';   // icon
		var funct; 
		if (actions[act][1].indexOf("Zona ")==0)
			funct='window.parent.getRegisteredRTE(\''+objeto+'\').insertHeaderDetail(\''+actions[act][0]+'\',\'\',\''+(base==1?'EEEEEE':'DDDDDD')+'\')';
		else if (parent=="2")
			funct='window.parent.getRegisteredRTE(\''+objeto+'\').doTextFormat(\'insertHTML\',\''+actions[act][0]+'\')';
		else
			funct='window.parent.getRegisteredRTE(\''+objeto+'\').insertHeaderDetail(\''+actions[act][0]+'\',\'\',\''+(base==1?'EEEEEE':'DDDDDD')+'\')';
		actionsToDisplay[j][2] = funct;  // url
		j++;
	}
	
	var i;
	k=0;
	for (i=0; i<j; i++) {
		var texto = actionsToDisplay[k][0];
		var image = actionsToDisplay[k][1];
		var url = actionsToDisplay[k][2];
		shtml   += '<li class="dropdown-item" onclick="javascript:'+url+'">';
		shtml   += '<a class="btn btn-default btn-sm" onclick="'+url+'; consumeEvent(event);killMenu(); "><img border="0" style="padding-right: 5px;vertical-align:middle;" src="'+image+'">'+texto+'</a>';
		shtml   +='</li>';
		k++;
	}
	shtml   += '</ul></div>';

	$("body").append(shtml);
	$('#popup_menu_ul').dropdown('toggle')

	$(document).off("click",hidePopupMenu);
	$(document).on("click",hidePopupMenu);

   return false;
}
/*
var last = 0;
function rclickeditor(objeto,actions,parent, base){
	var ahora = new Date();
	var cm = document.getElementById(layout_menu);
	if (cm==null) return;

	var actionsToDisplay = new Array();
	var act=0;
	var j=0; 
	for (act=0; act<actions.length; act++) {
		if (actions[act][1]=='') continue;
		actionsToDisplay[j] = new Array(3);
		actionsToDisplay[j][0] = actions[act][1];   // title
		actionsToDisplay[j][1] = 'pss_icon/arrow_left.png';   // icon
		var funct; 
		if (actions[act][1].indexOf("Zona ")==0)
			funct='window.parent.getRegisteredRTE(\''+objeto+'\').insertHeaderDetail(\''+actions[act][0]+'\',\'\',\''+(base==1?'EEEEEE':'DDDDDD')+'\')';
		else if (parent=="2")
			funct='window.parent.getRegisteredRTE(\''+objeto+'\').doTextFormat(\'insertHTML\',\''+actions[act][0]+'\')';
		else
			funct='window.parent.getRegisteredRTE(\''+objeto+'\').insertHeaderDetail(\''+actions[act][0]+'\',\'\',\''+(base==1?'EEEEEE':'DDDDDD')+'\')';
		actionsToDisplay[j][2] = funct;  // url
		j++;
	}
	
	var i;
	k=0;
	for (i=0; i<cm.childNodes.length; i++) {
		var tag_a = cm.childNodes[i];
		if (tag_a.tagName == "A") {
       		if (k>=j) 
				tag_a.style.display="none";
			else {
				var texto = actionsToDisplay[k][0];
				var image = actionsToDisplay[k][1];
				var url = cEjec(actionsToDisplay[k][2]);
				var lid = tag_a.id.substring(3);

				tag_a.href=url;
				var div = tag_a.childNodes[0];
				var p =  div.childNodes[0];
				p.innerHTML = "<IMG border='0' src='"+image+"' style='margin-right: 5px;' id='im"+lid+"' onclick=\"gmobj('el"+lid+"').click()\"/>"+texto;
				k++;
				tag_a.style.display="block";
			}
		}
	}
	cm.style.overflow = 'scroll';
	cm.style.top = 50;
	cm.style.height = 300;
	popup(layout_menu,1);
}
*/