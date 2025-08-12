package pss.core.ui.components;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Simplified JPssButton that no longer extends {@link JButton}. It now wraps a
 * Swing {@link JButton} instance and exposes a minimal set of setters and
 * getters required by the application. All state previously stored in the
 * superclass is now handled locally through these delegating methods.
 */
public class JPssButton implements Cloneable {

    private final JButton button;
    private String id;
    private Object object;
    private String inputKey;

    public JPssButton() {
        this.button = new JButton();
        this.button.setRolloverEnabled(true);
        this.button.setOpaque(false);
        this.button.setBorder(null);
        this.button.setBorderPainted(false);
        this.button.setContentAreaFilled(false);
        this.button.setFocusPainted(false);
    }

    /** Returns the underlying Swing component. */
    public JButton getComponent() {
        return this.button;
    }

<<<<<<< HEAD
    // -----------------------------------------------------------------
    // Delegated setters/getters that previously belonged to JButton
    // -----------------------------------------------------------------
    public void setText(String text) { this.button.setText(text); }
    public String getText() { return this.button.getText(); }
    public String getLabel() { return this.button.getText(); }

    public void setPreferredSize(Dimension d) { this.button.setPreferredSize(d); }
    public void setSize(int w, int h) { this.button.setSize(w, h); }
    public Dimension getSize() { return this.button.getSize(); }
    public int getX() { return this.button.getX(); }
    public int getY() { return this.button.getY(); }
    public Rectangle getBounds() { return this.button.getBounds(); }

    public void setVisible(boolean visible) { this.button.setVisible(visible); }
    public boolean isVisible() { return this.button.isVisible(); }
    public Container getParent() { return this.button.getParent(); }

    public void setEnabled(boolean enabled) { this.button.setEnabled(enabled); }
    public boolean isEnabled() { return this.button.isEnabled(); }

    public void setSelected(boolean selected) { this.button.getModel().setSelected(selected); }
    public boolean isSelected() { return this.button.getModel().isSelected(); }

    public void setFocusable(boolean focusable) { this.button.setFocusable(focusable); }
    public void setHorizontalAlignment(int align) { this.button.setHorizontalAlignment(align); }
    public void setHorizontalTextPosition(int textPosition) { this.button.setHorizontalTextPosition(textPosition); }
    public void setVerticalAlignment(int align) { this.button.setVerticalAlignment(align); }
    public void setVerticalTextPosition(int textPosition) { this.button.setVerticalTextPosition(textPosition); }

    public void setForeground(Color c) { this.button.setForeground(c); }
    public Color getForeground() { return this.button.getForeground(); }
    public void setBackground(Color c) { this.button.setBackground(c); }
    public Color getBackground() { return this.button.getBackground(); }
    public void setFont(Font f) { this.button.setFont(f); }
    public Font getFont() { return this.button.getFont(); }
    public void setToolTipText(String text) { this.button.setToolTipText(text); }

    public void addActionListener(ActionListener l) { this.button.addActionListener(l); }
    public void doClick() { this.button.doClick(); }

    // -----------------------------------------------------------------
    // Local properties previously handled directly on the JPssButton
    // -----------------------------------------------------------------
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Object getObject() { return object; }
    public void setObject(Object object) { this.object = object; }

    public String getInputKey() { return inputKey; }
    public void setInputKey(String inputKey) { this.inputKey = inputKey; }
=======
    private synchronized boolean onPressed(MouseEvent e, JPssButton theButton) {
      boolean bConsumedHere = false;
      boolean bChangeButton = true;
      if (this.button != null && (System.currentTimeMillis() - this.mouseReleaseTime) < this.button.iMinimumTimeBetweenClicks) {
        bChangeButton = false;
        Point oNewLoc = getAbsoluteLocation(e);
        if ( oNewLoc != null && this.isNear(oNewLoc) ) {
          e.consume();
          bConsumedHere = true;
          JDebugPrint.logDebug("XXXXX JPssButton - PRESSED - ignoring event: " + oNewLoc + ", within protection window: " + this.button.iMinimumTimeBetweenClicks);
        }
      }
      if (bChangeButton) {
        this.button = theButton;
      }
      this.bLastPressIgnored = bConsumedHere;
      return bConsumedHere;
    }
    
    private synchronized void onDestroy(JPssButton theButton) {
      if (theButton == this.button) {
        this.button = null;
        this.mouseReleaseTime = -1;
        this.location.x = 0;
        this.location.y = 0;
      }
    }
    
  }
  
  private boolean bAvoidInvasiveClicks = false;
  private int iMinimumTimeBetweenClicks = 650;
  
  private static MouseHit lastHit = new MouseHit();
  
  public void setAvoidInvasiveClicks(boolean zAvoidInvasiveClicks) {
    this.bAvoidInvasiveClicks = zAvoidInvasiveClicks;
  }
  public void setMinimumTimeBetweenClicks(int ms) {
    this.iMinimumTimeBetweenClicks = ms;
  }
  */
