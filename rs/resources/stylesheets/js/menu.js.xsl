<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:variable name="skin_path" select="skin/@base_path" />

<xsl:variable name="menu_bar_style_id" select="'menu_bar'" />
<xsl:variable name="menu_style_id" select="'popup_menu'" />
<xsl:variable name="menu_bar_item_style_id" select="'menu_bar_item'" />
<xsl:variable name="menu_item_style_id" select="'popup_menu_item'" />

<xsl:template match="/">


<!--
	*********************************************
   BEGIN: SECTION TO CONFIGURE MENU PROPERTIES
  *********************************************
-->
<!-- behavior -->
ajustePopupUpX=<xsl:if test="not(skin/styles/style[@id=$menu_bar_style_id]/@ajustePopUpX)">-230</xsl:if><xsl:if test="skin/styles/style[@id=$menu_bar_style_id]/@ajustePopUpX"><xsl:value-of select="skin/styles/style[@id=$menu_bar_style_id]/@ajustePopUpX"/></xsl:if>; 
ajustePopupUpY=<xsl:if test="not(skin/styles/style[@id=$menu_bar_style_id]/@ajustePopUpY)">-10 </xsl:if><xsl:if test="skin/styles/style[@id=$menu_bar_style_id]/@ajustePopUpY"><xsl:value-of select="skin/styles/style[@id=$menu_bar_style_id]/@ajustePopUpY"/></xsl:if>; 
timegap=500;                   <!-- The time delay for menus to remain visible -->
followspeed=5;                 <!-- Follow Scrolling speed -->
followrate=50;                 <!-- Follow Scrolling Rate -->
suboffset_top=5;               <!-- Sub menu offset Top position -->
suboffset_left=10;             <!-- Sub menu offset Left position -->
openMenusOnMouseClick=1;      <!-- 1=open on click, 0=open on mouse over -->
withIconMenuBar=<xsl:if test="not(skin/styles/style[@id=$menu_bar_style_id]/@with_icon)">1</xsl:if><xsl:if test="skin/styles/style[@id=$menu_bar_style_id]/@with_icon"><xsl:value-of select="skin/styles/style[@id=$menu_bar_style_id]/@with_icon"/></xsl:if>;      <!-- 1=con iconos, 0=sin iconos -->
<!-- fonts:   name,size,style(normal-italic),weight(normal-bold) -->
menuBarFont = ["<xsl:value-of select="skin/styles/style[@id=$menu_bar_style_id]/foreground/font/@name" />",
"<xsl:value-of select="skin/styles/style[@id=$menu_bar_style_id]/foreground/font/@size" />",
"<xsl:value-of select="skin/styles/style[@id=$menu_bar_style_id]/foreground/font/@style" />",
"<xsl:value-of select="skin/styles/style[@id=$menu_bar_style_id]/foreground/font/@weight" />"];
menuFont = menuBarFont;
<xsl:if test="skin/styles/style[@id=$menu_style_id]/foreground/font" >
menuFont = ["<xsl:value-of select="skin/styles/style[@id=$menu_style_id]/foreground/font/@name" />",
"<xsl:value-of select="skin/styles/style[@id=$menu_style_id]/foreground/font/@size" />",
"<xsl:value-of select="skin/styles/style[@id=$menu_style_id]/foreground/font/@style" />",
"<xsl:value-of select="skin/styles/style[@id=$menu_style_id]/foreground/font/@weight" />"];
</xsl:if>
<!-- menu bar color scheme -->
menuBarBack = "transparent";
menuBarBackOn = "<xsl:value-of select="skin/styles/style[@id=$menu_bar_item_style_id]/background/color[@id='selected']/@value"/>";
menuBarBorder = "transparent";
<!-- menu bar item color scheme -->
menuBarItemBack = "<xsl:value-of select="skin/styles/style[@id=$menu_bar_item_style_id]/background/color[@id='unselected']/@value"/>";
menuBarItemBackOn = menuBarBackOn;
menuBarItemFore = "<xsl:value-of select="skin/styles/style[@id=$menu_bar_item_style_id]/foreground/color[@id='unselected']/@value"/>";
menuBarItemForeOn = "<xsl:value-of select="skin/styles/style[@id=$menu_bar_item_style_id]/foreground/color[@id='selected']/@value"/>";
menuBarItemBorder = "<xsl:value-of select="skin/styles/style[@id=$menu_bar_style_id]/background/color/@value"/>";;
menuBarItemBorderOn = menuBarItemForeOn;
<!-- pop up menu color scheme -->
menuBack = "<xsl:value-of select="skin/styles/style[@id=$menu_style_id]/background/color/@value"/>";
menuBackOn = "<xsl:value-of select="skin/styles/style[@id=$menu_item_style_id]/background/color[@id='selected']/@value"/>";
menuBorder = "<xsl:value-of select="skin/styles/style[@id=$menu_style_id]/border/color/@value"/>";
<!-- pop up menu item color scheme -->
menuItemBack = "<xsl:value-of select="skin/styles/style[@id=$menu_item_style_id]/background/color[@id='unselected']/@value"/>";
menuItemBackOn = menuBackOn;
menuItemFore = "<xsl:value-of select="skin/styles/style[@id=$menu_item_style_id]/foreground/color[@id='unselected']/@value"/>";
menuItemForeOn = "<xsl:value-of select="skin/styles/style[@id=$menu_item_style_id]/foreground/color[@id='selected']/@value"/>";
menuItemBorder = "transparent";
menuItemBorderOn = "transparent";
<!-- images -->
imagesPath = "pss_icon/";
skinPath = "<xsl:value-of select="$skin_path" />";
subMenuArrow = imagesPath + "aderecha.gif";
menuBarBackImg = "<xsl:value-of select="skin/styles/style[@id=$menu_bar_style_id]/background/image/@file"/>";
if(!(menuBarBackImg=="")) {
menuBarBackImg=skinPath+'/'+menuBarBackImg;
}
menuBackImg = "";
menuBarItemIconBorder=0;
menuItemIconBorder=0;
<!-- menu bar layout -->
menuBarIsHorizontal = 1;
verticalBarWidth = 160;         <!-- it has sense if the menu bar is vertical -->
horizontalBarItemWidth = "<xsl:value-of select="skin/styles/style[@id=$menu_bar_item_style_id]/@width"/>";
if (!(horizontalBarItemWidth) || (horizontalBarItemWidth=="")) {
	horizontalBarItemWidth = ""; <!-- it has sense if the menu bar is horizontal; it may be "" to mean it has to adjust to text and icon -->
}
menuBarMargin = <xsl:value-of select="skin/styles/style[@id=$menu_bar_style_id]/padding/@left"/>;
menuBarItemPadding = <xsl:value-of select="skin/styles/style[@id=$menu_bar_item_style_id]/padding/@left"/>;
menuBarItemTextPosition = "<xsl:value-of select="skin/styles/style[@id=$menu_bar_item_style_id]/foreground/content_layout/@text_position"/>";
<!-- top ; bottom ; left ; right -->
if (menuBarItemTextPosition=="top") menuBarItemIconPosition = "bottom";
else if (menuBarItemTextPosition=="bottom") menuBarItemIconPosition = "top";
else if (menuBarItemTextPosition=="left") menuBarItemIconPosition = "right";
else if (menuBarItemTextPosition=="right") menuBarItemIconPosition = "left";
menuBarItemTextIconGap = <xsl:value-of select="skin/styles/style[@id=$menu_bar_item_style_id]/foreground/content_layout/@text_icon_gap"/>;
menuBarItemHAlign = "<xsl:value-of select="skin/styles/style[@id=$menu_bar_item_style_id]/foreground/content_layout/@halignment"/>";
menuBarItemVAlign = "<xsl:value-of select="skin/styles/style[@id=$menu_bar_item_style_id]/foreground/content_layout/@valignment"/>";
if (menuBarItemVAlign=="center") menuBarItemVAlign = "middle";

