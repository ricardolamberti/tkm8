package pss.core.win;

import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;


public class GuiVirtual extends JWin {


  public GuiVirtual() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizVirtual(); }
  @Override
	public String GetTitle()       throws Exception { return ""; }
  @Override
	public String getKeyField()   throws Exception { return "valor"; }
  @Override
	public String getDescripField()                  { return "descripcion"; }


  @Override
	public void createActionMap() throws Exception {
  }

  public BizVirtual GetcDato() throws Exception {
    return (BizVirtual) this.getRecord();
  }

  boolean itemSeparador;

  @Override
	public int GetNroIcono() throws Exception {
    return GetcDato().getIcono();
  }
	@Override
	public String GetIconFile(long size) throws Exception {
		if (GetcDato().hasIconoString())
			return GetcDato().getIconoString();
		return super.GetIconFile(size);
	}
  public void setItemSeparador(boolean zAdd) {
  	itemSeparador=zAdd;
  }
  @Override
  public boolean addItemSeparator() throws Exception {
  	return itemSeparador;
  }
  
  public boolean isYes() throws Exception {
  	return GetcDato().getValor().equals("S");
  }
  public boolean isNo() throws Exception {
  	return GetcDato().getValor().equals("N");
  }
  public boolean isCancel() throws Exception {
  	return GetcDato().getValor().equals("C");
  }
}
