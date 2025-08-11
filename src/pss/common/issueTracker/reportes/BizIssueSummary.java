package pss.common.issueTracker.reportes;

import java.util.Date;

import pss.common.issueTracker.issue.BizIssue;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizIssueSummary extends BizIssue {
	
	public static final String PER_DIARIA = "1";
	public static final String PER_SEMANAL = "2";
	public static final String PER_MENSUAL = "3";
	public static final String PER_ANUAL = "4";
	
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//

	JDate pFechaDesde=new JDate();
	JDate pFechaHasta=new JDate();
	JString pPeriodo=new JString();
	JBoolean pAgrupaCompany=new JBoolean();
	JBoolean pAgrupaFecha=new JBoolean();
	JBoolean pAgrupaUsuario=new JBoolean();
	JBoolean pAgrupaEvento=new JBoolean();
	JBoolean pAgrupaStatus=new JBoolean();
	JString pDescrFecha=new JString() {
		public void preset() throws Exception {
			pDescrFecha.setValue(getDescrFecha());
		}
	}; 
	JString pFecha=new JString();
	JLong pCantidad=new JLong();
	
	public void setFechaDesde(Date value) throws Exception {pFechaDesde.setValue(value);}
	public void setFechaHasta(Date value) throws Exception {pFechaHasta.setValue(value);}
	public void setFecha(String value) throws Exception {pFecha.setValue(value);}
	public void setPeriodo(String value) throws Exception {pPeriodo.setValue(value);}
	public void setCantidad(double value) throws Exception {pCantidad.setValue(value);}
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizIssueSummary() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		super.createProperties();
		addItem("fechadesde", pFechaDesde) ;
		addItem("fechahasta", pFechaHasta) ;
		addItem("descr_fecha", pDescrFecha) ;
		addItem("fecha", pFecha) ;
		addItem("periodo", pPeriodo) ;
		addItem("agrupa_company", pAgrupaCompany) ;
		addItem("agrupa_fecha", pAgrupaFecha) ;
		addItem("agrupa_usuario", pAgrupaUsuario) ;
		addItem("agrupa_evento", pAgrupaEvento) ;
		addItem("agrupa_status", pAgrupaStatus) ;
		addItem("cantidad", pCantidad);
	}

	@Override
	public void createFixedProperties() throws Exception {
		super.createFixedProperties();
		addFixedItem(VIRTUAL, "descr_fecha", "Fecha", true, false, 15);
		addFixedItem(VIRTUAL, "fechadesde", "Fecha Desde", true, false, 15);
		addFixedItem(VIRTUAL, "fechahasta", "Fecha Hasta", true, false, 15);
		addFixedItem(VIRTUAL, "fecha", "Fecha", true, false, 15);
		addFixedItem(VIRTUAL, "periodo", "Periodo", true, false, 1);
		addFixedItem(VIRTUAL, "agrupa_company", "Agrupa Empresa", true, false, 1);
		addFixedItem(VIRTUAL, "agrupa_fecha", "Agrupa Fecha", true, false, 1);
		addFixedItem(VIRTUAL, "agrupa_usuario", "Agrupa Usuario", true, false, 1);
		addFixedItem(VIRTUAL, "agrupa_evento", "Agrupa Evento", true, false, 1);
		addFixedItem(VIRTUAL, "agrupa_status", "Agrupa Estado", true, false, 1);
		addFixedItem(VIRTUAL, "cantidad", "Cantidad", true, false, 16, 4);
		
		
	}

	@Override
	public String GetTable() {
		return "";
	}


	public String getDescrFecha() throws Exception {
		if (this.pFecha.isNotNull())
			return this.pFecha.getValue();
		else {
			String desde = (this.pFechaDesde.isNull()?"":JDateTools.DateToString(this.pFechaDesde.getValue()));
			String hasta = (this.pFechaHasta.isNull()?"":JDateTools.DateToString(this.pFechaHasta.getValue()));
			return desde+"->"+hasta;
		}
	}
	
	public static JMap<String, String> getPeriodos() throws Exception {
		JMap<String, String> map= JCollectionFactory.createOrderedMap();
		map.addElement(PER_DIARIA, "Diario");
		map.addElement(PER_SEMANAL, "Semanal");
		map.addElement(PER_MENSUAL, "Mensual");
		map.addElement(PER_ANUAL, "Anual");
		return map;
	}


	

}

