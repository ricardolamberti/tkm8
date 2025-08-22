package  pss.bsp.consola.typesLicense;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.ibm.icu.util.StringTokenizer;

import pss.common.security.license.JLicense;
import pss.common.security.license.typeLicense.detail.BizTypeLicenseDetail;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;

public class JTKMBase extends JLicense implements ITKM {

	public boolean canUpload() {
		return true;
	}

	public void requestMaxUsosPorMes() throws Exception {
		Date d = this.getLastChange("MAX_USOS_POR_MES");
		GregorianCalendar cal = new GregorianCalendar();
		GregorianCalendar now = new GregorianCalendar();
		cal.setTime(d);
		now.setTime(new Date());

		if (cal.get(Calendar.MONTH) != now.get(Calendar.MONTH) || cal.get(Calendar.YEAR) != now.get(Calendar.YEAR)) {
			this.reset("MAX_USOS_POR_MES");
		}
		if (!this.request("MAX_USOS_POR_MES")) throw new Exception("Se excedio el maximo de usos");
	}

	public boolean testMaxUsosPorMes() throws Exception {
		return this.test("MAX_USOS_POR_MES");
	}

	public void checkIATA(String zIata) throws Exception {
		String iata = this.getFieldValue("IATA");
		if (iata.equals("")) return;
		if (iata.indexOf(zIata+",")!=-1) throw new Exception("No esta habilitado para este IATA [" + zIata + "]");
	}

	public JWins getIATAs() throws Exception {
		JMap<String,String> map = JCollectionFactory.createMap();
		String iata = this.getFieldValue("IATA");
		StringTokenizer tok = new StringTokenizer(iata,",");
		while (tok.hasMoreElements()) {
			String i = tok.nextToken();
			map.addElement(i, i);
		}
		return JWins.createVirtualWinsFromMap(map);
	}
	
	
	public void startCheck(Date op) throws Exception {
		String fecha = this.getFieldValue("INICIO_OPERACIONES");
		if (fecha == null) return;
		if (fecha.equals("")) return;
		Date io = JDateTools.StringToDate(fecha);
		if (io.after(op)) throw new Exception("El pdf es anterior al inicio de la licencia");
	}

	@Override
	public void addNewLicenseDetail(BizTypeLicenseDetail zDetail) throws Exception {
		if (zDetail.getValue().equalsIgnoreCase("NOW")) zDetail.setValue(JDateTools.DateToString(new Date()));
		super.addNewLicenseDetail(zDetail);
	}
}
