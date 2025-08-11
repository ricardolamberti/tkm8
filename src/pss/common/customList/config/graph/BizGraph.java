package pss.common.customList.config.graph;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.field.ICampo;
import pss.common.customList.config.field.campo.BizCampo;
import pss.core.graph.Graph;
import pss.core.graph.analize.Dataset;
import pss.core.graph.model.ModelBullet;
import pss.core.graph.model.ModelGraph;
import pss.core.graph.model.ModelGrid;
import pss.core.graph.model.ModelGridAndLine;
import pss.core.graph.model.ModelMatrix;
import pss.core.graph.model.ModelMatrixAndLine;
import pss.core.graph.model.ModelPie;
import pss.core.graph.model.ModelVector;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;

public class BizGraph extends JRecord {

  private JString pCompany = new JString();
  private JLong pListId = new JLong();
  private JLong pSecuencia = new JLong();
  private JString pTitle = new JString();
  private JString pType = new JString();
  private JString pModelo = new JString();
  private JString pColCategoria = new JString();
  private JString pColDataset = new JString();
  private JString pColValue = new JString();
  private JString pColValue2 = new JString();
  private JString pColValue3 = new JString();
  private JString pColValue4 = new JString();
  private JString pColValue5 = new JString();
  private JString pDescrType = new JString() {
  	public void preset() throws Exception {
  		pDescrType.setValue(getDescrType());
  	}
  };
  
  public String getCompany() throws Exception {
  	return this.pCompany.getValue();
  }
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public void setListId(long zValue) throws Exception {    pListId.setValue(zValue);  }

  public long getListId() throws Exception {
  	return this.pListId.getValue();
  }
  public long getSecuencia() throws Exception {     return pSecuencia.getValue();  }
	public void setNullToSecuencia() throws Exception { pSecuencia.setNull();}

  public void setColCategoria(String zValue) throws Exception {      pColCategoria.setValue(zValue);  }
  public String getColCategoria() throws Exception {
  	return this.pColCategoria.getValue();
  }
  public void setColDataset(String zValue) throws Exception {      pColDataset.setValue(zValue);  }
  public String getColDataset() throws Exception {
  	return this.pColDataset.getValue();
  }
  public void setColValor(String zValue) throws Exception {      pColValue.setValue(zValue);  }
  public String getColValor() throws Exception {
  	return this.pColValue.getValue();
  }
  public void setColValor2(String zValue) throws Exception {      pColValue2.setValue(zValue);  }
  public String getColValor2() throws Exception {
  	return this.pColValue2.getValue();
  }
  public void setColValor3(String zValue) throws Exception {      pColValue3.setValue(zValue);  }
  public String getColValor3() throws Exception {
  	return this.pColValue3.getValue();
  }
  public void setColValor4(String zValue) throws Exception {      pColValue4.setValue(zValue);  }
  public String getColValor4() throws Exception {
  	return this.pColValue4.getValue();
  }
  public void setColValor5(String zValue) throws Exception {      pColValue5.setValue(zValue);  }
  public String getColValor5() throws Exception {
  	return this.pColValue5.getValue();
  }

  public void setTitle(String value) {
  	this.pTitle.setValue(value);
  }
  public void setType(String value) {
  	this.pType.setValue(value);
  	this.graph=null;
  	this.model=null;
  }
  public void setModelo(String value) {
  	this.pModelo.setValue(value);
  	this.graph=null;
  	this.model=null;
  }
  public String getModel() throws Exception {
  	return this.pModelo.getValue();
  }
  public String getType() throws Exception {
  	return this.pType.getValue();
  }
  
  public String getTitle() throws Exception {
  	return this.pTitle.getValue();
  }
  private BizCustomList customList;
  private ICampo campoCategoria;
  private ICampo campoDataset;
  private ICampo campoValor;
  private ICampo campoValor2;
  private ICampo campoValor3;
  private ICampo campoValor4;
  private ICampo campoValor5;
  private Graph graph;
  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////



