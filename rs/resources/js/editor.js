/**
*   WYSIWYG_Editor
*
*   Class constructor. Configures and displays the editor object according to values passed
*   This is an implementation of OO-Javascript based on my previous editor.
*
*   @param  instance_name   string  the name of the variable you assigned to this instance ( example: myEdt = new WYSIWYG_Editor('myEdt'); )
*                                   also used as the basis for the name and id attributes for this editor instance in the HTML (hidden input and iframe)
*   @param  content         string  a string of the content to display in the editor.
*   @param  path            string  the URI path to this directory to use for editor components (pallete, iamges, etc.)
*   @param  fwidth          int     the width in pixels of the editor interface on the screen
*   @param  fheight         int     the height in pixels of the editor interface on the screen
*   @param  styleHref       string  the URI of the stylesheet to use for the editor's content window
*   @param  spellCheckPath  string  the URI of the spellerpages install
*   @param  margenizq  int  the URI of the spellerpages install
*   @param  ancho  int  the URI of the spellerpages install
**/


function WYSIWYG_Editor(zone, instance_name, content, readonly,formulario, isweb,mapa_plantilla, source, margenizq,margenimgsup,margenimgleft,margenimgsize,fondo, ancho, fwidth, fheight, styleHref, spellCheckPath, path){
    // for each of the passed parameters, we need to be sure they are defined, or we will use a default value

    // the name used to create the object - used to define the name of the field that contains the HTML on submission
    if(typeof(instance_name)=='undefined'){
        alert("ERROR: No instance name was passed for the editor.");
        return false;
    }else{
        this.instance_name=instance_name;
    } 

    // the initial HTML source content for the editor
    if(typeof(content)=='undefined'){
        this.content='';
    }else{
        this.content=content;
    }
 
    // define the path to use for the editor components like images
    if(typeof(path)=='undefined'){
        this.wysiwyg_path='html'; // default value
    }else{
        path.replace(/[\/\\]$/,''); // remove trailing slashes
        this.wysiwyg_path=path;
    }

    // define the pixel dimensions of the editor
    if(typeof(fwidth)=='number' && Math.round(fwidth) > 50){
        this.frame_width=Math.round(fwidth); // default value
    }else{
        this.frame_width=548; // default width
    }
    if(typeof(fheight)=='number' && Math.round(fheight) > 50){
        this.frame_height=Math.round(fheight);
    }else{
        this.frame_height=250; // default height
    }

    // define the stylesheet to use for the editor components like images
    if(typeof(styleHref)=='undefined'){
        this.stylesheet=''; // default value
    }else{
        this.stylesheet=styleHref;
    }

    if(typeof(spellCheckPath)=='undefined'){
        this.spell_path=''; // default value
    }else{
        // show spell check button requires Speller Pages (http://spellerpages.sourceforge.net/)
        this.spell_path=spellCheckPath;
    }

    // properties that depended on the validated values above
    this.wysiwyg_zone=zone;
    this.readonly=(readonly=='1');
    this.formulario=(formulario=='1');
    this.isweb=(isweb=='1');
    
    this.instance_name = this.instance_name;
    this.wysiwyg_content=this.instance_name+'_WYSIWYG_Editor';  // the editor IFRMAE element id
    this.wysiwyg_hidden=this.instance_name+'_content';          // the editor's hidden field to store the HTML in for the post
    this.wysiwyg_speller=this.instance_name+'_speller';         // the editor's hidden textarea to store the HTML in for the spellchecker
    this.frame_width=getWidth(this.wysiwyg_zone); // default width
    this.ta_rows=Math.round(this.frame_height/15);              // number of rows for textarea for unsupported browsers
    this.ta_cols=Math.round(this.frame_width/8);                // number of cols for textarea for unsupported browsers
    this.mapa=mapa_plantilla;
    this.source=source;
    this.mapasec=null;
    this.page_width = ancho;
    this.page_margin_left=margenizq;
    this.page_img_top=margenimgsup;
    this.page_img_left=margenimgleft;
    this.page_img_size=margenimgsize;
    this.fondo=fondo;
    this.frame_height=getHeight(this.wysiwyg_zone); // default height
    if (!this.readonly)this.frame_height-=74;
    // other property defaults
    this.viewMode=1;                                        // by default, set to design view
    this._X = this._Y = 0;                                  // these are used to determine mouse position when clicked

    // the folloing properties are safe to set through the object variable, for example:
    //  var editor = new WYSIWYG_Editor('editor');
    //  editor.allow_mode_toggle = false;
    // below are just the defaults that I use most of the time

    // insert table defaults
    this.table_border = 1;                                  // default border used when inserting tables
    this.table_cell_padding = 3;                            // default cellpadding used when inserting tables
    this.table_cell_spacing = 0;                            // default cellspacing used when inserting tables

    // tool bar display
    this.allow_mode_toggle = !this.readonly;                          // allow users to switch to source code mode
    this.font_format_toolbar1 = !this.readonly;                       // buttons for font family, size, and style
    this.font_format_toolbar2 = !this.readonly;                       // buttons for font color and background-color
    this.font_format_toolbar3 = !this.readonly;                       // buttons for bold, italic, underline
    this.font_format_toolbar4 = !this.readonly;                       // buttons for superscript, subscript
    this.font_format_toolbar5 = !this.readonly;                      
    this.alignment_toolbar1 = !this.readonly;                         // buttons for left, center, right, full justify
    this.alignment_toolbar2 = !this.readonly;                         // buttons for indent, outdent
    this.web_toolbar1 = !this.readonly;                               // buttons for add, remove hyperlinks
    this.web_toolbar2 = !this.readonly;                               // buttons for ordered, unordered lists
    this.web_toolbar3 = !this.readonly;                               // buttons for horizontal rule, insert table
    this.web_toolbar4 = !this.readonly;                              // buttons for insert image and save (submit form)
    this.misc_format_toolbar = !this.readonly;                       // buttons for remove format, copy, paste, redo, undo, etc.

    // this button is not implemented on the version for koivi.com
    // it is only currently available in WAFCMS (being developed as a
    // proprietary CMS currently). If enabled, an insert-image button
    // will appear in web_toolbar4 which calls the InsertImage method
    this.image_button = true;

    // this makes a save icon that submits the form in web_toolbar4
    this.save_button = false;

    // what kind of separator to use after each toolbar
    // "br" is a line break, "|" is an image separator, false is nothing
    this.font_format_toolbar1_after = false;
    this.font_format_toolbar2_after = '|';
    this.font_format_toolbar3_after = '|';
    this.font_format_toolbar4_after = 'br';
    this.alignment_toolbar1_after = '|';
    this.alignment_toolbar2_after = '|';
    this.web_toolbar1_after = '|';
    this.web_toolbar2_after = '|';
    this.web_toolbar3_after = '|';
    this.web_toolbar4_after = false;
    this.misc_format_toolbar_after = false;

    // these are used in my custom CMS
    this.web_toolbar4 = !this.readonly;                               // show insert image and save buttons
    this.imagePopUp = null;                                 // used in the insertImage and addImage methods
    this.site_path = this.wysiwyg_path;                     // default to what was passed, should be set in HTML
    this.page_title = 'index';                              // default to nothing, should be set in HTML
}

