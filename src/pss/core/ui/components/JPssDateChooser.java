package pss.core.ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DateFormatter;
import javax.swing.text.PlainDocument;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.formatters.JRegionalFormatterFactory;

public class JPssDateChooser extends JComponent {


  //////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
	public static final int DATE = 0;
	public static final int TIME = 1;
	public static final int DATETIME = 2;
	
	private int model = DATE;
	
  private JFormattedTextField field;
  private JButton button;
  private JSpinner monthSpinner;
  private JSpinner yearSpinner;
  private JPanel monthGridPanel;
  private JPanel chooserPanel;
  private int columns = 7;
  private int rows = 6;
  private JList<JButton> buttons;
  private ComponentsListener componentsListener;
  private Border selectedButtonBorder;
  private Border unselectedButtonBorder;
  private Calendar value;
  private int firstDayOfWeek;
  private int selectedMonth;
  private int selectedYear;
  private JPopupMenu popup;
  private JButton selectedButton;
  // bean properties
  private Dimension cellSize;
  private Dimension headerCellSize;
  private Dimension monthSpinnerSize;
  private Dimension yearSpinnerSize;
  private Font cellFont;
  private Font headerCellFont;
  private Font spinnersFont;
  private SimpleDateFormat dateFormat;
  private String dateSeparator;
  private int yearPositionInPattern; // 1, 2, or 3
  private Date miminumAllowed;
  private PropertyChangeSupport changeNotifier;
  private String[] aMonthNames;
  private String[] aDayNames;

 	int hour = 0;
	int minute = 0;
	int second = 0;

  //////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////
  public JPssDateChooser(int zModel) {
    this.model = zModel;
    this.initialize();
  }


  //////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////

  public void addValueListener(PropertyChangeListener listener) {
    this.changeNotifier.addPropertyChangeListener("value", listener);
  }
  public void removeValueListener(PropertyChangeListener listener) {
    this.changeNotifier.removePropertyChangeListener("value", listener);
  }

  @Override
	public void setEnabled(boolean zEnabled) {
    this.cancelPopup();
    super.setEnabled(zEnabled);
    this.field.setEditable(zEnabled);
    this.button.setEnabled(zEnabled);
  }

  private void initialize() {
    DateFormatter df = new DateFormatter(getDateFormat());
    field = new JFormattedTextField(df);
    button = new JPssButton();
    button.setPreferredSize(new Dimension(21, 21));
    button.setText("...");
    button.setFocusable(false);
    button.setHorizontalAlignment(SwingConstants.CENTER);
    button.setHorizontalTextPosition(SwingConstants.CENTER);
    button.setVerticalAlignment(SwingConstants.CENTER);
    button.setVerticalTextPosition(SwingConstants.CENTER);

    
    this.setLayout(new BorderLayout(0,0));
    this.add(field, BorderLayout.CENTER);
    this.add(button, BorderLayout.EAST);

    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        popupRequested(e);
      }
    });

    this.firstDayOfWeek = Calendar.getInstance().getFirstDayOfWeek();

    this.changeNotifier = new PropertyChangeSupport(this);

    this.setDefaultBeanProperties();
    this.buttons = JCollectionFactory.createList(31);
    this.componentsListener = new ComponentsListener();

    this.field.addKeyListener(this.componentsListener);
    this.field.addFocusListener(this.componentsListener);

    this.setUpTextFieldForDateInput();
    this.field.getDocument().addDocumentListener(this.componentsListener);

    this.updateTextField();
  }


  private void setUpTextFieldForDateInput() {
    this.field.setDocument(new PlainDocument() {
      @Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        int count = str.length();
        boolean allValid = count > 0;
        for (int i = 0; i < count; i++) {
          char c = str.charAt(i);
          allValid = allValid && (Character.isDigit(c) || dateSeparator.indexOf(c)!=-1);
        }
        if (allValid && (field.getText().length() + count) <= dateFormat.toPattern().length()) {
          super.insertString(offs, str, a);
        } else {
          if (isShowing()) {
            Toolkit.getDefaultToolkit().beep();
          }
        }
      }
    });
  }