//  @Override
//	public void processMouseEvent(MouseEvent e) {
///*    if (this.bAvoidInvasiveClicks) {
//      if (e.getID() == MouseEvent.MOUSE_RELEASED) {
//        lastHit.onRelease(e, this);
//      } else if (e.getID() == MouseEvent.MOUSE_PRESSED) {
//        JDebugPrint.logDebug("XXXXX JPssButton Processing event: " + this.getId());
//        if (lastHit.onPressed(e, this)) {
//          // event was consumed, just return
//          return;
//        }
//      }
//    }
//*/
//  //  -- UP TO HERE
//  //////////////////////////////////////////////////////////////////////
//  	if (e.getID() == MouseEvent.MOUSE_PRESSED) {
//  		if (this.getId()!= null)
//  			PssLogger.logDebug("XXXXX JPssButton Processing event: " + this.getId());
//  	}
//  	
//    super.processMouseEvent(e);
//    switch (e.getID()) {
//      case MouseEvent.MOUSE_ENTERED:
//        this.setMouseOver(true);
//        break;
//      case MouseEvent.MOUSE_EXITED:
//        this.setMouseOver(false);
//        break;
//    }
//  }

//  @Override
//	protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
//    if (this.clickOnEnter && condition == WHEN_FOCUSED && !e.isConsumed() && ks.getKeyCode() == KeyEvent.VK_ENTER && !e.isControlDown() && !e.isAltDown() && !e.isShiftDown()) {
//      if (ks.getKeyEventType() == KeyEvent.KEY_PRESSED) {
//        e.consume();
//        enterPressedHere = true;
//        return true;
//      } else if (enterPressedHere && ks.getKeyEventType() == KeyEvent.KEY_RELEASED) {
//        e.consume();
//        enterPressedHere = false;
//        this.doClick();
//        return true;
//      }
//    }
//    if (this.speedKeyOwner == null || this.speedKeyOwner.hasFocus()) { return super.processKeyBinding(ks, e, condition, pressed); }
//    return false;
//  }

  private void setPressed(boolean zPressed) {
    if (zPressed != this.pressed) {
      this.pressed = zPressed;
      this.updateStatus();
    }
  }

  private void setMouseOver(boolean zMouseOver) {
    if (zMouseOver != this.mouseOver) {
      this.mouseOver = zMouseOver;
      this.updateStatus();
    }
  }

  @Override
	public void setText(String zText) {
    if (!this.hasMultipleTexts()) {
      this.createDefaultText(zText);
    } else if (this.hasDefaultText()) {
      this.getStyledText(DEFAULT_TEXT_ID).setText(zText);
    }
  }

  @Override
	public String getText() {
    if (this.hasDefaultText()) {
      return this.getStyledText(DEFAULT_TEXT_ID).getText();
    } else {
      return null;
    }
  }

  public void setPainted(boolean zPainted) {
    this.painted = zPainted;
    this.repaint();
  }

  public boolean isPainted() {
    return this.painted;
  }

  @Override
	public void setForeground(Color zColor) {
    if (this.hasDefaultText()) {
      this.getStyledText(DEFAULT_TEXT_ID).getStyle(STATUS_ALL).setColor(zColor);
    }
    super.setForeground(zColor);
  }

  @Override
	public void setFont(Font zFont) {
    if (this.hasDefaultText()) {
      this.getStyledText(DEFAULT_TEXT_ID).getStyle(STATUS_ALL).setFont(zFont);
    }
    super.setFont(zFont);
  }

//  private Timer getClickTimer(int pressTime) {
//    if (this.clickTimer == null) {
//      Timer oTimer = new Timer(0, new JAct() {
//
//        @Override
//				public void Do() {
//          model.setPressed(false);
//          model.setArmed(false);
//        }
//      });
//      oTimer.setInitialDelay(pressTime);
//      oTimer.setRepeats(false);
//      this.clickTimer = oTimer;
//    }
//    return this.clickTimer;
//  }

  @Override
	public void doClick(int pressTime) {
    Dimension size = getSize();
    model.setArmed(true);
    model.setPressed(true);
    paintImmediately(0, 0, size.width, size.height);
//    this.getClickTimer(pressTime).restart();
  }

  public void setWordWrap(boolean isWordWrap) {
    if (this.hasDefaultText()) {
      this.getStyledText(DEFAULT_TEXT_ID).getStyle(STATUS_ALL).setWordWrap(isWordWrap);
    }
  }

  public boolean isWordWrap() {
    boolean result = false;
    if (this.hasDefaultText()) {
      result = this.getStyledText(DEFAULT_TEXT_ID).getStyle(STATUS_ALL).isWordWrap();
    }
    return result;
  }

  public boolean hasDefaultText() {
    return this.texts != null && this.getStyledText(DEFAULT_TEXT_ID) != null;
  }

  @Override
	public void setVisible(boolean zVisible) {
    if (this.isVisible() != zVisible) {
      super.setVisible(zVisible);
      this.repaint();
    }
  }

  @Override
	public void setSelected(boolean zSelected) {
    if (this.isSelected() != zSelected) {
      super.setSelected(zSelected);
      this.pressed = zSelected;
      this.updateStatus();
    }
  }

  @Override
	public void setEnabled(boolean zEnabled) {
    if (this.isEnabled() != zEnabled) {
      boolean nowPressed = this.pressed;
      super.setEnabled(zEnabled);
      this.pressed = nowPressed;
      this.mouseOver = this.mouseOver && zEnabled;
      this.hasFocus = this.hasFocus && zEnabled;
      this.updateStatus();
    }
  }

  public void setTextIconGap(int zGap) {
    this.textIconGap = zGap;
    this.repaint();
  }

  public int getTextIconGap() {
    return this.textIconGap;
  }

