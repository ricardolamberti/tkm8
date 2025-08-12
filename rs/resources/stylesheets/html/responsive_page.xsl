<?xml version="1.0" encoding="ISO-8859-1"?>
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
	<xsl:template match="objects"/>
	<xsl:template match="view"/>
	<xsl:template match="view/navigation_bar" />
	<xsl:template match="view/navigation_bar_complex" />
	<xsl:template match="view/navigation_group_sidebar" />
	
	<xsl:template match="view/history_bar" />

	<!-- build the page structure taking the values which come in the header -->
	<xsl:template match="page">
		<html>
			<xsl:for-each select="header">
				<xsl:call-template name="page.header"/>
			</xsl:for-each>
			<body>
 				<xsl:attribute name="onLoad">
						setupZTables();
						backListener();
						cssVars();
						$(".se-pre-con").fadeOut("slow");
				</xsl:attribute>

		
 				<xsl:attribute name="onResize">
					renderHelp();
				</xsl:attribute>
				<script>
					setUrlPrefix('<xsl:value-of select="$url_prefix" />') ;
				</script>
				
				<xsl:apply-templates select="*" />
			</body>
		</html>
	</xsl:template>
	<xsl:template match="header">
				<div class="se-pre-con"></div>
				<xsl:call-template name="render_page" />
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
			

			<div id="wrapper"   >

				<xsl:for-each select="../view">
			 		<xsl:call-template name="render_view_area"/>
				</xsl:for-each>
			</div>

	</xsl:template>

	
	<xsl:template match="help/helpbox"/>
	<xsl:template match="help">
		<div id ="help_zone">
			<xsl:for-each select="helpbox">
				<div onclick="hide(this);">
					<xsl:call-template name="basic_generate_composite"/>

					<xsl:if test="@relative">
						<script>
					    	subscribeHelpBox(
							"<xsl:value-of select="@name"/>",
							"<xsl:value-of select="@position"/>",
							"<xsl:value-of select="@relative"/>",
							<xsl:value-of select="@x"/>,
							<xsl:value-of select="@y"/>,
							<xsl:value-of select="@z"/>,
							0
							);
						</script>
					</xsl:if>
				</div>
			</xsl:for-each>	
		</div>
	</xsl:template>
 
	<xsl:template match="publicity_campaign/publicity"/>
	<xsl:template match="publicity_campaign">
		<div id ="publicity_zone">
		<xsl:for-each select="publicity">
			<div>
				<xsl:call-template name="basic_generate_composite"/>
					<script>
				    	subscribeHelpBox(
						"<xsl:value-of select="@name"/>",
						"<xsl:value-of select="@position"/>",
						"<xsl:value-of select="@relative"/>",
						<xsl:value-of select="@x"/>,
						<xsl:value-of select="@y"/>,
						<xsl:value-of select="@z"/>,
						1
						);
					</script>
			</div>
		</xsl:for-each>	
		</div>
	</xsl:template>
 
	

</xsl:stylesheet> 