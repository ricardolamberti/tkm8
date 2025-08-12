<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


	<xsl:param name="dictionary" select="0"/>
	<xsl:param name="requestid" select="0"/>





	<!-- includes -->
	<xsl:include href="responsive_page.view.xsl"/>
	
	<xsl:variable name="page_stereotype" select="page/header/@stereotype" />
	

	<!-- global instructions -->
	<xsl:strip-space elements="*"/>
	<xsl:template match="page/message" />

	<!-- empty templates -->
	<xsl:template match="page/view" >
			<!--xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text-->
<!-- 			<script>initializeViewArea();</script> -->
			<script type="POSTSCRIPT">
			 adaptativeScroll('','','body','<xsl:value-of select="@scroll"/>','');
			 firstFocus('<xsl:value-of select="@scroll"/>');
			 analizeTable('<xsl:value-of select="@scroll"/>');
			 analizeModal('<xsl:value-of select="@scroll"/>');
			</script>
			<DIV>
					<script>
							sessionStorage.setItem('dictionary', '<xsl:value-of select="../url/@payload"/>');
						</script>					

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
					<xsl:call-template name="basic_generate_component_responsive" />
			</DIV>


	</xsl:template>


</xsl:stylesheet> 
