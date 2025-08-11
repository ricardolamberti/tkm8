<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:param name="basedir" select="''" />

	<xsl:variable name="skin_path" select="page/header/layouts/@base_path" />
	<xsl:variable name="page_stereotype" select="page/header/@stereotype" />
	<xsl:variable name="url_prefix" select="page/header/layouts/@url_prefix" />

	<xsl:strip-space elements="*" />

	<xsl:template match="/">
		<xsl:apply-templates select="*" />
	</xsl:template>

	<xsl:template match="page/header" />
	<xsl:template match="action" />

	<xsl:template match="win_list">
		<fo:root font-family="TimesNewRoman">
			<fo:layout-master-set>

				<fo:simple-page-master master-name="leftPage"
					margin-top="35.4pt" margin-bottom="35.4pt" margin-right="85.05pt"
					margin-left="85.05pt">
					<xsl:attribute name="page-width"><xsl:value-of
						select="@pageWidth" /></xsl:attribute>
					<xsl:attribute name="page-height"><xsl:value-of
						select="@pageHeight" /></xsl:attribute>
					<fo:region-before extent="2.5cm" />
					<fo:region-after extent="2cm" />
					<fo:region-body margin-top="2.6cm" margin-bottom="2.1cm" />
				</fo:simple-page-master>
				<fo:simple-page-master master-name="rightPage"
					margin-top="35.4pt" margin-bottom="35.4pt" margin-right="85.05pt"
					margin-left="85.05pt">
					<xsl:attribute name="page-width"><xsl:value-of
						select="@pageWidth" /></xsl:attribute>
					<xsl:attribute name="page-height"><xsl:value-of
						select="@pageHeight" /></xsl:attribute>
					<fo:region-before extent="2.5cm" />
					<fo:region-after extent="2cm" />
					<fo:region-body margin-top="2.6cm" margin-bottom="2.1cm" />
				</fo:simple-page-master>

				<fo:page-sequence-master master-name="contents">
					<fo:repeatable-page-master-alternatives>
						<fo:conditional-page-master-reference
							master-reference="leftPage" odd-or-even="even" />
						<fo:conditional-page-master-reference
							master-reference="rightPage" odd-or-even="odd" />
					</fo:repeatable-page-master-alternatives>
				</fo:page-sequence-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="contents">
				<fo:static-content flow-name="xsl-region-before">
					<xsl:call-template name="generate_header" />
				</fo:static-content>

				<fo:static-content flow-name="xsl-region-after">
					<xsl:call-template name="generate_line" />
					<xsl:call-template name="generate_footer" />
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">
					<xsl:call-template name="generate_body" />
					<fo:block id="IDACIMCC" />
				</fo:flow>
			</fo:page-sequence>
		</fo:root>

	</xsl:template>


	<xsl:template name="generate_win_list_cell">
		<xsl:param name="thispos" />
		<xsl:variable name="objtype">
			<xsl:value-of select="../../../header/column[ position()-1 = $thispos ]/@type" />
		</xsl:variable>
		<xsl:variable name="halignment">
			<xsl:value-of
				select="../../../header/column[ position()-1 = $thispos ]/@halignment" />
		</xsl:variable>
		<xsl:attribute name="text-align"><xsl:value-of select="$halignment" />
		</xsl:attribute>
		<xsl:choose>
			<xsl:when test="$objtype='JBOOLEAN'">
				<xsl:choose>
				<xsl:when test=".='S'">
						<fo:inline>
							<fo:external-graphic>
								<xsl:attribute name="src">url('<xsl:value-of
									select="$basedir" />/<xsl:value-of select="$url_prefix" />/<xsl:value-of
									select="$skin_path" /><xsl:value-of
									select="@image_true" />')</xsl:attribute>
							</fo:external-graphic>
						</fo:inline>
					</xsl:when>
					<xsl:when test=".='N' and not(@image_false='')">
						<fo:inline>
							<fo:external-graphic>
								<xsl:attribute name="src">url('<xsl:value-of
									select="$basedir" />/<xsl:value-of select="$url_prefix" />/<xsl:value-of
									select="$skin_path" /><xsl:value-of
									select="@image_false" />')</xsl:attribute>
							</fo:external-graphic>
						</fo:inline>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text disable-output-escaping="yes"> </xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test=".=''">
						<xsl:text disable-output-escaping="yes"> </xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="." />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="generate_icon">
		<xsl:if test="not(contains(icon/@file, '.png' ))">
			<fo:external-graphic>
				<xsl:choose>
					<xsl:when test="icon/@source='pss_icon'">
						<xsl:attribute name="src">url('<xsl:value-of
							select="$basedir" />/<xsl:value-of select="$url_prefix" />/pss_icon/<xsl:value-of
							select="icon/@file" />')</xsl:attribute>
					</xsl:when>
					<xsl:when test="icon/@source='webapp_url'">
						<xsl:attribute name="src">url('<xsl:value-of
							select="$basedir" />/<xsl:value-of select="$url_prefix" />/<xsl:value-of
							select="icon/@file" />')</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="src">url('<xsl:value-of
							select="$basedir" />/<xsl:value-of select="$url_prefix" />/<xsl:value-of
							select="$skin_path" />/<xsl:value-of select="icon/@file" />')</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
			</fo:external-graphic>
		</xsl:if>
	</xsl:template>

	<xsl:template name="generate_footer">
		<fo:table font-family="TimesNewRoman" start-indent="0pt"
			border-top-style="none" border-top-color="black" border-top-width="0pt"
			border-left-style="none" border-left-color="black" border-left-width="0pt"
			border-bottom-style="none" border-bottom-color="black"
			border-bottom-width="0pt" border-right-style="none"
			border-right-color="black" border-right-width="0pt">
			<fo:table-column column-number="1" column-width="216.1pt" />
			<fo:table-column column-number="2" column-width="216.1pt" />
			<fo:table-body start-indent="0pt" end-indent="0pt">
				<fo:table-row>
					<fo:table-cell padding-top="0pt" padding-left="5.4pt"
						padding-bottom="0pt" padding-right="5.4pt" border-left-style="none"
						border-right-style="none" border-left-color="black"
						border-right-color="black" border-left-width="0pt"
						border-right-width="0pt" border-top-style="none"
						border-bottom-style="none" border-top-color="black"
						border-bottom-color="black" border-top-width="0pt"
						border-bottom-width="0pt">
						<fo:block font-family="TimesNewRoman" font-size="12pt"
							language="ES">
							<fo:inline>
								<fo:leader leader-length="0pt" />
								<fo:page-number />
							</fo:inline>
							<fo:inline>
								<fo:leader leader-length="0pt" />
								<xsl:text disable-output-escaping="yes"><![CDATA[ de]]></xsl:text>
							</fo:inline>
							<fo:inline>
								<fo:leader leader-length="0pt" />
								<fo:page-number-citation ref-id="IDACIMCC" />
							</fo:inline>
						</fo:block>
					</fo:table-cell>
					<fo:table-cell padding-top="0pt" padding-left="5.4pt"
						padding-bottom="0pt" padding-right="5.4pt" border-left-style="none"
						border-right-style="none" border-left-color="black"
						border-right-color="black" border-left-width="0pt"
						border-right-width="0pt" border-top-style="none"
						border-bottom-style="none" border-top-color="black"
						border-bottom-color="black" border-top-width="0pt"
						border-bottom-width="0pt">
						<fo:block font-family="TimesNewRoman" font-size="12pt"
							language="ES" text-align="right">
							<fo:inline>
								<fo:leader leader-length="0pt" />
								<xsl:value-of select="header/@datetime" />
							</fo:inline>
						</fo:block>
						<fo:block font-family="TimesNewRoman" font-size="12pt"
							language="ES" text-align="right">
							<fo:leader />
						</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-body>
		</fo:table>
		<fo:block font-family="TimesNewRoman" font-size="12pt"
			language="ES" text-align="right">
			<fo:leader />
		</fo:block>
	</xsl:template>

	<xsl:template name="generate_header">
		<fo:block>
			<fo:table width="100%">
				<fo:table-column>titulo</fo:table-column>
				<fo:table-column column-width="90px">
					imagen
				</fo:table-column>
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block font-size="22pt">
								<xsl:value-of select="@title" />
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block border="1px black solid">
								<xsl:choose>
									<xsl:when
										test="/page/header/layouts/layout[@id=$page_stereotype]/view[@id='application_bar']/component[@id='logo']/icon/@file">
										<xsl:for-each
											select="/page/header/layouts/layout[@id=$page_stereotype]/view[@id='application_bar']/component[@id='logo']">

											<xsl:call-template name="generate_icon" />

										</xsl:for-each>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text disable-output-escaping="yes">
														</xsl:text>
									</xsl:otherwise>
								</xsl:choose>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block font-family="TimesNewRoman" font-size="6pt"
			language="ES">
			<xsl:value-of select="header/@filters" />
		</fo:block>
	</xsl:template>

	<xsl:template name="generate_line">
		<fo:block>
			<fo:leader leader-pattern="rule" leader-length="100%"
				rule-thickness="0.5pt" rule-style="solid" color="gray" />
		</fo:block>
	</xsl:template>


	<xsl:template name="generate_body">
		<fo:table font-size="7pt" border="1px gray solid">
			<xsl:for-each select="header/column">
				<xsl:if test="not(title/.='')">
					<fo:table-column>
						<xsl:choose>
							<xsl:when test="@type='JBOOLEAN'">
								<xsl:attribute name="column-width">10px</xsl:attribute>
							</xsl:when>
							<xsl:when test="not(title/.='')">
								<xsl:if test="@width">
								<xsl:attribute name="column-width"><xsl:value-of select="@width"/>px</xsl:attribute>
									
								</xsl:if>							
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="column-width">10px</xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
						<xsl:attribute name="column-label">
											<xsl:choose>
												<xsl:when test="not(title/.='')">
													<xsl:value-of select="title/." />
												</xsl:when>
												<xsl:otherwise>-</xsl:otherwise>
											</xsl:choose>
										</xsl:attribute>
					</fo:table-column>
				</xsl:if>
			</xsl:for-each>
			<fo:table-header>
				<fo:table-row background-color="blue" color="white" >
					<xsl:for-each select="header/column">
						<xsl:if test="not(title/.='')">
							<fo:table-cell>
								<xsl:choose>
									<xsl:when test="not(title/.='')">
										<xsl:attribute name="padding-right">1mm</xsl:attribute>
										<xsl:attribute name="padding-top">1mm</xsl:attribute>
									</xsl:when>
									<xsl:otherwise>
										<xsl:attribute name="padding-right">.5mm</xsl:attribute>
									</xsl:otherwise>
								</xsl:choose>
									<fo:block font-size="8pt">
										<xsl:attribute name="text-align">
											<xsl:value-of select="@halignment" />
									 	</xsl:attribute>
										<xsl:choose>
											<xsl:when test="not(title/.='')">
												<xsl:value-of select="title/." />
											</xsl:when>
											<xsl:otherwise>
												-
											</xsl:otherwise>
										</xsl:choose>
									</fo:block>
							</fo:table-cell>
						</xsl:if>
					</xsl:for-each>
				</fo:table-row>
			</fo:table-header>
			<fo:table-body>
				<xsl:for-each select="rows/row">
					<fo:table-row>
						<xsl:if test="position() mod 2 = 0">
							<xsl:attribute name="background-color">silver</xsl:attribute>
						</xsl:if>
						<xsl:if test="position() mod 2 = 1">
							<xsl:attribute name="background-color">white</xsl:attribute>
						</xsl:if>
						<xsl:if test="icon">
							<xsl:variable name="halignment">
								<xsl:value-of select="../../header/column[0]/@halignment" />
							</xsl:variable>
							<fo:table-cell padding-right=".5mm">
								<fo:block>
									<xsl:attribute name="text-align">
										<xsl:value-of select="$halignment" />
									</xsl:attribute>
									<xsl:call-template name="generate_icon" />
								</fo:block>
							</fo:table-cell>
						</xsl:if>
						<xsl:for-each select="d">
							<xsl:if test="not(@skip) or not(@skip='true')">
								<fo:table-cell padding-right="1mm" padding-top="1mm">
									<fo:block>
										<xsl:attribute name="text-altitude">5px</xsl:attribute>
										<xsl:variable name="thispos" />
										<xsl:choose>
											<xsl:when test="../icon">
												<xsl:call-template name="generate_win_list_cell">
													<xsl:with-param name="thispos">
														<xsl:value-of select="position()+1" />
													</xsl:with-param>
												</xsl:call-template>
											</xsl:when>
											<xsl:otherwise>
												<xsl:call-template name="generate_win_list_cell">
													<xsl:with-param name="thispos">
														<xsl:value-of select="position()" />
													</xsl:with-param>
												</xsl:call-template>
											</xsl:otherwise>
										</xsl:choose>
									</fo:block>
								</fo:table-cell>
							</xsl:if>
						</xsl:for-each>
					</fo:table-row>
				</xsl:for-each>
				<xsl:if test="footer">
					<!-- fo:table-footer -->
					<fo:table-row background-color="blue" color="white">
						<xsl:for-each select="footer/column">
							<xsl:variable name="halignment">
								<xsl:value-of select="../../header/column[0]/@halignment" />
							</xsl:variable>
							<fo:table-cell padding-right="1mm" padding-top="1mm">
								<fo:block>
									<xsl:attribute name="text-align">
										<xsl:value-of select="$halignment" />
									</xsl:attribute>
									<xsl:choose>
										<xsl:when test="not(value/.='')">
											<xsl:value-of select="value/." />
										</xsl:when>
										<xsl:otherwise>
											<xsl:text disable-output-escaping="yes">  </xsl:text>
										</xsl:otherwise>
									</xsl:choose>
								</fo:block>
							</fo:table-cell>
						</xsl:for-each>
					</fo:table-row>
					<!--/fo:table-footer-->
				</xsl:if>
			</fo:table-body>
		</fo:table>
	</xsl:template>
</xsl:stylesheet> 