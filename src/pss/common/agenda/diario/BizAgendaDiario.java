package pss.common.agenda.diario;

import java.util.Calendar;
import java.util.Date;

import pss.common.agenda.evento.BizEvento;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;

public class BizAgendaDiario extends JRecord {

	public static final String VISION_CONSOLA = "VISION_CONSOLA";
	
  private JDateTime pId = new JDateTime();
  private JLong pIdAgenda = new JLong();
  private JString pCompany = new JString();
  private JLong pIdPersona = new JLong();
  private JLong pRangoDesde = new JLong();
  private JLong pRangoHasta = new JLong();
  private JDate pFechaRef = new JDate();
  private JDateTime pFechaDesde = new JDateTime();
  private JDateTime pFechaHasta = new JDateTime();
  private JString pHora = new JString() {
  	public void preset() throws Exception {
  		pHora.setValue(getHora());
  	};
  };
  private JString pFechahora = new JString() {
  	public void preset() throws Exception {
  		pFechahora.setValue(getFechaHora());
  	};
  };
  private JString pEstado = new JString();
  private JString pIdTipoEvento = new JString();
  private JString pTexto = new JString();
  private JFloat pPorcentaje = new JFloat();
  private JObjBDs<BizEvento> pEventos = new JObjBDs<BizEvento>( );

  public String getBackgroundColor() throws Exception {
  	Double porc = pPorcentaje.getValue();
  	if (porc>0 && porc<=10) return "d6f9d7";
  	if (porc>10 && porc<=20) return "86f189";
  	if (porc>20 && porc<=30) return "4cd44f";
  	if (porc>30 && porc<=40) return "f1f2a0";
  	if (porc>40 && porc<=50) return "e0e163";
  	if (porc>50 && porc<=60) return "eff20b";
  	if (porc>60 && porc<=70) return "f79b9b";
  	if (porc>70 && porc<=80) return "f16161";
  	if (porc>80 && porc<=90) return "ea3030";
  	if (porc>90 && porc<=100) return "f40f0f";
  	if (porc>100) return "FF0000";
  	return null;
  }

  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public void addNota(String text, float porc) throws Exception {
  	if (text.equals(""));pTexto.setValue((pTexto.getValue().equals("")?"":pTexto.getValue()+"\n")+text);
  	pPorcentaje.setValue(pPorcentaje.getValue()+porc);
  	pEstado.setValue("OCUPADO");
 	
  }
  public void setFechaDesde(Date fecha) throws Exception {
  	pFechaDesde.setValue(fecha);
 	
  }
  public void setFechaHasta(Date fecha) throws Exception {
  	pFechaHasta.setValue(fecha);
 	
  }
  public void setId(Date fecha) throws Exception {
  	pId.setValue(fecha);
 	
  }
  public Date getFechaDesde() throws Exception {
  	return pFechaDesde.getValue();
  }
  public Date getFechaHasta() throws Exception {
  	return pFechaHasta.getValue();
  }
  public long getIdAgenda() throws Exception {
  	return pIdAgenda.getValue();
  }
  public boolean hasIdAgenda() throws Exception {
  	return pIdAgenda.isNotNull();
  }
  /**
   * Constructor de la Clase
   */
  public BizAgendaDiario() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "fechaid", pId );
    this.addItem( "fecharef", pFechaRef );
    this.addItem( "rangodesde", pRangoDesde );
    this.addItem( "rangohasta", pRangoHasta);
    this.addItem( "fecha", pFechaDesde );
    this.addItem( "fecha_h", pFechaHasta );
    this.addItem( "estado", pEstado );
    this.addItem( "id_tipo_evento", pIdTipoEvento );
    this.addItem( "texto", pTexto );
    this.addItem( "porcentaje", pPorcentaje );
    this.addItem( "hora", pHora );
    this.addItem( "id_persona", pIdPersona );
    this.addItem( "fechahora", pFechahora );
    this.addItem( "id_agenda", pIdAgenda );
    this.addItem( "company", pCompany );
    this.addItem( "eventos", pEventos );
    }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "fechaid", "Id", true, false, 64 );
    this.addFixedItem( FIELD, "fecharef", "fecha ref", true, false, 50 );
    this.addFixedItem( FIELD, "rangodesde", "rango desde", true, false, 50 );
    this.addFixedItem( FIELD, "rangohasta", "rango hasta", true, false, 50 );
    this.addFixedItem( FIELD, "fecha", "fecha desde", true, false, 50 );
    this.addFixedItem( FIELD, "fecha_h", "fecha hasta", true, false, 50 );
    this.addFixedItem( FIELD, "estado", "estado", true, false, 50 );
    this.addFixedItem( FIELD, "id_tipo_evento", "id_tipo_evento", true, false, 50 );
    this.addFixedItem( FIELD, "texto", "texto", true, false, 1000 );
    this.addFixedItem( FIELD, "porcentaje", "porc", true, false, 18 );
    this.addFixedItem( FIELD, "id_agenda", "Id agenda", true, false, 18 );
    this.addFixedItem( FIELD, "company", "Company", true, false, 18 );
    this.addFixedItem( VIRTUAL, "hora", "hora", true, false, 18 );
    this.addFixedItem( VIRTUAL, "fechahora", "Fecha y hora", true, false, 18 );
    this.addFixedItem( VIRTUAL, "id_persona", "persona", true, false, 18 );
    this.addFixedItem( RECORDS, "eventos", "eventos", true, false, 18 ).setClase(BizEvento.class);
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return ""; }

  public String getHora() throws Exception {
  	Calendar d = Calendar.getInstance();
  	d.setTime(getFechaDesde());
  	return JTools.LPad(""+d.get(Calendar.HOUR_OF_DAY),2,"0")+":"+JTools.LPad(""+d.get(Calendar.MINUTE),2,"0");
  }
  
  public String getFechaHora() throws Exception {
  	Calendar d = Calendar.getInstance();
  	d.setTime(getFechaDesde());
  	return JDateTools.formatFechaLarga(getFechaDesde())+" "+JTools.LPad(""+d.get(Calendar.HOUR_OF_DAY),2,"0")+":"+JTools.LPad(""+d.get(Calendar.MINUTE),2,"0");
  }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public void setIdAgenda(long zVal) throws Exception {
  	pIdAgenda.setValue(zVal);
  }
  public void setCompany(String zVal) throws Exception {
  	pCompany.setValue(zVal);
  }
  public void setTexto(String zVal) throws Exception {
  	pTexto.setValue(zVal);
  }
  public void setPorcentaje(double zVal) throws Exception {
  	pPorcentaje.setValue(zVal);
  }
  public void setEventos(JRecords<BizEvento> zVal) throws Exception{
  	pEventos.setValue(zVal);
  }
  
  public String getTexto() throws Exception {
  	return pTexto.getValue();
  }
  public JRecords<BizEvento> getEventos( ) throws Exception{
  	if (pEventos.isRawNull()) {
  		JRecords<BizEvento> evs = new JRecords<BizEvento>(BizEvento.class);
  		evs.setStatic(true);
  		pEventos.setValue(evs);
  	}
  	return pEventos.getValue();
  }
}
