<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="responsive_page.view.components.xsl" />

	<xsl:strip-space elements="*" />

	<!-- entry point template -->
	<xsl:template name="render_view">
	

		<form name="navform" id="navform" METHOD="post">
			<xsl:call-template name="generate_basic_form" />
		</form>
		<form name="mainform" id="mainform" METHOD="post" autocomplete="off">
			<xsl:call-template name="generate_basic_form" />

			<div id="row" onscroll="renderHelp();" >
				<xsl:choose>
					<xsl:when test="@help">
						<DIV class="main_canvas col-sm-9">
							<xsl:call-template name="basic_generate_component_responsive" />
						</DIV>
						<DIV data-html2canvas-ignore="true" class="col-sm-3">
							<iframe style="height:100%;width:100%;">
								<xsl:attribute name="id">help_session</xsl:attribute>
								<xsl:attribute name="src">
									about:blank
								</xsl:attribute>
							</iframe>
							<script>document.getElementById('help_session').src = goTo(this,'<xsl:value-of select="@help"/>', false, '', null, 'ONLY_URL', '', '', true, true);</script>
						</DIV>
					</xsl:when>
					<xsl:otherwise>
						<DIV class="main_canvas">
							<xsl:call-template name="basic_generate_component_responsive" />
						</DIV>
					</xsl:otherwise>
				</xsl:choose>
			</div>

			<script>
			 adaptativeScroll('','','body','<xsl:value-of select="@scroll"/>','');
			 firstFocus('<xsl:value-of select="@scroll"/>');
			 analizeTable('<xsl:value-of select="@scroll"/>');
			 analizeModal('<xsl:value-of select="@scroll"/>');
			 executeStartedScripts();

			</script>
		</form>
	</xsl:template>
	

	
	<xsl:template name="render_message" >
		<div >
		 <xsl:attribute name="class">alert<![CDATA[ ]]> 
		 	<xsl:choose>
		 		<xsl:when test="@urgent_type='warning'">
					 alert-warning<![CDATA[ ]]>
		 		</xsl:when>
		 		<xsl:when test="@urgent_type='success'">
					 alert-success<![CDATA[ ]]>
		 		</xsl:when>
		 		<xsl:when test="@urgent_type='danger'">
					 alert-danger<![CDATA[ ]]>
		 		</xsl:when>
		 		<xsl:when test="@urgent_type='info'">
					 alert-info<![CDATA[ ]]>
		 		</xsl:when>
		 		<xsl:otherwise>
					 alert-warning<![CDATA[ ]]>
		 		</xsl:otherwise>
		 	</xsl:choose>
			  alert-dismissable</xsl:attribute>
			  <xsl:if test="action">
		 		 <button type="button" class="close" data-dismiss="alert">
		 		 	<xsl:call-template name="generate_action_on_click"/>
		 		 	<i><xsl:attribute name="class">fa fa-times</xsl:attribute></i>
		 		 </button>
	 		 </xsl:if>
	 		 <xsl:if test="not(action) and @urgent_permanent!='true'">
				<button type="button" class="close" data-dismiss="alert">  
		 		 	<i><xsl:attribute name="class">fa fa-times</xsl:attribute></i>
				</button>
			</xsl:if>
			<strong><xsl:value-of select="urgent_title"  disable-output-escaping="yes"/> - </strong> <xsl:value-of select="urgent_message" disable-output-escaping="yes"/>
    	</div>

	</xsl:template>
	
	
	<!--
		*********************************************************************
		INTERNAL UTILITY TEMPLATES
		*********************************************************************
	-->

	<!-- template to render a view in the page -->
	<xsl:template name="render_view_area">
			<xsl:if test="jumbotron">
				<xsl:for-each select="jumbotron">
				  <div>
						<xsl:call-template name="basic_generate_component_responsive" />
				  </div>
				  </xsl:for-each>
			</xsl:if>
			<xsl:if test="navigation_bar_complex">
				<xsl:for-each select="navigation_bar_complex">
				  <nav style="margin-bottom: 0;">
					<xsl:call-template name="basic_generate_component_responsive" />
			      </nav>
	      		</xsl:for-each>
			</xsl:if> 
		<div>
				<xsl:if test="not(navigation_bar_complex)">
					<xsl:choose>
						<xsl:when test="/page/header/@stereotype='app_indoors'">
							<xsl:attribute name="id">page-wrapper</xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="id">container</xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:if>
				<xsl:if test="navigation_bar_complex">
						<xsl:attribute name="id"><xsl:value-of select="navigation_bar_complex/@container_class"/></xsl:attribute>
				</xsl:if>
		
			<div>
				<xsl:attribute name="id">view_area_and_title</xsl:attribute>
					<xsl:for-each select="../message">
				 		<xsl:call-template name="render_message"/>
					</xsl:for-each>
					<xsl:call-template name="render_view" />
			</div>
			<div id="waitpane"></div>
			<div id="modal-zone"></div>
			<div id="modal-zone-confirm"></div>
			
		</div>

	</xsl:template>
</xsl:stylesheet> 