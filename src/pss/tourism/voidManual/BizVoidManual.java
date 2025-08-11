package pss.tourism.voidManual;


import java.util.Date;

import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.tourism.pnr.BizPNRTicket;

public class BizVoidManual extends JRecord {

	public final static String VM_VOID = "V";
	public final static String VM_CHANGEDK = "DK";
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId=new JLong();
	private JString pCompany=new JString();
	private JString pNumeroBoleto=new JString();
	private JString pOperacion=new JString();
	private JString pDK=new JString();
	private JString pDKOriginal=new JString();
	private JDate pFecha=new JDate();
	private JString pUsuario=new JString();

	public long getId() throws Exception {
		return pId.getValue();
	}



	public String getCompany() throws Exception {
		return pCompany.getValue();
	}
	public void setFecha(Date value) throws Exception {
		pFecha.setValue(value);
	}
	public Date getFecha() throws Exception {
		return pFecha.getValue();
	}
	public void setNumeroBoleto(String value) throws Exception {
		pNumeroBoleto.setValue(value);
	}
	public String geNumeroBoleto() throws Exception {
		return pNumeroBoleto.getValue();
	}
	public void setOperacion(String value) throws Exception {
		pOperacion.setValue(value);
	}
	public String geOperacion() throws Exception {
		return pOperacion.getValue();
	}
	public void setDKOriginal(String value) throws Exception {
		pDKOriginal.setValue(value);
	}
	public String getDKOriginal() throws Exception {
		return pDKOriginal.getValue();
	}
	public void setDK(String value) throws Exception {
		pDK.setValue(value);
	}
	public String getDK() throws Exception {
		return pDK.getValue();
	}
	public void setUsuario(String value) throws Exception {
		pUsuario.setValue(value);
	}

	public void setCompany(String zValor) {
		this.pCompany.setValue(zValor);
	}

