package pss.bsp.contrato.detalleBackendRutas;

import pss.bsp.contrato.JAutogenerador;
import pss.bsp.contrato.detalle.nivel.JTipoNivelNormal;
import pss.bsp.contrato.detalle.nivel.JTipoPremioPorcentajeDelTotal;
import pss.bsp.contrato.detalleDatamining.BizDetalleDatamining;
import pss.bsp.contrato.logica.JContratoBackendRuta;
import pss.bsp.contrato.quevender.BizQueVender;
import pss.bsp.contrato.rutas.ms.BizMS;
import pss.bsp.event.BizBSPSqlEvent;
import pss.common.event.sql.BizSqlEvent;
import pss.core.tools.JTools;
import pss.tourism.pnr.GuiPNRTickets;

public class BizDetalleBackendRuta extends BizDetalleDatamining {

	public BizDetalleBackendRuta() throws Exception {
		super();
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void autogenerar() throws Exception {
		BizSqlEvent event = autogenerarMeta();
		BizSqlEvent eventG = autogenerarBase();			

		String id = JTools.getValidFilename(getObjContrato().getDescripcion() + getObjContrato().getId()+getLinea()+eventG.getDescripcion().substring(8));
		id=id.length()>50?id.substring(0, 50):id;
		this.setCompany(getCompany());
		this.setId(getId());
		this.setLogicaContrato(JContratoBackendRuta.class.getName());
		this.setDefaultTipoObjetivo(JTipoNivelNormal.class.getName());
		this.setDefaultTipoPremio(JTipoPremioPorcentajeDelTotal.class.getName());
		this.setVariable(event.getId());
		this.setVariableGanancia(eventG.getId());
		this.setAutogenerado(id);

	}
	

	public BizSqlEvent autogenerarMeta() throws Exception {
		BizSqlEvent event = new BizBSPSqlEvent();
		event.dontThrowException(true);
		boolean read = event.read(getVariable());
		String sqlOld= event.getConsulta();

		JAutogenerador autogenerador = new JAutogenerador(this, JAutogenerador.META);
		
		event.setConsulta(autogenerador.getOutputSql());
		event.setConsultaHistorico(autogenerador.getOutputSqlHistorico());
		event.setConsultaDetalle(autogenerador.getOutputSqlDetalle());
		event.setConsultaAux1(autogenerador.getOutputConsultaAux1());
		event.setConsultaAux2(autogenerador.getOutputConsultaAux2());
		event.setDescrConsultaAux1(autogenerador.getOutputDescrConsultaAux1());
		event.setDescrConsultaAux2(autogenerador.getOutputDescrConsultaAux2());
			event.setDescripcion(autogenerador.getOutputTitle());
		event.setCompany(getCompany());
		event.setActivo(true);
		event.setCampo(autogenerador.getOutputField());
		event.setInvisible(true);
		event.setClassDetalle(autogenerador.getOutputClass());
		if (!read || !sqlOld.equals(event.getConsulta()))
			event.setEstado(BizSqlEvent.REPROCESAR);
		event.processUpdateOrInsertWithCheck();

		setConsultaReservaMeta(autogenerador.getOutputSqlReserva());
		return event;

	}
	public BizSqlEvent autogenerarBase() throws Exception {
		// ver borrado
		BizSqlEvent eventG = new BizBSPSqlEvent();
		eventG.dontThrowException(true);
		boolean read = eventG.read(getVariableGanancia());
		String sqlOld= eventG.getConsulta();

		JAutogenerador autogenerador = new JAutogenerador(this, JAutogenerador.BASE);

		eventG.setConsulta(autogenerador.getOutputSql());
		eventG.setConsultaHistorico(autogenerador.getOutputSqlHistorico());
		eventG.setConsultaDetalle(autogenerador.getOutputSqlDetalle());
		eventG.setConsultaAux1(autogenerador.getOutputConsultaAux1());
		eventG.setConsultaAux2(autogenerador.getOutputConsultaAux2());
		eventG.setDescrConsultaAux1(autogenerador.getOutputDescrConsultaAux1());
		eventG.setDescrConsultaAux2(autogenerador.getOutputDescrConsultaAux2());
		
		
		
		eventG.setDescripcion(autogenerador.getOutputTitle());
		eventG.setCompany(getCompany());
		eventG.setActivo(true);
		eventG.setCampo(autogenerador.getOutputField());
		eventG.setInvisible(true);
		eventG.setClassDetalle(autogenerador.getOutputClass());
		if (!read || !sqlOld.equals(eventG.getConsulta()))
			eventG.setEstado(BizSqlEvent.REPROCESAR);
		eventG.processUpdateOrInsertWithCheck();
		buildReports(autogenerador,1);

		setConsultaReservaBase(autogenerador.getOutputSqlReserva());

		return eventG;


	}
	

	@Override
	public void processInsert() throws Exception {
		if (pDescripcion.isNull())
			pDescripcion.setValue("Backend " + getIdAerolinea());
		setLogicaContrato(JContratoBackendRuta.class.getName());
		autogenerar();
		super.processInsert();
		autogenerar();
		super.processUpdate();

	}

	@Override
	public void processUpdate() throws Exception {
//		if (pDescripcion.isNull())
//			pDescripcion.setValue("MS por ruta " + getIdAerolinea());
		setLogicaContrato(JContratoBackendRuta.class.getName());
		autogenerar();
		super.processUpdate();
		

	}

}
