<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">



		<!-- build the page structure taking the values which come in the header -->
		<xsl:template match="page/header">
			<head>
				<xsl:for-each select="layouts/css">
				    <link rel="stylesheet">
				    	<xsl:attribute name="href"><xsl:value-of select="@source"/></xsl:attribute>
				    </link>
				    <link rel="stylesheet">
				    	<xsl:attribute name="href"><xsl:value-of select="@source_colors"/></xsl:attribute>
				    </link>
			    </xsl:for-each>
			</head>
			<body class="help-body">
				<xsl:apply-templates select="*" />
			</body>
		</xsl:template>
		<xsl:template match="subtitle">
				<h2>
						<xsl:value-of select="@title"/>
				</h2>
		</xsl:template>		
		<xsl:template match="help_win_form">
				<a><xsl:attribute name="id"/><xsl:value-of select="title"/></a>
				<h1>
						<strong><xsl:value-of select="@title"/></strong>
				</h1>
				<p><xsl:value-of select="@copete"/></p>
				<div>
					<xsl:attribute name="style">max-height:200px; overflow-y:hidden</xsl:attribute>
					<img>
						<xsl:attribute name="id">img</xsl:attribute>
						<xsl:attribute name="class">conborde</xsl:attribute>
						<xsl:attribute name="width">270px</xsl:attribute>
						<xsl:attribute name="object-fit">cover</xsl:attribute>
					</img>
				</div>
				<script>
					parent.takePicture(this.document.getElementById('img')); 
				</script>
				<xsl:apply-templates select="*" />

				
		</xsl:template>
		<xsl:template match="action">
					<p><b><xsl:value-of select="@name"/></b>: <xsl:value-of select="@help"/><span style="#792929"><xsl:value-of select="@reason"/></span></p>
		</xsl:template>		
		<xsl:template match="help_win_grid">
				<a><xsl:attribute name="id"/><xsl:value-of select="title"/></a>
				<h1>
						<strong><xsl:value-of select="@title"/></strong>
				</h1>
				<p><xsl:value-of select="@copete"/></p>
				<xsl:apply-templates select="*" />
				
		</xsl:template>	
		<xsl:template match="help_win_json">
				<a><xsl:attribute name="id"/><xsl:value-of select="title"/></a>
				<h1 style="width: 100%; ">
						<strong><xsl:value-of select="@title"/></strong>
				</h1>
				<p><xsl:value-of select="@copete"/></p>
				<div>
					<xsl:attribute name="style">max-height:200px; overflow-y:hidden</xsl:attribute>
					<img>
						<xsl:attribute name="id">img</xsl:attribute>
						<xsl:attribute name="class">conborde</xsl:attribute>
						<xsl:attribute name="width">270px</xsl:attribute>
						<xsl:attribute name="object-fit">cover</xsl:attribute>
					</img>
				</div>
				<script>
					parent.takePicture(this.document.getElementById('img')); 
				</script>
				<xsl:apply-templates select="*" />
				
		</xsl:template>	
		<xsl:template match="help_win_list">
				<a><xsl:attribute name="id"/><xsl:value-of select="title"/></a>
				<h1 style="width: 100%; ">
						<strong><xsl:value-of select="@title"/></strong>
				</h1>
				<p><xsl:value-of select="@copete"/></p>
				<div>
					<xsl:attribute name="style">max-height:200px; overflow-y:hidden</xsl:attribute>
					<img>
						<xsl:attribute name="id">img</xsl:attribute>
						<xsl:attribute name="class">conborde</xsl:attribute>
						<xsl:attribute name="width">270px</xsl:attribute>
						<xsl:attribute name="object-fit">cover</xsl:attribute>
					</img>
				</div>
				<script>
					parent.takePicture(this.document.getElementById('img')); 
				</script>
				<xsl:apply-templates select="*" />
				
		</xsl:template>
		
		<xsl:template match="help_text_field_responsive">
				<p>
					<b><xsl:value-of select="@label"/>:</b>	<xsl:value-of select="@help"/>
				</p>
				<xsl:apply-templates select="*" />
				
		</xsl:template>
		<xsl:template match="help_win_lov_responsive">
				<p>
					<b><xsl:value-of select="@label"/>:</b>	<xsl:value-of select="@help"/>
				</p>
				<xsl:apply-templates select="*" />
				
		</xsl:template>
		<xsl:template match="help_combobox_responsive">
				<p>
					<b><xsl:value-of select="@label"/>:</b>	<xsl:value-of select="@help"/>
				</p>
				<xsl:apply-templates select="*" />
				
		</xsl:template>
		<xsl:template match="help_date_chooser_responsive">
				<p>
					<b><xsl:value-of select="@label"/>:</b>	<xsl:value-of select="@help"/>
				</p>
				<xsl:apply-templates select="*" />
				
		</xsl:template>		
		<xsl:template match="help_check_box_responsive">
				<p>
					<b><xsl:value-of select="@label"/>:</b>	<xsl:value-of select="@help"/>
				</p>
				<xsl:apply-templates select="*" />
				
		</xsl:template>	
		<xsl:template match="help_radio_button_set_responsive">
				<p>
					<b><xsl:value-of select="@label"/>:</b>	<xsl:value-of select="@help"/>
				</p>
				<xsl:apply-templates select="*" />
				
		</xsl:template>	
</xsl:stylesheet>
