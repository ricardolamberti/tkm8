package pss.common.layout;

import pss.common.components.JSetupParameters;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.collections.JMap;

public class BizLayoutCampo extends JRecord {

	public final static String CAMPO_CONSTANTE="Constante";
	public final static String CAMPO_PARTICULAR="Dato Particular";
	public final static String CAMPO_GENERAL="Dato General";
	public final static String CAMPO_CONTROL="Control";

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	protected JString pCompany=new JString();
	protected JString pPais=new JString();
	protected JString pLayout=new JString();
	protected JString pSector=new JString();
	protected JString pCampo=new JString();
	protected JString pTipoCampo=new JString();
	protected JString pParametro=new JString();
	protected JString pParametro2=new JString();
	protected JInteger pX=new JInteger();
	protected JInteger pY=new JInteger();
	protected JInteger pLongMax=new JInteger();
	protected JLong pSecuencia=new JLong();
	protected JString pAlineacion=new JString();
	protected JString pFontName=new JString();
	protected JString pFontType=new JString();
	protected JString pFontSize=new JString();
	protected JString pBackGround=new JString();
	protected JString pForeGround=new JString();
	protected JString pPosition=new JString();
	protected JString pInterline=new JString();
	protected JString pFormato=new JString();
	protected JString pFormatoPredef=new JString();
	protected JString pCampoCondicion=new JString();
	protected JString pParamCondicion=new JString();
	protected JString pOperCondicion=new JString();
	protected JString pCondicion=new JString();
	protected JBoolean pAlFinal=new JBoolean();
	protected JInteger pWrap=new JInteger();	
	protected JString pDescrSector=new JString() {

		@Override
		public void preset() throws Exception {
			pDescrSector.setValue(getDescrSector());
		}
	};
	JString pDescrCampo=new JString() {

		@Override
		public void preset() throws Exception {
			pDescrCampo.setValue(getDescrCampo());
		}
	};
	JString pConstante=new JString();

	BizLayout oLayout=null;

	public String getTipoCampo() throws Exception {
		return pTipoCampo.getValue();
	}
	
	public void setCompany(String value) throws Exception {
		pCompany.setValue(value);
	}
	
	public void setLayout(String value) throws Exception {
		pLayout.setValue(value);
	}
	
	public int getLongMax() throws Exception {
		return this.pLongMax.getValue();
	}

	public boolean isAlFinal() throws Exception {
		return this.pAlFinal.getValue();
	}

