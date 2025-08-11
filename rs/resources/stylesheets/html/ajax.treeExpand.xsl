<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- global instructions -->
	<xsl:strip-space elements="*"/>

	<!-- empty templates -->
<xsl:template match="tree_expand">
  <script>
    <xsl:apply-templates select="tree_node" />
  </script>
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
	
	addChildOnLine(fld, <xsl:value-of select="@has_children"/>);	
</xsl:template>

</xsl:stylesheet> 
