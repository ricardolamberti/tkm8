<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:variable name="page_stereotype" select="page/header/@stereotype" />
	<xsl:variable name="url_prefix" select="page/header/layouts/@url_prefix" />

	<xsl:strip-space elements="*" />

	<xsl:template match="/">
		<xsl:apply-templates select="*" />
	</xsl:template>

	<xsl:template match="page/header" />
	<xsl:template match="action" />
<xsl:template match="panel[starts-with(@name,'filter_pane')]" />
<xsl:template match="div_responsive[@name='filter_header']" />
<xsl:template match="div_responsive[@name='filter_body ']" />
<xsl:template match="form_responsive[starts-with(@name,'filter_pane')]" />
	<xsl:template match="*/text_field_responsive" />

	<xsl:template match="win_list">

<html>
 	<head>
 	<TITLE><xsl:value-of select="@title"/></TITLE>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <meta charset="utf-8"/>
    <style>
      html, body, #map-canvas {
        margin: 0;
        padding: 0;
        height: 100%;
      }
    </style>
    
    <script>
    <xsl:attribute name="src"><![CDATA[ https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&language=es]]> </xsl:attribute>
    </script>
	<script type="text/javascript" src="js/map.js"/>
	<script type="text/javascript">
			cleanMapa();
			<xsl:for-each select="rows">
			<xsl:for-each select="row">
			addDir("<xsl:value-of select="@mapdir"/>","<xsl:value-of select="@mapname"/>",iconMapaDefa);
			</xsl:for-each></xsl:for-each>;
			displayMap('map-canvas');
   </script>
  </head>
  <body>
    <a href="javascript:showEtiquetas()">Ver etiquetas</a>
    <div id="map-canvas"></div>
  </body>
</html>


</xsl:template>

</xsl:stylesheet> 