<!-- pop up menu layout -->
menuMargin = <xsl:value-of select="skin/styles/style[@id=$menu_style_id]/padding/@left"/>;
menuItemPadding = <xsl:value-of select="skin/styles/style[@id=$menu_item_style_id]/padding/@left"/>;
menuItemTextPosition = "<xsl:value-of select="skin/styles/style[@id=$menu_item_style_id]/foreground/content_layout/@text_position"/>";
if (menuItemTextPosition=="top") menuItemIconPosition = "bottom";
else if (menuItemTextPosition=="bottom") menuItemIconPosition = "top";
else if (menuItemTextPosition=="left") menuItemIconPosition = "right";
else if (menuItemTextPosition=="right") menuItemIconPosition = "left";
<!-- top ; bottom ; left ; right -->
menuItemTextIconGap = <xsl:value-of select="skin/styles/style[@id=$menu_item_style_id]/foreground/content_layout/@text_icon_gap"/>;
menuItemHAlign = "<xsl:value-of select="skin/styles/style[@id=$menu_item_style_id]/foreground/content_layout/@halignment"/>";

menuWidth = <xsl:value-of select="skin/styles/style[@id=$menu_style_id]/@width"/>;
<!-- effects -->
menuEffects = "fade(duration=0.4);Shadow(color=aaaaaa,Direction=115,Strength=4)"<!--"Fade(duration=0.1);Alpha(style=0,opacity=90);Shadow(color='#777777', Direction=135, Strength=5)"-->;
<!-- "fade(duration=0.4);Shadow(color=aaaaaa,Direction=115,Strength=4)", -->