/**
*   WYSIWYG_Editor::prepareSubmit
*
*   Use this in the onSubmit event for the form that the editor is displayed inside.
*   Puts the HTML content into a hidden form field for the submission
**/
WYSIWYG_Editor.prototype.prepareSubmit = function (){
    if(this.viewMode == 2){
        // be sure this is in design view before submission
        this.toggleMode();
    }
    var htmlCode=document.getElementById(this.wysiwyg_content).contentWindow.document.body.innerHTML;
    document.getElementById(this.wysiwyg_hidden).value=htmlCode;
    return true;
}
WYSIWYG_Editor.prototype.getMapa = function (value){
	 for (i=0;i<this.mapa.length;i++) {
		if ((this.mapa[i])[0]==value) return this.mapa[i];
	 }
	return null;
}
WYSIWYG_Editor.prototype.getSource = function (){
	return this.source;
}
WYSIWYG_Editor.prototype.setMapa = function (value){
	this.mapa=value;
}
WYSIWYG_Editor.prototype.getMapaSec = function (value){
	 for (i=0;i<this.mapasec.length;i++) {
		if ((this.mapasec[i])[0]==value) return this.mapasec[i];
	 }
	return null;
}
WYSIWYG_Editor.prototype.setMapaSec = function (value){
	this.mapasec=value;
}
WYSIWYG_Editor.prototype.getid = function (){
	return this.instance_name;
}

/**
*   WYSIWYG_Editor::display
*
*   Display the editor interface for the user
**/


WYSIWYG_Editor.prototype.display = function (){
	    var is_firefox = navigator.userAgent.toLowerCase().indexOf('firefox') > -1;
 //   if(this.isSupported()){
        this._display_editor();
        this._load_content();
        var iframe_object = document.getElementById(this.wysiwyg_content);
        
        if(iframe_object.contentDocument){
        	thedoc = iframe_object.contentDocument;
		}else if(iframe_object.contentWindow){
			thedoc = iframe_object.contentWindow.document;
		}else{
			thedoc = iframe_object.document;
		}       
        
   
        if (!window.chome && this.readonly!=true&&!this.formulario) thedoc.designMode='On';
        var margin_right=this.frame_width-this.page_width-this.page_margin_left;

        var s = "";
       	s+= '<html id="'+this.instance_name+'"><head>';
            s+='<style type="text/css">';
		        s+='body {';
		      	if (this.fondo=='')s+='    border-left: '+this.page_margin_left+'px solid #BBBBBB;';
	       		if (this.fondo=='')s+='    border-right: '+margin_right+'px solid #BBBBBB;';  
		      	if (this.fondo!='')s+='    padding-left: '+this.page_margin_left+'px;';
	       		if (this.fondo!='')s+='     padding-right: '+margin_right+'px;';  
		  //      if (this.fondo!='')s+='    padding-left: '+this.page_margin_left+'px ;';
		       	s+='    width: '+this.page_width+'px;'; 
//		    	if (this.fondo!='') s+='    background-image: url("/regtur/pss_data/Fondos/'+this.fondo+'"); ';
		    	if (this.fondo!='') s+='    background-image: url("'+this.fondo+'"); ';
		    	if (this.fondo!='') s+='    background-size: '+(this.page_img_size)+'px;';
		    	if (this.fondo!='') s+='	background-position: left '+(-1*this.page_img_left)+'px top '+(-1*this.page_img_top)+'px;';
		    	if (this.fondo!='') s+='	background-repeat: no-repeat;';
		       	s+='}';
				s += ".r90 {";
				s += "  display:inline-block;";
				s += "  filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=3);";
				s += "  -webkit-transform: rotate(270deg);";
				s += "  -ms-transform: rotate(270deg);";
				s += "  transform: rotate(270deg);";
				s += "}";
				if (!this.readonly) {
					s += ".editable {";
					//s += "    display:inline-block;";
					s += "    background-color: rgba(244,242,217,.6);";
					s += "    margin: -1px;";
					s += "    border: 1px inset black;";
					s += "}";
				}
				s += "@page land { ";
				s += "  size: A4 landscape; ";
				s += "}";
				s += ".landscape::before { ";
		       	s += "    content: \"\";";
		       	s += "    display: block;";
		       	s += "    border-color: #000000;";
		       	s += "    border-width: 1px;";
		       	s += "    border-style: dashed;";
		       	s += "    width: 100%;";
		       	s += "    margin: 0px;";
		       	s += "} ";
				s += ".landscape::after { ";
		       	s += "    content: \"\";";
		       	s += "    display: block;";
		       	s += "    border-color: #000000;";
		       	s += "    border-width: 1px;";
		       	s += "    border-style: dashed;";
		       	s += "    width: 100%;";
		       	s += "    margin: 0px;";
		       	s += "} ";
				s += ".landscape { ";
				s += "   page:land; ";
				s += "   page-break-before: always;"; 
		      // 	s+='    border-right: 10px solid #BBBBBB;';  
		       	s+='    background-color: #FFFFFF;';  
		       	s += "   width: 257mm; ";
		       	s += "} ";
				s += "table{ ";
				s += "-fs-table-paginate: paginate;";
				s += "border-spacing: 0;";
				s += "-moz-border-radius: 5px;"; 
				s += "border-radius: 5px;"; 

				s += "}";
		       	s+='p {';
			       	s+='margin-top: 0px;';
			       	s+='margin-bottom: 0px;';
		       	s+='}';
	            // must be done after the document has been opened
	        s+='</style>';
	        if (window.chrome && !this.readonly && !this.formulario) {
	        	s+='<script>';
	        	s+='function load(){';
	        	s+='window.document.designMode = \'On\'; ';
	            s+="}";
	            s+="</script>";
	        }else if (window.chrome && !this.readonly && this.formulario) {
	        	s+='<script>';
	            s+='function setearComoEditable(objName){';
            	s+='	var nodes = objName.childNodes;';
           		s+='	for(var i=0; i<nodes.length; i++) {';
       			s+=' 	    if (nodes[i].className == "editable") {';
   				s+=' 	         nodes[i].contentEditable= true;';
				s+='  	    } else {';
				s+='  	    	setearComoEditable(nodes[i]);';
				s+='   	    }';
				s+='  	}';
	            s+='  }';
	        	s+='function load(){';
	        	s+='setearComoEditable(window.document)';
	            s+="}";
	            s+="</script>";
	        }
	     s+='</head>';
        if (this.content.indexOf('<BODY')==-1) {
        	s+='<body ';
        	if (window.chrome && this.readonly==false && this.formulario==false) s+='contentEditable=\'true\' onload=\'load()\' '; 
        	else if (window.chrome && this.readonly==false && this.formulario==true) s+=' onload=\'load()\' '; 
        	s+='style="margin-left: 0px;margin-right: 0px; margin-top: 0px; margin-bottom: 0px;width:100%;">';
        }
        s+=this.content;
        if (this.content.indexOf('<BODY')==-1)s+='</body>';
        s+='</html>';

        // MSIE has caching problems...
        // http://technet2.microsoft.com/WindowsServer/en/Library/8e06b837-0027-4f47-95d6-0a60579904bc1033.mspx
 	    if(iframe_object.contentDocument){
	    	thedoc = iframe_object.contentDocument;
		}else if(iframe_object.contentWindow){
			thedoc = iframe_object.contentWindow.document;
		}else{
			thedoc = iframe_object.document;
		} 
	    
 	    // bug de chrome, se rompe el iframe y lo manda como una nueva pagina que recarga todo el contenido, y desplaza la ventana hacia arriba
 	    if (!window.chrome) {
		    thedoc.open();
		    thedoc.write(s);
		    thedoc.close();
		    if (!this.readonly && this.formulario) setearComoEditable(thedoc);
 	    } else {
 	        iframe_object.srcdoc=s;
 	    }
	    if (this.readonly!=true && this.formulario!=true) {
	    	doEjec("javascript:document.getElementById('"+this.wysiwyg_content+"').contentWindow.document.oncontextmenu=function(event){return clicker(event,'"+this.instance_name+"','"+this.wysiwyg_content+"');};");
	    }



}
function setearComoEditable(objName){
	var nodes = objName.childNodes;
	for(var i=0; i<nodes.length; i++) {
	    if (nodes[i].className == "editable") {
	         nodes[i].contentEditable= true;
	    } else {
	    	setearComoEditable(nodes[i]);
	    }
	    	
	}
}
function doEjec(zScript) {
	if (window.ActiveXObject)			{
		window.execScript(zScript);
	}
	else /* (window.XMLHttpRequest) */		{
		window.eval(zScript);
	}
}
/**
*   WYSIWYG_Editor::_display_textarea
*
*   Used to display a substitute for the wysiwyg editor HTML interface to non-supported browsers
**/
WYSIWYG_Editor.prototype._display_textarea = function (){
	var s = "";
    s = s + ('<p style="background-color: yellow; color: red; padding: 3px;"><b>CUIDADO:</b> Su browser no soporta WYSIWYG_Editor class.</p>');
    s = s + ('<textarea name="'+this.wysiwyg_hidden+'" id="'+this.wysiwyg_hidden+'" rows="'+this.ta_rows+'" cols="'+this.ta_cols+'"></textarea><br>');
    document.getElementById(this.wysiwyg_zone).innerHTML = s;
}

