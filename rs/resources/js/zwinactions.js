

var arr = null;
var notArr = null;

var row_subdetail = null;
var ca = null;
selectedRowForce = null;
 
function AAInit() {

 arr=null; 
 notArr=null;  
}  

function AAAdd(zid,zisWin,zvect,zMult) {
	if (arr==null)
		 arr=new Array();  
	ca=new Array();
	ca[0]=zid;
	ca[1]=zvect;
	ca[2]=zMult;
	ca[3]=zisWin;
	arr[arr.length]=ca;
}
function AAANodd(zid,zisWin,zvect,zMult) {
	if (notArr==null)
		notArr=new Array();  
	ca=new Array();
	ca[0]=zid;
	ca[1]=zvect;
	ca[2]=zMult;
	ca[3]=zisWin;
	notArr[notArr.length]=ca;
}

function AAFin(zTable,zBar,zProv,zObj,zMul,zSelL,zMoreSel,isDataTable,swapDest) {
	var sHtmlTableId = zTable;
	var sActionBarName =  zBar;
	var sObjectProvider = zProv;
	
	var zTable = new ZTable( zObj, arr,notArr, zMul,zSelL, sHtmlTableId, sActionBarName, sObjectProvider,zMoreSel=='1',isDataTable,swapDest);
	if (document.zTablesToSetup==null) {
		document.zTablesToSetup = new Array();
	}
	document.zTablesToSetup[document.zTablesToSetup.length] = zTable;

}

//
//  ZTable event handling functions
//
var regTables = new Array();
var pendingUnRegisterTable = null;
/*
 Register an HTML table to be handled.
*/ 
function unRegisterTable(htmlTableId) {
  pendingUnRegisterTable = htmlTableId;
}

function commitUnRegisterTable() {
  htmlTableId = pendingUnRegisterTable;
  for (i=0;i<regTables.length;i++) {
    currAss = regTables[i];
    if (currAss!=null) {
        if (currAss[1]) unselectAll(currAss[1],false);
    	if (currAss[0]==htmlTableId) {
    	    if (currAss[1].isDataTable()) {
	    	    var table = $('#'+htmlTableId);
	    	    if (table && currAss[1].isDataTable()) {
	    	    	table.DataTable().fixedHeader.disable();
	    	    	table.DataTable().destroy();
	    	    	
	    	    }
    	    }
    	}
	}
  }
}

function unRegisterAllTables() {
  selectedRowForce = null;
  for (i=0;i<regTables.length;i++) {
    currAss = regTables[i];
    if (currAss[1].isDataTable()) {
	    var table = $('#'+currAss[0]);
	    if (table) {
	    	table.DataTable().fixedHeader.disable();
	    	table.DataTable().destroy();
	    	
	    }
    }

  }
  regTables = new Array();
  //alert("unregister all");
}
function getTableRegistered(htmlTableId) {
	  for (i=0;i<regTables.length;i++) {
	    currAss = regTables[i];
	    if (currAss!=null && currAss[0]==htmlTableId) {
	      //alert("table registered found");
		  return currAss[1];
		}
	  }
	  //alert("table registered not found");
	  return null;
	}
function isTableRegistered(htmlTableId) {
  for (i=0;i<regTables.length;i++) {
    currAss = regTables[i];
    if (currAss!=null && currAss[0]==htmlTableId) {
      //alert("table registered found");
	  return true;
	}
  }
  //alert("table registered not found");
  return false;
}
function rmocell(cell,isOver,e) {
	  var zTable = getZTableForCell(cell);
	  if (zTable==null)
	    return;
	  var row = getRowForCell(cell);
	  zTable.ensureInitialized(row);
	  if (isOver) {
		  zTable.paintOverCell(row,cell);
	  } else {
		  zTable.unpaintOverCell(row,cell);
	  }
	}
/*
 Row mouse over handler.
*/
function rmo(row,isOver,e) {
  var zTable = getZTableFor(row);
  if (zTable==null)
    return;
  zTable.ensureInitialized(row);
  if (isOver) {
	   zTable.paintOver(row);
  } else 	   
	  zTable.unpaintOver(row);

  
}
function 	clearRow_subdetail() {
    row_subdetail = null;
}

function register_subdetail(name_row) {
	if (row_subdetail==null) row_subdetail = new Array();
	row_subdetail[row_subdetail.length]=name_row;
}
var row_expand_detail = 1;

function hide_show_expandall_subdetail() {
	var i;
	for ( i=0;i<row_subdetail.length;i++) {
		var obj = document.getElementById(row_subdetail[i]);
		if (!obj) continue;
		obj.style.display=row_expand_detail==1?"table-row":"none";
	}
	row_expand_detail=(row_expand_detail==0)?1:0;
}

function hide_show_subdetail(name_row, display) {
	var elem = document.getElementById(name_row);
	if (!elem) return;
	var old = elem.style.display;
	if (old!="none") {
		elem.style.display="none";
	} else {
		elem.style.display=display;
	} 
}

function hide_show_icon(elem, icon) {
	var ielem = $(elem).find("i");
	var oldclass = $(ielem).attr('oldclass');
	if (oldclass) {
		$(ielem).removeAttr('oldclass');
		$(ielem).removeAttr('class');
		$(ielem).attr('class', oldclass);
	} else {
		var currclass = $(ielem).attr('class');
		$(ielem).attr('oldclass', currclass);
		$(ielem).removeAttr('class');
		$(ielem).attr('class', icon);
	}
	
}



