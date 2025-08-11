package pss.common.documentos.docEmail;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import com.sun.mail.util.BASE64DecoderStream;

import pss.JPath;
import pss.common.documentos.biblioteca.BizBiblioteca;
import pss.common.documentos.docElectronico.BizDocAdjunto;
import pss.common.documentos.docElectronico.BizDocElectronico;
import pss.common.documentos.docHtmlBase.BizDocHtmlBase;
import pss.common.documentos.tipos.BizDocFisicoTipo;
import pss.common.event.mail.BizMailCasilla;
import pss.common.event.mail.BizMailSignature;
import pss.common.event.mail.IUserMail;
import pss.common.event.mail.JMailRecive;
import pss.common.event.mail.JMailSend;
import pss.common.event.manager.BizEvent;
import pss.common.layoutWysiwyg.GuiPlantillas;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JHtml;
import pss.core.services.fields.JImage;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLOB;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBD;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecords;
import pss.core.tools.JPair;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.core.winUI.lists.JPlantilla;

public class BizDocEmail extends BizDocHtmlBase implements IUserMail {

	public static final String SALIENTE = "S";
	public static final String ENTRANTE = "E";

	private JInteger pCasilla = new JInteger();
	private JString pMailFrom = new JString();
	private JString pMailTo = new JString();
	// private JHtml pHtmlEMail = new JHtml() {
	// public void preset() throws Exception {
	// setValue(getHtmlEMail());
	// };
	// };
	private JString pFlujo = new JString();
	private JLOB pBody = new JLOB();
	private JLOB pBodyBiblo = new JLOB() {
		public void preset() throws Exception {
			setValue(getBodyBiblo());
		};
	};
	private JBoolean pFirmar = new JBoolean();
	private JBoolean pTieneAdjuntos = new JBoolean();
	private JBoolean pGrupoAdjuntos = new JBoolean();
	// private JBoolean pGrupoLeido = new JBoolean();
	private JString pGrupoEMail = new JString();
	private JImage pAdjIcon = new JImage() {
		public void preset() throws Exception {
			setValue(getAdjIcon());
		};
	};
//	private JBoolean pAnulado = new JBoolean();
	private JBoolean pEnviado = new JBoolean();
	private JDateTime pFechaEnvio = new JDateTime();
	private JString pReferenciaEnvio = new JString();
//	private JLong pIdDocNext = new JLong();
	private JLong pIdDocGrupo = new JLong();
	private JBoolean pLast = new JBoolean();
	private JInteger pCantidad = new JInteger();
	private JBoolean pRespuesta = new JBoolean();
	private JBoolean pEnviar = new JBoolean();
	// private JDateTime pFechaRespuesta = new JDateTime();
	private JString pText = new JString() {
		public void preset() throws Exception {
			pText.setValue(extractText(findBody()));
		};
	};
	private JString pBodyTrunc = new JString() {
		public void preset() throws Exception {
			setValue(getBodyTrunc());
		};
	};
	private JHtml pAsuntoBodyTrunc = new JHtml() {
		public void preset() throws Exception {
			setValue(getAsuntoBodyTrunc());
		};
	};

	private JLong pIdBiblo = new JLong();

	private JBoolean pLeido = new JBoolean();
	private JString pMailId = new JString();

	private JObjBD pObjUsuario = new JObjBD() {
		public void preset() throws Exception {
			pObjUsuario.setValue(getObjUsuario());
		};
	};

	public void setLast(boolean zValue) throws Exception {
		pLast.setValue(zValue);
	}

	public boolean isLast() throws Exception {
		return pLast.getValue();
	}

	public void setCantidad(int zValue) throws Exception {
		pCantidad.setValue(zValue);
	}

	public int getCantidad() throws Exception {
		return this.pCantidad.getValue();
	}

	public void setGrupoAdjuntos(boolean zValue) throws Exception {
		pGrupoAdjuntos.setValue(zValue);
	}

	public void setTieneAdjuntos(boolean zValue) throws Exception {
		pTieneAdjuntos.setValue(zValue);
	}

	public void setGrupoEMail(String zValue) throws Exception {
		pGrupoEMail.setValue(zValue);
	}

	// public void setGrupoLeido(boolean zValue) throws Exception {
	// pGrupoLeido.setValue(zValue); }
//	public void setAnulado(boolean zValue) throws Exception {
//		pAnulado.setValue(zValue);
//	}

	public void setGrupo(long zValue) throws Exception {
		pIdDocGrupo.setValue(zValue);
	}

	public long getGrupo() throws Exception {
		return this.pIdDocGrupo.getValue();
	}

	public void setFlujo(String zValue) throws Exception {
		pFlujo.setValue(zValue);
	}

	public String getFlujo() throws Exception {
		return this.pFlujo.getValue();
	}

	public boolean isEnviado() throws Exception {
		return pEnviado.getValue();
	}

//	public boolean isAnulado() throws Exception {
//		return pAnulado.getValue();
//	}

	public boolean hasRespuesta() throws Exception {
		return pRespuesta.getValue();
	}

	public boolean isTieneAdjuntos() throws Exception {
		return pTieneAdjuntos.getValue();
	}

	public void setMailId(String zValue) throws Exception {
		pMailId.setValue(zValue);
	}

	public String getMailId() throws Exception {
		return pMailId.getValue();
	}

	public void setLeido(boolean zValue) throws Exception {
		pLeido.setValue(zValue);
	}

	public boolean isLeido() throws Exception {
		return pLeido.getValue();
	}

	// BizDocCorreoExterno docCorreExt;
	// public BizDocCorreoExterno getObjDocEmailPadre() throws Exception {
	// if (docCorreExt!=null) return docCorreExt;
	// if (pIdDocMail.getValue()==0) return null;
	// BizDocCorreoExterno c = new BizDocCorreoExterno();
	// c.dontThrowException(true);
	// if (!c.Read(pIdDocMail.getValue())) return null;
	// return docCorreExt=c;
	// }

	// BizTramiteContacto objContactoEmail;
	// BizTramiteContacto objContacto;
	public void clean() throws Exception {
		usuario = null;
		// mailSender=null;
		super.clean();
	}

	// private BizUsrMailSender mailSender;
	// public BizUsrMailSender getObjMailSender() throws Exception {
	// if (mailSender!=null) return mailSender;
	// BizUsrMailSender u = new BizUsrMailSender();
	// u.setIDMailConf(BizUsuario.getUsr().getMail());
	// return (mailSender=u);
	// }

	public String getPlantillaDefault() throws Exception {
		return "sys_mail";
	}
	private BizUsuario usuario;

	public BizUsuario getObjUsuario() throws Exception {
		if (usuario != null)
			return usuario;
		BizUsuario u = new BizUsuario();
		u.Read(this.getUsuario());
		return (usuario = u);
	}
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setBody(String zValue) throws Exception {
		pBody.setValue(zValue);
	}

	public String f() throws Exception {
		return pBody.getValue();
	}

	public void setFirmar(boolean v) throws Exception {
		pFirmar.setValue(v);
	}

	public boolean isFirmar() throws Exception {
		return pFirmar.getValue();
	}

	public int getCasilla() throws Exception {
		return pCasilla.getValue();
	}

	public void setCasilla(int value) throws Exception {
		pCasilla.setValue(value);
	}

