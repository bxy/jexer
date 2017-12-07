package bxy.jexer;

import jexer.TApplication;
import jexer.TBackgroundPicker;
import jexer.TForegroundPicker;
import jexer.TWindow;
import jexer.bits.Cell;
import jexer.bits.CellAttributes;
import jexer.bits.Color;
import jexer.bits.GraphicsChars;

import static jexer.bits.GraphicsChars.*;

public class PlaygroundWindow extends TWindow {

    public PlaygroundWindow(TApplication application, int width, int height) {
        super(application, "Playground Window", width, height);

        // zero height and width
        new TCellgridLabel(this, 0, 0, new Cellgrid(0,0));

        // default colors and chars - emtpy cellgrid
        new TCellgridLabel(this, 8, 2, new Cellgrid(10,8));


        // default colors and chars with explicit cells
        Cellgrid card = new Cellgrid(5, 6);
        // explicitly set cells
        Cell q = new Cell('Q');
        q.setForeColor(Color.RED);
        Cell heart = new Cell(HEART);
        heart.setForeColor(Color.RED);
        card.getCell(0,0).setTo(q);
        card.getCell(0,1).setTo(heart);

        new TCellgridLabel(this, 2, 2, card);

        // cellgrid with background and empty chars, explicitly set HEART
        CellAttributes cellAttributes = new CellAttributes();
        cellAttributes.setBackColor(Color.RED);
        cellAttributes.setForeColor(Color.WHITE);
        new TCellgridLabel(this, 10, 3, new Cellgrid(10, 8, cellAttributes)).setCell(0,0, new Cell(HEART));

        // cellgrid with theme background and chars smaller than cellgrid
        new TCellgridLabel(this, 12, 4, new Cellgrid(30, 12, getTheme().getColor("twindow.background.modal"), CP437));

        // cellgrid with custom background and chars bigger than cellgrid size
        new TCellgridLabel(this, 14, 5, new Cellgrid(5, 5, cellAttributes, CP437));

        // cellgrid with default background and chars
        new TCellgridLabel(this, 16, 6, new Cellgrid(32, 8, CP437));

        new TCellgridPicker(this, 50, 2, new Cellgrid(32, 8, getTheme().getColor("ttext"), GraphicsChars.CP437)).setSelected(5,5);
        new TForegroundPicker(this, "Foreground", 51, 10);
        new TBackgroundPicker(this, "Background", 65, 10);

    }


}
