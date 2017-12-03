package bxy.jexer;

import jexer.TApplication;
import jexer.TLabel;
import jexer.TWindow;
import jexer.bits.Cell;
import jexer.bits.Color;
import jexer.bits.GraphicsChars;
import jexer.event.TMouseEvent;

import java.util.Arrays;

public class TAsciiChartWindow extends TWindow {

    private TLabel[] labels = new TLabel[4];
    private TImage asciiImage;

    public TAsciiChartWindow(final TApplication application) {
        super(application, "ASCII Chart", 0, 0, 34, 12, NOZOOMBOX);
        asciiImage = new TImage(this, 0, 0, 32, 8, getTheme().getColor("ttext"), GraphicsChars.CP437);

        char[] line = new char[asciiImage.getWidth()];
        Arrays.fill(line, GraphicsChars.SINGLE_BAR);

        addLabel(new String(line), 0, 8);

        addLabel("Char:", 0, 9, "ttext");
        labels[0] = new TLabel(this, "", 5, 9);

        addLabel("Dec:", 7, 9, "ttext");
        labels[1] = new TLabel(this, "---", 11, 9);

        addLabel("Hex:", 15, 9, "ttext");
        labels[2] = new TLabel(this, "---", 19, 9);

        addLabel("Utf:", 24, 9, "ttext");
        labels[3] = new TLabel(this, "---", 28, 9);

    }

    @Override
    public void onMouseUp(TMouseEvent mouse) {
        if(asciiImage.mouseWouldHit(mouse)) {
            Cell oldSelectedCell = asciiImage.getSelectedCell();
            if(oldSelectedCell != null)
                oldSelectedCell.setAttr(getTheme().getColor("ttext"));
        }

        super.onMouseUp(mouse);

        int index = asciiImage.getSelectedIndex();
        if(index != -1) {
            char ch = GraphicsChars.CP437[index];
            labels[0].setLabel(String.valueOf(ch));
            labels[1].setLabel(String.valueOf(index));
            labels[2].setLabel("0x" + Integer.toHexString(index).toUpperCase());
            labels[3].setLabel(Integer.toHexString((int) ch).toUpperCase());
            asciiImage.getSelectedCell().setForeColor(Color.YELLOW);
            asciiImage.getSelectedCell().setBold(true);
        }

    }
}
