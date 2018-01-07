package bxy.jexer;

import jexer.TAction;
import jexer.TApplication;
import jexer.TWidget;
import jexer.TWindow;
import jexer.bits.CellAttributes;

import static bxy.jexer.TColorPicker.*;
import static jexer.bits.GraphicsChars.CP437;

public class ColorPickerWindow extends TWindow {

    private TCellgridLabel label;

    public ColorPickerWindow(TApplication application, int width, int height) {
        super(application, "Color Picker Test", width, height);

        TAction action = new TAction() {
            public void DO() {
                refreshLabel();
            }
        };

        new TColorPicker(this, 1, 0, action, action);
        new TColorPicker(this, 1, 3, BOLD_FIRST, action, action);
        new TColorPicker(this, 18, 0, REVERSE, action, action);
        new TColorPicker(this, 18, 3, BOLD_FIRST | REVERSE, action, action);

        new TColorPicker(this, 36, 0, ONE_LINE, action, action);
        new TColorPicker(this, 36, 2, ONE_LINE | BOLD_FIRST, action, action);
        new TColorPicker(this, 36, 4, ONE_LINE | REVERSE, action, action);
        new TColorPicker(this, 36, 6, ONE_LINE | BOLD_FIRST | REVERSE, action, action);

        new TColorPicker(this, 1, 6, VERTICAL | ONE_LINE, action, action);
        new TColorPicker(this, 4, 6, VERTICAL | ONE_LINE | BOLD_FIRST, action, action);
        new TColorPicker(this, 7, 6, VERTICAL | ONE_LINE | REVERSE, action, action);
        new TColorPicker(this, 10, 6, VERTICAL | ONE_LINE | BOLD_FIRST | REVERSE, action, action);

        new TColorPicker(this, 15, 6, BOX, action, action);
        new TColorPicker(this, 15, 11, BOX | BOLD_FIRST, action, action);
        new TColorPicker(this, 24, 6, BOX | REVERSE, action, action);
        new TColorPicker(this, 24, 11, BOX | BOLD_FIRST | REVERSE, action, action);

        new TColorPicker(this, 34, 8, 4, 2, BOX | TOP_DOWN, action, action);
        new TColorPicker(this, 51, 9, 3, 1, action, action);
        new TColorPicker(this, 34, 17, 1, 1, ONE_LINE | TOP_DOWN, action, action);

        new TColorPicker(this, 15, 16, 3, 1, BOX | BOLD_FIRST | REVERSE, action, action);

        new TColorPicker(this, 76, 0, 1, 1, ONE_LINE | VERTICAL, action, action);
        new TColorPicker(this, 29, 16, 1, 1, BOX | BOLD_FIRST | REVERSE, action, action);

        new TColorPicker(this, 37, 19, 1, 1, BOLD_FIRST | REVERSE, action, action);

        new TColorPicker(this, 70, 0, 1, 1, VERTICAL, action, action);
        new TColorPicker(this, 73, 0, 1, 1, VERTICAL | REVERSE | BOLD_FIRST, action, action);

        new TColorPicker(this, 52, 12, VERTICAL | TOP_DOWN, action, action);
        new TColorPicker(this, 57, 12, VERTICAL | TOP_DOWN | BOLD_FIRST, action, action);
        new TColorPicker(this, 62, 12, VERTICAL | TOP_DOWN | BOLD_FIRST | REVERSE, action, action);
        new TColorPicker(this, 67, 12, VERTICAL | TOP_DOWN | REVERSE, action, action);

        label = new TCellgridLabel(this, 15, 21, new Cellgrid(18, 1, CP437));

    }

    private void refreshLabel() {
        if(getActiveChild() instanceof TColorPicker) {
            TColorPicker colorPicker = (TColorPicker) getActiveChild();
            CellAttributes cellAttributes = new CellAttributes();
            cellAttributes.setForeColor(colorPicker.getFgColor());
            cellAttributes.setBold(colorPicker.isBold());
            cellAttributes.setBackColor(colorPicker.getBgColor());
            label.setOverrideCellAttributes(cellAttributes);
            label.setOverrideCellAttributes(cellAttributes);
            for (int i = 0; i < getChildren().size(); i++) {
                TWidget widget = getChildren().get(i);
                if (widget instanceof TColorPicker) {
                    TColorPicker colPick = (TColorPicker) widget;
                    colPick.setFgColor(colorPicker.getFgColor());
                    colPick.setBold(colorPicker.isBold());
                    colPick.setBgColor(colorPicker.getBgColor());
                }
            }
        }
    }
}
