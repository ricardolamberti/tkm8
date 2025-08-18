package pss.common.customList.config.field.campo;

import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.commons.lang.CharEncoding;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.field.BizField;
import pss.common.customList.config.field.ICampo;
import pss.common.customList.config.field.IItem;
import pss.common.customList.config.field.funciones.BizFuncion;
import pss.common.customList.config.field.operadores.BizOperador;
import pss.common.customList.config.field.stadistic.BizStadistic;
import pss.common.customList.config.field.stadistic.GuiStadistics;
import pss.common.customList.config.relation.BizCampoGallery;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.regions.company.BizCompany;
import pss.common.regions.company.JCompanyBusiness;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.data.interfaces.sentences.JRegSQL;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JColour;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JMultiple;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JPair;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.core.win.JControlWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.JControlCombo;

public class BizCampo extends BizField implements ICampo,IItem  {
	public static final String ORDER_ASC = "ASC";
	public static final String ORDER_DESC = "DESC";
	public static final String ORDER_NO = "-";

	public static final String OPER_AND = "AND";
	public static final String OPER_OR = "OR";
	public static final String OPER_AND_INTERNO = "ADI";
	
	public static final String TIPO_TRUE = "S";
	public static final String TIPO_FALSE = "N";

	public static final String SIEMPLE="SIMPLE";
	public static final String FORMULA="FORMULA";
	
	public static final String FILA = "F";
	public static final String COLUMNA = "C";
	public static final String CAMPO = "P";
	public static final String CATEGORIA = "F";
	public static final String DATASET = "C";
	public static final String VALOR = "P";
	public static final String LINEA = "L";


	private JString pCompany = new JString();
	private JLong pListId = new JLong();
	private JLong pSecuencia = new JLong();
	private JLong pOrden = new JLong();
	private JString pRecordOwner = new JString();
	private JLong pSecuenciaOld = new JLong();
		
	private JString pRecordSource = new JString();
	private JString pRelId = new JString();
	private JString pOrigenDatos = new JString();
	private JString pFuncion = new JString();
	private JString pFormat = new JString();
	private JString pFormatParam = new JString();
	private JString pCampo = new JString();
	private JString pNombreColumna = new JString();
	private JBoolean pColores = new JBoolean();
	private JColour pColorForeground = new JColour();
	private JColour pColorBackground = new JColour();
	private JColour pColorTopBackground = new JColour();
	private JColour pColorBottomBackground = new JColour();
	private JBoolean pMarcaMayores = new JBoolean();
	private JBoolean pMarcaMenores = new JBoolean();
	private JBoolean pMarcaTop10 = new JBoolean();
	private JBoolean pMarcaBottom10 = new JBoolean();
	private JString pMayoresA = new JString();
	private JString pMenoresA = new JString();
	private JBoolean pVisible = new JBoolean();
	private JBoolean pPorcentaje = new JBoolean();
	private JString pSerialDeep = new JString();
	private JString pOrdenPadre = new JString();
	private JString pIdKey = new JString();
	private JString pIdKey2 = new JString();
  private JBoolean pDinamico = new JBoolean();
  private JBoolean pNoIncluir = new JBoolean();
  private JBoolean pOnlyTotalizador = new JBoolean();
  private JBoolean pHideReport = new JBoolean();
	private JString pOperacion = new JString();
  private JString pNombreFiltro = new JString();
	private JString pCampoFuncion = new JString() {
		public void preset() throws Exception {
			pCampoFuncion.setValue(getCampoFuncion());
		};
	};
	private JString pSerialCampo = new JString() {
		public void preset() throws Exception {
			pSerialCampo.setValue(getSerialCampo());
		};
	};
	private JString pRolDescripcion = new JString() {
		public void preset() throws Exception {
			pRolDescripcion.setValue(getRolDescription());
		};
	};

	private JMultiple pAmbitoPorcentaje = new JMultiple();
//	private JLong pMaxSize = new JLong();
	
	private JString pOrientacion = new JString();
	private JString pRol = new JString();
	private JBoolean pCorteControl = new JBoolean();
  private JString pOperador = new JString();
  private JString pValor = new JString(){
  	public void preset() throws Exception {
  		pValor.setValue(findValue());
  	};

  	
  	
  };
  private JString pValor2 = new JString(){
  	public void preset() throws Exception {
  		pValor2.setValue(findValue2());
  	};
  	
  };
  private JString pNameForFilter = new JString(){
  	public void preset() throws Exception {
  		pNameForFilter.setValue(getNameForFilter());
  	};
  };
  private JString pNegado = new JString();

  private JString pDescrTrue = new JString();
  private JString pDescrFalse = new JString();

	private JBoolean pCalculeDiferencia = new JBoolean();
	private JMultiple pCampoDiferencia = new JMultiple();
	private JBoolean pPorcentajeDiferencia = new JBoolean();

	private JLong pOrdenOrden = new JLong();
  private JString pOrdenAscDesc = new JString();
	private JLong pOrdenLimite = new JLong();

  private JString pValueEdit = new JString();
  private JDate pValueDate = new JDate();
  private JString pValueLOV = new JString();
  private JBoolean pValueCheck = new JBoolean();

  private JString pValueEdit2 = new JString();
  private JDate pValueDate2 = new JDate();
  private JString pValueLOV2 = new JString();
  private JBoolean pValueCheck2 = new JBoolean();
	private JBoolean pHasFiltro = new JBoolean();

	private JLong pRanking = new JLong();

	private JString pCampoOrden = new JString();

	private JString pMetaCampo = new JString(){
		public void preset() throws Exception {
			pMetaCampo.setValue("{*"+getNameField(pCampo.getValue()).toLowerCase()+"}");
		};
	};
	private JLong pIdGallery = new JLong(){
		public void preset() throws Exception {
			if (pIdGallery.isRawNotNull()) return;
			pIdGallery.setValue(getIdGallery());
		};
	};
	public void calculeIdGallery() throws Exception {
		pIdGallery.setValue(getIdGallery());
	}

	private JString pDescrCampo = new JString() {
		public boolean forcePresetForDefault() throws Exception {
			return true;
		};
		public void preset() throws Exception {
			pDescrCampo.setValue(getDescrCampo());
		}
	};
	private JString pDescrFiltro = new JString() {
		public boolean forcePresetForDefault() throws Exception {
			return true;
		};
		public void preset() throws Exception {
			pDescrFiltro.setValue(getDescrCampoWithOp());
		}
	};
	private JString pDescrOrientacion = new JString() {
		public void preset() throws Exception {
			pDescrOrientacion.setValue(getDescrOrientacion());
		}
	};
  public void setOperador(String zValue) throws Exception { pOperador.setValue(zValue);  }

  public void setValueEdit(String zValue) throws Exception {    
  	pValueEdit.setValue(zValue); 
  }
  public void setValueLOV(String zValue) throws Exception {    
  	pValueLOV.setValue(zValue); 
  }
  public void setValueCheck(boolean zValue) throws Exception {    
  	pValueCheck.setValue(zValue); 
  }
  public void setValueDate(Date zValue) throws Exception {    
  	pValueDate.setValue(zValue); 
  }
  public void setValueEdit2(String zValue) throws Exception {    
  	pValueEdit2.setValue(zValue); 
  }
  public void setValueDate2(Date zValue) throws Exception {    
  	pValueDate2.setValue(zValue); 
  }
  public void setValueLOV2(String zValue) throws Exception {    
  	pValueLOV2.setValue(zValue); 
  }
  public void setValueCheck2(boolean zValue) throws Exception {    
  	pValueCheck2.setValue(zValue); 
  }
  public void setNullValues() throws Exception {    
   	pValueCheck.setNull(); 
   	pValueLOV.setNull(); 
   	pValueDate.setNull(); 
   	pValueEdit.setNull(); 
  	pValueCheck2.setNull(); 
   	pValueLOV2.setNull(); 
   	pValueDate2.setNull(); 
   	pValueEdit2.setNull(); 
   	pValor.setNull();
   	pValor2.setNull();
   } 
  public void setValor(String zValue) throws Exception {    
  	if (isCheckInput())
    	pValueCheck.setValue(zValue); 
  	if (isLOVInput())
    	pValueLOV.setValue(zValue); 
  	if (isDateInput())
    	pValueDate.setValue(zValue); 
  	pValueEdit.setValue(zValue); 
  }
  public void setValor(Date zValue) throws Exception {    
  	pValueDate.setValue(zValue); 
  }
  public void setValor2(String zValue) throws Exception {    
  	if (isCheckInput())
    	pValueCheck2.setValue(zValue); 
  	if (isLOVInput())
    	pValueLOV2.setValue(zValue); 
  	if (isDateInput())
    	pValueDate2.setValue(zValue); 
  	pValueEdit2.setValue(zValue); 
  }
  public void setValor2(Date zValue) throws Exception {    
  	pValueDate2.setValue(zValue); 
  }

	private JString pMetaTitulo = new JString(){
		public void preset() throws Exception {
			pMetaTitulo.setValue("{*titulo_alineado_"+pOrden.getValue()+"}");
		};
	};
	private JString pMetaCampoTotal = new JString(){
		public void preset() throws Exception {
			pMetaCampoTotal.setValue("{*x_total_"+getNameField(pCampo.getValue())+"}");
		};
	};
	private JString pDescrRecordSet = new JString() {
		public void preset() throws Exception {
			pDescrRecordSet.setValue(getDescrRecordSet());
		}
	};
	private JObjBDs pDetalle = new JObjBDs() {
		public void preset() throws Exception {
			setValue(getObjDetalle());
		}
	};
	
	private JRecord recordOwner;
	private JRecord recordGeoOwner;
	private JRecord recordSource;
	private transient JRelation relation;

	public void clean() throws Exception {
		recordOwner=null;
		recordGeoOwner=null;
		recordSource=null;
		relation=null;
		super.clean();
	}
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getDescrOrientacion() throws Exception {
		if (isColumna()) return JLanguage.translate("Columna");
		if (isFila()) return JLanguage.translate("Fila");
		return JLanguage.translate("Campo");
	}

	public boolean isFila() throws Exception {
		return getOrientacion().equals(FILA);
	}
	public boolean isColumna() throws Exception {
		return getOrientacion().equals(COLUMNA);
	}
	public boolean isCampo() throws Exception {
		return getOrientacion().equals(CAMPO);
	}
	public boolean isLinea() throws Exception {
		return getOrientacion().equals(LINEA);
	}
	public boolean isNoUsar() throws Exception {
		return getOrientacion().equals("-");
	}
	public boolean isRolEje() throws Exception {
		return isRolFila() || isRolColumna();
	}
	public boolean isRolFila() throws Exception {
		return getRol().equals(FILA);
	}
	public boolean isRolColumna() throws Exception {
		return getRol().equals(COLUMNA);
	}
	public boolean isRolCampo() throws Exception {
		return getRol().equals(CAMPO) || getRol().equals(LINEA);
	}
	public void setNullRol() throws Exception {
		 pRol.setNull();
	}
	public boolean hasRol() throws Exception {
		return !pRol.isNull();
	}
	public boolean isRolLinea() throws Exception {
		return getRol().equals(LINEA);
	}
	public boolean isRolCategoria() throws Exception {
		return getRol().equals(FILA);
	}
	public boolean isRolDataset() throws Exception {
		return getRol().equals(COLUMNA);
	}
	public boolean isRolValor() throws Exception {
		return getRol().equals(CAMPO);
	}
	public boolean getCalculeDiferencia() throws Exception {
		return pCalculeDiferencia.getValue();
	}
	public void setCalculeDiferencia(boolean zValue) throws Exception {
		pCalculeDiferencia.setValue(zValue);
	}

	public boolean getPorcentajeDiferencia() throws Exception {
		return pPorcentajeDiferencia.getValue();
	}
	public void setPorcentajeDiferencia(boolean zValue) throws Exception {
		pPorcentajeDiferencia.setValue(zValue);
	}
	
	public void setCampoDiferencia(JList<String> zValue) throws Exception {
		pCampoDiferencia.setValue(zValue);
		objDifCampos=null;
	}
	public void setOperacion(String zValue) throws Exception {
		pOperacion.setValue(zValue);
	}

	public String getOrigenDatos() throws Exception {
		return pOrigenDatos.getValue();
	}
	public boolean isNullOrigenDatos() throws Exception {
		return pOrigenDatos.isNull();
	}

	public void setOrigenDatos(String pOrigenDatos) throws Exception {
		this.pOrigenDatos.setValue(pOrigenDatos);
	}

	/* (non-Javadoc)
	 * @see pss.common.customList.config.ICampo#getCampo()
	 */
	public JList<String> getCampoDiferencia() throws Exception {
		return pCampoDiferencia.getValue();
	}
	


	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}
	public String getFormatParam() throws Exception {
	  String fencode = JTools.replace(pFormatParam.getValue().trim(), "+", "[PLUS]");
		String s =JTools.replace( URLDecoder.decode( fencode, CharEncoding.ISO_8859_1),"[PLUS]", "+");
		return s;
	}

	public String getRecordOwner() throws Exception {
		return pRecordOwner.getValue();
	}

	public JList<String> getAmbitoPorcentaje() throws Exception {
		return pAmbitoPorcentaje.getValue();
	}

	public String getRecordSource() throws Exception {
		return pRecordSource.getValue();
	}

	public String getRelId() throws Exception {
		return pRelId.getValue();
	}
  public boolean hasRelId() throws Exception {
  	return this.pRelId.isNotNull();
  }
	public void setRelId(String zValue) throws Exception {
		pRelId.setValue(zValue);
//		recordTarget=null;
		relation=null;
	}


	public boolean hasRanking() throws Exception {
		return pCampoOrden.isNotNull();
	}

	public boolean hasDiferencia() throws Exception {
		return false;
	}

	public boolean hasNombreFiltro() throws Exception {
		return( pNombreFiltro.isNotNull() && !pNombreFiltro.getValue().equals("")) || (pNombreColumna.isNotNull() && !pNombreColumna.getValue().equals(""));
	}

	public String getNombreFiltro() throws Exception {
		if (pNombreFiltro.isNull())
			return pNombreColumna.getValue();
		return pNombreFiltro.getValue();
	}
	//	public void setRecordSet(String zValue) throws Exception {
//		pRecordSet.setValue(zValue);
//		this.recordSet=null;
//	}

	public void setListId(long zValue) throws Exception {
		pListId.setValue(zValue);
	}
	public void setNullToListId() throws Exception {
		pListId.setNull();
	}

	public long getListId() throws Exception {
		return pListId.getValue();
	}
	public long getOrden() throws Exception {
		return pOrden.getValue();
	}

	public long getOrdenOrden() throws Exception {
		return pOrdenOrden.getValue();
	}
	public String getOrdenAscDesc() throws Exception {
		return pOrdenAscDesc.getValue();
	}
	public String getOrdenAscDescForce(String defa) throws Exception {
		return  !hasOrdenAscDesc()?defa:pOrdenAscDesc.getValue();
	}
	public boolean hasOrdenAscDesc() throws Exception {
		return !pOrdenAscDesc.isNull() && !pOrdenAscDesc.getValue().equals(ORDER_NO);
	}

	public long getOrdenLimite() throws Exception {
		return pOrdenLimite.getValue();
	}
	
	public void setOrdenLimite(int orden) throws Exception {
		pOrdenLimite.setValue(orden);
	}


	public String getCampoOrden() throws Exception {
		return pCampoOrden.getValue();
	}
	public void setCampoOrden(String zValue) throws Exception {
		pCampoOrden.setValue(zValue);
	}

	public long getSecuencia() throws Exception {
		return pSecuencia.getValue();
	}
	public boolean hasSecuencia() throws Exception {
		return pSecuencia.isNotNull();
	}
	public void setNullToSecuencia() throws Exception { pSecuencia.setNull();}
	public void setSecuenciaOld(long zValue) throws Exception {
		pSecuenciaOld.setValue(zValue);
	}
	public void setSecuencia(long zValue) throws Exception {
		pSecuencia.setValue(zValue);
	}
	public long getSecuenciaOld() throws Exception {
		return pSecuenciaOld.getValue();
	}
	public void setNullToSecuenciaOld() throws Exception { pSecuenciaOld.setNull();}

	//	public long getMaxSize() throws Exception {
