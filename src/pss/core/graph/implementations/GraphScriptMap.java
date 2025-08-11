package pss.core.graph.implementations;

import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;


public class GraphScriptMap extends   GraphVector {

	public String getScript(int width, int height) throws Exception {
		String s= "";		

		s+="<div class=\"graph_title\" id=\"span_title_"+this.hashCode()+"\" >"+JTools.encodeString2(this.getTitle())+"</div>\n";
		s+="<span id=\"gmap_"+this.hashCode()+"\" style=\"position: absolute; left: 15px; top: 15px; overflow: hidden; width: "+(width-15)+"px; height: "+(height-15)+"px; z-index: 0;\"></span>\n";
		s+="<script type=\"text/javascript\" >\n";
		s+="setComponenteMapa(\"gmap_"+this.hashCode()+"\");\n";
 		s+="var mapa = initializeMapa();\n";
 		s+="cleanMapa(); \n";

 		float max= 0;
 		JIterator<Dataset> ids = getDatasets().getValueIterator();
		while (ids.hasMoreElements()) {
			Dataset ds = ids.nextElement();
			if (ds.getGeoPosition()==null) continue;
			JIterator<Value> iv = ds.getValues().getValueIterator();
			while (iv.hasMoreElements()) {
				Value v = iv.nextElement();
				float l = Float.parseFloat(v.getData().toString().equals("")?"0":v.getData().toString());
				if (l >max) max=l; 
			}		
		}
 		ids = getDatasets().getValueIterator();
		while (ids.hasMoreElements()) {
			Dataset ds = ids.nextElement();
			if (ds.getGeoPosition()==null) continue;
			JIterator<Value> iv = ds.getValues().getValueIterator();
			while (iv.hasMoreElements()) {
				Value v = iv.nextElement();
				float valor = Float.parseFloat(v.getData().toString().equals("")?"0":v.getData().toString());
				float l = (valor / max) * 2000000;
				s+="addCircle(new google.maps.LatLng("+ds.getGeoPosition().getLatitude()+","+ds.getGeoPosition().getLongitude()+"),"+l+",'#"+ds.getColor()+"',0.8,2,'#"+ds.getColor()+"',0.35,'"+ds.getColname()+": "+(v.getData().toString().equals("")?"0":v.getData().toString())+"'); \n";
			}		
		}
 		s+="dibujarMapa(mapa)\n";
		
		s+="</script>\n";

		/*
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
       

	
	
*/
		
		return s;
	}
	public String getImage(int width, int height) throws Exception {
		

		return "script:"+getScript(width,height);
	}
	@Override
	public String getSWF() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getGeoRequest() throws Exception {
		return 1;
	}
	public String isTimeLine() throws Exception {
		return TIME_NO;
	}
}
