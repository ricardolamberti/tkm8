var valArr = new Array();
var valCount = 0;
var dependenciesArr = new Array();
var valArrNC = new Array();
var valCountNC = 0;
var valArrMeta = new Array();
var valCountMeta = 0;
var valArrTable = new Array();
var valCountTable = 0;

var scriptCalculateArr = new Array();
var scriptCalculateCount = 0;

var scriptStartedArr = new Array();
var scriptStartedCount = 0;

/* 
 PUBLIC FUNCTIONS
 ----------------
 - register a field for validation
 - ask to perform all the registered validations
*/ 

function register(fName, fDesc, req, needsInputCheck, maxLength, dt, pattern, chars, inputmode, changeColor, align) {
  register(fName, fDesc, req, needsInputCheck, maxLength, dt, pattern, chars, inputmode, changeColor, align, null, false);
}

function register(fName, fDesc, req, needsInputCheck, maxLength, dt, pattern, chars, inputmode, changeColor, align, errMsg, intable) {
  valIndex = getPosField(fName); 
  if (valIndex==-1) { 
  	valIndex=valCount;
	  valCount++;
  }
  valArr[valIndex] = [fName, fDesc, req, needsInputCheck, maxLength, dt, pattern, chars, errMsg, changeColor, inputmode, align, intable];
  
}
function registerMeta(fName,values) {
	  valIndex = getPosFieldMeta(fName); 
	  if (valIndex==-1) { 
	  	valIndex=valCountMeta;
		valCountMeta++;
	  }
	  valArrMeta[valIndex] = [fName,values];
	  
	}
function registerNoConstraint(fName,values) {
	registerNoConstraint(fName,values,false);
}
function registerNoConstraint(fName,values,descr,req) {
  valIndex = getPosFieldNC(fName); 
  if (valIndex==-1) { 
  	valIndex=valCountNC;
	valCountNC++;
  }
  valArrNC[valIndex] = [fName,values,descr,req];
  
}
function rdg(fName,name,values,row,column) {
	registerDataInGrid(fName,name,values,row,column)
}


function registerDataInGrid(fName,name,values,row,column) {
	if (column<0) return;
	var posTable = getPosFieldTable(fName)
	if (posTable==-1) {
		registerGrid(fName,new Array(),0,new Array(),"","");
		posTable = getPosFieldTable(fName);
	}
	var regTable = valArrTable[posTable];
	var table = regTable[1];
	var fieldnames = regTable[3];
	var variable = regTable[4];
	var fn = regTable[5];
	var fieldnames=registerGridFieldname(fieldnames,name,column);
	if (table.length<row+1) {
		for(var i=table.length;i<row+1;i++) 
			table[i]=new Array(regTable[2]);
	}
	var r = table[row];
	if (r.length<column+1) {
		for(var i=r.length;i<column+1;i++) 
			r[i]=(new Array(2));
	}	
	r[column]=[column,values];
	registerGrid(fName,table,column,fieldnames,variable,fn);
}
function registerGridFieldname(fieldname,fname,column) {
	for(var i=0;i<fieldname.length;i++) {
		if (fieldname[i]==column+":"+fname) return fieldname;
	}
	fieldname[fieldname.length]=column+":"+fname;
	return fieldname;
}
function registerGrid(fName,table,columns,fieldnames, variable,fieldname) {
  valIndex = getPosFieldTable(fName); 
  if (valIndex==-1) { 
  	valIndex=valCountTable;
  	valCountTable++;
  }
  if (table==null) table = new Array();
  if (fieldnames==null) fieldnames = new Array();
  valArrTable[valIndex] = [fName,table,columns,fieldnames,variable,fieldname];
  
}


var connectControls=null;
function unsubscribeControlConnect() {
	connectControls=null;
}
function getControlConnect() {
	if (connectControls==null) connectControls = new Array();
	return connectControls;
}
function subscribeControlConnect(control,funcion) {
	var cc=getControlConnect();
	var pos = cc.length;
	cc[pos] = [control,funcion];
}

function runControlConnect(control) {
	var cc=getControlConnect();
	var i=0;
	for (i=0;i<cc.length;i++) {
	   var currAss = cc[i];
	   if (currAss[0]!=control) continue;
	   currAss[1]();
	}
}
function runAllControlConnect() {
	var cc=getControlConnect();
	var i=0;
	for (i=0;i<cc.length;i++) {
	   var currAss = cc[i];
	   currAss[1]();
	}
}
function resolve(fName) {
	var val= getField(fName);
	var valor;
	var convert = "error";
	if (val==null) return null;
	dt = val[5];
	//alert("Name: "+fName+" dt: "+dt);
	if (val) {
		valor = document.getElementById(fName).value;

		if (isNumberFloatDt(dt) ) {
			convert=parseFloat(valor);
        }
		else if (isNumberIntDt(dt)) {
			convert=parseInt(valor);
		}
		else 
			convert = valor;
	}
	if (!convert)
		convert=0;
	//alert("convert:"+convert);
	return convert;
}

function registerStartedScript(orden, field, script) {
  var sc = new Array();
  sc[0]=orden; sc[1]=field; sc[2]=script;
  scriptStartedArr[scriptStartedCount] = sc;
  scriptStartedCount++;
  if(orden!=null && orden!="")
  	scriptStartedArr=scriptStartedArr.sort();
}

function cleanStartedScriptRegistry() {
  scriptStartedArr = new Array();
  scriptStartedCount = 0;
}

