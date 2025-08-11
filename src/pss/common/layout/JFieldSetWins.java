package pss.common.layout;

import pss.common.customList.config.dynamic.BizDynamic;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class JFieldSetWins extends JFieldSet {

	public static final String REPORT_HEADER="REPORT_HEADER";
	public static final String PAGE_HEADER="PAGE_HEADER";
	public static final String PAGE_FOOTER="PAGE_FOOTER";
	public static final String REPORT_FOOTER="REPORT_FOOTER";
	public static final String SECTOR_MAIN="SECTOR_MAIN";
	public static final String SECTOR_STARTMAIN="SECTOR_STARTMAIN";
	public static final String SECTOR_ENDMAIN="SECTOR_ENDMAIN";
	
	private JBaseRecord baseRecord;


	//-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public JFieldSetWins() throws Exception {
  }

  protected JMap<String, String> getFields(String section) throws Exception {
  	if (section==null) return null;
  	if (section.equals(SECTOR_MAIN) ||
  			section.equals(SECTOR_ENDMAIN) ||
  			section.equals(SECTOR_STARTMAIN) ||
  			section.equals(PAGE_HEADER) ||
  			section.equals(PAGE_FOOTER) ||
  			section.equals(REPORT_HEADER) ||
  			section.equals(REPORT_FOOTER) )
  		return this.getFields(this.getRecordRef());
		if (section.isEmpty()) return null;
		if (section.startsWith("header_") || section.startsWith("footer_")) {
  		return this.getFields(this.getRecordRef());
//			int dot = section.lastIndexOf('.');
//			return this.getFields(dot!=-1?section.substring(7,dot):section.substring(7));
		}

		JProperty fixProp = this.getRecordRef().getFixedPropDeep(section);
		if (fixProp==null) return null;
		JRecord record = (JRecord)fixProp.getClase().newInstance();
		return this.getFields(record);
  }

  protected JMap<String, String> getFields(JRecord record) throws Exception {
  	if (record instanceof BizDynamic) {
    	JMap<String, String> map = JCollectionFactory.createOrderedMap();
  		for(int i=1; i<=10; i++) {
  			map.addElement("titulo_"+i, "Titulo "+i);
  			map.addElement("campo_"+i, "Campo "+i);
  			map.addElement("campo_alineado_"+i, "Campo alineado "+i);
  		}
    	return map;
  	}
  	else
  		return record.getAllFixedProperties(this.includeRecords);
  }
//  	if (baseRecord instanceof JRecords) {
//  		return this.getFields(section, ((JRecords)baseRecord).getRecordRef());
//  	}
  