//  @Override
//	protected void paintComponent(Graphics g) {
//    if (this.painted) {
//      // useful data
//      Insets ins = UITools.borders().getBorderInsets(this);
//      int realWidth = this.getWidth() - ins.left - ins.right;
//      int realHeight = this.getHeight() - ins.top - ins.bottom;
//      // antialiasing
//      Graphics2D g2D = (Graphics2D) g;
//      if (this.antialiasing) {
//        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//      } else {
//        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
//      }
//      // background image
//      ImageIcon backgroundmage = this.getSuitableBackground();
//      if (backgroundmage != null) {
//        int imageWidth = backgroundmage.getIconWidth();
//        int imageHeight = backgroundmage.getIconHeight();
//        int imageX = ins.left + ((realWidth - imageWidth) / 2);
//        int imageY = ins.top + ((realHeight - imageHeight) / 2);
//        backgroundmage.paintIcon(this, g2D, imageX, imageY);
//      }
//      // paint the icon and the texts
//      this.paintForeground(g, ins.left, ins.top, realWidth, realHeight);
//      this.paintFrontImage(g);
//    }
//  }

//  private void paintForeground(Graphics g, int x, int y, int width, int height) {
//    g.clipRect(x, y, width, height);
//    // ensure to create needed objects for painting
//    this.createTextPaintingNeededObjects();
//    // get the icon
//    Icon suitableIcon = this.getSuitableIcon();
//    // set useful data
//    int gap = 0;
//    if (this.getModel().isPressed() || this.isSelected()) {
//      width -= this.pressedOffset;
//      height -= this.pressedOffset;
//      x += this.pressedOffset;
//      y += this.pressedOffset;
//    }
//    boolean hasToPaintText = this.hasToPaintText() && this.textsToPaint != null && !this.textsToPaint.isEmpty();
//    boolean hasToPaintIcon = suitableIcon != null;
//    // get icon dimension
//    int iconWidth = 0;
//    int iconHeight = 0;
//    if (hasToPaintIcon) {
//      iconWidth = suitableIcon.getIconWidth();
//      iconHeight = suitableIcon.getIconHeight();
//      gap = hasToPaintText ? this.getTextIconGap() : 0;
//    }
//    // get text dimension and calculate text and icon locations
//    int textWidth = 0;
//    int textHeight = 0;
//    int iconX = 0;
//    int iconY = 0;
//    int textX = 0;
//    int textY = 0;
//    textDim.width = 0;
//    textDim.height = 0;
//    int hPos = this.getHorizontalTextPosition();
//    int vPos = this.getVerticalTextPosition();
//    JList<JPair> textLinesToPaint = null;
//    // calculate text and icon positions for centered alignment...
//    switch (hPos) {
//      case SwingConstants.CENTER: {
//        int maxTextWidth = width;
//        textLinesToPaint = this.splitTextsAndSetDimension(textDim, maxTextWidth, hasToPaintText, textsToPaint);
//        textWidth = textDim.width;
//        textHeight = textDim.height;
//        switch (vPos) {
//          case SwingConstants.CENTER:
//            iconY = (height - iconHeight) / 2;
//            textY = (height - textHeight) / 2;
//            break;
//          case SwingConstants.TOP:
//            textY = (height - iconHeight - gap - textHeight) / 2;
//            iconY = textY + textHeight + gap;
//            break;
//          case SwingConstants.BOTTOM:
//            iconY = (height - textHeight - gap - iconHeight) / 2;
//            textY = iconY + iconHeight + gap;
//            break;
//        }
//        iconX = (width - iconWidth) / 2;
//        textX = (width - textWidth) / 2;
//        break;
//      }
//      case SwingConstants.LEADING:
//      case SwingConstants.LEFT: {
//        int maxTextWidth = width - iconWidth - gap;
//        textLinesToPaint = this.splitTextsAndSetDimension(textDim, maxTextWidth, hasToPaintText, textsToPaint);
//        textWidth = textDim.width;
//        textHeight = textDim.height;
//        switch (vPos) {
//          case SwingConstants.CENTER:
//            iconY = (height - iconHeight) / 2;
//            textY = (height - textHeight) / 2;
//            break;
//          case SwingConstants.TOP:
//            iconY = (height - iconHeight) / 2;
//            textY = iconY;
//            break;
//          case SwingConstants.BOTTOM:
//            iconY = (height - iconHeight) / 2;
//            textY = iconY + iconHeight - textHeight;
//            break;
//        }
//        textX = (width - iconWidth - gap - textWidth) / 2;
//        iconX = textX + textWidth + gap;
//        break;
//      }
//      case SwingConstants.TRAILING:
//      case SwingConstants.RIGHT: {
//        int maxTextWidth = width - iconWidth - gap;
//        textLinesToPaint = this.splitTextsAndSetDimension(textDim, maxTextWidth, hasToPaintText, textsToPaint);
//        textWidth = textDim.width;
//        textHeight = textDim.height;
//        switch (vPos) {
//          case SwingConstants.CENTER:
//            iconY = (height - iconHeight) / 2;
//            textY = (height - textHeight) / 2;
//            break;
//          case SwingConstants.TOP:
//            iconY = (height - iconHeight) / 2;
//            textY = iconY;
//            break;
//          case SwingConstants.BOTTOM:
//            iconY = (height - iconHeight) / 2;
//            textY = iconY + iconHeight - textHeight;
//            break;
//        }
//        iconX = (width - textWidth - gap - iconWidth) / 2;
//        textX = iconX + iconWidth + gap;
//        break;
//      }
//    }
//    // ...then create the union rectangle for icon and text...
//    labelRect.x = Math.min(iconX, textX);
//    labelRect.y = Math.min(iconY, textY);
//    labelRect.width = Math.max(iconX + iconWidth, textX + textWidth) - labelRect.x;
//    labelRect.height = Math.max(iconY + iconHeight, textY + textHeight) - labelRect.y;
//    // ...then transform positions to the suitable alignment
//    int hAlign = this.getHorizontalAlignment();
//    int vAlign = this.getVerticalAlignment();
//    int deltaX = 0;
//    int deltaY = 0;
//    switch (hAlign) {
//      case SwingConstants.LEADING:
//      case SwingConstants.LEFT:
//        deltaX = -labelRect.x;
//        break;
//      case SwingConstants.TRAILING:
//      case SwingConstants.RIGHT:
//        deltaX = width - (labelRect.x + labelRect.width);
//        break;
//    }
//    switch (vAlign) {
//      case SwingConstants.TOP:
//        deltaY = -labelRect.y;
//        break;
//      case SwingConstants.BOTTOM:
//        deltaY = height - (labelRect.y + labelRect.height);
//        break;
//    }
//    iconX += deltaX;
//    textX += deltaX;
//    iconY += deltaY;
//    textY += deltaY;
//    // finally, add margins to final x a y positions
//    iconX += x;
//    iconY += y;
//    textX += x;
//    textY += y;
//    // paint icon and texts
//    if (hasToPaintIcon) {
//      suitableIcon.paintIcon(this, g, iconX, iconY);
//    }
//    if (hasToPaintText) {
//      this.paintTexts(g, textX, textY, textWidth, textHeight, textLinesToPaint);
//    }
//  }