function rclickCell(cell,e,listactions,skin){
    var zTable = getZTableForCell(cell);
	if (zTable==null) return;
	var row = getRowForCell(cell);
    zTable.ensureInitialized(row);
    zTable.selectCellForce(cell,row,'','');

    $( cell).addClass("selected");
    rclick(row,e,listactions,skin);
}
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


function hidePopupMenu(event) {
	 if(!$(event.target).closest('dropdown-menu').length) {
		   
		    if ( !$(event.target).hasClass('popup_menu')) {
		    	$('#popup_menu_ul').dropdown('toggle')
		        $(".popup_menu").hide();
		   }
	 }
  }



function killMenu() {
	$('#popup_menu_ul').dropdown('toggle')
    $(".popup_menu").hide();
	$('#popup_menu').remove();
};

function contextMenu(row,event,listActions,skin) {

	rclick(row,event,listActions,skin);
}
function contextMenuCell(cell,event,listActions,skin) {

	rclickCell(cell,event,listActions,skin);
}

/*
 Row selected handler forced.
*/
function rs_forced_with_key(row,key,descr,e) {
  if (row==null) {
	selectedRowForce = row;
  	return ;
  }
  setupZTables();
  var zTable = getZTableFor(row);
  if (!zTable) {
  	return;
  }
  zTable.ensureInitialized(row);
  zTable.selectRowForce(row,e,key,descr);
  selectedRowForce = row;
}
function rs_forced_with_key_cell(cell,key,descr,e) {
	  if (cell==null) {
		selectedRowForce = null;
		selectecCell=null;
	  	return ;
	  }
	  setupZTables();
	  var zTable = getZTableForCell(cell);
	  if (!zTable) {
	  	return;
	  }
	  var row = getRowForCell(cell);
	  zTable.ensureInitialized(row);
	  zTable.selectCellForce(cell,row,key,descr);
	  
//	  selectedRowForce = row;
}
function rs_forced2(row,e) {
	  if (row==null) {
		selectedRowForce = row;
	  	return ;
	  }
	  setupZTables();
	  var zTable = getZTableFor(row);
	  if (!zTable) {
	  	return;
	  }
	  zTable.ensureInitialized(row);
	  if (zTable.isMultipleSelectMode) {
		  zTable.selectRow(row,'','');
		  zTable.firstRow=row;
	  }
	  else {
		  zTable.selectRowForce(row,e,'','');
		  selectedRowForce = row;
	  }
	}
function rs_forced(row,e) {
  if (row==null) {
	selectedRowForce = row;
  	return ;
  }
  setupZTables();
  var zTable = getZTableFor(row);
  if (!zTable) {
  	return;
  }
  zTable.ensureInitialized(row);
//  if (zTable.isMultipleSelectMode) {
//	  zTable.selectRow(row,'','');
//	  zTable.firstRow=row;
//  }
//  else {
	  zTable.selectRowForce(row,e,'','');
	  selectedRowForce = row;
//  }
}


function unselectAll_fromRow(row) {
  var zTable = getZTableFor(row);
  if (!zTable) return;
  unselectAll(zTable,false);
}

function unselectAll(zTable,user) {
  if (!zTable) return;
  zTable.unselectAllRows(user);
}

/*
 Row selected handler.
*/
function rsFirst(table) {
  var zTable = getZTable(table);
  if (zTable==null)
    return;
  var row = ($ ("#"+zTable.getHtmlTableId()+" tbody tr:first"));
  if (row.size()==0) 
	  return;
  rs(row[0],e,'','');
}
function rs(row,e) {
	rs(row,e,'','');
}
function rsCell(cell,e) {
	  var zTable = getZTableForCell(cell);
	  if (zTable==null)
	    return;
	  var row = getRowForCell(cell);
	  zTable.ensureInitialized(row);
      zTable.unselectAllRows(true);
      zTable.unselectAllCells(true);
      zTable.selectCell(cell,row,'','');
}
function rs(row,e,key,descr) {
  var zTable = getZTableFor(row);
  if (!zTable) return;
  zTable.ensureInitialized(row);
  if (e && e.shiftKey && zTable.isMultipleSelectMode) {
        if (zTable.firstRow==null)
           zTable.firstRow=zTable.selectedRow();
   	    zTable.unselectAllRows(true);
	  	zTable.selectGroupRow(zTable.firstRow,row,key,descr);
  }
  else {
	if (e && (!isCTRL(e) || !zTable.isMultipleSelectMode)) {
	   zTable.unselectAllRows(true);
	   zTable.clearSelection=true;
	}
	else if (zTable.isRowSelected(row)) {
	    zTable.unselectRow(row);
	    return;
    }
	zTable.selectRow(row,key,descr);
    zTable.firstRow=row;
  }
}
function selectSpecial(ev,obj,name) {
	if ($(obj)[0].checked) 
		$(obj).closest('tr').addClass('selected_'+name) 
	else 
		$(obj).closest('tr').removeClass('selected_'+name)
}
function rsKeyPressGrid(table,e) {
	var keynum;
	var keychar;
	var zTable = getZTableForTable(table);
	if (zTable==null)
	    return true;
	if(window.event) // IE
	  keynum = e.keyCode;
	else if(e.which) // Netscape/Firefox/Opera
	  keynum = e.which;

	if (focusObject) {
		var foco=document.getElementById(focusObject);
		if (foco && foco.tagName=="SELECT") return true;
		if (focusObject.indexOf("_row_")==-1) return gaOnKeyF(e,null);
	}

	return gaOnKeyF(e,null);
} 


