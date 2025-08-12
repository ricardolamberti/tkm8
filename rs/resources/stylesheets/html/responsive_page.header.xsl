<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
              	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<!-- build the page structure taking the values which come in the header -->
	<xsl:param name="dictionary"
         		select="0"/>
	<xsl:param name="requestid"
         		select="0"/>
	<xsl:template name="page.header">
		<head>
			<meta charset="utf-8"/>
			<META http-equiv="Content-Script-Type"
    				content="text/javascript" />
			<meta http-equiv="X-UA-Compatible"
    				content="IE=edge"/>
			<meta name="viewport"
    				content="width=device-width, initial-scale=1"/>
			<meta http-equiv="cache-control"
    				content="no-cache, must-revalidate, post-check=0, pre-check=0" />
			<meta http-equiv="cache-control"
    				content="max-age=0" />
			<meta http-equiv="expires"
    				content="0" />
			<meta http-equiv="expires"
    				content="Tue, 01 Jan 1980 1:00:00 GMT" />
			<meta http-equiv="pragma"
    				content="no-cache" />
			<title>
				<xsl:value-of select="application/@name" />
				<xsl:if test="../view/@title">- <xsl:value-of select="../view/@title" />
				</xsl:if>
			</title>
			<!-- CSS link -->
			<xsl:if test="not(layouts/css/@ignore_oldcss)">
				<link rel="stylesheet">
					<xsl:attribute name="href">/<xsl:value-of select="$url_prefix" />/resources/styles[<xsl:value-of select="session/@id" />].css</xsl:attribute>
				</link>
			</xsl:if>
			<link rel="icon"
    				type="image/x-icon">
				<xsl:attribute name="href">/<xsl:value-of select="$url_prefix" />/html/images/favicon.ico </xsl:attribute>
			</link>
			<link rel="stylesheet">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/bootstrap/css/bootstrap.min.css</xsl:attribute>
			</link>
			<link rel="stylesheet">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/metisMenu/metisMenu.min.css</xsl:attribute>
			</link>
			<link rel="stylesheet"
    				type="text/css">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/font-awesome/css/all.min.css</xsl:attribute>
			</link>
			<link rel="stylesheet">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/bootstrap/css/bootstrap-datetimepicker.css</xsl:attribute>
			</link>
			<link rel="stylesheet">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/datatables/dataTables.min.css</xsl:attribute>
			</link>
			<link rel="stylesheet">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/datatables/DataTables-1.10.16/css/jquery.dataTables.min.css</xsl:attribute>
			</link>
			<link rel="stylesheet">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/datatables/DataTables-1.10.16/css/dataTables.bootstrap.min.css</xsl:attribute>
			</link>
			<link rel="stylesheet">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/datatables/FixedHeader-3.1.3/css/fixedHeader.bootstrap.min.css</xsl:attribute>
			</link>
			<link rel="stylesheet">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/datatables/FixedHeader-3.1.3/css/fixedHeader.dataTables.min.css</xsl:attribute>
			</link>
			<link rel="stylesheet">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/datatables/Responsive-2.2.1/css/responsive.bootstrap.min.css</xsl:attribute>
			</link>
			<link rel="stylesheet">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/datatables/Responsive-2.2.1/css/responsive.dataTables.min.css</xsl:attribute>
			</link>
			<link rel="stylesheet">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/treefy/css/bootstrap-treefy.css</xsl:attribute>
			</link>
			<link rel="stylesheet"
    				type="text/css"
    				media="all">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/css/asterplot.css</xsl:attribute>
			</link>
			<link rel="stylesheet"
    				type="text/css"
    				media="all">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/css/nv.d3.css</xsl:attribute>
			</link>
			<link rel="stylesheet"
    				type="text/css"
    				media="all">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/select2/css/select2.min.css</xsl:attribute>
			</link>
			<link rel="stylesheet"
    				type="text/css"
    				media="all">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/select2/css/select2-bootstrap.css</xsl:attribute>
			</link>
			<link rel="stylesheet"
    				type="text/css"
    				media="all">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/jqueryui/jquery-ui.min.css</xsl:attribute>
			</link>
			<link rel="stylesheet"
    				type="text/css"
    				media="all">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/daterangepicker/daterangepicker.css</xsl:attribute>
			</link>
			<link rel="stylesheet">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/vendor/bootstrap/css/bootstrap-xlgrid.min.css</xsl:attribute>
			</link>
			<!-- Penta Style -->
			<link rel="stylesheet"
    				type="text/css"
    				media="all">
				<xsl:attribute name="href">v_<xsl:value-of select="@version"/>/css/penta.css</xsl:attribute>
			</link>
			<xsl:for-each select="layouts/css">
				<link rel="stylesheet">
					<xsl:attribute name="href">v_<xsl:value-of select="../../@version"/>/<xsl:value-of select="@source"/>
					</xsl:attribute>
				</link>
				<link rel="stylesheet">
					<xsl:attribute name="href">v_<xsl:value-of select="../../@version"/>/<xsl:value-of select="@source_colors"/>
					</xsl:attribute>
				</link>
			</xsl:for-each>
			<script src="https://www.google.com/recaptcha/api.js"
      				async="async"
      				defer="defer"/>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/user.agent.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/ajax.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/utils.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/layouts.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/form.controls.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/form.validation.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/calendar.js</xsl:attribute>
			</script>
			<script type="text/javascript"  >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/calendar.setup.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/zwinactions.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/dateChooser.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/editor.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/editorlib.js</xsl:attribute>
			</script>
			<xsl:if test="application/@google_map_key!=''">
				<script type="text/javascript">
					<xsl:attribute name="src"><![CDATA[https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&language=es]]></xsl:attribute>
				</script>
			</xsl:if>
			<xsl:if test="application/@google_pay">
				<script>const googlepayEnvironment='<xsl:value-of select="application/@google_pay_environment"/>'; const googlepayGateway='<xsl:value-of select="application/@google_pay_gateway"/>'; const googlepayGatewayMerchantId='<xsl:value-of select="application/@google_pay_gatewayMerchantId"/>'; </script>
				<script async="">
					<xsl:attribute name="src"><![CDATA[https://pay.google.com/gp/p/js/pay.js]]></xsl:attribute>
				</script>
				<script type="text/javascript">
					<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/googlepay.js</xsl:attribute>
				</script>
			</xsl:if>
			<xsl:if test="application/@mercadopago_pay">
				<script async="">
					<xsl:attribute name="src"><![CDATA[https://sdk.mercadopago.com/js/v2]]></xsl:attribute>
					<xsl:attribute name="onload">onMercadoPagoPayLoaded('<xsl:value-of select="application/@mercadopago_pay_publickey"/>');</xsl:attribute>
				</script>
				<script type="text/javascript">
					<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/mercadopago.js</xsl:attribute>
				</script>
			</xsl:if>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/map.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">/<xsl:value-of select="$url_prefix" />/resources/nls.strings.<xsl:value-of select="session/@id" />.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/jquery/jquery.min.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/jquery/jquery-resizable.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/bootstrap/js/bootstrap.min.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/metisMenu/metisMenu.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/raphael/raphael.min.js</xsl:attribute>
			</script>
			<xsl:for-each select="layouts/js">
				<script rel="stylesheet">
					<xsl:attribute name="src">v_<xsl:value-of select="../../@version"/>/<xsl:value-of select="@source"/>
					</xsl:attribute>
				</script>
			</xsl:for-each>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/moment/moment-with-locale.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/bootstrap/js/bootstrap-datetimepicker.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/daterangepicker/daterangepicker.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/datatables/datatables.min.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/datatables/DataTables-1.10.16/js/jquery.dataTables.min.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/datatables/DataTables-1.10.16/js/dataTables.bootstrap.min.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/datatables/FixedHeader-3.1.3/js/dataTables.fixedHeader.min.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/datatables/Responsive-2.2.1/js/dataTables.responsive.min.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/datatables/Responsive-2.2.1/js/responsive.bootstrap.min.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/treefy/bootstrap-treefy.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/utilsjquery.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/d3/d3.min.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/d3/d3.v5.min.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/d3/nv.d3.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/d3/d3.tip.v0.6.3.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/three.min.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/three-text2d.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/html2canvas.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/html2canvas.svg.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/dtree.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/TrackballControls.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/queue.v1.min.js</xsl:attribute>
			</script>
			<script type="text/javascript" >
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/topojson.v0.min.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/gauge.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/js/gaugev5.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/select2/js/select2.full.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/select2/js/anchor.min.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/jqueryui/jquery-ui.min.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/jqueryui/jquery.stickytableheaders.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/multiselect/multiselect.js</xsl:attribute>
			</script>
			<script type="text/javascript">
				<xsl:attribute name="src">v_<xsl:value-of select="@version"/>/vendor/css-vars-ponyfill/css-vars-ponyfill.min.js</xsl:attribute>
			</script>
			<style>.se-pre-con { position: fixed; left: 0px; top: 0px; width: 100%; height: 100%; z-index: 9999; background: url(html/images/preloader_2.gif) center no-repeat #fff; } </style>
		</head>
	</xsl:template>
</xsl:stylesheet>