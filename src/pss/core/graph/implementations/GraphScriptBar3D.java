package pss.core.graph.implementations;

import pss.core.graph.analize.Categories;
import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class GraphScriptBar3D extends   GraphMatrix {

	public String getScript(int width, int height) throws Exception {

		String datos = "";
		String hacerCubos = "";
		String hacerDS = "";
		int lastEjeX=0;
		int lastEjeY=0;
		double max=-1;
		double min=-1;
		
		JIterator<Dataset> ids2 = getDatasets().getValueIterator();
		while (ids2.hasMoreElements()) {
			Dataset ds = ids2.nextElement();
			ds.setRefNumerica(lastEjeY);

			hacerDS += "addText(arr,'30px Arial','rgba(0,0,0,1)','"+ds.getColname()+"',10,50,"+((lastEjeY*50))+", 15,finPos+215,-3.14/2,0,3.14/2);\n";
			hacerDS += "addCubeSimple(arr, '#cccccc', "+((lastEjeY*50)-10)+", -10, 0 ,45,10,finPos+430,0,"+(lastEjeY+1)+");\n";
			JIterator<Value> iv = ds.getValues().getValueIterator();
			while (iv.hasMoreElements()) {
				Value v = iv.nextElement();
				double value=Float.parseFloat(v.getData().toString().equals("") ? "0" : v.getData().toString());
				if (max==-1) max=value;
				if (max<value) max=value;
				if (min==-1) min=value;
				if (min>value) min=value;
			}
			lastEjeY++;
		}
		double maxAltura=2;
		double proporcinal = maxAltura/Math.max(Math.abs(max),Math.abs(min));
		long center =lastEjeY/2;
		long center2 =getCategories().size()/2;
		JIterator<Categories> icat = getCategories().getValueIterator();
		while (icat.hasMoreElements()) {
			Categories cat = icat.nextElement();
			Float[] values = new Float[getDatasets().size()];
			for (int y=0;y<getDatasets().size();y++) values[y]=0.0f;
	
			JIterator<Dataset> ids = getDatasets().getValueIterator();
			while (ids.hasMoreElements()) {
				Dataset ds = ids.nextElement();
				JIterator<Value> iv = ds.getValues().getValueIterator();
				while (iv.hasMoreElements()) {
					Value v = iv.nextElement();
					if (!v.getCategorie().equals(cat.getName()))
						continue;
					values[(int)ds.getRefNumerica()]+= Float.parseFloat(v.getData().toString().equals("") ? "0" : v.getData().toString());
					// dataset.setValue(Float.parseFloat(v.getData().toString().equals("")?"0":v.getData().toString()),ds.getColname(),v.getCategorie());
				}
			}
			String valores = "";
			String valoresProp = "";
			for (float val:values) 
				valores += ((valores.equals(""))?"":",")+(val);  
			datos += "cat_"+cat.hashCode()+" = ["+valores+ "]\n";
			for (float val:values) 
				valoresProp += ((valoresProp.equals(""))?"":",")+(val*proporcinal);  
			datos += "cat_"+cat.hashCode()+"_val = ["+valoresProp+ "]\n";
			
			hacerCubos += "var i=0;";
			hacerCubos += "while(true) { if (i==cat_"+cat.hashCode()+ "_val.length) break; \n";
//			hacerCubos += "addCube(scene, (cat_"+cat.hashCode()+ "_val[i]!=0?'#0088FF':'#999999'), i*50, cat_"+cat.hashCode()+ "_val[i]*18, "+((lastEjeX*50))+",2,cat_"+cat.hashCode()+ "_val[i] * 2,2);\n";
			hacerCubos += "addCube(arr, (cat_"+cat.hashCode()+ "_val[i]!=0?'#0088FF':'#999999'), i*50,5, "+((lastEjeX*50))+",40,(cat_"+cat.hashCode()+ "_val[i] * 100),40,"+lastEjeX+",i,cat_"+cat.hashCode()+ "_val[i]!=0?''+cat_"+cat.hashCode()+ "[i]:'',"+center+"==i&&"+lastEjeX+"=="+center2+");\n";
	  	hacerCubos += "i++;}\n";
			hacerCubos += "addText(arr,'30px Arial','rgba(0,0,0,1)','"+JTools.encodeString2(cat.getName())+"',10,50,-230, 15,"+((lastEjeX*50)+10)+",-3.14/2,0,0);\n";
			hacerCubos += "addCubeSimple(arr, '#cccccc', -430, -10, "+((lastEjeX*50))+",("+lastEjeY+"*50)+430,10,45,"+(lastEjeX+1)+",0);\n";
	  	hacerCubos += "finPos="+(lastEjeX*50)+">finPos?"+(lastEjeX*50)+":finPos;\n";
//	  		hacerCubos += "for d, i in cat_"+cat.hashCode()+ "\n";
//			hacerCubos += "  if !(d==0) then model.add(seen.Shapes.text(d.toFixed(1), {font : '10px Roboto', cullBackfaces : false, anchor : 'center'})\n";
//			hacerCubos += "	   .roty(-3.14)\n";
//			hacerCubos += "    .translate(i * 30 + 10, cat_"+cat.hashCode()+ "_val[i] + 10, "+((lastEjeX*30)+10)+")\n";
//			hacerCubos += "    .fill('#000000')\n";
//			hacerCubos += "  )\n";
//			hacerCubos += "model.add(seen.Shapes.text('"+cat.getName()+"', {font : '10px Roboto', cullBackfaces : false, anchor : 'center'})\n";
//			hacerCubos += "	   .rotx(-1.5)\n";
//			hacerCubos += "	   .roty(-3.14)\n";
//			hacerCubos += "	   .translate(-50,0,"+((lastEjeX*30)+10)+" )\n";
//			hacerCubos += "	   .fill('#000000')\n";
//			hacerCubos += ")\n";

			
			lastEjeX++;
			
		}
		
		String s="<div id=\"chart3d_"+this.hashCode()+"\" class=\"graph\"></div>";
		s+="<script type=\"text/javascript\" >\n";
		s+="function buildScene(namecontainer) {\n";
    s+="var $container = $('#'+namecontainer).parent();\n";
    s+="var width = $container.width();\n";
    s+="var height = $container.height();\n";
    s+="var size = Math.min(width, height);\n";
    s+="if (height==0||width==0)\n";
    s+=" size = Math.max(width, height);\n";
    s+="if (size<100)\n";
    s+=" size = 100;\n";
    s+="width=size;\n";
    s+="height=size;\n";
    s+="$('#'+namecontainer).width(width);\n";
    s+="$('#'+namecontainer).height(height);\n";
		s+="var arr = createScene(namecontainer);\n";
		s+="var geometry = new THREE.BoxGeometry( 20, 20, 20 );\n";
		s+="var renderer=arr[5];\n";
		s+="var container=arr[4];\n";
		s+="var finPos=0;\n";
		s+=datos;
		s += hacerCubos;
		s += hacerDS;
		
		
			s+="container.appendChild(renderer.domElement);\n";
			s+="container.addEventListener( 'mousemove', onDocumentMouseMove, false );\n";
			s+="animate();\n";
			s+="renderAll();\n";
			
		s+="}\n";
		s+="buildScene('chart3d_"+this.hashCode()+"');";
		s+="\n</script>\n";
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
}

