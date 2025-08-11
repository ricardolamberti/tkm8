package pss.core.graph.implementations;

import pss.core.graph.analize.Categories;
import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class GraphScriptArcosMap extends   GraphMatrix {

	public String getScript(int width, int height) throws Exception {
		String s= "";		

		s+="<div class=\"graph_title\" id=\"span_title_"+this.hashCode()+"\" >"+JTools.encodeString2(this.getTitle())+"</div>\n";
		s+="<span id=\"gmap_"+this.hashCode()+"\" style=\"position: absolute; left: 15px; top: 15px; overflow: hidden; width: "+(width-15)+"px; height: "+(height-15)+"px; z-index: 0;\"></span>\n";
		s+="<script type=\"text/javascript\" >\n";
		s+="setComponenteMapa(\"gmap_"+this.hashCode()+"\");\n";
 		s+="var mapa = initializeMapa();\n";
 		s+="cleanMapa(); \n";
 		//PENDIENTE optimizar la doble pasada
 		float max=0,min=1000000000;
		JIterator<Categories> icat = getCategories().getValueIterator();
		while (icat.hasMoreElements()) {
			Categories cat = icat.nextElement();
			Float[] values = new Float[getDatasets().size()];
			for (int y=0;y<getDatasets().size();y++) values[y]=0.0f;
			if (cat.getGeoPosition()==null) continue;
			JIterator<Dataset> ids = getDatasets().getValueIterator();
			while (ids.hasMoreElements()) {
				Dataset ds = ids.nextElement();
				JIterator<Value> iv = ds.getValues().getValueIterator();
				if (ds.getGeoPosition()==null) break;
				boolean find=false;
				while (iv.hasMoreElements()) {
					Value v = iv.nextElement(); 
					if (!v.getCategorie().equals(cat.getName()))
						continue;
					values[(int)ds.getRefNumerica()]+= Float.parseFloat(v.getData().toString().equals("") ? "0" : v.getData().toString());
					find=true;
				}
				if (!find) continue;
				if (ds.getGeoPosition()==null) continue;
				if (max<values[(int)ds.getRefNumerica()]) max=values[(int)ds.getRefNumerica()];
				if (min>values[(int)ds.getRefNumerica()]) min=values[(int)ds.getRefNumerica()];
			}
		}
		
		icat = getCategories().getValueIterator();
		while (icat.hasMoreElements()) {
			Categories cat = icat.nextElement();
			Float[] values = new Float[getDatasets().size()];
			for (int y=0;y<getDatasets().size();y++) values[y]=0.0f;
			if (cat.getGeoPosition()==null) continue;
			JIterator<Dataset> ids = getDatasets().getValueIterator();
			while (ids.hasMoreElements()) {
				Dataset ds = ids.nextElement();
				JIterator<Value> iv = ds.getValues().getValueIterator();
				if (ds.getGeoPosition()==null) break;
				boolean find=false;
				while (iv.hasMoreElements()) {
					Value v = iv.nextElement(); 
					if (!v.getCategorie().equals(cat.getName()))
						continue;
					values[(int)ds.getRefNumerica()]+= Float.parseFloat(v.getData().toString().equals("") ? "0" : v.getData().toString());
					find=true;
				}
				if (!find) continue;
				if (cat.getGeoPosition()==null) continue;
				int posColor = max<10?(int)Math.ceil(values[(int)ds.getRefNumerica()]):(int)(Math.ceil(values[(int)ds.getRefNumerica()]*10)/max);
				s+="addArco(new google.maps.LatLng("+cat.getGeoPosition().getLatitude()+","+cat.getGeoPosition().getLongitude()+"),new google.maps.LatLng("+ds.getGeoPosition().getLatitude()+","+ds.getGeoPosition().getLongitude()+"),"+values[(int)ds.getRefNumerica()]+",'#"+ds.getColorDegrade(posColor)+"',0.8,2,'#"+ds.getColorDegrade(posColor)+"',0.35,'"+cat.getName()+"-"+ds.getColname()+": "+values[(int)ds.getRefNumerica()]+"'); \n";
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
	public String getImage(int pwidth, int pheight) throws Exception {
		int width = pwidth==-1?600:pwidth;
		int height = pheight==-1?600:pheight;
		

		return "script:"+getScript(width,height);
	}
	@Override
	public String getSWF() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getGeoRequest() throws Exception {
		return 2;
	}
	public String isTimeLine() throws Exception {
		return TIME_NO;
	}
}
