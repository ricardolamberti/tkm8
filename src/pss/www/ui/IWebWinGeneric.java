package pss.www.ui;

import pss.core.win.JBaseWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.content.generators.JXMLContent;

public interface IWebWinGeneric extends JWebActionOwnerProvider {

	BizAction getSourceAction();

	String getSourceObjectId();

	String getRegisteredObjectId()  throws Exception;

	void regiterObjects() throws Exception;

	void setToolbar(String value);

	String getPresentation() throws Exception;

	String getToolbar() throws Exception;

	public String getPaginado() throws Exception;

	public long getPaginaActual() throws Exception;

	boolean isToolbarNone() throws Exception;

	void setBuild(int value);

	void setBackBotton(boolean value);

	JWins getWins()  throws Exception ;

	void setParentProvider(JWebActionOwnerProvider p);
	public JWebActionOwnerProvider getParentProvider();

	boolean hasWins();

	boolean isFirstAccess() throws Exception;

	void setPreviewFlag(String value);

	String invertPreviewFlag() throws Exception;

	boolean isPreviewNo() throws Exception;

	boolean isPreviewFlagSi() throws Exception;

	boolean isPreviewFlagMax() throws Exception;

	String findPreviewFlag() throws Exception;

	String getProviderName() throws Exception;

	String getName();

	boolean isNeedRefreshAllScreen();

	void setNeedRefreshAllScreen(boolean needRefreshAllScreen);

	boolean isReadOnly();

	public boolean isInForm() throws Exception;

	void setReadOnly(boolean readOnly);

	boolean isShowFilterBar() throws Exception;

  int getPageSize();
  
	void setShowFilterBar(boolean bShowFilterBar);

	boolean hasPreview() throws Exception;

	void attachWinsActions(JBaseWin zWin, String zBaseWinId, boolean append) throws Exception;

	String getCompletePanelName() throws Exception;

	String getCenterPanelName() throws Exception;

	String getPreviewCanvasPanelName() throws Exception;

	String getPreviewPanelName() throws Exception;

	boolean isEmbedded() throws Exception;

	void setEmbedded(boolean v) throws Exception;

	boolean isPreview() throws Exception;

	void setPreview(boolean v) throws Exception;

	boolean hasPreviewPanel() throws Exception;

	boolean isInternalToolbar() throws Exception;

	JWebActionData findPagination(JWebActionData nav) throws Exception;

	void addDrop(JXMLContent zContent, String zone, JBaseWin win) throws Exception;

}