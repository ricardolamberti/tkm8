<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">








	<!-- includes -->
	<xsl:include href="ajax.base.xsl"/>

	<xsl:template match="page/view/navigation_bar" />
	<xsl:template match="page/view/objects" />
	<xsl:template match="page/view/icon" />
	<xsl:template match="page/view/panel/win_action_bar"/>
	<xsl:template match="page/view/history_bar" />
	<xsl:template match="page/message" />
	
	<xsl:template match="page/view/panel" >
		<xsl:apply-templates select="*"/>
	</xsl:template>

	<xsl:template match="panel[@name='filter_pane']" />

	<xsl:template match="panel[@name='win_list_table_panel']/rows/row[@id_win='0']" >
		<xsl:apply-templates select="*"/>
	</xsl:template>
	
	<xsl:template match="page/help/helpbox"/>
	<xsl:template match="page/help_form" />
	<xsl:template match="page/help">
		<div id ="help_zone">
			<xsl:for-each select="helpbox">
				<div onclick="hide(this);">
					<xsl:call-template name="basic_generate_composite"/>
					
					
					<xsl:if test="@relative">
					<script>
				    	subscribeHelpBox(
						"<xsl:value-of select="@name"/>",
						"<xsl:value-of select="@position"/>",
						"<xsl:value-of select="@relative"/>",
						<xsl:value-of select="@x"/>,
						<xsl:value-of select="@y"/>,
						<xsl:value-of select="@z"/>,
						0
						);
					</script>
					</xsl:if>
				</div>
			</xsl:for-each>	
		</div>
	</xsl:template>

	<xsl:template match="page/publicity_campaign/publicity"/>
	<xsl:template match="page/publicity_campaign">
		<div id ="publicity_zone">
		<xsl:for-each select="publicity">
			<div>
				<xsl:call-template name="basic_generate_composite"/>
					<script>
				    	subscribeHelpBox(
						"<xsl:value-of select="@name"/>",
						"<xsl:value-of select="@position"/>",
						"<xsl:value-of select="@relative"/>",
						<xsl:value-of select="@x"/>,
						<xsl:value-of select="@y"/>,
						<xsl:value-of select="@z"/>,
						1
						);
					</script>
			</div>
		</xsl:for-each>	
		</div>
	</xsl:template>


</xsl:stylesheet> 