enabledLink = true;

<!-- END: SECTION TO CONFIGURE MENU PROPERTIES -->

<![CDATA[menunum=0;menus=new Array();_d=document;function addmenu(){menunum++;menus[menunum]=menu;}function dumpmenus(){mt="<scr"+"ipt language=JavaScript>";for(a=1;a<menus.length;a++){mt+=" menu"+a+"=menus["+a+"];"}mt+="<\/scr"+"ipt>";_d.write(mt)}]]>
var context_menu;
var layout_menu;

menuBarStyle=
[                 
menuBarItemFore,              <!-- Mouse Off Font Color -->
menuBarBack,                  <!-- Mouse Off Background Color (use zero for transparent in Netscape 6) -->
menuBarItemForeOn,            <!-- Mouse On Font Color -->
menuBarBackOn,                <!-- Mouse On Background Color -->
menuBarBorder,                <!-- Menu Border Color -->
menuBarFont[1],               <!-- Font Size (default is px but you can specify mm, pt or a percentage) -->
menuBarFont[2],               <!-- Font Style (italic or normal) -->
menuBarFont[3],               <!-- Font Weight (bold or normal) -->
menuBarFont[0],   						<!-- Font Name -->
menuBarItemPadding,           <!-- Menu Item Padding or spacing -->
,                             <!-- Sub Menu Image (Leave this blank if not needed) -->
0,                            <!-- 3D Border & Separator bar -->
,                             <!-- 3D High Color -->
,                             <!-- 3D Low Color -->
,                             <!-- Current Page Item Font Color (leave this blank to disable) -->
,                             <!-- Current Page Item Background Color (leave this blank to disable) -->
,                             <!-- Top Bar image (Leave this blank to disable) -->
,                             <!-- Menu Header Font Color (Leave blank if headers are not needed) -->
,                             <!-- Menu Header Background Color (Leave blank if headers are not needed) -->
,                             <!-- Menu Item Separator Color -->
];
menuStyle=
[                 
menuItemFore,                 <!-- Mouse Off Font Color -->
menuBack,                     <!-- Mouse Off Background Color (use zero for transparent in Netscape 6) -->
menuItemForeOn,               <!-- Mouse On Font Color -->
menuBackOn,                   <!-- Mouse On Background Color -->
menuBorder,                   <!-- Menu Border Color -->
menuFont[1],                  <!-- Font Size (default is px but you can specify mm, pt or a percentage) -->
menuFont[2],                  <!-- Font Style (italic or normal) -->
menuFont[3],                  <!-- Font Weight (bold or normal) -->
menuFont[0],                  <!-- Font Name -->
menuItemPadding,              <!-- Menu Item Padding or spacing -->
subMenuArrow,                 <!-- Sub Menu Image (Leave this blank if not needed) -->
0,                            <!-- 3D Border & Separator bar -->
,                             <!-- 3D High Color -->
,                             <!-- 3D Low Color -->
,                             <!-- Current Page Item Font Color (leave this blank to disable) -->
,                             <!-- Current Page Item Background Color (leave this blank to disable) -->
,                             <!-- Top Bar image (Leave this blank to disable) -->
,                             <!-- Menu Header Font Color (Leave blank if headers are not needed) -->
,                             <!-- Menu Header Background Color (Leave blank if headers are not needed) -->
,                             <!-- Menu Item Separator Color -->
];
<!--
	GLOBAL FUNCTIONS
	-->
