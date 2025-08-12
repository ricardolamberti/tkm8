<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">



	<!-- includes -->
	<xsl:include href="page.header.xsl"/>
	<xsl:include href="page.view.xsl"/>

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

	<!-- build the page structure taking the values which come in the header -->
	<xsl:template match="page/header">
		<html>
			<xsl:call-template name="page.header"/>

			<body style="overflow:hidden; margin: 0px 0px 0px 0px;">
				<xsl:if test="application/@google_map_key!=''">
		  			<xsl:attribute name="onunload">
		  				GUnload()
		  			</xsl:attribute>	
				</xsl:if>
				<xsl:attribute name="onLoad">
					setupBrowser();
					setupLayout('<xsl:value-of select="$page_stereotype" />',<xsl:value-of select="layouts/layout[@id=$page_stereotype]/view[@id='application_bar']/@height"/>,<xsl:value-of select="layouts/layout[@id=$page_stereotype]/view[@id='view_title']/@height"/>,<xsl:value-of select="layouts/layout[@id=$page_stereotype]/view[@id='info_bar']/@height"/>,<xsl:value-of select="layouts/layout[@id=$page_stereotype]/view[@id='tree_area']/@width"/>,'<xsl:value-of select="../view/@hposition"/>','<xsl:value-of select="../view/@vposition"/>',<xsl:value-of select="layouts/layout[@id=$page_stereotype]/view[@id='navigation_bar']/@width"/>,<xsl:value-of select="layouts/layout[@id=$page_stereotype]/view[@id='view_area']/@top"/>);
					locateView();
					setupZTables();
					<!--hideWaitingPane();-->
					<!--setupWorkingPane();-->
				</xsl:attribute>
				<xsl:attribute name="onResize">
					renderHelp();
					locateView();
				</xsl:attribute>
				<xsl:call-template name="render_page" />
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


		<!-- WAITING PANE RENDERING -->		
				
		<!--table id="waiting_pane" class="waiting_pane" style="position:absolute; left:0px;top:0px;bottom:0px;right:0px;width:100%;height:100%; margin: 0px 0px 0px 0px;z-index:20;" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<xsl:for-each select="waiting_pane">
						<xsl:call-template name="basic_generate_composite"/>
					</xsl:for-each>
				</td>
			</tr>
		</table-->
		
		<!-- CONSOLE RENDERING -->
		<xsl:if test="$page_stereotype='app_indoors'">
			<div id="pssMenuBarDIV" class="menu_bar" style="z-index:10;position:absolute;overflow:visible;">
				<!--script language="JavaScript1.2" type="text/javascript"><xsl:attribute name="src">/<xsl:value-of select="$url_prefix"/>/resources/menu[<xsl:value-of select="session/@id"/>].js</xsl:attribute></script-->
				<script src="js/menu.bar.js" type="text/javascript" language="JavaScript1.2" />
			</div>
		</xsl:if>

		
		<div id="logo_bar" class="logo_bar">
				<xsl:attribute name="style">
					<xsl:value-of select="layouts/layout[@id=$page_stereotype]/view[@id='application_bar']/component[@id='logo']/@style" />
				</xsl:attribute>
				<div id="logo" class="logo" >
					<xsl:choose>
						<xsl:when test="layouts/layout[@id=$page_stereotype]/view[@id='application_bar']/component[@id='logo']/icon/@file">
							<xsl:for-each select="layouts/layout[@id=$page_stereotype]/view[@id='application_bar']/component[@id='logo']">
								<xsl:call-template name="render_icon">
									<xsl:with-param name="style_attr">width:100%;height:100%;</xsl:with-param>
								</xsl:call-template>
							</xsl:for-each>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						</xsl:otherwise>
					</xsl:choose>
			</div>
		</div>
		

			<!-- render console application_bar -->
			<!-- height placeholder cell -->
			
			<!-- application bar cell -->
			<div id="application_bar" class="application_bar">
				<xsl:attribute name="style">
					<xsl:value-of select="layouts/layout[@id=$page_stereotype]/view[@id='application_bar']/@style" />
				</xsl:attribute>
				<div id="top_bar" class="top_bar" valign="middle">
					<xsl:attribute name="style">
						<xsl:value-of select="layouts/layout[@id=$page_stereotype]/view[@id='application_bar']/component[@id='top_bar']/@style" />
					</xsl:attribute>
					<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
				</div>
				<div id="end_bar" class="end_bar">
					<xsl:attribute name="style">
						<xsl:value-of select="layouts/layout[@id=$page_stereotype]/view[@id='application_bar']/component[@id='end_bar']/@style" />
					</xsl:attribute>
					<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
				</div>
					
			</div>

			<xsl:if test="layouts/layout[@id=$page_stereotype]/view[@id='tree_area']/@width!=0">
			<div id="tree_area" class="tree_area" >
					<xsl:attribute name="style">
						<xsl:value-of select="layouts/layout[@id=$page_stereotype]/view[@id='tree_area']/@style" />
					</xsl:attribute>
					<xsl:if test="$page_stereotype='app_indoors'">
						<font size="-2"><a href="http://www.treem.net" target="_blank" /></font>
						<div id="tree" class="tree">
							<script language="JavaScript1.2">
								<xsl:attribute name="src">/<xsl:value-of select="$url_prefix"/>/resources/tree[<xsl:value-of select="session/@id"/>].js</xsl:attribute>
							</script>
							<script>initializeDocument();</script>
						</div>
					</xsl:if>
				</div>
			</xsl:if>
							
			<div id="view_area_and_title" class="view_area_and_title" >
					<xsl:attribute name="style">
						<xsl:value-of select="layouts/layout[@id=$page_stereotype]/view[@id='view_area_and_title']/@style" />
					</xsl:attribute>
					<xsl:for-each select="../view">
				 		<xsl:call-template name="render_view_area"/>
					</xsl:for-each>
			</div>

			<!-- render console info_bar -->
			<div id="info_bar" class="info_bar">
					<xsl:attribute name="style">
						<xsl:value-of select="layouts/layout[@id=$page_stereotype]/view[@id='info_bar']/@style" />
					</xsl:attribute>
			
				<xsl:call-template name="render_info_bar"/>
			</div>
	</xsl:template>


	
	<xsl:template match="page/help/helpbox"/>
	<xsl:template match="page/help">
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
 
	<xsl:template match="page/publicity_campaign/publicity"/>
	<xsl:template match="page/publicity_campaign">
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
 
	
	<!-- template to render the page info bar -->
	<xsl:template name="render_info_bar">
		<table style="width:100%; height:100%;" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td id="manufacturer_info" class="manufacturer_info">
					<xsl:choose>
						<xsl:when test="layouts/layout[@id='manufacturer_info']/icon/@file">
							<xsl:for-each select="layouts/layout[@id='manufacturer_info']">
								<xsl:call-template name="render_icon"/>
							</xsl:for-each>
						</xsl:when>
						<xsl:otherwise>
							<!-- to keep space for the cell -->
							<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						</xsl:otherwise>
					</xsl:choose>
				</td>
				<xsl:if test="$page_stereotype='app_indoors'">
					<td id="company_info" class="version_info">
						<xsl:attribute name="width">
							<xsl:value-of select="layouts/layout[@id='company_info']/@width"/>
						</xsl:attribute>
						<xsl:value-of select="session/@company"/><br></br>
					</td>
					<td id="node_info" class="version_info">
						<xsl:attribute name="width">
							<xsl:value-of select="layouts/layout[@id='node_info']/@width"/>
						</xsl:attribute>
						<xsl:value-of select="session/@node"/><br></br>
						<xsl:value-of select="session/@country"/>
					</td>
					<td id="user_info" class="version_info">
						<xsl:attribute name="width">
							<xsl:value-of select="layouts/layout[@id='user_info']/@width"/>
						</xsl:attribute>
						<xsl:value-of select="session/@user"/><br></br>
						<xsl:value-of select="session/@user_name"/>
					</td>
				</xsl:if>
				<td id="version_info" class="version_info">
					<xsl:attribute name="width">
						<xsl:value-of select="layouts/layout[@id='version_info']/@width"/>
					</xsl:attribute>
					<xsl:value-of select="application/@version_info"/><br></br>
					<xsl:value-of select="application/@release_info"/>
				</td>
			</tr>
		</table>

	</xsl:template>

</xsl:stylesheet> 
