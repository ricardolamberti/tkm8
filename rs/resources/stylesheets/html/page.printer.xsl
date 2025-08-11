<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="dictionary" select="0"/>
	<xsl:param name="requestid" select="0"/>



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
	<xsl:template match="page/view/history_bar" />
	<xsl:template match="page/view/panel/win_action_bar" />
	<xsl:template match="panel[starts-with(@name,'filter_pane')]" />
	<xsl:template match="div_responsive[starts-with(@name,'filter_pane')]" />
	<xsl:template match="form_responsive[starts-with(@name,'filter_pane')]" />

	<!-- build the page structure taking the values which come in the header -->
	<xsl:template match="page/header">
		<html>
			<xsl:call-template name="page.header"/>
		<style>
			body:before, body:after {
				content: '';
				position: fixed;
				background: #FFFFFF;
				left: 0;
				right: 0;
				height: 10px;
			}
			body:before {
				top: 0;
			}
			body:after {
				bottom: 0;
			}
			.r90 {
			  display:inline-block;
			  filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=3);
			  -webkit-transform: rotate(270deg);
			  -ms-transform: rotate(270deg);
			  transform: rotate(270deg);
			}
			table{ 
				counter-reset: tablepage;
				-fs-table-paginate: paginate;
				border-spacing: 0;
				border-collapse:collapse;
				-moz-border-radius: 5px; 
				border-radius: 5px; 
			}
			th { counter-increment: tablepage }
			table tr{
			  page-break-inside:avoid;
			}
			body {
				border-left: 0px solid #FFFFFF;
				border-right: 0px solid #FFFFFF;
		  }
			p {
				margin-top: 0px;
				margin-bottom: 0px;
				letter-spacing: -0.05em;
			}
			@page { 
				size: A4; 
				margin-left: 10mm; 
				margin-right: 10mm; 
				margin-top: 15mm; 
				margin-bottom: 10mm; 
				padding: " + getPadding() + "em;
			}
			
			@page land { 
			  size: A4 landscape; 
				margin-left: 10mm; 
				margin-right: 10mm; 
				margin-top: 15mm; 
				margin-bottom: 10mm; 
	    }
			.landscape { 
			   page:land; 
			   page-break-before: always; 
		       width: 257mm; 
		  } 
		  #tablenumber:before {  content: counter(tablepage); } 
		  #pagenumber:before {  content: counter(page); } 
		  #pagecount:before {  content: counter(pages); } 
.panel {
}
.panel-body {
}

.panel-heading {
}

.panel-heading>.dropdown .dropdown-toggle {
}

.panel-title {
}

.panel-title>.small,.panel-title>.small>a,.panel-title>a,.panel-title>small,.panel-title>small>a {
}

.paddingtop {
    padding-top: 0px;
}
.panel-footer {
visibility: hidden;
}
		
		
		</style>

			<body>
				<xsl:call-template name="render_page_simple" />
			</body>
		</html>
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
				<xsl:call-template name="basic_generate_component_responsive">
					<xsl:with-param name="force_width">100%</xsl:with-param>
				</xsl:call-template>
			</DIV>
		</div>
	</xsl:template>

</xsl:stylesheet> 
