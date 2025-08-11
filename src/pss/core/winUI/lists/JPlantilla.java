package pss.core.winUI.lists;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import pss.core.tools.JTools;


public class JPlantilla {

	public class JPlantillaComponente implements Comparable<JPlantillaComponente>{
		String key;
		int type; //1:ZONE 2:TAG
		int orden;
		Map<String,String> menu;
		public JPlantillaComponente(String k, int t, Map<String,String> m) {
			key= k;
			type=t;
			menu=m;
			orden = index++;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
	
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public Map<String, String> getMenu() {
			return menu;
		}
		public void setMenu(Map<String, String> menu) {
			this.menu = menu;
		}
		@Override
		public int compareTo(JPlantillaComponente arg0) {
			
			return this.orden-arg0.orden;
		}
	}
	Map<String,JPlantillaComponente> plantilla;
	
	public JPlantilla() {
		index=0;
	}
	int index;
	public String serialize() throws Exception {
		StringBuffer out = new StringBuffer("[");
		for (JPlantillaComponente c : getPlantilla().values()) {
			out.append("['"+c.key+"','"+c.type+"',[");
			for (String k : c.getMenu().keySet()) {
				String v = c.getMenu().get(k);
				out.append("['"+k+"','"+JTools.replaceForeignChars(v)+"'],");
			}		
			out.append("['','']]],");
		}
		out.append("['','',[]]]");
		return out.toString();
	}
	
	public Map<String, JPlantillaComponente> getPlantilla() {
		if (plantilla!=null) return plantilla;
		index=0;
		return plantilla=new TreeMap<String, JPlantillaComponente>();
	}
	
	public void addTag(String k, String ik,String tag) throws Exception {
		JPlantillaComponente c = getPlantilla().get(k);
		if (c==null) {
			c=new JPlantillaComponente(k, 2, new LinkedHashMap<String,String>());
			getPlantilla().put(k, c);
		}
		String data = c.getMenu().get(ik);
		if (data ==null) {
			c.getMenu().put(ik,tag);
		}
	}

	public void addZone(String k, String ik,String idzone) throws Exception {
		JPlantillaComponente c = getPlantilla().get(k);
		if (c==null) {
			c=new JPlantillaComponente(k, 1, new LinkedHashMap<String,String>());
			getPlantilla().put(k, c);
		}
		String data = c.getMenu().get(ik);
		if (data ==null) {
			c.getMenu().put(ik,idzone);
		}
	}
	
	public void addPlantilla(JPlantilla p) throws Exception {
		
		this.getPlantilla().putAll(p.getPlantilla());
	}
}