//  private void paintFrontImage(Graphics g) {
//    if (!this.showFrontImage) return;
//    if (this.frontImage == null) return;
//    this.frontImage.paintIcon(this, g, this.frontImagePoint.x, this.frontImagePoint.y);
//  }

  private boolean hasToPaintText() {
    return this.hasMultipleTexts() && this.isShowText();
  }

  private void updateTextsToPaint() {
    if (this.textsToPaint != null) {
      this.textsToPaint.removeAllElements();
      Iterator<StyledText> styledTextsIt = this.texts.valueIterator();
      while (styledTextsIt.hasNext()) {
        StyledText text = styledTextsIt.next();
        Iterator<Style> stylesIt = text.styleByStatus.valueIterator();
        boolean styleFound = false;
        while (stylesIt.hasNext() && !styleFound) {
          Style style = stylesIt.next();
          if ((styleFound = style.appliesTo(this.status))) {
            this.textsToPaint.addElement(new JPair(text.text, style));
          }
        }
      }
    }
  }

  /**
   * Calculates the dimension of all the texts together and answers a List of
   * pairs of Strings and Styles, for each text line to be printed in the
   * button. The list of lines depends on the button status.
   */
  private JList<JPair> splitTextsAndSetDimension(Dimension dimension, int maxTextWidth, boolean hasToPaintText, JList<JPair> zTextsToPaint) {
    if (this.allSplittedTexts == null || zTextsToPaint == null) { return null; }
    this.allSplittedTexts.removeAllElements();
    dimension.width = 0;
    dimension.height = 0;
    if (hasToPaintText && !zTextsToPaint.isEmpty()) {
      Iterator<JPair> pairsIt = zTextsToPaint.iterator();
      while (pairsIt.hasNext()) {
        JPair pair = pairsIt.next();
        String text = (String) pair.fisrtObject;
        Style style = (Style) pair.secondObject;
        FontMetrics fm = this.getFontMetrics(style.getFont());
        if (style.isWordWrap()) {
          JList<String> splitted = this.splitTextAndSetDimension(dimension, text, maxTextWidth, fm);
          if (splitted != null) {
            Iterator<String> splittedTextsIt = splitted.iterator();
            while (splittedTextsIt.hasNext()) {
              String splittedText = splittedTextsIt.next();
              this.allSplittedTexts.addElement(new JPair(splittedText, style));
            }
          }
        } else {
          String truncatedText = this.truncateTextAndSetDimension(dimension, text, maxTextWidth, fm);
          if (truncatedText != null) {
            this.allSplittedTexts.addElement(new JPair(truncatedText, style));
          }
        }
      }
    }
    return this.allSplittedTexts;
  }

  private String truncateTextAndSetDimension(Dimension dimension, String text, int maxTextWidth, FontMetrics fm) {
    if (fm == null || text == null) { return null; }
    int charCount = text.length();
    String dots = "...";
    this.truncatedTextBuffer.setLength(0);
    this.truncatedTextBuffer.append(text);
    int lastStringWidth = 0;
    for (int i = 0; i < charCount; i++) {
      char c = text.charAt(i);
      int newCharWidth = fm.charWidth(c);
      if ((lastStringWidth + newCharWidth) > maxTextWidth) {
        this.truncatedTextBuffer.setLength(i);
        if (i < charCount) {
          this.truncatedTextBuffer.replace(Math.max(0, i - dots.length()), i, dots);
        }
        break;
      }
      lastStringWidth += newCharWidth;
    }
    dimension.width = Math.max(dimension.width, lastStringWidth);
    dimension.height += this.textHeight(fm.getAscent(), fm.getDescent());
    return this.truncatedTextBuffer.toString();
  }

  private JList<String> splitTextAndSetDimension(Dimension dimension, String text, int maxTextWidth, FontMetrics fm) {
    if (this.currSplittedTexts == null || fm == null || text == null) { return null; }
    this.currSplittedTexts.removeAllElements();
    int textHeight = this.textHeight(fm.getAscent(), fm.getDescent());
    text = text.replace('\t', ' ');
    JStringTokenizer paragraphTokens = JCollectionFactory.createStringTokenizer(text, '\n');
    while (paragraphTokens.hasMoreTokens()) {
      String paragraph = paragraphTokens.nextToken().trim();
      JStringTokenizer wordTokens = JCollectionFactory.createStringTokenizer(paragraph, ' ');
      formingLineBuffer.setLength(0);
      int formingLineWidth = 0;
      while (wordTokens.hasMoreTokens()) {
        String word = wordTokens.nextToken().trim();
        int wordWidth = fm.stringWidth(word);
        if ((formingLineWidth + wordWidth) > maxTextWidth) {
          this.addWrappedTextLineAndSetDimension(formingLineBuffer.toString(), this.currSplittedTexts, dimension, fm, textHeight);
          formingLineBuffer.setLength(0);
          formingLineWidth = 0;
        }
        formingLineBuffer.append(word).append(' ');
        formingLineWidth += (wordWidth + fm.charWidth(' '));
      }
      if (formingLineWidth > 0) {
        this.addWrappedTextLineAndSetDimension(formingLineBuffer.toString(), this.currSplittedTexts, dimension, fm, textHeight);
      }
    }
    return this.currSplittedTexts;
  }

  private void addWrappedTextLineAndSetDimension(String lineToAdd, JList<String> linesList, Dimension dimension, FontMetrics fm, int textHeight) {
    String newLine = lineToAdd.trim();
    linesList.addElement(newLine);
    dimension.height += textHeight;
    dimension.width = Math.max(dimension.width, fm.stringWidth(newLine));
  }

  private void paintTexts(Graphics g, int x, int y, int maxTextWidth, int textHeight, JList<JPair> allTextLinesToPaint) {
    if (allTextLinesToPaint != null) {
      Iterator<JPair> pairsIt = allTextLinesToPaint.iterator();
      int lastYOffset = y;
      while (pairsIt.hasNext()) {
        JPair textAndStyle = pairsIt.next();
        String text = (String) textAndStyle.fisrtObject;
        Style style = (Style) textAndStyle.secondObject;
        lastYOffset += this.paintText(g, text, style, x, lastYOffset, maxTextWidth);
      }
    }
  }

  private int textHeight(int ascent, int descent) {
    return ascent + descent;
  }

  /**
   * Paints the given text and answers its height.
   */
  private int paintText(Graphics g, String text, Style style, int x, int y, int maxTextWidth) {
    Font font = style.getFont();
    FontMetrics fm = this.getFontMetrics(font);
    int ascent = fm.getAscent();
    int textHeight = this.textHeight(ascent, fm.getDescent());
    int textWidth = fm.stringWidth(text);
    int newX = x;
    switch (style.getAlignment()) {
      case SwingConstants.CENTER:
        newX = x + ((maxTextWidth - textWidth) / 2);
        break;
      case SwingConstants.RIGHT:
        newX = x + maxTextWidth - textWidth;
        break;
    }
    g.setFont(font);
    g.setColor(style.getColor());
    g.drawString(text, newX, y + ascent);
    return textHeight;
  }

