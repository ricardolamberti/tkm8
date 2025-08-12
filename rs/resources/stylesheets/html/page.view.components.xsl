<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:variable name="skin_path" select="page/header/layouts/@base_path" />
<xsl:variable name="url_prefix" select="page/header/layouts/@url_prefix" />


<xsl:include href="utils.3d.xsl"/>
<xsl:include href="utils.actions.xsl"/>
<xsl:include href="utils.rendering.xsl"/>

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

<xsl:template match="split">
	<div>
	<xsl:call-template name="basic_generate_component"/>
	<xsl:for-each select="panel_a/panel">
		<xsl:call-template name="basic_generate_composite"/>
	</xsl:for-each>
	<xsl:for-each select="panel_b/panel">
		<xsl:call-template name="basic_generate_composite"/>
	</xsl:for-each>
	</div>
</xsl:template>

<xsl:template match="panel">
	<xsl:call-template name="basic_generate_composite"/>
</xsl:template>

<xsl:template match="win_list_canvas">
	<xsl:call-template name="basic_generate_composite"/>
</xsl:template>

<xsl:template match="datetime_component">
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	<DIV>
		<xsl:call-template name="basic_generate_component"/>
		<INPUT type="hidden">
			<xsl:attribute name="id"><xsl:value-of select="$fullname"/></xsl:attribute>
			<xsl:attribute name="name"><xsl:value-of select="$fullname"/></xsl:attribute>
		</INPUT>
	</DIV>
	<xsl:call-template name="basic_generate_form_component_scripts"/>
</xsl:template>

<xsl:template match="form">
	<DIV>
		<xsl:call-template name="basic_generate_component">
			<xsl:with-param name="isForm" select="'true'" />
		</xsl:call-template>
	</DIV>
</xsl:template>

<xsl:template match="notebook">
	<xsl:call-template name="basic_generate_composite"/>
	<xsl:for-each select="panel/notebook_tab[@state='selected']">
		<xsl:for-each select="action">
			<script>
				storeAjaxNotebook('<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="@id"/>');
			</script>
		</xsl:for-each>
	</xsl:for-each>
</xsl:template>


<!-- 
	******** action widgets **********
	they are components which launch requests
-->

<xsl:template match="link">
		<div>
			<xsl:if test="/page/header/savehelp">			
			    <xsl:attribute name="oncontextmenu">
			         return goToSaveHelp('<xsl:value-of select="@name"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',2);
				</xsl:attribute>
			</xsl:if>	
			<xsl:attribute name="id"><xsl:value-of select="@name"/></xsl:attribute>
			<xsl:if test="not(@key='')"><xsl:attribute name="accesskey"><xsl:value-of select="@key"/></xsl:attribute></xsl:if>
			<xsl:attribute name="style">
				<xsl:call-template name="generate_inline_style"/>
			</xsl:attribute>
			
			<a>
			    <xsl:attribute name="id"><xsl:value-of select="@name"/></xsl:attribute>
				<xsl:if test="@opens_new_window and @opens_new_window='true'">
					<xsl:attribute name="target">_blank</xsl:attribute>
				</xsl:if>
				<xsl:call-template name="basic_generate_action_view" />
	 		</a>
		</div>
	

		<xsl:if test="@is_submit='true'">
		<script>
			setAnchorSubmit('<xsl:value-of select="@name"/>');
		</script>
		</xsl:if>
		<xsl:if test="@is_cancel='true'">
		<script>
			setAnchorCancel('<xsl:value-of select="@name"/>');
		</script>
		</xsl:if>
		<xsl:if test="@functionKey">
		<script>
			setAnchorF(<xsl:value-of select="@functionKey"/>,'<xsl:value-of select="@name"/>');
		</script>
		</xsl:if>
</xsl:template>

<xsl:template match="navigation_group">
	 <li>
    	<xsl:if test="@data_toggle">
		  	<xsl:attribute name="class">dropdown</xsl:attribute>
    	</xsl:if>
	    <a href="#">
	    	<xsl:if test="@data_toggle">
			  	<xsl:attribute name="class">dropdown-toggle</xsl:attribute>
			  	<xsl:attribute name="data-toggle"><xsl:value-of select="@data_toggle"/></xsl:attribute>
	    	</xsl:if>
            <i>
  	          <xsl:attribute name="class">fa fa-fw <xsl:value-of select="icon/@file"/></xsl:attribute>
            </i> 
			<xsl:if test="@title"><xsl:value-of select="@title"/></xsl:if>
			<xsl:if test="arrow/icon">
		    	<xsl:if test="@data_toggle">
		            <i>
				    	<xsl:attribute name="class">fa <xsl:value-of select="arrow/icon/@file"/></xsl:attribute>
		           </i>
		    	</xsl:if>
		    	<xsl:if test="not(@data_toggle)">
		            <span>
				    	<xsl:attribute name="class">fa <xsl:value-of select="arrow/icon/@file"/></xsl:attribute>
		           </span>
		    	</xsl:if>
			</xsl:if>
        </a>
        <ul>
		  	<xsl:if test="@class_responsive"><xsl:attribute name="class"><xsl:value-of select="@class_responsive"/></xsl:attribute></xsl:if>
		  	<xsl:if test="@role"><xsl:attribute name="role"><xsl:value-of select="@role"/></xsl:attribute></xsl:if>
			<xsl:call-template name="basic_generate_component"/>
        </ul>
     </li>
</xsl:template>

<xsl:template match="navigation_group_sidebar">
           <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul>
					  	<xsl:if test="@class_responsive"><xsl:attribute name="class"><xsl:value-of select="@class_responsive"/></xsl:attribute></xsl:if>
					  	<xsl:if test="@role"><xsl:attribute name="role"><xsl:value-of select="@role"/></xsl:attribute></xsl:if>
						<xsl:call-template name="basic_generate_component"/>
 					</ul>
                </div>
            </div>
</xsl:template>

<xsl:template match="ol">
	<ol>
		<xsl:call-template name="basic_generate_component"/>
	</ol>
</xsl:template>

<xsl:template match="ul">
	<ul>
		<xsl:call-template name="basic_generate_component"/>
	</ul>
</xsl:template>

<xsl:template match="li">
	<li>
		<xsl:call-template name="basic_generate_component"/>
	</li>
</xsl:template>


<xsl:template match="notebook_tab">
		<div>
			<xsl:attribute name="id">
				<xsl:value-of select="@name"/>
			</xsl:attribute>
			
			<xsl:if test="/page/header/savehelp">			
			    <xsl:attribute name="oncontextmenu">
			         return goToSaveHelp('<xsl:value-of select="@name"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',2);
				</xsl:attribute>
			</xsl:if>
			<xsl:attribute name="style">
				<xsl:call-template name="generate_inline_style"/>
			</xsl:attribute>
			<a>
				<xsl:call-template name="basic_generate_action_view" />
			</a>
		</div>
</xsl:template>

<!--
	******** fixed components **********
	they are components which just show text or graphics
-->

<xsl:template match="break">
<xsl:if test="not(@appled_by_layout='true')"><br></br></xsl:if>
</xsl:template>

<xsl:template match="chart">
	<img>
		<xsl:attribute name="src"><xsl:value-of select="@chart_image_path"/></xsl:attribute>
		<xsl:call-template name="basic_generate_component"/>
	</img>
</xsl:template>


<xsl:template match="drawing_chart">
	<div>
		<xsl:call-template name="basic_generate_component">
			<xsl:with-param name="style_attr">overflow:auto;</xsl:with-param>
		</xsl:call-template>
		<img>
			<xsl:attribute name="src"><xsl:value-of select="@chart_image_path"/></xsl:attribute>
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
		<xsl:call-template name="basic_generate_component" />
         <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
             <span class="sr-only">Toggle navigation</span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
         </button>
    </div>
</xsl:template>

<xsl:template match="navigation_bar_complex">
	  <nav style="margin-bottom: 0;font-size:16;">
		<xsl:call-template name="basic_generate_component" />
      </nav>
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
					<xsl:attribute name="href"><xsl:value-of select="image/@link"/></xsl:attribute>
					<xsl:call-template name="basic_generate_component"/>
					<xsl:call-template name="basic_render_image"/>
				</a>
			</xsl:when>
			<xsl:when test="image/@expand">
			
				<a>
					<xsl:choose>
						<xsl:when test="image/@source='pss_icon'"> <!-- pss images path icon -->
							<xsl:attribute name="href">pss_icon<xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="image/@file"/></xsl:attribute>
						</xsl:when>
						<xsl:when test="image/@source='webapp_url'"> <!-- webapp relative path icon -->
							<xsl:attribute name="href"><xsl:value-of select="image/@file"/></xsl:attribute>
						</xsl:when>
						<xsl:when test="image/@source='pss_data'"> <!-- webapp relative path icon -->
							<xsl:attribute name="href">pss_data<xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="image/@file"/></xsl:attribute>
						</xsl:when>
						<xsl:when test="image/@source='pssdata_resource'"> <!-- webapp relative path icon -->
							<xsl:attribute name="href">pssdata_resource<xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="image/@file"/></xsl:attribute>
						</xsl:when>
						<xsl:otherwise> <!-- an icon which came from the skin -->
							<xsl:attribute name="href"><xsl:value-of select="$skin_path"/><xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="image/@file"/></xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
					<xsl:attribute name="onclick">return hs.expand(this)</xsl:attribute>
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

<xsl:template match="flash">
	<DIV>
		<xsl:call-template name="basic_generate_component"/>
		<xsl:call-template name="basic_render_flash"/>
	</DIV>
</xsl:template>

<xsl:template match="label">
	<DIV>
		<xsl:call-template name="basic_generate_component"/>
		<xsl:call-template name="render_compound_label"/>
	</DIV>
</xsl:template>

<xsl:template match="win_action_bar">

	<DIV>
		<xsl:call-template name="basic_generate_component" />
	</DIV>

	
</xsl:template>



<xsl:template match="win_list">

	<DIV>
		<xsl:call-template name="basic_generate_component">
			<xsl:with-param name="style_attr">overflow:auto;</xsl:with-param>
		</xsl:call-template>
		<xsl:for-each select="alert">
			<xsl:if test="@msg">
			    <script type="text/javascript">
			    	 alert('<xsl:value-of select="@msg"/>'); 
			   	</script>
			</xsl:if>
		</xsl:for-each>
		<xsl:if test="@scroll">
			<script type="text/javascript">
				adaptativeScroll('scroll_body','scroll_header','<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="@scroll"/>','');
			</script>
		<xsl:call-template name="win_list_table" />
		</xsl:if>
	</DIV>

</xsl:template>

<xsl:template match="win_grid">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
		<DIV>
		<xsl:call-template name="basic_generate_component">
			<xsl:with-param name="style_attr">overflow:auto;</xsl:with-param>
		</xsl:call-template>
	
		
		<xsl:for-each select="alert">
			<xsl:if test="@msg">
			    <script type="text/javascript">
			    	 alert('<xsl:value-of select="@msg"/>'); 
			   	</script>
			</xsl:if>
		</xsl:for-each>
		<xsl:if test="@scroll">
			<script type="text/javascript">
				adaptativeScroll('scroll_body','scroll_header','<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="@scroll"/>','');
			</script>
		<xsl:call-template name="win_grid_table" />
		</xsl:if>
	</DIV>

</xsl:template>

