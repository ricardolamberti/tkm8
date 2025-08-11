package pss.common.terminals.core;

import pss.common.layout.JFieldReq;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

public class JRecordLine {

	public String section;
	private JList<String> fields=null;

	public JRecordLine(String section) {
		this.section=section;
	}
	
	public String getSection() {
		return section;
	}


	public void addField(JFieldReq req) throws Exception {
		this.addField(section, req.getId(), String.valueOf(req.get()));
	}

	public void addField(String sector, String field, String value) throws Exception {
		if (this.fields==null) this.fields=JCollectionFactory.createList();
		this.fields.addElement(" "+field+"=\""+this.scape(value)+"\" ");
	}

	public String getXmlLine() throws Exception {
		StringBuffer xml=new StringBuffer();
		xml.append("<section id=\""+section+"\" ");
		JIterator<String> iter=this.fields.getIterator();
		while (iter.hasMoreElements()) {
			xml.append(iter.nextElement());
		}
		xml.append(" />");
		return xml.toString();
	}
	
	public String scape(String s) throws Exception {
		s = s.replace("\r", "");
		s = s.replace("\n", "\\n");
		s = s.replace("\"", "");
		return s;
	}


}