//  private Icon getSuitableIcon() {
//    Icon defaultIcon = this.getIcon();
//    Icon result = defaultIcon;
//    boolean selected = this.isSelected();
//    result = this.isEnabled() ? (result = this.getModel().isPressed() ? this.getPressedIcon() : this.mouseOver ? (selected ? this.getRolloverSelectedIcon() : this.getRolloverIcon())
//        : (selected ? this.getSelectedIcon() : result)) : selected ? this.getDisabledSelectedIcon() : this.getDisabledIcon();
//    return result == null ? defaultIcon : result;
//  }
//
//  private ImageIcon getSuitableBackground() {
//    ImageIcon image = null;
//    if (this.status == STATUS_UNPRESSED) {
//      image = this.unpressedBackground;
//    } else if (this.status == STATUS_UNPRESSED_ROLLOVER) {
//      image = this.unpressedRolloverBackground;
//    } else if (this.status == STATUS_PRESSED) {
//      image = this.pressedBackground;
//    } else if (this.status == STATUS_PRESSED_ROLLOVER) {
//      image = this.pressedRolloverBackground;
//    } else if (this.status == STATUS_UNPRESSED_DISABLED) {
//      image = this.unpressedDisabledBackground;
//    } else if (this.status == STATUS_PRESSED_DISABLED) {
//      image = this.pressedDisabledBackground;
//    }
//    return image;
//  }

  // internal methods
