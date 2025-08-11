package  pss.common.security.reports;

import pss.common.security.BizUsuario;
import pss.core.reports.JBDReportes;
import pss.core.reports.JReport;
import pss.core.reports.JReportesCommon;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizReporteUsuario extends JBDReportes {

	public static final String ORDEN_CODIGO="C";
	public static final String ORDEN_NOMBRE="D";
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JString pUsuario=new JString();
	JString pDescrip=new JString();
	JString pOrden=new JString();
	BizUsuario oUsuario=null;

	public String GetUsuario() throws Exception {
		return pUsuario.getValue();
	}

	public String GetDescrip() throws Exception {
		return pDescrip.getValue();
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizReporteUsuario() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("usuario", pUsuario);
		this.addItem("descripcion", pDescrip);
		this.addItem("ordenamiento", pOrden);
	}

	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "usuario", "usuario", true, false, 100);
		this.addFixedItem(FIELD, "descripcion", "descripcion", true, false, 100);
		this.addFixedItem(VIRTUAL, "ordenamiento", "ordenamiento", true, false, 100);
	}

	@Override
	public String GetTable() {
		return "";
	}

	public static JRecords<BizVirtual> getOrdenamientos() throws Exception {
		JRecords<BizVirtual> records=JRecords.createVirtualBDs();
		records.addItem(JRecord.virtualBD(ORDEN_CODIGO, "Código", 1));
		records.addItem(JRecord.virtualBD(ORDEN_NOMBRE, "Nombre", 1));
		return records;
	}

	@Override
	protected JReport obtenerReporte() throws Exception {
		return new JReportesCommon("security/reports/templates/Usuario.jod", JReport.DS_TYPE_APP);
	}

	@Override
	protected void verificarRestricciones() throws Exception {

	}

	@Override
	protected void configurarFormatos() throws Exception {
		getReport().setFormatDate("FECHA_INGRESO");
		getReport().setFormatDate("FECHA_EGRESO");
	}

	@Override
	protected void configurarControles() throws Exception {
		getReport().setLabel("ORDENAMIENTO", "Ordenamiento:"+BizReporteRol.getOrdenamientos().findVirtualKey(pOrden.getValue()).getDescrip());
		getReport().setLabel("USUARIO", BizUsuario.GetDescripcionReporte(pUsuario.getValue()));

		getReport().setLabel("TITLE", "Reporte de Usuarios");
		getReport().setLabel("LAB000", "Usuario");
		getReport().setLabel("LAB001", "Nombre");
		getReport().setLabel("LAB002", "Activo");
		getReport().setLabel("LAB003", "Último Ingreso");
		getReport().setLabel("LAB004", "Último Egreso");
		getReport().setLabel("LAB006", "Cantidad de Registros:");
	}

	@Override
	protected void configurarSQL() throws Exception {

		JRecords<BizUsuario> oUsuarios=new JRecords<BizUsuario>(BizUsuario.class);
		if (pUsuario.isNotNull()) {
			oUsuarios.addFilter("usuario", pUsuario.getValue());
		} else {
			if (pOrden.getValue().equals(ORDEN_CODIGO)) oUsuarios.addOrderBy("Usuario");
			else oUsuarios.addOrderBy("Descripcion");
		}
		oUsuarios.readAll();
		oUsuarios.toStatic();
		getReport().AddTabla(oUsuarios);
		getReport().setDesigner(false);
	}

}
