package pss.common.customLogin;

import java.awt.Color;
import java.util.Date;

import pss.core.JAplicacion;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JColour;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JHour;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JIterator;

public class BizCustomLogin extends JRecord {

  private JLong pId = new JLong();
  private JString pDescription = new JString();
  private JString pBackgrountImageA = new JString();
  private JString pBackgrountImageB = new JString();
  private JColour pColorA = new JColour();
  private JColour pColorB = new JColour();
  private JColour pSecondaryTitleColor = new JColour();
  private JColour pItemColor = new JColour();
  private JString pStyle = new JString();

  private JDateTime pFechaDesde = new JDateTime();
  private JDateTime pFechaHasta = new JDateTime();
  private JHour pHoraDesde = new JHour();
  private JHour pHoraHasta = new JHour();

  private JBoolean pActive = new JBoolean();
  private JInteger pOrden = new JInteger();

  private JString pWelcome = new JString();
  private JLong pTitleLogo = new JLong();
  private JString pWelcomeText = new JString();
  private JString pFooterText = new JString();
  private JString pTextLink = new JString();
  private JString pLink = new JString();
  private JString pSecondaryTitle = new JString();
  private JString pLeyendaBoton = new JString();
  private JBoolean pCaptcha = new JBoolean();
  private JString pLeyendaUsuario = new JString();
  private JString pLeyendaClave = new JString();
  private JString pLogo = new JString();
  private JObjBDs<BizCustomLoginComponent> pTextos = new JObjBDs<BizCustomLoginComponent>() {
		public void preset() throws Exception {
			pTextos.setValue(getObjTextos());
		}
	};

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 
	public JRecords<BizCustomLoginComponent> getObjTextos() throws Exception {
	  JRecords<BizCustomLoginComponent> records = new JRecords<BizCustomLoginComponent>(BizCustomLoginComponent.class);
		records.addFilter("id", this.getId());
		records.addOrderBy("orden","ASC");
		return records;
	}


  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  
  public void setDescription(String zValue) throws Exception {    pDescription.setValue(zValue);  }
  public String getDescription() throws Exception {     return pDescription.getValue();  }
  
  public boolean hasBackgroundImageA() throws Exception {     return pBackgrountImageA.isNotNull() && !pBackgrountImageA.getValue().equals("");  }
  public String getBackgroundImageA() throws Exception {     return pBackgrountImageA.getValue();  }
  public void setBackgroundImageA(String zValue) throws Exception {    pBackgrountImageA.setValue(zValue);  }
  public boolean hasBackgroundImageB() throws Exception {     return pBackgrountImageB.isNotNull() && !pBackgrountImageB.getValue().equals("");  }
  public String getBackgroundImageB() throws Exception {     return pBackgrountImageB.getValue();  }
  public void setBackgroundImageB(String zValue) throws Exception {    pBackgrountImageB.setValue(zValue);  }
  public boolean hasBackgroundColorA() throws Exception {     return pColorA.isNotNull() && !pColorA.getValue().equals("");  }
  public Color getBackgroundColorA() throws Exception {     return pColorA.getColor();  }
  public void setBackgroundColorA(String zValue) throws Exception {    pColorA.setValue(zValue);  }
  public boolean hasBackgroundColorB() throws Exception {     return pColorB.isNotNull() && !pColorB.getValue().equals("");  }
  public Color getBackgroundColorB() throws Exception {     return pColorB.getColor();  }
  public void setBackgroundColorB(String zValue) throws Exception {    pColorB.setValue(zValue);  }

  public boolean hasSecondaryTitleColor() throws Exception {     return pSecondaryTitleColor.isNotNull() && !pSecondaryTitleColor.getValue().equals("");  }
  public Color getSecondaryTitleColor() throws Exception {     return pSecondaryTitleColor.getColor();  }
  public void setSecondaryTitleColor(String zValue) throws Exception {    pSecondaryTitleColor.setValue(zValue);  }

  public boolean hasItemColor() throws Exception {     return pItemColor.isNotNull() && !pItemColor.getValue().equals("");  }
  public Color getItemColor() throws Exception {     return pItemColor.getColor();  }
  public void setItemColor(String zValue) throws Exception {    pItemColor.setValue(zValue);  }

  public void setStyle(String zValue) throws Exception {    pStyle.setValue(zValue);  }
  public String getStyle() throws Exception {     return pStyle.getValue();  }
  
  public boolean hasDate() throws Exception {     return pFechaDesde.isNotNull();  }
  public boolean hasHour() throws Exception {     return pHoraDesde.isNotNull();  }