<xsl:template name="win_list_table">
	    <xsl:param name="level" select="'_base'" />
	    <xsl:param name="jsorden">
			<xsl:if test="count(rows/row) &lt; 50">true</xsl:if>
			<xsl:if test="not(count(rows/row) &lt; 50)">false</xsl:if>
	    </xsl:param>
	    <script TYPE="POSTSCRIPT">
			<xsl:for-each select="showAction">
				<xsl:for-each select="action">
						<xsl:call-template name="generate_action_invokation"/>
				</xsl:for-each>
			</xsl:for-each>
	    </script>
		<table class="win_list_table" border="0" cellpadding="0" cellspacing="0"  tabindex="0">
			<xsl:attribute name="id"><xsl:value-of select="@obj_provider"/>_table</xsl:attribute>
			<xsl:if test="$level='_base'">
				<xsl:attribute name="onKeyDown">return rsKeyPressTable(this,event);</xsl:attribute>
			</xsl:if>
			<xsl:if test="not($level='_base')">
				<xsl:attribute name="width">100%</xsl:attribute>
			</xsl:if>
			<!-- render the header -->
			<THEAD id="scroll_header">
			<xsl:if test="@with_group='true'">
				<xsl:for-each select="level_column">
					<tr>
						<xsl:attribute name="class">zwlth<xsl:if test="not ($level='_base')"><xsl:value-of select="$level"/></xsl:if></xsl:attribute>
						<xsl:for-each select="grupo_column">
						<th>
							<xsl:attribute name="class">zwlthc<xsl:if test="not ($level='_base')"><xsl:value-of select="$level"/></xsl:if></xsl:attribute>
							<xsl:attribute name="colspan">0<xsl:value-of select="@span"/></xsl:attribute>
							<xsl:if test="@foreground or @background">
								<xsl:attribute name="style">
									<xsl:if test="@foreground">color:#<xsl:value-of select="@foreground"/>;</xsl:if>
									<xsl:if test="@background">background-color:#<xsl:value-of select="@background"/>;</xsl:if>
								</xsl:attribute>
							</xsl:if>
							<xsl:call-template name="dragdrop_component"/>
							<xsl:choose> 
								<xsl:when test="not(title/.='SINGRUPOCOLUMNA')">
									<xsl:value-of select="title/."/>
								</xsl:when>
								<xsl:otherwise></xsl:otherwise>
							</xsl:choose>
						</th>
						</xsl:for-each>
						<th class="win_list_bg_header">
							<xsl:attribute name="width">
								<xsl:if test="/page/header/layouts/layout[@id='win_list_table']/@width">
									<xsl:value-of select="/page/header/layouts/layout[@id='win_list_table']/@width"/>
								</xsl:if>
								<xsl:if test="not(/page/header/layouts/layout[@id='win_list_table']/@width)">
									5px
								</xsl:if>
							</xsl:attribute>
						   <xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						</th>
						
					</tr>
				</xsl:for-each>
			</xsl:if>
			<tr>
				<xsl:attribute name="class">zwlth<xsl:if test="not ($level='_base')"><xsl:value-of select="$level"/></xsl:if></xsl:attribute>
				<xsl:for-each select="header">
					<xsl:if test="@has_subdetail='true'">
						<th>
							<xsl:attribute name="width">
								<xsl:if test="/page/header/layouts/layout[@id='win_list_table']/@width">
									<xsl:value-of select="/page/header/layouts/layout[@id='win_list_table']/@width"/>
								</xsl:if>
								<xsl:if test="not(/page/header/layouts/layout[@id='win_list_table']/@width)">5px</xsl:if>
							</xsl:attribute>
							<xsl:attribute name="class">zwlthc<xsl:if test="not ($level='_base')"><xsl:value-of select="$level"/></xsl:if></xsl:attribute>
							<div>
								<xsl:attribute name="onclick">
									hide_show_expandall_subdetail();
								</xsl:attribute>
								<xsl:call-template name="render_icon"/>
							</div>
						</th>
					</xsl:if>
					<xsl:for-each select="column">
						<th align="center">
							<xsl:attribute name="class">zwlthc<xsl:if test="not ($level='_base')"><xsl:value-of select="$level"/></xsl:if></xsl:attribute>
							<xsl:if test="@width"><xsl:attribute name="width"><xsl:value-of select="@width"/></xsl:attribute></xsl:if>
							<xsl:if test="@foreground or @background">
								<xsl:attribute name="style">
									<xsl:if test="@foreground">color:#<xsl:value-of select="@foreground"/>;</xsl:if>
									<xsl:if test="@background">background-color:#<xsl:value-of select="@background"/>;</xsl:if>
								</xsl:attribute>
							</xsl:if>
							<xsl:call-template name="dragdrop_component"/>
				
							<xsl:if test="$level='_base'">
								<xsl:if test="$jsorden='true' and not(title/.='-') and not(dblclickDrag)">
									<xsl:attribute name="onClick">hsc(this);</xsl:attribute>
								</xsl:if>
								<xsl:if test="$jsorden='false' or title/.='-'">
									<xsl:call-template name="generate_action_on_click" />
								</xsl:if>
							</xsl:if>
							<span>
							<xsl:if test="not(hover/.='')">
							    <xsl:attribute name="title"><xsl:value-of select="hover/."/> </xsl:attribute>
							</xsl:if>
							<xsl:choose>
								<xsl:when test="html_data and not(html_data/.='')">
									<xsl:value-of select="html_data/."  disable-output-escaping="yes"/>
								</xsl:when>
								<xsl:when test="not(title/.='')">
									<xsl:value-of select="title/."/>
								</xsl:when>
								<xsl:otherwise>-</xsl:otherwise>
							</xsl:choose>
							</span>
						</th>
					</xsl:for-each>
					<xsl:if test="$level='_base'">
						<th class="win_list_bg_header">
							<xsl:attribute name="width">
								<xsl:if test="/page/header/layouts/layout[@id='win_list_table']/@width">
									<xsl:value-of select="/page/header/layouts/layout[@id='win_list_table']/@width"/>
								</xsl:if>
								<xsl:if test="not(/page/header/layouts/layout[@id='win_list_table']/@width)">
									5px
								</xsl:if>
							</xsl:attribute>
						   <xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						</th>
					</xsl:if>
				</xsl:for-each>
			</tr>
			
			</THEAD>
			<TBODY id="scroll_body">
			<!--xsl:attribute name="style">
				overflow-y:auto;
			</xsl:attribute-->
			<!-- render the rows -->
			<xsl:for-each select="rows/row">
				<xsl:variable name="name_row">row_<xsl:value-of select="position()"/></xsl:variable>
				<tr tabindex="0">
					<xsl:if test="../../@is_line_select='true'">
						<xsl:for-each select="dblclick">
								<xsl:call-template name="generate_action_on_double_click">
									<xsl:with-param name="row_id"><xsl:value-of select="../@zobject"/></xsl:with-param>
									 <xsl:with-param name="key"><xsl:value-of select="../@key"/></xsl:with-param>
									<xsl:with-param name="descr"><xsl:value-of select="../@descr"/></xsl:with-param>
								</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="../../@is_line_select='true'">
						<xsl:for-each select="rightclick">
							<xsl:call-template name="generate_action_on_right_click"/>
						</xsl:for-each>
					</xsl:if>					
      				<xsl:if test="@rowpos mod 2 = 0"><xsl:attribute name="class">zwltr</xsl:attribute></xsl:if>
      				<xsl:if test="@rowpos mod 2 = 1"><xsl:attribute name="class">zwltr2</xsl:attribute></xsl:if>
					<xsl:if test="@zobject"><xsl:attribute name="id"><xsl:value-of select="@zobject"/></xsl:attribute></xsl:if>
					<xsl:if test="@key"><xsl:attribute name="title"><xsl:value-of select="@key"/>;<xsl:value-of select="@descr"/></xsl:attribute></xsl:if>
					<xsl:if test="$level='_base'">
						<xsl:call-template name="dragdrop_component"/>
						<xsl:if test="../../@has_action_bar='true'">
						  <xsl:if test="smplclick">
								<xsl:choose>
									<xsl:when test="@key">
										<xsl:attribute name="onclick">
											rsKey(this,'<xsl:value-of select="@key"/>','<xsl:value-of select="@descr"/>',event);
											runClick(this,event,function(){
												<xsl:for-each select="smplclick">
													<xsl:for-each select="action">
															<xsl:call-template name="generate_action_invokation"/>
													</xsl:for-each>
												</xsl:for-each>
											});
										</xsl:attribute>
									</xsl:when>
									<xsl:otherwise>
										<xsl:if test="../../@is_line_select='true'">
											<xsl:attribute name="onclick">
												rs(this,event);
											    runClick(this,event,function(){
													<xsl:for-each select="smplclick">
														<xsl:for-each select="action">
																<xsl:call-template name="generate_action_invokation"/>
														</xsl:for-each>
													</xsl:for-each>
												});
											</xsl:attribute>
										</xsl:if>
									</xsl:otherwise>
								</xsl:choose>
						  </xsl:if>


						
						</xsl:if>
					</xsl:if>
					<xsl:if test="../../header/@has_subdetail='true'">
						<td>
							<xsl:call-template name="set_class" />
							<xsl:if test="details"> 
								<div>
									<xsl:attribute name="onclick">
										hide_show_subdetail("<xsl:value-of select="$name_row"/>");
									</xsl:attribute>
									<xsl:for-each select="details">
										<xsl:call-template name="render_icon"/>
									</xsl:for-each>
								</div>
								<script>register_subdetail("<xsl:value-of select="$name_row"/>")</script>
							</xsl:if>
						</td>
					</xsl:if>
					<xsl:if test="dblclick and icon">
						<xsl:variable name="halignment"><xsl:value-of select="../../header/column[0]/@halignment"/></xsl:variable>
						<td>
							<xsl:call-template name="set_class" />
							<xsl:attribute name="align"><xsl:value-of select="$halignment"/></xsl:attribute>
							<a href="#">
								<xsl:if test="@tooltip">
									<xsl:attribute name="title"><xsl:value-of select="@tooltip"/></xsl:attribute>
								</xsl:if>								
							
								<xsl:for-each select="dblclick">
									<xsl:call-template name="generate_action_on_click_forced">
										<xsl:with-param name="row_id"><xsl:value-of select="../@zobject"/></xsl:with-param>
										<xsl:with-param name="key"><xsl:value-of select="../@key"/></xsl:with-param>
										<xsl:with-param name="descr"><xsl:value-of select="../@descr"/></xsl:with-param>
									</xsl:call-template>
								</xsl:for-each>
								<xsl:call-template name="render_icon"/>
							</a>								
							
						</td>
					</xsl:if>
					<xsl:if test="icon and not(dblclick)">
						<xsl:variable name="halignment"><xsl:value-of select="../../header/column[0]/@halignment"/></xsl:variable>
						<td>
							<xsl:call-template name="set_class" />
							<xsl:attribute name="align"><xsl:value-of select="$halignment"/></xsl:attribute>
							<xsl:call-template name="render_icon"/>
						</td>
					</xsl:if>

					<xsl:for-each select="d">
						<xsl:if test="not(@visible) or @visible='true'">
							<xsl:if test="not(@skip) or not(@skip='true')">
								<xsl:variable name="thispos" />
								<xsl:choose>
									<xsl:when test="../icon">
										<xsl:call-template name="generate_win_list_cell">
											<xsl:with-param name="thispos"><xsl:value-of select="position()+1"/></xsl:with-param>
										</xsl:call-template>	
									</xsl:when>
									<xsl:otherwise>
										<xsl:call-template name="generate_win_list_cell">
											<xsl:with-param name="thispos"><xsl:value-of select="position()"/></xsl:with-param>
										</xsl:call-template>	
									</xsl:otherwise>
								</xsl:choose>
							</xsl:if>
						</xsl:if>
					</xsl:for-each>
					<xsl:if test="$level='_base'">
						<td class="win_list_background">
							<xsl:attribute name="width">
								<xsl:if test="/page/header/layouts/layout[@id='win_list_table']/@width">
									<xsl:value-of select="/page/header/layouts/layout[@id='win_list_table']/@width"/>
								</xsl:if>
								<xsl:if test="not(/page/header/layouts/layout[@id='win_list_table']/@width)">5px</xsl:if>
							</xsl:attribute>
						
						<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						</td>
					</xsl:if>
				</tr>
				<xsl:for-each select="details"> 
					<tr>
	      				<xsl:attribute name="name"><xsl:value-of select="$name_row"/></xsl:attribute>
	      				<xsl:attribute name="id"><xsl:value-of select="$name_row"/></xsl:attribute>
	      				<xsl:attribute name="style">display:none;</xsl:attribute>
	      				<xsl:if test="@rowpos mod 2 = 0"><xsl:attribute name="class">zwltr</xsl:attribute></xsl:if>
    	  				<xsl:if test="@rowpos mod 2 = 1"><xsl:attribute name="class">zwltr2</xsl:attribute></xsl:if>
						<td>
							<xsl:call-template name="set_class" />
							<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						</td>
						<td>
							<xsl:if test="@axis"><xsl:attribute name="axis"><xsl:value-of select="@axis"/></xsl:attribute></xsl:if>
							<xsl:attribute name="colspan"><xsl:value-of select="@colspan"/></xsl:attribute>
							<xsl:call-template name="set_class" />
							<xsl:call-template name="win_list_table" >
								<xsl:with-param name="level">_sub</xsl:with-param>
							</xsl:call-template>
						</td>
-						<xsl:if test="$level='_base'">
							<td class="win_list_background">
								<xsl:attribute name="width">
									<xsl:if test="/page/header/layouts/layout[@id='win_list_table']/@width">
										<xsl:value-of select="/page/header/layouts/layout[@id='win_list_table']/@width"/>
									</xsl:if>
									<xsl:if test="not(/page/header/layouts/layout[@id='win_list_table']/@width)">5px</xsl:if>
								</xsl:attribute>
								
								<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
							</td>
						</xsl:if>
					</tr>
				</xsl:for-each>
			</xsl:for-each>
			</TBODY>

			<xsl:if test="footer">
				<TFOOT id="scroll_foot">
				<tr class="zwlth">
					<xsl:attribute name="class">zwlth<xsl:if test="not ($level='_base')"><xsl:value-of select="$level"/></xsl:if></xsl:attribute>
					<xsl:if test="../../header/@has_subdetail='true'">
						<td>
							<xsl:attribute name="width">
								<xsl:if test="/page/header/layouts/layout[@id='win_list_table']/@width">
									<xsl:value-of select="/page/header/layouts/layout[@id='win_list_table']/@width"/>
								</xsl:if>
								<xsl:if test="not(/page/header/layouts/layout[@id='win_list_table']/@width)">5px</xsl:if>
							</xsl:attribute>
							<xsl:attribute name="class">zwlthc<xsl:if test="not ($level='_base')"><xsl:value-of select="$level"/></xsl:if></xsl:attribute>
							<xsl:if test="@foreground or @background">
								<xsl:attribute name="style">
									<xsl:if test="@foreground">color:#<xsl:value-of select="@foreground"/>;</xsl:if>
									<xsl:if test="@background">background-color:#<xsl:value-of select="@background"/>;</xsl:if>
								</xsl:attribute>
							</xsl:if>
							<xsl:call-template name="dragdrop_component"/>
						   <xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						</td>
					</xsl:if>
					<xsl:for-each select="footer/column">
						<td>
							<xsl:attribute name="class">zwlthc<xsl:if test="not ($level='_base')"><xsl:value-of select="$level"/></xsl:if></xsl:attribute>
							<xsl:if test="$level='_base'">
								<xsl:attribute name="style">
									text-align:<xsl:value-of select="@halignment"/>;
									<xsl:if test="@foreground or @background">
										<xsl:if test="@foreground">color:#<xsl:value-of select="@foreground"/>;</xsl:if>
										<xsl:if test="@background">background-color:#<xsl:value-of select="@background"/>;</xsl:if>
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
									<xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></xsl:otherwise>
								</xsl:choose>
							</xsl:if>
						</td>
					</xsl:for-each>
					<xsl:if test="$level='_base'">
						<td class="win_list_background">
							<xsl:attribute name="width">
								<xsl:if test="/page/header/layouts/layout[@id='win_list_table']/@width">
									<xsl:value-of select="/page/header/layouts/layout[@id='win_list_table']/@width"/>
								</xsl:if>
								<xsl:if test="not(/page/header/layouts/layout[@id='win_list_table']/@width)">5px</xsl:if>
							</xsl:attribute>
						
						   <xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						</td>
					</xsl:if>
				</tr>
				</TFOOT>
			</xsl:if>
		</table>
		<xsl:if test="$level='_base'">
			<xsl:if test="@has_action_bar='true'">
			   <xsl:variable name="v_provider"><xsl:value-of select="@obj_provider"/></xsl:variable>
			   <script>
				AAInit();
				<xsl:for-each select="//win_action_bar[@provider=$v_provider]/action">
					AAAdd('<xsl:value-of select="@id"/>',<xsl:value-of select="@win"/>,[<xsl:for-each select="allowed_win">'<xsl:value-of select="@id"/>',</xsl:for-each>null],<xsl:value-of select="@multiple"/>);
				</xsl:for-each>
				AAFin('<xsl:value-of select="@obj_provider"/>_table','<xsl:value-of select="//win_action_bar[@provider=$v_provider]/@name"/>','<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="rows/@zobject"/>',<xsl:value-of select="@is_multiple_select"/>,<xsl:value-of select="@is_line_select"/>,'<xsl:value-of select="select_info/@has_more_selection"/>',false,null);
			   </script>
			   <xsl:for-each select="rows/row[(@selected) and (@selected='true')]">
						<script TYPE="POSTSCRIPT">
							rs_forced2(document.getElementById('<xsl:value-of select="@zobject"/>'),null);
				   		</script>
				</xsl:for-each>
			   <xsl:for-each select="rows/row[(@selected) and (@selected='true')][1]">
						<script TYPE="POSTSCRIPT">
							<xsl:for-each select="smplclick">
								<xsl:for-each select="action">
									<xsl:call-template name="generate_action_invokation"/>
								</xsl:for-each>
							</xsl:for-each>
				   		</script>
				</xsl:for-each>
			</xsl:if>
		</xsl:if>
