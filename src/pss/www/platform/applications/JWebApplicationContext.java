/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.applications;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.cocoon.environment.Context;

import pss.www.ui.skins.JPssWebSkinProvider;

public class JWebApplicationContext extends JAbstractApplicationContext {

	private Context oContext;
	private JPssWebSkinProvider oSkinProvider;

	protected JWebApplicationContext(JSessionedApplication zApplication, Context zContext) {
		super(zApplication);
		this.oContext=zContext;
		this.oSkinProvider=new JPssWebSkinProvider(this);
	}

	@SuppressWarnings("unused")
	private Context getCocoonContext() {
		return this.oContext;
	}

	public String getWebappRelativePath(String zAbsolutePath) {
		return this.getHomeRelativePath(zAbsolutePath);
	}

	@Override
	protected String resolveHomePath() {
		return this.oContext.getRealPath("/");
	}

	@Override
	public String getWorkingDirectory() {
		return this.getHomePrecededPath("work");
	}

	@Override
	public URL getResource(String zResourceURL) throws MalformedURLException {
		return oContext.getResource(zResourceURL);
	}

	public JPssWebSkinProvider getSkinProvider() {
		return this.oSkinProvider;
	}

}
