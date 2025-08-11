<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- global instructions -->
	<xsl:strip-space elements="*"/>

	<xsl:template match="message" >
		<xsl:if test="@loginpage">nnnn<xsl:value-of select="@loginpage"/></xsl:if><xsl:if test="@title">tttt<xsl:value-of select="@title"/></xsl:if>zzzz<xsl:value-of select="@type"/>zzzz<xsl:value-of select="text/."/>
	</xsl:template>
	<xsl:template match="wait" >
	    <xsl:value-of select="source/."/>;
	</xsl:template>
	
	<xsl:template match="code" >
		<SCRIPT> <xsl:value-of select="source/."/>;</SCRIPT> 
	</xsl:template>


</xsl:stylesheet> 