function registerScript(orden, field, script) {
  var sc = new Array();
  sc[0]=orden; sc[1]=field; sc[2]=script; 
  scriptCalculateArr[scriptCalculateCount] = sc;
  scriptCalculateCount++;
	//  executeOneScript(script);
  if(orden!=null && orden!="")
  	scriptCalculateArr=scriptCalculateArr.sort();
}
function cleanCalculateScriptRegistry() {
  scriptCalculateArr = new Array();
  scriptCalculateCount = 0;
}

var canChange=true;
function setChangeInputs(change) {
	if (canChange)
		setModalChanges(change);
}
function startNoCanChange() {
	canChange=false;
}
function endNoCanChange() {
	canChange=true;
}
function hasChangeInputs() {
	return getModalChanges();
}
function executeOnChange(fName) {
	formatear(fName);
	executeScripts(fName);
}



function formatear(fName) {
	var val;
	var valor;
	var componente;
	var convert = "error";

	val = getField(fName)
	if (val==null) return;
	dt = val[5];
	pattern = val[6];
	chars = val[7];
	changeColor = val[9];
	inputmode = val[10];
	if (val) {
		componente = document.getElementById(fName);
		
		if (componente) {
//			componente.style.textAlign=val[11];
			valor = componente.value; 
			if (isNumberFloatDt(dt) ) {
				componente.style.paddingRight = 1;
				componente.style.paddingLeft = 1;
				convert=parseFloat(valor);
	/*			if (changeColor==1) {
					componente.style.color="#000000";
					if (convert && convert<0)
						componente.style.color="#FF0000";
				}
	*/        }
			else if (isNumberIntDt(dt)) {
				componente.style.paddingRight = 1;
				componente.style.paddingLeft = 1;
				convert=parseInt(valor);
	/*			if (changeColor==1) {
					componente.style.color="#000000";
					if (convert && convert<0)
						componente.style.color="#FF0000";
				}
			*/}
			else 
				convert = valor;
		}
		else
			convert="";
	}
	else
		convert="";

	if (inputmode!='false')
		aplicFormat(val[0],val[1],val[2],val[3],val[4],val[5],val[6],val[7],val[8])
}


function executeStartedScripts() {
	  var sc;
	  var scriptStartedIndex = 0;
	  while (scriptStartedIndex < scriptStartedCount) {
	    sc = scriptStartedArr[scriptStartedIndex];
		executeOneScript(sc[2]);
		scriptStartedIndex++;
	  }
	  return true;
}

function executeScripts(field) {
    return executeScripts(null);
}

function executeScripts(field) {
  var sc;
  for (var i=0;i<scriptCalculateCount;i++) {
	 sc = scriptCalculateArr[i];
	 if (field!=null && sc[1]==field) continue; // no ejecuta el script de donde se lanzo     
	 executeOneScript(sc[2]);
  }
  return true;
}



function executeOneScript(zScript) {
	zScript = replaceSpecialCharacters(zScript);
//	console.log("one script:"+zScript);

	if (window.ActiveXObject)			{
		window.execScript(zScript);
	}
	else /* (window.XMLHttpRequest) */		{
		window.eval(zScript);
	}
}

function legalNumericChar(e, unsigned,max) {
	var keynum
	var keychar
	var numcheck
	
	if (isCTRL(e)) return true;
	if(window.event) // IE
	{
		keynum = e.keyCode
	}
	else if(e.which) // Netscape/Firefox/Opera
	{
		keynum = e.which
	}

//	alert(keynum);
//	alert(keychar);
	if (keynum==13) //enter
		return true;
	if (keynum==27) //esc
		return true;
	if (keynum==8)
		return true;
	if (keynum==9)
		return true;
	if (keynum==37)
		return true;
	if (keynum==38)
		return true;
	if (keynum==39)
		return true;
	if (keynum==40)
		return true;
	if (keynum==46)
		return true;
	keychar = String.fromCharCode(keynum)
	if (max!=-1 && e.target.value.length>=max) {
		return false;
	}
	if (keynum==189 && !unsigned) //menos
		return true;
    if (keynum==109 && !unsigned) //menos del keypad
		return true;
	if (keynum==189 && unsigned) //menos
		return false;
    if (keynum==109 && unsigned) //menos del keypad
		return false;

	if (keynum==190)
		return event.currentTarget.value.indexOf(".")==-1;
	if (keynum==188) {
		if (event.currentTarget.value.indexOf(".")!=-1) return false;
		event.currentTarget.value = event.currentTarget.value +'.';
		return false;
	}
	if (keychar>='a' && keychar<='i') // keypad 1-9
		return true;
	if (keychar=='m') // keypad - 
		return true;
	if (keynum==189) // keypad - 
		return true;
	if (keychar=='n') // keypad . 
		return true;
	if (keynum==96) // keypad 0
		return true;
	
	numcheck = /\d/
	return numcheck.test(keychar);
}

function validationForm(zActionOwnerProvider) {

  var val;
  var valIndex = 0;
  while (valIndex < valCount) {
    val = valArr[valIndex];
    if ((zActionOwnerProvider && val[0].indexOf(zActionOwnerProvider)!=-1) ||
    	(!zActionOwnerProvider && val[0].indexOf("win_form"))
    ) {
  	if (!validate(val[0],val[1],val[2],val[3],val[4],val[5],val[6],val[7],val[8])) {
	  return false;
	}}
	valIndex++;
  }
  valIndex = 0;
  while (valIndex < valCountNC) {
	    val = valArrNC[valIndex];
	    if ((zActionOwnerProvider && val[0].indexOf(zActionOwnerProvider)!=-1) ||
	    	(!zActionOwnerProvider && val[0].indexOf("win_form"))
	    ) {
	    if (val[2]) {
		  	if (!validateReq(val[0],val[2],val[1])) {
			  return false;
			}}
	    }
		valIndex++;
	  }
  return true;
}

