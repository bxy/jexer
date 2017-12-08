package bxy.jexer;

import jexer.*;
import jexer.bits.Cell;
import jexer.bits.CellAttributes;
import jexer.bits.Color;
import jexer.bits.GraphicsChars;

import static jexer.bits.GraphicsChars.*;

public class PlaygroundWindow extends TWindow {

    TCellgridLabel asciiLabel;

    TBackgroundPicker backgroundPicker;
    TForegroundPicker foregroundPicker;

    public PlaygroundWindow(TApplication application, int width, int height) {
        super(application, "Playground Window", width, height);

        // zero height and width
        new TCellgridLabel(this, 0, 0, new Cellgrid(0,0));

        // default colors and chars - emtpy cellgrid
        new TCellgridLabel(this, 4, 2, new Cellgrid(10,8));


        // default colors and chars with explicit cells
        Cellgrid card = new Cellgrid(2, 4);
        // explicitly set cells
        Cell q = new Cell('Q');
        q.setForeColor(Color.RED);
        Cell heart = new Cell(HEART);
        heart.setForeColor(Color.RED);
        card.getCell(0,0).setTo(q);
        card.getCell(0,1).setTo(heart);

        new TCellgridLabel(this, 1, 2, card);

        // cellgrid with background and empty chars, explicitly set HEART
        CellAttributes cellAttributes = new CellAttributes();
        cellAttributes.setBackColor(Color.RED);
        cellAttributes.setForeColor(Color.WHITE);
        new TCellgridLabel(this, 6, 3, new Cellgrid(10, 8, cellAttributes)).setCell(0,0, new Cell(HEART));

        // cellgrid with theme background and chars smaller than cellgrid
        new TCellgridLabel(this, 8, 4, new Cellgrid(30, 12, getTheme().getColor("twindow.background.modal"), CP437));

        // cellgrid with custom background and chars bigger than cellgrid size
        new TCellgridLabel(this, 10, 5, new Cellgrid(5, 5, cellAttributes, CP437));

        // cellgrid with default background and chars
        asciiLabel = new TCellgridLabel(this, 12, 6, new Cellgrid(32, 8, CP437));

        new TCellgridPicker(this, 46, 2, new Cellgrid(32, 8, getTheme().getColor("ttext"), GraphicsChars.CP437)).setSelected(5,5);

        foregroundPicker = new TForegroundPicker(this, "Foreground", 47, 10,
                new TAction() {
                    // When the user presses Enter
                    public void DO() {
                        refreshLabel();
                    }
                },
                new TAction() {
                    // When the user presses Enter
                    public void DO() {
                        refreshLabel();
                    }
                });
        backgroundPicker = new TBackgroundPicker(this, "Background", 61, 10,
                new TAction() {
                    // When the user presses Enter
                    public void DO() {
                        refreshLabel();
                    }
                },
                new TAction() {
                    // When the user presses Enter
                    public void DO() {
                        refreshLabel();
                    }
                });

    }

    private void refreshLabel() {
        CellAttributes cellAttributes = new CellAttributes();
        cellAttributes.setForeColor(foregroundPicker.getColor());
        cellAttributes.setBold(foregroundPicker.isBold());
        cellAttributes.setBackColor(backgroundPicker.getColor());
        asciiLabel.setOverrideCellAttributes(cellAttributes);
    }


}
