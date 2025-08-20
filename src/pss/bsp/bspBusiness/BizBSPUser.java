package pss.bsp.bspBusiness;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import pss.bsp.consola.BizBSPConsola;
import pss.bsp.pais.BizPais;
import pss.common.security.BizUsuario;
import pss.common.security.BizUsuarioRol;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.tools.JDateTools;

public class BizBSPUser extends BizUsuario {
	
	BizPais bspPais = null;
	BizBSPConsola bspConsola = null;
	JString pUsuarioPre = new JString() {
		public void preset() throws Exception {
			if (pUsuarioPre.isRawNotNull()) return;
			pUsuarioPre.setValue(calculeUsuarioPre());
		};
	};
	JInteger pOldUsuarioRol = new JInteger() {
		public void preset() throws Exception {
			if (pOldUsuarioRol.isRawNotNull()) return;
			pOldUsuarioRol.setValue(loadUsuarioRol());
		};
	};
		
	JInteger pUsuarioRol = new JInteger() {
		public void preset() throws Exception {
			if (pUsuarioRol.isRawNotNull()) return;
			pUsuarioRol.setValue(loadUsuarioRol());
		};
	};

	public String getUsuarioPre() throws Exception {
		return pUsuarioPre.getValue();
	}
	
	public BizBSPConsola getBspConsola() {
		return bspConsola;
	}
	

	public long getUsuarioRol() throws Exception {
		return pUsuarioRol.getValue();
	}
	public long getOldUsuarioRol() throws Exception {
		return pOldUsuarioRol.getValue();
	}
	
	public Integer loadUsuarioRol()  throws Exception {
		BizUsuarioRol ur = new BizUsuarioRol();
		ur.dontThrowException(true);
		ur.addFilter("usuario", GetUsuario());
		ur.addFilter("company", getCompany());
		if (!ur.read()) return null;
		return ur.getRol();
	}

	public String calculeUsuarioPre()  throws Exception {
		if (GetUsuario()==null) return GetUsuario();
		if (GetUsuario().toUpperCase().endsWith("."+getCompany().toUpperCase()))
			return GetUsuario().toUpperCase().substring(0, GetUsuario().toUpperCase().indexOf("."+getCompany().toUpperCase()));
		return GetUsuario();
	}
	public void setBspConsola(BizBSPConsola bspConsola) {
		this.bspConsola = bspConsola;
	}

	public BizBSPUser() throws Exception {
		super();
	}
	
	
	
	public synchronized static BizBSPUser getUsrBSP() {
		return (BizBSPUser) BizUsuario.getUsr();
	}
	
	public static JCompanyBusinessBSP getBusinessBSP() throws Exception {
		return (JCompanyBusinessBSP) BizUsuario.getUsr().getObjBusiness();
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("usuario_pre", pUsuarioPre);
		this.addItem("usuario_rol", pUsuarioRol);
		this.addItem("oldusuario_rol", pOldUsuarioRol);
		super.createProperties();
	}
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(VIRTUAL, "usuario_pre", "usuario_pre", true, false,10);
		this.addFixedItem(VIRTUAL, "usuario_rol", "usuario_rol", true, false,10);
		this.addFixedItem(VIRTUAL, "oldusuario_rol", "oldusuario_rol", true, false,10);
		super.createFixedProperties();
	}
	public BizPais getObjBSPPais() throws Exception {
		if (bspPais!=null) return bspPais;
		BizPais pais = new BizPais();
		pais.read(BizBSPCompany.getObjBSPCompany(getCompany()).getPais());
		return bspPais=pais;
	}
	@Override
	public long getGMT() throws Exception {
		BizPais pais= getObjBSPPais();
		return pais.getGMT();
	}
	
	@Override
	public boolean read() throws Exception {
		pUsuarioRol.setNull();
		pOldUsuarioRol.setNull();
		return super.read();
	}
	@Override
	public Date todayGMT(boolean withHora) throws Exception {
		BizPais pais= getObjBSPPais();
		String gmt = pais.getGMT()>0?"+"+pais.getGMT():""+pais.getGMT();
		
		Calendar cSchedStartCal = Calendar.getInstance(TimeZone.getTimeZone("GMT" + gmt));
		long gmtTime = cSchedStartCal.getTime().getTime();

		long timezoneAlteredTime = gmtTime + TimeZone.getTimeZone("GMT" + gmt).getRawOffset();
		Calendar cSchedStartCal1 = Calendar.getInstance(TimeZone.getTimeZone("GMT" + gmt));
		cSchedStartCal1.setTimeInMillis(timezoneAlteredTime);
		Date d = cSchedStartCal1.getTime();
		if (withHora)
			return d;
		return JDateTools.StringToDate(JDateTools.DateToString(d), JDateTools.DEFAULT_DATE_FORMAT);
	}
	@Override
	public void processInsert() throws Exception {
		if (getUsuarioPre().startsWith("ADMIN")) {
			throw new Exception ("No puede crear usuario que comiencen su nombre con ADMIN");
		}
		if (!getUsuarioPre().endsWith("."+getCompany().toUpperCase()))
			SetUsuario(getUsuarioPre().toUpperCase()+"."+getCompany().toUpperCase());
		this.setCustomMenu("EMPTY");
		super.processInsert();
		
		if (pUsuarioRol.isNotNull()) {
			BizUsuarioRol ur = new BizUsuarioRol();
			ur.SetUsuario(GetUsuario());
			ur.setCompany(getCompany());
			ur.setRol(getUsuarioRol());
			ur.processInsert();
		}
	}
	
	@Override
	public void processUpdate() throws Exception {
		if (getUsuarioPre().startsWith("ADMIN")) {
			throw new Exception ("No puede crear usuario que comiencen su nombre con ADMIN");
		}
		if (!getUsuarioPre().endsWith("."+getCompany().toUpperCase()))
			SetUsuario(getUsuarioPre().toUpperCase()+"."+getCompany().toUpperCase());
		super.processUpdate();
		 
		if (pUsuarioRol.isNotNull()) {
			BizUsuarioRol ur = new BizUsuarioRol();
			ur.addFilter("usuario",GetUsuario());
			ur.addFilter("company",getCompany());
			ur.addFilter("rol",getOldUsuarioRol());
			ur.dontThrowException(true);
			if (ur.read()) {
				ur.processDelete();
				ur.setRol(getUsuarioRol());
				ur.processInsert();
			} else {
				ur.setRol(getUsuarioRol());
				ur.processInsert();
			}
			pOldUsuarioRol.setValue(pUsuarioRol.getValue());
		}
	}
	
	@Override
	public boolean serializeOnlyProperties() throws Exception {
		return false;
	}
	
	
}
