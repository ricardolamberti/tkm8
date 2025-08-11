<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="dictionary" select="0"/>
	<xsl:param name="requestid" select="0"/>


	<!-- includes -->
	<xsl:include href="responsive_page.view.components.xsl"/>

	<!-- global instructions -->
	<xsl:strip-space elements="*"/>

	<!-- empty templates -->
	<xsl:template match="page/objects"/>
	<xsl:template match="page/header" />
	<xsl:template match="page/message" />


</xsl:stylesheet> 
