package pss.bsp.contrato.detalleBackendDobleRutas;

import java.util.Calendar;
import java.util.StringTokenizer;

import pss.bsp.contrato.JAutogenerador;
import pss.bsp.contrato.detalle.nivel.JTipoNivelNormal;
import pss.bsp.contrato.detalle.nivel.JTipoPremioPorcentajeDelTotal;
import pss.bsp.contrato.detalleDataminingTri.BizDetalleDataminingTri;
import pss.bsp.contrato.logica.JContratoBackendDobleRuta;
import pss.bsp.contrato.logica.JContratoBackendRuta;
import pss.bsp.contrato.quevender.BizQueVender;
import pss.bsp.event.BizBSPSqlEvent;
import pss.common.event.sql.BizSqlEvent;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.tourism.pnr.GuiBookings;
import pss.tourism.pnr.GuiPNRTickets;

public class BizDetalleBackendDobleRuta extends BizDetalleDataminingTri
{

	public BizDetalleBackendDobleRuta() throws Exception {
		super();
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void autogenerar() throws Exception {
		BizSqlEvent event = autogenerarMeta();
		BizSqlEvent eventAux = autogenerarAux();
		BizSqlEvent eventG = autogenerarBase();

		String id = JTools.getValidFilename(getObjContrato().getDescripcion() + getObjContrato().getId()+getLinea()+eventG.getDescripcion().substring(8));
		id=id.length()>50?id.substring(0, 50):id;
		this.setCompany(getCompany());
		this.setId(getId());
		this.setLogicaContrato(JContratoBackendDobleRuta.class.getName());
		this.setDefaultTipoObjetivo(JTipoNivelNormal.class.getName());
		this.setDefaultTipoPremio(JTipoPremioPorcentajeDelTotal.class.getName());
		this.setVariable(event.getId());
		this.setVariableAuxiliar(eventAux.getId());
		this.setVariableGanancia(eventG.getId());
		this.setAutogenerado(id);

	}

	public BizSqlEvent autogenerarMeta() throws Exception {
		// ver borrado
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
		event.setCampo(autogenerador.getOutputField());
		event.setCompany(getCompany());
		event.setActivo(true);
		event.setInvisible(true);
		event.setClassDetalle(autogenerador.getOutputClass());
		if (!read || !sqlOld.equals(event.getConsulta()))
			event.setEstado(BizSqlEvent.REPROCESAR);
		event.processUpdateOrInsertWithCheck();

		setConsultaReservaMeta(autogenerador.getOutputSqlReserva());
		
		return event;

	}
	

	public BizSqlEvent autogenerarAux() throws Exception {
		// ver borrado
		BizSqlEvent event = new BizBSPSqlEvent();
		event.dontThrowException(true);
		boolean read = event.read(getVariableAuxiliar());
		String sqlOld= event.getConsulta();

		JAutogenerador autogenerador = new JAutogenerador(this, JAutogenerador.AUX);
		
		event.setConsulta(autogenerador.getOutputSql());
		event.setConsultaHistorico(autogenerador.getOutputSqlHistorico());
		event.setConsultaDetalle(autogenerador.getOutputSqlDetalle());
		event.setConsultaAux1(autogenerador.getOutputConsultaAux1());
		event.setConsultaAux2(autogenerador.getOutputConsultaAux2());
		event.setDescrConsultaAux1(autogenerador.getOutputDescrConsultaAux1());
		event.setDescrConsultaAux2(autogenerador.getOutputDescrConsultaAux2());
		
		
		event.setDescripcion(autogenerador.getOutputTitle());
		event.setCampo(autogenerador.getOutputField());
		event.setCompany(getCompany());
		event.setActivo(true);
		event.setInvisible(true);
		event.setClassDetalle(autogenerador.getOutputClass());
		if (!read || !sqlOld.equals(event.getConsulta()))
			event.setEstado(BizSqlEvent.REPROCESAR);
		event.processUpdateOrInsertWithCheck();

		setConsultaReservaAux(autogenerador.getOutputSqlReserva());

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
		eventG.setCampo(autogenerador.getOutputField());
		eventG.setCompany(getCompany());
		eventG.setActivo(true);
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
		setLogicaContrato(JContratoBackendDobleRuta.class.getName());
		autogenerar();
		super.processInsert();
		autogenerar();
		super.processUpdate();
	}

	@Override
	public void processUpdate() throws Exception {
//		if (pDescripcion.isNull())
//			pDescripcion.setValue("MS por ruta " + getIdAerolinea());
		setLogicaContrato(JContratoBackendDobleRuta.class.getName());
		autogenerar();
		super.processUpdate();
		

	}


	 
}
