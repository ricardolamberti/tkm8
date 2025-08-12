<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>

<xsl:stylesheet version="1.0" xmlns:html="http://www.w3.org/TR/REC-html40"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="urn:schemas-microsoft-com:office:spreadsheet"
	xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel"
	xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet">

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
	<xsl:template match="div[starts-with(@name,'filter_pane')]" />
	<xsl:template match="*/text_field_responsive" />





	<xsl:template name="generate_win_list_cell">
		<xsl:param name="thispos"></xsl:param>
		<xsl:param name="iseje"></xsl:param>
		<xsl:variable name="objtype"><xsl:value-of select="../../../header/column[ position() = $thispos ]/@type"/></xsl:variable>
		<xsl:variable name="halignment"><xsl:value-of select="../../../header/column[ position() = $thispos ]/@halignment"/></xsl:variable>
		<xsl:if test="$iseje">
			<xsl:choose>
			    <xsl:when test="$objtype='JDATE'">
					<xsl:attribute name="ss:StyleID">HEADERROW_DATE</xsl:attribute>
				</xsl:when>
			    <xsl:when test="$objtype='JDATETIME'">
					<xsl:attribute name="ss:StyleID">HEADERROW_DATETIME</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="ss:StyleID">HEADERROW</xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
 		<xsl:if test="not($iseje)">
			<xsl:choose>
			    <xsl:when test="$objtype='JDATE'  and not(normalize-space(@data_xls)='') and not(normalize-space(@data_xls)='0,00') and not(normalize-space(@data_xls)='0.00') ">
					<xsl:attribute name="ss:StyleID">DATA_DATE</xsl:attribute>
				</xsl:when>
			    <xsl:when test="$objtype='JDATETIME'  and not(normalize-space(@data_xls)='') and not(normalize-space(@data_xls)='0,00') and not(normalize-space(@data_xls)='0.00')">
					<xsl:attribute name="ss:StyleID">DATA_DATETIME</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="ss:StyleID">DATA</xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
		<Data>

			<xsl:choose>
			    <xsl:when test="$objtype='JDATE' and not(normalize-space(@data_xls)='') and not(normalize-space(@data_xls)='0,00') and not(normalize-space(@data_xls)='0.00')">
					<xsl:attribute name="ss:Type">DateTime</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
			    <xsl:when test="$objtype='JDATETIME' and not(normalize-space(@data_xls)='') and not(normalize-space(@data_xls)='0,00') and not(normalize-space(@data_xls)='0.00')">
					<xsl:attribute name="ss:Type">DateTime</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
				<xsl:when test="$objtype='JBOOLEAN'">
					<xsl:attribute name="ss:Type">String</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
			    <xsl:when test="$objtype = 'JLONG'">
					<xsl:attribute name="ss:Type">Number</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
			    <xsl:when test="$objtype = 'JINT'">
					<xsl:attribute name="ss:Type">Number</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
			    <xsl:when test="$objtype = 'JUNSIGNED'">
					<xsl:attribute name="ss:Type">Number</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
				<xsl:when test="$objtype='JFLOAT'">
					<xsl:attribute name="ss:Type">Number</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
				<xsl:when test="$objtype='JCURRENCY'">
					<xsl:attribute name="ss:Type">Number</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
				<xsl:when test="$objtype='JINTEGER'">
					<xsl:attribute name="ss:Type">Number</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
				<xsl:when test="$objtype='JIMAGE'">
					<xsl:attribute name="ss:Type">String</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
				<xsl:when test="html_data">
					<xsl:attribute name="ss:Type">String</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="ss:Type">String</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:otherwise>
			</xsl:choose>
		</Data>
	</xsl:template>
	<xsl:template name="generate_win_list_footercell">
		<xsl:param name="thispos"></xsl:param>
		<xsl:variable name="objtype"><xsl:value-of select="../../header/column[ position() = $thispos ]/@type"/></xsl:variable>
		<xsl:variable name="halignment"><xsl:value-of select="../../header/column[ position() = $thispos ]/@halignment"/></xsl:variable>
		
		<xsl:choose>
		    <xsl:when test="$objtype='JDATE'  and not(normalize-space(@data_xls)='')  and not(normalize-space(@data_xls)='0,00') and not(normalize-space(@data_xls)='0.00')" >
				<xsl:attribute name="ss:StyleID">FOOTER_DATE</xsl:attribute>
			</xsl:when>
		    <xsl:when test="$objtype='JDATETIME'  and not(normalize-space(@data_xls)='')  and not(normalize-space(@data_xls)='0,00') and not(normalize-space(@data_xls)='0.00')">
				<xsl:attribute name="ss:StyleID">FOOTER_DATETIME</xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="ss:StyleID">FOOTER</xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
		
		<Data>

			<xsl:choose>
			    <xsl:when test="$objtype='JDATE'  and not(normalize-space(@data_xls)='') and not(normalize-space(@data_xls)='0,00') and not(normalize-space(@data_xls)='0.00')">
					<xsl:attribute name="ss:Type">Date</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
			    <xsl:when test="$objtype='JDATETIME'  and not(normalize-space(@data_xls)='') and not(normalize-space(@data_xls)='0,00') and not(normalize-space(@data_xls)='0.00')">
					<xsl:attribute name="ss:Type">DateTime</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
				<xsl:when test="$objtype='JBOOLEAN'">
					<xsl:attribute name="ss:Type">String</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
			    <xsl:when test="$objtype = 'JLONG'">
					<xsl:attribute name="ss:Type">Number</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
			    <xsl:when test="$objtype = 'JINT'">
					<xsl:attribute name="ss:Type">Number</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
			    <xsl:when test="$objtype = 'JUNSIGNED'">
					<xsl:attribute name="ss:Type">Number</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
				<xsl:when test="$objtype='JFLOAT'">
					<xsl:attribute name="ss:Type">Number</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
				<xsl:when test="$objtype='JCURRENCY'">
					<xsl:attribute name="ss:Type">Number</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
				<xsl:when test="$objtype='JINTEGER'">
					<xsl:attribute name="ss:Type">Number</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
				<xsl:when test="$objtype='JIMAGE'">
					<xsl:attribute name="ss:Type">String</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
				<xsl:when test="html_data">
					<xsl:attribute name="ss:Type">String</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="ss:Type">String</xsl:attribute>
					<xsl:value-of select="@data_xls"/>
				</xsl:otherwise>
			</xsl:choose>
		</Data>
	</xsl:template>
	<xsl:template match="win_list">
		<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
			xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel"
			xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet" xmlns:html="http://www.w3.org/TR/REC-html40">
			<Styles>
				<Style ss:ID="Default" ss:Name="Normal">
					<Alignment ss:Vertical="Top" />
					<Borders />
					<Font />
					<Interior />
					<NumberFormat />
					<Protection />
				</Style>
				<Style ss:ID="HEADER" ss:name="HEADER">
					<Font ss:Size="12" ss:Color="#FFFFFF" ss:Bold="1" />
					<Interior>
						<xsl:choose>
							<xsl:when test="header/column/@background">
								<xsl:attribute name="ss:Color">#<xsl:value-of select="header/column/@background"/></xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="ss:Color">#000080</xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
						<xsl:attribute name="ss:Pattern">Solid</xsl:attribute>
					</Interior>
				</Style>
				<Style ss:ID="HEADER_DATE">
					<xsl:attribute name="ss:parent">HEADER</xsl:attribute>
					<NumberFormat ss:Format="Short Date"/>
				</Style>
				<Style ss:ID="HEADER_DATETIME">
					<xsl:attribute name="ss:parent">HEADER</xsl:attribute>
					<NumberFormat ss:Format="General Date"/>
				</Style>
				<Style ss:ID="HEADERROW" ss:name="HEADERROW">
					<Font ss:Size="12" ss:Color="#FFFFFF" ss:Bold="1" />
					<Interior>
						<xsl:attribute name="ss:Color">#0000CC</xsl:attribute>
						<xsl:attribute name="ss:Pattern">Solid</xsl:attribute>
					</Interior>
				</Style>
				<Style ss:ID="HEADERROW_DATE">
					<xsl:attribute name="ss:parent">HEADERROW</xsl:attribute>
					<NumberFormat ss:Format="Short Date"/>
				</Style>
				<Style ss:ID="HEADERROW_DATETIME">
					<xsl:attribute name="ss:parent">HEADERROW</xsl:attribute>
					<NumberFormat ss:Format="General Date"/>
				</Style>
				
				<Style ss:ID="DATA" ss:name="DATA">
					<Font ss:Size="12" />
				</Style>
				<Style ss:ID="DATA_DATE">
					<xsl:attribute name="ss:parent">DATA</xsl:attribute>
					<Font ss:Size="12" />
					<NumberFormat ss:Format="Short Date"/>
				</Style>
				<Style ss:ID="DATA_DATETIME">
					<xsl:attribute name="ss:parent">DATA</xsl:attribute>
					<Font ss:Size="12" />
					<NumberFormat ss:Format="General Date"/>
				</Style>
				<Style ss:ID="FOOTER" ss:name="FOOTER">
					<Font ss:Size="12" ss:Color="#FFFFFF"  ss:Bold="1" />
					<Interior>
						<xsl:choose>
							<xsl:when test="footer/column/@background">
								<xsl:attribute name="ss:Color">#<xsl:value-of select="header/column/@background"/></xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="ss:Color">#000080</xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
						<xsl:attribute name="ss:Pattern">Solid</xsl:attribute>
					</Interior>
				</Style>
				<Style ss:ID="FOOTER_DATE">
					<xsl:attribute name="ss:parent">FOOTER</xsl:attribute>
					<NumberFormat ss:Format="Short Date"/>
				</Style>
				<Style ss:ID="FOOTER_DATETIME">
					<xsl:attribute name="ss:parent">FOOTER</xsl:attribute>
					<NumberFormat ss:Format="General Date"/>
				</Style>
				
			</Styles>

			<Worksheet ss:Name="Page1">
				<Table>
					<xsl:for-each select="level_column">
						<Row>
							<xsl:for-each select="grupo_column">
								<Cell>
									<xsl:attribute name="ss:StyleID">HEADER</xsl:attribute>
									<xsl:if test="@span &gt; 1">
										<xsl:attribute name="ss:MergeAcross"><xsl:value-of select="(@span)-1"/></xsl:attribute>
									</xsl:if>

									<xsl:choose>
										<xsl:when test="not(title/.='SINGRUPOCOLUMNA')">
												<Data ss:Type="String">
													<xsl:value-of select="title/." />
												</Data>
										</xsl:when>
										<xsl:otherwise>
										</xsl:otherwise>
									</xsl:choose>
								</Cell>
							</xsl:for-each>
						</Row>
					</xsl:for-each>
					<Row>
						<xsl:for-each select="header/column">
							<xsl:if test="not(title/.='')">
								<Cell>
									<xsl:attribute name="ss:StyleID">HEADER</xsl:attribute>
									<xsl:if test="@span &gt; 1">
										<xsl:attribute name="ss:MergeAcross"><xsl:value-of select="(@span)-1"/></xsl:attribute>
									</xsl:if>
									<Data ss:Type="String">
										<xsl:value-of select="title/." />
									</Data>
								</Cell>
							</xsl:if>
						</xsl:for-each>
					</Row>
					<xsl:for-each select="rows/row">
						<Row>
							<xsl:for-each select="d">
								<Cell>
									<xsl:if test="@span &gt; 1">
										<xsl:attribute name="ss:MergeAcross"><xsl:value-of select="(@span)-1"/></xsl:attribute>
									</xsl:if>
									<xsl:call-template name="generate_win_list_cell">
										<xsl:with-param name="iseje"><xsl:value-of select="@iseje"/></xsl:with-param>
										<xsl:with-param name="thispos"><xsl:value-of select="position()"/></xsl:with-param>
									</xsl:call-template>	
								</Cell>
							</xsl:for-each>
						</Row>

						<xsl:for-each select="details">
						<Row ss:StyleID="DATA">
								<Cell>
									<Data ss:Type="String">
										<xsl:text></xsl:text>
									</Data>
								</Cell>
								<xsl:for-each select="header/column">
									<xsl:if test="not(title/.='')">
										<Cell>
											<Data ss:Type="String">
												<xsl:value-of select="title/." />
											</Data>
										</Cell>
									</xsl:if>
								</xsl:for-each>
							</Row>

							<xsl:for-each select="rows/row">
								<Row ss:StyleID="DATA">
									<Cell>
										<Data ss:Type="String">
											<xsl:text></xsl:text>
										</Data>
									</Cell>
									<xsl:for-each select="d">
										<Cell>
											<xsl:if test="@span &gt; 1">
												<xsl:attribute name="ss:MergeAcross"><xsl:value-of select="(@span)-1"/></xsl:attribute>
											</xsl:if>
											<xsl:call-template name="generate_win_list_cell">
												<xsl:with-param name="iseje"><xsl:value-of select="@iseje"/></xsl:with-param>
												<xsl:with-param name="thispos"><xsl:value-of select="position()"/></xsl:with-param>
											</xsl:call-template>	
										</Cell>
									</xsl:for-each>
								</Row>
							</xsl:for-each>
						</xsl:for-each>
					</xsl:for-each>

					<xsl:for-each select="footer">
						<Row>
							<xsl:for-each select="column">
								<Cell>
									<xsl:if test="@span &gt; 1">
										<xsl:attribute name="ss:MergeAcross"><xsl:value-of select="(@span)-1"/></xsl:attribute>
									</xsl:if>
									<xsl:call-template name="generate_win_list_footercell">
										<xsl:with-param name="thispos"><xsl:value-of select="position()"/></xsl:with-param>
									</xsl:call-template>								
								</Cell>
							</xsl:for-each>
						</Row>
					</xsl:for-each>
				</Table>
			</Worksheet>
		</Workbook>

	</xsl:template>

</xsl:stylesheet> 

