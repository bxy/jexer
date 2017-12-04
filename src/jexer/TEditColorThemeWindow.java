/*
 * Jexer - Java Text User Interface
 *
 * The MIT License (MIT)
 *
 * Copyright (C) 2017 Kevin Lamonte
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 * @author Kevin Lamonte [kevin.lamonte@gmail.com]
 * @version 1
 */
package jexer;

import java.util.List;
import java.util.ResourceBundle;

import jexer.bits.ColorTheme;
import jexer.bits.CellAttributes;
import jexer.event.TKeypressEvent;

import static jexer.TKeypress.*;

/**
 * TEditColorThemeWindow provides an easy UI for users to alter the running
 * color theme.
 *
 */
public class TEditColorThemeWindow extends TWindow {

    /**
     * Translated strings.
     */
    private static final ResourceBundle i18n = ResourceBundle.getBundle(TEditColorThemeWindow.class.getName());

    // ------------------------------------------------------------------------
    // Variables --------------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * The current editing theme.
     */
    private ColorTheme editTheme;

    /**
     * The left-side list of colors pane.
     */
    private TList colorNames;

    /**
     * The foreground color.
     */
    private TForegroundPicker foreground;

    /**
     * The background color.
     */
    private TBackgroundPicker background;

    // ------------------------------------------------------------------------
    // Constructors -----------------------------------------------------------
    // ------------------------------------------------------------------------


    /**
     * Public constructor.  The file open box will be centered on screen.
     *
     * @param application the TApplication that manages this window
     */
    public TEditColorThemeWindow(final TApplication application) {

        // Register with the TApplication
        super(application, i18n.getString("windowTitle"), 0, 0, 60, 18, MODAL);

        // Initialize with the first color
        List<String> colors = getTheme().getColorNames();
        assert (colors.size() > 0);
        editTheme = new ColorTheme();
        for (String key: colors) {
            CellAttributes attr = new CellAttributes();
            attr.setTo(getTheme().getColor(key));
            editTheme.setColor(key, attr);
        }

        colorNames = addList(colors, 2, 2, 38, getHeight() - 7,
            new TAction() {
                // When the user presses Enter
                public void DO() {
                    refreshFromTheme(colorNames.getSelected());
                }
            },
            new TAction() {
                // When the user navigates with keyboard
                public void DO() {
                    refreshFromTheme(colorNames.getSelected());
                }
            }
        );
        foreground = new TForegroundPicker(this, i18n.getString("foregroundLabel"), 42, 1,
                new TAction() {
                    // When the user presses Enter
                    public void DO() {
                        saveToEditTheme();
                    }
                },
                new TAction() {
                    // When the user navigates with keyboard
                    public void DO() {
                        saveToEditTheme();
                    }
                }
        );
        background = new TBackgroundPicker(this, i18n.getString("backgroundLabel"), 42, 7,
                new TAction() {
                    // When the user presses Enter
                    public void DO() {
                        saveToEditTheme();
                    }
                },
                new TAction() {
                    // When the user navigates with keyboard
                    public void DO() {
                        saveToEditTheme();
                    }
                }
         );
        refreshFromTheme(colors.get(0));
        colorNames.setSelectedIndex(0);

        addButton(i18n.getString("okButton"), getWidth() - 37, getHeight() - 4,
            new TAction() {
                public void DO() {
                    ColorTheme global = getTheme();
                    List<String> colors = editTheme.getColorNames();
                    for (String key: colors) {
                        CellAttributes attr = new CellAttributes();
                        attr.setTo(editTheme.getColor(key));
                        global.setColor(key, attr);
                    }
                    getApplication().closeWindow(TEditColorThemeWindow.this);
                }
            }
        );

        addButton(i18n.getString("cancelButton"), getWidth() - 25,
            getHeight() - 4,
            new TAction() {
                public void DO() {
                    getApplication().closeWindow(TEditColorThemeWindow.this);
                }
            }
        );

        // Default to the color list
        activate(colorNames);

        // Add shortcut text
        newStatusBar(i18n.getString("statusBar"));
    }

    // ------------------------------------------------------------------------
    // Event handlers ---------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Handle keystrokes.
     *
     * @param keypress keystroke event
     */
    @Override
    public void onKeypress(final TKeypressEvent keypress) {
        // Escape - behave like cancel
        if (keypress.equals(kbEsc)) {
            getApplication().closeWindow(this);
            return;
        }

        // Pass to my parent
        super.onKeypress(keypress);
    }

    // ------------------------------------------------------------------------
    // TWindow ----------------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Draw me on screen.
     */
    @Override
    public void draw() {
        super.draw();
        CellAttributes attr = new CellAttributes();

        // Draw the label on colorNames
        attr.setTo(getTheme().getColor("twindow.background.modal"));
        if (colorNames.isActive()) {
            attr.setForeColor(getTheme().getColor("tlabel").getForeColor());
            attr.setBold(getTheme().getColor("tlabel").isBold());
        }
        getScreen().putStringXY(3, 2, i18n.getString("colorName"), attr);

        // Draw the sample text box
        attr.reset();
        attr.setForeColor(foreground.getColor());
        attr.setBold(foreground.isBold());
        attr.setBackColor(background.getColor());
        getScreen().putStringXY(getWidth() - 17, getHeight() - 6,
            i18n.getString("textTextText"), attr);
        getScreen().putStringXY(getWidth() - 17, getHeight() - 5,
            i18n.getString("textTextText"), attr);
    }

    // ------------------------------------------------------------------------
    // TEditColorThemeWindow --------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Set various widgets/values to the editing theme color.
     *
     * @param colorName name of color from theme
     */
    private void refreshFromTheme(final String colorName) {
        CellAttributes attr = editTheme.getColor(colorName);
        foreground.setColor(attr.getForeColor());
        foreground.setBold(attr.isBold());
        background.setColor(attr.getBackColor());
    }

    /**
     * Examines foreground, background, and colorNames and sets the color in
     * editTheme.
     */
    private void saveToEditTheme() {
        String colorName = colorNames.getSelected();
        if (colorName == null) {
            return;
        }
        CellAttributes attr = editTheme.getColor(colorName);
        attr.setForeColor(foreground.getColor());
        attr.setBold(foreground.isBold());
        attr.setBackColor(background.getColor());
        editTheme.setColor(colorName, attr);
    }

}
