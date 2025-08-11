<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:strip-space elements="*"/>
<xsl:output method="text" omit-xml-declaration="yes" />

		
	
		<xsl:template match="channel">
	  	{
			 <xsl:if test="@channel">"channel":"<xsl:value-of select="@channel"/>"</xsl:if>
			 }
		</xsl:template>	
		
		<xsl:template match="messages">
		{
		   "messages": [
				<xsl:for-each select="message">
				    {
				    	"type": "<xsl:value-of select="@type"/>",
				    	"title": "<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@title"/></xsl:with-param></xsl:call-template>",
				    	"info": "<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@info"/></xsl:with-param></xsl:call-template>",
				    	"image": "<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@image"/></xsl:with-param></xsl:call-template>",
				    	"link": "<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@link"/></xsl:with-param></xsl:call-template>"
				    }<xsl:if test="position()!=last()">,</xsl:if>
				</xsl:for-each>
			]
			
		}
	</xsl:template>
		<xsl:template name="escapeQuote">
	  <xsl:param name="pText" select="."/>
	  <xsl:if test="string-length($pText) >0">
	   <xsl:value-of select="substring-before(concat($pText, '&quot;'), '&quot;')"/>
	   <xsl:if test="contains($pText, '&quot;')">
	    <xsl:text>\"</xsl:text>
	    <xsl:call-template name="escapeQuote">
	      <xsl:with-param name="pText" select="substring-after($pText, '&quot;')"/>
	    </xsl:call-template>
	   </xsl:if>
	  </xsl:if>
	</xsl:template>
</xsl:stylesheet>
