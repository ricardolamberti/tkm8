package  pss.common.security.type;

import pss.common.security.BizUsuario;
import pss.core.connectivity.messageManager.common.core.JMMRecord;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecords;

public class BizUsuarioTipo extends JMMRecord {


	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JString pCompany = new JString();
	JString pUsuarioTipo = new JString();
	JString pDescripcion = new JString();
	JString pLenguaje = new JString();
	JString pLoginSource = new JString();
	JString pBirthCountry = new JString();
	JBoolean pLogFlag = new JBoolean();
	JString pCustomMenu = new JString();
	JString pSkin = new JString();
	JString pSkinWeb = new JString();
	JInteger pPageSize = new JInteger();
	JInteger pMaxMatrix = new JInteger();
	JString pHomePage = new JString();
	JBoolean pHasNavigationBar = new JBoolean();
	JBoolean pHasMail = new JBoolean();
	JInteger pExpire = new JInteger();
	JBoolean pHasHelp = new JBoolean();
	JBoolean pHasMulti = new JBoolean();
	JBoolean pHasPreference= new JBoolean();
	JBoolean pHasChangeKey= new JBoolean();
	JInteger pRetriesClave = new JInteger();
	JInteger pLongitudMinClave = new JInteger();
	JBoolean pViewInseg = new JBoolean();
	JBoolean pCanOutAccess = new JBoolean();
	JBoolean pStrongPass = new JBoolean();

	public String getLoginSource() throws Exception {
		return pLoginSource.getValue();
	}
	public long getPageSize() throws Exception {
		return pPageSize.getValue();
	}
	public void setPageSize(long pageSize) throws Exception {
		pPageSize.setValue(pageSize);
	}
	public long getMaxMatrix() throws Exception {
		return pMaxMatrix.getValue();
	}
	public void setMaxMatrix(long maxMatrix) throws Exception {
		pMaxMatrix.setValue(maxMatrix);
	}
	public String getSkinWeb() throws Exception {
		return pSkinWeb.getValue();
	}
	public void setSkinWeb(String skinWeb) throws Exception {
		pSkinWeb.setValue(skinWeb);
	}
	public String getHomePage() throws Exception {
		return pHomePage.getValue();
	}
	public void setHomePage(String homePage) throws Exception {
		pHomePage.setValue(homePage);
	}
	public String getPaisNacimiento() throws Exception {
		return pBirthCountry.getValue();
	}
	public void setPaisNacimiento(String birthCountry) throws Exception {
		pBirthCountry.setValue(birthCountry);
	}
	public String getCustomMenu() throws Exception {
		return pCustomMenu.getValue();
	}
	public long getLongitudMinClave() throws Exception {
		return pLongitudMinClave.getValue();
	}
	
	public int getRetriesClave() throws Exception {
		return pRetriesClave.getValue();
	}

	public boolean hasCompany() throws Exception {
		return pCompany.isNotNull();
	}

	public boolean hasViewInseg() throws Exception {
		return pViewInseg.getValue();
	}


