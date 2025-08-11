<?xml version="1.0"?>

<!-- <skin> <session id="a session id" /> (only if there is a current session) 
	</layout> <styles> (taken from the skin XML file) </styles> </skin> -->

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- take the skin base path, ended without slash -->
	<xsl:variable name="skin_path" select="skin/@base_path" />
	<xsl:variable name="url_prefix" select="skin/@url_prefix" />

	<xsl:template match="skin">
<!-- 
		option.selecthr { border-bottom: 1px dotted #000; }


		.clear {
		overflow:
		hidden;
		width: 100%;
		}
		* {
		margin: 0;
		padding: 0;
		}

		h1, h2, h3, h4, h5, h6 {
		font-size: 100%;
		}



		ol, ul {
		list-style: none;
		}

		address, caption, cite,
		code, dfn, em, strong, th, var {
		font-style: normal;
		font-weight:
		normal;
		}

		table {
		border-collapse: collapse;
		border-spacing: 0;
		}
		thead {
		position: -webkit-sticky;
		position: -moz-sticky;
		position: -ms-sticky;
		position: -o-sticky;
		position: sticky;
		top: 0px;
		}
		tbody {
		display:block;
		display:table-row-group;
		}
		fieldset, img {
		border: 0;
		}

		caption, th, td {
		text-align: left;
		}

		th, td {
		vertical-align: top;
		}

		q:before, q:after {
		content: ”;
		}

-->


		<xsl:apply-templates select="styles/style" />
	</xsl:template>

	<xsl:template match="style">
		<xsl:choose>
			<xsl:when
				test="@id='winform_bar_link' or @id='winform_bar_link_i1' or @id='winform_bar_link_i2'">
				<xsl:call-template name="prerender_style">
					<xsl:with-param name="variant">
						f1
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="prerender_style">
					<xsl:with-param name="variant">
						f2
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="prerender_style">
					<xsl:with-param name="variant">
						f3
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="prerender_style">
					<xsl:with-param name="variant">
						f4
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="prerender_style">
					<xsl:with-param name="variant">
						f5
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="prerender_style">
					<xsl:with-param name="variant">
						f6
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="prerender_style">
					<xsl:with-param name="variant">
						f7
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="prerender_style">
					<xsl:with-param name="variant">
						f8
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="prerender_style">
					<xsl:with-param name="variant">
						f9
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="prerender_style">
					<xsl:with-param name="variant">
						f10
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="prerender_style">
					<xsl:with-param name="variant">
						f11
					</xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="prerender_style">
					<xsl:with-param name="variant">
						f12
					</xsl:with-param>
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
		<xsl:call-template name="prerender_style" />
	</xsl:template>

	<xsl:template name="prerender_style">
		<xsl:param name="variant" select="'null'" />

		<!-- set the suitable name -->

		<xsl:choose>
			<xsl:when test="@inner">
				<xsl:choose>
					<xsl:when test="@action">
						.pwr_<xsl:value-of select="@id" /><xsl:if test="not($variant='null')">_<xsl:value-of select="$variant" /></xsl:if>:
						<xsl:value-of select="@action" />
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
						<xsl:value-of select="@inner" />
						{
					</xsl:when>
					<xsl:otherwise>
						.pwr_<xsl:value-of select="@id" /><xsl:if test="not($variant='null')">_<xsl:value-of select="$variant" /></xsl:if>
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
						<xsl:value-of select="@inner" />
						{
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<!-- any other one !! -->
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="@action">
						.pwr_<xsl:value-of select="@id" /><xsl:if test="not($variant='null')">_<xsl:value-of select="$variant" /></xsl:if>:
						<xsl:value-of select="@action" />
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
						<xsl:value-of select="@inner" />
						,
						.pwr_<xsl:value-of select="@id" /><xsl:if test="not($variant='null')">_<xsl:value-of select="$variant" /></xsl:if>.<xsl:value-of select="@action" />
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
						<xsl:value-of select="@inner" />
						{
					</xsl:when>
					<xsl:otherwise>
						.pwr_<xsl:value-of select="@id" /><xsl:if test="not($variant='null')">_<xsl:value-of select="$variant" /></xsl:if>
						{
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
		<!-- then render !! -->
		<xsl:call-template name="render_style">
			<xsl:with-param name="variant" select="$variant" />
		</xsl:call-template>
		}
		<!-- children styles -->
		<xsl:apply-templates select="style" />
	</xsl:template>


	<!-- *********************************** basic template to render a style 
		*********************************** -->
	<xsl:template name="render_style">
		<xsl:param name="selector" select="'null'" />
		<xsl:param name="variant" select="'null'" />
		<!-- any style output -->
		<xsl:text> 
		</xsl:text>
		<!-- overflow: hidden; -->
		<xsl:choose>
			<xsl:when test="overflow">
				<xsl:apply-templates select="overflow" />
			</xsl:when>
			<xsl:otherwise>
				overflow: hidden;<!-- default overflow -->
			</xsl:otherwise>
		</xsl:choose>
		<xsl:choose>
			<xsl:when test="cursor">
				<xsl:apply-templates select="cursor" />
			</xsl:when>
			<xsl:otherwise>
				cursor:default; <!-- arrow pointer -->
			</xsl:otherwise>
		</xsl:choose>
		<xsl:call-template name="location" />
		<xsl:call-template name="dimension" />
		<xsl:apply-templates select="display" />
		<xsl:apply-templates select="line-height" />
		<xsl:apply-templates select="float" />
		<xsl:apply-templates select="foreground">
			<xsl:with-param name="selector">
				<xsl:value-of select="$selector" />
			</xsl:with-param>
			<xsl:with-param name="variant">
				<xsl:value-of select="$variant" />
			</xsl:with-param>
		</xsl:apply-templates>
		<xsl:apply-templates select="background">
			<xsl:with-param name="selector">
				<xsl:value-of select="$selector" />
			</xsl:with-param>
			<xsl:with-param name="variant">
				<xsl:value-of select="$variant" />
			</xsl:with-param>
		</xsl:apply-templates>
		<xsl:for-each
			select="border[(not(@variant) and $selector='null' and not(@id)) or (not(@variant) and not($selector='null') and @id and @id=$selector) or ((not($variant='null') and @variant and @variant=$variant) and (($selector='null' and not(@id) ) or @id=$selector))]">
			<xsl:call-template name="border" />
		</xsl:for-each>
		<xsl:apply-templates select="transition" />
		<xsl:apply-templates select="box-shadow" />
		<xsl:apply-templates select="outline" />
		<xsl:apply-templates select="appearance" />
		<xsl:apply-templates select="border_line" />
		<xsl:apply-templates select="box-shadow_line" />
		<xsl:apply-templates select="radius" />
		<xsl:apply-templates select="radius_line" />
		<xsl:apply-templates select="padding" />
		<xsl:apply-templates select="content_layout" />
		<xsl:apply-templates select="margin" />
		<xsl:apply-templates select="filter" />
		<!--xsl:for-each select="scroll_bar[($selector='null' and not(@id)) or 
			@id=$selector]/color"> scrollbar-<xsl:value-of select="@id"/>-color:<xsl:value-of 
			select="@value"/>; </xsl:for-each -->

	</xsl:template>

	<!-- line-height attributes -->
	<xsl:template match="line-height">
		<xsl:if test="@size">
			line-height:
			<xsl:value-of select="@size" />
			px;
		</xsl:if>
	</xsl:template>

	<!-- display attributes -->
	<xsl:template match="display">
		<xsl:if test="@type">
			display:
			<xsl:value-of select="@type" />
			;
		</xsl:if>
	</xsl:template>

	<!-- float attributes -->
	<xsl:template match="float">
		<xsl:if test="@type">
			float:
			<xsl:value-of select="@type" />
			;
		</xsl:if>
	</xsl:template>

	<!-- filter attributes -->
	<xsl:template match="filter">
		<xsl:if test="@alpha">
			filter:alpha(Opacity=
			<xsl:value-of select="@alpha" />
			);-moz-opacity:.
			<xsl:value-of select="@alpha" />
			;opacity:.
			<xsl:value-of select="@alpha" />
		</xsl:if>

	</xsl:template>

	<!-- location attributes -->
	<xsl:template name="location">
		<xsl:if test="@position">
			position:
			<xsl:value-of select="@position" />
			;
		</xsl:if>
		<xsl:if test="@x">
			left:
			<xsl:value-of select="@x" />
			px;
		</xsl:if>
		<xsl:if test="@y">
			top:
			<xsl:value-of select="@y" />
			px;
		</xsl:if>
		<xsl:if test="@z">
			z-index:
			<xsl:value-of select="@z" />
			;
		</xsl:if>
		<xsl:if test="@l">
			left:
			<xsl:value-of select="@l" />
			px;
		</xsl:if>
		<xsl:if test="@t">
			top:
			<xsl:value-of select="@t" />
			px;
		</xsl:if>
		<xsl:if test="@r">
			right:
			<xsl:value-of select="@r" />
			px;
		</xsl:if>
		<xsl:if test="@b">
			bottom:
			<xsl:value-of select="@b" />
			px;
		</xsl:if>
	</xsl:template>

	<!-- dimension attributes -->
	<xsl:template name="dimension">
		<xsl:if test="@width">
			width:
			<xsl:value-of select="@width" />
			px;
		</xsl:if>
		<xsl:if test="@height">
			height:
			<xsl:value-of select="@height" />
			px;
		</xsl:if>
		<xsl:if test="@pwidth">
			width:
			<xsl:value-of select="@pwidth" />
			%;
		</xsl:if>
		<xsl:if test="@pheight">
			height:
			<xsl:value-of select="@pheight" />
			%;
		</xsl:if>
	</xsl:template>

	<!-- background node -->
	<xsl:template match="background">
		<xsl:param name="selector" select="'null'" />
		<xsl:param name="variant" select="'null'" />
		<xsl:for-each
			select="color[(not(@variant) and $selector='null' and not(@id)) or (not(@variant) and not($selector='null') and @id and @id=$selector) or ((not($variant='null') and @variant and @variant=$variant) and (($selector='null' and not(@id) ) or @id=$selector))]">
			background-color:
			<xsl:value-of select="@value" />
			;
		</xsl:for-each>
		<xsl:if
			test="color[(not(@variant) and $selector='null' and not(@id)) or (not(@variant) and not($selector='null') and @id and @id=$selector) or ((not($variant='null') and @variant and @variant=$variant) and (($selector='null' and not(@id) ) or @id=$selector))]/@value">


		</xsl:if>
		<xsl:for-each
			select="image[(not(@variant) and $selector='null' and not(@id)) or (not(@variant) and not($selector='null') and @id and @id=$selector) or ((not($variant='null') and @variant and @variant=$variant) and (($selector='null' and not(@id) ) or @id=$selector))]">
			<xsl:choose>
				<xsl:when test="@paint_mode='tiled'">
					background: url("/
					<xsl:value-of select="$url_prefix" />
					/
					<xsl:value-of select="$skin_path" />
					/
					<xsl:value-of select="@file" />
					") repeat;
				</xsl:when>
				<xsl:when test="@paint_mode='htiled'">
					background: url("/
					<xsl:value-of select="$url_prefix" />
					/
					<xsl:value-of select="$skin_path" />
					/
					<xsl:value-of select="@file" />
					") repeat-x;
				</xsl:when>
				<xsl:when test="@paint_mode='vtiled'">
					background: url("/
					<xsl:value-of select="$url_prefix" />
					/
					<xsl:value-of select="$skin_path" />
					/
					<xsl:value-of select="@file" />
					") repeat-y;
				</xsl:when>
				<xsl:when test="@paint_mode='scaled'">
					background: url("/
					<xsl:value-of select="$url_prefix" />
					/
					<xsl:value-of select="$skin_path" />
					/
					<xsl:value-of select="@file" />
					") no-repeat;
					background-size: 100% 100%;
				</xsl:when>
				<xsl:when test="@paint_mode='normal'">
					background-repeat:no-repeat;
				</xsl:when>
				<xsl:when test="@paint_mode='gradient'">
					background-image: -webkit-linear-gradient(
					<xsl:value-of select="@direccion" />
					,
					<xsl:value-of select="@color_desde" />
					,
					<xsl:value-of select="@color_hasta" />
					);
					background-image: -moz-linear-gradient(
					<xsl:value-of select="@direccion" />
					,
					<xsl:value-of select="@color_desde" />
					,
					<xsl:value-of select="@color_hasta" />
					);
					background-image: -o-linear-gradient(
					<xsl:value-of select="@direccion" />
					,
					<xsl:value-of select="@color_desde" />
					,
					<xsl:value-of select="@color_hasta" />
					);
					background-image: linear-gradient(
					<xsl:if test="@direccion='top'">
						to bottom
					</xsl:if>
					<xsl:if test="not(@direccion='top')">
						to top
					</xsl:if>
					,
					<xsl:value-of select="@color_desde" />
					,
					<xsl:value-of select="@color_hasta" />
					);
				</xsl:when>
				<xsl:otherwise>
					background: url("/
					<xsl:value-of select="$url_prefix" />
					/
					<xsl:value-of select="$skin_path" />
					/
					<xsl:value-of select="@file" />
					")
					<xsl:value-of select="@paint_mode" />
					;
				</xsl:otherwise>
			</xsl:choose>
			<xsl:if test="@scrollable='false'">
				background-attachment:fixed;
			</xsl:if>
		</xsl:for-each>
	</xsl:template>



	<!-- foreground node -->
	<xsl:template match="foreground">
		<xsl:param name="selector" select="'null'" />
		<xsl:param name="variant" select="'null'" />
		<xsl:for-each
			select="color[(not(@variant) and $selector='null' and not(@id)) or (not(@variant) and not($selector='null') and @id and @id=$selector) or ((not($variant='null') and @variant and @variant=$variant) and (($selector='null' and not(@id) ) or @id=$selector))]">
			color:
			<xsl:value-of select="@value" />
			;
		</xsl:for-each>

		<xsl:for-each
			select="font[(not(@variant) and $selector='null' and not(@id)) or (not(@variant) and not($selector='null') and @id and @id=$selector) or ((not($variant='null') and @variant and @variant=$variant) and (($selector='null' and not(@id) ) or @id=$selector))]">
			font-family:
			<xsl:value-of select="@name" />
			;
			<xsl:if test="@size">
				font-size:
				<xsl:value-of select="@size" />
				px;
			</xsl:if>
			<xsl:if test="@style">
				font-style:
				<xsl:value-of select="@style" />
				;
			</xsl:if>
			<xsl:if test="@weight">
				font-weight:
				<xsl:value-of select="@weight" />
				;
			</xsl:if>
			<xsl:if test="@decoration">
				text-decoration:
				<xsl:value-of select="@decoration" />
				;
			</xsl:if>
			<xsl:if test="@letter-spacing">
				letter-spacing:
				<xsl:value-of select="@letter-spacing" />
				;
			</xsl:if>

			<xsl:if test="@transform">
				<xsl:choose>
					<xsl:when test="@transform='small_caps'">
						font-variant:small-caps;
						text-transform:none;
					</xsl:when>
					<xsl:otherwise>
						font-variant:normal;
						text-transform:
						<xsl:value-of select="@transform" />
						;
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>

		</xsl:for-each>
		<!-- content_layout node -->
		<xsl:for-each select="content_layout">
			<xsl:if test="@halignment">
				text-align:
				<xsl:value-of select="@halignment" />
				;
			</xsl:if>
			<xsl:if test="@valignment"> <!-- text-top y text-bottom -->
				vertical-align:
				<xsl:value-of select="@valignment" />
				;
			</xsl:if>
			<xsl:if test="@word_wrap">
				<xsl:if test="@word_wrap='true'">
					white-space:normal;
				</xsl:if>
				<xsl:if test="@word_wrap='false'">
					white-space:nowrap;
				</xsl:if>
			</xsl:if>
		</xsl:for-each>

	</xsl:template>

	<!-- padding node -->
	<xsl:template match="padding">
		<xsl:if test="@left">
			padding-left:
			<xsl:value-of select="@left" />
			px;
		</xsl:if>
		<xsl:if test="@right">
			padding-right:
			<xsl:value-of select="@right" />
			px;
		</xsl:if>
		<xsl:if test="@top">
			padding-top:
			<xsl:value-of select="@top" />
			px;
		</xsl:if>
		<xsl:if test="@bottom">
			padding-bottom:
			<xsl:value-of select="@bottom" />
			px;
		</xsl:if>
	</xsl:template>

	<xsl:template match="box-shadow">
		-moz-box-shadow: 0
		<xsl:value-of select="@size" />
		<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
		<xsl:if test="@sizeH">
			<xsl:value-of select="@sizeH" />
		</xsl:if>
		<xsl:if test="not(@sizeH)">
			<xsl:value-of select="@size" />
		</xsl:if>
		<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
		<xsl:value-of select="@color" />
		<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
		<xsl:value-of select="@mode" />
		;
		-webkit-box-shadow: 0
		<xsl:value-of select="@size" />
		<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
		<xsl:if test="@sizeH">
			<xsl:value-of select="@sizeH" />
		</xsl:if>
		<xsl:if test="not(@sizeH)">
			<xsl:value-of select="@size" />
		</xsl:if>
		<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
		<xsl:value-of select="@color" />
		<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
		<xsl:value-of select="@mode" />
		;
		-o-box-shadow: 0
		<xsl:value-of select="@size" />
		<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
		<xsl:if test="@sizeH">
			<xsl:value-of select="@sizeH" />
		</xsl:if>
		<xsl:if test="not(@sizeH)">
			<xsl:value-of select="@size" />
		</xsl:if>
		<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
		<xsl:value-of select="@color" />
		<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
		<xsl:value-of select="@mode" />
		;
		box-shadow: 0
		<xsl:value-of select="@size" />
		<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
		<xsl:if test="@sizeH">
			<xsl:value-of select="@sizeH" />
		</xsl:if>
		<xsl:if test="not(@sizeH)">
			<xsl:value-of select="@size" />
		</xsl:if>
		<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
		<xsl:value-of select="@color" />
		<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
		<xsl:value-of select="@mode" />
		;
	</xsl:template>


	<xsl:template match="transition">
		transition:
		<xsl:if test="@border">
			border
			<xsl:value-of select="@border" />
		</xsl:if>
		<xsl:if test="@box-shadow">
			<xsl:if test="@border">
				,
			</xsl:if>
			<xsl:value-of select="@box-shadow" />
		</xsl:if>
		;
	</xsl:template>

	<xsl:template match="outline">
		outline:
		<xsl:value-of select="@style" />
		;
	</xsl:template>

	<xsl:template match="appearance">
		appearance:
		<xsl:value-of select="@style" />
		;
	</xsl:template>

	<xsl:template match="border_line">
		border:
		<xsl:value-of select="@style" />
		;
	</xsl:template>
	<xsl:template match="box-shadow_line">
		box-shadow:
		<xsl:value-of select="@style" />
		;
	</xsl:template>

	<xsl:template match="radius">
		-moz-border-radius:
		<xsl:value-of select="@size" />
		;
		-webkit-border-radius:
		<xsl:value-of select="@size" />
		;
		-o-border-radius:
		<xsl:value-of select="@size" />
		;
		border-radius:
		<xsl:value-of select="@size" />
		;
	</xsl:template>

	<xsl:template match="radius_line">
		-moz-border-radius:
		<xsl:value-of select="@style" />
		;
		-webkit-border-radius:
		<xsl:value-of select="@style" />
		;
		-o-border-radius:
		<xsl:value-of select="@style" />
		;
		border-radius:
		<xsl:value-of select="@style" />
		;
	</xsl:template>



	<!-- margin node -->
	<xsl:template match="margin">
		<xsl:if test="@left">
			margin-left:
			<xsl:value-of select="@left" />
			px;
		</xsl:if>
		<xsl:if test="@right">
			margin-right:
			<xsl:value-of select="@right" />
			px;
		</xsl:if>
		<xsl:if test="@top">
			margin-top:
			<xsl:value-of select="@top" />
			px;
		</xsl:if>
		<xsl:if test="@bottom">
			margin-bottom:
			<xsl:value-of select="@bottom" />
			px;
		</xsl:if>
	</xsl:template>

	<!-- border node -->
	<xsl:template name="border">
		<xsl:if test="@type">
			border-style:
			<xsl:value-of select="@type" />
			;
		</xsl:if>
		<xsl:if test="color/@value">
			border-color:
			<xsl:value-of select="color/@value" />
			;
		</xsl:if>
		<xsl:if test="@thickness">
			border-width:
			<xsl:value-of select="@thickness" />
			px;
		</xsl:if>
		<xsl:if test="@box-sizing">
			box-sizing:
			<xsl:value-of select="@box-sizing" />
			;
			-moz-box-sizing:
			<xsl:value-of select="@box-sizing" />
			;
			-webkit-box-sizing:
			<xsl:value-of select="@box-sizing" />
			;
		</xsl:if>
		<xsl:call-template name="border_child">
			<xsl:with-param name="border_id" select="'left'" />
		</xsl:call-template>
		<xsl:call-template name="border_child">
			<xsl:with-param name="border_id" select="'right'" />
		</xsl:call-template>
		<xsl:call-template name="border_child">
			<xsl:with-param name="border_id" select="'top'" />
		</xsl:call-template>
		<xsl:call-template name="border_child">
			<xsl:with-param name="border_id" select="'bottom'" />
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="border_child">
		<xsl:param name="border_id" select="'unknown'" />
		<xsl:if test="border[@id=$border_id]/@type">
			border-
			<xsl:value-of select="$border_id" />
			-style:
			<xsl:value-of select="border[@id=$border_id]/@type" />
			;
		</xsl:if>
		<xsl:if test="border[@id=$border_id]/color/@value">
			border-
			<xsl:value-of select="$border_id" />
			-color:
			<xsl:value-of select="border[@id=$border_id]/color/@value" />
			;
		</xsl:if>
		<xsl:if test="border[@id=$border_id]/@thickness">
			border-
			<xsl:value-of select="$border_id" />
			-width:
			<xsl:value-of select="border[@id=$border_id]/@thickness" />
			px;
		</xsl:if>
	</xsl:template>

	<!-- overflow node -->
	<xsl:template match="overflow">
		<xsl:if test="@type">
			overflow:
			<xsl:value-of select="@type" />
			;
		</xsl:if>
	</xsl:template>


	<!-- cursor node -->
	<xsl:template match="cursor">
		<xsl:choose>
			<xsl:when test="@predefined">
				cursor:
				<xsl:value-of select="@predefined" />
				;
			</xsl:when>
			<xsl:when test="@file">
				cursor:url("/
				<xsl:value-of select="$url_prefix" />
				/
				<xsl:value-of select="$skin_path" />
				/
				<xsl:value-of select="@file" />
				");
			</xsl:when>
			<xsl:otherwise>
				cursor:default;
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet> 