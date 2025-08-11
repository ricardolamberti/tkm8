package pss.core.winUI.icons;

import java.net.URL;

import javax.swing.ImageIcon;

import pss.JPss;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;


public class BizIconGalery extends JRecord {
  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  public JString  pFile = new JString();
  public ImageIcon oImageIcon;

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizIconGalery() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "nombre_file", pFile);
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY , "nombre_file"  , "File", true, true, 100) ;
  }

  @Override
	public String GetTable() {return "";}

  public ImageIcon GetImage(String zIcon) throws Exception {
     ImageIcon oImage;
     URL uURL = JPss.class.getResource( "/pss/core/ui/Images/"+zIcon );
     oImage = new ImageIcon( uURL );
     return oImage;
  }

  public ImageIcon GetImage() {
    if ( oImageIcon != null ) return (this.oImageIcon);

    try { this.oImageIcon = this.GetImage(this.pFile.getValue()); }
    catch (Exception e) {
       try {this.oImageIcon =  this.GetImage("mundo.gif");}
       catch (Exception exc) {}
    }
    return this.oImageIcon;
  }



}


