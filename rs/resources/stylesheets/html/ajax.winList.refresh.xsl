<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- includes -->
	<xsl:include href="ajax.base.xsl"/>

	<xsl:template match="page/help" />
	<xsl:template match="page/help_form" />
	<xsl:template match="page/publicity_campaign" />

	<xsl:template match="page/view/navigation_bar" />
	<xsl:template match="page/view/history_bar" />
	
	<xsl:template match="page/view/objects" />
	<xsl:template match="page/view/icon" />
	<xsl:template match="page/message" />
	
	<!--xsl:template match="panel[@name='filter_pane']" /-->


	<xsl:template match="page/view" >
		<script type="POSTSCRIPT">
		 firstFocus('<xsl:value-of select="@scroll"/>');
		 analizeTable('<xsl:value-of select="@scroll"/>');
		 analizeModal('<xsl:value-of select="@scroll"/>');
							sessionStorage.setItem('dictionary', '<xsl:value-of select="../url/@payload"/>');
		</script>
		<xsl:for-each select="*">
			<xsl:apply-templates select="*"/>
		</xsl:for-each>

	</xsl:template>
	
</xsl:stylesheet> 
