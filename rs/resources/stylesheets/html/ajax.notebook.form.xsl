<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- includes -->
	<xsl:include href="ajax.base.xsl"/>

	<xsl:template match="page/help" />
	<xsl:template match="page/help_form" />
	<xsl:template match="page/publicity_campaign" />
	<xsl:template match="page/view/navigation_bar" />
	<xsl:template match="page/view/objects" />
	<xsl:template match="page/view/history_bar" />
	<xsl:template match="page/message" />

	<!-- build the page structure taking the values which come in the header -->
	<xsl:template match="page/view/notebook">
		<xsl:apply-templates select="panel"/>
	</xsl:template>



</xsl:stylesheet> 
