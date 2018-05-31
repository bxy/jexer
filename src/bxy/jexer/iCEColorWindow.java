package bxy.jexer;

import jexer.TAction;
import jexer.TApplication;
import jexer.TWindow;
import jexer.bits.CellAttributes;

public class iCEColorWindow extends TWindow {

    private TColorPicker colorPicker;
    private TCellgridLabel[] bgToFgLabel = new TCellgridLabel[8];
    private TCellgridLabel[] fgToBgLabel = new TCellgridLabel[8];

    private TAction action = new TAction() {
        public void DO() {
            refreshLabel();
        }
    };

    public iCEColorWindow(final TApplication application, final int width, final int height) {
        super(application, "iCE Colors Test", width, height);


        String bgToFg = "\u00A0\u2591\u2592\u2593\u2588";
        String fgToBg = "\u2588\u2593\u2592\u2591\u00A0";
//        String bgToFg = "\u00A0\u00A0\u2591\u2591\u2592\u2592\u2593\u2593\u2588\u2588";

        colorPicker = new TColorPicker(this, 1, 1, TColorPicker.VERTICAL | TColorPicker.BOLD_FIRST | TColorPicker.TOP_DOWN, action, action);

        for (int i = 0; i < 8; i++) {
            CellAttributes cellAttributes = new CellAttributes();
            cellAttributes.setBackColor(TColorPicker.colors[i]);
            bgToFgLabel[i] = new TCellgridLabel(this, 6, 1 + i, new Cellgrid(bgToFg.length(), 1, bgToFg.toCharArray()), cellAttributes);
            fgToBgLabel[i] = new TCellgridLabel(this, 12, 1 + i, new Cellgrid(bgToFg.length(), 1, fgToBg.toCharArray()), cellAttributes);
        }



    }

    private void refreshLabel() {
        for (int i = 0; i < 8; i++) {
            CellAttributes bgToFgCellAttributes = bgToFgLabel[i].getCellAttributes();
            bgToFgCellAttributes.setForeColor(colorPicker.getFgColor());
            bgToFgCellAttributes.setBold(colorPicker.isBold());

            CellAttributes fgToBgCellAttributes = fgToBgLabel[i].getCellAttributes();
            fgToBgCellAttributes.setForeColor(colorPicker.getFgColor());
            fgToBgCellAttributes.setBold(colorPicker.isBold());
        }
    }
}
