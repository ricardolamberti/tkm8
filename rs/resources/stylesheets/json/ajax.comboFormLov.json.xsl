<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:strip-space elements="*"/>
<xsl:output method="text" omit-xml-declaration="yes" />

	<!-- includes -->
	<xsl:param name="dictionary" select="0"/>
	<xsl:param name="requestid" select="0"/>


	<xsl:template match="page/view/navigation_bar" />
	<xsl:template match="page/view/history_bar" />
	<xsl:template match="page/view/objects" />
	<xsl:template match="page/message" />

	<xsl:template match="page/help" />
	<xsl:template match="page/help_form" />
	<xsl:template match="page/publicity_campaign" />
	
	<xsl:template match="page/view/panel">
		<xsl:apply-templates select="*" />
	</xsl:template>

	<xsl:template match="panel">
		<xsl:apply-templates select="*" />
	</xsl:template>

	<xsl:template match="win_form">
		<xsl:apply-templates select="*" />
	</xsl:template>
	<xsl:template match="column_responsive">
		<xsl:apply-templates select="*" />
	</xsl:template>


	<xsl:template match="tabpanel_responsive">
		<xsl:apply-templates select="*" />
	</xsl:template>

	<xsl:template match="div_responsive">
		<xsl:apply-templates select="*" />
	</xsl:template>

	<xsl:template match="panel_responsive">
		<xsl:apply-templates select="*" />
	</xsl:template>

	<xsl:template match="form_lov">
		<xsl:call-template name="do_formlov_combo_box_options_json"/>
	</xsl:template>

	<xsl:template match="win_lov">
		<xsl:call-template name="do_formlov_combo_box_options_json"/>
	</xsl:template>


	<xsl:template match="dropdownwinlov_responsive">
		<xsl:for-each select="winlov">
			<xsl:call-template name="do_formlov_combo_box_options_json"/>
		</xsl:for-each>
		<xsl:apply-templates select="*" />
	</xsl:template>

	<xsl:template match="dropdownwinlov_responsive_noform">
		<xsl:for-each select="winlov">
			<xsl:call-template name="do_formlov_combo_box_options_json"/>
		</xsl:for-each>
		<xsl:apply-templates select="*" />
	</xsl:template>
	
	<xsl:template match="win_lov_responsive">
		<xsl:call-template name="do_formlov_combo_box_options_json"/>
	</xsl:template>
	
	<xsl:template match="win_grid">
	</xsl:template>
	
	<xsl:template name="do_formlov_combo_box_options_json">
		{
		  "results": [
			<xsl:for-each select="groupCombo">
			{
			      "text": "<xsl:value-of select="@description"/>", 
      			  "children" : [
					<xsl:for-each select="itemCombo">
					    {
					      "id": "<xsl:value-of select="@id"/>",
					      "real_id": "<xsl:value-of select="@real_id"/>",
					      "text": "<xsl:value-of select="@description"/>"
					    }<xsl:if test="position()!=last()">,</xsl:if>
				    </xsl:for-each>
      			  ]
			}<xsl:if test="position()!=last()">,</xsl:if>
			</xsl:for-each>

			<xsl:for-each select="itemCombo">
			    {
			      "id": "<xsl:value-of select="@id"/>",
			      "real_id": "<xsl:value-of select="@real_id"/>",
			      "text": "<xsl:value-of select="@description"/>"
			    }<xsl:if test="position()!=last()">,</xsl:if>
		    </xsl:for-each>
		  ]
		    ,"pagination": {
			    "more": <xsl:value-of select="@moreOptions"/>
			  }
			}
	</xsl:template>

		
</xsl:stylesheet> 