	public void setMailFrom(String zValue) throws Exception {
		pMailFrom.setValue(zValue);
	}

	public String getMailFrom() throws Exception {
		return pMailFrom.getValue();
	}

	public void setMailTo(String zValue) throws Exception {
		pMailTo.setValue(zValue);
	}
		
	public String getMailTo() throws Exception {
		return pMailTo.getValue();
	}

	public String getGrupoEMail() throws Exception {
		return pGrupoEMail.getValue();
	}
	
	public String findAnyMail() throws Exception {
		if (this.isSaliente()) 
			return this.getMailTo();
		return this.getMailFrom();
	}


	// public String findEMail() throws Exception {
	// if (this.isEntrante())
	// return this.getEMail();
	// else
	// return this.getObjMailCasilla().getMailFrom();
	// }
	// public String findEMail() throws Exception {
	// if (this.isEntrante())
	// return this.getEMail();
	// else
	// return this.getObjMailCasilla().getMailFrom();
	// }

	// public String findNombreFrom() throws Exception {
	// if (this.isEntrante())
	// return this.getEMail();
	// else
	// return this.getObjMailCasilla().findNombreEmail();
	// }

	public String findHtmlFrom() throws Exception {
		String s = JTools.encodeString2(this.getMailFrom());
		if (this.isEntrante() && !this.isLeido())
			return "<b>" + s + "</b>";
		return s;

	}

	// public void setDireccion(String zValue) throws Exception {
	// pDireccion.setValue(zValue);
	// }
	//
	// public String getDireccion() throws Exception {
	// return pDireccion.getValue();
	// }

	// public String getDescrPlazo() throws Exception {
	// if (getPlazo()==0) return "";
	// return BizRegla.getPeriodos().getElement(""+getPlazo());
	// }

	// boolean isVencido() throws Exception {
	// if (getPlazo()==0) return false;
	// if (getFechaVencimiento()==null) return false;
	// return new Date().after(getFechaVencimiento());
	// }

	public void setEnviado(boolean zValue) throws Exception {
		pEnviado.setValue(zValue);
	}

	public boolean getEnviado() throws Exception {
		return pEnviado.getValue();
	}

	public String getReferenciaEnviado() throws Exception {
		return pReferenciaEnvio.getValue();
	}

	public void setReferenciaEnvio(String zValue) throws Exception {
		pReferenciaEnvio.setValue(zValue);
	}

//	public long getIdDocNext() throws Exception {
//		return pIdDocNext.getValue();
//	}

	public long getIdDocGrupo() throws Exception {
		return pIdDocGrupo.getValue();
	}

//	public void setIdDocNext(long zValue) throws Exception {
//		pIdDocNext.setValue(zValue);
//	}

	public void setFechaEnviado(Date zValue) throws Exception {
		pFechaEnvio.setValue(zValue);
	}

	public void setRespuesta(boolean zValue) throws Exception {
		pRespuesta.setValue(zValue);
	}

	public boolean getRespuesta() throws Exception {
		return pRespuesta.getValue();
	}

	// public void setFechaRespuesta(Date zValue) throws Exception {
	// pFechaRespuesta.setValue(zValue);
	// }
	// public Date getFechaRespuesta() throws Exception {
	// return pFechaRespuesta.getValue();
	// }
	public Date getFechaEnviado() throws Exception {
		return pFechaEnvio.getValue();
	}

	// public static JMap<String, String> getCorreos() throws Exception {
	// JMap<String, String> c = JCollectionFactory.createMap();
	// c.addElement("*", "Nota");
	// c.addElement("=", "Email no integrado");
	// c.addElement("CD", "Carta documento");
	// c.addElement("0", "Correo convencional");
	// JRecords<BizUsrMailSender> mails = new
	// JRecords<BizUsrMailSender>(BizUsrMailSender.class);
	// mails.addFilter("usuario", BizUsuario.getUsr().GetUsuario());
	// mails.toStatic();
	// JIterator<BizUsrMailSender> it = mails.getStaticIterator();
	// while (it.hasMoreElements()) {
	// BizUsrMailSender mail = it.nextElement();
	// if (!mail.isSender()) continue;
	// c.addElement("" + mail.getID(), mail.getDescripcion());
	// }
	// return c;
	// }

	/**
	 * Constructor de la Clase
	 */
	public BizDocEmail() throws Exception {
	}

	public void createProperties() throws Exception {
		super.createProperties();
		this.addItem("flujo", pFlujo);
		this.addItem("body", pBody);
		this.addItem("mail_from", pMailFrom);
		this.addItem("mail_to", pMailTo);
		// this.addItem("html_email", pHtmlEMail);
		this.addItem("casilla", pCasilla);
		this.addItem("text", pText);
		this.addItem("enviado", pEnviado);
		this.addItem("fecha_envio", pFechaEnvio);
		this.addItem("respuesta", pRespuesta);
		// this.addItem("fecha_respuesta", pFechaRespuesta);
//		this.addItem("id_doc_next", pIdDocNext);
		this.addItem("obj_usuario", pObjUsuario);
		// this.addItem( "id_doc_mail", pIdDocMail );
		this.addItem("firmar", pFirmar);
		this.addItem("tiene_adjuntos", pTieneAdjuntos);
		this.addItem("adj_icon", pAdjIcon);
//		this.addItem("anulado", pAnulado);
		this.addItem("id_doc_grupo", pIdDocGrupo);
		this.addItem("mail_uid", pMailId);
		this.addItem("leido", pLeido);
		this.addItem("cantidad", pCantidad);
		this.addItem("last", pLast);
		this.addItem("grupo_adjuntos", pGrupoAdjuntos);
		this.addItem("grupo_email", pGrupoEMail);
		// this.addItem( "grupo_leido", pGrupoLeido);
		this.addItem("body_trunc", pBodyTrunc);
		this.addItem("asunto_body_trunc", pAsuntoBodyTrunc);
		this.addItem("body_biblo", pBodyBiblo);
		this.addItem("id_biblo", pIdBiblo);
		this.addItem("enviar", pEnviar);		
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		super.createFixedProperties();
		this.addFixedItem(FIELD, "flujo", "Flujo", true, false, 1);
		this.addFixedItem(FIELD, "body", "Body", true, true, 100000);
		this.addFixedItem(FIELD, "casilla", "Id Casilla", true, false, 18);
		this.addFixedItem(FIELD, "mail_from", "Mail From", true, false, 250);
		this.addFixedItem(FIELD, "mail_to", "Mail To", true, false, 250);

		this.addFixedItem(FIELD, "enviado", "enviado", true, false, 1);
		this.addFixedItem(FIELD, "fecha_envio", "Fecha envio", true, false, 18);
		this.addFixedItem(FIELD, "respuesta", "respuesta", true, false, 1);
		this.addFixedItem(FIELD, "firmar", "Firmar", true, false, 1);
		this.addFixedItem(FIELD, "tiene_adjuntos", "Tiene Adjuntos", true, false, 1);
		this.addFixedItem(FIELD, "id_doc_grupo", "Grupo", true, false, 18);
		this.addFixedItem(VIRTUAL, "text", "Texto", true, false, 4000);
		this.addFixedItem(FIELD, "leido", "Leido", true, false, 1);
		this.addFixedItem(FIELD, "mail_uid", "UID", true, true, 250);
		this.addFixedItem(FIELD, "cantidad", "Cantdad", true, true, 5);
		this.addFixedItem(FIELD, "last", "Ultimo", true, true, 1);
		this.addFixedItem(FIELD, "grupo_adjuntos", "Grupo Adjuntos", true, true, 1);
		this.addFixedItem(FIELD, "grupo_email", "Grupo Email", true, true, 100);
		this.addFixedItem(FIELD, "id_biblo", "Id Biblo", true, true, 18);
		this.addFixedItem(VIRTUAL, "enviar", "Enviar", true, false, 1);
		this.addFixedItem(VIRTUAL, "adj_icon", "Icon", true, false, 100);
		this.addFixedItem(VIRTUAL, "body_trunc", "Body Trunc", true, false, 100);
		this.addFixedItem(VIRTUAL, "asunto_body_trunc", "Body Trunc", true, false, 100);
		this.addFixedItem(VIRTUAL, "body_biblo", "Body Biblo", true, false, 4000);

		this.addFixedItem(RECORD, "obj_usuario", "Usuario", true, false, 50).setClase(BizUsuario.class);
	}
	

	
	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "DOC_EMAIL";
	}
	

	public String getTipoDocumento() throws Exception {
		return BizDocFisicoTipo.CORREO;
	}

	// public String getBodyAsHtml() throws Exception {
	// return (new URLCodec()).decode(this.getBody(), "ISO-8859-1");
	// }

	public String getBody() throws Exception {
		return this.pBody.getValue();
	}

	BizMailCasilla casilla = null;

	public void setObjMailCasilla(BizMailCasilla c) throws Exception {
		this.casilla = c;
		this.pCasilla.setValue(c.getID());
	}

	public BizMailCasilla getObjMailCasilla() throws Exception {
		if (this.casilla != null)
			return this.casilla;
		BizMailCasilla c = new BizMailCasilla();
		c.read(this.pCasilla.getValue());
		return (this.casilla = c);
	}

	private boolean hasCasilla() throws Exception {
		if (pCasilla.isNull())
			return false;
		return true;
	}