	public int getWrap() throws Exception {
		return this.pWrap.getValue();
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizLayoutCampo() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("pais", pPais);
		addItem("layout", pLayout);
		addItem("secuencia", pSecuencia);
		addItem("sector", pSector);
		addItem("campo", pCampo);
		addItem("tipo_campo", pTipoCampo);
		addItem("parametro", pParametro);
		addItem("parametro2", pParametro2);
		addItem("x", pX);
		addItem("y", pY);
		addItem("long_max", pLongMax);
		addItem("alineacion", pAlineacion);
		addItem("formato_predefinido", pFormatoPredef);
		addItem("formato", pFormato);
		addItem("campo_condicion", pCampoCondicion);
		addItem("param_condicion", pParamCondicion);
		addItem("oper_condicion", pOperCondicion);
		addItem("condicion", pCondicion);
		addItem("descr_sector", pDescrSector);
		addItem("descr_campo", pDescrCampo);
		addItem("constante", pConstante);
		addItem("font_name", pFontName);
		addItem("font_type", pFontType);
		addItem("font_size", pFontSize);
		addItem("background", pBackGround);
		addItem("foreground", pForeGround);
		addItem("position", pPosition);
		addItem("interline", pInterline);
		addItem("al_final", pAlFinal);
		addItem("wrap", pWrap);		
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "empresa", true, true, 15);
		addFixedItem(KEY, "pais", "Pais", true, true, 2);
		addFixedItem(KEY, "layout", "Layout", true, true, 15);
		addFixedItem(KEY, "secuencia", "Secuencia", true, true, 5);
		addFixedItem(FIELD, "sector", "Sector", true, true, 100);
		addFixedItem(FIELD, "campo", "Campo", true, false, 300);
		addFixedItem(FIELD, "tipo_campo", "Tipo Campo", true, true, 15);
		addFixedItem(FIELD, "parametro", "Parametro", true, false, 30);
		addFixedItem(FIELD, "parametro2", "Parametro 2", true, false, 30);
		addFixedItem(FIELD, "x", "X", true, true, 5);
		addFixedItem(FIELD, "y", "Y", true, true, 5);
		addFixedItem(FIELD, "long_max", "Long Maxima", true, true, 5);
		addFixedItem(FIELD, "alineacion", "Alineacion", true, true, 15);
		addFixedItem(FIELD, "formato_predefinido", "F.Predefinido", true, false, 15);
		addFixedItem(FIELD, "formato", "Formato", true, false, 50);
		addFixedItem(FIELD, "campo_condicion", "Campo Condicion", true, false, 50);
		addFixedItem(FIELD, "param_condicion", "Parametro Condicion", true, false, 50);
		addFixedItem(FIELD, "oper_condicion", "Oper Condicion", true, false, 10);
		addFixedItem(FIELD, "condicion", "Condicion", true, false, 100);
		addFixedItem(VIRTUAL, "descr_sector", "Sector", true, true, 30);
		addFixedItem(VIRTUAL, "descr_campo", "Campo", true, true, 30);
		addFixedItem(FIELD, "constante", "Constante", true, false, 255);
		addFixedItem(FIELD, "font_name", "Letra", true, false, 20);
		addFixedItem(FIELD, "font_type", "Tipo", true, false, 20);
		addFixedItem(FIELD, "font_size", "Tamaño Tipo Letra", true, false, 20);
		addFixedItem(FIELD, "al_final", "Al Final", true, false, 1);
		addFixedItem(FIELD, "position", "Posicion", true, false, 20);
		addFixedItem(FIELD, "wrap", "Wrap", true, false, 5);		
		addFixedItem(FIELD, "background", "BackGround", true, false, 6);
		addFixedItem(FIELD, "foreground", "ForeGround", true, false, 6);
		addFixedItem(FIELD, "interline", "Interline", true, false, 10);
	}

	public boolean Read(String company, String zPais, String zLayout, long zSec) throws Exception {
		addFilter("company", company);
		addFilter("pais", zPais);
		addFilter("layout", zLayout);
		addFilter("secuencia", zSec);
		return this.Read();
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {

		if (zParams.isLevelCountry()) {
			zParams.setTruncateData(true);
			zParams.setExportData(true);
		}
		// zParams.setExportData(zParams.isLevelCountry());
		// zParams.setExportSQL("select rtl_layout_campos.* from rtl_layout_campos, rtl_layouts "+
		// " where rtl_layout_campos.pais = rtl_layouts.pais " +
		// " and rtl_layout_campos.layout = rtl_layouts.layout " +
		// " and rtl_layouts.preconfigurado = 'S' ");
	}

	public BizLayout getObjLayout() throws Exception {
		if (oLayout!=null) return oLayout;
		oLayout=new BizLayout();
		oLayout.Read(pCompany.getValue(), pPais.getValue(), pLayout.getValue());
		return oLayout;
	}

	@Override
	public String GetTable() {
		return "RTL_LAYOUT_CAMPOS";
	}

	public String getSector() throws Exception {
		return pSector.getValue();
	}

	public boolean ifConstante() throws Exception {
		return pTipoCampo.getValue().equals(CAMPO_CONSTANTE);
	}

	public boolean ifControl() throws Exception {
		return pTipoCampo.getValue().equals(CAMPO_CONTROL);
	}

	public boolean ifDato() throws Exception {
		return pTipoCampo.getValue().equals(CAMPO_PARTICULAR);
	}

	public boolean ifDatoGeneral() throws Exception {
		return pTipoCampo.getValue().equals(CAMPO_GENERAL);
	}
	public boolean ifMonedaConSimbolo() throws Exception {
		return this.ifMonedaConSimboloAdelante()||this.ifMonedaConSimboloAtras();
	}

	public boolean ifMonedaPais() throws Exception {
		return pFormatoPredef.getValue().equals("MonedaPais")||pFormatoPredef.getValue().equals("MonedaPaisSigno");
	}

	public boolean ifNumericoPais() throws Exception {
		return pFormatoPredef.getValue().equals("NumericoPais")||pFormatoPredef.getValue().equals("NumericoPaisSigno");
	}

	public boolean ifFechaCorta() throws Exception {
		return pFormatoPredef.getValue().equals("FechaCorta");
	}

	public boolean ifHoraCorta() throws Exception {
		return pFormatoPredef.getValue().equals("HoraCorta");
	}

	public boolean isImage() throws Exception {
		return pFormatoPredef.getValue().equals("Image");
	}

	public boolean ifHoraLarga() throws Exception {
		return pFormatoPredef.getValue().equals("HoraLarga");
	}

	public boolean ifFecha() throws Exception {
		return pFormatoPredef.getValue().equals("Fecha");
	}

	public boolean ifFechaLarga() throws Exception {
		return pFormatoPredef.getValue().equals("FechaL");
	}

	public boolean ifHoraCustom() throws Exception {
		return pFormatoPredef.getValue().equals("HoraCustom");
	}
	public boolean ifMonedaConSimboloAdelante() throws Exception {
		return pFormatoPredef.getValue().equals("MonConSimbolo");
	}
	public boolean ifMonedaConSimboloAtras() throws Exception {
		return pFormatoPredef.getValue().equals("MonConSimbAtras");
	}

	public boolean ifNumerico() throws Exception {
		return pFormatoPredef.getValue().equals("Numerico")||pFormatoPredef.getValue().equals("NumericoSigno");
	}

	public boolean ifTextoEnmascarado() throws Exception {
		return pFormatoPredef.getValue().equals("TextoEnmasc");
	}

	public boolean ifMonedaPalabra() throws Exception {
		return pFormatoPredef.getValue().equals("MonedaPalabra");
	}

	public boolean isCondicionado() throws Exception {
		return !pCampoCondicion.getValue().equals("");
	}

	public boolean ifNumericoConSigno() throws Exception {
		return pFormatoPredef.getValue().equals("NumericoSigno")||pFormatoPredef.getValue().equals("NumericoPaisSigno")||pFormatoPredef.getValue().equals("MonedaPaisSigno");
	}

	public boolean isCustom(String zPredefinedId) {
		return zPredefinedId.equals("HoraCustom")||zPredefinedId.equals("Fecha")||zPredefinedId.equals("Numerico")||zPredefinedId.equals("NumericoSigno")||zPredefinedId.equals("TextoEnmasc");
	}

	public boolean ifAlignLeft() throws Exception {
		return pAlineacion.getValue().equals("Left");
	}

	public boolean ifAlignRigth() throws Exception {
		return pAlineacion.getValue().equals("Right");
	}

	public boolean ifAlignCenter() throws Exception {
		return pAlineacion.getValue().equals("Center");
	}

	public String getDescrSector() throws Exception {
		JMap<String, String> map=JFieldSet.getSet(getObjLayout()).getAllSections();
		return map.getElement(pSector.getValue());
	}

	public String getDescrCampo() throws Exception {
		if (this.ifConstante()) return this.pConstante.getValue();
		else if (this.ifControl()) return this.pCampo.getValue()+" ("+pParametro.getValue()+")";
		else if (this.ifDatoGeneral()) {
			JRecords<BizVirtual> oCampos=ObtenerCamposGenerales();
			return oCampos.findVirtualKey(this.pCampo.getValue()).getDescrip()+" ("+pParametro.getValue()+")";
		} else {
			JMap<String, String> map=this.getCampos();
			String descrCampo = map.getElement(this.pCampo.getValue()) + (pParametro.isEmpty() ? "" : " ("+pParametro.getValue()+")");
			return descrCampo!=null?descrCampo:"ERROR->"+this.pCampo.getValue()+" ("+pParametro.getValue()+")";
		}
	}

	public String getDescrCampoCondicion() throws Exception {
		JMap<String, String> map=this.getCampos();
		return map.getElement(this.pCampoCondicion.getValue());
	}

	private JMap<String, String> getCampos() throws Exception {
		return JFieldSet.getSet(getObjLayout()).getFields(pSector.getValue());
	}

	private JRecords<BizVirtual> ObtenerCamposGenerales() throws Exception {
		return BizLayout.getGeneralFields();
	}

	@Override
	public void processInsert() throws Exception {
		BizLayoutCampo oMax=new BizLayoutCampo();
		oMax.addFilter("pais", pPais.getValue());
		oMax.addFilter("layout", pLayout.getValue());
		pSecuencia.setValue(oMax.SelectMaxLong("secuencia")+1);
		super.processInsert();
	}

	@Override
	public void validateConstraints() throws Exception {

		if (!this.ifConstante()&&pCampo.getValue().equals("")) JExcepcion.SendError("Debe ingresar el Campo");

		if (isCustom(pFormatoPredef.getValue())) {
			if (pFormato.isNull()||pFormato.isEmpty()||"".equals(pFormato.getValue())) {
				JExcepcion.SendError("Debe ingresar el Formato");
			}
		}

		if (isCondicionado()) {
			if (pOperCondicion.isNull()||pOperCondicion.isEmpty()||"".equals(pOperCondicion.getValue())) {
				JExcepcion.SendError("Debe ingresar el Operador ya que seleccionó una Condición");
			}
			// if( pCondicion.IsNull() || pCondicion.IsSpaces() || "".equals(pCondicion.GetValor()) ) {
			// JExcepcion.SendError("Debe ingresar Valor ya que seleccionó una Condición");
			// }
		}

//		if (ifFecha()&&pFormato.getValue().indexOf("m")>0) {
//			pFormato.setValue(pFormato.getValue().replaceAll("m", "M"));
//		}

	}

	public static JRecords<BizVirtual> ObtenerFormats() throws Exception {
		JRecords<BizVirtual> oBDs=JRecords.createVirtualBDs();
		oBDs.addItem(JRecord.virtualBD("MonedaPais", "Moneda Default", 1));
		oBDs.addItem(JRecord.virtualBD("MonedaPaisSigno", "Moneda Default con Signo", 1));
		oBDs.addItem(JRecord.virtualBD("MonedaPalabra", "Moneda en palabras", 1));
		oBDs.addItem(JRecord.virtualBD("MonConSimbolo", "Moneda c/Simbolo Adelante", 1));
		oBDs.addItem(JRecord.virtualBD("MonConSimbAtras", "Moneda c/Simbolo Atras", 1));
		oBDs.addItem(JRecord.virtualBD("NumericoPais", "Numerico Default", 1));
		oBDs.addItem(JRecord.virtualBD("NumericoPaisSigno", "Numerico Default Con Signo", 1));
		oBDs.addItem(JRecord.virtualBD("Numerico", "Numerico Custom", 1));
		oBDs.addItem(JRecord.virtualBD("NumericoSigno", "Numerico Custom Con Signo", 1));
		oBDs.addItem(JRecord.virtualBD("Fecha", "Fecha Custom", 1));
		oBDs.addItem(JRecord.virtualBD("FechaL", "Fecha Larga", 1));
		oBDs.addItem(JRecord.virtualBD("FechaCorta", "Fecha Corta Default", 1));
		oBDs.addItem(JRecord.virtualBD("HoraCustom", "Hora Custom", 1));
		oBDs.addItem(JRecord.virtualBD("HoraCorta", "Hora Corta Default", 1));
		oBDs.addItem(JRecord.virtualBD("HoraLarga", "Hora Larga Default", 1));
		oBDs.addItem(JRecord.virtualBD("TextoEnmasc", "Texto Enmascarado", 1));
		oBDs.addItem(JRecord.virtualBD("Image", "Imagen", 1));
		return oBDs;
	}

	public String getCampo() throws Exception {
		return pCampo.getValue();
	}

	public String alinear(String result) throws Exception {
		if (result.equals("")) 
			return result;
		if (this.hasPosition())
			return result;// si usa posicion fija lo alinea el pdf
		if (this.ifAlignRigth()) {
			result=JTools.LPad(result, this.pLongMax.getValue(), " ", true);
		} else if (this.ifAlignCenter()) {
			result=JTools.centerString(result, this.pLongMax.getValue(), ' ');
		} else if (this.ifAlignLeft()) {
			result=JTools.RPad(result, this.pLongMax.getValue(), " ", true);
		}
		return result;
	}
	
	public boolean hasFontName() throws Exception {
		return this.pFontName.isNotNull();
	}

	public boolean hasFontType() throws Exception {
		return this.pFontType.isNotNull();
	}

	public boolean hasFontSize() throws Exception {
		return this.pFontSize.isNotNull();
	}

	public boolean hasBackGround() throws Exception {
		return this.pBackGround.isNotNull();
	}

	public boolean hasForeGround() throws Exception {
		return this.pForeGround.isNotNull();
	}
	
	public boolean hasPosition() throws Exception {
		return this.pPosition.isNotNull();
	}

	public boolean hasAlineacion() throws Exception {
		return this.pAlineacion.isNotNull();
	}

	public boolean hasInterline() throws Exception {
		return this.pInterline.isNotNull();
	}

	public boolean hasStyle(String result) throws Exception {
		if (this.hasFontName()) return true;
		if (this.hasFontSize()) return true;
		if (this.hasBackGround()) return true;
		if (this.hasForeGround()) return true;
		if (this.hasPosition()) return true;
		if (this.isImage()) return true;
//		if (this.hasAlineacion()) return true; // no porque se comparte con el courrier
		return false;
	}
	
	public String assignStyle(String result) throws Exception {
		if (!this.hasStyle(result)) return result; 
		String style = " ~";
		if (this.hasFontName()) style+=" f="+this.pFontName.getValue();
		if (this.hasFontType()) style+=";"+this.pFontType.getValue();
		if (this.hasFontSize()) style+=" s="+this.pFontSize.getValue();
		if (this.hasForeGround()) style+=" fg="+this.pForeGround.getValue();
		if (this.hasBackGround()) style+=" bg="+this.pBackGround.getValue();
		if (this.hasPosition()) style+=" pos="+this.pPosition.getValue();
		if (this.hasAlineacion()) style+=" a="+this.pAlineacion.getValue();
		if (this.hasInterline()) style+=" iline="+this.pInterline.getValue();
		if (this.isImage()) {style+=" img="+result;result="";}
		return style+"~"+result;
	}
	

	public int findLongMax() throws Exception {
		int maxfield = this.getLongMax();
		if (maxfield>this.getObjLayout().getAnchoMaximo()) 
			maxfield=this.getObjLayout().getAnchoMaximo();
		return maxfield;
	}

	
	
}