/**
*   WYSIWYG_Editor::_display_editor
*
*   Used to display the actual wysiwyg editor HTML interface to supported browsers
**/
WYSIWYG_Editor.prototype._display_editor = function (){
    var is_firefox = navigator.userAgent.toLowerCase().indexOf('firefox') > -1;
	var s = "";
    s = s + ('   <textarea name="'+this.wysiwyg_hidden+'" id="'+this.wysiwyg_hidden+'" style="visibility:hidden;display:none;"></textarea>');
    s = s + ('   <table width="100%" cellpadding="5" cellspacing="0" '+(this.isweb?'' :'border="2"')+' id="'+this.instance_name+'_table" bgcolor="#dfdfdf">');
    s = s + ('    <tr>');
    s = s + ('     <td>');
    s = s + ('      <table width="100%" border="0" cellspacing="0" cellpadding="0">');
    s = s + ('       <tr>');
    s = s + ('        <td valign="top" colspan="2">');
    s = s + ('         <div id="'+this.instance_name+'_toolbars">');
    if(this.font_format_toolbar1){
        s = s + ('          <select onChange="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'fontname\',this.options[this.selectedIndex].value)">');
        s = s + ('           <option value="">- Font -</option>');
        s = s + ('           <option value="Arial">Arial</option>');
        s = s + ('           <option value="Helvetica">Helvetica</option>');
        s = s + ('           <option value="Courier">Courier</option>');
        s = s + ('           <option value="Times New Roman">Times New Roman</option>');
        s = s + ('           <option value="cursive">Cursiva</option>');
        s = s + ('           <option value="fantasy">Fantasia</option>');
        s = s + ('           <option value="sans-serif">Sans Serif</option>');
        s = s + ('           <option value="serif">Serif</option>');
        s = s + ('           <option value="monospace">Typewriter</option>');
        s = s + ('           <option value="barcode39ext">C�digo de barra</option>');
        s = s + ('          </select>');
        s = s + ('          <select onChange="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'fontSize\',this.options[this.selectedIndex].value)">');
        s = s + ('           <option value="">- Size -</option>');
        s = s + ('           <option value="-2">Muy Peque�o</option>');
        s = s + ('           <option value="-1">Peque�o</option>');
        s = s + ('           <option value="+0">Normal</option>');
        s = s + ('           <option value="+1">Grande</option>');
        s = s + ('           <option value="+2">Muy Grande</option>');
        s = s + ('           <option value="+3">Extra Grande</option>');
        s = s + ('           <option value="+4">Gigante</option>');
        s = s + ('          </select>');
        s = s + ('          <select onChange="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'formatblock\',this.options[this.selectedIndex].value)">');
        s = s + ('           <option value="<p>">Normal</option>');
        s = s + ('           <option value="<h1>">Cabecera 1</option>');
        s = s + ('           <option value="<h2>">Cabecera 2</option>');
        s = s + ('           <option value="<h3>">Cabecera 3</option>');
        s = s + ('           <option value="<h4>">Cabecera 4</option>');
        s = s + ('           <option value="<h5>">Cabecera 5</option>');
        s = s + ('           <option value="<h6>">Cabecera 6</option>');
        s = s + ('           <option value="<address>">Address</option>');
        s = s + ('          </select>');
        if(this.font_format_toolbar1_after=='br'){
            s = s + ('         <br>');
        }else if(this.font_format_toolbar1_after=='|'){
            s = s + ('         <img alt="|" src="'+this.wysiwyg_path+'/images/separator.png">');
        }
    }
    if(this.font_format_toolbar2){
        s = s + ('           <img alt="Font Color" title="Font Color" class="butClass" src="'+this.wysiwyg_path+'/images/forecol.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'forecolor\',\'\',event)" id="'+this.instance_name+'_forecolor">');
        s = s + ('           <img alt="Background Color" title="Background Color" class="butClass" src="'+this.wysiwyg_path+'/images/bgcol.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'hilitecolor\',\'\',event)" id="'+this.instance_name+'_hilitecolor">');
        if(this.font_format_toolbar2_after=='br'){
            s = s + ('         <br>');
        }else if(this.font_format_toolbar2_after=='|'){
            s = s + ('         <img alt="|" src="'+this.wysiwyg_path+'/images/separator.png">');
        }
    }
    if(this.font_format_toolbar3){
        s = s + ('          <img alt="Bold" title="Bold" class="butClass" src="'+this.wysiwyg_path+'/images/bold.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'bold\',\'\')">');
        s = s + ('          <img alt="Italic" title="Italic" class="butClass" src="'+this.wysiwyg_path+'/images/italic.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'italic\',\'\')">');
        s = s + ('          <img alt="Underline" title="Underline" class="butClass" src="'+this.wysiwyg_path+'/images/underline.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'underline\',\'\')">');
        if(this.font_format_toolbar3_after=='br'){
            s = s + ('         <br>');
        }else if(this.font_format_toolbar3_after=='|'){
            s = s + ('         <img alt="|" src="'+this.wysiwyg_path+'/images/separator.png">');
        }
    }
    if(this.font_format_toolbar4){
        s = s + ('          <img alt="Superscript" title="Superscript" class="butClass" src="'+this.wysiwyg_path+'/images/sup.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'superscript\',\'\')">');
        s = s + ('          <img alt="Subscript" title="Subscript" class="butClass" src="'+this.wysiwyg_path+'/images/sub.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'subscript\',\'\')">');
        if(this.font_format_toolbar4_after=='br'){
            s = s + ('         <br>');
        }else if(this.font_format_toolbar4_after=='|'){
            s = s + ('         <img alt="|" src="'+this.wysiwyg_path+'/images/separator.png">');
        }
    }
    if(this.alignment_toolbar1){
        s = s + ('          <img alt="Left" title="Left" class="butClass" src="'+this.wysiwyg_path+'/images/left.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'justifyleft\',\'\')">');
        s = s + ('          <img alt="Center" title="Center" class="butClass" src="'+this.wysiwyg_path+'/images/center.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'justifycenter\',\'\')">');
        s = s + ('          <img alt="Right" title="Right" class="butClass" src="'+this.wysiwyg_path+'/images/right.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'justifyright\',\'\')">');
        s = s + ('          <img alt="Full" title="Full" class="butClass" src="'+this.wysiwyg_path+'/images/full.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'justifyfull\',\'\')">');
        if(this.alignment_toolbar1_after=='br'){
            s = s + ('         <br>');
        }else if(this.alignment_toolbar1_after=='|'){
            s = s + ('         <img alt="|" src="'+this.wysiwyg_path+'/images/separator.png">');
        }
    }
    if(this.alignment_toolbar2){
        s = s + ('          <img alt="Indent" title="Indent" src="'+this.wysiwyg_path+'/images/indent.png" class=butClass onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'indent\',\'\')">');
        s = s + ('          <img alt="Outdent" title="Outdent" src="'+this.wysiwyg_path+'/images/outdent.png" class=butClass onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'outdent\',\'\')">');
        if(this.alignment_toolbar2_after=='br'){
            s = s + ('         <br>');
        }else if(this.alignment_toolbar2_after=='|'){
            s = s + ('         <img alt="|" src="'+this.wysiwyg_path+'/images/separator.png">');
        }
    }
    if(this.web_toolbar1){
        s = s + ('          <img alt="Hyperlink" title="Hyperlink" class="butClass" src="'+this.wysiwyg_path+'/images/link.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'createlink\',\'\')">');
        s = s + ('          <img alt="Remove Link" title="Remove Link" class="butClass" src="'+this.wysiwyg_path+'/images/unlink.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'unlink\',\'\')">');
        if(this.web_toolbar1_after=='br'){
            s = s + ('         <br>');
        }else if(this.web_toolbar1_after=='|'){
            s = s + ('         <img alt="|" src="'+this.wysiwyg_path+'/images/separator.png">');
        }
    }
    if(this.web_toolbar2){
        s = s + ('          <img alt="Ordered List" title="Ordered List" class="butClass" src="'+this.wysiwyg_path+'/images/ordlist.png"  onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'insertorderedlist\',\'\')">');
        s = s + ('          <img alt="Bulleted List" title="Bulleted List" class="butClass" src="'+this.wysiwyg_path+'/images/bullist.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'insertunorderedlist\',\'\')">');
        if(this.web_toolbar2_after=='br'){
            s = s + ('         <br>');
        }else if(this.web_toolbar2_after=='|'){
            s = s + ('         <img alt="|" src="'+this.wysiwyg_path+'/images/separator.png">');
        }
    }
    if(this.web_toolbar3){
        s = s + ('          <img alt="Horizontal Rule" title="Horizontal Rule" class="butClass" src="'+this.wysiwyg_path+'/images/rule.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'inserthorizontalrule\',\'\')">');
        s = s + ('          <img alt="Insert Table" title="Insert Table" class="butClass" src="'+this.wysiwyg_path+'/images/table.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').insertTable()">');
        if(this.web_toolbar3_after=='br'){
            s = s + ('         <br>');
        }else if(this.web_toolbar3_after=='|'){
            s = s + ('         <img alt="|" src="'+this.wysiwyg_path+'/images/separator.png">');
        }
    }
    if(this.web_toolbar4){
        if(this.image_button)
            s = s + ('          <img alt="Insert Image" title="Insert Image" class="butClass" src="'+this.wysiwyg_path+'/images/image.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').insertImage()">');
        s = s + ('          <img alt="Insert page" title="Insert page" class="butClass" src="'+this.wysiwyg_path+'/images/newpage.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').insertPage()">');
        s = s + ('          <img alt="Insert page LANDSCAPE" title="Insert page LANDSCAPE" class="butClass" src="'+this.wysiwyg_path+'/images/newpageland.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').insertPageLand()">');
//        if(this.spell_path.length > 0)
//            s = s + ('          <img alt="Check Spelling" title="Check Spelling" class="butClass" src="'+this.wysiwyg_path+'/images/spelling.png" onclick="getRegisteredRTE(\''+this.instance_name+'\').checkSpelling()">');
//        if(this.save_button)
//            s = s + ('          <input type="image" alt="Save" title="Save" class="butClass" src="'+this.wysiwyg_path+'/images/save.png" name="'+this.instance_name+'_save">');
        if(this.web_toolbar4_after=='br'){
            s = s + ('         <br>');
        }else if(this.web_toolbar4_after=='|'){
            s = s + ('         <img alt="|" src="'+this.wysiwyg_path+'/images/separator.png">');
        }
    }
   	
    if(this.misc_format_toolbar){
//        s = s + ('         <br>');
          s = s + ('          <img alt="Remove Formatting" title="Remove Formatting" class="butClass" src="'+this.wysiwyg_path+'/images/unformat.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'removeformat\',\'\')">');
//        s = s + ('         <img alt="|" src="'+this.wysiwyg_path+'/images/separator.png">');
//        s = s + ('          <img alt="Undo" title="Undo" class="butClass" src="'+this.wysiwyg_path+'/images/undo.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'undo\',\'\')">');
//        s = s + ('          <img alt="Redo" title="Redo" class="butClass" src="'+this.wysiwyg_path+'/images/redo.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'redo\',\'\')">');
//        s = s + ('         <img alt="|" src="'+this.wysiwyg_path+'/images/separator.png">');
//        s = s + ('          <img alt="Cut" title="Cut" class="butClass" src="'+this.wysiwyg_path+'/images/cut.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'cut\',\'\')">');
//        s = s + ('          <img alt="Copy" title="Copy" class="butClass" src="'+this.wysiwyg_path+'/images/copy.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'copy\',\'\')">');
//        s = s + ('          <img alt="Paste" title="Paste" class="butClass" src="'+this.wysiwyg_path+'/images/paste.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').doTextFormat(\'paste\',\'\')">');
//        if(this.misc_format_toolbar=='br'){
//            s = s + ('         <br>');
//        }else if(this.misc_format_toolbar_after=='|'){
//            s = s + ('         <img alt="|" src="'+this.wysiwyg_path+'/images/separator.png">');
//        }
    	if (this.formulario) 
            s = s + ('<font size="0">(FORMULARIO:click para editar campo)</font>');
    	else
    		s = s + ('          <img alt="Insert Field" title="Insert Field" class="butClass" src="'+this.wysiwyg_path+'/images/field.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').insertField()">');
    }
  
    s = s + ('         </div>');
    s = s + ('        </td>');
    s = s + ('       </tr>');
    s = s + ('      </table>');
//    s = s + ('      <iframe id="'+this.wysiwyg_content+'" style="margin-left: 3px; background-color: white; color: black; width:'+this.frame_width+'px; height:'+this.frame_height+'px;" src="' + window.location.protocol + '//' + location.host + this.wysiwyg_path+'/blank.html"></iframe>');

    if (!is_firefox)
    	s = s + ('      <iframe onMouseMove="getMouseXY(event);" onload="javascript:pasteImage(\''+this.instance_name+'\',\''+this.wysiwyg_content+'\');document.getElementById(\''+this.wysiwyg_content+'\').contentWindow.document.oncontextmenu=function(event){return clicker(event,\''+this.instance_name+'\',\''+this.wysiwyg_content+'\');}" ');
    else
    	s = s + ('      <iframe onMouseMove="getMouseXY(event);" onload="javascript:pasteImage(\''+this.instance_name+'\',\''+this.wysiwyg_content+'\');document.getElementById(\''+this.wysiwyg_content+'\').contentWindow.document.oncontextmenu=function(event){return clicker(event,\''+this.instance_name+'\',\''+this.wysiwyg_content+'\');}" ');
    
//    s = s + (' src= "_blank" id="'+this.wysiwyg_content+'" style="border-width :0px; '+(this.readonly||this.isweb?'':'background-color: white;')+'; color: black; width:'+this.frame_width+'px; height:'+this.frame_height+'px;"></iframe>');
    s = s + (' src= "" id="'+this.wysiwyg_content+'" style="border-width :0px; background-color: white; color: black; width:100%; height:'+this.frame_height+'px;"></iframe>');
    s = s + ('      <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#dfdfdf">');
    s = s + ('       <tr>');
    s = s + ('        <td>');
    s = s + ('        </td>');
    s = s + ('        <td align="right">');
    if(this.allow_mode_toggle){
        s = s + ('         <img alt="Toggle Mode" title="Toggle Mode" class="butClass" src="'+this.wysiwyg_path+'/images/mode.png" onClick="getRegisteredRTE(\''+this.instance_name+'\').toggleMode()">');
    }
    s = s + ('        </td>');
    s = s + ('       </tr>');
    s = s + ('      </table>');
    s = s + ('     </td>');
    s = s + ('    </tr>');
    s = s + ('   </table>');
    s = s + ('   <br>');
    document.getElementById(this.wysiwyg_zone).innerHTML = s;
    
 
}