  public void setWelcome(String zValue) throws Exception {    pWelcome.setValue(zValue);  }
  public String getWelcome() throws Exception {     return pWelcome.getValue();  }
  
  public void setTitleLogo(long zValue) throws Exception {    pTitleLogo.setValue(zValue);  }
  public long getTitleLogo() throws Exception {     return pTitleLogo.getValue();  }

  public void setWelcomeText(String zValue) throws Exception {    pWelcomeText.setValue(zValue);  }
  public String getWelcomeText() throws Exception {     return pWelcomeText.getValue();  }

  public void setFooterText(String zValue) throws Exception {    pFooterText.setValue(zValue);  }
  public String getFooterText() throws Exception {     return pFooterText.getValue();  }
  
  public void setTextLink(String zValue) throws Exception {    pTextLink.setValue(zValue);  }
  public String getTextLink() throws Exception {     return pTextLink.getValue();  }

  public void setLink(String zValue) throws Exception {    pLink.setValue(zValue);  }
  public String getLink() throws Exception {     return pLink.getValue();  }

  public void setSecondaryTitle(String zValue) throws Exception {    pSecondaryTitle.setValue(zValue);  }
  public String getSecondaryTitle() throws Exception {     return pSecondaryTitle.getValue();  }

  public void setLeyendaBoton(String zValue) throws Exception {    pLeyendaBoton.setValue(zValue);  }
  public String getLeyendaBoton() throws Exception {     return pLeyendaBoton.getValue();  }

  public void setCaptcha(boolean zValue) throws Exception {    pCaptcha.setValue(zValue);  }
  public boolean hasCaptcha() throws Exception {     return pCaptcha.getValue();  }

  public void setLeyendaUsuario(String zValue) throws Exception {    pLeyendaUsuario.setValue(zValue);  }
  public String getLeyendaUsuario() throws Exception {     return pLeyendaUsuario.getValue();  }

  public void setLeyendaClave(String zValue) throws Exception {    pLeyendaClave.setValue(zValue);  }
  public String getLeyendaClave() throws Exception {     return pLeyendaClave.getValue();  }

  public void setLogo(String zValue) throws Exception {    pLogo.setValue(zValue);  }
  public String getLogo() throws Exception {     return pLogo.getValue();  }

  public boolean isActive() throws Exception {
  	if (!pActive.getValue()) return false;
  	if (hasDate()) {
  		if (!JDateTools.between(new Date(), pFechaDesde.getValue(), pFechaHasta.getValue())) return false;
  	}
  	if (hasHour()) {
  		if (!JDateTools.betweenHours(new Date(), pHoraDesde, pHoraHasta)) return false;
  	}
  	return true;
  }


  /**
   * Constructor de la Clase
   */
  public BizCustomLogin() throws Exception {
  }


