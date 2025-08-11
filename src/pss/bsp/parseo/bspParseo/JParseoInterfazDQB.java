package pss.bsp.parseo.bspParseo;

import pss.bsp.interfaces.dqb.IFinderDQB;
import pss.bsp.interfaces.dqb.JParseoDQB;
import pss.bsp.interfaces.dqb.cabecera.BizDQBCabecera;
import pss.bsp.interfaces.dqb.cabecera.GuiDQBCabecera;
import pss.bsp.interfaces.dqb.detalle.BizDQBDetalle;
import pss.bsp.interfaces.dqb.detalle.GuiDQBDetalles;
import pss.core.win.JWin;
import pss.core.win.JWins;

public class JParseoInterfazDQB extends JParseoDQB implements IFinderDQB ,IInterfazParseo {

	
	public JParseoInterfazDQB() {
		this.addListenerDetect(this);
	}
	
	public void detectDetail(BizDQBDetalle detail) throws Exception {

		detail.processInsert();
	}

	public void detectHeader(BizDQBCabecera header) throws Exception {
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
		GuiDQBCabecera cabecera = new GuiDQBCabecera();
		cabecera.GetcDato().addFilter("company", zCompany);
//		cabecera.GetcDato().addFilter("owner", zOwner);
		cabecera.GetcDato().addFilter("idPDF", zIdPdf);
		cabecera.GetcDato().read();
		return cabecera;
	}

	public JWins getGuisDetail(String zCompany, String zOwner, String zIdPdf) throws Exception {
		GuiDQBDetalles detalle = new GuiDQBDetalles();
		detalle.getRecords().addFilter("company", zCompany);
//		detalle.getRecords().addFilter("owner", zOwner);
		detalle.getRecords().addFilter("idPDF", zIdPdf);
		detalle.getRecords().addOrderBy("fecha");
		return detalle;
	}

	public void eliminar(String zCompany, String zOwner, String zIdPdf) throws Exception {
		GuiDQBCabecera cabecera = new GuiDQBCabecera();
		cabecera.GetcDato().addFilter("company", zCompany);
//		cabecera.GetcDato().addFilter("owner", zOwner);
		cabecera.GetcDato().addFilter("idPDF", zIdPdf);
		cabecera.GetcDato().dontThrowException(true);
		if (cabecera.GetcDato().read())
			cabecera.GetcDato().processDelete();
		
		GuiDQBDetalles detalle = new GuiDQBDetalles();
		detalle.getRecords().addFilter("company", zCompany);
//		detalle.getRecords().addFilter("owner", zOwner);
		detalle.getRecords().addFilter("idPDF", zIdPdf);
		detalle.getRecords().delete();

	}

}