/**
*   WYSIWYG_Editor::doTextFormat
*
*   Apply a text formatting command to the selected text in the editor (or starting at the current cursor position)
*
*   @param  command string  Which of the editor/browser text formatting commands to apply
**/
WYSIWYG_Editor.prototype.doTextFormat = function (command, optn, evnt){
    document.getElementById(this.wysiwyg_content).contentWindow.focus();
    if((command=='forecolor') || (command=='hilitecolor')){
        this.getPallete(command, optn, evnt);
    }else {if(command=='createlink'){
        var szURL=prompt('Ingrese URL:', '');
        if(document.getElementById(this.wysiwyg_content).contentWindow.document.queryCommandEnabled(command)){
            document.getElementById(this.wysiwyg_content).contentWindow.document.execCommand('CreateLink',false,szURL);
        }else return false;
    }else{
        if (!document.getElementById(this.wysiwyg_content).contentWindow.document.queryCommandSupported(command)){
            var cursor=document.getElementById(this.wysiwyg_content).contentWindow.document.selection.createRange();
        	cursor.pasteHTML(optn);
        } else {
        	if(document.getElementById(this.wysiwyg_content).contentWindow.document.queryCommandEnabled(command)){
              document.getElementById(this.wysiwyg_content).contentWindow.document.execCommand(command, false, optn);
            } 
            else return false;
        }
    }}
    document.getElementById(this.wysiwyg_content).contentWindow.focus();
}

