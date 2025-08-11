package  pss.bsp.parseo.bspParseo;

import pss.bsp.bo.data.GuiBODetalles;
import pss.bsp.bo.formato.BizFormato;
import pss.bsp.bo.gen.IFinderGenBO;
import pss.bsp.bo.gen.JParseoGenBO;
import pss.bsp.bo.gen.cabecera.BizGenCabecera;
import pss.bsp.bo.gen.cabecera.GuiGenCabecera;
import pss.bsp.bo.gen.detalle.BizGenDetalle;
import pss.bsp.bo.gen.detalle.GuiGenDetalles;
import pss.bsp.pdf.BizPDF;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;


public class JParseoGenericoBO extends JParseoGenBO implements IFinderGenBO, IBspSititaParser {

	public JParseoGenericoBO() {
		this.addListenerDetect(this);
	}
	public void detectDetail(BizGenDetalle detail) throws Exception {
		detail.processInsert();
	}

	public void detectHeader(BizGenCabecera header) throws Exception {
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
	public void processDks(String zCompany) throws Exception {
		
	}


	public JWin getGuiHeader(String zCompany, String zOwner, String zId) throws Exception {
		GuiGenCabecera cabecera = new GuiGenCabecera();
		cabecera.GetcDato().addFilter("company", zCompany);
	//	cabecera.GetcDato().addFilter("owner", zOwner);
		cabecera.GetcDato().addFilter("idInterfaz", Long.parseLong(zId));
		cabecera.GetcDato().read();
		return cabecera;
	}

	public JWins getGuisDetail(String zCompany, String zOwner, String zId) throws Exception {
		GuiGenDetalles detalle = new GuiGenDetalles();
		detalle.getRecords().addFilter("company", zCompany);
//		detalle.getRecords().addFilter("owner", zOwner);
		detalle.getRecords().addFilter("idInterfaz",  Long.parseLong(zId));
		return detalle;
	}

	public void eliminar(String zCompany, String zOwner, String zId) throws Exception {
		GuiGenCabecera cabecera = new GuiGenCabecera();
		cabecera.GetcDato().addFilter("company", zCompany);
	//	cabecera.GetcDato().addFilter("owner", zOwner);
		cabecera.GetcDato().addFilter("idInterfaz",  Long.parseLong(zId));
		cabecera.GetcDato().dontThrowException(true);
		if (cabecera.GetcDato().read());
			cabecera.GetcDato().processDelete();
		
		GuiGenDetalles detalle = new GuiGenDetalles();
		detalle.getRecords().addFilter("company", zCompany);
	//	detalle.getRecords().addFilter("owner", zOwner);
		detalle.getRecords().addFilter("idInterfaz",  Long.parseLong(zId));
		detalle.getRecords().delete();
		
		GuiBODetalles detalleBO = new GuiBODetalles();
		detalleBO.getRecords().addFilter("company", zCompany);
	//	detalle.getRecords().addFilter("owner", zOwner);
		detalleBO.getRecords().addFilter("id_interfaz",  Long.parseLong(zId));
		detalleBO.getRecords().delete();
	}
	public void refill(String zCompany, String zOwner, String zId) throws Exception {
		GuiBODetalles detalleBO = new GuiBODetalles();
		detalleBO.getRecords().addFilter("company", zCompany);
	//	detalle.getRecords().addFilter("owner", zOwner);
		detalleBO.getRecords().addFilter("id_interfaz",  Long.parseLong(zId));
		detalleBO.getRecords().delete();
		
		
		GuiGenDetalles detalle = new GuiGenDetalles();
		detalle.getRecords().addFilter("company", zCompany);
	//	detalle.getRecords().addFilter("owner", zOwner);
		detalle.getRecords().addFilter("idInterfaz",  Long.parseLong(zId));
		JIterator<BizGenDetalle> it = detalle.getRecords().getStaticIterator();
		while (it.hasMoreElements()) {
			BizGenDetalle det =it.nextElement();
			det.processBODetalle();
		}
	}

	public boolean fillFormat(BizPDF pdfBase,String[][] learningBase,BizFormato formato) throws Exception {
		// no aplica
		return false;
	}
	
	public void detectFormat(BizFormato format) throws Exception {
		if (format.checkRecordExists())
			format.processUpdate();
		else 
			format.processInsert();
	}
	@Override
	public boolean supportRefunds() {
		return false;
	}
	
}
