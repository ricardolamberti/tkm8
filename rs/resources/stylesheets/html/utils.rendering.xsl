<?xml version="1.0"?>

<!--
	****************************************************************
		UTILITY TEMPLATES TO RENDER COMPONENT PARTS
	****************************************************************
-->


<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:strip-space elements="*"/>

<!-- 
	renders a label which may contain text and icon
 -->
<xsl:template name="render_compound_label">
	<xsl:choose>
		<!-- if there is label -->
		<xsl:when test="@title">
		           <div>
			           <xsl:if test="@title"><strong><xsl:value-of select="@title"/></strong></xsl:if>
			           <xsl:if test="@subtitle">
			           		<span class="pull-right text-muted">
	                             <em><xsl:value-of select="@subtitle"/></em>
	                   	 	</span>
	                   </xsl:if>
	               </div>
	               <div><xsl:value-of select="@label"/></div>
        </xsl:when>		
		<xsl:when test="@label">
			<xsl:choose>
				<!-- if there is icon... -->
				<xsl:when test="icon/@file">
					<xsl:choose>
						<!-- text on left -->
						<xsl:when test="content_layout/@text_position='left'">
							<xsl:value-of select="@label"/>
							<xsl:choose>
								<xsl:when test="content_layout/@text_icon_gap">
									<xsl:call-template name="render_icon">
										<xsl:with-param name="style_attr">margin-left:<xsl:value-of select="content_layout/@text_icon_gap"/>px;float:left;<xsl:if test="content_layout/@image_style"><xsl:value-of select="content_layout/@image_style"/></xsl:if></xsl:with-param>
									</xsl:call-template>
								</xsl:when>
								<xsl:otherwise>
									<xsl:call-template name="render_icon">
										<xsl:with-param name="style_attr">float:left;<xsl:if test="content_layout/@image_style"><xsl:value-of select="content_layout/@image_style"/></xsl:if></xsl:with-param>
									</xsl:call-template>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<!-- text on right -->
						<xsl:when test="content_layout/@text_position='right' or not(content_layout/@text_position)">
							<xsl:choose>
								<xsl:when test="content_layout/@text_icon_gap">
									<xsl:call-template name="render_icon">
										<xsl:with-param name="style_attr">margin-right:<xsl:value-of select="content_layout/@text_icon_gap"/>px;float:right;<xsl:if test="content_layout/@image_style"><xsl:value-of select="content_layout/@image_style"/></xsl:if></xsl:with-param>
									</xsl:call-template>
								</xsl:when>
								<xsl:otherwise>
									<xsl:call-template name="render_icon">
										<xsl:with-param name="style_attr">float:right;<xsl:if test="content_layout/@image_style"><xsl:value-of select="content_layout/@image_style"/></xsl:if></xsl:with-param>
									</xsl:call-template>
								</xsl:otherwise>
							</xsl:choose>
							<xsl:value-of select="@label"/>
						</xsl:when>
						<!-- text on top -->
						<xsl:when test="content_layout/@text_position='top'">
							<xsl:value-of select="@label"/>
							<br></br>
							<xsl:choose>
								<xsl:when test="content_layout/@text_icon_gap">
									<xsl:call-template name="render_icon">
										<xsl:with-param name="style_attr">margin-top:<xsl:value-of select="content_layout/@text_icon_gap"/>px;<xsl:if test="content_layout/@image_style"><xsl:value-of select="content_layout/@image_style"/></xsl:if></xsl:with-param>
									</xsl:call-template>
								</xsl:when>
								<xsl:otherwise>
									<xsl:call-template name="render_icon">
										<xsl:with-param name="style_attr"><xsl:if test="content_layout/@image_style"><xsl:value-of select="content_layout/@image_style"/></xsl:if></xsl:with-param>
									</xsl:call-template>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<!-- text on bottom -->
						<xsl:when test="content_layout/@text_position='bottom'">
							<xsl:choose>
								<xsl:when test="content_layout/@text_icon_gap">
									<xsl:call-template name="render_icon">
										<xsl:with-param name="style_attr">margin-bottom:<xsl:value-of select="content_layout/@text_icon_gap"/>px;<xsl:if test="content_layout/@image_style"><xsl:value-of select="content_layout/@image_style"/></xsl:if></xsl:with-param>
									</xsl:call-template>
								</xsl:when>
								<xsl:otherwise>
									<xsl:call-template name="render_icon">
										<xsl:with-param name="style_attr"><xsl:if test="content_layout/@image_style"><xsl:value-of select="content_layout/@image_style"/></xsl:if></xsl:with-param>
									</xsl:call-template>
								</xsl:otherwise>
							</xsl:choose>
							<br></br>
							<xsl:value-of select="@label"/>
						</xsl:when>
					</xsl:choose>
				</xsl:when>
				<!-- if there is no icon, only label -->
				<xsl:otherwise>
					<xsl:value-of select="@label"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		<!-- if there is no label, only icon -->
		<xsl:when test="icon/@file">
				<xsl:call-template name="render_icon">
						<xsl:with-param name="style_attr"><xsl:if test="content_layout/@image_style"><xsl:value-of select="content_layout/@image_style"/></xsl:if></xsl:with-param>
  			</xsl:call-template>
		</xsl:when>
	</xsl:choose>