function rsKeyPressTable(table,e) {
	var keynum;
	var keychar;
 	var zTable = getZTableForTable(table);
	if (zTable==null)
	    return true;
	if(window.event) // IE
	  keynum = e.keyCode;
	else if(e.which) // Netscape/Firefox/Opera
	  keynum = e.which;
	  
	if (keynum==38) {
		zTable.rsDown(e);
	    return false;
	}  
	else if (keynum==40) {
		zTable.rsUp(e);
	    return false;
	}  
	else if (keynum==32) {
		zTable.rsSelect(e);
	    return false;
	}  
	else if (keynum==17) {
		/*ctrl*/
	}  
	else if (keynum==16) {
		/*shift*/
	}  
	//else 
	  //alert("key table "+keynum);
	return gaOnKeyF(e,null);
} 

function newRow(r,i,d) {
	var a = new Array();
	a[0]=r;
	a[1]=i;
	a[2]=d;
	return a;
}


function rsKey(row, key, description,e) {
//  row.id = key;
  var zTable = getZTableFor(row);
  zTable.isFormLovMode = true;
  rs(row,e,key,description);
}

/* 
 Header cell sort request handler
*/
function hsc(headerCell){
// if (!document.ie4) return;
 dir = 1;
 if(headerCell.sortOrder == 1) dir = -1;
 theHead = headerCell.parentNode;
 theTab = theHead.parentNode.parentNode;
 for(i=0;i<theHead.cells.length;i++){
   if(theHead.cells[i]==headerCell){
     colNum = i;
     headerCell.sortOrder=dir;
   } else
     theHead.cells[i].sortOrder=0;
 } 
 do {
   sorted = true;
   for(i=1;i<theTab.rows.length-1;i++){
     swap = false;
     fVal = theTab.rows[i].cells[colNum].childNodes.length>1?theTab.rows[i].cells[colNum].childNodes[0].innerHTML:theTab.rows[i].cells[colNum].innerHTML;
     sVal =  theTab.rows[i].cells[colNum].childNodes.length>1?theTab.rows[i+1].cells[colNum].childNodes[0].innerHTML:theTab.rows[i+1].cells[colNum].innerHTML;
     if(!isNaN(fVal * 1)){
       fVal *= 1; 
       sVal *= 1;
     }
     if(dir==1){
       if(fVal < sVal) swap = true;
     } else {
       if(fVal > sVal) swap = true;
     }
     if(swap == true){
     	var row1 =  theTab.rows[i];
     	var row2 =  theTab.rows[i+1];
        swapNodes(row2,row1);
        sorted = false 
      }
   } 
 } while (sorted == false);
}

function swapNodes(item1,item2) {
	var itemtmp = item1.cloneNode(1);
	var parent = item1.parentNode;
	item2 = parent.replaceChild(itemtmp,item2);
	parent.replaceChild(item2,item1);
	parent.replaceChild(item1,itemtmp);
	itemtmp = null;
}
/////////////////////////////////////////////////////////////////////
//
//  ZTable object implementation
//
/////////////////////////////////////////////////////////////////////
ZTableCellColorScheme = function(zFore,zBack,zImage) {
this.foreColor = zFore;
this.backColor = zBack;
this.backImage = zImage;
}

ZTable = function(zWinsId, zActionsArr, zNotActionsArr, zIsMultiple,zLineSelect, zHtmlTableId, zActionBarDOMId, zObjectProvider,zMoreSelections, zIsDatatable,zWinsIdDest) {

// the selected row
this.blockSelection = false;
this.firstRow = null;
this.overRow = null;
this.clearSelection = false;
this.moreSelections = zMoreSelections;
// the wins object id
this.winsObjectId = zWinsId;
this.winsObjectDest = zWinsIdDest;
// an array with: 0 - the action id; 1 - an array of allowed wins
this.actionsArr = zActionsArr;
this.notActionsArr = zNotActionsArr;
this.actionBarDOMId = zActionBarDOMId;
this.isDatatable = zIsDatatable;
// Multiple Selection
this.isLineSelect = zLineSelect;
this.isMultipleSelectMode = zIsMultiple;
// FormLov Mode
this.isFormLovMode = false;
this.htmlTableId = zHtmlTableId;
this.objectProvider = zObjectProvider;
this.oTable=null;
}

ZTable.prototype.isDataTable = function() {
	return this.isDatatable;
}
ZTable.prototype.isSwap = function() {
	return this.winsObjectDest!=null;
}
ZTable.prototype.registerTable = function() {
//  alert("Register table: "+this.getHtmlTableId());
  var pos = getPosZTableForId(this.getHtmlTableId())
  if (pos!=-1) 
  	regTables[pos] = new Array(this.getHtmlTableId(), this, getElement(this.getActionBarDOMId()));
  else
	regTables[regTables.length] = new Array(this.getHtmlTableId(), this, getElement(this.getActionBarDOMId()));
  return true;
}

