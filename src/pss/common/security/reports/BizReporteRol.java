package  pss.common.security.reports;

import pss.common.security.BizRol;
import pss.core.reports.JBDReportes;
import pss.core.reports.JReport;
import pss.core.reports.JReportesCommon;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizReporteRol extends JBDReportes {

	public static final String ORDEN_CODIGO = "C";
	public static final String ORDEN_DESCR  = "D";
  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  JString     pRol      = new JString();
  JString     pOrden     = new JString();
//  private String sQuery = null;
//  private String sOrder = "";
//  int         iOrdenamiento;
  

//  public void setOrden(int iOrden){iOrdenamiento=iOrden;}
  public String GetRol    () throws Exception { return pRol.getValue()     ; }


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizReporteRol() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    this.addItem("Rol", pRol) ;
    this.addItem("ordenamiento", pOrden);
  }

  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( FIELD , "Rol"     , "Rol"          , true, false, 100 ) ;
    this.addFixedItem( FIELD , "ordenamiento"    , "Ordenamiento"  , true, false, 100 ) ;
  }

  public static JRecords<BizVirtual> getOrdenamientos() throws Exception {
		JRecords<BizVirtual> records = JRecords.createVirtualBDs();
		records.addItem(JRecord.virtualBD(ORDEN_CODIGO, "Código", 1));
		records.addItem(JRecord.virtualBD(ORDEN_DESCR, "Descripción", 1));
    return records;
  }
		

  @Override
	public String GetTable() {return ""; }


  @Override
	protected JReport obtenerReporte() throws Exception {
    return new JReportesCommon("security/reports/templates/Rol.jod", JReport.DS_TYPE_APP);
  }

  @Override
	protected void verificarRestricciones() throws Exception {
  }

  @Override
	protected void configurarFormatos() throws Exception {
  }

  @Override
	protected void configurarControles() throws Exception {
    // poner datos en labels de encabezado
    getReport().setLabel("ORDENAMIENTO","Ordenamiento:" + BizReporteRol.getOrdenamientos().findVirtualKey(pOrden.getValue()).getDescrip());
//    getReport().setLabel("ROL",BizRol.GetDescripcionReporte(pRol.getValue()));

    getReport().setLabel("TITLE","Reporte de Roles");
    getReport().setLabel("LAB000","Rol");
    getReport().setLabel("LAB001","Descripción");
    getReport().setLabel("LAB002","Jerarquico");
    getReport().setLabel("LAB003","Cantidad de Registros:");
  }

  @Override
	protected void configurarSQL() throws Exception {

    JRecords<BizRol> oRoles = new JRecords<BizRol>(BizRol.class);
    if(pRol.isNotNull()) {
      oRoles.addFilter("rol",pRol.getValue());
    } else {
      if (pOrden.getValue().equals(ORDEN_DESCR))
        oRoles.addOrderBy("descripcion");
      else
        oRoles.addOrderBy("rol");
    }
    oRoles.readAll();
    oRoles.toStatic();
    getReport().AddTabla(oRoles);
    getReport().setDesigner(false);
  }

}


