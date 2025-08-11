<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- global instructions -->
	<xsl:strip-space elements="*"/>

	<xsl:template match="message" >
	
	
	
	
	
	
	
	
		<html>
			<script type="text/javascript" src="js/functions.js"></script>
			<script type="text/vbscript" src="js/functions.vbs"></script>
			<script type="text/javascript">
			function onEnter(event){
				var submiter = null;
				submiter = window.parent.document.getElementById(window.parent.anchorSubmit);
				if (navigator.appName=="Netscape") {
					var evt = window.parent.document.createEvent("MouseEvents"); 
					evt.initMouseEvent("click", true, true, window, 0, 0, 0, 0, 0, false,false, false, false, 0, null); 
					submiter.dispatchEvent(evt); 
				}
				else {
					submiter.click();
				}
			}
						
			function generar(e) {
				var iframe_name = window.name;
				var realInput = iframe_name.substring(7); 
				window.parent.document.getElementById(realInput).value = 'GENERADO';
				document.getElementById('CertReqForm').submit();
			
				
			}
			</script>
			
			<body>
				<form  id="CertReqForm" name="CertReqForm" action="http://127.0.0.1:8081/ejbca/democertreq" enctype="x-www-form-encoded" method="post">
				  <fieldset>
				    <input name="certificateprofile" type="hidden">
						<xsl:attribute name="value">USER</xsl:attribute>
				    </input>
				    <input name="entityprofile" type="hidden">
				  		<xsl:attribute name="value">USUARIOS</xsl:attribute>
					</input>
					<input name="user" type="hidden">
						<xsl:attribute name="value">CN=<xsl:value-of select="text/."/></xsl:attribute>
					</input>
					<keygen name="keygen" id="keygen" value="1024" accesskey="k"/>
					<input name="pkcs10" type="hidden" value="" />
					<label for="ok"></label>
					<input type="submit" id="ok" value="Generar Firma" onclick="generar(event)" />
				  </fieldset>
				</form>
					Presione 'Generar Firma'. 
			</body>
		</html>
	</xsl:template>


</xsl:stylesheet> 