//  private void updateSpeedKey(KeyStroke zOldSpeedKey, KeyStroke zNewSpeedKey) {
//    if (zOldSpeedKey != null) {
//      this.unregisterKeyboardAction(zOldSpeedKey);
//    }
//    if (zNewSpeedKey != null) {
//      this.registerKeyboardAction(new JAct() {
//
//        @Override
//				public void Do() {
//          doClick();
//        }
//      }, zNewSpeedKey, JComponent.WHEN_IN_FOCUSED_WINDOW);
//    }
//  }

  private void updateStatus() {
    if (this.isEnabled()) {
      if (this.isSelected() || this.pressed) {
        if (this.mouseOver || this.hasFocus) {
          this.setStatus(STATUS_PRESSED_ROLLOVER);
        } else {
          this.setStatus(STATUS_PRESSED);
        }
      } else {
        if (this.mouseOver || this.hasFocus) {
          this.setStatus(STATUS_UNPRESSED_ROLLOVER);
        } else {
          this.setStatus(STATUS_UNPRESSED);
        }
      }
    } else {
      if (this.isSelected() || pressed) {
        this.setStatus(STATUS_PRESSED_DISABLED);
      } else {
        this.setStatus(STATUS_UNPRESSED_DISABLED);
      }
    }
    this.updateTextsToPaint();
  }

  private static boolean areEqual(Object zObject1, Object zObject2) {
    return (zObject1 == null && zObject2 == null) || (zObject1 != null && zObject2 != null && zObject1.equals(zObject2));
  }

  private void createDefaultText(String zText) {
    this.addText(DEFAULT_TEXT_ID, zText);
    Color color = this.getForeground();
    if (color == null) {
      color = Color.black;
    }
    Font font = this.getFont();
    if (font == null) {
      font = new Font("Arial", Font.PLAIN, 10);
    }
    int textAlign = this.getHorizontalAlignment();
    this.setStyle(DEFAULT_TEXT_ID, STATUS_ALL, textAlign, true, color, font);
  }

  private void setStatus(int zStatus) {
    if (this.status != zStatus) {
      this.status = zStatus;
      this.repaint();
    }
  }

  private void createTextPaintingNeededObjects() {
    if (!this.canPaintFaster) {
      this.textDim = new Dimension();
      this.labelRect = new Rectangle();
      if (this.hasToPaintText()) {
        this.textsToPaint = JCollectionFactory.createList(3);
        this.formingLineBuffer = new StringBuffer(20);
        this.truncatedTextBuffer = new StringBuffer(20);
        this.allSplittedTexts = JCollectionFactory.createList(5);
        this.currSplittedTexts = JCollectionFactory.createList(3);
        this.updateTextsToPaint();
      } else {
        this.textsToPaint = null;
        this.formingLineBuffer = null;
        this.truncatedTextBuffer = null;
        this.allSplittedTexts = null;
        this.currSplittedTexts = null;
      }
      this.canPaintFaster = true;
    }
  }

  /*****************************************************************************
   * @description: Fires the actionperformed event without show visual changes
   *               in the buttons state
   * @param: none
   ****************************************************************************/
  public void fireActionPerformed() {
    this.fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
  }

//  public String getSpeedKeyText() {
//    String result = "";
//    if (this.speedKey != null) {
//      int keyCode = this.speedKey.getKeyCode();
//      if (keyCode != 0) {
//        StringBuffer resultBuffer = new StringBuffer();
//        int keyModifiers = this.speedKey.getModifiers();
//        if (keyModifiers != 0) {
//          resultBuffer.append(KeyEvent.getKeyModifiersText(keyModifiers));
//          resultBuffer.append("+");
//        }
//        resultBuffer.append(KeyEvent.getKeyText(keyCode));
//        result = resultBuffer.toString();
//      }
//    }
//    return result;
//  }

  /*****************************************************************************
   * LEO DEL ORTO COMENT� ALGO!!!! Return true if the button has an object
   ****************************************************************************/
  public boolean hasObject() {
    return (this.object == null) ? false : true;
  }

  public boolean hasMultipleTexts() {
    return this.texts != null && !this.texts.isEmpty();
  }

  public boolean isFocused() {
    return hasFocus;
  }

  public void setFocused(boolean zFocused) {
    if (this.hasFocus != zFocused) {
      this.hasFocus = zFocused;
      this.updateStatus();
    }
  }

  public void select() {
    this.setSelected(true);
  }

  public void deselect() {
    this.setSelected(false);
  }

  public void addText(String zId, String zText) {
    if (this.texts.containsKey(DEFAULT_TEXT_ID)) { throw new RuntimeException("Id '" + DEFAULT_TEXT_ID + "' is already used"); }
    this.texts.addElement(zId, new StyledText(zId, zText));
    this.canPaintFaster = false;
  }

  public void setStyle(String zTextId, int zApplyStatus, int zAlignment, boolean zWordWrap, Color zColor, Font zFont) {
    this.getStyledText(zTextId).setStyle(zApplyStatus, zAlignment, zWordWrap, zColor, zFont);
  }

  public void setStyle(int zApplyStatus, int zAlignment, boolean zWordWrap, Color zColor, Font zFont) {
    if (!this.hasDefaultText()) {
      this.createDefaultText(super.getText());
    }
    this.getStyledText(DEFAULT_TEXT_ID).setStyle(zApplyStatus, zAlignment, zWordWrap, zColor, zFont);
  }

  public StyledText getStyledText(String zTextId) {
    return this.texts.getElement(zTextId);
  }

  public String getText(String zTextId) {
    return this.getStyledText(zTextId).getText();
  }

  public void setText(String zTextId, String zText) {
    this.getStyledText(zTextId).setText(zText);
  }

  // bean interface methods
  public boolean isAntialiasing() {
    return antialiasing;
  }

  public void setAntialiasing(boolean newAntialiasing) {
    if (this.antialiasing != newAntialiasing) {
      this.antialiasing = newAntialiasing;
      this.repaint();
    }
  }