/**
*   WYSIWYG_Editor::_load_content
*
*   Puts the passed content into the editor or textarea (Use this one *after* displaying the editor.)
*
*   @param  content string  The string containing the properly-formatted HTML source to use
*/
WYSIWYG_Editor.prototype._load_content = function (){
    document.getElementById(this.wysiwyg_hidden).value=this.content;
}

/**
*   WYSIWYG_Editor::toggleMode
*
*   Toggles between design view and source view in the IFRAME element
**/
WYSIWYG_Editor.prototype.toggleMode = function (){
    // change the display styles
    if(this.viewMode == 2){
        document.getElementById(this.wysiwyg_content).contentWindow.document.body.style.fontFamily = '';
        document.getElementById(this.wysiwyg_content).contentWindow.document.body.style.fontSize = '';
        document.getElementById(this.wysiwyg_content).contentWindow.document.body.style.color = '';
        document.getElementById(this.wysiwyg_content).contentWindow.document.body.style.fontWeight = '';
        document.getElementById(this.wysiwyg_content).contentWindow.document.body.style.backgroundColor = '';
        document.getElementById(this.instance_name+'_toolbars').style.visibility='visible';
    }else{
        document.getElementById(this.wysiwyg_content).contentWindow.document.body.style.fontFamily = 'monospace';
        document.getElementById(this.wysiwyg_content).contentWindow.document.body.style.fontSize = '10pt';
        document.getElementById(this.wysiwyg_content).contentWindow.document.body.style.color = '#000';
        document.getElementById(this.wysiwyg_content).contentWindow.document.body.style.backgroundColor = '#fff';
        document.getElementById(this.wysiwyg_content).contentWindow.document.body.style.fontWeight = 'normal';
        document.getElementById(this.instance_name+'_toolbars').style.visibility='hidden';
    }

    // do the content swapping
    if(this.isMSIE()){
        this._toggle_mode_ie();
    }else{
        this._toggle_mode_gecko();
    }
}

/**
*   WYSIWYG_Editor::_toggle_mode_ie
*
*   Toggles between design view and source view in the IFRAME element for MSIE
**/
WYSIWYG_Editor.prototype._toggle_mode_ie = function (){
    if(this.viewMode == 2){
        document.getElementById(this.wysiwyg_content).contentWindow.document.body.innerHTML = document.getElementById(this.wysiwyg_content).contentWindow.document.body.innerText;
        document.getElementById(this.wysiwyg_content).contentWindow.focus();
        this.viewMode = 1; // WYSIWYG
    }else{
        document.getElementById(this.wysiwyg_content).contentWindow.document.body.innerText = document.getElementById(this.wysiwyg_content).contentWindow.document.body.innerHTML;
        document.getElementById(this.wysiwyg_content).contentWindow.focus();
        this.viewMode = 2; // Code
    }
}

/**
*   WYSIWYG_Editor::_toggle_mode_gecko
*
*   Toggles between design view and source view in the IFRAME element for Gecko browsers
**/
WYSIWYG_Editor.prototype._toggle_mode_gecko = function (){
    if(this.viewMode == 2){
        var html = document.getElementById(this.wysiwyg_content).contentWindow.document.body.ownerDocument.createRange();
        html.selectNodeContents(document.getElementById(this.wysiwyg_content).contentWindow.document.body);
        document.getElementById(this.wysiwyg_content).contentWindow.document.body.innerHTML = html.toString();
        document.getElementById(this.wysiwyg_content).contentWindow.focus();
        this.viewMode = 1; // WYSIWYG
    }else{
        var html = document.createTextNode(document.getElementById(this.wysiwyg_content).contentWindow.document.body.innerHTML);
        document.getElementById(this.wysiwyg_content).contentWindow.document.body.innerHTML = '';
        document.getElementById(this.wysiwyg_content).contentWindow.document.body.appendChild(html);
        document.getElementById(this.wysiwyg_content).contentWindow.focus();
        this.viewMode = 2; // Code
    }
}

/**
*   WYSIWYG_Editor::_get_offset_left
*
*   Used to define position of pop-up pallete window in Gecko browsers
**/
WYSIWYG_Editor.prototype._get_offset_left = function (elm){
    var mOffsetLeft=elm.offsetLeft;
    var mOffsetParent=elm.offsetParent;
    while(mOffsetParent){
        mOffsetLeft += mOffsetParent.offsetLeft;
        mOffsetParent=mOffsetParent.offsetParent;
    }
    return mOffsetLeft;
}

/**
*   WYSIWYG_Editor::_get_offset_top
*
*   Used to define position of pop-up pallete window in Gecko browsers
**/
WYSIWYG_Editor.prototype._get_offset_top = function (elm){
    var mOffsetTop=elm.offsetTop;
    var mOffsetParent=elm.offsetParent;
    while(mOffsetParent){
        mOffsetTop += mOffsetParent.offsetTop;
        mOffsetParent=mOffsetParent.offsetParent;
    }
    return mOffsetTop;
}

