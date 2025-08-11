package pss.core.tools.formatters;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssPanel;


public class FormRegionalFormattersEdit extends JPssPanel {


  //////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////

  JPssEdit longDateFormat = new JPssEdit();
  JPanel jPanel1 = new JPanel();
  Border border1;
  TitledBorder titledBorder1;
  JPssLabel jLabel1 = new JPssLabel();
  JPssEdit shortDateFormat = new JPssEdit();
  JPssLabel jLabel2 = new JPssLabel();
//  JPssLabel longDateSample = new JPssLabel();
//  JPssLabel shortDateSample = new JPssLabel();
//  JPssLabel longTimeSample = new JPssLabel();
  JPssEdit longTimeFormat = new JPssEdit();
//  JPssLabel shortTimeSample = new JPssLabel();
  JPssEdit shortTimeFormat = new JPssEdit();
  JPanel jPanel2 = new JPanel();
  JPssLabel jLabel3 = new JPssLabel();
  JPssLabel jLabel4 = new JPssLabel();
  Border border2;
  TitledBorder titledBorder2;
  JPanel jPanel3 = new JPanel();
  TitledBorder titledBorder3;
  JPssEdit decimalSeparator = new JPssEdit();
  JPssLabel jLabel5 = new JPssLabel();
  JPssEdit thousandSeparator = new JPssEdit();
  JPssLabel jLabel6 = new JPssLabel();
//  JPssLabel numberSample = new JPssLabel();


  //////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////

  public FormRegionalFormattersEdit() {
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  //////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////

  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)), JLanguage.translate("Formatos de fecha"));
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)), JLanguage.translate("Formatos de hora"));
    titledBorder3 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)), JLanguage.translate("Símbolos para números"));
    longDateFormat.setBounds(new Rectangle(58, 22, 209, 23));
    this.setLayout(null);
    jPanel1.setBorder(titledBorder1);
    jPanel1.setBounds(new Rectangle(3, 0, 441, 60));
    jPanel1.setLayout(null);
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("Largo");
    jLabel1.setBounds(new Rectangle(8, 26, 43, 15));
    shortDateFormat.setBounds(new Rectangle(327, 22, 87, 23));
    jLabel2.setBounds(new Rectangle(272, 26, 49, 15));
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel2.setText("Corto");
//    longDateSample.setForeground(Color.blue);
//    longDateSample.setText("Lunes, 4 de Noviembre de 2002");
//    longDateSample.setBounds(new Rectangle(58, 46, 209, 21));
//    shortDateSample.setForeground(Color.blue);
//    shortDateSample.setText("04-11-2002");
//    shortDateSample.setBounds(new Rectangle(327, 46, 110, 21));
//    longTimeSample.setBounds(new Rectangle(59, 47, 206, 21));
//    longTimeSample.setForeground(Color.blue);
//    longTimeSample.setText("16:33:24");
    longTimeFormat.setBounds(new Rectangle(59, 23, 206, 23));
//    shortTimeSample.setBounds(new Rectangle(326, 47, 112, 21));
//    shortTimeSample.setForeground(Color.blue);
//    shortTimeSample.setText("16:33");
    shortTimeFormat.setBounds(new Rectangle(326, 23, 87, 23));
    jPanel2.setLayout(null);
    jPanel2.setBounds(new Rectangle(3, 58, 441, 60));
    jPanel2.setBorder(titledBorder2);
    jLabel3.setBounds(new Rectangle(273, 27, 46, 15));
    jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel3.setText("Corto");
    jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel4.setText("Largo");
    jLabel4.setBounds(new Rectangle(8, 27, 44, 15));
    jPanel3.setBorder(titledBorder3);
    jPanel3.setBounds(new Rectangle(3, 116, 441, 57));
    jPanel3.setLayout(null);
    decimalSeparator.setBounds(new Rectangle(116, 22, 32, 23));
    jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel5.setText("Separador decimal");
    jLabel5.setBounds(new Rectangle(10, 26, 99, 15));
    thousandSeparator.setBounds(new Rectangle(266, 22, 32, 23));
    jLabel6.setBounds(new Rectangle(156, 26, 104, 15));
    jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel6.setText("Separador de miles");
