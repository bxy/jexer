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
        new TImageLabel(this, 0, 0, new TImage(0,0));

        // default colors and chars - emtpy image
        new TImageLabel(this, 8, 2, new TImage(10,8));


        // default colors and chars with explicit cells
        TImage card = new TImage(5, 6);
        // explicitly set cells
        Cell q = new Cell('Q');
        q.setForeColor(Color.RED);
        Cell heart = new Cell(HEART);
        heart.setForeColor(Color.RED);
        card.setCell(0,0, q);
        card.setCell(0,1, heart);

        new TImageLabel(this, 2, 2, card);

        // image with background and empty chars, explicitly set HEART
        CellAttributes cellAttributes = new CellAttributes();
        cellAttributes.setBackColor(Color.RED);
        cellAttributes.setForeColor(Color.WHITE);
        new TImageLabel(this, 10, 3, new TImage(10, 8, cellAttributes)).setCell(0,0, new Cell(HEART));

        // image with theme background and chars
        new TImageLabel(this, 12, 4, new TImage(30, 12, getTheme().getColor("twindow.background.modal"), CP437));

        new TImageLabel(this, 14, 5, new TImage(32, 8, CP437));

    }


}