//  public void setUnpressedBackground(ImageIcon newUnpressedBackground) {
//    ImageIcon oldUnpressedBackground = unpressedBackground;
//    unpressedBackground = newUnpressedBackground;
//    if (!areEqual(oldUnpressedBackground, newUnpressedBackground)) {
//      if (this.unpressedRolloverBackground == null) {
//        this.unpressedRolloverBackground = newUnpressedBackground;
//      }
//      if (this.unpressedDisabledBackground == null && newUnpressedBackground != null) {
//        this.unpressedDisabledBackground = this.createDisabledImageFrom(newUnpressedBackground);
//      }
//      this.repaint();
//    }
//  }
//
//  public ImageIcon getUnpressedBackground() {
//    return unpressedBackground;
//  }
//
//  public void setPressedBackground(ImageIcon newPressedBackground) {
//    ImageIcon oldPressedBackground = pressedBackground;
//    pressedBackground = newPressedBackground;
//    if (!areEqual(oldPressedBackground, newPressedBackground)) {
//      if (this.pressedRolloverBackground == null) {
//        this.pressedRolloverBackground = newPressedBackground;
//      }
//      if (this.pressedDisabledBackground == null && newPressedBackground != null) {
//        this.pressedDisabledBackground = this.createDisabledImageFrom(newPressedBackground);
//      }
//      this.repaint();
//    }
//  }

  public void setShowFrontImage(boolean value) {
    this.showFrontImage = value;
  }

//  protected ImageIcon createDisabledImageFrom(ImageIcon zImageIcon) {
//  	return null;
////    return UITools.imaging().createDisabledImageFrom(zImageIcon);
//  }

//  public ImageIcon getPressedBackground() {
//    return pressedBackground;
//  }
//
//  public void setUnpressedRolloverBackground(ImageIcon newUnpressedRolloverBackground) {
//    ImageIcon oldUnpressedRolloverBackground = unpressedRolloverBackground;
//    unpressedRolloverBackground = newUnpressedRolloverBackground;
//    if (!areEqual(oldUnpressedRolloverBackground, newUnpressedRolloverBackground)) {
//      if (newUnpressedRolloverBackground == null) {
//        this.unpressedRolloverBackground = this.unpressedBackground;
//      }
//      this.repaint();
//    }
//  }
//
//  public ImageIcon getUnpressedRolloverBackground() {
//    return unpressedRolloverBackground;
//  }
//
//  public void setPressedRolloverBackground(ImageIcon newPressedRolloverBackground) {
//    ImageIcon oldPressedRolloverBackground = pressedRolloverBackground;
//    pressedRolloverBackground = newPressedRolloverBackground;
//    if (!areEqual(oldPressedRolloverBackground, newPressedRolloverBackground)) {
//      if (newPressedRolloverBackground == null) {
//        this.pressedRolloverBackground = this.pressedBackground;
//      }
//      this.repaint();
//    }
//  }
//
//  public ImageIcon getPressedRolloverBackground() {
//    return pressedRolloverBackground;
//  }

//  public void setUnpressedDisabledBackground(ImageIcon newDisabledBackground) {
//    ImageIcon oldDisabledBackground = this.unpressedDisabledBackground;
//    this.unpressedDisabledBackground = newDisabledBackground;
//    if (!areEqual(oldDisabledBackground, newDisabledBackground)) {
//      if (newDisabledBackground == null && this.unpressedBackground != null) {
//        this.unpressedDisabledBackground = this.createDisabledImageFrom(this.unpressedBackground);
//      }
//      this.repaint();
//    }
//  }
//
//  public ImageIcon getUnpressedDisabledBackground() {
//    return this.unpressedDisabledBackground;
//  }
//
//  public void setPressedDisabledBackground(ImageIcon newDisabledSelectedBackground) {
//    ImageIcon oldDisabledSelectedBackground = this.pressedDisabledBackground;
//    this.pressedDisabledBackground = newDisabledSelectedBackground;
//    if (!areEqual(oldDisabledSelectedBackground, newDisabledSelectedBackground)) {
//      if (newDisabledSelectedBackground == null && this.pressedBackground != null) {
//        this.pressedDisabledBackground = this.createDisabledImageFrom(this.pressedBackground);
//      }
//      this.repaint();
//    }
//  }
//
//  public ImageIcon getPressedDisabledBackground() {
//    return this.pressedDisabledBackground;
//  }

  public void setId(String newId) {
   // String oldId = id;
    id = newId;
  }

  public String getId() {
    return id;
  }

  public void setInputKey(String zInputKey) {
    this.inputKey = zInputKey;
  }

  public String getInputKey() {
    return this.inputKey;
  }

  public void setObject(Object newObject) {
    //Object oldObject = object;
    object = newObject;
  }

  public Object getObject() {
    return object;
  }

  public boolean isShowText() {
    return this.showText;
  }

  public void setShowText(boolean zShowText) {
    this.showText = zShowText;
    this.canPaintFaster = false;
    this.repaint();
  }

//  public void setSpeedKey(KeyStroke zSpeedKey) {
//    KeyStroke oldSpeedKey = this.speedKey;
//    this.speedKey = zSpeedKey;
////    this.updateSpeedKey(oldSpeedKey, zSpeedKey);
//  }

