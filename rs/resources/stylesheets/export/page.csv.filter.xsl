<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:strip-space elements="*"/>

<xsl:template match="/">
	<xsl:apply-templates select="*"/>
</xsl:template>

<xsl:template match="page/header" />
<xsl:template match="action" />
<xsl:template match="panel[@name='filter_pane']" />
<xsl:template match="panel[starts-with(@name,'filter_pane')]" />
<xsl:template match="div_responsive[@name='filter_header']" />
<xsl:template match="div_responsive[@name='filter_body ']" />
<xsl:template match="form_responsive[starts-with(@name,'filter_pane')]" />
	<xsl:template match="*/text_field_responsive" />

 <xsl:template name="addSeparadores">
      <xsl:param name="pStart"/>
      <xsl:param name="pEnd"/>
      <xsl:text>;</xsl:text>
      <xsl:if test="$pStart &lt; $pEnd">
          <xsl:call-template name="addSeparadores">
           <xsl:with-param name="pStart" select="$pStart+1"/>
           <xsl:with-param name="pEnd" select="$pEnd"/>
          </xsl:call-template>
      </xsl:if>
     </xsl:template>

<xsl:template match="win_list">
    <xsl:variable name="separator" select="'&#59;'" />
    <xsl:variable name="newline" select="'&#10;'" />
	<xsl:for-each select="level_column">
			<xsl:text>G1;</xsl:text>
			<xsl:for-each select="grupo_column">
				<xsl:choose>
					<xsl:when test="not(title/.='SINGRUPOCOLUMNA')">
						<xsl:value-of select="title/."/>
					</xsl:when>
					<xsl:otherwise>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@span &gt; 1">
					<xsl:call-template name="addSeparadores">
				        <xsl:with-param name="pStart" select="2"/>
				        <xsl:with-param name="pEnd" select="@span"/>
			        </xsl:call-template>
		        </xsl:if>

				<xsl:if test="not(position()=last())">
					<xsl:text>;</xsl:text>
				</xsl:if>

			</xsl:for-each>
			<xsl:value-of select="$newline" />
	</xsl:for-each>

	<xsl:text>H1;</xsl:text>
	<xsl:for-each select="header/column">
		<xsl:if test="not(title/.='')">
			<xsl:value-of select="title/."/>
			<xsl:if test="not(position()=last())">
				<xsl:text>;</xsl:text>
			</xsl:if>
		</xsl:if>
	</xsl:for-each>
	<xsl:value-of select="$newline" />

	<xsl:for-each select="rows/row">
		<xsl:text>D1;</xsl:text>
		<xsl:for-each select="d">
			<xsl:choose>
				<xsl:when test=".=''">
					<xsl:text> </xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="@data"/>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:if test="not(position()=last())">
				<xsl:text>;</xsl:text>
			</xsl:if>
		</xsl:for-each>
		<xsl:value-of select="$newline" />
		<xsl:for-each select="details">
			<xsl:text>H2;</xsl:text>
			<xsl:for-each select="header/column">
				<xsl:if test="not(title/.='')">
					<xsl:value-of select="title/."/>
					<xsl:if test="not(position()=last())">
						<xsl:text>;</xsl:text>
					</xsl:if>
				</xsl:if>
			</xsl:for-each>
			<xsl:value-of select="$newline" />
			<xsl:for-each select="rows/row">
					<xsl:text>D2;</xsl:text>
					<xsl:for-each select="d">
						<xsl:choose>
							<xsl:when test=".=''">
								<xsl:text> </xsl:text>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="@data"/>
							</xsl:otherwise>
						</xsl:choose>
						<xsl:if test="not(position()=last())">
							<xsl:text>;</xsl:text>
						</xsl:if>
					</xsl:for-each>
					<xsl:value-of select="$newline" />
			</xsl:for-each>
		</xsl:for-each>
	</xsl:for-each>

	<xsl:for-each select="footer">
			<xsl:for-each select="column">
				<xsl:choose>
					<xsl:when test=".=''">
						<xsl:text> </xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="@data_xls"/>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="not(position()=last())">
					<xsl:text>;</xsl:text>
				</xsl:if>
			</xsl:for-each>
			<xsl:value-of select="$newline" />
		</xsl:for-each>
</xsl:template>

</xsl:stylesheet> 