	public boolean canOutAccess() throws Exception {
		return pCanOutAccess.getValue();
	}	
	public boolean reqStrongPass() throws Exception {
		return pStrongPass.getValue();
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public boolean getHasNavigayionBar() throws Exception {
		return pHasNavigationBar.getValue();
	}
	public boolean getHasMail() throws Exception {
		return pHasMail.getValue();
	}
	public boolean getHasHelp() throws Exception {
		return pHasHelp.getValue();
	}
	public boolean getHasMulti() throws Exception {
		return pHasMulti.getValue();
	}
	public boolean getHasPreference() throws Exception {
		return pHasPreference.getValue();
	}
	public boolean getHasChangeKey() throws Exception {
		return pHasChangeKey.getValue();
	}
	public long getExpire() throws Exception {
		return pExpire.getValue();
	}


	public String getIdUsuarioTipo() throws Exception {
		return pUsuarioTipo.getValue();
	}
	

	public String GetUserFlag() throws Exception {
		return this.pLogFlag.toString();
	}





	public void setLanguage(String zValue) {
		pLenguaje.setValue(zValue);
	}

	public String getLanguage() throws Exception {
		return pLenguaje.getValue();
	}

	public void setCompany(String zValue) {
		pCompany.setValue(zValue);
	}


	public void SetUsuarioTipo(String zValue) {
		pUsuarioTipo.setValue(zValue);
	}



	public void setLenguaje(String sLenguaje) {
		this.pLenguaje.setValue(sLenguaje);
	}

	public void setUserFlag(String sFlag) {
		this.pLogFlag.setValue(sFlag);
	}
	public void setViewInseg(boolean sFlag) {
		this.pViewInseg.setValue(sFlag);
	}
	public void setCanOutAccess(boolean sFlag) {
		this.pCanOutAccess.setValue(sFlag);
	}
	public void setStrongPass(boolean sFlag) {
		this.pStrongPass.setValue(sFlag);
	}
	public String isUsuarioTipo() throws Exception {
		return pUsuarioTipo.getValue();
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizUsuarioTipo() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("company", pCompany);//
		this.addItem("tipo_usuario", pUsuarioTipo);//
		this.addItem("descripcion", pDescripcion);//
		this.addItem("lenguaje", pLenguaje);//
		this.addItem("loguear", pLogFlag);//
		this.addItem("birth_country", pBirthCountry);

		this.addItem("custom_menu", pCustomMenu);//
		this.addItem("skin", pSkin);//
		this.addItem("skin_web", pSkinWeb);//
		this.addItem("home_web", pHomePage);//
		this.addItem("page_web", pPageSize);//
		this.addItem("matrix_web", pMaxMatrix);//
		this.addItem("expire", pExpire);//
		this.addItem("has_navigationbar", pHasNavigationBar);//
		this.addItem("has_mail", pHasMail);//
		this.addItem("has_help", pHasHelp);//
		this.addItem("has_multi", pHasMulti);//
		this.addItem("has_preference", pHasPreference);//
		this.addItem("has_changekey", pHasChangeKey);//
		this.addItem("view_inseg", pViewInseg);//
		this.addItem("can_outaccess", pCanOutAccess);//
		this.addItem("longitud_min_clave", pLongitudMinClave);//
		this.addItem("strong_pass", pStrongPass);//
		this.addItem("retries_clave", pRetriesClave);//
	}

	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "tipo_usuario", "Usuario tipo", true, false, 16);
		this.addFixedItem(KEY, "company", "Empresa", true, false, 15);
		this.addFixedItem(FIELD, "descripcion", "Descripción", true, true, 50);
		this.addFixedItem(FIELD, "lenguaje", "Idioma", true, true, 2, 0, JObject.JLOWERCASE);
		this.addFixedItem(FIELD, "loguear", "Loguear", true, false, 1);
		this.addFixedItem(FIELD, "birth_country", "País de origen", true, true, 15);
		this.addFixedItem(FIELD, "custom_menu", "Menú Custom", true, false, 15);
		this.addFixedItem(FIELD, "skin", "Skin", true, false, 50);
		this.addFixedItem(FIELD, "skin_web", "Skin Web", true, false, 50);
		this.addFixedItem(FIELD, "home_web", "Origen web", true, false, 250);
		this.addFixedItem(FIELD, "page_web", "Pagina Web", true, false, 18);
		this.addFixedItem(FIELD, "matrix_web", "Max.Matriz", true, false, 18);
		this.addFixedItem(FIELD, "expire", "tiempo expiracion session", true, false, 16);
		this.addFixedItem(FIELD, "has_navigationbar", "tiene barra navegacion", true, false, 1);
		this.addFixedItem(FIELD, "has_mail", "tiene correo", true, false, 1);
		this.addFixedItem(FIELD, "has_help", "tiene ayuda", true, false, 1);
		this.addFixedItem(FIELD, "has_multi", "tiene multi ventanas", true, false, 1);
		this.addFixedItem(FIELD, "has_preference", "tiene preferencias", true, false, 1);
		this.addFixedItem(FIELD, "has_changekey", "tiene cambio clave", true, false, 1);
		this.addFixedItem(FIELD, "view_inseg", "Ver sin seg.definida", true, false, 1);
		this.addFixedItem(FIELD, "can_outaccess", "Acceso externo permitido", true, false, 1);
		this.addFixedItem(FIELD, "longitud_min_clave", "Long Min Clave", true, false, 5);
		this.addFixedItem(FIELD, "strong_pass", "Req.clave fuerte", true, false, 1);
		this.addFixedItem(FIELD, "retries_clave", "Retries", true, false, 5);
	}

	public boolean hasCustomMenu() throws Exception {
		return pCustomMenu.isNotNull();
	}

	@Override
	public String GetTable() {
		return "SEG_USUARIO_TYPE";
	}

	public boolean Read(String company,String zUsuario) throws Exception {
		this.clearFilters();
		this.addFilter("company", company);
		this.addFilter("tipo_usuario", zUsuario);
		return read();
	}
	
	@Override
	public void processDelete() throws Exception {
		JRecords<BizUsuario> recs = new JRecords<BizUsuario>(BizUsuario.class);
		recs.addFilter("tipo_usuario", getIdUsuarioTipo());
		if  (recs.selectCount()>0) throw new Exception("Hay usuarios de este tipo");
		super.processDelete();
	}

	public BizUsuarioTipo processClon(BizUsuarioTipo newDoc) throws Exception {
//	newDoc.copyProperties(this);
	newDoc.processInsert();
	return newDoc;
}


 }