//	public String makeMailIdent() throws Exception {
//		if (this.getObjMailCasilla().getObjMailConf().hasPop3())
//			return " (Ref:#" + this.getIddoc() + ")"; // si necesito las respuestas le pongo una referencia
//		return "";
//	}

	private String sendMail() throws Exception {
		// if (!this.hasRemitente())
		// JExcepcion.SendError("No existe remitente");

		BizMailCasilla casilla=this.getObjMailCasilla();
		
		JMailSend send = new JMailSend();
		send.setObjServer(casilla.getObjMailConf());
		send.setMailFrom(casilla.getMailFrom());
		send.setMailFromName(casilla.getNombre());
		send.setAuthUser(casilla.findUser());
		send.setPassword(casilla.getMailPassword());
		send.setMailTo(this.getMailTo());
		send.setTitle(this.getTitulo());
//		send.setTitle(this.getTitulo() + this.makeMailIdent());
		send.setSignature(this.findSignature());
		send.setBody(JTools.decodeIso(this.findBody()));
		send.setFilesAttach(this.getFilesAttached());
		send.send();
		return this.scapeId(send.getMailId());
	}

	public String findSignature() throws Exception {
		if (!this.isFirmar())
			return "";
		BizMailSignature sig = new BizMailSignature();
		sig.dontThrowException(true);
		if (!sig.read(this.getCompany(), BizUsuario.getUsr().GetUsuario()))
			return null;

		return JTools.decodeIso(sig.getSignature());
	}

	public void processEnviar() throws Exception {
		BizDocEmail email = new BizDocEmail();
		email.Read(this.getIddoc());

		if (this.getObjMailCasilla() == null) {
			BizMailCasilla casilla = BizUsuario.getUsr().getObjMailCasilla();
			this.setObjMailCasilla(casilla);
		}

		String id = email.sendMail();

		email.setEnviado(true);
		email.setFechaEnviado(BizUsuario.getUsr().todayGMT());
		email.setMailId(id);
		email.setRespuesta(false);

		email.update();

		// BizDocEmail.startPollingThread();

		// BizHito hito = new BizHito();
		// hito.setObservaciones("Se marca para enviar. Emisor "+
		// BizUsuario.getUsr().GetUsuario() + " el " + JDateTools.DateToString(
		// fechaEnvio, "dd-MM-yyyy"));
		// hito.setIddoc(getIddoc());
		// hito.setTipoHito(BizHito.DOC);
		// hito.processInsert();

	}

	private void processVolverEspera() throws Exception {
		Date fechaEspera = new Date();
		BizDocEmail d = (BizDocEmail) this.getPreInstance();
		d.setRespuesta(false);
		// d.setFechaRespuesta(null);
		d.update();
		// processUpdate();
		// BizHito hito = new BizHito();
		// hito.setObservaciones("Se marca como nuevamente en espera: "+
		// BizUsuario.getUsr().GetUsuario() + " el " + JDateTools.DateToString(
		// fechaEspera, "dd-MM-yyyy"));
		// hito.setIddoc(getIddoc());
		// hito.setTipoHito(BizHito.DOC);
		// hito.processInsert();
	}

	public JPlantilla getDatosPlantilla() throws Exception {
		if (!this.hasPlantilla())
			return null;
		return this.getObjPlantilla().getDatosPlantilla();
		// JPlantilla p =
		// JDoLayoutActionResolver.getInfoDatosPlantilla(null,BizTramite.class.getName(),
		// getObjTramiteOrigen()==null?BizConsola.getConsola().getObjTramite():getObjTramiteOrigen(),
		// this, JFieldSetWins.SECTOR_MAIN);
		// return p;
	}

	public boolean hasGrupo() throws Exception {
		return this.pIdDocGrupo.getValue() != 0L;
	}

	public void assignLast() throws Exception {
		int cant = 0;
		boolean adj = false;
		boolean first = true;
		BizDocEmail last = null;
		JMap<String, String> map = JCollectionFactory.createMap();
		JIterator<BizDocEmail> iter = this.getGrupoDocs().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizDocEmail e = iter.nextElement();
			if (first)
				last = e;
			e.setLast(first);
			// if (first && !e.isLast()) e.setLast(true);
			// if (!first && e.isLast()) e.setLast(false);

			if (e.isTieneAdjuntos())
				adj = true;

			this.appendMails(map, e.getMailFrom());
			this.appendMails(map, e.getMailTo());

			cant++;
			first = false;
			e.update();
		}

		String mail = "";
		boolean f = true;
		boolean one = (map.size() == 1);
		JIterator<String> it = map.getValueIterator();
		while (it.hasMoreElements()) {
			String m = it.nextElement();
			if (!f)
				mail += ", ";
			if (one)
				mail = JTools.subStr(m, 0, m.indexOf('<'));
			else
				mail += JTools.subStr(JTools.subStr(m, 0, m.indexOf('<')).trim(), 0, m.indexOf(' '));
			f = false;
		}
		last.setLast(true);
		// if (cant==1 && last.isSaliente()) last.setLast(false); // no se
		// porque esta esto? si lo dejo no aparecen los nuevos mails salientes
		// en el negocio
		last.setCantidad(cant);
		last.setGrupoEMail(mail + (cant == 1 ? "" : " (" + cant + ")"));
		last.setGrupoAdjuntos(adj);
		last.update();
	}

	public void appendMails(JMap<String, String> map, String sEmail) throws Exception {
		JStringTokenizer tk = JCollectionFactory.createStringTokenizer(sEmail, ',');
		while (tk.hasMoreTokens()) {
			String mail = tk.nextToken().trim();
			int start = mail.indexOf('<') + 1;
			int end = mail.indexOf('>');
			String email = (end == -1 ? mail : mail.substring(start, end));
			String valor = map.getElement(email);
			if (valor == null)
				valor = mail;
			if (valor.length() > mail.length())
				mail = valor;
			map.addElement(email, mail);
		}
	}

	// @Override
	// public void processUpdate() throws Exception {
	// if (!this.hasIdDoc()) {
	// this.processInsert();
	// return;
	// }
	// super.processUpdate();
	// }

	@Override
	public void processUpdate() throws Exception {
		this.updateBody();
		super.processUpdate();
	}

	public void setEnviar(boolean v) {
		this.pEnviar.setValue(v);
	}

	public boolean isEnviar() throws Exception {
		return this.pEnviar.getValue();
	}

	@Override
	public void processInsert() throws Exception {
		if (this.pCasilla.isNull()) this.pCasilla.setValue(BizUsuario.getUsr().getObjMailCasilla().getID());
		if (this.pMailFrom.isNull()) this.setMailFrom(this.getObjMailCasilla().findNombreEmail());
		if (this.pFlujo.isNull()) this.setFlujo(BizDocEmail.SALIENTE);
		this.pushBody();
		this.setTieneAdjuntos(this.hasAdjuntos());
		super.processInsert();

		if (!this.hasGrupo()) {
			BizDocEmail m = (BizDocEmail)this.getPreInstance();
			m.setGrupo(this.getIddoc());
			m.update();
			this.setGrupo(m.getGrupo());
		}

		this.assignLast();
		this.processAttached();
//		this.notifySource(this);

		this.tryEnviar();
	}
	
	private void tryEnviar() throws Exception {
		if (!this.isEnviar()) return;
		try {
			this.processEnviar();
		} catch (Exception e) {
			this.doError();
			throw e;
		}
	}
	
	private void doError() throws Exception {
		if (biblo!=null) {
			this.pBody.setValue(this.biblo.findContents());
			this.biblo=null;
			this.pIdBiblo.setNull();
		}
		if (this.hasAdjuntos()) {
			JIterator<BizDocAdjunto> iter = this.adjuntos.getStaticIterator();
			while (iter.hasMoreElements()) {
				BizDocElectronico elec = iter.nextElement();
				elec.setIdDocPadre(0L);
				elec.setIddoc(0L);
			}
		}
		if (this.getGrupo()==this.getIddoc())
			this.setGrupo(0L);
		this.setIddoc(0);
	}

	private void pushBody() throws Exception {
		if (this.pBody.isEmpty()) return;
		// pone el body en biblioteca
		BizBiblioteca biblo = new BizBiblioteca();
		biblo.setCompany(this.getCompany());
		biblo.setTipo("html");
//		biblo.setContenido(JTools.decodeIso(this.getBody()));
		biblo.setContenido(this.getBody());
		biblo.captureContent(this.getTitulo());
		biblo.processInsert();
		this.pBody.setNull(); // quedo en biblo
		this.pIdBiblo.setValue(biblo.getId());
		this.biblo=biblo;
	}

	private void updateBody() throws Exception {
		// pone el body en biblioteca
		BizBiblioteca biblo = this.getObjBiblioteca();
		biblo.setContenido(JTools.decodeIso(this.findBody()));
		biblo.updateContent();
		biblo.update();
	}

	// public String findFlujo() throws Exception {
	// return "S";
	// }

	private void processAttached() throws Exception {
		if (!this.hasAdjuntos())
			return;
		JIterator<BizDocAdjunto> iter = this.adjuntos.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizDocElectronico elec = iter.nextElement();
			elec.setIdDocPadre(this.getIddoc());
			elec.processInsert();
		}
	}

	public static boolean isPolling() throws Exception {
		return pollingThread != null;
	}

	public static void stopPolling() throws Exception {
		if (pollingThread == null)
			return;
		pollingThread.interrupt();
		Thread.sleep(5000);
		pollingThread = null;
	}

	static Thread pollingThread = null;

	public static void startPollingThread() throws Exception {
    Thread th = new Thread(new Runnable () {public void run() { BizDocEmail.polling(); }});
    th.setName("Mail Polling");
    th.start();
    pollingThread=th;
	}
	
	public JRecords<BizMailCasilla> getAllCasillas() throws Exception {
		JRecords<BizMailCasilla> recs = new JRecords<BizMailCasilla>(BizMailCasilla.class);
		recs.addFilter("estado", BizMailCasilla.STATUS_OK);
		recs.readAll();
		recs.toStatic();
		return recs;
	}
	public static void polling() {
		try {
			JAplicacion.openSession();
			JAplicacion.GetApp().openApp("PollingEmail", JAplicacion.AppService(), true);
			BizUsuario usuario = new BizUsuario();
			usuario.Read(BizUsuario.C_ADMIN_USER);
			BizUsuario.SetGlobal(usuario);

			BizDocEmail listener = new BizDocEmail();
			while (true) {
				if (Thread.currentThread().isInterrupted())
					return;

				JMailRecive recive = new JMailRecive();
				recive.setListener(listener);
				JIterator<BizMailCasilla> it = listener.getAllCasillas().getStaticIterator();
				while (it.hasMoreElements()) {
					BizMailCasilla casilla = it.nextElement();
					if (!casilla.getObjMailConf().hasPop3())
						continue;
					recive.setObjMailCasilla(casilla);
//					if (casilla.isOnlyRspuestas())
//					recive.setPattern(business); // solo las respuestas del SITI
//						recive.setContent("(Ref:#");
//					if (casilla.isAll())
//						recive.setContent(null);
					recive.reciveMails();
					
				}
				Thread.sleep(60000);
			}
		} catch (Exception e) {
			PssLogger.logDebug(e);
			return;
		} finally {
			JAplicacion.closeSession();
//			JAplicacion.GetApp().closeApp();
		}
	}

	public static void main(String[] args) {
		try {
			BizDocEmail.startPollingThread();
		} catch (Exception e) {

		}
	}

