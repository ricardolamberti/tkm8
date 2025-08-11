package  pss.common.regions.nodes;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import pss.common.components.JSetupParameters;
import pss.common.personalData.BizPersona;
import pss.common.regions.company.BizCompany;
import pss.common.regions.divitions.BizPais;
import pss.common.regions.propagation.BizPropagate;
import pss.common.regions.propagation.JSetupPropagate;
import pss.common.security.BizUsuario;
import pss.core.connectivity.messageManager.common.core.JMMRecord;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizNodo extends JMMRecord {

	public static final String DEFAULT_NODO = "DEFAULT";

	public static boolean sprocessDeleteFlag = true;
	private static final String aNodosHijos[] = { "pss.core.drivers.printers.BizPrinter" };
	
//	public static boolean sprocessDeleteFlag=true;

	private JString pNodo=new JString();
	private JString pCompany=new JString();
	private JLong pIdPersona=new JLong();
	private JString pCodigoIdent=new JString();
	private JString pNodoPadre=new JString();
	private JString pPropagar=new JString();
	private JString pHost=new JString();
	private JString pModeloPropagar=new JString();
	private JLong gmt = new JLong();
	private JBoolean pNodoForaneo = new JBoolean();
	
	private static JMap<String, BizNodo> mNodo = JCollectionFactory.createMap(10);

	public static void cleanObjNodo(String company, String store) throws Exception {
		mNodo.removeElement(company + store);
	}

	public static void cleanCache() {
		mNodo = null;
		mNodo = JCollectionFactory.createMap(10);
	}
	
	public static BizNodo getObjNodo(String company, String nodo) throws Exception {
		return getObjNodo(null,company, nodo);
	}
	
	public static BizNodo getObjNodo(BizNodo nod, String company, String nodo) throws Exception {
		BizNodo val = mNodo.getElement(company+nodo);
		if (val == null) {
			if ( nod == null )
			  val = new BizNodo();
			else
				val = nod;
			val.Read(company,nodo);
			mNodo.addElement(company+nodo, val);
		}
		return val;
	}



	
//	private static JMap<String, BizNodo> mNodo = JCollectionFactory.createMap(10);

//	public static BizNodo getObjNodo(String company, String nodo) throws Exception {
//		return getObjNodo(null,company, nodo);
//	}
	
//	public static void cleanObjNodo(String company, String store) throws Exception {
//		mNodo.removeElement(company + store);
//	}
	
//	public static void cleanCache() {
//		mNodo = null;
//		mNodo = JCollectionFactory.createMap(10);
//	}
	
	public void clearPersistantObjects() throws Exception{
		oPersona=null;
	}
	
//	public static BizNodo getObjNodo(BizNodo nod, String company, String nodo) throws Exception {
//		BizNodo val = mNodo.getElement(company+nodo);
//		if (val == null) {
//			if ( nod == null )
//			  val = new BizNodo();
//			else
//				val = nod;
//			val.Read(company,nodo);
//			mNodo.addElement(company+nodo, val);
//		}
//		return val;
//	}
	
	public static String getStoreDesc(String company, JString store) throws Exception {
		if (store.isEmpty())
			return "sin definir";
		BizNodo val = BizCompany.getCompany(company).findNodo(store.getValue());
		return val.GetSiteName();
	}

	
	public static String getCompanyDesc(BizCompany cpy, JString val) throws Exception {
		if (val.isEmpty())
			return "sin definir";
		if (cpy != null)
			return cpy.getDescription();
		return BizCompany.getObjCompany(val.getValue()).getDescription();
	}
	
	/**
	 * @return the gmt
	 */
	public long getGmt() throws Exception {
		return gmt.getValue();
	}

	/**
	 * @param gmt
	 *          the gmt to set
	 */
	public void setGmt(long gmt) {
		this.gmt.setValue(gmt);
	}

	private JBoolean pLocal = new JBoolean() {

		@Override
		public void preset() throws Exception {
			ObtenerIsLocal();
		}
	};
	private JString pDescripcion = new JString() {

		@Override
		public void preset() throws Exception {
			assignDescription();
		}
	};
	private JString pServidorLicencia = new JString();
	private JInteger pPort = new JInteger();
	private JString pPais = new JString();
	private JBoolean pModoConcentrador = new JBoolean();
	private JString pReportDir = new JString();
	private JString pReportCabecera = new JString();
	private JString pImpresoraReportes = new JString();
	private JBoolean pModuloEventosHabilitado = new JBoolean();

	private BizNodoDatabase oNodoDatabaseMaster = null; // master
	// private BizMjeCliente oMjeCliente=null;
	private BizNodo oNodoPadre = null;
	private BizPais oPais = null;
	private BizCompany oCompany = null;
	private BizPersona oPersona = null;

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizNodo() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("nodo", pNodo);
		addItem("id_person", pIdPersona);
		addItem("codigo_ident", pCodigoIdent);
		addItem("descripcion", pDescripcion);
		addItem("host", pHost);
		addItem("port", pPort);
		addItem("nodo_padre", pNodoPadre);
		addItem("propagar", pPropagar);
		addItem("pais", pPais);
		addItem("local", pLocal);
		addItem("concentrador", pModoConcentrador);
		addItem("report_dir", pReportDir);
		addItem("modelo_propagar", pModeloPropagar);
		addItem("reporte_cabecera", pReportCabecera);
		addItem("impresora_reportes", pImpresoraReportes);
		addItem("servidor_licencia", pServidorLicencia);
		addItem("modulo_eventos", pModuloEventosHabilitado);
		addItem("gmt", gmt);
		addItem("nodo_foraneo", pNodoForaneo);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "Empresa", true, true, 15);
		addFixedItem(KEY, "nodo", "Sucursal", true, true, 15);
		addFixedItem(FIELD, "id_person", "Id Persona", true, true, 18);
		addFixedItem(FIELD, "codigo_ident", "Código", true, false, 18);
		addFixedItem(VIRTUAL, "descripcion", "Descripción", true, false, 70);
		addFixedItem(FIELD, "host", "Host", true, false, 20);
		addFixedItem(FIELD, "port", "Port", true, false, 5);
		addFixedItem(FIELD, "nodo_padre", "Sucursal Padre", true, false, 15);
		addFixedItem(FIELD, "propagar", "Propagar", true, false, 1);
		addFixedItem(FIELD, "pais", "Pais", true, true, 15);
		addFixedItem(VIRTUAL, "local", "Local", true, false, 1);
		addFixedItem(FIELD, "report_dir", "Directorio reportes", true, false, 80);
		addFixedItem(FIELD, "modelo_propagar", "Modelo Propagar", true, false, 50);
		addFixedItem(FIELD, "reporte_cabecera", "Reporte Cabecera", true, false, 80);
		addFixedItem(FIELD, "impresora_reportes", "Impresora de Reportes", true, false, 80);
		addFixedItem(FIELD, "servidor_licencia", "Servidor de Licencias", true, false, 20);
		addFixedItem(FIELD, "modulo_eventos", "Listeners habilitados", true, false, 1, 0, null, "S");
		addFixedItem(FIELD, "gmt", "GMT", true, false, 2);
		addFixedItem(FIELD, "nodo_foraneo", "Nodo Foraneo", true, false, 1);
	}

	@Override
	public String GetTable() {
		return "nod_nodo";
	}

	public long getIdPersona() throws Exception {
		return pIdPersona.getValue();
	}

	public void SetPersona(long zValor) throws Exception {
		pIdPersona.setValue(zValor);
	}

	public void setCodigoIdent(String zValor) throws Exception {
		pCodigoIdent.setValue(zValor);
	}

	public void setHost(String zValor) throws Exception {
		pHost.setValue(zValor);
	}

	public String getCompany() throws Exception {
		return this.pCompany.getValue();
	}

	public String GetPais() throws Exception {
		return this.pPais.getValue();
	}

	public String GetNodo() throws Exception {
		return this.pNodo.getValue();
	}

	public String getHost() throws Exception {
		return this.pHost.getValue();
	}

	public String getCodigoIdent() throws Exception {
		return this.pCodigoIdent.getValue();
	}

	public JString GetpNodo() throws Exception {
		return pNodo;
	}

	public String GetReporteCabecera() throws Exception {
		return pReportCabecera.getValue();
	}

	public String GetImpresoraReportes() throws Exception {
		return pImpresoraReportes.getValue();
	}

	public boolean hasImpresoraReportes() throws Exception {
		return pImpresoraReportes.isNotNull();
	}

	public String getReportDir() throws Exception {
		return pReportDir.getValue();
	}

	public void setReportDir(String zDir) throws Exception {
		pReportDir.setValue(zDir);
	}

	/*
	 * public static BizNodo GetVirtualNodo() throws Exception { return (BizNodo)
	 * oVirtualNodo.get(); } public static void SetVirtualNodo(BizNodo zNodo)
	 * throws Exception { oVirtualNodo.set(zNodo); }
	 */

	/*
	 * public synchronized static BizNodo GetNodoLocal() throws Exception { if
	 * (oNodoLocal != null) return oNodoLocal; BizNodo newNodo = new BizNodo();
	 * newNodo.SetNoExcSelect(true); if (!newNodo.Read("PSS",
	 * BizPssConfig.GetNodoLocal())) {
	 * JExcepcion.SendError("Sucursal Local Inexistente^, '" +
	 * BizPssConfig.GetNodoLocal() + "' "); } return oNodoLocal = newNodo; }
	 */
	/*
	 * public static BizNodo ReadStatic(String id_nodo) throws Exception { return
	 * (BizNodo) ReadStaticRecord(id_nodo + "|", BizNodo.class); }
	 */
	/*
	 * public static boolean ifModoRemoto() throws Exception { BizNodo oNodo =
	 * BizNodo.GetVirtualNodo(); if ( oNodo == null ) return false; return
	 * !oNodo.ifModoLocal(); }
	 * 
	 * public boolean ifModoLocal() throws Exception { return
	 * this.pModoLocal.GetValorOrig().equals("S"); }
	 */
	public boolean ifPropagar() throws Exception {
		return this.pPropagar.getValue() != null && this.pPropagar.getValue().equals("S");
	}

	/*
	 * public void ConfigurarNodo() throws Exception { String sNodo =
	 * BizPssConfig.GetNodoRemoto(); if ( sNodo.equals("") ) sNodo =
	 * BizPssConfig.GetNodoLocal(); this.pNodo.SetValor(sNodo);
	 * 
	 * if (sNodo.equalsIgnoreCase(BizPssConfig.GetNodoLocal())) {
	 * this.pModoLocal.SetValor("S"); return; }
	 * JAplicacion.GetApp().AbrirApp(JAplicacion.AppFrontEndWin(),
	 * JAplicacion.AppTipoWindows(), false); JObject.SetVulneraPreSet(true);
	 * BizLoginTrace.SetLoginFail(false); this.pModoLocal.SetValor("N");
	 * BizPssConfig oPss = new BizPssConfig(); String sHost = oPss.GetValor(sNodo,
	 * "HOST"); String sPort = oPss.GetValor(sNodo, "PORT"); BizTcpClient oTcp =
	 * new BizTcpClient(); oTcp.SetDireccion(sHost);
	 * oTcp.SetPort(Integer.parseInt(sPort)); BizClientMensaje oClientMensaje =
	 * new BizClientMensaje(); oClientMensaje.SetConexion(oTcp); oMjeCliente = new
	 * BizMjeCliente(); oMjeCliente.SetClientMensaje(oClientMensaje); }
	 */

	/*
	 * public boolean Read(String zNodo) throws Exception { this.addFilter("nodo",
	 * zNodo); return this.Read(); }
	 */

	public boolean Read(String zCompany, String zNodo) throws Exception {
		this.addFilter("company", zCompany);
		this.addFilter("nodo", zNodo);
		return this.read();
	}
	
	public String GetDescrip() throws Exception {
		return this.getObjPerson().getNombreCompleto();
	}

	public String GetBusinessName() throws Exception {
		return this.getObjPerson().GetApellido();
	}

	public String GetSiteName() throws Exception {
		return this.getObjPerson().GetNombre();
	}

	public void assignDescription() throws Exception {
		pDescripcion.setValue(GetDescrip());
	}

	public BizNodo ObtenerNodoPadre() throws Exception {
		if (oNodoPadre != null)
			return oNodoPadre;
		if (this.pNodoPadre.isNull())
			return null;
		oNodoPadre = new BizNodo();
		oNodoPadre.Read(this.getCompany(), this.pNodoPadre.getValue());
		return oNodoPadre;
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Retorna el nodo_database Master
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	public BizNodoDatabase getNodoDatabaseMaster() throws Exception {
		if (oNodoDatabaseMaster != null)
			return oNodoDatabaseMaster;
		JRecords<BizNodoDatabase> oNodosDB = new JRecords<BizNodoDatabase>(BizNodoDatabase.class);
		oNodosDB.addFilter("nodo", this.GetNodo());
		oNodosDB.addFilter("is_master", "S");
		oNodosDB.readAll();
		if (!oNodosDB.nextRecord()) {
			oNodosDB.closeRecord();
			JExcepcion.SendError("No esta indicado que nodo es master para el nodo " + BizUsuario.getUsr().getNodo());
		}
		oNodoDatabaseMaster = oNodosDB.getRecord();
		oNodosDB.closeRecord();
		return oNodoDatabaseMaster;
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Informa si el host donde se encuentra la base conectada actualmente es el
	 * mismo que el host donde se encuentra la base Master
	 * 
	 * @return true Si son los mismos hosts
	 * @return false Si son distintos hosts
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	public boolean isConnectedToDBMaster() throws Exception {
		// BizNodoDatabase oNodoDBMaster = this.getNodoDatabaseMaster();
		// obtiene el host donde esta la base que esta conectada actualmente
		return true;
		// String sHostBaseConectada =
		// JBDatos.GetBases().GetBaseDefault().getHostName();
		// return sHostBaseConectada.equalsIgnoreCase(oNodoDBMaster.GetHostDb());
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Informa si el nodo local está configurado como Site Master.
	 * 
	 * @return true si está indicado
	 * @return false Si el nodo local no es SM, o no hay ningun nodo
	 *         correspondiente a la sucursal que sea SM, o no existe el nodo local
	 * @author CJG
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	public static boolean isSiteMaster() {
		boolean bIsSiteMaster = false;
		try {
			BizNodoDatabase oNodoDB = BizUsuario.getUsr().getObjNodo().getNodoDatabaseMaster();
			bIsSiteMaster = JTools.GetHostName().equalsIgnoreCase(oNodoDB.GetHostDb());
		} catch (Exception ex) {
			bIsSiteMaster = false;
		}
		return bIsSiteMaster;
	}

	private void ObtenerIsLocal() throws Exception {
		if (pCompany.getValue().equals(BizUsuario.getUsr().getCompany()) && pNodo.getValue().equals(BizUsuario.getUsr().getNodo()))
			pLocal.setValue("S");
		else
			pLocal.setValue("N");
	}

	public boolean ifLocal() throws Exception {
		return pLocal.getValue();
	}

	public BizPais ObtenerPais() throws Exception {
		if (oPais!=null) return oPais;
		return (oPais=BizPais.findPais(pPais.getValue()));
	}

	public void refreshCountry() throws Exception {
		if (oPais == null)
			return;
		oPais.Read(oPais.GetPais());
	}

	@Override
	public void processInsert() throws Exception {
		// BizPropagate.expire();
		if (pNodo.isNull()) {
			BizNodo max = new BizNodo();
			max.addFilter("company", this.getCompany());
			pNodo.setValue("" + (max.SelectMaxLongFromString("nodo") + 1));
		}
		if (pCodigoIdent.isNull())
			pCodigoIdent.setValue(pNodo.getValue());
		
		BizCompany.getCompany(this.pCompany.getValue()).getNodosMap().addElement(this.pNodo.getValue(), this);
		super.processInsert();
	}

	@Override
	public void processUpdate() throws Exception {
//		BizPropagate.expire();

		super.processUpdate();
		BizCompany.getCompany(this.pCompany.getValue()).getNodosMap().addElement(this.pNodo.getValue(), this);
		/*
		 * if (this.ifLocal()) {
		 * BizUsuario.getUsr().getObjNodo().Read(this.getCompany(), this.GetNodo());
		 * }
		 */
	}

	@Override
	public void processDelete() throws Exception {
		this.ObtenerBasesDatos().processDeleteAll();
		this.ObtenerPCs().processDeleteAll();
		this.getNodoUsuarios().processDeleteAll();
		super.processDelete();
	}

//	private void processDeleteHijos() throws Exception {
//		BizNodo.sprocessDeleteFlag=false;
//		for(int i=0; i<aNodosHijos.length; i++)
//			this.processDeleteHijo(i);
//		BizNodo.sprocessDeleteFlag=true;
//	}

//	private void processDeleteHijo(int zHijo) throws Exception {
//		JRecord oNodoHijo=null;
//		try {
//			oNodoHijo=(JRecord) Class.forName(aNodosHijos[zHijo]).newInstance();
//		} catch (Exception e) {
//			return;
//		}
//
//		if (oNodoHijo.getClass()!=this.getClass()) {
//			oNodoHijo.addFilter("nodo", pNodo.getValue());
//			oNodoHijo.SetNoExcSelect(true);
//			oNodoHijo.Read();
//			try {
//				oNodoHijo.processDelete();
//			} catch (Exception e) {
//				BizNodo.sprocessDeleteFlag=true;
//				JExcepcion.SendError(e.getMessage());
//			}
//		}
//		;
//	}

	private JRecords<BizNodoUsuario> getNodoUsuarios() throws Exception {
		JRecords<BizNodoUsuario> oNodoUsuarios = new JRecords<BizNodoUsuario>(BizNodoUsuario.class);
		oNodoUsuarios.addFilter("NODO", pNodo);
		// oNodoUsuarios.addFilter("PAIS", pPais);
		oNodoUsuarios.readAll();
		return oNodoUsuarios;
	}

	public static String GetDescripcionReporte(String zCompany, String zNodo) throws Exception {
		return GetDescripcionReporte(zCompany, zNodo, "Sucursal");
	}

	public static String GetDescripcionReporte(String zCompany, String zNodo, String zDescripcion) throws Exception {
		String sDesc = "";
		if (zNodo.trim().equals(""))
			sDesc = "< Todas >";
		else {
			BizNodo oNodo = new BizNodo();
			oNodo.Read(zCompany, zNodo);
			sDesc = oNodo.GetNodo() + " - " + oNodo.GetDescrip();
		}
		return zDescripcion + ": ^" + sDesc;
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Metodo que me retorna la longitud minima de un usuario
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	/*
	 * public static int getUserIDMinLength() throws Exception { BizNodo oNodo =
	 * new BizNodo(); oNodo.SetNoExcSelect(true); oNodo.Read("",
	 * BizPssConfig.GetNodoLocal());
	 * 
	 * int iLong = oNodo.pLongMinUser.getValue(); oNodo = null; return iLong != 0
	 * ? iLong : 4; }
	 */

//	public static String joinShop(String zNodo, String zShop) {
//		return " and "+zShop+".company = "+zNodo+".company "+" and "+zShop+".nodo = "+zNodo+".nodo ";
//	}

//	public static String joinPOS(String zNodo, String zPOS) {
//		return " and "+zPOS+".company = "+zNodo+".company "+" and "+zPOS+".nodo = "+zNodo+".nodo ";
//	}

	public boolean isConcentrador() throws Exception {
		return pModoConcentrador.getValue();
	}

	public boolean isModuloEventosHabilitado() throws Exception {
		return pModuloEventosHabilitado.getValue();
	}

	public JRecords<BizNodoDatabase> ObtenerBasesDatos() throws Exception {
		JRecords<BizNodoDatabase> oNodoDatabases = new JRecords<BizNodoDatabase>(BizNodoDatabase.class);
		oNodoDatabases.addFilter("company", this.pCompany.getValue());
		oNodoDatabases.addFilter("nodo", this.pNodo.getValue());
		oNodoDatabases.readAll();
		return oNodoDatabases;
	}

	public JRecords<BizNodoPC> ObtenerPCs() throws Exception {
		JRecords<BizNodoPC> oNodoPCs = new JRecords<BizNodoPC>(BizNodoPC.class);
		oNodoPCs.addFilter("company", this.pCompany.getValue());
		oNodoPCs.addFilter("nodo", this.pNodo.getValue());
		oNodoPCs.readAll();
		return oNodoPCs;
	}

	public String getPropagateModel() throws Exception {
		return pModeloPropagar.getValue();
	}

	public JSetupPropagate getPropagateModel(String zTable) throws Exception {
		if (!ifPropagar()) return null;
		return BizPropagate.get(zTable);
	}

	@Override
	public JSetupPropagate doSetupPropagate(JSetupPropagate zSetup) throws Exception {
		return zSetup;
	}

	public BizCompany getObjCompany() throws Exception {
		if (this.oCompany != null) return this.oCompany;
		return (this.oCompany=BizCompany.getCompany(this.getCompany()));
	}

	public void setObjPerson(BizPersona p) throws Exception {
		this.oPersona=p;
	}

	public BizPersona getObjPerson() throws Exception {
		if (oPersona != null)
			return oPersona;
		oPersona = new BizPersona();
		oPersona.Read(pIdPersona.getValue());
		return oPersona;
	}

	public void setPais(String zValue) throws Exception {
		pPais.setValue(zValue);
	}

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public void setNodo(String zValue) throws Exception {
		pNodo.setValue(zValue);
	}

	public String getServidorLicencia() throws Exception {
		return pServidorLicencia.getValue();
	}

	@Override
	public void validateConstraints() throws Exception {
		// if (pNodo.getValue().indexOf(" ")>-1)
		// JExcepcion.SendError("El ID de la Sucursal no puede tener espacios en blanco");
		if (pNodo.isEmpty()) return;
		if (pCodigoIdent.isEmpty()) return;
		JRecords<BizNodo> r = new JRecords<BizNodo>(BizNodo.class);
		r.addFilter("company", this.getCompany());
		r.addFilter("codigo_ident", this.getCodigoIdent());
		r.addFilter("nodo", this.GetNodo(), "<>");
		r.readAll();
		if (r.nextRecord())
			JExcepcion.SendError("Código Duplicado");

	}

	public static JRecords<BizVirtual> getValidGMTs() throws Exception {
		JRecords<BizVirtual> oBDs = JRecords.createVirtualBDs();
		for (int i = 12; i >= 1; i--) {
			oBDs.addItem(JRecord.virtualBD((i * -1) + "", "GMT-" + i, 72));
		}
		oBDs.addItem(JRecord.virtualBD(0 + "", "GMT", 72));
		for (int i = 1; i <= 12; i++) {
			oBDs.addItem(JRecord.virtualBD(i + "", "GMT+" + i, 72));
		}
		return oBDs;
	}

	@Override
	protected void setupConfig(JSetupParameters params) throws Exception {
		if (params.getDataModel().equals("jud")) {
			params.setExportData(true);
		}
		super.setupConfig(params);
	}

	public Date todayGMT() throws Exception {
		return todayGMT(true);
	}

	public Date todayGMT(boolean withHora) throws Exception {
		Date d = Calendar.getInstance(TimeZone.getTimeZone("GMT+" + this.getGmt())).getTime();
		// int gmt = Calendar.getInstance().get(Calendar.ZONE_OFFSET)/(60*60*1000);
		// gmt+=this.getGmt();
		// d = JDateTools.addHours(d, (long)gmt);
		if (withHora)
			return d;
		// optimizar
		return JDateTools.StringToDate(JDateTools.DateToString(d), JDateTools.DEFAULT_DATE_FORMAT);
	}

	public boolean readByCodigo(String zCompany, String ident) throws Exception {
		this.clearFilters();
		this.addFilter("company", zCompany);
		this.addFilter("codigo_ident", ident);
		return this.read();
	}

	public boolean isNodoForaneo() throws Exception {
		return this.pNodoForaneo.getValue();
	}	

	public static String getStoreDesc(String company, String store) throws Exception {
		if (store.isEmpty())
			return "sin definir";
		BizNodo val = BizCompany.getCompany(company).findNodo(store);
		return val.GetSiteName();
	}

  public String findLocalCurrency() throws Exception { 
  	return this.getObjCompany().findPais(this.GetPais()).findLocalCurrency();
  }

}
