package pss.common.layout;

import pss.common.security.BizUsuario;
import pss.common.terminals.printGen.JPrintGen;
import pss.core.services.fields.JObject;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;

public class JBaseLayout implements JFieldInterface {

	private JBaseRecord baseRecord;
	
	public JBaseLayout(JBaseRecord value) throws Exception {
		this.baseRecord= value;
	}

	private JPrintGen prinGen;
	public void setObjPrintGen(JPrintGen p) {
		this.prinGen=p;
	}
	
	public String generateLayout() throws Exception {
		JPrintGen g = this.prinGen;
		g.setFieldInterface(this);
		g.setPageSector(JFieldSetWins.PAGE_HEADER, JFieldSetWins.PAGE_FOOTER, this.getRecord());
//		wins.readAll();
		g.setDirect(true);
		g.startRecorder();
		g.generate(JFieldSetWins.REPORT_HEADER, this.getRecord());
//		g.generate(JFieldSetWins.PAGE_HEADER, this);
//		g.generate(JFieldSetWins.SECTOR_STARTMAIN, this);
		this.doGenerate(g);
//		g.generate(JFieldSetWins.SECTOR_ENDMAIN, this);
//		JRecords recs = wins.getRecords();
		g.generate(JFieldSetWins.REPORT_FOOTER, this.getRecord());
//		g.print();
		g.endRecorder();
		return g.getFileName();
	}
	
	public JBaseRecord getBaseRecord() throws Exception {
		return this.baseRecord;
	}
	
	public JRecord getRecord() throws Exception {
		if (this.baseRecord instanceof JRecord)
			return (JRecord)this.baseRecord;
		return null;
	}
	
	public JRecords getRecords() throws Exception {
		if (this.baseRecord instanceof JRecords)
			return (JRecords)this.baseRecord;
		return null;
	}

	private void doGenerate(JPrintGen g) throws Exception {
		if (this.baseRecord instanceof JRecords)
			this.doGenerateRecords(g, this.getRecords());
		if (this.baseRecord instanceof JRecord)
			this.doGenerateRecord(g, this.getRecord());
	}
	private void doGenerateRecords(JPrintGen g, JRecords records) throws Exception {
		records.firstRecord();
		while (records.nextRecord()) {
			JRecord r = records.getRecord();
			this.doGenerateRecord(g, r);
		}
	}

	private void doGenerateRecord(JPrintGen g, JRecord record) throws Exception {
		g.generate(JFieldSetWins.SECTOR_STARTMAIN, record);
		g.generate(JFieldSetWins.SECTOR_MAIN, record);
		this.doReportsDetail(g, record, "");
		g.generate(JFieldSetWins.SECTOR_ENDMAIN, record);
	}

	
	private void doReportsDetail(JPrintGen g, JRecord rec, String pref) throws Exception {
		JIterator<JProperty> iter = rec.getFixedProperties().getValueIterator();
		while (iter.hasMoreElements()) {
			JProperty prop = iter.nextElement();
			if (!prop.isRecords()) continue;
			// optimizar para no ir a buscar cuando no haya nada configurado
			JRecords records = (JRecords)rec.getProp(prop.GetCampo()).asObject();
			//	records.readAll();// tiene que venir leido
			if (records==null) continue;
  		g.generate("header_"+pref+prop.GetCampo(), rec);
			records.firstRecord();
			while (records.nextRecord()) {
				JRecord ri = records.getRecord();
	  		g.generate(pref+prop.GetCampo(), ri);
	  		doReportsDetail(g, ri, pref+prop.GetCampo()+".");
			}
  		g.generate("footer_"+pref+prop.GetCampo(), rec);
		}
	}

	public String getUniqueFileName() throws Exception {
		return BizUsuario.getUsr().getCompany()+"/"+this.toString()+".pdf";
	}
	

	@Override
	public Object getField(JFieldReq req) throws Exception {
		JRecord r = (JRecord)req.getSource1();
		if (r instanceof JRecord) {
			JObject field = r.getPropDeep(req.id);
			if (field==null) return "";
			return field.asObject();
		}
		return null;
	}
	
}
