package  pss.common.security;

import pss.common.event.device.GuiSubscribeLink;
import pss.common.personalData.GuiPersona;
import pss.common.personalData.types.GuiPersonaFisica;
import pss.common.regions.company.GuiCompany;
import pss.common.regions.divitions.GuiPaises;
import pss.common.regions.nodes.BizNodo;
import pss.common.regions.nodes.BizNodoUsuario;
import pss.common.regions.nodes.GuiNodo;
import pss.common.regions.nodes.GuiNodos;
import pss.common.regions.nodes.GuiNodosUsuarios;
import pss.common.security.friends.GuiUserFriends;
import pss.common.security.license.license.GuiLicense;
import pss.common.security.mail.GuiUsrMailsSender;
import pss.core.connectivity.messageManager.common.core.JMMWin;
import pss.core.services.JExec;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActQueryActive;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActUpdate;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiUsuario extends JMMWin {

	// private BizCambioPassword oCambio;
	// fsprivate static InheritableThreadLocal oUsr = new
	// InheritableThreadLocal();//GuiUsuario oUsr = null;

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Metodos de manejo de variables estaticas al Thread
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	// public static GuiUsuario GetGlobal() { return (GuiUsuario)oUsr.get(); }
	// public static void SetGlobal(GuiUsuario zUsr){
	// oUsr.set( zUsr );
	// }
	// public static void CrearGlobal(GuiUsuario zUser) throws Exception {
	// oUsr.set( zUser );
	// JDebugPrint.logDebug( "front-end web. Peligro. Creando usuario global." );
	// }
	public static GuiUsuario getUsr() throws Exception {
		GuiUsuario oUsr = BizUsuario.getUsr().getObjBusiness().getUserWinInstance();
		oUsr.setRecord(BizUsuario.getUsr());
		return oUsr;
	}

	public GuiUsuario() throws Exception {
	}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizUsuario();
	}

	@Override
	public int GetNroIcono() throws Exception {
		if (!this.GetcDato().isActive()) return 5510;
		return 10074;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Conf. Login";
	}

	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormUsuario.class;
	}

	@Override
	public Class<? extends JBaseForm> getFormFlat() throws Exception {
		return FormUsuarioFlat.class;
	}

	@Override
	public String getKeyField() throws Exception {
		return "usuario";
	}

	// --------- Acciones
	@Override
	public void createActionMap() throws Exception {

		this.addActionQuery(1, "Consultar");
		this.addActionUpdate(2, "Modificar");
		this.addActionDelete(3, "Eliminar");
		this.addAction(14, "Cambio de Clave", null, 5023, true, true);
//		this.addAction(15, "Cambio de Clave Usuario actual", null, 5023, true, true);
		this.addAction(8, "Activar Usuario", null, 6104, true, true);
		this.addAction(9, "Desactivar Usuario", null, 5510, true, true);
		this.addAction(20, "Caducar la Clave", null, 5512, true, true).setConfirmMessage(true);
		this.addAction(25, "Blanqueo de Clave", null, 5513, true, true).setConfirmMessage(true);
		// this.addAction(16, "Agregar Nuevo Rol", null, 43, true, false);
		this.addAction(7, "Perfiles", null, 83, false, false, true);
		this.addAction(12, "Identificaciones", null, 15008, false, false, true);
    this.addAction(30, "Sucursal", null, 10072, false, true );
		this.addAction(40, "Sucursales", null, 10072, false, false, true);
//		this.addAction(50, "Generar Licencia", null, 912, true, true, true);
//		this.addAction(60, "Ver Licencia", null, 912, true, true, true);
//		this.addAction(70, "Firma", null, 5051, true, true, true);
//		this.addAction(72, "Firmas", null, 5051, false, false);
		this.addAction(73, "Configuracion Correo", null, 5514, true, true);
//		addAction(100, "Firma Mail", null, 10003, true, true);
		this.addAction(80, "Ver Clave", null, 5051, true, true);
		this.addAction(90, "Web Config", null, 5515, true, true);
		this.addAction(100, "Firma Mail", null, 10003, true, true);
		this.addAction(110, "Suscribir a telegram", null, 10003, true, true);
		this.addAction(210, "Forzar Log", null, 10003, true, true);
		this.addAction(220, "DesForzar Log", null, 10003, true, true);

	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 14) return new JActNew(this.getNewCambioPassword(), 0);
		if (a.getId() == 25)
			return new JActSubmit(this, a.getId()) {
				public void execSubmit() throws Exception {
					GetcDato().blanquearClave();
				}
			};
		if (a.getId() == 8)
			return new JActSubmit(this, a.getId()) {
				public void execSubmit() throws Exception {
					GetcDato().activar();
				}
			};
		if (a.getId() == 9)
			return new JActSubmit(this, a.getId()) {
				public void execSubmit() throws Exception {
					GetcDato().desactivar();
				}
			};
		if (a.getId() == 20)
			return new JActSubmit(this, a.getId()) {
				public void execSubmit() throws Exception {
					GetcDato().caducar();
				}
			};
		if (a.getId() == 7)	return new JActWins(this.getUsuRoles());
		if (a.getId() == 12) return new JActWins(this.getElectronicIdentifications());
  	if (a.getId() == 30) return new JActQuery(this.getNodo());  	
		if (a.getId() == 40) return new JActWins(this.getUsuNodes());
