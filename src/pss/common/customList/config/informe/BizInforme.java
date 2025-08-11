package pss.common.customList.config.informe;


import java.awt.Rectangle;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.regions.company.BizCompany;
import pss.core.services.JExec;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.winUI.responsiveControls.IResizableView;

public class BizInforme extends JRecord  implements IResizableView {



	
	private JString pCompany = new JString();
	private JLong pListId = new JLong();
	private JLong pSecuencia = new JLong();
	private JLong pOrden = new JLong();
	private JLong pInforme = new JLong();
	private JFloat pPosX = new JFloat();
	private JFloat pPosY = new JFloat();
	private JFloat pWidth = new JFloat();
	private JFloat pHeight = new JFloat();
	private JString pDescripcion = new JString() {
		public void preset() throws Exception {
			pDescripcion.setValue(getDescripcion());
		};
	};

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setListId(long zValue) throws Exception {
		pListId.setValue(zValue);
	}

	public long getListId() throws Exception {
		return pListId.getValue();
	}
	
	public void setInforme(long zValue) throws Exception {
		pInforme.setValue(zValue);
	}

	public long getInforme() throws Exception {
		return pInforme.getValue();
	}

	public void setRectangle(long x,long y,long w,long h) throws Exception {
		pPosX.setValue(x);
		pPosY.setValue(y);
		pWidth.setValue(w);
		pHeight.setValue(h);
	}
	public void setRectangle(Rectangle r) throws Exception {
		pPosX.setValue(r.getX());
		pPosY.setValue(r.getY());
		pWidth.setValue(r.getWidth());
		pHeight.setValue(r.getHeight());
	}

