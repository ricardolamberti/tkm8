package  pss.common.calendarTools.diasNoLaborales;

import java.util.Calendar;
import java.util.Date;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizDiaNoLaborable extends JRecord {

	private JString pCompany = new JString();
	private JLong pIdDiaNoLaborable = new JLong();
	private JDate pFechaDesde = new JDate();
	private JDate pFechaHasta = new JDate();
	private JString pDescripcion = new JString();
	private JString pTipoDiaNoLaborable = new JString();
	// private JString pMovil = new JString();
	private JBoolean pMovil = new JBoolean();
	private JString pDescTipoDiaNoLaborable = new JString() {
		public void preset() throws Exception {
			JRecords<BizVirtual> oDiasNoLaborables =  BizDiaNoLaborable.ObtenerTipoDiaNoLaborable();
			BizVirtual oDiaNoLaborable = (BizVirtual) oDiasNoLaborables.findVirtualKey(pTipoDiaNoLaborable.getValue());
			
			if (oDiaNoLaborable == null) {
				pDescTipoDiaNoLaborable.setValue("");
				return;
			}
			
			pDescTipoDiaNoLaborable.setValue(oDiaNoLaborable.getDescrip());
		}
	};
	

	
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setIdDiaNoLaborable(long zValue) throws Exception {
		pIdDiaNoLaborable.setValue(zValue);
	}

	public long getIdDiaNoLaborable() throws Exception {
		return pIdDiaNoLaborable.getValue();
	}

	public void setFechaDesde(Date zValue) throws Exception {
		pFechaDesde.setValue(zValue);
	}

	public Date getFechaDesde() throws Exception {
		return pFechaDesde.getValue();
	}

	public void setFechaHasta(Date zValue) throws Exception {
		pFechaHasta.setValue(zValue);
	}

	public Date getFechaHasta() throws Exception {
		return pFechaHasta.getValue();
	}

	public void setDescripcion(String zValue) throws Exception {
		pDescripcion.setValue(zValue);
	}

	public String getDescripcion() throws Exception {
		return pDescripcion.getValue();
	}

	public void setTipoDiaNoLaborable(String zValue) throws Exception {
		pTipoDiaNoLaborable.setValue(zValue);
	}

	public String getTipoDiaNoLaborable() throws Exception {
		return pTipoDiaNoLaborable.getValue();
	}

	public void setMovil(String zValue) throws Exception {
		pMovil.setValue(zValue);
	}

	public boolean getMovil() throws Exception {
		return pMovil.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizDiaNoLaborable() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("id_dia_no_laborable", pIdDiaNoLaborable);
		this.addItem("fecha_desde", pFechaDesde);
		this.addItem("fecha_hasta", pFechaHasta);
		this.addItem("descripcion", pDescripcion);
		this.addItem("tipo_dia_no_laborable", pTipoDiaNoLaborable);
		this.addItem("movil", pMovil);
		this.addItem("desc_tipo_dia_no_laborable", pDescTipoDiaNoLaborable);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 15);
		this.addFixedItem(KEY, "id_dia_no_laborable", "Id dia no laborable", true, true, 10);
		this.addFixedItem(FIELD, "fecha_desde", "Fecha desde", true, true, 10);
		this.addFixedItem(FIELD, "fecha_hasta", "Fecha hasta", true, false, 10);
		this.addFixedItem(FIELD, "descripcion", "Descripcion", true, true, 200);
		this.addFixedItem(FIELD, "tipo_dia_no_laborable", "Tipo dia no laborable", true, true, 2);
		this.addFixedItem(FIELD, "movil", "Movil", true, true, 2);
		this.addFixedItem(VIRTUAL, "desc_tipo_dia_no_laborable", "Tipo dia no laborable", true, true, 200);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "tool_dias_no_laborables";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(String zCompany, long zIddia_no_laborable) throws Exception {
		addFilter("company", zCompany);
		addFilter("id_dia_no_laborable", zIddia_no_laborable);
		return read();
	}

	public static JRecords<BizVirtual> ObtenerTipoDiaNoLaborable() throws Exception {
		JRecords<BizVirtual> oBDs = JRecords.createVirtualBDs();
		oBDs.addItem(JRecord.virtualBD("PE", "Periodico", 1));
		oBDs.addItem(JRecord.virtualBD("EX", "Extraordinario", 1));
		return oBDs;
	}

	@Override
	public void processInsert() throws Exception {
		if (this.pIdDiaNoLaborable.isNull()) {
			BizDiaNoLaborable oMax = new BizDiaNoLaborable();
			oMax.addFilter("company", pCompany.getValue());
			pIdDiaNoLaborable.setValue(oMax.SelectMaxLong("id_dia_no_laborable") + 1);
		}

		if (this.pFechaHasta.toRawString().isEmpty()) {
			this.pFechaHasta.setValue(this.pFechaDesde.getValue());
		}
		insertRecord();
	}
	
	public boolean ifDiaNoLaborable(Date zDate) throws Exception {
		String strFechaDesde;
		String strFechaHasta;
		String strCompDate;
		Calendar calCompDate;
		
		calCompDate = Calendar.getInstance();
		calCompDate.setTime(zDate);
	
		if (this.pTipoDiaNoLaborable.getValue().compareTo("PE") == 0){
			strFechaDesde = String.format("%02d%02d", this.pFechaDesde.getMonth(), this.pFechaDesde.getDays());
			strFechaHasta = String.format("%02d%02d", this.pFechaHasta.getMonth(), this.pFechaHasta.getDays());
			strCompDate = String.format("%02d%02d", calCompDate.get(Calendar.MONTH)+1, calCompDate.get(Calendar.DAY_OF_MONTH));
		}
		else {
			strFechaDesde = String.format("%04d%02d%02d", this.pFechaDesde.getYear(), this.pFechaDesde.getMonth(), this.pFechaDesde.getDays());
			strFechaHasta = String.format("%04d%02d%02d", this.pFechaHasta.getYear(), this.pFechaHasta.getMonth(), this.pFechaHasta.getDays());
			strCompDate = String.format("%04d%02d%02d", calCompDate.get(Calendar.YEAR), calCompDate.get(Calendar.MONTH)+1, calCompDate.get(Calendar.DAY_OF_MONTH));
		}
		
		if ( strCompDate.compareTo(strFechaDesde) < 0) {
			return false;
		}
		
		if ( strCompDate.compareTo(strFechaHasta) > 0) {
			return false;
		}
		
		if (strFechaDesde.compareTo(strFechaHasta) == 0) {
			if (strFechaDesde.compareTo(strCompDate) == 0) {
				return true;
			}
		}
		else {
			if (strFechaDesde.compareTo(strCompDate) <= 0 && strCompDate.compareTo(strFechaHasta) <= 0) {
				return true;
			}
		}
		
		return false;
	}
}
