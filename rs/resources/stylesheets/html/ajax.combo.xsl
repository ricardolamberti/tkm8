<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- includes -->
	<xsl:include href="ajax.base.xsl"/>

	<xsl:template match="page/view/navigation_bar" />
	<xsl:template match="page/view/history_bar" />
	<xsl:template match="page/view/objects" />
	<xsl:template match="page/view/icon" />
	<xsl:template match="page/view/panel/win_action_bar" />
	<xsl:template match="page/message" />
	

	<xsl:template match="page/help" />
	<xsl:template match="page/help_form" />
	<xsl:template match="page/publicity_campaign" />
	<!-- build the page structure taking the values which come in the header -->
	<xsl:template match="page/view/win_form">
		<xsl:call-template name="do_combo_box_options"/>
	</xsl:template>

	<xsl:template match="page/view/panel">
		<xsl:apply-templates select="*" />
	</xsl:template>
<!-- 
	<xsl:template match="win_form">
		<xsl:apply-templates select="*" />
	</xsl:template>

	<xsl:template match="combo_box_dependant">
		<xsl:call-template name="do_combo_box_inner_div">
			<xsl:with-param name="specialClass">combo_box</xsl:with-param>
		</xsl:call-template>
	</xsl:template>
 -->
</xsl:stylesheet> 