<!-- Creates the HTML which displays a menu item --><![CDATA[
function cidh(text,icon,isBarItem) {
	if(isBarItem==1) {
		halign = menuBarItemHAlign;
		valign = menuBarItemVAlign;
	} else {
		halign = menuItemHAlign;
	}
	
	blockTag = "<p style='margin:0px;' align='" + halign + "'>";
	if(icon=="" || (withIconMenuBar==0 && isBarItem==1)) {
		return blockTag + text + "</p>";
	}
	if(isBarItem==1) {
		iconPos = menuBarItemIconPosition;
		iconBorder = menuBarItemIconBorder;
		gap = menuBarItemTextIconGap;
	} else {
		iconPos = menuItemIconPosition;
		iconBorder = menuItemIconBorder;
		gap = menuItemTextIconGap;
	}
	valignTag=(valign==null?"":"vertical-align:"+valign);
	imgPreTag = "<img style='margin-";
	imgPostTag = ":" + gap + "px;"+ valignTag + "' src="+imagesPath+icon+" border="+iconBorder+">";
	if(iconPos=="top") {
		return blockTag + imgPreTag + "bottom" + imgPostTag + "<br>" + text + "</p>";
	} else if(iconPos=="bottom") {
		return blockTag + text + "<br>" + imgPreTag + "top" + imgPostTag + "</p>";
	} else if(iconPos=="left") {
		return blockTag + imgPreTag + "right" + imgPostTag + text + "</p>";
	} else if(iconPos=="right") {
		return blockTag + text + imgPreTag + "left" + imgPostTag + "</p>";
	}
};]]>
<!-- Creates the HTML which links an item with an URI -->
function cilh(uri,isBarItem,overImg) {
	var sBackColor;
	if (!enabledLink) {
		sBackColor = "#B2B2B2";
	} else {
		if(isBarItem==1) {
			sBackColor = menuBarItemBack;
		} else {
			sBackColor = menuItemBack;
		}
	}
	var swapImg = "";
	if(overImg!="") {
		swapImg = " swapimage=" + imagesPath + overImg;
	}
	if(isBarItem==1) {
		return (uri==""?"#":uri) + swapImg + " offbackcolor="+sBackColor+
					 ";onbordercolor="+menuBarItemBorderOn+
					 ";"+(menuBarItemBorder=="transparent"?"":"offbordercolor="+menuBarItemBorder+";");
	} else {
		return (uri==""?"#":uri) + swapImg + " offbackcolor="+sBackColor+
					 ";onbordercolor="+menuItemBorderOn+
					 ";"+(menuItemBorder=="transparent"?"":"offbordercolor="+menuItemBorder+";");
	}
};
<!-- Creates an URI for a Pss action --><![CDATA[
function cURI(action, newWindow) {
	enabledLink = true;
	return "javascript:goToMenuAction(\"" + action + "\",\"" + newWindow + "\");closeallmenus();";
};]]>
<!-- Creates an URI for a Pss action --><![CDATA[
function cEjec(action) {
	enabledLink = true;
	return "javascript:"+action+ ";closeallmenus();";
};]]>
<!-- Adds the menu bar definition -->
function addMBar() {
addmenu(menu=[
"pssMenuBar",                       <!--  0 - Menu Name - This is needed in order for this menu to be called -->
,                                    <!--  1 - Menu Top - The Top position of this menu in pixels -->
,                                    <!--  2 - Menu Left - The Left position of this menu in pixels -->
menuBarIsHorizontal==1 ?
	horizontalBarItemWidth :
	verticalBarWidth,                  <!--  3 - Menu Width - Menus width in pixels -->
0,                                   <!--  4 - Menu Border Width -->
,                                    <!--  5 - Screen Position - here you can use "center;left;right;middle;top;bottom" or a combination of "center:middle" -->
menuBarStyle,                        <!--  6 - Properties Array - this array is declared higher up as you can see above -->
1,                                   <!--  7 - Always Visible - allows this menu item to be visible at all time (1=on or 0=off) -->
,                                    <!--  8 - Alignment - sets this menu elements text alignment, values valid here are: left, right or center -->
,                                    <!--  9 - Filter - Text variable for setting transitional effects on menu activation - see above for more info -->
0,                                   <!-- 10 - Follow Scrolling Top Position - Tells this menu to follow the user down the screen on scroll placing the menu at the value specified. -->
menuBarIsHorizontal,                 <!-- 11 - Horizontal Menu - Tells this menu to display horizontaly instead of top to bottom style (1=on or 0=off) -->
0,                                   <!-- 12 - Keep Alive - Keeps the menu visible until the user moves over another menu or clicks elsewhere on the page (1=on or 0=off) -->
,                                    <!-- 13 - Position of TOP sub image left:center:right -->
menuBarMargin*2,                     <!-- 14 - Set the Overall Width of Horizontal Menu to specified width or 100% and height to a specified amount -->
0,                                   <!-- 15 - Right To Left - Used in Hebrew for example. (1=on or 0=off) -->
openMenusOnMouseClick,               <!-- 16 - Open the Menus OnClick - leave blank for OnMouseover (1=on or 0=off) -->
,                                    <!-- 17 - ID of the div you want to hide on MouseOver (useful for hiding form elements) -->
menuBarBackImg,                      <!-- 18 - Background image for menu Color must be set to transparent for this to work -->
0,                                   <!-- 19 - Scrollable Menu -->
menuBarIsHorizontal==1 ?
	"margin="+menuBarMargin+";" : "",  <!-- 20 - Miscellaneous Menu Properties -->
""																	 <!-- 21 - (EMPTY - reserved) -->
]);
menubar = menu;
};
function addLAYM(newMenuName) {
addmenu(menu=[
newMenuName,,,menuWidth>0?menuWidth:"",1,
,menuStyle,0,,
menuEffects,
1,0,1,,,0,0,,
menuBackImg,
0,
,
""]);
return menu;
};
<!-- Adds a context menu definition with the given name -->
function addCM(newMenuName) {
addmenu(menu=[
newMenuName,,,menuWidth>0?menuWidth:"",1,
,menuStyle,0,,
menuEffects,
1,0,0,,,0,0,,
menuBackImg,
0,
,
""]);
return menu;
};<!-- Adds a menu definition with the given name -->
function addM(newMenuName) {
addmenu(menu=[
newMenuName,,,menuWidth>0?menuWidth:"",1,
,menuStyle,0,,
menuEffects,
0,0,0,,,0,0,,
menuBackImg,
0,
"margin="+menuMargin+";",
""]);
return menu;
};
<!-- Functions to add menu items, for a link or for a submenu -->
function addLMI(menuDef,text,icon,isBarItem,URI,description) {
i = menuDef.length;
menuDef[i]=cidh(text,icon,isBarItem);i++;
menuDef[i]=cilh(URI,isBarItem,"");i++;i++;
menuDef[i]=description;i++;
menuDef[i]=0;
enabledLink = true;
};
function addSMI(menuDef,text,icon,isBarItem,subm,description) {
i = menuDef.length;
menuDef[i]=cidh(text,icon,isBarItem);i++;
menuDef[i]="show-menu="+subm;i++;
menuDef[i]=cilh("",isBarItem,"");i++;
menuDef[i]=description;i++;
menuDef[i]=0;
};


