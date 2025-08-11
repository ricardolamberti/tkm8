<?xml version="1.0"?>



<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="page.view.components.xsl" />

	<xsl:strip-space elements="*" />

	<!-- entry point template -->
	<xsl:template name="render_view">
		<!-- hidden form to only send requests without including the values
			of the input fields which are in the mainform -->
		<form name="navform" id="navform" METHOD="post">
			<xsl:call-template name="generate_basic_form" />
		</form>
		<!-- view holder form; it will contain all the components rendered
			for the view -->
		<form name="mainform" id="mainform" METHOD="post">
			<xsl:call-template name="generate_basic_form" />
			<DIV>
				<xsl:call-template name="basic_generate_component" />
			</DIV>
			<!-- script>setupCalendarEditors();</script-->
			<script>
			 firstFocus();
			 executeStartedScripts();
			</script>

		</form>
	</xsl:template>


	<!--
		*********************************************************************
		INTERNAL UTILITY TEMPLATES
		*********************************************************************
	-->

	<!-- template to render a view in the page -->
	<xsl:template name="render_view_area">
			<!-- the title -->
		<xsl:if test="navigation_bar">
				<div>
					<xsl:attribute name="style">
						<xsl:value-of select="../header/layouts/layout[@id=$page_stereotype]/view[@id='navigation_bar']/@style" />
					</xsl:attribute>
					<xsl:if test="navigation_bar">
						<xsl:for-each select="navigation_bar">
							<xsl:call-template name="basic_generate_composite" />
						</xsl:for-each>
					</xsl:if>
				</div>
				<div class="navigation_bar_patron">
					<xsl:attribute name="style">
						<xsl:value-of select="../header/layouts/layout[@id=$page_stereotype]/view[@id='navigation_bar_patron']/@style" />
					</xsl:attribute>
				</div>
		</xsl:if>
		<xsl:if test="history_bar">
				<div>
					<xsl:attribute name="style">
						<xsl:value-of select="../header/layouts/layout[@id=$page_stereotype]/view[@id='history_bar']/@style" />
					</xsl:attribute>
<!-- 					<xsl:if test="history_bar"> -->
						<xsl:for-each select="history_bar">
							<xsl:call-template name="basic_generate_composite" />
						</xsl:for-each>
<!-- 					</xsl:if> -->
				</div>

		</xsl:if>
		<xsl:if test="@title">
			<div id="view_title" class="view_title">
				
				<xsl:attribute name="style">
					<xsl:value-of select="../header/layouts/layout[@id=$page_stereotype]/view[@id='view_title']/@style" />
				</xsl:attribute>
				<span>
				<xsl:if test="icon">
					<xsl:call-template name="render_icon" />
					<div style="display:inline;width:5px;padding: 0 0 0 10px" />
				</xsl:if>
				<xsl:value-of select="@title" />
				</span>
			</div>
		</xsl:if>
		<div id="view_area" onscroll="renderHelp();" class="view_area">
				<xsl:attribute name="style">
					<xsl:value-of select="../header/layouts/layout[@id=$page_stereotype]/view[@id='view_area']/@style" />
				</xsl:attribute>
			<xsl:text disable-output-escaping="yes">
				<![CDATA[&nbsp;]]>
			</xsl:text>
			<xsl:call-template name="render_view" />
		</div>
	</xsl:template>
	<xsl:template match="page/message" >
		<div id="urgent_message" name="urgent_message" class="urgent_message">
			<div style="position:absolute; left:90px;top:5px;"><xsl:value-of select="urgent_title" disable-output-escaping="yes"/></div>
			<div style="position:absolute; left:90px;top:30px;"><xsl:value-of select="urgent_message" disable-output-escaping="yes"/></div>
			<xsl:if test="action">
		    <a style="position:absolute; left:5px;top:5px;width:40px;height:33px;" class="urgent_close"  href="#">  
					<xsl:call-template name="generate_action_on_click"/> 
				</a>
			</xsl:if>
			<xsl:if test="not(action) and @urgent_permanent!='true'">
				<a style="position:absolute; left:5px;top:5px;width:40px;height:33px;" class="urgent_close"  href="#">  
					<xsl:attribute name="onclick"> 
						$('#urgent_message').hide();
					</xsl:attribute>
				</a>
			</xsl:if>
		</div>

	</xsl:template>

</xsl:stylesheet> 