/*  private void removeAllListeners() {
    this.field.removeKeyListener(this.componentsListener);
    this.field.removeFocusListener(this.componentsListener);
    this.field.getDocument().removeDocumentListener(this.componentsListener);

    JIterator oIt = this.buttons.getIterator();
    while (oIt.hasMoreElements()) {
      JButton oButton = (JButton) oIt.nextElement();
      oButton.removeMouseListener(this.componentsListener);
      oButton.removeActionListener(this.componentsListener);
    }
  }
*/
  private void setDefaultBeanProperties() {
    this.cellSize = new Dimension(22,22);
    this.headerCellSize = new Dimension(22,16);
    this.monthSpinnerSize = new Dimension(88,21);
    this.yearSpinnerSize = new Dimension(58,21);
    this.cellFont = new Font("Arial", Font.PLAIN, 10);
    this.headerCellFont = new Font("Arial", Font.PLAIN, 9);
    this.spinnersFont = new Font("Arial", Font.PLAIN, 10);
    this.selectedButtonBorder = BorderFactory.createLoweredBevelBorder();
    this.unselectedButtonBorder = BorderFactory.createEmptyBorder();
    this.getDateFormat();
  }
  private void createDateFormat() {
    try {
      this.setDatePattern(JRegionalFormatterFactory.getRegionalFormatter().getShortDatePattern());
    } catch (Exception ex) {
      this.createDefaultDateFormat();
      PssLogger.logDebug(ex, "No se pudo crear formato de fecha regional");
    }
  }
  private void createTimeFormat() {
    try {
      this.setDatePattern(JRegionalFormatterFactory.getRegionalFormatter().getShortTimePattern());
    } catch (Exception ex) {
      this.createDefaultTimeFormat();
      PssLogger.logDebug(ex, "No se pudo crear formato de fecha regional");
    }
  }
  private void createDateTimeFormat() {
    try {
      this.setDatePattern(JRegionalFormatterFactory.getRegionalFormatter().getDateTimePattern());
    } catch (Exception ex) {
      this.createDefaultDateTimeFormat();
      PssLogger.logDebug(ex, "No se pudo crear formato de fecha regional");
    }
  }
  private void validateDateFormat() {
    // tiene que ser lenient
    if (this.dateFormat.isLenient()) {
      PssLogger.logDebug("El DateFormat de un JPssDateChooser NO debe ser misericordioso (lenient)");
      this.createDefaultDateFormat();
      return;
    }
    // el patron no puede estar vacío
    String sample = this.dateFormat.format(new Date());
    if (sample==null || sample.trim().length() < 1) {
      PssLogger.logDebug("El DateFormat de un JPssDateChooser debe tener un patrón no vacío");
      this.createDefaultDateFormat();
      return;
    }
  }
  private void createDefaultDateFormat() {
    this.setDatePattern("dd-MM-yyyy");
  }
  private void createDefaultTimeFormat() {
    this.setDatePattern("HH:mm:ss");
  }
  private void createDefaultDateTimeFormat() {
    this.setDatePattern("dd-MM-yyyy HH:mm:ss");
  }

  private void popupRequested(ActionEvent e) {
    if (this.popup==null) {
      JPopupMenu newPopup = new JPopupMenu();
      newPopup.setLayout(new BorderLayout());
      newPopup.add(this.getChooserPanel());
      newPopup.pack();
      this.popup = newPopup;
    }
    this.popup.show(this.field, 0, this.field.getHeight());
    this.field.requestFocus();
  }
  private void cancelPopup() {
    if (this.popup != null) {
      this.popup.setVisible(false);
      this.field.requestFocus();
    }
  }

  private void refreshPopupAttributes() {
    this.cancelPopup();
    this.chooserPanel = null;
    this.monthSpinner = null;
    this.yearSpinner = null;
    this.monthGridPanel = null;
    this.chooserPanel = null;
  }

  private JPanel getMonthGridPanel() {
    this.getChooserPanel();
    return this.monthGridPanel;
  }
  private JPanel getChooserPanel() {
    if (this.chooserPanel != null) {
      return this.chooserPanel;
    }

    this.chooserPanel = new JPanel();
    this.chooserPanel.setLayout(new BorderLayout(0,0));
    //
    // month/year panel
    //
    JPanel spinnersPanel = new JPanel();
    int hgap = 3;
    int vgap = 3;
    Dimension spinnersPanelSize = new Dimension(0,
      Math.max(this.monthSpinnerSize.height, this.yearSpinnerSize.height) + (vgap*2)
    );
    spinnersPanel.setLayout(new FlowLayout(FlowLayout.LEFT, hgap, vgap));
    spinnersPanel.setPreferredSize(spinnersPanelSize);
    spinnersPanel.setSize(spinnersPanelSize);
//    JPssLabel monthHeader = new JPssLabel("Mes");
//    monthHeader.setOpaque(false);
//    monthHeader.setFont(this.spinnersFont);
//    JPssLabel yearHeader = new JPssLabel("Año");
//    yearHeader.setOpaque(false);
//    yearHeader.setFont(this.spinnersFont);
//    spinnersPanel.add(monthHeader);
    spinnersPanel.add(this.createMonthSpinner());
//    spinnersPanel.add(yearHeader);
    spinnersPanel.add(this.createYearSpinner());
    this.chooserPanel.add(spinnersPanel, BorderLayout.NORTH);
    //
    // grid panel
    //
    JPanel gridPanel = new JPanel();
    gridPanel.setLayout(new BorderLayout(0,0));
    // header
    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    for (int i = firstDayOfWeek; i <= 7; i++) {
      headerPanel.add(this.createHeaderLabel(i));
    }
    for (int i = 1; i < firstDayOfWeek; i++) {
      headerPanel.add(this.createHeaderLabel(i));
    }
    headerPanel.setPreferredSize(new Dimension(this.headerCellSize.width * columns, this.headerCellSize.height));
    gridPanel.add(headerPanel, BorderLayout.NORTH);
    // grid
    this.monthGridPanel = new JPanel();
    gridPanel.add(this.monthGridPanel, BorderLayout.CENTER);
    this.monthGridPanel.setPreferredSize(new Dimension(this.cellSize.width * columns, this.cellSize.height * rows));
    this.monthGridPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    this.chooserPanel.add(gridPanel, BorderLayout.CENTER);
    // size for the grid panel
    gridPanel.setPreferredSize(new Dimension(headerPanel.getPreferredSize().width, headerPanel.getPreferredSize().height + this.monthGridPanel.getPreferredSize().height));
    // size for the the entire panel
    this.chooserPanel.setPreferredSize(new Dimension(gridPanel.getPreferredSize().width, gridPanel.getPreferredSize().height + spinnersPanel.getPreferredSize().height));
    // update the month view
    this.updateFieldsFromSelectedValue(true);
    //
    return this.chooserPanel;
  }


  private void updateMonthView() {
    if (this.chooserPanel == null) return;

    Calendar oCalendar = Calendar.getInstance();
    oCalendar.set(this.selectedYear, this.selectedMonth, 1);
    // create grid
    int daysInTheMonth = oCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    int firstDayOfWeekInMonth = oCalendar.get(Calendar.DAY_OF_WEEK);
    this.getMonthGridPanel().removeAll();
    JIterator<JButton> oIt = this.buttons.getIterator();
    while (oIt.hasMoreElements()) {
      JButton oButton = oIt.nextElement();
      oButton.removeMouseListener(this.componentsListener);
      oButton.removeActionListener(this.componentsListener);
    }
    this.buttons.removeAllElements();
    int cells = columns * rows;
    int dayOfMonth = 0;
    int counterIncrement;
    if (firstDayOfWeekInMonth > firstDayOfWeek) {
      counterIncrement = firstDayOfWeek - Calendar.SUNDAY;
    } else if (firstDayOfWeekInMonth < firstDayOfWeek) {
      counterIncrement = firstDayOfWeek - Calendar.SUNDAY - 7;
    } else {
      counterIncrement = firstDayOfWeekInMonth - Calendar.SUNDAY;
    }
    for (int i = 1; i <= cells; i++) {
      if ((i + counterIncrement) < firstDayOfWeekInMonth || dayOfMonth >= daysInTheMonth) {
        this.getMonthGridPanel().add(this.createFillLabel());
      } else {
        dayOfMonth++;
        JButton oButton = this.createNumberButton(dayOfMonth);
        this.getMonthGridPanel().add(oButton);
        this.buttons.addElement(oButton);
      }
    }
    // selects the suitable button
    this.selectSuitableButton();
  }

  private void selectSuitableButton() {
    Calendar date = this.value;
    if (date == null) {
      date = Calendar.getInstance();
    }
    int indexToSelect = date.get(Calendar.DAY_OF_MONTH);
    this.setSelectedButton(this.buttons.getElementAt(indexToSelect-1));
  }

  private JButton createNumberButton(int number) {
    JButton oButton = new JButton();
    oButton.setSize(cellSize);
    oButton.setFont(this.cellFont);
    oButton.setPreferredSize(cellSize);
    oButton.setFocusable(false);
    oButton.setText(String.valueOf(number));
    oButton.setHorizontalAlignment(SwingConstants.CENTER);
    oButton.setMargin(new Insets(0,0,0,0));
    oButton.addKeyListener(this.componentsListener);
    oButton.addMouseListener(this.componentsListener);
    oButton.addActionListener(this.componentsListener);
    oButton.setBorder(this.unselectedButtonBorder);
    return oButton;
  }
  private JPssLabel createFillLabel() {
    JPssLabel oLabel = new JPssLabel();
    oLabel.setOpaque(false);
    oLabel.setText("");
    oLabel.setSize(cellSize);
    oLabel.setPreferredSize(cellSize);
    return oLabel;
  }
  private JPssLabel createHeaderLabel(int dayOfWeek) {
    JPssLabel oLabel = new JPssLabel();
    oLabel.setOpaque(false);
    oLabel.setFont(this.headerCellFont);
    oLabel.setText(getDayOfWeekName(dayOfWeek));
    oLabel.setHorizontalAlignment(SwingConstants.CENTER);
    oLabel.setSize(this.headerCellSize);
    oLabel.setPreferredSize(this.headerCellSize);
    return oLabel;
  }
  private JSpinner createMonthSpinner() {
    // create model
    SpinnerListModel oModel = new SpinnerListModel();
    oModel.setList(Arrays.asList(getMonthNames()));
    // create spinner
    JSpinner oSpinner = new JSpinner();
    oSpinner.setModel(oModel);
    oSpinner.setPreferredSize(this.monthSpinnerSize);
    oSpinner.setSize(this.monthSpinnerSize);
    JFormattedTextField oField = ((JSpinner.DefaultEditor)oSpinner.getEditor()).getTextField();
    oField.setFont(this.spinnersFont);
    oField.setEditable(false);
    oField.setFocusable(false);
    this.monthSpinner = oSpinner;

    // set initial value
    Calendar initial = this.value;
    if (initial==null) {
      initial = Calendar.getInstance();
    }
    oModel.setValue(oModel.getList().get(initial.get(Calendar.MONTH)));
    // add listener
    oModel.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        SpinnerListModel model = (SpinnerListModel) e.getSource();
        int monthIndex = model.getList().indexOf(model.getValue());
        setSelectedMonth(monthIndex);
        updateMonthView();
      }
    });
    //
    return oSpinner;
  }
  private JSpinner createYearSpinner() {
    // create model
    SpinnerNumberModel oModel = new SpinnerNumberModel();
    oModel.setStepSize(new Integer(1));
    // create spinner
    JSpinner oSpinner = new JSpinner();
    oSpinner.setModel(oModel);
    oSpinner.setPreferredSize(this.yearSpinnerSize);
    oSpinner.setSize(this.yearSpinnerSize);
    JFormattedTextField oField = ((JSpinner.DefaultEditor)oSpinner.getEditor()).getTextField();
    oField.setFont(this.spinnersFont);
    oField.setEditable(false);
    oField.setFocusable(false);
    this.yearSpinner = oSpinner;

    // set initial value
    Calendar initial = this.value;
    if (initial==null) {
      initial = Calendar.getInstance();
    }
    oModel.setValue(new Integer(initial.get(Calendar.YEAR)));
    // add listener
    oModel.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        SpinnerNumberModel model = (SpinnerNumberModel) e.getSource();
        int year = ((Integer)model.getValue()).intValue();
        setSelectedYear(year);
        updateMonthView();
      }
    });
    //
    return oSpinner;
  }
  private String[] getMonthNames() {
    if (this.aMonthNames==null) {
      this.aMonthNames = new String[] {
      JLanguage.translate("Enero"),
      JLanguage.translate("Febrero"),
      JLanguage.translate("Marzo"),
      JLanguage.translate("Abril"),
      JLanguage.translate("Mayo"),
      JLanguage.translate("Junio"),
      JLanguage.translate("Julio"),
      JLanguage.translate("Agosto"),
      JLanguage.translate("Septiembre"),
      JLanguage.translate("Octubre"),
      JLanguage.translate("Noviembre"),
      JLanguage.translate("Diciembre")
      }; 
    }
    return this.aMonthNames;
  }
  private String[] getDayNames() {
    if (this.aDayNames==null) {
      this.aDayNames = new String[] {
      JLanguage.translate("Domingo"),
      JLanguage.translate("Lunes"),
      JLanguage.translate("Martes"),
      JLanguage.translate("Miércoles"),
      JLanguage.translate("Jueves"),
      JLanguage.translate("Viernes"),
      JLanguage.translate("Sábado")
      };
    }
    return this.aDayNames;
  }
  private String getDayOfWeekName(int day) {
    return getDayNames()[day-1].substring(0,3);
  }