//		return pMaxSize.getValue();
//	}
//	public void setMaxSize(long zVal) throws Exception {
//		pMaxSize.setValue(zVal);
//	}
	public String getForceNombreColumna() throws Exception {
		if (pNombreColumna.isNull() || pNombreColumna.equals(""))
			return getDescrCampo();
		return pNombreColumna.getValue();
	}
	public String getNombreColumna() throws Exception {
		return pNombreColumna.getValue();
	}
	public boolean isCorteControl() throws Exception {
		return pCorteControl.getValue();
	}
	public void setCorteControl(boolean zValue) throws Exception {
		pCorteControl.setValue(zValue);
	}
  public boolean isNegado() throws Exception {
  	return this.pNegado.getValue().equals(TIPO_FALSE);
  }
  public boolean isOnlyFiltro() throws Exception {
		if (getFuncion()!=null) {
			BizFuncion fx = BizFuncion.findFuncion(getFuncion());
			if (fx != null) {
				if (fx.hasOnlyFunction()) return true;
			}			
		}
  	return false;
  }
  public boolean isFiltroActivo() throws Exception {
  	if (isOnlyFiltro()) return true;
  	if (isGroup()) return true;
//   	if (!hasOperador())//this
//    		return false;
  	if (BizFuncion.isFuncionYOperador(this.getFuncion())) return true;
		BizOperador oper = BizOperador.findOperador(getOperador());
		if (oper==null) {
			if ( hasValor1() && hasStadistic()) return true;
			
			return false;
		}
		int cant = oper.getCantValores();
		if (cant==0) return true;
		if (getOperador().equals("<>")) return true;
		if (cant==1 && hasValor1()) return true;
		if (cant==2 && hasValor2()) return true;
		if (cant==1 && !getCampoKey().equals("")) return true;
		if (cant==2 && !getCampoKey().equals("") && !getCampoKey2().equals("")) return true;
		return false;
  }
	public boolean getVisible() throws Exception {
		return pVisible.getValue();
	}
	public void setVisible(boolean zValue) throws Exception {
		pVisible.setValue(zValue);
	}
	public boolean isPorcentaje() throws Exception {
		return pPorcentaje.getValue();
	}
	public void setPorcentaje(boolean zValue) throws Exception {
		pPorcentaje.setValue(zValue);
	}
	boolean over=false;
	public boolean isOver() throws Exception {
		return over;
	}
	public void setOver(boolean zValue) throws Exception {
		over = zValue;
	}
	public void setCampo(String zValue) throws Exception {
		pCampo.setValue(zValue);
	}
	public String getOrientacion() throws Exception {
		return pOrientacion.getValue();
	}
	public void setOrientacion(String zValue) throws Exception {
		pOrientacion.setValue(zValue);
	}
	public String getRol() throws Exception {
		return pRol.getValue();
	}
	public String getRolDescription() throws Exception  {
		if (!hasRol()) return getRolDescription(null);
		if (isRolFila()) return getRolDescription(BizCampo.FILA);
		if (isRolColumna()) return getRolDescription(BizCampo.COLUMNA);
		if (isRolCampo()) return getRolDescription(BizCampo.CAMPO);
		if (isRolLinea()) return getRolDescription(BizCampo.LINEA);
		return null;
	}
	public String getRolDescription(String campo) throws Exception  {
		if (campo==null) {
			if (hasFiltro())
				return "Filtro";
			return "No usado";
		}
		if (getObjCustomList().isGrafico()) {
			boolean isGeo =getObjCustomList().isGeo();
			if (campo.equals(BizCampo.FILA)) return isGeo?"GeoPosition":"Categoria";
			if (campo.equals(BizCampo.COLUMNA)) return isGeo?"GeoPosition":"Set de datos";
			if (campo.equals(BizCampo.CAMPO)) return "Valor";
			if (campo.equals(BizCampo.LINEA)) return "Linea";
			return null;
			
		}
		if (campo.equals(BizCampo.FILA)) return "Fila";
		if (campo.equals(BizCampo.COLUMNA)) return "Columna";
		if (campo.equals(BizCampo.CAMPO)) return "Campo";
		return null;
	}
  public boolean isDate() throws Exception {
		if (getTipoCampo().equalsIgnoreCase("JDATE")) return true;
		return false;
  }
	public void setRol(String zValue) throws Exception {
		pRol.setValue(zValue);
	}
	public void setIdGallery(long zValue) throws Exception {
		pIdGallery.setValue(zValue);
	}
	public void setOrden(long zValue) throws Exception {
		pOrden.setValue(zValue);
	}
	public void setOrdenOrden(long zValue) throws Exception {
		pOrdenOrden.setValue(zValue);
	}
	public void setOrdenAscDesc(String zValue) throws Exception {
		pOrdenAscDesc.setValue(zValue);
	}
	public String getCampo() throws Exception {
		return pCampo.getValue();
	}

	public void setOrdenPadre(String zValue) throws Exception {
		pOrdenPadre.setValue(zValue);
	}

	public String getOrdenPadre() throws Exception {
		return pOrdenPadre.getValue();
	}
	public String getCampoClave() throws Exception {
		return pIdKey.getValue();
	}
	public void setCampoKey(String zValue) throws Exception {
		pIdKey.setValue(zValue);
	}
	public String getCampoKey() throws Exception {
		return pIdKey.getValue();
	}
	public boolean hasCampoKey() throws Exception {
		return pIdKey.hasValue();
	}
	public void setCampoKey2(String zValue) throws Exception {
		pIdKey2.setValue(zValue);
	}
	public String getCampoKey2() throws Exception {
		return pIdKey2.getValue();
	}
	public boolean hasCampoKey2() throws Exception {
		return pIdKey2.hasValue();
	}
  public void setDinamico(boolean zValue) throws Exception {    pDinamico.setValue(zValue);  }
  public boolean isDinamico() throws Exception {     return pDinamico.getValue();  }
  public void setNoIncluir(boolean zValue) throws Exception {    pNoIncluir.setValue(zValue);  }
  public boolean isNoIncluir() throws Exception {     return pNoIncluir.getValue();  }
  public void setSoloTotalizador(boolean zValue) throws Exception {    pOnlyTotalizador.setValue(zValue);  }
  public boolean isSoloTotalizador() throws Exception {     return pOnlyTotalizador.getValue();  }
  public void setOcultarReporte(boolean zValue) throws Exception {    pHideReport.setValue(zValue);  }
  public boolean isOcultarReporte() throws Exception {     return pHideReport.getValue();  }

	public String getFuncion() throws Exception {
		if (pFuncion==null) return "";
		if (getObjCustomList()==null) 
			return "";
		if (!BizFuncion.isFuncionYOperador(pFuncion.getValue())&&
				(getObjCustomList().isLista()&&!BizFuncion.activeFunctionInLista(pFuncion.getValue()))) return "";
		return pFuncion.getValue();
	}
	public void setFuncion(String value) throws Exception {
		pFuncion.setValue(value);
	}
	public boolean hasFiltro() throws Exception {
		return pHasFiltro.getValue();
	}
	
	public void setHasFiltro(boolean hasFiltro) throws Exception {
		pHasFiltro.setValue(hasFiltro);
	}

	public boolean getHasColores() throws Exception {
		return pColores.getValue();
	}
	public boolean getHasMarcaMayores() throws Exception {
		return pMarcaMayores.getValue();
	}
	public boolean getHasMarcaMenores() throws Exception {
		return pMarcaMenores.getValue();
	}
	public boolean getHasMarcaTop10() throws Exception {
		return pMarcaTop10.getValue();
	}
	public boolean getHasMarcaBottom10() throws Exception {
		return pMarcaBottom10.getValue();
	}
	public String getColorBackgorund() throws Exception {
		return pColorBackground.getValue();
	}
	public String getColorForegorund() throws Exception {
		return pColorForeground.getValue();
	}
	public String getColorTopBackgorund() throws Exception {
		return pColorTopBackground.getValue();
	}
	public String getColorBottomBackgorund() throws Exception {
		return pColorBottomBackground.getValue();
	}
	public String getMayoresA() throws Exception {
		return pMayoresA.getValue();
	}
	public String getMenoresA() throws Exception {
		return pMenoresA.getValue();
	}
	public void setRecordOwner(String zValue) throws Exception {
		pRecordOwner.setValue(zValue);
	}

	public void setRecordSource(String zValue) throws Exception {
		pRecordSource.setValue(zValue);
	}
  public void setSerialDeep(String s) throws Exception {
  	this.pSerialDeep.setValue(s);
  }
  
  public String getOperacion() throws Exception {     return pOperacion.getValue();  }
  public String getOperador() throws Exception {     return pOperador.getValue();  }
  public boolean hasOperador() throws Exception {     return false;/*pOperador.isNotNull();*/  }
  public String getValor() throws Exception {    
  	return pValor.getValue();  
  }
  public String getValor2() throws Exception {    
  	return pValor2.getValue(); 
  }
  public boolean hasValor2() throws Exception {     return pValor2.isNotNull() && !pValor2.getValue().equals("");  }
  public boolean hasValor1() throws Exception {     return pValor.isNotNull()  && !pValor.getValue().equals("");  }
  public String getDescrTrue() throws Exception {     return null;  }
  public String getDescrFalse() throws Exception {     return null;  }


	/**
	 * Constructor de la Clase
	 */
	public BizCampo() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem( "company", pCompany);
		this.addItem( "list_id", pListId);
		this.addItem( "secuencia", pSecuencia);
		this.addItem( "secuencia_old", pSecuenciaOld);
		this.addItem( "record_owner", pRecordOwner);
		this.addItem( "record_source", pRecordSource);
		this.addItem( "rel_id", pRelId);
		this.addItem( "campo", pCampo);
		this.addItem( "nombre_columna", pNombreColumna);
		this.addItem( "formato", pFormat);
		this.addItem( "formato_param", pFormatParam);
		this.addItem( "funcion", pFuncion);
		this.addItem( "porcentaje", pPorcentaje);
		this.addItem( "ambito_porcentaje", pAmbitoPorcentaje);
//		this.addItem("max_size", pMaxSize);
		this.addItem( "orden", pOrden);
		this.addItem( "orden_orden", pOrdenOrden);
		this.addItem( "orden_ascdesc", pOrdenAscDesc);
		this.addItem( "orden_limite", pOrdenLimite);
		this.addItem( "visible", pVisible);
		this.addItem( "colores", pColores);
		this.addItem( "color_background", pColorBackground);
		this.addItem( "color_foreground", pColorForeground);
		this.addItem( "color_topbackground", pColorTopBackground);
		this.addItem( "color_bottombackground", pColorBottomBackground);
		this.addItem( "marca_mayores", pMarcaMayores);
		this.addItem( "marca_menores", pMarcaMenores);
		this.addItem( "marca_top", pMarcaTop10);
		this.addItem( "marca_bottom", pMarcaBottom10);
		this.addItem( "mayores_a", pMayoresA);
		this.addItem( "menores_a", pMenoresA);
		this.addItem( "descr_campo", pDescrCampo);
		this.addItem( "descr_filtro", pDescrFiltro);
		this.addItem( "descr_record_set", pDescrRecordSet);
		this.addItem( "serial_deep", pSerialDeep);
		this.addItem( "id_gallery", pIdGallery);
		this.addItem( "metacampo", pMetaCampo);
		this.addItem( "metatitulo", pMetaTitulo);
		this.addItem( "metacampototal", pMetaCampoTotal);
//		this.addItem( "ranking", pRanking);
		this.addItem( "campo_orden", pCampoOrden);
    this.addItem( "operador", pOperador );
    this.addItem( "valor", pValor );
    this.addItem( "valor2", pValor2 );
    this.addItem( "negado", pNegado );
    this.addItem( "descr_true", pDescrTrue );
    this.addItem( "descr_false", pDescrFalse );
		this.addItem( "orientacion", pOrientacion);
		this.addItem( "rol", pRol);
		this.addItem( "descr_orientacion", pDescrOrientacion);
		this.addItem( "corte_control", pCorteControl);		
		this.addItem( "calc_diferencia", pCalculeDiferencia);
		this.addItem( "porc_diferencia", pPorcentajeDiferencia);
		this.addItem( "campo_diferencia", pCampoDiferencia);
		this.addItem( "operacion", pOperacion);
	  this.addItem( "nombre_filtro", pNombreFiltro );
	  this.addItem( "name_for_filter", pNameForFilter );
	  this.addItem( "campo_function", pCampoFuncion );
	  this.addItem( "campo_serial", pSerialCampo );
	  
    this.addItem( "value_edit", pValueEdit );
    this.addItem( "value_lov", pValueLOV );
    this.addItem( "value_check", pValueCheck );
    this.addItem( "value_date", pValueDate );
    this.addItem( "value_edit2", pValueEdit2 );
    this.addItem( "value_lov2", pValueLOV2 );
    this.addItem( "value_check2", pValueCheck2 );
    this.addItem( "value_date2", pValueDate2 );
    
		this.addItem( "has_filter", pHasFiltro);
		this.addItem( "orden_padre", pOrdenPadre);
  	this.addItem( "campo_key", pIdKey);
  	this.addItem( "campo_key2", pIdKey2);
    this.addItem( "dinamico", pDinamico );
    this.addItem( "no_incluir", pNoIncluir );
    this.addItem( "solo_tot", pOnlyTotalizador );
    this.addItem( "hide_report", pHideReport );
    this.addItem( "rol_descripcion", pRolDescripcion );
    this.addItem( "origendatos", pOrigenDatos );
    
    

        
	  this.addItem( "detalle", pDetalle );
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 15);
		this.addFixedItem(KEY, "list_id", "List id", true, true, 64);
		this.addFixedItem(KEY, "secuencia", "Secuencia", false, false, 64);
		this.addFixedItem(FIELD, "secuencia_old", "Record Owner old ", false, false, 18);
		this.addFixedItem(FIELD, "record_owner", "Record Owner", true, false, 250);
		this.addFixedItem(FIELD, "record_source", "Record Source", true, false, 250);
		this.addFixedItem(FIELD, "rel_id", "Relación", true, false, 20);
		this.addFixedItem(FIELD, "campo", "Campo", true, false, 100);
		this.addFixedItem(FIELD, "orden_orden", "Orden orden", true, false, 18);
		this.addFixedItem(FIELD, "orden_ascdesc", "Orden ascdesc", true, false, 10);
		this.addFixedItem(FIELD, "orden_limite", "Orden limite", true, false, 18);
		this.addFixedItem(FIELD, "nombre_columna", "Nombre Columna", true, false, 200);
		this.addFixedItem(FIELD, "formato", "Formato", true, false, 10);
		this.addFixedItem(FIELD, "formato_param", "Formato Parámetro", true, false, 1000);
		this.addFixedItem(FIELD, "funcion", "Función", true, false, 100);
		this.addFixedItem(FIELD, "porcentaje", "Porcentaje", true, false, 1);
		this.addFixedItem(FIELD, "ambito_porcentaje", "Ambito Porcentaje", true, false, 1000);
//		this.addFixedItem(FIELD, "max_size", "Max filas", true, false, 18);
		this.addFixedItem(FIELD, "orden", "Orden", true, true, 5);
		this.addFixedItem(FIELD, "visible", "Visible", true, false, 1);
		this.addFixedItem(FIELD, "color_background", "Color fondo", true, false, 10);
		this.addFixedItem(FIELD, "color_foreground", "Color letra", true, false, 10);
		this.addFixedItem(FIELD, "color_topbackground", "Color mayores fondo", true, false, 10);
		this.addFixedItem(FIELD, "color_bottombackground", "Color menores letra", true, false, 10);
		this.addFixedItem(FIELD, "colores", "Tiene colores", true, false, 1);
		this.addFixedItem(FIELD, "marca_top", "Marca top 10 mayores", true, false, 1);
		this.addFixedItem(FIELD, "marca_bottom", "Marca top 10 menores", true, false, 1);
		this.addFixedItem(FIELD, "marca_mayores", "Marca mayores", true, false, 1);
		this.addFixedItem(FIELD, "marca_menores", "Marca menores", true, false, 1);
		this.addFixedItem(FIELD, "mayores_a", "Mayores a", true, false, 50);
		this.addFixedItem(FIELD, "menores_a", "Menores a", true, false, 50);
		this.addFixedItem(FIELD, "hide_report", "Ocultar reporte", true, false, 1);
		this.addFixedItem(VIRTUAL, "descr_campo", "Campo", true, true, 100);
		this.addFixedItem(VIRTUAL, "descr_filtro", "Filtro", true, true, 100);
		this.addFixedItem(VIRTUAL, "descr_record_set", "Record Set", true, true, 100);
		this.addFixedItem(FIELD, "serial_deep", "Profundidad", true, false, 300);
		this.addFixedItem(VIRTUAL, "id_gallery", "Galleria", true, false, 18);
		this.addFixedItem(VIRTUAL, "metatitulo", "MetaTitulo", true, false, 18);
		this.addFixedItem(VIRTUAL, "metacampo", "Metacampo", true, false, 18);
		this.addFixedItem(VIRTUAL, "metacampototal", "Metacampototal", true, false, 18);
