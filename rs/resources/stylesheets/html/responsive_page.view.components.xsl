<xsl:stylesheet version="1.0"
              	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:variable name="skin_path"
            		select="page/header/layouts/@base_path"/>
	<xsl:variable name="url_prefix"
            		select="page/header/layouts/@url_prefix"/>
	<xsl:include href="utils.3d.xsl"/>
	<xsl:include href="utils.actions.xsl"/>
	<xsl:include href="utils.rendering.xsl"/>
	<xsl:include href="utils.combos.xsl"/>
	<xsl:strip-space elements="*"/>
	<!--
	**************************************************************************
	  ENTRY POINT TEMPLATES BY PATTERN MATCHING:
	  they render each kind of component
	**************************************************************************
-->
	<!--
	 ******** composites **********
	 they are components which may contain other components
-->
	<xsl:template match="message">
		<xsl:if test="text/.!=''">
			<script>showErrRefresh('<xsl:value-of select="@type"/>','<xsl:value-of select="@title"/>','<xsl:value-of disable-output-escaping="yes"
            					select="text/."/>'); </script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="split">
		<xsl:choose>
			<xsl:when test="@orientation='V'">
				<div>
					<xsl:call-template name="basic_generate_component_responsive"/>
					<div class="panel-left">
						<xsl:attribute name="id">panel-left_<xsl:value-of select="@name"/>
						</xsl:attribute>
						<xsl:for-each select="panel_a">
							<xsl:apply-templates select="*"/>
						</xsl:for-each>
					</div>
					<div class="splitter">
						<xsl:attribute name="id">splitter_<xsl:value-of select="@name"/>
						</xsl:attribute>
					</div>
					<div class="panel-right">
						<xsl:attribute name="id">panel-right_<xsl:value-of select="@name"/>
						</xsl:attribute>
						<xsl:for-each select="panel_b">
							<xsl:apply-templates select="*"/>
						</xsl:for-each>
					</div>
				</div>
				<script type="text/javascript"
      					charset="ISO-8859-1">
					$(function() {
					$("#panel-left_<xsl:value-of select="@name"/>").width('<xsl:value-of select="@pos_split"/>');
					$("#panel-left_<xsl:value-of select="@name"/>").resizable({
					handleSelector:"#splitter_<xsl:value-of select="@name"/>",
					resizeHeight:false
					});
					var h = $("#panel-right_<xsl:value-of select="@name"/>").height();
					$("#panel-right_<xsl:value-of select="@name"/>").css('min-height:'+h+'px');
					});
				</script>
			</xsl:when>
			<xsl:otherwise>
				<div class="panel-container-vertical">
					<xsl:call-template name="basic_generate_component_responsive"/>
					<div class="panel-top">
						<xsl:attribute name="id">panel-top_<xsl:value-of select="@name"/>
						</xsl:attribute>
						<xsl:for-each select="panel_a">
							<xsl:call-template name="basic_generate_component_responsive"/>
						</xsl:for-each>
					</div>
					<div class="splitter-horizontal">
						<xsl:attribute name="id">splitter-horizontal_<xsl:value-of select="@name"/>
						</xsl:attribute>
						<xsl:attribute name="id">splitter-horizontal</xsl:attribute>
					</div>
					<div class="panel-bottom">
						<xsl:attribute name="id">panel-bottom_<xsl:value-of select="@name"/>
						</xsl:attribute>
						<xsl:for-each select="panel_b">
							<xsl:call-template name="basic_generate_component_responsive"/>
						</xsl:for-each>
					</div>
				</div>
				<script type="text/javascript"
      					charset="ISO-8859-1">
					$("#panel-left_<xsl:value-of select="@name"/>").height('<xsl:value-of select="@pos_split"/>');
					$("#panel-top_<xsl:value-of select="@name"/>).resizable({
					handleSelector : "#splitter-horizontal_<xsl:value-of select="@name"/>,
					resizeWidth : false
					});
				</script>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="captcha">
		<div class="g-recaptcha">
			<xsl:attribute name="data-sitekey">
				<xsl:value-of select="/page/header/@google_captcha_key"/>
			</xsl:attribute>
			<xsl:attribute name="id">
				<xsl:value-of select="@name"/>
			</xsl:attribute>
		</div>
	</xsl:template>
	<xsl:template match="card_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:attribute name="class">
				<xsl:value-of select="@size_responsive"/>
			</xsl:attribute>
			<input type="hidden">
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_zobject</xsl:attribute>
				<xsl:attribute name="value">
					<xsl:value-of select="@zobject"/>
				</xsl:attribute>
			</input>
			<div>
				<xsl:call-template name="basic_generate_component_responsive"/>
				<xsl:for-each select="diferido/action">
					<div>
						<xsl:attribute name="id">
							<xsl:value-of select="@ajax_container"/>
						</xsl:attribute>
						<div style="width:100%;height:100%">
							<xsl:attribute name="class">
								panel panel-primary jumbotron
							</xsl:attribute>
						</div>
					</div>
					<script type="POSTSCRIPT">
						<xsl:call-template name="generate_action_invokation"/>
					</script>
				</xsl:for-each>
			</div>
			<script>
				registerNoConstraint('<xsl:value-of select="$fullname"/>_zobject',['<xsl:value-of select="$fullname"/>_zobject']);
			</script>
		</div>
	</xsl:template>
	<xsl:template match="infocard_responsive">
		<div>
			<xsl:attribute name="class">
				<xsl:value-of select="@size_responsive"/>
			</xsl:attribute>
			<div>
				<xsl:call-template name="basic_generate_component_responsive"/>
				<xsl:if test="@label and @label!=''">
					<div class="card-body">
						<div class="row gutter-0">
							<div>
								<xsl:attribute name="class">
									<xsl:value-of select="@class_header_image"/>
								</xsl:attribute>
								<xsl:call-template name="render_icon"/>
							</div>
							<div>
								<xsl:attribute name="class">
									<xsl:value-of select="@class_header_text"/>
								</xsl:attribute>
								<div class="infocard">
									<xsl:value-of select="text/."/>
								</div>
								<div>
									<xsl:value-of select="@label"/>
								</div>
							</div>
						</div>
					</div>
				</xsl:if>
				<xsl:if test="@directlink!=''">
					<div>
						<xsl:if test="@label and @label!=''">
							<xsl:attribute name="class">panel-footer</xsl:attribute>
						</xsl:if>
						<xsl:if test="not(@label and @label!='')">
							<xsl:attribute name="class">panel-heading</xsl:attribute>
						</xsl:if>
						<a>
							<xsl:attribute name="id">
								<xsl:value-of select="@name"/>_link</xsl:attribute>
							<xsl:attribute name="href">#<xsl:value-of select="@directlink"/>
							</xsl:attribute>
							<xsl:attribute name="data-toggle">collapse</xsl:attribute>
							<xsl:attribute name="aria-expanded">false</xsl:attribute>
							<xsl:attribute name="aria-controls">
								<xsl:value-of select="@directlink"/>
							</xsl:attribute>
							<xsl:if test="@dataparent">
								<xsl:attribute name="data-parent">#<xsl:value-of select="@dataparent"/>
								</xsl:attribute>
							</xsl:if>
							<span class="pull-left">
								<xsl:value-of select="@label_link"/>
							</span>
							<span class="pull-right">
								<i class="fa fa-arrow-circle-right"/>
							</span>
							<div class="clearfix"/>
						</a>
					</div>
				</xsl:if>
				<xsl:if test="action">
					<div>
						<xsl:if test="@label and @label!=''">
							<xsl:attribute name="class">panel-footer</xsl:attribute>
						</xsl:if>
						<xsl:if test="not(@label and @label!='')">
							<xsl:attribute name="class">panel-heading</xsl:attribute>
						</xsl:if>
						<a>
							<xsl:attribute name="id">
								<xsl:value-of select="@name"/>_link</xsl:attribute>
							<xsl:attribute name="onclick">
								<xsl:for-each select="action">
									<xsl:call-template name="generate_action_invokation"/>
								</xsl:for-each>
							</xsl:attribute>
							<span class="pull-left">
								<xsl:value-of select="@label_link"/>
							</span>
							<span class="pull-right">
								<i class="fa fa-arrow-circle-right"/>
							</span>
							<div class="clearfix"/>
						</a>
					</div>
				</xsl:if>
			</div>
		</div>
	</xsl:template>
	<xsl:template name="view_image_responsive">
		<xsl:choose>
			<xsl:when test="@diferido='true'">
				<div>
					<xsl:call-template name="basic_generate_component_responsive"/>
					<div>
						<xsl:attribute name="id">
							<xsl:value-of select="image/@graph_content"/>
						</xsl:attribute>
						<xsl:attribute name="style">
							<xsl:call-template name="generate_inline_style_responsive"/>
						</xsl:attribute>
					</div>
				</div>
				<script type="POSTSCRIPT">
					<xsl:for-each select="image/diferido/action">
						<xsl:call-template name="generate_action_invokation"/>
					</xsl:for-each>
				</script>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="icon">
						<xsl:call-template name="render_icon">
							<xsl:with-param name="style_attr">
								<xsl:if test="icon/@style_image=''">width:100%;</xsl:if>
							</xsl:with-param>
						</xsl:call-template>
					</xsl:when>
					<xsl:when test="image/@source='script'">
						<xsl:call-template name="render_3d"/>
						<!-- xsl:if test="image/@link">
					<script>
						function clickRender3D<xsl:value-of select="image/@link"/>() {
							<xsl:for-each select="image/action">
									<xsl:call-template name="generate_action_invokation"/>
							</xsl:for-each>
						}
					</script>	
				</xsl:if -->
					</xsl:when>
					<xsl:when test="image/@link">
						<a>
							<xsl:attribute name="href">
								<xsl:value-of select="image/@link"/>
							</xsl:attribute>
							<xsl:call-template name="basic_render_image"/>
						</a>
					</xsl:when>
					<xsl:otherwise>
						<img border="0">
							<xsl:call-template name="internal_basic_render_image">
								<xsl:with-param name="style_attr">width:100%;</xsl:with-param>
							</xsl:call-template>
						</img>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="imagecard_responsive">
		<div>
			<xsl:attribute name="class">
				<xsl:value-of select="@size_responsive"/>
			</xsl:attribute>
			<div>
				<xsl:call-template name="basic_generate_component_responsive"/>
				<div class="card-body">
					<div class="row gutter-0">
						<xsl:if test="@content='false'">
							<xsl:choose>
								<xsl:when test="@label and icon">
									<div class="col-xs-3">
										<xsl:call-template name="view_image_responsive"/>
									</div>
									<div class="col-xs-9 text-right">
										<div>
											<xsl:attribute name="style">
												<xsl:if test="@label_fontweight">font-weight:<xsl:value-of select="@label_font_weight"/>;</xsl:if>
												<xsl:if test="@label_fontstyle">font-style:<xsl:value-of select="@label_fontstyle"/>;</xsl:if>
												<xsl:if test="@label_fontsize">font-size:<xsl:value-of select="@label_fontsize"/>px;</xsl:if>
											</xsl:attribute>
											<xsl:if test="string-length(substring-before(@label, '&lt;')) &lt; string-length(substring-before(@label, '&gt;'))">
												<xsl:value-of disable-output-escaping="yes"
            													select="@label"/>
											</xsl:if>
											<xsl:if test="not(string-length(substring-before(@label, '&lt;'))  &lt; string-length(substring-before(@label, '&gt;')))">
												<xsl:value-of select="@label"/>
											</xsl:if>
										</div>
									</div>
								</xsl:when>
								<xsl:when test="@label and image">
									<div class="col-xs-12">
										<xsl:call-template name="view_image_responsive"/>
									</div>
								</xsl:when>
								<xsl:when test="@label">
									<div class="col-xs-12">
										<div>
											<xsl:if test="string-length(substring-before(@label, '&lt;')) &lt; string-length(substring-before(@label, '&gt;'))">
												<xsl:value-of disable-output-escaping="yes"
            													select="@label"/>
											</xsl:if>
											<xsl:if test="not(string-length(substring-before(@label, '&lt;'))  &lt; string-length(substring-before(@label, '&gt;')))">
												<xsl:value-of select="@label"/>
											</xsl:if>
										</div>
									</div>
								</xsl:when>
								<xsl:otherwise>
									<div class="col-xs-12">
										<xsl:call-template name="view_image_responsive"/>
									</div>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:if>
						<xsl:if test="@content='true'">
							<xsl:choose>
								<xsl:when test="@label and icon">
									<div class="col-xs-3">
										<xsl:call-template name="view_image_responsive"/>
									</div>
									<div class="col-xs-9 text-right">
										<div>
											<xsl:attribute name="style">
												<xsl:if test="@label_fontweight">font-weight:<xsl:value-of select="@label_font_weight"/>;</xsl:if>
												<xsl:if test="@label_fontstyle">font-style:<xsl:value-of select="@label_fontstyle"/>;</xsl:if>
												<xsl:if test="@label_fontsize">font-size:<xsl:value-of select="@label_fontsize"/>px;</xsl:if>
											</xsl:attribute>
											<xsl:if test="string-length(substring-before(@label, '&lt;')) &lt; string-length(substring-before(@label, '&gt;'))">
												<xsl:value-of disable-output-escaping="yes"
            													select="@label"/>
											</xsl:if>
											<xsl:if test="not(string-length(substring-before(@label, '&lt;'))  &lt; string-length(substring-before(@label, '&gt;')))">
												<xsl:value-of select="@label"/>
											</xsl:if>
										</div>
									</div>
								</xsl:when>
								<xsl:when test="@label">
									<div class="col-xs-12">
										<div>
											<xsl:if test="string-length(substring-before(@label, '&lt;')) &lt; string-length(substring-before(@label, '&gt;'))">
												<xsl:value-of disable-output-escaping="yes"
            													select="@label"/>
											</xsl:if>
											<xsl:if test="not(string-length(substring-before(@label, '&lt;'))  &lt; string-length(substring-before(@label, '&gt;')))">
												<xsl:value-of select="@label"/>
											</xsl:if>
										</div>
									</div>
								</xsl:when>
							</xsl:choose>
						</xsl:if>
					</div>
				</div>
				<xsl:if test="@content='true'">
					<div class="card-body">
						<div class="col-xs-12">
							<xsl:call-template name="view_image_responsive"/>
						</div>
					</div>
				</xsl:if>
				<xsl:if test="action">
					<a>
						<xsl:if test="@refreshForm='true'">
							<xsl:for-each select="action">
								<xsl:attribute name="onClick">goToRefreshForm('do-PartialRefreshForm', '<xsl:value-of select="../@name"/>', '<xsl:value-of select="../action/@obj_provider"/>','<xsl:value-of select="../action/@object_owner_id"/>', getLastModal(), '<xsl:value-of select="../action/@id_action"/>'); var evt = event ? event:window.event; if (evt.stopPropagation) evt.stopPropagation(); if (evt.cancelBubble!=null) evt.cancelBubble = true; evt.preventDefault(); return false; </xsl:attribute>
							</xsl:for-each>
						</xsl:if>
						<xsl:if test="not(@refreshForm='true')">
							<xsl:attribute name="onclick">
								<xsl:for-each select="action">
									<xsl:call-template name="generate_action_invokation"/>
								</xsl:for-each>
							</xsl:attribute>
						</xsl:if>
						<div class="panel-footer">
							<span class="pull-left">
								<xsl:value-of select="@label_link"/>
							</span>
							<span class="pull-right">
								<i class="fa fa-arrow-circle-right"/>
							</span>
							<div class="clearfix"/>
						</div>
					</a>
				</xsl:if>
			</div>
		</div>
	</xsl:template>
	<xsl:template match="panel">
		<xsl:call-template name="basic_generate_composite"/>
	</xsl:template>
	<xsl:template match="h1_responsive">
		<H1>
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="with_title"
              					select="'true'"/>
			</xsl:call-template>
		</H1>
	</xsl:template>
	<xsl:template match="h2_responsive">
		<H2>
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="with_title"
              					select="'true'"/>
			</xsl:call-template>
		</H2>
	</xsl:template>
	<xsl:template match="h3_responsive">
		<H3>
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="with_title"
              					select="'true'"/>
			</xsl:call-template>
		</H3>
	</xsl:template>
	<xsl:template match="h4_responsive">
		<H4>
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="with_title"
              					select="'true'"/>
			</xsl:call-template>
		</H4>
	</xsl:template>
	<xsl:template match="formform_responsive">
		<DIV>
			<xsl:if test="/page/header/savehelp">
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="@name"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',0); </xsl:attribute>
			</xsl:if>
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="with_title"
              					select="'true'"/>
			</xsl:call-template>
		</DIV>
	</xsl:template>
	<xsl:template match="localform_responsive">
		<DIV>
			<xsl:if test="/page/header/savehelp">
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="@name"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',0); </xsl:attribute>
			</xsl:if>
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="with_title"
              					select="'true'"/>
			</xsl:call-template>
		</DIV>
	</xsl:template>
	<xsl:template match="div_responsive">
		<DIV>
			<xsl:if test="/page/header/savehelp">
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="@name"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',0); </xsl:attribute>
			</xsl:if>
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="with_title"
              					select="'true'"/>
			</xsl:call-template>
		</DIV>
	</xsl:template>
	<xsl:template match="jumbotron">
		<DIV>
			<xsl:if test="/page/header/savehelp">
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="@name"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',0); </xsl:attribute>
			</xsl:if>
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="with_title"
              					select="'true'"/>
			</xsl:call-template>
		</DIV>
	</xsl:template>
	<xsl:template match="win_expand_responsive">
		<DIV>
			<xsl:if test="/page/header/savehelp">
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="@name"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',0); </xsl:attribute>
			</xsl:if>
			<xsl:if test="@class_responsive">
				<xsl:attribute name="class">
					<xsl:value-of select="@class_responsive"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="not(@class_responsive)">
				<xsl:attribute name="class">col-sm-12</xsl:attribute>
			</xsl:if>
			<xsl:attribute name="style">min-height:10000px</xsl:attribute>
			<xsl:for-each select="rows">
				<xsl:for-each select="row">
					<div>
						<xsl:attribute name="id">
							<xsl:value-of select="@id"/>_move</xsl:attribute>
						<xsl:attribute name="class">zone_draggeable</xsl:attribute>
						<div style="display:inline-block">
							<xsl:attribute name="id">
								<xsl:value-of select="@id"/>_resize</xsl:attribute>
							<xsl:attribute name="class">zone_resizeable</xsl:attribute>
							<div>
								<xsl:attribute name="id">
									<xsl:value-of select="@id"/>
								</xsl:attribute>
								<xsl:if test="not(@resize) and @class_responsive">
									<xsl:attribute name="class">
										<xsl:value-of select="@class_responsive"/>
									</xsl:attribute>
								</xsl:if>
								<script type="POSTSCRIPT">
									<xsl:for-each select="action">
										<xsl:call-template name="generate_action_invokation"/>
									</xsl:for-each>
								</script>
							</div>
						</div>
					</div>
					<input type="hidden">
						<xsl:attribute name="id">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_posy</xsl:attribute>
						<xsl:attribute name="name">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_posy</xsl:attribute>
						<xsl:attribute name="value">
							<xsl:value-of select="@cv_posy"/>
						</xsl:attribute>
					</input>
					<input type="hidden">
						<xsl:attribute name="id">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_posx</xsl:attribute>
						<xsl:attribute name="name">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_posx</xsl:attribute>
						<xsl:attribute name="value">
							<xsl:value-of select="@cv_posx"/>
						</xsl:attribute>
					</input>
					<input type="hidden">
						<xsl:attribute name="id">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_width</xsl:attribute>
						<xsl:attribute name="name">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_width</xsl:attribute>
						<xsl:attribute name="value">
							<xsl:value-of select="@cv_width"/>
						</xsl:attribute>
					</input>
					<input type="hidden">
						<xsl:attribute name="id">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_height</xsl:attribute>
						<xsl:attribute name="name">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_height</xsl:attribute>
						<xsl:attribute name="value">
							<xsl:value-of select="@cv_height"/>
						</xsl:attribute>
					</input>
					<script>$( function() { registerMeta('dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_posy',['dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_posy']); registerMeta('dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_posx',['dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_posx']); registerMeta('dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_width',['dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_width']); registerMeta('dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_height',['dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_height']); $('#<xsl:value-of select="@id"/>_resize').resize("resize.<xsl:value-of select="@id"/>",function(){ $("#dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_width").val($("#<xsl:value-of select="@id"/>_move").width()); $("#dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_height").val($("#<xsl:value-of select="@id"/>_move").height()); }); $('#<xsl:value-of select="@id"/>_move').on('dragstop.<xsl:value-of select="@id"/>', function (event) { $(function() { $("#dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_posy").val($("#<xsl:value-of select="@id"/>_resize").position().top); $("#dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>_posx").val($("#<xsl:value-of select="@id"/>_resize").position().left); }); }); $("#<xsl:value-of select="@id"/>_move").css( {"top":"<xsl:value-of select="@cv_posy"/>px", "left":"<xsl:value-of select="@cv_posx"/>px","width":"<xsl:value-of select="@cv_width"/>px", "height":"<xsl:value-of select="@cv_height"/>px"}); $("#<xsl:value-of select="@id"/>_resize").resizable(); $("#<xsl:value-of select="@id"/>_move").draggable({ scroll: true, containment: "parent" }); }); </script>
				</xsl:for-each>
			</xsl:for-each>
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="with_title"
              					select="'true'"/>
			</xsl:call-template>
		</DIV>
	</xsl:template>
	<xsl:template match="win_row_expand_responsive">
		<xsl:choose>
			<xsl:when test="@expanded='true'">
				<div>
					<xsl:attribute name="id">
						<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>_move</xsl:attribute>
					<xsl:if test="@resizable='true' and @editable='true'">
						<xsl:attribute name="class">zone_draggeable</xsl:attribute>
					</xsl:if>
					<div>
						<xsl:attribute name="id">
							<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>_resize</xsl:attribute>
						<xsl:if test="@resizable='true'">
							<xsl:attribute name="style">display:inline-block;</xsl:attribute>
							<xsl:if test="@editable='true'">
								<xsl:attribute name="class">zone_resizeable</xsl:attribute>
							</xsl:if>
							<xsl:attribute name="onresize">javascript:zoomToFit('<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>_resize','<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>') </xsl:attribute>
						</xsl:if>
						<div>
							<xsl:attribute name="id">
								<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>
							</xsl:attribute>
							<div>
								<xsl:call-template name="basic_generate_component_responsive"/>
							</div>
						</div>
					</div>
				</div>
				<xsl:if test="@resizable='true'">
					<script type="POSTSCRIPT">$( function() { <xsl:if test="@editable='true'">$('#<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>_resize').resize("resize.<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>",function(){ setJQValue("#dgf_<xsl:value-of select="@form_name"/>_fd-width",$("#<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>_resize").width()); setJQValue("#dgf_<xsl:value-of select="@form_name"/>_fd-height",$("#<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>_resize").height()); setJQValue("#dgf_<xsl:value-of select="@form_name"/>_fd-posy",parseInt($("#<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>_move").css("top"), 10) || 0); setJQValue("#dgf_<xsl:value-of select="@form_name"/>_fd-posx",parseInt($("#<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>_move").css("left"), 10) || 0); }); $('#<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>_move').on('dragstop.<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>', function (event) { $(function() { setJQValue("#dgf_<xsl:value-of select="@form_name"/>_fd-posy",parseInt($("#<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>_move").css("top"), 10) || 0); setJQValue("#dgf_<xsl:value-of select="@form_name"/>_fd-posx",parseInt($("#<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>_move").css("left"), 10) || 0); }); }); $("#<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>_resize").resizable(); $("#<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>_move").draggable({ scroll: true, containment: "parent" }); </xsl:if>$("#<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>_move").css( {"top":"<xsl:value-of select="@cv_posy"/>px", "left":"<xsl:value-of select="@cv_posx"/>px"}); $("#<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>_resize").css( {"width":"<xsl:value-of select="@cv_width"/>px", "height":"<xsl:value-of select="@cv_height"/>px"}); zoomToFit('<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>_resize','<xsl:value-of select="@name_parent"/>_<xsl:value-of select="@rowid"/>') }); </script>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<div>
					<xsl:attribute name="class">flex-col panel<xsl:text><![CDATA[ ]]></xsl:text>
						<xsl:value-of select="@class_header_responsive"/>
						<xsl:text><![CDATA[ ]]></xsl:text>
						<xsl:if test="not(@visible and @visible='true')">hidden</xsl:if>
					</xsl:attribute>
					<div class="panel-heading no-margin">
						<xsl:for-each select="dragdropinfo">
							<xsl:call-template name="dragdrop_component"/>
						</xsl:for-each>
						<h4 class="panel-title">
							<a data-toggle="collapse">
								<xsl:attribute name="data-parent">#<xsl:value-of select="@name_parent"/>
								</xsl:attribute>
								<xsl:attribute name="href">#<xsl:value-of select="@name_parent"/>collapse<xsl:value-of select="@rowid"/>
								</xsl:attribute>
								<xsl:attribute name="onclick">javascript:setSelectExpandTab('<xsl:value-of select="@name_parent"/>','<xsl:value-of select="@name_parent"/>collapse<xsl:value-of select="@rowid"/>')</xsl:attribute>
								<xsl:value-of select="@label"/>
							</a>
							<xsl:if test="@label_right or @label_right!=''">
								<div class="panel-info"
   									style="float:right">
									<xsl:value-of select="@label_right"/>
								</div>
							</xsl:if>
							<xsl:if test="@toolbarpos='in'">
								<div style="float:right"
   									class="panel-info">
									<xsl:attribute name="id">
										<xsl:value-of select="@provider_name"/>_toolbar</xsl:attribute>
								</div>
							</xsl:if>
						</h4>
					</div>
					<div class="panel-collapse collapse">
						<xsl:attribute name="id">
							<xsl:value-of select="@name_parent"/>collapse<xsl:value-of select="@rowid"/>
						</xsl:attribute>
						<div class="panel-body">
							<xsl:call-template name="basic_generate_component_responsive"/>
						</div>
					</div>
				</div>
				<xsl:if test="@toolbarpos='in'">
					<script>if ($('#actionbarinternal_<xsl:value-of select="@provider_name"/>').html()!="") { $('#<xsl:value-of select="@provider_name"/>_toolbar').html($('#actionbarinternal_<xsl:value-of select="@provider_name"/>').html()); $('#actionbarinternal_<xsl:value-of select="@provider_name"/>').html(""); } </script>
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="panel_responsive">
		<div>
			<xsl:if test="@zoomtofit">
				<xsl:attribute name="id">
					<xsl:value-of select="@zoomtofit"/>_father</xsl:attribute>
			</xsl:if>
			<xsl:attribute name="class">
				<xsl:value-of select="@size_responsive"/>
				<xsl:if test="@dataparent">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapse </xsl:if>
			</xsl:attribute>
			<xsl:if test="@height">
				<xsl:attribute name="style">height:<xsl:value-of select="@height"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@dataparent">
				<xsl:attribute name="data-parent">#<xsl:value-of select="@dataparent"/>
				</xsl:attribute>
			</xsl:if>
			<div>
				<xsl:if test="@zoomtofit">
					<xsl:attribute name="id">
						<xsl:value-of select="@zoomtofit"/>_size</xsl:attribute>
					<xsl:attribute name="style">display:inline-block;width:<xsl:value-of select="@zoomtofit_width"/>px; </xsl:attribute>
				</xsl:if>
				<div>
					<xsl:attribute name="class">
						<xsl:choose>
							<xsl:when test="@visible and (@visible='true')">form-group<xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
							</xsl:when>
							<xsl:otherwise>form-group hidden</xsl:otherwise>
						</xsl:choose>
						<xsl:if test="@only-expanded">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
						<xsl:if test="@only-collapsed">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
					</xsl:attribute>
					<div>
						<xsl:if test="@class_responsive!='well-white' and @class_responsive!='row'">
							<xsl:attribute name="class">
								<xsl:value-of select="@class_responsive"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:if test="@label">
							<div class="panel-heading">
								<xsl:value-of select="@label"/>
								<xsl:if test="@label_right">
									<div class="panel-info"
   										style="float:right">
										<xsl:value-of select="@label_right"/>
									</div>
								</xsl:if>
							</div>
						</xsl:if>
						<div>
							<xsl:choose>
								<xsl:when test="@class_responsive!='well-white' and @class_responsive!='row'">
									<xsl:call-template name="basic_generate_component_responsive">
										<xsl:with-param name="specialClass">panel-body</xsl:with-param>
									</xsl:call-template>
								</xsl:when>
								<xsl:otherwise>
									<xsl:if test="@equals='true'">
										<xsl:call-template name="basic_generate_component_responsive">
											<xsl:with-param name="specialClass">equal</xsl:with-param>
										</xsl:call-template>
									</xsl:if>
									<xsl:if test="not(@equals='true')">
										<xsl:call-template name="basic_generate_component_responsive"/>
									</xsl:if>
								</xsl:otherwise>
							</xsl:choose>
						</div>
					</div>
					<xsl:if test="@zoomtofit">
						<script>zoomToFitHorizontal('<xsl:value-of select="@zoomtofit"/>_father','<xsl:value-of select="@zoomtofit"/>_size'); $(window).off('<xsl:value-of select="@zoomtofit"/>_father'); $(window).resize('<xsl:value-of select="@zoomtofit"/>_father',function(){ zoomToFitHorizontal('<xsl:value-of select="@zoomtofit"/>_father','<xsl:value-of select="@zoomtofit"/>_size'); }); </script>
					</xsl:if>
					<xsl:if test="@onCalculate and @isCalculeOthersFields">
						<script>
							<xsl:value-of select="@onCalculate"/>; </script>
					</xsl:if>
				</div>
			</div>
		</div>
	</xsl:template>
	<xsl:template match="panel_resizable_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@id"/>
		</xsl:variable>
		<div>
			<xsl:attribute name="id">
				<xsl:value-of select="@id"/>_move</xsl:attribute>
			<xsl:attribute name="class">zone_draggeable</xsl:attribute>
			<xsl:attribute name="style">overflow:auto;</xsl:attribute>
			<div>
				<xsl:attribute name="id">
					<xsl:value-of select="@id"/>_resize</xsl:attribute>
				<xsl:attribute name="style">display:inline-block;</xsl:attribute>
				<xsl:attribute name="class">zone_resizeable</xsl:attribute>
				<div>
					<xsl:attribute name="id">
						<xsl:value-of select="@id"/>
					</xsl:attribute>
					<xsl:call-template name="basic_generate_component_responsive"/>
				</div>
			</div>
		</div>
		<script>$( function() { $("#<xsl:value-of select="@id"/>_move").css( {"top":"<xsl:value-of select="@cv_posy"/>px", "left":"<xsl:value-of select="@cv_posx"/>px","width":"<xsl:value-of select="@cv_width"/>px", "height":"<xsl:value-of select="@cv_height"/>px"}); $("#<xsl:value-of select="@id"/>_resize").resizable(); $("#<xsl:value-of select="@id"/>_move").draggable({ scroll: true, containment: "parent" }); }); }); </script>
	</xsl:template>
	<xsl:template match="table_responsive">
		<div>
			<xsl:if test="@label_lateral">
				<label>
					<xsl:if test="@label_class">
						<xsl:attribute name="class">
							<xsl:value-of select="@label_class"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="@label_lateral"/>
				</label>
			</xsl:if>
			<input type="hidden">
				<xsl:attribute name="id">
					<xsl:value-of select="@id"/>
				</xsl:attribute>
				<xsl:attribute name="value">
					<xsl:value-of select="@active"/>
				</xsl:attribute>
			</input>
			<div>
				<xsl:call-template name="basic_generate_component_responsive"/>
			</div>
			<script>registerMeta('<xsl:value-of select="@id"/>',['<xsl:value-of select="@id"/>']); selectExpandTab('<xsl:value-of select="@id"/>'); </script>
		</div>
	</xsl:template>
	<xsl:template match="fieldset_responsive">
		<xsl:if test="@size_responsive and not(@only_fieldset)">
			<div>
				<xsl:attribute name="class">
					<xsl:value-of select="@size_responsive"/><![CDATA[ ]]>form-group <xsl:if test="@only-expanded">
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
					<xsl:if test="@only-collapsed">
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
				</xsl:attribute>
				<fieldset>
					<xsl:attribute name="id">
						<xsl:value-of select="@name"/>
					</xsl:attribute>
					<xsl:attribute name="class">
						<xsl:if test="@class_responsive">
							<xsl:value-of select="@class_responsive"/>
						</xsl:if>
						<xsl:text><![CDATA[ ]]></xsl:text>shadow<xsl:text><![CDATA[ ]]></xsl:text>
						<xsl:if test="not(@visible) or not(@visible='true')">hidden</xsl:if>
						<xsl:if test="@dataparent">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapse </xsl:if>
					</xsl:attribute>
					<xsl:if test="@dataparent">
						<xsl:attribute name="data-parent">#<xsl:value-of select="@dataparent"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@label">
						<legend>
							<xsl:value-of select="@label"/>
							<xsl:if test="@label_right">
								<div class="panel-info"
   									style="float:right">
									<xsl:value-of select="@label_right"/>
								</div>
							</xsl:if>
						</legend>
					</xsl:if>
					<xsl:call-template name="basic_generate_component_responsive"/>
				</fieldset>
			</div>
		</xsl:if>
		<xsl:if test="not(@size_responsive and not(@only_fieldset))">
			<fieldset>
				<xsl:attribute name="id">
					<xsl:value-of select="@name"/>
				</xsl:attribute>
				<xsl:attribute name="class">
					<xsl:if test="@size_responsive">
						<xsl:value-of select="@size_responsive"/>
					</xsl:if>
					<xsl:text><![CDATA[ ]]></xsl:text>
					<xsl:if test="@class_responsive">
						<xsl:value-of select="@class_responsive"/>
					</xsl:if>
					<xsl:text><![CDATA[ ]]></xsl:text>
					<xsl:if test="not(@visible) or not(@visible='true')">hidden</xsl:if>
					<xsl:if test="@dataparent">
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapse </xsl:if>
				</xsl:attribute>
				<xsl:if test="@dataparent">
					<xsl:attribute name="data-parent">#<xsl:value-of select="@dataparent"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:if test="@label">
					<legend>
						<xsl:value-of select="@label"/>
						<xsl:if test="@label_right">
							<div class="panel-info"
   								style="float:right">
								<xsl:value-of select="@label_right"/>
							</div>
						</xsl:if>
					</legend>
				</xsl:if>
				<xsl:call-template name="basic_generate_component_responsive"/>
			</fieldset>
		</xsl:if>
	</xsl:template>
	<xsl:template match="fieldsetexpanded_responsive">
		<xsl:if test="@size_responsive and not(@only_fieldset)">
			<div>
				<xsl:attribute name="class">
					<xsl:value-of select="@size_responsive"/><![CDATA[ ]]>form-group
					<xsl:if test="@only-expanded">
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
					<xsl:if test="@only-collapsed">
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
				</xsl:attribute>
				<fieldset>
					<xsl:attribute name="id">
						<xsl:value-of select="@name"/>
					</xsl:attribute>
					<xsl:attribute name="class">
						<xsl:if test="@class_responsive">
							<xsl:value-of select="@class_responsive"/>
						</xsl:if>
						<xsl:text><![CDATA[ ]]></xsl:text>shadow<xsl:text><![CDATA[ ]]></xsl:text>
						<xsl:if test="not(@visible) or not(@visible='true')">hidden</xsl:if>
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>expand <xsl:if test="@dataparent">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapse </xsl:if>
					</xsl:attribute>
					<xsl:if test="@dataparent">
						<xsl:attribute name="data-parent">#<xsl:value-of select="@dataparent"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@label">
						<legend>
							<xsl:value-of select="@label"/>
							<div class="panel-info"
   								style="float:right">
								<xsl:if test="@label_right">
									<xsl:value-of select="@label_right"/>
								</xsl:if>
								<a>
									<xsl:attribute name="href">javascript:toogleExpandedFieldset('<xsl:value-of select="@name"/>'); </xsl:attribute>
									<div>
										<xsl:attribute name="id">
											<xsl:value-of select="@name"/>_button</xsl:attribute>
										<xsl:attribute name="class">button_fieldset</xsl:attribute>
									</div>
								</a>
							</div>
						</legend>
					</xsl:if>
					<xsl:call-template name="basic_generate_component_responsive"/>
				</fieldset>
			</div>
		</xsl:if>
		<xsl:if test="not(@size_responsive and not(@only_fieldset))">
			<fieldset>
				<xsl:attribute name="id">
					<xsl:value-of select="@name"/>
				</xsl:attribute>
				<xsl:attribute name="class">
					<xsl:if test="@size_responsive">
						<xsl:value-of select="@size_responsive"/>
					</xsl:if>
					<xsl:text><![CDATA[ ]]></xsl:text>
					<xsl:if test="@class_responsive">
						<xsl:value-of select="@class_responsive"/>
					</xsl:if>
					<xsl:text><![CDATA[ ]]></xsl:text>
					<xsl:if test="not(@visible) or not(@visible='true')">hidden</xsl:if>
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>expand <xsl:if test="@dataparent">
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapse </xsl:if>
				</xsl:attribute>
				<xsl:if test="@dataparent">
					<xsl:attribute name="data-parent">#<xsl:value-of select="@dataparent"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:if test="@label">
					<legend>
						<xsl:value-of select="@label"/>
						<div class="panel-info"
   							style="float:right">
							<xsl:if test="@label_right">
								<xsl:value-of select="@label_right"/>
							</xsl:if>
							<a>
								<xsl:attribute name="href">javascript:toogleExpandedFieldset('<xsl:value-of select="@name"/>'); </xsl:attribute>
								<div>
									<xsl:attribute name="id">
										<xsl:value-of select="@name"/>_button</xsl:attribute>
									<xsl:attribute name="class">button_fieldset</xsl:attribute>
								</div>
							</a>
						</div>
					</legend>
				</xsl:if>
				<xsl:call-template name="basic_generate_component_responsive"/>
			</fieldset>
		</xsl:if>
		<script>showExpandedFieldset('<xsl:value-of select="@name"/>'); <xsl:if test="@onstart-collapse='true'">toogleExpandedFieldset('<xsl:value-of select="@name"/>'); </xsl:if>
		</script>
	</xsl:template>
	<xsl:template match="column_responsive">
		<xsl:choose>
			<xsl:when test="@zoomtofit">
				<div>
					<xsl:if test="@zoomtofit">
						<xsl:attribute name="id">
							<xsl:value-of select="@zoomtofit"/>_father</xsl:attribute>
					</xsl:if>
					<xsl:attribute name="class">
						<xsl:value-of select="@size_responsive"/>
						<xsl:if test="@only-expanded">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
						<xsl:if test="@only-collapsed">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
					</xsl:attribute>
					<div>
						<xsl:attribute name="id">
							<xsl:value-of select="@zoomtofit"/>_size</xsl:attribute>
						<xsl:attribute name="style">display:inline-block;width:<xsl:value-of select="@zoomtofit_width"/>px; </xsl:attribute>
						<div>
							<xsl:call-template name="basic_generate_component_responsive"/>
						</div>
					</div>
				</div>
				<script>zoomToFitHorizontal('<xsl:value-of select="@zoomtofit"/>_father','<xsl:value-of select="@zoomtofit"/>_size'); $(window).off('<xsl:value-of select="@zoomtofit"/>_father'); $(window).resize('<xsl:value-of select="@zoomtofit"/>_father',function(){ zoomToFitHorizontal('<xsl:value-of select="@zoomtofit"/>_father','<xsl:value-of select="@zoomtofit"/>_size'); }); </script>
			</xsl:when>
			<xsl:when test="@tobottom">
				<div>
					<xsl:attribute name="class">
						<xsl:value-of select="@size_responsive"/>
						<xsl:if test="@only-expanded">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
						<xsl:if test="@only-collapsed">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
					</xsl:attribute>
					<div>
						<xsl:attribute name="class">to-bottom-header</xsl:attribute>
						<xsl:attribute name="style">width:100%;height:100%;</xsl:attribute>
						<div>
							<xsl:call-template name="basic_generate_component_responsive">
								<xsl:with-param name="specialClass">to-bottom</xsl:with-param>
							</xsl:call-template>
						</div>
					</div>
				</div>
			</xsl:when>
			<xsl:otherwise>
				<div>
					<xsl:attribute name="class">
						<xsl:value-of select="@size_responsive"/>
						<xsl:if test="@only-expanded">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
					</xsl:attribute>
					<div>
						<xsl:call-template name="basic_generate_component_responsive"/>
					</div>
				</div>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:if test="@onCalculate and @isCalculeOthersFields">
			<script>
				<xsl:value-of select="@onCalculate"/>; </script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="tabpanel_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:attribute name="class">
				<xsl:value-of select="@size_responsive"/>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<input type="hidden">
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:attribute name="name">
					<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:attribute name="value">
					<xsl:value-of select="@tab_select"/>
				</xsl:attribute>
			</input>
			<div>
				<xsl:attribute name="class">
					<xsl:choose>
						<xsl:when test="@visible and (@visible='true')">form-group<xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
						</xsl:when>
						<xsl:otherwise>form-group hidden</xsl:otherwise>
					</xsl:choose>
					<xsl:if test="@only-expanded">
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
					<xsl:if test="@only-collapsed">
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
				</xsl:attribute>
				<xsl:if test="@label_lateral">
					<label>
						<xsl:attribute name="for">
							<xsl:value-of select="$fullname"/>
						</xsl:attribute>
						<xsl:if test="@label_class">
							<xsl:attribute name="class">
								<xsl:value-of select="@label_class"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:value-of select="@label_lateral"/>
					</label>
				</xsl:if>
				<ul>
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_ul</xsl:attribute>
					<xsl:if test="/page/header/savehelp">
						<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="@name"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',0); </xsl:attribute>
					</xsl:if>
					<xsl:if test="@class_responsive">
						<xsl:attribute name="class">
							<xsl:value-of select="@class_responsive"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@role">
						<xsl:attribute name="role">
							<xsl:value-of select="@role"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:for-each select="tab">
						<xsl:call-template name="tab_header_responsive">
							<xsl:with-param name="fullname">
								<xsl:value-of select="$fullname"/>
							</xsl:with-param>
						</xsl:call-template>
					</xsl:for-each>
				</ul>
				<div class="tab-content">
					<xsl:for-each select="tab_responsive">
						<xsl:call-template name="tab_content_responsive"/>
					</xsl:for-each>
				</div>
				<script>registerMeta('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']); selectJSTab('<xsl:value-of select="$fullname"/>'); </script>
			</div>
		</div>
	</xsl:template>
	<xsl:template name="tab_content_responsive">
		<div>
			<xsl:attribute name="id">
				<xsl:value-of select="@id"/>
			</xsl:attribute>
			<xsl:attribute name="class">tab-pane fade</xsl:attribute>
			<div>
				<xsl:call-template name="basic_generate_component_responsive"/>
			</div>
		</div>
	</xsl:template>
	<xsl:template name="tab_header_responsive">
		<xsl:param name="fullname"
         			select="''"/>
		<xsl:choose>
			<xsl:when test="@model='dropdown'">
				<li class="dropdown">
					<a data-toggle="dropdown"
 						class="dropdown-toggle">
						<xsl:value-of select="@title"
            							disable-output-escaping="yes"/>
						<b class="caret"/>
					</a>
					<ul class="dropdown-menu">
						<xsl:for-each select="tab">
							<xsl:call-template name="tab_header_responsive"/>
						</xsl:for-each>
					</ul>
				</li>
			</xsl:when>
			<xsl:otherwise>
				<li>
					<a data-toggle="tab">
						<xsl:attribute name="href">#<xsl:value-of select="@id"/>
						</xsl:attribute>
						<xsl:attribute name="onclick">
							<xsl:if test="@ondemand='true'">selectTab('#<xsl:value-of select="$fullname"/>','<xsl:value-of select="@id"/>'); <xsl:for-each select="link">
									<xsl:for-each select="action">
										<xsl:call-template name="generate_action_invokation"/>
									</xsl:for-each>
								</xsl:for-each>
							</xsl:if>
							<xsl:if test="@ondemand!='true'">if (!selectTab('#<xsl:value-of select="$fullname"/>','<xsl:value-of select="@id"/>')) { <xsl:for-each select="link">
									<xsl:for-each select="action">
										<xsl:call-template name="generate_action_invokation"/>
									</xsl:for-each>
								</xsl:for-each>} </xsl:if>
						</xsl:attribute>
						<xsl:value-of select="@title"/>
					</a>
				</li>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="zone">
		<DIV>
			<xsl:if test="@name">
				<xsl:attribute name="id">
					<xsl:value-of select="@name"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@class_responsive">
				<xsl:attribute name="class">
					<xsl:value-of select="@class_responsive"/>
					<xsl:if test="@only-expanded">
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
					<xsl:if test="@only-collapsed">
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@role">
				<xsl:attribute name="role">
					<xsl:value-of select="@role"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="with_title"
              					select="'true'"/>
			</xsl:call-template>
		</DIV>
		<xsl:if test="@affix">
			<script>affixObject('<xsl:value-of select="@name"/>'); </script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="zone_row">
		<div id="row"
   			onscroll="renderHelp();">
			<xsl:call-template name="basic_generate_component_responsive"/>
		</div>
	</xsl:template>
	<xsl:template match="win_list_canvas">
		<xsl:call-template name="basic_generate_composite"/>
	</xsl:template>
	<xsl:template match="datetime_component">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<DIV>
			<xsl:call-template name="basic_generate_component"/>
			<INPUT type="hidden">
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:attribute name="name">
					<xsl:value-of select="$fullname"/>
				</xsl:attribute>
			</INPUT>
		</DIV>
		<xsl:call-template name="basic_generate_form_component_scripts_out"/>
	</xsl:template>
	<xsl:template match="date_chooser_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="fullnamecontrol">dgf_<xsl:value-of select="@form_name"/>_fd_<xsl:value-of select="@name"/>_control</xsl:variable>
		<div>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:value-of select="@size_responsive"/><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:attribute name="style">
				<xsl:if test="@size_responsive&gt;6">max-width:200px </xsl:if>
			</xsl:attribute>
			<xsl:if test="@label_lateral">
				<xsl:if test="@size_responsive!='inline_component'">
					<label>
						<xsl:attribute name="for">
							<xsl:value-of select="$fullname"/>
						</xsl:attribute>
						<xsl:if test="@label_class">
							<xsl:attribute name="class">
								<xsl:value-of select="@label_class"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:value-of select="@label_lateral"/>
					</label>
				</xsl:if>
			</xsl:if>
			<div>
				<xsl:attribute name="class">
					<xsl:if test="@field_class">
						<xsl:value-of select="@field_class"/>
					</xsl:if>
				</xsl:attribute>
				<div>
					<xsl:attribute name="class">input-group date</xsl:attribute>
					<xsl:attribute name="style">
						<xsl:call-template name="generate_inline_style_margin_and_padding"/>
					</xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="$fullnamecontrol"/>
					</xsl:attribute>
					<input type='text'>
						<xsl:attribute name="class">form-control input-sm <xsl:if test="@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
						<xsl:attribute name="value">
							<xsl:value-of select="text/."/>
						</xsl:attribute>
						<xsl:if test="@placeholder">
							<xsl:attribute name="placeholder">
								<xsl:value-of select="@placeholder"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:call-template name="basic_generate_form_component_responsive">
							<xsl:with-param name="margin_padding">false</xsl:with-param>
						</xsl:call-template>
					</input>
					<xsl:if test="@label_lateral">
						<xsl:if test="@size_responsive='inline_component'">
							<label>
								<xsl:attribute name="for">
									<xsl:value-of select="$fullname"/>
								</xsl:attribute>
								<xsl:if test="@label_class">
									<xsl:attribute name="class">
										<xsl:value-of select="@label_class"/>
									</xsl:attribute>
								</xsl:if>
								<xsl:value-of select="@label_lateral"/>
							</label>
						</xsl:if>
					</xsl:if>
					<xsl:if test="@visible and (@visible='true') and @editable='true'">
						<span class="input-group-addon">
							<xsl:attribute name="onclick">openDataTimePicker('<xsl:value-of select="$fullnamecontrol"/>'); </xsl:attribute>
							<span class="glyphicon glyphicon-calendar"/>
						</span>
						<xsl:if test="constraints/@required!='true'">
							<span class="input-group-addon">
								<xsl:attribute name="onclick">$('#<xsl:value-of select="$fullname"/>').val('');<xsl:if test="@detect_changes='true'">setChangeInputs(true);</xsl:if>
								</xsl:attribute>
								<span class="glyphicon glyphicon-remove"/>
							</span>
						</xsl:if>
					</xsl:if>
				</div>
			</div>
		</div>
		<script charset="ISO-8859-1">initializeDataTimePicker('<xsl:value-of select="$fullname"/>'<xsl:if test="@options">,{<xsl:value-of select="@options"/>}</xsl:if>); </script>
		<xsl:call-template name="basic_generate_form_component_scripts_out"/>
	</xsl:template>
	<xsl:template match="interval_date_chooser_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="fullnamefrom">
			<xsl:if test="@propname_from!=''">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@propname_from"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="fullnameto">
			<xsl:if test="@propname_to!=''">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@propname_to"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="fullnamecontrol">dgf_<xsl:value-of select="@form_name"/>_fd_<xsl:value-of select="@name"/>_ctrl</xsl:variable>
		<div>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:value-of select="@size_responsive"/><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:attribute name="style">
				<xsl:if test="@size_responsive&gt;6">max-width:200px </xsl:if>
			</xsl:attribute>
			<xsl:if test="@label_lateral">
				<xsl:if test="@size_responsive!='inline_component'">
					<label>
						<xsl:attribute name="for">
							<xsl:value-of select="$fullname"/>
						</xsl:attribute>
						<xsl:if test="@label_class">
							<xsl:attribute name="class">
								<xsl:value-of select="@label_class"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:value-of select="@label_lateral"/>
					</label>
				</xsl:if>
			</xsl:if>
			<div>
				<xsl:attribute name="class">
					<xsl:if test="@field_class">
						<xsl:value-of select="@field_class"/>
					</xsl:if>
				</xsl:attribute>
				<xsl:if test="@propname_from!=''">
					<input type='hidden'>
						<xsl:attribute name="id">
							<xsl:value-of select="$fullnamefrom"/>
						</xsl:attribute>
					</input>
				</xsl:if>
				<xsl:if test="@propname_to!=''">
					<input type='hidden'>
						<xsl:attribute name="id">
							<xsl:value-of select="$fullnameto"/>
						</xsl:attribute>
					</input>
				</xsl:if>
				<div>
					<xsl:attribute name="class">input-group </xsl:attribute>
					<xsl:attribute name="style">
						<xsl:call-template name="generate_inline_style_margin_and_padding"/>
					</xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="$fullnamecontrol"/>
					</xsl:attribute>
					<input type='text'>
						<xsl:attribute name="class">form-control input-sm <xsl:if test="@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
						<xsl:if test="text/.!=' - '">
							<xsl:attribute name="value">
								<xsl:value-of select="text/."/>
							</xsl:attribute>
						</xsl:if>
						<xsl:if test="@placeholder">
							<xsl:attribute name="placeholder">
								<xsl:value-of select="@placeholder"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:call-template name="basic_generate_form_component_responsive">
							<xsl:with-param name="margin_padding">false</xsl:with-param>
							<xsl:with-param name="actionOnEnter">false</xsl:with-param>
						</xsl:call-template>
						<xsl:if test="@needenter='false'">
							<xsl:attribute name="onKeydown">if (isClosedIntervalDataTimePicker('<xsl:value-of select="$fullnamecontrol"/>')) { var out= gaOnKey(event); return out; } </xsl:attribute>
						</xsl:if>
					</input>
					<xsl:if test="@label_lateral">
						<xsl:if test="@size_responsive='inline_component'">
							<label>
								<xsl:attribute name="for">
									<xsl:value-of select="$fullname"/>
								</xsl:attribute>
								<xsl:if test="@label_class">
									<xsl:attribute name="class">
										<xsl:value-of select="@label_class"/>
									</xsl:attribute>
								</xsl:if>
								<xsl:value-of select="@label_lateral"/>
							</label>
						</xsl:if>
					</xsl:if>
					<xsl:if test="@visible and (@visible='true') and @editable='true'">
						<span class="input-group-addon">
							<xsl:attribute name="onclick">openIntervalDataTimePicker('<xsl:value-of select="$fullnamecontrol"/>'); </xsl:attribute>
							<span class="glyphicon glyphicon-calendar"/>
						</span>
						<xsl:if test="constraints/@required!='true'">
							<span class="input-group-addon">
								<xsl:attribute name="onclick">clearIntervalDataTimePicker('<xsl:value-of select="$fullnamecontrol"/>','<xsl:value-of select="$fullnamefrom"/>','<xsl:value-of select="$fullnameto"/>',<xsl:value-of select="@detect_changes"/>); </xsl:attribute>
								<span class="glyphicon glyphicon-remove"/>
							</span>
						</xsl:if>
					</xsl:if>
				</div>
			</div>
		</div>
		<script charset="ISO-8859-1">initializeIntervalDataTimePicker('<xsl:value-of select="$fullname"/>','<xsl:value-of select="$fullnamefrom"/>','<xsl:value-of select="$fullnameto"/>',{<xsl:value-of select="@options"/>},'<xsl:value-of select="@out_format"/>',<xsl:value-of select="@detect_changes"/>); </script>
		<xsl:call-template name="basic_generate_form_component_scripts_out"/>
		<xsl:if test="@two_prop='true'">
			<script charset="ISO-8859-1">registerNoConstraint('<xsl:value-of select="$fullnamefrom"/>',['<xsl:value-of select="$fullnamefrom"/>']); registerNoConstraint('<xsl:value-of select="$fullnameto"/>',['<xsl:value-of select="$fullnameto"/>']); </script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="dropdowncombo_responsive_noform">
		<xsl:call-template name="dropdowncombo_responsive_basic">
			<xsl:with-param name="inForm"
              				select="'false'"/>
		</xsl:call-template>
	</xsl:template>
	<xsl:template match="dropdowncombo_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="onchange_event_var">
			<xsl:value-of select="@onchange_event"/>
		</xsl:variable>
		<xsl:variable name="onclick_event_var">
			<xsl:value-of select="@onclick_event"/>
		</xsl:variable>
		<div>
			<xsl:if test="/page/header/savehelp">
				<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
				</xsl:variable>
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
			</xsl:if>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:value-of select="@size_responsive"/><![CDATA[ ]]>form-group <xsl:if test="@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:call-template name="dropdowncombo_responsive_basic">
				<xsl:with-param name="inForm"
              					select="'true'"/>
			</xsl:call-template>
		</div>
		<script>
			<xsl:if test="@connect_control">subscribeControlConnect( '<xsl:value-of select="@connect_control"/>', function() { hideSelectMetaItemsCombo('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); hideSelectMetaItemsCombo('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone_to','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); }); </xsl:if>
		</script>
	</xsl:template>
	<xsl:template name="dropdowncombo_responsive_basic">
		<xsl:param name="inForm"
         			select="'true'"/>
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="onchange_event_var">
			<xsl:value-of select="@onchange_event"/>
		</xsl:variable>
		<xsl:variable name="onclick_event_var">
			<xsl:value-of select="@onclick_event"/>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="@size_responsive='inline_component'">
				<xsl:if test="$inForm='false'">
					<xsl:if test="@label_lateral">
						<label>
							<xsl:attribute name="for">
								<xsl:value-of select="$fullname"/>
							</xsl:attribute>
							<xsl:if test="@label_class">
								<xsl:attribute name="class">
									<xsl:value-of select="@label_class"/>
								</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="@label_lateral"/>
						</label>
					</xsl:if>
				</xsl:if>
				<xsl:call-template name="dropdowncombo_responsive_internal">
					<xsl:with-param name="inForm"
              						select="'true'"/>
					<xsl:with-param name="fullname">
						<xsl:value-of select="$fullname"/>
					</xsl:with-param>
					<xsl:with-param name="onchange_event_var">
						<xsl:value-of select="$onchange_event_var"/>
					</xsl:with-param>
					<xsl:with-param name="onclick_event_var">
						<xsl:value-of select="$onclick_event_var"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:if test="@label_lateral">
					<xsl:if test="$inForm!='false'">
						<label>
							<xsl:attribute name="for">
								<xsl:value-of select="$fullname"/>
							</xsl:attribute>
							<xsl:if test="@label_class">
								<xsl:attribute name="class">
									<xsl:value-of select="@label_class"/>
								</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="@label_lateral"/>
						</label>
					</xsl:if>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:if test="(@label_lateral or labelicon) and (not(@size_responsive) or @size_responsive!='inline_component')">
					<label>
						<xsl:attribute name="for">
							<xsl:value-of select="$fullname"/>
						</xsl:attribute>
						<xsl:if test="@label_class">
							<xsl:attribute name="class">
								<xsl:value-of select="@label_class"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:if test="content_layout/@halignment">
							<xsl:attribute name="style">text-align:<xsl:value-of select="content_layout/@halignment"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:value-of select="@label_lateral"/>
						<xsl:if test="labelicon">
							<xsl:for-each select="labelicon">
								<xsl:call-template name="render_icon"/>
							</xsl:for-each>
						</xsl:if>
					</label>
				</xsl:if>
				<xsl:call-template name="dropdowncombo_responsive_internal">
					<xsl:with-param name="inForm"
              						select="'true'"/>
					<xsl:with-param name="fullname">
						<xsl:value-of select="$fullname"/>
					</xsl:with-param>
					<xsl:with-param name="onchange_event_var">
						<xsl:value-of select="$onchange_event_var"/>
					</xsl:with-param>
					<xsl:with-param name="onclick_event_var">
						<xsl:value-of select="$onclick_event_var"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:if test="(@label_lateral or labelicon) and (@size_responsive and @size_responsive='inline_component')">
					<label>
						<xsl:attribute name="for">
							<xsl:value-of select="$fullname"/>
						</xsl:attribute>
						<xsl:if test="@label_class">
							<xsl:attribute name="class">
								<xsl:value-of select="@label_class"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:if test="content_layout/@halignment">
							<xsl:attribute name="style">text-align:<xsl:value-of select="content_layout/@halignment"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:value-of select="@label_lateral"/>
						<xsl:if test="labelicon">
							<xsl:for-each select="labelicon">
								<xsl:call-template name="render_icon"/>
							</xsl:for-each>
						</xsl:if>
					</label>
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="dropdowncombo_responsive_internal">
		<xsl:param name="inForm"
         			select="'true'"/>
		<xsl:param name="fullname"
         			select="''"/>
		<xsl:variable name="onchange_event_var">
			<xsl:value-of select="@onchange_event"/>
		</xsl:variable>
		<xsl:variable name="onclick_event_var">
			<xsl:value-of select="@onclick_event"/>
		</xsl:variable>
		<div>
			<xsl:attribute name="class">
				<xsl:if test="@field_class and (@label_lateral or labelicon)">
					<xsl:value-of select="@field_class"/>
				</xsl:if>
			</xsl:attribute>
			<div>
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_div</xsl:attribute>
				<xsl:attribute name="class">input-group <![CDATA[ ]]>dropdown dropdowncombo </xsl:attribute>
				<input type="hidden">
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<xsl:attribute name="value">
						<xsl:value-of select="combo/selected/@value"/>
					</xsl:attribute>
					<xsl:call-template name="basic_generate_form_component_scripts_in">
						<xsl:with-param name="onchange_event_var">
							<xsl:value-of select="$onchange_event_var"/>
						</xsl:with-param>
						<xsl:with-param name="onclick_event_var">
							<xsl:value-of select="$onclick_event_var"/>
						</xsl:with-param>
					</xsl:call-template>
				</input>
				<input type="text">
					<xsl:attribute name="class">
						<xsl:if test="$inForm='true'">form-control </xsl:if><![CDATA[ ]]>input-sm <![CDATA[ ]]>dropdown-toggle <xsl:if test="@class_responsive">
							<xsl:value-of select="@class_responsive"/>
						</xsl:if>
					</xsl:attribute>
					<xsl:if test="combo/@editable='true'">
						<xsl:attribute name="onclick">$('span[id="<xsl:value-of select="$fullname"/>_button"]').click(); </xsl:attribute>
						<xsl:attribute name="onkeydown">$('span[id="<xsl:value-of select="$fullname"/>_button"]').click(); </xsl:attribute>
					</xsl:if>
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_data</xsl:attribute>
					<xsl:attribute name="value">
						<xsl:value-of select="combo/selected/@description"/>
					</xsl:attribute>
					<xsl:attribute name="readonly">readonly</xsl:attribute>
					<xsl:attribute name="style">
						<xsl:call-template name="generate_inline_style_responsive">
							<xsl:with-param name="style_attr">
								<xsl:if test="combo/@editable='true' and @required!='true'">background:#fff; </xsl:if>
							</xsl:with-param>
						</xsl:call-template>
					</xsl:attribute>
				</input>
				<div class="dropdown-menu">
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_dropdown</xsl:attribute>
					<div class="input-group input-group-sm">
						<span class="input-group-addon">
							<xsl:attribute name="id">
								<xsl:value-of select="$fullname"/>_sizing-addon</xsl:attribute>
							<i class="fa fa-search"
 								style="float:left"/>
						</span>
						<input type="text"
     							class="search form-control"
     							autocomplete="off">
							<xsl:attribute name="id">
								<xsl:value-of select="$fullname"/>_search</xsl:attribute>
							<xsl:attribute name="aria-describedby">
								<xsl:value-of select="$fullname"/>_sizing-addon</xsl:attribute>
							<xsl:attribute name="oninput">javascript:dropdowncombofilter('<xsl:value-of select="$fullname"/>'); </xsl:attribute>
							<xsl:for-each select="onblur">
								<xsl:call-template name="generate_action_onblur"/>
							</xsl:for-each>
						</input>
					</div>
					<div>
						<xsl:attribute name="style">max-height:<xsl:value-of select="@size_rows*20"/>px;overflow:auto;margin-bottom:10px; </xsl:attribute>
						<ul>
							<xsl:for-each select="combo/item">
								<li>
									<a>
										<xsl:attribute name="data-value">
											<xsl:value-of select="@id"/>
										</xsl:attribute>
										<xsl:attribute name="data-real_id">
											<xsl:value-of select="@real_id"/>
										</xsl:attribute>
										<xsl:if test="(../@show_key='true') and not(@real_id='')">
											<xsl:value-of select="@real_id"/>- </xsl:if>
										<xsl:value-of select="@description"/>
									</a>
								</li>
							</xsl:for-each>
						</ul>
					</div>
					<div>
						<xsl:call-template name="basic_generate_component_responsive"/>
						<script>var zForm = new ZForm('<xsl:value-of select="@zobject"/>',1); registerObjectProvider('<xsl:value-of select="@obj_provider"/>', zForm); </script>
					</div>
				</div>
				<xsl:if test="combo/@editable='true'">
					<span role="button"
    						data-toggle="dropdown"
    						aria-haspopup="true"
    						aria-expanded="false">
						<xsl:attribute name="class">input-group-addon <![CDATA[ ]]>dropdown-toggle <xsl:if test="combo/@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
						<xsl:attribute name="id">
							<xsl:value-of select="$fullname"/>_button</xsl:attribute>
						<xsl:if test="combo/@force_focus">
							<xsl:attribute name="autofocus">true</xsl:attribute>
						</xsl:if>
						<span class="caret"/>
					</span>
				</xsl:if>
			</div>
		</div>
		<xsl:call-template name="basic_generate_form_component_scripts_out"/>
		<script>dropdowncomboinitialize('<xsl:value-of select="$fullname"/>'); </script>
	</xsl:template>
	<xsl:template match="dropdownwinlov_responsive_noform">
		<xsl:call-template name="dropdownwinlov_responsive_basic">
			<xsl:with-param name="inForm"
              				select="'false'"/>
		</xsl:call-template>
	</xsl:template>
	<xsl:template match="dropdownwinlov_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="onchange_event_var">
			<xsl:value-of select="@onchange_event"/>
		</xsl:variable>
		<xsl:variable name="onclick_event_var">
			<xsl:value-of select="@onclick_event"/>
		</xsl:variable>
		<div>
			<xsl:if test="/page/header/savehelp">
				<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
				</xsl:variable>
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
			</xsl:if>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:value-of select="@size_responsive"/><![CDATA[ ]]>form-group <xsl:if test="@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:call-template name="dropdownwinlov_responsive_basic">
				<xsl:with-param name="inForm"
              					select="'true'"/>
			</xsl:call-template>
		</div>
		<script>
			<xsl:if test="@connect_control">subscribeControlConnect( '<xsl:value-of select="@connect_control"/>', function() { hideSelectMetaItemsLov('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); hideSelectMetaItemsLov('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone_to','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); }); </xsl:if>
		</script>
	</xsl:template>
	<xsl:template name="dropdownwinlov_responsive_basic">
		<xsl:param name="inForm"
         			select="'true'"/>
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="onchange_event_var">
			<xsl:value-of select="@onchange_event"/>
		</xsl:variable>
		<xsl:variable name="onclick_event_var">
			<xsl:value-of select="@onclick_event"/>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="@size_responsive='inline_component'">
				<xsl:if test="$inForm='false'">
					<xsl:if test="@label_lateral">
						<label>
							<xsl:attribute name="for">
								<xsl:value-of select="$fullname"/>
							</xsl:attribute>
							<xsl:if test="@label_class">
								<xsl:attribute name="class">
									<xsl:value-of select="@label_class"/>
								</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="@label_lateral"/>
						</label>
					</xsl:if>
				</xsl:if>
				<xsl:call-template name="dropdownwinlov_responsive_internal">
					<xsl:with-param name="inForm"
              						select="'true'"/>
					<xsl:with-param name="fullname">
						<xsl:value-of select="$fullname"/>
					</xsl:with-param>
					<xsl:with-param name="onchange_event_var">
						<xsl:value-of select="$onchange_event_var"/>
					</xsl:with-param>
					<xsl:with-param name="onclick_event_var">
						<xsl:value-of select="$onclick_event_var"/>
					</xsl:with-param>
				</xsl:call-template>
				<xsl:if test="@label_lateral">
					<xsl:if test="$inForm!='false'">
						<label>
							<xsl:attribute name="for">
								<xsl:value-of select="$fullname"/>
							</xsl:attribute>
							<xsl:if test="@label_class">
								<xsl:attribute name="class">
									<xsl:value-of select="@label_class"/>
								</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="@label_lateral"/>
						</label>
					</xsl:if>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:if test="@label_lateral">
					<label>
						<xsl:attribute name="for">
							<xsl:value-of select="$fullname"/>
						</xsl:attribute>
						<xsl:if test="@label_class">
							<xsl:attribute name="class">
								<xsl:value-of select="@label_class"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:value-of select="@label_lateral"/>
					</label>
				</xsl:if>
				<xsl:call-template name="dropdownwinlov_responsive_internal">
					<xsl:with-param name="inForm"
              						select="'true'"/>
					<xsl:with-param name="fullname">
						<xsl:value-of select="$fullname"/>
					</xsl:with-param>
					<xsl:with-param name="onchange_event_var">
						<xsl:value-of select="$onchange_event_var"/>
					</xsl:with-param>
					<xsl:with-param name="onclick_event_var">
						<xsl:value-of select="$onclick_event_var"/>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="dropdownwinlov_responsive_internal">
		<xsl:param name="inForm"
         			select="'true'"/>
		<xsl:param name="fullname"
         			select="''"/>
		<xsl:variable name="onchange_event_var">
			<xsl:value-of select="@onchange_event"/>
		</xsl:variable>
		<xsl:variable name="onclick_event_var">
			<xsl:value-of select="@onclick_event"/>
		</xsl:variable>
		<div>
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>_div</xsl:attribute>
			<xsl:attribute name="class">input-group <![CDATA[ ]]>dropdown dropdownwinlov </xsl:attribute>
			<input type="hidden">
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_isopen</xsl:attribute>
				<xsl:attribute name="value">
					<xsl:value-of select="@opened"/>
				</xsl:attribute>
			</input>
			<input type="hidden">
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_zobject</xsl:attribute>
				<xsl:attribute name="value">
					<xsl:value-of select="@zobject"/>
				</xsl:attribute>
			</input>
			<input type="hidden">
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:attribute name="value">
					<xsl:value-of select="winlov/item/@id"/>
				</xsl:attribute>
				<xsl:call-template name="basic_generate_form_component_scripts_in">
					<xsl:with-param name="onchange_event_var">
						<xsl:value-of select="$onchange_event_var"/>
					</xsl:with-param>
					<xsl:with-param name="onclick_event_var">
						<xsl:value-of select="$onclick_event_var"/>
					</xsl:with-param>
				</xsl:call-template>
			</input>
			<input type="text">
				<xsl:attribute name="class">
					<xsl:if test="$inForm='true'">form-control </xsl:if><![CDATA[ ]]>input-sm <![CDATA[ ]]>dropdown-toggle <xsl:if test="@class_responsive">
						<xsl:value-of select="@class_responsive"/>
					</xsl:if>
				</xsl:attribute>
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_data</xsl:attribute>
				<xsl:attribute name="value">
					<xsl:value-of select="winlov/item/@description"/>
				</xsl:attribute>
				<xsl:attribute name="readonly">readonly</xsl:attribute>
				<xsl:attribute name="style">
					<xsl:call-template name="generate_inline_style_responsive">
						<xsl:with-param name="style_attr">
							<xsl:if test="winlov/@editable='true' and @required!='true'">background:#fff; </xsl:if>
						</xsl:with-param>
					</xsl:call-template>
				</xsl:attribute>
			</input>
			<div class="modal modal-component"
   				role="dialog"
   				data-backdrop="false">
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_dropdown</xsl:attribute>
				<div class="modal-dialog-component">
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_dialog</xsl:attribute>
					<div class="modal-content">
						<div class="modal-body modal-body-component dropdown-component">
							<div class="input-group input-group-sm">
								<span class="input-group-addon">
									<xsl:attribute name="id">
										<xsl:value-of select="$fullname"/>_sizing-addon</xsl:attribute>
									<i class="fa fa-search"
 										style="float:left"/>
								</span>
								<input type="text"
     									class="search form-control"
     									autocomplete="off">
									<xsl:attribute name="id">
										<xsl:value-of select="$fullname"/>_search</xsl:attribute>
									<xsl:attribute name="aria-describedby">
										<xsl:value-of select="$fullname"/>_sizing-addon</xsl:attribute>
									<xsl:for-each select="onblur">
										<xsl:call-template name="generate_action_onblur"/>
									</xsl:for-each>
								</input>
							</div>
							<div>
								<xsl:attribute name="style">max-height:<xsl:value-of select="@size_rows*20"/>px;overflow:auto;margin-bottom:10px; </xsl:attribute>
								<ul>
									<xsl:attribute name="id">
										<xsl:value-of select="$fullname"/>_ul</xsl:attribute>
									<xsl:for-each select="winlov/wellknows">
										<li>
											<a tabindex="0">
												<xsl:attribute name="data-value">
													<xsl:value-of select="@id"/>
												</xsl:attribute>
												<xsl:attribute name="data-real_id">
													<xsl:value-of select="@real_id"/>
												</xsl:attribute>
												<xsl:if test="(../@show_key='true') and not(@real_id='')">
													<xsl:value-of select="@real_id"/>- </xsl:if>
												<xsl:value-of select="@description"/>
											</a>
										</li>
									</xsl:for-each>
								</ul>
							</div>
							<div>
								<xsl:call-template name="basic_generate_component_responsive"/>
								<script>var zForm = new ZForm('<xsl:value-of select="@zobject"/>',1); registerObjectProvider('<xsl:value-of select="@obj_provider"/>', zForm); </script>
							</div>
						</div>
					</div>
				</div>
			</div>
			<xsl:for-each select="action_edit">
				<span>
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_view</xsl:attribute>
					<xsl:attribute name="class">input-group-addon <![CDATA[ ]]>dropdown-toggle <xsl:if test="winlov/@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
					<xsl:attribute name="onclick">
						<xsl:for-each select="action">
							<xsl:call-template name="generate_action_invokation">
								<xsl:with-param name="dynamic_owner">
									<xsl:value-of select="$fullname"/>
								</xsl:with-param>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:attribute>
					<xsl:attribute name="type">button</xsl:attribute>
					<i class="fa fa-edit"/>
				</span>
			</xsl:for-each>
			<xsl:if test="winlov/@editable='true'">
				<span role="button"
    					data-toggle="modal">
					<xsl:attribute name="data-target">#<xsl:value-of select="$fullname"/>_dropdown </xsl:attribute>
					<xsl:attribute name="class">input-group-addon <![CDATA[ ]]>dropdown-toggle <xsl:if test="winlov/@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_button</xsl:attribute>
					<xsl:if test="winlov/@force_focus">
						<xsl:attribute name="autofocus">true</xsl:attribute>
					</xsl:if>
					<span class="caret"/>
				</span>
				<span role="button">
					<xsl:attribute name="class">input-group-addon</xsl:attribute>
					<xsl:attribute name="onclick">dropdownwinlovclear('<xsl:value-of select="$fullname"/>'); </xsl:attribute>
					<span>
						<xsl:attribute name="class">glyphicon glyphicon-remove</xsl:attribute>
					</span>
				</span>
			</xsl:if>
		</div>
		<xsl:call-template name="basic_generate_form_component_scripts_out"/>
		<script>registerNoConstraint('<xsl:value-of select="$fullname"/>_isopen',['<xsl:value-of select="$fullname"/>_isopen']); registerNoConstraint('<xsl:value-of select="$fullname"/>_zobject',['<xsl:value-of select="$fullname"/>_zobject']); dropdownWinlovinitialize('<xsl:value-of select="$fullname"/>',<xsl:value-of select="@is_open"/>,<xsl:value-of select="winlov/@editable"/>,'<xsl:value-of select="@form_objectOwner"/>','<xsl:value-of select="@form_objectAction"/>','<xsl:value-of select="@form_objprovider"/>',<xsl:value-of select="winlov/@show_key"/>); </script>
	</xsl:template>
	<xsl:template match="combobox_responsive_noform">
		<xsl:call-template name="combobox_responsive_basic">
			<xsl:with-param name="inForm"
              				select="'false'"/>
		</xsl:call-template>
	</xsl:template>
	<xsl:template match="combobox_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="onchange_event_var">
			<xsl:value-of select="@onchange_event"/>
		</xsl:variable>
		<xsl:variable name="onclick_event_var">
			<xsl:value-of select="@onclick_event"/>
		</xsl:variable>
		<div>
			<xsl:if test="/page/header/savehelp">
				<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
				</xsl:variable>
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
			</xsl:if>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:value-of select="@size_responsive"/><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:call-template name="combobox_responsive_basic">
				<xsl:with-param name="inForm"
              					select="'true'"/>
			</xsl:call-template>
		</div>
		<script>
			<xsl:if test="@connect_control">subscribeControlConnect( '<xsl:value-of select="@connect_control"/>', function() { hideSelectMetaItemsCombo('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); hideSelectMetaItemsCombo('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone_to','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); }); </xsl:if>
		</script>
	</xsl:template>
	<xsl:template name="combobox_responsive_basic">
		<xsl:param name="inForm"
         			select="'true'"/>
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="onchange_event_var">
			<xsl:value-of select="@onchange_event"/>
		</xsl:variable>
		<xsl:variable name="onclick_event_var">
			<xsl:value-of select="@onclick_event"/>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="@size_responsive='inline_component'">
				<xsl:if test="$inForm='false'">
					<xsl:if test="@label_lateral">
						<label>
							<xsl:attribute name="for">
								<xsl:value-of select="$fullname"/>
							</xsl:attribute>
							<xsl:if test="@label_class">
								<xsl:attribute name="class">
									<xsl:value-of select="@label_class"/>
								</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="@label_lateral"/>
						</label>
					</xsl:if>
				</xsl:if>
				<SELECT>
					<xsl:attribute name="class">
						<xsl:if test="$inForm='true'">form-control input-sm </xsl:if>
						<xsl:if test="$inForm='false'">input-sm </xsl:if>
						<xsl:if test="@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
					<xsl:attribute name="size">
						<xsl:value-of select="@size_rows"/>
					</xsl:attribute>
					<xsl:if test="@force_focus">
						<xsl:attribute name="autofocus">true</xsl:attribute>
					</xsl:if>
					<xsl:for-each select="onblur">
						<xsl:call-template name="generate_action_onblur"/>
					</xsl:for-each>
					<xsl:call-template name="basic_generate_form_component_responsive">
						<xsl:with-param name="onchange_event_var">
							<xsl:value-of select="$onchange_event_var"/>
						</xsl:with-param>
						<xsl:with-param name="onclick_event_var">
							<xsl:value-of select="$onclick_event_var"/>
						</xsl:with-param>
					</xsl:call-template>
					<xsl:call-template name="do_combo_box_options"/>
				</SELECT>
				<xsl:if test="@label_lateral">
					<xsl:if test="$inForm!='false'">
						<label>
							<xsl:attribute name="for">
								<xsl:value-of select="$fullname"/>
							</xsl:attribute>
							<xsl:if test="@label_class">
								<xsl:attribute name="class">
									<xsl:value-of select="@label_class"/>
								</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="@label_lateral"/>
						</label>
					</xsl:if>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:if test="@label_lateral">
					<label>
						<xsl:attribute name="for">
							<xsl:value-of select="$fullname"/>
						</xsl:attribute>
						<xsl:if test="@label_class">
							<xsl:attribute name="class">
								<xsl:value-of select="@label_class"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:value-of select="@label_lateral"/>
					</label>
				</xsl:if>
				<div>
					<xsl:attribute name="class">
						<xsl:if test="@field_class">
							<xsl:value-of select="@field_class"/>
						</xsl:if>
					</xsl:attribute>
					<div>
						<xsl:attribute name="class">input-group </xsl:attribute>
						<SELECT>
							<xsl:attribute name="class">
								<xsl:if test="$inForm='true'">form-control input-sm </xsl:if>
								<xsl:if test="$inForm='false'">input-sm </xsl:if>
								<xsl:if test="@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
							<xsl:attribute name="size">
								<xsl:value-of select="@size_rows"/>
							</xsl:attribute>
							<xsl:if test="@force_focus">
								<xsl:attribute name="autofocus">true</xsl:attribute>
							</xsl:if>
							<xsl:for-each select="onblur">
								<xsl:call-template name="generate_action_onblur"/>
							</xsl:for-each>
							<xsl:call-template name="basic_generate_form_component_responsive">
								<xsl:with-param name="onchange_event_var">
									<xsl:value-of select="$onchange_event_var"/>
								</xsl:with-param>
								<xsl:with-param name="onclick_event_var">
									<xsl:value-of select="$onclick_event_var"/>
								</xsl:with-param>
							</xsl:call-template>
							<xsl:call-template name="do_combo_box_options"/>
						</SELECT>
					</div>
				</div>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:call-template name="basic_generate_form_component_scripts_out"/>
	</xsl:template>
	<xsl:template match="form">
		<form role="form">
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="isForm"
              					select="'true'"/>
			</xsl:call-template>
		</form>
	</xsl:template>
	<xsl:template match="form_responsive">
		<div>
			<xsl:if test="@role">
				<xsl:attribute name="role">
					<xsl:value-of select="@role"/>
				</xsl:attribute>
			</xsl:if>
			<DIV>
				<xsl:call-template name="basic_generate_component_responsive">
					<xsl:with-param name="isForm"
              						select="'true'"/>
					<xsl:with-param name="with_title"
              						select="'true'"/>
				</xsl:call-template>
			</DIV>
		</div>
	</xsl:template>
	<xsl:template match="notebook">
		<xsl:call-template name="basic_generate_composite"/>
		<xsl:for-each select="panel/notebook_tab[@state='selected']">
			<xsl:for-each select="action">
				<script charset="ISO-8859-1">storeAjaxNotebook('<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="@id"/>'); </script>
			</xsl:for-each>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="link">
		<xsl:choose>
			<xsl:when test="@mode='button'">
				<button>
					<xsl:attribute name="id">
						<xsl:value-of select="@name"/>
					</xsl:attribute>
					<xsl:if test="@class_responsive">
						<xsl:attribute name="class">
							<xsl:value-of select="@class_responsive"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@role">
						<xsl:attribute name="role">
							<xsl:value-of select="@role"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="not(@key='')">
						<xsl:attribute name="accesskey">
							<xsl:value-of select="@key"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="/page/header/savehelp">
						<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="@name"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',2); </xsl:attribute>
					</xsl:if>
					<xsl:if test="@opens_new_window and @opens_new_window='true'">
						<xsl:attribute name="target">_blank</xsl:attribute>
					</xsl:if>
					<xsl:attribute name="data-toggle">tooltip</xsl:attribute>
					<xsl:attribute name="title">
						<xsl:value-of select="@tooltip"/>
					</xsl:attribute>
					<xsl:call-template name="generate_action_on_click"/>
					<xsl:if test="@title">
						<div>
							<xsl:if test="icon">
								<xsl:call-template name="render_icon">
									<xsl:with-param name="style_attr">padding-right: 5px;<xsl:if test="@style_image">
											<xsl:value-of select="@style_image"/>
										</xsl:if>
									</xsl:with-param>
								</xsl:call-template>
							</xsl:if>
							<xsl:choose>
								<xsl:when test="@class_position_icon='pull-right nodiv'">
									<xsl:if test="@title">
										<strong>
											<xsl:value-of select="@title"/>
										</strong>
									</xsl:if>
									<xsl:if test="@subtitle">
										<span class="pull-right text-muted">
											<em>
												<xsl:value-of select="@subtitle"/>
											</em>
										</span>
									</xsl:if>
								</xsl:when>
								<xsl:otherwise>
									<div>
										<xsl:if test="@class_position_icon and icon">
											<xsl:attribute name="class">
												<xsl:value-of select="@class_position_icon"/>
											</xsl:attribute>
										</xsl:if>
										<xsl:if test="@title">
											<strong>
												<xsl:value-of select="@title"/>
											</strong>
										</xsl:if>
										<xsl:if test="@subtitle">
											<span class="pull-right text-muted">
												<em>
													<xsl:value-of select="@subtitle"/>
												</em>
											</span>
										</xsl:if>
									</div>
								</xsl:otherwise>
							</xsl:choose>
						</div>
						<div>
							<xsl:value-of select="@label"
            								disable-output-escaping="yes"/>
						</div>
					</xsl:if>
					<xsl:if test="not(@title) and @label">
						<xsl:if test="icon">
							<xsl:call-template name="render_icon">
								<xsl:with-param name="style_attr">padding-right: 5px;<xsl:if test="@style_image">
										<xsl:value-of select="@style_image"/>
									</xsl:if>
								</xsl:with-param>
							</xsl:call-template>
						</xsl:if>
						<xsl:choose>
							<xsl:when test="@class_position_icon='pull-right nodiv'">
								<xsl:value-of select="@label"/>
							</xsl:when>
							<xsl:otherwise>
								<div>
									<xsl:if test="@class_position_icon and icon">
										<xsl:attribute name="class">
											<xsl:value-of select="@class_position_icon"/>
										</xsl:attribute>
									</xsl:if>
									<xsl:value-of select="@label"/>
								</div>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:if>
					<xsl:if test="not(@title) and not(@label)">
						<xsl:if test="icon">
							<xsl:call-template name="render_icon">
								<xsl:with-param name="style_attr">
									<xsl:if test="@style_image">
										<xsl:value-of select="@style_image"/>
									</xsl:if>
								</xsl:with-param>
							</xsl:call-template>
						</xsl:if>
					</xsl:if>
				</button>
				<xsl:if test="@is_submit='true'">
					<script charset="ISO-8859-1">setAnchorSubmit('<xsl:value-of select="@name"/>'); </script>
				</xsl:if>
				<xsl:if test="@is_cancel='true'">
					<script charset="ISO-8859-1">setAnchorCancel('<xsl:value-of select="@name"/>'); adaptChanges( ) ; </script>
				</xsl:if>
				<xsl:if test="@functionKey">
					<script charset="ISO-8859-1">setAnchorF(<xsl:value-of select="@functionKey"/>,'<xsl:value-of select="@name"/>'); </script>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<a>
					<xsl:attribute name="id">
						<xsl:value-of select="@name"/>
					</xsl:attribute>
					<xsl:if test="@class_responsive">
						<xsl:attribute name="class">
							<xsl:value-of select="@class_responsive"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@role">
						<xsl:attribute name="role">
							<xsl:value-of select="@role"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="not(@key='')">
						<xsl:attribute name="accesskey">
							<xsl:value-of select="@key"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="/page/header/savehelp">
						<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="@name"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',2); </xsl:attribute>
					</xsl:if>
					<xsl:attribute name="style">
						<xsl:call-template name="generate_inline_style_responsive"/>
					</xsl:attribute>
					<xsl:attribute name="data-toggle">tooltip</xsl:attribute>
					<xsl:attribute name="title">
						<xsl:value-of select="@tool_tip"/>
					</xsl:attribute>
					<xsl:if test="@is_submit='true'">
						<xsl:attribute name="type">submit</xsl:attribute>
					</xsl:if>
					<xsl:if test="@is_cancel='true'">
						<xsl:attribute name="type">reset</xsl:attribute>
					</xsl:if>
					<xsl:if test="@opens_new_window and @opens_new_window='true'">
						<xsl:attribute name="target">_blank</xsl:attribute>
					</xsl:if>
					<xsl:call-template name="generate_action_on_click"/>
					<xsl:if test="@title">
						<div>
							<xsl:if test="icon">
								<xsl:call-template name="render_icon">
									<xsl:with-param name="style_attr">padding-right: 5px;<xsl:if test="@style_image">
											<xsl:value-of select="@style_image"/>
										</xsl:if>
									</xsl:with-param>
								</xsl:call-template>
							</xsl:if>
							<xsl:choose>
								<xsl:when test="@class_position_icon='pull-right nodiv'">
									<xsl:if test="@title">
										<strong>
											<xsl:value-of select="@title"/>
										</strong>
									</xsl:if>
									<xsl:if test="@subtitle">
										<span class="pull-right text-muted">
											<em>
												<xsl:value-of select="@subtitle"/>
											</em>
										</span>
									</xsl:if>
								</xsl:when>
								<xsl:otherwise>
									<div>
										<xsl:if test="@class_position_icon">
											<xsl:attribute name="class">
												<xsl:value-of select="@class_position_icon"/>
											</xsl:attribute>
										</xsl:if>
										<xsl:if test="@title">
											<strong>
												<xsl:value-of select="@title"/>
											</strong>
										</xsl:if>
										<xsl:if test="@subtitle">
											<span class="pull-right text-muted">
												<em>
													<xsl:value-of select="@subtitle"/>
												</em>
											</span>
										</xsl:if>
									</div>
								</xsl:otherwise>
							</xsl:choose>
						</div>
						<div>
							<xsl:value-of select="@label"
            								disable-output-escaping="yes"/>
						</div>
					</xsl:if>
					<xsl:if test="not(@title) and @label">
						<xsl:if test="icon">
							<xsl:call-template name="render_icon">
								<xsl:with-param name="style_attr">padding-right: 5px;<xsl:if test="@style_image">
										<xsl:value-of select="@style_image"/>
									</xsl:if>
								</xsl:with-param>
							</xsl:call-template>
						</xsl:if>
						<xsl:choose>
							<xsl:when test="@class_position_icon='pull-right nodiv'">
								<xsl:value-of select="@label"/>
							</xsl:when>
							<xsl:otherwise>
								<div>
									<xsl:if test="@class_position_icon">
										<xsl:attribute name="class">
											<xsl:value-of select="@class_position_icon"/>
										</xsl:attribute>
									</xsl:if>
									<xsl:value-of select="@label"/>
								</div>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:if>
					<xsl:if test="not(@title) and not(@label)">
						<xsl:if test="icon">
							<xsl:call-template name="render_icon">
								<xsl:with-param name="style_attr">
									<xsl:if test="@style_image">
										<xsl:value-of select="@style_image"/>
									</xsl:if>
								</xsl:with-param>
							</xsl:call-template>
						</xsl:if>
					</xsl:if>
				</a>
				<xsl:if test="@is_submit='true'">
					<script charset="ISO-8859-1">setAnchorSubmit('<xsl:value-of select="@name"/>'); </script>
				</xsl:if>
				<xsl:if test="@is_cancel='true'">
					<script charset="ISO-8859-1">setAnchorCancel('<xsl:value-of select="@name"/>'); adaptChanges( ) ; </script>
				</xsl:if>
				<xsl:if test="@functionKey">
					<script charset="ISO-8859-1">setAnchorF(<xsl:value-of select="@functionKey"/>,'<xsl:value-of select="@name"/>'); </script>
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:for-each select="action">
			<xsl:if test="@target='do-login'">
				<script>prepareLogin();</script>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="navigation_item">
		<a>
			<xsl:attribute name="id">
				<xsl:value-of select="@name"/>
			</xsl:attribute>
			<xsl:attribute name="title">
				<xsl:value-of select="@label"/>
			</xsl:attribute>
			<xsl:if test="@class_responsive">
				<xsl:attribute name="class">
					<xsl:value-of select="@class_responsive"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@role">
				<xsl:attribute name="role">
					<xsl:value-of select="@role"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:attribute name="style">
				<xsl:call-template name="generate_inline_style_responsive"/>
			</xsl:attribute>
			<xsl:if test="/page/header/savehelp">
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="@name"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',2); </xsl:attribute>
			</xsl:if>
			<xsl:choose>
				<xsl:when test="@action">
					<xsl:if test="@confirmation">
						<xsl:attribute name="href">javascript:runAction(this,true,'<xsl:value-of select="@confirmation"/>',null, function(){ <xsl:choose>
								<xsl:when test="@opens_new_window and @opens_new_window='true'">goToMenuActionResponsive('<xsl:value-of select="@action"/>','true'); </xsl:when>
								<xsl:otherwise>goToMenuActionResponsive('<xsl:value-of select="@action"/>','false'); </xsl:otherwise>
							</xsl:choose>},function() {}); </xsl:attribute>
					</xsl:if>
					<xsl:if test="not(@confirmation)">
						<xsl:choose>
							<xsl:when test="@opens_new_window and @opens_new_window='true'">
								<xsl:attribute name="href">javascript:goToMenuActionResponsive('<xsl:value-of select="@action"/>','true')</xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="href">javascript:goToMenuActionResponsive('<xsl:value-of select="@action"/>','false')</xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:if>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="generate_action_on_click"/>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:if test="icon">
				<xsl:call-template name="render_icon">
					<xsl:with-param name="style_attr">padding-right: 5px;<xsl:if test="@style_image">
							<xsl:value-of select="@style_image"/>
						</xsl:if>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:if>
			<div class="nav-label floatingDiv">
				<xsl:value-of select="@label"/>
			</div>
		</a>
		<xsl:if test="@is_submit='true'">
			<script charset="ISO-8859-1">setAnchorSubmit('<xsl:value-of select="@name"/>'); </script>
		</xsl:if>
		<xsl:if test="@is_cancel='true'">
			<script charset="ISO-8859-1">setAnchorCancel('<xsl:value-of select="@name"/>'); adaptChanges( ) ; </script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="navigation_group">
		<li>
			<xsl:attribute name="class">nav-group</xsl:attribute>
			<xsl:if test="@data_toggle">
				<xsl:attribute name="class">
					<xsl:value-of select="@nav_group_class"/>
				</xsl:attribute>
			</xsl:if>
			<a>
				<xsl:attribute name="title">
					<xsl:value-of select="@title"
            						disable-output-escaping="yes"/>
				</xsl:attribute>
				<xsl:if test="@data_toggle">
					<xsl:attribute name="class">dropdown-toggle</xsl:attribute>
					<xsl:attribute name="data-toggle">
						<xsl:value-of select="@data_toggle"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:if test="icon">
					<xsl:call-template name="render_icon">
						<xsl:with-param name="style_attr">padding-right: 5px;</xsl:with-param>
					</xsl:call-template>
				</xsl:if>
				<xsl:if test="not(arrow/icon)">
					<xsl:if test="@title">
						<xsl:value-of select="@title"/>
					</xsl:if>
				</xsl:if>
				<xsl:if test="arrow/icon">
					<xsl:if test="@title">
						<div class="nav-label floatingDiv">
							<xsl:if test="@data_toggle">
								<xsl:value-of select="@title"
            									disable-output-escaping="yes"/>
								<xsl:for-each select="arrow">
									<xsl:call-template name="render_icon"/>
								</xsl:for-each>
							</xsl:if>
							<xsl:if test="not(@data_toggle)">
								<span>
									<xsl:attribute name="class">nav-label</xsl:attribute>
									<xsl:value-of select="@title"
            										disable-output-escaping="yes"/>
									<xsl:for-each select="arrow">
										<xsl:call-template name="render_icon"/>
									</xsl:for-each>
								</span>
							</xsl:if>
						</div>
					</xsl:if>
					<xsl:if test="not(@title)">
						<xsl:if test="@data_toggle">
							<xsl:for-each select="arrow">
								<xsl:call-template name="render_icon"/>
							</xsl:for-each>
						</xsl:if>
						<xsl:if test="not(@data_toggle)">
							<span>
								<xsl:attribute name="class">nav-label</xsl:attribute>
								<xsl:for-each select="arrow">
									<xsl:call-template name="render_icon"/>
								</xsl:for-each>
							</span>
						</xsl:if>
					</xsl:if>
				</xsl:if>
			</a>
			<ul>
				<xsl:if test="@class_responsive">
					<xsl:attribute name="class">
						<xsl:value-of select="@class_responsive"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:if test="@role">
					<xsl:attribute name="role">
						<xsl:value-of select="@role"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:call-template name="basic_generate_component_responsive"/>
			</ul>
		</li>
	</xsl:template>
	<xsl:template match="navigation_group_sidebar">
		<div role="navigation">
			<xsl:if test="@name">
				<xsl:attribute name="id">
					<xsl:value-of select="@name"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@class_navigation">
				<xsl:attribute name="class">
					<xsl:value-of select="@class_navigation"/>
				</xsl:attribute>
			</xsl:if>
			<div>
				<xsl:if test="@name">
					<xsl:attribute name="id">
						<xsl:value-of select="@name"/>_sidebar</xsl:attribute>
				</xsl:if>
				<xsl:if test="@class_navigation_internal">
					<xsl:attribute name="class">
						<xsl:value-of select="@class_navigation_internal"/>
					</xsl:attribute>
				</xsl:if>
				<ul>
					<xsl:if test="@class_responsive">
						<xsl:attribute name="class">
							<xsl:value-of select="@class_responsive"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@role">
						<xsl:attribute name="role">
							<xsl:value-of select="@role"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:call-template name="basic_generate_component_responsive"/>
				</ul>
			</div>
		</div>
	</xsl:template>
	<xsl:template match="history_bar">
		<nav>
			<xsl:if test="@class_responsive">
				<xsl:attribute name="class">
					<xsl:value-of select="@class_responsive"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@role">
				<xsl:attribute name="role">
					<xsl:value-of select="@role"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:call-template name="basic_generate_component_responsive"/>
		</nav>
	</xsl:template>
	<xsl:template match="ol">
		<ol>
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="with_title"
              					select="'true'"/>
			</xsl:call-template>
		</ol>
	</xsl:template>
	<xsl:template match="ul">
		<ul>
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="with_title"
              					select="'true'"/>
			</xsl:call-template>
		</ul>
	</xsl:template>
	<xsl:template match="li">
		<li>
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="with_title"
              					select="'true'"/>
			</xsl:call-template>
		</li>
	</xsl:template>
	<xsl:template match="notebook_tab">
		<div>
			<xsl:attribute name="id">
				<xsl:value-of select="@name"/>
			</xsl:attribute>
			<xsl:if test="/page/header/savehelp">
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="@name"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',2); </xsl:attribute>
			</xsl:if>
			<xsl:attribute name="style">
				<xsl:call-template name="generate_inline_style"/>
			</xsl:attribute>
			<a>
				<xsl:call-template name="basic_generate_action_view"/>
			</a>
		</div>
	</xsl:template>
	<xsl:template match="break">
		<xsl:choose>
			<xsl:when test="@with_line">
				<hr>
					<xsl:if test="@class_responsive">
						<xsl:attribute name="class">
							<xsl:value-of select="@class_responsive"/>
						</xsl:attribute>
					</xsl:if>
				</hr>
			</xsl:when>
			<xsl:when test="not(@appled_by_layout='true')">
				<br/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="chart">
		<img>
			<xsl:attribute name="src">
				<xsl:value-of select="@chart_image_path"/>
			</xsl:attribute>
			<xsl:call-template name="basic_generate_component"/>
		</img>
	</xsl:template>
	<xsl:template match="drawing_chart">
		<div>
			<xsl:call-template name="basic_generate_component">
				<xsl:with-param name="style_attr">overflow:auto;</xsl:with-param>
			</xsl:call-template>
			<img>
				<xsl:attribute name="src">
					<xsl:value-of select="@chart_image_path"/>
				</xsl:attribute>
			</img>
		</div>
	</xsl:template>
	<xsl:template match="gmap">
		<DIV>
			<xsl:call-template name="basic_generate_component"/>
			<xsl:call-template name="basic_render_map"/>
		</DIV>
	</xsl:template>
	<xsl:template match="container">
		<div>
			<xsl:call-template name="basic_generate_component_responsive"/>
			<button type="button"
      				class="navbar-toggle"
      				data-toggle="collapse"
      				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"/>
				<span class="icon-bar"/>
				<span class="icon-bar"/>
			</button>
		</div>
	</xsl:template>
	<xsl:template match="image">
		<xsl:choose>
			<xsl:when test="image/@source='script'">
				<div>
					<xsl:call-template name="generate_action_on_dblclick"/>
					<xsl:call-template name="basic_generate_component"/>
					<xsl:call-template name="render_3d"/>
				</div>
			</xsl:when>
			<xsl:when test="image/@link">
				<a>
					<xsl:attribute name="style">
						<xsl:if test="@label_fontweight">font-weight:<xsl:value-of select="@label_font_weight"/>;</xsl:if>
						<xsl:if test="@label_fontstyle">font-style:<xsl:value-of select="@label_fontstyle"/>;</xsl:if>
						<xsl:if test="@label_fontsize">font-size:<xsl:value-of select="@label_fontsize"/>px;</xsl:if>
					</xsl:attribute>
					<xsl:attribute name="href">
						<xsl:value-of select="image/@link"/>
					</xsl:attribute>
					<xsl:call-template name="basic_generate_component"/>
					<xsl:call-template name="basic_render_image"/>
				</a>
			</xsl:when>
			<xsl:otherwise>
				<DIV>
					<xsl:call-template name="generate_action_on_dblclick"/>
					<xsl:call-template name="basic_generate_component"/>
					<xsl:call-template name="basic_render_image"/>
				</DIV>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="image_responsive">
		<xsl:choose>
			<xsl:when test="@sinDIV">
				<xsl:call-template name="view_image_responsive"/>
			</xsl:when>
			<xsl:otherwise>
				<div>
					<xsl:call-template name="basic_generate_component_responsive">
						<xsl:with-param name="style_attr">width:100%;</xsl:with-param>
					</xsl:call-template>
					<xsl:call-template name="view_image_responsive"/>
				</div>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="flash">
		<DIV>
			<xsl:call-template name="basic_generate_component"/>
			<xsl:call-template name="basic_render_flash"/>
		</DIV>
	</xsl:template>
	<xsl:template match="label_responsive">
		<xsl:if test="@size_responsive">
			<div>
				<xsl:attribute name="class">
					<xsl:value-of select="@size_responsive"/><![CDATA[ ]]>form-group <xsl:if test="@only-expanded">
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
					<xsl:if test="@only-collapsed">
						<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
				</xsl:attribute>
				<div>
					<xsl:attribute name="class">pss-label<![CDATA[ ]]><xsl:if test="@label_class">
							<xsl:value-of select="@label_class"/>
						</xsl:if>
					</xsl:attribute>
					<xsl:call-template name="basic_generate_component_responsive"/>
					<xsl:call-template name="render_compound_label"/>
				</div>
			</div>
		</xsl:if>
		<xsl:if test="not(@size_responsive)">
			<div>
				<xsl:if test="@label_class">
					<xsl:attribute name="class">
						<xsl:value-of select="@label_class"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:call-template name="basic_generate_component_responsive"/>
				<xsl:call-template name="render_compound_label"/>
			</div>
		</xsl:if>
	</xsl:template>
	<xsl:template match="label">
		<div>
			<xsl:call-template name="basic_generate_component"/>
			<xsl:call-template name="render_compound_label"/>
		</div>
	</xsl:template>
	<xsl:template match="dropdown">
		<div>
			<xsl:if test="@class_responsive">
				<xsl:attribute name="class">
					<xsl:value-of select="@class_responsive"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:attribute name="style">
				<xsl:call-template name="generate_inline_style_responsive"/>
			</xsl:attribute>
			<button type="button"
      				data-toggle="dropdown">
				<xsl:if test="@class_button">
					<xsl:attribute name="class">
						<xsl:value-of select="@class_button"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:value-of select="@label_button"/>
				<xsl:if test="@image">
					<i>
						<xsl:attribute name="class">
							<xsl:value-of select="@image"/>
						</xsl:attribute>
					</i>
				</xsl:if>
				<span>
					<xsl:if test="arrow/icon/@file">
						<xsl:attribute name="class">
							<xsl:value-of select="arrow/icon/@file"/>
						</xsl:attribute>
					</xsl:if>
				</span>
			</button>
			<ul class="dropdown-menu pull-right">
				<xsl:apply-templates select="*"/>
			</ul>
		</div>
	</xsl:template>
	<xsl:template match="dropdown_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
						</xsl:if><![CDATA[ ]]>form-group \ </xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:if test="(not(@size_responsive) or @size_responsive!='inline_component')">
				<label>
					<xsl:attribute name="for">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<xsl:if test="@label_class">
						<xsl:attribute name="class">
							<xsl:value-of select="@label_class"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="content_layout/@halignment">
						<xsl:attribute name="style">text-align:<xsl:value-of select="content_layout/@halignment"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="@label_lateral"/>
					<xsl:if test="labelicon">
						<xsl:for-each select="labelicon">
							<xsl:call-template name="render_icon"/>
						</xsl:for-each>
					</xsl:if>
				</label>
			</xsl:if>
			<div>
				<xsl:if test="@class_responsive">
					<xsl:attribute name="class">
						<xsl:value-of select="@class_responsive"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:attribute name="style">width:100%</xsl:attribute>
				<button type="button"
      					data-toggle="dropdown">
					<xsl:if test="@editable='false'">
						<xsl:attribute name="disabled">disabled</xsl:attribute>
					</xsl:if>
					<xsl:attribute name="style">width:100%</xsl:attribute>
					<xsl:if test="@class_button">
						<xsl:attribute name="class">
							<xsl:value-of select="@class_button"/>
						</xsl:attribute>
					</xsl:if>
					<span class="pull-left">
						<xsl:value-of select="@label_button"/>
						<xsl:if test="@image">
							<i>
								<xsl:attribute name="class">
									<xsl:value-of select="@image"/>
								</xsl:attribute>
							</i>
						</xsl:if>
					</span>
					<span>
						<xsl:if test="arrow/icon/@file">
							<xsl:attribute name="class">pull-right <xsl:value-of select="arrow/icon/@file"/>
							</xsl:attribute>
						</xsl:if>
					</span>
				</button>
				<ul class="dropdown-menu pull-left">
					<xsl:apply-templates select="*"/>
				</ul>
			</div>
		</div>
	</xsl:template>
	<xsl:template match="win_action_bar">
		<DIV>
			<xsl:call-template name="basic_generate_component_responsive"/>
		</DIV>
	</xsl:template>
	<xsl:template match="win_action_bar_internal">
		<DIV class="hidden">
			<DIV>
				<xsl:call-template name="basic_generate_component_responsive"/>
			</DIV>
		</DIV>
	</xsl:template>
	<xsl:template match="tree_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<DIV>
			<xsl:call-template name="basic_generate_component_responsive"/>
			<xsl:for-each select="alert">
				<xsl:if test="@msg">
					<script type="text/javascript"
      						charset="ISO-8859-1">showErrRefresh('inf','','<xsl:value-of select="@msg"/>'); </script>
				</xsl:if>
			</xsl:for-each>
			<xsl:if test="@scroll">
				<script type="text/javascript"
      					charset="ISO-8859-1">adaptativeScroll('scroll_body','scroll_header','<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="@scroll"/>',''); </script>
			</xsl:if>
			<xsl:call-template name="tree_table_responsive"/>
		</DIV>
	</xsl:template>
	<xsl:template match="win_list_json">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<DIV>
			<xsl:call-template name="basic_generate_component_responsive"/>
			<xsl:for-each select="alert">
				<xsl:if test="@msg">
					<script type="text/javascript"
      						charset="ISO-8859-1">showErrRefresh('inf','','<xsl:value-of select="@msg"/>'); </script>
				</xsl:if>
			</xsl:for-each>
			<xsl:if test="@scroll">
				<script type="text/javascript"
      					charset="ISO-8859-1">adaptativeScroll('scroll_body','scroll_header','<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="@scroll"/>',''); </script>
				<xsl:call-template name="win_list_table_json"/>
			</xsl:if>
		</DIV>
	</xsl:template>
	<xsl:template match="win_list_bigdata">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<DIV>
			<xsl:call-template name="basic_generate_component_responsive"/>
			<xsl:for-each select="alert">
				<xsl:if test="@msg">
					<script type="text/javascript"
      						charset="ISO-8859-1">alert('<xsl:value-of select="@msg"/>'); </script>
				</xsl:if>
			</xsl:for-each>
			<xsl:if test="@scroll">
				<script type="text/javascript"
      					charset="ISO-8859-1">adaptativeScroll('scroll_body','scroll_header','<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="@scroll"/>',''); </script>
				<xsl:call-template name="win_list_table_bigdata"/>
			</xsl:if>
		</DIV>
	</xsl:template>
	<xsl:template match="win_list">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<DIV>
			<xsl:call-template name="basic_generate_component_responsive"/>
			<xsl:for-each select="alert">
				<xsl:if test="@msg">
					<script type="text/javascript"
      						charset="ISO-8859-1">showErrRefresh('inf','','<xsl:value-of select="@msg"/>'); </script>
				</xsl:if>
			</xsl:for-each>
			<xsl:if test="@scroll">
				<script type="text/javascript"
      					charset="ISO-8859-1">adaptativeScroll('scroll_body','scroll_header','<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="@scroll"/>',''); </script>
				<xsl:call-template name="win_list_table"/>
			</xsl:if>
		</DIV>
	</xsl:template>
	<xsl:template match="win_grid">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<DIV>
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="specialClass">table-responsive</xsl:with-param>
			</xsl:call-template>
			<xsl:for-each select="alert">
				<xsl:if test="@msg">
					<script type="text/javascript">showErrRefresh('inf','','<xsl:value-of select="@msg"/>'); </script>
				</xsl:if>
			</xsl:for-each>
			<xsl:if test="@scroll">
				<script type="text/javascript">adaptativeScroll('scroll_body','scroll_header','<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="@scroll"/>',''); </script>
			</xsl:if>
			<xsl:call-template name="win_grid_table"/>
		</DIV>
	</xsl:template>
	<!-- <xsl:template match="win_grid_expand">
	<DIV>
		<xsl:call-template name="basic_generate_component_responsive"/>
		
		<xsl:for-each select="alert">
			<xsl:if test="@msg">
			    <script type="text/javascript">
			    	showErrRefresh('inf','','<xsl:value-of select="@msg"/>'); 
			   	</script>
			</xsl:if>
		</xsl:for-each>
		<xsl:if test="@scroll">
			<script type="text/javascript">
				adaptativeScroll('scroll_body','scroll_header','<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="@scroll"/>','');
			</script>
		</xsl:if>
	    <xsl:call-template name="win_grid_expand_table" />
	</DIV>

</xsl:template> -->
	<xsl:template name="win_list_table">
		<xsl:param name="level"
         			select="'_base'"/>
		<xsl:if test="showAction">
			<script TYPE="POSTSCRIPT">
				<xsl:for-each select="showAction">
					<xsl:for-each select="action">
						<xsl:call-template name="generate_action_invokation"/>
					</xsl:for-each>
				</xsl:for-each>
			</script>
		</xsl:if>
		<script>if ($('#actionbarinternal_<xsl:value-of select="@obj_provider"/>').html()!="") { $("#<xsl:value-of select="@obj_provider"/>_toolbar").html($('#actionbarinternal_<xsl:value-of select="@obj_provider"/>').html()); $('#actionbarinternal_<xsl:value-of select="@obj_provider"/>').html(""); } <xsl:if test="@modifiedonserver='true'">setChangeInputs(true); </xsl:if>
		</script>
		<xsl:call-template name="generate_table">
			<xsl:with-param name="level">
				<xsl:value-of select="$level"/>
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="tree_table_responsive">
		<xsl:param name="level"
         			select="'_base'"/>
		<xsl:if test="showAction">
			<script TYPE="POSTSCRIPT">
				<xsl:for-each select="showAction">
					<xsl:for-each select="action">
						<xsl:call-template name="generate_action_invokation"/>
					</xsl:for-each>
				</xsl:for-each>
			</script>
		</xsl:if>
		<script>$(document).ready(function() { $("#<xsl:value-of select="@obj_provider"/>_table").treeFy({ expanderTemplate:'<xsl:value-of select="@expanderTemplate"/>', indentTemplate:'<xsl:value-of select="@indentTemplate"/>', expanderExpandedClass:'<xsl:value-of select="@expanderExpandedClass"/>', expanderCollapsedClass:'<xsl:value-of select="@expanderCollapsedClass"/>', treeColumn: <xsl:value-of select="@treeColumn"/>, initStatusClass: '<xsl:value-of select="@initStatusClass"/>', collapseAnimateCallback:function(row){row.hide();}, expandAnimateCallback:function(row){row.show();} }); if ($('#actionbarinternal_<xsl:value-of select="@obj_provider"/>').html()!="") { $("#<xsl:value-of select="@obj_provider"/>_toolbar").html($('#actionbarinternal_<xsl:value-of select="@obj_provider"/>').html()); $('#actionbarinternal_<xsl:value-of select="@obj_provider"/>').html(""); } } ); </script>
		<div>
			<xsl:call-template name="generate_table">
				<xsl:with-param name="level">
					<xsl:value-of select="$level"/>
				</xsl:with-param>
			</xsl:call-template>
		</div>
	</xsl:template>
	<xsl:template name="generate_table">
		<xsl:param name="level"
         			select="'_base'"/>
		<div class="table-responsive">
			<xsl:attribute name="id">
				<xsl:value-of select="@obj_provider"/>_container</xsl:attribute>
			<table>
				<xsl:attribute name="class">
					<xsl:value-of select="@class_table_responsive"/>
				</xsl:attribute>
				<xsl:attribute name="id">
					<xsl:value-of select="@obj_provider"/>_table</xsl:attribute>
				<xsl:if test="$level='_base'">
					<xsl:attribute name="onKeyDown">return rsKeyPressTable(this,event);</xsl:attribute>
					<xsl:if test="@zoomtofit">
						<xsl:attribute name="style">width:<xsl:value-of select="@zoomtofit_width"/>px; </xsl:attribute>
					</xsl:if>
				</xsl:if>
				<xsl:if test="not($level='_base')">
					<xsl:attribute name="width">100%</xsl:attribute>
				</xsl:if>
				<THEAD id="scroll_header">
					<xsl:if test="@with_group='true'">
						<xsl:for-each select="level_column">
							<tr>
								<xsl:for-each select="grupo_column">
									<th>
										<xsl:attribute name="colspan">0<xsl:value-of select="@span"/>
										</xsl:attribute>
										<xsl:if test="@foreground or @background or @width">
											<xsl:attribute name="style">
												<xsl:if test="@width">width:<xsl:value-of select="@width"/>;min-width:<xsl:value-of select="@width"/>;</xsl:if>
												<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
												<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>
											</xsl:attribute>
										</xsl:if>
										<xsl:call-template name="dragdrop_component"/>
										<xsl:choose>
											<xsl:when test="not(title/.='SINGRUPOCOLUMNA')">
												<xsl:value-of select="title/."/>
											</xsl:when>
											<xsl:otherwise/>
										</xsl:choose>
									</th>
								</xsl:for-each>
							</tr>
						</xsl:for-each>
					</xsl:if>
					<tr>
						<xsl:for-each select="header">
							<xsl:if test="@has_subdetail='true'">
								<th>
									<xsl:attribute name="style">width:30px;</xsl:attribute>
									<div>
										<xsl:attribute name="onclick">hide_show_expandall_subdetail(); </xsl:attribute>
										<xsl:call-template name="render_icon"/>
									</div>
								</th>
							</xsl:if>
							<xsl:for-each select="column">
								<th>
									<xsl:variable name="columnname">
										<xsl:value-of select="../../@obj_provider"/>_<xsl:value-of select="position()"/>
									</xsl:variable>
									<xsl:attribute name="id">
										<xsl:value-of select="$columnname"/>
									</xsl:attribute>
									<xsl:attribute name="data-pos">
										<xsl:value-of select="position()"/>
									</xsl:attribute>
									<xsl:attribute name="class">
										<xsl:value-of select="@class_table_header"/>
										<xsl:if test="@is_html='true'">
											<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>column-html </xsl:if>
									</xsl:attribute>
									<xsl:if test="@foreground or @background or @width">
										<xsl:attribute name="style">
											<xsl:if test="@width">width:<xsl:value-of select="@width"/>;min-width:<xsl:value-of select="@width"/>;</xsl:if>
											<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
											<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>text-align: center; </xsl:attribute>
									</xsl:if>
									<xsl:call-template name="dragdrop_component"/>
									<xsl:if test="../../@allowSortedColumns='true' and not(../../@with_group='true')">
										<xsl:attribute name="ondrop">dropColumn(event)</xsl:attribute>
										<xsl:attribute name="ondragover">allowDropColumn(event)</xsl:attribute>
										<xsl:attribute name="ondragend">dragLeave(event)</xsl:attribute>
									</xsl:if>
									<xsl:if test="$level='_base'">
										<xsl:call-template name="generate_action_on_click"/>
									</xsl:if>
									<xsl:if test="../../@allowSortedColumns='true' and not(../../@with_group='true')">
										<span>
											<xsl:attribute name="id">
												<xsl:value-of select="$columnname"/>_resize</xsl:attribute>
											<xsl:attribute name="class">pull-right grabbable</xsl:attribute>
											<xsl:attribute name="draggable">true</xsl:attribute>
											<xsl:attribute name="ondragstart">dragColumn(event)</xsl:attribute>
											<xsl:attribute name="ondragleave">dragLeave(event)</xsl:attribute>
											<i class="fa fa-grip-vertical"
 												style="color:#00000052"/>
										</span>
									</xsl:if>
									<xsl:choose>
										<xsl:when test="@type='JWINFORM'">
											<xsl:call-template name="basic_generate_component_responsive">
												<xsl:with-param name="isForm"
              													select="'true'"/>
											</xsl:call-template>
										</xsl:when>
										<xsl:otherwise>
											<span>
												<xsl:if test="not(hover/.='')">
													<xsl:attribute name="title">
														<xsl:value-of select="hover/."/>
													</xsl:attribute>
												</xsl:if>
												<xsl:choose>
													<xsl:when test="html_data and not(html_data/.='')">
														<xsl:value-of select="html_data/."
            															disable-output-escaping="yes"/>
													</xsl:when>
													<xsl:when test="not(title/.='')">
														<xsl:value-of select="title/."/>
													</xsl:when>
													<xsl:when test="@type='VOID'"/>
													<xsl:otherwise>-</xsl:otherwise>
												</xsl:choose>
											</span>
										</xsl:otherwise>
									</xsl:choose>
									<xsl:for-each select="control">
										<xsl:apply-templates select="*"/>
									</xsl:for-each>
									<script type="text/javascript"
      										charset="ISO-8859-1">$("#<xsl:value-of select="$columnname"/>").resizable({ resizeHeight : false }); </script>
								</th>
							</xsl:for-each>
						</xsl:for-each>
					</tr>
				</THEAD>
				<TBODY id="scroll_body">
					<xsl:for-each select="rows/row">
						<xsl:variable name="name_row">row_<xsl:value-of select="@rowpos"/>
						</xsl:variable>
						<tr>
							<xsl:attribute name="rowpos">
								<xsl:value-of select="@rowpos"/>
							</xsl:attribute>
							<xsl:if test="../../@is_line_select='true'">
								<xsl:for-each select="dblclick">
									<xsl:call-template name="generate_action_on_double_click">
										<xsl:with-param name="row_id">
											<xsl:value-of select="../@zobject"/>
										</xsl:with-param>
										<xsl:with-param name="key">
											<xsl:value-of select="../@key"/>
										</xsl:with-param>
										<xsl:with-param name="descr">
											<xsl:value-of select="../@descr"/>
										</xsl:with-param>
									</xsl:call-template>
								</xsl:for-each>
							</xsl:if>
							<xsl:if test="../../@is_line_select='true' and ../../@use_context_menu='true'">
								<xsl:for-each select="rightclick">
									<xsl:call-template name="generate_action_on_right_click"/>
								</xsl:for-each>
							</xsl:if>
							<xsl:if test="@rowpos mod 2 = 0">
								<xsl:attribute name="class">odd</xsl:attribute>
							</xsl:if>
							<xsl:if test="@rowpos mod 2 = 1">
								<xsl:attribute name="class">even</xsl:attribute>
							</xsl:if>
							<xsl:if test="@zobject">
								<xsl:attribute name="id">
									<xsl:value-of select="@zobject"/>
								</xsl:attribute>
							</xsl:if>
							<xsl:if test="@key">
								<xsl:attribute name="title">
									<xsl:value-of select="@key"/>;<xsl:value-of select="@descr"/>
								</xsl:attribute>
							</xsl:if>
							<xsl:if test="@id_tree">
								<xsl:attribute name="data-node">
									<xsl:value-of select="@id_tree"/>
								</xsl:attribute>
							</xsl:if>
							<xsl:if test="@id_tree_parent">
								<xsl:attribute name="data-pnode">
									<xsl:value-of select="@id_tree_parent"/>
								</xsl:attribute>
							</xsl:if>
							<xsl:if test="$level='_base'">
								<xsl:call-template name="dragdrop_component"/>
								<xsl:if test='smplclick'>
									<xsl:if test="../../@has_action_bar='true'">
										<xsl:choose>
											<xsl:when test="@key">
												<xsl:attribute name="onclick">return dc6(this,event,function(that) { <xsl:call-template name="generate_action_invokation">
														<xsl:with-param name="that">that</xsl:with-param>
													</xsl:call-template>},'<xsl:value-of select="@key"/>','<xsl:value-of select="@descr"/>'); </xsl:attribute>
											</xsl:when>
											<xsl:otherwise>
												<xsl:if test="../../@is_line_select='true'">
													<xsl:attribute name="onclick">return dc5(this,event,function(that) { <xsl:call-template name="generate_action_invokation">
															<xsl:with-param name="that">that</xsl:with-param>
														</xsl:call-template>}); </xsl:attribute>
												</xsl:if>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:if>
								</xsl:if>
							</xsl:if>
							<xsl:if test="../../header/@has_subdetail='true'">
								<td>
									<xsl:call-template name="set_class"/>
									<xsl:if test="details">
										<div>
											<xsl:attribute name="onclick">hide_show_subdetail("<xsl:value-of select="$name_row"/>"); </xsl:attribute>
											<xsl:for-each select="details">
												<xsl:call-template name="render_icon"/>
											</xsl:for-each>
										</div>
										<script>register_subdetail("<xsl:value-of select="$name_row"/>")</script>
									</xsl:if>
								</td>
							</xsl:if>
							<xsl:for-each select="d">
								<xsl:if test="not(@visible) or @visible='true'">
									<xsl:if test="not(@skip) or not(@skip='true')">
										<xsl:variable name="thispos"/>
										<xsl:call-template name="generate_win_list_cell">
											<xsl:with-param name="thispos">
												<xsl:value-of select="position()"/>
											</xsl:with-param>
											<xsl:with-param name="rowpos">
												<xsl:value-of select="../@rowpos"/>
											</xsl:with-param>
										</xsl:call-template>
									</xsl:if>
								</xsl:if>
							</xsl:for-each>
						</tr>
						<xsl:for-each select="details">
							<tr>
								<xsl:attribute name="name">
									<xsl:value-of select="$name_row"/>
								</xsl:attribute>
								<xsl:attribute name="id">
									<xsl:value-of select="$name_row"/>
								</xsl:attribute>
								<xsl:attribute name="style">display:none;</xsl:attribute>
								<td>
									<xsl:if test="@axis">
										<xsl:attribute name="axis">
											<xsl:value-of select="@axis"/>
										</xsl:attribute>
									</xsl:if>
									<xsl:attribute name="colspan">
										<xsl:value-of select="@colspan"/>
									</xsl:attribute>
									<xsl:call-template name="set_class"/>
									<xsl:call-template name="win_list_table">
										<xsl:with-param name="level">_sub</xsl:with-param>
									</xsl:call-template>
								</td>
							</tr>
						</xsl:for-each>
					</xsl:for-each>
				</TBODY>
				<xsl:if test="footer">
					<TFOOT id="scroll_foot">
						<tr>
							<xsl:if test="../../header/@has_subdetail='true'">
								<td>
									<xsl:if test="@foreground or @background">
										<xsl:attribute name="style">
											<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
											<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>
										</xsl:attribute>
									</xsl:if>
									<xsl:call-template name="dragdrop_component"/>
									<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
								</td>
							</xsl:if>
							<xsl:for-each select="footer/column">
								<td>
									<xsl:if test="$level='_base'">
										<xsl:attribute name="style">text-align:<xsl:value-of select="@halignment"/>; <xsl:if test="@foreground or @background">
												<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
												<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>
											</xsl:if>
										</xsl:attribute>
										<xsl:if test="not(dblclickDrag)">
											<xsl:attribute name="onClick">hsc(this);</xsl:attribute>
										</xsl:if>
										<xsl:call-template name="dragdrop_component"/>
										<xsl:choose>
											<xsl:when test="html_data and not(html_data/.='')">
												<xsl:value-of select="html_data/."
            													disable-output-escaping="yes"/>
											</xsl:when>
											<xsl:when test="not(value/.='')">
												<xsl:value-of select="value/."/>
											</xsl:when>
											<xsl:otherwise>
												<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:if>
									<xsl:if test="@type='JWINFORM'">
										<xsl:call-template name="basic_generate_component_responsive">
											<xsl:with-param name="isForm"
              												select="'true'"/>
										</xsl:call-template>
									</xsl:if>
								</td>
							</xsl:for-each>
						</tr>
					</TFOOT>
				</xsl:if>
			</table>
		</div>
		<xsl:if test="$level='_base'">
			<xsl:if test="@has_action_bar='true'">
				<xsl:variable name="v_provider">
					<xsl:value-of select="@obj_provider"/>
				</xsl:variable>
				<script>AAInit(); <xsl:for-each select="actions/action">
						<xsl:if test="allowed_win">AAAdd('<xsl:value-of select="@id"/>',<xsl:value-of select="@win"/>,[<xsl:for-each select="allowed_win">'<xsl:value-of select="@id"/>',</xsl:for-each>null],<xsl:value-of select="@multiple"/>); </xsl:if>
						<xsl:if test="not_allowed_win">AAANodd('<xsl:value-of select="@id"/>',<xsl:value-of select="@win"/>,[<xsl:for-each select="not_allowed_win">'<xsl:value-of select="@id"/>',</xsl:for-each>null],<xsl:value-of select="@multiple"/>); </xsl:if>
					</xsl:for-each>AAFin('<xsl:value-of select="@obj_provider"/>_table','<xsl:value-of select="//win_action_bar[@provider=$v_provider]/@name"/>','<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="rows/@zobject"/>',<xsl:value-of select="@is_multiple_select"/>,<xsl:value-of select="@is_line_select"/>,'<xsl:value-of select="select_info/@has_more_selection"/>',false,null); initializeTable('<xsl:value-of select="@obj_provider"/>_table','<xsl:value-of select="@obj_provider"/>_container',<xsl:value-of select="@is_line_select"/>); <xsl:for-each select="default_actions">
						<xsl:for-each select="default_action">
							<xsl:choose>
								<xsl:when test="@name='dblclick'">$('#<xsl:value-of select="../../@obj_provider"/>_table tbody').off('dblclick'); $('#<xsl:value-of select="../../@obj_provider"/>_table tbody').off('keydown'); $('#<xsl:value-of select="../../@obj_provider"/>_table tbody').off('click'); $('#<xsl:value-of select="../../@obj_provider"/>_table tbody').on('dblclick','tr', function() { <xsl:for-each select="action">dc1(this,event,function(that) { <xsl:call-template name="generate_action_invokation">
											<xsl:with-param name="that">that</xsl:with-param>
										</xsl:call-template>},'',''); </xsl:for-each>}); $('#<xsl:value-of select="../../@obj_provider"/>_table tbody').on('keydown','tr', function() { <xsl:for-each select="action">return dc3(this,event,function(that) { <xsl:call-template name="generate_action_invokation">
											<xsl:with-param name="that">that</xsl:with-param>
										</xsl:call-template>}); </xsl:for-each>}); </xsl:when>
								<xsl:when test="@name='smplclick'">$('#<xsl:value-of select="../../@obj_provider"/>_table tbody').on('click','tr', function() { <xsl:for-each select="action">return dc5(this,event,function(that) { <xsl:call-template name="generate_action_invokation">
											<xsl:with-param name="that">that</xsl:with-param>
										</xsl:call-template>}); </xsl:for-each>}); </xsl:when>
							</xsl:choose>
						</xsl:for-each>
					</xsl:for-each>
				</script>
				<xsl:for-each select="posfunction">
					<script>
						<xsl:value-of select="@script"/>
					</script>
				</xsl:for-each>
				<xsl:for-each select="rows/row[(@selected) and (@selected='true')]">
					<script TYPE="POSTSCRIPT">rs_forced2(document.getElementById('<xsl:value-of select="@zobject"/>'),null); </script>
				</xsl:for-each>
				<xsl:if test="(rows/row[(@selected) and (@selected='true')])">
					<xsl:for-each select="default_actions/default_action[@name='smplclick']">
						<script TYPE="POSTSCRIPT">
							<xsl:for-each select="action">
								<xsl:call-template name="generate_action_invokation"/>
							</xsl:for-each>
						</script>
					</xsl:for-each>
				</xsl:if>
			</xsl:if>
		</xsl:if>
		<xsl:if test="$level='_base'">
			<xsl:if test="@zoomtofit">
				<script>zoomToFitHorizontal('<xsl:value-of select="@obj_provider"/>_container','<xsl:value-of select="@obj_provider"/>_table'); $(window).off('<xsl:value-of select="@obj_provider"/>_container'); $(window).resize('<xsl:value-of select="@obj_provider"/>_container',function(){ zoomToFitHorizontal('<xsl:value-of select="@obj_provider"/>_container','<xsl:value-of select="@obj_provider"/>_table'); }); </script>
			</xsl:if>
		</xsl:if>
	</xsl:template>
	<xsl:template name="win_list_table_bigdata">
		<xsl:if test="showAction">
			<script TYPE="POSTSCRIPT">
				<xsl:for-each select="showAction">
					<xsl:for-each select="action">
						<xsl:call-template name="generate_action_invokation"/>
					</xsl:for-each>
				</xsl:for-each>
			</script>
		</xsl:if>
		<script>$(document).ready(function(){ var pendingDraw=false; var focusName=null; var table = $('#<xsl:value-of select="@obj_provider"/>_table').on('xhr.dt', function ( e, settings, json, xhr ) { if (json==null) return; <xsl:for-each select="header/updateControl">updateField($('input[id*="<xsl:value-of select="@field"/>"]').prop('id'),json.<xsl:value-of select="@data"/>); </xsl:for-each>} ).DataTable( { "destroy": true, "responsive": <xsl:value-of select="@list_responsive"/>, "processing": true, "dom": '<xsl:value-of select="@distribution"/>', "serverSide": true, "ordering": false, "searching": true, "scrollY": 550, "scroller": { "loadingIndicator": true }, "preDrawCallback": function (settings) { onFocusObjectById($(':focus').attr('id')); if (!(typeof table === 'undefined')) if(table!=null) { <xsl:for-each select="header/manualFilter">
				<xsl:for-each select="column">
					<xsl:if test="not(@name=preceding::column/@name)">var <xsl:value-of select="@name"/>Data=""; </xsl:if>
				</xsl:for-each>
			</xsl:for-each>
			<xsl:for-each select="header/manualFilter">
				<xsl:choose>
					<xsl:when test="@control='JFormCheckResponsive'">if ($( 'input[type="checkbox"][id*="<xsl:value-of select="@field"/>"]' ).length>0) if ( $( 'input[type="checkbox"][id*="<xsl:value-of select="@field"/>"]' ).get(0).checked) { <xsl:for-each select="column">
							<xsl:value-of select="@name"/>Data+=((<xsl:value-of select="@name"/>Data=='')?'':';')+ '<xsl:value-of select="../@field"/>|<xsl:value-of select="../@oper"/>|'+$( 'input[type="checkbox"][id*="<xsl:value-of select="../@field"/>"]' ).get(0).checked ; </xsl:for-each>} </xsl:when>
					<xsl:when test="@control='JFormWinLOVResponsive'">if ($( 'select[id*="<xsl:value-of select="@field"/>"]' ).length>0) if ( getSelectValues($( 'select[id*="<xsl:value-of select="@field"/>"]' ).get(0)) !='') { <xsl:for-each select="column">
							<xsl:value-of select="@name"/>Data+=((<xsl:value-of select="@name"/>Data=='')?'':';')+ '<xsl:value-of select="../@field"/>|<xsl:value-of select="../@oper"/>'+'|'+getSelectValues($( 'select[id*="<xsl:value-of select="../@field"/>"]' ).get(0)) ; </xsl:for-each>} </xsl:when>
					<xsl:otherwise>if ( $( 'input[id*="<xsl:value-of select="@field"/>"]' ).length>0) if( $( 'input[id*="<xsl:value-of select="@field"/>"]' ).get(0).value!='') { <xsl:for-each select="column">
							<xsl:value-of select="@name"/>Data+=((<xsl:value-of select="@name"/>Data=='')?'':';')+ '<xsl:value-of select="../@field"/>|<xsl:value-of select="../@oper"/>'+'|'+$( 'input[id*="<xsl:value-of select="../@field"/>"]' ).get(0).value ; </xsl:for-each>} </xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
			<xsl:for-each select="header/manualFilter">
				<xsl:for-each select="column">
					<xsl:if test="not(@name=preceding::column/@name)">if (table.column('<xsl:value-of select="@name"/>:name').search()!= <xsl:value-of select="@name"/>Data ) table.column('<xsl:value-of select="@name"/>:name').search( <xsl:value-of select="@name"/>Data ); </xsl:if>
				</xsl:for-each>
			</xsl:for-each>} }, "aoSearchCols": [ <xsl:for-each select="header/column[@col_name!='']">{ "sSearch" : function() { <xsl:variable name="col">
					<xsl:value-of select="@col_name"/>
				</xsl:variable>var out=""; <xsl:for-each select="../manualFilter[column/@name=$col]">
					<xsl:choose>
						<xsl:when test="@control='JFormCheckResponsive'">if ($( 'input[type="checkbox"][id*="<xsl:value-of select="@field"/>"]' ).length>0) if ( $( 'input[type="checkbox"][id*="<xsl:value-of select="@field"/>"]' ).get(0).checked) { <xsl:for-each select="column[@name=$col]">out+=(out==""?'':';')+ '<xsl:value-of select="../@field"/>|<xsl:value-of select="../@oper"/>|'+$( 'input[type="checkbox"][id*="<xsl:value-of select="../@field"/>"]' ).get(0).checked ; </xsl:for-each>} </xsl:when>
						<xsl:when test="@control='JFormWinLOVResponsive'">if ($( 'select[id*="<xsl:value-of select="@field"/>"]' ).length>0) if ( getSelectValues($( 'select[id*="<xsl:value-of select="@field"/>"]' ).get(0)) !='') { <xsl:for-each select="column[@name=$col]">out+=(out==""?'':';')+ '<xsl:value-of select="../@field"/>|<xsl:value-of select="../@oper"/>'+'|'+getSelectValues($( 'select[id*="<xsl:value-of select="../@field"/>"]' ).get(0)) ; </xsl:for-each>} </xsl:when>
						<xsl:otherwise>if ( $( 'input[id*="<xsl:value-of select="@field"/>"]' ).length>0) if( $( 'input[id*="<xsl:value-of select="@field"/>"]' ).get(0).value!='') { <xsl:for-each select="column[@name=$col]">out+=(out==""?'':';')+ '<xsl:value-of select="../@field"/>|<xsl:value-of select="../@oper"/>'+'|'+$( 'input[id*="<xsl:value-of select="../@field"/>"]' ).get(0).value ; </xsl:for-each>} </xsl:otherwise>
					</xsl:choose>
				</xsl:for-each>return out; } }<xsl:if test="position()!=last()">,</xsl:if>
			</xsl:for-each>], "drawCallback": function (settings) { forceFocusObject(); pendingDraw=false; }, "createdRow": function (row, data, dataIndex) { $(row).attr('data-provider', data.DT_RowId); $(row).attr('data-provider_parent', '<xsl:value-of select="@obj_provider"/>'); }, <xsl:for-each select="json">
				<xsl:for-each select="action">"ajax": { url: '<xsl:value-of select="@target"/>
					<xsl:if test="@data_string and not(@data_string='')">?<xsl:value-of select="@data_string"/>
					</xsl:if>', method: 'POST', data: fillJSonRequest('<xsl:value-of select="@target"/>','<xsl:value-of select="@id_action"/>',<xsl:choose>
						<xsl:when test="@object_owner_id">'<xsl:value-of select="@object_owner_id"/>'</xsl:when>
						<xsl:otherwise>null</xsl:otherwise>
					</xsl:choose>,'<xsl:value-of select="@obj_provider"/>') } , </xsl:for-each>
			</xsl:for-each>"initComplete": function(settings, json) { initialize(json); <xsl:if test="@is_line_select='true'">
				<xsl:for-each select="dblclick">$('#<xsl:value-of select="../@obj_provider"/>_table').on('dblclick', 'tr', function () { clearClick(); rs_forced_with_key(this,'','',event); <xsl:for-each select="action">
						<xsl:call-template name="generate_action_invokation"/>
					</xsl:for-each>this.disabled=false; rs_forced(null,event); } ); </xsl:for-each>$('#<xsl:value-of select="@obj_provider"/>_table').on('click', 'tr', function () { rs(this,event); <xsl:for-each select="smplclick">runClick(this,event,function(that){ <xsl:for-each select="action">
						<xsl:call-template name="generate_action_invokation"/>
					</xsl:for-each>}); </xsl:for-each>} ); $('#<xsl:value-of select="@obj_provider"/>_table').on( 'draw.dt', function () { var zTable= getTableRegistered('<xsl:value-of select="@obj_provider"/>_table'); zTable.updateActionBar(); zTable.rs_forcedJSON(); } ); <xsl:for-each select="header/manualFilter">
					<xsl:choose>
						<xsl:when test="@control='JFormCheckResponsive'">$( 'input[type="checkbox"][id*="<xsl:value-of select="@field"/>"]' ).on( 'change', function () { console.log("change check <xsl:value-of select="@field"/>"); if (pendingDraw) { return; } console.log("change check <xsl:value-of select="@field"/>draw"); pendingDraw=true; table.draw(); } ); </xsl:when>
						<xsl:when test="@control='JFormWinLOVResponsive'">$( 'select[id*="<xsl:value-of select="@field"/>"]' ).on( 'change select2:unselect select2:select', function () { console.log("change winlov <xsl:value-of select="@field"/>"); if (pendingDraw) { return; } console.log("change winlov <xsl:value-of select="@field"/>draw "); pendingDraw=true; table.draw(); } ); </xsl:when>
						<xsl:otherwise>$( 'input[id*="<xsl:value-of select="@field"/>"]' ).on( 'keyup change clear', function () { console.log("change edit <xsl:value-of select="@field"/>"); if (pendingDraw) { return; } console.log("change edit <xsl:value-of select="@field"/>draw"); pendingDraw=true; table.draw(); } ); </xsl:otherwise>
					</xsl:choose>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="@is_line_select='false'">$('#<xsl:value-of select="@obj_provider"/>_table').on('onclick', 'tr', function () { rsCell(this,event); } ); $('#<xsl:value-of select="@obj_provider"/>_table').on( 'draw.dt', function () { var zTable= getTableRegistered('<xsl:value-of select="@obj_provider"/>_table'); zTable.updateActionBar(); } ); </xsl:if>}, "language": { "processing": '<i class="fa fa-spinner fa-spin fa-3x fa-fw"/>', "decimal": ",", "emptyTable": global_jsondtnodata, "info": global_jsondtinfo, "infoEmpty": global_jsondtinfoempty, "infoFiltered": global_jsondtinfomax, "infoPostFix": "", "thousands": ".", "lengthMenu": global_jsondtlines, "loadingRecords": global_jsondtloading, "search": '<i class="fa fa-search"/>', "zeroRecords": global_jsondtzerorecords, "select": { rows: { _: global_jsondtrowselect, 0: global_jsondtrow0, 1: global_jsondtrow1 } } } <xsl:if test="header/column/@col_name">,columns: [ <xsl:for-each select="header">
					<xsl:for-each select="column">
						<xsl:if test="@type!='JLINK'">{ "name": '<xsl:value-of select="@col_name"/>', <xsl:choose>
								<xsl:when test="@control='JSCRIPT'">"data": null, "render": function(data,type,row) { return <xsl:value-of select="@onCalculate"/>} </xsl:when>
								<xsl:otherwise>"data": '<xsl:value-of select="@col_name"/>', <xsl:choose>
										<xsl:when test="@halignment='left'">"className": "text-left", </xsl:when>
										<xsl:when test="@halignment='right'">"className": "text-right", </xsl:when>
										<xsl:when test="@halignment='center'">"className": "text-center", </xsl:when>
									</xsl:choose>
									<xsl:if test="@width">"width": "<xsl:value-of select="@width"/>", </xsl:if>
									<xsl:choose>
										<xsl:when test="@control='JINPUT'">
											<xsl:choose>
												<xsl:when test="@type='JLONG' or @type='JINTEGER' or @type='JFLOAT'">"render": function (data, type, row){ return <xsl:text>'&lt;input id=\"'+row.DT_RowId+'_</xsl:text>
													<xsl:value-of select="@col_name"/>
													<xsl:text>\" type=\"text\" value=\"'+data+'\" class=\"</xsl:text>
													<xsl:value-of select="@class_component"/>
													<xsl:text><![CDATA[ ]]>text-right\" data-colname=\"</xsl:text>
													<xsl:value-of select="@col_name"/>
													<xsl:text>\"/&gt;';</xsl:text>}, </xsl:when>
												<xsl:otherwise>"render": function (data, type, row){ return <xsl:text>'&lt;input id=\"'+row.DT_RowId+'_</xsl:text>
													<xsl:value-of select="@col_name"/>
													<xsl:text>\" type=\"text\" value=\"'+data+'\" class=\"</xsl:text>
													<xsl:value-of select="@class_component"/>
													<xsl:text><![CDATA[ ]]>text-left\" data-colname=\"</xsl:text>
													<xsl:value-of select="@col_name"/>
													<xsl:text>\"/&gt;';</xsl:text>}, </xsl:otherwise>
											</xsl:choose>
										</xsl:when>
										<xsl:when test="@control='JCHECK'">"render": function (data, type, row){ return <xsl:text>'&lt;input id=\"'+row.DT_RowId+'_</xsl:text>
											<xsl:value-of select="@col_name"/>
											<xsl:text>\" type=\"checkbox\" value=\"S\" class=\"</xsl:text>
											<xsl:value-of select="@class_component"/>
											<xsl:text>\" '+(data=='S'?'checked':'')+' data-colname=\"</xsl:text>
											<xsl:value-of select="@col_name"/>
											<xsl:text>\"/&gt;';</xsl:text>}, </xsl:when>
										<xsl:when test="@type='JIMAGE'">"render": function (data, type, row){ return (data=='')?<xsl:text>' '</xsl:text>:<xsl:text>'&lt;img src="</xsl:text>
											<xsl:value-of select="$skin_path"/>
											<xsl:text>/'+data+'"/&gt;';</xsl:text>}, </xsl:when>
										<xsl:when test="@type='JBOOLEAN'">"render": function (data, type, row){ if (data=='S') return ('<xsl:value-of select="@image_true"/>'=='')?<xsl:text>' '</xsl:text>:<xsl:text>'&lt;img src="</xsl:text>
											<xsl:value-of select="$skin_path"/>
											<xsl:text>/</xsl:text>
											<xsl:value-of select="@image_true"/>
											<xsl:text>"/&gt;';</xsl:text>return ('<xsl:value-of select="@image_false"/>'=='')?<xsl:text>' '</xsl:text>:<xsl:text>'&lt;img src="</xsl:text>
											<xsl:value-of select="$skin_path"/>
											<xsl:text>/</xsl:text>
											<xsl:value-of select="@image_false"/>
											<xsl:text>"/&gt;';</xsl:text>}, </xsl:when>
										<xsl:when test="@type='JCOLOUR'">"render": function (data, type, row){ return <xsl:text>'&lt;div style=\"height: 16; width: 16; border-bottom-width: 1px; border-top-width: 1px; border-right-width: 1px; border-left-width: 1px; border-style: solid solid none; border-top-color: #000000; border-bottom-color: #000000; order-right-color: #000000; border-left-color: #000000; background-color: #'+data+'\"/&gt;';</xsl:text>}, </xsl:when>
										<xsl:otherwise>"render": function (data, type, row){ if ((typeof data === 'string' || data instanceof String)) { if (!data.startsWith("*b64*:")) return data; return Base64.decode(data.substring(6)); } return ''; }, </xsl:otherwise>
									</xsl:choose>"orderable": true, "searchable": true, "visible": <xsl:choose>
										<xsl:when test="@visible='true'">true</xsl:when>
										<xsl:otherwise>false</xsl:otherwise>
									</xsl:choose>
								</xsl:otherwise>
							</xsl:choose>}<xsl:if test="position()!=last()">,</xsl:if>
						</xsl:if>
					</xsl:for-each>
				</xsl:for-each>],columnDefs: [ <xsl:for-each select="header">
					<xsl:for-each select="column">
						<xsl:if test="@type!='JLINK'">{ <xsl:if test="@width">"width": '<xsl:value-of select="@width"/>', </xsl:if>"targets": '<xsl:value-of select="@col_name"/>', "visible": <xsl:choose>
								<xsl:when test="@visible='true'">true</xsl:when>
								<xsl:otherwise>false</xsl:otherwise>
							</xsl:choose>, "createdCell": function (td, cellData, rowData, row, col){ if (! rowData.DT_RowData) return; var backColor = rowData.DT_RowData['<xsl:value-of select="@col_name"/>_background']; if (backColor) if(backColor!='') $(td).css("background-color",backColor); var cellColor = rowData.DT_RowData['<xsl:value-of select="@col_name"/>_foreground']; if (cellColor) if(cellColor!='') $(td).css("color",cellColor); } }<xsl:if test="position()!=last()">,</xsl:if>
						</xsl:if>
					</xsl:for-each>
				</xsl:for-each>] </xsl:if>,fixedHeader: true } ); if ($('#actionbarinternal_<xsl:value-of select="@obj_provider"/>').html()!="") { $("#<xsl:value-of select="@obj_provider"/>_table_wrapper div.toolbar").html($('#actionbarinternal_<xsl:value-of select="@obj_provider"/>').html()); $('#actionbarinternal_<xsl:value-of select="@obj_provider"/>').html(""); } $('#<xsl:value-of select="@obj_provider"/>_table').on('keydown', 'input', function () { return gaOnKey(event, this); } ); $('#<xsl:value-of select="@obj_provider"/>_table').on('change', 'input', function () { updateServerField(this,event,table); } ); }); $('a[data-toggle="tab"]').on('shown.bs.tab', function(e){ $('#<xsl:value-of select="@obj_provider"/>_table').DataTable().ajax.reload(); } ); </script>
		<div class="table-responsive">
			<table id="win_list_table">
				<xsl:attribute name="id">
					<xsl:value-of select="@obj_provider"/>_table</xsl:attribute>
				<xsl:attribute name="class">
					<xsl:value-of select="@class_table_responsive"/>
				</xsl:attribute>
				<xsl:attribute name="style">width:100%</xsl:attribute>
				<xsl:attribute name="onKeyDown">return rsKeyPressTable(this,event);</xsl:attribute>
				<THEAD id="scroll_header">
					<xsl:if test="@with_group='true'">
						<xsl:for-each select="level_column">
							<tr>
								<xsl:for-each select="grupo_column">
									<th>
										<xsl:attribute name="colspan">0<xsl:value-of select="@span"/>
										</xsl:attribute>
										<xsl:if test="@foreground or @background">
											<xsl:attribute name="style">
												<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
												<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>
											</xsl:attribute>
										</xsl:if>
										<xsl:call-template name="dragdrop_component"/>
										<xsl:choose>
											<xsl:when test="not(title/.='SINGRUPOCOLUMNA')">
												<xsl:value-of select="title/."/>
											</xsl:when>
											<xsl:otherwise/>
										</xsl:choose>
									</th>
								</xsl:for-each>
							</tr>
						</xsl:for-each>
					</xsl:if>
					<tr>
						<xsl:for-each select="header">
							<xsl:for-each select="column">
								<xsl:if test="@type!='JLINK'">
									<th>
										<xsl:attribute name="class">
											<xsl:value-of select="@col_name"/>
										</xsl:attribute>
										<xsl:if test="@foreground or @background">
											<xsl:attribute name="style">
												<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
												<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>
											</xsl:attribute>
										</xsl:if>
										<xsl:call-template name="dragdrop_component"/>
										<span>
											<xsl:if test="not(hover/.='')">
												<xsl:attribute name="title">
													<xsl:value-of select="hover/."/>
												</xsl:attribute>
											</xsl:if>
											<xsl:choose>
												<xsl:when test="html_data and not(html_data/.='')">
													<xsl:value-of select="html_data/."
            														disable-output-escaping="yes"/>
												</xsl:when>
												<xsl:when test="not(title/.='')">
													<xsl:value-of select="title/."/>
												</xsl:when>
												<xsl:when test="@type='VOID'"/>
												<xsl:otherwise>-</xsl:otherwise>
											</xsl:choose>
										</span>
									</th>
								</xsl:if>
							</xsl:for-each>
						</xsl:for-each>
					</tr>
				</THEAD>
				<xsl:if test="not(footer)">
					<xsl:if test="@headerinfooter='true'">
						<TFOOT id="scroll_foot">
							<tr>
								<xsl:for-each select="header">
									<xsl:for-each select="column">
										<xsl:if test="@type!='JLINK'">
											<th>
												<xsl:attribute name="style">text-align: center;</xsl:attribute>
												<span>
													<xsl:if test="not(hover/.='')">
														<xsl:attribute name="title">
															<xsl:value-of select="hover/."/>
														</xsl:attribute>
													</xsl:if>
													<xsl:choose>
														<xsl:when test="html_data and not(html_data/.='')">
															<xsl:value-of select="html_data/."
            																disable-output-escaping="yes"/>
														</xsl:when>
														<xsl:when test="not(title/.='')">
															<xsl:value-of select="title/."/>
														</xsl:when>
														<xsl:when test="@type='VOID'"/>
														<xsl:otherwise>-</xsl:otherwise>
													</xsl:choose>
												</span>
											</th>
										</xsl:if>
									</xsl:for-each>
								</xsl:for-each>
							</tr>
						</TFOOT>
					</xsl:if>
				</xsl:if>
				<xsl:if test="footer">
					<TFOOT id="scroll_foot">
						<tr>
							<xsl:for-each select="footer/column">
								<xsl:if test="@type!='JLINK'">
									<td>
										<xsl:attribute name="style">text-align:<xsl:value-of select="@halignment"/>; <xsl:if test="@foreground or @background">
												<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
												<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>
											</xsl:if>
										</xsl:attribute>
										<xsl:if test="not(dblclickDrag)">
											<xsl:attribute name="onClick">hsc(this);</xsl:attribute>
										</xsl:if>
										<xsl:call-template name="dragdrop_component"/>
										<xsl:choose>
											<xsl:when test="not(value/.='')">
												<xsl:value-of select="value/."/>
											</xsl:when>
											<xsl:otherwise>
												<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
											</xsl:otherwise>
										</xsl:choose>
									</td>
								</xsl:if>
							</xsl:for-each>
						</tr>
					</TFOOT>
				</xsl:if>
			</table>
		</div>
	</xsl:template>
	<xsl:template name="win_list_table_json">
		<xsl:if test="showAction">
			<script TYPE="POSTSCRIPT">
				<xsl:for-each select="showAction">
					<xsl:for-each select="action">
						<xsl:call-template name="generate_action_invokation"/>
					</xsl:for-each>
				</xsl:for-each>
			</script>
		</xsl:if>
		<script>$(document).ready(function(){ var table = $('#<xsl:value-of select="@obj_provider"/>_table').DataTable( { "destroy": true, "responsive": <xsl:value-of select="@list_responsive"/>, "processing": true, "dom": '<xsl:value-of select="@distribution"/>', "serverSide": true, "ordering": <xsl:value-of select="@ordering"/>, "lengthChange": <xsl:value-of select="@lengthchange"/>, "searching": <xsl:value-of select="@searching"/>, <xsl:for-each select="json">
				<xsl:for-each select="action">"ajax": $.fn.dataTable.pipeline( { url: '<xsl:value-of select="@target"/>
					<xsl:if test="@data_string and not(@data_string='')">?<xsl:value-of select="@data_string"/>
					</xsl:if>', pages: 5, data: fillJSonRequest('<xsl:value-of select="@target"/>','<xsl:value-of select="@id_action"/>',<xsl:choose>
						<xsl:when test="@object_owner_id">'<xsl:value-of select="@object_owner_id"/>'</xsl:when>
						<xsl:otherwise>null</xsl:otherwise>
					</xsl:choose>,'<xsl:value-of select="@obj_provider"/>') } ), </xsl:for-each>
			</xsl:for-each>"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "Tds"]], "language": { "processing": '<i class="fa fa-spinner fa-spin fa-3x fa-fw"/>
			<span class="sr-only">Cargando...</span>', "decimal": ",", "emptyTable": "Sin datos", "info": "_START_ a _END_ de _TOTAL_ filas", "infoEmpty": "0 filas", "infoFiltered": "(Filtradas _MAX_ filas)", "infoPostFix": "", "thousands": ".", "lengthMenu": '#Lineas _MENU_', "loadingRecords": "Cargando...", "search": '<i class="fa fa-search"/>', "zeroRecords": "Sin coincidencias", "deferRender": true, "paginate": { "first": "Primero", "last": "Ultimo", "next": "Siguiente", "previous": "Anterior" }, "aria": { "sortAscending": ": active orden ascendente", "sortDescending": ": active orden descendente" }, select: { rows: { _: "Filas %d seleccionadas", 0: "Click para seleccionar", 1: "Solo 1 fila seleccionada" } } } <xsl:if test="header/column/@col_name">,columns: [ <xsl:for-each select="header">
					<xsl:for-each select="column">
						<xsl:if test="@type!='JLINK'">{ "data": '<xsl:value-of select="@col_name"/>', <xsl:choose>
								<xsl:when test="@halignment='left'">"className": "text-left", </xsl:when>
								<xsl:when test="@halignment='right'">"className": "text-right", </xsl:when>
								<xsl:when test="@halignment='center'">"className": "text-center", </xsl:when>
							</xsl:choose>
							<xsl:if test="@width">"width": "<xsl:value-of select="@width"/>", </xsl:if>
							<xsl:choose>
								<xsl:when test="@type='JIMAGE'">"render": function (data, type, row){ return (data=='')?<xsl:text>' '</xsl:text>:<xsl:text>'&lt;img src="</xsl:text>
									<xsl:value-of select="$skin_path"/>
									<xsl:text>/'+data+'"/&gt;';</xsl:text>}, </xsl:when>
								<xsl:when test="@type='JBOOLEAN'">"render": function (data, type, row){ if (data=='S') return ('<xsl:value-of select="@image_true"/>'=='')?<xsl:text>' '</xsl:text>:<xsl:text>'&lt;img src="</xsl:text>
									<xsl:value-of select="$skin_path"/>
									<xsl:text>/</xsl:text>
									<xsl:value-of select="@image_true"/>
									<xsl:text>"/&gt;';</xsl:text>return ('<xsl:value-of select="@image_false"/>'=='')?<xsl:text>' '</xsl:text>:<xsl:text>'&lt;img src="</xsl:text>
									<xsl:value-of select="$skin_path"/>
									<xsl:text>/</xsl:text>
									<xsl:value-of select="@image_false"/>
									<xsl:text>"/&gt;';</xsl:text>}, </xsl:when>
								<xsl:when test="@type='JCOLOUR'">"render": function (data, type, row){ return <xsl:text>'&lt;div style=\"height: 16; width: 16; border-bottom-width: 1px; border-top-width: 1px; border-right-width: 1px; border-left-width: 1px; border-style: solid solid none; border-top-color: #000000; border-bottom-color: #000000; order-right-color: #000000; border-left-color: #000000; background-color: #'+data+'\"/&gt;';</xsl:text>}, </xsl:when>
								<xsl:otherwise>"render": function (data, type, row){ if ((typeof data === 'string' || data instanceof String)) { if (!data.startsWith("*b64*:")) return data; return Base64.decode(data.substring(6)); } return ''; }, </xsl:otherwise>
							</xsl:choose>"orderable": true, "searchable": true, "visible": true }<xsl:if test="position()!=last()">,</xsl:if>
						</xsl:if>
					</xsl:for-each>
				</xsl:for-each>] ,columnDefs: [ <xsl:for-each select="header">
					<xsl:for-each select="column">
						<xsl:if test="@type!='JLINK'">{ "targets": '<xsl:value-of select="@col_name"/>', "createdCell": function (td, cellData, rowData, row, col){ if (! rowData.DT_RowData) return; var backColor = rowData.DT_RowData['<xsl:value-of select="@col_name"/>_background']; if (backColor) if(backColor!='') $(td).css("background-color",backColor); var cellColor = rowData.DT_RowData['<xsl:value-of select="@col_name"/>_foreground']; if (cellColor) if(cellColor!='') $(td).css("color",cellColor); } }<xsl:if test="position()!=last()">,</xsl:if>
						</xsl:if>
					</xsl:for-each>
				</xsl:for-each>] </xsl:if>,fixedHeader: true } ); if ($('#actionbarinternal_<xsl:value-of select="@obj_provider"/>').html()!="") { $("#<xsl:value-of select="@obj_provider"/>_table_wrapper div.toolbar").html($('#actionbarinternal_<xsl:value-of select="@obj_provider"/>').html()); $('#actionbarinternal_<xsl:value-of select="@obj_provider"/>').html(""); } <xsl:if test="@is_line_select='true'">
				<xsl:for-each select="dblclick">$('#<xsl:value-of select="../@obj_provider"/>_table').on('dblclick', 'tr', function () { clearClick(); rs_forced_with_key(this,'','',event); <xsl:for-each select="action">
						<xsl:call-template name="generate_action_invokation"/>
					</xsl:for-each>this.disabled=false; rs_forced(null,event); } ); </xsl:for-each>$('#<xsl:value-of select="@obj_provider"/>_table').on('click', 'tr', function () { rs(this,event); <xsl:for-each select="smplclick">runClick(this,event,function(that){ <xsl:for-each select="action">
						<xsl:call-template name="generate_action_invokation"/>
					</xsl:for-each>}); </xsl:for-each>} ); $('#<xsl:value-of select="@obj_provider"/>_table').on( 'draw.dt', function () { var zTable= getTableRegistered('<xsl:value-of select="@obj_provider"/>_table'); zTable.updateActionBar(); zTable.rs_forcedJSON(); } ); </xsl:if>
			<xsl:if test="@is_line_select='false'">$('#<xsl:value-of select="@obj_provider"/>_table').on('onclick', 'tr', function () { rsCell(this,event); } ); $('#<xsl:value-of select="@obj_provider"/>_table').on( 'draw.dt', function () { var zTable= getTableRegistered('<xsl:value-of select="@obj_provider"/>_table'); zTable.updateActionBar(); } ); </xsl:if>}); $('a[data-toggle="tab"]').on('shown.bs.tab', function(e){ $('#<xsl:value-of select="@obj_provider"/>_table').DataTable().ajax.reload(); } ); </script>
		<div class="table-responsive">
			<table id="win_list_table">
				<xsl:attribute name="id">
					<xsl:value-of select="@obj_provider"/>_table</xsl:attribute>
				<xsl:attribute name="class">
					<xsl:value-of select="@class_table_responsive"/>
				</xsl:attribute>
				<xsl:attribute name="style">width:100%</xsl:attribute>
				<xsl:attribute name="onKeyDown">return rsKeyPressTable(this,event);</xsl:attribute>
				<THEAD id="scroll_header">
					<xsl:if test="@with_group='true'">
						<xsl:for-each select="level_column">
							<tr>
								<xsl:for-each select="grupo_column">
									<th>
										<xsl:attribute name="colspan">0<xsl:value-of select="@span"/>
										</xsl:attribute>
										<xsl:if test="@foreground or @background">
											<xsl:attribute name="style">
												<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
												<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>
											</xsl:attribute>
										</xsl:if>
										<xsl:call-template name="dragdrop_component"/>
										<xsl:choose>
											<xsl:when test="not(title/.='SINGRUPOCOLUMNA')">
												<xsl:value-of select="title/."/>
											</xsl:when>
											<xsl:otherwise/>
										</xsl:choose>
									</th>
								</xsl:for-each>
							</tr>
						</xsl:for-each>
					</xsl:if>
					<tr>
						<xsl:for-each select="header">
							<xsl:for-each select="column">
								<xsl:if test="@type!='JLINK'">
									<th>
										<xsl:attribute name="class">
											<xsl:value-of select="@col_name"/>
										</xsl:attribute>
										<xsl:if test="@foreground or @background">
											<xsl:attribute name="style">
												<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
												<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>
											</xsl:attribute>
										</xsl:if>
										<xsl:call-template name="dragdrop_component"/>
										<span>
											<xsl:if test="not(hover/.='')">
												<xsl:attribute name="title">
													<xsl:value-of select="hover/."/>
												</xsl:attribute>
											</xsl:if>
											<xsl:choose>
												<xsl:when test="html_data and not(html_data/.='')">
													<xsl:value-of select="html_data/."
            														disable-output-escaping="yes"/>
												</xsl:when>
												<xsl:when test="not(title/.='')">
													<xsl:value-of select="title/."/>
												</xsl:when>
												<xsl:when test="@type='VOID'"/>
												<xsl:otherwise>-</xsl:otherwise>
											</xsl:choose>
										</span>
									</th>
								</xsl:if>
							</xsl:for-each>
						</xsl:for-each>
					</tr>
				</THEAD>
				<xsl:if test="not(footer)">
					<xsl:if test="@headerinfooter='true'">
						<TFOOT id="scroll_foot">
							<tr>
								<xsl:for-each select="header">
									<xsl:for-each select="column">
										<xsl:if test="@type!='JLINK'">
											<th>
												<xsl:attribute name="style">text-align: center;</xsl:attribute>
												<span>
													<xsl:if test="not(hover/.='')">
														<xsl:attribute name="title">
															<xsl:value-of select="hover/."/>
														</xsl:attribute>
													</xsl:if>
													<xsl:choose>
														<xsl:when test="html_data and not(html_data/.='')">
															<xsl:value-of select="html_data/."
            																disable-output-escaping="yes"/>
														</xsl:when>
														<xsl:when test="not(title/.='')">
															<xsl:value-of select="title/."/>
														</xsl:when>
														<xsl:when test="@type='VOID'"/>
														<xsl:otherwise>-</xsl:otherwise>
													</xsl:choose>
												</span>
											</th>
										</xsl:if>
									</xsl:for-each>
								</xsl:for-each>
							</tr>
						</TFOOT>
					</xsl:if>
				</xsl:if>
				<xsl:if test="footer">
					<TFOOT id="scroll_foot">
						<tr>
							<xsl:for-each select="footer/column">
								<xsl:if test="@type!='JLINK'">
									<td>
										<xsl:attribute name="style">text-align:<xsl:value-of select="@halignment"/>; <xsl:if test="@foreground or @background">
												<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
												<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>
											</xsl:if>
										</xsl:attribute>
										<xsl:if test="not(dblclickDrag)">
											<xsl:attribute name="onClick">hsc(this);</xsl:attribute>
										</xsl:if>
										<xsl:call-template name="dragdrop_component"/>
										<xsl:choose>
											<xsl:when test="not(value/.='')">
												<xsl:value-of select="value/."/>
											</xsl:when>
											<xsl:otherwise>
												<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
											</xsl:otherwise>
										</xsl:choose>
									</td>
								</xsl:if>
							</xsl:for-each>
						</tr>
					</TFOOT>
				</xsl:if>
			</table>
		</div>
	</xsl:template>
	<xsl:template name="win_grid_table">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="visibleicon">
			<xsl:value-of select="header/column[1]/@visible"/>
		</xsl:variable>
		<xsl:variable name="halignmenticon">
			<xsl:value-of select="header/column[1]/@halignment"/>
		</xsl:variable>
		<script>registerGrid('<xsl:value-of select="@tablename"/>',null,<xsl:value-of select="count(header/column)"/>,null,'<xsl:value-of select="@field"/>','<xsl:value-of select="$fullname"/>'); <xsl:if test="@modifiedonserver='true'">setChangeInputs(true); </xsl:if>
		</script>
		<table class="win_list_table"
     			border="0"
     			cellpadding="0"
     			cellspacing="0"
     			tabindex="0">
			<xsl:attribute name="id">
				<xsl:value-of select="@obj_provider"/>_table</xsl:attribute>
			<xsl:attribute name="class">table-grid<![CDATA[ ]]><xsl:value-of select="@class_table_responsive"/>
			</xsl:attribute>
			<xsl:if test="@editable='true'">
				<xsl:attribute name="onKeyDown">return rsKeyPressGrid(this,event);</xsl:attribute>
			</xsl:if>
			<xsl:if test="not(@editable='true')">
				<xsl:attribute name="onKeyDown">return rsKeyPressTable(this,event);</xsl:attribute>
			</xsl:if>
			<xsl:attribute name="width">100%</xsl:attribute>
			<THEAD id="scroll_header">
				<tr>
					<xsl:for-each select="header">
						<xsl:for-each select="column">
							<th>
								<xsl:variable name="columnnamegrid">grid_<xsl:value-of select="../../@obj_provider"/>_<xsl:value-of select="position()"/>
								</xsl:variable>
								<xsl:attribute name="id">
									<xsl:value-of select="$columnnamegrid"/>
								</xsl:attribute>
								<xsl:attribute name="data-pos">
									<xsl:value-of select="position()"/>
								</xsl:attribute>
								<xsl:attribute name="style">text-align: center;<xsl:if test="@visible='false'">display:none;</xsl:if>
								</xsl:attribute>
								<xsl:if test="@width">
									<xsl:attribute name="width">
										<xsl:value-of select="@width"/>
									</xsl:attribute>
								</xsl:if>
								<span>
									<xsl:if test="not(hover/.='')">
										<xsl:attribute name="title">
											<xsl:value-of select="hover/."/>
										</xsl:attribute>
									</xsl:if>
									<xsl:choose>
										<xsl:when test="html_data and not(html_data/.='')">
											<xsl:value-of select="html_data/."
            												disable-output-escaping="yes"/>
										</xsl:when>
										<xsl:when test="not(title/.='')">
											<xsl:value-of select="title/."/>
										</xsl:when>
										<xsl:when test="@type='VOID'"/>
										<xsl:otherwise>-</xsl:otherwise>
									</xsl:choose>
								</span>
							</th>
						</xsl:for-each>
					</xsl:for-each>
				</tr>
			</THEAD>
			<TBODY id="scroll_body">
				<xsl:for-each select="rows/row">
					<xsl:variable name="name_row">row_<xsl:value-of select="@rowpos"/>
					</xsl:variable>
					<tr tabindex="-1">
						<xsl:attribute name="name">
							<xsl:value-of select="$name_row"/>
						</xsl:attribute>
						<xsl:attribute name="id">
							<xsl:value-of select="$name_row"/>
						</xsl:attribute>
						<xsl:for-each select="dblclick">
							<xsl:call-template name="generate_action_on_double_click">
								<xsl:with-param name="row_id">
									<xsl:value-of select="../@zobject"/>
								</xsl:with-param>
								<xsl:with-param name="key">
									<xsl:value-of select="../@key"/>
								</xsl:with-param>
								<xsl:with-param name="descr">
									<xsl:value-of select="../@descr"/>
								</xsl:with-param>
							</xsl:call-template>
						</xsl:for-each>
						<xsl:if test="not(@deleted) and @rowpos mod 2 = 0">
							<xsl:attribute name="class">odd</xsl:attribute>
						</xsl:if>
						<xsl:if test="not(@deleted) and @rowpos mod 2 = 1">
							<xsl:attribute name="class">even</xsl:attribute>
						</xsl:if>
						<xsl:if test="@deleted">
							<xsl:attribute name="class">rowdeleted</xsl:attribute>
						</xsl:if>
						<xsl:if test="@zobject">
							<xsl:attribute name="id">
								<xsl:value-of select="@zobject"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:if test="@key">
							<xsl:attribute name="title">
								<xsl:value-of select="@key"/>;<xsl:value-of select="@descr"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:for-each select="row_control">
							<xsl:if test="@visible='false'">
								<xsl:attribute name="style">display:none;</xsl:attribute>
							</xsl:if>
						</xsl:for-each>
						<td>
							<xsl:attribute name="style">text-align: center;<xsl:if test="$visibleicon='false'">display:none;</xsl:if>
							</xsl:attribute>
							<xsl:if test="dblclick and icon">
								<xsl:call-template name="set_class"/>
								<xsl:attribute name="align">
									<xsl:value-of select="$halignmenticon"/>
								</xsl:attribute>
								<a>
									<xsl:if test="@tooltip">
										<xsl:attribute name="title">
											<xsl:value-of select="@tooltip"/>
										</xsl:attribute>
									</xsl:if>
									<xsl:for-each select="dblclick">
										<xsl:call-template name="generate_action_on_click_forced">
											<xsl:with-param name="row_id">
												<xsl:value-of select="../@zobject"/>
											</xsl:with-param>
											<xsl:with-param name="key">
												<xsl:value-of select="../@key"/>
											</xsl:with-param>
											<xsl:with-param name="descr">
												<xsl:value-of select="../@descr"/>
											</xsl:with-param>
										</xsl:call-template>
									</xsl:for-each>
									<xsl:call-template name="render_icon"/>
								</a>
							</xsl:if>
							<xsl:if test="icon and not(dblclick)">
								<xsl:call-template name="set_class"/>
								<xsl:attribute name="align">
									<xsl:value-of select="$halignmenticon"/>
								</xsl:attribute>
								<xsl:call-template name="render_icon"/>
							</xsl:if>
							<xsl:for-each select="control">
								<xsl:apply-templates select="*"/>
							</xsl:for-each>
						</td>
						<xsl:for-each select="d">
							<xsl:if test="not(@visible) or @visible='true'">
								<xsl:if test="not(@skip) or not(@skip='true')">
									<xsl:variable name="thispos"/>
									<xsl:choose>
										<xsl:when test="../icon">
											<xsl:call-template name="generate_win_grid_cell">
												<xsl:with-param name="thispos">
													<xsl:value-of select="position()+1"/>
												</xsl:with-param>
												<xsl:with-param name="name_row">
													<xsl:value-of select="$name_row"/>
												</xsl:with-param>
											</xsl:call-template>
										</xsl:when>
										<xsl:otherwise>
											<xsl:call-template name="generate_win_grid_cell">
												<xsl:with-param name="thispos">
													<xsl:value-of select="position()"/>
												</xsl:with-param>
												<xsl:with-param name="name_row">
													<xsl:value-of select="$name_row"/>
												</xsl:with-param>
											</xsl:call-template>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:if>
							</xsl:if>
						</xsl:for-each>
					</tr>
				</xsl:for-each>
			</TBODY>
			<xsl:if test="footer">
				<TFOOT id="scroll_foot">
					<tr>
						<xsl:if test="../../header/@has_subdetail='true'">
							<td>
								<xsl:attribute name="width">
									<xsl:if test="/page/header/layouts/layout[@id='win_list_table']/@width">
										<xsl:value-of select="/page/header/layouts/layout[@id='win_list_table']/@width"/>
									</xsl:if>
									<xsl:if test="not(/page/header/layouts/layout[@id='win_list_table']/@width)">5px</xsl:if>
								</xsl:attribute>
								<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
							</td>
						</xsl:if>
						<xsl:for-each select="footer/column">
							<td>
								<xsl:attribute name="onClick">hsc(this);</xsl:attribute>
								<xsl:attribute name="style">text-align:<xsl:value-of select="@halignment"/>; </xsl:attribute>
								<xsl:choose>
									<xsl:when test="not(value/.='')">
										<xsl:value-of select="value/."/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
									</xsl:otherwise>
								</xsl:choose>
							</td>
						</xsl:for-each>
					</tr>
				</TFOOT>
			</xsl:if>
		</table>
		<xsl:if test="@has_action_bar='true'">
			<xsl:variable name="v_provider">
				<xsl:value-of select="@obj_provider"/>
			</xsl:variable>
			<script>AAInit(); <xsl:for-each select="//win_action_bar[@provider=$v_provider]/action">
					<xsl:if test="allowed_win">AAAdd('<xsl:value-of select="@id"/>',<xsl:value-of select="@win"/>,[<xsl:for-each select="allowed_win">'<xsl:value-of select="@id"/>',</xsl:for-each>null],<xsl:value-of select="@multiple"/>); </xsl:if>
					<xsl:if test="not_allowed_win">AAAnodd('<xsl:value-of select="@id"/>',<xsl:value-of select="@win"/>,[<xsl:for-each select="not_allowed_win">'<xsl:value-of select="@id"/>',</xsl:for-each>null],<xsl:value-of select="@multiple"/>); </xsl:if>
				</xsl:for-each>AAFin('<xsl:value-of select="@obj_provider"/>_table','<xsl:value-of select="//win_action_bar[@provider=$v_provider]/@name"/>','<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="rows/@zobject"/>',<xsl:value-of select="@is_multiple_select"/>,<xsl:value-of select="@is_line_select"/>,'<xsl:value-of select="select_info/@has_more_selection"/>',false,null); <xsl:for-each select="rows/row[(@selected) and (@selected='true')]"/>
			</script>
		</xsl:if>
		<xsl:if test="@has_action_bar!='true'">
			<xsl:variable name="v_provider">
				<xsl:value-of select="@obj_provider"/>
			</xsl:variable>
			<script>AAInit(); AAFin('<xsl:value-of select="@obj_provider"/>_table',null,'<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="rows/@zobject"/>',<xsl:value-of select="@is_multiple_select"/>,true,false,false,null); </script>
		</xsl:if>
		<script>$("#<xsl:value-of select="@obj_provider"/>_table th").resizable({ resizeHeight : false, distance: 1, start: function( event, ui ) { dropContentColumn(event,'<xsl:value-of select="@obj_provider"/>_table',ui); }, stop: function( event, ui ) { removeDropContentColumn(event,'<xsl:value-of select="@obj_provider"/>_table',ui); }, resize: function( event, ui ) { resizeDropContentColumn(event,'<xsl:value-of select="@obj_provider"/>_table',ui); } }); </script>
		<xsl:for-each select="posfunction">
			<script>
				<xsl:value-of select="@script"/>
			</script>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="generate_basic_form">
		<input type="hidden"
     			name="dg_dictionary">
			<xsl:if test="$dictionary">
				<xsl:attribute name="value">
					<xsl:value-of select="$dictionary"/>
				</xsl:attribute>
			</xsl:if>
		</input>
		<input type="hidden"
     			name="dg_request">
			<xsl:if test="$requestid">
				<xsl:attribute name="value">
					<xsl:value-of select="$requestid"/>
				</xsl:attribute>
			</xsl:if>
		</input>
		<input type="hidden"
     			name="subsession">
			<xsl:attribute name="value">
				<xsl:value-of select="../header/session/@subsession"/>
			</xsl:attribute>
		</input>
		<input type="hidden"
     			name="src_uri">
			<xsl:attribute name="value">
				<xsl:value-of select="../header/action/@target"/>
			</xsl:attribute>
		</input>
		<input type="hidden"
     			name="src_sbmt">
			<xsl:attribute name="value">
				<xsl:value-of select="../header/action/@is_submit"/>
			</xsl:attribute>
		</input>
		<input type="hidden"
     			id="dg_action"
     			name="dg_action"/>
		<!-- comunica la accion a realizar -->
		<input type="hidden"
     			name="dg_tree_selection"/>
		<!-- comunica la seleccion en el arbol -->
		<input type="hidden"
     			name="dg_source_control_id"/>
		<!-- contiene el parent en el formlov  -->
		<input type="hidden"
     			name="dg_client_conf"/>
		<!-- configuracion del cliente ancho y alto de la pantalla -->
		<input type="hidden"
     			name="dg_act_owner"/>
		<!--  comunica el owner action de la accion  -->
		<input type="hidden"
     			name="dg_is_modal"/>
		<!--  comunica si la accion es modal -->
		<input type="hidden"
     			name="dg_object_owner"/>
		<!--  comunica el object owner de la accion  -->
		<input type="hidden"
     			name="dg_object_owner_dest"/>
		<!--  comunica el object owner dest de la accion swap  -->
		<input type="hidden"
     			name="dg_object_owner_context"/>
		<!--  comunica el object owner context para lista extendidas -->
		<input type="hidden"
     			name="dg_object_select"/>
		<!--  comunica el object select  de la accion  -->
		<input type="hidden"
     			id="dg_table_provider"
     			name="dg_table_provider"/>
		<!--  comunica el action owner de la accion en las solapas  -->
		<input type="hidden"
     			name="dg_clear_select"/>
		<!--  comunica que se elimina la seleccion anterior  -->
		<input type="hidden"
     			name="dg_multiple_owner_list"/>
		<!--  comunica multiples owners de la accion  -->
		<input type="hidden"
     			name="dg_multiple_owner_list_dest"/>
		<!--  comunica multiples owners de la accion para swap en refresh -->
		<input type="hidden"
     			name="dg_is_multiple_owner"/>
		<!--  comunica si hay multiples owners de la accion  -->
		<input type="hidden"
     			name="dg_row_select"/>
		<!--  comunica multiples owners de la accion  -->
		<input type="hidden"
     			name="dg_cell_select"/>
		<!--  comunica multiples owners de la accion  -->
		<input type="hidden"
     			name="dg_scroller"/>
		<!--  comunica la posicion y del scroll actual  -->
		<input type="hidden"
     			name="dg_back_modal"/>
		<!--  comunica si los retornos deben hacerse a modal -->
		<input type="hidden"
     			name="dg_extra_form_data"/>
		<!--  si el form es embedded  -->
		<input type="hidden"
     			name="dg_stadistics"/>
		<!--  informacion estadistica -->
    <input type="hidden"
     			name="dg_ajaxcontainer"/>
		<!--  informacion ajaxcontainer -->
		<input type="hidden"
     			name="dg_url"/>
		<!--  url address -->
	</xsl:template>
	<xsl:template match="win_form_embedded">
		<div role="form">
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="isEmbeddedForm"
              					select="'true'"/>
			</xsl:call-template>
		</div>
		<script>var zForm = new ZForm('<xsl:value-of select="@zobject"/>',1); registerObjectProvider('<xsl:value-of select="@obj_provider"/>', zForm); </script>
	</xsl:template>
	<xsl:template match="win_form">
		<div role="form">
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="isForm"
              					select="'true'"/>
			</xsl:call-template>
		</div>
		<script>var zForm = new ZForm('<xsl:value-of select="@zobject"/>',0); registerObjectProvider('<xsl:value-of select="@obj_provider"/>', zForm); </script>
		<xsl:if test="@scroll">
			<script type="text/javascript">adaptativeScroll('','','splitB','<xsl:value-of select="@scroll"/>',''); </script>
		</xsl:if>
		<xsl:if test="@autorefresh">
			<script>iniciarChecker(<xsl:value-of select="@timer"/>,'<xsl:value-of select="@marca"/>');</script>
		</xsl:if>
	</xsl:template>
	<xsl:template name="set_class">
		<xsl:attribute name="class">
			<xsl:choose>
				<xsl:when test="@iseje='true'">zwlthcy matrix_eje <xsl:value-of select="@class_responsive"/>
				</xsl:when>
				<xsl:when test="@marked='true'">zwltrmc matrix_marked <xsl:value-of select="@class_responsive"/>
				</xsl:when>
				<xsl:when test="@word_wrap">x m <xsl:value-of select="@class_responsive"/>
				</xsl:when>
				<xsl:when test="not(@word_wrap)">z m <xsl:value-of select="@class_responsive"/>
				</xsl:when>
				<xsl:otherwise>zwltrc m <xsl:value-of select="@class_responsive"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
	</xsl:template>
	<xsl:template name="generate_win_list_cell">
		<xsl:param name="thispos"/>
		<xsl:param name="rowpos"/>
		<xsl:variable name="objtype">
			<xsl:value-of select="@type"/>
		</xsl:variable>
		<xsl:variable name="halignment">
			<xsl:value-of select="@halignment"/>
		</xsl:variable>
		<td>
			<xsl:if test="@zobjectcell">
				<xsl:attribute name="id">
					<xsl:value-of select="@zobjectcell"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@axis">
				<xsl:attribute name="axis">
					<xsl:value-of select="@axis"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@rowspan">
				<xsl:attribute name="rowspan">
					<xsl:value-of select="@rowspan"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@colspan">
				<xsl:attribute name="colspan">
					<xsl:value-of select="@colspan"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:call-template name="dragdrop_component"/>
			<xsl:if test="../../../@is_line_select='false'">
				<xsl:if test="../../@use_context_menu='true'">
					<xsl:for-each select="../rightclick">
						<xsl:call-template name="generate_action_on_right_click_cell"/>
					</xsl:for-each>
					<xsl:for-each select="rightclick">
						<xsl:call-template name="generate_action_on_right_click_cell"/>
					</xsl:for-each>
				</xsl:if>
				<xsl:for-each select="../dblclick">
					<xsl:call-template name="generate_action_on_double_click_cell">
						<xsl:with-param name="row_id">
							<xsl:value-of select="../@zobject"/>
						</xsl:with-param>
					</xsl:call-template>
				</xsl:for-each>
				<xsl:for-each select="dblclick">
					<xsl:call-template name="generate_action_on_double_click_cell">
						<xsl:with-param name="row_id">
							<xsl:value-of select="@zobject"/>
						</xsl:with-param>
					</xsl:call-template>
				</xsl:for-each>
				<xsl:if test="../rightclick">
					<xsl:attribute name="onclick">rsCell(this,event);</xsl:attribute>
				</xsl:if>
			</xsl:if>
			<xsl:if test="@tooltip">
				<xsl:attribute name="title">
					<xsl:value-of select="@tooltip"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="(@height and @height!='20px' and @height!='20') or (@visibility and @visibility!='visible') or ($halignment and $halignment!='left') or (@valignment and @valignment!='')">
				<xsl:attribute name="style">
					<xsl:call-template name="generate_inline_style_no_location">
						<xsl:with-param name="style_attr">
							<xsl:if test="@height and @height!='20px'and @height!='20'">height:<xsl:value-of select="@height"/>;</xsl:if>
							<xsl:if test="$halignment and $halignment!='left'">text-align:<xsl:value-of select="$halignment"/>;</xsl:if>
							<xsl:if test="@valignment and @valignment!=''">vertical-align:<xsl:value-of select="@valignment"/>;</xsl:if>
						</xsl:with-param>
						<xsl:with-param name="useParentVisibility">
							<xsl:if test="(@visibility and @visibility='visible')">true</xsl:if>
							<xsl:if test="not(@visibility and @visibility='visible')">false</xsl:if>
						</xsl:with-param>
					</xsl:call-template>
				</xsl:attribute>
			</xsl:if>
			<xsl:call-template name="set_class"/>
			<xsl:choose>
				<xsl:when test="$objtype='JICON'">
					<xsl:choose>
						<xsl:when test="actionlink">
							<a>
								<xsl:if test="@tooltip">
									<xsl:attribute name="title">
										<xsl:value-of select="@tooltip"/>
									</xsl:attribute>
								</xsl:if>
								<xsl:if test="@foreground or @background">
									<xsl:attribute name="style">
										<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
										<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>;null </xsl:attribute>
								</xsl:if>
								<xsl:for-each select="actionlink">
									<xsl:call-template name="generate_action_on_dblclick"/>
									<xsl:call-template name="render_icon">
										<xsl:with-param name="style_attr">
											<xsl:if test="../@foreground">color:<xsl:value-of select="../@foreground"/>;</xsl:if>
											<xsl:if test="../@background">background-color:<xsl:value-of select="../@background"/>;</xsl:if>
										</xsl:with-param>
									</xsl:call-template>
								</xsl:for-each>
							</a>
						</xsl:when>
						<xsl:when test="icon">
							<xsl:call-template name="render_icon">
								<xsl:with-param name="style_attr">
									<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
									<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>
								</xsl:with-param>
							</xsl:call-template>
						</xsl:when>
						<xsl:otherwise/>
					</xsl:choose>
				</xsl:when>
				<xsl:when test="$objtype='JLINK'">
					<xsl:choose>
						<xsl:when test="@agrupado='true'">
							<div>
								<xsl:attribute name="class">btn-group dropdown-repair</xsl:attribute>
								<button>
									<xsl:attribute name="type">button</xsl:attribute>
									<xsl:attribute name="data-toggle">dropdown</xsl:attribute>
									<xsl:attribute name="aria-haspopup">true</xsl:attribute>
									<xsl:attribute name="aria-expanded">true</xsl:attribute>
									<xsl:attribute name="class">btn <xsl:value-of select="@clase"/>dropdown-toggle</xsl:attribute>
									<xsl:attribute name="id">btnGroupDrop<xsl:value-of select="$rowpos"/>
									</xsl:attribute>
									<xsl:if test="icon">
										<xsl:call-template name="render_icon">
											<xsl:with-param name="style_attr">
												<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
												<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>
											</xsl:with-param>
										</xsl:call-template>
									</xsl:if>
									<xsl:if test="not(icon)">
										<xsl:value-of select="@titulo"/>
									</xsl:if>
									<xsl:if test="with_caret='true'">
										<span class="caret"/>
									</xsl:if>
								</button>
								<ul class="dropdown-menu pull-right">
									<xsl:attribute name="class">dropdown-menu</xsl:attribute>
									<xsl:attribute name="aria-labelledby">btnGroupDrop<xsl:value-of select="$thispos"/>
									</xsl:attribute>
									<xsl:for-each select="actionclick">
										<li>
											<a>
												<xsl:attribute name="class">dropdown-item </xsl:attribute>
												<xsl:attribute name="onclick">
													<xsl:for-each select="action">
														<xsl:call-template name="generate_action_invokation"/>
													</xsl:for-each>
												</xsl:attribute>
												<xsl:call-template name="render_icon">
													<xsl:with-param name="style_attr">
														<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
														<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>
													</xsl:with-param>
												</xsl:call-template>
												<xsl:value-of select="@descripcion"/>
											</a>
										</li>
									</xsl:for-each>
								</ul>
							</div>
						</xsl:when>
						<xsl:otherwise>
							<div>
								<xsl:attribute name="class">toolbar-row </xsl:attribute>
								<xsl:for-each select="actionclick">
									<a>
										<xsl:attribute name="class">toolbar-row-link </xsl:attribute>
										<xsl:attribute name="role">button </xsl:attribute>
										<xsl:attribute name="title">
											<xsl:value-of select="@descripcion"/>
										</xsl:attribute>
										<xsl:attribute name="onclick">
											<xsl:for-each select="action">
												<xsl:call-template name="generate_action_invokation"/>
											</xsl:for-each>
										</xsl:attribute>
										<xsl:call-template name="render_icon">
											<xsl:with-param name="style_attr">
												<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
												<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>
											</xsl:with-param>
										</xsl:call-template>
									</a>
								</xsl:for-each>
							</div>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:when test="$objtype='JCOLOUR'">
					<div>
						<xsl:attribute name="style">height: 16; width: 16; border-width: 1px; border-style: solid; border-color: #000000; background-color: #<xsl:value-of select="."/>; </xsl:attribute>
					</div>
				</xsl:when>
				<xsl:when test="$objtype='JIMAGE'">
					<xsl:if test="not(.='')">
						<img>
							<xsl:attribute name="src">
								<xsl:value-of select="$skin_path"/>
								<xsl:value-of select="."/>
							</xsl:attribute>
						</img>
					</xsl:if>
				</xsl:when>
				<xsl:when test="$objtype='JWINFORM'">
					<xsl:call-template name="basic_generate_component_responsive">
						<xsl:with-param name="isForm"
              							select="'true'"/>
					</xsl:call-template>
				</xsl:when>
				<xsl:when test="@selector">
					<input type="checkbox">
						<xsl:attribute name="onclick">selectSpecial(event,this,'<xsl:value-of select="@selector"/>') </xsl:attribute>
						<xsl:attribute name="onkeydown">if(event.keyCode==32) this.click(); </xsl:attribute>
					</input>
				</xsl:when>
				<xsl:when test="$objtype='JBOOLEAN'">
					<xsl:choose>
						<xsl:when test="actionlink">
							<xsl:for-each select="actionlink">
								<a>
									<xsl:attribute name="onclick">
										<xsl:for-each select="action">
											<xsl:call-template name="generate_action_invokation"/>
										</xsl:for-each>
									</xsl:attribute>
									<xsl:choose>
										<xsl:when test="../.='S'">
											<xsl:choose>
												<xsl:when test="not(../@image_true='') and contains(../@image_true,'.gif')">
													<img>
														<xsl:attribute name="src">
															<xsl:value-of select="$skin_path"/>
															<xsl:value-of select="../@image_true"/>
														</xsl:attribute>
													</img>
												</xsl:when>
												<xsl:when test="not(../@image_true='') and not(contains(../@image_true,'.gif'))">
													<i>
														<xsl:attribute name='class'>
															<xsl:value-of select="../@image_true"/>
														</xsl:attribute>
													</i>
												</xsl:when>
												<xsl:otherwise>
													<i class="fa fa-check green"/>
												</xsl:otherwise>
											</xsl:choose>
										</xsl:when>
										<xsl:otherwise>
											<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
											<xsl:choose>
												<xsl:when test="not(../@image_false='') and contains(../@image_false,'.gif')">
													<img>
														<xsl:attribute name="src">
															<xsl:value-of select="$skin_path"/>
															<xsl:value-of select="../@image_false"/>
														</xsl:attribute>
													</img>
												</xsl:when>
												<xsl:when test="not(../@image_false='') and not(contains(../@image_false,'.gif'))">
													<i>
														<xsl:attribute name='class'>
															<xsl:value-of select="../@image_false"/>
														</xsl:attribute>
													</i>
												</xsl:when>
												<xsl:otherwise>
													<i class="fa fa-times red"/>
												</xsl:otherwise>
											</xsl:choose>
										</xsl:otherwise>
									</xsl:choose>
								</a>
							</xsl:for-each>
						</xsl:when>
						<xsl:when test=".='S'">
							<xsl:choose>
								<xsl:when test="not(@image_true='') and contains(@image_true,'.gif')">
									<img>
										<xsl:attribute name="src">
											<xsl:value-of select="$skin_path"/>
											<xsl:value-of select="@image_true"/>
										</xsl:attribute>
									</img>
								</xsl:when>
								<xsl:when test="not(@image_true='') and not(contains(@image_true,'.gif'))">
									<i>
										<xsl:attribute name='class'>
											<xsl:value-of select="@image_true"/>
										</xsl:attribute>
									</i>
								</xsl:when>
							</xsl:choose>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
							<xsl:choose>
								<xsl:when test="not(@image_false='') and contains(@image_false,'.gif')">
									<img>
										<xsl:attribute name="src">
											<xsl:value-of select="$skin_path"/>
											<xsl:value-of select="@image_false"/>
										</xsl:attribute>
									</img>
								</xsl:when>
								<xsl:when test="not(@image_false='') and not(contains(@image_false,'.gif'))">
									<i>
										<xsl:attribute name='class'>
											<xsl:value-of select="@image_false"/>
										</xsl:attribute>
									</i>
								</xsl:when>
							</xsl:choose>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<xsl:choose>
						<xsl:when test="actionlink">
							<xsl:for-each select="actionlink">
								<a>
									<xsl:attribute name="onclick">
										<xsl:for-each select="action">
											<xsl:call-template name="generate_action_invokation"/>
										</xsl:for-each>
									</xsl:attribute>
									<xsl:value-of select="../."/>
								</a>
							</xsl:for-each>
						</xsl:when>
						<xsl:when test=".=''"/>
						<xsl:when test="html_data">
							<xsl:for-each select="html_data">
								<xsl:value-of select="."
            									disable-output-escaping="yes"/>
							</xsl:for-each>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="."/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:otherwise>
			</xsl:choose>
		</td>
	</xsl:template>
	<xsl:template name="generate_win_grid_cell">
		<xsl:param name="thispos"/>
		<xsl:param name="name_row"/>
		<xsl:variable name="objtype">
			<xsl:value-of select="../../../header/column[ position() = $thispos ]/@type"/>
		</xsl:variable>
		<xsl:variable name="halignment">
			<xsl:value-of select="../../../header/column[ position() = $thispos ]/@halignment"/>
		</xsl:variable>
		<xsl:variable name="visible">
			<xsl:value-of select="../../../header/column[ position() = $thispos ]/@visible"/>
		</xsl:variable>
		<xsl:variable name="width">
			<xsl:value-of select="../../../header/column[ position() = $thispos ]/@width"/>
		</xsl:variable>
		<td>
			<xsl:attribute name="name">name_<xsl:value-of select="position()"/>
			</xsl:attribute>
			<xsl:if test="@axis">
				<xsl:attribute name="axis">
					<xsl:value-of select="@axis"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@zobjectcell">
				<xsl:attribute name="id">
					<xsl:value-of select="@zobjectcell"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@rowspan">
				<xsl:attribute name="rowspan">
					<xsl:value-of select="@rowspan"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@colspan">
				<xsl:attribute name="colspan">
					<xsl:value-of select="@colspan"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="../../../@is_line_select='false'">
				<xsl:if test="../../@use_context_menu='true'">
					<xsl:for-each select="../rightclick">
						<xsl:call-template name="generate_action_on_right_click_cell"/>
					</xsl:for-each>
					<xsl:for-each select="rightclick">
						<xsl:call-template name="generate_action_on_right_click_cell"/>
					</xsl:for-each>
				</xsl:if>
				<xsl:for-each select="../dblclick">
					<xsl:call-template name="generate_action_on_double_click_cell">
						<xsl:with-param name="row_id">
							<xsl:value-of select="../@zobject"/>
						</xsl:with-param>
					</xsl:call-template>
				</xsl:for-each>
				<xsl:for-each select="dblclick">
					<xsl:call-template name="generate_action_on_double_click_cell">
						<xsl:with-param name="row_id">
							<xsl:value-of select="@zobject"/>
						</xsl:with-param>
					</xsl:call-template>
				</xsl:for-each>
				<xsl:attribute name="onclick">rsCell(this,event);</xsl:attribute>
				<xsl:attribute name="onMouseOver">rmocell(this,true,event);</xsl:attribute>
				<xsl:attribute name="onMouseOut">rmocell(this,false,event);</xsl:attribute>
			</xsl:if>
			<xsl:if test="@tooltip">
				<xsl:attribute name="title">
					<xsl:value-of select="@tooltip"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:attribute name="style">
				<xsl:if test="@word_wrap">
					<xsl:call-template name="generate_inline_style_no_location">
						<xsl:with-param name="style_attr">
							<xsl:if test="@height">height:<xsl:value-of select="@height"/>;</xsl:if>white-space: pre; white-space: pre-wrap; white-space: pre-line; white-space: -pre-wrap; white-space: -o-pre-wrap; white-space: -moz-pre-wrap; white-space: -hp-pre-wrap; word-wrap: break-word; text-align:<xsl:value-of select="$halignment"/>;vertical-align:<xsl:value-of select="@valignment"/>
						</xsl:with-param>
					</xsl:call-template>
				</xsl:if>
				<xsl:if test="not(@word_wrap)">
					<xsl:call-template name="generate_inline_style_no_location">
						<xsl:with-param name="style_attr">
							<xsl:if test="@height">height:<xsl:value-of select="@height"/>;</xsl:if>white-space:nowrap; text-align:<xsl:value-of select="$halignment"/>;vertical-align:<xsl:value-of select="@valignment"/>;</xsl:with-param>
					</xsl:call-template>
				</xsl:if>
				<xsl:if test="$visible='false'">display:none;</xsl:if>
				<xsl:if test="$width!=0">width: <xsl:value-of select="$width"/>px;</xsl:if>
			</xsl:attribute>
			<xsl:call-template name="set_class"/>
			<xsl:choose>
				<xsl:when test="control">
					<xsl:for-each select="control">
						<xsl:apply-templates select="*"/>
					</xsl:for-each>
				</xsl:when>
				<xsl:when test="html_data">
					<xsl:for-each select="html_data">
						<xsl:value-of select="."
            							disable-output-escaping="yes"/>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="."/>
				</xsl:otherwise>
			</xsl:choose>
		</td>
	</xsl:template>
	<xsl:template match="color_field">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>_preview</xsl:attribute>
			<xsl:attribute name="style">
				<xsl:call-template name="generate_inline_style">
					<xsl:with-param name="style_attr">border-top-width: 1px; border-right-width: 1px; border-left-width: 1px; border-style: solid; border-top-color: #000000; border-right-color: #000000; border-left-color: #000000; background-color: #<xsl:value-of select="text/."/>; </xsl:with-param>
				</xsl:call-template>
			</xsl:attribute>
		</div>
		<INPUT type="hidden">
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
			<xsl:attribute name="name">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
			<xsl:attribute name="value">
				<xsl:value-of select="text/."/>
			</xsl:attribute>
		</INPUT>
		<xsl:call-template name="basic_generate_form_component_scripts_out"/>
		<xsl:if test="@visible and (@visible='true')">
			<img>
				<xsl:attribute name="style">position:absolute;left:<xsl:value-of select="@icon_x"/>px;top:<xsl:value-of select="@y"/>px;height:<xsl:value-of select="@icon_y"/>px;cursor:pointer; </xsl:attribute>
				<xsl:attribute name="name">
					<xsl:value-of select="$fullname"/>_trigger</xsl:attribute>
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_trigger</xsl:attribute>
				<xsl:if test="@pop_up_icon">
					<xsl:attribute name="src">
						<xsl:value-of select="$skin_path"/>/<xsl:value-of select="@pop_up_icon"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:attribute name="onclick">openColor(event,'<xsl:value-of select="$fullname"/>_trigger','<xsl:value-of select="$fullname"/>')</xsl:attribute>
			</img>
		</xsl:if>
	</xsl:template>
	<xsl:template match="text_field">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<INPUT type="text">
			<xsl:call-template name="basic_generate_text_field"/>
		</INPUT>
		<xsl:if test="@visible and (@visible='true')">
			<xsl:if test="@pop_up_icon">
				<img>
					<xsl:attribute name="style">position:absolute;left:<xsl:value-of select="@icon_x"/>px;top:<xsl:value-of select="@y"/>px;height:<xsl:value-of select="@icon_y"/>px;cursor:pointer; </xsl:attribute>
					<xsl:attribute name="name">
						<xsl:value-of select="$fullname"/>_trigger</xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_trigger</xsl:attribute>
					<xsl:if test="@pop_up_icon">
						<xsl:attribute name="src">
							<xsl:value-of select="$skin_path"/>/<xsl:value-of select="@pop_up_icon"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:attribute name="onclick">openCalculator(event,'<xsl:value-of select="$fullname"/>_trigger','<xsl:value-of select="$fullname"/>')</xsl:attribute>
				</img>
			</xsl:if>
		</xsl:if>
	</xsl:template>
	<xsl:template match="gmap_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
						</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:if test="@label_lateral">
				<label>
					<xsl:attribute name="for">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<xsl:if test="@label_class">
						<xsl:attribute name="class">
							<xsl:value-of select="@label_class"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="@label_lateral"/>
				</label>
			</xsl:if>
			<xsl:call-template name="basic_render_map_responsive"/>
		</div>
	</xsl:template>
	<xsl:template name="basic_render_map_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<INPUT type="HIDDEN">
			<xsl:attribute name="value">
				<xsl:value-of select="gmap/@lat"/>, <xsl:value-of select="gmap/@lng"/>
			</xsl:attribute>
			<xsl:call-template name="basic_generate_form_component_responsive"/>
		</INPUT>
		<xsl:call-template name="basic_generate_form_component_scripts_out"/>
		<DIV>
			<xsl:attribute name="id">
				<xsl:value-of select="@form_name"/>_<xsl:value-of select="@name"/>_gmap</xsl:attribute>
			<xsl:attribute name="style">width:100%; height:100%; </xsl:attribute>
			<xsl:if test="@class_responsive">
				<xsl:attribute name="class">
					<xsl:value-of select="@class_responsive"/>
				</xsl:attribute>
			</xsl:if>
		</DIV>
		<script>setComponenteMapa("<xsl:value-of select="@form_name"/>_<xsl:value-of select="@name"/>_gmap"); var mapa = initializeMapa(); cleanMapa(); <xsl:if test="gmap/waypoint">
				<xsl:if test="gmap/@modo='polygon'">var area= new Array(); <xsl:for-each select="gmap/waypoint">area[<xsl:value-of select="position()-1"/>]= new google.maps.LatLng(<xsl:value-of select="@lat"/>,<xsl:value-of select="@lng"/>); </xsl:for-each>addPol(area, "#669933", 5, 0.7, "#996633", 0.4); </xsl:if>
				<xsl:if test="gmap/@modo='points'">addDir('<xsl:value-of select="gmap/@mapdir"/>', '<xsl:value-of select="gmap/@mapname"/>',null); var icon=null; <xsl:for-each select="gmap/waypoint">icon=iconMapaDefa; <xsl:for-each select="link">icon = new google.maps.MarkerImage( '<xsl:choose>
								<xsl:when test="image/@source='webapp_url'">
									<xsl:value-of select="image/@file"/>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="image/@source"/>
									<xsl:text disable-output-escaping="yes">/</xsl:text>
									<xsl:value-of select="image/@file"/>
								</xsl:otherwise>
							</xsl:choose>', new google.maps.Size(40, 60), new google.maps.Point(0, 0), new google.maps.Point(20, 60) ); </xsl:for-each>addDir('<xsl:value-of select="@mapdir"/>','<xsl:value-of select="@mapname"/>',icon); </xsl:for-each>
				</xsl:if>
			</xsl:if>
			<xsl:if test="gmap/@lat">var pointer = { lat: <xsl:value-of select="gmap/@lat"/>, lng: <xsl:value-of select="gmap/@lng"/>}; var marker=addPointMapa(mapa,pointer,iconMapaDefa,""); </xsl:if>dibujarMapa(mapa); <xsl:if test="@editable!='false'">google.maps.event.addListener(mapa,"click", function(event) { cleanMarcas(); addPointMapa(this,event.latLng,iconMapaDefa,""); document.getElementById("<xsl:value-of select="$fullname"/>").value=""+event.latLng.lat()+","+event.latLng.lng(); }); </xsl:if>
		</script>
	</xsl:template>
	<xsl:template match="color_field_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
						</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:if test="@label_lateral">
				<label>
					<xsl:attribute name="for">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<xsl:if test="@label_class">
						<xsl:attribute name="class">
							<xsl:value-of select="@label_class"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="@label_lateral"/>
				</label>
			</xsl:if>
			<div>
				<xsl:if test="@enteristab">
					<xsl:attribute name="class">>enteristab</xsl:attribute>
				</xsl:if>
				<xsl:if test="not(@enteristab)">
					<xsl:attribute name="class">>focuseable</xsl:attribute>
				</xsl:if>
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_preview</xsl:attribute>
				<xsl:attribute name="style">
					<xsl:call-template name="generate_inline_style_responsive">
						<xsl:with-param name="style_attr">border-width: 1px; border-style: solid; height:30px; border-color: #000000; background-color: #<xsl:value-of select="text/."/>; </xsl:with-param>
					</xsl:call-template>
				</xsl:attribute>
				<xsl:attribute name="onclick">openColor(event,'<xsl:value-of select="$fullname"/>_preview','<xsl:value-of select="$fullname"/>')</xsl:attribute>
			</div>
			<INPUT type="hidden">
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:attribute name="name">
					<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:attribute name="value">
					<xsl:value-of select="text/."/>
				</xsl:attribute>
			</INPUT>
			<xsl:call-template name="basic_generate_form_component_scripts_out"/>
		</div>
	</xsl:template>
	<xsl:template match="checkselect">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<INPUT type="hidden">
			<xsl:attribute name="data-checkselect">true</xsl:attribute>
			<xsl:call-template name="basic_generate_text_field_responsive"/>
		</INPUT>
		<xsl:call-template name="basic_generate_form_component_scripts_out"/>
	</xsl:template>
	<xsl:template match="fromto_field_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="fullnamefrom">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@propname_from"/>
		</xsl:variable>
		<xsl:variable name="fullnameto">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@propname_to"/>
		</xsl:variable>
		<div>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
						</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:if test="(@label_lateral or labelicon) and (not(@size_responsive) or @size_responsive!='inline_component')">
				<label>
					<xsl:attribute name="for">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<xsl:if test="@label_class">
						<xsl:attribute name="class">
							<xsl:value-of select="@label_class"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="content_layout/@halignment">
						<xsl:attribute name="style">text-align:<xsl:value-of select="content_layout/@halignment"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="@label_lateral"/>
					<xsl:if test="labelicon">
						<xsl:for-each select="labelicon">
							<xsl:call-template name="render_icon"/>
						</xsl:for-each>
					</xsl:if>
				</label>
			</xsl:if>
			<div>
				<xsl:attribute name="class">
					<xsl:if test="@field_class and (@label_lateral or labelicon)">
						<xsl:value-of select="@field_class"/>
					</xsl:if>
				</xsl:attribute>
				<div>
					<xsl:attribute name="class">input-group </xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<input>
						<xsl:attribute name="class">form-control<![CDATA[ ]]>input-sm <xsl:if test="@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
						<xsl:attribute name="id">
							<xsl:value-of select="$fullnamefrom"/>
						</xsl:attribute>
						<xsl:call-template name="basic_generate_text_field_responsive">
							<xsl:with-param name="name">
								<xsl:value-of select="@propname_from"/>
							</xsl:with-param>
						</xsl:call-template>
					</input>
					<div class="input-group-addon">
						<span class="fa fa-long-arrow-alt-right"/>
					</div>
					<input>
						<xsl:attribute name="class">form-control<![CDATA[ ]]>input-sm <xsl:if test="@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
						<xsl:attribute name="id">
							<xsl:value-of select="$fullnameto"/>
						</xsl:attribute>
						<xsl:call-template name="basic_generate_text_field_responsive">
							<xsl:with-param name="name">
								<xsl:value-of select="@propname_to"/>
							</xsl:with-param>
						</xsl:call-template>
					</input>
				</div>
				<script>initializeFromTo('<xsl:value-of select="$fullnamefrom"/>','<xsl:value-of select="$fullnameto"/>','<xsl:value-of select="text/."/>'); registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullnamefrom"/>','<xsl:value-of select="$fullnameto"/>']); registerNoConstraint('<xsl:value-of select="$fullnamefrom"/>',['<xsl:value-of select="$fullnamefrom"/>']); registerNoConstraint('<xsl:value-of select="$fullnameto"/>',['<xsl:value-of select="$fullnameto"/>']); </script>
			</div>
			<xsl:if test="(@label_lateral or labelicon) and (@size_responsive and @size_responsive='inline_component')">
				<label>
					<xsl:attribute name="for">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<xsl:if test="@label_class">
						<xsl:attribute name="class">
							<xsl:value-of select="@label_class"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="content_layout/@halignment">
						<xsl:attribute name="style">text-align:<xsl:value-of select="content_layout/@halignment"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="@label_lateral"/>
					<xsl:if test="labelicon">
						<xsl:for-each select="labelicon">
							<xsl:call-template name="render_icon"/>
						</xsl:for-each>
					</xsl:if>
				</label>
			</xsl:if>
		</div>
	</xsl:template>
	<xsl:template match="text_field_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
						</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:if test="(@label_lateral or labelicon) and (not(@size_responsive) or @size_responsive!='inline_component')">
				<label>
					<xsl:attribute name="for">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<xsl:if test="@label_class">
						<xsl:attribute name="class">
							<xsl:value-of select="@label_class"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="content_layout/@halignment_label">
						<xsl:attribute name="style">text-align:<xsl:value-of select="content_layout/@halignment_label"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="@label_lateral"/>
					<xsl:if test="labelicon">
						<xsl:for-each select="labelicon">
							<xsl:call-template name="render_icon"/>
						</xsl:for-each>
					</xsl:if>
				</label>
			</xsl:if>
			<xsl:choose>
				<xsl:when test="@pop_up_icon and @visible and (@visible='true') and @editable='true'">
					<div>
						<xsl:attribute name="class">
							<xsl:if test="@field_class and (@label_lateral or labelicon)">
								<xsl:value-of select="@field_class"/>
							</xsl:if>
						</xsl:attribute>
						<div>
							<xsl:attribute name="class">input-group </xsl:attribute>
							<input>
								<xsl:attribute name="class">form-control<![CDATA[ ]]>input-sm <xsl:if test="@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
								<xsl:call-template name="basic_generate_text_field_responsive"/>
								<span class="input-group-addon">
									<i>
										<xsl:attribute name="class">
											<xsl:value-of select="@pop_up_icon"/>
										</xsl:attribute>
										<xsl:attribute name="onclick">openCalculator(event,'<xsl:value-of select="$fullname"/>','<xsl:value-of select="$fullname"/>')</xsl:attribute>
									</i>
								</span>
							</input>
							<xsl:call-template name="basic_generate_form_component_scripts_out"/>
						</div>
					</div>
				</xsl:when>
				<xsl:otherwise>
					<xsl:if test="@size_responsive and @size_responsive='inline_component'">
						<input>
							<xsl:attribute name="class">form-control<![CDATA[ ]]>input-sm <xsl:if test="@outstanding">><![CDATA[ ]]>outstanding</xsl:if>
								<xsl:if test="@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
							<xsl:attribute name="formnovalidate"/>
							<xsl:attribute name="type">text</xsl:attribute>
							<xsl:if test="@autocomplete='off'">
								<xsl:attribute name="autocomplete">off</xsl:attribute>
							</xsl:if>
							<xsl:call-template name="basic_generate_text_field_responsive"/>
						</input>
						<xsl:call-template name="basic_generate_form_component_scripts_out"/>
					</xsl:if>
					<xsl:if test="not(@size_responsive and @size_responsive='inline_component')">
						<div>
							<xsl:attribute name="class">
								<xsl:if test="@field_class and (@label_lateral or labelicon)">
									<xsl:value-of select="@field_class"/>
								</xsl:if>
							</xsl:attribute>
							<div>
								<xsl:attribute name="class">input-group </xsl:attribute>
								<input>
									<xsl:attribute name="class">form-control<![CDATA[ ]]>input-sm <xsl:if test="@outstanding">><![CDATA[ ]]>outstanding</xsl:if>
										<xsl:if test="@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
									<xsl:attribute name="formnovalidate"/>
									<xsl:attribute name="type">text</xsl:attribute>
									<xsl:call-template name="basic_generate_text_field_responsive"/>
								</input>
								<xsl:call-template name="basic_generate_form_component_scripts_out"/>
							</div>
						</div>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:if test="(@label_lateral or labelicon) and (@size_responsive and @size_responsive='inline_component')">
				<label>
					<xsl:attribute name="for">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<xsl:if test="@label_class">
						<xsl:attribute name="class">
							<xsl:value-of select="@label_class"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="content_layout/@halignment_label">
						<xsl:attribute name="style">text-align:<xsl:value-of select="content_layout/@halignment_label"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="@label_lateral"/>
					<xsl:if test="labelicon">
						<xsl:for-each select="labelicon">
							<xsl:call-template name="render_icon"/>
						</xsl:for-each>
					</xsl:if>
				</label>
			</xsl:if>
		</div>
		<script>
			<xsl:if test="@connect_control">subscribeControlConnect( '<xsl:value-of select="@connect_control"/>', function() { hideSelectMetaItemsEdit('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); hideSelectMetaItemsEdit('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone_to','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); }); </xsl:if>
		</script>
	</xsl:template>
	<xsl:template match="text_label">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<INPUT type="text">
			<xsl:call-template name="basic_generate_text_field"/>
		</INPUT>
	</xsl:template>
	<xsl:template match="text_label_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="@label_lateral=text/.">
				<label>
					<xsl:attribute name="class">
						<xsl:value-of select="@size_responsive"/>
						<xsl:if test="@label_class">
							<xsl:value-of select="@label_class"/>
						</xsl:if>
					</xsl:attribute>
					<xsl:value-of select="@label_lateral"/>
					<xsl:value-of select="text/."/>
				</label>
			</xsl:when>
			<xsl:otherwise>
				<div>
					<xsl:attribute name="class">
						<xsl:choose>
							<xsl:when test="@visible and (@visible='true')">
								<xsl:if test="@size_responsive">
									<xsl:value-of select="@size_responsive"/>
								</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
							</xsl:when>
							<xsl:otherwise>form-group hidden</xsl:otherwise>
						</xsl:choose>
						<xsl:if test="@only-expanded">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
						<xsl:if test="@only-collapsed">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
					</xsl:attribute>
					<xsl:if test="not(@size_responsive) or @size_responsive!='inline_component'">
						<xsl:if test="@label_lateral">
							<label>
								<xsl:attribute name="for">
									<xsl:value-of select="$fullname"/>
								</xsl:attribute>
								<xsl:attribute name="class">
									<xsl:if test="@label_class">
										<xsl:value-of select="@label_class"/>
									</xsl:if>
								</xsl:attribute>
								<xsl:value-of select="@label_lateral"/>
							</label>
						</xsl:if>
						<div>
							<xsl:attribute name="class">data_label<![CDATA[ ]]><xsl:if test="@field_class">
									<xsl:value-of select="@field_class"/>
								</xsl:if>
							</xsl:attribute>
							<div>
								<xsl:attribute name="class">input-group<![CDATA[ ]]>sinborde<![CDATA[ ]]></xsl:attribute>
								<input>
									<xsl:attribute name="class">form-control<![CDATA[ ]]>sinborde<![CDATA[ ]]>input-sm <xsl:if test="@outstanding">outstanding</xsl:if>
									</xsl:attribute>
									<xsl:attribute name="formnovalidate"/>
									<xsl:attribute name="type">text</xsl:attribute>
									<xsl:attribute name="readonly">readonly</xsl:attribute>
									<xsl:call-template name="basic_generate_text_field_responsive"/>
								</input>
								<xsl:call-template name="basic_generate_form_component_scripts_out"/>
							</div>
						</div>
					</xsl:if>
					<xsl:if test="@size_responsive and @size_responsive='inline_component'">
						<input>
							<xsl:attribute name="class">form-control<![CDATA[ ]]>sinborde<![CDATA[ ]]>input-sm <xsl:if test="@outstanding">outstanding</xsl:if>
							</xsl:attribute>
							<xsl:attribute name="formnovalidate"/>
							<xsl:attribute name="type">text</xsl:attribute>
							<xsl:attribute name="readonly">readonly</xsl:attribute>
							<xsl:call-template name="basic_generate_text_field_responsive"/>
						</input>
						<xsl:call-template name="basic_generate_form_component_scripts_out"/>
						<xsl:if test="@label_lateral">
							<label>
								<xsl:attribute name="for">
									<xsl:value-of select="$fullname"/>
								</xsl:attribute>
								<xsl:attribute name="class">data_label<![CDATA[ ]]><xsl:if test="@label_class">
										<xsl:value-of select="@label_class"/>
									</xsl:if>
								</xsl:attribute>
								<xsl:value-of select="@label_lateral"/>
							</label>
						</xsl:if>
					</xsl:if>
				</div>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="html_text_area_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="@diferido='true'">
				<div>
					<xsl:call-template name="basic_generate_component_responsive"/>
					<div>
						<xsl:attribute name="id">
							<xsl:value-of select="@htmlarea_content"/>
						</xsl:attribute>
						<xsl:attribute name="style">
							<xsl:call-template name="generate_inline_style_responsive"/>
						</xsl:attribute>
					</div>
				</div>
				<script type="POSTSCRIPT">
					<xsl:for-each select="diferido/action">
						<xsl:call-template name="generate_action_invokation"/>
					</xsl:for-each>
				</script>
			</xsl:when>
			<xsl:otherwise>
				<div>
					<xsl:attribute name="class">
						<xsl:choose>
							<xsl:when test="@visible and (@visible='true')">
								<xsl:if test="@size_responsive">
									<xsl:value-of select="@size_responsive"/>
								</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
							</xsl:when>
							<xsl:otherwise>form-group hidden</xsl:otherwise>
						</xsl:choose>
						<xsl:if test="@only-expanded">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
						<xsl:if test="@only-collapsed">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
					</xsl:attribute>
					<xsl:if test="@label_lateral">
						<label>
							<xsl:attribute name="for">
								<xsl:value-of select="$fullname"/>
							</xsl:attribute>
							<xsl:if test="@label_class">
								<xsl:attribute name="class">
									<xsl:value-of select="@label_class"/>
								</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="@label_lateral"/>
						</label>
					</xsl:if>
					<div>
						<xsl:attribute name="class">
							<xsl:if test="@field_class and (@label_lateral or labelicon)">
								<xsl:value-of select="@field_class"/>
							</xsl:if>
						</xsl:attribute>
						<div>
							<xsl:attribute name="class">input-group </xsl:attribute>
							<xsl:attribute name="id">
								<xsl:value-of select="$fullname"/>
							</xsl:attribute>
							<xsl:call-template name="basic_generate_form_component_responsive">
								<xsl:with-param name="isTextComponent">true</xsl:with-param>
								<xsl:with-param name="noScripts">true</xsl:with-param>
								<xsl:with-param name="style_attr">width:100%;</xsl:with-param>
							</xsl:call-template>
							<xsl:call-template name="basic_generate_form_component_scripts_out"/>
						</div>
					</div>
				</div>
				<script language="JavaScript"
      					type="text/javascript">addrte('<xsl:value-of select="$fullname"/>','<xsl:value-of select="$fullname"/>_i','<xsl:value-of select="text/."/>', '<xsl:if test="@editable='true'">0</xsl:if>
					<xsl:if test="@editable='false'">1</xsl:if>', '<xsl:if test="@formulario='true'">1</xsl:if>
					<xsl:if test="@formulario='false'">0</xsl:if>', '<xsl:if test="@isweb='true'">1</xsl:if>
					<xsl:if test="@isweb='false'">0</xsl:if>', <xsl:value-of select="@margen_izq"/>,<xsl:value-of select="@margen_imgsup"/>, <xsl:value-of select="@margen_imgleft"/>,<xsl:value-of select="@margen_imgsize"/>, '/<xsl:value-of select="$url_prefix"/>/pss_data/<xsl:value-of select="@fondo"/>', <xsl:value-of select="@ancho_pagina"/>, <xsl:value-of select="@mapa_plantilla"/>,'<xsl:value-of select="@mapa_source"/>'); registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>_i_WYSIWYG_Editor']); </script>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="html_text_area">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:call-template name="basic_generate_form_component">
				<xsl:with-param name="isTextComponent">true</xsl:with-param>
				<xsl:with-param name="noScripts">true</xsl:with-param>
				<xsl:with-param name="style_attr">overflow:hidden</xsl:with-param>
			</xsl:call-template>
		</div>
		<script language="JavaScript"
      			type="text/javascript">addrte('<xsl:value-of select="$fullname"/>','<xsl:value-of select="$fullname"/>_i','<xsl:value-of select="text/."/>', '<xsl:if test="@editable='true'">0</xsl:if>
			<xsl:if test="@editable='false'">1</xsl:if>', '<xsl:if test="@formulario='true'">1</xsl:if>
			<xsl:if test="@formulario='false'">0</xsl:if>', '<xsl:if test="@isweb='true'">1</xsl:if>
			<xsl:if test="@isweb='false'">0</xsl:if>', <xsl:value-of select="@margen_izq"/>,<xsl:value-of select="@margen_imgsup"/>, <xsl:value-of select="@margen_imgleft"/>,<xsl:value-of select="@margen_imgsize"/>, '/<xsl:value-of select="$url_prefix"/>/pss_data/<xsl:value-of select="@fondo"/>', <xsl:value-of select="@ancho_pagina"/>, <xsl:value-of select="@mapa_plantilla"/>,'<xsl:value-of select="@mapa_source"/>'); registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>_i_WYSIWYG_Editor']); </script>
	</xsl:template>
	<xsl:template match="password_field_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
						</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:if test="@label_lateral">
				<label>
					<xsl:attribute name="for">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<xsl:if test="@label_class">
						<xsl:attribute name="class">
							<xsl:value-of select="@label_class"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="@label_lateral"/>
				</label>
			</xsl:if>
			<div>
				<xsl:attribute name="class">
					<xsl:if test="@field_class">
						<xsl:value-of select="@field_class"/>
					</xsl:if>
				</xsl:attribute>
				<div>
					<xsl:attribute name="class">input-group </xsl:attribute>
					<input type="password">
						<xsl:attribute name="class">form-control input-sm <xsl:if test="@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
						<xsl:attribute name="autocomplete">off</xsl:attribute>
						<xsl:call-template name="basic_generate_text_field_responsive">
							<xsl:with-param name="isEncrypted">true</xsl:with-param>
						</xsl:call-template>
					</input>
					<xsl:call-template name="basic_generate_form_component_scripts_out">
						<xsl:with-param name="isEncrypted">true</xsl:with-param>
					</xsl:call-template>
				</div>
			</div>
		</div>
	</xsl:template>
	<xsl:template match="password_field">
		<INPUT type="password">
			<xsl:call-template name="basic_generate_text_field">
				<xsl:with-param name="isEncrypted">true</xsl:with-param>
			</xsl:call-template>
		</INPUT>
	</xsl:template>
	<xsl:template match="text_area">
		<TEXTAREA type="textarea">
			<xsl:if test="/page/header/savehelp">
				<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
				</xsl:variable>
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
			</xsl:if>
			<xsl:call-template name="basic_generate_form_component">
				<xsl:with-param name="isTextComponent">true</xsl:with-param>
				<xsl:with-param name="noScripts">true</xsl:with-param>
			</xsl:call-template>
			<xsl:if test="text">
				<xsl:value-of select="text/."/>
			</xsl:if>
		</TEXTAREA>
		<xsl:call-template name="basic_generate_form_component_scripts_out"/>
	</xsl:template>
	<xsl:template match="text_area_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:if test="/page/header/savehelp">
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
			</xsl:if>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
						</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:if test="@label_lateral">
				<label>
					<xsl:attribute name="for">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<xsl:if test="@label_class">
						<xsl:attribute name="class">
							<xsl:value-of select="@label_class"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="@label_lateral"/>
				</label>
			</xsl:if>
			<div>
				<xsl:attribute name="class">
					<xsl:if test="@field_class">
						<xsl:value-of select="@field_class"/>
					</xsl:if>
				</xsl:attribute>
				<div>
					<xsl:attribute name="class">input-group </xsl:attribute>
					<TEXTAREA type="textarea">
						<xsl:attribute name="class">form-control input-sm <xsl:if test="@outstanding">><![CDATA[ ]]>outstanding</xsl:if>
							<xsl:if test="@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
						<xsl:call-template name="basic_generate_form_component_responsive">
							<xsl:with-param name="isTextComponent">true</xsl:with-param>
							<xsl:with-param name="noScripts">true</xsl:with-param>
						</xsl:call-template>
						<xsl:attribute name="onchange">
							<xsl:if test="@detect_changes='true'">setChangeInputs(true);</xsl:if>
						</xsl:attribute>
						<xsl:attribute name="oninput">
							<xsl:if test="@detect_changes='true'">setChangeInputs(true);</xsl:if>
						</xsl:attribute>
						<xsl:if test="text">
							<xsl:value-of select="text/."/>
						</xsl:if>
					</TEXTAREA>
				</div>
			</div>
			<xsl:call-template name="basic_generate_form_component_scripts_out"/>
		</div>
	</xsl:template>
	<xsl:template match="check_box">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="onchange_event_var">document.getElementById('<xsl:value-of select="$fullname"/>').value=this.checked?'S':'N';</xsl:variable>
		<DIV>
			<xsl:call-template name="basic_generate_component"/>
			<INPUT type="hidden">
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:attribute name="name">
					<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:if test="@checked='S'">
					<xsl:attribute name="value">S</xsl:attribute>
				</xsl:if>
				<xsl:if test="@checked!='S'">
					<xsl:attribute name="value">N</xsl:attribute>
				</xsl:if>
			</INPUT>
			<INPUT type="checkbox">
				<xsl:attribute name="name">
					<xsl:value-of select="$fullname"/>_check</xsl:attribute>
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_check</xsl:attribute>
				<xsl:if test="@checked='S'">
					<xsl:attribute name="checked"/>
				</xsl:if>
				<xsl:if test="@editable!='true'">
					<xsl:attribute name="readonly">readonly</xsl:attribute>
					<xsl:attribute name="tabindex">-1</xsl:attribute>
					<xsl:attribute name="disabled">disabled</xsl:attribute>
				</xsl:if>
				<xsl:if test="@force_focus">
					<xsl:attribute name="autofocus">true</xsl:attribute>
				</xsl:if>
				<xsl:for-each select="onblur">
					<xsl:call-template name="generate_action_onblur"/>
				</xsl:for-each>
				<xsl:attribute name="onkeydown">if(event.keyCode==32) this.click(); </xsl:attribute>
				<xsl:if test="@enteristab">
					<xsl:attribute name="class">enteristab</xsl:attribute>
				</xsl:if>
				<xsl:if test="not(@enteristab)">
					<xsl:attribute name="class">focuseable</xsl:attribute>
				</xsl:if>
				<xsl:call-template name="basic_generate_form_component_scripts_in">
					<xsl:with-param name="onchange_event_var"
              						select="$onchange_event_var"/>
					<xsl:with-param name="change_mode"
              						select="'onclick'"/>
				</xsl:call-template>
				<xsl:call-template name="basic_set_editable">
					<xsl:with-param name="isTextComponent">false</xsl:with-param>
				</xsl:call-template>
			</INPUT>
			<xsl:if test="@label_lateral">
				<label>
					<xsl:attribute name="for">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<xsl:if test="@label_class">
						<xsl:attribute name="class">
							<xsl:value-of select="@label_class"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="@label_lateral"/>
				</label>
			</xsl:if>
		</DIV>
		<xsl:call-template name="basic_generate_form_component_scripts_out"/>
	</xsl:template>
	<xsl:template match="collapsable_responsive">
		<xsl:variable name="fullname">
			<xsl:value-of select="@name"/>
		</xsl:variable>
		<button type="button">
			<xsl:attribute name="class">
				<xsl:value-of select="@class_responsive"/>
			</xsl:attribute>
			<xsl:attribute name="data-toggle">
				<xsl:value-of select="@data-toggle"/>
			</xsl:attribute>
			<xsl:attribute name="data-target">
				<xsl:value-of select="@data-target"/>
			</xsl:attribute>
			<xsl:if test="/page/header/savehelp">
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
			</xsl:if>
			<i>
				<xsl:attribute name="class">
					<xsl:value-of select="@image"/>
				</xsl:attribute>
			</i>
		</button>
	</xsl:template>
	<xsl:template match="check_box_responsive_noform">
		<xsl:variable name="fullname">
			<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="onchange_event_var">document.getElementById('<xsl:value-of select="$fullname"/>').value=this.checked?'S':'N';</xsl:variable>
		<xsl:if test="/page/header/savehelp">
			<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
		</xsl:if>
		<label>
			<xsl:attribute name="class">
				<xsl:value-of select="@class_responsive"/>
			</xsl:attribute>
			<xsl:value-of select="@label"/>
			<INPUT type="checkbox">
				<xsl:attribute name="name">
					<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:if test="@mode">
					<xsl:attribute name="data-toggle">
						<xsl:value-of select="@mode"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:if test="@dataon">
					<xsl:attribute name="data-on">
						<xsl:value-of select="@dataon"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:if test="@dataoff">
					<xsl:attribute name="data-off">
						<xsl:value-of select="@dataoff"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:if test="@datasize">
					<xsl:attribute name="data-size">
						<xsl:value-of select="@datasize"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:if test="@datawidth">
					<xsl:attribute name="data-width">
						<xsl:value-of select="@datawidth"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:if test="@dataheight">
					<xsl:attribute name="data-height">
						<xsl:value-of select="@dataheight"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:if test="@dataonstyle">
					<xsl:attribute name="data-onstyle">
						<xsl:value-of select="@dataonstyle"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:if test="@dataoffstyle">
					<xsl:attribute name="data-offstyle">
						<xsl:value-of select="@dataoffstyle"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:if test="@datastyle">
					<xsl:attribute name="data-style">
						<xsl:value-of select="@datastyle"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:if test="@checked='S'">
					<xsl:attribute name="checked"/>
				</xsl:if>
				<xsl:if test="@editable!='true'">
					<xsl:attribute name="readonly">readonly</xsl:attribute>
					<xsl:attribute name="tabindex">-1</xsl:attribute>
					<xsl:attribute name="disabled">disabled</xsl:attribute>
				</xsl:if>
				<xsl:attribute name="class">
					<xsl:if test="@mode='square'">regular-checkbox </xsl:if>
					<xsl:if test="@mode='bigsquare'">regular-checkbox<![CDATA[ ]]>big-checkbox </xsl:if>
					<xsl:if test="@mode='cicle'">regular-radio </xsl:if>
					<xsl:if test="@mode='bigcircle'">regular-radio<![CDATA[ ]]>big-radio </xsl:if><![CDATA[ ]]>pss-checkbox </xsl:attribute>
				<xsl:for-each select="onblur">
					<xsl:call-template name="generate_action_onblur"/>
				</xsl:for-each>
				<xsl:attribute name="onkeydown">if(event.keyCode==32) this.click(); </xsl:attribute>
				<xsl:attribute name="onclick">
					<xsl:for-each select="action">
						<xsl:call-template name="generate_action_invokation"/>
					</xsl:for-each>
				</xsl:attribute>
				<xsl:call-template name="basic_set_editable">
					<xsl:with-param name="isTextComponent">false</xsl:with-param>
				</xsl:call-template>
			</INPUT>
			<xsl:if test="@mode!='toggle'">
				<span>
					<xsl:attribute name="for">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<xsl:attribute name="onclick">document.getElementById('<xsl:value-of select="$fullname"/>').click();</xsl:attribute>
				</span>
			</xsl:if>
			<script type="text/javascript">inicializeCheckBox('<xsl:value-of select="$fullname"/>'); </script>
		</label>
	</xsl:template>
	<xsl:template match="check_box_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="onchange_event_var">
			<xsl:if test="@mode!='toggle'">document.getElementById('<xsl:value-of select="$fullname"/>').value=this.checked?'S':'N'; </xsl:if>
		</xsl:variable>
		<div>
			<xsl:if test="/page/header/savehelp">
				<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
				</xsl:variable>
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
			</xsl:if>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
						</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:if test="@mode!='toggle'">
				<INPUT type="hidden">
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<xsl:attribute name="name">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<xsl:if test="@checked='S'">
						<xsl:attribute name="value">S</xsl:attribute>
					</xsl:if>
					<xsl:if test="@checked!='S'">
						<xsl:attribute name="value">N</xsl:attribute>
					</xsl:if>
				</INPUT>
			</xsl:if>
			<xsl:if test="@align='TOP'">
				<label>
					<xsl:attribute name="for">
						<xsl:value-of select="$fullname"/>
						<xsl:if test="@mode!='toggle'">_check</xsl:if>
					</xsl:attribute>
					<xsl:if test="@label_class">
						<xsl:attribute name="class">
							<xsl:value-of select="@label_class"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="@label_lateral"/>
				</label>
			</xsl:if>
			<div>
				<xsl:if test="@align='LEFT'">
					<xsl:attribute name="style">display: flex</xsl:attribute>
					<label>
						<xsl:attribute name="for">
							<xsl:value-of select="$fullname"/>
							<xsl:if test="@mode!='toggle'">_check</xsl:if>
						</xsl:attribute>
						<xsl:if test="@label_class">
							<xsl:attribute name="class">
								<xsl:value-of select="@label_class"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:value-of select="@label_lateral"/>
					</label>
				</xsl:if>
				<xsl:call-template name="basic_generate_component_responsive"/>
				<xsl:if test="@mode!='toggle'">
					<xsl:attribute name="class">input-group s<xsl:if test="@field_class">
							<xsl:value-of select="@field_class"/>
						</xsl:if>
					</xsl:attribute>
				</xsl:if>
				<INPUT type="checkbox">
					<xsl:attribute name="name">
						<xsl:value-of select="$fullname"/>
						<xsl:if test="@mode!='toggle'">_check</xsl:if>
					</xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>
						<xsl:if test="@mode!='toggle'">_check</xsl:if>
					</xsl:attribute>
					<xsl:if test="@mode and @mode='toggle'">
						<xsl:attribute name="data-toggle">
							<xsl:value-of select="@mode"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@dataon">
						<xsl:attribute name="data-on">
							<xsl:value-of select="@dataon"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@dataoff">
						<xsl:attribute name="data-off">
							<xsl:value-of select="@dataoff"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@datasize">
						<xsl:attribute name="data-size">
							<xsl:value-of select="@datasize"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@datawidth">
						<xsl:attribute name="data-width">
							<xsl:value-of select="@datawidth"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@dataheight">
						<xsl:attribute name="data-height">
							<xsl:value-of select="@dataheight"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@dataonstyle">
						<xsl:attribute name="data-onstyle">
							<xsl:value-of select="@dataonstyle"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@dataoffstyle">
						<xsl:attribute name="data-offstyle">
							<xsl:value-of select="@dataoffstyle"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@datastyle">
						<xsl:attribute name="data-style">
							<xsl:value-of select="@datastyle"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@checked='S'">
						<xsl:attribute name="checked"/>
					</xsl:if>
					<xsl:attribute name="class">
						<xsl:if test="@mode='single'">form-control<![CDATA[ ]]>input-sm<![CDATA[ ]]></xsl:if>
						<xsl:if test="@mode='square'">regular-checkbox </xsl:if>
						<xsl:if test="@mode='bigsquare'">regular-checkbox<![CDATA[ ]]>big-checkbox </xsl:if>
						<xsl:if test="@mode='cicle'">regular-radio </xsl:if>
						<xsl:if test="@mode='bigcircle'">regular-radio<![CDATA[ ]]>big-radio </xsl:if>
						<xsl:if test="@mode='animated'">label__checkbox </xsl:if>
						<xsl:if test="@enteristab"><![CDATA[ ]]>enteristab </xsl:if><![CDATA[ ]]>pss-checkbox <![CDATA[ ]]>focuseable <xsl:if test="@refreshForm='true'"><![CDATA[ ]]>refreshform</xsl:if>
					</xsl:attribute>
					<xsl:if test="@editable!='true'">
						<xsl:attribute name="readonly">readonly</xsl:attribute>
						<xsl:attribute name="tabindex">-1</xsl:attribute>
						<xsl:attribute name="disabled">disabled</xsl:attribute>
					</xsl:if>
					<xsl:if test="@force_focus">
						<xsl:attribute name="autofocus">true</xsl:attribute>
					</xsl:if>
					<xsl:for-each select="onblur">
						<xsl:call-template name="generate_action_onblur"/>
					</xsl:for-each>
					<xsl:attribute name="onkeydown">if(event.keyCode==32) this.click(); </xsl:attribute>
					<xsl:call-template name="basic_generate_form_component_scripts_in">
						<xsl:with-param name="onchange_event_var"
              							select="$onchange_event_var"/>
						<xsl:with-param name="change_mode"
              							select="'onclick'"/>
					</xsl:call-template>
					<xsl:call-template name="basic_set_editable">
						<xsl:with-param name="isTextComponent">false</xsl:with-param>
					</xsl:call-template>
				</INPUT>
				<xsl:if test="@mode!='toggle'">
					<span>
						<xsl:attribute name="for">
							<xsl:value-of select="$fullname"/>_check</xsl:attribute>
						<xsl:attribute name="onclick">document.getElementById('<xsl:value-of select="$fullname"/>_check').click();</xsl:attribute>
						<xsl:attribute name="onkeydown">if(event.keyCode==32) document.getElementById('<xsl:value-of select="$fullname"/>_check').click();</xsl:attribute>
						<xsl:if test="@mode='animated'">
							<xsl:attribute name="class">label__text</xsl:attribute>
							<span class="label__check">
								<i class="fa fa-check icon"/>
							</span>
						</xsl:if>
					</span>
				</xsl:if>
				<xsl:if test="@align='RIGHT'">
					<label>
						<xsl:attribute name="for">
							<xsl:value-of select="$fullname"/>
							<xsl:if test="@mode!='toggle'">_check</xsl:if>
						</xsl:attribute>
						<xsl:if test="@label_class">
							<xsl:attribute name="class">
								<xsl:value-of select="@label_class"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:value-of select="@label_lateral"/>
					</label>
				</xsl:if>
			</div>
			<xsl:if test="@align='BOTTOM'">
				<label>
					<xsl:attribute name="for">
						<xsl:value-of select="$fullname"/>
						<xsl:if test="@mode!='toggle'">_check</xsl:if>
					</xsl:attribute>
					<xsl:if test="@label_class">
						<xsl:attribute name="class">
							<xsl:value-of select="@label_class"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="@label_lateral"/>
				</label>
			</xsl:if>
		</div>
		<xsl:if test="@mode='toggle'">
			<script>inicializeCheckBox('<xsl:value-of select="$fullname"/>'); </script>
		</xsl:if>
		<xsl:if test="@connect_control">
			<script>subscribeControlConnect( '<xsl:value-of select="@connect_control"/>', function() { hideSelectMetaItemsCheck('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); hideSelectMetaItemsCheck('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone_to','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); }); </script>
		</xsl:if>
		<xsl:call-template name="basic_generate_form_component_scripts_out"/>
	</xsl:template>
	<xsl:template match="titled_border">
		<fieldset>
			<xsl:call-template name="basic_generate_component"/>
			<xsl:if test="@label">
				<legend>
					<xsl:value-of select="@label"/>
				</legend>
			</xsl:if>
		</fieldset>
	</xsl:template>
	<xsl:template match="web_button_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:if test="/page/header/savehelp">
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
			</xsl:if>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
						</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:choose>
				<xsl:when test="((image or icon) and not(@label))">
					<a>
						<xsl:call-template name="basic_generate_component_responsive"/>
						<xsl:if test="@readonly">
							<xsl:attribute name="readonly">readonly</xsl:attribute>
						</xsl:if>
						<xsl:if test="not(action)">
							<xsl:if test="@onCalculate and @isCalculeOthersFields">
								<xsl:attribute name="onClick">
									<xsl:value-of select="@onCalculate"/>; </xsl:attribute>
							</xsl:if>
						</xsl:if>
						<xsl:if test="action">
							<xsl:if test="@refreshForm='true'">
								<xsl:for-each select="action">
									<xsl:attribute name="onClick">goToRefreshForm('do-PartialRefreshForm', '<xsl:value-of select="$fullname"/>', '<xsl:value-of select="../action/@obj_provider"/>','<xsl:value-of select="../action/@object_owner_id"/>', getLastModal(), '<xsl:value-of select="../action/@id_action"/>'); var evt = event ? event:window.event; if (evt.stopPropagation) evt.stopPropagation(); if (evt.cancelBubble!=null) evt.cancelBubble = true; evt.preventDefault(); return false; </xsl:attribute>
								</xsl:for-each>
							</xsl:if>
							<xsl:if test="not(@refreshForm='true')">
								<xsl:call-template name="generate_action_on_click">
									<xsl:with-param name="onscript">
										<xsl:if test="@onCalculate and @isCalculeOthersFields">
											<xsl:value-of select="@onCalculate"/>; </xsl:if>
									</xsl:with-param>
								</xsl:call-template>
							</xsl:if>
						</xsl:if>
						<xsl:call-template name="view_image_responsive"/>
					</a>
				</xsl:when>
				<xsl:otherwise>
					<a>
						<xsl:call-template name="basic_generate_component_responsive">
							<xsl:with-param name="specialClass">input-group btn<![CDATA[ ]]><xsl:value-of select="@class_responsive"/>
								<xsl:if test="@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:with-param>
						</xsl:call-template>
						<xsl:if test="@readonly">
							<xsl:attribute name="readonly">readonly</xsl:attribute>
						</xsl:if>
						<xsl:if test="not(action)">
							<xsl:if test="@onCalculate and @isCalculeOthersFields">
								<xsl:attribute name="onClick">
									<xsl:value-of select="@onCalculate"/>; </xsl:attribute>
							</xsl:if>
						</xsl:if>
						<xsl:if test="action">
							<xsl:if test="@refreshForm='true'">
								<xsl:for-each select="action">
									<xsl:attribute name="onClick">goToRefreshForm('do-PartialRefreshForm', <xsl:choose>
											<xsl:when test="starts-with(../@name, 'component-')">'<xsl:value-of select="../@label"/>'</xsl:when>
											<xsl:otherwise>'<xsl:value-of select="$fullname"/>'</xsl:otherwise>
										</xsl:choose>, '<xsl:value-of select="../action/@obj_provider"/>','<xsl:value-of select="../action/@object_owner_id"/>', getLastModal(), '<xsl:value-of select="../action/@id_action"/>'); var evt = event ? event:window.event; if (evt.stopPropagation) evt.stopPropagation(); if (evt.cancelBubble!=null) evt.cancelBubble = true; evt.preventDefault(); return false; </xsl:attribute>
								</xsl:for-each>
							</xsl:if>
							<xsl:if test="not(@refreshForm='true')">
								<xsl:call-template name="generate_action_on_click">
									<xsl:with-param name="onscript">
										<xsl:if test="@onCalculate and @isCalculeOthersFields">
											<xsl:value-of select="@onCalculate"/>; </xsl:if>
									</xsl:with-param>
								</xsl:call-template>
							</xsl:if>
						</xsl:if>
						<xsl:if test="icon and not(@align='right')">
							<xsl:call-template name="render_icon">
								<xsl:with-param name="style_attr">
									<xsl:if test="icon/@style_image=''">padding-right:10px;</xsl:if>
								</xsl:with-param>
							</xsl:call-template>
						</xsl:if>
						<xsl:if test="@label">
							<xsl:if test="string-length(substring-before(@label, '&lt;')) &lt; string-length(substring-before(@label, '&gt;'))">
								<xsl:value-of disable-output-escaping="yes"
            									select="@label"/>
							</xsl:if>
							<xsl:if test="not(string-length(substring-before(@label, '&lt;'))  &lt; string-length(substring-before(@label, '&gt;')))">
								<xsl:value-of select="@label"/>
							</xsl:if>
						</xsl:if>
						<xsl:if test="icon and @align='right'">
							<xsl:call-template name="render_icon">
								<xsl:with-param name="style_attr">
									<xsl:if test="icon/@style_image=''">padding-left:10px;</xsl:if>
								</xsl:with-param>
							</xsl:call-template>
						</xsl:if>
					</a>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:if test="@is_submit='true'">
				<script charset="ISO-8859-1">setAnchorSubmit('<xsl:value-of select="@name"/>'); </script>
			</xsl:if>
			<xsl:if test="@is_cancel='true'">
				<script charset="ISO-8859-1">setAnchorCancel('<xsl:value-of select="@name"/>'); adaptChanges( ) ; </script>
			</xsl:if>
			<xsl:if test="@functionKey">
				<script charset="ISO-8859-1">setAnchorF(<xsl:value-of select="@functionKey"/>,'<xsl:value-of select="@name"/>'); </script>
			</xsl:if>
		</div>
	</xsl:template>
	<xsl:template match="web_button">
		<a class="web_button">
			<xsl:call-template name="basic_generate_component"/>
			<xsl:if test="@refreshForm='true'">
				<xsl:for-each select="action">
					<xsl:attribute name="onClick">goToRefreshForm('do-PartialRefreshForm', '<xsl:value-of select="@label"/>', '<xsl:value-of select="../action/@obj_provider"/>','<xsl:value-of select="../action/@object_owner_id"/>', getLastModal(), '<xsl:value-of select="../action/@id_action"/>'); </xsl:attribute>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="not(@refreshForm='true')">
				<xsl:call-template name="generate_action_on_click"/>
			</xsl:if>
			<xsl:if test="@label">
				<xsl:value-of select="@label"/>
			</xsl:if>
		</a>
	</xsl:template>
	<xsl:template match="form_lov">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:call-template name="basic_generate_component">
				<xsl:with-param name="isEditComponent"
              					select="'true'"/>
			</xsl:call-template>
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>_div</xsl:attribute>
			<table id="form_lov_toURL"
     				width="100%"
     				height="100%">
				<tr>
					<td>
						<xsl:attribute name="id">
							<xsl:value-of select="$fullname"/>_td_div</xsl:attribute>
						<INPUT type="text">
							<xsl:choose>
								<xsl:when test="@isSearcheable='true' and  @editable='true'">
									<xsl:attribute name="class">text_field-edit</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="class">text_field</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<xsl:if test="@force_focus">
								<xsl:attribute name="autofocus">true</xsl:attribute>
							</xsl:if>
							<xsl:for-each select="onblur">
								<xsl:call-template name="generate_action_onblur"/>
							</xsl:for-each>
							<xsl:if test="@placeholder">
								<xsl:attribute name="placeholder">
									<xsl:value-of select="@placeholder"/>
								</xsl:attribute>
							</xsl:if>
							<xsl:if test="@isSearcheable='true' and @editable!='false'">
								<xsl:attribute name="onkeypress">formLovKeyPress('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>',event); </xsl:attribute>
								<xsl:attribute name="onfocus">onFocusObject(this); formLovFocus('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>'); </xsl:attribute>
								<xsl:attribute name="onblur">formLovLostFocus('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>'); </xsl:attribute>
								<xsl:attribute name="onchange">formLovChange('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>'); </xsl:attribute>
							</xsl:if>
							<xsl:if test="@isSearcheable!='true' or @editable!='true'">
								<xsl:attribute name="readonly">true</xsl:attribute>
								<xsl:attribute name="tabindex">-1</xsl:attribute>
							</xsl:if>
							<xsl:attribute name="id">
								<xsl:value-of select="$fullname"/>_text</xsl:attribute>
							<xsl:attribute name="value">
								<xsl:value-of select="item/@description"/>
							</xsl:attribute>
							<xsl:attribute name="style">
								<xsl:call-template name="generate_inline_style_no_location">
									<xsl:with-param name="style_attr">z-index:10; </xsl:with-param>
								</xsl:call-template>
							</xsl:attribute>
						</INPUT>
						<INPUT type="hidden">
							<xsl:attribute name="value">
								<xsl:value-of select="item/@id"/>
							</xsl:attribute>
							<xsl:call-template name="basic_generate_form_component"/>
						</INPUT>
						<xsl:if test="@isSearcheable='true' and @editable!='false'">
							<a>
								<xsl:attribute name="style">
									<xsl:call-template name="generate_inline_style_no_location">
										<xsl:with-param name="style_attr">z-index:10;</xsl:with-param>
									</xsl:call-template>
								</xsl:attribute>
								<xsl:attribute name="id">
									<xsl:value-of select="$fullname"/>_combo_button</xsl:attribute>
								<xsl:attribute name="onclick">formLovChange('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>');</xsl:attribute>
								<img>
									<xsl:attribute name="src">
										<xsl:value-of select="$skin_path"/>/images/comboButton.gif</xsl:attribute>
								</img>
							</a>
							<a>
								<xsl:attribute name="style">
									<xsl:call-template name="generate_inline_style_no_location">
										<xsl:with-param name="style_attr">z-index:100;</xsl:with-param>
									</xsl:call-template>
								</xsl:attribute>
								<xsl:attribute name="id">
									<xsl:value-of select="$fullname"/>_combo_processing</xsl:attribute>
								<img>
									<xsl:attribute name="src">
										<xsl:value-of select="$skin_path"/>/images/indicator.gif</xsl:attribute>
								</img>
							</a>
						</xsl:if>
					</td>
					<xsl:if test="@editable!='false'">
						<td width="23">
							<a class="form_lov_button_open">
								<xsl:attribute name="id">
									<xsl:value-of select="$fullname"/>_open</xsl:attribute>
								<xsl:attribute name="onclick">formLovOpen('<xsl:value-of select="$fullname"/>', '<xsl:value-of select="item/@objectOwner"/>', '<xsl:value-of select="item/@objectAction"/>', '<xsl:value-of select="item/@obj_provider"/>', <xsl:choose>
										<xsl:when test="item/@refreshFormOnSelect='true'">true</xsl:when>
										<xsl:otherwise>false</xsl:otherwise>
									</xsl:choose>, false,'<xsl:value-of select="item/@multiple"/>', <xsl:value-of select="@width_lov"/>, <xsl:value-of select="@height_lov"/>); </xsl:attribute>
								<xsl:attribute name="oncontextmenu">formLovOpen('<xsl:value-of select="$fullname"/>', '<xsl:value-of select="item/@objectOwner"/>', '<xsl:value-of select="item/@objectAction"/>', '<xsl:value-of select="item/@obj_provider"/>', <xsl:choose>
										<xsl:when test="item/@refreshFormOnSelect='true'">true</xsl:when>
										<xsl:otherwise>false</xsl:otherwise>
									</xsl:choose>, true,'<xsl:value-of select="item/@multiple"/>', <xsl:value-of select="@width_lov"/>, <xsl:value-of select="@height_lov"/>); return false; </xsl:attribute>
								<span/>
							</a>
						</td>
						<td width="23">
							<a class="form_lov_button_clear">
								<xsl:attribute name="id">
									<xsl:value-of select="$fullname"/>_clear</xsl:attribute>
								<xsl:attribute name="onclick">onClickFormLovClear('<xsl:value-of select="$fullname"/>');</xsl:attribute>
								<span/>
							</a>
						</td>
					</xsl:if>
				</tr>
			</table>
		</div>
		<xsl:if test="@isSearcheable='true' and @editable!='false'">
			<div class="formLovOptions">
				<xsl:attribute name="style">z-index:50;position:absolute;<xsl:if test="@x">left:<xsl:value-of select="@x"/>px;</xsl:if>
					<xsl:if test="@y">top:<xsl:value-of select="@y+21"/>px;</xsl:if>
				</xsl:attribute>
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_combo_div</xsl:attribute>
				<xsl:call-template name="do_formlov_combo_box_inner_div">
					<xsl:with-param name="fullname">
						<xsl:value-of select="$fullname"/>
					</xsl:with-param>
					<xsl:with-param name="specialClass">list_box</xsl:with-param>
				</xsl:call-template>
			</div>
		</xsl:if>
		<xsl:if test="@editable!='false'">
			<script>registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']); registerNoConstraint('<xsl:value-of select="$fullname"/>_text',['<xsl:value-of select="$fullname"/>_text']); formLovRegister('<xsl:value-of select="$fullname"/>'); <xsl:if test="@isSearcheable='true' and @editable!='false'">registerDependantFormLov('<xsl:value-of select="$fullname"/>',<xsl:value-of select="item/@refreshFormOnSelect"/>, '<xsl:value-of select="item/@objectOwner"/>','<xsl:value-of select="item/@objectAction"/>','<xsl:value-of select="item/@multiple"/>','<xsl:value-of select="item/@obj_provider"/>'); </xsl:if>
			</script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="win_lov">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:call-template name="basic_generate_component">
				<xsl:with-param name="isEditComponent"
              					select="'true'"/>
			</xsl:call-template>
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>_div</xsl:attribute>
			<table id="form_lov_toURL"
     				width="100%"
     				height="100%">
				<tr>
					<td>
						<xsl:attribute name="id">
							<xsl:value-of select="$fullname"/>_td_div</xsl:attribute>
						<INPUT type="text">
							<xsl:if test="@force_focus">
								<xsl:attribute name="autofocus">true</xsl:attribute>
							</xsl:if>
							<xsl:for-each select="onblur">
								<xsl:call-template name="generate_action_onblur"/>
							</xsl:for-each>
							<xsl:if test="@placeholder">
								<xsl:attribute name="placeholder">
									<xsl:value-of select="@placeholder"/>
								</xsl:attribute>
							</xsl:if>
							<xsl:if test="/page/header/savehelp">
								<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>_text',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
							</xsl:if>
							<xsl:choose>
								<xsl:when test="@isSearcheable='true' and  @editable='true'">
									<xsl:attribute name="class">text_field-edit</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="class">text_field</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<xsl:if test="@isSearcheable='true' and @editable!='false'">
								<xsl:attribute name="onkeypress">formLovKeyPress('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>',event); </xsl:attribute>
								<xsl:attribute name="onfocus">onFocusObject(this); formLovFocus('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>'); </xsl:attribute>
								<xsl:attribute name="onblur">formLovLostFocus('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>'); </xsl:attribute>
								<xsl:attribute name="onchange">formLovChange('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>'); </xsl:attribute>
							</xsl:if>
							<xsl:if test="@isSearcheable!='true' or @editable!='true'">
								<xsl:attribute name="readonly">true</xsl:attribute>
								<xsl:attribute name="tabindex">-1</xsl:attribute>
							</xsl:if>
							<xsl:attribute name="id">
								<xsl:value-of select="$fullname"/>_text</xsl:attribute>
							<xsl:attribute name="value">
								<xsl:value-of select="item/@description"/>
							</xsl:attribute>
							<xsl:attribute name="style">
								<xsl:call-template name="generate_inline_style_no_location">
									<xsl:with-param name="style_attr">z-index:10; </xsl:with-param>
								</xsl:call-template>
							</xsl:attribute>
						</INPUT>
						<INPUT type="hidden">
							<xsl:attribute name="value">
								<xsl:value-of select="item/@id"/>
							</xsl:attribute>
							<xsl:call-template name="basic_generate_form_component"/>
						</INPUT>
						<xsl:if test="@isSearcheable='true' and @editable!='false'">
							<a>
								<xsl:attribute name="style">
									<xsl:call-template name="generate_inline_style_no_location">
										<xsl:with-param name="style_attr">z-index:10;</xsl:with-param>
									</xsl:call-template>
								</xsl:attribute>
								<xsl:attribute name="id">
									<xsl:value-of select="$fullname"/>_combo_button</xsl:attribute>
								<xsl:attribute name="onclick">onFocusObjectById('<xsl:value-of select="$fullname"/>'); formLovChange('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>');</xsl:attribute>
								<img>
									<xsl:attribute name="src">
										<xsl:value-of select="$skin_path"/>/images/comboButton.gif</xsl:attribute>
								</img>
							</a>
							<a>
								<xsl:attribute name="style">
									<xsl:call-template name="generate_inline_style_no_location">
										<xsl:with-param name="style_attr">z-index:100;</xsl:with-param>
									</xsl:call-template>
								</xsl:attribute>
								<xsl:attribute name="id">
									<xsl:value-of select="$fullname"/>_combo_processing</xsl:attribute>
								<img>
									<xsl:attribute name="src">
										<xsl:value-of select="$skin_path"/>/images/indicator.gif</xsl:attribute>
								</img>
							</a>
						</xsl:if>
					</td>
					<xsl:if test="@editable!='false'">
						<td width="23">
							<a class="form_lov_button_open">
								<xsl:attribute name="id">
									<xsl:value-of select="$fullname"/>_open</xsl:attribute>
								<xsl:attribute name="onclick">onFocusObjectById('<xsl:value-of select="$fullname"/>'); winLovOpen(this,'<xsl:value-of select="$fullname"/>', '<xsl:value-of select="item/@objectOwner"/>', '<xsl:value-of select="item/@objectAction"/>', '<xsl:value-of select="item/@obj_provider"/>', <xsl:choose>
										<xsl:when test="item/@refreshFormOnSelect='true'">true</xsl:when>
										<xsl:otherwise>false</xsl:otherwise>
									</xsl:choose>, false,'<xsl:value-of select="item/@multiple"/>',false); </xsl:attribute>
								<span/>
							</a>
						</td>
						<td width="23">
							<a class="form_lov_button_clear">
								<xsl:attribute name="id">
									<xsl:value-of select="$fullname"/>_clear</xsl:attribute>
								<xsl:attribute name="onclick">onClickFormLovClear('<xsl:value-of select="$fullname"/>');</xsl:attribute>
								<span/>
							</a>
						</td>
					</xsl:if>
				</tr>
			</table>
		</div>
		<xsl:if test="@isSearcheable='true' and @editable!='false'">
			<div class="formLovOptions">
				<xsl:attribute name="style">z-index:50;position:absolute;<xsl:if test="@x">left:<xsl:value-of select="@x"/>px;</xsl:if>
					<xsl:if test="@y">top:<xsl:value-of select="@y+21"/>px;</xsl:if>
				</xsl:attribute>
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_combo_div</xsl:attribute>
				<xsl:call-template name="do_formlov_combo_box_inner_div">
					<xsl:with-param name="fullname">
						<xsl:value-of select="$fullname"/>
					</xsl:with-param>
					<xsl:with-param name="specialClass">list_box</xsl:with-param>
				</xsl:call-template>
			</div>
		</xsl:if>registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']); registerNoConstraint('<xsl:value-of select="$fullname"/>_text',['<xsl:value-of select="$fullname"/>_text']); <xsl:if test="@editable!='false'">
			<script>formLovRegister('<xsl:value-of select="$fullname"/>'); <xsl:if test="@isSearcheable='true' and @editable!='false'">registerDependantFormLov('<xsl:value-of select="$fullname"/>',<xsl:value-of select="item/@refreshFormOnSelect"/>, '<xsl:value-of select="item/@objectOwner"/>','<xsl:value-of select="item/@objectAction"/>','<xsl:value-of select="item/@multiple"/>','<xsl:value-of select="item/@obj_provider"/>'); </xsl:if>
			</script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="swaplist_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-swap</xsl:variable>
		<div class="row">
			<xsl:call-template name="basic_generate_component_responsive">
				<xsl:with-param name="isEditComponent"
              					select="'true'"/>
			</xsl:call-template>
			<div class="col-xs-5">
				<input>
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_search_a</xsl:attribute>
					<xsl:attribute name="onkeyup">runUpKey('<xsl:value-of select="@form_name"/>');</xsl:attribute>
				</input>
				<div class="pull-right">
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_label_hidden_a</xsl:attribute>
				</div>
			</div>
			<div class="col-xs-2"/>
			<div class="col-xs-5">
				<input>
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_search_b</xsl:attribute>
					<xsl:attribute name="onkeyup">runUpKey('<xsl:value-of select="@form_name"/>');</xsl:attribute>
				</input>
				<div class="pull-right">
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_label_hidden_b</xsl:attribute>
				</div>
			</div>
			<div class="col-xs-5">
				<select name="from[]"
      					class="form-control"
      					size="13"
      					multiple="multiple">
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_zone</xsl:attribute>
					<xsl:attribute name="style">overflow-x:auto</xsl:attribute>
					<xsl:for-each select="origen">
						<xsl:for-each select="items">
							<option>
								<xsl:attribute name="value">
									<xsl:value-of select="@id"/>
								</xsl:attribute>
								<xsl:for-each select="extradata">
									<xsl:attribute name="data-{@extradata}">
										<xsl:value-of select="@extradata_value"/>
									</xsl:attribute>
								</xsl:for-each>
								<xsl:value-of select="@description"/>
							</option>
						</xsl:for-each>
					</xsl:for-each>
				</select>
			</div>
			<div class="col-xs-2">
				<button type="button"
      					class="btn btn-primary2 btn-block">
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_zone_undo</xsl:attribute>undo</button>
				<button type="button"
      					class="btn btn-default btn-block">
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_zone_rightAll</xsl:attribute>
					<i class="glyphicon glyphicon-forward"/>
				</button>
				<button type="button"
      					class="btn btn-default btn-block">
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_zone_rightSelected</xsl:attribute>
					<i class="glyphicon glyphicon-chevron-right"/>
				</button>
				<button type="button"
      					class="btn btn-default btn-block">
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_zone_leftSelected</xsl:attribute>
					<i class="glyphicon glyphicon-chevron-left"/>
				</button>
				<button type="button"
      					class="btn btn-default btn-block">
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_zone_leftAll</xsl:attribute>
					<i class="glyphicon glyphicon-backward"/>
				</button>
				<button type="button"
      					class="btn btn-warning btn-block">
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_zone_redo</xsl:attribute>redo</button>
			</div>
			<div class="col-xs-5">
				<select name="to[]"
      					class="form-control"
      					size="13"
      					multiple="multiple">
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_zone_to</xsl:attribute>
					<xsl:attribute name="style">overflow-x:auto</xsl:attribute>
					<xsl:for-each select="destino">
						<xsl:for-each select="items">
							<option>
								<xsl:attribute name="value">
									<xsl:value-of select="@id"/>
								</xsl:attribute>
								<xsl:for-each select="extradata">
									<xsl:attribute name="data-{@extradata}">
										<xsl:value-of select="@extradata_value"/>
									</xsl:attribute>
								</xsl:for-each>
								<xsl:value-of select="@description"/>
							</option>
						</xsl:for-each>
					</xsl:for-each>
				</select>
			</div>
			<script type="text/javascript">
				<xsl:variable name="v_provider">
					<xsl:value-of select="@obj_provider"/>
				</xsl:variable>inicializeSwaplist('<xsl:value-of select="$fullname"/>_zone'); AAInit(); <xsl:for-each select="actions/action">
					<xsl:if test="allowed_win">AAAdd('<xsl:value-of select="@id"/>',<xsl:value-of select="@win"/>,[<xsl:for-each select="allowed_win">'<xsl:value-of select="@id"/>',</xsl:for-each>null],<xsl:value-of select="@multiple"/>); </xsl:if>
					<xsl:if test="not_allowed_win">AAANodd('<xsl:value-of select="@id"/>',<xsl:value-of select="@win"/>,[<xsl:for-each select="not_allowed_win">'<xsl:value-of select="@id"/>',</xsl:for-each>null],<xsl:value-of select="@multiple"/>); </xsl:if>
				</xsl:for-each>AAFin('<xsl:value-of select="$fullname"/>_zone_to','<xsl:value-of select="//win_action_bar[@provider=$v_provider]/@name"/>','<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="@zobject"/>',true,true,'0',false,'<xsl:value-of select="@zobjectdest"/>'); swapStartFilters('<xsl:value-of select="@form_name"/>','<xsl:value-of select="$fullname"/>'); registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>_zone_to']); </script>
		</div>
	</xsl:template>
	<xsl:template match="options_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-options</xsl:variable>
		<xsl:for-each select="option">
			<input type="button">
				<xsl:attribute name="class">btn<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
					<xsl:value-of select="@class_responsive"/>
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
					<xsl:value-of select="@size_button"/>
				</xsl:attribute>
				<xsl:attribute name="onclick">
					<xsl:for-each select="action">
						<xsl:call-template name="generate_action_invokation"/>
					</xsl:for-each>
				</xsl:attribute>
				<xsl:attribute name="value">
					<xsl:value-of select="@description"/>
				</xsl:attribute>
			</input>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="win_lov_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
						</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:if test="@label_lateral">
				<xsl:if test="@size_responsive!='inline_component'">
					<label>
						<xsl:attribute name="for">
							<xsl:value-of select="$fullname"/>
						</xsl:attribute>
						<xsl:if test="@label_class">
							<xsl:attribute name="class">
								<xsl:value-of select="@label_class"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:value-of select="@label_lateral"/>
					</label>
				</xsl:if>
			</xsl:if>
			<div>
				<xsl:attribute name="class">
					<xsl:if test="@field_class">
						<xsl:value-of select="@field_class"/>
					</xsl:if>
				</xsl:attribute>
				<div>
					<xsl:attribute name="class">input-group </xsl:attribute>
					<xsl:if test="@maxwidth or @maxheight">
						<xsl:attribute name="style">
							<xsl:if test="@maxwidth">max-width:<xsl:value-of select="@maxwidth"/>px;</xsl:if>
							<xsl:if test="@maxheight">max-height:<xsl:value-of select="@maxheight"/>px;</xsl:if>
						</xsl:attribute>
					</xsl:if>
					<select>
						<xsl:if test="@multiple='true'">
							<xsl:attribute name="multiple"/>
							<xsl:attribute name="class">form-control select2-multiple <xsl:if test="@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
						</xsl:if>
						<xsl:if test="@multiple!='true'">
							<xsl:attribute name="class">form-control select2-single <xsl:if test="@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
						</xsl:if>
						<xsl:attribute name="aria-hidden">true</xsl:attribute>
						<xsl:attribute name="autocomplete">off</xsl:attribute>
						<xsl:if test="/page/header/savehelp">
							<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
						</xsl:if>
						<xsl:if test="constraints/@required and (constraints/@required='true')">
							<xsl:attribute name="required"/>
						</xsl:if>
						<xsl:if test="@connect_control">
							<xsl:attribute name="onChange">
								<xsl:if test="@onCalculate and @isCalculeOthersFields">
									<xsl:value-of select="@onCalculate"/>; </xsl:if>runUpKey('<xsl:value-of select="@connect_control"/>'); return false; </xsl:attribute>
						</xsl:if>
						<xsl:if test="not(@connect_control)">
							<xsl:if test="@refreshForm='true'">
								<xsl:for-each select="action">
									<xsl:attribute name="onChange">onFocusObjectById('<xsl:value-of select="$fullname"/>'); goToRefreshForm('do-PartialRefreshForm', '<xsl:value-of select="../@name"/>', '<xsl:value-of select="../action/@obj_provider"/>','<xsl:value-of select="../action/@object_owner_id"/>', getLastModal(), '<xsl:value-of select="../action/@id_action"/>'); return false; </xsl:attribute>
								</xsl:for-each>
							</xsl:if>
						</xsl:if>
						<xsl:attribute name="id">
							<xsl:value-of select="$fullname"/>
						</xsl:attribute>
						<xsl:if test="@editable!='true'">
							<xsl:attribute name="disabled"/>
							<xsl:attribute name="tabindex">-1</xsl:attribute>
							<xsl:attribute name="readonly"/>
						</xsl:if>
						<xsl:for-each select="item">
							<option>
								<xsl:attribute name="selected"/>
								<xsl:attribute name="value">
									<xsl:value-of select="@id"/>
								</xsl:attribute>
								<xsl:attribute name="data-real_id">
									<xsl:value-of select="@real_id"/>
								</xsl:attribute>
								<xsl:value-of select="@description"/>
							</option>
						</xsl:for-each>
					</select>
					<xsl:if test="@label_lateral">
						<xsl:if test="@size_responsive='inline_component'">
							<label>
								<xsl:attribute name="for">
									<xsl:value-of select="$fullname"/>
								</xsl:attribute>
								<xsl:if test="@label_class">
									<xsl:attribute name="class">
										<xsl:value-of select="@label_class"/>
									</xsl:attribute>
								</xsl:if>
								<xsl:value-of select="@label_lateral"/>
							</label>
						</xsl:if>
					</xsl:if>
					<xsl:if test="@editable='true'">
						<xsl:if test="@show_lupa='true'">
							<span>
								<xsl:attribute name="class">input-group-btn</xsl:attribute>
								<button>
									<xsl:attribute name="class">btn btn-default</xsl:attribute>
									<xsl:attribute name="type">button</xsl:attribute>
									<xsl:attribute name="onclick">onFocusObjectById('<xsl:value-of select="$fullname"/>'); winLovOpen(this,'<xsl:value-of select="$fullname"/>', '<xsl:value-of select="@objectOwner"/>', '<xsl:value-of select="@objectAction"/>', '<xsl:value-of select="@obj_provider"/>', <xsl:choose>
											<xsl:when test="@refreshFormOnSelect='true'">true</xsl:when>
											<xsl:otherwise>false</xsl:otherwise>
										</xsl:choose>, <xsl:value-of select="@multiple"/>,true); </xsl:attribute>
									<span>
										<xsl:attribute name="class">fa fa-search</xsl:attribute>
									</span>
								</button>
							</span>
						</xsl:if>
						<xsl:if test="@show_lupa='true'">
							<span>
								<xsl:attribute name="class">input-group-btn</xsl:attribute>
								<button>
									<xsl:attribute name="class">btn btn-default</xsl:attribute>
									<xsl:attribute name="type">button</xsl:attribute>
									<xsl:attribute name="onclick">clearSelect2('<xsl:value-of select="$fullname"/>', <xsl:choose>
											<xsl:when test="@refreshFormOnSelect='true'">true</xsl:when>
											<xsl:otherwise>false</xsl:otherwise>
										</xsl:choose>); </xsl:attribute>
									<span>
										<xsl:attribute name="class">glyphicon glyphicon-remove</xsl:attribute>
									</span>
								</button>
							</span>
							<xsl:for-each select="extrabutton">
								<xsl:for-each select="web_button_responsive">
									<span>
										<xsl:attribute name="class">input-group-btn</xsl:attribute>
										<button>
											<xsl:attribute name="type">button</xsl:attribute>
											<xsl:call-template name="basic_generate_component_responsive">
												<xsl:with-param name="specialClass">btn <xsl:value-of select="@class_responsive"/>
												</xsl:with-param>
												<xsl:with-param name="style_attr">
													<xsl:if test="not(icon)">padding-bottom: 2px;padding-top: 2px;</xsl:if>
												</xsl:with-param>
											</xsl:call-template>
											<xsl:if test="@tooltip">
												<xsl:attribute name="title">
													<xsl:value-of select="@tooltip"/>
												</xsl:attribute>
											</xsl:if>
											<xsl:if test="@onCalculate and @isCalculeOthersFields">
												<xsl:attribute name="onClick">
													<xsl:value-of select="@onCalculate"/>; </xsl:attribute>
											</xsl:if>
											<xsl:if test="not(@onCalculate and @isCalculeOthersFields)">
												<xsl:if test="@refreshForm='true'">
													<xsl:for-each select="action">
														<xsl:attribute name="onClick">onFocusObjectById('<xsl:value-of select="$fullname"/>'); goToRefreshForm('do-PartialRefreshForm', '<xsl:value-of select="@label"/>', '<xsl:value-of select="../action/@obj_provider"/>','<xsl:value-of select="../action/@object_owner_id"/>', getLastModal(), '<xsl:value-of select="../action/@id_action"/>'); </xsl:attribute>
													</xsl:for-each>
												</xsl:if>
												<xsl:if test="not(@refreshForm='true')">
													<xsl:call-template name="generate_action_on_click"/>
												</xsl:if>1 </xsl:if>
											<xsl:if test="icon">
												<xsl:call-template name="render_icon"/>
											</xsl:if>
											<xsl:if test="@label">
												<xsl:attribute name="value">
													<xsl:value-of select="@label"/>
												</xsl:attribute>
											</xsl:if>
										</button>
									</span>
								</xsl:for-each>
							</xsl:for-each>
						</xsl:if>
					</xsl:if>
				</div>
			</div>
			<script type="text/javascript"
      				charset="ISO-8859-1">inicializeSelect2('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@placeholder"/>',<xsl:value-of select="@MaximumSelectionLength"/>,<xsl:value-of select="@MinimumInputLength"/>,<xsl:value-of select="@show_key"/>,<xsl:value-of select="@multiple"/>,<xsl:value-of select="show_lupa"/>); </script>
			<script charset="ISO-8859-1">
				<xsl:if test="@editable!='false'">
					<xsl:if test="@modifiedonserver='true'">setChangeInputs(true); </xsl:if>
				</xsl:if>
				<xsl:if test="@intable='true'">rdg('<xsl:value-of select="@table_name"/>','<xsl:value-of select="@name"/>',['<xsl:value-of select="$fullname"/>'],<xsl:value-of select="@table_row"/>,<xsl:value-of select="@table_column"/>); </xsl:if>
				<xsl:if test="not(@intable='true')">registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']<xsl:if test="constraints/@required='true'">,'<xsl:value-of select="constraints/@description"/>',true</xsl:if>); </xsl:if>
				<xsl:if test="@isSearcheable='true' and @editable!='false'">registerResponsiveFormLov('<xsl:value-of select="$fullname"/>',<xsl:value-of select="@show_key"/>,<xsl:value-of select="@refreshFormOnSelect"/>, '<xsl:value-of select="@objectOwner"/>','<xsl:value-of select="@objectAction"/>','<xsl:value-of select="@multiple"/>','<xsl:value-of select="@obj_provider"/>',<xsl:if test="@detect_changes='true'">true</xsl:if>
					<xsl:if test="not(@detect_changes='true')">false</xsl:if>); </xsl:if>
			</script>
			<script>
				<xsl:if test="@connect_control">subscribeControlConnect( '<xsl:value-of select="@connect_control"/>', function() { hideSelectMetaItemsLov('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); hideSelectMetaItemsLov('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone_to','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); }); </xsl:if>
			</script>
		</div>
	</xsl:template>
	<xsl:template name="do_combo_box_dependant">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:call-template name="basic_generate_component">
				<xsl:with-param name="isEditComponent"
              					select="'true'"/>
			</xsl:call-template>
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>_div</xsl:attribute>
			<INPUT type="text">
				<xsl:if test="@force_focus">
					<xsl:attribute name="autofocus">true</xsl:attribute>
				</xsl:if>
				<xsl:for-each select="onblur">
					<xsl:call-template name="generate_action_onblur"/>
				</xsl:for-each>
				<xsl:if test="@placeholder">
					<xsl:attribute name="placeholder">
						<xsl:value-of select="@placeholder"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:attribute name="onclick">dependantComboClicked('<xsl:value-of select="$fullname"/>');</xsl:attribute>
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_text</xsl:attribute>
				<xsl:attribute name="style">
					<xsl:call-template name="generate_inline_style_no_location">
						<xsl:with-param name="style_attr">z-index:10;</xsl:with-param>
					</xsl:call-template>
				</xsl:attribute>
			</INPUT>
			<a>
				<xsl:attribute name="style">
					<xsl:call-template name="generate_inline_style_no_location">
						<xsl:with-param name="style_attr">z-index:10;</xsl:with-param>
					</xsl:call-template>
				</xsl:attribute>
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_button</xsl:attribute>
				<xsl:attribute name="onclick">dependantComboClicked('<xsl:value-of select="$fullname"/>');</xsl:attribute>
				<img>
					<xsl:attribute name="src">
						<xsl:value-of select="$skin_path"/>/images/comboButton.gif</xsl:attribute>
				</img>
			</a>
			<a>
				<xsl:attribute name="style">
					<xsl:call-template name="generate_inline_style_no_location">
						<xsl:with-param name="style_attr">z-index:10;</xsl:with-param>
					</xsl:call-template>
				</xsl:attribute>
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_processing</xsl:attribute>
				<img>
					<xsl:attribute name="src">
						<xsl:value-of select="$skin_path"/>/images/indicator.gif</xsl:attribute>
				</img>
			</a>
		</div>
		<xsl:if test="@visible and (@visible='true')">
			<script charset="ISO-8859-1">registerDependantCombo('<xsl:value-of select="$fullname"/>'); </script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="multiple_list">
		<xsl:param name="specialClass"
         			select="'null'"/>
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<INPUT type="hidden">
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
			<xsl:attribute name="name">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
		</INPUT>
		<div>
			<xsl:if test="/page/header/savehelp">
				<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
				</xsl:variable>
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
			</xsl:if>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
						</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<script charset="ISO-8859-1">registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']<xsl:if test="constraints/@required='true'">,true</xsl:if>); </script>
			<ul>
				<xsl:attribute name="class">form-control input-sm <xsl:if test="@field_class">
						<xsl:value-of select="@field_class"/>
					</xsl:if>
					<xsl:if test="@enteristab"><![CDATA[ ]]>enteristab</xsl:if><![CDATA[ ]]>focuseable </xsl:attribute>
				<xsl:attribute name="style">
					<xsl:call-template name="generate_inline_style"/>;overflow:auto; </xsl:attribute>
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_div_main</xsl:attribute>
				<xsl:attribute name="name">
					<xsl:value-of select="$fullname"/>_div_main</xsl:attribute>
				<xsl:call-template name="do_multiple_list_inner_div">
					<xsl:with-param name="specialClass">multiple_list</xsl:with-param>
					<xsl:with-param name="fullname">
						<xsl:value-of select="$fullname"/>
					</xsl:with-param>
				</xsl:call-template>
			</ul>
		</div>
	</xsl:template>
	<xsl:template match="multiple_check_responsive">
		<xsl:param name="specialClass"
         			select="'null'"/>
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:if test="/page/header/savehelp">
				<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
				</xsl:variable>
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
			</xsl:if>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
						</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:if test="@label_lateral">
				<label>
					<xsl:attribute name="for">
						<xsl:value-of select="$fullname"/>
						<xsl:if test="@mode!='toggle'">_check</xsl:if>
					</xsl:attribute>
					<xsl:if test="@label_class">
						<xsl:attribute name="class">
							<xsl:value-of select="@label_class"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="@label_lateral"/>
					<xsl:call-template name="basic_generate_component_responsive"/>
				</label>
			</xsl:if>
			<INPUT type="hidden">
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:attribute name="name">
					<xsl:value-of select="$fullname"/>
				</xsl:attribute>
			</INPUT>
			<div>
				<xsl:attribute name="style">
					<xsl:call-template name="generate_inline_style_responsive"/>;overflow:auto; </xsl:attribute>
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_div_main</xsl:attribute>
				<xsl:attribute name="name">
					<xsl:value-of select="$fullname"/>_div_main</xsl:attribute>
				<xsl:call-template name="do_multiple_check_options">
					<xsl:with-param name="fullname">
						<xsl:value-of select="$fullname"/>
					</xsl:with-param>
				</xsl:call-template>
			</div>
			<script charset="ISO-8859-1">registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']<xsl:if test="constraints/@required='true'">,'<xsl:value-of select="constraints/@description"/>',true</xsl:if>); </script>
		</div>
	</xsl:template>
	<xsl:template name="do_multiple_check_inner_div">
		<xsl:param name="specialClass"
         			select="'null'"/>
		<xsl:param name="fullname"
         			select="'null'"/>
		<xsl:call-template name="do_multiple_check_options">
			<xsl:with-param name="fullname">
				<xsl:value-of select="$fullname"/>
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="do_multiple_check_options">
		<xsl:param name="fullname"
         			select="'null'"/>
		<xsl:for-each select="item">
			<xsl:if test="../@editable='false'">
				<xsl:if test="../@onlyshowselected='false' or @selected">
					<input type="checkbox"
     						disabled="disabled">
						<xsl:attribute name="id">
							<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id_name"/>_check</xsl:attribute>
						<xsl:attribute name="name">
							<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id_name"/>_check</xsl:attribute>
						<xsl:attribute name="data-toggle">toogle</xsl:attribute>
						<xsl:attribute name="data-size">mini</xsl:attribute>
						<xsl:attribute name="data-style">ios</xsl:attribute>
						<xsl:attribute name="data-onstyle">success</xsl:attribute>
						<xsl:attribute name="data-on">
							<xsl:value-of select="@description"/>
						</xsl:attribute>
						<xsl:attribute name="data-off">
							<xsl:value-of select="@description"/>
						</xsl:attribute>
						<xsl:attribute name="checked"/>
						<xsl:attribute name="value">
							<xsl:value-of select="@id"/>
						</xsl:attribute>
					</input>
					<script type="text/javascript">inicializeCheckBox('<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id_name"/>_check'); <xsl:if test="@selected">fillMarked('<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id_name"/>_check','<xsl:value-of select="$fullname"/>','<xsl:value-of select="@id"/>'); </xsl:if>
					</script>
				</xsl:if>
			</xsl:if>
			<xsl:if test="../@editable!='false'">
				<label>
					<xsl:attribute name="for">
						<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id_name"/>_check</xsl:attribute>
					<xsl:if test="@label_class">
						<xsl:attribute name="class">
							<xsl:value-of select="@label_class"/>
						</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="@label_lateral"/>
					<INPUT type="checkbox">
						<xsl:attribute name="id">
							<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id_name"/>_check</xsl:attribute>
						<xsl:attribute name="data-toggle">toogle</xsl:attribute>
						<xsl:attribute name="data-size">mini</xsl:attribute>
						<xsl:attribute name="data-style">ios</xsl:attribute>
						<xsl:attribute name="data-onstyle">success</xsl:attribute>
						<xsl:attribute name="data-on">
							<xsl:value-of select="@description"/>
						</xsl:attribute>
						<xsl:attribute name="data-off">
							<xsl:value-of select="@description"/>
						</xsl:attribute>
						<xsl:if test="@checked='S'">
							<xsl:attribute name="checked"/>
						</xsl:if>
						<xsl:attribute name="onkeydown">if(event.keyCode==32) this.click(); </xsl:attribute>
						<xsl:if test="@enteristab">
							<xsl:attribute name="class">enteristab</xsl:attribute>
						</xsl:if>
						<xsl:if test="not(@enteristab)">
							<xsl:attribute name="class">focuseable</xsl:attribute>
						</xsl:if>
						<xsl:attribute name="value">
							<xsl:value-of select="@id"/>
						</xsl:attribute>
						<xsl:attribute name="onclick">javascript: <xsl:if test="@detect_changes='true'">setChangeInputs(true);</xsl:if>
							<xsl:if test="@onCalculate and @isCalculeOthersFields">
								<xsl:value-of select="@onCalculate"/>; </xsl:if>executeOnChange('<xsl:value-of select="$fullname"/>'); fillMark('<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id_name"/>_check','<xsl:value-of select="$fullname"/>','<xsl:value-of select="@id"/>'); <xsl:if test="../@refreshForm='true'">goToRefreshForm('do-PartialRefreshForm', '<xsl:value-of select="$fullname"/>', '<xsl:value-of select="../action/@obj_provider"/>','<xsl:value-of select="../action/@object_owner_id"/>', getLastModal(), '<xsl:value-of select="../action/@id_action"/>'); </xsl:if>
							<xsl:if test="@connect_control">runUpKey('<xsl:value-of select="@connect_control"/>'); </xsl:if>return false; </xsl:attribute>
					</INPUT>
					<script type="text/javascript">inicializeCheckBox('<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id_name"/>_check'); <xsl:if test="@selected">fillMarked('<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id_name"/>_check','<xsl:value-of select="$fullname"/>','<xsl:value-of select="@id"/>'); </xsl:if>
					</script>
				</label>
				<xsl:if test="@connect_control">
					<script>subscribeControlConnect( '<xsl:value-of select="@connect_control"/>', function() { hideSelectMetaItemsCheck('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); hideSelectMetaItemsCheck('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone_to','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); }); </script>
				</xsl:if>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="multiple_list_responsive">
		<xsl:param name="specialClass"
         			select="'null'"/>
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:if test="/page/header/savehelp">
				<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
				</xsl:variable>
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
			</xsl:if>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
						</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<label>
				<xsl:attribute name="for">
					<xsl:value-of select="$fullname"/>
					<xsl:if test="@mode!='toggle'">_check</xsl:if>
				</xsl:attribute>
				<xsl:if test="@label_class">
					<xsl:attribute name="class">
						<xsl:value-of select="@label_class"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:value-of select="@label_lateral"/>
				<xsl:call-template name="basic_generate_component_responsive"/>
			</label>
			<INPUT type="hidden">
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:attribute name="name">
					<xsl:value-of select="$fullname"/>
				</xsl:attribute>
			</INPUT>
			<ul>
				<xsl:attribute name="class">gallery<![CDATA[ ]]><xsl:value-of select="@class_responsive"/>
				</xsl:attribute>
				<xsl:attribute name="style">
					<xsl:call-template name="generate_inline_style_responsive"/>;overflow:auto; </xsl:attribute>
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_div_main</xsl:attribute>
				<xsl:attribute name="name">
					<xsl:value-of select="$fullname"/>_div_main</xsl:attribute>
				<xsl:call-template name="do_multiple_list_options">
					<xsl:with-param name="fullname">
						<xsl:value-of select="$fullname"/>
					</xsl:with-param>
				</xsl:call-template>
			</ul>
			<script charset="ISO-8859-1">registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']<xsl:if test="constraints/@required='true'">,'<xsl:value-of select="constraints/@description"/>',true</xsl:if>); </script>
			<xsl:if test="@connect_control">
				<script>subscribeControlConnect( '<xsl:value-of select="@connect_control"/>', function() { hideSelectMetaItemsRadio('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); hideSelectMetaItemsRadio('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone_to','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); }); </script>
			</xsl:if>
		</div>
	</xsl:template>
	<xsl:template name="do_multiple_list_inner_div">
		<xsl:param name="specialClass"
         			select="'null'"/>
		<xsl:param name="fullname"
         			select="'null'"/>
		<xsl:call-template name="do_multiple_list_options">
			<xsl:with-param name="fullname">
				<xsl:value-of select="$fullname"/>
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="do_multiple_list_options">
		<xsl:param name="fullname"
         			select="'null'"/>
		<xsl:for-each select="item">
			<xsl:if test="../@editable='false'">
				<xsl:if test="../@onlyshowselected='false' or @selected">
					<li>
						<xsl:attribute name="id">
							<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id"/>
						</xsl:attribute>
						<xsl:attribute name="name">
							<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id"/>
						</xsl:attribute>
						<xsl:attribute name="onclick">javascript: return false; </xsl:attribute>
						<xsl:attribute name="class">multiple_list_option_readonly <xsl:if test="@selected and @selected='true'">selected </xsl:if>
						</xsl:attribute>
						<div>
							<div>
								<xsl:if test="string-length(substring-before(@description, '&lt;')) &lt; string-length(substring-before(@description, '&gt;'))">
									<xsl:value-of disable-output-escaping="yes"
            										select="@description"/>
								</xsl:if>
								<xsl:if test="not(string-length(substring-before(@description, '&lt;'))  &lt; string-length(substring-before(@description, '&gt;')))">
									<xsl:value-of select="@description"/>
								</xsl:if>
							</div>
							<xsl:if test="icon">
								<xsl:call-template name="render_icon"/>
							</xsl:if>
						</div>
					</li>
				</xsl:if>
			</xsl:if>
			<xsl:if test="../@editable!='false'">
				<xsl:if test="@selected">
					<script charset="ISO-8859-1">document.getElementById('<xsl:value-of select="$fullname"/>').value='<xsl:value-of select="@id"/>'; document.getElementById('<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id"/>').className ='multiple_list_option_select'; </script>
				</xsl:if>
				<li>
					<xsl:attribute name="class">multiple_list_option</xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id"/>
					</xsl:attribute>
					<xsl:attribute name="name">
						<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id"/>
					</xsl:attribute>
					<xsl:attribute name="onclick">javascript: set_class('<xsl:value-of select="$fullname"/>_div_main','li','multiple_list_option'); document.getElementById('<xsl:value-of select="$fullname"/>').value='<xsl:value-of select="@id"/>'; document.getElementById('<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id"/>').className ='multiple_list_option_select'; <xsl:if test="@detect_changes='true'">setChangeInputs(true);</xsl:if>
						<xsl:if test="@onCalculate and @isCalculeOthersFields">
							<xsl:value-of select="@onCalculate"/>; </xsl:if>executeOnChange('<xsl:value-of select="$fullname"/>'); <xsl:if test="../@refreshForm='true'">goToRefreshForm('do-PartialRefreshForm', '<xsl:value-of select="$fullname"/>', '<xsl:value-of select="../action/@obj_provider"/>','<xsl:value-of select="../action/@object_owner_id"/>', getLastModal(), '<xsl:value-of select="../action/@id_action"/>'); </xsl:if>return false; </xsl:attribute>
					<div>
						<div>
							<xsl:if test="string-length(substring-before(@description, '&lt;')) &lt; string-length(substring-before(@description, '&gt;'))">
								<xsl:value-of disable-output-escaping="yes"
            									select="@description"/>
							</xsl:if>
							<xsl:if test="not(string-length(substring-before(@description, '&lt;'))  &lt; string-length(substring-before(@description, '&gt;')))">
								<xsl:value-of select="@description"/>
							</xsl:if>
						</div>
						<xsl:if test="icon">
							<xsl:call-template name="render_icon"/>
						</xsl:if>
					</div>
				</li>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="treecomponent_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<div>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
						</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<label>
				<xsl:attribute name="for">
					<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:if test="@label_class">
					<xsl:attribute name="class">
						<xsl:value-of select="@label_class"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:value-of select="@label_lateral"/>
			</label>
			<xsl:call-template name="tree_view_responsive"/>
		</div>
	</xsl:template>
	<xsl:template name="tree_view_responsive">
		<xsl:param name="specialClass"
         			select="'null'"/>
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="varname">dgf_<xsl:value-of select="@form_name"/>_fd_<xsl:value-of select="@name"/>
		</xsl:variable>
		<INPUT type="hidden">
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
			<xsl:attribute name="name">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
		</INPUT>
		<script charset="ISO-8859-1">registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']); </script>
		<DIV>
			<xsl:attribute name="style">
				<xsl:call-template name="generate_inline_style_responsive"/>;overflow:auto; </xsl:attribute>
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>_div_main</xsl:attribute>
			<xsl:attribute name="name">
				<xsl:value-of select="$fullname"/>_div_main</xsl:attribute>
			<xsl:call-template name="do_treeview_div">
				<xsl:with-param name="specialClass">dtree</xsl:with-param>
				<xsl:with-param name="fullname">
					<xsl:value-of select="$fullname"/>
				</xsl:with-param>
				<xsl:with-param name="varname">
					<xsl:value-of select="$varname"/>
				</xsl:with-param>
			</xsl:call-template>
		</DIV>
	</xsl:template>
	<xsl:template match="tree">
		<xsl:call-template name="tree_view"/>
	</xsl:template>
	<xsl:template name="tree_view">
		<xsl:param name="specialClass"
         			select="'null'"/>
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="varname">dgf_<xsl:value-of select="@form_name"/>_fd_<xsl:value-of select="@name"/>
		</xsl:variable>
		<INPUT type="hidden">
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
			<xsl:attribute name="name">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
		</INPUT>
		<script charset="ISO-8859-1">registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']); </script>
		<DIV>
			<xsl:attribute name="style">
				<xsl:call-template name="generate_inline_style"/>;overflow:auto; </xsl:attribute>
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>_div_main</xsl:attribute>
			<xsl:attribute name="name">
				<xsl:value-of select="$fullname"/>_div_main</xsl:attribute>
			<xsl:call-template name="do_treeview_div">
				<xsl:with-param name="specialClass">dtree</xsl:with-param>
				<xsl:with-param name="fullname">
					<xsl:value-of select="$fullname"/>
				</xsl:with-param>
				<xsl:with-param name="varname">
					<xsl:value-of select="$varname"/>
				</xsl:with-param>
			</xsl:call-template>
		</DIV>
	</xsl:template>
	<xsl:template name="do_treeview_div">
		<xsl:param name="specialClass"
         			select="'null'"/>
		<xsl:param name="fullname"
         			select="'null'"/>
		<xsl:param name="varname"
         			select="'null'"/>
		<div>
			<xsl:attribute name="id">
				<xsl:value-of select="$varname"/>_tree</xsl:attribute>
			<xsl:attribute name="autofocus">true</xsl:attribute>
			<xsl:attribute name="class">
				<xsl:value-of select="$specialClass"/>
			</xsl:attribute>
		</div>
		<script type="text/javascript"
      			charset="ISO-8859-1">var <xsl:value-of select="$varname"/>_d = new dTree('<xsl:value-of select="$varname"/>_d'); var k = <xsl:value-of select="$varname"/>_d; var p = { fullname: '<xsl:value-of select="$fullname"/>', provider:'<xsl:value-of select="action/@obj_provider"/>', owner:'<xsl:value-of select="action/@object_owner_id"/>', action:'<xsl:value-of select="action/@id_action"/>'}; var i=saveMemoryArray(p); var s='uf('+i+','; var g='gr('+i+')'; var m= [ <xsl:for-each select="item">{ a:'<xsl:value-of select="@id"/>', i:'<xsl:value-of select="@id_tree"/>', p:'<xsl:value-of select="@parent"/>', d:'<xsl:value-of select="@description"/>', f:'<xsl:value-of select="$fullname"/>', <xsl:if test="@icon=@icon_open">c:'<xsl:value-of select="@icon"/>', o:null </xsl:if>
				<xsl:if test="@icon!=@icon_open">c:'<xsl:value-of select="@icon"/>', o:'<xsl:value-of select="@icon_open"/>' </xsl:if>,x:<xsl:if test="not(../@editable='true' and @elegible='true')">null </xsl:if>
				<xsl:if test="../@editable='true' and @elegible='true'">s+'\'<xsl:value-of select="@id"/>\'); <xsl:if test="../@refreshForm='true'">'+g </xsl:if>
					<xsl:if test="../@refreshForm!='true'">' </xsl:if>
				</xsl:if>}<xsl:if test="position()!=last()">,</xsl:if>
			</xsl:for-each>]; bt(k,m,'<xsl:value-of select="$varname"/>_tree','<xsl:value-of select="@value"/>','<xsl:value-of select="@title"/>','<xsl:value-of select="@icon"/>'); </script>
	</xsl:template>
	<xsl:template match="combo_box">
		<xsl:call-template name="do_combo_box"/>
	</xsl:template>
	<xsl:template match="combo_box_dependant">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:if test="@editable!='false'">
			<xsl:call-template name="do_combo_box_dependant"/>
		</xsl:if>
		<xsl:call-template name="do_combo_box">
			<xsl:with-param name="specialClass">combo_box</xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="do_combo_box">
		<xsl:param name="specialClass"
         			select="'null'"/>
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<DIV>
			<xsl:attribute name="style">
				<xsl:call-template name="generate_inline_style"/>
			</xsl:attribute>
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>_div_main</xsl:attribute>
			<xsl:attribute name="name">
				<xsl:value-of select="$fullname"/>_div_main</xsl:attribute>
			<xsl:call-template name="do_combo_box_inner_div">
				<xsl:with-param name="specialClass">combo_box</xsl:with-param>
			</xsl:call-template>
		</DIV>
	</xsl:template>
	<xsl:template name="do_combo_box_inner_div">
		<xsl:param name="specialClass"
         			select="'null'"/>
		<SELECT class="combo_box">
			<xsl:attribute name="size">
				<xsl:value-of select="@size_rows"/>
			</xsl:attribute>
			<xsl:if test="@force_focus">
				<xsl:attribute name="autofocus">true</xsl:attribute>
			</xsl:if>
			<xsl:for-each select="onblur">
				<xsl:call-template name="generate_action_onblur"/>
			</xsl:for-each>
			<xsl:variable name="onchange_event_var">
				<xsl:value-of select="@onchange_event"/>
			</xsl:variable>
			<xsl:variable name="onclick_event_var">
				<xsl:value-of select="@onclick_event"/>
			</xsl:variable>
			<xsl:attribute name="style">
				<xsl:call-template name="generate_inline_style_no_location"/>
			</xsl:attribute>
			<xsl:call-template name="basic_generate_form_component">
				<xsl:with-param name="onchange_event_var">
					<xsl:value-of select="$onchange_event_var"/>
				</xsl:with-param>
				<xsl:with-param name="onclick_event_var">
					<xsl:value-of select="$onclick_event_var"/>
				</xsl:with-param>
				<xsl:with-param name="specialClass">
					<xsl:value-of select="$specialClass"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="do_combo_box_options"/>
		</SELECT>
	</xsl:template>
	<xsl:template name="do_combo_box_options">
		<xsl:for-each select="item">
			<OPTION>
				<xsl:attribute name="value">
					<xsl:value-of select="@id"/>
				</xsl:attribute>
				<xsl:attribute name="data-real_id">
					<xsl:value-of select="@real_id"/>
				</xsl:attribute>
				<xsl:if test="@selected='true'">
					<xsl:attribute name="selected"/>
				</xsl:if>
				<xsl:if test="@separator='true'">
					<xsl:attribute name="disable"/>
					<xsl:attribute name="selecthr"/>
				</xsl:if>
				<xsl:if test="(../@show_key='true') and not(@real_id='')">
					<xsl:value-of select="@real_id"/>- </xsl:if>
				<xsl:value-of select="@description"/>
			</OPTION>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="file_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<INPUT type="hidden">
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
			<xsl:attribute name="name">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
		</INPUT>
		<script charset="ISO-8859-1">registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']<xsl:if test="constraints/@required='true'">,'<xsl:value-of select="constraints/@description"/>',true</xsl:if>); </script>
		<div>
			<xsl:if test="/page/header/savehelp">
				<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
				</xsl:variable>
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
			</xsl:if>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
						</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:if test="@editable!='false'">
				<iframe src="html/upload.html"
      					frameborder="0"
      					seamless="seamless">
					<xsl:attribute name="id">iframe_<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<xsl:attribute name="name">iframe_<xsl:value-of select="$fullname"/>
					</xsl:attribute>
					<xsl:attribute name="style">
						<xsl:call-template name="generate_inline_style_responsive"/>
					</xsl:attribute>
				</iframe>
			</xsl:if>
		</div>
	</xsl:template>
	<xsl:template match="buttonpay_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<INPUT type="hidden">
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
			<xsl:attribute name="name">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
		</INPUT>
		<script charset="ISO-8859-1">registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']<xsl:if test="constraints/@required='true'">,'<xsl:value-of select="constraints/@description"/>',true</xsl:if>); </script>
		<div>
			<xsl:if test="/page/header/savehelp">
				<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
				</xsl:variable>
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
			</xsl:if>
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="@visible and (@visible='true')">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
						</xsl:if><![CDATA[ ]]>form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
					</xsl:when>
					<xsl:otherwise>form-group hidden</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@only-expanded">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
				<xsl:if test="@only-collapsed">
					<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
			</xsl:attribute>
			<xsl:if test="@mode='GOOGLEPAY'">
				<div>
					<xsl:attribute name="id">
						<xsl:value-of select="$fullname"/>_button</xsl:attribute>
				</div>
				<script type="POSTSCRIPT">createPaymentManager('<xsl:value-of select="$fullname"/>',{ countryCode: '<xsl:value-of select="@country"/>', currencyCode: '<xsl:value-of select="@currency"/>', totalPriceStatus: '<xsl:value-of select="@status"/>', totalPrice: '<xsl:value-of select="@price"/>' }, function() {goToRefreshForm('do-PartialRefreshForm', '<xsl:value-of select="$fullname"/>', '<xsl:value-of select="action/@obj_provider"/>','<xsl:value-of select="action/@object_owner_id"/>', getLastModal(), '<xsl:value-of select="action/@id_action"/>', null<xsl:if test="action/@object_context_id">,'<xsl:value-of select="action/@object_context_id"/>'</xsl:if>);} ); </script>
			</xsl:if>
			<xsl:if test="@mode='MERCADOPAGO'">
				<div class="col-sm-offset-3 col-sm-6">
					<fieldset id="pay-panel"
        						class="panel-default shadow "
        						style="height:250px">
						<legend>Formulario de pago</legend>
						<div class="panel-body"
   							id="form-checkout">
							<div class="col-sm-12">
								<div class="input-group">
									<input type="text"
     										class="form-control input-sm"
     										name="cardNumber"
     										id="form-checkout__cardNumber"/>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="input-group">
									<input type="text"
     										class="form-control input-sm"
     										name="cardExpirationMonth"
     										id="form-checkout__cardExpirationMonth"/>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="input-group">
									<input type="text"
     										class="form-control input-sm"
     										name="cardExpirationYear"
     										id="form-checkout__cardExpirationYear"/>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="input-group">
									<input type="text"
     										class="form-control input-sm"
     										name="cardholderName"
     										id="form-checkout__cardholderName"/>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="input-group">
									<input type="text"
     										class="form-control input-sm"
     										name="securityCode"
     										id="form-checkout__securityCode"/>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="input-group">
									<select name="issuer"
      										class="form-control input-sm"
      										id="form-checkout__issuer"/>
								</div>
							</div>
							<div class="col-sm-4">
								<div class="input-group">
									<select name="identificationType"
      										class="form-control input-sm"
      										id="form-checkout__identificationType"/>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="input-group">
									<input type="text"
     										class="form-control input-sm"
     										name="identificationNumber"
     										id="form-checkout__identificationNumber"/>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="input-group">
									<select name="installments"
      										class="form-control input-sm"
      										id="form-checkout__installments"/>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="input-group">
									<button type="submit"
      										class="btn btn-primary"
      										id="form-checkout__submit">Pagar</button>
								</div>
							</div>
							<progress value="0"
        								class="progress-bar">loading...</progress>
						</div>
					</fieldset>
				</div>
				<script type="POSTSCRIPT">createPaymentMercadopagoManager('<xsl:value-of select="$fullname"/>',{ countryCode: '<xsl:value-of select="@country"/>', currencyCode: '<xsl:value-of select="@currency"/>', totalPriceStatus: '<xsl:value-of select="@status"/>', totalPrice: '<xsl:value-of select="@price"/>' }, function() {goToRefreshForm('do-PartialRefreshForm', '<xsl:value-of select="$fullname"/>', '<xsl:value-of select="action/@obj_provider"/>','<xsl:value-of select="action/@object_owner_id"/>', getLastModal(), '<xsl:value-of select="action/@id_action"/>', null<xsl:if test="action/@object_context_id">,'<xsl:value-of select="action/@object_context_id"/>'</xsl:if>);} ); </script>
			</xsl:if>
		</div>
	</xsl:template>
	<xsl:template match="file">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<INPUT type="hidden">
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
			<xsl:attribute name="name">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
		</INPUT>
		<script charset="ISO-8859-1">registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']<xsl:if test="constraints/@required='true'">,'<xsl:value-of select="constraints/@description"/>',true</xsl:if>); </script>
		<xsl:if test="@editable!='false'">
			<iframe src="html/upload.html"
      				seamless="seamless">
				<xsl:attribute name="id">iframe_<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:attribute name="name">iframe_<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:attribute name="style">
					<xsl:call-template name="generate_inline_style"/>
				</xsl:attribute>
			</iframe>
		</xsl:if>
	</xsl:template>
	<xsl:template match="sign">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<INPUT type="hidden">
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
			<xsl:attribute name="name">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
		</INPUT>
		<script charset="ISO-8859-1">registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']); document.getElementById('signApplet_<xsl:value-of select="$fullname"/>').setScriptFromApplet(getURL()+'/html/do-upload'); </script>
		<div>
			<xsl:attribute name="style">
				<xsl:call-template name="generate_inline_style"/>
			</xsl:attribute>
			<applet codebase="jar"
      				code="pss.common.pki.appletSign.SignForm.class"
      				archive="signForm.jar,itext.jar,bcprov-jdk15.jar,WebSign.jar,sunpkcs11.jar"
      				width="170"
      				height="35">
				<xsl:attribute name="id">signApplet_<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:attribute name="name">signApplet_<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<PARAM NAME="outputFilename">
					<xsl:attribute name="value">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
				</PARAM>
				<PARAM NAME="outputProgreso">
					<xsl:attribute name="value">f1_progreso_process</xsl:attribute>
				</PARAM>
				<PARAM NAME="filename"
     					VALUE=""/>
				<PARAM NAME="label_button"
     					VALUE="Elegir Escrito"/>
			</applet>
			<p id="f1_progreso_process"/>
		</div>
	</xsl:template>
	<xsl:template match="scanner">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<INPUT type="hidden">
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
			<xsl:attribute name="name">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
		</INPUT>
		<script charset="ISO-8859-1">registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']); document.getElementById('scannerApplet_<xsl:value-of select="$fullname"/>').setScriptFromApplet(getURL()+'/html/do-upload'); </script>
		<div>
			<xsl:attribute name="style">
				<xsl:call-template name="generate_inline_style"/>
			</xsl:attribute>
			<applet codebase="jar"
      				code="uk.co.mmscomputing.device.twain.applet.TwainApplet.class"
      				archive="scanner.jar"
      				width="300"
      				height="35">
				<xsl:attribute name="id">scannerApplet_<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<xsl:attribute name="name">scannerApplet_<xsl:value-of select="$fullname"/>
				</xsl:attribute>
				<PARAM NAME="outputFilename">
					<xsl:attribute name="value">
						<xsl:value-of select="$fullname"/>
					</xsl:attribute>
				</PARAM>
				<PARAM NAME="outputProgreso">
					<xsl:attribute name="value">f1_progreso_process</xsl:attribute>
				</PARAM>
				<PARAM NAME="filename"
     					VALUE=""/>
			</applet>
			<p id="f1_progreso_process"/>
		</div>
	</xsl:template>
	<xsl:template match="splitpane">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<INPUT type="hidden">
			<xsl:attribute name="id">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
			<xsl:attribute name="name">
				<xsl:value-of select="$fullname"/>
			</xsl:attribute>
		</INPUT>
		<script charset="ISO-8859-1">registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']); </script>
		<div>
			<xsl:attribute name="style">
				<xsl:call-template name="generate_inline_style"/>
			</xsl:attribute>
			<xsl:call-template name="basic_generate_component"/>
			<xsl:for-each select="panel_a/panel">
				<xsl:call-template name="basic_generate_composite"/>
			</xsl:for-each>
			<xsl:for-each select="panel_b/panel">
				<xsl:call-template name="basic_generate_composite"/>
			</xsl:for-each>
		</div>
	</xsl:template>
	<xsl:template match="date_combos_box">
		<xsl:call-template name="basic_generate_composite"/>
	</xsl:template>
	<xsl:template match="month_year_combos_box">
		<xsl:call-template name="basic_generate_composite"/>
	</xsl:template>
	<xsl:template match="radio_button_set">
		<xsl:variable name="onclick_event_var">
			<xsl:value-of select="@onclick_event"/>
		</xsl:variable>
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="orientation">
			<xsl:value-of select="@orientation"/>
		</xsl:variable>
		<DIV>
			<xsl:call-template name="basic_generate_form_component"/>
			<xsl:for-each select="item">
				<INPUT type="radio">
					<xsl:if test="../@editable='false'">
						<xsl:attribute name="disabled">true</xsl:attribute>
					</xsl:if>
					<xsl:attribute name="name">
						<xsl:value-of select="$fullname"/>_options</xsl:attribute>
					<xsl:attribute name="value">
						<xsl:value-of select="@id"/>
					</xsl:attribute>
					<xsl:if test="@selected='true'">
						<xsl:attribute name="checked"/>
					</xsl:if>
					<xsl:if test="../@editable!='false'">
						<xsl:if test="$onclick_event_var!=''">
							<xsl:attribute name="onclick">
								<xsl:value-of select="$onclick_event_var"/>
							</xsl:attribute>
						</xsl:if>
					</xsl:if>
					<xsl:value-of select="@description"/>
				</INPUT>
				<xsl:if test="$orientation='vertical'">
					<br/>
				</xsl:if>
				<xsl:if test="$orientation='horizontal'">
					<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;&nbsp;]]></xsl:text>
				</xsl:if>
			</xsl:for-each>
		</DIV>
	</xsl:template>
	<xsl:template match="radio_button_set_responsive">
		<xsl:variable name="onclick_event_var">
			<xsl:value-of select="@onclick_event"/>
		</xsl:variable>
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="orientation">
			<xsl:value-of select="@orientation"/>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="$orientation='toogle'">
				<DIV>
					<xsl:attribute name="class">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
						</xsl:if>
						<xsl:choose>
							<xsl:when test="@visible and (@visible='true')">form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
							</xsl:when>
							<xsl:otherwise>form-group hidden</xsl:otherwise>
						</xsl:choose>
						<xsl:if test="@only-expanded">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
						<xsl:if test="@only-collapsed">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
					</xsl:attribute>
					<label>
						<xsl:attribute name="for">
							<xsl:value-of select="$fullname"/>
						</xsl:attribute>
						<xsl:if test="@label_class">
							<xsl:attribute name="class">
								<xsl:value-of select="@label_class"/>
							</xsl:attribute>
						</xsl:if>
						<xsl:value-of select="@label_lateral"/>
					</label>
					<DIV>
						<xsl:attribute name="class">radio-group btn-group</xsl:attribute>
						<xsl:attribute name="data-toggle">buttons</xsl:attribute>
						<xsl:call-template name="basic_generate_form_component_responsive">
							<xsl:with-param name="noScripts">true</xsl:with-param>
						</xsl:call-template>
						<xsl:call-template name="basic_generate_form_component_scripts_in"/>
						<xsl:for-each select="item">
							<label>
								<xsl:attribute name="class">btn<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
									<xsl:value-of select="../@class_radio"/>
									<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
									<xsl:if test="../@editable='false'">disabled</xsl:if>
									<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
									<xsl:if test="@selected='true'">active</xsl:if>
								</xsl:attribute>
								<xsl:if test="../@editable='false'">
									<xsl:attribute name="onclick">var evt = event ? event:window.event; if (evt.stopPropagation) evt.stopPropagation(); if (evt.cancelBubble!=null) evt.cancelBubble = true; evt.preventDefault(); return false; </xsl:attribute>
								</xsl:if>
								<input>
									<xsl:attribute name="type">radio</xsl:attribute>
									<xsl:if test="../@editable='false'">
										<xsl:attribute name="disabled"/>
									</xsl:if>
									<xsl:attribute name="name">
										<xsl:value-of select="$fullname"/>_options</xsl:attribute>
									<xsl:attribute name="value">
										<xsl:value-of select="@id"/>
									</xsl:attribute>
									<xsl:if test="@selected='true'">
										<xsl:attribute name="checked"/>
									</xsl:if>
									<xsl:if test="@enteristab">
										<xsl:attribute name="class">enteristab</xsl:attribute>
									</xsl:if>
									<xsl:if test="not(@enteristab)">
										<xsl:attribute name="class">focuseable</xsl:attribute>
									</xsl:if>
									<xsl:if test="../@editable!='false'">
										<xsl:attribute name="onclick">
											<xsl:if test="$onclick_event_var!=''">
												<xsl:value-of select="$onclick_event_var"/>
											</xsl:if>
										</xsl:attribute>
									</xsl:if>
									<xsl:value-of select="@description"/>
								</input>
							</label>
						</xsl:for-each>
					</DIV>
				</DIV>
			</xsl:when>
			<xsl:otherwise>
				<DIV>
					<xsl:attribute name="class">
						<xsl:if test="@size_responsive">
							<xsl:value-of select="@size_responsive"/>
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
						</xsl:if>
						<xsl:choose>
							<xsl:when test="@visible and (@visible='true')">form-group <xsl:if test="constraints/@required='true'"><![CDATA[ ]]>required</xsl:if>
							</xsl:when>
							<xsl:otherwise>form-group hidden</xsl:otherwise>
						</xsl:choose>
						<xsl:if test="@only-expanded">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>collapsable</xsl:if>
						<xsl:if test="@only-collapsed">
							<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>nocollapsable</xsl:if>
					</xsl:attribute>
					<xsl:if test="@label_lateral">
						<label>
							<xsl:attribute name="for">
								<xsl:value-of select="$fullname"/>
							</xsl:attribute>
							<xsl:if test="@label_class">
								<xsl:attribute name="class">
									<xsl:value-of select="@label_class"/>
								</xsl:attribute>
							</xsl:if>
							<xsl:value-of select="@label_lateral"/>
						</label>
					</xsl:if>
					<DIV>
						<xsl:attribute name="class">radio</xsl:attribute>
						<xsl:call-template name="basic_generate_form_component_responsive">
							<xsl:with-param name="noScripts">true</xsl:with-param>
						</xsl:call-template>
						<xsl:for-each select="item">
							<INPUT type="radio">
								<xsl:if test="../@editable='false'">
									<xsl:attribute name="disabled">true</xsl:attribute>
								</xsl:if>
								<xsl:attribute name="name">
									<xsl:value-of select="$fullname"/>_options</xsl:attribute>
								<xsl:attribute name="value">
									<xsl:value-of select="@id"/>
								</xsl:attribute>
								<xsl:attribute name="class">radio <xsl:if test="@enteristab">enteristab</xsl:if><![CDATA[ ]]>focuseable</xsl:attribute>
								<xsl:if test="@selected='true'">
									<xsl:attribute name="checked"/>
								</xsl:if>
								<xsl:if test="../@editable!='false'">
									<xsl:attribute name="onclick">
										<xsl:if test="$onclick_event_var!=''">
											<xsl:value-of select="$onclick_event_var"/>
										</xsl:if>
									</xsl:attribute>
									<xsl:if test="@connect_control">
										<xsl:attribute name="onchange">runUpKey('<xsl:value-of select="@connect_control"/>'); </xsl:attribute>
									</xsl:if>
								</xsl:if>
								<xsl:value-of select="@description"/>
							</INPUT>
							<xsl:if test="$orientation='vertical'">
								<br/>
							</xsl:if>
							<xsl:if test="$orientation='horizontal'">
								<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;&nbsp;]]></xsl:text>
							</xsl:if>
						</xsl:for-each>
					</DIV>
				</DIV>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:call-template name="basic_generate_form_component_scripts_out"/>
		<xsl:if test="@connect_control">
			<script>subscribeControlConnect( '<xsl:value-of select="@connect_control"/>', function() { hideSelectMetaItemsRadio('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); hideSelectMetaItemsRadio('<xsl:value-of select="$fullname"/>','<xsl:value-of select="@connect_control_field"/>','dgf_form_<xsl:value-of select="@connect_control"/>_fd-swap_zone_to','<xsl:value-of select="@connect_control_operator"/>','<xsl:value-of select="@connect_control_datatype"/>'); }); </script>
		</xsl:if>
	</xsl:template>
	<xsl:template match="date_chooser">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:variable name="isEditable">
			<xsl:value-of select="@editable"/>
		</xsl:variable>
		<INPUT type="text">
			<xsl:if test="$isEditable='true'">
				<xsl:attribute name="onKeypress">onKeyToClick(event,'<xsl:value-of select="$fullname"/>_trigger');</xsl:attribute>
			</xsl:if>
			<xsl:call-template name="basic_generate_text_field">
				<xsl:with-param name="specialClass">date_field</xsl:with-param>
			</xsl:call-template>
		</INPUT>
		<xsl:if test="@js_date_format and @visible and (@visible='true')">
			<img>
				<xsl:attribute name="style">position:<xsl:value-of select="@position_img"/>;left:<xsl:value-of select="@icon_x"/>px;height:<xsl:value-of select="@icon_y"/>px;cursor:pointer; </xsl:attribute>
				<xsl:attribute name="name">
					<xsl:value-of select="$fullname"/>_trigger</xsl:attribute>
				<xsl:attribute name="id">
					<xsl:value-of select="$fullname"/>_trigger</xsl:attribute>
				<xsl:if test="@pop_up_icon">
					<xsl:attribute name="src">
						<xsl:value-of select="$skin_path"/>/<xsl:value-of select="@pop_up_icon"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:if test="$isEditable='false'">
					<xsl:attribute name="disabled">
						<xsl:value-of select="$isEditable"/>
					</xsl:attribute>
				</xsl:if>
			</img>
			<script charset="ISO-8859-1">registerDate("<xsl:value-of select="$fullname"/>","<xsl:value-of select="@js_date_format"/>","<xsl:value-of select="/page/header/layouts/layout[@id='text_field']/@height"/>"); </script>
		</xsl:if>
	</xsl:template>
	<xsl:template name="basic_generate_composite">
		<DIV>
			<xsl:if test="/page/header/savehelp">
				<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="@name"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',0); </xsl:attribute>
			</xsl:if>
			<xsl:call-template name="basic_generate_component"/>
		</DIV>
	</xsl:template>
	<xsl:template name="basic_generate_action_view">
		<xsl:param name="style_attr"
         			select="'null'"/>
		<xsl:call-template name="generate_action_on_click"/>
		<div>
			<xsl:call-template name="basic_generate_component">
				<xsl:with-param name="isLink">true</xsl:with-param>
				<xsl:with-param name="style_attr"
              					select="$style_attr"/>
			</xsl:call-template>
			<xsl:call-template name="render_compound_label"/>
		</div>
	</xsl:template>
	<xsl:template name="basic_generate_text_field">
		<xsl:param name="isEncrypted"
         			select="'false'"/>
		<xsl:param name="specialClass"
         			select="'null'"/>
		<xsl:if test="@force_focus">
			<xsl:attribute name="autofocus">true</xsl:attribute>
		</xsl:if>
		<xsl:for-each select="onblur">
			<xsl:call-template name="generate_action_onblur"/>
		</xsl:for-each>
		<xsl:if test="@placeholder">
			<xsl:attribute name="placeholder">
				<xsl:value-of select="@placeholder"/>
			</xsl:attribute>
		</xsl:if>
		<xsl:attribute name="onKeyup">return calculeColor(this,<xsl:if test="not(@max_length)">-1</xsl:if>
			<xsl:if test="@max_length ">
				<xsl:value-of select="@max_length"/>
			</xsl:if>,'<xsl:value-of select="constraints/@datatype"/>', '<xsl:value-of select="constraints/format/@pattern"/>', '<xsl:value-of select="constraints/format/@chars"/>', '#ff0000', '#000000'); </xsl:attribute>
		<xsl:if test="constraints/@datatype='JFLOAT' or constraints/@datatype='JCURRENCY' or constraints/@datatype='JLONG' or constraints/@datatype='JINT' ">
			<xsl:attribute name="onkeydown">if (!legalNumericChar(event, <xsl:value-of select="constraints/@unsigned"/>, <xsl:value-of select="constraints/@blockoversite"/>)) return false; return gaOnKey(event, this);</xsl:attribute>
		</xsl:if>
		<xsl:if test="text">
			<xsl:attribute name="value">
				<xsl:value-of select="text/."/>
			</xsl:attribute>
		</xsl:if>
		<xsl:if test="@max_length">
			<xsl:attribute name="maxlength">
				<xsl:value-of select="@max_length"/>
			</xsl:attribute>
		</xsl:if>
		<xsl:call-template name="basic_generate_form_component">
			<xsl:with-param name="isTextComponent">true</xsl:with-param>
			<xsl:with-param name="isEncrypted">
				<xsl:value-of select="$isEncrypted"/>
			</xsl:with-param>
			<xsl:with-param name="specialClass">
				<xsl:value-of select="$specialClass"/>
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="basic_generate_text_field_responsive">
		<xsl:param name="isEncrypted"
         			select="'false'"/>
		<xsl:param name="specialClass"
         			select="'null'"/>
		<xsl:param name="actionOnEnter"
         			select="'true'"/>
		<xsl:param name="margin_padding"
         			select="'true'"/>
		<xsl:param name="name">
			<xsl:value-of select="@name"/>
		</xsl:param>
		<xsl:if test="@force_focus">
			<xsl:attribute name="autofocus">true</xsl:attribute>
		</xsl:if>
		<xsl:for-each select="onblur">
			<xsl:call-template name="generate_action_onblur"/>
		</xsl:for-each>
		<xsl:if test="@placeholder">
			<xsl:attribute name="placeholder">
				<xsl:value-of select="@placeholder"/>
			</xsl:attribute>
		</xsl:if>
		<xsl:attribute name="onKeyup">runUpKey('<xsl:value-of select="@connect_control"/>'); return calculeColor(this,<xsl:if test="not(@max_length)">-1</xsl:if>
			<xsl:if test="@max_length ">
				<xsl:value-of select="@max_length"/>
			</xsl:if>,'<xsl:value-of select="constraints/@datatype"/>', '<xsl:value-of select="constraints/format/@pattern"/>', '<xsl:value-of select="constraints/format/@chars"/>', '#ff0000', '#000000'); </xsl:attribute>
		<xsl:if test="constraints/@datatype='JFLOAT' or constraints/@datatype='JCURRENCY' or constraints/@datatype='JLONG' or constraints/@datatype='JINT' ">
			<xsl:attribute name="onkeydown">if (!legalNumericChar(event, <xsl:value-of select="constraints/@unsigned"/>, <xsl:value-of select="constraints/@blockoversite"/>)) return false; return gaOnKey(event, this);</xsl:attribute>
		</xsl:if>
		<xsl:if test="text">
			<xsl:attribute name="value">
				<xsl:value-of select="text/."/>
			</xsl:attribute>
		</xsl:if>
		<xsl:if test="@max_length">
			<xsl:attribute name="maxlength">
				<xsl:value-of select="@max_length"/>
			</xsl:attribute>
		</xsl:if>
		<xsl:call-template name="basic_generate_form_component_responsive">
			<xsl:with-param name="isTextComponent">true</xsl:with-param>
			<xsl:with-param name="isEncrypted">
				<xsl:value-of select="$isEncrypted"/>
			</xsl:with-param>
			<xsl:with-param name="specialClass">
				<xsl:value-of select="$specialClass"/>
			</xsl:with-param>
			<xsl:with-param name="actionOnEnter">
				<xsl:value-of select="$actionOnEnter"/>
			</xsl:with-param>
			<xsl:with-param name="name">
				<xsl:value-of select="$name"/>
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="basic_set_editable">
		<xsl:param name="isTextComponent"
         			select="'false'"/>
		<xsl:param name="actionOnEnter"
         			select="'true'"/>
		<xsl:if test="@needenter='false' and $actionOnEnter='true'">
			<xsl:call-template name="generate_action_on_enter"/>
		</xsl:if>
		<xsl:if test="@editable='false'">
			<xsl:attribute name="tabindex">-1</xsl:attribute>
			<xsl:choose>
				<xsl:when test="$isTextComponent='true'">
					<xsl:attribute name="readonly">true</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="disabled"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
	<xsl:template name="basic_generate_form_component">
		<xsl:param name="isTextComponent"
         			select="'false'"/>
		<xsl:param name="isEncrypted"
         			select="'false'"/>
		<xsl:param name="specialClass"
         			select="'null'"/>
		<xsl:param name="noScripts"
         			select="'false'"/>
		<xsl:param name="onchange_event_var"
         			select="''"/>
		<xsl:param name="onclick_event_var"
         			select="''"/>
		<xsl:param name="style_attr"
         			select="@style_attr"/>
		<xsl:call-template name="basic_set_editable">
			<xsl:with-param name="isTextComponent">
				<xsl:value-of select="$isTextComponent"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="basic_generate_component">
			<xsl:with-param name="isEditComponent"
              				select="'true'"/>
			<xsl:with-param name="isEncrypted">
				<xsl:value-of select="$isEncrypted"/>
			</xsl:with-param>
			<xsl:with-param name="specialClass">
				<xsl:value-of select="$specialClass"/>
			</xsl:with-param>
			<xsl:with-param name="style_attr"
              				select="$style_attr"/>
		</xsl:call-template>
		<xsl:if test="$noScripts='false'">
			<xsl:call-template name="basic_generate_form_component_scripts_in">
				<xsl:with-param name="isEncrypted"
              					select="$isEncrypted"/>
				<xsl:with-param name="onchange_event_var"
              					select="$onchange_event_var"/>
				<xsl:with-param name="onclick_event_var"
              					select="$onclick_event_var"/>
			</xsl:call-template>
		</xsl:if>
		<xsl:if test="$noScripts!='false'">
			<xsl:if test="$onchange_event_var!=''">
				<xsl:attribute name="onchange">
					<xsl:value-of select="$onchange_event_var"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="$onclick_event_var!=''">
				<xsl:attribute name="onclick">
					<xsl:value-of select="$onclick_event_var"/>
				</xsl:attribute>
			</xsl:if>
		</xsl:if>
	</xsl:template>
	<xsl:template name="basic_generate_form_component_responsive">
		<xsl:param name="isTextComponent"
         			select="'false'"/>
		<xsl:param name="isEncrypted"
         			select="'false'"/>
		<xsl:param name="specialClass"
         			select="'null'"/>
		<xsl:param name="noScripts"
         			select="'false'"/>
		<xsl:param name="onchange_event_var"
         			select="''"/>
		<xsl:param name="onclick_event_var"
         			select="''"/>
		<xsl:param name="style_attr"
         			select="@style_attr"/>
		<xsl:param name="actionOnEnter"
         			select="'true'"/>
		<xsl:param name="margin_padding"
         			select="'true'"/>
		<xsl:param name="name">
			<xsl:value-of select="@name"/>
		</xsl:param>
		<xsl:call-template name="basic_set_editable">
			<xsl:with-param name="isTextComponent">
				<xsl:value-of select="$isTextComponent"/>
			</xsl:with-param>
			<xsl:with-param name="actionOnEnter">
				<xsl:value-of select="$actionOnEnter"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="basic_generate_component_responsive">
			<xsl:with-param name="isEditComponent"
              				select="'true'"/>
			<xsl:with-param name="isEncrypted">
				<xsl:value-of select="$isEncrypted"/>
			</xsl:with-param>
			<xsl:with-param name="specialClass">
				<xsl:value-of select="$specialClass"/>
			</xsl:with-param>
			<xsl:with-param name="style_attr"
              				select="$style_attr"/>
			<xsl:with-param name="margin_padding"
              				select="$margin_padding"/>
			<xsl:with-param name="name"
              				select="$name"/>
		</xsl:call-template>
		<xsl:if test="$noScripts='false'">
			<xsl:call-template name="basic_generate_form_component_scripts_in">
				<xsl:with-param name="isEncrypted"
              					select="$isEncrypted"/>
				<xsl:with-param name="onchange_event_var"
              					select="$onchange_event_var"/>
				<xsl:with-param name="onclick_event_var"
              					select="$onclick_event_var"/>
			</xsl:call-template>
		</xsl:if>
		<xsl:if test="$noScripts!='false'">
			<xsl:if test="$onchange_event_var!=''">
				<xsl:attribute name="onchange">
					<xsl:value-of select="$onchange_event_var"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="$onclick_event_var!=''">
				<xsl:attribute name="onclick">
					<xsl:value-of select="$onclick_event_var"/>
				</xsl:attribute>
			</xsl:if>
		</xsl:if>
	</xsl:template>
	<xsl:template name="basic_generate_form_component_scripts_in">
		<xsl:param name="isEncrypted"
         			select="'false'"/>
		<xsl:param name="onchange_event_var"
         			select="''"/>
		<xsl:param name="onclick_event_var"
         			select="''"/>
		<xsl:param name="change_mode"
         			select="''"/>
		<xsl:variable name="name_prefix">dgf_<xsl:value-of select="@form_name"/>_fd-</xsl:variable>
		<xsl:variable name="fullname">
			<xsl:value-of select="$name_prefix"/>
			<xsl:value-of select="@name"/>
			<xsl:if test="$isEncrypted='true'">_encrypted</xsl:if>
		</xsl:variable>
		<xsl:attribute name="oninput">
			<xsl:if test="@detect_changes='true'">setChangeInputs(true);</xsl:if>
		</xsl:attribute>
		<xsl:if test="$change_mode=''">
			<xsl:attribute name="onchange">
				<xsl:if test="@detect_changes='true'">setChangeInputs(true);</xsl:if>
				<xsl:if test="$onchange_event_var!=''">
					<xsl:value-of select="$onchange_event_var"/>
				</xsl:if>
				<xsl:if test="@onCalculate and @isCalculeOthersFields">
					<xsl:value-of select="@onCalculate"/>; </xsl:if>executeOnChange('<xsl:value-of select="$fullname"/>'); <xsl:if test="@refreshForm='true'">
					<xsl:choose>
						<xsl:when test="starts-with(@form_name,'filter_pane') or (@noform='true')">goToRefreshForm('do-WinListRefreshAction<xsl:if test="action/@data_string and not(@action/data_string='')">?<xsl:value-of select="action/@data_string"/>
							</xsl:if>', '<xsl:value-of select="$fullname"/>', '<xsl:value-of select="action/@obj_provider"/>','<xsl:value-of select="action/@object_owner_id"/>', 'win_list_complete_<xsl:value-of select="action/@obj_provider"/>', '<xsl:value-of select="action/@id_action"/>'); </xsl:when>
						<xsl:otherwise>goToRefreshForm('do-PartialRefreshForm', '<xsl:value-of select="$fullname"/>', '<xsl:value-of select="action/@obj_provider"/>','<xsl:value-of select="action/@object_owner_id"/>', getLastModal(), '<xsl:value-of select="action/@id_action"/>', null<xsl:if test="action/@object_context_id">,'<xsl:value-of select="action/@object_context_id"/>'</xsl:if>); </xsl:otherwise>
					</xsl:choose>
				</xsl:if>
				<xsl:if test="@connect_control and not(@refreshForm='true')">runUpKey('<xsl:value-of select="@connect_control"/>'); </xsl:if>
			</xsl:attribute>
			<xsl:if test="$onclick_event_var!=''">
				<xsl:attribute name="onclick">
					<xsl:value-of select="$onclick_event_var"/>
				</xsl:attribute>
			</xsl:if>
		</xsl:if>
		<xsl:if test="$change_mode!=''">
			<xsl:attribute name="onclick">
				<xsl:if test="$onchange_event_var!=''">
					<xsl:value-of select="$onchange_event_var"/>
				</xsl:if>
				<xsl:if test="@onCalculate and @isCalculeOthersFields">
					<xsl:value-of select="@onCalculate"/>; </xsl:if>
				<xsl:if test="@detect_changes='true'">setChangeInputs(true);</xsl:if>executeOnChange('<xsl:value-of select="$fullname"/>'); <xsl:if test="@refreshForm='true'">
					<xsl:choose>
						<xsl:when test="starts-with(@form_name,'filter_pane') or (@noform='true')">goToRefreshForm('do-WinListRefreshAction<xsl:if test="action/@data_string and not(@action/data_string='')">?<xsl:value-of select="action/@data_string"/>
							</xsl:if>', '<xsl:value-of select="$fullname"/>', '<xsl:value-of select="action/@obj_provider"/>','<xsl:value-of select="action/@object_owner_id"/>', 'win_list_complete_<xsl:value-of select="action/@obj_provider"/>', '<xsl:value-of select="action/@id_action"/>'); </xsl:when>
						<xsl:otherwise>goToRefreshForm('do-PartialRefreshForm', '<xsl:value-of select="$fullname"/>', '<xsl:value-of select="action/@obj_provider"/>','<xsl:value-of select="action/@object_owner_id"/>', getLastModal(), '<xsl:value-of select="action/@id_action"/>', null<xsl:if test="action/@object_context_id">,'<xsl:value-of select="action/@object_context_id"/>'</xsl:if>); </xsl:otherwise>
					</xsl:choose>
				</xsl:if>
				<xsl:if test="@connect_control and not(@refreshForm='true')">runUpKey('<xsl:value-of select="@connect_control"/>'); </xsl:if>
				<xsl:if test="$onclick_event_var!=''">
					<xsl:value-of select="$onclick_event_var"/>
				</xsl:if>
			</xsl:attribute>
		</xsl:if>
		<xsl:if test="/page/header/savehelp">
			<xsl:attribute name="oncontextmenu">return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1); </xsl:attribute>
		</xsl:if>
		<xsl:if test="$onclick_event_var!=''">
			<xsl:attribute name="onclick">
				<xsl:value-of select="$onclick_event_var"/>
			</xsl:attribute>
		</xsl:if>
	</xsl:template>
	<xsl:template name="basic_generate_form_component_scripts_out">
		<xsl:param name="isEncrypted"
         			select="'false'"/>
		<xsl:param name="onchange_event_var"
         			select="''"/>
		<xsl:param name="onclick_event_var"
         			select="''"/>
		<xsl:param name="change_mode"
         			select="''"/>
		<xsl:variable name="name_prefix">dgf_<xsl:value-of select="@form_name"/>_fd-</xsl:variable>
		<xsl:variable name="fullname">
			<xsl:value-of select="$name_prefix"/>
			<xsl:value-of select="@name"/>
			<xsl:if test="$isEncrypted='true'">_encrypted</xsl:if>
		</xsl:variable>
		<xsl:if test="@intable='true'">
			<script>rdg('<xsl:value-of select="@table_name"/>','<xsl:value-of select="@name"/>',['<xsl:value-of select="$fullname"/>'],<xsl:value-of select="@table_row"/>,<xsl:value-of select="@table_column"/>); </script>
		</xsl:if>
		<xsl:if test="constraints">
			<script charset="ISO-8859-1">
				<xsl:if test="@force_focus">cleanFocusObject(); </xsl:if>register( '<xsl:value-of select="$fullname"/>', '<xsl:value-of select="constraints/@description"/>', <xsl:if test="@intable='true'">false</xsl:if>
				<xsl:if test="not(@intable) or @intable!='true'">
					<xsl:value-of select="constraints/@required"/>
				</xsl:if>, '<xsl:value-of select="constraints/format/@needs_input_check"/>', <xsl:value-of select="constraints/format/@max_length"/>, '<xsl:value-of select="constraints/@datatype"/>', '<xsl:value-of select="constraints/format/@pattern"/>', '<xsl:value-of select="constraints/format/@chars"/>', '<xsl:value-of select="constraints/@inputmode"/>', <xsl:if test="@foreground">0,</xsl:if>
				<xsl:if test="not(@foreground)">1,</xsl:if>'<xsl:value-of select="constraints/@align"/>',<xsl:if test="@intable='true'">true</xsl:if>
				<xsl:if test="not(@intable) or @intable!='true'">false</xsl:if>); </script>
			<script charset="ISO-8859-1">formatear('<xsl:value-of select="$fullname"/>');</script>
		</xsl:if>
		<xsl:if test="not(@intable) or @intable!='true'">
			<xsl:if test="registerAsField">
				<script charset="ISO-8859-1">registerNoConstraint('<xsl:value-of select="$fullname"/>',[ <xsl:for-each select="registerAsField/data">'<xsl:value-of select="$name_prefix"/>
						<xsl:value-of select="@value"/>', </xsl:for-each>]<xsl:if test="constraints/@required='true'">,'<xsl:value-of select="constraints/@description"/>',true</xsl:if>); </script>
			</xsl:if>
			<xsl:if test="not(registerAsField)">
				<script charset="ISO-8859-1">registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']); </script>
			</xsl:if>
		</xsl:if>
		<xsl:if test="@onAnyChange">
			<script charset="ISO-8859-1">
				<xsl:if test="@isCalculeOnStart">registerStartedScript('<xsl:value-of select="@orden"/>', '<xsl:value-of select="$fullname"/>', "<xsl:value-of select="@onAnyChange"/>; formatear('<xsl:value-of select="$fullname"/>');"); </xsl:if>
				<xsl:if test="@isCalculeOnAnyChange">registerScript('<xsl:value-of select="@orden"/>', '<xsl:value-of select="$fullname"/>', "<xsl:value-of select="@onAnyChange"/>;formatear('<xsl:value-of select="$fullname"/>');"); </xsl:if>
			</script>
		</xsl:if>
		<xsl:call-template name="registerDependencies"/>
	</xsl:template>
	<xsl:template name="registerDependencies">
		<xsl:variable name="name_prefix">dgf_<xsl:value-of select="@form_name"/>_fd-</xsl:variable>
		<xsl:variable name="fullname">
			<xsl:value-of select="$name_prefix"/>
			<xsl:value-of select="@name"/>
		</xsl:variable>
		<xsl:for-each select="dependencies">
			<script charset="ISO-8859-1">registerControlDependency('<xsl:value-of select="$fullname"/>','<xsl:value-of select="$name_prefix"/>
				<xsl:value-of select="@child"/>');</script>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="dragdrop_component">
		<xsl:if test="@drag and not(@drag='')">
			<xsl:attribute name="draggable">true</xsl:attribute>
			<xsl:attribute name="ondragstart">event.dataTransfer.setData("object","<xsl:value-of select="@drag"/>"); event.dataTransfer.setData("class","<xsl:value-of select="@dragclass"/>"); </xsl:attribute>
		</xsl:if>
		<xsl:for-each select="drop">
			<xsl:call-template name="generate_action_ondrop"/>
		</xsl:for-each>
		<xsl:if test="not(dblclick)">
			<xsl:for-each select="dblclickDrag">
				<xsl:call-template name="generate_action_on_dblclick"/>
			</xsl:for-each>
		</xsl:if>
	</xsl:template>
	<xsl:template name="basic_generate_component">
		<xsl:param name="isForm"
         			select="'false'"/>
		<xsl:param name="isEmbeddedForm"
         			select="'false'"/>
		<xsl:param name="isEditComponent"
         			select="'false'"/>
		<xsl:param name="style_attr"
         			select="@style_attr"/>
		<xsl:param name="isLink"
         			select="'false'"/>
		<xsl:param name="isEncrypted"
         			select="'false'"/>
		<xsl:param name="specialClass"
         			select="'null'"/>
		<xsl:param name="with_title"
         			select="'false'"/>
		<xsl:variable name="className">
			<xsl:choose>
				<xsl:when test="$specialClass!='null'">
					<xsl:value-of select="$specialClass"/>
				</xsl:when>
				<xsl:when test="@skin_stereotype and not(@skin_stereotype='')">pwr_<xsl:value-of select="@skin_stereotype"/>
				</xsl:when>
				<xsl:otherwise/>
			</xsl:choose>
		</xsl:variable>
		<xsl:if test="$className!='' and $className!='null'">
			<xsl:attribute name="class">
				<xsl:value-of select="$className"/>
				<xsl:if test="@state">-<xsl:value-of select="@state"/>
				</xsl:if>
			</xsl:attribute>
		</xsl:if>
		<xsl:if test="@role">
			<xsl:attribute name="role">
				<xsl:value-of select="@role"/>
			</xsl:attribute>
		</xsl:if>
		<xsl:if test="@editable='true' and constraints/@required and (constraints/@required='true')">
			<xsl:attribute name="required"/>
		</xsl:if>
		<xsl:attribute name="onfocus">onFocusObject(this); </xsl:attribute>
		<xsl:if test="@tool_tip">
			<xsl:attribute name="title">
				<xsl:value-of select="@tool_tip"/>
			</xsl:attribute>
		</xsl:if>
		<xsl:for-each select="dragdropinfo">
			<xsl:call-template name="dragdrop_component"/>
		</xsl:for-each>
		<xsl:choose>
			<xsl:when test="$isLink='true'">
				<xsl:attribute name="style">{ <xsl:call-template name="generate_inline_style_no_location">
						<xsl:with-param name="style_attr"
              							select="$style_attr"/>
						<xsl:with-param name="useParentVisibility">true</xsl:with-param>
					</xsl:call-template>} :hover { <xsl:call-template name="generate_inline_style_no_location">
						<xsl:with-param name="style_attr"
              							select="$style_attr"/>
						<xsl:with-param name="useParentVisibility">true</xsl:with-param>
					</xsl:call-template>} </xsl:attribute>
			</xsl:when>
			<xsl:when test="$className='combo_box'"/>
			<xsl:otherwise>
				<xsl:attribute name="style">
					<xsl:call-template name="generate_inline_style">
						<xsl:with-param name="style_attr"
              							select="$style_attr"/>
					</xsl:call-template>
				</xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:choose>
			<xsl:when test="$isForm='true'">
				<DIV>
					<xsl:attribute name="name">
						<xsl:value-of select="@name"/>
					</xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="@name"/>
					</xsl:attribute>
					<xsl:apply-templates select="*"/>
				</DIV>
			</xsl:when>
			<xsl:when test="$isEmbeddedForm='true'">
				<div>
					<xsl:attribute name="name">
						<xsl:value-of select="@name"/>
					</xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="@name"/>
					</xsl:attribute>
					<xsl:apply-templates select="*"/>
				</div>
			</xsl:when>
			<xsl:when test="$isLink='true'">
				<xsl:if test="@composite and (@composite='true')">
					<xsl:apply-templates select="*"/>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="$isEditComponent='true'">
						<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/>
							<xsl:if test="$isEncrypted='true'">_encrypted</xsl:if>
						</xsl:variable>
						<xsl:attribute name="name">
							<xsl:value-of select="$fullname"/>
						</xsl:attribute>
						<xsl:attribute name="id">
							<xsl:value-of select="$fullname"/>
						</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="id">
							<xsl:value-of select="@name"/>
						</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="$with_title='true'">
					<xsl:if test="icon">
						<xsl:call-template name="render_icon"/>
					</xsl:if>
					<xsl:if test="@label">
						<xsl:value-of select="@label"/>
					</xsl:if>
				</xsl:if>
				<xsl:if test="@composite and (@composite='true')">
					<xsl:apply-templates select="*"/>
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="basic_generate_component_responsive">
		<xsl:param name="isForm"
         			select="'false'"/>
		<xsl:param name="isEmbeddedForm"
         			select="'false'"/>
		<xsl:param name="isEditComponent"
         			select="'false'"/>
		<xsl:param name="style_attr"
         			select="@style_attr"/>
		<xsl:param name="isLink"
         			select="'false'"/>
		<xsl:param name="isEncrypted"
         			select="'false'"/>
		<xsl:param name="specialClass"
         			select="'null'"/>
		<xsl:param name="with_title"
         			select="'false'"/>
		<xsl:param name="margin_padding"
         			select="'true'"/>
		<xsl:param name="name">
			<xsl:value-of select="@name"/>
		</xsl:param>
		<xsl:param name="force_width"
         			select="0"/>
		<xsl:if test="@class_responsive or $specialClass!='null'">
			<xsl:attribute name="class">
				<xsl:choose>
					<xsl:when test="$specialClass!='null'">
						<xsl:value-of select="$specialClass"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="@class_responsive"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
		</xsl:if>
		<xsl:if test="@role">
			<xsl:attribute name="role">
				<xsl:value-of select="@role"/>
			</xsl:attribute>
		</xsl:if>
		<xsl:if test="@editable='true' and constraints/@required and (constraints/@required='true')">
			<xsl:attribute name="required"/>
		</xsl:if>
		<xsl:attribute name="onfocus">onFocusObject(this); </xsl:attribute>
		<xsl:if test="@tool_tip">
			<xsl:attribute name="title">
				<xsl:value-of select="@tool_tip"/>
			</xsl:attribute>
		</xsl:if>
		<xsl:for-each select="dragdropinfo">
			<xsl:call-template name="dragdrop_component"/>
		</xsl:for-each>
		<xsl:attribute name="style">
			<xsl:call-template name="generate_inline_style_responsive">
				<xsl:with-param name="style_attr"
              					select="$style_attr"/>
				<xsl:with-param name="margin_padding"
              					select="$margin_padding"/>
				<xsl:with-param name="force_width"
              					select="$force_width"/>
			</xsl:call-template>
		</xsl:attribute>
		<xsl:choose>
			<xsl:when test="$isForm='true'">
				<DIV>
					<xsl:attribute name="name">
						<xsl:value-of select="@name"/>
					</xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="@name"/>
					</xsl:attribute>
					<xsl:apply-templates select="*"/>
				</DIV>
			</xsl:when>
			<xsl:when test="$isEmbeddedForm='true'">
				<div>
					<xsl:attribute name="name">
						<xsl:value-of select="@name"/>
					</xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="@name"/>
					</xsl:attribute>
					<xsl:apply-templates select="*"/>
				</div>
			</xsl:when>
			<xsl:when test="$isLink='true'">
				<xsl:if test="@composite and (@composite='true')">
					<xsl:apply-templates select="*"/>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="$isEditComponent='true'">
						<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="$name"/>
							<xsl:if test="$isEncrypted='true'">_encrypted</xsl:if>
						</xsl:variable>
						<xsl:attribute name="name">
							<xsl:value-of select="$fullname"/>
						</xsl:attribute>
						<xsl:attribute name="id">
							<xsl:value-of select="$fullname"/>
						</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="id">
							<xsl:value-of select="@name"/>
						</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="$with_title='true'">
					<xsl:choose>
						<xsl:when test="@label">
							<xsl:if test="icon">
								<xsl:call-template name="render_icon">
									<xsl:with-param name="style_attr">padding-right:5px</xsl:with-param>
								</xsl:call-template>
							</xsl:if>
							<xsl:if test="@label">
								<xsl:value-of select="@label"/>
							</xsl:if>
						</xsl:when>
					</xsl:choose>
				</xsl:if>
				<xsl:if test="@composite and (@composite='true')">
					<xsl:apply-templates select="*"/>
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="generate_inline_style">
		<xsl:param name="style_attr"
         			select="'null'"/>
		<xsl:param name="useParentVisibility"
         			select="'false'"/>
		<xsl:if test="@relative">position:<xsl:value-of select="@relative"/>;</xsl:if>
		<xsl:if test="not(@relative)">position:absolute;</xsl:if>
		<xsl:if test="@x">left:<xsl:value-of select="@x"/>px;</xsl:if>
		<xsl:if test="@y">top:<xsl:value-of select="@y"/>px;</xsl:if>
		<xsl:if test="@z">z-index:<xsl:value-of select="@z"/>;</xsl:if>
		<xsl:call-template name="generate_inline_style_no_location">
			<xsl:with-param name="style_attr"
              				select="$style_attr"/>
			<xsl:with-param name="useParentVisibility"
              				select="$useParentVisibility"/>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="generate_inline_style_no_location">
		<xsl:param name="style_attr"
         			select="'null'"/>
		<xsl:param name="useParentVisibility"
         			select="'false'"/>
		<xsl:if test="@overflow-x">overflow-x:<xsl:value-of select="@overflow-x"/>;</xsl:if>
		<xsl:if test="@overflow-y">overflow-y:<xsl:value-of select="@overflow-y"/>;</xsl:if>
		<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
		<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>
		<xsl:if test="@backgroundimage">background-image:url("<xsl:value-of select="@backgroundimage"/>");</xsl:if>
		<xsl:if test="@backgroundrepeat">background-repeat:<xsl:value-of select="@backgroundrepeat"/>;</xsl:if>
		<xsl:if test="@backgroundsize">background-size:<xsl:value-of select="@backgroundsize"/>;</xsl:if>
		<xsl:if test="dragdropinfo/@foreground and not(@foreground)">
			<xsl:if test="dragdropinfo/@foreground">color:<xsl:value-of select="dragdropinfo/@foreground"/>;</xsl:if>
		</xsl:if>
		<xsl:if test="(dragdropinfo/@background) and not(@background)">
			<xsl:if test="dragdropinfo/@background">background-color:<xsl:value-of select="dragdropinfo/@background"/>;</xsl:if>
		</xsl:if>
		<xsl:if test="@width">width:<xsl:value-of select="@width"/>px;</xsl:if>
		<xsl:if test="@height">height:<xsl:value-of select="@height"/>px;</xsl:if>
		<xsl:call-template name="generate_inline_style_margin_and_padding"/>
		<xsl:if test="$useParentVisibility='false'">
			<xsl:choose>
				<xsl:when test="@visible and (@visible='true')">visibility:visible;</xsl:when>
				<xsl:otherwise>visibility:hidden;</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
		<xsl:if test="content_layout/@halignment">text-align:<xsl:value-of select="content_layout/@halignment"/>; </xsl:if>
		<xsl:if test="content_layout/@valignment">vertical-align:<xsl:value-of select="content_layout/@valignment"/>; </xsl:if>
		<xsl:if test="not($style_attr='null')">
			<xsl:value-of select="$style_attr"/>
		</xsl:if>
	</xsl:template>
	<xsl:template name="generate_inline_style_responsive">
		<xsl:param name="style_attr"
         			select="'null'"/>
		<xsl:param name="useParentVisibility"
         			select="'false'"/>
		<xsl:param name="margin_padding"
         			select="'true'"/>
		<xsl:param name="force_width"
         			select="0"/>
		<xsl:if test="@maxwidth">max-width:<xsl:value-of select="@maxwidth"/>px;</xsl:if>
		<xsl:if test="@maxheight">max-height:<xsl:value-of select="@maxheight"/>px;</xsl:if>
		<xsl:if test="@rwidth">min-width:<xsl:value-of select="@rwidth"/>px;</xsl:if>
		<xsl:if test="@rheight">min-height:<xsl:value-of select="@rheight"/>px;</xsl:if>
		<xsl:if test="@height">height:<xsl:value-of select="@height"/>px;</xsl:if>
		<xsl:if test="$force_width!=0">width:<xsl:value-of select="$force_width"/>;</xsl:if>
		<xsl:if test="$force_width=0">
			<xsl:if test="@width">width:<xsl:value-of select="@width"/>px;</xsl:if>
		</xsl:if>
		<xsl:if test="@foreground">color:<xsl:value-of select="@foreground"/>;</xsl:if>
		<xsl:if test="@background">background-color:<xsl:value-of select="@background"/>;</xsl:if>
		<xsl:if test="@backgroundimage">background-image:url('<xsl:value-of select="@backgroundimage"/>');</xsl:if>
		<xsl:if test="@backgroundrepeat">background-repeat:<xsl:value-of select="@backgroundrepeat"/>;</xsl:if>
		<xsl:if test="@backgroundposition">background-position:<xsl:value-of select="@backgroundposition"/>;</xsl:if>
		<xsl:if test="@backgroundsize">background-size:<xsl:value-of select="@backgroundsize"/>;</xsl:if>
		<xsl:if test="@overflow-x">overflow-x:<xsl:value-of select="@overflow-x"/>;</xsl:if>
		<xsl:if test="@overflow-y">overflow-y:<xsl:value-of select="@overflow-y"/>;</xsl:if>
		<xsl:if test="@border">border:<xsl:value-of select="@border"/>;</xsl:if>
		<xsl:if test="@bordertop">border-top:<xsl:value-of select="@bordertop"/>;</xsl:if>
		<xsl:if test="@borderbottom">border-bottom:<xsl:value-of select="@borderbottom"/>;</xsl:if>
		<xsl:if test="@nowrap">white-space: nowrap;</xsl:if>
		<xsl:if test="@display">display: <xsl:value-of select="@display"/>;</xsl:if>
		<xsl:if test="@boxshadow">box-shadow: <xsl:value-of select="@boxshadow"/>;</xsl:if>
		<xsl:if test="@font_weight">font-weight:<xsl:value-of select="@font_weight"/>;</xsl:if>
		<xsl:if test="@font_style">font-style:<xsl:value-of select="@font_style"/>;</xsl:if>
		<xsl:if test="@font_size">font-size:<xsl:value-of select="@font_size"/>px;</xsl:if>
		<xsl:if test="$margin_padding='true'">
			<xsl:call-template name="generate_inline_style_margin_and_padding"/>
		</xsl:if>
		<xsl:if test="content_layout/@halignment">text-align:<xsl:value-of select="content_layout/@halignment"/>; </xsl:if>
		<xsl:if test="content_layout/@valignment">vertical-align:<xsl:value-of select="content_layout/@valignment"/>; </xsl:if>
		<xsl:if test="$useParentVisibility='false'">
			<xsl:choose>
				<xsl:when test="@visible and (@visible='true')">visibility:visible;</xsl:when>
				<xsl:otherwise>visibility:hidden;</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
		<xsl:if test="not($style_attr='null')">
			<xsl:value-of select="$style_attr"/>
		</xsl:if>
	</xsl:template>
	<xsl:template name="generate_inline_style_margin_and_padding">
		<xsl:if test="@padding-left">padding-left:<xsl:value-of select="@padding-left"/>;</xsl:if>
		<xsl:if test="@padding-top">padding-top:<xsl:value-of select="@padding-top"/>;</xsl:if>
		<xsl:if test="@padding-bottom">padding-bottom:<xsl:value-of select="@padding-bottom"/>;</xsl:if>
		<xsl:if test="@padding-right">padding-right:<xsl:value-of select="@padding-right"/>;</xsl:if>
		<xsl:if test="@margin-left">margin-left:<xsl:value-of select="@margin-left"/>;</xsl:if>
		<xsl:if test="@margin-top">margin-top:<xsl:value-of select="@margin-top"/>;</xsl:if>
		<xsl:if test="@margin-bottom">margin-bottom:<xsl:value-of select="@margin-bottom"/>;</xsl:if>
		<xsl:if test="@margin-right">margin-right:<xsl:value-of select="@margin-right"/>;</xsl:if>
	</xsl:template>
</xsl:stylesheet>