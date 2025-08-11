package pss.core.services.fields;

import org.w3c.dom.Element;

public class JObjRecords extends JObjRecord {

	public JObjRecords() {
	}
	
	@Override
	public JBaseObject findField(Element e) throws Exception {
//		JBaseObject obj = (JBaseObject)this.getMap().getElement(field);
//		if (obj!=null) return obj;
		String className = e.getAttribute("class");
		JBaseObject obj = (JBaseObject)Class.forName(className).newInstance();
		this.addValue(obj);
		return obj;
	}

//	protected String getXMLClassName(JBaseObject obj) throws Exception {
//		return " class=\""+obj.getClass().getName()+"\"";
//	}
//	

}
