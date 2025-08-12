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
	<xsl:template match="page/help_form" />
	<xsl:template match="page/view/navigation_bar" />
	<xsl:template match="page/view/history_bar" />
	<xsl:template match="page/message" />

	<xsl:template match="page/view" >
			<!--xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text-->
<!-- 			<script>initializeViewArea();</script> -->
			<DIV>
					<xsl:if test="../header/session/@dictionary">
						<script>
							sessionStorage.setItem('dictionary', '<xsl:value-of select="../url/@payload"/>');
						</script>
					</xsl:if>
				<xsl:call-template name="basic_generate_component" />
			</DIV>
	</xsl:template>


</xsl:stylesheet> 
