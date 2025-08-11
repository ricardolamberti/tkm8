package pss.core.connectivity.messageManager.common.message;

import pss.core.tools.JExcepcion;

public class MsgMngrExcepcion extends JExcepcion {
  private static final long serialVersionUID = 8752588020481144381L;
	EvtError.EvtErrorCode evtErrorCode = EvtError.EvtErrorCode.EC99998;
	
//	public MsgMngrExcepcion(EvtError.EvtErrorCode zEvtErrorCode, String zOrigen, String zErrCode, String zMje, String zFuncOrigen) {
//	  super(zOrigen, zErrCode, zMje, zFuncOrigen);
//	  evtErrorCode = zEvtErrorCode;
//  }
//
//	public MsgMngrExcepcion(EvtError.EvtErrorCode zEvtErrorCode, String zErrCode, String zMje, String zFuncOrigen) {
//	  super(zErrCode, zMje, zFuncOrigen);
//	  evtErrorCode = zEvtErrorCode;
//  }
//
//	public MsgMngrExcepcion(EvtError.EvtErrorCode zEvtErrorCode, String zCode, String zMessage, Throwable cause) {
//	  super(zCode, zMessage, cause);
//	  evtErrorCode = zEvtErrorCode;
//  }
//
//	public MsgMngrExcepcion(EvtError.EvtErrorCode zEvtErrorCode, String errCode, String msg) {
//	  super(errCode, msg);
//	  evtErrorCode = zEvtErrorCode;
//  }

	public MsgMngrExcepcion(EvtError.EvtErrorCode zEvtErrorCode, String zMsg) {
	  super(zMsg);
	  evtErrorCode = zEvtErrorCode;
  }

	public MsgMngrExcepcion(EvtError.EvtErrorCode zEvtErrorCode) {
	  super(EvtError.getErrorCodeDescription(zEvtErrorCode));
	  evtErrorCode = zEvtErrorCode;
  }
	
	public EvtError.EvtErrorCode getEvtErrorCode() {
  	return evtErrorCode;
  }

	public void setEvtErrorCode(EvtError.EvtErrorCode evtErrorCode) {
  	this.evtErrorCode = evtErrorCode;
  }
	
}
