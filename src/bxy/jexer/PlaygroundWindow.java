package bxy.jexer;

import jexer.TApplication;
import jexer.TWindow;
import jexer.bits.Cell;
import jexer.bits.CellAttributes;
import jexer.bits.Color;

import static jexer.bits.GraphicsChars.*;

public class PlaygroundWindow extends TWindow {

    public PlaygroundWindow(TApplication application, int width, int height) {
        super(application, "Playground Window", width, height);

        // zero height and width
        new TImage(this, 0, 0, 0, 0);


        // default colors and chars - empty image
        TImage card = new TImage(this, 2, 2, 5, 6);
        // explicitly set cells
        Cell q = new Cell('Q');
        q.setForeColor(Color.RED);
        Cell heart = new Cell(HEART);
        heart.setForeColor(Color.RED);
        card.setCell(0,0, q);
        card.setCell(0,1, heart);

        // default colors and chars - emtpy image
        new TImage(this, 8, 2, 10,8);

        // image with background and empty chars, explicitly set HEART
        CellAttributes cellAttributes = new CellAttributes();
        cellAttributes.setBackColor(Color.RED);
        cellAttributes.setForeColor(Color.WHITE);
        new TImage(this, 10, 3, 10, 8, cellAttributes).setCell(0,0, new Cell(HEART));

        // image with theme background and chars
        new TImage(this, 12, 4, 30, 12, getTheme().getColor("twindow.background.modal"), CP437);

        new TImage(this, 14, 5, 32, 8, CP437);

    }


}
