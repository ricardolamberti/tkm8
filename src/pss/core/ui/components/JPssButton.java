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
}

