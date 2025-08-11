<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:strip-space elements="*"/>
	
	<xsl:template name="do_formlov_combo_box_inner_div_responsive">
		<xsl:param name="fullname" select="'null'" />
		<xsl:param name="showkey" select="1" />
		<datalist>
			<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_datalist</xsl:attribute>
			<xsl:attribute name="data-search"><xsl:value-of select="@search_string"/></xsl:attribute>
			<xsl:call-template name="do_formlov_combo_box_options_responsive">
				<xsl:with-param name="fullname"><xsl:value-of select="$fullname"/></xsl:with-param>
				<xsl:with-param name="showkey"><xsl:value-of select="$showkey"/></xsl:with-param>
			</xsl:call-template>
		</datalist>
	</xsl:template>
	
	<xsl:template name="do_formlov_combo_box_inner_div">
		<xsl:param name="specialClass" select="'null'" />
		<xsl:param name="fullname" select="'null'" />
		<SELECT size="10" >
			<xsl:attribute name="style">z-index:99; position:fixed;</xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_combo</xsl:attribute>
			<xsl:attribute name="autofocus">true</xsl:attribute>
			<xsl:attribute name="onKeypress">
				formLovKey('<xsl:value-of select="$fullname"/>',this,event);
			</xsl:attribute>
			<xsl:attribute name="onclick">
				formLovClickSelect('<xsl:value-of select="$fullname"/>',this);
			</xsl:attribute>
			<xsl:attribute name="class">
				<xsl:value-of select="$specialClass"/>
			</xsl:attribute>
			<xsl:call-template name="do_formlov_combo_box_options"/>
		</SELECT>
	</xsl:template>
	
	
	<xsl:template name="do_formlov_combo_box_options">
		<xsl:for-each select="itemCombo">
			<OPTION>
				<xsl:attribute name="value"><xsl:value-of select="@id"/></xsl:attribute>
				<xsl:attribute name="data-value"><xsl:value-of select="@id"/></xsl:attribute>
				<xsl:value-of select="@description"/>
			</OPTION>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template name="do_formlov_combo_box_options_responsive">
		<xsl:param name="fullname" select="'null'" />
		<xsl:param name="showkey" select="0" />
		<xsl:for-each select="itemCombo">
			<xsl:if test="$showkey=1">
				<OPTION>
					<xsl:attribute name="value"><xsl:value-of select="@description"/></xsl:attribute>
					<xsl:attribute name="data-value"><xsl:value-of select="@id"/></xsl:attribute>
					<xsl:value-of select="@id"/>
				</OPTION>
			</xsl:if>
			<xsl:if test="$showkey!=1">
				<OPTION>
					<xsl:attribute name="data-value"><xsl:value-of select="@id"/></xsl:attribute>
					<xsl:value-of select="@description"/>
				</OPTION>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet> 