//		if (a.getId() == 14) return new JActNew(this.getNewCambioPassword(), 0);		
			// if (a.getId() == 16)// return new JActNew(this.getNuevoUsuarioRol(),
				// 0); if (a.getId() == 8) return new JActSubmit(this, a.getId()) {
				// @Override public void submit() throws Exception {
				// GetcDato().execActivar(); } }; if (a.getId() == 9) return new
				// JActSubmit(this, a.getId()) { @Override public void submit() throws
				// Exception { GetcDato().execDesactivar(); } }; if (a.getId() == 20)
				// return new JActSubmit(this, a.getId()) { @Override public void
				// submit() throws Exception { GetcDato().execCaducar(); } }; if
				// (a.getId() == 7) return new JActWins(this.getUsuRoles()); if
				// (a.getId() == 12) return new
				// JActWins(this.getElectronicIdentifications()); if (a.getId() == 30)
				// return new JActQuery(this.getNodo()); if (a.getId() == 40) return new
				// JActWins(this.getUsuNodes()); // if ( id==25) return new
				// JActNew(this.ObtenerPersona(), 4);
		if (a.getId() == 50)
			return new JActNew(this.getNewLicense(), 4);
		if (a.getId()==60) return new JActQuery(this.getLicense());
//		if (a.getId()==70) return new JActNew(this.getNewFirma(), 0);
//		if (a.getId()==72) return new JActWins(this.getFirmas());
		if (a.getId() == 68) return new JActWins(this.getUserFriends());
		if (a.getId()==73) return new JActWins(this.getCorreos());
		if (a.getId() == 80) return this.getClaveUser();
		if (a.getId() == 90) return new JActQuery(this.getUsuWeb());
		if (a.getId()==100) return this.getMailAction(a);
		if (a.getId()==110) return new JActQueryActive(getSubscripcionTelegram());
		if (a.getId()==210) return new JActSubmit(this, a.getId()) {
			public void execSubmit() throws Exception {
				GetcDato().forzarLogStatus(true);
			}
		};
		if (a.getId()==220) return new JActSubmit(this, a.getId()) {
			public void execSubmit() throws Exception {
				GetcDato().forzarLogStatus(false);
			}
		};
		if (a.getId()==230) return new JActSubmit(this, a.getId()) {
			public void execSubmit() throws Exception {
				GetcDato().autorizarViaLdap(true);
			}
		};		
		if (a.getId()==240) return new JActSubmit(this, a.getId()) {
			public void execSubmit() throws Exception {
				GetcDato().autorizarViaLdap(false);
			}
		};		
		
		return null;
	}
	
	public JWin getSubscripcionTelegram() throws Exception {
		GuiSubscribeLink channel = new GuiSubscribeLink();
		channel.GetcDato().addFilter("usuario", GetcDato().GetUsuario());
		channel.GetcDato().forceFilterToData();
		return channel;
		
	}
	  public JAct getMailAction(BizAction a) throws Exception {
			return new JActUpdate(this.getNewMail(a), 2);
		}

		public JWin getNewMail(BizAction a) throws Exception {

//			GuiMailSignature fv = new GuiMailSignature();
//			fv.GetcDato().addFilter("company", this.GetcDato().getCompany());
//			fv.GetcDato().addFilter("username",this.GetcDato().GetUsuario());
//			fv.GetcDato().dontThrowException(true);
//			if (fv.GetcDato().read()==false) {
//				BizMailSignature ms = new BizMailSignature();
//				ms.setCompany(this.GetcDato().getCompany());
//				ms.setUsername(this.GetcDato().GetUsuario());
//				ms.execProcessInsert();
//				fv.GetcDato().read();
//			}
//			fv.GetcDato().setUserDesc(BizUsuario.getUsr().getDescrUsuario());
//			return fv;
			return null;
		}

	
	public JWin getUsuWeb() throws Exception {
		GuiWebUserProfile user = new GuiWebUserProfile();
		user.GetcDato().read(this.GetcDato().GetUsuario());
		return user;
	}
	

	protected JBaseWin getElectronicIdentifications() throws Exception {
		GuiUserElectronicIds oUserIds=new GuiUserElectronicIds();
		oUserIds.getRecords().addFilter("usuario", this.GetcDato().GetUsuario());
		return oUserIds;
	}

	public JWin getNewCambioPassword() throws Exception {
    JWin oCambioPassword = this.GetcDato().getObjBusiness().buildCambioClave(this.GetcDato().GetUsuario(),this.GetcDato().GetDescrip(),this.GetcDato().getPasswordDecrypt(),null,null);
//		GuiCambioPassword oCambioPassword=new GuiCambioPassword();
//		oCambioPassword.GetcDato().pLogin.setValue(this.GetcDato().GetUsuario());
//		oCambioPassword.GetcDato().pDescrip.setValue(this.GetcDato().GetDescrip());
		return oCambioPassword;
	}
	private JWin getLicense() throws Exception {
		GuiLicense oLicense=new GuiLicense();
		oLicense.GetcDato().read(this.GetcDato().pLicense.getValue());
		return oLicense;
	}

	private JWin getNewLicense() throws Exception {
		GuiLicense oLicense=new GuiLicense();
		oLicense.GetcDato().addFilter("company", this.GetcDato().getCompany());
		oLicense.setDropListener(this);
		return oLicense;
	}

	private GuiUsuarioRol getNuevoUsuarioRol() throws Exception {
		GuiUsuarioRol oUsuRol=new GuiUsuarioRol();
		oUsuRol.GetcDato().pUsuario.setValue(GetcDato().pUsuario.getValue());
		return oUsuRol;
	}