//    numberSample.setForeground(Color.blue);
//    numberSample.setHorizontalAlignment(SwingConstants.RIGHT);
//    numberSample.setText("15.656.899,45");
//    numberSample.setBounds(new Rectangle(308, 23, 115, 21));
//    jPanel1.add(longDateSample, null);
    jPanel1.add(jLabel1, null);
    jPanel1.add(longDateFormat, null);
//    jPanel1.add(shortDateSample, null);
    jPanel1.add(shortDateFormat, null);
    jPanel1.add(jLabel2, null);
    this.add(jPanel2, null);
    jPanel2.add(jLabel4, null);
    jPanel2.add(longTimeFormat, null);
//    jPanel2.add(longTimeSample, null);
//    jPanel2.add(shortTimeSample, null);
    jPanel2.add(jLabel3, null);
    jPanel2.add(shortTimeFormat, null);
    this.add(jPanel3, null);
    jPanel3.add(jLabel5, null);
    jPanel3.add(decimalSeparator, null);
//    jPanel3.add(numberSample, null);
    jPanel3.add(thousandSeparator, null);
    jPanel3.add(jLabel6, null);
    this.add(jPanel1, null);

//    this.longDateFormat.getDocument().addDocumentListener(new TextChangeListener() {protected void textChanged() {
//      showDateTimeFormatSample(longDateFormat, longDateSample, false);
//    }});
//    this.shortDateFormat.getDocument().addDocumentListener(new TextChangeListener() {protected void textChanged() {
//      showDateTimeFormatSample(shortDateFormat, shortDateSample, false);
//    }});
//
//    this.longTimeFormat.getDocument().addDocumentListener(new TextChangeListener() {protected void textChanged() {
//      showDateTimeFormatSample(longTimeFormat, longTimeSample, true);
//    }});
//    this.shortTimeFormat.getDocument().addDocumentListener(new TextChangeListener() {protected void textChanged() {
//      showDateTimeFormatSample(shortTimeFormat, shortTimeSample, true);
//    }});
//
//    this.decimalSeparator.getDocument().addDocumentListener(new TextChangeListener() {protected void textChanged() {
//      showNumberFormatSample(decimalSeparator, thousandSeparator, numberSample);
//    }});
//    this.thousandSeparator.getDocument().addDocumentListener(new TextChangeListener() {protected void textChanged() {
//      showNumberFormatSample(decimalSeparator, thousandSeparator, numberSample);
//    }});

//    UITools.hierarchy().whenShownInHierarchyDo(this, new JAct() {
//      public void Do() {
//        initializeAllFormatInputs();
//      }
//    });

  }


//  private void initializeAllFormatInputs() {
//    this.showDateTimeFormatSample(this.longDateFormat, this.longDateSample, false);
//    this.showDateTimeFormatSample(this.shortDateFormat, this.shortDateSample, false);
//    this.showDateTimeFormatSample(this.longTimeFormat, this.longTimeSample, true);
//    this.showDateTimeFormatSample(this.shortTimeFormat, this.shortTimeSample, true);
//    this.showNumberFormatSample(this.decimalSeparator, this.thousandSeparator, this.numberSample);
//  }

  public JPssEdit getDecimalSeparator() {
    return decimalSeparator;
  }
  public JPssEdit getLongDateFormat() {
    return longDateFormat;
  }
  public JPssEdit getLongTimeFormat() {
    return longTimeFormat;
  }
  public JPssEdit getShortDateFormat() {
    return shortDateFormat;
  }
  public JPssEdit getShortTimeFormat() {
    return shortTimeFormat;
  }
  public JPssEdit getThousandSeparator() {
    return thousandSeparator;
  }


//  private boolean showDateTimeFormatSample(JPssEdit zInputField, JPssLabel zSampleLabel, boolean isTime) {
//    String sResult;
//    boolean bResult;
//    try {
//      String sPattern = zInputField.getText();
//      SimpleDateFormat oFormat = new SimpleDateFormat(sPattern);
//      oFormat.setLenient(false);
//      Calendar oCal = Calendar.getInstance();
//      if (!isTime) {
//        oCal.set(Calendar.HOUR_OF_DAY, 0);
//        oCal.set(Calendar.MINUTE, 0);
//        oCal.set(Calendar.SECOND, 0);
//        oCal.set(Calendar.MILLISECOND, 0);
//        Date oOriginalDate = oCal.getTime();
//        sResult = oFormat.format(oOriginalDate);
//        if (!oOriginalDate.equals(oFormat.parse(sResult))) {
//          JExcepcion.SendError("El formato no garantiza la interconversión en ambos sentidos");
//        }
//      } else {
//        this.checkAllValidCharsForTimeFormat(zInputField.getText());
//        sResult = oFormat.format(oCal.getTime());
//      }
//      zSampleLabel.setForeground(Color.BLUE);
//      bResult = true;
//    } catch (Exception ex) {
//      sResult = "<formato inválido>";
//      zSampleLabel.setForeground(Color.RED);
//      bResult = false;
//    }
//    zSampleLabel.setText(sResult);
//    return bResult;
//  }
//
//
//  private boolean hasAtLeastFourDigitsInYear(String zPattern) {
//    return zPattern.indexOf("yyyy") >= 0;
//  }