  /**
   * Constructor de la Clase
   */
  public BizGraph() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "list_id", pListId );
    this.addItem( "secuencia", pSecuencia );
    this.addItem( "title", pTitle );
    this.addItem( "type", pType );
    this.addItem( "modelo", pModelo );
    this.addItem( "col_categoria", pColCategoria);
    this.addItem( "col_dataset", pColDataset);
    this.addItem( "col_valor", pColValue);
    this.addItem( "col_valor2", pColValue2);
    this.addItem( "col_valor3", pColValue3);
    this.addItem( "col_valor4", pColValue4);
    this.addItem( "col_valor5", pColValue5);
    this.addItem( "descr_type", pDescrType);
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "list_id", "List id", true, true, 5 );
    this.addFixedItem( KEY, "secuencia", "Secuencia", false, false, 64); 
    this.addFixedItem( FIELD, "title", "Titulo", true, false, 250);
    this.addFixedItem( FIELD, "type", "Tipo", true, true, 30);
    this.addFixedItem( FIELD, "modelo", "Modelo", true, true, 30);
    this.addFixedItem( FIELD, "col_categoria", "Columan Categor�a", true, false, 18 );
    this.addFixedItem( FIELD, "col_dataset", "Columna Dataset", true, false, 18 );
    this.addFixedItem( FIELD, "col_valor", "Columna Valor", true, false, 18	 );
    this.addFixedItem( FIELD, "col_valor2", "Columna Valor 2", true, false, 18	 );
    this.addFixedItem( FIELD, "col_valor3", "Columna Valor 3", true, false, 18	 );
    this.addFixedItem( FIELD, "col_valor4", "Columna Valor 4" , true, false, 18	 );
    this.addFixedItem( FIELD, "col_valor5", "Columna Valor 5", true, false, 18	 );
    this.addFixedItem( VIRTUAL, "descr_type", "Tipo Gr�fico", true, false, 100 );
  }
  
  /**
   * Returns the table name
   */
  public String GetTable() { return ""; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany, long zListId, long sec ) throws Exception { 
    addFilter( "company", zCompany ); 
    addFilter( "list_id", zListId );
    addFilter( "secuencia", sec ); 
    return read(); 
  }
  
	public void processInsert() throws Exception {
		super.processInsert();
		pSecuencia.setValue(getIdentity("secuencia"));

	}

	public String getDescrType() throws Exception {
		BizVirtual v = Graph.getGraphTypes().findVirtualKey(this.pType.getValue());
		return v==null?"Desconocido":v.getDescrip();
	}
	
	public Graph getObjGraph() throws Exception {
		if (graph!=null) return graph;
		if (pType.isNull()) return null;
		return (graph=(Graph)Class.forName("pss.core.graph.implementations."+this.pType.getValue()).newInstance());
	}
	ModelGraph model;
	
	public ModelGraph getObjModel() throws Exception {
		if (model!=null) return model;
		if (pType.isNull()) return null;
		if (pModelo.getValue().equals("") || pModelo.getValue().equals("A"))
			return model=getObjGraph().getModel();
		return model=(ModelGraph)Class.forName("pss.core.graph.model."+this.pModelo.getValue()).newInstance();
	}
	
	public boolean hasColCategoria() throws Exception {
		if (getObjModel()==null) return false;
		return getObjModel().hasCategoria();
	}
	
	public boolean hasColDataset() throws Exception {
		if (getObjModel()==null) return false;
		return getObjModel().hasDataset();
	}
	public boolean hasColValor2() throws Exception {
		if (getObjModel()==null) return false;
		return getObjModel().hasValor2();
	}
	public boolean hasColValor3() throws Exception {
		if (getObjModel()==null) return false;
		return getObjModel().hasValor3();
	}
	public boolean hasColValor4() throws Exception {
		if (getObjModel()==null) return false;
		return getObjModel().hasValor4();
	}
	public boolean hasColValor5() throws Exception {
		if (getObjModel()==null) return false;
		return getObjModel().hasValor5();
	}
	public boolean hasColDatasetLine() throws Exception {
		if (getObjModel()==null) return false;
		return getObjModel().hasDatasetLine();
	}
	public boolean hasColValorLine() throws Exception {
		if (getObjModel()==null) return false;
		return getObjModel().hasValorLine();
	}
	public boolean hasCampoCategoria(boolean inEje) throws Exception {
			return this.getObjCampoCategoria()!=null;
	}
	public String getCampoCategoria(boolean inEje) throws Exception {
		return this.getObjCampoCategoria().getNameField();
	}
	public String getGeoCampoCategoria(boolean inEje) throws Exception {
		
		BizCampo campo = (BizCampo)this.getObjCampoCategoria();
		if (campo==null) return "";
		if (campo.getGeoCampo()==null) return "";
  	String table = campo.getObjGeoRelation().getTargetAlias(campo.getGeoCampo());
  	if (table.equals("")) 
  		table=campo.getTargetAlias();

		return campo.getGeoNameField(table,campo.getGeoCampo());
	}
	public String getCampoDataset(boolean inEje) throws Exception {
		if (pColDataset.getValue().startsWith("-")) 
			return getCampoDatasetDiferencia(pColDataset.getValue());
//		if (inEje) return this.getObjCampoDatasetInEje().getNameField();
		return this.getObjCampoDataset().getNameField();
	}
	public boolean hasCampoDataset(boolean inEje) throws Exception {
		if (pColDataset.getValue().startsWith("-"))
			return true;
		//if (inEje) return this.getObjCampoDatasetInEje()!=null;
		return this.getObjCampoDataset()!=null;
	}
	public String getGeoCampoDataset(boolean inEje) throws Exception {
		BizCampo campo = (BizCampo)this.getObjCampoDataset();
		if (campo==null) return "";
		if (campo.getGeoCampo()==null) return "";
  	String table = campo.getObjGeoRelation().getTargetAlias(campo.getGeoCampo());
  	if (table.equals("")) 
  		table=campo.getTargetAlias();

		return campo.getGeoNameField(table,campo.getGeoCampo());
	}
	public String getCampoValor() throws Exception {
		if (pColValue.getValue().startsWith("-")) 
			return getCampoDatasetDiferencia(pColValue.getValue());
		return this.getObjCampoValor().getNameField();
	}
	public boolean hasCampoValor() throws Exception {
		if (pColValue.getValue().startsWith("-"))
			return true;
		return this.getObjCampoValor()!=null;
	}
	public boolean hasCampoValor2() throws Exception {
		if (pColValue2.getValue().startsWith("-"))
			return true;
		return this.hasObjCampoValor2();
	}
	public String getCampoValor2() throws Exception {
		if (pColValue2.getValue().startsWith("-")) 
			return getCampoDatasetDiferencia(pColValue2.getValue());
		return this.getObjCampoValor2().getNameField();
	}
	public String getCampoValor3() throws Exception {
		if (pColValue3.getValue().startsWith("-")) 
			return getCampoDatasetDiferencia(pColValue3.getValue());
		return this.getObjCampoValor3().getNameField();
	}
	public String getCampoValor4() throws Exception {
		if (pColValue4.getValue().startsWith("-")) 
			return getCampoDatasetDiferencia(pColValue4.getValue());
		return this.getObjCampoValor4().getNameField();
	}
	public String getCampoValor5() throws Exception {
		if (pColValue5.getValue().startsWith("-")) 
			return getCampoDatasetDiferencia(pColValue5.getValue());
		return this.getObjCampoValor5().getNameField();
	}
	public void setObjCustomList(BizCustomList list) throws Exception {
		customList=list;
	}
	
	public BizCustomList getObjCustomList() throws Exception {
		if (this.customList!=null) return this.customList;
		BizCustomList c = new BizCustomList();
		c.read(this.getCompany(), this.getListId());
		return (this.customList=c);
	}

	public ICampo getObjCampoCategoria() throws Exception {
		if (this.pColCategoria.isNull()) return null;
		if (this.campoCategoria!=null) return this.campoCategoria;

		ICampo campo = customList.findCampo(this.pColCategoria.getValue());
		if (campo==null) 
			JExcepcion.SendError("Debe configurar el campo CATEGORIA");
		return (this.campoCategoria = campo);

		
//		BizCampo c = new BizCampo();
//		c.dontThrowException(true);
//		if (!c.read(this.getCompany(), this.getListId(), Long.parseLong(this.pColCategoria.getValue()))) {
//			JExcepcion.SendError("Debe configurar el campo CATEGORIA");
//		}
//		return (this.campoCategoria=c);
	}
	
	BizCampo campoDiferencia;
	public String getCampoDatasetDiferencia(String sec) throws Exception {
		BizCampo eje=getObjCampoDatasetDiferencia(sec);
		if (eje==null) return null;
		if (JTools.getLongNumberEmbedded(sec)%2==0)
			return eje.getNameFieldDif(null); //rjl
		else
			return eje.getNameFieldDifPorc(null); //rjl
	}
	
	public BizCampo getObjCampoDatasetDiferencia(String sec) throws Exception {
		long secuencia = -Long.parseLong(sec);
		if (secuencia%2==1) secuencia-=1;
		secuencia/=2;
		BizCampo e = new BizCampo();
		e.dontThrowException(true);
		if (!e.read(this.getCompany(), this.getListId(), secuencia))
			return null;
		return (this.campoDiferencia = e);
}
/*	public ICampo getObjCampoDatasetInEje() throws Exception {

			BizCampo e = new BizCampo();
			e.dontThrowException(true);
			if (!e.read(this.getCompany(), this.getListId(), this.pColDataset.getValue()))
				return null;
//			JExcepcion.SendError("Debe configurar el campo DATASET");
			return (this.campoDataset = e);
	}*/
	public ICampo getObjCampoDataset() throws Exception {
		if (this.campoDataset!=null) return this.campoDataset;
		
		ICampo campo = customList.findCampo(this.pColDataset.getValue());
		if (campo==null) 
			JExcepcion.SendError("Debe configurar el campo DATASET");
		return (this.campoDataset = campo);
		
		
//		BizCampo c = new BizCampo();
//		c.dontThrowException(true);
//		if (!c.read(this.getCompany(), this.getListId(), Long.parseLong(this.pColDataset.getValue()))) {
//				JExcepcion.SendError("Debe configurar el campo DATASET");
//		}
//		return (this.campoDataset = c);
	}

	public ICampo getObjCampoValor() throws Exception {
		if (this.campoValor!=null) return this.campoValor;
		ICampo campo = customList.findCampo(this.pColValue.getValue());
		if (campo==null) 
			JExcepcion.SendError("Debe configurar el campo VALOR 1");
		return (this.campoValor = campo);

		
//		if (this.campoValor!=null) return this.campoValor;
//		BizCampo c = new BizCampo();
//		c.dontThrowException(true);
//		if (!c.read(this.getCompany(), this.getListId(), Long.parseLong(this.pColValue.getValue()))) {
//				return null;
////				JExcepcion.SendError("Debe configurar el campo VALOR");
//		}
//		return (this.campoValor=c);
	}
	public ICampo getObjCampoValor2() throws Exception {
		if (this.campoValor2!=null) return this.campoValor2;
		ICampo campo = customList.findCampo(this.pColValue2.getValue());
		if (campo==null) 
			JExcepcion.SendError("Debe configurar el campo VALOR 2");
		return (this.campoValor2 = campo);
		
		
//
//		if (this.campoValor2!=null) return this.campoValor2;
//		BizCampo c = new BizCampo();
//		c.dontThrowException(true);
//		if (!c.read(this.getCompany(), this.getListId(), JTools.getLongNumberEmbedded(this.pColValue2.getValue()))){
//				JExcepcion.SendError("Debe configurar el campo VALOR 2");
//		}
//		return (this.campoValor2=c);
	}
	public boolean hasObjCampoValor2() throws Exception {
		if (this.campoValor2!=null) return true;
		ICampo campo = customList.findCampo(this.pColValue2.getValue());
		if (campo==null) 
			return false;
		return true;
	}
	public ICampo getObjCampoValor3() throws Exception {
		if (this.campoValor3!=null) return this.campoValor3;
		ICampo campo = customList.findCampo(this.pColValue3.getValue());
		if (campo==null) 
			JExcepcion.SendError("Debe configurar el campo VALOR 3");
		return (this.campoValor3 = campo);

//		if (this.campoValor3!=null) return this.campoValor3;
//		BizCampo c = new BizCampo();
//		c.dontThrowException(true);
//		if (!c.read(this.getCompany(), this.getListId(), Long.parseLong(this.pColValue3.getValue()))){
//			JExcepcion.SendError("Debe configurar el campo VALOR 3");
//		}
//		return (this.campoValor3=c);
	}
	public ICampo getObjCampoValor4() throws Exception {
		if (this.campoValor4!=null) return this.campoValor4;
		ICampo campo = customList.findCampo(this.pColValue4.getValue());
		if (campo==null) 
			JExcepcion.SendError("Debe configurar el campo VALOR 4");
		return (this.campoValor4 = campo);
//		if (this.campoValor4!=null) return this.campoValor4;
//		BizCampo c = new BizCampo();
//		c.dontThrowException(true);
//		if (!c.read(this.getCompany(), this.getListId(), Long.parseLong(this.pColValue4.getValue()))){
//
//			JExcepcion.SendError("Debe configurar el campo VALOR 4");
//		}
//		return (this.campoValor4=c);
	}
	public ICampo getObjCampoValor5() throws Exception {
		if (this.campoValor5!=null) return this.campoValor5;
		ICampo campo = customList.findCampo(this.pColValue5.getValue());
		if (campo==null) 
			JExcepcion.SendError("Debe configurar el campo VALOR 5");
		return (this.campoValor5 = campo);

//		if (this.campoValor5!=null) return this.campoValor5;
//		BizCampo c = new BizCampo();
//		c.dontThrowException(true);
//		if (!c.read(this.getCompany(), this.getListId(), Long.parseLong(this.pColValue5.getValue()))){
//			JExcepcion.SendError("Debe configurar el campo VALOR 5");
//		}
//		return (this.campoValor5=c);
	}
	
	
	
	public Graph getGraph() throws Exception {
			BizCustomList customList= getObjCustomList();
			Graph gr = getObjGraph();
			if (gr==null) 
				return null;
			customList.setWithGeo(gr.getGeoRequest()>0);
			gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
			gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
			gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
			gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
		  if (customList.hasLimite()) 
		  	gr.setSizeOthers((int)customList.getLimite());
			if (getTitle().equals(""))
				gr.setTitle(customList.getDescription());
			else
				gr.setTitle(getTitle());
				
			String model = getModel();
			if (model.equals("A")||model.equals("")) {
				model = gr.getModel().getClass().getSimpleName();
			}
			if (model.equals("") || model.equals(ModelMatrix.class.getSimpleName())) {
				if (hasColDataset() && !hasCampoDataset(customList.isMatriz())) return null;
				if (!hasCampoValor()) return null;
			
				if (hasColCategoria()) {
					if (!hasCampoCategoria(customList.isMatriz())) 
						gr.getModel().addColumn("", null,ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
					else
						gr.getModel().addColumn(getCampoCategoria(customList.isMatriz()), getGeoCampoCategoria(customList.isMatriz()),ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
				}
				if (hasColDataset()) {
					gr.getModel().addColumn(getCampoDataset(customList.isMatriz()),getGeoCampoDataset(customList.isMatriz()), ModelMatrix.GRAPH_FUNCTION_DATASET);
		  		gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, getObjCampoDataset().getForceNombreColumna());
				}
				gr.getModel().addColumn(getCampoValor(),null, ModelMatrix.GRAPH_FUNCTION_VALUE);
	  		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, getObjCampoValor().getForceNombreColumna());
			}
			else if (model.equals(ModelGrid.class.getSimpleName())) {
				if (hasColCategoria() && !hasCampoCategoria(customList.isMatriz())) return null;
				if (!hasCampoValor()) return null;
				ModelGrid m= new ModelGrid();
				if (hasColCategoria())
					m.addColumn(getCampoCategoria(customList.isMatriz()),getGeoCampoCategoria(customList.isMatriz()), ModelGrid.GRAPH_FUNCTION_CATEGORIE);
				m.addColumn(new Dataset(getObjCampoValor().getForceNombreColumna()),getCampoValor(), ModelGrid.GRAPH_FUNCTION_VALUE);//.setDataset(new Dataset(getObjCampoDataset().getForceNombreColumna()));
				if (!getColValor2().equals("")) m.addColumn(new Dataset( getObjCampoValor2().getForceNombreColumna()),getCampoValor2(),ModelGrid.GRAPH_FUNCTION_VALUE);//.setDataset(new Dataset(getObjCampoValor().getForceNombreColumna()));
				if (!getColValor3().equals("")) m.addColumn(new Dataset( getObjCampoValor3().getForceNombreColumna()),getCampoValor3(),ModelGrid.GRAPH_FUNCTION_VALUE);//.setDataset(new Dataset(getObjCampoValor().getForceNombreColumna()));
				if (!getColValor4().equals("")) m.addColumn(new Dataset( getObjCampoValor4().getForceNombreColumna()),getCampoValor4(),ModelGrid.GRAPH_FUNCTION_VALUE);//.setDataset(new Dataset(getObjCampoValor().getForceNombreColumna()));
				if (!getColValor5().equals("")) m.addColumn(new Dataset( getObjCampoValor5().getForceNombreColumna()),getCampoValor5(),ModelGrid.GRAPH_FUNCTION_VALUE);//.setDataset(new Dataset(getObjCampoValor().getForceNombreColumna()));
				gr.setModel(m);
			}
			else if (model.equals(ModelBullet.class.getSimpleName())) {
				if (hasColCategoria() && !hasCampoCategoria(customList.isMatriz())) return null;
				if (!hasCampoValor()) return null;
				ModelBullet m= new ModelBullet();
				if (hasColCategoria())
					m.addColumn(getCampoCategoria(customList.isMatriz()),getGeoCampoCategoria(customList.isMatriz()), ModelBullet.GRAPH_FUNCTION_CATEGORIE);
				m.addColumn(new Dataset(getObjCampoValor().getForceNombreColumna()),getCampoValor(), ModelBullet.GRAPH_FUNCTION_DATA);//.setDataset(new Dataset(getObjCampoDataset().getForceNombreColumna()));
				if (!getColValor2().equals("")) m.addColumn(new Dataset( getObjCampoValor2().getForceNombreColumna()),getCampoValor2(),ModelBullet.GRAPH_FUNCTION_OBJ);//.setDataset(new Dataset(getObjCampoValor().getForceNombreColumna()));
				if (!getColValor3().equals("")) m.addColumn(new Dataset( getObjCampoValor3().getForceNombreColumna()),getCampoValor3(),ModelBullet.GRAPH_FUNCTION_ZONE1);//.setDataset(new Dataset(getObjCampoValor().getForceNombreColumna()));
				if (!getColValor4().equals("")) m.addColumn(new Dataset( getObjCampoValor4().getForceNombreColumna()),getCampoValor4(),ModelBullet.GRAPH_FUNCTION_ZONE2);//.setDataset(new Dataset(getObjCampoValor().getForceNombreColumna()));
				if (!getColValor5().equals("")) m.addColumn(new Dataset( getObjCampoValor5().getForceNombreColumna()),getCampoValor5(),ModelBullet.GRAPH_FUNCTION_ZONE3);//.setDataset(new Dataset(getObjCampoValor().getForceNombreColumna()));
				gr.setModel(m);
			}
			else if (model.equals(ModelGridAndLine.class.getSimpleName())) {
				if (hasColCategoria() && !hasCampoCategoria(customList.isMatriz())) return null;
				if (!hasCampoValor()) return null;
				ModelGridAndLine m= new ModelGridAndLine();
				if (hasColCategoria()) {
					if (!hasCampoCategoria(customList.isMatriz())) 
						m.addColumn("",null, ModelGrid.GRAPH_FUNCTION_CATEGORIE);
					else
						m.addColumn(getCampoCategoria(customList.isMatriz()),getGeoCampoCategoria(customList.isMatriz()), ModelGrid.GRAPH_FUNCTION_CATEGORIE);
				}
				m.addColumn(new Dataset(getObjCampoValor().getForceNombreColumna()),getCampoValor(), ModelGrid.GRAPH_FUNCTION_VALUE);//.setDataset(new Dataset(getObjCampoDataset().getForceNombreColumna()));
				if (!getColValor2().equals("")) m.addColumn(new Dataset( getObjCampoValor2().getForceNombreColumna()),getCampoValor2(),ModelGridAndLine.GRAPH_FUNCTION_VALUE);//.setDataset(new Dataset(getObjCampoValor().getForceNombreColumna()));
				if (!getColValor3().equals("")) m.addColumn(new Dataset( getObjCampoValor3().getForceNombreColumna()),getCampoValor3(),ModelGridAndLine.GRAPH_FUNCTION_VALUE);//.setDataset(new Dataset(getObjCampoValor().getForceNombreColumna()));
				if (!getColValor4().equals("")) m.addColumn(new Dataset( getObjCampoValor4().getForceNombreColumna()),getCampoValor4(),ModelGridAndLine.GRAPH_FUNCTION_VALUE);//.setDataset(new Dataset(getObjCampoValor().getForceNombreColumna()));
				if (!getColValor5().equals("")) m.addColumn(new Dataset( getObjCampoValor5().getForceNombreColumna()),getCampoValor5(),ModelGridAndLine.GRAPH_FUNCTION_VALUE_LINE);//.setDataset(new Dataset(getObjCampoValor().getForceNombreColumna()));
				gr.setModel(m);
			}
			else if (model.equals(ModelVector.class.getSimpleName())) {
				if (!hasCampoDataset(customList.isMatriz())) return null;
				if (!hasCampoValor()) return null;
				ModelVector m= new ModelVector();
				m.addColumn(getCampoDataset(customList.isMatriz()),getGeoCampoDataset(customList.isMatriz()), ModelVector.GRAPH_FUNCTION_DATASET);
				m.addColumn(getCampoValor(),null, ModelVector.GRAPH_FUNCTION_VALUE);
	  		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, getObjCampoValor().getForceNombreColumna());
	  		gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, getObjCampoDataset().getForceNombreColumna());
				gr.setModel(m);
			}
			else if (model.equals(ModelMatrixAndLine.class.getSimpleName())) {
				ModelMatrixAndLine m= new ModelMatrixAndLine();
				if (hasColDataset() && !hasCampoDataset(customList.isMatriz())) return null;
				if (!hasCampoValor()) return null;
				if (hasColCategoria()) {
					if (!hasCampoCategoria(customList.isMatriz())) 
						m.addColumn("",null, ModelMatrixAndLine.GRAPH_FUNCTION_CATEGORIE);
					else
						m.addColumn(getCampoCategoria(customList.isMatriz()),getGeoCampoCategoria(customList.isMatriz()), ModelMatrixAndLine.GRAPH_FUNCTION_CATEGORIE);
				}
				if (hasColDataset())
					m.addColumn(getCampoDataset(customList.isMatriz()),getGeoCampoDataset(customList.isMatriz()), ModelMatrixAndLine.GRAPH_FUNCTION_DATASET);
				m.addColumn(getCampoValor(),null, ModelMatrixAndLine.GRAPH_FUNCTION_VALUE);

				m.addColumn(getCampoDataset(customList.isMatriz()),null, ModelMatrixAndLine.GRAPH_FUNCTION_DATASET_LINE);
				if (hasCampoValor2())
					m.addColumn(getCampoValor2(),null, ModelMatrixAndLine.GRAPH_FUNCTION_VALUE_LINE);
				else
					m.addColumn(getCampoValor(),null, ModelMatrixAndLine.GRAPH_FUNCTION_VALUE_LINE);
				gr.setModel(m);
			} else if (model.equals(ModelPie.class.getSimpleName())) {
				ModelPie m= new ModelPie();
				if (!hasCampoValor()) return null;
				if (!getColValor().equals(""))m.addColumn(new Dataset(getObjCampoValor().getForceNombreColumna()),getCampoValor(), ModelPie.GRAPH_FUNCTION_COMPLEMENTO);//.setDataset(new Dataset(getObjCampoDataset().getForceNombreColumna()));
				if (!getColValor2().equals(""))m.addColumn(new Dataset(getObjCampoValor2().getForceNombreColumna()),getCampoValor2(), ModelPie.GRAPH_FUNCTION_VALUE);//.setDataset(new Dataset(getObjCampoDataset().getForceNombreColumna()));
				if (!getColValor3().equals(""))m.addColumn(new Dataset(getObjCampoValor3().getForceNombreColumna()),getCampoValor3(), ModelPie.GRAPH_FUNCTION_VALUE);//.setDataset(new Dataset(getObjCampoDataset().getForceNombreColumna()));
				if (!getColValor4().equals(""))m.addColumn(new Dataset(getObjCampoValor4().getForceNombreColumna()),getCampoValor4(), ModelPie.GRAPH_FUNCTION_VALUE);//.setDataset(new Dataset(getObjCampoDataset().getForceNombreColumna()));
				if (!getColValor5().equals(""))m.addColumn(new Dataset(getObjCampoValor5().getForceNombreColumna()),getCampoValor5(), ModelPie.GRAPH_FUNCTION_VALUE);//.setDataset(new Dataset(getObjCampoDataset().getForceNombreColumna()));
				gr.setModel(m);
			}
			return gr;
	}
	
	public long getDefaultValorCategoria() throws Exception {
		BizCustomList list= getObjCustomList();
		if (list.isMatriz()) {
			BizCampo eje=list.getFirstColumna();
			if (eje==null) return 0;
			return eje.getSecuencia();
		}
		BizCampo c=list.getFirstCampo();
		if (c==null) return 0;
		return c.getSecuencia();
	}
	public long getDefaultValorDataset() throws Exception {
		BizCustomList list= getObjCustomList();
		if (list.isMatriz()) {
			BizCampo eje=list.getFirstFila();
			if (eje==null) return 0;
			return eje.getSecuencia();
		}
		BizCampo c=list.getFirstCampo();
		if (c==null) return 0;
		return c.getSecuencia();
	}
	public long getDefaultValorCampo() throws Exception {
		BizCustomList list= getObjCustomList();
		BizCampo c=list.getFirstCampo();
		if (c==null) return 0;
		return c.getSecuencia();
	}
}
