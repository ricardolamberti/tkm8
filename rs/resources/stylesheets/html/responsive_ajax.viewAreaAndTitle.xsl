<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


	<xsl:param name="dictionary" select="0"/>
	<xsl:param name="requestid" select="0"/>




	<!-- includes -->
	<xsl:include href="responsive_page.view.xsl"/>

	<xsl:variable name="page_stereotype" select="page/header/@stereotype" />

	<!-- global instructions -->
	<xsl:strip-space elements="*"/>

	<!-- empty templates -->
	<xsl:template match="page/view" >
 		<script type="POSTSCRIPT">
		 adaptativeScroll('','','body','<xsl:value-of select="@scroll"/>','');
		 firstFocus('<xsl:value-of select="@scroll"/>');
		 analizeTable('<xsl:value-of select="@scroll"/>');
		 analizeModal('<xsl:value-of select="@scroll"/>');
		</script>
							<script>
							sessionStorage.setItem('dictionary', '<xsl:value-of select="../url/@payload"/>');
						</script>					
		
		<script>initializeViewArea();</script>
   		<xsl:if test="@modalmustback">
			<div class="hiddden">
				<xsl:attribute name="id">modalmustback_<xsl:value-of select="@modalmustback"/></xsl:attribute>
			</div>
		</xsl:if>
		<xsl:if test="@clearChangeInputs='true'">
			<div class="hiddden">
				<xsl:attribute name="id">clearChangeInputs</xsl:attribute>
			</div>
		</xsl:if>
		<xsl:if test="../url/@location">
			<div class="hiddden">
				<xsl:attribute name="id">url_location</xsl:attribute>
				<xsl:attribute name="data-modal"><xsl:value-of select="../url/@modal"/></xsl:attribute>
				<xsl:attribute name="data-location"><xsl:value-of select="../url/@location"/></xsl:attribute>
			</div>
		</xsl:if>		
		<xsl:call-template name="render_view"/>
		<script>setupStylesResponsive();</script>

	</xsl:template>

	<xsl:template match="page/message" >
		<xsl:call-template name="render_message"/>
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
