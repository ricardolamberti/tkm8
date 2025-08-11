<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text" omit-xml-declaration="yes"/>
<xsl:strip-space elements="*"/>

<xsl:template match="/">
	<xsl:apply-templates select="*"/>
</xsl:template>

<xsl:template match="page/message" />

<xsl:template match="page/header" />
<xsl:template match="action" />
<xsl:template match="panel[starts-with(@name,'filter_pane')]" />
<xsl:template match="div_responsive[@name='filter_header']" />
<xsl:template match="div_responsive[@name='filter_body ']" />
<xsl:template match="form_responsive[starts-with(@name,'filter_pane')]" />


 <xsl:template name="addSeparadores">
      <xsl:param name="pStart"/>
      <xsl:param name="pEnd"/>
      <xsl:text>&#009;</xsl:text>
      <xsl:if test="$pStart &lt; $pEnd">
          <xsl:call-template name="addSeparadores">
           <xsl:with-param name="pStart" select="$pStart+1"/>
           <xsl:with-param name="pEnd" select="$pEnd"/>
          </xsl:call-template>
      </xsl:if>
     </xsl:template>
<xsl:template match="win_list_json">
{
  "draw": <xsl:value-of select="@draw"/>,
  "recordsTotal": <xsl:value-of select="info/@nro_records"/>,
  "recordsFiltered": <xsl:value-of select="info/@nro_records"/>,
	 <xsl:if test="@has_action_bar='true'">
		   <xsl:variable name="v_provider"><xsl:value-of select="@obj_provider"/></xsl:variable>
			"provider": "<xsl:value-of select="@obj_provider"/>"
			,"zobject": "<xsl:value-of select="@zobject"/>"
			,"multiselect": "<xsl:value-of select="@is_multiple_select"/>"
			,"islineselect": "<xsl:value-of select="@is_line_select"/>"
			,"moreselection": "<xsl:value-of select="select_info/@has_more_selection"/>"
			<xsl:for-each select="actions">
				,"actionbarname": "<xsl:value-of select="@name"/>"
	 		    ,"actionMap": [
					<xsl:for-each select="action">
						{
						"action": "<xsl:value-of select="@id"/>",
						"win": <xsl:value-of select="@win"/>,
						<xsl:if test="not_allowed_win">
							"norows": [<xsl:for-each select="not_allowed_win">"<xsl:value-of select="@id"/>"<xsl:if test="position()!=last()">,</xsl:if></xsl:for-each>],
						</xsl:if>
						<xsl:if test="allowed_win">
							"rows": [<xsl:for-each select="allowed_win">"<xsl:value-of select="@id"/>"<xsl:if test="position()!=last()">,</xsl:if></xsl:for-each>],
						</xsl:if>
						"multiple": "<xsl:value-of select="@multiple"/>"
						}<xsl:if test="position()!=last()">,</xsl:if>
					</xsl:for-each>
				],
			</xsl:for-each>	
	 </xsl:if>
   "data": [
	<xsl:for-each select="rows/row">
	{
		 <xsl:if test="@zobject">
			"DT_RowId":"<xsl:value-of select="@zobject" />",
		 </xsl:if>

		 <xsl:choose>
			 <xsl:when test="rowattr">
				"DT_RowData":{
				<xsl:for-each select="rowattr">
					"<xsl:value-of select="@key" />":"<xsl:value-of select="@value" />"<xsl:if test="position()!=last()">,</xsl:if>
				</xsl:for-each>
				},
			 </xsl:when>
			 <xsl:when test="rowdata">
				"DT_RowData":{
				<xsl:for-each select="rowdata">
					"<xsl:value-of select="@key" />":"<xsl:value-of select="@value" />"<xsl:if test="position()!=last()">,</xsl:if>
				</xsl:for-each>
				},
			 </xsl:when>
			 <xsl:when test="d/@foreground or d/@background">
				"DT_RowData":{
					 <xsl:for-each select="d">
						<xsl:if test="@col_name">
							"<xsl:value-of select="@col_name"/>_background":"<xsl:value-of select="@background" />",
							"<xsl:value-of select="@col_name"/>_foreground":"<xsl:value-of select="@foreground" />"<xsl:if test="position()!=last()">,</xsl:if>
						</xsl:if>
					</xsl:for-each>
				},
			 </xsl:when>
		 </xsl:choose>	
		 <xsl:if test="@rowclass">
         	"DT_RowClass":"<xsl:value-of select="@rowclass"/>",
         </xsl:if>
            "selected_internal":"<xsl:value-of select="@selected"/>",
  		 <xsl:for-each select="d">
			<xsl:if test="@col_name">
				"<xsl:value-of select="@col_name"/>":
				"<xsl:choose>
					<xsl:when test=".=''">
					  <xsl:text disable-output-escaping="yes"> </xsl:text>
					</xsl:when>
					<xsl:when test="html_data">
						<xsl:for-each select="html_data">
						  <xsl:value-of select="." disable-output-escaping="yes"/>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						  <xsl:value-of select="." />
					</xsl:otherwise>
				</xsl:choose>"
				<xsl:if test="position()!=last()">,</xsl:if>
			</xsl:if>
		 </xsl:for-each>
	}<xsl:if test="position()!=last()">,</xsl:if>
	</xsl:for-each>
   ]
   <xsl:for-each select="footer">
		,"foot": [
		<xsl:for-each select="column">
			{
            "col_name": "<xsl:value-of select="@col_name"/>",
			"align": "<xsl:value-of select="@halignment"/>",
			"value": "<xsl:choose>
				<xsl:when test="not(value/.='')">
					<xsl:value-of select="value/." />
				</xsl:when>
				<xsl:otherwise>
					<xsl:text disable-output-escaping="yes"> </xsl:text>
				</xsl:otherwise>
			</xsl:choose>"}<xsl:if test="position()!=last()">,</xsl:if>
		</xsl:for-each>
		]
   </xsl:for-each>

}
</xsl:template>
<xsl:template match="win_list_bigdata">
{
  "draw": <xsl:value-of select="@draw"/>,
  "recordsTotal": <xsl:value-of select="info/@total_records"/>,
  "recordsFiltered": <xsl:value-of select="info/@visible_records"/>,
  "recordsError": <xsl:value-of select="info/@error_records"/>,
  "recordsHide": <xsl:value-of select="info/@hide_records"/>,
  <xsl:for-each select="info/customInfo">
	  "<xsl:value-of select="@name"/>": <xsl:value-of select="@value"/>,
  </xsl:for-each>
 	 <xsl:if test="@has_action_bar='true'">
		   <xsl:variable name="v_provider"><xsl:value-of select="@obj_provider"/></xsl:variable>
			"provider": "<xsl:value-of select="@obj_provider"/>"
			,"zobject": "<xsl:value-of select="@zobject"/>"
			,"multiselect": "<xsl:value-of select="@is_multiple_select"/>"
			,"islineselect": "<xsl:value-of select="@is_line_select"/>"
			,"moreselection": "<xsl:value-of select="select_info/@has_more_selection"/>"
			<xsl:for-each select="actions">
				,"actionbarname": "<xsl:value-of select="@name"/>"
	 		    ,"actionMap": [
					<xsl:for-each select="action">
						{
						"action": "<xsl:value-of select="@id"/>",
						"win": <xsl:value-of select="@win"/>,
						<xsl:if test="not_allowed_win">
							"norows": [<xsl:for-each select="not_allowed_win">"<xsl:value-of select="@id"/>"<xsl:if test="position()!=last()">,</xsl:if></xsl:for-each>],
						</xsl:if>
						<xsl:if test="allowed_win">
							"rows": [<xsl:for-each select="allowed_win">"<xsl:value-of select="@id"/>"<xsl:if test="position()!=last()">,</xsl:if></xsl:for-each>],
						</xsl:if>
						"multiple": "<xsl:value-of select="@multiple"/>"
						}<xsl:if test="position()!=last()">,</xsl:if>
					</xsl:for-each>
				]
			</xsl:for-each>	
	 </xsl:if>
   ,"data": [
	<xsl:for-each select="rows/row">
	{
		 <xsl:if test="@zobject">
			"DT_RowId":"<xsl:value-of select="@zobject" />",
		 </xsl:if>

		 <xsl:choose>
			 <xsl:when test="rowattr">
				"DT_RowData":{
				<xsl:for-each select="rowattr">
					"<xsl:value-of select="@key" />":"<xsl:value-of select="@value" />"<xsl:if test="position()!=last()">,</xsl:if>
				</xsl:for-each>
				},
			 </xsl:when>
			 <xsl:when test="rowdata">
				"DT_RowData":{
				<xsl:for-each select="rowdata">
					"<xsl:value-of select="@key" />":"<xsl:value-of select="@value" />"<xsl:if test="position()!=last()">,</xsl:if>
				</xsl:for-each>
				},
			 </xsl:when>
			 <xsl:when test="d/@foreground or d/@background">
				"DT_RowData":{
					 <xsl:for-each select="d">
						<xsl:if test="@col_name">
							"<xsl:value-of select="@col_name"/>_background":"<xsl:value-of select="@background" />",
							"<xsl:value-of select="@col_name"/>_foreground":"<xsl:value-of select="@foreground" />"<xsl:if test="position()!=last()">,</xsl:if>
						</xsl:if>
					</xsl:for-each>
				},
			 </xsl:when>
		 </xsl:choose>	
		 <xsl:if test="@rowclass">
         	"DT_RowClass":"<xsl:value-of select="@rowclass"/>",
         </xsl:if>
            "selected":"<xsl:value-of select="@selected"/>",
  		 <xsl:for-each select="d">
			<xsl:if test="@col_name">
				"<xsl:value-of select="@col_name"/>":
				"<xsl:choose>
					<xsl:when test=".=''">
					  <xsl:text disable-output-escaping="yes"> </xsl:text>
					</xsl:when>
					<xsl:when test="html_data">
						<xsl:for-each select="html_data">
						  <xsl:value-of select="." disable-output-escaping="yes"/>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						  <xsl:value-of select="." />
					</xsl:otherwise>
				</xsl:choose>"
				<xsl:if test="position()!=last()">,</xsl:if>
			</xsl:if>
		 </xsl:for-each>
	}<xsl:if test="position()!=last()">,</xsl:if>
	</xsl:for-each>
   ]
   <xsl:for-each select="footer">
		,"foot": [
		<xsl:for-each select="column">
			{
            "col_name": "<xsl:value-of select="@col_name"/>",
			"align": "<xsl:value-of select="@halignment"/>",
			"value": "<xsl:choose>
				<xsl:when test="not(value/.='')">
					<xsl:value-of select="value/." />
				</xsl:when>
				<xsl:otherwise>
					<xsl:text disable-output-escaping="yes"> </xsl:text>
				</xsl:otherwise>
			</xsl:choose>"}<xsl:if test="position()!=last()">,</xsl:if>
		</xsl:for-each>
		]
   </xsl:for-each>
   <xsl:for-each select="error">
	   ,"error": "<xsl:value-of select="@message"/>"
   </xsl:for-each>	
}
</xsl:template>
       <xsl:template match="text()" />

</xsl:stylesheet> 
