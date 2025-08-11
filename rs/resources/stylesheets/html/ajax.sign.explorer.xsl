<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- global instructions -->
	<xsl:strip-space elements="*" />

	<xsl:template match="message">
		<html>
		
			<script type="text/javascript" src="js/functions.js"></script>
			<script type="text/vbscript" src="js/functions.vbs"></script>
			<script type="text/vbscript" src="js/ajax.sign.explorer.vbs"></script>
			<script type="text/javascript">
			showCSPActiveX();
			</script>


		<body>
				<div class="frame">
					<div class="content">
						<p>
							<script type="text/javascript">
							function onEnter(event){ 
								var submiter =	null;
								submiter =window.parent.document.getElementById(window.parent.anchorSubmit);
									if (navigator.appName=="Netscape") { 
										var	evt =window.parent.document.createEvent("MouseEvents");
										evt.initMouseEvent("click", true, true,
										window, 0, 0, 0, 0, 0, false,false,false, false, 0, null);
										submiter.dispatchEvent(evt); } else {
										submiter.click(); 
									} 
								}

								function generar(e) { 
									var iframe_name = window.name; 
									var realInput =iframe_name.substring(7);
									window.parent.document.getElementById(realInput).value= 'GENERADO'; 
									document.getElementById('CertReqForm').submit();
								}
					
							</script>

						</p>
					</div>
				</div>
				<form name="CertReqForm" id="CertReqForm" action="http://192.168.1.105:8081/ejbca/democertreq" enctype="x-www-form-encoded" method="POST">
					<fieldset>
						<input name="certificateprofile" type="hidden">
							<xsl:attribute name="value">USER</xsl:attribute>
						</input>
						<input name="entityprofile" type="hidden">
							<xsl:attribute name="value">USUARIOS</xsl:attribute>
						</input>
						<input name="user" type="hidden">
							<xsl:attribute name="value">CN=<xsl:value-of select="text/." /></xsl:attribute>
						</input>
						<input name="classid" type="hidden" value="" />
						<input name="pkcs10req" type="hidden" value="" />
						<input name="containername" type="hidden" value="" />
						<input name="keysize" type="hidden" value="1024" />
						<input name="enchancedeid" type="hidden" value="false" />
						<input name="exportable" type="hidden" value="true" />

						<label for="CspProvider">Provider:</label>
						<select name="CspProvider" id="CspProvider"
							accesskey="p">
						</select>
						<br />

						<label for="dummy"></label>
						<input type="button" value="Generar Firma"
							name="GenReq" onclick="generar(event)" />
					</fieldset>
				</form>

				<script type="text/vbscript">startup()</script>






			</body>
		</html>
	</xsl:template>


</xsl:stylesheet> 