//	private JWin getNewFirma() throws Exception {
//		GuiUsuarioFirma f=new GuiUsuarioFirma();
//		f.GetcDato().addFilter("usuario", this.GetcDato().GetUsuario());
//		return f;
//	}
//
//	private JWins getFirmas() throws Exception {
//		GuiUsuarioFirmas f=new GuiUsuarioFirmas();
//		f.getRecords().addFilter("usuario", this.GetcDato().GetUsuario());
//		return f;
//	}
	private JWins getCorreos() throws Exception {
		GuiUsrMailsSender f=new GuiUsrMailsSender();
		f.getRecords().addFilter("usuario", this.GetcDato().GetUsuario());
		return f;
	}
	// ------------

	// false para eliminar la acción, true para dejarla pasar
	@Override
	public boolean OkAction(BizAction zAct) throws Exception {

		if (zAct.getId()==14) return !(BizUsuario.getUsr().GetUsuario().equalsIgnoreCase(this.GetcDato().GetUsuario()));
		if (zAct.getId()==15)	return false;
		if (zAct.getId()==3) {
			if (!GetcDato().isAnyAdminUser()) return false;
			if (GetcDato().pUsuario.getValue().equals(BizUsuario.getUsr().GetUsuario())) return false;
			return true;
		}
		if (zAct.getId()==20) return !(this.GetcDato().pFechaCambioClave.isNull());
		if (zAct.getId()==25) return !(GetcDato().pClave.toString().length()==0);
		if (zAct.getId()==8) return !GetcDato().isActive();
		if (zAct.getId()==9) return GetcDato().isActive();
		if (zAct.getId()==60) return GetcDato().hasLicense();
		if (zAct.getId()==80)	return BizUsuario.IsAdminUser();
		if (zAct.getId()==210)	return  BizUsuario.IsAdminCompanyUser() && !GetcDato().getForzarLogStatus();
		if (zAct.getId()==220)	return BizUsuario.IsAdminCompanyUser() && GetcDato().getForzarLogStatus();
		if (zAct.getId()==230) return !GetcDato().getHasLdap();
		if (zAct.getId()==240) return GetcDato().getHasLdap();
		return super.OkAction(zAct);
	}

	public JWins getUsuRoles() throws Exception {
		GuiUsuarioRoles g=new GuiUsuarioRoles();
		g.getRecords().addFilter("company", GetcDato().pCompany.getValue());
		g.getRecords().addFilter("usuario", GetcDato().pUsuario.getValue());
		g.SetVision(BizUsuarioRol.VISION_ROL);
		return g;
	}

	public JWins getUsuNodes() throws Exception {
		GuiNodosUsuarios g = new GuiNodosUsuarios();
		g.getRecords().addFilter("company", GetcDato().pCompany.getValue());
		g.getRecords().addFilter("usuario", GetcDato().pUsuario.getValue());
		return g;
	}

	@Override
	public String getDescripField() {
		if (GetVision().equals("usuario")) return "usuario";
		if (GetVision().equals("descripcion")) return "descripcion";
		return "descr_login";
	}

	// -------------------------------------------------------------------------//
	// Devuelve el dato casteado
	// -------------------------------------------------------------------------//
	public BizUsuario GetcDato() throws Exception {
		return (BizUsuario) this.getRecord();
	}

	//
	// //-------------------------------------------------------------------------/
	// /
	// // Pantalla de login de las aplicaciones
	// //-------------------------------------------------------------------------/
	// /
	// public void FormLogIn() throws Exception {
	// GuiLoginTrace oLogin = new GuiLoginTrace();
	// oLogin.FormLogIn();
	// }

	//
	// //-------------------------------------------------------------------------/
	// /
	// // Pantalla de cambio de clave usuario
	// //-------------------------------------------------------------------------/
	// /
	// public void FormCambioPassword( String zUser ) throws Exception {
	// GuiCambioPassword oCambioPassword = new GuiCambioPassword();
	// oCambioPassword.GetcDato().Read( zUser );
	// oCambioPassword.showNewForm();
	// }

	@Override
	public boolean ifAcceptDrop(JBaseWin zBaseWin) throws Exception {
		if (zBaseWin instanceof GuiRol) return this.ifHabilitado(6);
		if (zBaseWin instanceof GuiPersona) return this.ifHabilitado(25);
		if (zBaseWin instanceof GuiLicense) return true;
		return false;
	}

	@Override
	public JAct Drop(JBaseWin w) throws Exception {		if (w instanceof GuiRol) 			execDropRol(w);		else if (w instanceof GuiPersona) 			execDropPersona(w);		else if (w instanceof GuiNodo)   		this.createUsuarioNodo(JRecords.createRecords(w.GetBaseDato()));  	else if (w instanceof GuiNodos)   		this.createUsuarioNodo(JRecords.createRecords(w.GetBaseDato()));		return null;
	}

	public void execDropLicense(final JBaseWin zBaseWin) throws Exception {
		JExec oExec=new JExec(null, null) {

			@Override
			public void Do() throws Exception {
				dropLicense(zBaseWin);
			}
		};
		oExec.execute();
	}

	public void execDropRol(final JBaseWin zBaseWin) throws Exception {
		JExec oExec=new JExec(null, null) {

			@Override
			public void Do() throws Exception {
				dropRol(zBaseWin);
			}
		};
		oExec.execute();
	}

	public void execDrop(final JBaseWin zBaseWin) throws Exception {
		JExec oExec=new JExec(null, null) {

			@Override
			public void Do() throws Exception {
				dropRol(zBaseWin);
			}
		};
		oExec.execute();
	}

	public void execDropPersona(final JBaseWin zBaseWin) throws Exception {
		JExec oExec=new JExec(null, null) {

			@Override
			public void Do() throws Exception {
				dropPersona(zBaseWin);
			}
		};
		oExec.execute();
	}

	public void dropLicense(JBaseWin zBaseWin) throws Exception {
		GuiLicense oLicencia=(GuiLicense) zBaseWin;
		oLicencia.GetcDato().processInsert();
		this.GetcDato().pLicense.setValue(oLicencia.GetcDato().getIdlicense());
		this.GetcDato().update();
	}

	public void dropPersona(JBaseWin zBaseWin) throws Exception {
		GuiPersona oPersona=(GuiPersona) zBaseWin;
		oPersona.GetcDato().processInsert();
		this.GetcDato().SetPersona(oPersona.GetcDato().GetPersona());
		this.GetcDato().update();
	}

	public void dropRol(JBaseWin zBaseWin) throws Exception {
		GuiRol oRol=(GuiRol) zBaseWin;
		GuiUsuarioRol oUsuRol=new GuiUsuarioRol();
		oUsuRol.GetcDato().pUsuario.setValue(this.GetcDato().pUsuario.getValue());
		oUsuRol.GetcDato().pRol.setValue(oRol.GetcDato().pRol.getValue());
		oUsuRol.GetcDato().processInsert();
	}

	public GuiPersona ObtenerPersona() throws Exception {
		GuiPersona oPersona=new GuiPersonaFisica();
		oPersona.getRecord().dontThrowException(true);
		if (!oPersona.GetcDato().Read(GetcDato().pIdPersona.getValue())) oPersona.setDropListener(this);
		return oPersona;
	}
	
	public JWin getNodo() throws Exception {
    GuiNodo oWin = new GuiNodo();
    oWin.GetcDato().Read(GetcDato().getCompany(), GetcDato().getNodo());
    return oWin;
  }

  public void createUsuarioNodo(JRecords<?> records) throws Exception {
  	JIterator<?> iter = records.getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizNodo v = (BizNodo)iter.nextElement();
  		BizNodoUsuario s = new BizNodoUsuario();
  		s.setCompany(v.getCompany());
  		s.SetNode(v.GetNodo());
  		s.SetUser(this.GetcDato().GetUsuario());
  		s.dontThrowException(true);
  		if (s.readByKeys()) continue;
  		s.execProcessInsert();
  	}
  }
  
  public GuiNodos getNodoHabilitados() throws Exception {
  	return this.getNodoHabilitados(null);
  }
  public GuiNodos getNodoHabilitados(String pais) throws Exception {
  	GuiNodos nodos = new GuiNodos();
  	nodos.setRecords(this.GetcDato().getNodosHabilitados(pais));
  	return nodos;
  }
  
  public GuiPaises getPaisesHabilitados() throws Exception {
  	GuiPaises w = new GuiPaises();
  	w.setRecords(this.GetcDato().getPaisesHabilitados());
  	return w;
  }
  

  public GuiCompany getObjCompany() throws Exception {
		GuiCompany c = GuiCompany.VirtualCreateType(this.GetcDato().getObjCompany().getBusiness());
		c.setRecord(this.GetcDato().getObjCompany());
		return c;
	}
	
	public JAct getClaveUser() throws Exception {
		BizUsuario oUsuario = (BizUsuario) this.GetcDato().getPreInstance();
		JExcepcion.SendError(oUsuario.getPasswordDecrypt());
		return null;
	}
 
	private JWins getUserFriends() throws Exception {
		GuiUserFriends f = new GuiUserFriends();
		f.getRecords().addFilter("company", this.GetcDato().getCompany());
		f.getRecords().addFilter("usuario", this.GetcDato().GetUsuario());
		return f;
	}

	@Override
	public String getFieldForeground(String col) throws Exception {
		if (col.equals("ICONO") && !this.GetcDato().isActive())
			return "red";
		return super.getFieldForeground(col);
	}
	
}
