<?xml version="1.0"?>

<!--
	****************************************************************
		UTILITY TEMPLATES TO RENDER COMPONENT PARTS
	****************************************************************
-->


<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:strip-space elements="*"/>

<!-- 
	renders a label which may contain text and icon
 -->
<xsl:template name="render_3d">
	<xsl:value-of select="image/@file" disable-output-escaping="yes"/>
  	
</xsl:template>

</xsl:stylesheet> 