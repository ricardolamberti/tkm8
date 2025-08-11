package pss.bsp.contrato.detalleObjetivo;

import pss.bsp.contrato.JAutogenerador;
import pss.bsp.contrato.detalle.nivel.JTipoNivelNormal;
import pss.bsp.contrato.detalleDatamining.BizDetalleDatamining;
import pss.bsp.contrato.logica.JContratoObjetivo;
import pss.bsp.event.BizBSPSqlEvent;
import pss.common.event.sql.BizSqlEvent;
import pss.core.tools.JTools;

public class BizDetalleObjetivo extends BizDetalleDatamining {

	public BizDetalleObjetivo() throws Exception {
		super();
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void autogenerar() throws Exception {
		BizSqlEvent event = autogenerarMeta();
	
		String id = JTools.getValidFilename(getObjContrato().getDescripcion() + getObjContrato().getId()+getLinea()+event.getDescripcion().substring(8));
		id=id.length()>50?id.substring(0, 50):id;
		this.setCompany(getCompany());
		this.setId(getId());
		this.setLogicaContrato(JContratoObjetivo.class.getName());
		this.setDefaultTipoObjetivo(JTipoNivelNormal.class.getName());
		this.setVariable(event.getId());
		this.setAutogenerado(id);

	}
	

	public BizSqlEvent autogenerarMeta() throws Exception {
		BizSqlEvent event = new BizBSPSqlEvent();
		event.dontThrowException(true);
		boolean read = event.read(getVariable());
		String sqlOld= event.getConsulta();

		JAutogenerador autogenerador = new JAutogenerador(this, JAutogenerador.META);
		
		event.setConsulta(autogenerador.getOutputSql());
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
  	return event;
 
	}
	
	

	@Override
	public void processInsert() throws Exception {
		if (pDescripcion.isNull())
			pDescripcion.setValue("Objetivo sin nombre");
		setLogicaContrato(JContratoObjetivo.class.getName());
		autogenerar();
		super.processInsert();
		autogenerar();
		super.processUpdate();

	}

	@Override
	public void processUpdate() throws Exception {
//		if (pDescripcion.isNull())
//			pDescripcion.setValue("MS por ruta " + getIdAerolinea());
		setLogicaContrato(JContratoObjetivo.class.getName());
		autogenerar();
		super.processUpdate();
		

	}

}
