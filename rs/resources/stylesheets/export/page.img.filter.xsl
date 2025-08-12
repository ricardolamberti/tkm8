<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:variable name="page_stereotype" select="page/header/@stereotype" />
	<xsl:variable name="url_prefix" select="page/header/layouts/@url_prefix" />

	<xsl:strip-space elements="*" />

	<xsl:template match="/">
		<xsl:apply-templates select="*" />
	</xsl:template>

	<xsl:template match="page/header" />
	<xsl:template match="action" />
<xsl:template match="panel[starts-with(@name,'filter_pane')]" />
<xsl:template match="div_responsive[@name='filter_header']" />
<xsl:template match="div_responsive[@name='filter_body ']" />
<xsl:template match="form_responsive[starts-with(@name,'filter_pane')]" />
	<xsl:template match="*/text_field_responsive" />

	<xsl:template match="win_list">

<HTML>
	<xsl:if test="graph/@refresh">
	    <meta http-equiv="refresh" >
		    <xsl:attribute name="content"><xsl:value-of select="graph/@refresh"/></xsl:attribute>
		</meta>
	</xsl:if>
 	<HEAD>
			<link rel="stylesheet">
				<xsl:attribute name="href">/<xsl:value-of select="$url_prefix"/>/resources/styles[<xsl:value-of select="session/@id"/>].css</xsl:attribute>
			</link>

	   <TITLE><xsl:value-of select="@title"/></TITLE>
	   <SCRIPT LANGUAGE="Javascript" SRC="js/FusionCharts.js"></SCRIPT>
	   <SCRIPT LANGUAGE="Javascript" SRC="js/FusionChartsExportComponent.js"></SCRIPT>
	   <SCRIPT LANGUAGE="JavaScript">
	
	<!-- categories      dataset         value
	Quarter 1       Product A	  	10
	Quarter 1       Product B	  	20
	Quarter 1       Product C	  	30
	Quarter 1       Product D	  	40

	Quarter 2       Product A	  	50
	Quarter 2       Product B	  	60
	Quarter 2       Product C	  	70
	Quarter 2       Product D	  	80

	
	 var graphs = {{title,swf,categories,dataset},{title,swf,categories,dataset,trendlines}}
     var categories = {0,Quarter1},{1,Quarter2}
     var dataset = {ProductA,{10,50},color},{ProductB,{20,60},color}
	-->

	var graphs = new Array();
    var gr;
    var cat;
    var pc;
    var pcv;
    var val;
    var tl;
    var maxValue =0;
    var model;
   	<xsl:for-each select="graph/graphic">
   		gr = new Array();
   		graphs[graphs.length]=gr;
   		gr[0] = '<xsl:value-of select="@title"/>';
   		gr[1] = 'swf/<xsl:value-of select="@swf"/>';
   		model = '<xsl:value-of select="@model"/>';
   		cat = new Array();

	   	<xsl:for-each select="categories">
	   		pc=cat.length;
	   		cat[pc] = new Array();
			cat[pc][0]=pc;
			cat[pc][1]='<xsl:value-of select="@name"/>';
			cat[pc][2]="<xsl:value-of select="@attributes"/>";
		</xsl:for-each>
   		gr[2] = cat; 

   		ds = new Array();
	   	<xsl:for-each select="dataset">
	   		pc=ds.length;
	   		ds[pc] = new Array();
			ds[pc][0]='<xsl:value-of select="@name"/>';
			ds[pc][1]='<xsl:value-of select="@color"/>';
	   		val = new Array();
		   	<xsl:for-each select="value">
		   		pcv=val.length;
		   		val[pcv] = new Array();
				val[pcv][0]='<xsl:value-of select="@value"/>';
				val[pcv][1]='<xsl:value-of select="@categorie"/>';
				val[pcv][2]="<xsl:value-of select="@attributes"/>";
			</xsl:for-each>
	   		ds[pc][2] = val; 
	   		ds[pc][3] = '<xsl:value-of select="@parentYAxis"/>'; 
	   		ds[pc][4] = "<xsl:value-of select="@attributes"/>"; 
		</xsl:for-each>
   		gr[3] = ds; 
   		gr[4] = '<xsl:value-of select="@maxvalue"/>';
   		gr[5] = model;
   		att = new Array();
   		<xsl:for-each select="attribute">
	   		pc=att.length;
	   		att[pc] = new Array();
			att[pc][0]='<xsl:value-of select="@name"/>';
			att[pc][1]='<xsl:value-of select="@value"/>';
		</xsl:for-each>
   		gr[6] = att; 
   		tl = new Array();
	   	<xsl:for-each select="trendlines">
	   		pc=tl.length;
	   		tl[pc] = new Array();
			tl[pc][0]='<xsl:value-of select="@displayvalue"/>';
			tl[pc][1]='<xsl:value-of select="@startvalue"/>';
			tl[pc][2]='<xsl:value-of select="@endvalue"/>';
			tl[pc][3]='<xsl:value-of select="@color"/>';
			tl[pc][4]='<xsl:value-of select="@showontop"/>';
			tl[pc][5]='<xsl:value-of select="@trendzone"/>';
			tl[pc][6]='<xsl:value-of select="@alpha"/>';
			tl[pc][7]='<xsl:value-of select="@thickness"/>';
		</xsl:for-each>
   		gr[7] = tl;    		
	</xsl:for-each>
	var graphActive = 0;
	<![CDATA[
	
	      function updateChart(domId){
	         updateChartXML(domId,generateXML(graphs[graphActive],this.document.productSelector.AnimateChart.checked));
	      }
	      function generateXML(graph, animate){
	      	if (graph[5]=="matrix")
	      		return generateXMLMatrix(graph, animate);
	      	else if (graph[5]=="vector")
	      		return generateXMLVector(graph, animate);
	      }

	      function generateXMLVector(graph, animate){
	         var strXML;
	         strXML = 
	         "<graph caption='"+graph[0]+"' "+ 
	         ((this.document.productSelector.ShowValues.checked==true) ? ("showValues='1' rotateValues='1'"):(" showValues='0' ")) + 
	         "yaxismaxvalue='"+graph[4]+"' " +
	         "animation='" + ((animate==true)?"1":"0") + "' "; 
	         var attr = graph[6];
			 for(var a=0;a<attr.length;a++) {
		     	strXML += attr[a][0]+"='"+attr[a][1]+"'";
 		     }
	         strXML += ">";
	         
    	      var dataset = graph[3];

	         var dataset = graph[3];
			 for(var v=0;v<dataset.length;v++) {
		          strXML += "<set name='" + dataset[v][0]+"' ";
	              var values = dataset[v][2];
				  strXML += "value='" + values[0][0] + "' "+values[0][2]+"/>";
 		     }
 		     strXML += generateTrendLines(graph);
	         strXML += "</graph>";
	         return strXML;
		   }
	      
	      function generateXMLMatrix(graph, animate){
	         var strXML;
	         strXML = 
	         "<graph caption='"+graph[0]+"' "+ 
	         ((this.document.productSelector.ShowValues.checked==true) ? ("showValues='1' rotateValues='1'"):(" showValues='0' ")) + 
	         "yaxismaxvalue='"+graph[4]+"' " +
	         "animation='" + ((animate==true)?"1":"0") + "' "; 
	         var attr = graph[6];
			 for(var a=0;a<attr.length;a++) {
		     	strXML += attr[a][0]+"='"+attr[a][1]+"'";
 		     }
	         strXML += ">";

	         var categories = graph[2];
	         strXML += "<categories>";
	         for(var i=0;i<categories.length;i++)
		         strXML += "<category name='"+categories[i][1]+"' "+categories[i][2]+"/>";
	         strXML += "</categories>";

	         var dataset = graph[3];
			 for(var v=0;v<dataset.length;v++) {
			 	 var control = document.getElementById(dataset[v][0]);
			 	 if (control==null)
		         	strXML += getDatasetXML(graph,v);
			 	 else if (control.checked)		
		         	strXML += getDatasetXML(graph,v);
		     }
	
 		     strXML += generateTrendLines(graph);
	         strXML += "</graph>";
	
	         return strXML;
	      }

		  function generateTrendLines(graph) {
	         var tl = graph[7];
	         var strXML = "<trendlines>";
	         for(var i=0;i<tl.length;i++)
		         strXML += "<line startValue='"+tl[i][1]+"' endValue='"+tl[i][2]+"' color='"+tl[i][3]+"' displayValue='"+tl[i][0]+"' thickness='"+tl[i][7]+"' alpha='"+tl[i][6]+"' showontop='"+tl[i][4]+"' trendzone='"+tl[i][5]+"'/>";
	         strXML += "</trendlines>";
	         return strXML;
		  } 
	
	      function getDatasetXML(graph,datasetIndex){
	          var productXML;

   	          var categories = graph[2];
    	      var dataset = graph[3];

	          productXML = "<dataset seriesName='" + dataset[datasetIndex][0] + "' color='"+ dataset[datasetIndex][1] +"' parentYAxis='"+ dataset[datasetIndex][3]+"' "+dataset[datasetIndex][4]+">";
              var values = dataset[datasetIndex][2];
	          for (var i=0; i<categories.length; i++){
	              var find = 0;
		          for (var j=0; j<values.length; j++) {
		          	if (categories[i][1]==values[j][1]) {
  		          		 find = 1;
			             productXML += "<set value='" + values[j][0]+"' "+ values[0][2]+"/>";
			             break;
			        }
		          }
		          if (find==0) {
		             productXML += "<set />";
		          }
	          }
	          productXML += "</dataset>";
	          return productXML;
	      }
	
	      function generateOptions(graph){
	      	var controls = document.getElementById("OPTIONS");
   	        var categories = graph[2];
     	    var dataset = graph[3];
	      	var salida = "";
	      	if (graph[5]=="matrix") {
            	for(var i=0;i<dataset.length;i++)
            		if(dataset[i][3]!="S")
  		        	 salida +="<INPUT TYPE='Checkbox' name='"+dataset[i][0]+"' id='"+dataset[i][0]+"' onClick=\"JavaScript:updateChart('chartId');\" checked='1'/>"+dataset[i][0];
	      	}
	      		controls.innerHTML = salida;
	      }
	]]>      
	      
	   </SCRIPT>
	</HEAD>
	<BODY class="graph_bg" >
		   <Table border="0">
		   <TR height="50">
			   <td>
			   </td>
		   </TR>
		   </Table>
				
	   <center>
		   <Table border="0" ALIGN="CENTER" class="win_form_border" WIDTH="740">
		   <TR height="480" VALIGN="MIDDLE">
			   <td ALIGN="CENTER" >
			   <Table border="1">
			   <tr   ><td >
			   <div id="chartdiv">
			      Principal
			   </div>
			   </td></tr>
			   </Table>
			   </td>
		   </TR>
		   <TR height="50" class="win_list_filters">
			   <td align="center">
			   <FORM NAME="productSelector" Id="productSelector" action="Chart.html" method="POST" >
			      <div id="OPTIONS"></div>
				  <br/>
				  <INPUT TYPE="Checkbox" name="AnimateChart" />Animado?
			      <INPUT TYPE="Checkbox" name="ShowValues" onClick="JavaScript:updateChart('chartId');" />Mostrar Datos?			<br/>
			   </FORM>
			   </td>
		   </TR>
		   <tr>
		   <td  ALIGN="CENTER">
		   <table border="1" >
			  
			   	<xsl:for-each select="graph/graphic">
					<xsl:if test="position()!=1">
					<xsl:if test="position() mod 2 = 0">
				   	 <tr>
					   <td>
						    <xsl:attribute name="id">td<xsl:value-of select="position()"/>div</xsl:attribute>
						    <a  href="#">  
						    	<xsl:attribute name="name">btn<xsl:value-of select="position()"/></xsl:attribute>
						    	<xsl:attribute name="onclick">active(<xsl:value-of select="position()"/>);</xsl:attribute>
						    	<div class="lupa"></div> 
							</a>						    
						    <div ><xsl:attribute name="id">chart<xsl:value-of select="position()"/>div</xsl:attribute></div>
		 		      </td>
					   <td>
						    <xsl:attribute name="id">td<xsl:value-of select="position()+1"/>div</xsl:attribute>
						    <a  href="#">  
						    	<xsl:attribute name="name">btn<xsl:value-of select="position()+1"/></xsl:attribute>
						    	<xsl:attribute name="onclick">active(<xsl:value-of select="position()+1"/>);</xsl:attribute>
						    	<div class="lupa"></div> 
							</a>						    
						    <div ><xsl:attribute name="id">chart<xsl:value-of select="position()+1"/>div</xsl:attribute></div>
		 		      </td>
		    		 </tr>
				   </xsl:if>
				   </xsl:if>
				</xsl:for-each>				   
			</table>
		   </td>
		   </tr>
		   </Table>
       </center>	
	   
	   <script language="JavaScript">
	      var zoneChart = new Array();
	   	  function active(z) {
	   	  	graphActive=zoneChart[z];
	   	  	refill();
	   	  }
	   
	   function refill() {
	   	  generateOptions(graphs[graphActive]);
	      var chart = new FusionCharts(graphs[graphActive][1],"chartId", "702", "450");
	      var strXML=generateXML(graphs[graphActive],this.document.productSelector.AnimateChart.checked);
	      chart.setDataXML(strXML);
	      chart.render("chartdiv");

		  var g=0;
		  var z=2;	
		  zoneChart = new Array();
		  var chartG = new Array();
   	<xsl:for-each select="graph/graphic">
    	  if (g!=graphActive) {	
		      chartG[g] = new FusionCharts(graphs[g][1],"chart"+z+"Id", "350", "350");
		      var strXML=generateXML(graphs[g],this.document.productSelector.AnimateChart.checked);
		      chartG[g].setDataXML(strXML);
		      chartG[g].render("chart"+z+"div");
		      zoneChart[z]=g;
		      z++;
	      }
	      g++;
	      
   	</xsl:for-each>

		}
		
		refill();
	   </script>
	   
	</BODY>
</HTML>
	</xsl:template>
</xsl:stylesheet> 