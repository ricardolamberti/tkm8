package pss.common.customList.config.carpetas;


import java.util.Date;

import com.ibm.icu.util.Calendar;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.structure.ROrderBy;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

public class BizCarpeta extends JRecord   {

  
	
	private JString pCompany = new JString();
	private JLong pSecuencia = new JLong();
  private JLong pSystemProtect = new JLong();
	private JString pClase = new JString();
	private JLong pListado = new JLong();
	private JString pKey = new JString();
	private JLong pParent = new JLong();
	private JLong pOrden = new JLong();
	private JDateTime pLastUpdate = new JDateTime();
	private JBoolean pInvisible = new JBoolean();
	private JBoolean pOnlyOwner = new JBoolean();
	private JString pDescripcion = new JString();
	private JString pOwner = new JString();
	private JString pEspecial = new JString();
	private JString pNavegacion = new JString();
	private JString pDescripcionView = new JString() {
		public void preset() throws Exception {
			pDescripcionView.setValue(getDescripcionView());
		};
	};
	private JString pDescripcionProtect = new JString() {
		public void preset() throws Exception {
			pDescripcionProtect.setValue(getDescripcionProtect());
		};
	};
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public void setSystemProtect(long zValue) throws Exception {    pSystemProtect.setValue(zValue);  }
  public void setNullSystemProtect() throws Exception {    pSystemProtect.setNull();  }
  public long getSystemProtect() throws Exception {     return pSystemProtect.getValue();  }
  public boolean isSystemProtect() throws Exception {     return !(pSystemProtect.isNull() || pSystemProtect.getValue()==0);  }

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}
	public void setOwner(String zValue) throws Exception {
		pOwner.setValue(zValue);
	}
	public boolean hasOwner() throws Exception {
		return pOwner.isNotNull();
	}
	public String getOwner() throws Exception {
		return pOwner.getValue();
	}
	public void setNavegacion(String zValue) throws Exception {
		pNavegacion.setValue(zValue);
	}

	public String getNavegacion() throws Exception {
		return pNavegacion.getValue();
	}
	public String getClase() throws Exception {
		return pClase.getValue();
	}
	public boolean hasClase() throws Exception {
		return !(pClase.isNull() || pClase.getValue().equals(""));
	}
	public void setClase(String zValue) throws Exception {
		pClase.setValue(zValue);
	}
	public void setSecuencia(long zValue) throws Exception {
		pSecuencia.setValue(zValue);
	}

	public long getSecuencia() throws Exception {
		return pSecuencia.getValue();
	}
	public void setOrden(long zValue) throws Exception {
		pOrden.setValue(zValue);
	}
  public void setLastUpdate(Date zValue) throws Exception {    pLastUpdate.setValue(zValue);  }
  public Date getLastUpdate() throws Exception {     return pLastUpdate.getValue();  }

	public long getOrden() throws Exception {
		return pOrden.getValue();
	}
	public void setPadre(long zValue) throws Exception {
		pParent.setValue(zValue);
	}
	public long getPadre() throws Exception {
		return pParent.getValue();
	}
	public void setParent(long zValue) throws Exception {
		pParent.setValue(zValue);
	}

	public long getListado() throws Exception {
		return pListado.getValue();
	}
	public void setListado(long zValue) throws Exception {
		pListado.setValue(zValue);
	}
	public String getKey() throws Exception {
		if (pKey.isNull()) return ""+pListado.getValue();
		return pKey.getValue();
	}
	public void setKey(String zValue) throws Exception {
		pKey.setValue(zValue);
	}

	public boolean isInvisible() throws Exception {
		return pInvisible.getValue();
	}
	public void setInvisible(boolean zValue) throws Exception {
		pInvisible.setValue(zValue);
	}
	public String getDescripcion() throws Exception {
		return pDescripcion.getValue();
	}
	
	public void setDescripcion(String zValue) throws Exception {
		pDescripcion.setValue(zValue);
	}
	public String getEspecial() throws Exception {
		return pEspecial.getValue();
	}
	public boolean isEspecial() throws Exception {
		return !(pEspecial.isNull() || pEspecial.equals(""));
	}	
	public void setEspecial(String zValue) throws Exception {
		pEspecial.setValue(zValue);
	}	
	public long getCustomList() throws Exception {
		return pListado.getValue();
	}
	
	
	public String getDescripcionView() throws Exception {
		if (pListado.isNull())
			return pDescripcion.getValue();
		if (!getEspecial().equals("") && !pDescripcion.getValue().equals(""))
			return pDescripcion.getValue();
		if (getObjCustomList()==null)
			return "";
		return getObjCustomList().getDescripcion();
	}
	public String getDescripcionProtect() throws Exception {
		if (getObjCarpetaPadre()==null) return "";
		return getObjCarpetaPadre().getCompany()+" - "+getObjCarpetaPadre().getDescripcion();
  }

	
	private BizCarpeta objCarpetaPadre;
	public BizCarpeta getObjCarpetaPadre() throws Exception {
		if (this.objCarpetaPadre != null) return this.objCarpetaPadre;
		if (this.pSystemProtect.isNull()) return null;

		BizCarpeta r = new BizCarpeta();
		r.dontThrowException(true);
		if (!r.read( this.pSystemProtect.getValue())) return null;
		return (this.objCarpetaPadre = r);
	}
	
	private BizCustomList objCustomList;
	public BizCustomList getObjCustomList() throws Exception {
		if (this.objCustomList != null) return this.objCustomList;
		BizCustomList r = new BizCustomList();
		r.dontThrowException(true);
		if (!r.read(this.pCompany.getValue(), this.pListado.getValue())) return null;
		return (this.objCustomList = r);
	}
	/**
	 * Constructor de la Clase
	 */
	public BizCarpeta() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("secuencia", pSecuencia);
	  this.addItem("system_protect", pSystemProtect );
		this.addItem("padre", pParent);
		this.addItem("orden", pOrden);
		this.addItem("invisible", pInvisible);
		this.addItem("clase", pClase);
		this.addItem("last_update", pLastUpdate);
		this.addItem("customlist", pListado);
		this.addItem("key_especial", pKey);
		this.addItem("carp_owner", pOwner);
		this.addItem("descripcion", pDescripcion);
		this.addItem("especial", pEspecial);
		this.addItem("descripcion_view", pDescripcionView);
		this.addItem("descripcion_protect", pDescripcionProtect);
		this.addItem("nav", pNavegacion);
		this.addItem("only_owner", pOnlyOwner);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 15);
		this.addFixedItem(KEY, "secuencia", "Secuencia", false, false, 64);
	  this.addFixedItem(FIELD, "system_protect", "De sistema", true, false, 64); 
		this.addFixedItem(FIELD, "padre", "padre", true, false, 64);
		this.addFixedItem(FIELD, "orden", "orden", true, true, 64);
		this.addFixedItem(FIELD, "carp_owner", "dueño", true, false, 50);
		this.addFixedItem(FIELD, "invisible", "invisible", true, false, 1);
		this.addFixedItem(FIELD, "clase", "Clase", true, false, 500);
		this.addFixedItem(FIELD, "last_update", "Ultima act.", true, false, 500);
		this.addFixedItem(FIELD, "customlist", "id listado", true, false, 64);
		this.addFixedItem(FIELD, "key_especial", "Clave acceso", true, false, 64);
		this.addFixedItem(FIELD, "descripcion", "Descripcion", true, false, 200);
		this.addFixedItem(FIELD, "especial", "Carpeta especial", true, false, 200);
		this.addFixedItem(VIRTUAL, "descripcion_view", "Descripcion", true, false, 200);
		this.addFixedItem(VIRTUAL, "descripcion_protect", "padre", true, false, 200);
		this.addFixedItem(VIRTUAL, "nav", "navegacion", true, false, 400);
		this.addFixedItem(VIRTUAL, "only_owner", "Solo mios", true, false, 400);
	}
	

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "LST_CARPETASV2";
	}

	@Override
	public String GetTableTemporal() throws Exception {
	  if (!GetVision().equals(GuiCarpetas.VISION_NAV)) return super.GetTableTemporal();
		JList< ROrderBy > orden = getStructure().getOrderBy();
		String strOrden = "last_update";
		String strSentido = "DESC";
		String marcarParent = "Z";
		String marcaChild = "A";
		if (orden.size()>0) {
			strOrden= orden.getElementAt(0).GetCampo();
			strSentido = orden.getElementAt(0).GetOrden();
			if (strSentido.equals("ASC")) {
				marcarParent = "A";
				marcaChild = "Z";
			}

			getStructure().clearOrderBy();
		}
		String sql = ""; 
		sql+= "		WITH RECURSIVE node_rec AS ( ";
		sql+= "		   (SELECT 1 AS depth, coalesce("+strOrden+" || '_' || secuencia::text || '"+marcarParent+"','2000-01-01') AS path, coalesce("+strOrden+" || '_' || secuencia::text || '"+marcaChild+"','2000-01-01') AS pathc, * ";
		sql+= "		    FROM   LST_CARPETASV2 ";
		sql+= "		    WHERE  padre IS NULL  ";
		sql+= "		    )     ";
		sql+= "		    UNION ALL  ";
		sql+= "		    SELECT r.depth + 1, coalesce(r.pathc || '_' || n."+strOrden+" || '_' ||  n.secuencia::text || '"+marcarParent+"','2000-01-01'), coalesce(r.path || '_' || n."+strOrden+" || '_' ||  n.secuencia::text || '"+marcaChild+"','2000-01-01') , n.* ";
		sql+= "		    FROM   node_rec r  ";
		sql+= "		    JOIN   LST_CARPETASV2    n ON n.padre = r.secuencia ";
		sql+= "		    WHERE  r.depth < 4    ";
		sql+= "		)  ";
		sql+= "		SELECT * ";
		sql+= "		FROM   node_rec ";
		sql+= "		ORDER  BY path "+strSentido+", orden "+strSentido;
		
		return "("+sql+ ") as "+GetTable(); 
	}
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(long zSec) throws Exception {
		addFilter("secuencia", zSec);
		return read();
	}
	public boolean read(String zCompany, String zCampo, JFilterMap param) throws Exception {
  	dontThrowException(true);
		addFilter("company", zCompany);
		addFilter("secuencia", zCampo);
		return read();
	}
	public boolean readBySystemProtect(String zCompany, long zSp) throws Exception {
		addFilter("company", zCompany);
		addFilter("system_protect", zSp);
		return read();
	}
	public boolean read(String zCompany, long zListId) throws Exception {
		addFilter("company", zCompany);
		addFilter("customlist", zListId);
		return read();
	}
  public void execAddToFolder(final BizCarpeta folder) throws Exception {
  	JExec exec = new JExec(null,null) {
  		public void Do() throws Exception {
  			processcAddToFolder(folder);
  		}
  	};
  	exec.execute();
  }
  public void execMoveToFolder(final BizCarpeta folder) throws Exception {
  	JExec exec = new JExec(null,null) {
  		public void Do() throws Exception {
  			processMoveToFolder(folder);
  		}
  	};
  	exec.execute();
  }
  public BizCarpeta getObjWithoutRead() throws Exception { 
  	BizCarpeta sql = BizUsuario.getUsr().getObjBusiness().getCarpeta().GetcDato();
  	sql.copyProperties(this);
    return sql; 
  }
	public void processMoveToFolder(BizCarpeta folder) throws Exception {
		processcAddToFolder(folder);
	}
	
	public void processcCopyToFolder(BizCarpeta folder) throws Exception {
		
		if (this.getSecuencia()==folder.getSecuencia())
			return ;// padre e hijo son la misma carpeta
		if (folder.isCarpeta()) {
			BizCarpeta carpeta = new BizCarpeta();
			carpeta.setCompany(folder.getCompany());
			carpeta.setListado(this.getListado());
			carpeta.setKey(this.getKey());
			carpeta.setInvisible(folder.isInvisible());
			carpeta.setDescripcion(this.getDescripcion());
			carpeta.setClase(this.getClase());
			carpeta.setPadre(folder.getSecuencia());
			carpeta.setEspecial(folder.getEspecial());
			carpeta.setLastUpdate(new Date());
			carpeta.processInsert();
		} else {
			BizCarpeta carpeta = new BizCarpeta();
			carpeta.setCompany(folder.getCompany());
			carpeta.setListado(this.getListado());
			carpeta.setKey(this.getKey());
			carpeta.setInvisible(folder.isInvisible());
			carpeta.setClase(this.getClase());
			carpeta.setDescripcion(this.getDescripcion());
			carpeta.setLastUpdate(new Date());
			carpeta.setEspecial(folder.getEspecial());
			if (folder.getPadre()==0)
		  	carpeta.pParent.setNull();
		  else
		  	carpeta.setPadre(folder.getPadre());// si son dos archivos los hago hermanos
			carpeta.processInsert();
		}
	}
	public void processcAddToFolder(BizCarpeta folder) throws Exception {
		
		if (this.getSecuencia()==folder.getSecuencia())
			return ;// padre e hijo son la misma carpeta
		if (folder.isCarpeta())
			setPadre(folder.getSecuencia()); // si es carpeta es el padre
		else {
		  if (folder.getPadre()==0)
		  	pParent.setNull();
		  else
		  	setPadre(folder.getPadre());// si son dos archivos los hago hermanos
		}
		processUpdate();
	}

	public void execProcessInsert(final BizCustomList listado) throws Exception {
		JExec oExec = new JExec(this, "processInsert") {

			@Override
			public void Do() throws Exception {
				processInsert(listado);
			}
		};
		oExec.execute();
	}
	public void processInsert(final BizCustomList listado) throws Exception {
		listado.processUpdateOrInsertWithCheck();
		setClase(GuiCustomList.class.getName());
		setListado(listado.getListId());
		setLastUpdate(new Date());
		setDescripcion(listado.getDescripcion());
		processInsert();
	}
	
	public void processInsert() throws Exception {
  	if (pInvisible.isNull())
  		pInvisible.setValue(false);
  	if (pCompany.isNull())
  		pCompany.setValue(BizUsuario.getUsr().getCompany());
 		if (pOwner.isNull())
 			pOwner.setValue(BizUsuario.getUsr().GetUsuario());

		if (pOrden.isNull()) {
			BizCarpeta max = new BizCarpeta();
			max.addFilter("company", this.pCompany.getValue());
			pOrden.setValue(max.SelectMaxLong("orden") + 1);
		}
		setLastUpdate(new Date());
		super.processInsert();
		clean();
	}

	@Override
	public void processUpdate() throws Exception {
  	if (pInvisible.isNull())
  		pInvisible.setValue(false);
 		setLastUpdate(new Date());
 		if (pOwner.isNull())
 			pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processUpdate();
		clean();
	}
	
  public void execSubir() throws Exception {
  	JExec exec = new JExec(null,null) {
  		public void Do() throws Exception {
  			subir();
  		}
  	};
  	exec.execute();
  }
  
  public void subir() throws Exception {
  	BizCarpeta max = new BizCarpeta();
  	max.addFilter("company", this.getCompany());
  	max.addFilter("orden", this.pOrden.getValue(), "<");
  	long orden = max.SelectMaxLong("orden");
 
  	max = new BizCarpeta();
  	max.addFilter("company", this.getCompany());
  	max.addFilter("orden", orden);
  	max.dontThrowException(true);
  	if (!max.read()) return;
  	
  	max.pOrden.setValue(this.pOrden.getValue());
  	this.pOrden.setValue(orden);
  	max.update();
  	this.update();
  }

  public void execBajar() throws Exception {
  	JExec exec = new JExec(null,null) {
  		public void Do() throws Exception {
  			bajar();
  		}
  	};
  	exec.execute();
  }
  
  public void bajar() throws Exception {
  	BizCarpeta min = new BizCarpeta();
  	min.addFilter("company", this.getCompany());
  	min.addFilter("orden", this.pOrden.getValue(), ">");
  	long orden = min.SelectMinLong("orden");
  	min = new BizCarpeta();
  	min.addFilter("company", this.getCompany());
  	min.addFilter("orden", orden);
  	min.dontThrowException(true);
  	if (!min.read()) return;
  	min.pOrden.setValue(this.pOrden.getValue());
  	this.pOrden.setValue(orden);
  	min.update();
  	this.update();
  }
  
	public void execProcessCompletarCarpetas() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processCompletarCarpetas();
			}
		};
		oExec.execute();
	}

	public boolean isCarpeta() throws Exception {
		return pListado.isNull();
	}
	
	public JRecords<BizCarpeta> getContenidos() throws Exception {
		JRecords<BizCarpeta> carp = new JRecords<BizCarpeta>(BizCarpeta.class);
		carp.addFilter("company", getCompany());
		carp.addFilter("padre", getSecuencia());
		if (!BizUsuario.IsAdminCompanyUser()){
			carp.addFilter("invisible", "S","<>");
			carp.addFixedFilter(
					"where "+
			    " (NOT EXISTS(SELECT 1 FROM LST_OWNERV2 WHERE LST_OWNERV2.company=LST_CARPETASV2.company and LST_OWNERV2.list_id=LST_CARPETASV2.customlist )  "+
					" OR EXISTS(SELECT 1 FROM LST_OWNERV2 WHERE LST_OWNERV2.company=LST_CARPETASV2.company and LST_OWNERV2.list_id=LST_CARPETASV2.customlist and LST_OWNERV2.usuario='"+BizUsuario.getUsr().GetUsuario()+"' )) ");
		}
		carp.addOrderBy("orden","DESC");
		return carp;
	}
	public void processRepararCarpetas() throws Exception {

		JRecords<BizCarpeta>  recsD = new JRecords<BizCarpeta>(BizCarpeta.class);
		recsD.addFilter("company", BizUsuario.getUsr().getCompany());
		recsD.addFixedFilter("where customlist is null ");
		JIterator<BizCarpeta>itD = recsD.getStaticIterator();
		while (itD.hasMoreElements()) {
			BizCarpeta carpeta = itD.nextElement();
			String company = carpeta.getCompany();
			String parentCompany = BizCompany.getObjCompany(company).getParentCompany();
			if (parentCompany==null) continue;
			if (carpeta.isSystemProtect()) continue;
			BizCarpeta carpPadre = new BizCarpeta();
			carpPadre.dontThrowException(true);
			carpPadre.addFilter("company", parentCompany);
			carpPadre.addFilter("descripcion", carpeta.getDescripcion());
			if (!carpPadre.read()) continue;
			
			carpeta.setSystemProtect(carpPadre.getSecuencia());
			carpeta.processUpdate();
		}
		
		
	}		
	public void processCompletarCarpetas() throws Exception {
		// agrego fecha de actualizacion a los que vienen de la version anterior
		JRecords<BizCarpeta> recsT = new JRecords<BizCarpeta>(BizCarpeta.class);
		recsT.addFilter("company", BizUsuario.getUsr().getCompany());
		recsT.addFixedFilter("where last_update is null  ");
		JIterator<BizCarpeta>itT = recsT.getStaticIterator();
		while (itT.hasMoreElements()) {
			BizCarpeta carpeta = itT.nextElement();

  		Calendar c = Calendar.getInstance();
  		c.set(2014, 1, 1);
  		c.add(Calendar.SECOND, (int)(100000-getOrden()));
   		carpeta.setLastUpdate(c.getTime()); // for migration
   		carpeta.update();
		}
		//processRepararCarpetas();
		//agrego las que faltan
		JRecords<BizCustomList> recs = new JRecords<BizCustomList>(BizCustomList.class);
		recs.addFilter("company", BizUsuario.getUsr().getCompany());
		recs.addFixedFilter("where list_id not in (select customlist from LST_CARPETASV2 where LST_CUSTOM_LISTV2.list_id=LST_CARPETASV2.customlist and LST_CUSTOM_LISTV2.company=LST_CARPETASV2.company and LST_CARPETASV2.company='"+BizUsuario.getUsr().getCompany()+"' ) ");
		recs.addOrderBy("description","DESC");
		JIterator<BizCustomList>it = recs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCustomList c = it.nextElement();
			BizCarpeta carpeta = new BizCarpeta();
			carpeta.setCompany(c.getCompany());
			carpeta.setListado(c.getListId());
			carpeta.setInvisible(c.isInvisible());
			carpeta.setDescripcion(c.getDescripcion());
			carpeta.processInsert();
		}
		// ajusto las diferencias
		JRecords<BizCarpeta>  recsD = new JRecords<BizCarpeta>(BizCarpeta.class);
		recsD.addFilter("company", BizUsuario.getUsr().getCompany());
		recsD.addFixedFilter("where especial is null and customlist in (select list_id from LST_CUSTOM_LISTV2 where LST_CUSTOM_LISTV2.description<>LST_CARPETASV2.descripcion and LST_CUSTOM_LISTV2.company=LST_CARPETASV2.company and LST_CUSTOM_LISTV2.list_id=LST_CARPETASV2.customlist and LST_CARPETASV2.company='"+BizUsuario.getUsr().getCompany()+"' ) ");
		JIterator<BizCarpeta>itD = recsD.getStaticIterator();
		while (itD.hasMoreElements()) {
			BizCarpeta carpeta = itD.nextElement();
			if (carpeta.getObjCustomList()==null) {
				carpeta.delete();
				continue;
			}
			if (carpeta.pLastUpdate.isNull()||!carpeta.getDescripcion().equals(carpeta.getObjCustomList().getDescripcion())) {
				carpeta.setDescripcion(carpeta.getObjCustomList().getDescripcion());
				carpeta.processUpdate();
			}
		}
		// borro lo sobrante
		JRecords<BizCarpeta> carpetas = new JRecords<BizCarpeta>(BizCarpeta.class);
		carpetas.addFilter("company", BizUsuario.getUsr().getCompany());
		carpetas.addFixedFilter("where LST_CARPETASV2.customlist is not null and LST_CARPETASV2.especial is null and customlist not in (select customlist from LST_CUSTOM_LISTV2 where LST_CUSTOM_LISTV2.list_id=LST_CARPETASV2.customlist and LST_CUSTOM_LISTV2.company=LST_CARPETASV2.company and LST_CUSTOM_LISTV2.company='"+BizUsuario.getUsr().getCompany()+"') ");
		carpetas.delete();

		
	}
	
	IContenidoCarpeta objClass;
	
	public IContenidoCarpeta getObjClass() throws Exception {
		if (objClass!=null) return objClass;
		if (pClase.isNull()) setClase(GuiCustomList.class.getName());
		IContenidoCarpeta obj = (IContenidoCarpeta) Class.forName(getClase()).newInstance();
		if (!getCompany().equals("")&&!getKey().equals("")) {
			if (obj.read(getCompany(), getKey()))
				objClass=obj;
		}
		return obj;
	}

	

	
	@Override
	public void processDelete() throws Exception {
		if (isCarpeta()) {
			JRecords<BizCarpeta> listas = getContenidos();
			long count = listas.selectCount();
			if (count>0) 
				throw new Exception("No se puede borrar carpeta con contenido");
			super.processDelete();
		} else {
			if (isEspecial()) {
				super.processDelete();
				return;
			}
			
			processBorrarCarpetasAsociadas();
			BizCustomList cl = getObjCustomList();
			if (cl!=null)
				cl.processDelete();
			else
				super.processDelete();
		}
		

	}
	
	public void processBorrarCarpetasAsociadas() throws Exception {
		JRecords<BizCarpeta> recs = new JRecords<BizCarpeta>(BizCarpeta.class);
		recs.addFilter("company", getCompany());
		recs.addFilter("customlist", getListado());
		JIterator<BizCarpeta> it = recs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCarpeta oBD = it.nextElement();
			if (oBD.getSecuencia()==this.getSecuencia()) continue;
			if (!oBD.isEspecial()) continue;
			BizCarpeta carp = BizUsuario.getUsr().getObjBusiness().getCarpeta().GetcDato();
			carp.copyProperties(oBD);
			carp.processDelete();
		}
	}
	
	public void execAgregarTodosLosHijos() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processAgregarTodosLosHijos();
			}
		};
		oExec.execute();
	}
	public BizCarpeta findPadre() throws Exception {
		if (this.pParent.isNull() || this.pParent.getValue()==0) return null;
		BizCarpeta carpPadre = new BizCarpeta();
		carpPadre.dontThrowException(true);
		carpPadre.addFilter("company", this.getCompany());
		carpPadre.addFilter("system_protect", this.getPadre());
		if (!carpPadre.read()) {
			return null;
		}
		return carpPadre;
		
	}
	public void processAgregarTodosLosHijos() throws Exception {
		JRecords<BizCompany> lista = new JRecords<BizCompany>(BizCompany.class);
		lista.addFilter("parent_company", getCompany());
		// busco todos los hijos
		JIterator<BizCompany> it = lista.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCompany comp = it.nextElement();
			BizCarpeta oldEvent = new BizCarpeta();
			oldEvent.dontThrowException(true);
			oldEvent.addFilter("company", comp.getCompany());
			oldEvent.addFilter("system_protect", this.getSecuencia());
			// el hijo existe?
			BizCarpeta oNewCarpea=new BizCarpeta();
			if (!oldEvent.read()) {
				// si no existe lo creo
				oNewCarpea.copyProperties(this);
				oNewCarpea.setCompany(comp.getCompany());
				BizCarpeta padre = findPadre();
				if (padre!=null) oNewCarpea.setPadre(padre.getSecuencia());
				oNewCarpea.setSystemProtect(this.getSecuencia());
				oNewCarpea.pSecuencia.setNull();
				oNewCarpea.processInsert();
			} else {
				oNewCarpea=oldEvent;
			}

			// agrego los hijos
			processAgregarTodosLosHijosDeHijos(this,oNewCarpea);
		}
		
	}
	public void processAgregarTodosLosHijosDeHijos(BizCarpeta ramaACopiar,BizCarpeta ramaCopiada) throws Exception {
		JRecords<BizCarpeta> lista = new JRecords<BizCarpeta>(BizCarpeta.class);
		lista.addFilter("company", ramaACopiar.getCompany());
		lista.addFilter("padre", ramaACopiar.getSecuencia());
		// recorrer lo hijos
		JIterator<BizCarpeta> it = lista.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCarpeta comp = it.nextElement();
			BizCarpeta oldEvent = new BizCarpeta();
			oldEvent.dontThrowException(true);
			oldEvent.addFilter("company", ramaCopiada.getCompany());
			oldEvent.addFilter("descripcion", comp.getDescripcion());
			BizCarpeta oNewCarpeta=new BizCarpeta();
			if (oldEvent.read()) {
				oNewCarpeta=oldEvent;
				oNewCarpeta.setPadre(ramaCopiada.getSecuencia());
				oNewCarpeta.processUpdate();
			} else {
				oNewCarpeta.copyProperties(comp);
				oNewCarpeta.setPadre(ramaCopiada.getSecuencia());
				oNewCarpeta.setCompany(ramaCopiada.getCompany());
				oNewCarpeta.setSystemProtect(comp.getSecuencia());
				oNewCarpeta.pSecuencia.setNull();
				oNewCarpeta.processInsert();
			}
			
			if (oNewCarpeta.isCarpeta()) {
				processAgregarTodosLosHijosDeHijos(oldEvent,oNewCarpeta);
			} else {
				BizCustomList clramaACopiar = comp.getObjCustomList();
				BizCustomList clramaCopiada = new BizCustomList();
				clramaCopiada.addFilter("company", ramaCopiada.getCompany());
				clramaCopiada.addFilter("system_protect", clramaACopiar.getListId());
				clramaCopiada.dontThrowException(true);
				if (!clramaCopiada.read()) {
					BizCustomList cln = clramaACopiar.processClon(ramaCopiada.getCompany(), true);
					oNewCarpeta.setListado(cln.getListId());
				} else {
					oNewCarpeta.setListado(clramaCopiada.getListId());
					clramaACopiar.processCambiarUnHijos(clramaCopiada);
				}

				oNewCarpeta.processUpdate();
			}


		}
		
	}
	public void execBorrarTodosLosHijos() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processBorrarTodosLosHijos();
			}
		};
		oExec.execute();
	}
	public void processBorrarTodosLosHijos() throws Exception {
		JRecords<BizCompany> lista = new JRecords<BizCompany>(BizCompany.class);
		lista.addFilter("parent_company", getCompany());
		JIterator<BizCompany> it = lista.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCompany comp = it.nextElement();
			BizCarpeta oldEvent = new BizCarpeta();
			oldEvent.dontThrowException(true);
			oldEvent.addFilter("company", comp.getCompany());
			oldEvent.addFilter("descripcion", this.getDescripcion());
			if (!oldEvent.exists()) continue;
			oldEvent.processDelete();
		}
		
	}
	
	public BizCarpeta processClon(String company, boolean alhijo) throws Exception {
		BizCarpeta oNewCustomList=new BizCarpeta();
		oNewCustomList.copyProperties(this);
		oNewCustomList.setCompany(company);
		if (alhijo) oNewCustomList.setSystemProtect(this.getSecuencia());
		return this.processClon(oNewCustomList);
	}

	public BizCarpeta processClon(BizCarpeta newDoc) throws Exception {
//	newDoc.copyProperties(this);
	if (getCompany().equals(newDoc.getCompany()))
			newDoc.setNullSystemProtect();
	newDoc.processInsert();
	
	return newDoc;
}

	public String imprimir() throws Exception {
		return null;
	}

}