//  public KeyStroke getSpeedKey() {
//    return this.speedKey;
//  }

  public int getPressedOffset() {
    return this.pressedOffset;
  }

  public void setPressedOffset(int pressedOffset) {
    this.pressedOffset = pressedOffset;
  }

  public boolean isClickOnEnter() {
    return this.clickOnEnter;
  }

  public void setClickOnEnter(boolean clickOnEnter) {
    this.clickOnEnter = clickOnEnter;
  }

  public Component getSpeedKeyOwner() {
    return this.speedKeyOwner;
  }

  /**
   * Sets this buttons's speed key owner. <br>The speed key owner is the
   * component which must be focused to make this button's key strokes
   * processable. <br>If the speed key owner is <code>null</code>, the key
   * strokes are allways processed.
   */
  public void setSpeedKeyOwner(Component speedKeyOwner) {
    this.speedKeyOwner = speedKeyOwner;
  }
  //
  //  INNER CLASSES FOR MANAGING TEXTS
  //
  public class StyledText {

    //
    //  INSTANCE VARIABLES
    //
    // internal variables
    private JMap<Integer, Style> styleByStatus = null;
    // properties
    private String id = null;
    private String text = null;

    //
    //  CONSTRUCTORS
    //
    private StyledText(String zId, String zText) {
      this.id = zId;
//      this.text = zText;
      this.setText(zText);
      this.styleByStatus = JCollectionFactory.createMap(11);
    }

    //
    //  METHODS
    //
    // internal methods
    private void updateParent() {
      repaint();
      updateTextsToPaint();
    }

    // API
    public Style getStyle(int zApplyStatus) {
      return this.styleByStatus.getElement(new Integer(zApplyStatus));
    }

    public void setStyle(int zApplyStatus, int zAlignment, boolean zWordWrap, Color zColor, Font zFont) {
      Style style = new Style(this.id, zApplyStatus, zAlignment, zWordWrap, zColor, zFont);
      this.styleByStatus.addElement(new Integer(zApplyStatus), style);
      //
      this.updateParent();
    }

    public String getId() {
      return this.id;
    }

    public String getText() {
      return this.text;
    }

    public void setText(String zText) {
      String sText = null;
      if (zText != null) {
        sText = JLanguage.translate(zText);
      }
      if (!areEqual(this.text, sText)) {
        this.text = sText;
        this.updateParent();
      }
    }
  }
  public class Style {

    //
    //  INSTANCE VARIABLES
    //
    // properties
    private String id = null;
    private int applyStatus = 0;
    private int alignment = 0;
    private boolean wordWrap = true;
    private Color color = null;
    private Font font = null;

    //
    //  CONSTRUCTORS
    //
    private Style(String zId, int zApplyStatus, int zAlignment, boolean zWordWrap, Color zColor, Font zFont) {
      this.id = zId;
      this.applyStatus = zApplyStatus;
      this.alignment = zAlignment;
      this.wordWrap = zWordWrap;
      this.color = zColor;
      this.font = zFont;
    }

    //
    //  METHODS
    //
    // internal methods
    private void updateParent() {
      repaint();
    }

    // API
    public boolean appliesTo(int zStatus) {
      return (this.applyStatus & zStatus) == zStatus;
    }

    public String getId() {
      return this.id;
    }

    public int getAlignment() {
      return this.alignment;
    }

    public void setAlignment(int zAlignment) {
      if (this.alignment != zAlignment) {
        this.alignment = zAlignment;
        this.updateParent();
      }
    }

    public boolean isWordWrap() {
      return this.wordWrap;
    }

    public void setWordWrap(boolean zWordWrap) {
      if (this.wordWrap != zWordWrap) {
        this.wordWrap = zWordWrap;
        this.updateParent();
      }
    }

    public Color getColor() {
      return this.color;
    }

    public void setColor(Color zColor) {
      if (!areEqual(this.color, zColor)) {
        this.color = zColor;
        this.updateParent();
      }
    }

    public Font getFont() {
      return this.font;
    }

    public void setFont(Font zFont) {
      if (!areEqual(this.font, zFont)) {
        this.font = zFont;
        this.updateParent();
      }
    }

    public int getApplyStatus() {
      return this.applyStatus;
    }

    public void setApplyStatus(int zApplyStatus) {
      if (this.applyStatus != zApplyStatus) {
        this.applyStatus = zApplyStatus;
        this.updateParent();
        updateTextsToPaint();
      }
    }
  }

  /**
   * Destroy the button references
   */
  public void destroy() {
    //JPssButton.lastHit.onDestroy(this);
    this.object = null;
    this.id = null;
//    this.speedKey = null;
    this.speedKeyOwner = null;
    this.destroySkinProperties();
  };

  /**
   * Destroy just the skin properties
   */
  public void destroySkinProperties() {
//    this.unpressedBackground = null;
//    this.pressedBackground = null;
//    this.unpressedRolloverBackground = null;
//    this.pressedRolloverBackground = null;
//    this.unpressedDisabledBackground = null;
//    this.pressedDisabledBackground = null;
//    this.setDisabledIcon(null);
    this.setIcon(null);
    if (this.textsToPaint != null) this.textsToPaint.removeAllElements();
    if (this.texts != null) this.texts.removeAllElements();
  }

  @Override
	public void setToolTipText(String zToolTip) {
    super.setToolTipText(JLanguage.translate(zToolTip));
  }

  @Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
    int rate = -1;
    if ((infoflags & (FRAMEBITS | ALLBITS)) != 0) {
      rate = 0;
    } else if ((infoflags & SOMEBITS) != 0) {
      rate = 100;
    }
    if (rate >= 0) {
      this.repaint(rate, 0, 0, this.getWidth(), this.getHeight());
    }
    return (infoflags & (ALLBITS | ABORT)) == 0;
  }

//  /**
//   * @param image
//   */
//  public void setFrontImage(ImageIcon image) {
//    this.frontImage = image;
//  }
//
//  /**
//   * @param point
//   */
//  public void setFrontImagePoint(Point point) {
//    this.frontImagePoint = point;
//  }
  
  @Override
	public String toString() {
    return this.getId() + " - " + this.getText();
  }
>>>>>>> b3281718 (Merge inicial: unifico historias)
}

