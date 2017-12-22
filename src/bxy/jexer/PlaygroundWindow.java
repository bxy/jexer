package bxy.jexer;

import jexer.*;
import jexer.bits.Cell;
import jexer.bits.CellAttributes;
import jexer.bits.Color;
import jexer.bits.GraphicsChars;

import static jexer.bits.GraphicsChars.*;

public class PlaygroundWindow extends TWindow {

    TCellgridLabel asciiLabel;
    TCellgridPicker drawLabel;
    TCellgridPicker boxDrawingLabel;
    TCellgridLabel ansiArtLabel;

    TBackgroundPicker backgroundPicker;
    TForegroundPicker foregroundPicker;

    public PlaygroundWindow(TApplication application, int width, int height) {
        super(application, "Playground Window", width, height);

        // zero height and width
        new TCellgridLabel(this, 0, 0, new Cellgrid(0,0));

        // default colors and chars - emtpy cellgrid
        new TCellgridLabel(this, 4, 1, new Cellgrid(10,8));


        // default colors and chars with explicit cells
        Cellgrid card = new Cellgrid(2, 4);
        // explicitly set cells
        Cell q = new Cell('Q');
        q.setForeColor(Color.RED);
        Cell heart = new Cell(HEART);
        heart.setForeColor(Color.RED);
        card.getCell(0,0).setTo(q);
        card.getCell(0,1).setTo(heart);

        new TCellgridLabel(this, 1, 1, card);

        // cellgrid with background and empty chars, explicitly set HEART
        CellAttributes cellAttributes = new CellAttributes();
        cellAttributes.setBackColor(Color.RED);
        cellAttributes.setForeColor(Color.WHITE);
        new TCellgridLabel(this, 6, 2, new Cellgrid(10, 8, cellAttributes)).setCell(0,0, new Cell(HEART));

        // cellgrid with theme background and chars smaller than cellgrid
        new TCellgridLabel(this, 8, 3, new Cellgrid(30, 12, getTheme().getColor("twindow.background.modal"), CP437));

        // cellgrid with custom background and chars bigger than cellgrid size
        new TCellgridLabel(this, 10, 4, new Cellgrid(5, 5, cellAttributes, CP437));

        // cellgrid with default background and chars
        asciiLabel = new TCellgridLabel(this, 12, 6, new Cellgrid(32, 8, CP437));

        new TCellgridPicker(this, 46, 0, new Cellgrid(32, 8, getTheme().getColor("ttext"), GraphicsChars.CP437)).setSelected(5,5);

        foregroundPicker = new TForegroundPicker(this, "Foreground", 45, 8,
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
        backgroundPicker = new TBackgroundPicker(this, "Background", 59, 8,
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


        String ansiArtChars = "\u258c\u2580\u2584\u00b7\u25a0" +
                "\u00a0\u2591\u2592\u2593\u2588\u2593\u2592\u2591\u00a0" +
                "\u25a0\u00b7\u2584\u2580\u2590\u2685\u265c";

//        String ansiChars = "\u2590\u258c\u2580\u2584\u00b7\u25a0" +
//                "\u00A0\u2591\u2592\u2593\u2588";

        ansiArtLabel = new TCellgridLabel(this, 59, 12, new Cellgrid(ansiArtChars.length() ,2, ansiArtChars.toCharArray()));

        String drawingChars = "\u2580\u2581\u2582\u2583\u2584\u2585\u2586\u2587\u2588\u2589\u258a\u258b\u258c\u258d\u258e\u258f" +
                "\u2590\u2591\u2592\u2593\u2594\u2595\u2596\u2597\u2598\u2599\u259a\u259b\u259c\u259d\u259e\u259f";


        drawLabel = new TCellgridPicker(this, 18, 16, new Cellgrid(16,2, drawingChars.toCharArray()));

        char[] boxDrawingChars = new char[128];

        int i = 0;
        for (char c = '\u2500'; c < '\u2580'; c++) {
            boxDrawingChars[i++] = c;
        }

        boxDrawingLabel = new TCellgridPicker(this, 1, 13, new Cellgrid(16,8, getTheme().getColor("ttext"), boxDrawingChars));

        new TColorPicker(this, 46, 16, 2, 1);

    }

    private void refreshLabel() {
        CellAttributes cellAttributes = new CellAttributes();
        cellAttributes.setForeColor(foregroundPicker.getColor());
        cellAttributes.setBold(foregroundPicker.isBold());
        cellAttributes.setBackColor(backgroundPicker.getColor());
        asciiLabel.setOverrideCellAttributes(cellAttributes);
        ansiArtLabel.setOverrideCellAttributes(cellAttributes);
    }


}
