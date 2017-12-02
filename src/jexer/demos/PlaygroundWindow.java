package jexer.demos;

import jexer.TApplication;
import jexer.TImage;
import jexer.TWindow;
import jexer.bits.Cell;
import jexer.bits.Color;

import static jexer.bits.GraphicsChars.*;

public class PlaygroundWindow extends TWindow {

    public PlaygroundWindow(TApplication application, int width, int height) {
        super(application, "Playground Window", width, height);
        TImage card = new TImage(this, 0, 0, 5, 6);

        Cell q = new Cell('Q');
        q.setForeColor(Color.RED);

        Cell heart = new Cell(HEART);
        heart.setForeColor(Color.RED);
        card.setCell(0,0, q);
        card.setCell(0,1, heart);

        new TImage(this, 12, 2, 10,8);
        new TImage(this, 14, 3, 10, 8).setCell(0,0, new Cell(HEART));
        new TImage(this, 16, 4, 32, CP437);
    }


}
