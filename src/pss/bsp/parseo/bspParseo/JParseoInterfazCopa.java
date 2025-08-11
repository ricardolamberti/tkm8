package  pss.bsp.parseo.bspParseo;

import pss.bsp.contrato.detalleCopa.BizDetalleCopa;
import pss.bsp.interfaces.copa.IFinderCopa;
import pss.bsp.interfaces.copa.JParseoCopa;
import pss.bsp.interfaces.copa.cabecera.BizCopaCabecera;
import pss.bsp.interfaces.copa.cabecera.GuiCopaCabecera;
import pss.bsp.interfaces.copa.datos.BizCopa;
import pss.bsp.interfaces.copa.detalle.BizCopaDetalle;
import pss.bsp.interfaces.copa.detalle.GuiCopaDetalles;
import pss.core.win.JWin;
import pss.core.win.JWins;

public class JParseoInterfazCopa  extends JParseoCopa implements IFinderCopa ,IInterfazParseo {

	
	public JParseoInterfazCopa() {
		this.addListenerDetect(this);
	}
	
	public void detectDetail(BizCopaDetalle detail) throws Exception {
		BizCopaDetalle det2 = new BizCopaDetalle();
		det2.dontThrowException(true);
		if (det2.read(detail.getCompany(), detail.getIdpdf(), detail.getOrigen(),detail.getDestino())) {
			double min = detail.getMinimo();
	//		det2.copyNotKeysProperties(detail);
			if  (min!=0) {
				det2.setMinimo(min);
			}
			det2.processUpdate();
			return;
		}
		detail.processInsert();
	}

	public void detectHeader(BizCopaCabecera header) throws Exception {
		header.processInsert();
	}

	public void stop() throws Exception {
//		JBDatos.GetBases().commit();	
	}

	public void error(Exception e) throws Exception {
	//	JBDatos.GetBases().rollback();	
		throw e;
		
	}

	public void start() throws Exception {
	//	JBDatos.GetBases().beginTransaction();	
	
	}


	public JWin getGuiHeader(String zCompany, String zOwner, String zIdPdf) throws Exception {
		GuiCopaCabecera cabecera = new GuiCopaCabecera();
		cabecera.GetcDato().addFilter("company", zCompany);
//		cabecera.GetcDato().addFilter("owner", zOwner);
		cabecera.GetcDato().addFilter("idPDF", zIdPdf);
		cabecera.GetcDato().read();
		return cabecera;
	}

	public JWins getGuisDetail(String zCompany, String zOwner, String zIdPdf) throws Exception {
		GuiCopaDetalles detalle = new GuiCopaDetalles();
		detalle.getRecords().addFilter("company", zCompany);
//		detalle.getRecords().addFilter("owner", zOwner);
		detalle.getRecords().addFilter("idPDF", zIdPdf);
		detalle.getRecords().addOrderBy("marketing_id");
		return detalle;
	}

	public void eliminar(String zCompany, String zOwner, String zIdPdf) throws Exception {
		GuiCopaCabecera cabecera = new GuiCopaCabecera();
		cabecera.GetcDato().addFilter("company", zCompany);
//		cabecera.GetcDato().addFilter("owner", zOwner);
		cabecera.GetcDato().addFilter("idPDF", zIdPdf);
		cabecera.GetcDato().dontThrowException(true);
		if (cabecera.GetcDato().read())
			cabecera.GetcDato().processDelete();
		
		GuiCopaDetalles detalle = new GuiCopaDetalles();
		detalle.getRecords().addFilter("company", zCompany);
//		detalle.getRecords().addFilter("owner", zOwner);
		detalle.getRecords().addFilter("idPDF", zIdPdf);
		detalle.getRecords().delete();

	}

}
