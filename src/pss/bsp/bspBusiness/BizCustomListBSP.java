package pss.bsp.bspBusiness;

import pss.bsp.auxiliar.lineaTemporal.BizLineaTemporal;
import pss.bsp.bo.data.BizBODetalle;
import pss.bsp.contrato.BizContrato;
import pss.bsp.pdf.arg.detalle.BizArgDetalle;
import pss.bsp.pdf.chl.detalle.BizChileDetalle;
import pss.bsp.pdf.ecu.detalle.BizEcuDetalle;
import pss.bsp.pdf.gua.detalle.BizGuaDetalle;
import pss.bsp.pdf.mex.detalle.BizMexDetalle;
import pss.bsp.pdf.pan.detalle.BizPanDetalle;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.records.JRecord;
import pss.tourism.pnr.BizBooking;
import pss.tourism.pnr.BizPNROtro;
import pss.tourism.pnr.BizPNRTicket;

public class BizCustomListBSP extends JRecord {

	public BizCustomListBSP() throws Exception {
	}

	public void attachRelationMap(JRelations rels) throws Exception {
	//	rels.addRelationChild(6, "Segmento Aereos", BizPNRSegmentoAereo.class);
		rels.addRelationChild(4, "Boletos PNR", BizPNRTicket.class);
		rels.addRelationChild(8, "Hotel,Autos,etc", BizPNROtro.class);
		rels.addRelationChild(14, "Bookings", BizBooking.class);
		rels.addRelationChild(3, "Contratos", BizContrato.class);
		rels.addRelationChild(5, "Boletos BO", BizBODetalle.class);
//		rels.addRelationChild(6, "Publicidad", BizPublicity.class);
//		rels.addRelationChild(7, "Linea Temporal", BizLineaTemporal.class);
		rels.addRelationChild(7, "Linea Temporal", BizLineaTemporal.class);
		rels.addRelationChild(2, "Boletos Chile BSP", BizChileDetalle.class);
		rels.addRelationChild(6, "Boletos Mexico BSP", BizMexDetalle.class);
		rels.addRelationChild(9, "Boletos Guatemala BSP", BizGuaDetalle.class);
		rels.addRelationChild(10, "Boletos Ecuador BSP", BizEcuDetalle.class);
		rels.addRelationChild(11, "Boletos Panama BSP", BizPanDetalle.class);
		rels.addRelationChild(1, "Boletos Otros BSP", BizArgDetalle.class);

	}
	
}