//	private long findIdDoc(String subject) throws Exception {
//		if (subject == null)
//			return 0;
//		int pos1 = subject.indexOf("(Ref:#");
//		if (pos1 == -1)
//			return 0;
//		int pos2 = subject.indexOf(")");
//		if (pos2 == -1)
//			return 0;
//		return Long.parseLong(subject.substring(pos1 + 6, pos2));
//	}

	private boolean findDupp(String myId) throws Exception {
		if (myId==null) return false;
		if (myId.equals("")) return false;
		BizDocEmail mail = new BizDocEmail();
		return mail.readById(myId);
	}

	private BizDocEmail findMailOriginal(String replay) throws Exception {
		if (replay==null) return null;
		if (replay.equals("")) return null;
		BizDocEmail mail = new BizDocEmail();
		mail.dontThrowException(true);
		if (!mail.readById(replay))
			return null;
		return mail;
//	PssLogger.logDebug("No Existe el mail original: " + replay);
	}

	private String cleanSubject(String subject) throws Exception {
		if (subject == null)
			return "";
		int pos1 = subject.indexOf("(Ref:#");
		if (pos1 == -1)
			return subject;
		return subject.substring(0, pos1);
	}

	private String findIdMsg(Message m) throws Exception {
		if (m.getHeader("Message-ID") == null)
			return "" + m.getSubject();
		return this.scapeId(m.getHeader("Message-ID")[0]);
	}
	
	private String scapeId(String id) throws Exception {
		id=id.replace("[", ""); // en el In-Reply-To el host viene sin corchetes 
		id=id.replace("]", "");
		return id; 
	}

	private String findReplayTo(Message m) throws Exception {
		if (m.getHeader("In-Reply-To") == null)
			return null;
		return m.getHeader("In-Reply-To")[0];
	}

	@Override
	public void onMessage(JMailRecive reciver, Message m) throws Exception {

		BizMailCasilla casilla = reciver.getObjMailCasilla();
		if (casilla.hasLastMail() && (m.getSentDate().equals(casilla.getLastMail()) || m.getSentDate().before(casilla.getLastMail())))
			return; // mail viejo sigo de largo

		String myId = this.findIdMsg(m);
		if (this.findDupp(myId)) return;
		
		String replay = this.findReplayTo(m);
		BizDocEmail orig = this.findMailOriginal(replay);
		if (orig==null) return; // solo proceso respuestas
		
		BizDocEmail newMail = new BizDocEmail();
		newMail.setCompany(casilla.getCompany());
		newMail.setFlujo(BizDocEmail.ENTRANTE);
		newMail.setTitulo(this.cleanSubject(m.getSubject()));
		newMail.setMailFrom(MimeUtility.decodeText(InternetAddress.toString(m.getFrom())).replaceAll("\"", ""));
		newMail.setMailTo(casilla.findNombreEmail());
		newMail.setCasilla(casilla.getID());
		newMail.setMailId(myId);
		newMail.setFechaEnviado(m.getReceivedDate());
		newMail.setEnviado(true);

		// vinculo con original
		newMail.setIdDocPadre(orig.getIddoc());
		newMail.setGrupo(orig.getGrupo());
		newMail.setUsuario(orig.getUsuario());

		ReciveCollector collector = newMail.processContent(m);
		newMail.setBody(collector.findBody());
//		newMail.setTieneAdjuntos(collector.hasAdjuntos());
		newMail.appendAdjuntos(collector);
		newMail.processInsert();
//		newMail.processAdjuntos(collector);
		newMail.processEmbedded(collector);

//		orig.notifySource(newMail); // notifico al modulo la respuesta
		orig.setRespuesta(true);
		orig.update();
		this.notifyEvent(newMail);
	}
	
  private void notifyEvent(BizDocEmail mail) throws Exception {
		//evento
  	if (!mail.isEntrante()) return; 
		BizEvent e = new BizEvent();
//		e.setEventCode(BizRetailEvent.EVT_MAIL_RECIVE);
		e.setCompany(mail.getCompany());
//		e.setSenderNode(mail.GetNodo());
		e.setSenderUser(mail.getUsuario());
		e.setTitle("Mail Recibido");
		e.setInfo(mail.getMailFrom() + "->" + mail.getTitulo() );
		e.processEvent();
  }


	private boolean isHtml(BodyPart part) throws Exception {
		if (!(part.getContent() instanceof String))
			return false;
		return part.getContentType().toUpperCase().startsWith("TEXT/HTML");
	}

	private boolean isMultipart(Object content) throws Exception {
		return content instanceof Multipart;
	}

	private boolean isString(BodyPart part) throws Exception {
		return part.getContent() instanceof String;
	}

	private boolean isString(Object content) throws Exception {
		return content instanceof String;
	}

	private boolean isBase64(BodyPart part) throws Exception {
		return part.getContent() instanceof BASE64DecoderStream;
	}

	private boolean isStream(BodyPart part) throws Exception {
		return part.getContent() instanceof InputStream;
	}

	private boolean isAttachment(BodyPart part) throws Exception {
		if (part.getDisposition() == null)
			return false;
		if (part.getDisposition().equalsIgnoreCase(Part.ATTACHMENT))
			return true;
		return false;
	}

	// private String findBody(Object content) throws Exception {
	// if (content instanceof String)
	// return (String) content;
	// }

	private void processString(BodyPart part, ReciveCollector collector) throws Exception {
		collector.setBody((String) part.getContent());
	}

	private void processString(Object content, ReciveCollector collector) throws Exception {
		collector.setBody((String) content);
	}

	private void processHtml(BodyPart part, ReciveCollector collector) throws Exception {
		collector.setHtml((String) part.getContent());
	}

	private void processAttachment(Message msg, BodyPart part, ReciveCollector collector) throws Exception {
		String dir = JPath.PssPathTempFiles() + "/" + this.getCompany();
		JTools.MakeDirectory(dir);
		String file = dir + "/" + MimeUtility.decodeText(part.getFileName());
		FileOutputStream output = new FileOutputStream(file);

		InputStream input = part.getInputStream();
		byte[] buffer = new byte[4096 * 16];
		int byteRead;
		while ((byteRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, byteRead);
		}
		output.close();

		BizDocAdjunto elec = new BizDocAdjunto();
		elec.setCompany(this.getCompany());
		elec.setNombreArchivo(file);
		elec.setTitulo(part.getFileName());
		elec.setSubType(BizDocElectronico.ADJUNTO);
		collector.addAdjunto(elec);

	}

	private void processBase64(Message msg, BodyPart part, ReciveCollector collector) throws Exception {
		String dir = JPath.PssPathTempFiles() + "/" + this.getCompany();
		JTools.MakeDirectory(dir);
		String name = part.getFileName();
		if (name == null)
			name = part.hashCode() + "";
		String file = dir + "/" + MimeUtility.decodeText(name);
		FileOutputStream output = new FileOutputStream(file);

		InputStream input = part.getInputStream();
		byte[] buffer = new byte[4096 * 16];
		int byteRead;
		while ((byteRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, byteRead);
		}
		output.close();

		BizBiblioteca biblo = new BizBiblioteca();
		biblo.setCompany(this.getCompany());
		biblo.setTipo(BizBiblioteca.findType(file));
		// biblo.dontThrowException(true);
		// biblo.read(pIdBiblio.getValue());
		biblo.captureFile(file);

		collector.addImage(part.getHeader("Content-ID")[0], biblo);
	}
	// private void processBase64(BodyPart part, ReciveCollector collector)
	// throws Exception {
	// ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	// DataOutputStream output = new DataOutputStream(new
	// BufferedOutputStream(bytes));
	// BASE64DecoderStream test = (BASE64DecoderStream) part.getContent();
	// byte[] buffer = new byte[1024];
	// int bytesRead;
	// while ((bytesRead = test.read(buffer)) != -1) {
	// output.write(buffer, 0, bytesRead);
	// }
	// output.close();
	//// collector.addImage("image"+new Date().getTime()+".jpg",
	// bytes.toByteArray());
	// collector.addImage(part.getHeader("Content-ID")[0], bytes.toByteArray());
	// }

	private void processStream(BodyPart part, ReciveCollector collector) throws Exception {
		String txt = "";
		InputStream is = (InputStream) part.getContent();
		int c;
		while ((c = is.read()) != -1) {
			txt += (c);
		}
		collector.setBody(txt);
	}

