/*
 * Created on 02-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import java.util.Map;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Redirector;
import org.apache.cocoon.environment.SourceResolver;
import org.apache.cocoon.environment.http.HttpRequest;
import org.apache.cocoon.environment.http.HttpResponse;

import io.jsonwebtoken.Claims;
import pss.core.services.records.JFilterMap;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.submits.JAct;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.actions.requestBundle.JWebActionDataField;
import pss.www.platform.applications.JHistory;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.platform.applications.JwtTokenUtil;
import pss.www.ui.processing.JWebTreeSelection;


public abstract class JIndoorsActionResolver extends JBasicWebActionResolver {

  public JIndoorsActionResolver() {
  }

  //
  //  METHODS OVERRIDEN FROM SUPERCLASS
  //

  @Override
	protected void onPerform() throws Exception {
    this.setTreeSelection();
  }


  @Override
	protected boolean isSessionDependent() {
    return true;
  }


  private static final String TOKEN_COOKIE_NAME = "Authorization"; // Nombre de la cookie

  @Override
  public Map act(Redirector redirector, SourceResolver resolver, Map objectModel, String source, Parameters parameters) throws Exception {
      HttpRequest request = (HttpRequest) ObjectModelHelper.getRequest(objectModel);
      HttpResponse response = (HttpResponse) ObjectModelHelper.getResponse(objectModel);

      String token = getTokenFromCookies(request.getCookies());
      if (token != null) {
         try {
              // Verificar y parsear el token JWT
              Claims claims = JwtTokenUtil.parse(token);
              
      				String ipaddress = request.getRemoteAddr();
      				HttpSession oServletSession = request.getSession(false);

      				if (oServletSession == null) {
      					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // No hay token
      					throw new AuthenticationException("Session expirada");
      				}

      				JWebApplicationSession session = new JWebApplicationSession(ipaddress, oServletSession, null, "");
      				session.initializeUser(claims.getSubject());
      				session.attachGlobalsToCurrentRequest();

      				request.setAttribute("session", session);
      		    return super.act(redirector, resolver, objectModel, source, parameters);
      		              } catch (Exception e) {
              // Token inválido
              response.setStatus(HttpResponse.SC_UNAUTHORIZED);
    					throw new AuthenticationException("Acceso no Autorizado");
         }
      }

      // Si no hay token, también denegar acceso
      response.setStatus(HttpResponse.SC_UNAUTHORIZED);
			throw new AuthenticationException("Acceso no Autorizado");
  }
  
  private String getTokenFromCookies(Cookie[] cookies) {
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
    }
    return null;
}

  //
  //  INTERNAL UTILITIES
  //

  private void ensureListSize(JList<String> zList, int zIndexToSet) {
    int iToAdd = zIndexToSet-zList.size() + 1;
    for (int i = 0; i < iToAdd; i++) {
      zList.addElement(null);     
    }
  }

  private void setTreeSelection() throws Exception {
    JWebActionData oTreeData = this.getRequest().getData("tree_selection");
    if (oTreeData.isNull()) {
      return;
    }
    String sTreeId = oTreeData.get("tree");
    if (sTreeId==null) {
      return;
    }
    JIterator<JWebActionDataField> oFieldsIt = oTreeData.getFieldIterator();
    JWebActionDataField oField;
    JList<String> oLevelValues = JCollectionFactory.createList();
    while (oFieldsIt.hasMoreElements()) {
      oField = oFieldsIt.nextElement();
      if (oField.getName().startsWith("lvl")) {
        int iLevel = Integer.parseInt(oField.getName().substring(3));
        this.ensureListSize(oLevelValues, iLevel);
        oLevelValues.setElementAt(iLevel, oField.getValue());
      }
    }
    this.getSession().getUICoordinator().getTreeCoordinator().setSelection(new JWebTreeSelection(sTreeId, oLevelValues));
  }

	protected boolean isFirstAccess() throws Exception {
		return true;
	}

//	protected void attachDynamicFilters(BizAction action) throws Exception {
//
//		if (action==null) return;
//		action.clearFilterMap();
//		
//		JWebActionData webFilterBar = this.getRequest().getFilters();
//		
//		if (webFilterBar==null) return;
//		if (webFilterBar.isNull()) return;
//		if (webFilterBar.isEmpty()) return;
//		
//		JIterator<JWebActionDataField> iter=webFilterBar.getFieldIterator();
//		while (iter.hasMoreElements()) {
//			JWebActionDataField filter=iter.nextElement();
////			if (filter.getName().endsWith("_time") || filter.getName().endsWith("_date")) continue;
//			action.addFilterMap(filter.getName(), filter.getValue());
//		}
//		
//	}

	protected JMap<String,JFilterMap> createFilterMap() throws Exception {
		
		JMap<String,JWebActionData> webFilterBars = this.getRequest().getFilters();
		if (webFilterBars==null) return null;
		JMap<String,JFilterMap> filters = JCollectionFactory.createMap();
	  String sourceId = this.getRequest().getSourceControId();
		JIterator<String> it = webFilterBars.getKeyIterator();
		while (it.hasMoreElements()) {
			String key = it.nextElement();
			JWebActionData webFilterBar = webFilterBars.getElement(key);
			if (webFilterBar.isNull()) continue;
			if (webFilterBar.isEmpty()) continue;
			JFilterMap fm = new JFilterMap();
			JIterator<JWebActionDataField> iter=webFilterBar.getFieldIterator();
			while (iter.hasMoreElements()) {
				JWebActionDataField filter=iter.nextElement();
	//			if (filter.getName().endsWith("_time") || filter.getName().endsWith("_date")) continue;
				JWin win =null;
				if (filter.getValue().startsWith("obj_")) 
					win = (JWin)this.getRequest().getRegisterObject(filter.getValue());
				if (win!=null)
					fm.addFilterMap(filter.getName(), win);
				//				fm.addFilterMap(filter.getName(), win.getRecord().getPropAsString(win.getKeyField()));
				else
					fm.addFilterMap(filter.getName(), filter.getValue());
			}
			fm.setFieldChange(sourceId);
			filters.addElement(key, fm);
		}
		return filters;
	}
	


	protected boolean isActionHistoryAffected() throws Exception {
		return false;
	}

	private JHistory findLastHistory() throws Exception {
		return this.getSession().getHistoryManager().getLastHistory();
	}


//	protected void touchHistory(BizAction newAction) throws Exception {
//	}
	
	protected void storeHistory(JAct submit) throws Exception {

//		if (submit==null) {
//			JHistory h = this.findLastHistory(); 
//			this.getHistoryManager().setLastSubmit(h.getFirstSumbit());
//			return;
//		}

//		if (this.isLastActionAffected()) {
//			this.getSession().getHistoryManager().setLastAction(zAction);
//		}
		
		if (submit==null) 
			submit=this.findLastHistory().getMainSumbit();

		if (!this.isActionHistoryAffected()) 
			this.getHistoryManager().getLastHistory().addProvider(submit.getActionSource());
		else if (submit.isHistoryAction() && !submit.isInHistory()) 
			this.getHistoryManager().addHistory(submit.getActionSource());

	
		this.getHistoryManager().setLastSubmit(submit);
		return; 
	}
	

	protected boolean cleanPreviousHistory() throws Exception {
		return false;
	}

	protected void assignTarget(JAct action) throws Exception {
		
		this.getHistoryManager().verifyClean(action, this.cleanPreviousHistory());

		this.storeHistory(action);

	}
//	protected void keepFilters(JAct act) throws Exception {
//	  // esto es para que no pierda los filtros en los refreshs
//		BizAction hAction = this.getSession().getHistoryManager().getLastHistoryAction().getActionSource();
//		hAction.takeDinamycData(act.getActionSource());
//	}

	protected void backToHistory() throws Exception {
		JHistory h = this.getHistoryManager().backOneHistory();
		h.returnning();
		this.getHistoryManager().setLastSubmit(h.getMainSumbit());
	}
	

	
}