//ZTable.prototype.findBotonGap = function() {
//	// busco el gap entre iconos de la barra de acciones
//  
//  var bar = getElement(this.actionBarDOMId)
//  if (!bar) return 0;
//  if (!bar.childNodes) return 0;
//  var len = bar.childNodes.length;
//  if (len<2) return 0;
//  
//  var firstbot = bar.childNodes[0];
//  var secondbot = bar.childNodes[1];//getElement(this.actionsArr[1][0]);
////  var gap = getRealLeft(firstbot)-getRealLeft(secondbot);
////  if (gap!=0) return -gap;
////  gap = getRealTop(firstbot)-getRealTop(secondbot);
////  if (gap!=0) return gap;
//  if (bar.offsetHeight>bar.offsetWidth)
//	  return -firstbot.offsetHeight;
//  return firstbot.offsetWidth;
//}

//ZTable.prototype.findOffset = function(gap) {
//	// busco el gap entre iconos de la barra de acciones
//  var len = this.actionsArr.length;
//  if (len<1) return 0;
//  var firstbot = getElement(this.actionsArr[0][0]);
//  if (gap<0)
//	  return getRealTop(firstbot);
//  else
//	  return getRealLeft(firstbot);
//}

ZTable.prototype.setup = function() {
//	this.actionGap = this.findBotonGap();
//	this.actionOffset = this.findOffset(this.actionGap);
	this.unselectAllRows(false);
}

ZTable.prototype.updateActionBar = function() {
  var i = 0;
  var visibles=0;
  var old= -1;
  
  if (this.actionsArr) {
	  for (i=0; i < this.actionsArr.length; i++) {
		    var currArr = this.actionsArr[i];
		    this.showHideAction(currArr[0], currArr[1], currArr[2], currArr[3],false);
	  }	  
  }
  if (this.notActionsArr) {
	  for (i=0; i < this.notActionsArr.length; i++) {
		    var currArr = this.notActionsArr[i];
		    this.showHideAction(currArr[0], currArr[1], currArr[2], currArr[3],true);
	  }	  
  } 
}

 
ZTable.prototype.showHideAction = function(zActionId, zAllowedObjectsArr, zMultipleAllowed, zIsWin, negado) {
  var bMultiAction = true;
  var bFind;
  var prevBot;
  if (this.isMultipleSelection()||(this.isSwap()&&zActionId.endsWith("_4"))) {
      	if (!zMultipleAllowed) {
          bShowAction = false;
  	  	}
  	  	else {
			bFind = true;  	  	
			var rows = this.selectedRows();
			for(iSel=0;iSel<this.selectedRows().length;iSel++) {
				var row = rows[iSel]
				var key = this.isSwap()?row.value:row.id;
		        bFind &= this.showHideActionSingle(key, zActionId, zAllowedObjectsArr, zMultipleAllowed, row, zIsWin, negado);
		        if (!bFind) 
		        	break;
		    }
	        bShowAction = bFind;
        }
    }
    else {
		  var iSelected;
		  var row = this.selectedRow();
		  if (row==null) {
		    iSelected="";
		  } else {
			if (this.isSwap())
			    iSelected = row.value;
			else
				iSelected = row.id;
		  }
		  bShowAction = this.showHideActionSingle(iSelected, zActionId, zAllowedObjectsArr, zMultipleAllowed, row,zIsWin, negado);
	}

	  var sButtonId = zActionId;
	  while (sButtonId.indexOf('.')!=-1) {
	    sButtonId = sButtonId.replace('.','_');
	  }
	
	  var oButton = getElement(sButtonId); // CAMBIAR ESTO !!! que no va a andar !!!
	 /* 
	  if (!zMultipleAllowed && this.isMultipleSelection())
	    bShowAction = false;
	  */
	  if (this.isFormLovMode)
	  	bShowAction = true;
	  	
	  if (oButton==null || oButton.style==null) {
	  	return null; // no se por que per oa veces viene en nulo
	  }
	
	  checkHelpShow(oButton);
	  
	  if (bShowAction==true) {
	    $(oButton).removeClass('hidden');
	    $(oButton).closest('li').removeClass('hidden');
	  } else {
		$(oButton).addClass('hidden');
		$(oButton).closest('li').addClass('hidden');
	  }
	  return oButton;
	  
}

ZTable.prototype.showHideActionSingle = function(iSelected, zActionId, zAllowedObjectsArr, zMultipleAllowed, row, zIsWin, negado) {
  var bShowAction = negado;
  var iCount = zAllowedObjectsArr.length;

  if (zIsWin && iSelected=='') 
	  return false;

  var iCounter = 0;
  for (iCounter=0; iCounter < iCount; iCounter++) {
	  
    if (zAllowedObjectsArr[iCounter] == null) {
	  bShowAction = negado?true:false;
      break;
    }
    if ((zAllowedObjectsArr[iCounter])==this.winsObjectId) {
      bShowAction = negado?false:true;
      break;
    }
    if (iSelected!="" && (zAllowedObjectsArr[iCounter])==iSelected) {
      bShowAction = negado?false:true;
      break;
	}
  }
  return bShowAction;
}

ZTable.prototype.ensureInitialized = function(row) {
}

ZTable.prototype.paintNormalCell = function(cell) {
	 $(cell).removeClass("selected");
	 $(cell).removeClass("over");
}

