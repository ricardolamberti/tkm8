<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:strip-space elements="*"/>
<xsl:output method="text" omit-xml-declaration="yes" />

	<!-- includes -->
	<xsl:param name="dictionary" select="0"/>
	<xsl:param name="requestid" select="0"/>
	


	<!-- take the skin base path, ended without slash; and the page stereotype -->
	<xsl:variable name="page_stereotype" select="page/header/@stereotype" />
	<xsl:variable name="skin_path" select="page/header/layouts/@base_path" />
	<xsl:variable name="url_prefix" select="page/header/layouts/@url_prefix" />
	<xsl:variable name="url_total" select="page/header/layouts/@url_total" />

	<!-- global instructions -->
	<xsl:strip-space elements="*"/>


	<xsl:template match="message" />
 
	<xsl:template match="page">
		{
		   "application_name": "<xsl:value-of select="header/application/@name"/>",
		   "application_version_info": "<xsl:value-of select="header/application/@version_info"/>",
		   "application_release_info": "<xsl:value-of select="header/application/@release_info"/>",
		   "dictionary": "<xsl:value-of select="$dictionary"/>",
		   "request": "<xsl:value-of select="$requestid"/>",
		   "subsession": "<xsl:value-of select="header/session/@subsession"/>",
		   "user": "<xsl:value-of select="header/session/@user"/>",
		   "user_name": "<xsl:value-of select="header/session/@user_name"/>",
		   "company": "<xsl:value-of select="header/session/@company"/>",
		   "company_id": "<xsl:value-of select="header/session/@company_id"/>",
		   "src_uri": "<xsl:value-of select="header/action/@target"/>",
		   "src_sbmt": "<xsl:value-of select="header/action/@is_submit"/>",
		   "ajax_container": "<xsl:value-of select="header/@ajax_container"/>",
		   "form_lov_control_id": "<xsl:value-of select="header/formLovInfo/@controlId"/>",

		   "views": [
				<xsl:for-each select="view">
				    {
				    	"type": "view",
						<xsl:call-template name="basic_generate_component_responsive">
							<xsl:with-param name="forcename">view_area_and_title</xsl:with-param>
						</xsl:call-template>
				    }<xsl:if test="position()!=last()">,</xsl:if>
				</xsl:for-each>
			]
			<xsl:if test="/message">
				,"message": [
				<xsl:for-each select="/message">
					{
					   "type": "message",
					   "loginpage": "<xsl:value-of select="@loginpage"/>",
					   "title": "<xsl:value-of select="@title"/>",
					   "message_type": "<xsl:value-of select="@type"/>",
					   "text": "<xsl:value-of select="text/."/>"
					} <xsl:if test="position()!=last()">,</xsl:if>
				</xsl:for-each>
				]		
			
			</xsl:if>
		}
	</xsl:template>
	
	<xsl:template match="text_field_responsive">
		{
			"type": "text_field_responsive",
			<xsl:if test="text">"value":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="text/."/></xsl:with-param></xsl:call-template>",</xsl:if>
			<xsl:if test="@max_length">"max_length":"<xsl:value-of select="@max_length"/>",</xsl:if>
			<xsl:call-template name="basic_generate_component_responsive" />
		},
	</xsl:template>

	
	<xsl:template match="split">
			<xsl:for-each select="panel_a">
				<xsl:apply-templates select="*" />
			</xsl:for-each>
	</xsl:template>
	<xsl:template match="card_responsive">
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
		{
			"type": "card_responsive",
			"id": "<xsl:value-of select="$fullname"/>_zobject",
			<xsl:if test="diferido">
				"zobject":"<xsl:value-of select="@zobject"/>",
				"diferido":"<xsl:choose><xsl:when test="diferido">true</xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose>",
				<xsl:for-each select="diferido">
					"id_diferido": "<xsl:value-of select="@ajax_container"/>",
					<xsl:call-template name="generate_action_on_click"/>
				</xsl:for-each>			
			</xsl:if>
			<xsl:call-template name="basic_generate_component_responsive" />
		},
	</xsl:template>
	<xsl:template match="infocard_responsive">
		{
			"type": "infocard_responsive",
			<xsl:if test="@label">"title":"<xsl:value-of select="@label"/>",</xsl:if>
			<xsl:if test="@label_link">"label_link":"<xsl:value-of select="@label_link"/>",</xsl:if>
			"data":"<xsl:value-of select="text/."/>",
			<xsl:if test="@directlink">"direct_link":"<xsl:value-of select="directlink"/>",</xsl:if>
			<xsl:if test="action">
				<xsl:call-template name="generate_action_on_click"/>
			</xsl:if>
			
			<xsl:if test="@dataparent">"direct_parent":"<xsl:value-of select="@dataparent"/>",</xsl:if>
			<xsl:if test="@class_header_image">"class_header_image":"<xsl:value-of select="@class_header_image"/>",</xsl:if>
			<xsl:if test="@class_header_text">"class_header_text":"<xsl:value-of select="@class_header_text"/>",</xsl:if>
			<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
			<xsl:call-template name="basic_generate_component_responsive" />
		},
	</xsl:template>
	<xsl:template name="view_image_responsive">
		{
			"type": "view_image_responsive",
			<xsl:call-template name="basic_generate_component_responsive" />
		},
	</xsl:template>
	<xsl:template match="imagecard_responsive">
		{
			"type": "imagecard_responsive",
			<xsl:if test="@label">"title":"<xsl:value-of select="@label"/>",</xsl:if>
			<xsl:if test="@label_link">"label_link":"<xsl:value-of select="@label_link"/>",</xsl:if>
			"data":"<xsl:value-of select="text/."/>",
			<xsl:if test="@directlink">"direct_link":"<xsl:value-of select="@directlink"/>",</xsl:if>
			<xsl:if test="@refreshForm">"refreshForm":"<xsl:value-of select="@refreshForm"/>",</xsl:if>
			<xsl:if test="action">
				<xsl:call-template name="generate_action_on_click"/>
			</xsl:if>
			
			<xsl:if test="@dataparent">"direct_parent":"<xsl:value-of select="@dataparent"/>",</xsl:if>
			<xsl:if test="@class_header_image">"class_header_image":"<xsl:value-of select="@class_header_image"/>",</xsl:if>
			<xsl:if test="@class_header_text">"class_header_text":"<xsl:value-of select="@class_header_text"/>",</xsl:if>
			<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
			<xsl:call-template name="basic_generate_component_responsive" />
		},
	</xsl:template>
	<xsl:template match="h1_responsive">
		{
			"type": "h1_responsive",
			<xsl:if test="@label">"title":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@label"/></xsl:with-param></xsl:call-template>",</xsl:if>
			<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
			<xsl:call-template name="basic_generate_component_responsive" />
		},
	</xsl:template>
	<xsl:template match="h2_responsive">
			{
				"type": "h2_responsive",
				<xsl:if test="@label">"title":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@label"/></xsl:with-param></xsl:call-template>",</xsl:if>
				<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
				<xsl:call-template name="basic_generate_component_responsive" />
			},
	</xsl:template>

	<xsl:template match="h3_responsive">
			{
				"type": "h3_responsive",
				<xsl:if test="@label">"title":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@label"/></xsl:with-param></xsl:call-template>",</xsl:if>
				<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
				<xsl:call-template name="basic_generate_component_responsive" />
			},
	</xsl:template>
	<xsl:template match="h4_responsive">
			{
				"type": "h4_responsive",
				<xsl:if test="@label">"title":"<xsl:value-of select="@label"/>",</xsl:if>
				<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
				<xsl:call-template name="basic_generate_component_responsive" />
			},
	</xsl:template>
	<xsl:template match="formform_responsive">
			{
				"type": "formform_responsive",
				<xsl:call-template name="basic_generate_component_responsive" />
			},
	</xsl:template>
	<xsl:template match="basic_generate_component_responsive">
				{
					"type": "formform_responsive",
					<xsl:call-template name="basic_generate_component_responsive" />
				},
	</xsl:template>
	<xsl:template match="div_responsive">
				{
					"type": "div_responsive",
					<xsl:call-template name="basic_generate_component_responsive" />
				},
	</xsl:template>
	<xsl:template match="jumbotron">
				{
					"type": "jumbotron",
					<xsl:call-template name="basic_generate_component_responsive" />
				},
	</xsl:template>
	<xsl:template match="win_expand_responsive">
				{
					"type": "win_expand_responsive",
					<xsl:call-template name="basic_generate_component_responsive" />
				},
	</xsl:template>
	<xsl:template match="win_row_expand_responsive">
					{
						"type": "win_row_expand_responsive",
						<xsl:if test="@expanded">"expanded":"<xsl:value-of select="@expanded"/>",</xsl:if>
						<xsl:if test="@label">"title":"<xsl:value-of select="@label"/>",</xsl:if>
						<xsl:if test="@label_right">"label_right":"<xsl:value-of select="@label_right"/>",</xsl:if>
						<xsl:if test="@rowid">"rowid":"<xsl:value-of select="@rowid"/>",</xsl:if>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="panel_responsive">
					{
						"type": "panel_responsive",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="panel_resizable_responsive">
					{
						"type": "jumbotron",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="table_responsive">
					{
						"type": "table_responsive",
						"id":"<xsl:value-of select="@id"/>",
						<xsl:if test="@active">"active":"<xsl:value-of select="@active"/>",</xsl:if>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="fieldset_responsive"> 
					{
						"type": "fieldset_responsive",
						<xsl:if test="@label">"title":"<xsl:value-of select="@label"/>",</xsl:if>
						<xsl:if test="@label_right">"label_right":"<xsl:value-of select="@label_right"/>",</xsl:if>
						
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="fieldsetexpanded_responsive"> 
					{
						"type": "fieldsetexpanded_responsive",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="column_responsive">
					{
						"type": "column_responsive",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="tabpanel_responsive">
					{
						"type": "tabpanel_responsive",
						<xsl:if test="@tab_select">"tab_select":"<xsl:value-of select="@tab_select"/>",</xsl:if>
						"tabs": [
							<xsl:for-each select="tab">
								{
								"id":"<xsl:value-of select="@id"/>",
								<xsl:if test="@ondemand">"ondemand":"<xsl:value-of select="@ondemand"/>",</xsl:if>
								<xsl:for-each select="link">
									<xsl:if test="action">
										<xsl:call-template name="generate_action_on_click"/>
									</xsl:if>
								</xsl:for-each>
								"title":"<xsl:value-of select="@title"/>"
								} <xsl:if test="position()!=last()">,</xsl:if>
							</xsl:for-each>
						],
						"tabs_content":[
							<xsl:for-each select="tab_responsive">
								{
								"type":"tab_content",
								"id":"<xsl:value-of select="@id"/>",
								<xsl:call-template name="basic_generate_component_responsive" />
								} <xsl:if test="position()!=last()">,</xsl:if>
							</xsl:for-each>
						],
						<xsl:call-template name="basic_generate_component_responsive" />
					},
	</xsl:template>

	<xsl:template match="zone">
					{
						"type": "zone",
						<xsl:call-template name="basic_generate_component_responsive" >
							<xsl:with-param name="singlename">true</xsl:with-param>
						</xsl:call-template>
					},
		</xsl:template>
	<xsl:template match="zone_row">
					{
						"type": "zone_row",
						<xsl:call-template name="basic_generate_component_responsive" >
							<xsl:with-param name="singlename">true</xsl:with-param>
						</xsl:call-template>
					},
		</xsl:template>
	<xsl:template match="win_list">
					{
						"type": "win_list",
						<xsl:call-template name="generate_table"/>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
	</xsl:template>
	<xsl:template name="generate_table">
						<xsl:if test="rows/row/d/@type='JWINFORM'">"with_flat":"true",</xsl:if>
						<xsl:if test="rows/row/d/@colspan or rows/row/d/@rowspan">"with_span":"true",</xsl:if>
						<xsl:if test="not(rows/row/d/@type='JWINFORM')">"with_flat":"false",</xsl:if>
						<xsl:if test="@obj_provider">"obj_provider":"<xsl:value-of select="@obj_provider"/>",</xsl:if>
						<xsl:if test="@class_table_responsive">"class_table_responsive":"<xsl:value-of select="@class_table_responsive"/>",</xsl:if>
						<xsl:if test="rows/@zobject">"rows_zobject":"<xsl:value-of select="rows/@zobject"/>",</xsl:if>
						<xsl:if test="@is_multiple_select">"is_multiple_select":"<xsl:value-of select="@is_multiple_select"/>",</xsl:if>
						<xsl:if test="@select_info/@has_more_selection">"has_more_selection":"<xsl:value-of select="@select_info/@has_more_selection"/>",</xsl:if>
						<xsl:if test="@with_group">"with_group":"<xsl:value-of select="@with_group"/>",</xsl:if>
						"actions": [
							<xsl:for-each select="actions/action">
								{
									"id":"<xsl:value-of select="@id"/>",
									"win":"<xsl:value-of select="@win"/>",
									"multiple":"<xsl:value-of select="@multiple"/>",
									"allowed": [
										<xsl:for-each select="allowed_win">
											{
												"id":"<xsl:value-of select="@id"/>"
											}<xsl:if test="position()!=last()">,</xsl:if>
										</xsl:for-each>
									],						
									"not_allowed": [
										<xsl:for-each select="not_allowed_win">
											{
												"id":"<xsl:value-of select="@id"/>"
											}<xsl:if test="position()!=last()">,</xsl:if>
										</xsl:for-each>
									]				
								}<xsl:if test="position()!=last()">,</xsl:if>
							</xsl:for-each>
						],
						<xsl:for-each select="default_actions">
						    <xsl:for-each select="default_action">
						        <xsl:choose>
						        	<xsl:when test="@name='dblclick'">
						        		<xsl:if test="action">
											<xsl:call-template name="generate_action_on_click"/>
										</xsl:if>
						        	</xsl:when>
						        </xsl:choose>
						    </xsl:for-each>
				    </xsl:for-each>
						

						<xsl:if test="@with_group='true'">
							"levels":[
								<xsl:for-each select="level_column">
								    {
								    	"level_columns": [
												<xsl:for-each select="grupo_column">
												    {
														<xsl:if test="@span">"span":"<xsl:value-of select="@span"/>",</xsl:if>
														<xsl:if test="@width">"width":"<xsl:value-of select="@width"/>",</xsl:if>
														<xsl:if test="@foreground">"foreground":"<xsl:value-of select="@foreground"/>",</xsl:if>
														<xsl:if test="@background">"background":"<xsl:value-of select="@background"/>",</xsl:if>
														"model":"grupo_column"
													}<xsl:if test="position()!=last()">,</xsl:if>
												</xsl:for-each>
											]	
									}<xsl:if test="position()!=last()">,</xsl:if>
								</xsl:for-each>
							],
						</xsl:if>

						<xsl:for-each select="header">
						"header_columns": [
							<xsl:if test="@has_subdetail='true'">
								{
									<xsl:if test="@width">"width":"20",</xsl:if>
									<xsl:call-template name="render_icon"/>
									"pos": "0"
								},
							</xsl:if>
							<xsl:for-each select="column">
							    {
							   		"columnname":"<xsl:value-of select="../../@obj_provider"/>_<xsl:value-of select="position()"/>",
									"pos":"<xsl:value-of select="position()"/>",
									<xsl:if test="@type">"type":"<xsl:value-of select="@type"/>",</xsl:if>
									<xsl:if test="@is_html">"is_html":"<xsl:value-of select="@is_html"/>",</xsl:if>
									<xsl:if test="@span">"span":"<xsl:value-of select="@span"/>",</xsl:if>
									<xsl:if test="@width">"width":"<xsl:value-of select="@width"/>",</xsl:if>
									<xsl:if test="@foreground">"foreground":"<xsl:value-of select="@foreground"/>",</xsl:if>
									<xsl:if test="@background">"background":"<xsl:value-of select="@background"/>",</xsl:if>
									<xsl:choose>
										<xsl:when test="@type='JWINFORM'">
											<xsl:call-template name="basic_generate_component_responsive">
												<xsl:with-param name="isForm" select="'true'" />
											</xsl:call-template>,
										</xsl:when>
											<xsl:when test="html_data and not(html_data/.='')">
												 	"title":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="html_data/."  disable-output-escaping="yes"/></xsl:with-param></xsl:call-template>", 
										</xsl:when>
										<xsl:when test="not(title/.='')">
											"title":"<xsl:value-of select="title/."/>",
										</xsl:when>
										<xsl:when test="@type='VOID'">
											"title"":"",
										</xsl:when>
										<xsl:otherwise>
											"title":"-",
										</xsl:otherwise>
									</xsl:choose>
									"model":"column"
								} <xsl:if test="position()!=last()">,</xsl:if>
							</xsl:for-each>
						],	
						</xsl:for-each>
			
			
				 "rows" :[ 
				 <xsl:for-each select="rows/row">
				 {
					"name_row":"row_<xsl:value-of select="@rowpos"/>",
					"rowpos":"<xsl:value-of select="@rowpos"/>",
					"zobject":"<xsl:value-of select="@zobject"/>",
					"key":"<xsl:value-of select="@key"/>",
					"descr":"<xsl:value-of select="@descr"/>",
					"selected":<xsl:if test="@selected and @selected='true'">"true"</xsl:if><xsl:if test="not(@selected and @selected='true')">"false"</xsl:if>,
					<xsl:if test="@id_tree">"id_tree":"<xsl:value-of select="@id_tree"/>",</xsl:if>
					<xsl:if test="@id_tree_parent">"id_tree_parent":"<xsl:value-of select="@id_tree_parent"/>",</xsl:if>
					"cells" :[ 
						<xsl:if test="../../header/@has_subdetail='true'">
								{
									<xsl:if test="@width">"width":"20",</xsl:if>
									<xsl:if test="details"> 
										<xsl:for-each select="details">
											<xsl:call-template name="render_icon"/>
										</xsl:for-each>
									</xsl:if>
									"pos": "0"
								},
						</xsl:if>
						<xsl:for-each select="d">
						{
							<xsl:if test="@type">"type":"<xsl:value-of select="@type"/>",</xsl:if>
							<xsl:if test="@halignment">"halignment":"<xsl:value-of select="@halignment"/>",</xsl:if>
							<xsl:if test="@valignment">"valignment":"<xsl:value-of select="@valignment"/>",</xsl:if>
							<xsl:if test="@zobjectcell">"zobjectcell":"<xsl:value-of select="@zobjectcell"/>",</xsl:if>
							<xsl:if test="@axis">"axis":"<xsl:value-of select="@axis"/>",</xsl:if>
							<xsl:if test="@rowspan">"rowspan":"<xsl:value-of select="@rowspan"/>",</xsl:if>
							<xsl:if test="@colspan">"colspan":"<xsl:value-of select="@colspan"/>",</xsl:if>
							<xsl:if test="@visibility">"visibility":"<xsl:value-of select="@visibility"/>",</xsl:if>
							<xsl:choose>
								<xsl:when test="@type='JICON'">
									<xsl:choose>
										<!--  xsl:when test="actionlink">
											"composite": "true",
											"components": [
												<xsl:for-each select="actionclick">
													{
														<xsl:if test="@descripcion">"label_lateral":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="."/></xsl:with-param></xsl:call-template>",</xsl:if>
														<xsl:call-template name="render_icon"/>
														<xsl:call-template name="generate_action_on_click"/>
														"type":"link"
													} <xsl:if test="position()!=last()">,</xsl:if>
												</xsl:for-each>
											],
										</xsl:when-->
										<xsl:when test="icon">
												<xsl:call-template name="render_icon"/>
										</xsl:when>
										<xsl:otherwise>
										</xsl:otherwise>
									</xsl:choose>
									<xsl:if test="not(@width)">"width":"20",</xsl:if>
								</xsl:when>
								<xsl:when test="@type='JLINK'">
										
											"composite": "true",
											<xsl:if test="@label_button">"label_button":"<xsl:value-of select="@title"/>",</xsl:if>
											<xsl:call-template name="render_icon"/>
											"components": [
												<xsl:for-each select="actionclick">
													{
														<xsl:if test="@descripcion">"label_lateral":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@descripcion"/></xsl:with-param></xsl:call-template>",</xsl:if>
														<xsl:call-template name="render_icon"/>
														<xsl:call-template name="generate_action_on_click"/>
														"type":"link"
													} <xsl:if test="position()!=last()">,</xsl:if>
												</xsl:for-each>
											],
								</xsl:when>
								<xsl:when test="@type='JBOOLEAN'">
									"value": "<xsl:value-of select="."/>",
										<xsl:choose>
												<xsl:when test="actionlink">
													"composite": "true",
													"components": [
														<xsl:for-each select="actionlink">
															{
																<xsl:choose>
																	<xsl:when test="../.='S'">
																		<xsl:choose>
																			<xsl:when test="not(../@image_true='') and contains(../@image_true,'.gif')">
																				"icon_path":"<xsl:value-of select="$url_total"/>/<xsl:value-of select="$skin_path"/>",
																				"icon_file":"<xsl:value-of select="@image_true"/>",
																				"icon_source":"pss_file",
																			</xsl:when>
																			<xsl:when test="not(../@image_true='') and not(contains(../@image_true,'.gif'))">
																				"icon_file":"<xsl:value-of select="@image_true"/>",
																				"icon_source":"responsive",
																			</xsl:when>
																			<xsl:otherwise>
																				"icon_file":"fa fa-check green",
																				"icon_source":"responsive",
																			</xsl:otherwise>
																		</xsl:choose>
																		
																	</xsl:when>
																	<xsl:otherwise>
																	    <xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
																	    <xsl:choose>
																			<xsl:when test="not(../@image_false='') and contains(../@image_false,'.gif')">
																				"icon_path":"<xsl:value-of select="$url_total"/>/<xsl:value-of select="$skin_path"/>",
																				"icon_file":"<xsl:value-of select="@image_false"/>",
																				"icon_source":"pss_file",
																			</xsl:when>
																			<xsl:when test="not(../@image_false='') and not(contains(../@image_false,'.gif'))">
																				"icon_file":"<xsl:value-of select="@image_false"/>",
																				"icon_source":"responsive",
																			</xsl:when>
																			<xsl:otherwise>
																				"icon_file":"fa fa-times red",
																				"icon_source":"responsive",
																			</xsl:otherwise>
																		</xsl:choose>
																	</xsl:otherwise>									
																</xsl:choose>
																<xsl:call-template name="generate_action_on_click"/>
																"type":"link"
															} <xsl:if test="position()!=last()">,</xsl:if>
														</xsl:for-each>
													],
												</xsl:when>
												<xsl:when test=".='S'">
														<xsl:choose>
															<xsl:when test="not(@image_true='') and contains(@image_true,'.gif')">
																"icon_path":"<xsl:value-of select="$url_total"/>/<xsl:value-of select="$skin_path"/>",
																"icon_file":"<xsl:value-of select="@image_true"/>",
																"icon_source":"pss_file",
															</xsl:when>
															<xsl:when test="not(@image_true='') and not(contains(@image_true,'.gif'))">
																"icon_file":"<xsl:value-of select="@image_true"/>",
																"icon_source":"responsive",
															</xsl:when>
														</xsl:choose>
													</xsl:when>
													<xsl:otherwise>
														<xsl:choose>
															<xsl:when test="not(@image_false='') and contains(@image_false,'.gif')">
																"icon_path":"<xsl:value-of select="$url_total"/>/<xsl:value-of select="$skin_path"/>",
																"icon_file":"<xsl:value-of select="@image_false"/>",
																"icon_source":"pss_file",
															</xsl:when>
															<xsl:when test="not(@image_false='') and not(contains(@image_false,'.gif'))">
																"icon_file":"<xsl:value-of select="@image_false"/>",
																"icon_source":"responsive",
															</xsl:when>
														</xsl:choose>
													</xsl:otherwise>
											</xsl:choose>
								</xsl:when>
								<xsl:when test="@type='JCOLOUR'">
									"value": "<xsl:value-of select="."/>",
								</xsl:when>
						    <xsl:when test="@type='JIMAGE'">
						    	<xsl:if test="not(.='')">
						    		"icon_file":"<xsl:value-of select="$skin_path"/><xsl:value-of select="."/>",
										"icon_source":"webapp_url>",
						    	</xsl:if>
								</xsl:when>
								<xsl:when test="@type='JWINFORM'">
									<xsl:call-template name="basic_generate_component_responsive">
										<xsl:with-param name="isForm" select="'true'" />
									</xsl:call-template>,						
								</xsl:when>
								<xsl:otherwise>
									<xsl:choose>
										<xsl:when test="actionlink">
											"composite": "true",
											"components": [
												<xsl:for-each select="actionlink">
													{
														"label_lateral":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="../."/></xsl:with-param></xsl:call-template>",
														<xsl:call-template name="render_icon"/>
														<xsl:call-template name="generate_action_on_click"/>
														"type":"link"
													} <xsl:if test="position()!=last()">,</xsl:if>
												</xsl:for-each>
											],
										</xsl:when>
										<xsl:when test=".=''">
										  "value":"",
										</xsl:when>
										<xsl:when test="html_data">
											<xsl:for-each select="html_data">
												"value":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="."  disable-output-escaping="yes"/></xsl:with-param></xsl:call-template>",
											</xsl:for-each>
										</xsl:when>
										<xsl:otherwise>
											"value":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="."/></xsl:with-param></xsl:call-template>",
										</xsl:otherwise>
									</xsl:choose>
								</xsl:otherwise>
							</xsl:choose>
							"model":"cell"
							}<xsl:if test="position()!=last()">,</xsl:if>
						</xsl:for-each>		
						]
					}<xsl:if test="position()!=last()">,</xsl:if>
				</xsl:for-each>	
				],
		</xsl:template>
	<xsl:template match="win_list_canvas">
					{
						"type": "win_list_canvas",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="datetime_component">
					{
						"type": "datetime_component",
						"value": "<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="text/."/></xsl:with-param></xsl:call-template>",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="date_chooser_responsive">
					{
						"type": "date_chooser_responsive",
						"options": "<xsl:value-of select="@options"/>",
						"value": "<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="text/."/></xsl:with-param></xsl:call-template>",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="interval_date_chooser_responsive">
					<xsl:variable name="fullnamefrom"><xsl:if test="@propname_from!=''">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@propname_from"/></xsl:if></xsl:variable>
					<xsl:variable name="fullnameto"><xsl:if test="@propname_to!=''">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@propname_to"/></xsl:if></xsl:variable>
					{
						"type": "interval_date_chooser_responsive",
						<xsl:if test="@two_prop">"two_prop":"<xsl:value-of select="@two_prop"/>",</xsl:if>
						"name_from":"<xsl:value-of select="$fullnamefrom"/>",
						"name_to":"<xsl:value-of select="$fullnameto"/>",
						"options": "<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@options"/></xsl:with-param></xsl:call-template>",
						"out_format": "<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@out_format"/></xsl:with-param></xsl:call-template>",
						"detect_changes": "<xsl:value-of select="@detect_changes"/>",
						"value": "<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="text/."/></xsl:with-param></xsl:call-template>",
						
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="dropdowncombo_responsive_noform">
					{
						"type": "dropdowncombo_responsive_noform",
						"multiple":"false",
						"noform":"true",
						<xsl:if test="@multiple">"multiple":"<xsl:value-of select="@multiple"/>",</xsl:if>
						<xsl:if test="combo/@editable">"editable":"<xsl:value-of select="combo/@editable"/>",</xsl:if>
						<xsl:if test="@zobject">"zobject":"<xsl:value-of select="@zobject"/>",</xsl:if>
						<xsl:if test="@obj_provider">"obj_provider":"<xsl:value-of select="@obj_provider"/>",</xsl:if>
						<xsl:for-each select="combo">
							<xsl:call-template name="do_combo_box_options"/>
						</xsl:for-each>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="dropdowncombo_responsive">
					{
						"type": "dropdowncombo_responsive",
						"multiple":"false",
						<xsl:if test="@multiple">"multiple":"<xsl:value-of select="@multiple"/>",</xsl:if>
						<xsl:if test="combo/@editable">"editable":"<xsl:value-of select="combo/@editable"/>",</xsl:if>
						<xsl:if test="@obj_provider">"obj_provider":"<xsl:value-of select="@obj_provider"/>",</xsl:if>
						<xsl:if test="@zobject">"zobject":"<xsl:value-of select="@zobject"/>",</xsl:if>
						<xsl:for-each select="combo">
							<xsl:call-template name="do_combo_box_options"/>
						</xsl:for-each>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="dropdownwinlov_responsive_noform">
					{
						"type": "dropdownwinlov_responsive_noform",
						"multiple":"false",
						"noform":"true",
						<xsl:if test="@objectOwner">"objectOwner":"<xsl:value-of select="@objectOwner"/>",</xsl:if>
						<xsl:if test="@objectAction">"objectAction":"<xsl:value-of select="@objectAction"/>",</xsl:if>
						<xsl:if test="@obj_provider">"obj_provider":"<xsl:value-of select="@obj_provider"/>",</xsl:if>
						<xsl:if test="@form_objectOwner">"form_objectOwner":"<xsl:value-of select="@form_objectOwner"/>",</xsl:if>
						<xsl:if test="@form_objectAction">"form_objectAction":"<xsl:value-of select="@form_objectAction"/>",</xsl:if>
						<xsl:if test="@form_objprovider">"form_obj_provider":"<xsl:value-of select="@form_objprovider"/>",</xsl:if>
						<xsl:if test="@zobject">"zobject":"<xsl:value-of select="@zobject"/>",</xsl:if>
						<xsl:if test="@refreshFormOnSelect">"refreshFormOnSelect":"<xsl:value-of select="@refreshFormOnSelect"/>",</xsl:if>
						<xsl:if test="@show_lupa">"show_lupa":"<xsl:value-of select="@show_lupa"/>",</xsl:if>
						<xsl:if test="winlov/@editable">"editable":"<xsl:value-of select="winlov/@editable"/>",</xsl:if>
						<xsl:if test="@opened">"opened":"<xsl:value-of select="@opened"/>",</xsl:if>
						"selected": [{
							"value":"<xsl:value-of select="winlov/item/@id"/>",
							"real_id":"<xsl:value-of select="winlov/item/@id"/>",
							"description":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="winlov/item/@description"/></xsl:with-param></xsl:call-template>"
						}],
						"items":[
					       	<xsl:for-each select="winlov/wellknows">
								{
									"value":"<xsl:value-of select="@id"/>",
									"real_id":"<xsl:value-of select="@real_id"/>",
									"description": "<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@description"/></xsl:with-param></xsl:call-template>"
								 }<xsl:if test="position()!=last()">,</xsl:if>
							</xsl:for-each>
							],		
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="dropdownwinlov_responsive">
					{
						"type": "dropdownwinlov_responsive",
						"multiple":"false",
						<xsl:if test="@objectOwner">"objectOwner":"<xsl:value-of select="@objectOwner"/>",</xsl:if>
						<xsl:if test="@objectAction">"objectAction":"<xsl:value-of select="@objectAction"/>",</xsl:if>
						<xsl:if test="@obj_provider">"obj_provider":"<xsl:value-of select="@obj_provider"/>",</xsl:if>
						<xsl:if test="@form_objectOwner">"form_objectOwner":"<xsl:value-of select="@form_objectOwner"/>",</xsl:if>
						<xsl:if test="@form_objectAction">"form_objectAction":"<xsl:value-of select="@form_objectAction"/>",</xsl:if>
						<xsl:if test="@form_objprovider">"form_obj_provider":"<xsl:value-of select="@form_objprovider"/>",</xsl:if>
						<xsl:if test="@zobject">"zobject":"<xsl:value-of select="@zobject"/>",</xsl:if>
						<xsl:if test="@refreshFormOnSelect">"refreshFormOnSelect":"<xsl:value-of select="@refreshFormOnSelect"/>",</xsl:if>
						<xsl:if test="@show_lupa">"show_lupa":"<xsl:value-of select="@show_lupa"/>",</xsl:if>
						<xsl:if test="winlov/@editable">"editable":"<xsl:value-of select="winlov/@editable"/>",</xsl:if>
						<xsl:if test="@opened">"opened":"<xsl:value-of select="@opened"/>",</xsl:if>
						"selected": [{
							"value":"<xsl:value-of select="winlov/item/@id"/>",
							"real_id":"<xsl:value-of select="winlov/item/@id"/>",
							"description":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="winlov/item/@description"/></xsl:with-param></xsl:call-template>"
						}],
						"items":[
					       	<xsl:for-each select="winlov/wellknows">
								{
									"value":"<xsl:value-of select="@id"/>",
									"real_id":"<xsl:value-of select="@real_id"/>",
									"description": "<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@description"/></xsl:with-param></xsl:call-template>"
								 }<xsl:if test="position()!=last()">,</xsl:if>
							</xsl:for-each>
							],		
								<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="combobox_responsive_noform">
					{
						"type": "combobox_responsive_noform",
						"noform":"true",
						<xsl:call-template name="do_combo_box_options"/>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="combobox_responsive">
					{
						"type": "combobox_responsive",
						<xsl:call-template name="do_combo_box_options"/>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
		
	<xsl:template name="do_combo_box_options">
		"items": [
		<xsl:for-each select="item">
			{
				"value":"<xsl:value-of select="@id"/>",
				"real_id":"<xsl:value-of select="@real_id"/>",
				"separator":"<xsl:value-of select="@separator"/>",
				<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
				<xsl:choose>
					<xsl:when test="@selected and @selected='true'">
						"selected": "true",
					</xsl:when>
					<xsl:when test="@checked and @checked='true'">
						"selected": "true",
					</xsl:when>
					<xsl:otherwise>
						"selected": "false",
					</xsl:otherwise>				
				</xsl:choose>
				<xsl:choose>
					<xsl:when test="(../@show_key='true') and not(@real_id='')">
						"description": "<xsl:value-of select="@real_id"/>-<xsl:value-of select="@description"/>"
					</xsl:when>
					<xsl:otherwise>
						"description": "<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@description"/></xsl:with-param></xsl:call-template>"
					</xsl:otherwise>				
				</xsl:choose>
										
			 }<xsl:if test="position()!=last()">,</xsl:if>
		</xsl:for-each>
		],
	</xsl:template>

	<xsl:template match="form">
					{
						"type": "form",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="form_responsive">
					{
						"type": "form_responsive",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
	</xsl:template>
	<xsl:template match="notebook">
					{
						"type": "notebook",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
	</xsl:template>
	<xsl:template match="link">
					{
						"type": "link",
						<xsl:if test="@title">"label_lateral":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@title"/></xsl:with-param></xsl:call-template>",</xsl:if>
						<xsl:if test="@label">"label_lateral":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@label"/></xsl:with-param></xsl:call-template>",</xsl:if>
						<xsl:if test="@subtitle">"subtitle":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@subtitle"/></xsl:with-param></xsl:call-template>",</xsl:if>
						<xsl:if test="@mode">"mode":"<xsl:value-of select="@mode"/>",</xsl:if>
						<xsl:if test="@role">"role":"<xsl:value-of select="@role"/>",</xsl:if>
						<xsl:if test="@key">"key":"<xsl:value-of select="@key"/>",</xsl:if>
						<xsl:if test="@class_position_icon">"class_position_icon":"<xsl:value-of select="@class_position_icon"/>",</xsl:if>
						<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
						<xsl:if test="@is_submit">"is_submit":"<xsl:value-of select="@is_submit"/>",</xsl:if>
						<xsl:if test="@is_cancel">"is_cancel":"<xsl:value-of select="@is_cancel"/>",</xsl:if>
						<xsl:call-template name="generate_action_on_click"/>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
	</xsl:template>

	<xsl:template match="navigation_bar_complex">
			{
				"type": "navigation_bar_complex",
				<xsl:call-template name="basic_generate_component_responsive" />
			},
	</xsl:template>	

	<xsl:template match="navigation_item">
					{
						"type": "navigation_item",
						"title":"<xsl:value-of select="@label"/>",
						"role": "<xsl:value-of select="@role"/>",
						<xsl:if test="@confirmation">"action_confirmation":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@confirmation"/></xsl:with-param></xsl:call-template>",</xsl:if>
						<xsl:if test="@opens_new_window and @opens_new_window='true'">"action_opens_new_window":"<xsl:value-of select="@opens_new_window"/>",</xsl:if>
						<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
						<xsl:if test="@is_submit">"action_is_submit":"<xsl:value-of select="@is_submit"/>",</xsl:if>
						<xsl:if test="@is_cancel">"action_is_cancel":"<xsl:value-of select="@is_cancel"/>",</xsl:if>
						<xsl:if test="@action">"action_id_action":"<xsl:value-of select="@action"/>",</xsl:if>
						<xsl:if test="@action">"action_ajax_container":"view_area_and_title",</xsl:if>
						<xsl:if test="not(@action)"><xsl:call-template name="generate_action_on_click"/></xsl:if>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
	</xsl:template>
    <xsl:template match="navigation_group">
					{
						"type": "navigation_group",
						"toggle": "<xsl:value-of select="@data_toggle"/>",
						<xsl:if test="@title">"title":"<xsl:value-of select="@title"/>",</xsl:if>
						"role": "<xsl:value-of select="@role"/>",
						<xsl:if test="arrow/icon">
							"arrow": "<xsl:value-of select="arrow/icon/@file"/>",
						</xsl:if>
						<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
	</xsl:template>
	<xsl:template match="navigation_group_sidebar">
					{
						"type": "navigation_group_sidebar",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
	</xsl:template>
	<xsl:template match="history_bar">
					{
						"type": "history_bar",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="ol">
					{
						"type": "ol",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="ul">
					{
						"type": "ul",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="li">
					{
						"type": "li",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="notebook_tab">
					{
						"type": "notebook_tab",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="break">
					{
						"type": "break",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="chart">
					{
						"type": "chart",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="drawing_chart">
					{
						"type": "drawing_chart",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="gmap">
					{
						"type": "gmap",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="container">
					{
						"type": "container",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="image">
					{
						"type": "image",
						<xsl:choose>
							<xsl:when test="icon">
								<xsl:call-template name="render_icon"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:call-template name="render_image"/>
							</xsl:otherwise>
						</xsl:choose>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="image_responsive">
					{
						"type": "image_responsive",
						<xsl:choose>
							<xsl:when test="icon">
								<xsl:call-template name="render_icon"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:call-template name="render_image"/>
							</xsl:otherwise>
						</xsl:choose>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="label_responsive">
					{
						"type": "label_responsive",
						<xsl:if test="@label">"title":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@label"/></xsl:with-param></xsl:call-template>",</xsl:if>
						<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
	</xsl:template>
	<xsl:template match="dropdown">
				{
					"type": "dropdown",
					<xsl:if test="@label_button">"label_button":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@label_button"/></xsl:with-param></xsl:call-template>",</xsl:if>
					<xsl:if test="@image">"image":"<xsl:value-of select="@image"/>",</xsl:if>
					<xsl:for-each select="arrow">
						<xsl:call-template name="render_icon"/>
					</xsl:for-each>
						
					<xsl:call-template name="basic_generate_component_responsive" />
				},
	</xsl:template>
	<xsl:template match="dropdown_responsive">
					{
						"type": "dropdown_responsive",
						<xsl:if test="@label_button">"label_button":"<xsl:value-of select="@label_button"/>",</xsl:if>
						<xsl:if test="@image">"image":"<xsl:value-of select="@image"/>",</xsl:if>
						<xsl:for-each select="arrow">
							<xsl:call-template name="render_icon"/>
						</xsl:for-each>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
	</xsl:template>
	<xsl:template match="win_action_bar">
					{
						"type": "win_action_bar",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="win_action_bar_internal">
					{
						"type": "win_action_bar_internal",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="tree_responsive">
					{
						"type": "tree_responsive",
						"treeColumn": "<xsl:value-of select="@treeColumn"/>",
						<xsl:call-template name="generate_table"/>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="win_list_json">
					{
						"type": "win_list_json",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="win_list_bigdata">
					{
						"type": "win_list_bigdata",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	
	<xsl:template match="win_grid">
					{
						"type": "win_grid",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="win_form">
					{
						"type": "win_form",
						<xsl:if test="@zobject">"zobject":"<xsl:value-of select="@zobject"/>",</xsl:if>
						<xsl:if test="@obj_provider">"obj_provider":"<xsl:value-of select="@obj_provider"/>",</xsl:if>
						<xsl:if test="@scroll">"scroll":"<xsl:value-of select="@scroll"/>",</xsl:if>
						<xsl:if test="@autorefresh">"autorefesh":"<xsl:value-of select="@autorefresh"/>",</xsl:if>
						<xsl:if test="@timer">"timer":"<xsl:value-of select="@timer"/>",</xsl:if>
						<xsl:if test="@marca">"marca":"<xsl:value-of select="@marca"/>",</xsl:if>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="win_form_embedded">
					{
						"type": "win_form_embedded",
						<xsl:if test="@zobject">"zobject":"<xsl:value-of select="@zobject"/>",</xsl:if>
						<xsl:if test="@obj_provider">"obj_provider":"<xsl:value-of select="@obj_provider"/>",</xsl:if>
						<xsl:if test="@scroll">"scroll":"<xsl:value-of select="@scroll"/>",</xsl:if>
						<xsl:if test="@autorefresh">"autorefesh":"<xsl:value-of select="@autorefresh"/>",</xsl:if>
						<xsl:if test="@timer">"timer":"<xsl:value-of select="@timer"/>",</xsl:if>
						<xsl:if test="@marca">"marca":"<xsl:value-of select="@marca"/>",</xsl:if>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="color_field">
					{
						"type": "color_field",
						"value": "<xsl:value-of select="text/."/>",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="gmap_responsive">
					{
						"type": "gmap_responsive",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="color_field_responsive">
					{
						"type": "color_field_responsive",
						"value": "<xsl:value-of select="text/."/>",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="fromto_field_responsive">
					{
						"type": "fromto_field_responsive",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="text_label_responsive">
					{
						"type": "text_label_responsive",
						<xsl:if test="text">"value":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="text/."/></xsl:with-param></xsl:call-template>",</xsl:if>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="html_text_area_responsive">
					{
						"type": "html_text_area_responsive",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="html_text_area">
					{
						"type": "html_text_area",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="password_field_responsive">
					{
						"type": "password_field_responsive",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="password_field">
					{
						"type": "password_field",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="text_area">
					{
						"type": "text_area",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="text_area_responsive">
					{
						"type": "text_area_responsive",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="check_box"> 
					{
						"type": "check_box",
						<xsl:if test="@label">"label_lateral":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@label"/></xsl:with-param></xsl:call-template>",</xsl:if>
						<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="collapsable_responsive"> 
					{
						"type": "collapsable_responsive",
						<xsl:if test="@data-target">"data-target":"<xsl:value-of select="@data-target"/>",</xsl:if>
						<xsl:if test="data-toggle">"data-toggle":"<xsl:value-of select="data-toggle"/>",</xsl:if>
						<xsl:if test="@image">"icon_class_image":"<xsl:value-of select="@image"/>",</xsl:if>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="check_box_responsive_noform">
					{
						"type": "check_box_responsive_noform",
						"noform":"true",
						<xsl:if test="@label">"label_lateral":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@label"/></xsl:with-param></xsl:call-template>",</xsl:if>
						<xsl:if test="@mode">"mode":"<xsl:value-of select="@mode"/>",</xsl:if>
						<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
						<xsl:if test="@checked='S'">"value":"S",</xsl:if>
						"refreshForm":"true", 
						<xsl:if test="action">
							<xsl:call-template name="generate_action_on_click"/>
						</xsl:if>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template> 
	
	<xsl:template match="check_box_responsive"> 
					{
						"type": "check_box_responsive",
						<xsl:if test="@label">"label_lateral":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@label"/></xsl:with-param></xsl:call-template>",</xsl:if>
						<xsl:if test="@mode">"mode":"<xsl:value-of select="@mode"/>",</xsl:if>
						<xsl:if test="@checked='S'">"value":"S",</xsl:if>
						<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="titled_border">
					{
						"type": "titled_border",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="web_button_responsive">
					{
						"type": "web_button_responsive",
						"mode": "buttongroup",
						<xsl:if test="@title">"label_lateral":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@title"/></xsl:with-param></xsl:call-template>",</xsl:if>
						<xsl:if test="@label">"label_lateral":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@label"/></xsl:with-param></xsl:call-template>",</xsl:if>
						<xsl:if test="@subtitle">"subtitle":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@subtitle"/></xsl:with-param></xsl:call-template>",</xsl:if>
						<xsl:if test="@mode">"mode":"<xsl:value-of select="@mode"/>",</xsl:if>
						<xsl:if test="@role">"role":"<xsl:value-of select="@role"/>",</xsl:if>
						<xsl:if test="@key">"key":"<xsl:value-of select="@key"/>",</xsl:if>
						<xsl:if test="@class_position_icon">"class_position_icon":"<xsl:value-of select="@class_position_icon"/>",</xsl:if>
						<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
						<xsl:if test="@is_submit">"is_submit":"<xsl:value-of select="@is_submit"/>",</xsl:if>
						<xsl:if test="@is_cancel">"is_cancel":"<xsl:value-of select="@is_cancel"/>",</xsl:if>
						<xsl:if test="action">
							<xsl:call-template name="generate_action_on_click"/>
						</xsl:if>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="web_button">
					{
						"type": "web_button",
						"mode": "buttongroup",
						<xsl:if test="@title">"label_lateral":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@title"/></xsl:with-param></xsl:call-template>",</xsl:if>
						<xsl:if test="@label">"label_lateral":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@label"/></xsl:with-param></xsl:call-template>",</xsl:if>
						<xsl:if test="@subtitle">"subtitle":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@subtitle"/></xsl:with-param></xsl:call-template>",</xsl:if>
						<xsl:if test="@mode">"mode":"<xsl:value-of select="@mode"/>",</xsl:if>
						<xsl:if test="@role">"role":"<xsl:value-of select="@role"/>",</xsl:if>
						<xsl:if test="@key">"key":"<xsl:value-of select="@key"/>",</xsl:if>
						<xsl:if test="@class_position_icon">"class_position_icon":"<xsl:value-of select="@class_position_icon"/>",</xsl:if>
						<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
						<xsl:if test="@is_submit">"is_submit":"<xsl:value-of select="@is_submit"/>",</xsl:if>
						<xsl:if test="@is_cancel">"is_cancel":"<xsl:value-of select="@is_cancel"/>",</xsl:if>
						<xsl:if test="action">
							<xsl:call-template name="generate_action_on_click"/>
						</xsl:if>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	 
	<xsl:template match="form_lov">
					{
						"type": "form_lov",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="win_lov">
					{
						"type": "win_lov",
						<xsl:if test="@multiple">"multiple":"<xsl:value-of select="@multiple"/>",</xsl:if>
						<xsl:if test="@objectOwner">"objectOwner":"<xsl:value-of select="@objectOwner"/>",</xsl:if>
						<xsl:if test="@objectAction">"objectAction":"<xsl:value-of select="@objectAction"/>",</xsl:if>
						<xsl:if test="@obj_provider">"obj_provider":"<xsl:value-of select="@obj_provider"/>",</xsl:if>
						<xsl:if test="@refreshFormOnSelect">"refreshFormOnSelect":"<xsl:value-of select="@refreshFormOnSelect"/>",</xsl:if>
						<xsl:if test="@show_lupa">"show_lupa":"<xsl:value-of select="@show_lupa"/>",</xsl:if>
						"selected": [
							<xsl:for-each select="item">
								{
									"value":"<xsl:value-of select="@id"/>",
									"real_id":"<xsl:value-of select="@real_id"/>",
									"description": "<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@description"/></xsl:with-param></xsl:call-template>"
								 }<xsl:if test="position()!=last()">,</xsl:if>
							</xsl:for-each>
							],
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="swaplist_responsive">
					<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-swap</xsl:variable>
					{
						"type": "swaplist_responsive",
						<xsl:if test="@obj_provider">"obj_provider":"<xsl:value-of select="@obj_provider"/>",</xsl:if>
						<xsl:if test="@zobject">"zobject":"<xsl:value-of select="@zobject"/>",</xsl:if>
						<xsl:if test="@zobject">"zobjectdest":"<xsl:value-of select="@zobjectdest"/>",</xsl:if>
						"origen": [
							<xsl:for-each select="origen/items">
								{
									"value":"<xsl:value-of select="@id"/>",
									"real_id":"<xsl:value-of select="@real_id"/>",
									<xsl:for-each select="extradata">
										"data-<xsl:value-of select="@extradata"/>":"<xsl:value-of select="@extradata_value"/>",
									</xsl:for-each>
									"description": "<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@description"/></xsl:with-param></xsl:call-template>"
								 }<xsl:if test="position()!=last()">,</xsl:if>
							</xsl:for-each>
							],
						"destino": [
							<xsl:for-each select="destino/items">
								{
									"value":"<xsl:value-of select="@id"/>",
									"real_id":"<xsl:value-of select="@real_id"/>",
									<xsl:for-each select="extradata">
										"data-<xsl:value-of select="@extradata"/>":"<xsl:value-of select="@extradata_value"/>",
									</xsl:for-each>
									"description": "<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@description"/></xsl:with-param></xsl:call-template>"
								 }<xsl:if test="position()!=last()">,</xsl:if>
							</xsl:for-each>
							],
						"actions": [
							<xsl:for-each select="actions/action">
								{
									"id":"<xsl:value-of select="@id"/>",
									"win":"<xsl:value-of select="@win"/>",
									"multiple":"<xsl:value-of select="@multiple"/>",
									"allowed": [
										<xsl:for-each select="allowed_win">
											{
												"id":"<xsl:value-of select="@id"/>"
											}<xsl:if test="position()!=last()">,</xsl:if>
										</xsl:for-each>
									],						
									"not_allowed": [
										<xsl:for-each select="not_allowed_win">
											{
												"id":"<xsl:value-of select="@id"/>"
											}<xsl:if test="position()!=last()">,</xsl:if>
										</xsl:for-each>
									]				
								}<xsl:if test="position()!=last()">,</xsl:if>
							</xsl:for-each>
						],	  		
						<xsl:call-template name="basic_generate_component_responsive">
							<xsl:with-param name="forcename"><xsl:value-of select="$fullname"/></xsl:with-param>
						</xsl:call-template>
						
					},
		</xsl:template>
	<xsl:template match="options_responsive">
					{
						"type": "options_responsive",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="win_lov_responsive">
					{
						"type": "win_lov_responsive",
						<xsl:if test="@multiple">"multiple":"<xsl:value-of select="@multiple"/>",</xsl:if>
						<xsl:if test="@objectOwner">"objectOwner":"<xsl:value-of select="@objectOwner"/>",</xsl:if>
						<xsl:if test="@objectAction">"objectAction":"<xsl:value-of select="@objectAction"/>",</xsl:if>
						<xsl:if test="@obj_provider">"obj_provider":"<xsl:value-of select="@obj_provider"/>",</xsl:if>
						<xsl:if test="@refreshFormOnSelect">"refreshFormOnSelect":"<xsl:value-of select="@refreshFormOnSelect"/>",</xsl:if>
						<xsl:if test="@show_lupa">"show_lupa":"<xsl:value-of select="@show_lupa"/>",</xsl:if>
						"selected": [
							<xsl:for-each select="item">
								{
									"value":"<xsl:value-of select="@id"/>",
									"real_id":"<xsl:value-of select="@real_id"/>",
									"description": "<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@description"/></xsl:with-param></xsl:call-template>"
								 }<xsl:if test="position()!=last()">,</xsl:if>
							</xsl:for-each>
							],
						"extrabutton": [
							<xsl:for-each select="web_button_responsive">
								{
									"type": "web_button_responsive",
									"mode": "buttongroup",
									<xsl:if test="@title">"label_lateral":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@title"/></xsl:with-param></xsl:call-template>",</xsl:if>
									<xsl:if test="@label">"label_lateral":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@label"/></xsl:with-param></xsl:call-template>",</xsl:if>
									<xsl:if test="@subtitle">"subtitle":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@subtitle"/></xsl:with-param></xsl:call-template>",</xsl:if>
									<xsl:if test="@mode">"mode":"<xsl:value-of select="@mode"/>",</xsl:if>
									<xsl:if test="@role">"role":"<xsl:value-of select="@role"/>",</xsl:if>
									<xsl:if test="@key">"key":"<xsl:value-of select="@key"/>",</xsl:if>
									<xsl:if test="@class_position_icon">"class_position_icon":"<xsl:value-of select="@class_position_icon"/>",</xsl:if>
									<xsl:if test="icon"><xsl:call-template name="render_icon"/></xsl:if>
									<xsl:if test="@is_submit">"is_submit":"<xsl:value-of select="@is_submit"/>",</xsl:if>
									<xsl:if test="@is_cancel">"is_cancel":"<xsl:value-of select="@is_cancel"/>",</xsl:if>
									<xsl:if test="action">
										<xsl:call-template name="generate_action_on_click"/>
									</xsl:if>
									<xsl:call-template name="basic_generate_component_responsive" />
								 }<xsl:if test="position()!=last()">,</xsl:if>
							</xsl:for-each>
							],
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="multiple_list">
					{
						"type": "multiple_list",
						"onlyshowselected": "<xsl:value-of select="@onlyshowselected"/>",
						<xsl:call-template name="do_combo_box_options"/>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="multiple_check_responsive">
					{
						"type": "multiple_check_responsive",
						"onlyshowselected": "<xsl:value-of select="@onlyshowselected"/>",
						<xsl:call-template name="do_combo_box_options"/>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="multiple_list_responsive">
					{
						"type": "multiple_list_responsive",
						"onlyshowselected": "<xsl:value-of select="@onlyshowselected"/>",
						<xsl:call-template name="do_combo_box_options"/>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="treecomponent_responsive">
					{
						"type": "treecomponent_responsive",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="tree">
					{
						"type": "tree",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="combo_box">
					{
						"type": "combo_box",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="file_responsive">
					{
						"type": "file_responsive",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="buttonpay_responsive">
					{
						"type": "buttonpay_responsive",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="file">
					{
						"type": "file",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="sign">
					{
						"type": "sign",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="scanner">
					{
						"type": "scanner",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="splitpane">
					{
						"type": "splitpane",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="date_combos_box">
					{
						"type": "date_combos_box",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="month_year_combos_box">
					{
						"type": "month_year_combos_box",
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="radio_button_set">
					{
						"type": "radio_button_set",
						"orientation": "<xsl:value-of select="@orientation"/>",
						<xsl:call-template name="do_combo_box_options"/>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	
	<xsl:template match="radio_button_set_responsive">
					{
						"type": "radio_button_set_responsive",
						"orientation": "<xsl:value-of select="@orientation"/>",
						<xsl:call-template name="do_combo_box_options"/>
						<xsl:call-template name="basic_generate_component_responsive" />
					},
		</xsl:template>
	<xsl:template match="date_chooser">
				{
					"type": "date_chooser",
					<xsl:call-template name="basic_generate_component_responsive" />
				},
	</xsl:template>
	
	
	<xsl:template name="basic_generate_component_responsive">
		<xsl:param name="isForm" select="''"/>
		<xsl:param name="forcename" select="''"/>
		<xsl:param name="singlename" select="'false'"/>
		
		<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
		<xsl:choose>
			<xsl:when test="$forcename!=''">
				"name": "<xsl:value-of select="$forcename"/>",
			</xsl:when>
			<xsl:when test="not(@form_name and (@form_name!='') and $singlename='false')">
				"name": "<xsl:value-of select="@name"/>",
			</xsl:when>
			<xsl:otherwise>
				"name": "<xsl:value-of select="$fullname"/>",
			</xsl:otherwise>
		</xsl:choose>
		<xsl:if test="@form_name and (@form_name!='') and $singlename='false'">
		</xsl:if>
		<xsl:if test="not(@form_name and (@form_name!='') and $singlename='false')">
		</xsl:if>
		
		<xsl:if test="@composite and (@composite='true')">"composite":"<xsl:value-of select="@composite"/>",</xsl:if>
		<xsl:if test="not(@composite and (@composite='true'))">"composite":"false",</xsl:if>
		<xsl:if test="@class_responsive">"class_responsive":"<xsl:value-of select="@class_responsive"/>",</xsl:if>
		<xsl:if test="@size_responsive">"size_responsive":"<xsl:value-of select="@size_responsive"/>",</xsl:if>
		<xsl:if test="@label_lateral">"label_lateral":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@label_lateral"/></xsl:with-param></xsl:call-template>",</xsl:if>
		<xsl:if test="@role_mobile">"role_mobile":"<xsl:value-of select="@role_mobile"/>",</xsl:if>
		<xsl:if test="@placeholder">"placeholder":"<xsl:value-of select="@placeholder"/>",</xsl:if>
		<xsl:if test="@padding-left">"padding-left":"<xsl:value-of select="@padding-left"/>",</xsl:if>
		<xsl:if test="@padding-left">"padding-left":"<xsl:value-of select="@padding-left"/>",</xsl:if>
		<xsl:if test="@padding-top">"padding-top":"<xsl:value-of select="@padding-top"/>",</xsl:if>
		<xsl:if test="@padding-bottom">"padding-bottom":"<xsl:value-of select="@padding-bottom"/>",</xsl:if>
		<xsl:if test="@padding-right">"padding-right":"<xsl:value-of select="@padding-right"/>",</xsl:if>
		<xsl:if test="@margin-left">"margin-left":"<xsl:value-of select="@margin-left"/>",</xsl:if>
		<xsl:if test="@margin-top">"margin-top":"<xsl:value-of select="@margin-top"/>",</xsl:if>
		<xsl:if test="@margin-bottom">"margin-bottom":"<xsl:value-of select="@margin-bottom"/>",</xsl:if>
		<xsl:if test="@margin-right">"margin-right":"<xsl:value-of select="@margin-right"/>",</xsl:if>
		<xsl:if test="@maxwidth">"max-width":"<xsl:value-of select="@maxwidth"/>",</xsl:if>
		<xsl:if test="@maxheight">"max-height":"<xsl:value-of select="@maxheight"/>",</xsl:if>
		<xsl:if test="@rwidth">"min-width":"<xsl:value-of select="@rwidth"/>",</xsl:if>
		<xsl:if test="@rheight">"min-height":"<xsl:value-of select="@rheight"/>",</xsl:if>
		<xsl:if test="@height">"height":"<xsl:value-of select="@height"/>",</xsl:if>
		<xsl:if test="@width">"width":"<xsl:value-of select="@width"/>",</xsl:if>
		<xsl:if test="@foreground">"color":"<xsl:value-of select="@foreground"/>",</xsl:if>
		<xsl:if test="@background">"background-color":"<xsl:value-of select="@background"/>",</xsl:if>
		<xsl:if test="@backgroundimage">"background-image":"<xsl:value-of select="@backgroundimage"/>",</xsl:if>
		<xsl:if test="@backgroundrepeat">"background-repeat":"<xsl:value-of select="@backgroundrepeat"/>",</xsl:if>
		<xsl:if test="@backgroundposition">"background-position":"<xsl:value-of select="@backgroundposition"/>",</xsl:if>
		<xsl:if test="@backgroundsize">"background-size":"<xsl:value-of select="@backgroundsize"/>",</xsl:if>
		<xsl:if test="@overflow-x">"overflow-x":"<xsl:value-of select="@overflow-x"/>",</xsl:if>
		<xsl:if test="@overflow-y">"overflow-y":"<xsl:value-of select="@overflow-y"/>",</xsl:if>
		<xsl:if test="@border">"border":"<xsl:value-of select="@border"/>",</xsl:if>
		<xsl:if test="@bordertop">"border-top":"<xsl:value-of select="@bordertop"/>",</xsl:if>
		<xsl:if test="@borderbottom">"border-bottom":"<xsl:value-of select="@borderbottom"/>",</xsl:if>
		<xsl:if test="@tooltip">"tooltip":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@tooltip"/></xsl:with-param></xsl:call-template>",</xsl:if>
		<xsl:if test="@nowrap">"white-space": "nowrap",</xsl:if>
		<xsl:if test="@display">"display": "<xsl:value-of select="@display"/>",</xsl:if>
		<xsl:if test="@boxshadow">"box-shadow": "<xsl:value-of select="@boxshadow"/>",</xsl:if>
		<xsl:if test="@font_weight">"font-weight":"<xsl:value-of select="@font_weight"/>",</xsl:if>
		<xsl:if test="@font_style">"font-style":"<xsl:value-of select="@font_style"/>",</xsl:if>
		<xsl:if test="@onCalculate">"onCalculate":"<xsl:value-of select="@onCalculate"/>",</xsl:if>
		<xsl:if test="@isCalculeOthersFields">"isCalculeOthersFields":"<xsl:value-of select="@isCalculeOthersFields"/>",</xsl:if>
		<xsl:if test="@onCalculate">"onCalculate":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@onCalculate"/></xsl:with-param></xsl:call-template>",</xsl:if>
		<xsl:if test="@font_size">"font-size":"<xsl:value-of select="@font_size"/>",</xsl:if>
		<xsl:if test="content_layout/@halignment">"text-align":"<xsl:value-of select="content_layout/@halignment"/>",</xsl:if>
		<xsl:if test="content_layout/@valignment">"vertical-align":"<xsl:value-of select="content_layout/@valignment"/>",</xsl:if>
		<xsl:if test="constraints">
			"constraints_inputmode":"<xsl:value-of select="constraints/@inputmode"/>",
			"constraints_required":"<xsl:value-of select="constraints/@required"/>",
			"constraints_datatype":"<xsl:value-of select="constraints/@datatype"/>",
			"constraints_blockoversite":"<xsl:value-of select="constraints/@blockoversite"/>",
			"constraints_unsigned":"<xsl:value-of select="constraints/@unsigned"/>",
			"constraints_align":"<xsl:value-of select="constraints/@align"/>",
			"constraints_description":"<xsl:value-of select="constraints/@description"/>",
			"constraints_format_max_length":"<xsl:value-of select="constraints/format/@max_length"/>",
			"constraints_format_pattern":"<xsl:value-of select="constraints/format/@pattern"/>",
			"constraints_format_chars":"<xsl:value-of select="constraints/format/@chars"/>",
			"constraints_format_needs_input_check":"<xsl:value-of select="constraints/format/@needs_input_check"/>",
		</xsl:if>
		<xsl:if test="@editable">"editable":"<xsl:value-of select="@editable"/>",</xsl:if>
		<xsl:if test="@refreshForm and @refreshForm='true'">
		    "refreshForm":"<xsl:value-of select="@refreshForm"/>",
			<xsl:if test="action">
				<xsl:call-template name="generate_action_on_click"/>
			</xsl:if>
		</xsl:if>
		<xsl:choose>
			<xsl:when test="@visible and (@visible='true')">"visibility":"visible"</xsl:when>
			<xsl:otherwise>"visibility":"hidden"</xsl:otherwise>
		</xsl:choose>
		<xsl:if test="(@composite and (@composite='true')) or $isForm='true'">
			,"components": [
				<xsl:apply-templates select="*" />
				{ "type": "end"}
			]
		</xsl:if>
	</xsl:template>

	<xsl:template name="generate_action_on_click">
		<xsl:param name="onscript" select="'null'" /> 
		<xsl:for-each select="action">
			<xsl:call-template name="generate_action_invokation">
				<xsl:with-param name="onscript" select="$onscript" />
			</xsl:call-template>
		</xsl:for-each>

	</xsl:template>
	<xsl:template name="generate_action_invokation">
		<xsl:param name="onscript" select="'null'" /> 
		<xsl:param name="dynamic_owner" select="'null'" /> 
		<xsl:param name="that" select="'this'" /> 
		<xsl:if test="@confirmation">"action_confirmation":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@confirmation"/></xsl:with-param></xsl:call-template>",</xsl:if>
		<xsl:if test="@perderdatos">"action_perderdatos":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@perderdatos"/></xsl:with-param></xsl:call-template>",</xsl:if>
		<xsl:call-template name="generate_action_invokation_do">
			<xsl:with-param name="onscript" select="$onscript" /> 
			<xsl:with-param name="dynamic_owner" select="$dynamic_owner" /> 
			<xsl:with-param name="that">that</xsl:with-param> 
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="generate_action_invokation_do">
		<xsl:param name="onscript" select="'null'" /> 
		<xsl:param name="dynamic_owner" select="'null'" /> 
		<xsl:param name="that" select="this" /> 
		<xsl:if test="@opens_new_window and @opens_new_window='true'">"action_opens_new_window":"<xsl:value-of select="@opens_new_window"/>",</xsl:if>
		<xsl:if test="@owner_object_owner_id">"action_owner_object_owner_id":"<xsl:value-of select="@owner_object_owner_id"/>",</xsl:if>
		<xsl:if test="@obj_provider">"action_obj_provider":"<xsl:value-of select="@obj_provider"/>",</xsl:if>
		<xsl:if test="@owner_obj_provider">"action_owner_obj_provider":"<xsl:value-of select="@owner_obj_provider"/>",</xsl:if>
		<xsl:if test="@owner_id_action">"action_owner_id_action":"<xsl:value-of select="@owner_id_action"/>",</xsl:if>
		<xsl:if test="@function">"action_function":"<xsl:value-of select="@function"/>",</xsl:if>
		"action_this":"<xsl:value-of select="$that"/>",
		<xsl:if test="@target">"action_target":"<xsl:value-of select="@target"/><xsl:if test="@data_string and not(@data_string='')">?<xsl:value-of select="@data_string"/></xsl:if>",</xsl:if>
		<xsl:if test="@is_submit">"action_is_submit":"<xsl:value-of select="@is_submit"/>",</xsl:if>
		<xsl:if test="$dynamic_owner">"action_dynamic_owner":"<xsl:value-of select="$dynamic_owner"/>",</xsl:if>
		<xsl:if test="@object_owner_id">"action_object_owner_id":"<xsl:value-of select="@object_owner_id"/>",</xsl:if>
		<xsl:if test="@ajax_container">"action_ajax_container":"<xsl:value-of select="@ajax_container"/>",</xsl:if>
		<xsl:if test="@resolve_string">"action_resolve_string":"<xsl:value-of select="@resolve_string"/>",</xsl:if>
		<xsl:if test="@id_action">"action_id_action":"<xsl:value-of select="@id_action"/>",</xsl:if>
		<xsl:if test="@asinc">"action_asinc":"<xsl:value-of select="@asinc"/>",</xsl:if>
		<xsl:if test="@cancelable">"action_cancelable":"<xsl:value-of select="@cancelable"/>",</xsl:if>
		<xsl:if test="@upload_data">"action_upload_data":"<xsl:value-of select="@upload_data"/>",</xsl:if>
		<xsl:if test="@special_selector">"action_special_selector":"<xsl:value-of select="@special_selector"/>",</xsl:if>
		<xsl:if test="@back_on_printer">"action_back_on_printer":"<xsl:value-of select="@back_on_printer"/>",</xsl:if>
		<xsl:if test="@refresh_on_printer">"action_refresh_on_printer":"<xsl:value-of select="@refresh_on_printer"/>",</xsl:if>
		<xsl:if test="@refresh_on_printer">"action_refresh_on_printer":"<xsl:value-of select="@refresh_on_printer"/>",</xsl:if>
		<xsl:if test="@object_context_id">"action_object_context_id":"<xsl:value-of select="@object_context_id"/>",</xsl:if>
		<xsl:if test="@is_submitafterback">"action_is_submitafterback":"<xsl:value-of select="@is_submitafterback"/>",</xsl:if>
		<xsl:if test="@data_asoc">"action_dataasoc":"<xsl:value-of select="@data_asoc"/>",</xsl:if>
		<xsl:if test="$onscript!='null'">"action_script":"<xsl:value-of select="$onscript"/>",</xsl:if>
		<xsl:if test="@post_function">"action_post_function":"<xsl:call-template name="escapeQuote"><xsl:with-param name="pText"><xsl:value-of select="@post_function"/></xsl:with-param></xsl:call-template>",</xsl:if>

	</xsl:template>
	<xsl:template name="render_icon">
		<xsl:param name="style_attr" select="'null'" /> 
		<xsl:if test="@link">"icon_link":"<xsl:value-of select="@link"/>",</xsl:if>
		<xsl:if test="$skin_path">"icon_path":"<xsl:value-of select="$url_total"/>",</xsl:if>
		<xsl:if test="icon/@file">"icon_file":"<xsl:value-of select="icon/@file"/>",</xsl:if>
		<xsl:if test="icon/@source">"icon_source":"<xsl:value-of select="icon/@source"/>",</xsl:if>
		<xsl:if test="icon/@class_image">"icon_class_image":"<xsl:value-of select="icon/@class_image"/>",</xsl:if>
		<xsl:if test="@tooltip">"icon_tooltip":"<xsl:value-of select="@tooltip"/>",</xsl:if>
		<xsl:if test="content_layout/@valignment">"icon_valigment":"<xsl:value-of select="content_layout/@valignment"/>",</xsl:if>
	</xsl:template>
	<xsl:template name="render_image">
		<xsl:param name="style_attr" select="'null'" /> 
		<xsl:if test="@link">"icon_link":"<xsl:value-of select="@link"/>",</xsl:if>
		<xsl:if test="image/@source">"icon_source":"<xsl:value-of select="image/@source"/>",</xsl:if>
		<xsl:if test="$skin_path">"icon_path":"<xsl:value-of select="$url_total"/>",</xsl:if>
		<xsl:if test="image/@file">"icon_file":"<xsl:value-of select="image/@file"/>",</xsl:if>
		<xsl:if test="image/@source">"icon_source":"<xsl:value-of select="image/@source"/>",</xsl:if>
		<xsl:if test="image/@class_image">"icon_class_image":"<xsl:value-of select="image/@class_image"/>",</xsl:if>
		<xsl:if test="@tooltip">"icon_tooltip":"<xsl:value-of select="@tooltip"/>",</xsl:if>
		<xsl:if test="content_layout/@valignment">"icon_valigment":"<xsl:value-of select="content_layout/@valignment"/>",</xsl:if>
	</xsl:template>

	<xsl:template name="escapeQuote">
	  <xsl:param name="pText" select="."/>
	  <xsl:if test="string-length($pText) >0">
	   <xsl:value-of select="substring-before(concat($pText, '&quot;'), '&quot;')"/>
	   <xsl:if test="contains($pText, '&quot;')">
	    <xsl:text>\"</xsl:text>
	    <xsl:call-template name="escapeQuote">
	      <xsl:with-param name="pText" select="substring-after($pText, '&quot;')"/>
	    </xsl:call-template>
	   </xsl:if>
	  </xsl:if>
	</xsl:template>
</xsl:stylesheet>