</xsl:template>

<xsl:template name="win_grid_table">
		<table class="win_list_table" border="0" cellpadding="0" cellspacing="0"  tabindex="0">
			<xsl:attribute name="id"><xsl:value-of select="@obj_provider"/>_table</xsl:attribute>
			<xsl:if test="@editable='true'"><xsl:attribute name="onKeyDown">return rsKeyPressGrid(this,event);</xsl:attribute></xsl:if>
			<xsl:if test="not(@editable='true')"><xsl:attribute name="onKeyDown">return rsKeyPressTable(this,event);</xsl:attribute></xsl:if>
			<xsl:attribute name="width">100%</xsl:attribute>
			<!-- render the header -->
			<THEAD id="scroll_header">
			<tr>
				<xsl:attribute name="class">zwlth</xsl:attribute>
				<xsl:for-each select="header">
					<xsl:for-each select="column">
						<th align="center">
							<xsl:attribute name="class">zwlthc</xsl:attribute>
							<xsl:if test="@width"><xsl:attribute name="width"><xsl:value-of select="@width"/></xsl:attribute></xsl:if>
							<span>
							<xsl:if test="not(hover/.='')">
							<xsl:attribute name="title"><xsl:value-of select="hover/."/> </xsl:attribute>
							</xsl:if>
							<xsl:choose>
								<xsl:when test="html_data and not(html_data/.='')">
									<xsl:value-of select="html_data/."  disable-output-escaping="yes"/>
								</xsl:when>
								<xsl:when test="not(title/.='')">
									<xsl:value-of select="title/."/>
								</xsl:when>
								<xsl:otherwise>-</xsl:otherwise>
							</xsl:choose>
							</span>
						</th>
					</xsl:for-each>
					<th class="win_list_bg_header">
						<xsl:attribute name="width">
							<xsl:if test="/page/header/layouts/layout[@id='win_list_table']/@width">
								<xsl:value-of select="/page/header/layouts/layout[@id='win_list_table']/@width"/>
							</xsl:if>
							<xsl:if test="not(/page/header/layouts/layout[@id='win_list_table']/@width)">5px</xsl:if>
						</xsl:attribute>
					
					   <xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
					</th>
				</xsl:for-each>
			</tr>
			
			</THEAD>
			<TBODY id="scroll_body">
			<!--xsl:attribute name="style">
				overflow-y:auto;
			</xsl:attribute-->
			<!-- render the rows -->
			<xsl:for-each select="rows/row">
				<xsl:variable name="name_row">row_<xsl:value-of select="@rowpos"/></xsl:variable>
				<tr tabindex="0">
      				<xsl:attribute name="name"><xsl:value-of select="$name_row"/></xsl:attribute>
      				<xsl:attribute name="id"><xsl:value-of select="$name_row"/></xsl:attribute>
					<xsl:if test="../../@is_line_select='true'">
						<xsl:for-each select="dblclick">
								<xsl:call-template name="generate_action_on_double_click">
									<xsl:with-param name="row_id"><xsl:value-of select="../@zobject"/></xsl:with-param>
									 <xsl:with-param name="key"><xsl:value-of select="../@key"/></xsl:with-param>
									<xsl:with-param name="descr"><xsl:value-of select="../@descr"/></xsl:with-param>
								</xsl:call-template>
						</xsl:for-each>
					</xsl:if>
					<xsl:if test="../../@is_line_select='true'">
						<xsl:for-each select="rightclick">
							<xsl:call-template name="generate_action_on_right_click"/>
						</xsl:for-each>
					</xsl:if>					
      				<xsl:if test="not(@deleted) and @rowpos mod 2 = 0"><xsl:attribute name="class">zwltr</xsl:attribute></xsl:if>
      				<xsl:if test="not(@deleted) and @rowpos mod 2 = 1"><xsl:attribute name="class">zwltr2</xsl:attribute></xsl:if>
      				<xsl:if test="@deleted"><xsl:attribute name="class">zwltr3</xsl:attribute></xsl:if>
					<xsl:if test="@zobject"><xsl:attribute name="id"><xsl:value-of select="@zobject"/></xsl:attribute></xsl:if>
					<xsl:if test="@key"><xsl:attribute name="title"><xsl:value-of select="@key"/>;<xsl:value-of select="@descr"/></xsl:attribute></xsl:if>
						<xsl:if test="../../@has_action_bar='true'">
							<xsl:choose>
								<xsl:when test="@key">
									<xsl:attribute name="onclick">
										rsKey(this,'<xsl:value-of select="@key"/>','<xsl:value-of select="@descr"/>',event);
									</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:if test="../../@is_line_select='true'">
										<xsl:attribute name="onclick">
											rs(this,event);
											<xsl:for-each select="smplclick">
												<xsl:for-each select="action">
													<xsl:call-template name="generate_action_invokation"/>
												</xsl:for-each>
											</xsl:for-each>
										</xsl:attribute>
									</xsl:if>
								</xsl:otherwise>
							</xsl:choose>
							<xsl:if test="../../@is_line_select='true'">
								<xsl:attribute name="onMouseOver">rmo(this,true,event);</xsl:attribute>
								<xsl:attribute name="onMouseOut">rmo(this,false,event);</xsl:attribute>
							</xsl:if>
						</xsl:if>
					<xsl:if test="dblclick and icon">
						<xsl:variable name="halignment"><xsl:value-of select="../../header/column[0]/@halignment"/></xsl:variable>
						<td>
							<xsl:call-template name="set_class" />
							<xsl:attribute name="align"><xsl:value-of select="$halignment"/></xsl:attribute>
							<a href="#">
								<xsl:if test="@tooltip">
									<xsl:attribute name="title"><xsl:value-of select="@tooltip"/></xsl:attribute>
								</xsl:if>								
								<xsl:for-each select="dblclick">
									<xsl:call-template name="generate_action_on_click_forced">
										<xsl:with-param name="row_id"><xsl:value-of select="../@zobject"/></xsl:with-param>
										<xsl:with-param name="key"><xsl:value-of select="../@key"/></xsl:with-param>
										<xsl:with-param name="descr"><xsl:value-of select="../@descr"/></xsl:with-param>
									</xsl:call-template>
								</xsl:for-each>
								<xsl:call-template name="render_icon"/>
							</a>
							<xsl:for-each select="control">
								<xsl:apply-templates select="*" />
							</xsl:for-each>
							
						</td>
					</xsl:if>
					<xsl:if test="icon and not(dblclick)">
						<xsl:variable name="halignment"><xsl:value-of select="../../header/column[0]/@halignment"/></xsl:variable>
						<td>
							<xsl:call-template name="set_class" />
							<xsl:attribute name="align"><xsl:value-of select="$halignment"/></xsl:attribute>
							<xsl:call-template name="render_icon"/>
						</td>
					</xsl:if>

					<xsl:for-each select="d">
						<xsl:if test="not(@visible) or @visible='true'">
							<xsl:if test="not(@skip) or not(@skip='true')">
								<xsl:variable name="thispos" />
								<xsl:choose>
									<xsl:when test="../icon">
										<xsl:call-template name="generate_win_grid_cell">
											<xsl:with-param name="thispos"><xsl:value-of select="position()+1"/></xsl:with-param>
											<xsl:with-param name="name_row"><xsl:value-of select="$name_row"/></xsl:with-param>
										</xsl:call-template>	
									</xsl:when>
									<xsl:otherwise>
										<xsl:call-template name="generate_win_grid_cell">
											<xsl:with-param name="thispos"><xsl:value-of select="position()"/></xsl:with-param>
											<xsl:with-param name="name_row"><xsl:value-of select="$name_row"/></xsl:with-param>
										</xsl:call-template>	
									</xsl:otherwise>
								</xsl:choose>
							</xsl:if>
						</xsl:if>
					</xsl:for-each>
					<td class="win_list_background">
						<xsl:attribute name="width">
							<xsl:if test="/page/header/layouts/layout[@id='win_list_table']/@width">
								<xsl:value-of select="/page/header/layouts/layout[@id='win_list_table']/@width"/>
							</xsl:if>
							<xsl:if test="not(/page/header/layouts/layout[@id='win_list_table']/@width)">5px</xsl:if>
						</xsl:attribute>
					
						<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
					</td>
				</tr>
			</xsl:for-each>
			</TBODY>

			<xsl:if test="footer">
				<TFOOT id="scroll_foot">
				<tr class="zwlth">
					<xsl:attribute name="class">zwlth</xsl:attribute>
					<xsl:if test="../../header/@has_subdetail='true'">
						<td>
							<xsl:attribute name="width">
								<xsl:if test="/page/header/layouts/layout[@id='win_list_table']/@width">
									<xsl:value-of select="/page/header/layouts/layout[@id='win_list_table']/@width"/>
								</xsl:if>
								<xsl:if test="not(/page/header/layouts/layout[@id='win_list_table']/@width)">5px</xsl:if>
							</xsl:attribute>
							<xsl:attribute name="class">zwlthc</xsl:attribute>
						   <xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						</td>
					</xsl:if>
					<xsl:for-each select="footer/column">
						<td>
							<xsl:attribute name="class">zwlthc</xsl:attribute>
								<xsl:attribute name="onClick">hsc(this);</xsl:attribute>
								<xsl:attribute name="style">
									text-align:<xsl:value-of select="@halignment"/>;
								</xsl:attribute>
								<xsl:choose>
									<xsl:when test="not(value/.='')">
										<xsl:value-of select="value/."/>
									</xsl:when>
									<xsl:otherwise><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></xsl:otherwise>
								</xsl:choose>
						</td>
					</xsl:for-each>
						<td class="win_list_background">
							<xsl:attribute name="width">
								<xsl:if test="/page/header/layouts/layout[@id='win_list_table']/@width">
									<xsl:value-of select="/page/header/layouts/layout[@id='win_list_table']/@width"/>
								</xsl:if>
								<xsl:if test="not(/page/header/layouts/layout[@id='win_list_table']/@width)">5px</xsl:if>
							</xsl:attribute>
						
						   <xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						</td>
				</tr>
				</TFOOT>
			</xsl:if>
		</table>
			<xsl:if test="@has_action_bar='true'">
			   <xsl:variable name="v_provider"><xsl:value-of select="@obj_provider"/></xsl:variable>
			   <script>
				AAInit();
				<xsl:for-each select="//win_action_bar[@provider=$v_provider]/action">
					AAAdd('<xsl:value-of select="@id"/>',<xsl:value-of select="@win"/>,[<xsl:for-each select="allowed_win">'<xsl:value-of select="@id"/>',</xsl:for-each>null],<xsl:value-of select="@multiple"/>);
				</xsl:for-each>
				AAFin('<xsl:value-of select="@obj_provider"/>_table','<xsl:value-of select="//win_action_bar[@provider=$v_provider]/@name"/>','<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="rows/@zobject"/>',<xsl:value-of select="@is_multiple_select"/>,<xsl:value-of select="@is_line_select"/>,'<xsl:value-of select="select_info/@has_more_selection"/>',false,null);
				<xsl:for-each select="rows/row">
					rs_forced2(document.getElementById('<xsl:value-of select="@zobject"/>'),null);
				</xsl:for-each>
			   </script>
			</xsl:if>
</xsl:template>
	
<xsl:template name="generate_basic_form">
	<input type="hidden" name="dg_dictionary">
		<xsl:if test="$dictionary">
			<xsl:attribute name="value">
					<xsl:value-of select="$dictionary" />
			</xsl:attribute>
		</xsl:if>
	</input>
	<input type="hidden" name="subsession">
		<xsl:attribute name="value">
			<xsl:value-of select="../header/session/@subsession" />
		</xsl:attribute>
	</input>
	<input type="hidden" name="src_uri">
		<xsl:attribute name="value">
			<xsl:value-of select="../header/action/@target" />
		</xsl:attribute>
	</input>
	<input type="hidden" name="src_sbmt">
		<xsl:attribute name="value">
			<xsl:value-of select="../header/action/@is_submit" />
		</xsl:attribute>
	</input>
		<input type="hidden" id="dg_action" name="dg_action" /> <!-- comunica la accion a realizar -->
		<input type="hidden" name="dg_tree_selection" /> <!-- comunica la seleccion en el arbol -->
		<input type="hidden" name="dg_source_control_id" /> <!-- contiene el parent en el formlov  -->
		<input type="hidden" name="dg_client_conf" /> <!-- configuracion del cliente ancho y alto de la pantalla -->
		<input type="hidden" name="dg_act_owner" /> <!--  comunica el owner action de la accion  -->
		<input type="hidden" name="dg_object_owner" />  <!--  comunica el object owner de la accion  -->
		<input type="hidden" name="dg_object_select" />  <!--  comunica el object select  de la accion  -->
		<input type="hidden" id="dg_table_provider" name="dg_table_provider" />  <!--  comunica el action owner de la accion en las solapas  -->
		<input type="hidden" name="dg_clear_select" />  <!--  comunica que se elimina la seleccion anterior  -->
		<input type="hidden" name="dg_multiple_owner_list" />  <!--  comunica multiples owners de la accion  -->
		<input type="hidden" name="dg_is_multiple_owner" />  <!--  comunica multiples owners de la accion  -->
		<input type="hidden" name="dg_cell_select" />  <!--  comunica multiples owners de la accion  -->
		<input type="hidden" name="dg_scroller" /> <!--  comunica la posicion y del scroll actual  -->
		<input type="hidden" name="dg_back_modal" />  <!--  comunica si los retornos deben hacerse a modal -->
		<input type="hidden" name="dg_extra_form_data" /> <!--  si el form es embedded  -->
		<input type="hidden" name="dg_stadistics" /> <!--  informacion estadistica -->
		<input type="hidden" name="dg_url" /> <!--  url address -->
</xsl:template>


<xsl:template match="win_form_embedded">
		<DIV >
			<xsl:call-template name="basic_generate_component">
				<xsl:with-param name="isEmbeddedForm" select="'true'" />
			</xsl:call-template>
		</DIV>
<!-- 		<xsl:if test="@has_action_bar='true'"> -->
			<script>
				var zForm = new ZForm('<xsl:value-of select="@zobject"/>',1);
				registerObjectProvider('<xsl:value-of select="@obj_provider"/>', zForm);
			</script>
<!-- 		</xsl:if> -->
</xsl:template>

