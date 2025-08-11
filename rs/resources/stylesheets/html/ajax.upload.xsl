<?xml version="1.0"?>
	<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">	
	<!-- global instructions -->	
	<xsl:strip-space elements="*"/>
	<xsl:template match="message" >	
		<script language="javascript" type="text/javascript">		
			<xsl:if test="@field">infoResultUpload('<xsl:value-of select="@field"/>','<xsl:value-of select="text/."/>','<xsl:value-of select="@progreso"/>')		
			</xsl:if>		
			<xsl:if test="not(@field)">				
				<!-- alert("upload: <xsl:value-of select="text/."/>"); --> 				
				var iframe_name = window.parent.name;				
				var realInput = iframe_name.substring(7); 				
				window.parent.parent.document.getElementById(realInput).value = '<xsl:value-of select="text/."/>';
				window.parent.stopUpload('<xsl:value-of select="text/."/>'==''?0:1);		
			</xsl:if>	
		</script>   	
	</xsl:template>
	</xsl:stylesheet> 