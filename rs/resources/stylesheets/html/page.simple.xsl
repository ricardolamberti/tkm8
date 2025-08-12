<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="dictionary" select="0"/>
	<xsl:param name="requestid" select="0"/>



	<!-- includes -->
	<xsl:include href="responsive_page.view.xsl"/>

	<!-- take the skin base path, ended without slash; and the page stereotype -->
	<xsl:variable name="page_stereotype" select="page/header/@stereotype" />
	<xsl:variable name="url_prefix" select="page/header/layouts/@url_prefix" />

	<!-- global instructions -->
	<xsl:strip-space elements="*"/>

	<!-- empty templates -->
	<xsl:template match="page/objects"/>
	<xsl:template match="page/message" />
	<xsl:template match="page/view"/>
	<xsl:template match="page/view/navigation_bar" />
	<xsl:template match="page/view/history_bar" />
	<xsl:template match="page/view/panel/win_action_bar" />
	<xsl:template match="panel[starts-with(@name,'filter_pane')]" />
	<xsl:template match="div_responsive[starts-with(@name,'filter_pane')]" />
	<xsl:template match="form_responsive[starts-with(@name,'filter_pane')]" />

	<!-- build the page structure taking the values which come in the header -->
	<xsl:template match="page/header">
		<html>
			<xsl:call-template name="page.header.simple"/>
		
			<body>
			
				<xsl:call-template name="render_page_simple" />
			</body>
		</html>
	</xsl:template>

	<xsl:template name="page.header.simple">
		<head>
	    	<meta name="viewport" content="width=device-width, initial-scale=1"/>
			<link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.min.css"/>
		    <xsl:for-each select="layouts/css">
			    <link rel="stylesheet">
			    	<xsl:attribute name="href"><xsl:value-of select="@source"/></xsl:attribute>
			    </link>
			    <link rel="stylesheet">
			    	<xsl:attribute name="href"><xsl:value-of select="@source_colors"/></xsl:attribute>
			    </link>
		    </xsl:for-each>
		    <link rel="stylesheet" href="vendor/font-awesome/css/all.min.css" type="text/css"/>
	        <link rel="stylesheet" href="vendor/datatables/Responsive-2.2.1/css/responsive.bootstrap.min.css"/>
			<link rel="stylesheet" type="text/css" media="all" href="css/penta.css" />
			<link rel="stylesheet" type="text/css" media="all" href="css/asterplot.css" />
			<link rel="stylesheet" type="text/css" media="all" href="css/nv.d3.css" />
		</head>
	</xsl:template>
	
	
	<!--
	****************************
	  utility templates
	****************************
	-->
	<!-- template to render the page for the incoming page stereotype -->
	<xsl:template name="render_page_simple">

		<!-- CREATE THE OBJECT ARRAY -->
		<xsl:if test="../objects">
			<script>var a=new Array();<xsl:for-each select="../objects/object">a[<xsl:value-of select="@id"/>]='<xsl:value-of select="@resolve_string"/>';</xsl:for-each>document.pssObjectsArr=a;</script>
		</xsl:if>
							
		<div id="wrapper" >
			<xsl:for-each select="../view">
		 		<xsl:call-template name="render_view_area_simple"/>
			</xsl:for-each>
		</div>

	</xsl:template>
	
	<xsl:template name="render_view_area_simple">
		
		<div>
			<xsl:attribute name="id">view_area_and_title</xsl:attribute>
			<xsl:call-template name="render_view_simple" />
		</div>

	</xsl:template>
	
	<xsl:template name="render_view_simple">
		<div id="row">
			<DIV class="main_canvas">
				<xsl:call-template name="basic_generate_component_responsive" />
			</DIV>
		</div>
	</xsl:template>

</xsl:stylesheet> 