<xsl:template match="win_form">
	<DIV >
		<xsl:call-template name="basic_generate_component">
			<xsl:with-param name="isForm" select="'true'" />
		</xsl:call-template>
	</DIV>
<!-- 	<xsl:if test="@has_action_bar='true'"> -->
		<script>
			var zForm = new ZForm('<xsl:value-of select="@zobject"/>',0);
			registerObjectProvider('<xsl:value-of select="@obj_provider"/>', zForm);
		</script>
<!-- 	</xsl:if> -->
	<xsl:if test="@scroll">
		<script type="text/javascript">
			adaptativeScroll('','','splitB','<xsl:value-of select="@scroll"/>','');
		</script>
	</xsl:if>
	<xsl:if test="@autorefresh">
	    <script>iniciarChecker(<xsl:value-of select="@timer"/>,'<xsl:value-of select="@marca"/>');</script>
	</xsl:if>

</xsl:template>


<xsl:template name="set_class">
	<xsl:attribute name="class">
		<xsl:choose>
			<xsl:when test="@iseje='true'">zwlthcy</xsl:when>
			<xsl:when test="@marked='true'">zwltrmc</xsl:when>
			<xsl:otherwise>zwltrc</xsl:otherwise>
		</xsl:choose>
	</xsl:attribute>
</xsl:template>

<xsl:template name="generate_win_list_cell">
	<xsl:param name="thispos"></xsl:param>
	<xsl:variable name="objtype"><xsl:value-of select="../../../header/column[ position() = $thispos ]/@type" /></xsl:variable>
	<xsl:variable name="halignment"><xsl:value-of select="../../../header/column[ position() = $thispos ]/@halignment"/></xsl:variable>
	<td>
		<xsl:if test="@zobjectcell"><xsl:attribute name="id"><xsl:value-of select="@zobjectcell"/></xsl:attribute></xsl:if>
		<xsl:if test="@axis"><xsl:attribute name="axis"><xsl:value-of select="@axis"/></xsl:attribute></xsl:if>
		<xsl:if test="@rowspan">
			<xsl:attribute name="rowspan"><xsl:value-of select="@rowspan"/></xsl:attribute>
		</xsl:if>
		<xsl:if test="@colspan">
			<xsl:attribute name="colspan"><xsl:value-of select="@colspan"/></xsl:attribute>
		</xsl:if>
		<xsl:call-template name="dragdrop_component"/>

		<xsl:if test="../../../@is_line_select='false'">
				<xsl:for-each select="../rightclick">
					<xsl:call-template name="generate_action_on_right_click_cell"/>
				</xsl:for-each>
				<xsl:for-each select="../dblclick">
					<xsl:call-template name="generate_action_on_double_click_cell">
						<xsl:with-param name="row_id"><xsl:value-of select="../@zobject"/></xsl:with-param>
					</xsl:call-template>
				</xsl:for-each>
				<xsl:for-each select="rightclick">
					<xsl:call-template name="generate_action_on_right_click_cell"/>
				</xsl:for-each>
				<xsl:for-each select="dblclick">
					<xsl:call-template name="generate_action_on_double_click_cell">
						<xsl:with-param name="row_id"><xsl:value-of select="@zobject"/></xsl:with-param>
					</xsl:call-template>
				</xsl:for-each>
				<xsl:if test="../rightclick">
					<xsl:attribute name="onclick">rsCell(this,event);</xsl:attribute>
					<xsl:attribute name="onMouseOver">rmocell(this,true,event);</xsl:attribute>
					<xsl:attribute name="onMouseOut">rmocell(this,false,event);</xsl:attribute>
				</xsl:if>
		</xsl:if>
		<xsl:if test="@tooltip">
			<xsl:attribute name="title"><xsl:value-of select="@tooltip"/></xsl:attribute>
		</xsl:if>
		<!-- xsl:if test="@foreground or @background"-->
			<xsl:attribute name="style">
				<xsl:if test="@word_wrap">
					<xsl:call-template name="generate_inline_style_no_location">
						<xsl:with-param name="style_attr"><xsl:if test="@height">height:<xsl:value-of select="@height"/>;</xsl:if> white-space: pre; white-space: pre-wrap; white-space: pre-line; white-space: -pre-wrap; white-space: -o-pre-wrap; white-space: -moz-pre-wrap; white-space: -hp-pre-wrap; word-wrap: break-word; text-align:<xsl:value-of select="$halignment"/>;</xsl:with-param>
					</xsl:call-template>
				</xsl:if>
				<xsl:if test="not(@word_wrap)">
					<xsl:call-template name="generate_inline_style_no_location">
						<xsl:with-param name="style_attr"><xsl:if test="@height">height:<xsl:value-of select="@height"/>;</xsl:if> white-space:nowrap; text-align:<xsl:value-of select="$halignment"/>;</xsl:with-param>
					</xsl:call-template>
				</xsl:if>
			</xsl:attribute>
		<!--/xsl:if-->
		<xsl:call-template name="set_class" />
		<xsl:choose>
				<xsl:when test="$objtype='JCOLOUR'">
					<div>		
						<xsl:attribute name="style">
								height: 16;
								width: 16;
								border-bottom-width: 1px; border-top-width: 1px; border-right-width: 1px; border-left-width: 1px; border-style: solid solid none; 
								border-top-color: #000000; 
								border-bottom-color: #000000;
								border-right-color: #000000;
								border-left-color: #000000;
								background-color: #<xsl:value-of select="."/>;
						</xsl:attribute>
					</div>
				</xsl:when>
			    <xsl:when test="$objtype='JIMAGE'">
					<img>
						 <xsl:attribute name="src"><xsl:value-of select="$skin_path"/><xsl:value-of select="."/></xsl:attribute>
					</img>
				</xsl:when>
			    <xsl:when test="$objtype='JBOOLEAN'">
				<xsl:choose>
					<xsl:when test=".='S'">
						<xsl:if test="not(@image_true='')">
							<img>
							    <xsl:attribute name="src"><xsl:value-of select="$skin_path"/><xsl:value-of select="@image_true"/></xsl:attribute>
							</img>
						</xsl:if>
					</xsl:when>
					<xsl:otherwise>
					    <xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
						<xsl:if test="not(@image_false='')">
							<img>
							    <xsl:attribute name="src"><xsl:value-of select="$skin_path"/><xsl:value-of select="@image_false"/></xsl:attribute>
							</img>
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test=".=''">
					  <xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
					</xsl:when>
					<xsl:when test="html_data">
						<xsl:for-each select="html_data">
						  <xsl:value-of select="." disable-output-escaping="yes"/>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						  <xsl:value-of select="." />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
		
	</td>
</xsl:template>
<xsl:template name="generate_win_grid_cell">
	<xsl:param name="thispos"></xsl:param>
	<xsl:param name="name_row"></xsl:param>
	<xsl:variable name="objtype"><xsl:value-of select="../../../header/column[ position() = $thispos ]/@type" /></xsl:variable>
	<xsl:variable name="halignment"><xsl:value-of select="../../../header/column[ position() = $thispos ]/@halignment"/></xsl:variable>
	<td>
		<xsl:attribute name="name">name_<xsl:value-of select="@zobjectcell"/></xsl:attribute>
		<xsl:if test="@axis"><xsl:attribute name="axis"><xsl:value-of select="@axis"/></xsl:attribute></xsl:if>
		<xsl:if test="@zobjectcell"><xsl:attribute name="id"><xsl:value-of select="@zobjectcell"/></xsl:attribute></xsl:if>
	
		<xsl:if test="@rowspan">
			<xsl:attribute name="rowspan"><xsl:value-of select="@rowspan"/></xsl:attribute>
		</xsl:if>
		<xsl:if test="@colspan">
			<xsl:attribute name="colspan"><xsl:value-of select="@colspan"/></xsl:attribute>
		</xsl:if>
		<xsl:if test="../../../@is_line_select='false'">
				<xsl:for-each select="../rightclick">
					<xsl:call-template name="generate_action_on_right_click_cell"/>
				</xsl:for-each>
				<xsl:for-each select="../dblclick">
					<xsl:call-template name="generate_action_on_double_click_cell">
						<xsl:with-param name="row_id"><xsl:value-of select="../@zobject"/></xsl:with-param>
					</xsl:call-template>
				</xsl:for-each>
				<xsl:for-each select="rightclick">
					<xsl:call-template name="generate_action_on_right_click_cell"/>
				</xsl:for-each>
				<xsl:for-each select="dblclick">
					<xsl:call-template name="generate_action_on_double_click_cell">
						<xsl:with-param name="row_id"><xsl:value-of select="@zobject"/></xsl:with-param>
					</xsl:call-template>
				</xsl:for-each>
				<xsl:attribute name="onclick">rsCell(this,event);</xsl:attribute>
				<xsl:attribute name="onMouseOver">rmocell(this,true,event);</xsl:attribute>
				<xsl:attribute name="onMouseOut">rmocell(this,false,event);</xsl:attribute>
		</xsl:if>
		<xsl:if test="@tooltip">
			<xsl:attribute name="title"><xsl:value-of select="@tooltip"/></xsl:attribute>
		</xsl:if>
		<!-- xsl:if test="@foreground or @background"-->
			<xsl:attribute name="style">
				<xsl:if test="@word_wrap">
					<xsl:call-template name="generate_inline_style_no_location">
						<xsl:with-param name="style_attr"><xsl:if test="@height">height:<xsl:value-of select="@height"/>;</xsl:if> white-space: pre; white-space: pre-wrap; white-space: pre-line; white-space: -pre-wrap; white-space: -o-pre-wrap; white-space: -moz-pre-wrap; white-space: -hp-pre-wrap; word-wrap: break-word; text-align:<xsl:value-of select="$halignment"/>;</xsl:with-param>
					</xsl:call-template>
				</xsl:if>
				<xsl:if test="not(@word_wrap)">
					<xsl:call-template name="generate_inline_style_no_location">
						<xsl:with-param name="style_attr"><xsl:if test="@height">height:<xsl:value-of select="@height"/>;</xsl:if> white-space:nowrap; text-align:<xsl:value-of select="$halignment"/>;</xsl:with-param>
					</xsl:call-template>
				</xsl:if>
			</xsl:attribute>
		<!--/xsl:if-->
		<xsl:call-template name="set_class" />

				<xsl:choose>
					<xsl:when test="control">
						<xsl:for-each select="control">
							<xsl:apply-templates select="*" />
						</xsl:for-each>
					</xsl:when>
					<xsl:when test="html_data">
						<xsl:for-each select="html_data">
						  <xsl:value-of select="." disable-output-escaping="yes"/>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						  <xsl:value-of select="." />
					</xsl:otherwise>
				</xsl:choose>
		
	</td>
</xsl:template>


<!--
	******** edit widgets **********
	they are components which may edit data to be negotiated with the
	server, which is sent from within a form
-->
<xsl:template match="color_field">
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	<div>
		<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_preview</xsl:attribute>
		<xsl:attribute name="style">
			<xsl:call-template name="generate_inline_style">
				<xsl:with-param name="style_attr">border-top-width: 1px; border-right-width: 1px; border-left-width: 1px; border-style: solid solid none; 
				border-top-color: #000000; 
				border-right-color: #000000;
				border-left-color: #000000;
				background-color: #<xsl:value-of select="text/."/>;
				</xsl:with-param>
			</xsl:call-template>
		</xsl:attribute>
	</div>

	<INPUT type="hidden">
			<xsl:attribute name="id"><xsl:value-of select="$fullname"/></xsl:attribute>
			<xsl:attribute name="name"><xsl:value-of select="$fullname"/></xsl:attribute>
			<xsl:attribute name="value"><xsl:value-of select="text/."/></xsl:attribute>
	</INPUT>
		<xsl:call-template name="basic_generate_form_component_scripts"/>
	
	
	<xsl:if test="@visible and (@visible='true')">
		<img>
			<xsl:attribute name="style">
				position:absolute;left:<xsl:value-of select="@icon_x"/>px;top:<xsl:value-of select="@y"/>px;height:<xsl:value-of select="@icon_y"/>px;cursor:pointer;
			</xsl:attribute>
			<xsl:attribute name="name"><xsl:value-of select="$fullname"/>_trigger</xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_trigger</xsl:attribute>
			<xsl:if test="@pop_up_icon"><xsl:attribute name="src"><xsl:value-of select="$skin_path"/>/<xsl:value-of select="@pop_up_icon"/></xsl:attribute></xsl:if>
			<xsl:attribute name="onclick">openColor(event,'<xsl:value-of select="$fullname"/>_trigger','<xsl:value-of select="$fullname"/>')</xsl:attribute>
		</img>
	</xsl:if>
	
</xsl:template>

<xsl:template match="text_field">
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	<INPUT type="text">
		<xsl:call-template name="basic_generate_text_field" />
	</INPUT>
	
	
	<xsl:if test="@visible and (@visible='true')">
		<xsl:if test="@pop_up_icon">
			<img>
				<xsl:attribute name="style">
					position:absolute;left:<xsl:value-of select="@icon_x"/>px;top:<xsl:value-of select="@y"/>px;height:<xsl:value-of select="@icon_y"/>px;cursor:pointer;
				</xsl:attribute>
				<xsl:attribute name="name"><xsl:value-of select="$fullname"/>_trigger</xsl:attribute>
				<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_trigger</xsl:attribute>
				<xsl:if test="@pop_up_icon"><xsl:attribute name="src"><xsl:value-of select="$skin_path"/>/<xsl:value-of select="@pop_up_icon"/></xsl:attribute></xsl:if>
				<xsl:attribute name="onclick">openCalculator(event,'<xsl:value-of select="$fullname"/>_trigger','<xsl:value-of select="$fullname"/>')</xsl:attribute>
			</img>
		</xsl:if>
	</xsl:if>
</xsl:template>

<xsl:template match="text_label">
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	<INPUT type="text">
		<xsl:call-template name="basic_generate_text_field" />
	</INPUT>
</xsl:template>

