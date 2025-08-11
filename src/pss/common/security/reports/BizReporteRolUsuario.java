package  pss.common.security.reports;

import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.reports.JBDReportes;
import pss.core.reports.JReport;
import pss.core.reports.JReportesCommon;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizReporteRolUsuario extends JBDReportes {

	public static final String ORDEN_ROL = "R";
	public static final String ORDEN_USUARIO  = "U";
	
  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  JString     pRol      = new JString();
  JString     pDescrip  = new JString();
  JString     pTipo     = new JString();
  JString     pOrdenamiento = new JString();

  public String GetRol    () throws Exception { return pRol.getValue()     ; }
  public String GetDescrip() throws Exception { return pDescrip.getValue() ; }
  public String GetTipo   () throws Exception { return pTipo.getValue()    ; }

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizReporteRolUsuario() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    this.addItem("rol"            , pRol) ;
    this.addItem("usuario"    , pDescrip) ;
    this.addItem("ordenamiento"    , pOrdenamiento ) ;
  }

  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "rol", "Rol", true, false, 100 );
    this.addFixedItem( FIELD, "usuario", "Usuario", true, false, 100 );
    this.addFixedItem( FIELD, "ordenamiento", "Ordenamiento", true, false, 100 );
  }

  @Override
	public String GetTable() {return ""; }

  @Override
	protected JReport obtenerReporte() throws Exception {
    return new JReportesCommon("security/reports/templates/RolUsuario.jod", JReport.DS_TYPE_JDBC);
  }

  public static JRecords<BizVirtual> getOrdenamientos() throws Exception {
		JRecords<BizVirtual> records = JRecords.createVirtualBDs();
		records.addItem(JRecord.virtualBD(ORDEN_ROL, "Rol", 1));
		records.addItem(JRecord.virtualBD(ORDEN_USUARIO, "Usuario", 1));
    return records;
  }
  
  @Override
	protected void verificarRestricciones() throws Exception {
  }

  @Override
	protected void configurarFormatos() throws Exception {
  }

  private boolean isOrdenRol() throws Exception {
  	return pOrdenamiento.getValue().equals(ORDEN_ROL);
  }
  @Override
	protected void configurarControles() throws Exception {
//    getReport().setLabel("ROL",BizRol.GetDescripcionReporte(pRol.getValue()) );
    getReport().setLabel("USUARIO",BizUsuario.GetDescripcionReporte(pDescrip.getValue()) );
    if (pOrdenamiento.isRawNotNull())
      getReport().setLabel("ORDENAMIENTO","Ordenamiento: ^" + JLanguage.translate(getOrdenamientos().findVirtualKey(pOrdenamiento.getValue()).getDescrip()));

    getReport().setVisible("SEC_ROL", this.isOrdenRol()?true:false);
    getReport().setVisible("DTL_ROL", this.isOrdenRol()?true:false);
    getReport().setVisible("GH_00", this.isOrdenRol()?true:false);
    getReport().setVisible("GF_00", this.isOrdenRol()?true:false);
    getReport().setVisible("SEC_USUARIO", !this.isOrdenRol()?true:false);
    getReport().setVisible("DTL_USUARIO", !this.isOrdenRol()?true:false);
    getReport().setVisible("GH_01", !this.isOrdenRol()?true:false);
    getReport().setVisible("GF_01", !this.isOrdenRol()?true:false);

    getReport().setLabel("TITLE","Usuarios por Rol");
    getReport().setLabel("TITCATEG1","Rol:");
    getReport().setLabel("TITCATEG2","Usuario");
    getReport().setLabel("LBL000","Usuario:");
    getReport().setLabel("LBL001","Rol");
    getReport().setLabel("LAB000","Cantidad de Registros:");
  }

  @Override
	protected void configurarSQL() throws Exception {
    String sQuery = "SELECT ";
    sQuery += " ROL.descripcion as rol, ";
    sQuery += " USR.descripcion as usuario ";
    sQuery += " FROM Seg_rol ROL ";
    sQuery += " ,Seg_Usuario USR ";
    sQuery += " ,Seg_Usuario_Rol REL ";
    sQuery += " WHERE ";
    sQuery += " ROL.rol = REL.rol ";
    sQuery += " and USR.usuario = REL.usuario ";
    if (!pRol.isEmpty()) sQuery += " and REL.rol='"+ pRol.getValue() + "' ";
    if (!pDescrip.isEmpty()) sQuery += " and REL.usuario='"+ pDescrip.getValue() + "' ";
    if(this.isOrdenRol()){
     sQuery += " ORDER BY ROL.descripcion, USR.descripcion  ";
     getReport().setImportedSQLQuery("SEC_ROL",sQuery);
    }
    else{
     sQuery += " ORDER BY USR.descripcion, ROL.descripcion ";
     getReport().setImportedSQLQuery("SEC_USUARIO",sQuery);
    }
  }
}


