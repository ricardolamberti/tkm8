<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:param name="dictionary" select="0"/>
	<xsl:param name="requestid" select="0"/>


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
			
			<body class="formlov_viewarea_and_title" id="formlov_viewarea_and_title">
				<xsl:for-each select="../view">
					<xsl:call-template name="render_view"/>
				</xsl:for-each>
				<script>
					formLovSetParentControl('<xsl:value-of select="formLovInfo/@controlId"/>');
					setupZTables();
				</script>
			</body>
		</html>
	</xsl:template>

</xsl:stylesheet> 
