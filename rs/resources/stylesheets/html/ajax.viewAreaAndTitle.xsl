<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


	<xsl:param name="dictionary" select="0"/>
	<xsl:param name="requestid" select="0"/>




	<!-- includes -->
	<xsl:include href="page.view.xsl"/>

	<xsl:variable name="page_stereotype" select="page/header/@stereotype" />

	<!-- global instructions -->
	<xsl:strip-space elements="*"/>

	<!-- empty templates -->
	<xsl:template match="page/objects"/>
	<xsl:template match="page/header" />
	<xsl:template match="page/view/navigation_bar" />
	<xsl:template match="page/view/history_bar" />

	<xsl:template match="page/view" >
   		<script>initializeViewArea();</script>
		<xsl:call-template name="render_view_area"/>
		<script>setupStyles();</script>

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
						0);
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
						1);
					</script>
			</div>
		</xsl:for-each>	
		</div>
	</xsl:template>



</xsl:stylesheet> 
