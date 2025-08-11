package  pss.bsp.parseo.bspParseo;

import pss.bsp.consolid.model.liquidacion.detail.BizLiqDetail;
import pss.bsp.consolid.model.liquidacion.detail.GuiLiqDetails;
import pss.bsp.consolid.model.liquidacion.header.BizLiqHeader;
import pss.bsp.consolid.model.liquidacion.terrestres.IFinderTerrestre;
import pss.bsp.consolid.model.liquidacion.terrestres.JParseoTerrestre;
import pss.core.win.JWin;
import pss.core.win.JWins;

public class JParseoInterfazTerrestre  extends JParseoTerrestre implements IFinderTerrestre ,IInterfazParseo {

	
	public JParseoInterfazTerrestre() {
		this.addListenerDetect(this);
	}
	
	public void detectDetail(BizLiqDetail detail) throws Exception {
		if (isContado(detail.getFormaPago())) {
			detail.setSaldo(detail.getImporte());
			detail.setContado(detail.getImporte());
		} else {
			detail.setTarjeta(detail.getImporte());
			detail.setSaldo(0);
		}
//		detail.setFiscal((isDocFiscal(detail.getTipoDoc()) ? detail.getImporte() : 0));
//		detail.setNoFiscal((!isDocFiscal(detail.getTipoDoc()) ? detail.getImporte(): 0));

		detail.processInsert();
	}
	public boolean isContado(String doc) {
		if (doc.equals("CH"))
			return true;
		if (doc.equals("NI"))
			return true;
		if (doc.equals("TR"))
			return true;
		if (doc.equals("CA"))
			return true;
		return false;
	}

	public void detectHeader(BizLiqHeader header) throws Exception {
		
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
		return null;
	}

	public JWins getGuisDetail(String zCompany, String zOwner, String zIdPdf) throws Exception {
		GuiLiqDetails detalle = new GuiLiqDetails();
		detalle.getRecords().addFilter("company", zCompany);
		detalle.getRecords().addFilter("liquidacion_id", Long.parseLong(zIdPdf));
		detalle.getRecords().addFilter("origen", "XSLX");
		detalle.getRecords().addOrderBy("linea","asc");
		return detalle;
	}

	public void eliminar(String zCompany, String zOwner, String zIdPdf) throws Exception {
		GuiLiqDetails detalle = new GuiLiqDetails();
		detalle.getRecords().addFilter("company", zCompany);
		detalle.getRecords().addFilter("liquidacion_id", Long.parseLong(zIdPdf));
		detalle.getRecords().addFilter("origen", "XSLX");
		detalle.getRecords().delete();

	}

}