  @Override
	public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "description", pDescription );
    this.addItem( "background_a", pBackgrountImageA );
    this.addItem( "background_b", pBackgrountImageB );
    this.addItem( "color_a", pColorA );
    this.addItem( "color_b", pColorB );
    this.addItem( "color_secondary", pSecondaryTitleColor);
    this.addItem( "color_item", pItemColor );
    this.addItem( "style", pStyle );
    this.addItem( "date_from", pFechaDesde );
    this.addItem( "date_to", pFechaHasta );
    this.addItem( "hour_from", pHoraDesde );
    this.addItem( "hour_to", pHoraHasta );
    this.addItem( "active", pActive );
    this.addItem( "orden", pOrden );
    
    this.addItem( "welcome", pWelcome );
    this.addItem( "title_logo", pTitleLogo );
    this.addItem( "welcome_text", pWelcomeText );
    this.addItem( "footer_text", pFooterText );
    this.addItem( "text_link", pTextLink );
    this.addItem( "link", pLink );
    this.addItem( "secondary_title", pSecondaryTitle );
    this.addItem( "leyenda_boton", pLeyendaBoton );
    this.addItem( "captcha", pCaptcha );
    this.addItem( "leyenda_usuario", pLeyendaUsuario );
    this.addItem( "leyenda_clave", pLeyendaClave );
    this.addItem( "logo", pLogo );
    this.addItem( "textos", pTextos );
  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", false, false, 64 );
    this.addFixedItem( FIELD, "description", "descripcion", true, true, 256 );
    this.addFixedItem( FIELD, "background_a", "fondo A", true, false, 256 );
    this.addFixedItem( FIELD, "background_b", "fondo B", true, false, 256 );
    this.addFixedItem( FIELD, "color_a", "color A", true, false, 256 );
    this.addFixedItem( FIELD, "color_b", "color B", true, false, 256 );
    this.addFixedItem( FIELD, "color_secondary", "color Secondary", true, false, 256 );
    this.addFixedItem( FIELD, "color_item", "color Item", true, false, 256 );
    this.addFixedItem( FIELD, "style", "estilo", true, false, 256 );
    this.addFixedItem( FIELD, "date_from", "fecha desde", true, false, 18 );
    this.addFixedItem( FIELD, "date_to", "fecha hasta", true, false, 18 );
    this.addFixedItem( FIELD, "hour_from", "hora desde", true, false, 18 );
    this.addFixedItem( FIELD, "hour_to", "hora hasta", true, false, 18 );
    this.addFixedItem( FIELD, "active", "activo", true, false, 1 );
    this.addFixedItem( FIELD, "orden", "orden", true, true, 18 );

    this.addFixedItem( FIELD, "welcome", "Bienvenida", true, false, 2000 );
    this.addFixedItem( FIELD, "title_logo", "Logo titulo", true, false, 18 );
    this.addFixedItem( FIELD, "welcome_text", "Bienvenida texto", true, false, 2000 );
    this.addFixedItem( FIELD, "footer_text", "Texto pie", true, false, 2000 );
    this.addFixedItem( FIELD, "text_link", "Texto Link", true, false, 2000 );
    this.addFixedItem( FIELD, "link", "Link", true, false, 2000 );
    this.addFixedItem( FIELD, "secondary_title", "Titulo secundario", true, false, 2000 );
    this.addFixedItem( FIELD, "leyenda_boton", "Leyenda boton", true, false, 2000 );
    this.addFixedItem( FIELD, "captcha", "Captcha?", true, false, 1 );
    this.addFixedItem( FIELD, "leyenda_usuario", "Leyenda Usuario", true, false, 2000 );
    this.addFixedItem( FIELD, "leyenda_clave", "Leyenda Clave", true, false, 2000 );
    this.addFixedItem( FIELD, "logo", "logo", true, false, 2000 );
    this.addFixedItem( RECORDS, "textos", "textos", true, false, 0 ).setClase(BizCustomLoginComponent.class);


  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "SEG_LOGINCUSTOM"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  @Override
  public void processInsert() throws Exception {
  	if (pOrden.isNull()) {
			BizCustomLogin max = new BizCustomLogin();
			pOrden.setValue(max.SelectMaxLong("orden") + 1);
  	}
  	super.processInsert();
  	JRecords<BizCustomLoginComponent> detalles = this.pTextos.getRawValue();
   	if (detalles!=null)
   		detalles.processProcessTable(this,true);

  }
  @Override
  public void processUpdate() throws Exception {
  	super.processUpdate();
  	JRecords<BizCustomLoginComponent> detalles = this.pTextos.getRawValue();
   	if (detalles!=null)
   		detalles.processProcessTable(this,true);
  }
  public static BizCustomLogin getDefaultCustomLogin() {
	  BizCustomLogin defa =null;
	  try {
			JAplicacion.GetApp().openSession();
			JAplicacion.GetApp().openApp("login", "Web", true);

			JRecords<BizCustomLogin> recs = new JRecords<BizCustomLogin>(BizCustomLogin.class);
		  recs.addFilter("active", true);
		  recs.addOrderBy("orden","DESC");
	  	JIterator<BizCustomLogin> it = recs.getStaticIterator();
	  	while (it.hasMoreElements()) {
	  		BizCustomLogin rec = it.nextElement();
	  		if (!rec.isActive()) continue;
	  		defa =rec;
	  		rec.getListItems();
	  		break;
	  	}
			JAplicacion.GetApp().closeApp();
			JAplicacion.GetApp().closeSession();
	} catch (Exception e) {
		defa = null;
	} 
	return defa;
  }

  String[] itemsreaded;
  public String[] getListItems() throws Exception { 
  	if (itemsreaded!=null) return itemsreaded;
  	JRecords<BizCustomLoginComponent> recs = getObjTextos();
  	JIterator<BizCustomLoginComponent> it = recs.getStaticIterator();
  	;
  	String[] items = new String[(int)recs.sizeStaticElements()];
  	int i =0;
  	while (it.hasMoreElements()) {
  		BizCustomLoginComponent li = it.nextElement();
  		items[i]=li.getTexto();
  		i++;
  	}
		return itemsreaded=items;
  }

  /**
   * Default Read() method
   */
  public boolean Read( long zId ) throws Exception { 
    addFilter( "id",  zId ); 
    return Read(); 
  }
  


}
