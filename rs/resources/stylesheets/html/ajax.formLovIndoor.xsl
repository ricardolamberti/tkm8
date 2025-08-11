<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


	<!-- includes -->
	<xsl:include href="page.header.xsl"/>
	<xsl:include href="page.view.xsl"/>

	<xsl:variable name="page_stereotype" select="page/header/@stereotype" />
	<xsl:variable name="url_prefix" select="page/header/layouts/@url_prefix" />

	<!-- global instructions -->
	<xsl:strip-space elements="*"/>

	<!-- empty templates -->
	<xsl:template match="page/objects"/>
	<xsl:template match="page/message" />
	<xsl:template match="page/view"/>
	<xsl:template match="page/help" />
	<xsl:template match="page/help_form" />
	<xsl:template match="page/publicity_campaign" />
	<xsl:template match="page/view/navigation_bar" />
	<xsl:template match="page/view/history_bar" />

	<!-- build the page structure taking the values which come in the header -->
	<xsl:template match="page/header">
		<html>
			<xsl:call-template name="page.header"/>
			
			<body style="background-color:transparent;" id="formlov_viewarea_and_title">
				<table  width="905">
					<tr id="moveTitle" class="window_formlov_title" onmousedown="startDrag(parent.document.getElementById('virtual_windows'),event,true);">
						<td>
							<table     cellpadding="0" cellspacing="0">
								<tr >
									<td width="100%" >
										<xsl:choose>
											<xsl:when test="../view/@title">
												<xsl:for-each select="../view">
													<div id="view_title" class="window_formlov_title">
														<span>
														<xsl:if test="icon">
															<xsl:call-template name="render_icon" />
															<div style="display:inline;width:5px;" />
														</xsl:if>
														<xsl:value-of select="@title" />
														</span>
													</div>
												</xsl:for-each>
											</xsl:when>
											<xsl:otherwise>
												<div id="view_title" class="window_formlov_titlexx">
													<span>
													Seleccione su opción											
													</span>
												</div>
											</xsl:otherwise>
										</xsl:choose>							
									</td>
									<td width="100%" > 
										<script>
											function CloseKey() {
												parent.document.getElementById("virtual_windows").style.display="none";
											}
										</script>
										<a href="#" name="Close" class="window_formlov_close" onclick="CloseKey(); return false;"><span><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></span></a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr width="100%" height="100%" >
						<td  width="100%" valign="top">
							<div class="window_formlov" id="window_formlov">
								<xsl:for-each select="../view">
									<xsl:call-template name="render_view"/>
								</xsl:for-each>
								<script>
									formLovSetParentControl('<xsl:value-of select="formLovInfo/@controlId"/>');
									setupZTables();
								</script>
							</div>
						</td>
					</tr>
				</table>
			</body>
		</html>
	</xsl:template>

</xsl:stylesheet> 
