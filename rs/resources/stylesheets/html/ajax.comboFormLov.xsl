<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- includes -->
	<xsl:include href="utils.combos.xsl"/>
	
	<xsl:template match="page/view/navigation_bar" />
	<xsl:template match="page/view/history_bar" />
	<xsl:template match="page/view/objects" />

	<xsl:template match="page/message" />
	<xsl:template match="page/help" />
	<xsl:template match="page/help_form" />
	<xsl:template match="page/publicity_campaign" />
	<!-- build the page structure taking the values which come in the header -->
	<xsl:template match="page/view/win_form">
		<xsl:call-template name="do_formlov_combo_box_options"/>
	</xsl:template>

	<xsl:template match="page/view/panel">
		<xsl:apply-templates select="*" />
	</xsl:template>

	<xsl:template match="win_form">
		<xsl:apply-templates select="*" />
	</xsl:template>

	<xsl:template match="form_lov">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
		<xsl:call-template name="do_formlov_combo_box_inner_div">
			<xsl:with-param name="fullname"><xsl:value-of select="$fullname"/></xsl:with-param>
			<xsl:with-param name="specialClass">list_box</xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="win_lov">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
		<xsl:call-template name="do_formlov_combo_box_inner_div">
			<xsl:with-param name="fullname"><xsl:value-of select="$fullname"/></xsl:with-param>
			<xsl:with-param name="specialClass">list_box</xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="win_lov_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
		<xsl:call-template name="do_formlov_combo_box_inner_div_responsive">
			<xsl:with-param name="fullname"><xsl:value-of select="$fullname"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	

		
</xsl:stylesheet> 