//	private void notifySource(BizDocEmail target) throws Exception {
//		if (!this.getObjDocum().hasSource())
//			return;
//		JRecord source = this.getObjDocum().findDocumentSource();
//		((IMailSource) source).onNotifySource(target);
//	}

	private JRecords<BizDocAdjunto> adjuntos;

	public void setAdjuntos(JRecords<BizDocAdjunto> list) throws Exception {
		this.adjuntos = list;
	}

	public boolean hasAdjuntos() throws Exception {
		return this.adjuntos != null;
	}

	public void addAdjunto(BizDocAdjunto doc) throws Exception {
		if (this.adjuntos == null) {
			this.adjuntos = new JRecords<BizDocAdjunto>(BizDocAdjunto.class);
			this.adjuntos.setStatic(true);
		}
		this.adjuntos.addItem(doc);
	}

	public JRecords<BizDocAdjunto> getObjAdjuntos() throws Exception {
		if (this.adjuntos != null)
			return this.adjuntos;
		return (this.adjuntos = this.getAdjuntos());
	}

	public JRecords<BizDocAdjunto> getAdjuntos() throws Exception {
		JRecords<BizDocAdjunto> recs = new JRecords<BizDocAdjunto>(BizDocAdjunto.class);
		recs.setStatic(true);
		if (this.getIddoc() == 0)
			return recs;
		JRecords<BizDocAdjunto> docs = new JRecords<BizDocAdjunto>(BizDocAdjunto.class);
		docs.addFilter("doc_id_doc_padre", this.getIddoc());
		docs.addFilter("doc_tipo_doc", BizDocFisicoTipo.ELECT);
		docs.readAll();
		JIterator<BizDocAdjunto> iter = docs.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizDocAdjunto a = iter.nextElement();
			a.setObjDocEmail(this);
			recs.addItem(a);
		}
		return recs;
	}

	// GuiDocums docs = new GuiDocums();
	// docs.getRecords().addFilter("id_doc_padre", this.GetcDato().getIddoc());
	// docs.getRecords().addFilter("tipo_doc", BizDocFisicoTipo.ELECT);
	// docs.readAll();
	// GuiDocAdjuntos recs = new GuiDocAdjuntos();
	// recs.SetEstatico(true);
	// recs.setPreviewSplitPos(300);
	// JIterator<JWin> iter = docs.getStaticIterator();
	// while (iter.hasMoreElements()) {
	// GuiDocum d = (GuiDocum)iter.nextElement();
	// GuiDocAdjunto a = new GuiDocAdjunto();
	// a.setRecord(d.GetcDato().getObjDocumento());
	// a.setDocEmail(this);
	// recs.addRecord(a);
	// }
	// recs.setForceSelected(false);
	// return recs;

	public JRecords<BizBiblioteca> getDocsEmbedded() throws Exception {
		JRecords<BizBiblioteca> recs = new JRecords<BizBiblioteca>(BizBiblioteca.class);
		recs.setStatic(true);
		if (this.getIddoc() == 0)
			return recs;
		recs.addFilter("id_doc_padre", this.getIddoc());
		recs.readAll();
		recs.toStatic();
		return recs;
	}

	public JMap<String, String> getFilesAttached() throws Exception {
		JMap<String, String> map = JCollectionFactory.createMap();
		JIterator<BizDocAdjunto> iter = this.getAdjuntos().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizDocElectronico e = iter.nextElement();
			map.addElement(e.getNombreArchivo(), e.getObjBiblioteca().getAbsoluteFileName());
		}
		return map;
	}

	public JRecords<BizDocEmail> getGrupoDocs() throws Exception {
		JRecords<BizDocEmail> recs = new JRecords<BizDocEmail>(BizDocEmail.class);
		recs.addFilter("id_doc_grupo", this.getGrupo());
		recs.addOrderBy("id_doc", "desc");
		recs.readAll();
		return recs;
	}

	private void appendAdjuntos(ReciveCollector collector) throws Exception {
		if (!collector.hasAdjuntos())
			return;
//		this.setTieneAdjuntos(true);
		JIterator<BizDocAdjunto> iter = collector.getMapaAdjuntos().getIterator();
		while (iter.hasMoreElements()) {
			BizDocAdjunto a = iter.nextElement();
			this.addAdjunto(a);
//			e.setCompany(this.getCompany());
//			e.setIdDocPadre(this.getIddoc());
//			e.processInsert();
		}
	}

	private void processEmbedded(ReciveCollector collector) throws Exception {
		if (!collector.hasImages())
			return;
		JIterator<BizBiblioteca> iter = collector.getImages().getValueIterator();
		while (iter.hasMoreElements()) {
			BizBiblioteca b = iter.nextElement();
			b.setIdDocPadre(this.getIddoc());
			b.processInsert();
		}
	}

	private ReciveCollector processContent(Message msg) throws Exception {
		Object content = msg.getContent();
		ReciveCollector collector = new ReciveCollector();
		if (this.isString(content)) {
			this.processString(content, collector);
		} else if (this.isMultipart(content)) {
			this.processMultipart(msg, content, collector);
		}
		this.verifyImages(collector);
//		this.verifyBody(collector);

		return collector;
	}

	private void processPart(Message msg, BodyPart part, ReciveCollector collector) throws Exception {
		if (this.isMultipart(part.getContent())) {
			this.processMultipart(msg, part.getContent(), collector);
		} else if (this.isAttachment(part)) {
			this.processAttachment(msg, part, collector);
		} else if (this.isHtml(part)) {
			this.processHtml(part, collector);
		} else if (this.isString(part)) {
			this.processString(part, collector);
		} else if (this.isBase64(part)) {
			this.processBase64(msg, part, collector);
		} else if (this.isStream(part)) {
			this.processStream(part, collector);
		}
	}

	private void processMultipart(Message msg, Object content, ReciveCollector collector) throws Exception {
		Multipart mp = (Multipart) content;
		int count = mp.getCount();
		for (int j = 0; j < count; j++) {
			this.processPart(msg, mp.getBodyPart(j), collector);
		}
	}

	private void verifyBody(ReciveCollector collector) throws Exception {
		if (!collector.hasBody())
			return;
		collector.setBody(JTools.encodeIso(collector.getBody()).replaceAll("\\+", " "));
	}

	private void verifyImages(ReciveCollector collector) throws Exception {
		if (!collector.hasHtml())
			return;
		String html = collector.getHtml();
		int posInicial = 0;
		while (true) {
			int posI = html.indexOf("src=\"cid:", posInicial);
			if (posI == -1)
				break;
			int posIF = html.indexOf("\"", posI + 9);
			String src = html.substring(posI, posIF + 1).trim();
			if (!src.startsWith("src="))
				continue;
			int posIF2 = src.indexOf(":");
			int posIF3 = src.indexOf("@");
			String image;
			if (posIF3 != -1 && posIF2 != -1)
				image = src.substring(posIF2 + 1, posIF3);
			else if (posIF3 == -1)
				image = src.substring(posIF2 + 1, src.length() - 1);
			else
				image = src;
			BizBiblioteca doc = collector.findImage("<" + image.trim() + ">");
			if (doc != null)
				html = html.replace(src, "src=\"" + doc.getUrl() + "\"");
			// if (bImages!=null&&bImages.length<50000) {// limite para imagenes
			// muy grandes incrustadas
			// html = html.replace(src, "src=\"data:image/jpg;base64,"+(new
			// sun.misc.BASE64Encoder().encode(bImages).replaceAll("\r\n",
			// ""))+"\"");
			// }
			posInicial = posIF + 1;
		}
		html = html.toLowerCase().indexOf("<html>") != -1 ? html : "<html><body>" + html + "</body></html>";
//		collector.setHtml(JTools.encodeIso(html).replaceAll("\\+", " "));
		collector.setHtml(html.replaceAll("\\+", " "));

	}

	class ReciveCollector {

		private String body;
		private String html;
		private JMap<String, BizBiblioteca> mapaImagenes;
		private JList<BizDocAdjunto> mapaAdjuntos;

		public void setBody(String v) {
			this.body = v;
		}

		public String getBody() {
			return this.body;
		}

		public boolean hasBody() {
			if (this.body == null)
				return false;
			return !this.body.isEmpty();
		}

		public void setHtml(String v) {
			this.html = v;
		}

		public String getHtml() {
			return this.html;
		}

		public boolean hasHtml() {
			if (this.html == null)
				return false;
			return !this.html.isEmpty();
		}

		public String findBody() {
			if (this.hasHtml())
				return this.html;
			return this.body;
		}

		public boolean hasImages() throws Exception {
			return this.mapaImagenes != null;
		}

		public void addImage(String name, BizBiblioteca e) throws Exception {
			if (this.mapaImagenes == null)
				this.mapaImagenes = JCollectionFactory.createMap();
			this.mapaImagenes.addElement(name, e);
		}

		public BizBiblioteca findImage(String name) throws Exception {
			if (this.mapaImagenes == null)
				return null;
			;
			return this.mapaImagenes.getElement(name);
		}

		public boolean hasAdjuntos() throws Exception {
			return this.mapaAdjuntos != null;
		}

		public void addAdjunto(BizDocAdjunto e) throws Exception {
			if (this.mapaAdjuntos == null)
				this.mapaAdjuntos = JCollectionFactory.createList();
			this.mapaAdjuntos.addElement(e);
		}

		public JList<BizDocAdjunto> getMapaAdjuntos() throws Exception {
			return this.mapaAdjuntos;
		}

		public JMap<String, BizBiblioteca> getImages() throws Exception {
			return this.mapaImagenes;
		}
	}

	public void execProcessAddAttached(final BizDocAdjunto e) throws Exception {
		JExec exec = new JExec(null, null) {
			public void Do() throws Exception {
				processAddAttached(e);
			}
		};
		exec.execute();
	}

	public void processAddAttached(BizDocAdjunto a) throws Exception {
		a.setTitulo(a.findName());
		a.setType(a.findType());
		this.addAdjunto(a);
		if (!this.hasIdDoc())
			return;
		a.setIdDocPadre(this.getIddoc());
		a.processInsert();
		BizDocEmail m = new BizDocEmail();
		m.Read(this.getIddoc());
		m.setTieneAdjuntos(true);
		m.processUpdate();
	}

	public boolean isEntrante() throws Exception {
		return !this.isSaliente();
	}

	public boolean isSaliente() throws Exception {
		if (this.pFlujo.isNull())
			return true;
		return this.getFlujo().equals(BizDocEmail.SALIENTE);
	}

	public boolean hasConversacion() throws Exception {
//		if (this.getIddoc()!=this.getGrupo()) return false;
		if (this.getCantidad() <= 1)
			return false;
		return true;
	}

	public boolean canEliminar() throws Exception {
//		if (this.isEntrante())
//			return !this.hasIdDocPadre(); // si es respuesta no se puede borrar
//		if (this.isEnviado())
//			return false;
		return this.isLast();
	}

	public boolean canEnviar() throws Exception {
		if (!this.isSaliente())
			return false;
		if (this.isEnviado())
			return false;
//		if (this.isAnulado())
//			return false;
		return true;
	}

	public boolean canReEnviar() throws Exception {
		if (!this.isSaliente())
			return false;
//		if (this.isAnulado())
//			return false;
		if (!this.isEnviado())
			return false;
		if (this.hasRespuesta())
			return false;
		if (!this.isLast())
			return false;
		return true;
	}

	public boolean canResponder() throws Exception {
		if (!this.isEntrante())
			return false;
		if (this.hasRespuesta())
			return false;
		if (!this.isLast())
			return false;
		return true;
	}

	public boolean canMarcarLeido() throws Exception {
		if (!this.isEntrante())
			return false;
		if (this.hasRespuesta())
			return false;
		if (this.isLeido())
			return false;
		return true;
	}

	public boolean canMarcarNoLeido() throws Exception {
		if (!this.isEntrante())
			return false;
		if (this.hasRespuesta())
			return false;
		if (!this.isLeido())
			return false;
		return true;
	}

	public boolean canModificar() throws Exception {
		if (!this.isSaliente())
			return false;
		if (this.isEnviado())
			return false;
		return true;
	}

	public boolean canAddAdjunto() throws Exception {
		if (!this.isSaliente())
			return false;
		if (this.isEnviado())
			return false;
		return true;
	}

	public boolean canRemoveAdjunto() throws Exception {
		if (!this.isSaliente())
			return false;
		if (this.isEnviado())
			return false;
		return true;
	}