//  private void checkAllValidCharsForTimeFormat(String zPattern) throws Exception {
//    int count = zPattern.length();
//    for (int i = 0; i < count; i++) {
//      char c = zPattern.charAt(i);
//      if (c=='M' || c=='d' || c=='D' || c=='S' || c=='E' || c=='e' || c=='y' || c=='Y') {
//        JExcepcion.SendError("Caracter inválido en formato de hora: " + c);
//      }
//    }
//  }
//
//  private boolean showNumberFormatSample(JPssEdit zDecimalSeparatorField, JPssEdit zThousandSeparatorField, JPssLabel zSampleLabel) {
//    String sResult;
//    boolean bResult;
//    try {
//      DecimalFormatSymbols oSymbols = new DecimalFormatSymbols();
//      oSymbols.setDecimalSeparator(zDecimalSeparatorField.getText().charAt(0));
//      oSymbols.setGroupingSeparator(zThousandSeparatorField.getText().charAt(0));
//      DecimalFormat oFormat = new DecimalFormat("#,##0.00", oSymbols);
//      sResult = oFormat.format(11220.76);
//      oFormat.parse(sResult);
//      zSampleLabel.setForeground(Color.BLUE);
//      bResult = true;
//    } catch (Exception ex) {
//      sResult = "<símbolos inválidos>";
//      zSampleLabel.setForeground(Color.RED);
//      bResult = false;
//    }
//    zSampleLabel.setText(sResult);
//    return bResult;
//  }




//  public void validateFormats() throws Exception {
//    if (!this.longDateFormat.getText().trim().equals("") && !this.showDateTimeFormatSample(this.longDateFormat, this.longDateSample, false)) {
//      JExcepcion.SendError("Formato largo de fecha inválido.");
//    }
//    if (!this.shortDateFormat.getText().trim().equals("") && !this.showDateTimeFormatSample(this.shortDateFormat, this.shortDateSample, false)) {
//      JExcepcion.SendError("Formato corto de fecha inválido.");
//    }
//    if (!this.longTimeFormat.getText().trim().equals("") && !this.showDateTimeFormatSample(this.longTimeFormat, this.longTimeSample, true)) {
//      JExcepcion.SendError("Formato largo de hora inválido.");
//    }
//    if (!this.shortTimeFormat.getText().trim().equals("") && !this.showDateTimeFormatSample(this.shortTimeFormat, this.shortTimeSample, true)) {
//      JExcepcion.SendError("Formato corto de hora inválido.");
//    }
//    if (!(this.decimalSeparator.getText().trim().equals("") && this.thousandSeparator.getText().trim().equals("")) && !this.showNumberFormatSample(this.decimalSeparator, this.thousandSeparator, this.numberSample)) {
//      JExcepcion.SendError("Símbolos para números inválidos.");
//    }
//    if (!(this.decimalSeparator.getText().trim().equals("") && this.thousandSeparator.getText().trim().equals("")) && this.decimalSeparator.getText().trim().equals(this.thousandSeparator.getText().trim())) {
//      JExcepcion.SendError("El separador decimal no puede ser igual al separador de miles.");
//    }
//  }
//






//  private class TextChangeListener implements DocumentListener {
//    public void insertUpdate(DocumentEvent e) {
//      if (isShowing()) {
//        this.textChanged();
//      }
//    }
//    public void removeUpdate(DocumentEvent e) {
//      if (isShowing()) {
//        this.textChanged();
//      }
//    }
//    public void changedUpdate(DocumentEvent e) {
//      if (isShowing()) {
//        this.textChanged();
//      }
//    }
//    protected void textChanged() {
//      // by default, do nothing; ovverride it !!
//    }
//  }


}