//  protected void appendFields(JMap<String, String> map, JRecord record, String pref, String dpref) throws Exception {
//	  JIterator<JProperty> iter = record.getFixedProperties().getValueIterator();
//		while (iter.hasMoreElements()) {
//			JProperty p = iter.nextElement();
//			if (p.isRecords() && includeRecords) {
//				continue;
//			}
//			if (p.isRecord()) {
//				continue;
//			}
////			map.addElement((fixProp==null?"":fixProp.GetCampo()+".")+p.GetCampo(), (fixProp==null?"":fixProp.GetDescripcion()+".")+p.GetDescripcion());
//			String descr =record.visualizaInLayout(p.GetCampo());
//			if (descr!=null) {
//				PssLogger.logInfo(pref+p.GetCampo());
//				if (descr.equals("")) map.addElement(pref+p.GetCampo(), dpref+p.GetDescripcion());
//				else map.addElement(pref+p.GetCampo(), dpref+descr);
//			}
//		}
//	  iter = record.getFixedProperties().getValueIterator();
//		while (iter.hasMoreElements()) {
//			JProperty p = iter.nextElement();
//			if (p.isRecords() && includeRecords) {
//				if (map.containsKey(p.GetCampo()))
//					continue;
//				String descr =record.visualizaInLayout(p.GetCampo());
//				if (descr!=null) 
//					if (descr.equals("")) map.addElement( pref+p.GetCampo(),  "Zona "+dpref+p.GetDescripcion());
//					else map.addElement( pref+p.GetCampo(), "Zona "+dpref+descr);
//				continue;
//			}
//			if (p.isRecord()) {
////				if (map.containsKey(p.GetCampo())) return;
//	  		JRecord subrec = (JRecord)p.getClase().newInstance();
//				String descr =record.visualizaInLayout(p.GetCampo());
//				if (descr==null) continue;
//				if (descr.equals("")) descr =  p.GetDescripcion();
//				map.addElement(p.GetCampo(), descr);
//				this.appendFields(map, subrec, pref+p.GetCampo()+".", dpref+descr+".");
//				continue;
//			}
//		}
//	}
//
  protected void appendSecciones(JMap<String, String> map, JRecord record, String pref, String dpref,int nivel) throws Exception {
   	if (this.includeRecords) if (nivel>5) return;;
  	JIterator<JProperty> iter = record.getFixedProperties().getValueIterator();;
  	while (iter.hasMoreElements()) {
  		JProperty prop = iter.nextElement();

    	if (prop.isRecord() && this.includeRecords) {
  			if (map.containsKey(prop.GetCampo())) continue; 
  			System.out.println(prop.getClase());
				JRecord rec = (JRecord)prop.getClase().newInstance();
    		this.appendSecciones(map, rec, pref+prop.GetCampo()+".", dpref+prop.GetDescripcion()+".",nivel+1);
  			continue;
  		}

  		if (!prop.isRecords()) continue;
  		if (filter!=null && !filter.startsWith(pref+prop.GetCampo())) continue;
			if (this.includeHeaders) map.addElement("header_"+pref+prop.GetCampo(), dpref+prop.GetDescripcion() + " - Header");
  		map.addElement(pref+prop.GetCampo(), dpref+prop.GetDescripcion());
			map.addElement("footer_"+pref+prop.GetCampo(), dpref+prop.GetDescripcion() + " - Footer");
  		JRecord rec = (JRecord)prop.getClase().newInstance();
  		this.appendSecciones(map, rec, pref+prop.GetCampo()+".", dpref+prop.GetDescripcion()+".",nivel);
  	}
  }
  
  public JMap<String, String> getSections() throws Exception {
  	JMap<String, String> map = JCollectionFactory.createOrderedMap();
  	JMap<String, String> deepMap = JCollectionFactory.createOrderedMap();
  	if (this.getIdent().isEmpty()) return map;
  	JRecord record = this.getRecordRef();
  	if (this.includeHeaders)   	map.addElement(REPORT_HEADER, "Report Header");
  	if (this.includeHeaders) map.addElement(PAGE_HEADER, "Page Header");
  	if (this.includeHeaders) map.addElement(SECTOR_STARTMAIN, "Record Header");
  	map.addElement(SECTOR_MAIN, record.getClassTitle());
  	this.appendSecciones(map, record, "", record.getClassTitle()+".",0);
  	if (this.includeHeaders) map.addElement(SECTOR_ENDMAIN, "Record Footer");
  	if (this.includeHeaders) map.addElement(PAGE_FOOTER, "Page Footer");
  	if (this.includeHeaders) map.addElement(REPORT_FOOTER, "Report Footer");
//  	JIterator<BizAction> iter = this.getWinRef().getActionMapForSecurity().getStaticIterator();
//  	while (iter.hasMoreElements()) {
//  		BizAction a = iter.nextElement();
//  		if (!a.isReportDetail()) continue;
//  		map.addElement(a.getIdAction(), a.GetDescr());
//  	}
  	
    return map;
  }
  
  
//  public JRecord getRecordRef() throws Exception {
//  	if (this.isWin())
//  		return this.getWin().getRecord();
//  	else
//  		return this.getWins().getRecords().getRecordRef();
//  }  

  public JRecord getRecordRef() throws Exception {
  	if (this.isRecord())
  		return this.getRecord();
  	else
  		return this.getRecords().getRecordRef();
  }  
  
  public boolean isRecord() throws Exception {
  	return (this.getBaseRecord() instanceof JRecord);
  }
  
  public JRecord getRecord() throws Exception {
  	return (JRecord)this.getBaseRecord();
  }
  
  public JRecords getRecords() throws Exception {
  	return (JRecords)this.getBaseRecord();
  }
  
  public JBaseRecord getBaseRecord() throws Exception {
  	if (this.baseRecord!=null) return this.baseRecord;
  	String ident = this.getIdent();
  	JBaseRecord r=(JBaseRecord)Class.forName(ident).newInstance();
  	return (this.baseRecord=r);
  }
  
}