<xsl:template match="html_text_area">
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	<div >
		<xsl:call-template name="basic_generate_form_component">
			<xsl:with-param name="isTextComponent">true</xsl:with-param>
			<xsl:with-param name="noScripts">true</xsl:with-param>
			<xsl:with-param name="style_attr">overflow:hidden</xsl:with-param>
		</xsl:call-template>
	</div>
	<script language="JavaScript" type="text/javascript">
	addrte('<xsl:value-of select="$fullname"/>','<xsl:value-of select="$fullname"/>_i','<xsl:value-of select="text/."/>',
	'<xsl:if test="@editable='true'">0</xsl:if><xsl:if test="@editable='false'">1</xsl:if>',
	'<xsl:if test="@formulario='true'">1</xsl:if><xsl:if test="@formulario='false'">0</xsl:if>',
	'<xsl:if test="@isweb='true'">1</xsl:if><xsl:if test="@isweb='false'">0</xsl:if>',
	<xsl:value-of select="@margen_izq"/>,<xsl:value-of select="@margen_imgsup"/>,
	<xsl:value-of select="@margen_imgleft"/>,<xsl:value-of select="@margen_imgsize"/>,
	'/<xsl:value-of select="$url_prefix"/>/pss_data/<xsl:value-of select="@fondo"/>',
	<xsl:value-of select="@ancho_pagina"/>,
	<xsl:value-of select="@mapa_plantilla"/>,'<xsl:value-of select="@mapa_source"/>');
	registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>_i_WYSIWYG_Editor']);
	</script>
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
		        <xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
			    <xsl:attribute name="oncontextmenu">
			         return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1);
				</xsl:attribute>
			</xsl:if>	
		<xsl:call-template name="basic_generate_form_component">
			<xsl:with-param name="isTextComponent">true</xsl:with-param>
			<xsl:with-param name="noScripts">true</xsl:with-param>
		</xsl:call-template>
		<!-- set the TEXTAREA node text, which is the text to show in the text area-->
		
		<xsl:if test="text"><xsl:value-of select="text/."/></xsl:if>
	</TEXTAREA>
	<xsl:call-template name="basic_generate_form_component_scripts"/>
</xsl:template>

<xsl:template match="check_box"> 
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	<xsl:variable name="onchange_event_var">document.getElementById('<xsl:value-of select="$fullname"/>').value=this.checked?'S':'N';</xsl:variable>
	<DIV>
		<xsl:call-template name="basic_generate_component" />
		<INPUT type="hidden">
			<xsl:attribute name="id"><xsl:value-of select="$fullname"/></xsl:attribute>
			<xsl:attribute name="name"><xsl:value-of select="$fullname"/></xsl:attribute>
			<xsl:if test="@checked='S'"><xsl:attribute name="value">S</xsl:attribute></xsl:if>			
			<xsl:if test="@checked!='S'"><xsl:attribute name="value">N</xsl:attribute></xsl:if>			
		</INPUT>
		<INPUT type="checkbox" >
			<xsl:attribute name="name"><xsl:value-of select="$fullname"/>_check</xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_check</xsl:attribute>
			<xsl:if test="@checked='S'"><xsl:attribute name="checked"></xsl:attribute></xsl:if>
			<xsl:if test="@editable!='true'">
				<xsl:attribute name="readonly">readonly</xsl:attribute>
				<xsl:attribute name="disabled">disabled</xsl:attribute>
			</xsl:if>
			<xsl:if test="@force_focus">
				<xsl:attribute name="autofocus">true</xsl:attribute>
			</xsl:if>
			<xsl:for-each select="onblur">
					<xsl:call-template name="generate_action_onblur"/>
			</xsl:for-each>
			<xsl:attribute name="onkeydown">
					if(event.keyCode==32) this.click();
			</xsl:attribute>
			<xsl:call-template name="basic_generate_form_component_scripts">
					<xsl:with-param name="onchange_event_var" select="$onchange_event_var" />
					<xsl:with-param name="change_mode" select="'onclick'" />
			</xsl:call-template>
			<xsl:call-template name="basic_set_editable">
				<xsl:with-param name="isTextComponent">false</xsl:with-param>
			</xsl:call-template>
		</INPUT>

		<xsl:if test="@label"><xsl:value-of select="@label"/></xsl:if>	
	</DIV>
	<xsl:call-template name="basic_generate_form_component_scripts"/>
</xsl:template>

<xsl:template match="titled_border">
	<fieldset>
		<xsl:call-template name="basic_generate_component"/>
		<xsl:if test="@label">
			<legend><xsl:value-of select="@label"/></legend>
		</xsl:if>				
	</fieldset>
</xsl:template>


<xsl:template match="web_button">
    <a class="web_button"  href="#">  
		<xsl:call-template name="basic_generate_component" />
		<xsl:if test="@refreshForm='true'">
			<xsl:for-each select="action">
				<xsl:attribute name="onClick">
					goToRefreshForm('do-PartialRefreshForm', '<xsl:value-of select="@label"/>', '<xsl:value-of select="../action/@obj_provider"/>','<xsl:value-of select="../action/@object_owner_id"/>', 'view_area_and_title', '<xsl:value-of select="../action/@id_action"/>');
				</xsl:attribute>
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
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	<div>
		<xsl:call-template name="basic_generate_component">
			<xsl:with-param name="isEditComponent" select="'true'" />
		</xsl:call-template>
		<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_div</xsl:attribute>
		<table id="form_lov_toURL" width="100%" height="100%">
		<tr><td>
				<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_td_div</xsl:attribute>
				<INPUT type="text" >
	                <xsl:choose>
		                <xsl:when test="@isSearcheable='true' and @editable='true' and constraints/@required and (constraints/@required='true')">
							<xsl:attribute name="class">text_field-edit-required</xsl:attribute>
		                </xsl:when>
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
						<xsl:attribute name="placeholder"><xsl:value-of select="@placeholder"/></xsl:attribute>
					</xsl:if>
					<xsl:if test="@isSearcheable='true' and @editable!='false'">
						<xsl:attribute name="onkeypress">
								   	formLovKeyPress('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>',event);
						</xsl:attribute>
						<xsl:attribute name="onfocus">
									onFocusObject(this);
								   	formLovFocus('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>');
						</xsl:attribute>
						<xsl:attribute name="onblur">
								   	formLovLostFocus('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>');
						</xsl:attribute>
						<xsl:attribute name="onchange">
								   	formLovChange('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>');
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@isSearcheable!='true' or @editable!='true'">
						<xsl:attribute name="readonly">true</xsl:attribute>
					</xsl:if>
	
					<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_text</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="item/@description"/></xsl:attribute>
					<xsl:attribute name="style">
						<xsl:call-template name="generate_inline_style_no_location">
							<xsl:with-param name="style_attr">z-index:10; </xsl:with-param>
						</xsl:call-template>
					</xsl:attribute>
				</INPUT>
				<INPUT type="hidden" >
					<xsl:attribute name="value"><xsl:value-of select="item/@id"/></xsl:attribute>
					<xsl:call-template name="basic_generate_form_component"/>
				</INPUT>
				<xsl:if test="@isSearcheable='true' and @editable!='false'">
					<a>
						<xsl:attribute name="style">
							<xsl:call-template name="generate_inline_style_no_location">
								<xsl:with-param name="style_attr">z-index:10;</xsl:with-param>
							</xsl:call-template>
						</xsl:attribute>
						<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_combo_button</xsl:attribute>
						<xsl:attribute name="onclick">formLovChange('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>');</xsl:attribute>
						<img>
							<xsl:attribute name="src"><xsl:value-of select="$skin_path"/>/images/comboButton.gif</xsl:attribute>
						</img>
						
					</a>
					<a>
						<xsl:attribute name="style">
							<xsl:call-template name="generate_inline_style_no_location">
								<xsl:with-param name="style_attr">z-index:100;</xsl:with-param>
							</xsl:call-template>
						</xsl:attribute>
						<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_combo_processing</xsl:attribute>
						<img>
							<xsl:attribute name="src"><xsl:value-of select="$skin_path"/>/images/indicator.gif</xsl:attribute>
						</img>
					</a>
				</xsl:if>
			</td>
			<xsl:if test="@editable!='false'">
				<td width="23">
					<!--  xsl:if test="action">
					    <a class="form_lov_button_open" href="#">  
							<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_open</xsl:attribute>
							<xsl:call-template name="generate_action_on_click"/> 
							<span  >
							</span>
						</a>
					</xsl:if>
					<xsl:if test="not (action)"-->
					    <a class="form_lov_button_open"  href="#" >  
							<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_open</xsl:attribute>
							<xsl:attribute name="onclick">   
							   	formLovOpen('<xsl:value-of select="$fullname"/>',
					               '<xsl:value-of select="item/@objectOwner"/>',
					               '<xsl:value-of select="item/@objectAction"/>',
					               '<xsl:value-of select="item/@obj_provider"/>',
							        <xsl:choose><xsl:when test="item/@refreshFormOnSelect='true'">true</xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose>,
							        false,'<xsl:value-of select="item/@multiple"/>',
							        <xsl:value-of select="@width_lov"/>,
							        <xsl:value-of select="@height_lov"/>);
							</xsl:attribute>
							<xsl:attribute name="oncontextmenu">   
							   	formLovOpen('<xsl:value-of select="$fullname"/>',
					               '<xsl:value-of select="item/@objectOwner"/>',
  	  				               '<xsl:value-of select="item/@objectAction"/>',
  	  				               '<xsl:value-of select="item/@obj_provider"/>',
							        <xsl:choose><xsl:when test="item/@refreshFormOnSelect='true'">true</xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose>,
							        true,'<xsl:value-of select="item/@multiple"/>',
							        <xsl:value-of select="@width_lov"/>,
							        <xsl:value-of select="@height_lov"/>);
								return false;						                
							</xsl:attribute>
			        		<span  >
							</span>
						</a>
					<!--  /xsl:if-->
				</td>
				<td width="23">
					    <a class="form_lov_button_clear"  href="#">  
							<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_clear</xsl:attribute>
							<xsl:attribute name="onclick">onClickFormLovClear('<xsl:value-of select="$fullname"/>');</xsl:attribute>
			        		<span >
							</span>
						</a>
				</td>
			</xsl:if>
		</tr>
		</table>
	</div>
				<xsl:if test="@isSearcheable='true' and @editable!='false'">
					<div class="formLovOptions">
					    <xsl:attribute name="style">
					    z-index:50;position:absolute;<xsl:if test="@x">left:<xsl:value-of select="@x"/>px;</xsl:if>
						<xsl:if test="@y">top:<xsl:value-of select="@y+21"/>px;</xsl:if>
					    </xsl:attribute>
						<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_combo_div</xsl:attribute>
						<xsl:call-template name="do_formlov_combo_box_inner_div">
								<xsl:with-param name="fullname"><xsl:value-of select="$fullname"/></xsl:with-param>
								<xsl:with-param name="specialClass">list_box</xsl:with-param>
						</xsl:call-template>
					</div>
				</xsl:if>
	<xsl:if test="@editable!='false'">
		<script>
			registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']);
			registerNoConstraint('<xsl:value-of select="$fullname"/>_text',['<xsl:value-of select="$fullname"/>_text']);
			formLovRegister('<xsl:value-of select="$fullname"/>');
			<xsl:if test="@isSearcheable='true' and @editable!='false'">
				registerDependantFormLov('<xsl:value-of select="$fullname"/>',<xsl:value-of select="item/@refreshFormOnSelect"/>, '<xsl:value-of select="item/@objectOwner"/>','<xsl:value-of select="item/@objectAction"/>','<xsl:value-of select="item/@multiple"/>','<xsl:value-of select="item/@obj_provider"/>');
			</xsl:if>
		</script>
	</xsl:if>
</xsl:template>

<xsl:template match="win_lov">
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	<div>
		<xsl:call-template name="basic_generate_component">
			<xsl:with-param name="isEditComponent" select="'true'" />
		</xsl:call-template>
		<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_div</xsl:attribute>
		<table id="form_lov_toURL" width="100%" height="100%">
		<tr><td>
				<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_td_div</xsl:attribute>
				<INPUT type="text" >
					<xsl:if test="@force_focus">
						<xsl:attribute name="autofocus">true</xsl:attribute>
					</xsl:if>
					<xsl:for-each select="onblur">
							<xsl:call-template name="generate_action_onblur"/>
					</xsl:for-each>
					<xsl:if test="@placeholder">
						<xsl:attribute name="placeholder"><xsl:value-of select="@placeholder"/></xsl:attribute>
					</xsl:if>
					
					<xsl:if test="/page/header/savehelp">			
					    <xsl:attribute name="oncontextmenu">
					         return goToSaveHelp('<xsl:value-of select="$fullname"/>_text',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1);
						</xsl:attribute>
					</xsl:if>
					<xsl:choose>
		                <xsl:when test="@isSearcheable='true' and @editable='true' and constraints/@required and (constraints/@required='true')">
							<xsl:attribute name="class">text_field-edit-required</xsl:attribute>
		                </xsl:when>
		                <xsl:when test="@isSearcheable='true' and  @editable='true'">
							<xsl:attribute name="class">text_field-edit</xsl:attribute>
		                </xsl:when>
		                <xsl:otherwise>
							<xsl:attribute name="class">text_field</xsl:attribute>
		                </xsl:otherwise>
	                </xsl:choose>
					<xsl:if test="@isSearcheable='true' and @editable!='false'">
						<xsl:attribute name="onkeypress">
								   	formLovKeyPress('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>',event);
						</xsl:attribute>
						<xsl:attribute name="onfocus">
									onFocusObject(this);
								   	formLovFocus('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>');
						</xsl:attribute>
						<xsl:attribute name="onblur">
								   	formLovLostFocus('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>');
						</xsl:attribute>
						<xsl:attribute name="onchange">
								   	formLovChange('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>');
						</xsl:attribute>
					</xsl:if>
					<xsl:if test="@isSearcheable!='true' or @editable!='true'">
						<xsl:attribute name="readonly">true</xsl:attribute>
					</xsl:if>
	
					<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_text</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="item/@description"/></xsl:attribute>
					<xsl:attribute name="style">
						<xsl:call-template name="generate_inline_style_no_location">
							<xsl:with-param name="style_attr">z-index:10; </xsl:with-param>
						</xsl:call-template>
					</xsl:attribute>
				</INPUT>
				<INPUT type="hidden" >
					<xsl:attribute name="value"><xsl:value-of select="item/@id"/></xsl:attribute>
					<xsl:call-template name="basic_generate_form_component"/>
				</INPUT>
				<xsl:if test="@isSearcheable='true' and @editable!='false'">
					<a>
						<xsl:attribute name="style">
							<xsl:call-template name="generate_inline_style_no_location">
								<xsl:with-param name="style_attr">z-index:10;</xsl:with-param>
							</xsl:call-template>
						</xsl:attribute>
						<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_combo_button</xsl:attribute>
						<xsl:attribute name="onclick">formLovChange('<xsl:value-of select="$fullname"/>','<xsl:value-of select="item/@objectOwner"/>');</xsl:attribute>
						<img>
							<xsl:attribute name="src"><xsl:value-of select="$skin_path"/>/images/comboButton.gif</xsl:attribute>
						</img>
						
					</a>
					<a>
						<xsl:attribute name="style">
							<xsl:call-template name="generate_inline_style_no_location">
								<xsl:with-param name="style_attr">z-index:100;</xsl:with-param>
							</xsl:call-template>
						</xsl:attribute>
						<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_combo_processing</xsl:attribute>
						<img>
							<xsl:attribute name="src"><xsl:value-of select="$skin_path"/>/images/indicator.gif</xsl:attribute>
						</img>
					</a>
				</xsl:if>
			</td>
			<xsl:if test="@editable!='false'">
				<td width="23">
					    <a class="form_lov_button_open"  href="#" >  
							<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_open</xsl:attribute>
							<xsl:attribute name="onclick">   
							   	winLovOpen('<xsl:value-of select="$fullname"/>',
					               '<xsl:value-of select="item/@objectOwner"/>',
					               '<xsl:value-of select="item/@objectAction"/>',
							       '<xsl:value-of select="item/@obj_provider"/>',
							        <xsl:choose><xsl:when test="item/@refreshFormOnSelect='true'">true</xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose>,
							        false,'<xsl:value-of select="item/@multiple"/>');
							</xsl:attribute>
			        		<span  >
							</span>
						</a>
				</td>
				<td width="23">
					    <a class="form_lov_button_clear"  href="#">  
							<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_clear</xsl:attribute>
							<xsl:attribute name="onclick">onClickFormLovClear('<xsl:value-of select="$fullname"/>');</xsl:attribute>
			        		<span >
							</span>
						</a>
				</td>
			</xsl:if>
		</tr>
		</table>
	</div>
				<xsl:if test="@isSearcheable='true' and @editable!='false'">
					<div class="formLovOptions">
					    <xsl:attribute name="style">
					    z-index:50;position:absolute;<xsl:if test="@x">left:<xsl:value-of select="@x"/>px;</xsl:if>
						<xsl:if test="@y">top:<xsl:value-of select="@y+21"/>px;</xsl:if>
					    </xsl:attribute>
						<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_combo_div</xsl:attribute>
						<xsl:call-template name="do_formlov_combo_box_inner_div">
								<xsl:with-param name="fullname"><xsl:value-of select="$fullname"/></xsl:with-param>
								<xsl:with-param name="specialClass">list_box</xsl:with-param>
						</xsl:call-template>
					</div>
				</xsl:if>
	<xsl:if test="@editable!='false'">
		<script>
			registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']);
			registerNoConstraint('<xsl:value-of select="$fullname"/>_text',['<xsl:value-of select="$fullname"/>_text']);
			formLovRegister('<xsl:value-of select="$fullname"/>');
			<xsl:if test="@isSearcheable='true' and @editable!='false'">
				registerDependantFormLov('<xsl:value-of select="$fullname"/>',<xsl:value-of select="item/@refreshFormOnSelect"/>, '<xsl:value-of select="item/@objectOwner"/>','<xsl:value-of select="item/@objectAction"/>','<xsl:value-of select="item/@multiple"/>','<xsl:value-of select="item/@obj_provider"/>');
			</xsl:if>
		</script>
	</xsl:if>