ZTable.prototype.markIcon = function(row, mark) {
	var elem = $(row).find("i[id=main_icon]");
	if (mark) {
		var old = elem.attr('class');
		elem.attr('oldclass', old);
		old = elem.attr('style');
		elem.attr('oldstyle', old);
		elem.removeAttr('class');
		elem.removeAttr('style');
		elem.attr('class', 'far fa-check-circle');
		elem.attr('style', 'color:black;font-size:20');
	} else {
		var old = elem.attr('oldclass');
		elem.removeAttr('class');
		elem.removeAttr('oldclass');
		elem.attr('class', old);
		var old = elem.attr('oldstyle');
		elem.removeAttr('oldstyle');
		elem.removeAttr('style');
		elem.attr('style', old);
	}
}
ZTable.prototype.paintNormal = function(row) {
	 $(row).removeClass("selected");
	 $(row).removeClass("over");
	 this.markIcon(row, false);
}

ZTable.prototype.paintOver = function(row) {
	 $(row).addClass("over");
}
ZTable.prototype.paintOverCell = function(row,cell) {
	 $(cell).addClass("over");
}
ZTable.prototype.unpaintOver = function(row) {
	 $(row).removeClass("over");
}
ZTable.prototype.unpaintOverCell = function(row,cell) {
	 $(cell).removeClass("over");
}

ZTable.prototype.paintSelectedCell = function(row,cell) {
	$(cell).addClass("selected");
}

ZTable.prototype.paintSelected = function(row) {
  if (!this.isLineSelect) return;
	$(row).addClass("selected");
	this.markIcon(row, true);
}
ZTable.prototype.selectedCells  = function() {
	  return $('#'+this.htmlTableId+' td.selected');
}
ZTable.prototype.selectedRows  = function() {
	if (this.isSwap()) {
		return $('#'+this.htmlTableId+' option:selected');
	}
	return $('#'+this.htmlTableId+' tr.selected');
}
ZTable.prototype.selectedRowsSpecial  = function(name) {
	  return $('#'+this.htmlTableId+' tr.selected_'+name);
}
ZTable.prototype.selectedRow  = function() {
	  if (this.isLinesSelected()) {
		  var data =this.selectedRows();
		  if (data==null) return null;
		  if (data.length==0) return null;
		  return data[0];
	  }
	  var data =$('#'+this.htmlTableId+' tr td.selected');
	  if (data==null) return null;
	  if (data.length==0) return null;
	  return data[0].closest('tr');	  	
	  

}
ZTable.prototype.selectedRowSpecial  = function(name) {
	  var data =this.selectedRowsSpecial(name);
	  if (data==null) return null;
	  if (data.length==0) return null;
	  return data[0];
	  

}
ZTable.prototype.selectedCell  = function() {
	  var data =$('#'+this.htmlTableId+' tr td.selected');
	  if (data==null) return null;
	  if (data.length==0) return null;
	  return data[0];
}

/*
  Action owner provider implementation.
*/
ZTable.prototype.isMultipleSelection = function() {
//	if (this.isSwap())
//		return true;
	var rows=this.selectedRows();
	if (rows==null) return false;
	return rows.length>1;
}
ZTable.prototype.isMultipleSelectionSpecial = function(name) {
	return true;
}
ZTable.prototype.isLinesSelected = function() {
	if (this.isSwap()) return true;
	if (this.isLineSelect==null) return false;
	return this.isLineSelect;
}
ZTable.prototype.isRowSelected = function(row) {
  return $(row).hasClass("selected");
}
ZTable.prototype.isCellSelected = function(row,cell) {
  return $(cell).hasClass("selected");
}


ZTable.prototype.unselectAllRows = function(user) {
  if (this.blockSelection) return;
  var ztable=this;
  var data =this.selectedRows();
  data.each(function() {
	  ztable.paintNormal( this );
  });
  this.updateActionBar();
}

ZTable.prototype.unselectAllCells = function(user) {
	  if (this.blockSelection) return;
	  var ztable=this;
	  var data =this.selectedCells();
	  data.each(function() {
		  ztable.paintNormalCell( this );
	  });
	  this.updateActionBar();
	}

ZTable.prototype.unselectRow = function(row) {
  if (this.blockSelection) return;
  this.paintNormal(row);
  this.updateActionBar();
}
ZTable.prototype.selectRowForce = function(row,e,key,descr) {
  var  rowSel = this.selectedRow();
  if (rowSel==row) return;
  this.unselectAllRows(true);
  this.blockSelection=true;
  this.paintSelected(row);
  this.updateActionBar();
  this.blockSelection=false;
}

ZTable.prototype.selectCellForce = function(cell,row,key,descr) {
	  this.unselectAllRows(true);
	  this.unselectAllCells(true);
	  this.blockSelection=true;
	  this.paintSelectedCell(row,cell);
	  this.updateActionBar();
	  this.blockSelection=false;
}
ZTable.prototype.selectCell = function(cell,row,key,descr) {
  if (this.blockSelection) return;
  this.paintSelectedCell(row,cell);
  this.updateActionBar();
}

ZTable.prototype.selectRow = function(row,key,descr) {
  if (this.blockSelection) return;
  this.paintSelected(row);
  this.updateActionBar();
  row.focus();
} 

ZTable.prototype.selectGroupRow = function(fr,row,key,descr) {
  this.unselectAllRows(true);
  if (fr==null) {
       this.selectRow(row,key,descr);
       return;
  }
  var firstRow=fr.rowIndex<row.rowIndex?fr.rowIndex:row.rowIndex;
  var lastRow=fr.rowIndex>row.rowIndex?fr.rowIndex:row.rowIndex;
  var rc;
  table = getElement(this.getHtmlTableId());
  for(rc=firstRow;rc<=lastRow;rc++) {
      var workRow = table.rows[rc];
      this.ensureInitialized(workRow);
      if (!this.isRowSelected(workRow)) {
    	   this.selectRow(workRow,getKeyFromTitle(workRow.title),getDescrFromTitle(workRow.title));
	  }
  }
  this.updateActionBar();
}

