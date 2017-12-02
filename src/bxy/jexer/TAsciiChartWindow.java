package bxy.jexer;

import jexer.TApplication;
import jexer.TWindow;

import static jexer.bits.GraphicsChars.CP437;

public class TAsciiChartWindow extends TWindow {

    public TAsciiChartWindow(final TApplication application) {
        super(application, "ASCII Chart", 0, 0, 34, 10, NOZOOMBOX);
        new TImage(this, 0, 0, 32, 8, getTheme().getColor("twindow.background.modal"), CP437);

    }

}