/**
*   WYSIWYG_Editor::insertTable
*
*   Used for inserting a table into the IFRAME content window
**/
WYSIWYG_Editor.prototype.insertTable = function (){
    colstext = prompt('Numero de columnas por fila.');
    rowstext = prompt('Numero de filas.');
    bordetext = prompt('Ancho borde');
    anchotext = prompt('Ancho (si es porcentaje agregue % al final 100%)');
    rows = parseInt(rowstext,'0');
    cols = parseInt(colstext,'0');
    borde = parseInt(bordetext,'0');
    if(this.isMSIE()){
        return this._insert_table_ie(cols,0,rows,borde,anchotext,"","","");
    }else{
        return this._insert_table_gecko(cols,0,rows,borde,anchotext,"","","");
    }
}
WYSIWYG_Editor.prototype.insertHeaderDetail = function (data,html,color){
    colstext =  prompt('Numero de columnas por fila. (0= modo parrafo)');
    cols = parseInt(colstext,'0');
    if (cols==0) {
    	 var htmlText = '';
    	 htmlText += '<DIV ID="zone_'+data+'" STYLE="background-color:#'+color+'">';
    	 htmlText += '<!--DATA:'+data+'-->';
    	 htmlText += '<DIV><br/></DIV>';
         htmlText += '<!--DATAEND:'+data+'-->';
         htmlText += '</DIV><br/>';
         this.doTextFormat('insertHTML',htmlText,null);
    	 return;
    }
    row1stext = prompt('Numero de filas encabezado.');
    row2stext = prompt('Numero de filas detalle.');
    bordetext = prompt('Ancho borde');
    anchotext = prompt('Ancho (si es porcentaje agregue % al final 100%)');
    row1s = parseInt(row1stext,'0');
    row2s = parseInt(row2stext,'0');
    borde = parseInt(bordetext,'0');
    if(this.isMSIE()){
        return this._insert_table_ie(cols,row1s,row2s,borde,anchotext,"DATA:"+data,"DATAEND:"+data,html);
    }else{
        return this._insert_table_gecko(cols,row1s,row2s,borde,anchotext,"DATA:"+data,"DATAEND:"+data,html);
    }
}

/**
*   WYSIWYG_Editor::_insert_table_ie
*
*   This is the browser engine-specific code for inserting a table for MSIE browsers.
*
*   @param  cols    The number of columns to create
*   @param  rows    The number of rows to create
**/
WYSIWYG_Editor.prototype._insert_table_ie = function (cols, rowh, rows,borde,ancho,preFila,posFila,incolumna){
    document.getElementById(this.wysiwyg_content).contentWindow.focus();
    color = "#000000";
    if (borde==0) {
    	color = "GRAY";
    	borde = 1;
    }
    rows+=rowh;
    var c=0;
    //get current selected range
    var cursor=document.getElementById(this.wysiwyg_content).contentWindow.document.selection.createRange();
    if((rows > 0) && (cols > 0)){
        var tableHTML = '<table '+(ancho==""?"":'width="'+ancho+'"')+' border="'+borde+'" bordercolor="'+color+'" cellpadding="'+this.table_cell_padding+'" cellspacing="'+this.table_cell_spacing+'">';
        if (rowh>0)
        	tableHTML=tableHTML+'<thead>';
        else
        	tableHTML=tableHTML+'<tbody>';
        while(rows>0){
            rows--;
            var rowCols = cols;
            if (c<rowh) {
                tableHTML = tableHTML + '<tr>';
            } else {
                tableHTML = tableHTML + (c>=rowh && preFila!=""?(rowh>0?"</thead><tbody>":"<tbody>")+"<!--"+preFila+"-->":"") +'<tr>';
            }
            while(parseInt(rowCols)>0){
                rowCols--;
                if (c>=rowh)
                	tableHTML=tableHTML+'<td>'+(c<rowh || incolumna==""?'&nbsp;':incolumna)+'</td>';
                else
                	tableHTML=tableHTML+'<th>&nbsp;</th>';
           }
            if (c<rowh) {
                tableHTML = tableHTML + '</tr>';
            } else {
                tableHTML = tableHTML +'</tr>'+ (c>=rowh && preFila!=""?"<!--"+posFila+"-->":"") ;
            }
            c++;
        }
        tableHTML = tableHTML + '</tbody></table>';
        cursor.pasteHTML(tableHTML);
        document.getElementById(this.wysiwyg_content).contentWindow.focus();
    }
    return true;
}

/**
*   WYSIWYG_Editor::_insert_table_gecko
*
*   This is the browser engine-specific code for inserting a table for Gecko browsers.
*
*   @param  cols    The number of columns to create
*   @param  rows    The number of rows to create
**/
WYSIWYG_Editor.prototype._insert_table_gecko = function (cols, rowh, rows, borde, ancho, preFila, posFila,incolumna){
    color = "#000000";
    if (borde==0) {
    	color = "GRAY";
    	borde = 1;
    }
    var c=0;
    rows+=rowh;
    contentWin=document.getElementById(this.wysiwyg_content).contentWindow;
    if((rows > 0) && (cols > 0)){
        table=contentWin.document.createElement('table');
        table.setAttribute('border', borde);
        table.setAttribute('bordercolor', color);
        if (ancho!="") table.setAttribute('width', ancho);
        table.setAttribute('cellpadding', this.table_cell_padding);
        table.setAttribute('cellspacing', this.table_cell_spacing);
     
        thead=contentWin.document.createElement('thead');
        tbody=contentWin.document.createElement('tbody');
        for(i=0;i<rows;i++){
        	var tr;
        	if (c<rowh) {
                tr=contentWin.document.createElement('tr');
        	} else {
        		var com = contentWin.document.createComment(preFila);
        		tbody.appendChild(com);
                tr=contentWin.document.createElement('tr');
        	}
            for(j=0;j<cols;j++){
                td=contentWin.document.createElement(c<rowh?'th':'td');
                if (c<rowh || incolumna=="") {
                	br=contentWin.document.createElement('br');
                    td.appendChild(br);
                }
                else 
                	td.innerHTML=incolumna;
                tr.appendChild(td);
            }
            if (c<rowh)
                thead.appendChild(tr);
            else
            	tbody.appendChild(tr);
        	if (c>=rowh) {
        		var com = contentWin.document.createComment(posFila);
        		tbody.appendChild(com);
        	}
            c++;
        }
        if (rowh>0)
            table.appendChild(thead);
        table.appendChild(tbody);
        this._insert_element_gecko(contentWin, table);
    }
    return true;
}

/**
*   WYSIWYG_Editor::_insert_element_gecko
*
*   Used by Gecko browsers to insert elements into the document for the insertTable method
*
*   @param  win The window object to insert the element into
*   @param  elem    The element to insert into the content window
**/
WYSIWYG_Editor.prototype._insert_element_gecko = function (win, elem){
    var sel = win.getSelection();           // get current selection
    var range = sel.getRangeAt(0);          // get the first range of the selection (there's almost always only one range)
    sel.removeAllRanges();                  // deselect everything
    range.deleteContents();                 // remove content of current selection from document
    var container = range.startContainer;   // get location of current selection
    var pos = range.startOffset;
    range=document.createRange();           // make a new range for the new selection

    if (container.nodeType==3 && elem.nodeType==3) {
        // if we insert text in a textnode, do optimized insertion
        container.insertData(pos, elem.nodeValue);

        // put cursor after inserted text
        range.setEnd(container, pos+elem.length);
        range.setStart(container, pos+elem.length);
    }else{
        var afterNode;
        if (container.nodeType==3) {
          // when inserting into a textnode we create 2 new textnodes and put the elem in between
          var textNode = container;
          container = textNode.parentNode;
          var text = textNode.nodeValue;

          var textBefore = text.substr(0,pos);  // text before the split
          var textAfter = text.substr(pos);     // text after the split

          var beforeNode = document.createTextNode(textBefore);
          var afterNode = document.createTextNode(textAfter);

          // insert the 3 new nodes before the old one
          container.insertBefore(afterNode, textNode);
          container.insertBefore(elem, afterNode);
          container.insertBefore(beforeNode, elem);

          // remove the old node
          container.removeChild(textNode);
        }else{
          // else simply insert the node
          afterNode = container.childNodes[pos];
          container.insertBefore(elem, afterNode);
        }
    }
}