function getKeyFromTitle(title) {
	return title.substring(0,title.indexOf(";"));
}
function getDescrFromTitle(title) {
	return title.substring(title.indexOf(";")+1);
}
ZTable.prototype.rsScrollRight = function(e) {
	table = getElement(this.getHtmlTableId());
	if ($(table).parents('.table-responsive').scrollLeft()+10>$(table).parents('.table-responsive')[0].scrollWidth) return;
	$(table).parents('.table-responsive').scrollLeft($(table).parents('.table-responsive').scrollLeft()+10);
}
ZTable.prototype.rsScrollLeft = function(e) {
	table = getElement(this.getHtmlTableId());
	if ($(table).parents('.table-responsive').scrollLeft()-10<0) return;
	$(table).parents('.table-responsive').scrollLeft($(table).parents('.table-responsive').scrollLeft()-10);
}
ZTable.prototype.rsDown = function(e) {
  	table = getElement(this.getHtmlTableId());
    if (this.selectedRow()==null) {
  	    var row = table.rows[1];
	    rs(row,e);
	    row.click();
	    this.adjustScroll(table,row);
	    return;
    }
	  if (isCTRL(e) && this.isMultipleSelectMode) {
	      if (this.overRow==null) {
	      	this.overRow=this.selectedRow();
	      }
	  	  var pos = this.overRow.rowIndex -1;
	  	  if (pos>0) {
		  	table = getElement(this.getHtmlTableId());
	  	    var row = table.rows[pos];
		    rmo(this.overRow,false,e);
		    rmo(row,true,e);
		    this.overRow=row;
		    this.adjustScroll(table,this.overRow);
		  }
	  } else if (e.shiftKey && this.isMultipleSelectMode) {
	      if (this.overRow==null) {
	      	this.overRow=this.selectedRow();
	      }
	  	  var pos = this.overRow.rowIndex -1;
	  	  if (pos>0) {
		  	table = getElement(this.getHtmlTableId());
	  	    var row = table.rows[pos];
		    rs(row,e);
		    this.overRow=row;
		    this.adjustScroll(table,this.overRow);
	  	   }
	  } else {
		  if (this.overRow!=null) {
		    this.unselectAllRows(true);
		    this.paintSelected(this.overRow);
		    this.overRow=null;
		  }
	  	  var pos = this.selectedRow().rowIndex -1;
	  	  if (pos>0) {
		  	table = getElement(this.getHtmlTableId());
	  	    var row = table.rows[pos];
	  	    if (row.style.display == 'none') row = table.rows[pos-1];
	  	    if (row.id!=null && row.id!="") {
			    rs(row,e);
			    row.click();
			    this.adjustScroll(table,row);
	 		    this.clearSelection=true;
	  	    }
		  }
	  
	  }
}

ZTable.prototype.adjustScroll = function(table,row) {
	var posViewIni= $(document).scrollTop();
	var posViewEnd= $(document).scrollTop()+window.innerHeight;
	var hHeader = $(table)[0].tHead.offsetHeight;
  var pos = $(row).offset().top;
	if (pos-hHeader<posViewIni)
		$(document).scrollTop(pos-hHeader);
	if (pos+$(row).height()+10>posViewEnd) 
		$(document).scrollTop((pos+$(row).height()+10)-window.innerHeight);
}

ZTable.prototype.rsUp = function(e) {
  	table = getElement(this.getHtmlTableId());
    if (this.selectedRow()==null) {
  	    var row = table.rows[1];
	    rs(row,e);
	    row.click();
	    this.adjustScroll(table,row);
	    return;
    }
	if (isCTRL(e) && this.isMultipleSelectMode ) {
      if (this.overRow==null) {
      	this.overRow=this.selectedRow();
      }
 	  var pos = this.overRow.rowIndex +1;
  	  if (pos<table.rows.length) {
  	    var row = table.rows[pos];
	    rmo(this.overRow,false,e);
	    rmo(row,true,e);
	    this.overRow=row;
	    this.adjustScroll(table,this.overRow);
 	  } 
  	} else if (e.shiftKey && this.isMultipleSelectMode) {
      if (this.overRow==null) {
      	this.overRow=this.selectedRow();
      }
 	  var pos = this.overRow.rowIndex +1;
  	  if (pos<table.rows.length) {
  	    var row = table.rows[pos];
  	    if (row.id!=null && row.id!="") {
		    rs(row,e);
		    this.overRow=row;
		    this.adjustScroll(table,this.overRow);
  	    }
  	  } 
  	} else {
	  if (this.overRow!=null) {
	    this.unselectAllRows(true);
	    this.paintSelected(this.overRow);
	    this.overRow=null;
	  }
 
  	  var pos = this.selectedRow().rowIndex +1;
  	  if (pos<table.rows.length) {
  	    var row = table.rows[pos];
  	    if (row.id!=null && row.id!="") {
		    rs(row,e);
		    row.click();
		    this.adjustScroll(table,row);
	  	    this.clearSelection=true;
  	    }
  	  }
  }
}


