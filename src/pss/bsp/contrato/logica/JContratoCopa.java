package pss.bsp.contrato.logica;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.detalleCopa.BizDetalleCopa;
import pss.bsp.contrato.detalleCopa.GuiDetalleCopa;

public class JContratoCopa implements ILogicaContrato {

//	@Override
//	public void addObjetivos(BizObjetivo contrato) throws Exception {
//		JRecords<BizCopaCabecera> min = new JRecords<BizCopaCabecera>(BizCopaCabecera.class);
//		min.addFilter("company", contrato.getCompany());
//		min.addOrderBy("periodoDesde","desc");
//    min.setTop(1);
//    JIterator<BizCopaCabecera> it = min.getStaticIterator();
//    if (!it.hasMoreElements()) return;
//    BizCopaCabecera cabecera = it.nextElement();
//    
//		JRecords<BizCopaDetalle> detalles = new JRecords<BizCopaDetalle>(BizCopaDetalle.class);
//		detalles.addFilter("company", contrato.getCompany());
//		detalles.addFilter("idPDF", contrato.getId());
//		JIterator<BizCopaDetalle> itD =detalles.getStaticIterator();
//		while (itD.hasMoreElements()) {
//			BizCopaDetalle detalle = itD.nextElement();
//			
//			createObjetivo(contrato,detalle);
//		}
//	}
//
//	public void createObjetivo(BizObjetivo contrato,BizCopaDetalle detalle) throws Exception {
//		BizDetalle deta = new BizDetalle();
//		deta.setId(contrato.getId());
//		deta.setCompany(contrato.getCompany());
//		deta.setDescripcion("Market share "+detalle.getMarketingID());
//		deta.setVariable(1);
//		deta.setVariableGanancia(1);
//		
//		deta.processInsert();
//		
//		BizNivel nivel = new BizNivel();
//		nivel.setCompany(deta.getCompany());
//		nivel.setId(deta.getId());
//		nivel.setTipoObjetivo(JTipoNivelNormal.class.getName());
//		nivel.setValor(detalle.getFMS());
//		nivel.setTipoPremio(JTipoPremioPorcentajeDelTotal.class.getName());
//		nivel.setPremio(2);
//		nivel.processInsert();
//		
//	}

	@Override
	public GuiDetalle getWin() throws Exception {
		return new GuiDetalleCopa();
	}

	@Override
	public BizDetalle getBiz() throws Exception {
		return new BizDetalleCopa();
	}
	
	public boolean needIndicadorObjetivo() throws Exception {
		return false;
	}
	public boolean needIndicadorObjetivoGanancia() throws Exception {
		return false;
	}
	@Override
	public boolean needIndicadorObjetivoAuxiliar() throws Exception {
		return false;
	}

}