</xsl:template>

<xsl:template name="do_combo_box_dependant">
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	<div>
		<xsl:call-template name="basic_generate_component">
			<xsl:with-param name="isEditComponent" select="'true'" />
		</xsl:call-template>
		<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_div</xsl:attribute>
		<INPUT type="text" >
			<xsl:if test="@force_focus">
				<xsl:attribute name="autofocus">true</xsl:attribute>
			</xsl:if>
			<xsl:for-each select="onblur">
				<xsl:call-template name="generate_action_onblur"/>
			</xsl:for-each>
			<xsl:if test="@placeholder">
				<xsl:attribute name="placeholder"><xsl:value-of select="@placeholder"/></xsl:attribute>
			</xsl:if>
			
			<xsl:attribute name="onclick">dependantComboClicked('<xsl:value-of select="$fullname"/>');</xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_text</xsl:attribute>
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
			<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_button</xsl:attribute>
			<xsl:attribute name="onclick">dependantComboClicked('<xsl:value-of select="$fullname"/>');</xsl:attribute>
			<img>
				<xsl:attribute name="src"><xsl:value-of select="$skin_path"/>/images/comboButton.gif</xsl:attribute>
			</img>
		</a>
		<a>
			<xsl:attribute name="style">
				<xsl:call-template name="generate_inline_style_no_location">
					<xsl:with-param name="style_attr">z-index:10;</xsl:with-param>
				</xsl:call-template>
			</xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_processing</xsl:attribute>
			<img>
				<xsl:attribute name="src"><xsl:value-of select="$skin_path"/>/images/indicator.gif</xsl:attribute>
			</img>
		</a>
	</div>
	<xsl:if test="@visible and (@visible='true')">
		<script>
			registerDependantCombo('<xsl:value-of select="$fullname"/>');
		</script>
	</xsl:if>
</xsl:template>

<xsl:template match="multiple_list">
	<xsl:param name="specialClass" select="'null'" />
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
 	<INPUT type="hidden" >
		<xsl:attribute name="id"><xsl:value-of select="$fullname"/></xsl:attribute>
		<xsl:attribute name="name"><xsl:value-of select="$fullname"/></xsl:attribute>
	</INPUT>
	<script>
		registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']);
	</script>
	<ul class="gallery">
		<xsl:attribute name="style">
			<xsl:call-template name="generate_inline_style"/>;overflow:auto;
		</xsl:attribute>
		<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_div_main</xsl:attribute>
		<xsl:attribute name="name"><xsl:value-of select="$fullname"/>_div_main</xsl:attribute>

		<xsl:call-template name="do_multiple_list_inner_div">
			<xsl:with-param name="specialClass">multiple_list</xsl:with-param>
			<xsl:with-param name="fullname"><xsl:value-of select="$fullname"/></xsl:with-param>
		</xsl:call-template>
	</ul>
</xsl:template>

<xsl:template name="do_multiple_list_inner_div">
	<xsl:param name="specialClass" select="'null'" />
	<xsl:param name="fullname" select="'null'" />
	<!-- select>
	
		<xsl:attribute name="size"><xsl:value-of select="@height div 16"/></xsl:attribute>
		<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_combo</xsl:attribute>
		<xsl:attribute name="class">
			<xsl:value-of select="$specialClass"/>
		</xsl:attribute -->

		<xsl:call-template name="do_multiple_list_options">
			<xsl:with-param name="fullname"><xsl:value-of select="$fullname"/></xsl:with-param>
		</xsl:call-template>

	<!-- /select -->
</xsl:template>

<xsl:template name="do_multiple_list_options">
	<xsl:param name="fullname" select="'null'" />
	<xsl:for-each select="item">
		<xsl:if test="../@editable='false'">
			<xsl:if test="@selected">
				<li class="multiple_list_option_readonly">
					<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id"/></xsl:attribute>
					<xsl:attribute name="name"><xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id"/></xsl:attribute>
						<xsl:attribute name="onclick">javascript:
						return false;
						</xsl:attribute>
				
				
 	 				<img >
 	 					<xsl:attribute name="src">pss_icon/<xsl:value-of select="@icon"/></xsl:attribute>
 	 				</img>
						
					<h2><xsl:value-of select="@description"/></h2>
				</li>
			</xsl:if>
		</xsl:if>
		<xsl:if test="../@editable='true'">
			<xsl:if test="@selected">
			<script>
				document.getElementById('<xsl:value-of select="$fullname"/>').value='<xsl:value-of select="@id"/>';
				document.getElementById('<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id"/>').className ='multiple_list_option_select';
			</script>
			</xsl:if>
			<li class="multiple_list_option">
				<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id"/></xsl:attribute>
				<xsl:attribute name="name"><xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id"/></xsl:attribute>
					<xsl:attribute name="onclick">javascript:
					set_class('<xsl:value-of select="$fullname"/>_div_main','button','multiple_list_option');
					document.getElementById('<xsl:value-of select="$fullname"/>').value='<xsl:value-of select="@id"/>';
					document.getElementById('<xsl:value-of select="$fullname"/>_option_<xsl:value-of select="@id"/>').className ='multiple_list_option_select';
					executeOnChange('<xsl:value-of select="$fullname"/>');
					<xsl:if test="../@refreshForm='true'">
						goToRefreshForm('do-PartialRefreshForm', '<xsl:value-of select="$fullname"/>', '<xsl:value-of select="../action/@obj_provider"/>','<xsl:value-of select="../action/@object_owner_id"/>', 'view_area_and_title', '<xsl:value-of select="../action/@id_action"/>');
					</xsl:if>
					return false;
					</xsl:attribute>
				 	 <img>
 	 					<xsl:attribute name="src">pss_icon/<xsl:value-of select="@icon"/></xsl:attribute>
 	 				</img>

				<h2><xsl:value-of select="@description"/></h2>
			</li>
		</xsl:if>
	</xsl:for-each>
</xsl:template>

<xsl:template match="tree">
	<xsl:call-template name="tree_view" />
</xsl:template>

<xsl:template name="tree_view">
	<xsl:param name="specialClass" select="'null'" />
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	<xsl:variable name="varname">dgf_<xsl:value-of select="@form_name"/>_fd_<xsl:value-of select="@name"/></xsl:variable>
 	<INPUT type="hidden" >
		<xsl:attribute name="id"><xsl:value-of select="$fullname"/></xsl:attribute>
		<xsl:attribute name="name"><xsl:value-of select="$fullname"/></xsl:attribute>
	</INPUT>
	<script>
		registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']);
	</script>
	
	<DIV>
	
		<xsl:attribute name="style">
			<xsl:call-template name="generate_inline_style"/>;overflow:auto;
		</xsl:attribute>
		<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_div_main</xsl:attribute>
		<xsl:attribute name="name"><xsl:value-of select="$fullname"/>_div_main</xsl:attribute>

		<xsl:call-template name="do_treeview_div">
			<xsl:with-param name="specialClass">dtree</xsl:with-param>
			<xsl:with-param name="fullname"><xsl:value-of select="$fullname"/></xsl:with-param>
			<xsl:with-param name="varname"><xsl:value-of select="$varname"/></xsl:with-param>
		</xsl:call-template>
	</DIV>