/**
*   WYSIWYG_Editor::setColor
*
*   Used to set the text or highlight color of the selected text in Gecko engine browsers
**/
WYSIWYG_Editor.prototype.setColor = function (color, command){
    // close the window we made to display the pallete in
    if(typeof(pwin)=='object'){ // make sure it exists
        if(!pwin.closed){ // if it is still open
            pwin.close();
        }
    }

    // only one difference for MSIE
    if(this.isMSIE() && command == 'hilitecolor') command = 'backcolor';

    //get current selected range
    var sel=document.getElementById(this.wysiwyg_content).contentWindow.document.selection;
    if(sel!=null){
        rng=sel.createRange();
    } 

    document.getElementById(this.wysiwyg_content).contentWindow.focus();
    if(document.getElementById(this.wysiwyg_content).contentWindow.document.queryCommandEnabled(command)){
        document.getElementById(this.wysiwyg_content).contentWindow.document.execCommand(command, false, color);
    }else return false;
    document.getElementById(this.wysiwyg_content).contentWindow.focus();
    return true;
}

/**
*   WYSIWYG_Editor::getPallete
*
*   Apply a text color to selected text or starting at current position
*
*   @param  command string  Used to determine which pallete pop-up to display
**/
WYSIWYG_Editor.prototype.getPallete = function (command, optn, evnt) {
    // get the pallete HTML code
    html = this._get_palette_html(command);

    // close the window we made to display the pallete in
    // this is in case someone clicked the hilitecolor, then clicked the forcolor button
    // without making a choice
    if(typeof(pwin)=='object'){ // make sure it exists
        if(!pwin.closed){ // if it is still open
            pwin.close();
        }
    }

    // OK, now I need to open a new window to capture a click from
    pwin = window.open('','colorPallete','dependent=yes, directories=no, fullscreen=no,hotkeys=no,height=120,width=172,left='+evnt.screenX+',top='+evnt.screenY+',locatoin=no,menubar=no,resizable=no,scrollbars=no,status=no,titlebar=no,toolbar=no');
    pwin.document.write(html);
    pwin.document.close(); // prevents page from attempting to load more code
}

/**
*   WYSIWYG_Editor::isSupported
*
*   Checks that the browser supports this programming by writing an invisible IFRAME and testing its properties
*/
WYSIWYG_Editor.prototype.isSupported = function () {
    // This is to get rid of the browser UA check that was previously implemented for this class.
    // should be called from somewhere in the body of the document for best results
    document.write('<iframe src="_blank" id="WYSIWYG_Editor_Testing_Browser_Features" style="display: none; visibility: hidden;"></iframe>');
    test = typeof(document.getElementById('WYSIWYG_Editor_Testing_Browser_Features').contentWindow);
    if(test == 'object'){
        return true;
    }else{
        return false;
    }
    return this.supported;
}

/**
*   WYSIWYG_Editor::isMSIE
*
*   Checks if browser is MSIE by testing the document.all property that is only supported by MSIE and AOL
*/
WYSIWYG_Editor.prototype.isMSIE = function (){
    if(typeof(document.all)=='object'){
        return true;
    }else{
        return false;
    }
}

/**
*   WYSIWYG_Editor::_get_palette_html
*
*   Generates the HTML that will be used in the color palette pop-ups.
*
*   @param  command string  The command that indicates which text color is being set
**/
WYSIWYG_Editor.prototype._get_palette_html = function (command) {
    s =     '<!doctype HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">';
    s = s + '<html>';
    s = s + ' <head>';
    s = s + '  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">';
    s = s + '  <title>Color Pallete</title>';
    s = s + '  <style type="text/css">';
    s = s + '   <!--';
    s = s + '    html,body{margin: 0px; padding: 0px; color: black; background-color: white;}';
    s = s + '    td{border: black none 2px;}';
    s = s + '    td:hover{border: white solid 2px;}';
    s = s + '   -->';
    s = s + '  </style>';
    s = s + ' </head>';
    s = s + '';
    // we want the focus is brought to this new page
    s = s + ' <body onload="window.focus()" onblur="window.focus()">';
    s = s + '  <table border="0" cellpadding="0" cellspacing="2">';
    s = s + '   <tr>';
    s = s + '    <td id="cFFFFFF" bgcolor="#FFFFFF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFCCCC" bgcolor="#FFCCCC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFCC99" bgcolor="#FFCC99" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFFF99" bgcolor="#FFFF99" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFFFCC" bgcolor="#FFFFCC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c99FF99" bgcolor="#99FF99" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c99FFFF" bgcolor="#99FFFF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cCCFFFF" bgcolor="#CCFFFF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cCCCCFF" bgcolor="#CCCCFF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFCCFF" bgcolor="#FFCCFF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '   </tr>';
    s = s + '   <tr>';
    s = s + '    <td id="cCCCCCC" bgcolor="#CCCCCC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFF6666" bgcolor="#FF6666" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFF9966" bgcolor="#FF9966" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFFF66" bgcolor="#FFFF66" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFFF33" bgcolor="#FFFF33" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c66FF99" bgcolor="#66FF99" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c33FFFF" bgcolor="#33FFFF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c66FFFF" bgcolor="#66FFFF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c9999FF" bgcolor="#9999FF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFF99FF" bgcolor="#FF99FF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '   </tr>';
    s = s + '   <tr>';
    s = s + '    <td id="cC0C0C0" bgcolor="#C0C0C0" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFF0000" bgcolor="#FF0000" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFF9900" bgcolor="#FF9900" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFCC66" bgcolor="#FFCC66" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFFF00" bgcolor="#FFFF00" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c33FF33" bgcolor="#33FF33" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c66CCCC" bgcolor="#66CCCC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c33CCFF" bgcolor="#33CCFF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c6666CC" bgcolor="#6666CC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cCC66CC" bgcolor="#CC66CC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '   </tr>';
    s = s + '   <tr>';
    s = s + '    <td id="c999999" bgcolor="#999999" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cCC0000" bgcolor="#CC0000" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFF6600" bgcolor="#FF6600" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFCC33" bgcolor="#FFCC33" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cFFCC00" bgcolor="#FFCC00" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c33CC00" bgcolor="#33CC00" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c00CCCC" bgcolor="#00CCCC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c3366FF" bgcolor="#3366FF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c6633FF" bgcolor="#6633FF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cCC33CC" bgcolor="#CC33CC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '   </tr>';
    s = s + '   <tr>';
    s = s + '    <td id="c666666" bgcolor="#666666" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c990000" bgcolor="#990000" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cCC6600" bgcolor="#CC6600" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="cCC9933" bgcolor="#CC9933" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c999900" bgcolor="#999900" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c009900" bgcolor="#009900" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c339999" bgcolor="#339999" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c3333FF" bgcolor="#3333FF" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c6600CC" bgcolor="#6600CC" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c993399" bgcolor="#993399" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '   </tr>';
    s = s + '   <tr>';
    s = s + '    <td id="c333333" bgcolor="#333333" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c660000" bgcolor="#660000" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c993300" bgcolor="#993300" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c996633" bgcolor="#996633" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c666600" bgcolor="#666600" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c006600" bgcolor="#006600" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c336666" bgcolor="#336666" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c000099" bgcolor="#000099" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c333399" bgcolor="#333399" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c663366" bgcolor="#663366" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '   </tr>';
    s = s + '   <tr>';
    s = s + '    <td id="c000000" bgcolor="#000000" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c330000" bgcolor="#330000" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c663300" bgcolor="#663300" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c663333" bgcolor="#663333" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c333300" bgcolor="#333300" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c003300" bgcolor="#003300" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c003333" bgcolor="#003333" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c000066" bgcolor="#000066" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c330099" bgcolor="#330099" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '    <td id="c330033" bgcolor="#330033" width="15" height="15" onclick="str=this.id;color=str.replace(\'c\',\'#\');opener.getRegisteredRTE(\''+this.instance_name+'\').setColor(color,\''+command+'\');window.close();"><img width="1" height="1" alt="" src=""></td>';
    s = s + '   </tr>';
    s = s + '  </table>';
    s = s + ' </body>';
    s = s + '</html>';
    return s;
}