//		this.addFixedItem(FIELD, "ranking", "Ranking", true, false, 18);
		this.addFixedItem(FIELD, "campo_orden", "Campo orden", true, false, 100);
		this.addFixedItem(FIELD, "orientacion", "orientacion", true, false, 1);
		this.addFixedItem(VIRTUAL, "rol", "rol", true, false, 1);
    this.addFixedItem( FIELD, "operador", "Operador", true, false, 100 );
    this.addFixedItem( VIRTUAL, "valor", "Valor", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "valor2", "2do.Valor", true, false, 4000 );
    this.addFixedItem( FIELD, "negado", "negado", true, false, 1 );
    this.addFixedItem( FIELD, "descr_true", "Descr.Verdadero", true, false, 50 );
    this.addFixedItem( FIELD, "descr_false", "Descr.False", true, false, 50 );
		this.addFixedItem(FIELD, "calc_diferencia", "Calulo diferencia", true, false, 1);
		this.addFixedItem(FIELD, "porc_diferencia", "Porcentaje diferencia", true, false, 1);
		this.addFixedItem(FIELD, "campo_diferencia", "Campo diferencia", true, false, 100);
		this.addFixedItem(FIELD, "corte_control", "Corte control", true, false, 1);
		this.addFixedItem(VIRTUAL, "descr_orientacion", "Orientación", true, true, 100);
		this.addFixedItem(VIRTUAL, "campo_function", "campo funcion", true, true, 1000);
		this.addFixedItem(VIRTUAL, "campo_serial", "campo_serial", true, true, 1000);
		this.addFixedItem(VIRTUAL, "rol_descripcion", "rol descripcion", true, false, 1000);
		this.addFixedItem( FIELD, "origendatos", "Origen datos", true, false, 20);
		
    this.addFixedItem( FIELD, "value_edit", "Valor", true, false, 4000 );
    this.addFixedItem( FIELD, "value_lov", "Valor", true, false, 4000 );
    this.addFixedItem( FIELD, "value_check", "Valor", true, false, 4000 );
    this.addFixedItem( FIELD, "value_date", "Valor", true, false, 4000 );

    this.addFixedItem( FIELD, "value_edit2", "2do.Valor", true, false, 4000 );
    this.addFixedItem( FIELD, "value_lov2", "2do.Valor", true, false, 4000 );
    this.addFixedItem( FIELD, "value_check2", "2do.Valor ", true, false, 4000 );
    this.addFixedItem( FIELD, "value_date2", "2do.Valor", true, false, 4000 );
    
		this.addFixedItem( FIELD, "has_filter", "Tiene filtro?", true, false, 1);
		this.addFixedItem( FIELD, "orden_padre", "Orden Padre", true, false, 100);
  	this.addFixedItem( FIELD, "campo_key", "Default 1", true, false, 50);
  	this.addFixedItem( FIELD, "campo_key2", "Default 2", true, false, 50);
    this.addFixedItem( FIELD, "dinamico", "Ingreso Manual", true, false, 1 );
    this.addFixedItem( FIELD, "no_incluir", "No incluir", true, false, 1 );
    this.addFixedItem( FIELD, "solo_tot", "Solo en totalizador", true, false, 1 );
	  this.addFixedItem( FIELD, "nombre_filtro", "Nombre Filtro", true, false, 200 );
	  this.addFixedItem( FIELD, "operacion", "Operación", true, true, 5);
    this.addFixedItem( VIRTUAL, "name_for_filter", "Nombre Filtro", true, false, 4000 );
	  this.addFixedItem( RECORDS, "detalle", "Detalle", true, false, 0).setClase(BizCampo.class);

	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "LST_CAMPOV2";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean read( long zCampo) throws Exception {
		addFilter("secuencia", zCampo);
		return read();
	}
	/**
	 * Default read() method
	 */
	public boolean read(String zCompany, long zListId, long zCampo) throws Exception {
		addFilter("company", zCompany);
		addFilter("list_id", zListId);
		addFilter("secuencia", zCampo);
		return read();
	}
	public boolean readByOrden(String zCompany, long zListId, long zCampo) throws Exception {
		addFilter("company", zCompany);
		addFilter("list_id", zListId);
		addFilter("orden", zCampo);
		return read();
	}
	//esta lectura puede ser ambigua... pues pueden existir multiples campos con valor de campo
