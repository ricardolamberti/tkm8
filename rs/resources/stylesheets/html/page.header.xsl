<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="dictionary" select="0"/>
	<xsl:param name="requestid" select="0"/>


	<!-- build the page structure taking the values which come in the header -->
	<xsl:template name="page.header">
		<head>
			<meta http-equiv="Expires" content="01 Jan 2000" /><!-- no cache -->
			<META http-equiv="Content-Script-Type" content="text/javascript" />
			<link rel="icon" type="image/x-icon">
				<xsl:attribute name="href">/<xsl:value-of
					select="$url_prefix" /> /html/images/favicon.ico </xsl:attribute>
			</link>

			<title>
				<xsl:value-of select="application/@name" />
				<xsl:if test="../view/@title">
					-
					<xsl:value-of select="../view/@title" />
				</xsl:if>
			</title>
			<!-- CSS link -->
			<link rel="stylesheet">
				<xsl:attribute name="href">/<xsl:value-of
					select="$url_prefix" />/resources/styles[<xsl:value-of
					select="session/@id" />].css</xsl:attribute>
			</link>
			<!-- Calender Style -->
			<link rel="stylesheet" type="text/css" media="all" href="css/asterplot.css" />
			<link rel="stylesheet" type="text/css" media="all" href="css/nv.d3.css" />
			<!--link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css" 
				title="green" / -->

			<!-- Scripts -->

			<script src="js/d3.min.js" type="text/javascript" />
			<script src="js/nv.d3.js" type="text/javascript" />
			<script src="js/d3.tip.v0.6.3.js" type="text/javascript" />

			<script src="js/three.min.js" type="text/javascript" />
			<script src="js/three-text2d.js" type="text/javascript" />
			<script src="js/html2canvas.js" type="text/javascript" />
			<script src="js/html2canvas.svg.js" type="text/javascript" />
			<script src="js/dtree.js" type="text/javascript" />

			<script src="js/TrackballControls.js" type="text/javascript" />
			<script src="js/queue.v1.min.js" type="text/javascript" />
			<script src="js/gauge.js" type="text/javascript" />
			<script src="js/topojson.v0.min.js" type="text/javascript" />
			<script src="js/user.agent.js" type="text/javascript" />
			<script src="js/ajax.js" type="text/javascript" />
			<script src="js/utils.js" type="text/javascript" />
			<script src="js/layouts.js" type="text/javascript" />
			<script src="js/form.controls.js" type="text/javascript" />
			<script src="js/form.validation.js" type="text/javascript" />
			<script src="js/calendar.js" type="text/javascript" />
			<script src="js/calendar.setup.js" type="text/javascript" />
			<script src="js/zwinactions.js" type="text/javascript" />
			<script src="js/dateChooser.js" type="text/javascript" />
			<script src="js/editor.js" type="text/javascript" />
			<script src="js/editorlib.js" type="text/javascript" />
			<!-- http://69.65.97.45:8080/astor/ es ABQIAAAAXW9Ge-JkQvsWrHsdEM5ughQR3xk6oBaqc-s0CBYtDQPQbRiSLRTrpcfa-RZAAjYyxycfoirYkJrRkA -->
			<!-- http://127.0.0.1:8080/astor/ es ABQIAAAAXW9Ge-JkQvsWrHsdEM5ughT42aBd2TDQoA4gzG0PdqlIyh6G7hTJV7B6weD_DWCl_ncFmCKF2GuSCg -->
			<xsl:if test="application/@google_map_key!=''">
				<script type="text/javascript">
					<xsl:attribute name="src"><![CDATA[https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&language=es]]></xsl:attribute>
				</script>
			</xsl:if>
			<script src="js/map.js" type="text/javascript" />


			<script type="text/javascript">
				<xsl:attribute name="src">/<xsl:value-of
					select="$url_prefix" />/resources/nls.strings.<xsl:value-of
					select="session/@id" />.js</xsl:attribute>
			</script>
			<!-- include tree js if it is an indoor page -->
			<xsl:if test="$page_stereotype='app_indoors'">
				<script language="JavaScript1.2" type="text/javascript">
					<xsl:attribute name="src">/<xsl:value-of
						select="$url_prefix" />/resources/menu[<xsl:value-of
						select="session/@id" />].js</xsl:attribute>
				</script>
				<script src="js/tree.bar.js" type="text/javascript" />
				<script>
					var scriptLib = '/
					<xsl:value-of select="$url_prefix" />
					/resources/menu[
					<xsl:value-of select="session/@id" />
					].js';
				</script>
			</xsl:if>
			<script language="JavaScript1.2">reloadOnResize(true);</script>
		</head>
	</xsl:template>


</xsl:stylesheet> 
