package pss.www.platform.actions.resolvers;

import pss.common.help.BizHelp;
import pss.common.help.BizQuestion;
import pss.common.help.BizQuestionDetail;
import pss.core.JAplicacion;
import pss.core.tools.JTools;
import pss.www.platform.actions.JWebActionFactory;


public class JDoSaveHelpActionResolver extends JDoAjaxActionResolver {

	@Override
	protected String getBaseActionName() {
		return "save-help";
	}

	@Override
	protected boolean isAjax() {return true;}
	
//	public String getProviderName() throws Exception {
//		String action = JWebActionFactory.getCurrentRequest().getSession().getNameDictionary(this.getIdAction().replace(".", "_"));
//		if (JWebActionFactory.getCurrentRequest().getLevel()>1) action+="__l"+JWebActionFactory.getCurrentRequest().getLevel();
//		return "p_"+action+(row!=null?"_"+row:"");
//	}
	public String recoverFieldName(String name) throws Exception {
		
		String newname =name;
		while (true) {
			int posK = newname.indexOf("_k");
			if (posK==-1) break;
			int posEndK = name.indexOf("_",posK+1);
			if (posEndK==-1) break;
			String sPos = name.substring(posK+2,posEndK);
			if (!JTools.isNumberPure(sPos)) break;
			long k = JTools.getLongFirstNumberEmbedded(sPos);
			String action = JWebActionFactory.getCurrentRequest().getNameDictionary(k);
			newname=newname.substring(0,posK)+"_"+action+newname.substring(posEndK);
		}

		
		return newname;
	}
	@Override
	protected void onPerform() throws Exception {
		String texto = (String) this.getRequest().get("dg_texto");
		String sX = (String) this.getRequest().get("dg_x");
		String sY = (String) this.getRequest().get("dg_y");
		String fieldName = (String) this.getRequest().get("dg_fieldname");
		String sWin = (String) this.getRequest().get("dg_win");
		String sAct = (String) this.getRequest().get("dg_action");
		String sStatus = (String) this.getRequest().get("dg_status");
		String sTypePos = (String) this.getRequest().get("dg_typepos");
		BizQuestion questionHelp = JAplicacion.GetApp().getQuestionHelp();
		if (questionHelp==null) {
			String realname = (recoverFieldName(fieldName));
			// ayuda
			BizHelp help = new BizHelp();
			help.dontThrowException(true);
			help.read(null,sAct,sStatus,sWin,realname);
			help.setAction(sAct);
			help.setHelp(texto);
			help.setId(realname);
			help.setStatus(sStatus);
			help.setPage(sWin);
			help.execProcessUpdateOrInsertWithCheck();
			
			return;
		}
		

		BizQuestionDetail q = new BizQuestionDetail();
		q.setX(Long.parseLong(sX));
		q.setY(Long.parseLong(sY));
		q.setAction(sAct);
		q.setIdquestion(questionHelp.getIdquestion());
		q.setHelp(texto);
		//q.setStep(1);
		q.setType("STEP");
		q.setId(recoverFieldName(fieldName));
		q.setZ(20);
		//1=OnFocus 2=Static 3=Absolut 4=Relative 5=fixed
		if (sTypePos.equals("1")) {
			q.setTypePos("onfocus");
			q.setX(0);
			q.setY(0);
		}
		if (sTypePos.equals("2")) {
			q.setTypePos("static");
			q.setX(Long.parseLong(sX));
			q.setY(Long.parseLong(sY));
		}
		if (sTypePos.equals("3")) {
			q.setTypePos("absolut");
			q.setX(Long.parseLong(sX));
			q.setY(Long.parseLong(sY));
		}
		if (sTypePos.equals("4")) {
			q.setTypePos("relative");
			q.setX(Long.parseLong(sX));
			q.setY(Long.parseLong(sY));
		}
		if (sTypePos.equals("5")) {
			q.setTypePos("fixed");
			q.setX(Long.parseLong(sX));
			q.setY(Long.parseLong(sY));
		}
		q.setStatus(sStatus);
		q.setPage(sWin);
		q.execProcessInsert();
  }

}