function getContentTable(tablename) {
  var out="";
  var r;
  var c;
  var idxtab= getPosFieldTable(tablename);
  if (idxtab==-1) return "";
  var regtab = valArrTable[idxtab];
  var table=regtab[1];
  var fieldnames=regtab[3];
  var outstr=regtab[4]+'|'+regtab[5];
  var s=fieldnames.length;
  for (r=0;r<s;r++) {
	  var row = fieldnames[r];
	  outstr += (outstr==""?"":",") + row;
  }
  var size =table.length;
  for (r=0;r<size;r++) {
	  var row = table[r];
	  var outRow="";
	  var rs=row.length;
	  for (c=0;c<rs;c++) {
		  var data = row[c];
		  if (data==null || typeof data == "undefined") continue;
		  var  val = addAnRegisterToUrlFromList(data[0],data[1])[data[0]];
//		  if (val=='') continue;
		  outRow += (outRow==""?"":",")+ data[0]+":"+ myescape(val);
 	  }
	  if (outRow=='') continue;
	  out += (out==""?"":",") + "{"+outRow+"}";
  }
  return "["+outstr+"]"+out;
}


function getPosField(fName) {
  var val;
  var valIndex = 0;
  while (valIndex < valCount) {
    val = valArr[valIndex];
    if (val[0]==fName) {
      return valIndex;
    }
	valIndex++;
  }
  return -1;
}

function getPosFieldNC(fName) {
  var val;
  var valIndex = 0;
  while (valIndex < valCountNC) {
    val = valArrNC[valIndex];
    if (val[0]==fName) {
      return valIndex;
    }
	valIndex++;
  }
  return -1;
}
function getPosFieldTable(fName) {
  var val;
  var valIndex = 0;
  while (valIndex < valCountTable) {
    val = valArrTable[valIndex];
    if (val[0]==fName) {
      return valIndex;
    }
	valIndex++;
  }
  return -1;
}
function getPosFieldMeta(fName) {
	  var val;
	  var valIndex = 0;
	  while (valIndex < valCountMeta) {
	    val = valArrMeta[valIndex];
	    if (val[0]==fName) {
	      return valIndex;
	    }
		valIndex++;
	  }
	  return -1;
	}

function getField(fName) {
  var val;
  var valIndex = 0;
  while (valIndex < valCount) {
    val = valArr[valIndex];
    if (val[0]==fName) {
      return val;
    }
	valIndex++;
  }
  return null;
}

function validateField(fName) {
  var val;
  var valIndex = 0;
  while (valIndex < valCount) {
    val = valArr[valIndex];
    if (val[0]==fName) {
      return validate(val[0],val[1],val[2],val[3],val[4],val[5],val[6],val[7],val[8]);
    }
	valIndex++;
  }
  return true;
}


/*
 INTENRAL IMPLEMENTATION
*/
function aplicFormat(fName, fDesc, req, needsInputCheck, maxLength, dt, pattern, chars, errMsg, conErrores) {
  // take the field and its value
//  alert("validate: "+fName+" "+fDesc+" "+dt+" "+pattern+" "+chars);
  var field = getElement(fName);
  if (!field || field==null) {
	return false;
  }
  var fValue = field.value;
  if (fValue==null) {
    fValue = "";
  } else if (!isPasswordDt(dt) && (!isStringDt(dt) || pattern!="JNOTTRIM")) {
    fValue = trim(fValue);
  }
  // take the suitable description
  var fDescToShow = fDesc;
  if (fDescToShow==null || (trim(fDescToShow).length)==0) {
    fDescToShow = fName;
  }
  // check max length
  if (maxLength!=-1 && maxLength!=0 && fValue.length > maxLength) {
	return false;
  }
  // check data type
  if (fValue=="") return false;
  var rightValue = null;
  if (dt=='JBOOLEAN') return true;
  else if (dt=='JDATE') rightValue = validateDate(fValue, pattern, chars,0);
  else if (dt=='JDATETIME') rightValue = validateDatetime(fValue, pattern, chars,0);
  else if (dt=='JHOUR') rightValue = validateTime(fValue, pattern, chars,0);
  else if (isNumberDt(dt)) rightValue = validateNumber(fValue, pattern, chars,0);
  else if (isStringDt(dt)) rightValue = validateString(fValue, pattern, chars,0);
  else {
    return false;
  }
  
  if (rightValue!=null) {
    field.value = rightValue;
  }
  return rightValue!=null;
}

