<?xml version="1.0"?>
<!--
	****************************************************************
		UTILITY TEMPLATES TO GENERATE ACTION INVOCATIONS
	****************************************************************
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:strip-space elements="*"/>
	<xsl:template name="generate_action_on_click">
		<xsl:param name="onscript" select="'null'" /> 
		<xsl:for-each select="action">
			<xsl:attribute name="onClick">
				<xsl:call-template name="generate_action_invokation">
					<xsl:with-param name="onscript" select="$onscript" />
				</xsl:call-template>
			</xsl:attribute>

		</xsl:for-each>

	</xsl:template>
	<xsl:template name="generate_action_on_dblclick">
		<xsl:for-each select="action">
			<xsl:attribute name="onDblClick">
			   	clearClick();
				<xsl:call-template name="generate_action_invokation"/>
			</xsl:attribute>

		</xsl:for-each>

	</xsl:template>
	<xsl:template name="generate_action_ondrop">
		<xsl:for-each select="action">
			<xsl:attribute name="ondragover">
				<xsl:if test="@dropclassaccept">
					if ("<xsl:value-of select="../@dropclassaccept"/>".indexOf(event.dataTransfer.getData("class"))!=-1)
				</xsl:if>
				event.preventDefault();
			</xsl:attribute>
			<xsl:attribute name="ondrop">
				event.preventDefault();
				<xsl:call-template name="generate_action_invokation"/>
			</xsl:attribute>
	
		</xsl:for-each>

	</xsl:template>
	<xsl:template name="generate_action_onblur">
		<xsl:for-each select="action">
			<xsl:attribute name="onblur">
				focusObject = null;
				<xsl:call-template name="generate_action_invokation"/>
			</xsl:attribute>

		</xsl:for-each>

	</xsl:template>

	<xsl:template name="generate_action_on_click_forced">
		<xsl:param name="row_id" select="'null'" /> 
		<xsl:param name="key" select="'null'" /> 
		<xsl:param name="descr" select="'null'" /> 
		<xsl:for-each select="action">
			<xsl:attribute name="onClick">
				
			    rs_forced_with_key(document.getElementById('<xsl:value-of select="$row_id"/>'),'<xsl:value-of select="$key"/>','<xsl:value-of select="$descr"/>',event);
				<xsl:call-template name="generate_action_invokation"/>
				this.disabled=false;
			    rs_forced(null,event);
				 var evt = event ? event:window.event;
				 if (evt.stopPropagation)    evt.stopPropagation();
				 if (evt.cancelBubble!=null) evt.cancelBubble = true;

			</xsl:attribute>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="generate_action_on_right_click_cell">
		<xsl:attribute name="oncontextmenu">
		    rs_forced_with_key_cell(this,'','',event);
			contextMenuCell(this,event,'<xsl:value-of select="@obj_provider"/>_toolbar','<xsl:value-of select="$skin_path"/>');
			return false;
		</xsl:attribute>
	</xsl:template>

	<xsl:template name="generate_action_on_right_click">
		<xsl:attribute name="oncontextmenu">
		    rs_forced(this,null);
			contextMenu(this,event,'<xsl:value-of select="@obj_provider"/>_toolbar','<xsl:value-of select="$skin_path"/>');
		    rs_forced(null,null);
			return false;
		</xsl:attribute>
	</xsl:template>

	<xsl:template name="generate_action_on_double_click">
		<xsl:param name="row_id" select="'null'" /> 
		<xsl:param name="key" select="'null'" /> 
		<xsl:param name="descr" select="'null'" /> 
		<xsl:for-each select="action">
			<xsl:attribute name="onDblClick">
				dc1(this,event,function(that) {
					<xsl:call-template name="generate_action_invokation">
						<xsl:with-param name="that">that</xsl:with-param> 
					</xsl:call-template>
				},'<xsl:value-of select="$key"/>','<xsl:value-of select="$descr"/>');
			</xsl:attribute>
			<xsl:attribute name="onKeyDown">
				return dc3(this,event,function(that) { 
					<xsl:call-template name="generate_action_invokation">
						<xsl:with-param name="that">that</xsl:with-param> 
					</xsl:call-template>
				});
			</xsl:attribute>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template name="generate_action_on_double_click_cell">
		<xsl:param name="row_id" select="'null'" /> 
		<xsl:for-each select="action">
			<xsl:attribute name="onDblClick">
				dc2(this,event,function(that){
					<xsl:call-template name="generate_action_invokation">
						<xsl:with-param name="that">that</xsl:with-param> 
					</xsl:call-template>
				});
			</xsl:attribute>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="generate_action_on_enter">
			<xsl:attribute name="onKeydown">
				return dc4(event,<xsl:if test="constraints/@datatype='JFLOAT'">true</xsl:if><xsl:if test="constraints/@datatype!='JFLOAT'">false</xsl:if>);
			</xsl:attribute>
	</xsl:template>


	<!--
	Generates an action invokation:
	- asking confirmation if needed
	- invoking the action and telling if it has to submit data or not
	-->
	<xsl:template name="generate_action_invokation">
		<xsl:param name="onscript" select="'null'" /> 
		<xsl:param name="dynamic_owner" select="'null'" /> 
		<xsl:param name="that" select="'this'" /> 
		<xsl:if test="@confirmation">
 			ra(<xsl:value-of select="$that"/>,true,'<xsl:value-of select="@confirmation"/>','<xsl:value-of select="@target"/>',function(that) {
					<xsl:call-template name="generate_action_invokation_do">
						<xsl:with-param name="onscript" select="$onscript" /> 
						<xsl:with-param name="dynamic_owner" select="$dynamic_owner" /> 
						<xsl:with-param name="that">that</xsl:with-param> 
					</xsl:call-template>
			});
		</xsl:if>
		<xsl:if test="not(@confirmation)">
		 	ra(<xsl:value-of select="$that"/>,false,'<xsl:value-of select="@perderdatos"/>','<xsl:value-of select="@target"/>',function(that) {
					<xsl:call-template name="generate_action_invokation_do">
						<xsl:with-param name="onscript" select="$onscript" /> 
						<xsl:with-param name="dynamic_owner" select="$dynamic_owner" /> 
						<xsl:with-param name="that">that</xsl:with-param> 
					</xsl:call-template>
			});
		</xsl:if>
		cev(event);
	</xsl:template>
		
	<xsl:template name="generate_action_invokation_do">
		<xsl:param name="onscript" select="'null'" /> 
		<xsl:param name="dynamic_owner" select="'null'" /> 
		<xsl:param name="that" select="this" /> 
		<xsl:if test="@owner_object_owner_id">
			prf('<xsl:value-of select="@obj_provider"/>','<xsl:value-of select="@owner_obj_provider"/>','<xsl:value-of select="@owner_object_owner_id"/>','<xsl:value-of select="@owner_id_action"/>');
		</xsl:if>
		<xsl:choose>
			<xsl:when test="@function">
				<xsl:value-of select="@function"/>
			</xsl:when>
			<xsl:otherwise> goTo(<xsl:value-of select="$that"/>,'<xsl:value-of select="@target"/><xsl:if test="@data_string and not(@data_string='')">?<xsl:value-of select="@data_string"/></xsl:if>',
				<xsl:choose><xsl:when test="@is_submit='true'">true</xsl:when><xsl:otherwise>false</xsl:otherwise></xsl:choose>,
				'<xsl:value-of select="@obj_provider" />',
				<xsl:if test="$dynamic_owner='null'">
					<xsl:choose><xsl:when test="@object_owner_id">'<xsl:value-of select="@object_owner_id"/>'</xsl:when><xsl:otherwise>null</xsl:otherwise></xsl:choose>,
				</xsl:if>
				<xsl:if test="$dynamic_owner!='null'">
					$('#<xsl:value-of select="$dynamic_owner"/>').val(),
				</xsl:if>
				'<xsl:value-of select="@ajax_container"/>',
				'<xsl:value-of select="@resolve_string"/>',
				'<xsl:value-of select="@id_action"/>',
				<xsl:value-of select="@asinc"/>,
				<xsl:value-of select="@cancelable"/>, ev(event),null,<xsl:if test="not(@upload_data)">false</xsl:if><xsl:value-of select="@upload_data"/>, '<xsl:value-of select="@data_asoc"/>',
				'<xsl:value-of select="@special_selector"/>','<xsl:value-of select="@back_on_printer"/>','<xsl:value-of select="@refresh_on_printer"/>'
				<xsl:choose><xsl:when test="@object_context_id">,'<xsl:value-of select="@object_context_id"/>'</xsl:when><xsl:otherwise>,null</xsl:otherwise></xsl:choose>
				<xsl:choose><xsl:when test="@is_submitafterback">,<xsl:value-of select="@is_submitafterback"/></xsl:when><xsl:otherwise>,true</xsl:otherwise></xsl:choose>
				<xsl:if test="$onscript!='null'">,function() { <xsl:value-of select="$onscript"/> } </xsl:if>
				);
				<xsl:if test="@post_function">
					<script TYPE="POSTSCRIPT">
						<xsl:value-of select="@post_function"/>
	    			</script>
				</xsl:if>
				
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	


</xsl:stylesheet> 
