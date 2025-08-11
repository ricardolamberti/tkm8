package pss.common.customList.config.carpetas;

import pss.core.win.actions.BizAction;
import pss.core.win.actions.BizActions;
import pss.core.win.submits.JAct;

public interface IContenidoCarpeta {
	public boolean read(String company,String id) throws Exception;
	public JAct getActResultPreview(BizAction a,boolean inform) throws Exception;
	public int GetNroIcono()   throws Exception;
  public void createActionMap() throws Exception;
  public JAct getSubmitFor(BizAction a) throws Exception;
  public boolean OkAction(BizAction a) throws Exception;
  public boolean isSystemProtect() throws Exception;
  public String imprimir(BizAction a) throws Exception;
  public BizActions getRawActionMap() throws Exception;

}
