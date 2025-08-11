<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="page/header">
		<html>
			<body>
			<xsl:attribute name="style">
				margin: 0px 0px 0px 0px;
				overflow: hidden;background-color:transparent;* {margin: 0;padding: 0;}
				table, iframe {border-collapse: collapse;border-spacing: 0;}
				fieldset, img, iframe {border: 0;}
			</xsl:attribute>
			<!-- iframe src='login' width='100%' height='100%' margin='0' padding='0' border='0' alscrolling='no' frameborder='0' allowTransparency='true' style='background-color: transparent;overflow: hidden'>
				Su navegador no acepta iframes.<a href="login">Clickee aqui</a>
			</iframe-->
			<script>
			window.open('login','SITI','scrollbars=yes,resizable=yes,location=no,directories=no,copyhistory=no,toolbar=no,menubar=no,fullscreen=yes,channelmode=yes');
			</script> 
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet> 