//	private BizDocEmail docNext;
//
//	public BizDocEmail getObjDocNext() throws Exception {
//		if (this.docNext != null)
//			return this.docNext;
//		BizDocEmail m = new BizDocEmail();
//		m.Read(this.getIdDocNext());
//		return (this.docNext = m);
//	}

//	public void processAnular(BizDocumento source) throws Exception {
//		BizDocEmail m = (BizDocEmail) this.getPreInstance();
//		m.setAnulado(true);
//		m.setIdDocNext(source.getIddoc());
//		m.update();
//		this.setAnulado(true);
//	}

//	public void processRespuesta(BizDocumento source) throws Exception {
////		if (this.isAnulado()) {
////			this.getObjDocNext().processRespuesta(source);
////			return;
////		}
//		BizDocEmail m = (BizDocEmail) this.getPreInstance();
//		m.setRespuesta(true);
//		// m.setIdDocNext(source.getIddoc());
//		m.update();
//		this.setRespuesta(true);
//	}

	public boolean isReadOnly() throws Exception {
		if (this.isEntrante())
			return true;
		return this.isEnviado();
	}

//	public void notifyParent(BizDocEmail newMail) throws Exception {
//		if (this.isSaliente() && newMail.isSaliente())
//			return; //es un reenvio  this.processAnular(newMail);
//		else
//			this.processRespuesta(newMail);
//	}
	// public void onNotifySource(BizDocumento doc) throws Exception {
	// this.processRespuesta(doc);
	// }

	public boolean readById(String uid) throws Exception {
		this.dontThrowException(true);
		this.addFilter("mail_uid", uid);
		return super.read();
	}

	public void execProcessMarcarComoLeido() throws Exception {
		JExec exec = new JExec(null, null) {
			public void Do() throws Exception {
				processMarcarComoLeido();
			};
		};
		exec.execute();
	}

	public void processMarcarComoLeido() throws Exception {
		// if (this.justMarked) {
		// this.justMarked=false;
		// return;
		// }
		BizDocEmail d = new BizDocEmail();
		d.Read(this.getIddoc());
		d.setLeido(true);
		d.update();
		this.assignLast();
		this.setLeido(true);
	}

	// private boolean justMarked;
	public void processMarcarComoNoLeido() throws Exception {
		BizDocEmail d = new BizDocEmail();
		d.Read(this.getIddoc());
		d.setLeido(false);
		d.update();
		this.assignLast();
		this.setLeido(false);
		// this.justMarked=false;
	}

	public void attachDocsFrom(BizDocEmail source) throws Exception {
		JIterator<BizDocAdjunto> iter = source.getObjAdjuntos().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizDocAdjunto e = iter.nextElement();
			BizDocAdjunto eclone = (BizDocAdjunto) e.createClone();
			eclone.setIddoc(0L);
			eclone.setIdDocPadre(0L);
			this.addAdjunto(eclone);
		}
	}

	public String getAdjIcon() throws Exception {
		if (!this.isTieneAdjuntos())
			return null;
		return "clip.png";
	}

	public void processRefreshLast() throws Exception {
		BizDocEmail mail = new BizDocEmail();
		mail.Read(this.pIdDocGrupo.getValue());
		mail.assignLast();
	}

	public String getBodyTrunc() throws Exception {
		return JTools.subStr(JTools.unencodeString(JTools.trimHTML(this.findBody())), 0, 100);
	}

	public boolean isResaltar() throws Exception {
		return this.isEntrante() && !this.isLeido();
	}

	public String getAsuntoBodyTrunc() throws Exception {
		return this.getTitulo() + " - " + this.getBodyTrunc();
	}

	@Override
	public void processDelete() throws Exception {
		this.getAdjuntos().processDeleteAll();
		this.getDocsEmbedded().processDeleteAll();
		super.processDelete();
		if (this.hasIdBiblo())
			this.getObjBiblioteca().processDelete();
		if (this.isLast()) 
			this.getGrupoDocs().processDeleteAll();
	}