	public String getUsuario() throws Exception {
		return this.pUsuario.getValue();
	}



	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizVoidManual() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("company", pCompany);
		addItem("numero_boleto", pNumeroBoleto);
		addItem("operacion", pOperacion);
		addItem("dk", pDK);
		addItem("dk_original", pDKOriginal);
		addItem("fecha", pFecha);
		addItem("usuario", pUsuario);
	}

	@Override
	public void createFixedProperties() throws Exception {

		addFixedItem(KEY, "id", "id", false, false, 64);
		addFixedItem(FIELD, "company", "Company", true, false, 100);
		addFixedItem(FIELD, "numero_boleto", "Boleto", true, false, 100);
		addFixedItem(FIELD, "operacion", "Operacion", true, false, 10);
		addFixedItem(FIELD, "dk", "dk", true, false, 20);
		addFixedItem(FIELD, "dk_original", "dk original", true, false, 20);
		addFixedItem(FIELD, "fecha", "Fecha", true, false, 20);
		addFixedItem(FIELD, "usuario", "Usuario", true, false, 100);
		
	}


	@Override
	public String GetTable() {
		return "TUR_MANUALVOID";
	}
	public void execAnular(String company,String boleto) throws Exception {
		JExec oExec = new JExec(this, "procAnular") {

			@Override
			public void Do() throws Exception {
				procAnular( company, boleto);
			}
		};
		oExec.execute();
	}	
	public boolean procDesAnular(String company,String boleto) throws Exception {
		BizVoidManual vm = new BizVoidManual();
		vm.dontThrowException(true);
		vm.addFilter("company", company);
		vm.addFilter("operacion", VM_VOID);
		vm.addFilter("numero_boleto", boleto);
		if (!vm.read()) return false;
		BizPNRTicket pnr = new BizPNRTicket();
		pnr.dontThrowException(true);
		if (!pnr.ReadByBoleto(company, boleto)) return false;
		if (!pnr.isVoided()) return false;
		pnr.setVoid(false);
		pnr.setNullVoidDate();
		pnr.update();
		vm.processDelete();
		return true;
	}
	public boolean procAnular(String company,String boleto) throws Exception {
		BizVoidManual vm = new BizVoidManual();
		vm.dontThrowException(true);
		vm.addFilter("company", company);
		vm.addFilter("operacion", VM_VOID);
		vm.addFilter("numero_boleto", boleto);
		boolean exists= vm.read();
		BizPNRTicket pnr = new BizPNRTicket();
		pnr.dontThrowException(true);
		if (!pnr.ReadByBoleto(company, boleto)) return false;
		if (pnr.isVoided()) return false;
		pnr.setVoid(true);
		pnr.setVoidDate(new Date());
		pnr.update();
		
		if (!exists)
			vm = new BizVoidManual();
		vm.setCompany(company);
		vm.setNumeroBoleto(boleto);
		vm.setFecha(new Date());
		vm.setOperacion(VM_VOID);
		vm.setUsuario(BizUsuario.getUsr().GetUsuario());
		if (!exists)
			vm.processInsert();
		else
			vm.processUpdate();
		return true;
	}
	public boolean procAnularForce(String company,String boleto) throws Exception {
		BizVoidManual vm = new BizVoidManual();
		vm.dontThrowException(true);
		vm.addFilter("company", company);
		vm.addFilter("operacion", VM_VOID);
		vm.addFilter("numero_boleto", boleto);
		boolean exists= vm.read();
		BizPNRTicket pnr = new BizPNRTicket();
		pnr.dontThrowException(true);
		if (pnr.ReadByBoleto(company, boleto)) {
			if (!pnr.isVoided()) {
				pnr.setVoid(true);
				pnr.setVoidDate(new Date());
				pnr.update();
			}
		}

		
		if (!exists)
			vm = new BizVoidManual();
		vm.setCompany(company);
		vm.setNumeroBoleto(boleto);
		vm.setFecha(new Date());
		vm.setOperacion(VM_VOID);
		vm.setUsuario(BizUsuario.getUsr().GetUsuario());
		if (!exists)
			vm.processInsert();
		else
			vm.processUpdate();
		return true;
	}
	public void execChangeDK(String company,String boleto,String DK) throws Exception {
		JExec oExec = new JExec(this, "procChangeDK") {

			@Override
			public void Do() throws Exception {
				procChangeDK( company, boleto,DK);
			}
		};
		oExec.execute();
	}	
	public void execDesChangeDK(String company,String boleto) throws Exception {
		JExec oExec = new JExec(this, "procChangeDK") {

			@Override
			public void Do() throws Exception {
				procDesChangeDK( company, boleto);
			}
		};
		oExec.execute();
	}	
	public boolean procChangeDK(String company,String boleto,String DK) throws Exception {
		BizVoidManual vm = new BizVoidManual();
		vm.dontThrowException(true);
		vm.addFilter("company", company);
		vm.addFilter("operacion", VM_CHANGEDK);
		vm.addFilter("DK", DK);
		vm.addFilter("numero_boleto", boleto);
		if (vm.read()) return false;
		BizPNRTicket pnr = new BizPNRTicket();
		pnr.dontThrowException(true);
		if (!pnr.ReadByBoleto(company, boleto)) return false;
		if (pnr.getCustomerIdReducido().equals(DK)) return false;
		String orig = pnr.getCustomerIdReducido();
		pnr.setCustomerIdReducido(DK);
		pnr.update();
		vm = new BizVoidManual();
		vm.setCompany(company);
		vm.setNumeroBoleto(boleto);
		vm.setFecha(new Date());
		vm.setOperacion(VM_CHANGEDK);
		vm.setDK(DK);
		vm.setDKOriginal(orig);
		vm.setUsuario(BizUsuario.getUsr().GetUsuario());
		vm.processInsert();
		return true;
	}
	public boolean procDesChangeDK(String company,String boleto) throws Exception {
		BizVoidManual vm = new BizVoidManual();
		vm.dontThrowException(true);
		vm.addFilter("company", company);
		vm.addFilter("operacion", VM_CHANGEDK);
		vm.addFilter("numero_boleto", boleto);
		vm.addOrderBy("fecha","DESC");
		if (!vm.read()) return false;
		BizPNRTicket pnr = new BizPNRTicket();
		pnr.dontThrowException(true);
		if (!pnr.ReadByBoleto(company, boleto)) return false;
		if (pnr.getCustomerIdReducido().equals(vm.getDKOriginal())) return false;
		pnr.setCustomerIdReducido(vm.getDKOriginal());
		pnr.update();
		vm.deleteMultiple();
		return true;
	}

	public static Date isManualVoid(String company,String boleto) throws Exception {
		BizVoidManual vm = new BizVoidManual();
		vm.dontThrowException(true);
		vm.addFilter("company", company);
		vm.addFilter("numero_boleto", boleto);
		vm.addFilter("operacion", VM_VOID);
		if (!vm.read()) return null;
		return vm.getFecha();
	}
	public static String isManualDK(String company,String boleto) throws Exception {
		BizVoidManual vm = new BizVoidManual();
		vm.dontThrowException(true);
		vm.addFilter("company", company);
		vm.addFilter("numero_boleto", boleto);
		vm.addFilter("operacion", VM_CHANGEDK);
		vm.addOrderBy("fecha","DESC");
	
		if (!vm.read()) return null;
		return vm.getDK();
	}
}