//	public boolean read(String zCompany, long zListId, String zTitulo) throws Exception {
//		addFilter("company", zCompany);
//		addFilter("list_id", zListId);
//		addFilter("campo", zCampo);
//		return read();
//	}
	public boolean read(String zCompany, long zListId, String zSerialDeep,String campo,String funcion, boolean porcentaje,long orden) throws Exception {
		addFilter("company", zCompany);
		addFilter("list_id", zListId);
		addFilter("serial_deep", zSerialDeep);
		addFilter("campo", campo);
		addFilter("porcentaje", porcentaje);
		addFilter("funcion", funcion);
		addFilter("orden" , orden);
		return read();
	}
	public boolean read(String zCompany, long zListId, String zSerialDeep,String campo,String funcion) throws Exception {
		addFilter("company", zCompany);
		addFilter("list_id", zListId);
		addFilter("serial_deep", zSerialDeep);
		addFilter("campo", campo);
		addFilter("funcion", funcion);
		return read();
	}
	public static JMap<String, String> getFunctionMap(String tipo) throws Exception {
		if (tipo==null) return getFunctionStringMap();
		if (tipo.equals(JObject.JSTRING))
			return getFunctionStringMap();
		if (tipo.equals(JObject.JDATE))
			return getFunctionFilterFechaGrupoMap();
		if (tipo.equals(JObject.JDATETIME))
			return getFunctionFilterFechaGrupoMap();
		if (tipo.equals(JObject.JFLOAT))
			return getFunctionNumericaMap();
		if (tipo.equals(JObject.JLONG))
			return getFunctionNumericaMap();
		if (tipo.equals(JObject.JINTEGER))
			return getFunctionNumericaMap();
		if (tipo.equals(JObject.JCURRENCY))
			return getFunctionNumericaMap();
		if (tipo.equals(JObject.JBOOLEAN))
			return getFunctionBooleanMap();
		if (tipo.equals("FORMULA"))
			return getFunctionNumericaMap();
		return getFunctionEmptyMap();
	}

	static JMap<String, String> mapAll;

	public static JMap<String, String> getFunctionAllMap() throws Exception {
		if (mapAll != null)
			return mapAll;
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElements(getFunctionNumericaMap());
		map.addElements(getFunctionFilterFechaGrupoMap());
		return mapAll = map;
	}

	public static JMap<String, String> getFunctionEmptyMap() throws Exception {
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement(FUNTION_COUNT, "Cantidad");
		return map;
	}
	public static JMap<String, String> getFunctionBooleanMap() throws Exception {
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement(FUNTION_COUNT, "Cantidad");
		map.addElement(FUNTION_MAX, "Maximo");
		map.addElement(FUNTION_MIN, "Minimo");
		return map;
	}


	public static JMap<String, String> getFunctionStringMap() throws Exception {
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement(FUNTION_COUNT, "Cantidad");
		map.addElement(FUNTION_MAX, "Maximo");
		map.addElement(FUNTION_MIN, "Minimo");
//		map.addElement(FUNTION_FORMULA, "Formula");
		map.addElement(FUNTION_NULO, "Sin valor");
		map.addElement(FUNTION_NONULO, "Con valor");
		return map;
	}

	public static JMap<String, String> getFunctionNumericaMap() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();

		map.addElement(FUNTION_COUNT, "Cantidad");
		map.addElement(FUNTION_MAX, "Maximo");
		map.addElement(FUNTION_MIN, "Minimo");
		map.addElement(FUNTION_SUM, "Suma");
		map.addElement(FUNTION_AVR, "Promedio");
		map.addElement(FUNTION_NULO, "Sin valor");
		map.addElement(FUNTION_NONULO, "Con valor");
		return map;
	}

	public static JMap<String, String> getFunctionFilterFechaGrupoMap() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement(FUNTION_MES, "Mes");
		map.addElement(FUNTION_ANIO, "año");
		map.addElement(FUNTION_ANOMES, "año/Mes");
		map.addElement(FUNTION_ANOSEM, "año/Sem");
		map.addElement(FUNTION_BIMESTRE, "Bimestre");
		map.addElement(FUNTION_TRIMESTRE, "Trimestre");
		map.addElement(FUNTION_CUATRIMESTRE, "Cuatrimestre");
		map.addElement(FUNTION_SEMESTRE, "Semestre");
		map.addElement(FUNTION_DIA_SEMANA, "Dia de la semana");
		map.addElement(FUNTION_DIA_MES, "Dia del mes");
		map.addElement(FUNTION_DIA_ANO, "Dia del año");
		map.addElement(BizFuncion.FUNTION_HOY, "Hoy");
		map.addElement(FUNTION_AYER, "Ayer");
		map.addElement(BizFuncion.FUNTION_MANIANA, "Mañana");
		map.addElement(FUNTION_NULO, "Sin valor");
		map.addElement(FUNTION_NONULO, "Con valor");
		map.addElement(BizFuncion.FUNTION_ANIOACTUAL, "año actual");
		map.addElement(BizFuncion.FUNTION_MESACTUAL, "Mes actual");
		map.addElement(BizFuncion.FUNTION_BIMESTREACTUAL, "Bimestre actual");
		map.addElement(BizFuncion.FUNTION_TRIMESTREACTUAL, "Trimestre actual");
		map.addElement(BizFuncion.FUNTION_CUATRIMESTREACTUAL, "Cuatrimestre actual");
		map.addElement(BizFuncion.FUNTION_SEMESTREACTUAL, "Semestre actual");
		map.addElement(BizFuncion.FUNTION_ANIOANTERIOR, "año anterior");
		map.addElement(BizFuncion.FUNTION_MESANTERIOR, "Mes anterior");
		map.addElement(BizFuncion.FUNTION_BIMESTREANTERIOR, "Bimestre anterior");
		map.addElement(BizFuncion.FUNTION_TRIMESTREANTERIOR, "Trimestre anterior");
		map.addElement(BizFuncion.FUNTION_CUATRIMESTREANTERIOR, "Cuatrimestre anterior");
		map.addElement(BizFuncion.FUNTION_SEMESTREANTERIOR, "Semestre anterior");
		map.addElement(BizFuncion.FUNTION_FUTURO, "En el futuro");
		map.addElement(BizFuncion.FUNTION_PASADO, "En el pasado");
		map.addElement(BizFuncion.FUNTION_ULTIMOS, "Ultimos días");
		map.addElement(BizFuncion.FUNTION_PROXIMOS, "Proximos días");

		return map;
	}

	public static JMap<String, String> getFormatMap() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement(FORMAT_STR, "Substring (desde,hasta)");
		map.addElement(FORMAT_DATE, "Formato Fecha (DMY)");
		map.addElement(FORMAT_NUM, "Formato Número (#0)");
		return map;
	}
	public static JRecords<BizVirtual> getOperadores() throws Exception {
		JRecords<BizVirtual> oBDs=JRecords.createVirtualBDs();
		oBDs.addItem(JRecord.virtualBD("=", "igual", 1));
		oBDs.addItem(JRecord.virtualBD("<>", "distinto", 1));
		oBDs.addItem(JRecord.virtualBD(">", "mayor", 1));
		oBDs.addItem(JRecord.virtualBD("<", "menor", 1));
		oBDs.addItem(JRecord.virtualBD(">=", "mayor o igual", 1));
		oBDs.addItem(JRecord.virtualBD("<=", "menor o igual", 1));
		oBDs.addItem(JRecord.virtualBD("like", "Contenga", 1));
		oBDs.addItem(JRecord.virtualBD("NOT_NULL", "No Nulo", 0));
		oBDs.addItem(JRecord.virtualBD("IS_NULL", "Nulo", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_INTERVALO, "Intervalo", 2));
		oBDs.addItem(JRecord.virtualBD("in", "Incluya", 1));
		return oBDs;
	}


	public static JRecords<BizVirtual> getFuncionesFecha() throws Exception {
		JRecords<BizVirtual> oBDs=JRecords.createVirtualBDs();
		oBDs.addItem(JRecord.virtualBD(FUNTION_ANIO, "año", 1));
		oBDs.addItem(JRecord.virtualBD(FUNTION_MES, "Mes", 1));
		oBDs.addItem(JRecord.virtualBD(FUNTION_BIMESTRE, "Bimestre", 1));
		oBDs.addItem(JRecord.virtualBD(FUNTION_TRIMESTRE, "Trimestre", 1));
		oBDs.addItem(JRecord.virtualBD(FUNTION_CUATRIMESTRE, "Cuatrimestre", 1));
		oBDs.addItem(JRecord.virtualBD(FUNTION_SEMESTRE, "Semestre", 1));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_ANIOACTUAL, "año actual", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_MESACTUAL, "Mes actual", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_BIMESTREACTUAL, "Bimestre actual", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_TRIMESTREACTUAL, "Trimestre actual", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_CUATRIMESTREACTUAL, "Cuatrimestre actual", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_SEMESTREACTUAL, "Semestre actual", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_ANIOANTERIOR, "año anterior", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_MESANTERIOR, "Mes anterior", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_BIMESTREANTERIOR, "Bimestre anterior", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_TRIMESTREANTERIOR, "Trimestre anterior", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_CUATRIMESTREANTERIOR, "Cuatrimestre anterior", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_SEMESTREANTERIOR, "Semestre anterior", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_HOY, "Hoy", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_AYER, "Ayer", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_MANIANA, "Mañana", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_FUTURO, "En el futuro", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_PASADO, "En el pasado", 0));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_ULTIMOS, "Ultimos días", 1));
		oBDs.addItem(JRecord.virtualBD(BizFuncion.FUNTION_PROXIMOS, "Proximos días", 1));
	return oBDs;
	}
	public static JRecords<BizVirtual> getFunciones() throws Exception {
	JRecords<BizVirtual> oBDs=JRecords.createVirtualBDs();
	return oBDs;
	}
  public String findDescrCompleta(String campo) throws Exception {
  	BizCampoGallery c = (BizCampoGallery) this.getCamposGallery().findInHash("id",""+getIdGallery());
	  if (c==null) return "";
  	return c.getDescrCompleta();
  }
  public String findDescrSinFuncion(String campo) throws Exception {
  	BizCampoGallery c = (BizCampoGallery) this.getCamposGallery().findInHash("id",""+getIdGallery());
	  if (c==null) return "";
  	return c.getDescrSinFuncion();
  }
	public String getDescripcion() throws Exception {
		String out = getDescrCampo();
		if (getObjCustomList().isMatriz()) {
			BizCampo orden = getObjCampoOrden();
			if (orden!=null) {
				out += " (Rank "+orden.getDescrCampo();
				if (orden.getOrdenLimite()>0) 
					out+=" Max."+orden.getOrdenLimite();
				out+=")";
			}
		}
		return out;
	}
	public String getDescrCampo() throws Exception {
		String s = "";//this.getDescrRecordSet()+"->";
   			if (getSecuencia()<0) {
			if (getSecuencia()%2==0)
				s+="Dif.";
			else 
				s+="Porc.Dif.";
		}
		if (this.isPorcentaje())  s+= JLanguage.translate("Porcentaje de ");
		if (this.isCampoCantidad()) { 
			return JLanguage.translate(s)+" "+JLanguage.translate("Cantidad de")+" "+ this.getObjRelation().getDescription();
		}
		if (this.isCampoFormula()) { 
			return JLanguage.translate(s)+" "+JLanguage.translate("Formula" )+" " +(this.getNombreColumna().equals("")? URLDecoder.decode(getFormatParam(), CharEncoding.ISO_8859_1):this.getNombreColumna());
		}
//		if (this.hasFunction()) s+= BizCampo.getFunctionAllMap().getElement(this.getFuncion())+" "+JLanguage.translate("de")+" ";
		if (!isNullOrigenDatos() && !getOrigenDatos().equals(getObjCustomList().getRelId())) 
				s+="Otro origen de datos";
		else
			s+=!this.hasCampo()?getDescrFiltroOR(true):this.findDescrSinFuncion(this.pCampo.getValue());
//		if (!this.pOperador.isEmpty()) s+=" "+BizOperador.findOperador(this.pOperador.getValue()).getDescripcion();
//		if (!this.pValor.isEmpty()) s+=" "+pValor.getValue();
//		if (!this.pValor2.isEmpty()) s+="-"+pValor2.getValue();
		return JLanguage.translate(s);
	}
  public String getDescrFiltroOR(boolean cut) throws Exception {
		StringBuffer s = new StringBuffer();
		if (this.isNegado()) s.append(" not ");
		s.append("( ");
		JIterator<BizCampo> iter = this.getObjDetalle().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
			s.append(f.getDescrFiltro(cut,null));
			if (iter.hasMoreElements())
				s.append(" O ");
		}
		s.append(" )");
		if (cut && s.length()>100) return s.toString().substring(0, 100)+" ....";
		return s.toString().equals("(  )")?"Filtro O":s.toString();
  }
  
  public String getDescrFiltroAND(boolean cut) throws Exception {
		StringBuffer s = new StringBuffer();
		if (this.isNegado()) s.append(" not ");
		s.append("( ");
		JIterator<BizCampo> iter = this.getObjDetalle().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
			s.append(f.getDescrFiltro(cut,null));
			if (iter.hasMoreElements())
				s.append(" Y ");
		}
		s.append(" )");
		if (cut && s.length()>100) return s.toString().substring(0, 100)+" ....";
		return s.toString().equals("(  )")?"Filtro Y":s.toString();
  }


  public String getDescrFiltro(boolean cut,BizAction a) throws Exception {
  	if (this.isOperOR()) return this.getDescrFiltroOR(cut);
  	if (this.isOperAndInterno()) return this.getDescrFiltroAND(cut);
		StringBuffer s = new StringBuffer();
//		s.append(this.getDescrRecordSet()+"->");
		s.append(this.getDescrCampo());
	//	s.append(this.pFuncion.isEmpty()?" ":" "+BizFuncion.findFuncion(this.pFuncion.getValue()).getDescripcion()+ " ");
		s.append(this.pOperador.isEmpty()?" ":" "+BizOperador.findOperador(this.pOperador.getValue()).getDescripcion()+ " ");
		if (this.pOperador.getValue().equals("NOT_NULL")) return s.toString();
		if (this.pOperador.getValue().equals("IS_NULL")) return s.toString();
		if (this.pOperador.getValue().equals(BizFuncion.FUNTION_MESACTUAL)) return s.toString();
		if (this.pOperador.getValue().equals(BizFuncion.FUNTION_ANIOACTUAL)) return s.toString();
		if (this.pOperador.getValue().equals(BizFuncion.FUNTION_AYER)) return s.toString();
		if (this.pOperador.getValue().equals(BizFuncion.FUNTION_HOY)) return s.toString();
		if (this.pOperador.getValue().equals(BizFuncion.FUNTION_MANIANA)) return s.toString();
		if (this.pOperador.getValue().equals(BizFuncion.FUNTION_BIMESTREACTUAL)) return s.toString();
		if (this.pOperador.getValue().equals(BizFuncion.FUNTION_TRIMESTREACTUAL)) return s.toString();
		if (this.pOperador.getValue().equals(BizFuncion.FUNTION_CUATRIMESTREACTUAL)) return s.toString();
		if (this.pOperador.getValue().equals(BizFuncion.FUNTION_SEMESTREACTUAL)) return s.toString();
//		if (this.pOperador.getValue().equals(BizFiltro.FUNTION_MES)) return s.toString();
//		if (this.pOperador.getValue().equals(BizFiltro.FUNTION_ANIO)) return s.toString();
//		if (this.pOperador.getValue().equals(BizFiltro.FUNTION_BIMESTRE)) return s.toString();
//		if (this.pOperador.getValue().equals(BizFiltro.FUNTION_TRIMESTRE)) return s.toString();
//		if (this.pOperador.getValue().equals(BizFiltro.FUNTION_CUATRIMESTRE)) return s.toString();
//		if (this.pOperador.getValue().equals(BizFiltro.FUNTION_SEMESTRE)) return s.toString();
		if (!this.getDescrValor(a).equals(""))s.append("["+this.getDescrValor(a)+"]");
		return s.toString();
  }
  public String getDescrValor(BizAction a) throws Exception {
  	if (this.isDinamico() && a==null) return "Ingreso Manual";
  	if (this.isDinamico() && a!=null) return a.getFilterMapValue("filtro"+getOrden(), "");
  	if (this.isLOVInput()) return this.findDescripLOV();
  	if (this.isCheckInput()) return this.getValor();
  	return this.getValor()+(this.hasValor2()&&!this.getValor2().equals("")?","+this.getValor2():"");
  }
  private String findDescripLOV() throws Exception {
  	String descr = this.getDescrValorLOV();
  	if (descr==null) return "";
  	String descr2 = this.getDescrValor2LOV();
  	if (descr2==null) return descr;
  	if (descr2.equals("")) return descr;
  	return descr + ", " + descr2;
  }
  private String getDescrValorLOV() throws Exception {
  	JRelation rel = this.getObjRelationFiltro();
  	if (!rel.hasClassTarget()) 
  		return rel.getMapTarget().getElement(this.pValor.getValue());

  
  	JRecords recs = new JRecords<JRecord>(rel.getClassTarget());
  	this.assingFiltersLOV(true, recs, 1);
  	recs.readAll();
  	recs.toStatic();
  	if (!recs.ifRecordFound())
  		return "";
		String s = recs.getFirstRecord().getDescripFieldValue();
		if (s!=null) return s; 
		return this.getValor();
//  	JRecord target = rel.getObjRecordTarget();
//	target.clearFilters();
//		JIterator<JPair> iter = rel.getJoins().getIterator();
//  	while (iter.hasMoreElements()) {
//  		JPair p = iter.nextElement();
//  		String relField = this.getField(p.secondObject());
//  		String parent = this.getField(p.secondObject());
//  		if (rel.getMyKeyField().equals(parent))
//  			target.addFilter(child, this.getValor());
//  		else if (rel.getRelKeyField().equals(parent))
//   			target.addFilter(parent, this.getValor());
//  	}
//  	target.dontThrowException(true);
//  	if (!target.read()) return "";
//		String s = target.getDescripFieldValue();
//		if (s!=null) return s; 
//		return this.getValor();
  }
 
  private String getDescrValor2LOV() throws Exception {
  	JRelation rel = this.getObjRelationFiltro();
  	if (!rel.hasClassTarget()) 
  		return rel.getMapTarget().getElement(this.pValor2.getValue());

  	JRecords recs = new JRecords<JRecord>(rel.getClassTarget());
  	this.assingFiltersLOV(true, recs, 2);
  	recs.readAll();
  	recs.toStatic();
  	if (!recs.ifRecordFound())
  		return "";
		String s = recs.getFirstRecord().getDescripFieldValue();
		if (s!=null) return s; 
		return this.getValor2();

//  	JRecord target = rel.getObjRecordTarget();
//  	target.clearFilters();
//  	JIterator<JPair> iter = rel.getJoins().getIterator();
//  	while (iter.hasMoreElements()) {
//  		JPair p = iter.nextElement();
//  		String child = this.getField(p.firstObject());
//  		String parent = this.getField(p.secondObject());
//  		if (rel.getMyKeyField().equals(parent))
//  			target.addFilter(child, this.getValor2());
//  		else
//  			target.addFilter(parent, this.getValor2());
// // 			target.addFilter(child, this.findValueOnFiltres(parent));
//  	}
//  	target.dontThrowException(true);
//  	if (!target.read()) return "";
//		String s = target.getDescripFieldValue();
//		if (s!=null) return s; 
//		return this.getValor2();
  }
  public String getNameForFilter() throws Exception {
  	if (hasNombreFiltro()) {
  		return getNombreFiltro();
  	}
  	else
  		return getDescrCampo();
  }
  public String getDescrCampoWithOpAndOrder() throws Exception {
  	if (hasOrdenAscDesc())
  		return "("+getOrdenOrden()+") "+getDescrCampoWithOp();
  	return getDescrCampoWithOp();
  }
  
  public String getDescrCampoWithOp() throws Exception {
  	if (this.isOperOR()) return this.getDescrFiltroOR(false);
  	if (this.isOperAndInterno()) return this.getDescrFiltroAND(false);
  	String title ="";
  	BizFuncion func =  getFuncion()==null?null:BizFuncion.findFuncion(getFuncion());
  	BizOperador op = func!=null&&!func.hasOperador()?null:(getOperador()==null?null:BizOperador.findOperador(this.pOperador.getValue()));
  	if (!hasNombreFiltro()) {
    	title += func!=null? (title.equals("")?"":" ")+func.getDescripcion():"";
    	title += (title.equals("")?"":" ")+getDescrCampo();
  	} else {
  		title = getNameForFilter();
  	}
  	title += op==null || op.getDescripcion().equals("=")? "": (title.equals("")?"":" ")+ op.getDescripcion();
  	if (op!=null && op.getCantValores()>0)
  		title += getValor()==null && op!=null&&op.getCantValores()>0? "":  (title.equals("")?"":" ")+ ( getValor().length()>10 ? getValor().substring(0, 9)+"..." : getValor());
   	if (op!=null && op.getCantValores()>1)
   		title += getValor2()==null && op!=null&&op.getCantValores()>1? "":  (title.equals("")?"":" ")+ ( getValor2().length()>10 ? getValor2().substring(0, 9)+"..." : getValor2());
   return title;
  }

  public String getBeautyDescrCampo() throws Exception {
		String s = "";
		if (this.isPorcentaje())  s+= "Porcentaje de ";
		if (this.hasFunction()) s+= BizCampo.getFunctionAllMap().getElement(this.getFuncion())+" de ";
  	s+=findDescrCompleta();
		if (!this.pOperador.isEmpty()) s+=" "+BizOperador.findOperador(this.pOperador.getValue()).getDescripcion();
		if (!this.pValor.isEmpty()) s+=" "+pValor.getValue();
		if (!this.pValor2.isEmpty()) s+="-"+pValor2.getValue();
//  	String n=g.getObjRelacion().getBeautyDescrCampo();
//  	s+=(n.equals("")?"":" en ")+n;
		return JLanguage.translate(s);
	}
  public String findDescrCompleta() throws Exception {
  	BizCampoGallery c = (BizCampoGallery) this.getCamposGallery().findInHash("id",""+this.getIdGallery()); 
	  if (c==null) return "";
  	return c.getDescrCompleta();
  }

	public String getDescrRecordSet() throws Exception {
		return this.getObjRelation().getDescription();
	}
	
	public boolean isSimilar(BizCampo other) throws Exception {
		if (other==null) return false;
		if (!getSerialDeep().equals(other.getSerialDeep())) return false;
		if (!getCampo().equals(other.getCampo())) return false;
		if (getFuncion()==null && other.getFuncion()==null ) return true;
		if (getFuncion()==null && other.getFuncion()!=null) return false;
		if (getFuncion()!=null && other.getFuncion()==null) return false;
		if (!getFuncion().equals(other.getFuncion())) return false;
		if (isPorcentaje() != other.isPorcentaje()) return false;
		return true;
	}

	public void processInsert() throws Exception {

		if (pOrden.isNull()) {
			if (pSecuencia.isNotNull()) {
				BizCampo max = new BizCampo();
				max.addFilter("company", this.pCompany.getValue());
				max.addFilter("list_id", this.pListId.getValue());
				pOrden.setValue(max.SelectMaxLong("orden") + 1);
			} else {
				pOrden.setValue(getObjCustomList().getObjAllCampos().sizeStaticElements()+1);
			}
			
		}
	
		if (pOrdenOrden.isNull()) {
			top();
		}
		if (pOperacion.isNull()|| pOperacion.equals(""))
			pOperacion.setValue(OPER_AND);
		
//		if (pMaxSize.isNull())
//			pMaxSize.setValue(10);

  	if (pValor.isNull())
  		this.pValor.setValue(this.findValue());

  	if (pValor2.isNull())
  		this.pValor2.setValue(this.findValue2());
		
		if (this.isCampoFormula()) {
			//pCampo.setValue(pFormatParam.getValue());
			pFuncion.setValue(BizCampo.FUNTION_FORMULA);
		} else {
			if (pIdGallery.isNotNull() && pIdGallery.getValue()!=0)
				this.assignFieldsByIdGallery();
		
		}
		if (pNegado.isNull())
			pNegado.setValue(TIPO_TRUE);
		
		
		if (this.isCampoCantidad()) 
			this.pFuncion.setValue(BizField.FUNTION_COUNT);

		if (pColorBottomBackground.isNull())
			pColorBottomBackground.setValue("00AA00");
		if (pMarcaMayores.isNull())
			pMarcaMayores.setValue(false);
		if (pMarcaBottom10.isNull())
			pMarcaBottom10.setValue(false);
		if (pMarcaMenores.isNull())
			pMarcaMenores.setValue(false);
		if (pMarcaTop10.isNull())
			pMarcaTop10.setValue(false);
		if (pVisible.isNull())
			pVisible.setValue(true);
		if (pColorTopBackground.isNull())
			pColorTopBackground.setValue("FF0000");
	 	if (isGroup()) {
  		BizCampo parent = getObjFiltroParent();
  		if (parent!=null ) {
  			this.setOrdenPadre(""+parent.getOrden());
  			if (parent.pOperacion.getValue().equals(OPER_OR)) pOperacion.setValue(OPER_AND_INTERNO);
  			else if (parent.pOperacion.getValue().equals(OPER_AND_INTERNO)) pOperacion.setValue(OPER_OR);
  		}
 	} else {
		BizCampo parent = getObjFiltroParent();
		if (parent!=null ) {
			this.setOrdenPadre(""+parent.getOrden());
		}
	}

	}
	public void processInsertTable() throws Exception {
		if (pRecordOwner.isNull()&&!isGroup()) return;//ignore
		if (pOperacion.isNull()|| pOperacion.equals(""))
			pOperacion.setValue(OPER_AND);
		if (pOrden.isNull()) {
			if (pSecuencia.isNotNull()) {
				BizCampo max = new BizCampo();
				max.addFilter("company", this.pCompany.getValue());
				max.addFilter("list_id", this.pListId.getValue());
				pOrden.setValue(max.SelectMaxLong("orden") + 1);
			} else {
				pOrden.setValue(getObjCustomList().getObjAllCampos().sizeStaticElements()+1);
			}
			
		}
		
	 	if (isGroup()) {
  		BizCampo parent = getObjFiltroParent();
  		if (parent!=null ) {
  			if (parent.getOrden()==0) {
  				  parent.copyKeysProperties(getObjCustomList());
  					parent.processInsertTable();
  			}
  			this.setOrdenPadre(""+parent.getOrden());
  			if (parent.pOperacion.getValue().equals(OPER_OR)) this.pOperacion.setValue(OPER_AND_INTERNO);
  			else if (parent.pOperacion.getValue().equals(OPER_AND_INTERNO)) pOperacion.setValue(OPER_OR);
  		}
	 	} else {
			BizCampo parent = getObjFiltroParent();
			if (parent!=null ) {
				if (parent.getOrden()==0) {
				  parent.copyKeysProperties(getObjCustomList());
					parent.processInsertTable();
				}
				this.setOrdenPadre(""+parent.getOrden());
			}
		}
		if (hasOrdenAscDesc()) {
			top();
		}

//		pValor.setValue(findValue());
//		pValor2.setValue(findValue2());
		super.processInsertTable();
		pSecuencia.setValue(getIdentity("secuencia"));
		try {
			processCalculeStadistic();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clean();
	}

	public void processCalculeStadistic() throws Exception {
		if (isNumeric()) return;
		if (isCheckInput()) return;
		if (isDateInput()) return;
		if (isDateTimeInput()) return;
		if (isCampoCantidad()) return;
		if (!hasCampo()) return;
		if (getFixedProp().isVirtual()) return;
		if (!(getOperador().equals("in")||getOperador().equals(""))) return;
		if (getFuncion().equals(FUNTION_NULO)||getFuncion().equals(FUNTION_NONULO)) return;
		fillStadistic();
	}

	public boolean hasStadistic() throws Exception {
		if (!(getOperador().equals("in")||getOperador().equals(""))) return false;
		JRecords<BizStadistic> recs = new JRecords<BizStadistic>(BizStadistic.class);
		recs.addFilter("company", getCompany());
		recs.addFilter("list_id", getListId());
		recs.addFilter("secuencia", getSecuencia());
		return recs.selectCount()>0;
	}
	public JControlWin getOptions() throws Exception {
		return new JControlWin() {
			@Override
			public JWins getRecords(boolean bOneRow) throws Exception {
				GuiStadistics ss = new GuiStadistics();
				ss.getRecords().addFilter("id", getSecuencia());
				return ss;
			}
		};
	}
	public JControlCombo getOptionsCombo() throws Exception {
		return new JControlCombo() {
			@Override
			public JWins getRecords(boolean bOneRow) throws Exception {
				GuiStadistics ss = new GuiStadistics();
				ss.getRecords().addFilter("secuencia", getSecuencia());
				return ss;
			}
		};
	}
	public void cleanStadistic() throws Exception {
		BizStadistic stdelete = new BizStadistic();
		stdelete.addFilter("company", getCompany());
		stdelete.addFilter("list_id", getListId());
		stdelete.addFilter("secuencia", getSecuencia());
		stdelete.deleteMultiple();
	}
	public void fillStadistic() throws Exception {
		cleanStadistic();
		String sql = "";
		try {
			this.getNameField();
		} catch (Exception e) {
			PssLogger.logError(e);
		}
		String camposql=getObjCustomList().getSQL(this);
		if (camposql==null) return;
		if (camposql.indexOf(getNameField())==-1) return;
		sql+=" select "+getNameField()+" as descripcion,count(*) as valor, 1 as icono ,'' as icono_string";
		sql+=" from ( "+ getObjCustomList().getSQL(this)+" ) as s ";
		sql+=" group by "+getNameField();
		sql+=" order by valor desc ";
		JRecords<BizVirtual> count= new JRecords<BizVirtual>(BizVirtual.class);
		count.SetSQL(sql);
		long c = count.selectCount();
		if (c>500) return;
		JRecords<BizVirtual> virt= new JRecords<BizVirtual>(BizVirtual.class);
		virt.SetSQL(sql);
	 	JIterator<BizVirtual> it = virt.getStaticIterator();
	 	while (it.hasMoreElements()) {
	 		BizVirtual pnr = it.nextElement();
	 		BizStadistic st = new BizStadistic();
	 		st.setCompany(getCompany());
	 		st.setListId(getListId());
	 		st.setSecuencia(getSecuencia());
	 		st.setCampo(pnr.getDescrip());
	 		st.setCantidad(Long.parseLong(pnr.getValor()));
	 		st.processInsert();
	 	}
	} 
	
	public void assignFieldsByIdGallery() throws Exception {
		JRecords<BizCampoGallery> galls = getObjCustomList().getAllCampos("", true);
		
		BizCampoGallery gal = (BizCampoGallery)galls.findInHash("id",""+pIdGallery.getRawValue());
		this.setCampo(gal.getCampo());
		this.pRelId.setValue(gal.getRelId());
		this.pFuncion.setValue(gal.getFunction());
		this.pSerialDeep.setValue(gal.getSerialDeep());
		this.pRecordOwner.setValue(gal.getRecordOwner());
		this.pRecordSource.setValue(gal.getRecordSource());

	}
	
	
	public String serializeDeep() throws Exception {
		JRelation rel = this.getObjRelation();
		if (rel==null) return "";
		return rel.serializeDeep();
	}
  public boolean isOperIN() throws Exception {
  	if (!this.hasRelId()) return false;
  	return this.getObjRelation().isRelationChild();
  }

  public boolean isOperOR() throws Exception {
  	return this.pOperacion.getValue().equals(OPER_OR);
  }
  public boolean isOperAndInterno() throws Exception {
  	return this.pOperacion.getValue().equals(OPER_AND_INTERNO);
  }

  public boolean isOperAND() throws Exception {
  	return this.pOperacion.getValue().equals(OPER_AND)||this.pOperacion.isNull();
  }
  
	public boolean isGroup() throws Exception {
		return isOperOR() || isOperAndInterno();
	}
	@Override
	public void processDelete() throws Exception {
		if (getObjCustomList()==null)
			return;
		getObjCustomList().removeCampo(this);
	}
	
	@Override
	public void processDeleteTable() throws Exception {
		super.processDeleteTable();
	}
  public void execProcessDeleteTable() throws Exception {
  	JExec exec = new JExec(null,null) {
  		public void Do() throws Exception {
  			processDeleteTable();
  		}
  	};
  	exec.execute();
  }


	@Override
	public void processUpdate() throws Exception {
		if (pValor.isNull())
			this.pValor.setValue(this.findValue());

		if (pValor2.isNull())
			this.pValor2.setValue(this.findValue2());
		
//		if (pMaxSize.isNull())
//			pMaxSize.setValue(10);

		if (pOperacion.isNull()||pOperacion.equals(""))
			pOperacion.setValue(OPER_AND);
		if (pOrdenOrden.isNull() && hasOrdenAscDesc()) {
 			top();
		}
	 	if (isGroup()) {
  		BizCampo parent = getObjFiltroParent();
  		if (parent!=null ) {
  			this.setOrdenPadre(""+parent.getOrden());
  			if (parent.pOperacion.getValue().equals(OPER_OR)) pOperacion.setValue(OPER_AND_INTERNO);
  			else if (parent.pOperacion.getValue().equals(OPER_AND_INTERNO)) pOperacion.setValue(OPER_OR);
  		}
 	} else {
		BizCampo parent = getObjFiltroParent();
		if (parent!=null ) {
			this.setOrdenPadre(""+parent.getOrden());
		}
	}

  			
	}
	public void processUpdateTable() throws Exception {
//		if (pMaxSize.isNull())
//			pMaxSize.setValue(10);
		if (pOrdenOrden.isNull() && hasOrdenAscDesc()) {
 			top();
		}

		if (pOperacion.isNull()||pOperacion.equals(""))
			this.pOperacion.setValue(OPER_AND);

	 	if (isGroup()) {
  		BizCampo parent = getObjFiltroParent();
  		if (parent!=null ) {
  			this.setOrdenPadre(""+parent.getOrden());
  			if (parent.pOperacion.getValue().equals(OPER_OR)) pOperacion.setValue(OPER_AND_INTERNO);
  			else if (parent.pOperacion.getValue().equals(OPER_AND_INTERNO)) pOperacion.setValue(OPER_OR);
  		}
 	} else {
		BizCampo parent = getObjFiltroParent();
		if (parent!=null ) {
			this.setOrdenPadre(""+parent.getOrden());
		}
	}

//		pValor.setValue(findValue());
//		pValor2.setValue(findValue2());

		super.processUpdateTable();
		clean();
		processCalculeStadistic();
//		reordenar();

	}
  public boolean isDependiente() throws Exception {
  	return !(this.pOrdenPadre.getValue().equals("0") || this.pOrdenPadre.getValue().equals("")) || parentGroup!=null;
  }
	BizCampo parentGroup;
  public void setObjFiltroParent(BizCampo parent) {
		this.parentGroup = parent;
	}
	public BizCampo getObjFiltroParent() throws Exception {
  	if (parentGroup!=null) return parentGroup;
  	if (!isDependiente()) return null;
  	
  	BizCampo r = new BizCampo();
		r.dontThrowException(true);
		if (!r.readByOrden(getCompany(), getListId(),Long.parseLong(getOrdenPadre()))) return null;
		return parentGroup=r;
	}
  

	public String findValue() throws Exception {
		if (this.isDateInput())
			return this.pValueDate.toString();
		if (this.isDateTimeInput())
			return this.pValueDate.toString();
		if (this.isLOVInput()) {
			if (this.pValueLOV.toString().equals(""))//correcion bug
				return this.pValueEdit.toString();
			return this.pValueLOV.toString();
		}
		if (this.isCheckInput())
			return this.pValueCheck.toString();
		return this.pValueEdit.toString();
	}

	public String findValue2() throws Exception {
		if (this.isDateInput())
			return this.pValueDate2.toString();
		if (this.isDateTimeInput())
			return this.pValueDate2.toString();
		if (this.isLOVInput()) {
			if (this.pValueLOV2.toString().equals(""))//correcion bug
				return this.pValueEdit2.toString();
			return this.pValueLOV2.toString();
		}
		if (this.isCheckInput())
			return this.pValueCheck2.toString();
		return this.pValueEdit2.toString();
	}

	public void execChangeOrientation(final long pos) throws Exception {
		JExec exec = new JExec(null, null) {
			public void Do() throws Exception {
				changeOrientation(pos);
			}
		};
		exec.execute();
	}
  
  public void changeOrientation(long pos) throws Exception {
  	this.setOrientacion(isFila()?"C":"F");
  	this.setOrden(pos);
  	this.update();
  	getObjCustomList().processRenumerar();
  }

	
  public boolean hasFunction() throws Exception {
		
		if (this.getObjCustomList().isLista()&&!BizFuncion.activeFunctionInLista(pFuncion.getValue())) {
				return false;
		}
		return this.pFuncion.isNotNull() || this.pPorcentaje.getValue() || this.getCampo().equals(FUNTION_FORMULA);
	}
	public boolean hasFunctionFormula() throws Exception {
		return this.pFuncion.isNotNull() || this.pPorcentaje.getValue();
	}
	public boolean hasFormat() throws Exception {
		return this.pFormat.isNotNull();
	}
	
//	public JRecord getSourceData() throws Exception {
//		return this.getObjCustomList().getObjRecordSet();
//	}
	
	public String getAlias() throws Exception {
		return this.getObjRelation().getAlias(); 
	}

public JProperty createFixedProp() throws Exception {
//	if (this.getFuncion().equals(BizFuncion.FUNTION_ULTIMOS)) return getExtraFilterProperty();
//	if (this.getFuncion().equals(BizFuncion.FUNTION_PROXIMOS)) return getExtraFilterProperty();
//	if (this.getOperador().equals(BizFuncion.FUNTION_INTERVALO)) return getExtraFilterProperty();
	if (this.isCampoFormula() ) return getExtraProperty(this.getNameField(),this.getDescrCampo());
	if (this.getFuncion().equals(BizCampo.FUNTION_COUNT)) return getExtraProperty(this.getNameField(),this.getDescrCampo());
	JProperty p = this.getObjRecordTarget().getFixedProp(this.getObjRecordTarget().getClass(),this.getCampo(),false);
	if (p==null) return null;
	JProperty p1 = new JProperty(p.getType(), this.getNameField(), p.GetDescripcion(), null, "", true, true, p.getSize(), p.GetPrecision(), null, null, null);
	return p1;
}

public JObject<?> createProp() throws Exception {
	if (this.isCampoFormula()) return new JString();
	if (this.getOperador().equals(BizFuncion.FUNTION_INTERVALO) && isDateInput() || isDateTimeInput() ) return new JString();
	if (this.getOperador().equals(BizFuncion.FUNTION_INTERVALO)) return new JString();
	if (this.getFuncion().equals(BizCampo.FUNTION_COUNT)) return new JLong();
	if (this.getFuncion().equals(BizFuncion.FUNTION_ULTIMOS)) return new JLong();
	if (this.getFuncion().equals(BizFuncion.FUNTION_PROXIMOS)) return new JLong();
	if (this.getFuncion().equals(BizFuncion.FUNTION_ANIO)) return new JLong();
	if (this.getFuncion().equals(BizFuncion.FUNTION_SEMESTRE)) return new JLong();
	if (this.getFuncion().equals(BizFuncion.FUNTION_CUATRIMESTRE)) return new JLong();
	if (this.getFuncion().equals(BizFuncion.FUNTION_TRIMESTRE)) return new JLong();
	if (this.getFuncion().equals(BizFuncion.FUNTION_BIMESTRE)) return new JLong();
	if (this.getFuncion().equals(BizFuncion.FUNTION_MES)) return new JLong();
	return this.getObjRecordTarget().getProp(this.getCampo());
}

public String findFunction(String table,String field) throws Exception {
	JBaseRegistro bd = JBaseRegistro.recordsetFactory();
	String variable="";
	String function = getFuncion();
	String campo = "";
	if (this.isCampoCantidad()) {
		function = BizCampo.FUNTION_COUNT;
		campo = "distinct "+ table+".*";
	} else
		campo = table+"."+ field;
	Date hoy = getObjCustomList().getHoy();

	if (function.equals(BizCampo.FUNTION_COUNT))
		variable= bd.fcount(/* ((getObjCustomList().isMatriz())?"distinct ":"")+ */campo);
	else if (function.equals(BizCampo.FUNTION_SUM))
		variable= bd.fsum(campo);
	else if (function.equals(BizCampo.FUNTION_MAX))
		variable= bd.fmax(campo);
	else if (function.equals(BizCampo.FUNTION_MIN))
		variable= bd.fmin(campo);
	else if (function.equals(BizCampo.FUNTION_AVR))
		variable= bd.favg(campo);
	else if (function.equals(BizCampo.FUNTION_MES))
		variable= JTools.LPad(bd.fmes(campo), 2, "0");
	else if (function.equals(BizCampo.FUNTION_ANIO))
		variable= bd.fanio(campo);
	else if (function.equals(BizCampo.FUNTION_AYER))
		variable= bd.fayer(campo,hoy);
	else if (function.equals(BizCampo.FUNTION_ANOMES))
		variable= bd.faniomes(campo);	
	else if (function.equals(BizCampo.FUNTION_ANOSEM))
			variable= bd.faniosem(campo);
	else if (function.equals(BizCampo.FUNTION_BIMESTRE))
		variable= bd.fbimestre(campo);
	else if (function.equals(BizCampo.FUNTION_TRIMESTRE))
		variable= bd.ftrimestre(campo);
	else if (function.equals(BizCampo.FUNTION_CUATRIMESTRE))
		variable= bd.fcuatrimestre(campo);
	else if (function.equals(BizCampo.FUNTION_SEMESTRE))
		variable= bd.fsemestre(campo);
	else if (function.equals(BizCampo.FUNTION_DIA_SEMANA))
		variable= bd.fdiasemana(campo);
	else if (function.equals(BizCampo.FUNTION_DIA_MES))
		variable= JTools.LPad(bd.fdiames(campo), 2, "0");
	else if (function.equals(BizCampo.FUNTION_DIA_ANO))
		variable= JTools.LPad(bd.fdiaano(campo), 3, "0");
	else variable = campo;
//	if (!isOver() && isPorcentaje()) {
//		if (sqlTotalize==null)
//			variable= bd.fporc(variable);
//		else
//			variable= bd.fporc(variable,sqlTotalize);
//	}
//	if (isOver()) {
//			variable= bd.fsumover(variable);
//	}
	return variable;
}
	public String getCampoFuncion() throws Exception {
		return this.pSerialDeep.getValue()+"|"+ getCampo() + "|"+ pFuncion.getValue();
	}
	public String getSerialCampo() throws Exception {
		return this.pSerialDeep.getValue()+"|"+ getCampo() ;
	}
	public long getIdGallery() throws Exception {
		if (getObjCustomList()==null) return 0;
		JRecords<BizCampoGallery> galls = getObjCustomList().getAllCampos("", true);
		String key = getCampoFuncion();

		BizCampoGallery gal = (BizCampoGallery)galls.findInHash("campo_function",key);
		if (gal==null) return 0;
		return gal.getId();
	}
	public String getTargetTableForFrom() throws Exception {
		return this.getObjRecordTarget().GetTable();
//		if (this.getObjRecordTarget().GetTableTemporal()==null)return this.getObjRecordTarget().GetTable();
//		return this.getObjRecordTarget().GetTableTemporal();
	}
	
	public String getNameField() throws Exception {
		return this.getNameField(this.getCampo());
	}
	public String getNameField(String field) throws Exception {
		if (isCampoFormula() && field.endsWith("FORMULA")) 
			return "FORMULA_"+JTools.getValidFilename(this.getDescrCampo().replace(" ", ""));
		String c = (this.getTargetAlias(field)==null?"":this.getTargetAlias(field)+"_")+field;
		c=JTools.replace(c, "." , "_");
		c=JTools.replace(c,"\"" , "");
		if (this.hasFunction())
			c= this.getFuncion()+"_"+c;
		if (this.isPorcentaje())
			c= "PORC_"+c;
		return c;
	}
	public String getNameField(String table,String field) throws Exception {
		String c = table+"_"+field;
		c=JTools.replace(c, "." , "_");

		c=c.replaceAll( "\"" , "");
		if (this.hasFunction())
			c= this.getFuncion()+"_"+c;
		if (this.hasOperador())
			c= BizOperador.findOperador(this.pOperador.getValue()).getDescripcion()+"_"+c;
		return c;
	}

	public String getNameFieldFormula(String field) throws Exception {
		if (isCampoFormula() && field.endsWith("FORMULA")) return "FORMULA";
		String c = (this.getTargetAlias()==null?"":this.getTargetAlias()+"_")+field;
		c=JTools.replace(c, "." , "_");
		c=c.replaceAll( "\"" , "");
		return c;
	}
	public String getGeoNameField(String table,String field) throws Exception {
		String c = table+"_"+field;
		c=JTools.replace(c, "." , "_");

		c=JTools.replace(c, "." , "_");
		c=c.replaceAll( "\"" , "");

		return c;
	}
	
	
  public JRecord getObjRecordTarget() throws Exception {
  	if (this.getObjRelation()==null) return null;
  	return this.getObjRelation().getObjRecordTarget();
  }
  
  
	public JRelation getRelationGeoOwner() throws Exception {
		return getObjRecordTarget().getRelationMap().findRel(""+getObjRecordTarget().getRelationMap().getRelationGeoField(getCampo()));
	}
  public JRecord getObjRecordOwner() throws Exception {
  	if (this.recordOwner!=null) return this.recordOwner;
  	return (this.recordOwner=(JRecord)Class.forName(this.pRecordOwner.getValue()).newInstance());
  }
  public JRecord getObjRecordGeoOwner() throws Exception {
  	if (this.recordGeoOwner!=null) return this.recordGeoOwner;
    JRelation rel= getRelationGeoOwner();
  	if (rel==null) return getObjRecordOwner();

  	return rel.getObjRecordTarget();
  }
  public JRecord getObjRecordSource() throws Exception {
  	if (this.recordSource!=null) return this.recordSource;
  	if (pRecordSource.isNull()) { //por compatibilidad
    	return (this.recordSource=getObjRecordOwner());
  		
  	}
  	return (this.recordSource=(JRecord)Class.forName(this.pRecordSource.getValue()).newInstance());
  }

  public void setObjRelation(JRelation value) throws Exception {
  	this.relation=value;
  }
  public JRelation getObjRelation() throws Exception {
  	if (this.relation!=null) return this.relation;
  	if (this.pRelId.isNull()) return null;
  	JRelation rel = this.getObjRecordSource().getRelationMap().findRel(this.pRelId.getValue());
  	if (rel==null) 
  		rel =getObjCustomList().getObjRecordOwner().getRelationMap().findRel(this.pRelId.getValue());
  	rel.setObjRelParent(this.createRelParent(this.getSerialDeep()));
  	return (this.relation=rel);
  }
  BizCampo objOrden;

  public BizCampo getObjCampoOrden() throws Exception {
  	if (hasRanking()) {
  		BizCampo rank = getObjCustomList().findCampo(getCampoOrden());
  		return this.objOrden=rank;
  	}
//  	if (hasOrdenAscDesc()) 
  		return null;
//  	if (!this.isSimilar(getObjCustomList().getLastFila())) return null;
//  	BizCampos orden = getObjCustomList().getObjOrders();
//  	orden.firstRecord();
//  	if (!orden.nextRecord()) return null;
//  	return (this.objOrden=orden.getRecord());
  }

  public JRelation getObjGeoRelation() throws Exception {
  	JRelation rel = this.getRelationGeoOwner();
  	rel.setObjRelParent(this.createRelParent(this.getSerialDeep()));
  	return rel;
  }
  
  
  public void execSubir(final long pos) throws Exception {
  	JExec exec = new JExec(null,null) {
  		public void Do() throws Exception {
  			subir(pos);
  		}
  	};
  	exec.execute();
  }
  
  public void subir(long pos) throws Exception {
//  	if (!hasSecuencia()) {
  		getObjCustomList().processReordenar(this,pos,true);
//  		return;
//  	}
//  	BizCampo max = new BizCampo();
//  	long orden = pos;
//  	if (pos==-1) {
//	  	max.addFilter("company", this.getCompany());
//	  	max.addFilter("list_id", this.getListId());
//	  	max.addFilter("orden", this.pOrden.getValue(), "<");
//	  	orden = max.SelectMaxLong("orden");
//  	}
//  	max = new BizCampo();
//  	max.addFilter("company", this.getCompany());
//  	max.addFilter("list_id", this.getListId());
//  	max.addFilter("orden", orden);
//  	max.dontThrowException(true);
//  	if (!max.read()) return;
//  	max.pOrden.setValue(this.pOrden.getValue());
//  	this.pOrden.setValue(orden);
//  	max.update();
//  	this.update();
  }

  public void execBajar(final long pos) throws Exception {
  	JExec exec = new JExec(null,null) {
  		public void Do() throws Exception {
  			bajar(pos);
  		}
  	};
  	exec.execute();
  }
  public void execTop() throws Exception {
  	JExec exec = new JExec(null,null) {
  		public void Do() throws Exception {
  			top();
  		}
  	};
  	exec.execute();
  }

	public void execDuplicar() throws Exception {
		JExec exec = new JExec(null,null) {
			public void Do() throws Exception {
				duplicar();
			}
		};
		exec.execute();
	}

  
  public void top() throws Exception {
		getObjCustomList().updateOrderToTop(this);
  }
  public void duplicar() throws Exception {
  	BizCampo campo = (BizCampo) this.getClon(this.getCompany(),this.getListId());
  	campo.setNullToSecuencia();
  	
		getObjCustomList().addCampo(campo);
  }
  public void bajar(long pos) throws Exception {
//  	if (!hasSecuencia()) {
  		getObjCustomList().processReordenar(this,pos,false);
//  		return;
//  	}
//  	BizCampo min = new BizCampo();
//  	long orden = pos;
//  	if (pos==-1) {
//	  	min.addFilter("company", this.getCompany());
//	  	min.addFilter("list_id", this.getListId());
//	  	min.addFilter("orden", this.pOrden.getValue(), ">");
//	  	orden = min.SelectMinLong("orden");
//  	}
//  	min = new BizCampo();
//  	min.addFilter("company", this.getCompany());
//  	min.addFilter("list_id", this.getListId());
//  	min.addFilter("orden", orden);
//  	min.dontThrowException(true);
//  	if (!min.read()) return;
//  	min.pOrden.setValue(this.pOrden.getValue());
//  	this.pOrden.setValue(orden);
//  	min.update();
//  	this.update();
  }
  public void execOcultar() throws Exception {
  	JExec exec = new JExec(null,null) {
  		public void Do() throws Exception {
  			proceseOcultar(true);
  		}
  	};
  	exec.execute();
  }
  public void execMostrar() throws Exception {
  	JExec exec = new JExec(null,null) {
  		public void Do() throws Exception {
  			proceseOcultar(false);
  		}
  	};
  	exec.execute();
  }
  public void proceseOcultar(boolean ocultar) throws Exception {
  	pHideReport.setValue(ocultar);
  	processUpdate();
  }
  
  public boolean hasCampo() throws Exception {
  	return this.pCampo.isNotNull();
  }
  public String getCampoFromFiltro() throws Exception {
		if (this.hasFunction()) return this.findFunction(true);
		if (this.hasOperador()) return this.findFunction(true);
		return this.getCampo();
//		if (this.getObjRelation().hasAlias()) 
//			return this.getObjRelation().getAlias()+"."+this.getCampo();  
//		return this.getCampo();
  }  
  /* (non-Javadoc)
	 * @see pss.common.customList.config.ICampo#prepareField(pss.core.services.records.JRecords)
	 */

//	public String getRankNameField() throws Exception {
//		return "rank_"+this.getNameField();
//	}
//  public void prepareRanking(JBaseRecord r) throws Exception {
//  	BizCampo orden = this.getObjCampoOrden();
//  	if (orden==null) return;
//  	r.addField("dense_rank() over ( "+buildOrderByTo(r)+" order by "+orden.findFunction(orden.getTargetAlias(),orden.getCampo())+" ASC)" +" as "+ orden.getRankNameField()).setTabla(null);
//	
//  }
  public String buildOrderByTo(JBaseRecord r) throws Exception {
  	String out ="";
  	JIterator<BizCampo> it = getObjCustomList().getObjFilas().getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (c.isSimilar(this)) break;
  		out += (out.equals("")?"":",")+ 	c.findFunction(c.getTargetAlias(),c.getCampo());
  	}
  	return (out.equals("")?"":" partition by "+out+" " );
  }
  public void prepareDiferencia(JBaseRecord r) throws Exception {
		JList<BizCampo> campos = getObjCampoDiferencia();
		if (campos==null || campos.isEmpty()) return;
		JIterator<BizCampo> it = campos.getIterator();
		while (it.hasMoreElements()) {
			BizField campo = it.nextElement();
			String filas = "";
			JIterator<BizCampo> itd = getObjCustomList().getObjFilas().getStaticIterator();
			while (itd.hasMoreElements()) {
				BizCampo eje = itd.nextElement();
				if (!eje.isRolFila()) continue;
				filas += (filas.equals("")?"":",")+eje.getField();
			}
			if (!filas.equals("")) filas="PARTITION BY "+filas;
			if (hasOperador()) {
				r.addField( this.findFunction(true)+" as xdiffcol_"+ getNameField()+"_"+campo.getCampo()).setTabla(null);
				r.addField( "("+campo.findFunction(true)+")-lag("+campo.findFunction(true)+") over("+filas+" ORDER BY ("+findFunction(true)+")  RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING) as "+ getNameFieldDif(campo.getCampo())).setTabla(null);
				if (getPorcentajeDiferencia())
					r.addField( "case when (lag("+campo.findFunction(true)+") over("+filas+" ORDER BY ("+findFunction(true)+")  RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING))=0 then 0 else ((("+campo.findFunction(true)+")-(lag("+campo.findFunction(true)+") over("+filas+" ORDER BY ("+this.findFunction(true)+")  RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING)))*100)/(lag("+campo.findFunction(true)+") over("+filas+" ORDER BY ("+this.findFunction(true)+")  RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING)) end as "+ getNameFieldDifPorc(campo.getCampo())).setTabla(null);
			} else {
				r.addField( this.getField()+" as xdiffcol_"+ getNameField()+"_"+campo.getCampo()).setTabla(null);
				r.addField( campo.getField()+"-lag("+campo.getField()+") over("+filas+" ORDER BY ("+this.getField()+")  RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING) as "+ getNameFieldDif(campo.getCampo())).setTabla(null);
				if (getPorcentajeDiferencia())
					r.addField( "case when (lag("+campo.getField()+") over("+filas+" ORDER BY ("+this.getField()+")  RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING))=0 then 0 else (("+campo.getField()+"-(lag("+campo.getField()+") over("+filas+" ORDER BY ("+this.getField()+")  RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING)))*100)/(lag("+campo.getField()+") over("+filas+" ORDER BY ("+this.getField()+")  RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING)) end as "+ getNameFieldDifPorc(campo.getCampo())).setTabla(null);
			}
		}
	

  }
  public String getNameColFieldDif(String name) throws Exception {
   	return "xdiffcol_"+ getNameField()+"_"+name;
  	
  }
  public String getNameFieldDif(String name) throws Exception {
  	return "xdiffval_"+ getNameField()+"_"+name;
  	
  }
  public String getNameFieldDifPorc(String name) throws Exception {
  	return "xdiffprc_"+ getNameField()+"_"+name;
  	
  }
  
  JList<BizCampo> objDifCampos;
  public JList<BizCampo> getObjCampoDiferencia() throws Exception {
  	if (pCampoDiferencia.isNull()) return null;
 // 	if (objDifCampos!=null) return objDifCampos;
  	JList<BizCampo> campos = JCollectionFactory.createList();
  	JList<String> list = getCampoDiferencia();
  	JIterator<String> it =list.getIterator();
  	while (it.hasMoreElements()) {
  		campos.addElement(getObjCustomList().findCampo(it.nextElement()));
  	}
  	return objDifCampos=campos;
  }
  
  public BizCampo findFieldDiferencia(String campo) throws Exception {
  	 JList<BizCampo> lista= getObjCampoDiferencia();
  	 JIterator<BizCampo> it = lista.getIterator();
  	 while (it.hasMoreElements()) {
  		 BizCampo c = it.nextElement();
  		 if (c.getNameField().equals(campo)) {
  			 return c;
  		 }
  	 }
  	 return null;
  }
  public String getField() throws Exception {
  	if (this.hasFunction()) {
  		return this.findFunction(true);
		} 

  	JProperty p = this.getFixedProp();
		String table = this.getTargetAlias();
  	String formatField = this.getFieldWithFormat(table, p.GetCampo());
  	return formatField;
  	
  }

  public void prepareEje(JBaseRecord r) throws Exception {
		if (this.isCampoFormula() && !this.isCampoFormulaConFuncion()) {
			r.addFuntion(this.findFunction(true) + " as " + this.getNameField());
			r.addGroupBy(null, this.findFunction(false));
			r.addOrderBy(null, this.findFunction(true), BizCampo.ORDER_ASC);
			return;
		}
		if (this.hasFunction()) {
			r.addFuntion(this.findFunction(true) + " as " + this.getNameField());
			r.addGroupBy(null, this.getFuncion().equals("MIN")||this.getFuncion().equals("MAX")?(this.getTargetAlias()==null?"":this.getTargetAlias()+".")+this.getCampo():this.findFunction(false));
			r.addOrderBy(null, this.findFunction(true), BizCampo.ORDER_ASC);
			return;
		}
		if (this.hasOperador()) {
			r.addFuntion(this.findFunction(true) + " as " + this.getNameField());
			r.addGroupBy(null, this.findFunction(false));
			r.addOrderBy(null, this.findFunction(true), BizCampo.ORDER_ASC);
			return;
		}

		JProperty p = this.getFixedProp();
		if (p==null) return;
		
		if (p.isTableBased()) {
			this.attachField(r, p.GetCampo());
			return;
		}
//		p.setDepediente(getObjRecordTarget().getKeyList());
		if(!p.hasDependencias()) p.setDependiente(this.getObjRecordTarget().getKeyList());
		JStringTokenizer t = JCollectionFactory.createStringTokenizer(p.getDependiente(), ';');
		while (t.hasMoreTokens()) {
			this.attachField(r, t.nextToken());
		}
		
  }
  public void prepareField(JBaseRecord r) throws Exception {
  	prepareField(r, null,false);
  }  
  

  public void prepareField(JBaseRecord r,String sqlTotalize,boolean totalizar) throws Exception {
		if (this.isCampoFormula()) {
			if (!this.isCampoFormulaConFuncion()) {
				r.addFuntion(this.findFunction(true,sqlTotalize,totalizar)+" as "+this.getNameField());
				if (!getObjCustomList().isLista()) {
					r.addGroupBy(null, this.findFunction(false));
					r.addOrderBy(null, this.findFunction(true), BizCampo.ORDER_ASC);
				}
			} else {
				r.addFuntion(this.findFunction(true,sqlTotalize,totalizar)+" as "+this.getNameField());
			}
			return;
		}

		if (isCampoCantidad()) {
			if (this.hasFunction()) {
				r.addFuntion(this.findFunction(true,sqlTotalize,totalizar)+" as "+this.getNameField());
				if (this.getObjCustomList().isAgrupado() && functionReguireGroupBy()) {
					r.addGroupBy(null, this.findFunction(false));
					r.addOrderBy(null, this.findFunction(true), BizCampo.ORDER_ASC);
				}

				return;
			} 
			
		}
		
		JProperty p = this.getFixedProp();
		if (p.isTableBased()) {
			if (this.hasFunction()) {
				r.addFuntion(this.findFunction(true,sqlTotalize,totalizar)+" as "+this.getNameField());
				if (this.getObjCustomList().isAgrupado() && functionReguireGroupBy()) {
					r.addGroupBy(null, this.findFunction(false));
					r.addOrderBy(null, this.findFunction(true), BizCampo.ORDER_ASC);
				}

				return;
			} 

			this.attachField(r, p.getCampoRaiz());
			//this.attachForeignFields(r);
			return;
		}

		if(!p.hasDependencias()) p.setDependiente(this.getObjRecordTarget().getKeyList());
		JStringTokenizer t = JCollectionFactory.createStringTokenizer(p.getDependiente(), ';');
		while (t.hasMoreTokens()) {
			this.attachField(r, t.nextToken());
		}
		
  }

  public void prepareGeoField(JBaseRecord r) throws Exception {


		JProperty p = this.getGeoFixedProp();
		if (p.isTableBased()&&!p.isVirtual()) {
			this.attachGeoField(r, this.getGeoCampo());
			//this.attachForeignFields(r);
			return;
		}

		if(!p.hasDependencias()) p.setDependiente(this.getObjRecordGeoOwner().getKeyList());
		JStringTokenizer t = JCollectionFactory.createStringTokenizer(p.getDependiente(), ';');
		while (t.hasMoreTokens()) {
			this.attachField(r, t.nextToken());
		}
		
  }
  public JProperty getFixedProp() throws Exception {
  	if (this.isCampoFormula() ) return getExtraProperty(this.getNameField(),this.getDescrCampo());
  	return this.getObjRecordTarget().getFixedProp(this.getCampo());
  }
  public JProperty getGeoFixedProp() throws Exception {
  	JRecord target = this.getObjRecordGeoOwner();
  	if (target==null) return null;
  	return target.getFixedProp(this.getGeoCampo());
  }
  private JObject getProp() throws Exception {
  	return this.getObjRecordTarget().getProp(this.getCampo());
  }
  
  private String formatString(String table, String field) throws Exception {
  	JStringTokenizer t = JCollectionFactory.createStringTokenizer(this.pFormatParam.getValue(), ',');
  	if (t.countTokens()!=2) JExcepcion.SendError("Formato de String invalido");
  	int desde = Integer.parseInt(t.nextToken());
  	int hasta = Integer.parseInt(t.nextToken());
  	JBaseRegistro rset = JBaseRegistro.recordsetFactory();
  	return rset.fsubstring(table+"."+field, desde, hasta);
  }
  
  private String formatDate(String table, String field) throws Exception {
  	JBaseRegistro rset = JBaseRegistro.recordsetFactory();
  	String format="";
  	boolean asString=false;
  	if (this.getProp() instanceof JDate) {
  		format = ((JDate)this.getProp()).getDbFormat();
  		asString= ((JDate)this.getProp()).savedAsString();
  	}
  	if (this.getProp() instanceof JDateTime) {
  		format = ((JDateTime)this.getProp()).getDbFormat();
  		asString= ((JDateTime)this.getProp()).savedAsString();
  	}  	
  	if (asString)
  		field = rset.ftoDate(table+"."+field, format);
  	else 
  		field = table+"."+field;
  	return rset.ftoChar(field, this.pFormatParam.getValue());
  }
  
  
  public String getFieldWithFormat(String table, String field) throws Exception {
  	if (!this.getObjCustomList().isAgrupado()) return (table!=null?table+".":"")+field; // el formato lo hago luego
  	if (this.isCampoCantidad()) return (table!=null?table+".":"")+field;
  	JProperty prop = this.getFixedProp();
  	if (prop.isVirtual() && this.hasFormat()) 
  		JExcepcion.SendError("Formato sobre campo virtual agrupado, no soportado");
  	if (this.pFormat.getValue().equals(FORMAT_STR))
  		return this.formatString(table, field);
  	if (this.pFormat.getValue().equals(FORMAT_DATE))
  		return this.formatDate(table, field);
//  	if (this.pFormat.getValue()==FORMAT_NUM)
//  		return this.formatNum(field);
  	return (table!=null?table+".":"")+field;
  }
  
  public void attachField(JBaseRecord r, String field) throws Exception {
  	String table = this.getTargetAlias();
  	String formatField = this.getFieldWithFormat(table, field);
//  	String ffield = formatField + " as " + this.getSourceCampo(); 
//  	String gfield = formatField!=null ? formatField : field;
//  	String ftable = formatField!=null ? null : this.getTargetTable();
  	r.addField(formatField +" as "+ this.getNameField(field)).setTabla(null);
		if (this.getObjCustomList().isLista()) return;
		r.addGroupBy(null, formatField);
		r.addOrderBy(null, formatField, BizCampo.ORDER_ASC);
  }
  public void attachGeoField(JBaseRecord r, String field) throws Exception {
  	String table = this.getObjGeoRelation().getTargetAlias(getGeoCampo());
  	if (table.equals("")) 
  		table=getTargetAlias();
  	
  	String formatField = this.getFieldWithFormat(table, field);
//  	String ffield = formatField + " as " + this.getSourceCampo(); 
//  	String gfield = formatField!=null ? formatField : field;
//  	String ftable = formatField!=null ? null : this.getTargetTable();
		if (this.getObjCustomList().isLista()) 
			r.addField(formatField +" as "+ this.getGeoNameField(table,field)).setTabla(null);
		else
			r.addField("MAX("+formatField +") as "+ this.getGeoNameField(table,field)).setTabla(null);
//  	r.addField(formatField +" as "+ this.getGeoNameField(table,field)).setTabla(null);
//		if (this.getObjCustomList().isLista()) return;
//		r.addGroupBy(null, formatField);
//		r.addOrderBy(null, formatField, BizOrder.ORDER_ASC);
  }


  private void attachForeignFields(JBaseRecord r) throws Exception {
  	if (!this.getObjCustomList().isAgrupado()) return;
  	// se agrega las claves de las tablas foreaneas en los agrupados para poder acceder a los detalles
		if (!this.getObjRelation().isRelationParent()) return;
		if (this.getObjRelation().getClassTarget().getName().equals(this.getObjCustomList().getObjRelation().getClassTarget().getName())) 
			return;
//		String foreignTable = this.getObjRecordTarget().GetTable();
		JIterator<JPair> it = this.getObjRelation().getJoins().getIterator();
		while (it.hasMoreElements()) {
			JPair p = it.nextElement();
			String foreignField = (String)p.secondObject();
			r.addField(foreignField).setTabla(null);
			r.addGroupBy(null, foreignField);
			r.addOrderBy(null, foreignField, "desc");
		}
  }

  public void reordenar() throws Exception {
//  	int i=1;
//  	JRecords<BizCampo> recs = getObjCustomList().getObjCampoos();
//  	JIterator<BizCampo> it=recs.getStaticIterator();
//  	while (it.hasMoreElements()) {
//  		BizCampo campo = it.nextElement();
//  		campo.setOrden(i++);
//  		campo.update();
//  		
//  	}
  }


  @Override
  public String getSerialDeep() throws Exception {
  	return this.pSerialDeep.getValue();
  }

  public JRecords<BizCampoGallery> getCamposGallery() throws Exception {
   	return getObjCustomList().getAllCampos("", true);
  }	
  
	public String getClassWinsTarget() throws Exception {
		JRelation rel = this.getObjRelationEje();
		if (rel==null) return null;
		return rel.getObjRecordTarget().getRelationMap().getSourceWinsClass();
	}
  public JRelation getObjRelationEje() throws Exception {
  	JRelations rels = this.getObjRelation().getObjRecordTarget().getRelationMap();
  	JRelation rel = rels.findRelTargetByField(this.getCampo());
  	if (rel==null) rel=rels.findRelByFieldKey(this.getCampo());
  	return rel;
  }
	public JMap<String, String> getMapTarget() throws Exception {
		JRelation rel = this.getObjRelationEje();
		if (rel==null) return null;
		return rel.getMapTarget();
	}
	
	public String getTipoCampo() throws Exception {
		if (isCampoFormula()) return JString.class.getSimpleName();
		if (isCampoCantidad()) return JFloat.class.getSimpleName();
		if (getProp()==null) return"";
	  return getProp().getClass().getSimpleName();
	}

	public IItem getClon(String company,long listId) throws Exception {
		BizCampo campo = new BizCampo();
		campo.copyProperties(this);
		campo.setCompany(company);
		campo.setListId(listId);
		campo.pSecuencia.setNull();
		campo.pIdGallery.setNull();
		campo.processInsert();
		return campo;
	}

	 public boolean isDateInput() throws Exception {
  		if (pCampo.getValue().equals("")) return false;
	  	if (isCampoFormula()) return false;
	  	if (getCampo().equals("COUNT")) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_ANIO)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_MES)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_BIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_TRIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_CUATRIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_SEMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_FUTURO)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_PASADO)) return false;

	  	if (getFuncion().equals(BizFuncion.FUNTION_PROXIMOS)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_ULTIMOS)) return false;
	  	JObject obj = this.getObjRecordTarget().getProp(this.pCampo.getValue(),false);
	  	if (obj==null) return false;
	  	return obj.getObjectType().equals(JObject.JDATE);
	  }

	  public boolean isDateTimeInput() throws Exception {
  		if (pCampo.getValue().equals("")) return false;
	  	if (isCampoFormula()) return false;
	  	if (getCampo().equals("COUNT")) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_ANIO)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_MES)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_BIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_TRIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_CUATRIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_SEMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_FUTURO)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_PASADO)) return false;

	  	if (getFuncion().equals(BizFuncion.FUNTION_PROXIMOS)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_ULTIMOS)) return false;
	  	JObject obj = this.getObjRecordTarget().getProp(this.pCampo.getValue(),false);
	  	if (obj==null) return false;
	  	return obj.getObjectType().equals(JObject.JDATETIME);
	  }

	  public boolean isCheckInput() throws Exception {
  		if (pCampo.getValue().equals("")) return false;
	  	if (isCampoFormula()) return false;
	  	if (getCampo().equals("COUNT")) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_ANIO)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_MES)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_BIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_TRIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_CUATRIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_SEMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_FUTURO)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_PASADO)) return false;

	  	if (getFuncion().equals(BizFuncion.FUNTION_PROXIMOS)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_ULTIMOS)) return false;
	  	JObject obj = this.getObjRecordTarget().getProp(this.pCampo.getValue(),false);
	  	if (obj==null) return false;
	  	return obj.getObjectType().equals(JObject.JBOOLEAN);
	  }

	  public boolean isLOVInput() throws Exception {
  		if (pCampo.getValue().equals("")) return false;
	  	if (isCampoFormula()) return false;
	  	if (getCampo().equals("COUNT")) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_ANIO)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_MES)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_BIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_TRIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_CUATRIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_SEMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_FUTURO)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_PASADO)) return false;
	  	if (getFuncion().equals(BizField.FUNTION_COUNT)) return false;
	  	if (getFuncion().equals(BizField.FUNTION_SUM)) return false;

	  	if (getFuncion().equals(BizFuncion.FUNTION_PROXIMOS)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_ULTIMOS)) return false;
	  	if (getOperador()!=null) {
		  	if (getOperador().equals("in")) return false;
		  	if (getOperador().equals("not in")) return false;
		  	if (getOperador().equals("like")) return false;
	  	}
	  	if (this.getObjRecordTarget()==null) return false;
	  	if (this.getObjRecordTarget().getRelationMap()==null) return false;
	  	if (this.getObjRecordTarget().getRelationMap().hasParentRecord(this.pCampo.getValue()))
	  			return true;
	  	return false;
	  }
	  public boolean isFilterInLista() throws Exception {
  		if (pCampo.getValue().equals("")) return false;
	  	if (isCampoFormula()) return false;
	  	if (getCampo().equals("COUNT")) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_ANIO)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_MES)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_BIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_TRIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_CUATRIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_SEMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_FUTURO)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_PASADO)) return false;

	  	if (getFuncion().equals(BizFuncion.FUNTION_PROXIMOS)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_ULTIMOS)) return false;
	  	JObject obj = this.getObjRecordTarget().getProp(this.pCampo.getValue(),false);
	  	if (obj==null) return false;
	  
	  	if (obj.getObjectType().equals(JObject.JCURRENCY)) return false;
	  	if (obj.getObjectType().equals(JObject.JFLOAT)) return false;
	  	if (obj.getObjectType().equals(JObject.JINTEGER)) return true;
	  	if (obj.getObjectType().equals(JObject.JLONG)) return true;
	  	if (obj.getObjectType().equals(JObject.JDATE)) return true;
	  	if (obj.getObjectType().equals(JObject.JSTRING)) return true;
	  	return false;
	  }
	  
	  public boolean isNumeric() throws Exception {
  		if (pCampo.getValue().equals("")) return false;
	  	if (isCampoFormula()) return false;
	  	if (getCampo().equals("COUNT")) return true;
	  	if (getFuncion().equals(BizFuncion.FUNTION_ANIO)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_MES)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_BIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_TRIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_CUATRIMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_SEMESTRE)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_FUTURO)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_PASADO)) return false;

	  	if (getFuncion().equals(BizFuncion.FUNTION_PROXIMOS)) return false;
	  	if (getFuncion().equals(BizFuncion.FUNTION_ULTIMOS)) return false;
	  	JObject obj = this.getObjRecordTarget().getProp(this.pCampo.getValue(),false);
	  	if (obj==null) return false;
	  
	  	if (obj.getObjectType().equals(JObject.JCURRENCY)) return true;
	  	if (obj.getObjectType().equals(JObject.JFLOAT)) return true;
	  	if (obj.getObjectType().equals(JObject.JINTEGER)) return true;
	  	if (obj.getObjectType().equals(JObject.JLONG)) return true;
	  	return false;
	  }

	  BizCampos detalles;
	
	  public BizCampos getObjDetalle() throws Exception {
	  	if (detalles!=null) return detalles;
	    	BizCampos c = new BizCampos();
	    	c.addFilter("company", this.getCompany());
	    	c.addFilter("list_id", this.getListId());
	    	c.addFilter("orden_padre", ""+this.getOrden());
	    	c.SetVision("FILTROS");
	    	c.setObjFiltroParent(this);
	    	c.setObjCustomList(getObjCustomList());
	    	c.toStatic();
	    	return detalles=c;
	    }	
	  
	  public JRelation getObjRelationFiltro() throws Exception {
	  	JRelations rels = this.getObjRelation().getObjRecordTarget().getRelationMap();
	  	return rels.findRelByFieldKey(this.getCampo());
	  }
	  public JRelation getObjRelationFiltroTarget() throws Exception {
	  	JRelations rels = this.getObjRelation().getObjRecordTarget().getRelationMap();
	  	JRelation rel = rels.findRelTargetByField(this.getCampo());
	  	return rel;
	  }
	  public String findValueOnFiltres(String campoChild) throws Exception {
	  	if (campoChild.equals("company"))
	  		return BizUsuario.getUsr().getCompany();
	  	// tendria que buscar el valor en los otros filtros
	  	return null;
	  }
	  public JProperty getExtraFilterProperty() throws Exception {
			return new JProperty(JRecord.VIRTUAL, "filtro"+getOrden(), "Cantidad", null, "", true, true, 18, 6, null, null, null);
		}	
	  public JProperty getExtraProperty(String campo,String descripcion) throws Exception {
			return new JProperty(JRecord.VIRTUAL, campo, descripcion, null, "", true, true, 18, 6, null, null, null);
		}	
		private String getField(Object obj) throws Exception {
			String f = (String)obj;
			int idx = f.indexOf(".");
			if (idx<0) return f;
			return f.substring(idx+1);
		}
	  public void assingFiltersLOV(boolean one, JRecords<?> r, int v) throws Exception {
	  	JRelation rel = this.getObjRelationFiltro();
	  	if (rel==null) return;

			JRelation fix = rel.getObjRecordTarget().getRelationMap().findFixedRelation();
			if (fix!=null) {
		
	    	JList<JPair> filters = fix.getFilters();
	    	JIterator<JPair> itFilter = filters.getIterator();
	    	while (itFilter.hasMoreElements()) {
	    		JPair filter = itFilter.nextElement();
	    		String sFilter= JTools.replace((String)filter.firstObject(), "COMPANY_CUSTOMLIST",  BizCompany.getObjCompany(getObjCustomList().getCompany()).getCustomListCompany());
	       		sFilter= JTools.replace(sFilter, "COMPANY_TICKET",  BizCompany.getObjCompany(getObjCustomList().getCompany()).getTicketCompany());
	    		r.addFixedFilter(sFilter,(String)filter.secondObject());
	    	}
			}

			if (!rel.hasJoin()) return;
			JIterator<JPair> iter2 = rel.getJoins().getIterator();
			while (iter2.hasMoreElements()) {
				JPair pair = iter2.nextElement();
				String relField = this.getField(pair.secondObject());
				if (!relField.equals(rel.getRelKeyField())) {
					r.addFilter(relField, this.findValueOnFiltres(relField));
					continue;
				}
				if (one) r.addFilter(relField, (v==1)?this.getValor():this.getValor2());
			} 
	  }
	  @Override
	  public String toString() {
	  	try {
				return getNameField();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return super.toString();
			}
	  }
	  
	  
	  private String getKeys(boolean parent) throws Exception {
	  	String keys = "";
	  	boolean first=true;
	  	JIterator<JPair> iter = this.getObjRelation().getJoins().getIterator();
	  	while (iter.hasMoreElements()) {
	  		JPair p = iter.nextElement();
	  		String key = parent?(String)p.secondObject():(String)p.firstObject();
	  		if (!first) keys+=", ";
	  		keys+=key;
	  		first=false;
	  	}
	  	return keys;
	  }

	  public String getFixedFilter(boolean inJoin,boolean totalizer) throws Exception {
	  	return getFixedFilter(inJoin, totalizer,true);
	  }	  
	  public String getFixedFilter(boolean inJoin,boolean totalizer, boolean checkIsActivo) throws Exception {
//		if (this.isOperIN() && !inJoin && !hasJoin(this.getObjRelation().getObjRecordTarget().getTableByFilter(getFiltro())))	
//			return this.getFilterIN();
	  if (checkIsActivo&&!this.isFiltroActivo()) return "";
		if (this.isOperAND()) return this.getFilterAND();
		if (this.isOperOR()) return this.getFilterOR(inJoin, totalizer);
		if (this.isOperAndInterno()) return this.getFilterAndInterno(inJoin,totalizer);
		return "";
  }

		public String getFilterOR(boolean inJoin,boolean totalizer) throws Exception {
			StringBuffer s = new StringBuffer();
			boolean first=true;
			if (this.isNegado()) s.append(" not ");
			s.append("( ");
			JIterator<BizCampo> iter = this.getObjCustomList().getCamposHijos(this).getStaticIterator();
			while (iter.hasMoreElements()) {
				BizCampo f = iter.nextElement();
				if (f.isNoIncluir() && totalizer) 
					continue;
				if (f.isSoloTotalizador() && !totalizer) 
					continue;

				if (!first)s.append(" OR ");
				String ss=f.getFixedFilter(inJoin,totalizer,false);
				if (!ss.equals("")) {
					s.append(ss);
					first=false;
				} else {
					s.append("  true ");
					first=false;
				}
			}
			if (first) 
				s.append(" false ");//JExcepcion.SendError("Error en Configuración");
			
			s.append(" )");
//			if (s.length()>100) return s.tfmoString().substring(0, 100)+" ....";
			return s.toString();
		}
		public String getFilterAndInterno(boolean inJoin,boolean totalizer) throws Exception {
			StringBuffer s = new StringBuffer();
			boolean first=true;
			if (this.isNegado()) s.append(" not ");
			s.append("( ");
			JIterator<BizCampo> iter = this.getObjCustomList().getCamposHijos(this).getStaticIterator();
			while (iter.hasMoreElements()) {
				BizCampo f = iter.nextElement();
				if (f.isNoIncluir() && totalizer) 
					continue;
				if (f.isSoloTotalizador() && !totalizer) 
					continue;
			
				if (!first)s.append(" AND ");
				s.append(f.getFixedFilter(inJoin,totalizer,false));
				first=false;
			}
			if (first) 
				s.append(" true ");//JExcepcion.SendError("Error en Configuración");
			s.append(" )");
//			if (s.length()>100) return s.toString().substring(0, 100)+" ....";
			return s.toString();
		}
		
	  public String getFilterIN() throws Exception {
	  	StringBuffer sql = new StringBuffer(); 
	  	sql.append("("+this.getKeys(true)+ ") IN ( select "+this.getKeys(false));
	  	sql.append(" from ").append(this.getObjRelation().getObjRecordTarget().getTableByFilter(getCampo()));
	  	sql.append(" where ").append(this.getFilterAND());
	  	return sql.toString()+") ";
	  }
	  

  public String getFilterAND() throws Exception {
		if (getObjCustomList()==null) return "";
		JBaseRegistro bd = JBaseRegistro.recordsetFactory();
		String value = this.getValor();
		Date hoy = getObjCustomList().getHoy();
		if (value.equals("") || hasCampoKey())  {
			if (this.getFuncion().equals(BizFuncion.FUNTION_MES)) {
				value = ""+JDateTools.getMonthNumber( getValorDefaultDate(getCampoKey(), hoy));
			}
			else if (this.getFuncion().equals(BizFuncion.FUNTION_ANIO)) {
				value = ""+JDateTools.getYearNumber( getValorDefaultDate(getCampoKey(), hoy));
			}
			else if (this.getFuncion().equals(BizFuncion.FUNTION_BIMESTRE)) {
				value = ""+(((JDateTools.getMonthNumber( getValorDefaultDate(getCampoKey(), hoy))-1)/2)+1);
			}
			else if (this.getFuncion().equals(BizFuncion.FUNTION_TRIMESTRE)) {
				value = ""+(((JDateTools.getMonthNumber( getValorDefaultDate(getCampoKey(), hoy))-1)/3)+1);
			}
			else if (this.getFuncion().equals(BizFuncion.FUNTION_CUATRIMESTRE)) {
				value = ""+(((JDateTools.getMonthNumber( getValorDefaultDate(getCampoKey(), hoy))-1)/4)+1);
			}
			else if (this.getFuncion().equals(BizFuncion.FUNTION_SEMESTRE)) {
				value = ""+(((JDateTools.getMonthNumber( getValorDefaultDate(getCampoKey(), hoy))-1)/6)+1);
			}			
			else
				value = getValorDefault(getCampoKey(), hoy);
		}
		if (this.getFuncion().equals(BizCampo.FUNTION_COUNT)) {
			return getExpresionWithOperator(bd, bd.fcount("*"),JObject.JLONG,value);
		}
		JObject obj = this.getObjRecordTarget().getProp(this.getCampo());
		if (this.getFuncion().equals(BizFuncion.FUNTION_ANIO)) {
			return getExpresionWithOperator(bd, bd.fanio(this.getTargetAlias()+"."+this.getCampo()),JObject.JLONG,value);
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_BIMESTRE)) {
			return getExpresionWithOperator(bd,bd.fbimestre(this.getTargetAlias()+"."+this.getCampo()),JObject.JLONG,value);
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_TRIMESTRE)) {
			return getExpresionWithOperator(bd,bd.ftrimestre(this.getTargetAlias()+"."+this.getCampo()),JObject.JLONG,value);
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_CUATRIMESTRE)) {
			return getExpresionWithOperator(bd,bd.fcuatrimestre(this.getTargetAlias()+"."+this.getCampo()),JObject.JLONG,value);
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_SEMESTRE)) {
			return getExpresionWithOperator(bd,bd.fsemestre(this.getTargetAlias()+"."+this.getCampo()),JObject.JLONG,value);
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_FUTURO)) {
			return bd.ffuturo(this.getTargetAlias()+"."+this.getCampo(),hoy);
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_PASADO)) {
			return bd.fpasado(this.getTargetAlias()+"."+this.getCampo(),hoy);
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_MES)) {
			return getExpresionWithOperator(bd, bd.fmes(this.getTargetAlias()+"."+this.getCampo()),JObject.JLONG,value);
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_ANIOACTUAL)) {
			return bd.fanioactual(this.getTargetAlias()+"."+this.getCampo(),hoy);
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_BIMESTREACTUAL)) {
			return bd.fbimestreactual(this.getTargetAlias()+"."+this.getCampo(),hoy);
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_TRIMESTREACTUAL)) {
			return bd.ftrimestreactual(this.getTargetAlias()+"."+this.getCampo(),hoy);
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_CUATRIMESTREACTUAL)) {
			return bd.fcuatrimestreactual(this.getTargetAlias()+"."+this.getCampo(),hoy);
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_SEMESTREACTUAL)) {
			return bd.fsemestreactual(this.getTargetAlias()+"."+this.getCampo(),hoy);
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_MESACTUAL)) {
			return bd.fmesactual(this.getTargetAlias()+"."+this.getCampo(),hoy);
		}

		if (this.getFuncion().equals(BizFuncion.FUNTION_MESANTERIOR)) {
			Calendar c= Calendar.getInstance();
			c.setTime(hoy);
			c.add(Calendar.MONTH,-1);
			return bd.fmesactual(this.getTargetAlias()+"."+this.getCampo(),c.getTime());
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_ANIOANTERIOR)) {
			Calendar c= Calendar.getInstance();
			c.setTime(hoy);
			c.add(Calendar.YEAR,-1);
			return bd.fanioactual(this.getTargetAlias()+"."+this.getCampo(),c.getTime());
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_BIMESTREANTERIOR)) {
			Calendar c= Calendar.getInstance();
			c.setTime(hoy);
			c.add(Calendar.MONTH,-2);
			return bd.fbimestreactual(this.getTargetAlias()+"."+this.getCampo(),c.getTime());
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_TRIMESTREANTERIOR)) {
			Calendar c= Calendar.getInstance();
			c.setTime(hoy);
			c.add(Calendar.MONTH,-3);
			return bd.ftrimestreactual(this.getTargetAlias()+"."+this.getCampo(),c.getTime());
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_CUATRIMESTREANTERIOR)) {
			Calendar c= Calendar.getInstance();
			c.setTime(hoy);
			c.add(Calendar.MONTH,-4);
			return bd.fcuatrimestreactual(this.getTargetAlias()+"."+this.getCampo(),c.getTime());
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_SEMESTREANTERIOR)) {
			Calendar c= Calendar.getInstance();
			c.setTime(hoy);
			c.add(Calendar.MONTH,-6);
			return bd.fsemestreactual(this.getTargetAlias()+"."+this.getCampo(),c.getTime());
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_HOY)) {
			return bd.fhoy(this.getTargetAlias()+"."+this.getCampo(),hoy);
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_AYER)) {
			return bd.fayer(this.getTargetAlias()+"."+this.getCampo(),hoy);
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_MANIANA)) {
			return bd.fmaniana(this.getTargetAlias()+"."+this.getCampo(),hoy);
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_PROXIMOS)) {
			return bd.fproximos(this.getTargetAlias()+"."+this.getCampo(),hoy,JTools.getLongNumberEmbedded(value));
		}
		if (this.getFuncion().equals(BizFuncion.FUNTION_ULTIMOS)) {
			return bd.fultimos(this.getTargetAlias()+"."+this.getCampo(),hoy,Long.parseLong(value.equals("")?"10":value));
		}
		obj.setValueFormUI(value);
		value=obj.toString();

		return getExpresionWithOperator(bd,this.getTargetAlias()+"."+this.getCampo(),obj.getObjectType(),value);
  }
  
  public String getExpresionWithOperator(JBaseRegistro bd,String field,String tipo,String value) throws Exception {
		if (this.getOperador().equals("NOT_NULL")) {
			return field+" is not null";
		}
		if (this.getOperador().equals("IS_NULL")) {
			return field+" is null";
		}
		if (this.getOperador().equals("NULL")) {
			return field+" is null";
		}
		if (this.getOperador().equals("in")) {
			return field + " in ("+ this.entrecomillar(value) +")";
		}	
		if (this.getOperador().equals("not in")) {
			return field + " not in ("+ this.entrecomillar(value) +")";
		}		
		if (this.getOperador().equals("<>") && tipo.equals("JCHAR") && value.equals("")) {
			return " ("+field + " <> '' or "+field+" is not null ) ";
		}	
		if (this.getOperador().equals("=") && tipo.equals("JCHAR") && value.equals("")) {
			return " ("+field + "= '' or "+field+" is  null ) ";
		}	
		String dato = value;
		if (this.getOperador().equals("like")|| this.getOperador().equals("not like")) {
			StringTokenizer toks = new StringTokenizer(value,",;-");
			String condicion ="";
			while (toks.hasMoreTokens()) {
				String val = toks.nextToken();
				value="%"+val+"%";
				dato = new JRegSQL().ArmarDato(null, tipo, value);
				condicion += (condicion.equals("")?"":" OR ")+"("+field+" "+getOperador()+" "+dato+")";
			}
			return "("+condicion+")";
		}
		if (tipo!=null)
			dato = new JRegSQL().ArmarDato(null, tipo, value);
		if (this.getOperador().equals(BizFuncion.FUNTION_INTERVALO)) {
			String dato2 = this.getValor2();
			if (tipo!=null) {
				dato2 = new JRegSQL().ArmarDato(null, tipo, dato2);
			}
			return bd.fintervalo(field,dato,dato2);//corregir
		}
		return field+" "+getOperador()+" "+dato;
  }
  
  public void processDrop(BizCustomList cl) throws Exception {
		setObjCustomList(cl);
		if (!isDependiente())
			cl.addCampo(this);
		if (getObjFiltroParent()!=null) {
			getObjFiltroParent().addDetalle(this);
			this.setOrdenPadre(getObjFiltroParent().getOrdenPadre());
		}

//		if (this.getObjDetalle().getStaticItems().size()!=0) {
//			JIterator<BizCampo> it=this.getObjDetalle().getStaticIterator();
//			while(it.hasMoreElements()) {
//				BizCampo det = it.nextElement();
//				if (!cl.getObjAllCampos().getStaticItems().containsElement(det))
//					cl.getObjAllCampos().getStaticItems().addElement(det);
//			}
//		}

  }
  public void addDetalle(BizCampo detail) throws Exception {
  	getObjDetalle().getStaticItems().addElement(detail);
  }
  public void setearOrden(BizCustomList cl) throws Exception {
		if (this.pOrden.isNull())
			this.setOrden(cl.getObjAllCampos().getStaticItems().size()+1);
		if (this.getObjDetalle().getStaticItems().size()!=0) {
			JIterator<BizCampo> it=this.getObjDetalle().getStaticIterator();
			while(it.hasMoreElements()) {
				BizCampo det = it.nextElement();
				det.setOrdenPadre(""+this.getOrden());
			}
		}
  }
  public void addDetailToCampos(BizCustomList cl) throws Exception {
		if (this.pOrden.isNull())
			this.setOrden(cl.getObjAllCampos().getStaticItems().size()+1);
		if (this.isDependiente())
			cl.addCampo(this);
		if (this.detalles!=null) {
			JIterator<BizCampo> it=this.detalles.getStaticIterator();
			while(it.hasMoreElements()) {
				BizCampo det = it.nextElement();
				det.setOrdenPadre(""+this.getOrden());
				det.addDetailToCampos(cl);
			}
		}
  }
  public Date getConvertToDefaultDate(String key) throws Exception {
		if (key.equalsIgnoreCase(JCompanyBusiness.FECHA_INICIAL_MES)) return JDateTools.getFirstDayOfMonth(getObjCustomList().getHoy());
		if (key.equalsIgnoreCase(JCompanyBusiness.FECHA_FINAL_MES)) return JDateTools.getLastDayOfMonth(getObjCustomList().getHoy());
		if (key.equalsIgnoreCase(JCompanyBusiness.FECHA_INICIAL_ANO)) return JDateTools.getFirstDayOfYear(getObjCustomList().getHoy());
		if (key.equalsIgnoreCase(JCompanyBusiness.FECHA_FINAL_ANO)) return JDateTools.getLastDayOfYear(getObjCustomList().getHoy());
		if (key.equalsIgnoreCase(JCompanyBusiness.FECHA_ACTUAL)) return getObjCustomList().getHoy();
		return null;
  }  
  public Date getDefaultValueDate1() throws Exception {
  	if (!hasCampoKey())
  		return null;
  	return getConvertToDefaultDate(getCampoKey());
  }
  public Date getDefaultValueDate2() throws Exception {
  	if (!hasCampoKey2())
  		return null;
  	return getConvertToDefaultDate(getCampoKey2());
  }
  
	public JMap<String, String> getOptionsOrden() {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement(BizCampo.ORDER_ASC, "Asc");
		map.addElement(BizCampo.ORDER_NO, "-");
		map.addElement(BizCampo.ORDER_DESC, "Desc");
		return map;
	}
	
  
  public static JMap<String, String> getTipoNegaciones() throws Exception {
  	JMap<String, String> map = JCollectionFactory.createMap();
  	map.addElement(TIPO_TRUE, "Registros que Incluyen");
  	map.addElement(TIPO_FALSE, "Registros que NO Incluyen");
  	return map;
  }

  
	@Override
	public boolean detectIsComplete() throws Exception {
		return true;
	}

}