//  private String getMonthName(int month) {
//    return getMonthNames()[month];
//  }


  //////////////////////////////////////////////////////////////////////////////
  //
  //   LISTENERS
  //
  //////////////////////////////////////////////////////////////////////////////

  private class ComponentsListener implements KeyListener, MouseListener, ActionListener, FocusListener, DocumentListener {
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void focusGained(FocusEvent e) {}

    //
    //  events from a button
    //
    public void mouseEntered(MouseEvent e) {
      JButton oButton = (JButton) e.getSource();
      setSelectedButton(oButton);
    }
    public void actionPerformed(ActionEvent e) {
      JButton oButton = (JButton) e.getSource();
      setValueFrom(oButton);
      cancelPopup();
    }
    public void mousePressed(MouseEvent e) {
      JButton oButton = (JButton) e.getSource();
      oButton.doClick(100);
    }

    //
    //  events from the text field
    //

    public void insertUpdate(DocumentEvent e) {
      setValueFromInput(false);
    }
    public void removeUpdate(DocumentEvent e) {
      setValueFromInput(false);
    }
    public void changedUpdate(DocumentEvent e) {
      setValueFromInput(false);
    }

    public void focusLost(FocusEvent e) {
      if (e.isTemporary()) return;
      setValueFromInput(true);
    }
    public void keyPressed(KeyEvent e) {
      int keyCode = e.getKeyCode();
      JButton oButton = selectedButton;
      if (popup==null || !popup.isVisible()) {
        // teclas sobre el campo
        switch (keyCode) {
          case KeyEvent.VK_ENTER:
            // aplica pero no consume el evento para que vaya al padre
            setValueFromInput(true);
            break;
          case KeyEvent.VK_DOWN:
            setValueFromInput(true);
            button.doClick();
            e.consume();
            break;
        }
      } else {
        // teclas sobre el pop-up
        switch (keyCode) {
          case KeyEvent.VK_ENTER:
          case KeyEvent.VK_SPACE:
            oButton.doClick();
            e.consume();
            break;
          case KeyEvent.VK_ESCAPE:
            cancelPopup();
            e.consume();
            break;
          case KeyEvent.VK_DOWN:
            selectDownButton(oButton);
            e.consume();
            break;
          case KeyEvent.VK_UP:
            selectUpButton(oButton);
            e.consume();
            break;
          case KeyEvent.VK_LEFT:
            selectLeftButton(oButton);
            e.consume();
            break;
          case KeyEvent.VK_RIGHT:
            selectRightButton(oButton);
            e.consume();
            break;
        }
      }

    }
  }


  private void selectDownButton(JButton zButton) {
    int currIndex = this.buttons.indexOf(zButton);
    int maxIndex = this.buttons.size() - 1;
    int newIndex = currIndex + 7;
    if (newIndex > maxIndex) return;
    this.setSelectedButton(this.buttons.getElementAt(newIndex));
  }
  private void selectUpButton(JButton zButton) {
    int currIndex = this.buttons.indexOf(zButton);
    int newIndex = currIndex - 7;
    if (newIndex < 0) return;
    this.setSelectedButton(this.buttons.getElementAt(newIndex));
  }
  private void selectRightButton(JButton zButton) {
    int currIndex = this.buttons.indexOf(zButton);
    int maxIndex = this.buttons.size() - 1;
    int newIndex = currIndex + 1;
    if (newIndex > maxIndex) return;
    this.setSelectedButton(this.buttons.getElementAt(newIndex));
  }
  private void selectLeftButton(JButton zButton) {
    int currIndex = this.buttons.indexOf(zButton);
    //int maxIndex = this.buttons.size() - 1;
    int newIndex = currIndex - 1;
    if (newIndex < 0) return;
    this.setSelectedButton(this.buttons.getElementAt(newIndex));
  }
  private void setSelectedButton(JButton oButton) {
    oButton.setBorder(this.selectedButtonBorder);
    JIterator<JButton> oIt = this.buttons.getIterator();
    while (oIt.hasMoreElements()) {
      JButton b = oIt.nextElement();
      if (oButton != b) {
        b.setBorder(this.unselectedButtonBorder);
      }
    }
    this.selectedButton = oButton;
  }
  private void setValueFrom(JButton oButton) {
    int day = Integer.parseInt(oButton.getText());
    this.setValue(new GregorianCalendar(this.selectedYear, this.selectedMonth, day,hour,minute,second));
  }
  private void setSelectedMonth(int month) {
    this.selectedMonth = month;
    if (this.value != null) {
      this.value.set(Calendar.MONTH, month);
    }
  }
  private void setSelectedYear(int year) {
    this.selectedYear = year;
    if (this.value != null) {
      this.value.set(Calendar.YEAR, year);
    }
  }

  private void updateFieldsFromSelectedValue(boolean zUpdateText) {
    Calendar initial = this.value;
    if (initial==null) {
      initial = Calendar.getInstance();
    }
    this.setSelectedMonth(initial.get(Calendar.MONTH));
    this.setSelectedYear(initial.get(Calendar.YEAR));
   	hour = initial.get(Calendar.HOUR);
  	minute = initial.get(Calendar.MINUTE);
  	second = initial.get(Calendar.SECOND);
    if (zUpdateText) {
      this.updateTextField();
    }
    this.updateSpinners();
    this.updateMonthView();
  }
  private void updateTextField() {
  	if (field==null) return;
    if (this.value!=null) {
      this.field.setText(this.getDateFormat().format(this.value.getTime()));
    } else {
      this.field.setText("");
    }
    this.changeNotifier.firePropertyChange("value", null, this.value);
    this.resetFieldColor();
  }
  private void resetFieldColor() {
    this.field.setForeground(this.getForeground());
  }
  private void updateSpinners() {
    if (this.monthSpinner != null) {
      this.monthSpinner.setValue(this.getMonthNames()[this.selectedMonth]);
    }
    if (this.yearSpinner != null) {
      this.yearSpinner.setValue(new Integer(this.selectedYear));
    }
  }
  private void setValueFromInput(boolean zUpdateText) {
    Date oDate = null;
    if (this.field.getText()==null || this.field.getText().length() < 1) {
      this.setValue(null, zUpdateText);
    } else {
      try {
        if (this.field.getText().length()==this.getDateFormat().toPattern().length()) {
          oDate = this.getDateFormat().parse(this.field.getText());
        }
      } catch (Exception ex) {}
      boolean dateIsValid = oDate != null && !oDate.before(this.getMiminumAllowed());
      if (dateIsValid) {
        Calendar newValue = Calendar.getInstance();
        newValue.setTime(oDate);
        this.setValue(newValue, zUpdateText);
        this.resetFieldColor();
      } else {
        this.field.setForeground(Color.RED);
        this.updateFieldsFromSelectedValue(zUpdateText);
      }
    }
  }

  public Calendar getValue() {
    return this.value;
  }
  public void setValue(Calendar zValue) {
    this.setValue(zValue, true);
  }
  private void setValue(Calendar zValue, boolean zUpdateText) {
    this.value = zValue;
    this.updateFieldsFromSelectedValue(zUpdateText);
  }



  public static void main(String[] args) {
    JFrame oFrame = new JFrame();
    JPssDateChooser oChooser = new JPssDateChooser(DATETIME);
    JPanel oPanel = new JPanel();
    oPanel.setLayout(null);
    oChooser.setBounds(new Rectangle(75, 60, 250, 21));
    oPanel.add(oChooser, null);
    JTextField otherText = new JTextField();
    otherText.setBounds(new Rectangle(75, 100, 99, 21));
    oPanel.add(otherText);

    JFrame.setDefaultLookAndFeelDecorated(true);
    oFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    oFrame.getContentPane().add(oPanel);
    oFrame.setSize(400,300);
    oFrame.show();
  }
  public Border getUnselectedButtonBorder() {
    return unselectedButtonBorder;
  }
  public void setUnselectedButtonBorder(Border unselectedButtonBorder) {
    this.unselectedButtonBorder = unselectedButtonBorder;
    this.refreshPopupAttributes();
  }
  public Font getSpinnersFont() {
    return spinnersFont;
  }
  public void setSpinnersFont(Font spinnersFont) {
    this.spinnersFont = spinnersFont;
    this.refreshPopupAttributes();
  }
  public Border getSelectedButtonBorder() {
    return selectedButtonBorder;
  }
  public void setSelectedButtonBorder(Border selectedButtonBorder) {
    this.selectedButtonBorder = selectedButtonBorder;
    this.refreshPopupAttributes();
  }
  public Dimension getMonthSpinnerSize() {
    return monthSpinnerSize;
  }
  public void setMonthSpinnerSize(Dimension monthSpinnerSize) {
    this.monthSpinnerSize = monthSpinnerSize;
    this.refreshPopupAttributes();
  }
  public Dimension getHeaderCellSize() {
    return headerCellSize;
  }
  public Font getHeaderCellFont() {
    return headerCellFont;
  }
  public void setHeaderCellSize(Dimension headerCellSize) {
    this.headerCellSize = headerCellSize;
    this.refreshPopupAttributes();
  }
  public void setHeaderCellFont(Font headerCellFont) {
    this.headerCellFont = headerCellFont;
    this.refreshPopupAttributes();
  }
  public SimpleDateFormat getDateFormat() {
    if (this.dateFormat==null) {
    	switch (model) {
    	case DATE:
        this.createDateFormat();
        break;
    	case TIME:
        this.createTimeFormat();
        break;
    	case DATETIME:
        this.createDateTimeFormat();
        break;
    	}
    }
    return this.dateFormat;
  }
  public void setDatePattern(String zDatePattern) {
    this.dateFormat = new SimpleDateFormat(zDatePattern);
    this.dateFormat.setLenient(false);
    this.dateSeparator = this.findDateSeparator(zDatePattern);
    if (this.dateSeparator.equals("")) {
      throw new RuntimeException("Patron de fecha sin separador: " + zDatePattern);
    }
    this.yearPositionInPattern = this.findYearPosition(zDatePattern, this.dateSeparator);
    if ((this.yearPositionInPattern < 1 || this.yearPositionInPattern > 3) && model!=TIME) {
      throw new RuntimeException("Patron de fecha incorrecto: " + zDatePattern + ". Debe incluir las variables dd, MM, yyyy y el separador");
    }
    this.validateDateFormat();
    this.updateTextField();
  }

  private String findDateSeparator(String zPattern) {
    int count = zPattern.length();
    String sep = "";
    for (int i = 0; i < count; i++) {
      char c = Character.toLowerCase(zPattern.charAt(i));
      if (c != 'd' && c != 'm' && c != 'y' && c != 'H' && c != 'm' && c != 's') {
        sep+= zPattern.charAt(i);
      }
    }
    return sep;
  }
  private int findYearPosition(String zPattern, String sep) {
    int count = zPattern.length();
    int pos = 0;
    char c = 0x00;
    for (int i = 0; i < count; i++) {
      char newC = zPattern.charAt(i);
      if (sep.indexOf(newC)!=-1) {
        if (c=='y') {
          pos++;
          return pos;
        } else if (c=='d' || c=='M') {
          pos++;
        } else {
          return 0;
        }
      }
      c = newC;
    }
    return pos;
  }

  public Dimension getCellSize() {
    return cellSize;
  }
  public void setCellSize(Dimension cellSize) {
    this.cellSize = cellSize;
    this.refreshPopupAttributes();
  }
  public Font getCellFont() {
    return cellFont;
  }
  public void setCellFont(Font cellFont) {
    this.cellFont = cellFont;
    this.refreshPopupAttributes();
  }
  public Dimension getYearSpinnerSize() {
    return yearSpinnerSize;
  }
  public void setYearSpinnerSize(Dimension yearSpinnerSize) {
    this.yearSpinnerSize = yearSpinnerSize;
  }
  public Date getMiminumAllowed() {
    if (this.miminumAllowed==null) {
      // el mínimo default es 01/Enero/1800
      Calendar oCal = Calendar.getInstance();
      oCal.set(Calendar.YEAR, 1800);
      oCal.set(Calendar.MONTH, Calendar.JANUARY);
      oCal.set(Calendar.DAY_OF_MONTH, 0);
      this.miminumAllowed = oCal.getTime();
    }
    return this.miminumAllowed;
  }
  public void setMiminumAllowed(Date miminumAllowed) {
    this.miminumAllowed = miminumAllowed;
  }

}
