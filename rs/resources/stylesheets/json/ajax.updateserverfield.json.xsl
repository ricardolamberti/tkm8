<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- global instructions -->
	<xsl:strip-space elements="*"/>

	<xsl:template match="message" >		
		{
	     "results": <xsl:value-of select="@ok" />,
		 "message": "<xsl:value-of select="@response"/>"
		}
	</xsl:template>
	<xsl:template match="wait" >
	</xsl:template>
	
	<xsl:template match="code" >
	</xsl:template>


</xsl:stylesheet> 
