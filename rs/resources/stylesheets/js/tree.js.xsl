<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:variable name="skin_path" select="skin/@base_path" />

<xsl:template match="/">

<!-- set global variables -->
USETEXTLINKS = 1;
STARTALLOPEN = 0;
USEFRAMES = 0;
USEICONS = 1;
WRAPTEXT = 1;
PERSERVESTATE = 0;
HIGHLIGHT = 1;
HIGHLIGHT_COLOR = '<xsl:value-of select="skin/styles/style[@id='tree']/foreground/color[@id='selection']/@value" />';
HIGHLIGHT_BG = '<xsl:value-of select="skin/styles/style[@id='tree']/background/color[@id='selection']/@value" />';
ICONPATH = '<xsl:value-of select="$skin_path"/>/images/tree/';


<!-- function to build urls -->
<![CDATA[
function bURL(actOwner, action) {
return "javascript: goTo(null,\"do-ViewAreaAndTitleAction\",false, \"\", \"\", \"view_area_and_title\", \"" + actOwner + "\", \"" + action + "\", true)";
}
]]>

<xsl:apply-templates select="trees"/>

</xsl:template>

<xsl:template match="trees">

<!-- to separate foldersTree def from the last function, when compressed -->
<!-- generate the root node -->
foldersTree = gFld('<xsl:value-of select="@description"/>', 'javascript:undefined', '', '');
foldersTree.treeID = '<xsl:value-of select="@tree_id"/>';
<xsl:if test="icon/@file">
foldersTree.iconSrc = 'pss_icon/<xsl:value-of select="icon/@file"/>';
foldersTree.iconSrcClosed = 'pss_icon/<xsl:value-of select="icon/@file"/>';

cnArr = new Array(); 
lvl = 0; 
cnArr[lvl]=foldersTree; 

</xsl:if>

<xsl:apply-templates select="tree_node_dummy" />

<!-- generate the trees -->
<xsl:apply-templates select="tree_node" />

<!-- set initial selection -->
<xsl:if test="@initial_selection">
setPssSelection('<xsl:value-of select="@initial_selection"/>');
</xsl:if>

</xsl:template>

<xsl:template match="tree_node">
	var fld=gFld('<xsl:value-of select="description/."/>', bURL('<xsl:value-of select="@act_owner"/>', '<xsl:value-of select="@action"/>'), '<xsl:value-of select="@act_owner"/>', '<xsl:value-of select="@action"/>');
	
	<xsl:if test="icon/@file">
		fld.iconSrc = 'pss_icon/<xsl:value-of select="icon/@file"/>';
		fld.iconSrcClosed = 'pss_icon/<xsl:value-of select="icon/@file"/>';
	</xsl:if>
	<xsl:if test="@expand">
		fld.setExpandAllways('<xsl:value-of select="@expand"/>');
	</xsl:if>
	insFld(cnArr[lvl], fld, <xsl:value-of select="@has_children"/>);
	lvl++; cnArr[lvl]=fld; 
	<xsl:apply-templates select="tree_node"/>
	lvl--;
</xsl:template>

<xsl:template match="tree_node_dummy">
	var fdummy=gFld('<xsl:value-of select="description/."/>', '', '', '');

	<xsl:if test="icon/@file">
		fdummy.iconSrc = 'pss_icon/<xsl:value-of select="icon/@file"/>';
		fdummy.iconSrcClosed = 'pss_icon/<xsl:value-of select="icon/@file"/>';
	</xsl:if>

	registerDummy(fdummy);

</xsl:template>



</xsl:stylesheet> 