ZTable.prototype.rsSelect = function(e) {
    if (!isCTRL(e) || !this.isMultipleSelectMode) return;
    if (this.overRow) {
	    rs(this.overRow,e);
    }
    else {
        this.overRow=this.selectedRow();
    }
}

ZTable.prototype.getCurrentActionOwner = function() {
	if (this.isSwap()) 
		return this.getMultipleCurrentActionOwner();
  if (this.isMultipleSelection())
  	return this.getMultipleCurrentActionOwner();
  else
  	return this.getSingleCurrentActionOwner();
}
ZTable.prototype.getCurrentActionOwnerFromSelect = function() {
  	return this.getMultipleCurrentActionOwner();
}
ZTable.prototype.getSelection = function() {
	  if (this.isMultipleSelection())
	  	return this.getMultipleActionOwnerList();
	  var row=this.selectedRow();
	  
	  if (row==null)
	  	return '';
	  if (this.isSwap())
		  return row.value+';'
	  return row.id+';'
	}
ZTable.prototype.getSelectionSpecial = function(name) {
	if (this.isSwap())
		return this.getMultipleActionOwnerListSpecialSwap(name);

	return this.getMultipleActionOwnerListSpecial(name);
	}
ZTable.prototype.getClearSelection = function() {
  var clear = this.clearSelection;
//  this.clearSelection=false;
  return clear;
}
ZTable.prototype.hasMoreSelections = function() {
	  return this.moreSelections;
	}
ZTable.prototype.rs_forcedJSON = function() {
	  var row = this.selectedRow();
      if (row==null) {
	  	return ;
	  }
	  row.click();
}

ZTable.prototype.getSingleCurrentActionOwner = function() {
  if (!this.isLineSelect && this.getSelectCell() && this.getSelectCell().id!='') {
	  return this.getSelectCell().id;
  }
  if (this.selectedRow() != null)
  	return this.selectedRow().id;
  if (selectedRowForce != null)
	  	return selectedRowForce.id;
  return null;
}

ZTable.prototype.getHtmlTableId = function() {
	return this.htmlTableId;
}
ZTable.prototype.getActionBarDOMId = function() {
	return this.actionBarDOMId;
}
ZTable.prototype.getObjectProvider = function() {
	return this.objectProvider;
}

ZTable.prototype.getMultipleCurrentActionOwner = function() {
  return this.winsObjectId;
}
ZTable.prototype.getMultipleCurrentActionOwnerDest = function() {
	  return this.winsObjectDest;
	}
ZTable.prototype.getMultipleActionOwnerList = function() {
	  if (this.isSwap())
		  return this.getMultipleActionOwnerListSwap();
	  if (!this.isMultipleSelection()) 
		  return '';

	  var selectedIds='';
	  var data =this.selectedRows();
	  var i;
	  data.each(function() {
		    currRow =  this;
		    selectedIds += currRow.id + ';';
	  });
	  return selectedIds;
	}
ZTable.prototype.getMultipleActionOwnerListSpecialSwap = function() {
	  var selectedIds='';
	  var data = $('#'+this.htmlTableId+ " option:selected");
	
	  var i;
	  data.each(function() {
		    currRow =  this;
		  //  if($(this).is(':visible'))
		    	selectedIds += currRow.value + ';';
	  });
	  return selectedIds;
	}
ZTable.prototype.getMultipleActionOwnerListSpecial = function(name) {
  var selectedIds='';
  var data =this.selectedRowsSpecial(name);
  var i;
  data.each(function() {
	    currRow =  this;
	    selectedIds += currRow.id + ';';
  });
  return selectedIds;
}
ZTable.prototype.hasMultipleSelectSpecial = function(name) {
	 return this.isMultipleSelectionSpecial(name);
}
ZTable.prototype.hasMultipleSelect = function() {
	  if (this.isSwap())
		  return true;
	 return this.isMultipleSelection();
}
ZTable.prototype.getMultipleActionOwnerListSwap = function() {
	  var selectedIds='';
	  var data = $('#'+this.htmlTableId+ " option");
	
	  var i;
	  data.each(function() {
		    currRow =  this;
		  //  if($(this).is(':visible'))
		    	selectedIds += currRow.value + ';';
	  });
	  return selectedIds;
	}
ZTable.prototype.getSelectCell = function() {
    var cell =  $('#'+this.htmlTableId+ " td.selected");
    if (cell.length==0) return null;
    return cell[0];
/*    var aCells = getCellsFor(row);
    var oCell;
    for (i=0;i<aCells.length-1;i++) {
      oCell = aCells[i];
      if (oCell==this.selectedCell) {
         return oCell.axis;
  	  }
    }*/
	return 0;
}
ZTable.prototype.getSelectedCell = function() {
    var cell =  $('#'+this.htmlTableId+ " td.selected");
    if (cell.length==0) return null;
    return cell[0].axis;
/*    var aCells = getCellsFor(row);
    var oCell;
    for (i=0;i<aCells.length-1;i++) {
      oCell = aCells[i];
      if (oCell==this.selectedCell) {
         return oCell.axis;
  	  }
    }*/
	return 0;
}

ZTable.prototype.getSelectedRow = function() {
    var row = this.selectedRow();
    if (row==null) return 0;
    return row.getAttribute('rowpos');
}