</xsl:template>
<xsl:template name="do_treeview_div">
	<xsl:param name="specialClass" select="'null'" />
	<xsl:param name="fullname" select="'null'" />
	<xsl:param name="varname" select="'null'" />
	<div>
		<xsl:attribute name="id"><xsl:value-of select="$varname"/>_tree</xsl:attribute>
		<xsl:attribute name="autofocus">true</xsl:attribute>
		<xsl:attribute name="class">
			<xsl:value-of select="$specialClass"/>
		</xsl:attribute>
	</div>
	
	<script type="text/javascript">
		var <xsl:value-of select="$varname"/>_d = new dTree('<xsl:value-of select="$varname"/>_d');
		<xsl:value-of select="$varname"/>_d.add('root_','-1','<xsl:value-of select="@title"/>',null,null,null,'<xsl:value-of select="@icon"/>');
			<xsl:for-each select="item">
				<xsl:if test="../@value=@id">
					document.getElementById('<xsl:value-of select="$fullname"/>').value='<xsl:value-of select="@id"/>';
					<xsl:value-of select="$varname"/>_d.s1('<xsl:value-of select="@id_tree"/>');
				</xsl:if>
				<xsl:value-of select="$varname"/>_d.add('<xsl:value-of select="@id_tree"/>'
					,'<xsl:value-of select="@parent"/>'
					,'<xsl:value-of select="@description"/>'
					<xsl:if test="../@editable='true' and @elegible='true'">
							,'document.getElementById(\'<xsl:value-of select="$fullname"/>\').value=\'<xsl:value-of select="@id"/>\';
							<xsl:if test="../@refreshForm='true'">
								goToRefreshForm(\'do-PartialRefreshForm\', \'<xsl:value-of select="$fullname"/>\',\'<xsl:value-of select="../action/@obj_provider"/>\',\'<xsl:value-of select="../action/@object_owner_id"/>\', \'view_area_and_title\', \'<xsl:value-of select="../action/@id_action"/>\');
							</xsl:if>'
					</xsl:if>
					<xsl:if test="not(../@editable='true' and @elegible='true')">
						,null
					</xsl:if>
					,null,null,'<xsl:value-of select="@icon"/>','<xsl:value-of select="@icon_open"/>'
	
				);
			</xsl:for-each>
			<xsl:value-of select="$varname"/>_d.preOpenTo(<xsl:value-of select="$varname"/>_d.selectedNode, true);
		document.getElementById('<xsl:value-of select="$varname"/>_tree').innerHTML= <xsl:value-of select="$varname"/>_d.toString();
	</script>
</xsl:template>

<xsl:template match="combo_box">
	<xsl:call-template name="do_combo_box" />
</xsl:template>


<xsl:template match="combo_box_dependant">
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	<xsl:if test="@editable!='false'">
		<xsl:call-template name="do_combo_box_dependant" />
	</xsl:if>
	<xsl:call-template name="do_combo_box">
		<xsl:with-param name="specialClass">combo_box</xsl:with-param>
	</xsl:call-template>
</xsl:template>



<xsl:template name="do_combo_box">
	<xsl:param name="specialClass" select="'null'" />
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	<DIV>
		<xsl:attribute name="style">
			<xsl:call-template name="generate_inline_style"/>
		</xsl:attribute>
		<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_div_main</xsl:attribute>
		<xsl:attribute name="name"><xsl:value-of select="$fullname"/>_div_main</xsl:attribute>

		<xsl:call-template name="do_combo_box_inner_div">
			<xsl:with-param name="specialClass">combo_box</xsl:with-param>
		</xsl:call-template>
	</DIV>
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
			<xsl:value-of select="@description"/>
		</OPTION>
	</xsl:for-each>
</xsl:template>


<xsl:template name="do_combo_box_inner_div">
	<xsl:param name="specialClass" select="'null'" />
	<SELECT class="combo_box">
		<xsl:attribute name="size"><xsl:value-of select="@size_rows"/></xsl:attribute>
		<xsl:if test="@force_focus">
			<xsl:attribute name="autofocus">true</xsl:attribute>
		</xsl:if>
		<xsl:for-each select="onblur">
				<xsl:call-template name="generate_action_onblur"/>
		</xsl:for-each>
	
		<xsl:variable name="onchange_event_var"><xsl:value-of select="@onchange_event"/></xsl:variable>
		<xsl:variable name="onclick_event_var"><xsl:value-of select="@onclick_event"/></xsl:variable>
		<xsl:attribute name="style">
			<xsl:call-template name="generate_inline_style_no_location"/>
		</xsl:attribute>
		<xsl:call-template name="basic_generate_form_component">
			<xsl:with-param name="onchange_event_var"><xsl:value-of select="$onchange_event_var"/></xsl:with-param>
			<xsl:with-param name="onclick_event_var"><xsl:value-of select="$onclick_event_var"/></xsl:with-param>
			<xsl:with-param name="specialClass"><xsl:value-of select="$specialClass"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="do_combo_box_options"/>
	</SELECT>
</xsl:template>


<xsl:template name="do_combo_box_options">
	<xsl:for-each select="item">
		<OPTION>
			<xsl:attribute name="value"><xsl:value-of select="@id"/></xsl:attribute>
			<xsl:if test="@selected='true'"><xsl:attribute name="selected"></xsl:attribute></xsl:if>
			<xsl:if test="@separator='true'"><xsl:attribute name="disable"/><xsl:attribute name="selecthr"></xsl:attribute></xsl:if>
			<xsl:value-of select="@description"/>
		</OPTION>
	</xsl:for-each>
</xsl:template>


<xsl:template match="file">
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	 	<INPUT type="hidden" >
 			<xsl:attribute name="id"><xsl:value-of select="$fullname"/></xsl:attribute>
			<xsl:attribute name="name"><xsl:value-of select="$fullname"/></xsl:attribute>
		</INPUT>
		<script>
			registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']);
		</script>
	
 		<xsl:if test="@editable!='false'">
			<iframe src="html/upload.html" seamless="seamless">
				<xsl:attribute name="id">iframe_<xsl:value-of select="$fullname"/></xsl:attribute>
				<xsl:attribute name="name">iframe_<xsl:value-of select="$fullname"/></xsl:attribute>
				<xsl:attribute name="style">
				     <xsl:call-template name="generate_inline_style"/>
				</xsl:attribute>
			</iframe>
		</xsl:if>

</xsl:template>

<xsl:template match="sign">
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	 	<INPUT type="hidden" >
 			<xsl:attribute name="id"><xsl:value-of select="$fullname"/></xsl:attribute>
			<xsl:attribute name="name"><xsl:value-of select="$fullname"/></xsl:attribute>
		</INPUT>
		<script>
			registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']);
			document.getElementById('signApplet_<xsl:value-of select="$fullname"/>').setScriptFromApplet(getURL()+'/html/do-upload');
		</script>
		<div>
			<xsl:attribute name="style">
			     <xsl:call-template name="generate_inline_style"/>
			</xsl:attribute>
 			<applet codebase="jar" code="pss.common.pki.appletSign.SignForm.class" archive="signForm.jar,itext.jar,bcprov-jdk15.jar,WebSign.jar,sunpkcs11.jar"	width="170" height="35">
 			     <xsl:attribute name="id">signApplet_<xsl:value-of select="$fullname"/></xsl:attribute>
 			     <xsl:attribute name="name">signApplet_<xsl:value-of select="$fullname"/></xsl:attribute>
				 <PARAM NAME="outputFilename"><xsl:attribute name="value"><xsl:value-of select="$fullname"/></xsl:attribute></PARAM> 
				 <PARAM NAME="outputProgreso"><xsl:attribute name="value">f1_progreso_process</xsl:attribute></PARAM> 
				 <PARAM NAME="filename"    VALUE=""/> 
				 <PARAM NAME="label_button"    VALUE="Elegir Escrito"/> 
			</applet>
 			<p id="f1_progreso_process"> </p>
 		</div>
		<!-- iframe src="html/signFile.html">
			<xsl:attribute name="id">iframe_<xsl:value-of select="$fullname"/></xsl:attribute>
			<xsl:attribute name="name">iframe_<xsl:value-of select="$fullname"/></xsl:attribute>
			<xsl:attribute name="style">
			     <xsl:call-template name="generate_inline_style"/>
			</xsl:attribute>
		</iframe-->
</xsl:template>

<xsl:template match="scanner">
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	 	<INPUT type="hidden" >
 			<xsl:attribute name="id"><xsl:value-of select="$fullname"/></xsl:attribute>
			<xsl:attribute name="name"><xsl:value-of select="$fullname"/></xsl:attribute>
		</INPUT>
		<script>
			registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']);
			document.getElementById('scannerApplet_<xsl:value-of select="$fullname"/>').setScriptFromApplet(getURL()+'/html/do-upload');
		</script>
		<div>
			<xsl:attribute name="style">
			     <xsl:call-template name="generate_inline_style"/>
			</xsl:attribute>
 			<applet codebase="jar" code="uk.co.mmscomputing.device.twain.applet.TwainApplet.class" archive="scanner.jar" width="300" height="35">
 			     <xsl:attribute name="id">scannerApplet_<xsl:value-of select="$fullname"/></xsl:attribute>
 			     <xsl:attribute name="name">scannerApplet_<xsl:value-of select="$fullname"/></xsl:attribute>
				 <PARAM NAME="outputFilename"><xsl:attribute name="value"><xsl:value-of select="$fullname"/></xsl:attribute></PARAM> 
				 <PARAM NAME="outputProgreso"><xsl:attribute name="value">f1_progreso_process</xsl:attribute></PARAM> 
				 <PARAM NAME="filename"    VALUE=""/> 
			</applet>
 			<p id="f1_progreso_process"> </p>
 		</div>
</xsl:template>
<xsl:template match="splitpane">
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	 	<INPUT type="hidden" >
 			<xsl:attribute name="id"><xsl:value-of select="$fullname"/></xsl:attribute>
			<xsl:attribute name="name"><xsl:value-of select="$fullname"/></xsl:attribute>
		</INPUT>
		<script>
			registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']);
		</script>
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
	<xsl:variable name="onclick_event_var"><xsl:value-of select="@onclick_event"/></xsl:variable>	
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	<xsl:variable name="orientation"><xsl:value-of select="@orientation"/></xsl:variable>
	<DIV>
		<xsl:call-template name="basic_generate_form_component" />
		<xsl:for-each select="item">
			<INPUT type="radio">
				<xsl:if test="../@editable='false'">
					<xsl:attribute name="disabled">true</xsl:attribute>
				</xsl:if>
				<xsl:attribute name="name"><xsl:value-of select="$fullname"/>_options</xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="@id"/></xsl:attribute>
				<xsl:if test="@selected='true'"><xsl:attribute name="checked"></xsl:attribute></xsl:if>
				
				<xsl:if test="$onclick_event_var!=''">			
					<xsl:attribute name="onclick"><xsl:value-of select="$onclick_event_var"/></xsl:attribute>
				</xsl:if>
				<xsl:value-of select="@description"/>			
				
			</INPUT>
			<xsl:if test="$orientation='vertical'"><br></br></xsl:if>
			<xsl:if test="$orientation='horizontal'"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;&nbsp;]]></xsl:text></xsl:if>
		</xsl:for-each>
	</DIV>
</xsl:template>

<xsl:template match="date_chooser">
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
	<xsl:variable name="isEditable"><xsl:value-of select="@editable"/></xsl:variable>
	<INPUT type="text"  >
		<xsl:if test="$isEditable='true'">			
			<xsl:attribute name="onKeypress">onKeyToClick(event,'<xsl:value-of select="$fullname"/>_trigger');</xsl:attribute>
		</xsl:if>

		<xsl:call-template name="basic_generate_text_field" >
			<xsl:with-param name="specialClass">date_field</xsl:with-param>
		</xsl:call-template>

	</INPUT>
	<xsl:if test="@js_date_format and @visible and (@visible='true')">
		<img>
			<xsl:attribute name="style">
				position:<xsl:value-of select="@position_img"/>;left:<xsl:value-of select="@icon_x"/>px;height:<xsl:value-of select="@icon_y"/>px;cursor:pointer;
			</xsl:attribute>

			<xsl:attribute name="name"><xsl:value-of select="$fullname"/>_trigger</xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="$fullname"/>_trigger</xsl:attribute>
			<xsl:if test="@pop_up_icon"><xsl:attribute name="src"><xsl:value-of select="$skin_path"/>/<xsl:value-of select="@pop_up_icon"/></xsl:attribute></xsl:if>
			<xsl:if test="$isEditable='false'"><xsl:attribute name="disabled"><xsl:value-of select="$isEditable"/></xsl:attribute></xsl:if>			
		</img>
		<script>
			registerDate("<xsl:value-of select="$fullname"/>","<xsl:value-of select="@js_date_format"/>","<xsl:value-of select="/page/header/layouts/layout[@id='text_field']/@height"/>");
		</script>
	</xsl:if>
</xsl:template>



<!--
	**************************************************************************
	  INTERNAL UTILITY TEMPLATES
	**************************************************************************
	-->

<xsl:template name="basic_generate_composite">
	<DIV>
	    <xsl:if test="(@name='win_root_pane' or @name='filter_pane')">
			<xsl:if test="/page/header/savehelp">			
			    <xsl:attribute name="oncontextmenu">
			         return goToSaveHelp('<xsl:value-of select="@name"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',0);
				</xsl:attribute>
			</xsl:if>
		</xsl:if>
		<xsl:call-template name="basic_generate_component"/>
	</DIV>
</xsl:template>

<xsl:template name="basic_generate_action_view">
	<xsl:param name="style_attr" select="'null'" />
	<xsl:call-template name="generate_action_on_click"/>
	<div>
	<xsl:call-template name="basic_generate_component">
		<xsl:with-param name="isLink">true</xsl:with-param>
		<xsl:with-param name="style_attr" select="$style_attr"/>
	</xsl:call-template>
		<xsl:call-template name="render_compound_label"/>
	</div>
</xsl:template>

<xsl:template name="basic_generate_text_field">
	<xsl:param name="isEncrypted" select="'false'" />
	<xsl:param name="specialClass" select="'null'" />
	
	<xsl:if test="@force_focus">
		<xsl:attribute name="autofocus">true</xsl:attribute>
	</xsl:if>
	<xsl:for-each select="onblur">
			<xsl:call-template name="generate_action_onblur"/>
	</xsl:for-each>
	<xsl:if test="@placeholder">
		<xsl:attribute name="placeholder"><xsl:value-of select="@placeholder"/></xsl:attribute>
	</xsl:if>
	<!-- generate the form's default action invokation for the onKeydown event -->
			<xsl:attribute name="onKeyup">
				return calculeColor(this,<xsl:if test="not(@max_length)">-1</xsl:if><xsl:if test="@max_length "><xsl:value-of select="@max_length"/></xsl:if>,'<xsl:value-of select="constraints/@datatype"/>', '<xsl:value-of select="constraints/format/@pattern"/>', '<xsl:value-of select="constraints/format/@chars"/>', '#ff0000', '#000000');
			</xsl:attribute>

	<xsl:if test="constraints/@datatype='JFLOAT' or constraints/@datatype='JCURRENCY' or constraints/@datatype='JLONG' or constraints/@datatype='JINT' ">
		<xsl:attribute name="onkeydown">if (!legalNumericChar(event, <xsl:value-of select="constraints/@unsigned"/>, <xsl:value-of select="constraints/@blockoversite"/>)) return false; return gaOnKey(event, this);</xsl:attribute>
	</xsl:if>
	<xsl:if test="text"><xsl:attribute name="value"><xsl:value-of select="text/."/></xsl:attribute></xsl:if>
	<xsl:if test="@max_length"><xsl:attribute name="maxlength"><xsl:value-of select="@max_length"/></xsl:attribute></xsl:if>
	<!-- generate component -->
	<xsl:call-template name="basic_generate_form_component">
		<xsl:with-param name="isTextComponent">true</xsl:with-param>
		<xsl:with-param name="isEncrypted"><xsl:value-of select="$isEncrypted"/></xsl:with-param>
		<xsl:with-param name="specialClass"><xsl:value-of select="$specialClass"/></xsl:with-param>
	</xsl:call-template>
</xsl:template>

<xsl:template name="basic_set_editable">
	<xsl:param name="isTextComponent" select="'false'" />
	<xsl:if test="@needenter='false'">
		<xsl:call-template name="generate_action_on_enter"/>
	</xsl:if>
	<xsl:if test="@editable='false'">
		<xsl:choose>
			<xsl:when test="$isTextComponent='true'">
				<xsl:attribute name="readonly">true</xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="disabled"></xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:if>
</xsl:template>

<xsl:template name="basic_generate_form_component">
	<xsl:param name="isTextComponent" select="'false'" />
	<xsl:param name="isEncrypted" select="'false'" />
	<xsl:param name="specialClass" select="'null'" />
	<xsl:param name="noScripts" select="'false'" />
	<xsl:param name="onchange_event_var" select="''" />
	<xsl:param name="onclick_event_var" select="''" />
	<xsl:param name="style_attr" select="@style_attr" />

	<xsl:call-template name="basic_set_editable">
		<xsl:with-param name="isTextComponent"><xsl:value-of select="$isTextComponent"/></xsl:with-param>
	</xsl:call-template>


	<xsl:call-template name="basic_generate_component">
		<xsl:with-param name="isEditComponent" select="'true'" />
		<xsl:with-param name="isEncrypted"><xsl:value-of select="$isEncrypted"/></xsl:with-param>
		<xsl:with-param name="specialClass"><xsl:value-of select="$specialClass"/></xsl:with-param>
		<xsl:with-param name="style_attr" select="$style_attr" />
	</xsl:call-template>


	<xsl:if test="$noScripts='false'">
		<xsl:call-template name="basic_generate_form_component_scripts">
				<xsl:with-param name="isEncrypted" select="$isEncrypted" />
				<xsl:with-param name="onchange_event_var" select="$onchange_event_var" />
				<xsl:with-param name="onclick_event_var" select="$onclick_event_var" />
		</xsl:call-template>
	</xsl:if>

	<xsl:if test="$noScripts!='false'">
		<xsl:if test="$onchange_event_var!=''">			
			<xsl:attribute name="onchange"><xsl:value-of select="$onchange_event_var"/></xsl:attribute>
		</xsl:if>
		<xsl:if test="$onclick_event_var!=''">			
			<xsl:attribute name="onclick"><xsl:value-of select="$onclick_event_var"/></xsl:attribute>
		</xsl:if>
	</xsl:if>

</xsl:template>		