/*****
    The following methods are used in my own custom CMS, but I have left them here for
    reference purposes in the event that you too want to incorporate some of this
    additional functionality.
*****/

/**
*   WYSIWYG_Editor::insertImage
*
*   Brings a pop-up window for the user to choose an image from to insert into the contents
*   This is really only part of my custom CMS, but I left it here for reference if other people
*   want to use a similar method.
**/
WYSIWYG_Editor.prototype.insertImage = function (){
//    this.imagePopUp=window.open(this.site_path+'/_include/back_end/image_dialog.php?page_title='+this.page_title,'','width=620,height=500,resizable=1,scrollbars=yes');

    //get current selected range
//    var sel=document.getElementById(this.wysiwyg_content).contentWindow.document.selection;
//    if(sel!=null){
//        rng=sel.createRange();
//    }
    gr = prompt('Numero de grafico');
    if (gr==null || gr==0) return;
    w = prompt('ancho');
    h = prompt('alto');
	 var htmlText = '<IMG src="{*grafico_'+gr+'}" width="'+w+'" height="'+h+'" />';
     this.doTextFormat('insertHTML',htmlText,null);
}
WYSIWYG_Editor.prototype.insertPage = function (){
   var htmlText = '<DIV STYLE="page-break-before:always"><hr style="border: 1px dashed #000;" /></DIV>';
   this.doTextFormat('insertHTML',htmlText,null);
}
WYSIWYG_Editor.prototype.insertPageLand= function (){
	   var htmlText = '<DIV class="landscape"><div><br/><br/></div></DIV>';
	   this.doTextFormat('insertHTML',htmlText,null);
	}
WYSIWYG_Editor.prototype.insertField = function (){
    gr = prompt('Variable');
    if (gr==null || gr==0) return;

    var htmlText = '<DIV id="variable__'+gr+'" class="editable" style="display:inline-block;">&nbsp;&nbsp;&nbsp;</DIV>';
	this.doTextFormat('insertHTML',htmlText,null);
	}

/**
*   WYSIWYG_Editor::addImage
*
*   Puts the image into the editor iframe (Called from the pop-up window that insertImage creates)
**/
WYSIWYG_Editor.prototype.addImage = function (thisimage){
    if(this.imagePopUp && !this.imagePopUp.closed)
        this.imagePopUp.close();
    document.getElementById(this.wysiwyg_content).contentWindow.focus()
    if(this.viewMode==1){
        var x=document.getElementById(this.wysiwyg_content).contentWindow.document;
        x.execCommand('insertimage', false, thisimage);
    }
}

/**
*   WYSIWYG_Editor::checkSpelling
*
*   This method uses spellerpages 0.5.1 to check the spelling of our
*   content area's HTML code... May not produce the results we really
*   want, but it's better than not having any checking!
**/
WYSIWYG_Editor.prototype.checkSpelling = function (){
    if(this.spell_path.length > 0){
/**
NOW I WOULD LIKE A WAY TO DYNAMICALLY INCLUDE THE JAVASCRIPT FILE HERE. (LIKE INCLUDE IN PHP)
OTHERWISE, YOU NEED TO INCLUDE THE JS FILE IN THE HTML PAGE. I WANT TO AVOID THAT IF POSSIBLE.
I WASN'T ABLE TO FIND A CLEAN WAY OF DOING SO, AND USING XMLHttpRequest DIDN'T WORK.
**/

        if(typeof(spellChecker)!='undefined'){
            // file is included, time to create an object to use

            this.speller = new spellChecker(document.getElementById(this.wysiwyg_hidden));
            if(typeof(this.speller)!='undefined'){
                this.prepareSubmit(); // so that we are using the most updated code
                this.speller.popUpUrl = this.spell_path+'/spellchecker.html';
                this.speller.popUpName = 'spellchecker';
                //this.speller.popUpProps = "menu=no,width=440,height=350,top=70,left=120,resizable=yes,status=yes";
                this.speller.popUpProps = "width=440,height=350,top=70,left=120,resizable=yes,status=yes";
                
                // choose which method you are going to use... there is php, ColdFusion and
                // cgi/perl that comes with spellerpages you will need to customize that script.
                // In this instance, the PHP script has been configured to accept settings
                // via query string which is not supported by the default script provided as spellchecker.php
                this.speller.spellCheckScript = this.spell_path+'/server-scripts/spellchecker.php?spellercss='+this.spell_path+'/spellerStyle.css&word_win_src='+this.spell_path+'/wordWindow.js';
                
                // this is made to replace spellerpages 0.5.1 terminateSpell function - do this instead of
                // editing the original files since we only want to replace the function for THIS instance
                // of the spell checker in case spellerpages is used for other input elements on the same page.
                // if future versions of spellerpages change this function, then the code for this will also
                // need to be updated.
                this.speller.terminateSpell = function (){
/** start 0.5.1 terminateSpell() **/
                    	// called when we have reached the end of the spell checking.
                    	var msg = "Spell check complete:\n\n";
                    	var numrepl = this._getTotalReplaced();
                    	if( numrepl == 0 ) {
                    		// see if there were no misspellings to begin with
                    		if( !this.wordWin ) {
                    			msg = "";
                    		} else {
                    			if( this.wordWin.totalMisspellings() ) {
                    				msg += "No words changed.";
                    			} else {
                    				msg += "No misspellings found.";
                    			}
                    		}
                    	} else if( numrepl == 1 ) {
                    		msg += "One word changed.";
                    	} else {
                    		msg += numrepl + " words changed.";
                    	}
                    	if( msg ) {
                    		msg += "\n";
                    		alert( msg );
                    	}
                    
                    	if( numrepl > 0 ) {
                    		// update the text field(s) on the opener window
                    /** begin koivi edit **/
                    		if(this.textInputs.length && this.wordWin && this.wordWin.textInputs[0] ) {
            				    // the editor only has 1 input!
            					var hidden_element = this.textInputs[0].name;
                                var iframe_element = (hidden_element.substring(0,hidden_element.length-8))+'_WYSIWYG_Editor';

                                // replace the values into the editor
                                document.getElementById(hidden_element).value = this.wordWin.textInputs[0];
                                document.getElementById(iframe_element).contentWindow.document.body.innerHTML = this.wordWin.textInputs[0];
                    	    }
                    /** end koivi edit **/
                    	}
                    
                    	// return back to the calling window
                    	this.spellCheckerWin.close();
                    	
                    	return true;
/** end 0.5.1 terminateSpell() **/
                }
                this.speller.openChecker();
            }else{
                alert('Unable to initiate spell checking session.');
                return false;
            }
        }else{
            alert('SpellerPages has not been included in your HTML document.');
        }
    }
}