<xsl:apply-templates select="menu" />
dumpmenus();
</xsl:template>

<!-- <xsl:template match="menu"> -->
<xsl:template match="menu">
	cmArr = new Array();
	lvl = 0;
	<xsl:if test="item">
	addMBar();
	</xsl:if>
	<xsl:for-each select="item">
		<xsl:choose>
			<xsl:when test="@is_action='true'">
				URI=cURI("<xsl:value-of select="accion/@action"/>","<xsl:value-of select="accion/@new_window"/>");
				lbl="<xsl:value-of select="accion/@label"/>";
				addLMI(menubar,lbl,"<xsl:value-of select="accion/icon/@file"/>",1,URI,lbl);
			</xsl:when>
			<xsl:otherwise>
				lbl="<xsl:value-of select="accion/@label"/>";
				subm="<xsl:value-of select="accion/@act_owner"/>_<xsl:value-of select="@id"/>";
				addSMI(menubar,lbl,"<xsl:value-of select="accion/icon/@file"/>",1,subm,lbl);
				lvl++; cmArr[lvl]=addM(subm);
				<xsl:apply-templates select="item"/>
				lvl--;
			</xsl:otherwise>
		</xsl:choose>
	</xsl:for-each>
	lvl++; 
	context_menu = "menu"+(menunum+1);
	cmArr[lvl] = addCM(context_menu);
	<![CDATA[var m;for(m=0;m <20;m++) 	addLMI(cmArr[lvl],"texto_"+m,"pss_icon/shopping.gif",0,"URI_"+m,"texto_"+m);]]>
	lvl--;
	lvl++; 
	layout_menu = "menu"+(menunum+1);
	cmArr[lvl] = addLAYM(layout_menu);
	<![CDATA[var m;for(m=0;m <1000;m++) 	addLMI(cmArr[lvl],"texto_"+m,"pss_icon/shopping.gif",0,"URI_"+m,"texto_"+m);]]>
	lvl--;
</xsl:template>

<xsl:template match="item">
	<xsl:choose>
		<xsl:when test="item">
			lbl=unescape("<xsl:value-of select="accion/@label"/>" );
			subm="<xsl:value-of select="accion/@act_owner"/>_<xsl:value-of select="@id"/>";
			addSMI(cmArr[lvl],lbl,"<xsl:value-of select="accion/icon/@file"/>",0,subm,lbl);
			lvl++; cmArr[lvl]=addM(subm);
			<xsl:apply-templates select="item"/>
			lvl--;
		</xsl:when>
		<xsl:otherwise>
			URI=cURI("<xsl:value-of select="accion/@action"/>","<xsl:value-of select="accion/@new_window"/>"); 
			lbl= unescape("<xsl:value-of select="accion/@label"/>");
			addLMI(cmArr[lvl],lbl,"<xsl:value-of select="accion/icon/@file"/>",0,URI,lbl);
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>	


</xsl:stylesheet> 