<xsl:template name="basic_generate_form_component_scripts">
	<xsl:param name="isEncrypted" select="'false'" />
	<xsl:param name="onchange_event_var" select="''" />
	<xsl:param name="onclick_event_var" select="''" />
	<xsl:param name="change_mode" select="''" />

	<xsl:variable name="name_prefix">dgf_<xsl:value-of select="@form_name"/>_fd-</xsl:variable>
	<xsl:variable name="fullname"><xsl:value-of select="$name_prefix"/><xsl:value-of select="@name"/><xsl:if test="$isEncrypted='true'">_encrypted</xsl:if></xsl:variable>
	<xsl:if test="$change_mode=''">			
		<xsl:attribute name="onchange">
			<xsl:if test="$onchange_event_var!=''">			
				<xsl:value-of select="$onchange_event_var"/>
			</xsl:if>
			<xsl:if test="@onCalculate and @isCalculeOthersFields">
				<xsl:value-of select="@onCalculate"/>;
			</xsl:if>
			executeOnChange('<xsl:value-of select="$fullname"/>');
			<xsl:if test="@refreshForm='true'">
				<xsl:choose>
					<xsl:when test="@form_name='filter_pane'">
						goToRefreshForm('do-WinListRefreshAction<xsl:if test="action/@data_string and not(@action/data_string='')">?<xsl:value-of select="action/@data_string"/></xsl:if>', '<xsl:value-of select="$fullname"/>', '<xsl:value-of select="action/@obj_provider"/>','<xsl:value-of select="action/@object_owner_id"/>', 'win_list_complete_<xsl:value-of select="action/@obj_provider"/>', '<xsl:value-of select="action/@id_action"/>');
					</xsl:when>
					<xsl:otherwise>
						goToRefreshForm('do-PartialRefreshForm', '<xsl:value-of select="$fullname"/>', '<xsl:value-of select="action/@obj_provider"/>','<xsl:value-of select="action/@object_owner_id"/>', 'view_area_and_title', '<xsl:value-of select="action/@id_action"/>');
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:attribute>
		<xsl:if test="$onclick_event_var!=''">			
			<xsl:attribute name="onclick"><xsl:value-of select="$onclick_event_var"/></xsl:attribute>
		</xsl:if>
	</xsl:if>
	<xsl:if test="$change_mode!=''">		
		<xsl:attribute name="onclick">
			<xsl:if test="$onchange_event_var!=''">			
				<xsl:value-of select="$onchange_event_var"/>
			</xsl:if>
			<xsl:if test="@onCalculate and @isCalculeOthersFields">
				<xsl:value-of select="@onCalculate"/>;
			</xsl:if>
			executeOnChange('<xsl:value-of select="$fullname"/>');
			<xsl:if test="@refreshForm='true'">
				<xsl:choose>
					<xsl:when test="@form_name='filter_pane'">
						goToRefreshForm('do-WinListRefreshAction<xsl:if test="action/@data_string and not(@action/data_string='')">?<xsl:value-of select="action/@data_string"/></xsl:if>', '<xsl:value-of select="$fullname"/>', '<xsl:value-of select="action/@obj_provider"/>','<xsl:value-of select="action/@object_owner_id"/>', 'win_list_complete_<xsl:value-of select="action/@obj_provider"/>', '<xsl:value-of select="action/@id_action"/>');
					</xsl:when>
					<xsl:otherwise>
						goToRefreshForm('do-PartialRefreshForm', '<xsl:value-of select="$fullname"/>', '<xsl:value-of select="action/@obj_provider"/>','<xsl:value-of select="action/@object_owner_id"/>', 'view_area_and_title', '<xsl:value-of select="action/@id_action"/>');
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$onclick_event_var!=''">			
				<xsl:value-of select="$onclick_event_var"/>
			</xsl:if>
		</xsl:attribute>
	</xsl:if>
		 
	<xsl:if test="/page/header/savehelp">			
		<xsl:attribute name="oncontextmenu">
		         return goToSaveHelp('<xsl:value-of select="$fullname"/>',event.x,event.y,'<xsl:value-of select="/page/header/savehelp/@win"/>','<xsl:value-of select="/page/header/savehelp/@action"/>','<xsl:value-of select="/page/header/savehelp/@status"/>',1);
		</xsl:attribute>
	</xsl:if>
	

	<xsl:if test="$onclick_event_var!=''">			
		<xsl:attribute name="onclick"><xsl:value-of select="$onclick_event_var"/></xsl:attribute>
	</xsl:if>
	
	<xsl:if test="constraints">
		<script>
			<xsl:if test="@force_focus">
				cleanFocusObject();
			</xsl:if>
			register(
				'<xsl:value-of select="$fullname"/>',
				'<xsl:value-of select="constraints/@description"/>',
				<xsl:value-of select="constraints/@required"/>,
				'<xsl:value-of select="constraints/format/@needs_input_check"/>',
				<xsl:value-of select="constraints/format/@max_length"/>,
				'<xsl:value-of select="constraints/@datatype"/>',
				'<xsl:value-of select="constraints/format/@pattern"/>',
				'<xsl:value-of select="constraints/format/@chars"/>',
				'<xsl:value-of select="constraints/@inputmode"/>',
				<xsl:if test="@foreground">0,</xsl:if><xsl:if test="not(@foreground)">1,</xsl:if>
				'<xsl:value-of select="constraints/@align"/>');
		</script>
		<script>formatear('<xsl:value-of select="$fullname"/>')</script>
	</xsl:if>
	<xsl:if test="registerAsField">
		<script>
			registerNoConstraint('<xsl:value-of select="$fullname"/>',[
			<xsl:for-each select="registerAsField/data">
				'<xsl:value-of select="$name_prefix"/><xsl:value-of select="@value"/>',			
			</xsl:for-each>
			]);
		</script>
	</xsl:if>
	<xsl:if test="not(constraints) and not(registerAsField)">
		<script>
			registerNoConstraint('<xsl:value-of select="$fullname"/>',['<xsl:value-of select="$fullname"/>']);
		</script>
	</xsl:if>

	<xsl:if test="@onAnyChange">
		<script>
			<xsl:if test="@isCalculeOnStart">
				registerStartedScript("<xsl:value-of select="@onAnyChange"/>; formatear('<xsl:value-of select="$fullname"/>');");
			</xsl:if>
			<xsl:if test="@isCalculeOnAnyChange">
				registerScript("<xsl:value-of select="@onAnyChange"/>;formatear('<xsl:value-of select="$fullname"/>');");
			</xsl:if>
		</script>
	</xsl:if>

	<xsl:call-template name="registerDependencies"/>
</xsl:template>		

<xsl:template name="registerDependencies">
	<xsl:variable name="name_prefix">dgf_<xsl:value-of select="@form_name"/>_fd-</xsl:variable>
	<xsl:variable name="fullname"><xsl:value-of select="$name_prefix"/><xsl:value-of select="@name"/></xsl:variable>
	<xsl:for-each select="dependencies">
		<script>registerControlDependency('<xsl:value-of select="$fullname"/>','<xsl:value-of select="$name_prefix"/><xsl:value-of select="@child"/>');</script>
	</xsl:for-each>
</xsl:template>		

<xsl:template name="dragdrop_component">
		<xsl:if test="@drag and not(@drag='')">
			<xsl:attribute name="draggable">true</xsl:attribute>
			<xsl:attribute name="ondragstart">
				event.dataTransfer.setData("object","<xsl:value-of select="@drag"/>");
				event.dataTransfer.setData("class","<xsl:value-of select="@dragclass"/>");
			</xsl:attribute>
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
	<xsl:param name="isForm" select="'false'" />
	<xsl:param name="isEmbeddedForm" select="'false'" />
	<xsl:param name="isEditComponent" select="'false'" />
	<xsl:param name="style_attr" select="@style_attr" />
	<xsl:param name="isLink" select="'false'" />
	<xsl:param name="isEncrypted" select="'false'" />
	<xsl:param name="specialClass" select="'null'" />

		<!-- CSS class is selected using the component state -->
	<xsl:variable name="className"><xsl:choose><xsl:when test="$specialClass!='null'"><xsl:value-of select="$specialClass"/></xsl:when><xsl:otherwise><xsl:value-of select="@skin_stereotype"/></xsl:otherwise></xsl:choose></xsl:variable>

		<xsl:attribute name="class"><xsl:value-of select="$className"/>
			<xsl:if test="@state">-<xsl:value-of select="@state"/></xsl:if>
			<xsl:if test="@editable='true' and constraints/@required and (constraints/@required='true')">-required</xsl:if>
		</xsl:attribute>
	

		<xsl:attribute name="onfocus">
			onFocusObject(this);
		</xsl:attribute>
		
		<!-- xsl:attribute name="onmousedown">
			insertHelp(this);
		</xsl:attribute-->
		
	<!-- tool tip -->
	<xsl:if test="@tool_tip"><xsl:attribute name="title"><xsl:value-of select="@tool_tip"/></xsl:attribute></xsl:if>	
	<!-- drop -->
	<xsl:for-each select="dragdropinfo">
		<xsl:call-template name="dragdrop_component"/>
	</xsl:for-each>



	<!-- CSS overriden attributes: layout -->
		<xsl:choose>
		<xsl:when test="$isLink='true'">
			<xsl:attribute name="style">
			{
			<xsl:call-template name="generate_inline_style_no_location">
				<xsl:with-param name="style_attr" select="$style_attr" />
				<xsl:with-param name="useParentVisibility">true</xsl:with-param>
			</xsl:call-template>
			}
			:hover {
			<xsl:call-template name="generate_inline_style_no_location">
				<xsl:with-param name="style_attr" select="$style_attr" />
				<xsl:with-param name="useParentVisibility">true</xsl:with-param>
			</xsl:call-template>
			}
			</xsl:attribute>
		</xsl:when>
		<xsl:when test="$className='combo_box'">
		</xsl:when>
		<xsl:otherwise>
			<xsl:attribute name="style">
				<xsl:call-template name="generate_inline_style">
				<xsl:with-param name="style_attr" select="$style_attr" />
			</xsl:call-template>
			</xsl:attribute>
		</xsl:otherwise>
		</xsl:choose>
	
	
	<xsl:choose>
		<!-- IF IT IS A FORM..... -->
		<xsl:when test="$isForm='true'">
			<!-- internal forms do not carry out any actions; all actions are
					 performed via 'mainform' and 'navform' -->
			<DIV>
				<xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
				<xsl:attribute name="id"><xsl:value-of select="@name"/></xsl:attribute>
				<!-- apply templates for children -->
				<xsl:apply-templates select="*" />
			</DIV>
		</xsl:when>
		<xsl:when test="$isEmbeddedForm='true'">
			<!-- internal forms do not carry out any actions; all actions are
					 performed via 'mainform' and 'navform' -->
			<div>
				<xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
				<xsl:attribute name="id"><xsl:value-of select="@name"/></xsl:attribute>
				<!-- apply templates for children -->
				<xsl:apply-templates select="*" />
			</div>
		</xsl:when>
		<xsl:when test="$isLink='true'">
			<xsl:if test="@composite and (@composite='true')">
				<xsl:apply-templates select="*" />
			</xsl:if>
		</xsl:when>
		<!-- IF IT ISN'T A FORM..... -->
		<xsl:otherwise>
			<xsl:choose>
				<!-- if it is an edit component, must set special name and id -->
				<xsl:when test="$isEditComponent='true'">
					<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/><xsl:if test="$isEncrypted='true'">_encrypted</xsl:if></xsl:variable>
					<xsl:attribute name="name"><xsl:value-of select="$fullname"/></xsl:attribute>
					<xsl:attribute name="id"><xsl:value-of select="$fullname"/></xsl:attribute>
				</xsl:when>
				<!-- otherwise, set id as usual -->
				<xsl:otherwise>
					<xsl:attribute name="id"><xsl:value-of select="@name"/></xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
			<!-- apply templates for children, if it is a composite although
				   it is not a form -->
			<xsl:if test="@composite and (@composite='true')">
				<xsl:apply-templates select="*" />
			</xsl:if>
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>

<xsl:template name="generate_inline_style">
	<xsl:param name="style_attr" select="'null'" />
	<xsl:param name="useParentVisibility" select="'false'" />
	<!-- position: always relative to parent -->
	<xsl:if test="@relative">position:<xsl:value-of select="@relative"/>;</xsl:if>
	<xsl:if test="not(@relative)">position:absolute;</xsl:if>
	<!-- location  -->
	<xsl:if test="@x">left:<xsl:value-of select="@x"/>px;</xsl:if>
	<xsl:if test="@y">top:<xsl:value-of select="@y"/>px;</xsl:if>
	<xsl:if test="@z">z-index:<xsl:value-of select="@z"/>;</xsl:if>
 	<xsl:call-template name="generate_inline_style_no_location">
		<xsl:with-param name="style_attr" select="$style_attr" />
		<xsl:with-param name="useParentVisibility" select="$useParentVisibility" />
	</xsl:call-template>
</xsl:template>

<xsl:template name="generate_inline_style_no_location">
	<xsl:param name="style_attr" select="'null'" />
	<xsl:param name="useParentVisibility" select="'false'" />
	<xsl:if test="@overflow-x">overflow-x:<xsl:value-of select="@overflow-x"/>;</xsl:if>
	<xsl:if test="@overflow-y">overflow-y:<xsl:value-of select="@overflow-y"/>;</xsl:if>
	<!-- colors -->
	<xsl:if test="@foreground">color:#<xsl:value-of select="@foreground"/>;</xsl:if>
	<xsl:if test="@background">background-color:#<xsl:value-of select="@background"/>;</xsl:if>
	<xsl:if test="dragdropinfo/@foreground and not(@foreground)">
		<xsl:if test="dragdropinfo/@foreground">color:#<xsl:value-of select="dragdropinfo/@foreground"/>;</xsl:if>
	</xsl:if>
	<xsl:if test="(dragdropinfo/@background) and not(@background)">
		<xsl:if test="dragdropinfo/@background">background-color:#<xsl:value-of select="dragdropinfo/@background"/>;</xsl:if>
	</xsl:if>
	<!-- margins -->
	<!-- size -->
	<xsl:if test="@width">width:<xsl:value-of select="@width"/>px;</xsl:if>
	<xsl:if test="@height">height:<xsl:value-of select="@height"/>px;</xsl:if>
	<!-- visibility -->
	<xsl:if test="$useParentVisibility='false'">
		<xsl:choose>
			<xsl:when test="@visible and (@visible='true')">visibility:visible;</xsl:when>
			<xsl:otherwise>visibility:hidden;</xsl:otherwise>
		</xsl:choose>
	</xsl:if>
	<!-- content layout -->
	<xsl:if test="content_layout/@halignment">
		text-align:<xsl:value-of select="content_layout/@halignment"/>;
	</xsl:if>
	<xsl:if test="content_layout/@valignment">	
		vertical-align:<xsl:value-of select="content_layout/@valignment"/>;
	</xsl:if>
	<xsl:if test="not($style_attr='null')">
		<xsl:value-of select="$style_attr"/>
	</xsl:if>
</xsl:template>

</xsl:stylesheet> 