ZTable.prototype.getCurrentSelectedKey = function() {
  var selectedIds = '';
  var ztable=this;
  var data =this.selectedRows();
  var i;
  for(i=0;i<data.length;i++) {
	currRow =  document.getElementById(data[i].DT_RowId);
	if (ztable.isMultipleSelectMode)
		selectedIds += currRow.dataset.key + '; ';
	else {
		selectedIds += currRow.dataset.key;
		break;
	}
  };		  
  return selectedIds;
}

ZTable.prototype.getCurrentSelectedDescription = function() {
	  var selectedDescrs = '';
	  var ztable=this;

	  var data =this.selectedRows();
	  data.each(function() {
			currRow = this;
			if (ztable.isMultipleSelectMode)
				selectedDescrs += currRow.dataset.descr + '; ';
			else {
				selectedDescrs += currRow.dataset.descr;
				return;
			}
	  });
	  

	  return selectedDescrs;
	}


/////////////////////////////////////////////////////////////////////
//
//  ZForm object implementation
//
/////////////////////////////////////////////////////////////////////
ZForm = function(zWinId, zEmbedded) {
// the win object id
this.winObjectId = zWinId;
this.embedded = zEmbedded;
}
/*
  Action owner provider implementation.
*/
ZForm.prototype.getCurrentActionOwner = function() {
  return this.winObjectId; 
}
ZForm.prototype.getCurrentActionOwnerFromSelect = function() {
	  return this.winObjectId; 
	}

ZForm.prototype.getMultipleCurrentActionOwnerDest = function() {
	  return null;
	}

ZForm.prototype.isEmbedded = function() {
  return this.embedded == 1;
}

ZForm.prototype.getSelectedCell = function() {
  return '';
}
ZForm.prototype.getSelectedRow = function() {
	  return '';
	}
ZForm.prototype.getMultipleActionOwnerList = function() {
	  return '';
	}
ZForm.prototype.hasMultipleSelect = function() {
	 return false;
}
ZForm.prototype.getSelection = function() {
	  return '';
	}

ZForm.prototype.getClearSelection = function() {
	  return false;
	}
ZForm.prototype.hasMoreSelections = function() {
	  return false;
	}

/////////////////////////////////////////////////////////////////////
//
//  utilities
//
/////////////////////////////////////////////////////////////////////
function getFirstTable() {
	  var currAss;
	  for (i=0;i<regTables.length;i++) {
	    currAss = regTables[i];
	    if (currAss==null) continue;
	    return currAss[0];
	  }
	  return null;
}
function getZTableForTableID(idtable) {
	  var currAss;
	  for (i=0;i<regTables.length;i++) {
	    currAss = regTables[i];
	    if (currAss!=null && currAss[0]==idtable) {
		  return currAss[1];
		}
	  }
	  return null;
	}

function getZTableForTable(table) {
  var currAss;
  var htmlTable = table;
  for (i=0;i<regTables.length;i++) {
    currAss = regTables[i];
    if (currAss!=null && currAss[0]==htmlTable.id) {
	  return currAss[1];
	}
  }
  return null;
}
function getPosZTableForId(tableId) {
  var currAss;
  for (i=0;i<regTables.length;i++) {
    currAss = regTables[i];
    if (currAss!=null && currAss[0]==tableId) {
	  return i;
	}
  }
  return -1;
}
function getZTableForCell(cell) {
	  var currAss;
	  if (!cell) return null;
	  if (!cell.parentNode) return null;
	  if (!cell.parentNode.parentNode) return null;
	  if (!cell.parentNode.parentNode.parentNode) return null;
	  var htmlTable = cell.parentNode.parentNode.parentNode;
	  for (i=0;i<regTables.length;i++) {
	    currAss = regTables[i];
	    if (currAss!=null && currAss[0]==htmlTable.id) {
		  return currAss[1];
		}
	  }
	  return null;
	}
function getRowForCell(cell) {
	  var currAss;
	  if (!cell) return null;
	  if (!cell.parentNode) return null;

	  return cell.parentNode;
	}
function getZTableFor(row) {
  var currAss;
  if (!row) return null;
  if (!row.parentNode) return null;
  if (!row.parentNode.parentNode) return null;
  var htmlTable = row.parentNode.parentNode;
  for (i=0;i<regTables.length;i++) {
    currAss = regTables[i];
    if (currAss!=null && currAss[0]==htmlTable.id) {
	  return currAss[1];
	}
  }
  return null;
}
function getActionBarFor(zTable) {
  var currAss;
  for (i=0;i<regTables.length;i++) {
    currAss = regTables[i];
    if (currAss!=null && currAss[1]==zTable) {
	  return currAss[2];
	}
  }
  return null;
}
function getCellsFor(row) {
  return row.cells;
}
function getForeFor(x) {
  var y;
  if (window.getComputedStyle) {
    y = document.defaultView.getComputedStyle(x,null).getPropertyValue('color');
  } else if (x.currentStyle) {
	y = x.currentStyle.color;
  }
  return y;
}
function getBackFor(x) {
  var y;
  if (window.getComputedStyle) {
    y = document.defaultView.getComputedStyle(x,null).getPropertyValue('background-color');
  } else if (x.currentStyle) {
	y = x.currentStyle.backgroundColor;
  }
  return y;
}

function getImageFor(x) {
  var y;
  if (window.getComputedStyle) {
    y = document.defaultView.getComputedStyle(x,null).getPropertyValue('background-image');
  } else if (x.currentStyle) {
	y = x.currentStyle.backgroundImage;
  }
  return y;
}