//	public void notifyPadre(BizDocumento child) throws Exception {
//		if (!child.getObjDocum().isElectronico())
//			return;
//		boolean hasAttached = this.getAdjuntos().ifRecordFound();
//		if (this.isTieneAdjuntos() == hasAttached)
//			return;
//
//		BizDocEmail e = (BizDocEmail) this.getPreInstance();
//		e.setTieneAdjuntos(hasAttached);
//		e.update();
//		this.setTieneAdjuntos(hasAttached);
//	}

	public void assignSignature() throws Exception {
		String s = BizMailSignature.getUserSignature();
		if (s == null)
			return;
		if (s.isEmpty())
			return;
		this.pBody.setValue(this.pBody.getValue() + "<br><br><br>" + s);
	}

	public void embeddedAllImages() throws Exception {
		JIterator<BizDocAdjunto> iter = this.getObjAdjuntos().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizDocAdjunto adj = iter.nextElement();
			adj.embeddedImage();
		}
	}

	public void addBodyStart(String s) throws Exception {
		this.setBody("<br><br><br>" + s + this.findBody());
	}

	public boolean hasIdBiblo() throws Exception {
		return this.pIdBiblo.getValue() != 0L;
	}
	
	

//	@Override
//	public void Read(JBaseRegistro zSet, boolean wf) throws Exception {
//		super.Read(zSet, wf);
//		if (!this.hasIdBiblo())
//			return;
//		this.pBody.setValue(this.findContents());
//	}
	public String findBody() throws Exception {
		if (this.pBody.isNotNull()) return this.pBody.getValue();
		this.pBody.setValue(this.getBodyBiblo());
		return this.pBody.getValue();
	}

	public String getBodyBiblo() throws Exception {
		if (!this.hasIdBiblo()) return "";
		try {
			return this.getObjBiblioteca().findContents();
		} catch (Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	private BizBiblioteca biblo;

	public BizBiblioteca getObjBiblioteca() throws Exception {
		if (this.biblo != null)
			return this.biblo;
		BizBiblioteca b = new BizBiblioteca();
		b.read(this.pIdBiblo.getValue());
		return (this.biblo = b);
	}

	public BizDocAdjunto findAdjunto(String row) throws Exception {
		if (row.startsWith("SEC_")) 
			return this.getObjAdjuntos().getStaticElement(Integer.parseInt(row.substring(4)));
		long lrow = Long.parseLong(row);
		JIterator<BizDocAdjunto> iter = this.getObjAdjuntos().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizDocAdjunto a = iter.nextElement();
			if (a.getIddoc() == lrow)
				return a;
		}
		return null;
	}

	public void removeAdjunto(String row) throws Exception {
		this.getObjAdjuntos().removeStaticItem(this.findAdjunto(row));
//		JIterator<BizDocAdjunto> iter = this.getObjAdjuntos().getStaticIterator();
//		while (iter.hasMoreElements()) {
//			BizDocAdjunto a = iter.nextElement();
//			if (a.getIddoc() == row)
//				iter.remove();
//		}
	}

	protected BizDocEmail createNewMail() throws Exception {
		return new BizDocEmail();
	}

	public BizDocEmail createMailRespuesta() throws Exception {
		BizDocEmail clone = this.createNewMail();
		clone.setCompany(this.getCompany());
		clone.setIddoc(0L);
		clone.setFlujo(BizDocEmail.SALIENTE);
		clone.setMailFrom(this.getObjMailCasilla().getMailFrom());
		clone.setMailTo(this.getMailFrom());
		clone.setTitulo("RE: "+this.getTitulo());
		clone.setBody(this.findBodyAnswer());
		clone.setGrupo(this.getGrupo());
		clone.setIdDocPadre(this.getIddoc());	
		clone.setSourceTipo(this.getSourceTipo());
		clone.setSourceCodigo(this.getSourceCodigo());
//		clone.setDatosLeidos(false);
		clone.setEnviar(true);
		return clone;
	}


	public BizDocEmail createMailReenvio() throws Exception {
		BizDocEmail clone = this.createNewMail();
		clone.setCompany(this.getCompany());
		clone.setIddoc(0L);
		clone.setFlujo(BizDocEmail.SALIENTE);
		clone.setMailFrom(this.getMailFrom());
		clone.setMailTo(this.getMailTo());
		clone.setTitulo("RV: " + this.getTitulo());
		clone.setBody(this.findBodyAnswer());
		clone.setGrupo(this.getGrupo());	
		clone.setIdDocPadre(this.getIddoc());	
		//String title=mail.getTitulo()+" "+t.getNroCompuesto();
		clone.setSourceTipo(this.getSourceTipo());
		clone.setSourceCodigo(this.getSourceCodigo());
		clone.attachDocsFrom(this);
//		clone.setDatosLeidos(false);
		clone.setEnviar(true);
		return clone;
	}
	
	public String findBodyAnswer() throws Exception {
		String body = this.findBody();
		if (body.isEmpty()) return "";
		return "<br><br><br>-------<br>" + body;
	}

	public void verifyLeido() throws Exception {
		if (!this.isEntrante()) return;
		if (this.isLeido()) return;
		this.execProcessMarcarComoLeido();
	}

}