function calculeColor(field, maxLength, dt, pattern, chars, colorError, colorOk) {
	  var fValue = field.value;
	  var oldColor = field.style.color;
	  // check max length
	  if (maxLength!=-1 && maxLength!=0 && fValue.length > maxLength) {
		field.style.foreground = colorError;
		return true;
	  }
	  
	  if (fValue=="") return true;
	  var rightValue = null;
	  if (dt=='JBOOLEAN') return true;
	  else if (dt=='JDATE') rightValue = validateDate(fValue, pattern, chars,0);
	  else if (dt=='JDATETIME') rightValue = validateDatetime(fValue, pattern, chars,0);
	  else if (dt=='JHOUR') rightValue = validateTime(fValue, pattern, chars,0);
	  else if (isNumberDt(dt)) rightValue = validateNumber(fValue, pattern, chars,0);
	  else if (isStringDt(dt)) rightValue = validateString(fValue, pattern, chars,0);
	  else {
	    return true;
	  }
	  
	  if (rightValue==null) 
		  field.style.color = colorError;
	  else
     	  field.style.color = (!oldColor || rgb2hex(oldColor)==colorError)?colorOk:oldColor;
	  
	  return true;
}
function rgb2hex(rgb) {
	if (rgb==null) return "";
    if (/^#[0-9A-F]{6}$/i.test(rgb)) return rgb;

    rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
    function hex(x) {
        return ("0" + parseInt(x).toString(16)).slice(-2);
    }
    return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
}

/*
 INTENRAL IMPLEMENTATION
*/
function validateReq(fName, fDesc, req) {
	  var field = getElement(fName);
	  if (!field || field==null) {
		return true;
	  }
	  if (field.readOnly) return true;
	  var fDescToShow = fDesc;
	  if (fDescToShow==null || (trim(fDescToShow).length)==0) {
	    fDescToShow = fName;
	  }
	  var fValue = field.value;
	  if (fValue.length < 1) {
	    if (req) {
		  showErr(field,msgRequiredField.replace("{0}", fDescToShow));
		  return false;
		}
	  }
	  return true;
}
function validate(fName, fDesc, req, needsInputCheck, maxLength, dt, pattern, chars, errMsg) {
  // take the field and its value
//  alert("validate: "+fName+" "+fDesc+" "+dt+" "+pattern+" "+chars);
  var field = getElement(fName);
  if (!field || field==null) {
//  	showDevErr("No field name with id: " + fName);
	return true;
  }
  if (field.readOnly) return true;
  if ($(field).closest(".form-group").hasClass("hidden")) return true;
  var fValue = field.value;
  if (fValue==null) {
    fValue = "";
  } else if (!isPasswordDt(dt) && (!isStringDt(dt) || pattern!="JNOTTRIM")) {
    fValue = trim(fValue);
  }
  // take the suitable description
  var fDescToShow = fDesc;
  if (fDescToShow==null || (trim(fDescToShow).length)==0) {
    fDescToShow = fName;
  }
  // check required
  if (fValue.length < 1) {
    if (req) {
	  showErr(field,msgRequiredField.replace("{0}", fDescToShow));
	  return false;
	} else {
	  return true;
	}
  }
  if (needsInputCheck!=null && needsInputCheck=="false") {
    return true;
  }
  // check max length
  if (maxLength!=-1 && maxLength!=0 && fValue.length > maxLength) {
	showErr(field,msgMaxLengthExceeded.replace("{0}", fDescToShow).replace("{1}", maxLength + ""));
	return false;
  }
  // check data type
  var rightValue = null;
  if (dt=='JBOOLEAN') return true;
  if (dt=='JSCRIPT') return true;
  if (dt=='JCOLOUR') return true;
  if (pattern=='') return true;
  else if (dt=='JDATE') rightValue = validateDate(fValue, pattern, chars,1);
  else if (dt=='JDATETIME') rightValue = validateDatetime(fValue, pattern, chars,1);
  else if (dt=='JHOUR') rightValue = validateTime(fValue, pattern, chars,1);
  else if (isNumberDt(dt)) rightValue = validateNumber(fValue, pattern, chars,1);
  else if (isStringDt(dt)) rightValue = validateString(fValue, pattern, chars,1);
  else {
    showDevErr("Unknown data type: " + dt);
    return false;
  }
  
  if (rightValue==null) {
    var msgToShow = errMsg;
    if (!msgToShow || msgToShow==null || msgToShow.length==0) {
	  msgToShow = getDefaultErrorMessage(fDescToShow, dt, pattern, chars);
	}
    showErr(field, msgToShow);
  } else {
    field.value = rightValue;
  }
  return rightValue!=null;
}
function isNumberFloatDt(dt) {
  return dt=='JFLOAT' || dt=='JCURRENCY' || dt=='JSCRIPT' ;
}
function isNumberIntDt(dt) {
  return dt=='JLONG' || dt=='JINT' ;
}
function isNumberDt(dt) {
  return dt=='JFLOAT' || dt=='JCURRENCY' || dt=='JINT' || dt=='JLONG';
}
function isStringDt(dt) {
  return dt=='JCHAR' || dt=='JPASSWORD';
}
function isPasswordDt(dt) {
  return dt=='JPASSWORD';
}

function getDefaultErrorMessage(fDesc, dt, pattern, chars) {
  var msg = "Error: {0}";
  var patternDesc = "";
  if (dt=='JDATE' || dt=='JDATETIME') {
    msg = msgInvalidDate;
	patternDesc = pattern.replace("D", descDateDField+chars.charAt(0));
	patternDesc = patternDesc.replace("M", descDateMField+chars.charAt(0));
	patternDesc = patternDesc.replace("Y", descDateYField+chars.charAt(0));
	patternDesc = patternDesc.substr(0, patternDesc.length - 1);
  } else if (dt=='JHOUR') {
    msg = msgInvalidTime;
	patternDesc = pattern.replace("H", descTimeHField+chars.charAt(0));
	patternDesc = patternDesc.replace("M", descTimeMField+chars.charAt(0));
	patternDesc = patternDesc.replace("S", descTimeSField+chars.charAt(0));
	patternDesc = patternDesc.substr(0, patternDesc.length - 1);
  } else if (isNumberDt(dt)) {
    msg = msgInvalidNumber;
	var dotIndex = pattern.indexOf(".");
    if (dotIndex >= 0) {
	  patternDesc = pattern.replace(".", chars.charAt(0));
	} else {
	  patternDesc = pattern;
	}
  	var withGrpSep = isValidNumberSepChar(chars.charAt(1));
    if (withGrpSep) {
	  var nextInsertPos = patternDesc.length - 3;
	  if (dotIndex >= 0) {
	    nextInsertPos = nextInsertPos - (patternDesc.length - dotIndex);
	  }
      while (nextInsertPos > 0) {
        patternDesc = patternDesc.substring(0, nextInsertPos) + chars.charAt(1) + patternDesc.substring(nextInsertPos, patternDesc.length);
        nextInsertPos = nextInsertPos - 3;
      }
    }
  } else if (isStringDt(dt)) {
    msg = msgInvalidString;
    if (pattern=='JUPPER_NOSPACE') {
      patternDesc = descWithNoSpaces;
    } else if (pattern=='JUPPER_ONLYLETTERORDIGIT') {
      patternDesc = descOnlyLetterOrDigit;
    } else if (pattern=='JLONG') {
	  msg = msgInvalidNumberNoFormat;
    } else  if (pattern.indexOf('#')!=-1) {
    	patternDesc = pattern.indexOf("#")+1>pattern.lenght?pattern:pattern.substring(pattern.indexOf("#")+1);
    } 
  }
  return msg.replace("{0}", fDesc).replace("{1}", patternDesc);
}


/*
 DATETIME validation
 ---------------
 param: chars - a string with the date separator
 accepts: an input value including the separator in the suitable positions
          or with no separator at all, always having the right length;
		  accepted patterns:
		  	DMY HMS, YMD HMS, MDY HMS, 
		  	DMY , YMD , MDY , 
			MY, YM
			D, M, Y
*/
function validateDatetime(fValue, pattern, chars, err) {
  var sep = ' ';
  var splittedPattern = pattern.split(sep);
  var splitted = fValue.split(sep);
  if (splittedPattern.length==1) {
    return validateDate(fValue, pattern, chars, err);
  } else {
	var onlyDate = splitted.length==1 || splitted[1]=="";
	var d = validateDate(splitted[0], splittedPattern[0], chars, err);
	var t = onlyDate?null: validateTime(splitted[1], splittedPattern[1], ':', err);
	if (d!=null&&(t==null && onlyDate)) t="00:00";
	if (d==null||t==null) return null;
    return  d+' '+t;
  }
}

/*
 DATE validation
 ---------------
 param: chars - a string with the date separator
 accepts: an input value including the separator in the suitable positions
          or with no separator at all, always having the right length;
		  accepted patterns:
		  	DMY, YMD, MDY,
			MY, YM
			D, M, Y
*/
function validateDate(fValue, pattern, chars, err) {
  var sep = chars.charAt(0);
  var splitted = fValue.split(sep);
  if (splitted.length==1) {
    return validateDateNoSep(fValue, pattern, sep, err);
  } else {
    return validateDateWithSep(splitted, pattern, sep, err);
  }
}
// date parsing with no separator in the input
function validateDateNoSep(fValue, pattern, sep, err) {
  if (pattern.length > 1 && fValue.length != expectedLengthWithoutSep(pattern)) {
    return null;
  }
  var dStr; var mStr; var yStr;
  // d, m and y
  if (pattern=="DMY") {
    dStr = fValue.substr(0,2);
	mStr = fValue.substr(2,2);
	yStr = fValue.substr(4,4);
	if (yStr.indexOf("00")==0) yStr="20"+YStr.substr(2,2);
	if (validateYMD(yStr,mStr,dStr)) return dStr+sep+mStr+sep+yStr;
	else return null;
  } else if (pattern=="YMD") {
    yStr = fValue.substr(0,4);
	mStr = fValue.substr(4,2);
	dStr = fValue.substr(6,2);
	if (yStr.indexOf("00")==0) yStr="20"+YStr.substr(2,2);
	if (validateYMD(yStr,mStr,dStr)) return yStr+sep+mStr+sep+dStr;
	else return null;
  } else if (pattern=="MDY") {
	mStr = fValue.substr(0,2);
	dStr = fValue.substr(2,2);
	yStr = fValue.substr(4,4);
	if (yStr.indexOf("00")==0) yStr="20"+YStr.substr(2,2);
	if (validateYMD(yStr,mStr,dStr)) return mStr+sep+dStr+sep+yStr;
	else return null;
  }
  // m and y
  else if (pattern=="MY") {
    mStr = fValue.substr(0,2);
	yStr = fValue.substr(2,4);
	if (yStr.indexOf("00")==0) yStr="20"+YStr.substr(2,2);
	if (validateYM(yStr,mStr)) return mStr+sep+yStr;
	else return null;
  } else if (pattern=="YM") {
	yStr = fValue.substr(0,4);
	mStr = fValue.substr(4,2);
	if (yStr.indexOf("00")==0) yStr="20"+YStr.substr(2,2);
	if (validateYM(yStr,mStr)) return yStr+sep+mStr;
	else return null;
  }
  // d, m or y
  else if (pattern=="D") {
	if (valIntRange(fValue, 1, 31)) return fValue;
	else return null;
  } else if (pattern=="M") {
	if (valIntRange(fValue, 1, 12)) return fValue;
	else return null;
  } else if (pattern=="Y") {
	if (valIntRange(fValue, 1900, 9999)) return fValue;
	else return null;
  } 
  if (err==1)
	  showDevErr("Invalid date pattern: " + pattern);
  return null;
}
function expectedLengthWithoutSep(pattern,err) {
  if (pattern.indexOf("Y") >= 0) {
    return (pattern.length * 2) + 2;
  } else {
    return pattern.length * 2;
  }
}
// date parsing with separator in the input
function validateDateWithSep(fValueSplits, pattern, sep, err) {
  if (fValueSplits.length != expectedSplitsCountWithSep(pattern)) {
    return null;
  }
  var dStr; var mStr; var yStr;
  // d, m and y
  if (pattern=="DMY") {
    dStr = lPad(fValueSplits[0],"0",2);
	mStr = lPad(fValueSplits[1],"0",2);
	yStr = lPad(fValueSplits[2],"0",4);
	if (yStr.indexOf("00")==0) yStr="20"+yStr.substr(2,2);
	if (validateYMD(yStr,mStr,dStr)) return dStr+sep+mStr+sep+yStr;
	else return null;
  } else if (pattern=="YMD") {
	yStr = lPad(fValueSplits[0],"0",4);
	mStr = lPad(fValueSplits[1],"0",2);
	dStr = lPad(fValueSplits[2],"0",2);
	if (yStr.indexOf("00")==0) yStr="20"+yStr.substr(2,2);
	if (validateYMD(yStr,mStr,dStr)) return yStr+sep+mStr+sep+dStr;
	else return null;
  } else if (pattern=="MDY") {
	mStr = lPad(fValueSplits[0],"0",2);
	dStr = lPad(fValueSplits[1],"0",2);
	yStr = lPad(fValueSplits[2],"0",4);
	if (yStr.indexOf("00")==0) yStr="20"+yStr.substr(2,2);
	if (validateYMD(yStr,mStr,dStr)) return mStr+sep+dStr+sep+yStr;
	else return null;
  }
  // m and y
  else if (pattern=="MY") {
	mStr = lPad(fValueSplits[0],"0",2);
	yStr = lPad(fValueSplits[1],"0",4);
	if (yStr.indexOf("00")==0) yStr="20"+yStr.substr(2,2);
	if (validateYM(yStr,mStr)) return mStr+sep+yStr;
	else return null;
  } else if (pattern=="YM") {
	yStr = lPad(fValueSplits[0],"0",4);
	mStr = lPad(fValueSplits[1],"0",2);
	if (yStr.indexOf("00")==0) yStr="20"+yStr.substr(2,2);
	if (validateYM(yStr,mStr)) return yStr+sep+mStr;
	else return null;
  }  // d, m or y don't validate here
  if (err==1)
	  showDevErr("Invalid date pattern: " + pattern);
  return null;
}
function expectedSplitsCountWithSep(pattern) {
  return pattern.length;
}
// date validations
function validateYMD(yStr,mStr,dStr) {
  var dayArr = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
  var ymdStr = yStr+mStr+dStr;
  var filterYMD=/^[0-9]{4}[0-9]{2}[0-9]{2}$/;
  if (!filterYMD.test(ymdStr)) return false;
  var year = Number(yStr);
  var month = Number(mStr);
  var day = Number(dStr);
  
  if (year < 1900) return false;
  if (month==2) {
    if ( ( year%4==0 && year%100!=0 ) || ( year%400==0 ) ) {
     dayArr[1]=29; // leap year
    }
  } else if (month < 1 || month > 12) return false;
  if (day < 1 || day > (dayArr[month-1])) return false;
  return true;
}
function validateYM(yStr,mStr) {
  var ymdStr = yStr+mStr;
  var filterYMD=/^[0-9]{4}[0-9]{2}$/;
  if (!filterYMD.test(ymdStr)) return false;
  var year = Number(yStr);
  var month = Number(mStr);
  
  if (year < 1900) return false;
  if (month < 1 || month > 12) return false;
  return true;
}

/*
 TIME validation
 ---------------
 param: chars - a string with the time separator
 accepts: an input value including the separator in the suitable positions
          or with no separator at all, always having the right length;
		  accepted patterns:
		  	HMS, HM, H, M
*/
function validateTime(fValue, pattern, chars,err) {
  if (pattern=='') return fValue;	
  var sep = chars.charAt(0);
  var splitted = fValue.split(sep);
  if (splitted.length==1) {
    return validateTimeNoSep(fValue, pattern, sep , err);
  } else {
    return validateTimeWithSep(splitted, pattern, sep, err);
  }
}
// time validation without separator
function validateTimeNoSep(fValue, pattern, sep,err) {
  if (pattern.length > 1 && fValue.length != (pattern.length*2)) {
    return null;
  }
  var hStr; var mStr; var sStr;
  if (pattern=="HMS") {
    hStr = fValue.substr(0,2);
	mStr = fValue.substr(2,2);
	sStr = fValue.substr(4,2);
	if (valIntRange(hStr,0,23)&&valIntRange(mStr,0,59)&&valIntRange(sStr,0,59)) return hStr+sep+mStr+sep+sStr;
	else return null;
  } else if (pattern=="HM") {
    hStr = fValue.substr(0,2);
	mStr = fValue.substr(2,2);
	if (valIntRange(hStr,0,23)&&valIntRange(mStr,0,59)) return hStr+sep+mStr;
	else return null;
  } else if (pattern=="H") {
	if (valIntRange(fValue,0,23)) return fValue;
	else return null;
  } else if (pattern=="M" || pattern=="S") {
	if (valIntRange(fValue,0,59)) return fValue;
	else return null;
  }
  if (err==1)
	  showDevErr("Invalid time pattern: " + pattern);
  return null;
}
// time validation with separator
function validateTimeWithSep(fValueSplits, pattern, sep, err) {
  if (fValueSplits.length != pattern.length) {
    return null;
  }
  var hStr; var mStr; var sStr;
  if (pattern=="HMS") {
    hStr = lPad(fValueSplits[0],"0",2);
	mStr = lPad(fValueSplits[1],"0",2);
	sStr = lPad(fValueSplits[2],"0",2);
	if (valIntRange(hStr,0,23)&&valIntRange(mStr,0,59)&&valIntRange(sStr,0,59)) return hStr+sep+mStr+sep+sStr;
	else return null;
  } else if (pattern=="HM") {
    hStr = lPad(fValueSplits[0],"0",2);
	mStr = lPad(fValueSplits[1],"0",2);
	if (valIntRange(hStr,0,23)&&valIntRange(mStr,0,59)) return hStr+sep+mStr;
	else return null;
  }     // h, m or s don't validate here
  if (err==1)
     showDevErr("Invalid time pattern: " + pattern);
  return null;
}


/*
 NUMBER validation
 ---------------
 param: chars - a string with:
          at 0: the decimal separator
		  at 1: the thousand separator
 accepts: an input value including the separators in the suitable positions
          or with no separator at all, always having the right length;
		  accepted patterns: zeros with a dot where the decimal separator goes:
		  	00000    0000.0   000.0000
		  the amount of zeros before the dot is the max integer input digits
		  allowed
*/
function validateNumber(fValue, pattern, chars, err) {
  // determine decimals
  //alert(fValue+" "+ pattern+" "+ chars);
  var decSepPosition = pattern.lastIndexOf(".");
  var decimals = 0;
  var integers = pattern.length;
  if (decSepPosition >= 0) {
    decimals = pattern.length - decSepPosition - 1;
	integers = pattern.length - decimals - 1;
  }
  var withDec = (decimals > 0);
  // validate supplied separator chars
  if (chars.length < 2) {
  	if (err==1)
    	showDevErr("Chars for a number format must include decimal and thousand separators");
	return null;
  }
  var decSep = chars.charAt(0);
  var grpSep = chars.charAt(1);
  if (fValue.indexOf(grpSep)!=-1)
	  return null;
  if (withDec && !isValidNumberSepChar(decSep)) {
  	if (err==1)
	    showDevErr("Invalid decimal separator: " + decSep);
	return null;
  }
  var withGrpSep = isValidNumberSepChar(grpSep);
  if (withGrpSep && (decSep==grpSep)) {
  	if (err==1)
	    showDevErr("Decimal and grouping separator cannot be the same");
	return null;
  }
  // validate the input value
  return validateNumberWithSeps(fValue, decSep, grpSep, integers, decimals, err);
}

function roundNumber(rnum, rlength) { 
  var newnumber = Math.round(rnum*Math.pow(10,rlength))/Math.pow(10,rlength);
  return newnumber; 
}

function validateNumberWithSeps(zValue, decSep, grpSep, integers, decimals, err) {
  // take integer and decimal parts
  var fValue = ""+roundNumber(zValue, decimals);
  if (isNaN(fValue)) return zValue;// no entiendo siempre da true
  var decSepPosition = fValue.indexOf(decSep);
  var intPart;
  var decPart;
  var decPartExtra;
  var acum=0;

  
  if (decSepPosition >= 0) {
    if (fValue.lastIndexOf(decSep)!=decSepPosition) return null;
	intPart = trim(fValue.substring(0, decSepPosition));
	decPart = trim(fValue.substring(decSepPosition+1, fValue.length));
  } else {
    // cannonical input: i.e.: 2788434.98 ???
	var dotIndex = fValue.indexOf(".");
	if (decimals > 0 && dotIndex >= 0 && dotIndex==fValue.lastIndexOf(".")) {
	  intPart = fValue.substring(0, dotIndex);
	  decPart = fValue.substring(dotIndex+1, fValue.length);
	  
	} else {
	  intPart = fValue;
	  decPart = "";
	}
  }
  // validate integer part
  var isNegative = false;
  if (intPart.length < 1) {
    intPart = "0";
  } else if (intPart.indexOf('-')==0) {
    if (intPart.lastIndexOf('-')!=0) return null;
    isNegative = true;
	intPart = intPart.substr(1, intPart.length-1);
	if (intPart.length < 1) {
	  intPart = "0";
	}
  }
  var withGrpSep = isValidNumberSepChar(grpSep);
  if (withGrpSep) {
    while (intPart.indexOf(grpSep) >= 0) {
	  intPart = intPart.replace(grpSep, ""); // strip grouping separators from input
	}
  }
  if (intPart.length > integers) return null;
  var numFilter = /^[0-9]{1,}$/;
  if (!numFilter.test(intPart)) {
  	if (err==1)
  		return null;
  	else
  		intPart="0";
  }
  // validate decimal part
  if (decPart.length > decimals) {
  	if (err==1)
	    return null;
	else {
  		decPartExtra = decPart.substring(decimals);
  		decPart = decPart.substring(0,decimals);
		if (Number("0."+decPartExtra)>0.5) {
	  		decPart = lPad(""+(Number(decPart)+1),"0",decimals);
     		decPart = decPart.substring(0,decimals);
		}
    }
  }
  if (decimals > 0) {
	decPart = rPad(decPart,"0",decimals);
	if (!numFilter.test(decPart)) {
	  	if (err==1)
	  		return null;
	  	else
	  		intPart="0";
	}
  }
  // return !!
  var retVal = intPart;
  if (isNegative) {
    retVal = "-" + retVal;
  }
  if (decimals > 0) {
    retVal += decSep;
    retVal += decPart;
  }
  return retVal;
}
function isValidNumberSepChar(c) {
  return c=="," || c==".";
}


/*
 ALPHANUMERIC STRING validation
 ------------------------------
 param: chars - nothing:
 accepts: an input value having the right length;
		  accepted patterns:
		    JUPPERCASE
		   	JLOWERCASE
		   	NOT_TRIM
		   	JUPPER_NOSPACE
		   	JUPPER_ONLYLETTERORDIGIT
		   	(none) -> no validations performed
*/
function validateString(fValue, pattern, chars, err) {
  if (pattern==null || pattern.length < 1) {
    return fValue;
  } else if (pattern=='NOT_TRIM') {
    return fValue;
  } else if (pattern=='JUPPERCASE') {
    return fValue.toUpperCase();
  } else if (pattern=='JLOWERCASE') {
    return fValue.toLowerCase();
  } else if (pattern=='JUPPER_NOSPACE') {
    if (fValue.indexOf(' ') >= 0) {
      return null;
    }
    return fValue.toUpperCase();
  } else if (pattern=='JUPPER_ONLYLETTERORDIGIT') {
    var sValidChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    var len = fValue.length;
    var c;
    for (i=0;i<len;i++) {
      if (sValidChars.indexOf(fValue.charAt(i)) < 0) {
        return null;
      }
    }
    return fValue.toUpperCase();
  } else if (pattern=="JLONG") {
    var sValidChars = "0123456789";
    var len = fValue.length;
    var c;
    for (i=0;i<len;i++) {
      if (sValidChars.indexOf(fValue.charAt(i)) < 0) {
        return null;
      }
    }
    return fValue.toUpperCase();
  }  else if (pattern.indexOf('#')!=-1) {
	 var regexp = new RegExp(pattern.indexOf("#")==-1?pattern:pattern.substring(0,pattern.indexOf("#")));
	 if (regexp==null) {
	    if (err==1) alert("Invalid reg exp input attributes: " + pattern);
	    disablear();
	    return null;
	 }
	 if (! regexp.test(fValue))	return null;
	 return fValue;
  } else {
   	if (err==1) alert("Invalid string input attributes: " + pattern);
    disablear();
  }
  return null;
}

//
//  GENERAL UTILITIES
//
function trim(str) {
  var count = str.length;
  var newStr = "";
  var started = false;
  var isSpace = false;
  var tmpStr = "";
  var c;
  for (i=0; i<count; i++) {
    c = str.charAt(i);
	isSpace = c==' ';
	if (!isSpace) {
	  started = true;
	  if (tmpStr.length > 0) {
		newStr += tmpStr;
		newStr += c;
		tmpStr = "";
	  } else {
	    newStr += c;
	  }
	} else if (started) {
	  tmpStr += c;
	}
  }
  return newStr;
}
function lPad(str,fill,newLength) {
  var newStr = str;
  while (newStr.length < newLength) {
    newStr = fill + newStr;
  }
  return newStr;
}
function rPad(str,fill,newLength) {
  var newStr = str;
  while (newStr.length < newLength) {
    newStr =  newStr + fill;
  }
  return newStr;
}
function valIntRange(str, minInt, maxInt) {
  var numFilter = /^[0-9]{1,}$/;
  if (!numFilter.test(str)) return false;
  var intValue = Number(str);
  return intValue >= minInt && intValue <= maxInt;
}

function showDevErr(msg) {
  console.log("BUG: " + msg);
  disablear();
}
function showErr(field,msg) {
  console.log("MSG:"+msg);
  openModal("err",msgError,msg,false,true);
//  alert(msg);
  disablear();
  field.focus();
}

function showErrRefresh(type,title,msg) {
	  console.log("MSG:"+msg);
	  openModal(type,title==''?(type=='inf'?msgInfo:msgError):title,msg,false,true);
	  disablear();
}

function registerControlDependency(fName, child) {
  // esto es porque los combos dependientes se vuelven a ejecutar por AJAX
  // y se debe evitar las duplicidades
  var valIndex = 0;
  while (valIndex < dependenciesArr.length) {
    val = dependenciesArr[valIndex];
    if (val[0]==fName && val[1]==child)
    	return;
    valIndex++;
  }
  valIndex = 0

  dependenciesArr[dependenciesArr.length] = [fName, child];
  //alert("register control dependency: "+fName+" "+child+" "+dependenciesArr.length);
}
function cleanControlDependencies(fName) {

  //alert("cleaning dependencies of:"+fName);
  var valIndex = 0;
  var pendingControls = new Array();
  var pendingControlsCounter = 0;
  
  while (valIndex < dependenciesArr.length) {
    val = dependenciesArr[valIndex];
    if (val[0]==fName)  {
      cleanControl(val[1]);
      pendingControls[pendingControls.length] = val[1];
    }
	valIndex++;
	if (valIndex==dependenciesArr.length) {
		if (pendingControls.length>pendingControlsCounter) {
			valIndex = 0;
			fName = pendingControls[pendingControlsCounter];
			pendingControlsCounter++;
		}
	}
  }
}
function cleanControl(fName) {

  //alert("cleaning control "+fName);
  var docControl = document.getElementById(fName);
	
  if (docControl.tagName=="SELECT") {
  	cleanCombo(fName);
  }  	
  if (docControl.tagName=="INPUT" && docControl.className=="form_lov") {
	formLovClear(fName);  	
  }
  if (docControl.tagName=="INPUT") {
  	docControl.value = "";
  }  	
}
function cleanFormRegister() {
	valArr = new Array();
	valCount = 0;
	dependenciesArr = new Array();
	valArrNC = new Array();
	valCountNC = 0;
	valArrMeta = new Array();
	valCountMeta = 0;
	valArrTable = new Array();
	valCountTable = 0;
}
