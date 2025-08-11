<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


	<!-- includes -->
	<xsl:include href="responsive_page.header.xsl"/>
	<xsl:include href="responsive_page.view.xsl"/>

	<!-- take the skin base path, ended without slash; and the page stereotype -->
	<xsl:variable name="page_stereotype" select="page/header/@stereotype" />
	<xsl:variable name="url_prefix" select="page/header/layouts/@url_prefix" />

	<!-- global instructions -->
	<xsl:strip-space elements="*"/>

	<!-- empty templates -->
	<xsl:template match="page/objects"/>
	<xsl:template match="page/view"/>
	<xsl:template match="page/view/navigation_bar" />
	<xsl:template match="page/view/navigation_bar_complex" />
	<xsl:template match="page/view/navigation_group_sidebar" />
	
	<xsl:template match="page/view/history_bar" />

	<!-- build the page structure taking the values which come in the header -->
	<xsl:template match="page/header">
		<html>
			<xsl:call-template name="page.header"/>
			
			<body>

 				<xsl:attribute name="onLoad">
					setupZTables();
				</xsl:attribute>
				<xsl:attribute name="onResize">
					renderHelp();
				</xsl:attribute>
				<xsl:call-template name="render_page" />
			  
			  	
			    <script src="vendor/jquery/jquery.min.js"></script>
			    <script src="vendor/jquery/jquery-resizable.js"></script>
			    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
			    <script src="vendor/metisMenu/metisMenu.js"></script>
			    <xsl:for-each select="layouts/js">
				    <script rel="stylesheet">
				    	<xsl:attribute name="src"><xsl:value-of select="@source"/></xsl:attribute>
				    </script>
			    </xsl:for-each>
				
				<script src="js/queue.v1.min.js" type="text/javascript" />
			</body>
			
		</html>
	</xsl:template>
	
	
	
	<!--
	****************************
	  utility templates
	****************************
	-->
	<!-- template to render the page for the incoming page stereotype -->
	<xsl:template name="render_page">
	
			<!-- CREATE THE OBJECT ARRAY -->
			<xsl:if test="../objects">
				<script>var a=new Array();<xsl:for-each select="../objects/object">a[<xsl:value-of select="@id"/>]='<xsl:value-of select="@resolve_string"/>';</xsl:for-each>document.pssObjectsArr=a;</script>
			</xsl:if>
			

			<div id="wrapper" >
				<xsl:for-each select="../view">
			 		<xsl:call-template name="render_view_area"/>
				</xsl:for-each>
			</div>

	</xsl:template>

</xsl:stylesheet> 