</xsl:template>

<!-- 
	renders an icon which may be linked to an URL
 -->
<xsl:template name="render_icon">
	<xsl:param name="style_attr" select="'null'" /> 
	<xsl:choose>
		<xsl:when test="icon/@link">
			<a target="_blank">
				<xsl:attribute name="href"><xsl:value-of select="icon/@link"/></xsl:attribute>
				<xsl:call-template name="basic_render_icon">
					<xsl:with-param name="style_attr"><xsl:value-of select="icon/@style_image"/>;<xsl:value-of select="$style_attr"/></xsl:with-param>
				</xsl:call-template>
			</a>
		</xsl:when>
		<xsl:otherwise>
			<xsl:call-template name="basic_render_icon">
				<xsl:with-param name="style_attr"><xsl:value-of select="icon/@style_image"/>;<xsl:value-of select="$style_attr"/>;</xsl:with-param>
			</xsl:call-template>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- 
	just renders an icon
 -->
<xsl:template name="basic_render_icon">
	<xsl:param name="style_attr" select="'null'" /> 
	
	<xsl:choose>
		<xsl:when test="icon/@source='responsive'">
 	       <i>
 	       		<xsl:if test="@name"><xsl:attribute name="id"><xsl:value-of select="@name"/></xsl:attribute></xsl:if>
 	            <xsl:attribute name="class">
 	            	<xsl:value-of select="icon/@file"/>
 	            	<xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
 	            	<xsl:value-of select="icon/@class_image"/>
 	            </xsl:attribute>
				<xsl:if test="not($style_attr='') and not($style_attr='null')">
					<xsl:attribute name="style">
						<xsl:value-of select="$style_attr"/>
					</xsl:attribute>
				</xsl:if>
           </i> 
		</xsl:when>
		<xsl:otherwise>
			<img>
				<xsl:attribute name="border">0</xsl:attribute>
				<xsl:attribute name="style">
					<xsl:if test="not($style_attr='') and not($style_attr='null')"><xsl:value-of select="$style_attr"/></xsl:if>
					<xsl:if test="content_layout/@valignment='top'">vertical-align:text-top;</xsl:if>
					<xsl:if test="content_layout/@valignment='middle' or not(content_layout/@valignment)">vertical-align:middle;</xsl:if>
					<xsl:if test="content_layout/@valignment='bottom'">vertical-align:text-bottom;</xsl:if>
				</xsl:attribute>
				<!-- determine the source -->
				<xsl:choose>
					<xsl:when test="icon/@source='pss_icon'"> <!-- pss images path icon -->
						<xsl:attribute name="src">pss_icon<![CDATA[/]]><xsl:value-of select="icon/@file"/></xsl:attribute>
					</xsl:when>
					<xsl:when test="icon/@source='pssdata_resource'"> <!-- pss images path icon -->
						<xsl:attribute name="src">pssdata_resource<![CDATA[/]]><xsl:value-of select="icon/@file"/></xsl:attribute>
					</xsl:when>
					<xsl:when test="icon/@source='webapp_url'"> <!-- webapp relative path icon -->
						<xsl:attribute name="src"><xsl:value-of select="icon/@file"/></xsl:attribute>
					</xsl:when>
					<xsl:when test="image/@source='pss_data'"> <!-- webapp relative path icon -->
						<xsl:attribute name="src">pss_data<xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="icon/@file"/></xsl:attribute>
					</xsl:when>
					<xsl:otherwise> <!-- an icon which came from the skin -->
						<xsl:attribute name="src"><xsl:value-of select="$skin_path"/>/<xsl:value-of select="icon/@file"/></xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="@tooltip">
					<xsl:attribute name="title"><xsl:value-of select="@tooltip"/></xsl:attribute>
					<xsl:attribute name="alt"><xsl:value-of select="@tooltip"/></xsl:attribute>
				</xsl:if>	
				<xsl:if test="icon/@class_image"><xsl:attribute name="class"><xsl:value-of select="icon/@class_image"/></xsl:attribute></xsl:if>
				<xsl:for-each select="maximized">
				  <xsl:attribute name="onclick">
						maximizeImage(this,event,'
						<xsl:choose>
							<xsl:when test="icon/@source='pss_icon'"> <!-- pss images path icon -->
								pss_icon<![CDATA[/]]><xsl:value-of select="icon/@file"/>
							</xsl:when>
							<xsl:when test="icon/@source='pssdata_resource'"> <!-- pss images path icon -->
								pssdata_resource<![CDATA[/]]><xsl:value-of select="icon/@file"/>
							</xsl:when>
							<xsl:when test="icon/@source='webapp_url'"> <!-- webapp relative path icon -->
								<xsl:value-of select="icon/@file"/>
							</xsl:when>
							<xsl:when test="image/@source='pss_data'"> <!-- webapp relative path icon -->
								pss_data<xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="icon/@file"/>
							</xsl:when>
							<xsl:otherwise> <!-- an icon which came from the skin -->
								<xsl:value-of select="$skin_path"/>/<xsl:value-of select="icon/@file"/>
							</xsl:otherwise>
						</xsl:choose>
						');
						
					</xsl:attribute>
 				</xsl:for-each>
											
			</img>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>



<xsl:template name="basic_render_map">
	<xsl:variable name="fullname">dgf_<xsl:value-of select="@form_name"/>_fd-<xsl:value-of select="@name"/></xsl:variable>
		<INPUT type="HIDDEN" >
			<xsl:attribute name="value"><xsl:value-of select="gmap/@lat"/>, <xsl:value-of select="gmap/@lng"/></xsl:attribute>
			<!--  <xsl:call-template name="basic_generate_form_component"/>-->
		</INPUT>
		<DIV >
			<xsl:attribute name="style">
				width:<xsl:value-of select="@width"/>px;
				height:<xsl:value-of select="@height"/>px;
			</xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="@form_name"/>_<xsl:value-of select="@name"/>_gmap</xsl:attribute>
		</DIV>
				<script type="text/javascript" charset="ISO-8859-1">

	            setComponenteMapa("<xsl:value-of select="@form_name"/>_<xsl:value-of select="@name"/>_gmap");
				var mapa = initializeMapa();
		        cleanMapa();
		        
				<xsl:if test="gmap/waypoint">
					<xsl:if test="gmap/@modo='polygon'">
				        var area= new Array();
						<xsl:for-each select="gmap/waypoint">
			    			area[<xsl:value-of select="position()-1"/>]= new google.maps.LatLng(<xsl:value-of select="@lat"/>,<xsl:value-of select="@lng"/>);
						</xsl:for-each>
							addPol(area, "#669933", 5, 0.7, "#996633", 0.4);
					</xsl:if>
					<xsl:if test="gmap/@modo='points'">
						addDir('<xsl:value-of select="gmap/@mapdir"/>', '<xsl:value-of select="gmap/@mapname"/>',null);
				        var icon=null; 
						<xsl:for-each select="gmap/waypoint">
						icon=iconMapaDefa; 
				   		    <xsl:for-each select="link"> icon = new google.maps.MarkerImage(
							'<xsl:choose><xsl:when test="image/@source='webapp_url'"><xsl:value-of select="image/@file"/></xsl:when>
										<xsl:otherwise><xsl:value-of select="image/@source"/><xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="image/@file"/></xsl:otherwise>
							</xsl:choose>',
							new google.maps.Size(40, 60),
							new google.maps.Point(0, 0),
							new google.maps.Point(20, 60)
							);
				   		    
				   			</xsl:for-each>
							addDir('<xsl:value-of select="@mapdir"/>','<xsl:value-of select="@mapname"/>',icon);
						</xsl:for-each>
					</xsl:if>
		       </xsl:if>
	        

    			<xsl:if test="gmap/@lat">
    			    var pointer = { lat: <xsl:value-of select="gmap/@lat"/>, lng: <xsl:value-of select="gmap/@lng"/> };
			   		var marker=addPointMapa(mapa,pointer,iconMapaDefa,"");
			   		
				</xsl:if>
				dibujarMapa(mapa);
			<xsl:if test="@editable!='false'">
			        google.maps.event.addListener(mapa,"click", function(event) {
			        	cleanMarcas();
			        	addPointMapa(this,event.latLng,iconMapaDefa,"");
			        	document.getElementById("<xsl:value-of select="$fullname"/>").value=""+event.latLng.lat()+","+event.latLng.lng();
			        });
	       </xsl:if>
		       
    	
	</script>
</xsl:template>

<xsl:template name="basic_render_image">
	<xsl:param name="style_attr" select="'null'" /> 
	<img border="0">
		<xsl:call-template name="internal_basic_render_image">
			<xsl:with-param name="style_attr"><xsl:value-of select="$style_attr"/></xsl:with-param>
		</xsl:call-template> 
	</img>
</xsl:template>
<xsl:template name="internal_basic_render_image">
	<xsl:param name="style_attr" select="'null'" /> 
		<!-- CSS class is selected using the component state -->
		<xsl:if test="@class_image"><xsl:attribute name="class"><xsl:value-of select="@class_image"/></xsl:attribute></xsl:if>
		<xsl:attribute name="style">
			<xsl:if test="not($style_attr='') and not($style_attr='null')"><xsl:value-of select="$style_attr"/></xsl:if>
			<xsl:if test="content_layout/@valignment='top'">vertical-align:text-top;</xsl:if>
			<xsl:if test="content_layout/@valignment='middle' or not(content_layout/@valignment)">vertical-align:middle;</xsl:if>
			<xsl:if test="content_layout/@valignment='bottom'">vertical-align:text-bottom;</xsl:if>
		</xsl:attribute>
		<!-- determine the source -->
		<xsl:choose>
			<xsl:when test="image/@source='pss_icon'"> <!-- pss images path icon -->
				<xsl:attribute name="src">pss_icon<xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="image/@file"/></xsl:attribute>
			</xsl:when>
			<xsl:when test="image/@source='webapp_url'"> <!-- webapp relative path icon -->
				<xsl:attribute name="src"><xsl:value-of select="image/@file"/></xsl:attribute>
			</xsl:when>
			<xsl:when test="image/@source='pss_data'"> <!-- webapp relative path icon -->
				<xsl:attribute name="src">pss_data<xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="image/@file"/></xsl:attribute>
			</xsl:when>
			<xsl:when test="image/@source='pssdata_resource'"> <!-- webapp relative path icon -->
				<xsl:attribute name="src">pssdata_resource<xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="image/@file"/></xsl:attribute>
			</xsl:when>
			<xsl:otherwise> <!-- an icon which came from the skin -->
				<xsl:attribute name="src"><xsl:value-of select="$skin_path"/><xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="image/@file"/></xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>

		<xsl:if test="image/@type=2">
  	    	<xsl:if test="image/@width"><xsl:attribute name="width"><xsl:value-of select="image/@width"/></xsl:attribute></xsl:if>
  	    	<xsl:if test="image/@height"><xsl:attribute name="height"><xsl:value-of select="image/@height"/></xsl:attribute></xsl:if>
  	    </xsl:if>
</xsl:template>

<xsl:template name="basic_render_flash">

      
    <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0"  title="Publicity">
		<param name="movie" >
			<xsl:choose>
				<xsl:when test="icon/@source='pss_icon'"> <!-- pss images path icon -->
					<xsl:attribute name="value">pss_icon<xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="flash/@file"/></xsl:attribute>
				</xsl:when>
				<xsl:when test="icon/@source='pssdata_resource'"> <!-- pss images path icon -->
					<xsl:attribute name="value">pssdata_resource<xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="flash/@file"/></xsl:attribute>
				</xsl:when>
				<xsl:when test="icon/@source='webapp_url'"> <!-- webapp relative path icon -->
					<xsl:attribute name="value"><xsl:value-of select="flash/@file"/></xsl:attribute>
				</xsl:when>
				<xsl:otherwise> <!-- an icon which came from the skin -->
					<xsl:attribute name="value"><xsl:value-of select="$skin_path"/><xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="flash/@file"/></xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
		</param>
        <embed  pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash">
  	    <xsl:if test="flash/@width"><xsl:attribute name="width"><xsl:value-of select="flash/@width"/></xsl:attribute></xsl:if>
  	    <xsl:if test="flash/@height"><xsl:attribute name="height"><xsl:value-of select="flash/@height"/></xsl:attribute></xsl:if>
  	    <xsl:attribute name="loop"><xsl:value-of select="flash/@loop"/></xsl:attribute>
  	    <xsl:attribute name="quality"><xsl:value-of select="flash/@quality"/></xsl:attribute>
   		<!-- determine the source -->
		<xsl:choose>
			<xsl:when test="icon/@source='pss_icon'"> <!-- pss images path icon -->
				<xsl:attribute name="src">pss_icon<xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="flash/@file"/></xsl:attribute>
			</xsl:when>
			<xsl:when test="icon/@source='pssdata_resource'"> <!-- pss images path icon -->
				<xsl:attribute name="src">pssdata_resource<xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="flash/@file"/></xsl:attribute>
			</xsl:when>
			<xsl:when test="icon/@source='webapp_url'"> <!-- webapp relative path icon -->
				<xsl:attribute name="src"><xsl:value-of select="flash/@file"/></xsl:attribute>
			</xsl:when>
			<xsl:otherwise> <!-- an icon which came from the skin -->
				<xsl:attribute name="src"><xsl:value-of select="$skin_path"/><xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="flash/@file"/></xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
		</embed>
 		<param name="movie" >
			<xsl:choose>
				<xsl:when test="icon/@source='pss_icon'"> <!-- pss images path icon -->
					<xsl:attribute name="value">pss_icon<xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="flash/@file"/></xsl:attribute>
				</xsl:when>
				<xsl:when test="icon/@source='webapp_url'"> <!-- webapp relative path icon -->
					<xsl:attribute name="value"><xsl:value-of select="flash/@file"/></xsl:attribute>
				</xsl:when>
				<xsl:otherwise> <!-- an icon which came from the skin -->
					<xsl:attribute name="value"><xsl:value-of select="$skin_path"/><xsl:text disable-output-escaping="yes">/</xsl:text><xsl:value-of select="flash/@file"/></xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
		</param>
	    </object>
</xsl:template>

</xsl:stylesheet> 