	public Rectangle getRectangle() throws Exception {
		return new Rectangle((int)pPosX.getValue(),(int)pPosY.getValue(),(int)pWidth.getValue(),(int)pHeight.getValue());
	}
	public String getDescripcion() throws Exception {
		if (getObjCustomList()==null) return "";
		return getObjCustomList().getDescription();
	}
	private BizCustomList parentCustomList;
	public BizCustomList getObjParentCustomList() throws Exception {
		if (this.parentCustomList != null) return this.parentCustomList;
		if (this.getGrandParent()!=null) return this.parentCustomList=(BizCustomList)this.getGrandParent();
		BizCustomList r = new BizCustomList();
		r.read(this.pCompany.getValue(), this.pListId.getValue());
		return (this.parentCustomList = r);
	}
	public void setObjParentCustomList(BizCustomList r) throws Exception {
		this.parentCustomList = r;
	}
	private BizCustomList customList;
	public void setObjCustomList(BizCustomList r) throws Exception {
		this.customList = r;
	}
	public BizCustomList getObjCustomList() throws Exception {
		if (this.customList != null) return this.customList;
		if (pInforme.isNull()) return null;
		BizCustomList r = new BizCustomList();
		r.read(this.pCompany.getValue(), this.pInforme.getValue());
		r.setStatic(true);
		return (this.customList = r);
	}
	/**
	 * Constructor de la Clase
	 */
	public BizInforme() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("list_id", pListId);
		this.addItem("secuencia", pSecuencia);
		this.addItem("informe", pInforme);
		this.addItem("orden", pOrden);
		this.addItem("posx", pPosX);
		this.addItem("posy", pPosY);
		this.addItem("width", pWidth);
		this.addItem("height", pHeight);
		this.addItem("descripcion", pDescripcion);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 15);
		this.addFixedItem(KEY, "list_id", "List id", true, true, 64);
		this.addFixedItem(KEY, "secuencia", "Secuencia", false, false, 64);
		this.addFixedItem(FIELD, "informe", "Informe", true, false, 5);
		this.addFixedItem(FIELD, "orden", "Orden", true, true, 5);
		this.addFixedItem(FIELD, "posx", "Pos X", true, true, 18);
		this.addFixedItem(FIELD, "posy", "Pos Y", true, true, 18);
		this.addFixedItem(FIELD, "width", "Ancho", true, true, 18);
		this.addFixedItem(FIELD, "height", "Alto", true, true, 18);
		this.addFixedItem(VIRTUAL, "Descripcion", "Descripcion", true, false, 200);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "LST_INFORME";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(String zCompany, long zListId, String zCampo) throws Exception {
		addFilter("company", zCompany);
		addFilter("list_id", zListId);
		addFilter("secuencia", zCampo);
		return read();
	}

	@Override
	public void processDelete() throws Exception {
		getObjParentCustomList().removeInforme(this);

	}
	
	@Override
	public void processDeleteTable() throws Exception {
		if (pSecuencia.isNull()) return;
		if (getObjCustomList().isInvisible())
			getObjCustomList().processDelete();
		super.processDeleteTable();
	}


	public void processInsert() throws Exception {

		if (pOrden.isNull()) {
			BizInforme max = new BizInforme();
			max.addFilter("company", this.pCompany.getValue());
			max.addFilter("list_id", this.pListId.getValue());
			pOrden.setValue(max.SelectMaxLong("orden") + 1);
		}
		setRectangle(10,10,100,500);//revisar
	}
	
	@Override
	public void processInsertTable() throws Exception {
		if (pOrden.isNull()) {
			BizInforme max = new BizInforme();
			max.addFilter("company", this.pCompany.getValue());
			max.addFilter("list_id", this.pListId.getValue());
			pOrden.setValue(max.SelectMaxLong("orden") + 1);
		}
			getObjCustomList().processUpdateOrInsertWithCheck();
		setInforme(getObjCustomList().getListId());
		super.processInsertTable();
		clean();
//		BizCompany.getCompany(getCompany()).getObjBusiness().getCarpeta().GetcDato().processCompletarCarpetas();

	}

	@Override
	public void processUpdate() throws Exception {
		super.processUpdate();
	}
	@Override
	public void processUpdateTable() throws Exception {
		getObjCustomList().processUpdateOrInsertWithCheck();
		setInforme(getObjCustomList().getListId());
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
  	BizInforme max = new BizInforme();
  	max.addFilter("company", this.getCompany());
  	max.addFilter("list_id", this.getListId());
  	max.addFilter("orden", this.pOrden.getValue(), "<");
  	long orden = max.SelectMaxLong("orden");
  	max = new BizInforme();
  	max.addFilter("company", this.getCompany());
  	max.addFilter("list_id", this.getListId());
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
  	BizInforme min = new BizInforme();
  	min.addFilter("company", this.getCompany());
  	min.addFilter("list_id", this.getListId());
  	min.addFilter("orden", this.pOrden.getValue(), ">");
  	long orden = min.SelectMinLong("orden");
  	min = new BizInforme();
  	min.addFilter("company", this.getCompany());
  	min.addFilter("list_id", this.getListId());
  	min.addFilter("orden", orden);
  	min.dontThrowException(true);
  	if (!min.read()) return;
  	min.pOrden.setValue(this.pOrden.getValue());
  	this.pOrden.setValue(orden);
  	min.update();
  	this.update();
  }

	@Override
	public void setPosX(double zValue) throws Exception {
		pPosX.setValue(zValue);
	}

	@Override
	public void setNullPosX() throws Exception {
		pPosX.setNull();
	}

	@Override
	public double getPosX() throws Exception {
		return pPosX.getValue();
	}

	@Override
	public boolean isNullPosX() throws Exception {
		return pPosX.isNull();
	}

	@Override
	public void setPosY(double zValue) throws Exception {
		pPosY.setValue(zValue);
	}

	@Override
	public void setNullPosY() throws Exception {
		pPosY.setNull();

	}

	@Override
	public double getPosY() throws Exception {
		return pPosY.getValue();

	}

	@Override
	public boolean isNullPosY() throws Exception {
		return pPosY.isNull();
	}

	@Override
	public void setWidth(double zValue) throws Exception {
		pWidth.setValue(zValue);

	}

	@Override
	public void setNullWidth() throws Exception {
		pWidth.setNull();
	}

	@Override
	public double getWidth() throws Exception {
		return pWidth.getValue();
	}

	@Override
	public boolean isNullWidth() throws Exception {
		return pWidth.isNull();
	}

	@Override
	public void setHeight(double zValue) throws Exception {
		pHeight.setValue(zValue);
	}

	@Override
	public void setNullHeight() throws Exception {
		pHeight.setNull();

	}

	@Override
	public double getHeight() throws Exception {
		return pHeight.getValue();
	}

	@Override
	public boolean isNullHeight() throws Exception {
		return pHeight.isNull();
	}
  
	
}
